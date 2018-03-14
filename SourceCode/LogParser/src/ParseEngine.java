import Models.SolrDataModel;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.List;

/**
 * This class acts as a controller for processing the log files and transforming each log into a structured format
 */
public class ParseEngine {

    /**
     * This method takes a file, parses each log and generates a model for each log. All models are stored in a
     * data repository. This method runs on a working thread.
     *
     * @param file - the file that it's analyzed
     */
    private static void processFile(File file) {

        String line;
        SolrDataModel model;

        Class templates = ParserTemplates.class;

        try{

            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {

                model = new SolrDataModel();

                Utils.updateHostname(model);
                Utils.updateSourceFileName(model, file);

                for(Method template : templates.getMethods()) {

                        model = (SolrDataModel)template.invoke(null, line);

                        if(Utils.checkModel(model))
                                DataRepository.addModel(model);
                }
            }
        } catch(IOException | InvocationTargetException | IllegalAccessException exception) {
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
    public static void ParseArchive(Path archivePath) {

        Thread thread = new Thread(() -> {

            List<File> files = DecompressService.decompressArchive(archivePath);

            for(File file : files) {
                processFile(file);
            }
        });

        thread.start();
    }
}
