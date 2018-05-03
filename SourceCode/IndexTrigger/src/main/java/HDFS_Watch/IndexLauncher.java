package HDFS_Watch;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class IndexLauncher {

    private final static String scriptName = "run_index_job.sh";
    private final static String inputFileList = "input_files.txt";  /* This must match the name in the bash script */

    private static void createInputFile(List<String> files) throws IOException{

        FileWriter writer = new FileWriter(inputFileList);

        for(String hdfs_file : files) {
            writer.write(hdfs_file);    /* TODO: check if the newline is appended by default or not */
        }

        writer.close();
    }

    public static void launchIndexJob(List<String> files) throws IOException {

        createInputFile(files);

        String[] script = new String[]{"/bin/bash", scriptName};

        Process process = Runtime.getRuntime().exec(script);
    }
}
