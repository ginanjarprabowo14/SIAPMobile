package com.example.ginanjarpr.siapmvpdev.function.admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.ginanjarpr.siapmvpdev.models.ServerResponseGet;
import com.example.ginanjarpr.siapmvpdev.models.Tiket;
import com.example.ginanjarpr.siapmvpdev.network.ApiClient;
import com.example.ginanjarpr.siapmvpdev.network.RequestInterface;
import com.example.ginanjarpr.siapmvpdev.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class AdminPresenter implements AdminContract.Presenter {

    private AdminContract.View mView;
    private SharedPreferences pref;
    private String rolespref;

    List<Tiket> tiketListBelumDiterimaPool;
    List<Tiket> tiketListBelumDiterimaSKPD;
    List<Tiket> tiketListDiterimaPool;
    List<Tiket> tiketListDiterimaSKPD;
    List<Tiket> tiketListPool;
    List<Tiket> tiketListSKPD;
    RequestInterface requestInterface;

    public AdminPresenter(AdminContract.View view){

        mView = view;

    }

    public void refreshCounter(Context context) {

        pref = getDefaultSharedPreferences(context);
        rolespref = pref.getString(Constants.ROLES, "").toString();

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        if (rolespref.equalsIgnoreCase("1")) {


            final Call<ServerResponseGet> tiketPoolDiterimaCall = requestInterface.getTiketPoolDiterima();
            tiketPoolDiterimaCall.enqueue(new Callback<ServerResponseGet>() {
                @Override
                public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                        response) {
                    if (response.body() != null) {
                        tiketListDiterimaPool = response.body().getListDataTiket();
                        mView.setViewTextTerima(String.valueOf(tiketListDiterimaPool.size()) + " Aduan");
                    } else {
                        mView.setViewTextTerima("0 Aduan");
                    }


                }

                @Override
                public void onFailure(Call<ServerResponseGet> call, Throwable t) {
                    Log.e("Retrofit Get", t.toString());
                }

            });

            final Call<ServerResponseGet> tiketPoolBelumDiterimaCall = requestInterface.getTiketPoolBelumDiterima();
            tiketPoolBelumDiterimaCall.enqueue(new Callback<ServerResponseGet>() {
                @Override
                public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                        response) {
                    if (response.body() != null) {
                        tiketListBelumDiterimaPool = response.body().getListDataTiket();
                        mView.setViewTextBelumDiterima(String.valueOf(tiketListBelumDiterimaPool.size()) + " Aduan");
                        mView.setSharedCountPool(String.valueOf(tiketListBelumDiterimaPool.size()));
                    } else {
                        mView.setViewTextBelumDiterima("0 Aduan");
                    }
                }

                @Override
                public void onFailure(Call<ServerResponseGet> call, Throwable t) {
                    Log.e("Retrofit Get", t.toString());
                }

            });

            final Call<ServerResponseGet> tiketPool = requestInterface.getTiketPool();
            tiketPool.enqueue(new Callback<ServerResponseGet>() {
                @Override
                public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                        response) {

                    if (response.body() != null) {
                        tiketListPool = response.body().getListDataTiket();
                        mView.setViewTextTotal(String.valueOf(tiketListPool.size()));
                    } else {
                        mView.setViewTextTotal("0");
                    }

                }

                @Override
                public void onFailure(Call<ServerResponseGet> call, Throwable t) {
                    Log.e("Retrofit Get", t.toString());
                }

            });

        } else {

            final Call<ServerResponseGet> tiketSKPDBelumDiterimaCall = requestInterface.getTiketSKPDBelumDiterima();
            tiketSKPDBelumDiterimaCall.enqueue(new Callback<ServerResponseGet>() {
                @Override
                public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                        response) {

                    if (response.body() != null) {
                        tiketListBelumDiterimaSKPD = response.body().getListDataTiket();
                        mView.setViewTextBelumDiterima(String.valueOf(tiketListBelumDiterimaSKPD.size()) + " Aduan");
                        mView.setSharedCountSKPD(String.valueOf(tiketListBelumDiterimaSKPD.size()));
                    } else {
                        mView.setViewTextBelumDiterima("0 Aduan");
                    }


                }

                @Override
                public void onFailure(Call<ServerResponseGet> call, Throwable t) {
                    Log.e("Retrofit Get", t.toString());
                }

            });


            final Call<ServerResponseGet> tiketSKPDDiterimaCall = requestInterface.getTiketSKPDDiterima();
            tiketSKPDDiterimaCall.enqueue(new Callback<ServerResponseGet>() {
                @Override
                public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                        response) {
                    if (response.body() != null) {
                        tiketListDiterimaSKPD = response.body().getListDataTiket();
                        mView.setViewTextTerima(String.valueOf(tiketListDiterimaSKPD.size()) + " Aduan");
                    } else {
                        mView.setViewTextTerima("0 Aduan");
                    }

                }

                @Override
                public void onFailure(Call<ServerResponseGet> call, Throwable t) {
                    Log.e("Retrofit Get", t.toString());
                }

            });

            final Call<ServerResponseGet> tiketSKPD = requestInterface.getTiketSKPD();
            tiketSKPD.enqueue(new Callback<ServerResponseGet>() {
                @Override
                public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                        response) {

                    if (response.body() != null) {
                        tiketListSKPD = response.body().getListDataTiket();
                        mView.setViewTextTotal(String.valueOf(tiketListSKPD.size()));
                    } else {
                        mView.setViewTextTotal("0");
                    }


                }

                @Override
                public void onFailure(Call<ServerResponseGet> call, Throwable t) {
                    Log.e("Retrofit Get", t.toString());
                }

            });


        }


    }


}
