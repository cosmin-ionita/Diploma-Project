import Utils.RandomString;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    // (10, 10.000) = 13 MB date, 6.3 MB arhivat
    private final static int filesPerArchive = 10;
    private final static int linesPerFile = 10000;

    private final static String bucketName = "aam-ioni-qe-1-test-logs";
    private final static String workDir = "/home/ec2-user/workDir/";

    private final static RandomString random = new RandomString();

    private static String getFileName(String directory) {
        return workDir + directory + "/" + random.getRandomString() + ".file";
    }

    private static String getArchiveName(String directory) {
        return workDir + directory + "/" + random.getRandomString() + ".zip";
    }

    private static ByteArrayInputStream getByteArrayInputStream(File file) throws IOException {
        return new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
    }

    /**
     * This generates files in a specific directory
     *
     * @param directory - the directory that will be used as a workDir
     */
    private static void generateFiles(String directory) {
        FileWriter writer;

        try {
            for (int i = 0; i < filesPerArchive; i++) {

                writer = new FileWriter(getFileName(directory));

                for (int j = 0; j < linesPerFile; j++) {
                    String line = Utils.Utils.generateFileLine();

                    //System.out.println("Generated line: " + line);

                    writer.write(line);
                }

                writer.close();
            }
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This creates an archive from a set of files
     * @param dir - the directory that contains the files that need to be archived
     * @return - the name of the archive
     */
    private static String createArchive(String dir) {
        int count;
        byte data[] = new byte[2048];

        File directory = new File(workDir + dir);

        String archiveName = getArchiveName(dir);

        try {

            FileOutputStream destination_stream = new FileOutputStream(archiveName);

            ZipOutputStream output_stream = new ZipOutputStream(new BufferedOutputStream(destination_stream));

            for (File file : directory.listFiles()) {

                if (file.getName().endsWith(".file")) {
                    output_stream.putNextEntry(new ZipEntry(file.getName()));

                    BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file));

                    while ((count = origin.read(data)) != -1) {
                        output_stream.write(data, 0, count);
                    }

                    output_stream.flush();
                    origin.close();
                }
            }

            output_stream.close();
            destination_stream.close();

        } catch (IOException exception) {
            exception.printStackTrace();;
        }

        return archiveName;
    }

    private static void uploadArchive(String archive){
        try {
            AmazonS3 client = AmazonS3ClientBuilder.defaultClient();

            File file = new File(archive);

            //for(File file : dir.listFiles()) {

                ByteArrayInputStream stream = getByteArrayInputStream(file);

                ObjectMetadata metadata = new ObjectMetadata();

                metadata.setContentLength(file.length());

                metadata.setContentType("application/octet-stream");

                client.putObject(bucketName, file.getName(), stream, metadata);
            //}

        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This will clean everything that was generated for a specific directory
     * @param directory - the directory that needs to be deleted completely
     */
    private static void deleteEverything(String directory) {
        File dir = new File(directory);

        for(File file : dir.listFiles()) {
            file.delete();
        }

        dir.delete();
    }

    /**
     * This powers a thread that generates an archive and uploads it to S3
     */
    private static class ArchiveUploader implements Runnable {
        private ArchiveUploader() {}

        @Override
        public void run() {

            String directory = random.getRandomString();

            File newWorkDir = new File(workDir + directory);

            newWorkDir.mkdir();

            generateFiles(directory);

            String archive = createArchive(directory);

            uploadArchive(archive);

            deleteEverything(workDir + directory);
        }
    }

    /**
     *  Main driver for the generator
     * @param args - the number of archives to generate, -1 for unlimited archives
     */
    public static void main(String args[]) {

            ExecutorService executor = Executors.newFixedThreadPool(8);

            int i = 0, count = Integer.parseInt(args[0]);

            if(count == -1) {
                System.out.println("Generating unlimited archives...");

                while(true) {
                    executor.submit(new ArchiveUploader());

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            } else {

                System.out.println("Generating " + count + " archives...");

                while(i < count) {

                    executor.submit(new ArchiveUploader());

                    i++;

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
    }
}
