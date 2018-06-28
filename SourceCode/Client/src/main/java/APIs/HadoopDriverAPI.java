package APIs;

import Models.HadoopApiResponse;
import flexjson.JSONDeserializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HadoopDriverAPI {

    private static final String baseUrl = "http://localhost:55555/";

    private static HadoopApiResponse executeRequest(String url) {

        String inputLine;
        StringBuffer response = new StringBuffer();

        JSONDeserializer deserializer = new JSONDeserializer().use(null, HadoopApiResponse.class);

        try {
            URL urlObject = new URL(url);

            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        }catch (IOException exception) {
            return new HadoopApiResponse("Error");
        }

        return (HadoopApiResponse)deserializer.deserialize(response.toString(), HadoopApiResponse.class);
    }

    public static String getStatus() {
        String specificUrl = baseUrl + "status";

        HadoopApiResponse response = executeRequest(specificUrl);

        return response.getMessage();
    }

    public static String setIndexInterval(String interval) {
        String specificUrl = baseUrl + "index_interval?interval=" + interval;

        HadoopApiResponse response = executeRequest(specificUrl);

        return response.getMessage();
    }

    public static String triggerIndexJob() {
        String specificUrl = baseUrl + "trigger_index_job";

        HadoopApiResponse response = executeRequest(specificUrl);

        return response.getMessage();
    }

    public static String getIndexJobStatus() {
        String specificUrl = baseUrl + "index_status";

        HadoopApiResponse response = executeRequest(specificUrl);

        return response.getMessage();
    }
}
