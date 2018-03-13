package Models;

import java.io.Serializable;

public class SolrDataModel implements Serializable{

    private String date;
    private String time;
    private String logLevel;
    private String message;
    private String hostName;
    private String sourceFileName;

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public String getLogLevel() {
        return this.logLevel;
    }

    public String getMessage() {
        return this.message;
    }

    public String getHostName() {
        return this.hostName;
    }

    public String getSourceFileName() {
        return this.sourceFileName;
    }
}
