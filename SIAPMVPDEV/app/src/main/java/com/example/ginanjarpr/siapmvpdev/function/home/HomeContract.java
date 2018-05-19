package com.example.ginanjarpr.siapmvpdev.function.home;

import android.content.Context;

public interface HomeContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showDitemukan(Context context, final String no_tiket, String status_aduan, String jawaban);

        void showKonfirmasiSelesai(final String no_tiket);

        void showSelesaiSukses(String no_tiket);

        void showTidakDitemukan(String no_tiket);

    }

    interface Presenter {

        void checkTiket(final Context context, final String no_tiket);

        void selesaiTiket(final Context context, final String no_tiket);

    }

}
