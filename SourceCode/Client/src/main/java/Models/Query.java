package Models;

import java.util.HashMap;

public class Query {

    /* Those values are valid for date / time inteval option */
    String dateFrom, dateTo;
    String timeFrom, timeTo;

    HashMap<String, String> fields;

    /* The values will be ASC / DESC only */
    String orderByDateDirection = "";
    String orderByTimeDirection = "";

    String exportFileName = "";

    public Query() {
        fields = new HashMap<>();
    }


}
