public class QueryManager {

    private static QueryManager instance;

    private QueryManager() {
        instance = new QueryManager();
    }

    public static QueryManager getInstance() {
        return instance;
    }

    public static String getSimpleSolrQuery(String keyValue) {
        return null;
    }
}
