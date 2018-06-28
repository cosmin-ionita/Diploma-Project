package Queries;

import Exceptions.IncorrectParameterException;
import Interfaces.Query;
import org.apache.solr.client.solrj.SolrQuery;

public class OrderByDate implements Query {

    private String[] params;
    private final String ascendantSort = "ASC", descendantSort = "DESC";

    public void setParams(String[] params) throws IncorrectParameterException {
        if(params.length != 1) {
            throw new IncorrectParameterException("Incorrect number of arguments to order by date query");

        } else if(!params[0].toUpperCase().equals(ascendantSort) && !params[0].toUpperCase().equals(descendantSort)) {
            throw new IncorrectParameterException("Incorrect value of order by date query");

        } else {
            this.params = params;
        }
    }

    public void execute(SolrQuery query) {
        String orderType = params[0].toUpperCase();

        if(query.get("q") == null) {
            query.set("q", "*:*");
        }

        if(orderType.equals(ascendantSort)) {
            query.addSort("timeStamp", SolrQuery.ORDER.asc);

        } else if(orderType.equals(descendantSort)) {
            query.addSort("timeStamp", SolrQuery.ORDER.desc);
        }
    }
}
