package HDFS_Watcher;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class IndexLauncher {

    private final static String bashExec = "/bin/bash";

    private final static String scriptName = "run_index_job.sh";
    private final static String inputFileList = "input_files.txt";

    /**
     *  This method will write the list of file names to a file that will be passed to the
     *  indexing job (as an input)
     *
     * @param files - the list of files to be written to the input_files.txt file
     * @throws IOException - if the file does not exist, this exception will be raised
     */
    private static void createInputFile(List<String> files) throws IOException {

        FileWriter writer = new FileWriter(inputFileList);

        for(String hdfs_file : files) {
            writer.write(hdfs_file + "\n");
        }

        writer.close();
    }

    /**
     *  This method is called from the Watcher module, it will launch the indexing job
     *
     * @param files - the list of files that will be indexed
     * @return - true if the job has started successfully, false otherwise (for example if there are no files to be indexed)
     * @throws IOException
     */
    public static boolean launchIndexJob(List<String> files) throws IOException {

        if(files.size() > 0) {
            createInputFile(files);

            System.out.println("Executing index job with " + files.size() + " files");

            String[] script = new String[]{bashExec, scriptName};

            Process process = Runtime.getRuntime().exec(script);

            Watcher.watchIndexJob(process);

            return true;

        } else {
            System.out.println("Nothing to index, continuing...");

            return false;
        }
    }
}