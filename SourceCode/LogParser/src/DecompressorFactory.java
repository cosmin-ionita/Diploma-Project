import Decompressors.GzipDecompressor;
import Decompressors.TarDecompressor;
import Decompressors.ZipDecompressor;
import Enums.ArchiveTypes;
import Interfaces.IDecompressable;

/**
 * This class is a simple implementation of factory pattern for decompressors
 */
public class DecompressorFactory {

    /**
     * This method simply takes an archive type and returns the appropriate decompressor object
     *
     * @param archiveType - the type of the archive based on it's extension
     * @return - returns the specialized decompressor
     */
    public IDecompressable getDecompressor(ArchiveTypes archiveType) {

        if(archiveType == ArchiveTypes.TAR)
            return new TarDecompressor();

        else if(archiveType == ArchiveTypes.ZIP)
            return new ZipDecompressor();

        else if(archiveType == ArchiveTypes.GZIP)
            return new GzipDecompressor();

        return null;
    }
}
