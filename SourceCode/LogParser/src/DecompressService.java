import Decompressors.TarDecompressor;
import Enums.ArchiveTypes;
import Interfaces.IDecompressable;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DecompressService {

    /* This should go into an utils-like module / class */
    private static String getExtension(String fileName) {
        return fileName.split(".")[1];
    }

    public static List<File> decompressArchive(Path path) {

        List<File> files = new ArrayList<File>();

        String extension = getExtension(path.toString());

        if(extension == ArchiveTypes.TAR.toString()) {
            IDecompressable decompressor = new TarDecompressor();

            decompressor.decompress(path.toFile());
        }
        else if(extension == ArchiveTypes.ZIP.toString()) {

        }

        return files;
    }
}
