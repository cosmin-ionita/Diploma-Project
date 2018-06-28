package ParserTests;

import Grok.GrokEngine;
import Models.SolrDataModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GrokTest {

    static List<String> testLines = new ArrayList<>();
    static List<SolrDataModel> correctResults = new ArrayList<>();

    @BeforeAll
    public static void setupInput() {
        testLines.add("2018-06-05T18:39:46Z INFO test test test test test");
        testLines.add("05-06-2018 12:22:22 test ERROR  test  - test test test test test");

        correctResults.add(new SolrDataModel("2018-06-05T18:39:46Z", "INFO", "test test test test test"));
        correctResults.add(new SolrDataModel("05-06-2018 12:22:22", "ERROR", "test test test test test"));
    }

    @Test
    public void testGrok() {
        setupInput();

        for(int i = 0; i < testLines.size(); i++) {
            SolrDataModel result = GrokEngine.parseLog(testLines.get(i));

            assert result.equals(correctResults.get(i));
        }
    }
}
