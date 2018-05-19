package com.example.ginanjarpr.siapmvpdev.function.buataduan;

import android.content.Context;

public interface BuataduanContract {

    interface View {

        void showDialogTelahDikirim(String no_tiket);

    }

    interface Presenter {

        void buatAduan(Context context, String nama, String alamat, String nomor, String topik, String isiAduan);

    }

}
