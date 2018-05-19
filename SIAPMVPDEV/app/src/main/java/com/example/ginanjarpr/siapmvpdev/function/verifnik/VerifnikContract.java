package com.example.ginanjarpr.siapmvpdev.function.verifnik;

public interface VerifnikContract {

    //Presenter -----> View
    interface View {

        void showProgress();

        void hideProgress();

        void showDitemukan();

        void showTidakDitemukan();


    }

    //View -----> Presenter
    interface Presenter {

        void checkNIK(String nik);

    }

}
