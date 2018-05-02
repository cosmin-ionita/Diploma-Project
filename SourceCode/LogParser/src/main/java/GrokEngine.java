import Models.SolrDataModel;
import flexjson.JSONDeserializer;
import io.thekraken.grok.api.Grok;
import io.thekraken.grok.api.Match;
import io.thekraken.grok.api.exception.GrokException;
import Utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class GrokEngine {

    private static Map<String, String> logFormats;

    private static final String grokPatternsPath = "/Users/ioni/Desktop/Diploma-Project-Repository/Diploma-Project-Repository/Diploma-Project/SourceCode/LogParser/src/main/java/GrokPatterns/patterns";

    private static String validLogFormat = "%{DATE:date} %{TIME:time} %{GREEDYDATA:message}";

    static
    {
        logFormats = new HashMap<>();

        logFormats.put("format_1", "%{DATE:date} %{TIME:time} (?:.*) %{LOGLEVEL:logLevel}  %{NOTSPACE} jobId=%{NOTSPACE:jobId} - %{GREEDYDATA:message}");
        logFormats.put("format_2", "%{CUSTOMDATE:date} %{TIME:time} %{NOTSPACE} %{LOGLEVEL:logLevel}  %{NOTSPACE}  - %{GREEDYDATA:message}");
    }

    public static SolrDataModel parseLog(String line, SolrDataModel model) throws GrokException {

        String serializedModel;
        JSONDeserializer deserializer;

        Grok grok = Grok.create(grokPatternsPath);

        for(Map.Entry<String, String> entry : logFormats.entrySet()) {

            grok.compile(entry.getValue(), true);

            Match match = grok.match(line);

            match.captures();

            serializedModel = match.toJson();

            deserializer = new JSONDeserializer().use(null, SolrDataModel.class);

            SolrDataModel deserializedModel = (SolrDataModel)deserializer.deserialize(serializedModel, SolrDataModel.class);

            if(Utils.checkModel(deserializedModel)) {   /* If the model is checked, we don't try the other logFormats */
                return deserializedModel;
            }
        }

        return null;   /* If there was no correct model found, then set return model to NULL */
    }

    public static boolean startsWithDateTime(String line) throws GrokException {

        Grok grok = Grok.create(grokPatternsPath);

        grok.compile(validLogFormat, true);

        Match match = grok.match(line);

        return !match.getSubject().equals("Nothing");
    }
}
