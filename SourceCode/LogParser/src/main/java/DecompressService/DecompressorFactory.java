package DecompressService;

import Decompressors.TarDecompressor;
import Decompressors.TarGzDecompressor;
import Decompressors.ZipDecompressor;
import Enums.ArchiveTypes;
import Interfaces.Decompressable;

/**
 * This class is a simple implementation of factory pattern for decompressors
 */
public class DecompressorFactory {

    /**
     * This method takes an archive type and returns the appropriate decompressor object
     *
     * @param archiveType - the type of the archive based on it's extension
     * @return - returns the specialized decompressor
     */
    public Decompressable getDecompressor(ArchiveTypes archiveType) {

        if(archiveType == ArchiveTypes.TAR)
            return new TarDecompressor();

        else if(archiveType == ArchiveTypes.ZIP)
            return new ZipDecompressor();

        else if(archiveType == ArchiveTypes.TARGZ)
            return new TarGzDecompressor();

        return null;
    }
}
