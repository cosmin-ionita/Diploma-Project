import Models.SolrDataModel;
import Utils.RandomString;
import flexjson.JSONSerializer;

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

    private int models_per_file = 10000;

    private String file_prefix = "generated_";
    private String destination_directory = "";

    private List<SolrDataModel> models;

    public DataRepository() {
        models = new ArrayList<>();
    }

    private void generateFile() {

        /* Take each model and write it in a file */

        RandomString generator = new RandomString();
        JSONSerializer serializer = new JSONSerializer();

        String serialized_models = serializer.serialize(new ArrayList(models));

        String fileName = destination_directory + file_prefix + generator.getRandomString();

        File file = new File(fileName);

        try {

            FileOutputStream outputStream = new FileOutputStream(file);

            outputStream.write(serialized_models.getBytes());

            outputStream.close();

            Logger.out("File generated: " + fileName + " with " + models.size() + " models serialized.");

        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void addModel(SolrDataModel model) {
        models.add(model);

        if(models.size() == models_per_file) {
            generateFile();

            models.clear();
        }
    }

    public void setDestinationDirectory(String path) {
        destination_directory = path;
    }

    /**
     * This method simply generate the models file with the existing models stored in models list.
     *
     * It is called at the end of the parsing stage in order to flush the accumulated models (probably less
     * than models_per_file)
     */
    public void dispatchModels() {
        generateFile();

        models.clear();
    }
}
