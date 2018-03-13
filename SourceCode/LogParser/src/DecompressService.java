import Decompressors.TarDecompressor;
import Enums.ArchiveTypes;
import Interfaces.IDecompressable;
import javafx.scene.shape.Arc;

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

        List<File> files;
        DecompressorFactory factory = new DecompressorFactory();

        String extension = getExtension(path.toString());

        IDecompressable decompressor = factory.getDecompressor(ArchiveTypes.valueOf(extension));
        assert decompressor != null;

        files = decompressor.decompress(path.toFile());

        return files;
    }
}
