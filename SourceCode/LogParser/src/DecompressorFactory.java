import Decompressors.GzipDecompressor;
import Decompressors.TarDecompressor;
import Decompressors.ZipDecompressor;
import Enums.ArchiveTypes;
import Interfaces.IDecompressable;

public class DecompressorFactory {

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
