package com.example.ginanjarpr.siapmvpdev.function.home;

import android.content.Context;
import android.util.Log;

import com.example.ginanjarpr.siapmvpdev.models.ServerRequest;
import com.example.ginanjarpr.siapmvpdev.models.ServerResponse;
import com.example.ginanjarpr.siapmvpdev.models.Tiket;
import com.example.ginanjarpr.siapmvpdev.network.ApiClient;
import com.example.ginanjarpr.siapmvpdev.network.RequestInterface;
import com.example.ginanjarpr.siapmvpdev.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter{

    private HomeContract.View mView;
    RequestInterface requestInterface;

    public HomePresenter(HomeContract.View view){

        mView = view;

    }

    public void checkTiket(final Context context, final String no_tiket){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        ServerRequest request = new ServerRequest();
        request.setOperation("checkTiket");
        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    mView.showDitemukan(context, no_tiket, resp.getStatus_aduan().toString(), resp.getJawaban().toString());
                } else{
                    mView.showTidakDitemukan(no_tiket);
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, t.getLocalizedMessage());

            }
        });
    }

    public void selesaiTiket(final Context context, final String no_tiket){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        ServerRequest request = new ServerRequest();
        request.setOperation("selesaiAduan");
        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    mView.showSelesaiSukses(no_tiket);
                } else{
                    //gagal
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, t.getLocalizedMessage());

            }
        });
    }

}
