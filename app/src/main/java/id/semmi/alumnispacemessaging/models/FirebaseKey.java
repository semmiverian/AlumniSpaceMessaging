package id.semmi.alumnispacemessaging.models;

/**
 * Created by semmi on 17/03/2016.
 */
public class FirebaseKey {
    private String kode;
    private String key;
    private String from;

    // Constructor for add new data to server
    public FirebaseKey(String key, String from, String to) {
        this.key = key;
        this.from = from;
        this.to = to;
    }

    private String to;


    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FirebaseKey() {

    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    // Constructor for receiving data from server
    public FirebaseKey(String from, String to) {
        this.from = from;
        this.to = to;
    }


}
