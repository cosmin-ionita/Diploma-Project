package DecompressorTests;

import Decompressors.TarDecompressor;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarOutputStream;

import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class TarDecompressorTest {

    private List<String> targetHash = new ArrayList<>();

    private String sourceFilesDirectory = "/Users/ioni/Desktop/Diploma-Project-Repository/Diploma-Project-Repository/Diploma-Project/SourceCode/LogParser/src/test/TestResources/Decompressors/";
    private String destinationFilesDirectory = "/Users/ioni/Desktop/Diploma-Project-Repository/Diploma-Project-Repository/Diploma-Project/SourceCode/LogParser/src/test/TestResources/Decompressors/";

    /**
     * Compute the SHA1 digest on a file
     * @param file
     * @return
     * @throws Exception
     */
    private String sha1(File file) throws Exception  {

        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        InputStream fis = new FileInputStream(file);

        int n = 0;
        byte[] buffer = new byte[8192];

        while (n != -1) {
            n = fis.read(buffer);
            if (n > 0) {
                digest.update(buffer, 0, n);
            }
        }

        return new String(digest.digest());
    }

    private String getArchiveName(){
        return destinationFilesDirectory + "archive.tar";
    }

    /**
     * Generate the TAR archive with the files placed in TestResources/Decompressors
     * @throws Exception
     */

    @BeforeAll
    public void setupArchives() throws Exception {
        int count;
        byte data[] = new byte[2048];

        File directory = new File(sourceFilesDirectory);

        FileOutputStream destination_stream = new FileOutputStream(getArchiveName());

        TarOutputStream output_stream = new TarOutputStream(new BufferedOutputStream(destination_stream));

        for(File file : directory.listFiles()) {

            if(file.getName().contains("file")) {

                targetHash.add(sha1(file));

                output_stream.putNextEntry(new TarEntry(file, file.getName()));

                BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file));

                while((count = origin.read(data)) != -1) {
                    output_stream.write(data, 0, count);
                }

                output_stream.flush();
                origin.close();
            }
        }

        output_stream.close();
        destination_stream.close();
    }

    /**
     * Decompress the archive and rehash the extracted files
     * @throws Exception
     */
    @Test
    public void testDecompressor() throws Exception{
        setupArchives();

        List<String> outputHash = new ArrayList<>();

        TarDecompressor decompressor = new TarDecompressor();

        List<File> files = decompressor.decompress(new File(getArchiveName()));

        for(File file : files) {
            outputHash.add(sha1(file));
        }

        assert outputHash.equals(targetHash);

        cleanTest();
    }

    /**
     * General cleanup
     * @throws Exception
     */
    @AfterAll
    public void cleanTest() throws IOException{
        File archive = new File(getArchiveName());
        File outputDirectory = new File(destinationFilesDirectory + "extracted_archive");

        FileUtils.deleteDirectory(outputDirectory);
        archive.delete();
    }
}
