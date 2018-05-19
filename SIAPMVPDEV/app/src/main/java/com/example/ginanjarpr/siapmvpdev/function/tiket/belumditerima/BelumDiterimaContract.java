package com.example.ginanjarpr.siapmvpdev.function.tiket.belumditerima;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

public interface BelumDiterimaContract {

    //Presenter -----> View
    interface View {

        void hideTidakDitemukan();

        void showTidakDitemukan();

        void showDialogSuksesHapus(Context context, String no_tiket);

        void showDialogSuksesKembalikan(Context context, String no_tiket);

        void showDialogSuksesTerima(Context context, String no_tiket);

        void showDialogSuksesTerimaSKPD(Context context, String no_tiket);

        void setAdapterRecycler(RecyclerView.Adapter adapters);

    }

    //Presenter -----> View
    interface Presenter {

        void refreshDataBelumDiterimaPool();

        void refreshDataBelumDiterimaSKPD();

        void hapusTiket(final Context context, final String no_tiket);

        void kembaliTiket(final Context context, final String no_tiket);

        void terimaTiket(final Context context, final String no_tiket);

    }

}
