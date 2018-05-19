package com.example.ginanjarpr.siapnonmvp.function.admin;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ginanjarpr.siapnonmvp.R;
import com.example.ginanjarpr.siapnonmvp.function.tiket.TiketActivity;
import com.example.ginanjarpr.siapnonmvp.utils.Constants;

import static android.view.View.VISIBLE;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public class AdminFragment extends Fragment implements View.OnClickListener{

    LinearLayout baris1;
    LinearLayout segmenbawah;
    LinearLayout notif;
    LinearLayout layoutInfoAdmin;
    TextView welcomeRole;
    TextView createNew;
    TextView descNotif;

    public SharedPreferences pref;
    public String namapref;
    public String counterpref;
    public String rolespref;
    public String statushomepref;
    public Boolean isloginpref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin,container,false);
        initViews(view);
        return view;
    }


    private void initViews(View view){

        baris1 = (LinearLayout) view.findViewById(R.id.titleHome);
        welcomeRole = (TextView) view.findViewById(R.id.welcomeRole);
        descNotif = (TextView) view.findViewById(R.id.penjelasanNotif);
        segmenbawah = (LinearLayout) view.findViewById(R.id.segmenbawah);
        layoutInfoAdmin = (LinearLayout) view.findViewById(R.id.layoutInfoAdmin);
        LinearLayout btnViewTiket = (LinearLayout) view.findViewById(R.id.btnViewAduan);

        pref = getActivity().getPreferences(0);
        namapref = pref.getString(Constants.NAME,"").toString();
        counterpref = pref.getString("notifcount","").toString();
        rolespref = pref.getString(Constants.ROLES,"").toString();
        statushomepref = pref.getString("status_home","").toString();
        isloginpref = pref.getBoolean(Constants.IS_LOGGED_IN,false);

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


        if(rolespref.equalsIgnoreCase("1")) {
            welcomeRole.setText("Admin Pool");
            welcomeRole.setBackgroundResource(R.drawable.shape2);
        }else{

        }


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
    public void onClick(View v) {


//
//        switch (v.getId()){
//            case R.id.tv_login:
//                goToLogin();
//                break;
//
//            case R.id.btn_register:
//
//                String name = et_name.getText().toString();
//                String email = et_email.getText().toString();
//                String password = et_password.getText().toString();
//
//                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
//
//                    progress.setVisibility(View.VISIBLE);
//                    registerProcess(name,email,password);
//
//                } else {
//
//                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
//                }
//                break;
//
//        }
//
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
}

