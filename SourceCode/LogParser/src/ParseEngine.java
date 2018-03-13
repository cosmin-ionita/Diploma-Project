import Models.SolrDataModel;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class ParseEngine {

    private static void processFile(File file) {
        SolrDataModel model = new SolrDataModel();

        
    }

    public static void ParseArchive(Path path) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                List<File> files = DecompressService.decompressArchive(path);

                for(File file : files) {
                    processFile(file);
                }
            }
        });

        thread.start();
    }
}
