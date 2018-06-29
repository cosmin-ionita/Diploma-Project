package UnitTests;

import Exceptions.IncorrectParameterException;
import Queries.DateInterval;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;

public class DateIntervalTest {

    @Test
    public void testDateInterval() {
        try {
            SolrQuery accumulator = new SolrQuery();

            DateInterval query = new DateInterval();

            query.setParams(new String[] {"2018-01-01", "2018-29-29"});
            query.execute(accumulator);

            assert accumulator.getFilterQueries()[0].equals("timeStamp:[\"2018-01-01T00:00:00Z\" TO \"2018-29-29T23:59:59Z\"]");
        }catch (IncorrectParameterException exception) {
            exception.printStackTrace();
        }
    }
}
