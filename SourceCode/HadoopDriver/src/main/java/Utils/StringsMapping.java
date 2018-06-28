package Utils;

public class StringsMapping {

    public static String indexIntervalResponse(boolean completed, String interval) {
        if(completed)
            return "The indexing interval was successfully updated to " + interval;
        else
            return "The indexing interval could not be updated to " + interval;
    }
}
