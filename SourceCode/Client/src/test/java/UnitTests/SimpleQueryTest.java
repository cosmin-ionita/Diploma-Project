package UnitTests;

import Exceptions.IncorrectParameterException;
import Queries.SimpleQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;

public class SimpleQueryTest {

    @Test
    public void testSimpleQuery() {
        try {
            SolrQuery accumulator = new SolrQuery();

            SimpleQuery query = new SimpleQuery();

            query.setParams(new String[] { "logLevel=INFO", "message=test"});
            query.execute(accumulator);

            assert accumulator.get("q").equals("logLevel : INFO AND message : test");

        }catch (IncorrectParameterException exception) {
            exception.printStackTrace();
        }
    }
}