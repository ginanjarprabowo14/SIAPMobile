package com.example.ginanjarpr.siapmvpdev.function.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.ginanjarpr.siapmvpdev.models.ServerRequest;
import com.example.ginanjarpr.siapmvpdev.models.ServerResponse;
import com.example.ginanjarpr.siapmvpdev.models.User;
import com.example.ginanjarpr.siapmvpdev.network.ApiClient;
import com.example.ginanjarpr.siapmvpdev.network.RequestInterface;
import com.example.ginanjarpr.siapmvpdev.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private SharedPreferences pref;

    public LoginPresenter(LoginContract.View view) {

        mView = view;

    }

    @Override
    public void doLogin(String email, String password, final Context context) {

        mView.showProgress();

        RequestInterface requestInterface = ApiClient.getClient().create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, final retrofit2.Response<ServerResponse> response) {

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ServerResponse resp = response.body();
                        if (resp.getMessage().equalsIgnoreCase("Invalid Login Credentials")) {

                            mView.hideProgress();
                            mView.loginWrongCredentials();

                        }

                        if (resp.getResult().equals(Constants.SUCCESS)) {
                            pref = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean(Constants.IS_LOGGED_IN, true);
                            editor.putString(Constants.EMAIL, resp.getUser().getEmail());
                            editor.putString(Constants.NAME, resp.getUser().getName());
                            editor.putString(Constants.ROLES, resp.getUser().getRoles());
                            editor.putString("username", resp.getUser().getUsername());
                            editor.putString("notifcount", "3");
                            editor.putString("channel", "channel");
                            editor.putString("countservice", "0");
                            editor.apply();

                            mView.setWelcomeName(resp.getUser().getName().toString());

                            Constants.NAME_FIELD = ", " + resp.getUser().getName();
                            Constants.ROLES_FIELD = resp.getUser().getRoles();
                            Constants.NOTIFCOUNT = "3";
                            Constants.LOGGED = "OK";

                            mView.setNamaEmailDrawer(resp.getUser().getName().toString(), resp.getUser().getEmail().toString());

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    mView.hideProgress();
                                    mView.goToAdmin();

                                }
                            }, 3000);

                        }

                    }
                }, 1000);

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                mView.hideProgress();
                Log.d(Constants.TAG, "failed");
                mView.loginErrorConnection();

            }
        });
    }
}
