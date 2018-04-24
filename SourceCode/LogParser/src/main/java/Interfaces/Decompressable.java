package Interfaces;

import java.io.File;
import java.util.List;

public interface Decompressable {
    List<File> decompress(File archive);
}
