package com.example.ginanjarpr.siapmvpdev.models;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public class ServerRequest {

    private String operation;
    private User user;
    private Tiket tiket;
    private Nik nik;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTiket(Tiket tiket) {
        this.tiket = tiket;
    }

    public void setNik(Nik nik) {
        this.nik = nik;
    }

}
