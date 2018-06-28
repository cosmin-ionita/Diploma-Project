package ParseEngine;

import Grok.GrokEngine;
import Models.SolrDataModel;
import Utils.Utils;
import io.krakens.grok.api.exception.GrokException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import DecompressService.*;
import ModelBuffer.*;

/**
 * This class acts as a controller for processing the log files and transforming each log into a structured format
 */
public class ParseEngine {

    private static String destinationDirectory = "";

    private static void processLogEvent(String line, int lineNumber, File file, ModelBuffer buffer) throws GrokException {

        SolrDataModel model;

        model = GrokEngine.parseLog(line);

        if(model != null) {
            Utils.updateSourceFileName(model, file);
            Utils.updateLineNumber(model, lineNumber);

            buffer.addModel(model);
        } else {
            //Logger.out("The model is not correct. Line = " + line);
        }
    }

    private static void processFile(File file) {
        int currentLineNumber = 1, eventLineNumber = 0;

        String line, completeLine = "";

        ModelBuffer buffer = new ModelBuffer();

        buffer.setDestinationDirectory(destinationDirectory);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {

                if (GrokEngine.startsWithDateTime(line)) {  /* we found a new log line */

                    if (!completeLine.isEmpty())
                        processLogEvent(completeLine, eventLineNumber, file, buffer);

                    eventLineNumber = currentLineNumber;

                    completeLine = line;
                }
                else
                    completeLine += line;

                currentLineNumber++;
            }

            if(!completeLine.isEmpty())
                processLogEvent(completeLine, eventLineNumber, file, buffer);

            Utils.cleanup(file);

            buffer.dispatchModels();

        } catch(IOException | GrokException exception) {
            exception.printStackTrace();
        }
    }

    /**
     *  This method takes a Path object to an archive, extracts the files contained in it and processes each extracted
     *  file. There is one thread per archive.
     *
     *  It is called by the WatchEngine (for each new archive that appeared in a specific directory)
     *
     * @param archivePath - the path to the archive
     */
    public static void parseArchive(Path archivePath, String destinationDirectory) {

        final Path archive = archivePath;

        ParseEngine.destinationDirectory = destinationDirectory;

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                List<File> files = DecompressService.decompressArchive(archive);

                for(File file : files) {
                    processFile(file);
                }

                try {

                    Files.delete(archivePath);

                    System.out.println("The archive " + archivePath.toString() + " was completely processed!");

                }catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

       thread.start();
    }
}
