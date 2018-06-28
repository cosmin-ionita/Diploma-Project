package Queries;

import Exceptions.IncorrectParameterException;
import Interfaces.Query;
import org.apache.solr.client.solrj.SolrQuery;

public class DateInterval implements Query {

    private String[] params;

    public void setParams(String[] params) throws IncorrectParameterException {
        if(params.length != 2) {
            throw new IncorrectParameterException("Incorrect number of date interval parameters");
        }

        /* Eventually check if the params are real dates */
        this.params = params;
    }

    private String getTimeStampInterval(String sourceTime, String destinationTime) {
        return "[\"" + params[0] + "T" + sourceTime + "Z" + "\"" + " TO " + "\"" + params[1] + "T" + destinationTime + "Z" + "\"]";
    }

    public void execute(SolrQuery solrQueryParams) {
        String dateRange = getTimeStampInterval("00:00:00", "23:59:59");

        String[] existentQueries = solrQueryParams.getFilterQueries();

        /* If there is an existent time interval query, we merge it with this one */
        if(existentQueries!= null && existentQueries.length == 1) {
            dateRange = updateRangeQuery(existentQueries[0]);

            solrQueryParams.removeFilterQuery(existentQueries[0]);
        }

        if(solrQueryParams.get("q") == null) {
            solrQueryParams.set("q", "*:*");
        }

        solrQueryParams.addFilterQuery("timeStamp:" + dateRange);
    }

    private String updateRangeQuery(String existentQueries) {
        String sourceTime = existentQueries.split("TO")[0].split("T")[1].split("Z")[0];
        String destinationTime = existentQueries.split("TO")[1].split("T")[1].split("Z")[0];

        return getTimeStampInterval(sourceTime, destinationTime);
    }
}
