package DecompressorTests;

import Decompressors.TarGzDecompressor;
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
import java.util.zip.GZIPOutputStream;

public class TarGzDecompressorTest {
    private List<String> targetHash = new ArrayList<>();

    private String sourceFilesDirectory = "/Users/ioni/Desktop/Diploma-Project-Repository/Diploma-Project-Repository/Diploma-Project/SourceCode/LogParser/src/test/TestResources/Decompressors/";
    private String destinationFilesDirectory = "/Users/ioni/Desktop/Diploma-Project-Repository/Diploma-Project-Repository/Diploma-Project/SourceCode/LogParser/src/test/TestResources/Decompressors/";

    private String sha1(File file) throws Exception  {

        int n = 0;
        byte[] buffer = new byte[8192];

        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        InputStream fis = new FileInputStream(file);

        while (n != -1) {
            n = fis.read(buffer);
            if (n > 0) {
                digest.update(buffer, 0, n);
            }
        }

        return new String(digest.digest());
    }

    private String getArchiveName(){
        return destinationFilesDirectory + "archive.tar.gz";
    }

    @BeforeAll
    void setupArchives() throws Exception {
        int count;
        byte data[] = new byte[2048];

        File directory = new File(sourceFilesDirectory);

        FileOutputStream destination_stream = new FileOutputStream(getArchiveName());

        GZIPOutputStream gzip_output_stream = new GZIPOutputStream(new BufferedOutputStream(destination_stream));
        TarOutputStream outputStream = new TarOutputStream(gzip_output_stream);

        for(File file : directory.listFiles()) {

            if(file.getName().contains("file")) {

                targetHash.add(sha1(file));

                outputStream.putNextEntry(new TarEntry(file, file.getName()));

                BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file));

                while((count = origin.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                }

                outputStream.flush();
                origin.close();
            }
        }

        outputStream.close();
    }

    @Test
    public void testDecompressor() throws Exception{

        setupArchives();

        List<String> outputHash = new ArrayList<>();

        TarGzDecompressor decompressor = new TarGzDecompressor();

        List<File> files = decompressor.decompress(new File(getArchiveName()));

        for(File file : files) {
            outputHash.add(sha1(file));
        }

        assert outputHash.equals(targetHash);

        cleanTest();
    }

    @AfterAll
    void cleanTest() throws IOException{
        File archive = new File(getArchiveName());
        File outputDirectory = new File(destinationFilesDirectory + "extracted_archive");

        FileUtils.deleteDirectory(outputDirectory);
        archive.delete();
    }
}
