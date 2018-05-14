package APIs;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolrAPI {

    private static HttpSolrClient solrClient = null;
    private static final String solrUrl = "http://localhost:8983/solr";

    static {
        createSolrClient();
    }

    private static void createSolrClient() {
        solrClient = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }

    private static List<String> executeQuery() throws SolrServerException, IOException {
        final QueryResponse response;

        final Map<String, String> queryParamMap = new HashMap<String, String>();

        //queryParamMap.put("q", "*:*");
        //queryParamMap.put("fl", "id, name");

        MapSolrParams queryParams = new MapSolrParams(queryParamMap);

        response = solrClient.query("newCore", queryParams);

        final SolrDocumentList documents = response.getResults();

        for(SolrDocument document : documents) {

        }

        return null;
    }
}
