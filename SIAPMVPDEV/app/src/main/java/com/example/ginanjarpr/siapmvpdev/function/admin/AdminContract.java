package com.example.ginanjarpr.siapmvpdev.function.admin;

import android.content.Context;

public interface AdminContract {

    interface View {

        void setViewTextTerima(String txtTerima);

        void setViewTextBelumDiterima(String txtBelumDiterima);

        void setViewTextTotal(String txtTotal);

        void setSharedCountPool(String count);

        void setSharedCountSKPD(String count);

    }

    interface Presenter {

        void refreshCounter(Context context);

    }

}
