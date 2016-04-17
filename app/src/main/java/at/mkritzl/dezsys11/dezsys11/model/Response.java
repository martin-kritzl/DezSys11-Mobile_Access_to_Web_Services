package at.mkritzl.dezsys11.dezsys11.model;

/**
 * The object that used for mapping the incoming http-message
 */
public class Response {

    private int status;
    private String message;

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
