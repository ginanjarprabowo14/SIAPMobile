package com.example.ginanjarpr.siapmvpdev.utils;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;

public class CSnackbar {

    public static TSnackbar snackbars;

    public static TSnackbar createSnackbarTop (View view, int warnaText, String background, String isi){

        snackbars = TSnackbar.make(view, isi, TSnackbar.LENGTH_LONG);
        snackbars.setActionTextColor(warnaText);
        View snackbarView = snackbars.getView();
        snackbarView.setBackgroundColor(Color.parseColor(background));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(warnaText);
        snackbars.show();

        return snackbars;

    }

}
