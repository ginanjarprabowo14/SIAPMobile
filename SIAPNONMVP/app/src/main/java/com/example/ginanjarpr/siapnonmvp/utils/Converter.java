package com.example.ginanjarpr.siapnonmvp.utils;

import android.content.res.Resources;

public class Converter {

    public static int dpToPx(int dp) {
        if(Resources.getSystem().getDisplayMetrics().density>=2){
            return (int) (dp * 2);
        }
        else {
            return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
        }
    }

}
