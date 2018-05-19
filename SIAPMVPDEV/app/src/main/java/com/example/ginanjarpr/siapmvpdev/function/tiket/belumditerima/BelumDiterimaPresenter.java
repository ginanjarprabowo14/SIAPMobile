package com.example.ginanjarpr.siapmvpdev.function.tiket.belumditerima;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ginanjarpr.siapmvpdev.adapter.TiketAdapterBelumDiterima;
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

public class BelumDiterimaPresenter implements BelumDiterimaContract.Presenter {

    private BelumDiterimaContract.View mView;
    private SharedPreferences pref;

    private RequestInterface requestInterface;
    List<Tiket> tiketList;
    private RecyclerView.Adapter mAdapterTiket;
    public String rolespref;

    public BelumDiterimaPresenter(BelumDiterimaContract.View view) {

        mView = view;

    }

    public void refreshDataBelumDiterimaPool() {

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        final Call<ServerResponseGet> tiketCall = requestInterface.getTiketPoolBelumDiterima();
        tiketCall.enqueue(new Callback<ServerResponseGet>() {
            @Override
            public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                    response) {

                if (response.body() != null) {
                    tiketList = response.body().getListDataTiket();

                    mAdapterTiket = new TiketAdapterBelumDiterima(tiketList);
                    mView.setAdapterRecycler(mAdapterTiket);
                }else {

                    tiketList = Collections.emptyList();
                    mAdapterTiket = new TiketAdapterBelumDiterima(tiketList);
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

    public void refreshDataBelumDiterimaSKPD() {

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        final Call<ServerResponseGet> skpdCall = requestInterface.getTiketSKPDBelumDiterima();
        skpdCall.enqueue(new Callback<ServerResponseGet>() {
            @Override
            public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                    response) {

                if (response.body() != null) {
                    mView.hideTidakDitemukan();
                    tiketList = response.body().getListDataTiket();
                    mAdapterTiket = new TiketAdapterBelumDiterima(tiketList);
                    mView.setAdapterRecycler(mAdapterTiket);
                } else {

                    tiketList = Collections.emptyList();
                    mAdapterTiket = new TiketAdapterBelumDiterima(tiketList);
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

    public void hapusTiket(final Context context, final String no_tiket){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.DELETE_OPERATION);
        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {


                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    mView.showDialogSuksesHapus(context, no_tiket);
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

    public void terimaTiket(final Context context, final String no_tiket){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        pref = getDefaultSharedPreferences(context);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        tiket.setUsername(pref.getString("username","").toString());
        ServerRequest request = new ServerRequest();

        rolespref = pref.getString(Constants.ROLES, "").toString();

        if (rolespref.equalsIgnoreCase("1")) {

            request.setOperation("terimaAdminPool");

        } else {

            request.setOperation("terimaSKPD");

        }

        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    if (rolespref.equalsIgnoreCase("1")) {

                        mView.showDialogSuksesTerima(context, no_tiket);

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

}
