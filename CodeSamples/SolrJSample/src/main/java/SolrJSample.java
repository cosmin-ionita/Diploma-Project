import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SolrJSample {


    private static HttpSolrClient getSolrClient() {

        final String solrUrl = "http://localhost:8983/solr";

        return new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }

    private static void executeQuery() {

        final HttpSolrClient client = getSolrClient();

        final Map<String, String> queryParamMap = new HashMap<String, String>();

        queryParamMap.put("q", "*:*");
        //queryParamMap.put("fl", "id, name");

        MapSolrParams queryParams = new MapSolrParams(queryParamMap);

        final QueryResponse response;

        try {
            response = client.query("newCore", queryParams);

            final SolrDocumentList documents = response.getResults();

            for(SolrDocument document : documents) {

            }
        }
        catch(SolrServerException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main( String[] args ) {
        executeQuery();
    }
}
