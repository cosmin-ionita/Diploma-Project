package Models;

public class HadoopApiResponse {
    private String message;

    public HadoopApiResponse() {}

    public HadoopApiResponse(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
