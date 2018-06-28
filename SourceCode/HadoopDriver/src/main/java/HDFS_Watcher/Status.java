package HDFS_Watcher;

import Enums.IndexJobStatus;

public class Status {

    /* The indexing period (possible values: 1m, 20m, 1h, 20h (X[m | h]) */
    private static String indexInterval;

    /* The timestamp of the last indexing job */
    private static String lastIndexTimestamp;

    private static IndexJobStatus indexJobStatus;

    static {
        indexJobStatus = IndexJobStatus.FINISHED;
        lastIndexTimestamp = "Nothing indexed yet";
    }

    public static void setIndexInterval(String interval) {
        indexInterval = interval;
    }

    public static void setIndexJobStatus(IndexJobStatus value) {
        indexJobStatus = value;
    }

    public static IndexJobStatus getIndexJobStatus() {
        return indexJobStatus;
    }

    public static void setLastIndexTimestamp(String timeStamp) {
        lastIndexTimestamp = timeStamp;
    }

    public static String getLastIndexTimestamp() {
        return lastIndexTimestamp;
    }

    public static String getIndexInterval() {
        return indexInterval;
    }

    public static String getGeneralStatus() {
        return String.format("======== loGrep Status ========= \n\nIndex Interval: %s \nLast Index Timestamp: %s \nIndex job status: %s \n", indexInterval, lastIndexTimestamp, indexJobStatus);
    }
}
