package com.example.ginanjarpr.siapnonmvp.models;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public class ServerResponse {

    private String result;
    private String message;
    private String jawaban;
    private String status_aduan;
    private User user;
    private Tiket tiket;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getJawaban() {
        return jawaban;
    }

    public String getStatus_aduan() {
        return status_aduan;
    }

    public User getUser() {
        return user;
    }

    public Tiket getTiket() {
        return tiket;
    }

}
