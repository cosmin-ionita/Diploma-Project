package UnitTests;

import Exceptions.IncorrectParameterException;
import Queries.OrderByTimeStamp;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;

public class OrderByTimeStampTest {

    @Test
    public void testOrderByTimeStamp() {
        try {

            SolrQuery accumulator = new SolrQuery();

            OrderByTimeStamp query = new OrderByTimeStamp();

            query.setParams(new String[] { "asc" });

            query.execute(accumulator);

            assert accumulator.getSortField().equals("timeStamp asc");

        }catch (IncorrectParameterException exception) {
            exception.printStackTrace();
        }
    }
}
