package ParserTests;

import ParseEngine.ParseEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ParseEngineTest {

    private static String archivePath = "/Users/ioni/Desktop/Diploma-Project-Repository/Diploma-Project-Repository/Diploma-Project/SourceCode/LogParser/src/test/TestResources/ParseEngine/TestArchive/archive.zip";
    private static String destinationDirectory = "/Users/ioni/Desktop/Diploma-Project-Repository/Diploma-Project-Repository/Diploma-Project/SourceCode/LogParser/src/test/TestResources/ParseEngine/DestinationDirectory/";

    @BeforeAll
    public static void setupInput() {

    }

    @Test
    public void testGrok() {
        File archive = new File(archivePath);

        ParseEngine.parseArchive(archive.toPath(), destinationDirectory);

        try {
            Thread.sleep(10000);
        }catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
