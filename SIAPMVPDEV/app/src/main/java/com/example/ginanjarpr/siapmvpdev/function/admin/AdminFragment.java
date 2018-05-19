package com.example.ginanjarpr.siapmvpdev.function.admin;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.JobIntentService;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ginanjarpr.siapmvpdev.R;
import com.example.ginanjarpr.siapmvpdev.function.tiket.TiketActivity;
import com.example.ginanjarpr.siapmvpdev.utils.Constants;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public class AdminFragment extends Fragment implements AdminContract.View {

    LinearLayout baris1;
    LinearLayout segmenbawah;
    LinearLayout layoutInfoAdmin;
    LinearLayout btnViewTiket;
    TextView welcomeRole;
    TextView descNotif;
    TextView notifAduan, diterima, belumDiterima;

    public SharedPreferences pref;
    public String namapref;
    public String counterpref;
    public String rolespref;
    public Boolean isloginpref;

    private AdminPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        initViews(view);

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms


//            }
//        }, 3000);

        return view;

    }

    private void initViews(View view) {

        mPresenter = new AdminPresenter(this);

        pref = getDefaultSharedPreferences(view.getContext());

        namapref = pref.getString(Constants.NAME, "").toString();
        counterpref = pref.getString("notifcount", "").toString();
        rolespref = pref.getString(Constants.ROLES, "").toString();
        isloginpref = pref.getBoolean(Constants.IS_LOGGED_IN, false);

        baris1 = (LinearLayout) view.findViewById(R.id.titleHome);
        welcomeRole = (TextView) view.findViewById(R.id.welcomeRole);
        descNotif = (TextView) view.findViewById(R.id.penjelasanNotif);
        segmenbawah = (LinearLayout) view.findViewById(R.id.segmenbawah);
        layoutInfoAdmin = (LinearLayout) view.findViewById(R.id.layoutInfoAdmin);
        btnViewTiket = (LinearLayout) view.findViewById(R.id.btnViewAduan);
        notifAduan = (TextView) view.findViewById(R.id.angkaNotifAduan);
        belumDiterima = (TextView) view.findViewById(R.id.belumditerima);
        diterima = (TextView) view.findViewById(R.id.diterima);


        btnViewTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(), TiketActivity.class);
                view.getContext().startActivity(a);
            }
        });


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

        layoutInfoAdmin.setAlpha(0f);
        layoutInfoAdmin.setTranslationX(0);
        layoutInfoAdmin.setTranslationY(200f);
        layoutInfoAdmin.setVisibility(View.VISIBLE);

        layoutInfoAdmin.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0f)
                .translationY(0f)
                .translationZ(10f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(800)
                .start();


        if (rolespref.equalsIgnoreCase("1")) {
            welcomeRole.setText("Admin Pool");
            welcomeRole.setBackgroundResource(R.drawable.shape2);
            diterima.setText(String.valueOf(Constants.counterPoolDiterima) + " Aduan");
            belumDiterima.setText(String.valueOf(Constants.counterPoolBelumDiterima) + " Aduan");
            notifAduan.setText(String.valueOf(Constants.counterPoolBelumDiterima + Constants.counterPoolDiterima));

        } else {
            diterima.setText(String.valueOf(Constants.counterSKPDDiterima) + " Aduan");
            belumDiterima.setText(String.valueOf(Constants.counterSKPDBelumDiterima) + " Aduan");
            notifAduan.setText(String.valueOf(Constants.counterSKPDBelumDiterima + Constants.counterSKPDDiterima));
        }


        if (pref.getBoolean(Constants.IS_LOGGED_IN, false)) {

            NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);

            TextView texts = (TextView) navigationView.findViewById(R.id.tv_titles3);
            texts.setText("Logout");

            final LinearLayout linear1 = (LinearLayout) navigationView.findViewById(R.id.drawerlist1);
            final LinearLayout linear3 = (LinearLayout) navigationView.findViewById(R.id.drawerlist3);

            linear1.setAlpha(1);
            linear3.setAlpha(0.5f);

        }

        mPresenter.refreshCounter(getActivity());

    }




    @Override
    public void onResume() {

        mPresenter.refreshCounter(getActivity());
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

    public void setViewTextTerima(String txtTerima){

        diterima.setText(txtTerima);

    }

    public void setViewTextBelumDiterima(String txtBelumDiterima){

        belumDiterima.setText(txtBelumDiterima);

    }

    public void setViewTextTotal(String txtTotal){

        notifAduan.setText(txtTotal);

    }

    public void setSharedCountPool(String count) {

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("countpool", count);
        editor.apply();

    }

    public void setSharedCountSKPD(String count) {

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("countskpd", count);
        editor.apply();

    }


}

