package Queries;

import Exceptions.IncorrectParameterException;
import Interfaces.Query;
import org.apache.solr.client.solrj.SolrQuery;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeInterval implements Query {

    private String[] params;

    public void setParams(String[] params) throws IncorrectParameterException {
        if(params.length != 2) {
            throw new IncorrectParameterException("Incorrect number of time interval parameters");
        }

        /* eventually check if the parameters are really time values */

        this.params = params;
    }

    public void execute(SolrQuery solrQueryParams) {
        Date now = new Date();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = currentDate.format(now);

        String dateRange = "[\"" + stringDate + "T" + params[0] + "Z" + "\" TO \"" + stringDate + "T" + params[1] + "Z" + "\"]";

        String[] existentQueries = solrQueryParams.getFilterQueries();

        /* If there is an existent date interval query, we merge it with this one */
        if(existentQueries!= null && existentQueries.length == 1) {
            dateRange = updateRangeQuery(existentQueries[0]);

            solrQueryParams.removeFilterQuery(existentQueries[0]);
        }

        if(solrQueryParams.get("q") == null) {
            solrQueryParams.set("q", "*:*");
        }

        solrQueryParams.addFilterQuery("timeStamp:" + dateRange);
    }

    private String updateRangeQuery(String existentFilterQuery) {
        String date1 = existentFilterQuery.split("TO")[0].split("T")[0].split(":")[1].split("\\[")[1].split("\"")[1];
        String date2 = existentFilterQuery.split("TO")[1].split("T")[0].split("\"")[1];

        return "[\"" + date1 + "T" + params[0] + "Z" + "\" TO \"" + date2 + "T" + params[1] + "Z" + "\"]";
    }
}
