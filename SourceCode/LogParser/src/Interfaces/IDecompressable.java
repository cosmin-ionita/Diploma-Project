package Interfaces;

import java.io.File;
import java.util.List;

public interface IDecompressable {

    List<File> decompress(File archive);

}
