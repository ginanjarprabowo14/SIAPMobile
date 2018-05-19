package com.example.ginanjarpr.siapnonmvp.function.home;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ginanjarpr.siapnonmvp.R;
import com.example.ginanjarpr.siapnonmvp.function.MainActivity;
import com.example.ginanjarpr.siapnonmvp.function.verifnik.VerifnikActivity;
import com.example.ginanjarpr.siapnonmvp.models.ServerRequest;
import com.example.ginanjarpr.siapnonmvp.models.ServerResponse;
import com.example.ginanjarpr.siapnonmvp.models.Tiket;
import com.example.ginanjarpr.siapnonmvp.network.ApiClient;
import com.example.ginanjarpr.siapnonmvp.network.RequestInterface;
import com.example.ginanjarpr.siapnonmvp.utils.CDialog;
import com.example.ginanjarpr.siapnonmvp.utils.CSnackbar;
import com.example.ginanjarpr.siapnonmvp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public class HomeFragment extends Fragment {

    LinearLayout layoutbawah;
    LinearLayout segmenbawah;
    TextView welcomeNama;
    TextView descNotif;
    LinearLayout buttonAddTiket;
    ImageView buttonCekTiket;
    EditText tiketText;
    ProgressBar progress;

    public SharedPreferences pref;
    public String namapref;
    public String counterpref;
    public String rolespref;
    public String statushomepref;
    public Boolean isloginpref;

    RequestInterface requestInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        initViews(view);
        return view;
    }


    private void initViews(View view){

        layoutbawah = (LinearLayout) view.findViewById(R.id.layoutBawah);
        welcomeNama = (TextView) view.findViewById(R.id.welcomeNama);
        descNotif = (TextView) view.findViewById(R.id.penjelasanNotif);
        segmenbawah = (LinearLayout) view.findViewById(R.id.segmenbawah);
        buttonAddTiket = (LinearLayout) view.findViewById(R.id.buttonAddTiket);
        tiketText = (EditText) view.findViewById(R.id.et_noTiket);
        progress = (ProgressBar) view.findViewById(R.id.progressHome);

        buttonAddTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(view.getContext(), VerifnikActivity.class);
                view.getContext().startActivity(a);
            }
        });

        buttonCekTiket = (ImageView) view.findViewById(R.id.btnCekAduan);
        buttonCekTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String no_tiket = tiketText.getText().toString();
                if (!no_tiket.isEmpty()) {
                    showProgress();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            checkTiket(view.getContext(), no_tiket);
                            hideProgress();

                        }
                    }, 2000);


                } else {
                    inputNotComplete();
                }

            }
        });


        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        namapref = pref.getString(Constants.NAME,"").toString();
        counterpref = pref.getString("notifcount","").toString();
        rolespref = pref.getString(Constants.ROLES,"").toString();
        statushomepref = pref.getString("status_home","").toString();
        isloginpref = pref.getBoolean(Constants.IS_LOGGED_IN,false);


        segmenbawah.setAlpha(0f);
        segmenbawah.setTranslationX(200f);
        segmenbawah.setTranslationY(0);
        segmenbawah.setVisibility(View.VISIBLE);

        segmenbawah.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0f)
                .translationY(0f)
                .translationZ(10f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(400)
                .start();

        layoutbawah.setAlpha(0f);
        layoutbawah.setTranslationX(0);
        layoutbawah.setTranslationY(200f);
        layoutbawah.setVisibility(View.VISIBLE);

        layoutbawah.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0f)
                .translationY(0f)
                .translationZ(10f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(800)
                .start();


        if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){

            NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);

            TextView texts = (TextView) navigationView.findViewById(R.id.tv_titles3);
            texts.setText("Logout");

            final LinearLayout linear1 = (LinearLayout) navigationView.findViewById(R.id.drawerlist1);
            final LinearLayout linear3 = (LinearLayout) navigationView.findViewById(R.id.drawerlist3);

            linear1.setAlpha(1);
            linear3.setAlpha(0.5f);

        }


    }


    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        getActivity().finish();
                    }

                    return true;

                }

                return false;
            }
        });
    }

    public void showDitemukan(Context context, final String no_tiket, String status_aduan, String jawaban){

        int widthinPixels = (int) getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) getResources().getDimension(R.dimen.ModalHeightDitemukan);

        final Dialog dialogDitemukan = CDialog.createDialog(getActivity(), R.layout.d_tiket_ditemukan, widthinPixels, heightinPixels);

        dialogDitemukan.show();

        TextView txtNoTiket = (TextView) dialogDitemukan.findViewById(R.id.txtnoTiketz);
        TextView txtStatusAduan = (TextView) dialogDitemukan.findViewById(R.id.labelStatusAduan);
        TextView txtJawaban = (TextView) dialogDitemukan.findViewById(R.id.txtJawabanTiket);

        txtNoTiket.setText(no_tiket);
        txtJawaban.setText(jawaban);

        LinearLayout btnDismiss = (LinearLayout) dialogDitemukan.findViewById(R.id.btnDismissDialog);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDitemukan.dismiss();

            }
        });

        LinearLayout btnSelesai = (LinearLayout) dialogDitemukan.findViewById(R.id.btnSelesaiDialog);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDitemukan.dismiss();
                showKonfirmasiSelesai(no_tiket);
            }
        });

        switch(status_aduan) {
            case "1":
                txtStatusAduan.setText("Belum Diterima Admin Pool");
                break;
            case "2":
                txtStatusAduan.setText("Diterima Admin Pool");
                break;
            case "3":
                txtStatusAduan.setText("Belum Diterima SKPD");
                break;
            case "4":
                txtStatusAduan.setText("Diterima SKPD");
                break;
            case "5":
                txtStatusAduan.setText("Aduan Terselesaikan");
                btnSelesai.setVisibility(View.GONE);
                break;
        }

    }

    public void showKonfirmasiSelesai(final String no_tiket){

        int widthinPixels = (int) getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogKonfirm = CDialog.createDialog(getActivity(), R.layout.d_confirm_selesai_aduan, widthinPixels, heightinPixels);

        dialogKonfirm.show();

        TextView txtKK = dialogKonfirm.findViewById(R.id.detailKonfirmasiSelesai);
        txtKK.setText("Anda yakin akan menyelesaikan\naduan dengan nomor\n" + no_tiket + " ?");

        LinearLayout btnDismiss = (LinearLayout) dialogKonfirm.findViewById(R.id.btnCancelDialog);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKonfirm.dismiss();

            }
        });

        LinearLayout btnSelesai = (LinearLayout) dialogKonfirm.findViewById(R.id.btnSelesaiFixDialog);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKonfirm.dismiss();
                selesaiTiket(view.getContext(), no_tiket);

            }
        });

    }

    public void showSelesaiSukses(String no_tiket){

        int widthinPixels = (int) getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) getResources().getDimension(R.dimen.ModalHeightTidakDitemukan);

        final Dialog dialogSelesai = CDialog.createDialog(getActivity(), R.layout.d_sukses_selesai_aduan, widthinPixels, heightinPixels);

        dialogSelesai.show();

        TextView txtNoTikets = (TextView) dialogSelesai.findViewById(R.id.labelNoTiketAduan);
        txtNoTikets.setText(no_tiket);

        LinearLayout btnDismiss = (LinearLayout) dialogSelesai.findViewById(R.id.btnDismissDialog);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSelesai.dismiss();

                Intent a = new Intent(getActivity(), MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(a);


            }
        });

    }

    public void showTidakDitemukan(String no_tiket){

        int widthinPixels = (int) getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) getResources().getDimension(R.dimen.ModalHeightTidakDitemukan);

        final Dialog dialogTidakDitemukan = CDialog.createDialog(getActivity(), R.layout.d_tiket_tidak_ditemukan, widthinPixels, heightinPixels);

        dialogTidakDitemukan.show();

        TextView noticket = (TextView) dialogTidakDitemukan.findViewById(R.id.txtNoTiketS);
        noticket.setText(no_tiket);

        LinearLayout btnDismiss = (LinearLayout) dialogTidakDitemukan.findViewById(R.id.btnDismissDialog);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTidakDitemukan.dismiss();


            }
        });

    }

    public void inputNotComplete(){

        CSnackbar.createSnackbarTop(getView().findViewById(R.id.parentLogin),
                Color.WHITE, "#5D5D5D", "Masukkan No. Tiket");

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

    public void checkTiket(final Context context, final String no_tiket){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        ServerRequest request = new ServerRequest();
        request.setOperation("checkTiket");
        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    showDitemukan(context, no_tiket, resp.getStatus_aduan().toString(), resp.getJawaban().toString());
                } else{
                    showTidakDitemukan(no_tiket);
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, t.getLocalizedMessage());

            }
        });
    }

    public void selesaiTiket(final Context context, final String no_tiket){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        ServerRequest request = new ServerRequest();
        request.setOperation("selesaiAduan");
        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    showSelesaiSukses(no_tiket);
                } else{
                    //gagal
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, t.getLocalizedMessage());

            }
        });
    }
}
