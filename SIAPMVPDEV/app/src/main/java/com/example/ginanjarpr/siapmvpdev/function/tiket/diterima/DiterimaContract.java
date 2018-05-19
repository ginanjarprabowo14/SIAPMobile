package com.example.ginanjarpr.siapmvpdev.function.tiket.diterima;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

public interface DiterimaContract {

    //Presenter -----> View
    interface View {

        void showDialogSuksesKembalikan(Context context, String no_tiket);

        void showDialogSuksesKirim(Context context, String no_tiket, String keterangan);

        void showDialogSuksesTerimaSKPD(Context context, String no_tiket);

        void setAdapterRecycler(RecyclerView.Adapter adapters);

        void showTidakDitemukan();

        void hideTidakDitemukan();

    }

    //Presenter -----> View
    interface Presenter {

        void refreshDataDiterimaPool();

        void refreshDataDiterimaSKPD();

        void kembaliTiket(final Context context, final String no_tiket);

        void kirimTiket(final Context context, final String no_tiket, final String departemen);

        void kelompokkanTiket(final Context context, final String no_tiket, final String kategori);

        void jawabTiket(final Context context, final String no_tiket, final String isi);

    }

}
