package Grok;

import Models.SolrDataModel;
import flexjson.JSONDeserializer;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import Utils.Utils;
import io.krakens.grok.api.Match;
import io.krakens.grok.api.exception.GrokException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GrokEngine {

    private static Map<String, String> logFormats;
    private static Map<String, String> dateLogFormats;

    static
    {
        logFormats = new HashMap<>();

        logFormats.put("format_1", "%{DATESTAMP:timeStamp} (?:.*) %{LOGLEVEL:logLevel}  %{NOTSPACE} jobId=%{NOTSPACE:jobId} - %{GREEDYDATA:message}");
        logFormats.put("format_2", "%{DATESTAMP:timeStamp} %{NOTSPACE} %{LOGLEVEL:logLevel}  %{NOTSPACE}  - %{GREEDYDATA:message}");
        logFormats.put("format_3", "%{TIMESTAMP_ISO8601:timeStamp} %{LOGLEVEL:logLevel} %{GREEDYDATA:message}");

        dateLogFormats = new HashMap<>();

        dateLogFormats.put("format_1", "%{DATE:date} %{TIME:time}");
        dateLogFormats.put("format_2", "%{TIMESTAMP_ISO8601:timeStamp}");
    }

    public static SolrDataModel parseLog(String line) throws GrokException {

        JSONDeserializer deserializer;

        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();

        for(Map.Entry<String, String> entry : logFormats.entrySet()) {

            final Grok grok = grokCompiler.compile(entry.getValue(), true);

            Match match = grok.match(line);

            Map<String, Object> object = match.captureFlattened();

            JSONObject json = new JSONObject(object);

            deserializer = new JSONDeserializer().use(null, SolrDataModel.class);

            SolrDataModel deserializedModel = (SolrDataModel)deserializer.deserialize(json.toString(), SolrDataModel.class);

            if(Utils.checkModel(deserializedModel)) {   /* If the model is checked, we don't try the other logFormats */
                return deserializedModel;
            }
        }

        return null;   /* If there was no correct model found, then set return model to NULL */
    }

    public static boolean startsWithDateTime(String line) throws GrokException {

        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();

        for(Map.Entry<String, String> entry : dateLogFormats.entrySet()) {
            Grok grok = grokCompiler.compile(entry.getValue(), true);

            Match match = grok.match(line);

            String subject = match.getSubject().toString();

            if(!subject.equals("Nothing") && !subject.equals(""))
                return true;
        }

        return false;
    }
}
