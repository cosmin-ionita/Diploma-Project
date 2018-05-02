import Models.SolrDataModel;
import Utils.Utils;
import io.thekraken.grok.api.exception.GrokException;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

/**
 * This class acts as a controller for processing the log files and transforming each log into a structured format
 */
public class ParseEngine {

    private static String destinationDirectory = "";

    private static void processLogEvent(String line, File file, ModelBuffer buffer) throws GrokException{

        SolrDataModel model = new SolrDataModel();

        model = GrokEngine.parseLog(line, model);

        if(model != null) {
            Utils.updateHostname(model);
            Utils.updateSourceFileName(model, file);

            buffer.addModel(model);
        } else {
            Logger.out("The model is not correct. Line = " + line);
        }
    }

    private static void processFile(File file) {
        String line, completeLine = "";

        ModelBuffer buffer = new ModelBuffer();

        buffer.setDestinationDirectory(destinationDirectory);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {

                if (GrokEngine.startsWithDateTime(line)) {  /* i.e. we found a new log line */

                    if (!completeLine.isEmpty())
                        processLogEvent(completeLine, file, buffer);

                    completeLine = line;
                }
                else
                    completeLine += line;
            }

            if(!completeLine.isEmpty())
                processLogEvent(completeLine, file, buffer);

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
    public static void ParseArchive(Path archivePath, String destinationDirectory) {

        ParseEngine.destinationDirectory = destinationDirectory;

        Thread thread = new Thread(() -> {

            List<File> files = DecompressService.decompressArchive(archivePath);

            for(File file : files) {
                processFile(file);
            }
        });

       thread.start();
    }
}
