package com.example.ginanjarpr.siapmvpdev.function.tiket.diterima;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ginanjarpr.siapmvpdev.adapter.TiketAdapterDiterima;
import com.example.ginanjarpr.siapmvpdev.models.ServerRequest;
import com.example.ginanjarpr.siapmvpdev.models.ServerResponse;
import com.example.ginanjarpr.siapmvpdev.models.ServerResponseGet;
import com.example.ginanjarpr.siapmvpdev.models.Tiket;
import com.example.ginanjarpr.siapmvpdev.network.ApiClient;
import com.example.ginanjarpr.siapmvpdev.network.RequestInterface;
import com.example.ginanjarpr.siapmvpdev.utils.Constants;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class DiterimaPresenter implements DiterimaContract.Presenter {

    private DiterimaContract.View mView;
    private SharedPreferences pref;

    private RequestInterface requestInterface;
    List<Tiket> tiketList;
    private RecyclerView.Adapter mAdapterTiket;
    public String rolespref;

    public DiterimaPresenter(DiterimaContract.View view) {

        mView = view;

    }

    public void refreshDataDiterimaPool() {

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        final Call<ServerResponseGet> tiketCall = requestInterface.getTiketPoolDiterima();
        tiketCall.enqueue(new Callback<ServerResponseGet>() {
            @Override
            public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                    response) {

                if (response.body() != null) {
                    tiketList = response.body().getListDataTiket();

                    mAdapterTiket = new TiketAdapterDiterima(tiketList);
                    mView.setAdapterRecycler(mAdapterTiket);
                }else {

                    tiketList = Collections.emptyList();
                    mAdapterTiket = new TiketAdapterDiterima(tiketList);
                    mView.setAdapterRecycler(mAdapterTiket);

                    mView.showTidakDitemukan();

                }



            }

            @Override
            public void onFailure(Call<ServerResponseGet> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }

        });

    }

    public void refreshDataDiterimaSKPD() {

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        final Call<ServerResponseGet> skpdCall = requestInterface.getTiketSKPDDiterima();
        skpdCall.enqueue(new Callback<ServerResponseGet>() {
            @Override
            public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                    response) {

                if (response.body() != null) {
                    mView.hideTidakDitemukan();
                    tiketList = response.body().getListDataTiket();
                    mAdapterTiket = new TiketAdapterDiterima(tiketList);
                    mView.setAdapterRecycler(mAdapterTiket);
                } else {

                    tiketList = Collections.emptyList();
                    mAdapterTiket = new TiketAdapterDiterima(tiketList);
                    mView.setAdapterRecycler(mAdapterTiket);

                    mView.showTidakDitemukan();

                }


            }

            @Override
            public void onFailure(Call<ServerResponseGet> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }

        });

    }

    public void kembaliTiket(final Context context, final String no_tiket){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        pref = getDefaultSharedPreferences(context);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        tiket.setUsername(pref.getString("username","").toString());
        ServerRequest request = new ServerRequest();
        request.setOperation("kembaliSKPD");
        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {


                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    mView.showDialogSuksesKembalikan(context, no_tiket);
                } else{
                    //delete gagal
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, t.getLocalizedMessage());

            }
        });
    }

    public void kirimTiket(final Context context, final String no_tiket, final String departemen){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        pref = getDefaultSharedPreferences(context);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        tiket.setUsername(pref.getString("username","").toString());
        tiket.setDepartemen(departemen);
        ServerRequest request = new ServerRequest();

        rolespref = pref.getString(Constants.ROLES, "").toString();


        request.setOperation("belumTerimaSKPD");

        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    if (rolespref.equalsIgnoreCase("1")) {

                        mView.showDialogSuksesKirim(context, no_tiket, "Telah Dikirim");

                    } else {

                        mView.showDialogSuksesTerimaSKPD(context, no_tiket);

                    }

                } else{
                    //delete gagal
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, t.getLocalizedMessage());

            }
        });
    }

    public void kelompokkanTiket(final Context context, final String no_tiket, final String kategori){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        pref = getDefaultSharedPreferences(context);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        tiket.setUsername(pref.getString("username","").toString());
        tiket.setTopik_aduan(kategori);
        ServerRequest request = new ServerRequest();

        rolespref = pref.getString(Constants.ROLES, "").toString();


        request.setOperation("kategoriAduan");

        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    mView.showDialogSuksesKirim(context, no_tiket, "Telah Dikelompokkan");

                } else{
                    //delete gagal
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, t.getLocalizedMessage());

            }
        });
    }

    public void jawabTiket(final Context context, final String no_tiket, final String isi){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        pref = getDefaultSharedPreferences(context);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        tiket.setUsername(pref.getString("username","").toString());
        tiket.setIsi(isi);
        ServerRequest request = new ServerRequest();

        rolespref = pref.getString(Constants.ROLES, "").toString();


        request.setOperation("jawabTiket");

        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    mView.showDialogSuksesKirim(context, no_tiket, "Telah Dijawab");


                } else{
                    //delete gagal
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, t.getLocalizedMessage());

            }
        });
    }

}
