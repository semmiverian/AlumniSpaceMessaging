package id.semmi.alumnispacemessaging.models;

/**
 * Created by semmi on 27/03/2016.
 */
public class Chatting {
    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    private String fromName;
    private String toName;

    public Chatting(String fromName, String toName, String message) {
        this.fromName = fromName;
        this.toName = toName;
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Chatting() {

    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }
}
