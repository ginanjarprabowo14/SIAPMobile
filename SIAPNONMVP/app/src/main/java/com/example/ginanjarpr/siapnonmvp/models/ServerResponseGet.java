package com.example.ginanjarpr.siapnonmvp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponseGet {

    @SerializedName("tiket")
    List<Tiket> listDataTiket;

    public List<Tiket> getListDataTiket() {
        return listDataTiket;
    }

    public void setListDataKapal(List<Tiket> listDataTiket) {
        this.listDataTiket = listDataTiket;
    }

}
