import Enums.ArchiveTypes;
import Interfaces.IDecompressable;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 *  This class is just a controller for decompressors.
 */
public class DecompressService {

    /**
     * This method takes a path to an archive, retrieves the extension, takes the appropriate decompressor and uses it
     * to obtain the files inside the archive.
     *
     * @param archivePath - the path to the archive (this comes directly from the WatchEngine)
     * @return - the list of files contained in the archive
     */
    public static List<File> decompressArchive(Path archivePath) {

        List<File> files;
        DecompressorFactory factory = new DecompressorFactory();

        String extension = Utils.getExtension(archivePath.toString());

        IDecompressable decompressor = factory.getDecompressor(ArchiveTypes.valueOf(extension));
        assert decompressor != null;

        files = decompressor.decompress(archivePath.toFile());

        return files;
    }
}
