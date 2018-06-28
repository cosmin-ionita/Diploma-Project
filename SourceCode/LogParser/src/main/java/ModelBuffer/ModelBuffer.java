package ModelBuffer;

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
 *  in a file that will be taken by Flume and then sent into HDFS.
 *
 */

public class ModelBuffer {

    private final int models_per_file = 10000;

    private String file_prefix = "generated_";
    private String destination_directory = "";

    private List<SolrDataModel> models;

    public ModelBuffer() {
        models = new ArrayList<>();
    }

    private void writeSerializedModels(File file) {
        try {
            JSONSerializer serializer = new JSONSerializer();
            FileOutputStream outputStream = new FileOutputStream(file);

            List<String> excludedFields = new ArrayList<>();
            excludedFields.add("class");    /* We exclude this because it's added by the deserializer */

            serializer.setExcludes(excludedFields);

            String serialized_models = serializer.serialize(new ArrayList<>(models));

            outputStream.write(serialized_models.getBytes());
            outputStream.close();

            String filename = file.getAbsolutePath();

            boolean renameResult = file.renameTo(new File(filename.replace(".tmp", "")));

            //Logger.out("File generated: " + file.getName() + " with " + models.size() + " models serialized.");

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void generateFile() {

        if(models.size() > 0) {
            RandomString generator = new RandomString();

            String fileName = destination_directory + file_prefix + generator.getRandomString() + ".tmp";

            File file = new File(fileName);

            writeSerializedModels(file);
        }
    }

    public void addModel(SolrDataModel model) {
        models.add(model);

        if(models.size() == models_per_file) {
            dispatchModels();
        }
    }

    public void dispatchModels() {
        generateFile();
        models.clear();
    }

    public void setDestinationDirectory(String path) {
        destination_directory = path;
    }
}
