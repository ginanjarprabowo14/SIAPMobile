package com.example.ginanjarpr.siapmvpdev.function.buataduan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.ginanjarpr.siapmvpdev.R;
import com.example.ginanjarpr.siapmvpdev.adapter.BuatAduanFragmentAdapter;
import com.example.ginanjarpr.siapmvpdev.function.MainActivity;
import com.example.ginanjarpr.siapmvpdev.function.home.HomePresenter;
import com.example.ginanjarpr.siapmvpdev.models.ServerRequest;
import com.example.ginanjarpr.siapmvpdev.models.ServerResponse;
import com.example.ginanjarpr.siapmvpdev.models.Tiket;
import com.example.ginanjarpr.siapmvpdev.network.ApiClient;
import com.example.ginanjarpr.siapmvpdev.network.RequestInterface;
import com.example.ginanjarpr.siapmvpdev.utils.CDialog;
import com.example.ginanjarpr.siapmvpdev.utils.CNetwork;
import com.example.ginanjarpr.siapmvpdev.utils.CSnackbar;
import com.example.ginanjarpr.siapmvpdev.utils.Constants;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class BuataduanActivity extends AppCompatActivity implements BuataduanContract.View {

    RelativeLayout globalLayout;
    BuatAduanFragmentAdapter mAduanFragmentAdapter;
    ViewPager mViewPager;

    public SharedPreferences pref;

    EditText bNama, bAlamat, bNomor, bIsiAduan;

    Spinner bTopik;
    CheckBox bDataBenar;

    Button btnNext;
    Button btnPrev;
    Button btnFinish;

    private BuataduanPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buataduan);

        mPresenter = new BuataduanPresenter(this);

        if (CNetwork.isNetworkAvailable(getApplicationContext())) {
            Constants.connect = 1;
        } else {
            Constants.connect = 0;
        }

        Constants.flagVerifJalan="no";

        globalLayout = findViewById(R.id.layoutGlobalBuatTiket);

        //Set System Status Tampil
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        ImageView imageBack = (ImageView) findViewById(R.id.buttonBackBuatTiket);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAduanFragmentAdapter = new BuatAduanFragmentAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) globalLayout.findViewById(R.id.containerBuatAduan);
        mViewPager.setAdapter(mAduanFragmentAdapter);

        btnNext = (Button) findViewById(R.id.buttonNext);
        btnPrev = (Button) findViewById(R.id.buttonPrevious);
        btnFinish = (Button) findViewById(R.id.buttonFinish);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                case 0:
                    btnPrev.setVisibility(View.GONE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnFinish.setVisibility(View.INVISIBLE);
                break;

                    case 1:
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                    btnFinish.setVisibility(View.VISIBLE);
                    break;

        }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPrev.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
                btnFinish.setVisibility(View.VISIBLE);
                mViewPager.setCurrentItem(1);
            }
        });


        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPrev.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
                btnFinish.setVisibility(View.INVISIBLE);
                mViewPager.setCurrentItem(0);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bNama = (EditText) view.getRootView().findViewById(R.id.et_nama);
                bAlamat = (EditText) view.getRootView().findViewById(R.id.et_alamat);
                bNomor = (EditText) view.getRootView().findViewById(R.id.et_nomor);
                bTopik = (Spinner) view.getRootView().findViewById(R.id.spinnerTopik);
                bIsiAduan = (EditText) view.getRootView().findViewById(R.id.et_isiAduan);

                bDataBenar = (CheckBox) view.getRootView().findViewById(R.id.checkDataBenar);

                String nama = bNama.getText().toString();
                String alamat = bAlamat.getText().toString();
                String nomor = bNomor.getText().toString();
                String topik = bTopik.getSelectedItem().toString();
                String isiAduan = bIsiAduan.getText().toString();

                if(!nama.isEmpty() && !alamat.isEmpty() && !nomor.isEmpty() && !topik.isEmpty() && !isiAduan.isEmpty()){

                    if(bDataBenar.isChecked()){
                        showDialogBuatAduan(nama, alamat, nomor, topik, isiAduan);
                    }else {
                        checkNotComplete();
                    }

                }else{

                    isianNotComplete();

                }

            }
        });



    }

    public void showDialogBuatAduan(final String nama, final String alamat, final String nomor, final String topik, final String isiAduan){

        int widthinPixels = (int) getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogKBuatAduan = CDialog.createDialog(getWindow().getContext(), R.layout.d_confirm_buat_aduan, widthinPixels, heightinPixels);

        dialogKBuatAduan.show();

        LinearLayout btnCancel = (LinearLayout) dialogKBuatAduan.findViewById(R.id.btnCancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKBuatAduan.dismiss();
            }
        });

        LinearLayout btnKirim = (LinearLayout) dialogKBuatAduan.findViewById(R.id.btnKirimDialog);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKBuatAduan.dismiss();
                mPresenter.buatAduan(view.getContext(), nama, alamat, nomor, topik, isiAduan);
            }
        });

    }

    public void showDialogTelahDikirim(String no_tiket){

        int widthinPixels = (int) getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) getResources().getDimension(R.dimen.ModalHeightSelesai);

        final Dialog dialogTelahDikirim = CDialog.createDialog(getWindow().getContext(), R.layout.d_telah_dikirim, widthinPixels, heightinPixels);

        dialogTelahDikirim.show();

        TextView txtNoTiket = (TextView) dialogTelahDikirim.findViewById(R.id.labelBuatAduan);
        txtNoTiket.setText("No. Tiket : "+no_tiket);

        LinearLayout btnDismiss = (LinearLayout) dialogTelahDikirim.findViewById(R.id.btnDismissDialog);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTelahDikirim.dismiss();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);// New activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }, 300);

            }
        });

    }

    public void isianNotComplete(){

        CSnackbar.createSnackbarTop(getWindow().getDecorView().findViewById(R.id.layoutGlobalBuatTiket),
                Color.WHITE, "#E18585", "Lengkapi Data Isian");

    }

    public void checkNotComplete(){

        CSnackbar.createSnackbarTop(getWindow().getDecorView().findViewById(R.id.layoutGlobalBuatTiket),
                Color.WHITE, "#E18585", "Centang Kebenaran Data");

    }

}
