package com.example.ginanjarpr.siapnonmvp.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.example.ginanjarpr.siapnonmvp.R;

public class CDialog {

    public static Dialog dialogs;

    public static Dialog createDialog(Context context, int layout, int width, int height){

        dialogs = new Dialog(context, R.style.CustomDialog);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(layout);
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        int widthinPixels = Converter.dpToPx(width);

        int heightinPixels = Converter.dpToPx(height);

        dialogs.getWindow().setLayout(widthinPixels, heightinPixels);
        dialogs.show();


        return dialogs;
    }

}
