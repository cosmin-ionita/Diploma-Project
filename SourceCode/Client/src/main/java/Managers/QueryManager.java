package Managers;

import Models.Query;

public class QueryManager {

    private static QueryManager instance;

    private QueryManager() {
        instance = new QueryManager();
    }

    public static QueryManager getInstance() {
        return instance;
    }

    public Query buildQuery(String[] args) {
        Query query = new Query();


        return query;
    }
}
