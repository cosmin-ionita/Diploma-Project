import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarOutputStream;

import java.io.*;
import java.sql.SQLOutput;

/**
 *
 * This class takes a large log file (over 100 MB) and splits it into multiple files, 2 MB in size.
 * It also archives a configurable set of small files in order to simulate a real world scenario.
 *
 */

public class Main {

    private static final int files_per_archive = 10;
    private static final int file_size = 2000000;   /*  2.000.000 bytes == 2 MB  */

    private static String large_log_filename = "/Users/ioni/logs.txt";
    private static String working_directory = "/Users/ioni/streaming_logs/";

    private static String getFileName(int no) {
        return Main.working_directory + "log_" + no + ".txt";
    }

    private static String getArchiveName(int no) {
        return Main.working_directory + "log_archive_" + no + ".tar";
    }

    /**
     *  This method returns a new writer associated to a file
     *
     * @param file_no - the unique identifier of the file
     * @return - the buffered writer mapped to the specified file
     */
    private static BufferedWriter updateWriter(int file_no) {

        BufferedWriter wr = null;

        String file_name = getFileName(file_no);

        try {
            wr = new BufferedWriter(new FileWriter(new File(file_name)));
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

        return wr;
    }

    /**
     *  This method generates an archive with the specified id
     *
     * @param archive_id - the unique id of the archive that will be generated
     * @param files_count - the number of files that will be archived
     */
    private static void generateArchive(int archive_id, int files_count) {

        try {

            Thread.sleep(3000);

            int count;
            byte data[] = new byte[2048];

            File[] files = new File[files_count];

            FileOutputStream destination_stream = new FileOutputStream(getArchiveName(archive_id));
            TarOutputStream output_stream = new TarOutputStream(new BufferedOutputStream(destination_stream));

            for(int i = 0; i < files_count; i++)
                files[i] = new File(getFileName(i));

            /* Iterate over the files and build the archive */
            for(File file : files) {

                output_stream.putNextEntry(new TarEntry(file, file.getName()));

                BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file));

                while((count = origin.read(data)) != -1) {
                    output_stream.write(data, 0, count);
                }

                output_stream.flush();
                origin.close();
            }

            output_stream.close();
        }
        catch(FileNotFoundException | InterruptedException exception) {
            exception.printStackTrace();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This method removes all files that were generated with the id in [0, files_per_archive)
     */

    private static void removeFiles() {
        File file;

        for(int i = 0; i < Main.files_per_archive; i++) {
            file = new File(getFileName(i));

            if(file.exists())
                file.delete();
        }
    }

    public static void main(String args[]) {

        String line, file_name;
        int file_no = 0, bytes_written = 0, archive_id = 0;

        while(args.length != 2 || !Utils.checkInput(args[0], args[1])) {
            System.out.println("Usage: java -jar Log_Generator.jar --input-file=/path/to/input/file --destination-directory=/path/to/dest/dir");
            return;
        }

        Main.large_log_filename = args[0].split("=")[1];
        Main.working_directory = args[1].split("=")[1];

        try {

            BufferedWriter wr = null;
            BufferedReader br = new BufferedReader(new FileReader(new File(Main.large_log_filename)));

            wr = updateWriter(file_no);

            while ((line = br.readLine()) != null) {
                line += "\n";

                wr.write(line);

                bytes_written += line.length();

                if(bytes_written >= Main.file_size) {             /* if the file has 2 MB in size */
                    bytes_written = 0;

                    if(file_no == Main.files_per_archive) {   /* if we generate a number of files, we archive them */
                        file_no = 0;

                        generateArchive(archive_id++, Main.files_per_archive);
                        removeFiles();
                    }

                    wr = updateWriter(file_no++);

                    assert wr != null;
                }
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

        /* Archive the remaining files (possible less than Main.files_per_archive */

        generateArchive(archive_id, file_no);
        removeFiles();

        Logger.out("Done!");
    }
}
