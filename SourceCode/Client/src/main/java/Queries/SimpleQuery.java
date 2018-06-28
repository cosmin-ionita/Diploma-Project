package Queries;

import Exceptions.IncorrectParameterException;
import org.apache.solr.client.solrj.SolrQuery;

public class SimpleQuery implements Interfaces.Query {

    private String[] params;

    public void setParams(String[] params) throws IncorrectParameterException {

        for(String param : params)
            if(!param.contains("=")) {
                throw new IncorrectParameterException("The query value pairs are not formatted correctly");
            }

        this.params = params;
    }

    public void execute(SolrQuery query) {
        String queryAccumulator = "", key = "", value = "";

        for(int i = 0; i < params.length; i++) {
            key = params[i].split("=")[0];
            value = params[i].split("=")[1];

            queryAccumulator += key + " : " + value;

            if(i < params.length - 1){
                queryAccumulator += " AND ";
            }
        }

        query.add("q", queryAccumulator);
    }
}
