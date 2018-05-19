package com.example.ginanjarpr.siapnonmvp.function.buataduan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ginanjarpr.siapnonmvp.R;
import com.example.ginanjarpr.siapnonmvp.adapter.BuatAduanFragmentAdapter;
import com.example.ginanjarpr.siapnonmvp.function.MainActivity;
import com.example.ginanjarpr.siapnonmvp.models.ServerRequest;
import com.example.ginanjarpr.siapnonmvp.models.ServerResponse;
import com.example.ginanjarpr.siapnonmvp.models.Tiket;
import com.example.ginanjarpr.siapnonmvp.network.ApiClient;
import com.example.ginanjarpr.siapnonmvp.network.RequestInterface;
import com.example.ginanjarpr.siapnonmvp.utils.CDialog;
import com.example.ginanjarpr.siapnonmvp.utils.CNetwork;
import com.example.ginanjarpr.siapnonmvp.utils.CSnackbar;
import com.example.ginanjarpr.siapnonmvp.utils.Constants;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuataduanActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buataduan);

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
                buatAduan(view.getContext(), nama, alamat, nomor, topik, isiAduan);
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

    public void buatAduan(Context context, String nama, String alamat, String nomor, String topik, String isiAduan){

        RequestInterface requestInterface = ApiClient.getClient().create(RequestInterface.class);

        Calendar calendar = Calendar.getInstance();

        String nm = String.valueOf(calendar.get(Calendar.YEAR));
        String week = String.valueOf(calendar.get(Calendar.WEEK_OF_MONTH));
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String minutes = String.valueOf(calendar.get(Calendar.MINUTE));
        String seconds = String.valueOf(calendar.get(Calendar.SECOND));

        final String noTiketInput = "KH1"+nm+week+day+minutes+seconds;

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(noTiketInput);
        tiket.setNama_pengadu(nama);
        tiket.setAlamat_pengadu(alamat);
        tiket.setNo_telepon_pengadu(nomor);
        tiket.setTopik_aduan(topik);
        tiket.setIsi_aduan(isiAduan);

        ServerRequest request = new ServerRequest();
        request.setOperation("buatTiket");

        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    showDialogTelahDikirim(noTiketInput);
                } else{
                    //delete gagal
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, t.getLocalizedMessage());

            }
        });


    }

}
