package com.example.ginanjarpr.siapmvpdev.function.verifnik;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.ginanjarpr.siapmvpdev.R;
import com.example.ginanjarpr.siapmvpdev.function.buataduan.BuataduanActivity;
import com.example.ginanjarpr.siapmvpdev.function.MainActivity;
import com.example.ginanjarpr.siapmvpdev.function.login.LoginPresenter;
import com.example.ginanjarpr.siapmvpdev.models.Nik;
import com.example.ginanjarpr.siapmvpdev.models.ServerRequest;
import com.example.ginanjarpr.siapmvpdev.models.ServerResponse;
import com.example.ginanjarpr.siapmvpdev.network.ApiClient;
import com.example.ginanjarpr.siapmvpdev.network.RequestInterface;
import com.example.ginanjarpr.siapmvpdev.utils.CSnackbar;
import com.example.ginanjarpr.siapmvpdev.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifnikActivity extends AppCompatActivity implements VerifnikContract.View {

    Toolbar toolbar;
    AppCompatButton buttonVerif;
    EditText editTextNIK;
    ImageView imageBack;
    LinearLayout nikTerdaftar;
    LinearLayout nikTidakTerdaftar;

    ProgressBar progress;

    private VerifnikPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifnik);

        mPresenter = new VerifnikPresenter(this);

        imageBack= (ImageView) findViewById(R.id.buttonBackVerifNIK);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent a = new Intent(getApplicationContext(), MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(a);
            }
        });

        editTextNIK = (EditText) findViewById(R.id.et_nik);

        nikTerdaftar = (LinearLayout) findViewById(R.id.nikTerdaftar);
        nikTidakTerdaftar = (LinearLayout) findViewById(R.id.nikTidakTerdaftar);

        progress = (ProgressBar) findViewById(R.id.progressVerif);

        buttonVerif = (AppCompatButton) findViewById(R.id.btn_verifikasi);
        buttonVerif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Constants.flagVerifJalan.equalsIgnoreCase("no")){

                    String nik = editTextNIK.getText().toString();
                    if(!nik.isEmpty()){

                        showProgress();
                        mPresenter.checkNIK(nik);

                    }else{

                        inputNotComplete();

                    }

                }else{
                    //do nothing
                }


            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbars);
        toolbar.setTitle(Html.fromHtml("<font color='#FFFFF'></font>"));
        toolbar.setElevation(0);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(getApplicationContext(), MainActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(a);

    }

    public void showDitemukan(){

        nikTerdaftar.setAlpha(0f);
        nikTerdaftar.setTranslationX(200f);
        nikTerdaftar.setTranslationY(0);
        nikTerdaftar.setVisibility(View.VISIBLE);
        nikTidakTerdaftar.setVisibility(View.GONE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                hideProgress();
                nikTerdaftar.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .translationX(0f)
                        .translationY(0f)
                        .translationZ(10f)
                        .setInterpolator(new FastOutSlowInInterpolator())
                        .setStartDelay(300)
                        .start();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent a = new Intent(getApplicationContext(), BuataduanActivity.class);
                        getWindow().getDecorView().getContext().startActivity(a);

                    }
                }, 2000);

            }
        }, 3000);

    }

    public void showTidakDitemukan(){

        Constants.flagVerifJalan="no";
        nikTidakTerdaftar.setAlpha(0f);
        nikTidakTerdaftar.setTranslationX(200f);
        nikTidakTerdaftar.setTranslationY(0);
        nikTidakTerdaftar.setVisibility(View.VISIBLE);
        nikTerdaftar.setVisibility(View.GONE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                hideProgress();
                nikTidakTerdaftar.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .translationX(0f)
                        .translationY(0f)
                        .translationZ(10f)
                        .setInterpolator(new FastOutSlowInInterpolator())
                        .setStartDelay(300)
                        .start();

            }
        }, 3000);

    }

    public void showProgress(){

        progress.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0f)
                .translationY(0f)
                .translationZ(10f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(300)
                .start();
        progress.setVisibility(View.VISIBLE);

    }

    public void hideProgress(){

        progress.animate()
                .alpha(0f)
                .scaleX(0.5f)
                .scaleY(0.5f)
                .translationX(0f)
                .translationY(0f)
                .translationZ(10f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(300)
                .start();

    }

    public void inputNotComplete(){

        CSnackbar.createSnackbarTop(getWindow().getDecorView().findViewById(R.id.rootViewVerif),
                Color.WHITE, "#E18585", "Isi Data NIK");

    }

}
