import Models.SolrDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 *  This class acts as a repository for the SolrDataModel objects. It ingests the parsed models and stores them
 *  in a file that will be taken by Flume and dumped into HDFS.
 *
 */
public class DataRepository {

    private static int models_per_file = 10;

    private static List<SolrDataModel> models = new ArrayList<>();

    private static void generateFile() {
            /* Creates a file and puts all models in there */
    }

    public static void addModel(SolrDataModel model) {
        models.add(model);

        if(models.size() == models_per_file) {
            generateFile();

            models = new ArrayList<>();
        }
    }
}
