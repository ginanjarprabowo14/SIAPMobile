package com.example.ginanjarpr.siapmvpdev.function.verifnik;

import android.util.Log;

import com.example.ginanjarpr.siapmvpdev.models.Nik;
import com.example.ginanjarpr.siapmvpdev.models.ServerRequest;
import com.example.ginanjarpr.siapmvpdev.models.ServerResponse;
import com.example.ginanjarpr.siapmvpdev.network.ApiClient;
import com.example.ginanjarpr.siapmvpdev.network.RequestInterface;
import com.example.ginanjarpr.siapmvpdev.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifnikPresenter implements VerifnikContract.Presenter {

    private VerifnikContract.View mView;

    public VerifnikPresenter(VerifnikContract.View view){

        mView = view;

    }


    public void checkNIK(String nik){

        Constants.flagVerifJalan="yes";
        RequestInterface requestInterface = ApiClient.getClient().create(RequestInterface.class);

        Nik niks = new Nik();
        niks.setNik(nik);
        ServerRequest request = new ServerRequest();
        request.setOperation("checkNIK");
        request.setNik(niks);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                mView.showProgress();

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    mView.showDitemukan();

                } else{

                    mView.showTidakDitemukan();

                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, t.getLocalizedMessage());

            }
        });

    }


}
