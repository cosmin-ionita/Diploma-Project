package Executors;

import APIs.SolrAPI;
import Interfaces.Command;
import Interfaces.Query;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.io.IOException;
import java.util.List;

public class Executor {

    public static void executeQueries(List<Query> queries) throws SolrServerException, HttpSolrClient.RemoteSolrException{
        try {

            final SolrQuery queryAccumulator = new SolrQuery();

            for(Query query : queries) {
                query.execute(queryAccumulator);
            }

            SolrAPI.executeQuery(queryAccumulator);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String getMoreResults() {
        try {
            return SolrAPI.getMoreResults();

        }catch (SolrServerException | HttpSolrClient.RemoteSolrException | IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public static void executeCommands(List<Command> commands) throws SolrServerException, HttpSolrClient.RemoteSolrException{
        for(Command command : commands) {
            command.execute();
        }
    }
}
