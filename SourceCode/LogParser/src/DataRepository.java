import Models.SolrDataModel;
import flexjson.JSONSerializer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class acts as a repository for the SolrDataModel objects. It ingests the parsed models and stores them
 *  in a file that will be taken by Flume and dumped into HDFS.
 *
 */
public class DataRepository {

    private static int models_per_file = 100;

    private static int file_count = 0;
    private static String file_prefix = "generated_";
    private static String destination_directory = "/Users/ioni/streaming_logs/generated/";

    private static List<SolrDataModel> models = new ArrayList<>();

    private static void generateFile() {

        /* Take each model and write it in a file */

        List<SolrDataModel> back_models = new ArrayList<>(models);

        JSONSerializer serializer = new JSONSerializer();

        String serialized_models = serializer.serialize(back_models);

        String fileName = destination_directory + file_prefix + file_count++;

        File file = new File(fileName);

        try {

            FileOutputStream outputStream = new FileOutputStream(file);

            outputStream.write(serialized_models.getBytes());

            outputStream.close();

            Logger.out("File generated: " + fileName + " with " + back_models.size() + " models serialized.");

        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void addModel(SolrDataModel model) {
        models.add(model);

        if(models.size() == models_per_file) {
            generateFile();

            models = new ArrayList<>();
        }
    }

    public static void setDestinationDirectory(String path) {
        destination_directory = path;
    }

    /**
     * This method simply generate the models file with the existing models stored in models list.
     *
     * It is called at the end of the parsing stage in order to flush the accumulated models (probably less
     * than models_per_file)
     */
    public static void dispatchModels() {
        generateFile();

        models = new ArrayList<>();
    }
}
