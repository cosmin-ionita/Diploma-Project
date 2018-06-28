package Utils;

public class StringsMapping {

    public final static String queryShort = "q";
    public final static String queryLong = "query";
    public final static String queryDescription = "Specify a list of key value pairs separated by space, like so: -q field1=value1 field2=value2";

    public final static String timeIntervalShort = "ti";
    public final static String timeIntervalLong = "time-interval";
    public final static String timeIntervalDescription = "Specify 2 time intervals in the following format HH:mm:ss";

    public final static String dateIntervalShort = "di";
    public final static String dateIntervalLong = "date-interval";
    public final static String dateIntervalDescription = "Specify 2 dates in the following format: yyyy-mm-dd";

    public final static String orderByTimeStampShort = "obt";
    public final static String orderByTimeStampLong = "order-by-timestamp";
    public final static String orderByTimeStampDescription = "Specify 'ASC' or 'DESC' depending on the order you want" ;

    public final static String exportShort = "e";
    public final static String exportLong = "export";
    public final static String exportDescription = "Specify the filename where you want the search results to be saved";

    public final static String fieldsShort = "f";
    public final static String fieldsLong = "fields";
    public final static String fieldsDescription = "This will print all available fields that you can use to search on";

    public final static String statusShort = "s";
    public final static String statusLong = "status";
    public final static String statusDescription = "This will show up the status of the tool";

    public final static String indexIntervalShort = "ii";
    public final static String indexIntervalLong = "index-interval";
    public final static String indexIntervalDescription = "This command will set the indexing interval (batch mode). Possible values: X[m|h] with X in [1,10]";

    public final static String indexNowShort = "in";
    public final static String indexNowLong = "index-now";
    public final static String indexNowDescription = "This command will trigger the indexing job immediately in order for you to be able to query through the most recent data (near real-time mode)";

    public final static String initCommand = "init";
    public final static String initCommandDescription = "This command will build up the tunnels to the infrastructure";

    public final static String guiShort = "g";
    public final static String guiLong = "gui";
    public final static String guiDescription = "This will launch the desktop GUI";
}
