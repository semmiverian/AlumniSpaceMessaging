package id.semmi.alumnispacemessaging.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by semmi on 15/03/2016.
 */
public class User {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    private String kode;
    private String status;
    private String info;
    private String image;
    private int id;
    private String jurusan;
    private String tahunlulus;
    private String nama;
    private String dob;
    private String email;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getTahunlulus() {
        return tahunlulus;
    }

    public void setTahunlulus(String tahunlulus) {
        this.tahunlulus = tahunlulus;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String username, String password, String kode, String status, String info, String image, int id, String jurusan, String tahunlulus, String nama, String dob, String email) {
        this.username = username;
        this.password = password;
        this.kode = kode;
        this.status = status;
        this.info = info;
        this.image = image;
        this.id = id;
        this.jurusan = jurusan;
        this.tahunlulus = tahunlulus;
        this.nama = nama;
        this.dob = dob;
        this.email = email;
    }

    public User() {

    }
}
