package com.example.ginanjarpr.siapmvpdev.function.buataduan;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ginanjarpr.siapmvpdev.models.ServerRequest;
import com.example.ginanjarpr.siapmvpdev.models.ServerResponse;
import com.example.ginanjarpr.siapmvpdev.models.Tiket;
import com.example.ginanjarpr.siapmvpdev.network.ApiClient;
import com.example.ginanjarpr.siapmvpdev.network.RequestInterface;
import com.example.ginanjarpr.siapmvpdev.utils.Constants;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class BuataduanPresenter implements BuataduanContract.Presenter {

    private BuataduanContract.View mView;

    public BuataduanPresenter(BuataduanContract.View view){

        mView = view;

    }

    public void buatAduan(Context context, String nama, String alamat, String nomor, String topik, String isiAduan){

        RequestInterface requestInterface = ApiClient.getClient().create(RequestInterface.class);

        Calendar calendar = Calendar.getInstance();

        String nm = String.valueOf(calendar.get(Calendar.YEAR));
        String week = String.valueOf(calendar.get(Calendar.WEEK_OF_MONTH));
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String minutes = String.valueOf(calendar.get(Calendar.MINUTE));
        String seconds = String.valueOf(calendar.get(Calendar.SECOND));

        final String noTiketInput = "KH1"+nm+week+day+minutes+seconds;

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(noTiketInput);
        tiket.setNama_pengadu(nama);
        tiket.setAlamat_pengadu(alamat);
        tiket.setNo_telepon_pengadu(nomor);
        tiket.setTopik_aduan(topik);
        tiket.setIsi_aduan(isiAduan);

        ServerRequest request = new ServerRequest();
        request.setOperation("buatTiket");

        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    mView.showDialogTelahDikirim(noTiketInput);
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
