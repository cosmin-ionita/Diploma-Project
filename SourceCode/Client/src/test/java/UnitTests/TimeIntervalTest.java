package UnitTests;

import Exceptions.IncorrectParameterException;
import Queries.TimeInterval;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;

public class TimeIntervalTest {

    @Test
    public void timeIntervalTest() {
        try {
            SolrQuery accumulator = new SolrQuery();

            TimeInterval query = new TimeInterval();

            query.setParams(new String[] {"10:00:00", "21:00:00"});
            query.execute(accumulator);

            /* This needs the current date to work properly */
            assert accumulator.getFilterQueries()[0].equals("timeStamp:[\"2018-06-29T10:00:00Z\" TO \"2018-06-29T21:00:00Z\"]");

        }catch (IncorrectParameterException exception) {
            exception.printStackTrace();
        }
    }
}
