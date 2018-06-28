package Models;

public class SolrDataModel {

    private String timeStamp;
    private String logLevel;
    private String message;
    private String jobId;
    private String sourceFileName;
    private String lineNumber;

    public SolrDataModel() {}

    public SolrDataModel(String timeStamp, String loglevel, String message) {
        this.timeStamp = timeStamp;
        this.logLevel = loglevel;
        this.message = message;
    }

    public void setTimeStamp(String date) {
        this.timeStamp = date;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public void setLineNumber(String lineNumber) { this.lineNumber = lineNumber; }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public String getLogLevel() {
        return this.logLevel;
    }

    public String getMessage() {
        return this.message;
    }

    public String getJobId() {
        return this.jobId;
    }

    public String getSourceFileName() {
        return this.sourceFileName;
    }

    public String getLineNumber() { return this.lineNumber; }
}
