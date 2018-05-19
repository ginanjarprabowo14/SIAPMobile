package com.example.ginanjarpr.siapnonmvp.function;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.JobIntentService;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ginanjarpr.siapnonmvp.R;
import com.example.ginanjarpr.siapnonmvp.function.admin.AdminFragment;
import com.example.ginanjarpr.siapnonmvp.function.home.HomeFragment;
import com.example.ginanjarpr.siapnonmvp.function.login.LoginFragment;
import com.example.ginanjarpr.siapnonmvp.function.tentang.TentangFragment;
import com.example.ginanjarpr.siapnonmvp.utils.CNetwork;
import com.example.ginanjarpr.siapnonmvp.utils.Constants;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    public SharedPreferences pref;

    Toolbar toolbar;
    public String rolespref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (CNetwork.isNetworkAvailable(getApplicationContext())) {
            Constants.connect = 1;
        } else {
            Constants.connect = 0;
        }

        pref = getDefaultSharedPreferences(getBaseContext());
        rolespref = pref.getString(Constants.ROLES,"").toString();

        init("");
        setDrawer();
        setNamaDrawer();
        initFragment();

    }


    public void initFragment(){

        Fragment fragment;
        if(pref.getBoolean(Constants.IS_LOGGED_IN, false)){

            fragment = new AdminFragment();
        }else{

            fragment = new HomeFragment();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,fragment);
        ft.commit();


    }

    @Override
    public void onBackPressed() {


    }

    //init menu navigation drawer
    private void init(String txt) {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<font color='#FFFFF'>"+txt+"</font>"));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setSupportActionBar(toolbar);


    }

    public void setDrawer() {

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final DrawerLayout drawers = (DrawerLayout) findViewById(R.id.drawer_layout);

        final LinearLayout linear1 = (LinearLayout) findViewById(R.id.drawerlist1);
        final LinearLayout linear3 = (LinearLayout) findViewById(R.id.drawerlist3);
        final LinearLayout linear4 = (LinearLayout) findViewById(R.id.drawerlist4);
        final TextView textlogg = (TextView) findViewById(R.id.tv_titles3);


        if(pref.getBoolean(Constants.IS_LOGGED_IN, false)){
            textlogg.setText("Logout");
        }

        linear1.setAlpha(1);

        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pref.getBoolean(Constants.IS_LOGGED_IN, false)){
                    linear1.setAlpha(1);
                    linear3.setAlpha((float) 0.5);
                    linear4.setAlpha((float) 0.5);
                    drawers.closeDrawer(GravityCompat.START);
                    toolbar.setTitle(Html.fromHtml("<font color='#FFFFF'></font>"));
                    goToAdmin();

                }else{
                    linear1.setAlpha(1);
                    linear3.setAlpha((float) 0.5);
                    linear4.setAlpha((float) 0.5);
                    drawers.closeDrawer(GravityCompat.START);
                    toolbar.setTitle(Html.fromHtml("<font color='#FFFFF'></font>"));
                    goToHome();
                }

            }
        });

        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView texts = (TextView) linear3.findViewById(R.id.tv_titles3);
                if(texts.getText().toString().equalsIgnoreCase("Logout")){
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

                    texts.setText("Login Admin");

                    linear3.setAlpha(1);
                    linear1.setAlpha((float) 0.5);
                    linear4.setAlpha((float) 0.5);
                    drawers.closeDrawer(GravityCompat.START);

                    logout();
                } else if (texts.getText().toString().equalsIgnoreCase("Login Admin")) {
                    linear3.setAlpha(1);
                    linear1.setAlpha((float) 0.5);
                    linear4.setAlpha((float) 0.5);
                    drawers.closeDrawer(GravityCompat.START);
                    goToLogin();
                } else {

                }

            }
        });

        linear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear4.setAlpha(1);
                linear3.setAlpha((float) 0.5);
                linear1.setAlpha((float) 0.5);
                drawers.closeDrawer(GravityCompat.START);
                goToTentang();
            }
        });

    }

    public void setNamaDrawer(){

        if(pref.getBoolean(Constants.IS_LOGGED_IN, false)){

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View header=navigationView.getHeaderView(0);


            final TextView namaDrawer = (TextView) header.findViewById(R.id.TextViewNamaDrawer);
            final TextView emailDrawer = (TextView) header.findViewById(R.id.TextViewEmailDrawer);


            namaDrawer.setText(pref.getString(Constants.NAME,"").toString());
            emailDrawer.setText(pref.getString(Constants.EMAIL,"").toString());
        }


    }

    private void goToTentang(){

        Fragment tentang = new TentangFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(R.id.fragment_frame,tentang);
        ft.commit();
    }

    private void goToLogin(){

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }

    private void goToHome(){

        Fragment home = new HomeFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(R.id.fragment_frame,home);
        ft.commit();
    }

    private void goToAdmin() {

        Fragment admin = new AdminFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.fragment_frame, admin);
        ft.commit();
    }

    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,false);
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.NAME,"");
        editor.putString(Constants.ROLES,"");
        editor.apply();
        editor.clear();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);


        final TextView namaDrawer = (TextView) header.findViewById(R.id.TextViewNamaDrawer);
        final TextView emailDrawer = (TextView) header.findViewById(R.id.TextViewEmailDrawer);


        namaDrawer.setText("You were not logged in");
        emailDrawer.setText("Login untuk menggunakan fitur notifikasi");

        goToLogin();
    }

}

