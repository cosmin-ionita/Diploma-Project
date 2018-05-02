package Decompressors;

import Interfaces.Decompressable;
import Utils.Utils;
import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarInputStream;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class TarGzDecompressor implements Decompressable {

    private static final int chunk_size = 2048;
    private static final String extractedDirectoryPrefix = "extracted_";

    private String getDestinationDirectory(File archive) {

        Path path = Paths.get(archive.getAbsolutePath());

        path = path.getParent().resolve(extractedDirectoryPrefix + Utils.getFileName(archive.getName()));

        Utils.createDirectory(path.toString());

        return path.toString();
    }

    public List<File> decompress(File archive) {
        int count;

        TarEntry entry;

        List<File> extractedFiles = new ArrayList<>();

        String fileName = "";
        String destDirectory = getDestinationDirectory(archive);

        try {

            while (!Utils.isFileClosed(archive))
                ;

            FileInputStream stream = new FileInputStream(archive);

            GZIPInputStream gzipInputStream = new GZIPInputStream(stream);

            TarInputStream inputStream = new TarInputStream(new BufferedInputStream(gzipInputStream));

            while (( entry = inputStream.getNextEntry() ) != null) {

                if(!entry.isDirectory()) {

                    byte data[] = new byte[chunk_size];

                    fileName = destDirectory + "/" + entry.getName();

                    FileOutputStream outputStream = new FileOutputStream(fileName);
                    BufferedOutputStream bufferedStream = new BufferedOutputStream(outputStream);

                    while (( count = inputStream.read( data ) ) != -1)
                        bufferedStream.write(data, 0, count );

                    extractedFiles.add(new File(fileName));

                    bufferedStream.flush();
                    bufferedStream.close();
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return extractedFiles;
    }
}
