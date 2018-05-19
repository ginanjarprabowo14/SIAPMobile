package com.example.ginanjarpr.siapmvpdev.function.login;

import android.content.Context;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public interface LoginContract {

    //Presenter -----> View
    interface View {

        void showProgress();

        void hideProgress();

        void loginWrongCredentials();

        void loginErrorConnection();

        void setWelcomeName(String name);

        void goToAdmin();

        void setNamaEmailDrawer(String nama, String email);

    }

    //View -----> Presenter
    interface Presenter {

        void doLogin(String email, String password, Context context);

    }

}
