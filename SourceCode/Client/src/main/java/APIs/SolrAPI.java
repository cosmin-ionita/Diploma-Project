package APIs;

import Utils.Utils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CursorMarkParams;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SolrAPI {

    private static String coreName = "TestCore";

    private static HttpSolrClient solrClient = null, schemaSolrClient = null;

    private static final String solrUrl = "http://localhost:8983/solr/";
    private static final String solrCollectionUrl = "http://localhost:8983/solr/" + coreName;

    private static String cursorInternal;
    private static SolrQuery queryInternal;


    static {
        createSolrClient();
    }

    private static void createSolrClient() {
        solrClient = new HttpSolrClient.Builder(solrUrl).build();
        schemaSolrClient = new HttpSolrClient.Builder(solrCollectionUrl).build();
    }

    public static void executeQuery(SolrQuery query) throws HttpSolrClient.RemoteSolrException, IOException, SolrServerException {
        queryInternal = query;

        //queryParamMap.put("fl", "id, name");

        queryInternal.setRows(10);
        queryInternal.addSort(SolrQuery.SortClause.asc("id"));

        String cursorMark = CursorMarkParams.CURSOR_MARK_START;

        queryInternal.set(CursorMarkParams.CURSOR_MARK_PARAM, cursorMark);

        cursorInternal = cursorMark;
    }

    public static String getMoreResults() throws HttpSolrClient.RemoteSolrException, IOException, SolrServerException {
        QueryResponse response;

        if(queryInternal != null) {
            queryInternal.set(CursorMarkParams.CURSOR_MARK_PARAM, cursorInternal);

            Instant start = Instant.now();

            response = solrClient.query(coreName, queryInternal);

            Instant end = Instant.now();

            System.out.println("Query execution time: " + Duration.between(start, end));

            String nextCursorMark = response.getNextCursorMark();

            if (nextCursorMark.equals(cursorInternal)) {
                queryInternal = null;
                return null;
            } else {
                cursorInternal = nextCursorMark;
            }

            SolrDocumentList documents = response.getResults();

            return Utils.getDocumentsSerialized(documents);
        } else {
            return null;
        }
    }

    public static List<String> getAllFields() throws SolrServerException{

        List<String> resultList = new ArrayList<>();

        try {
            SchemaRequest.Fields requestFields = new SchemaRequest.Fields();

            SchemaResponse.FieldsResponse response = requestFields.process(schemaSolrClient);

            List<Map<String,Object>> solrFields = response.getFields();

            for(Map<String, Object> key : solrFields) {
                resultList.add(key.get("name").toString());
            }

            resultList.remove("_version_");
            resultList.remove("jobId");

            return resultList;

        }catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
