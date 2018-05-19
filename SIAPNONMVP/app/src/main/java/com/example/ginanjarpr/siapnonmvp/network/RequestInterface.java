package com.example.ginanjarpr.siapnonmvp.network;

import com.example.ginanjarpr.siapnonmvp.models.ServerRequest;
import com.example.ginanjarpr.siapnonmvp.models.ServerResponse;
import com.example.ginanjarpr.siapnonmvp.models.ServerResponseGet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public interface RequestInterface {

    @POST("siapservice/")
    Call<ServerResponse> operation(@Body ServerRequest request);

    @GET("siapservice/index.php/getTiketPoolBelumDiterima")
    Call<ServerResponseGet> getTiketPoolBelumDiterima();

    @GET("siapservice/index.php/getTiketPoolDiterima")
    Call<ServerResponseGet> getTiketPoolDiterima();

    @GET("siapservice/index.php/getTiketSKPDBelumDiterima")
    Call<ServerResponseGet> getTiketSKPDBelumDiterima();

    @GET("siapservice/index.php/getTiketSKPDDiterima")
    Call<ServerResponseGet> getTiketSKPDDiterima();

    @GET("siapservice/index.php/getTiketPool")
    Call<ServerResponseGet> getTiketPool();

    @GET("siapservice/index.php/getTiketSKPD")
    Call<ServerResponseGet> getTiketSKPD();

}
