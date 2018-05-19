package com.example.ginanjarpr.siapmvpdev.function.login;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.ginanjarpr.siapmvpdev.R;
import com.example.ginanjarpr.siapmvpdev.function.admin.AdminFragment;
import com.example.ginanjarpr.siapmvpdev.utils.CSnackbar;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public class LoginFragment extends Fragment implements View.OnClickListener, LoginContract.View {

    private AppCompatButton btn_login;
    private EditText et_email,et_password;
    private TextView labelLogin;
    private ProgressBar progress;
    private SharedPreferences pref;

    private LoginPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);
        initViews(view);


        return view;
    }

    private void initViews(View view){

        mPresenter = new LoginPresenter(this);

        pref = getDefaultSharedPreferences(view.getContext());

        btn_login = (AppCompatButton)view.findViewById(R.id.btn_login);
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_password = (EditText)view.findViewById(R.id.et_password);
        labelLogin = (TextView)view.findViewById(R.id.labelLogin);

        labelLogin.setAlpha(0f);
        labelLogin.setTranslationX(200f);
        labelLogin.setTranslationY(200f);

        progress = (ProgressBar) view.findViewById(R.id.progress);

        labelLogin.animate()
                .alpha(1f)
                .scaleX(1f)
                .translationY(0f)
                .translationX(0f)
                .scaleY(1f)
                .setDuration(350)
                .setStartDelay(300)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();

        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_login:
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()) {

                    mPresenter.doLogin(email,password, getActivity());

                } else {

                    loginNotComplete();

                }
                break;

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

    public void showProgress(){

        progress.setVisibility(View.VISIBLE);

    }

    public void hideProgress(){

        progress.setVisibility(View.INVISIBLE);

    }

    public void setWelcomeName(String name){

        CSnackbar.createSnackbarTop(getView(),
                Color.WHITE, "#5D5D5D", "Selamat Datang, " + name);

    }

    public void setNamaEmailDrawer(String nama, String email){

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);


        final TextView namaDrawer = (TextView) header.findViewById(R.id.TextViewNamaDrawer);
        final TextView emailDrawer = (TextView) header.findViewById(R.id.TextViewEmailDrawer);


        namaDrawer.setText(nama);
        emailDrawer.setText(email);

    }

    public void goToAdmin() {

        Fragment admin = new AdminFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.fragment_frame, admin);
        ft.commit();
    }

    public void loginErrorConnection(){

        CSnackbar.createSnackbarTop(getView(),
                Color.WHITE, "#E18585", "Koneksi Error");

    }

    public void loginWrongCredentials(){

        CSnackbar.createSnackbarTop(getView(),
                Color.WHITE, "#E18585", "Username / Password Salah");

    }

    public void loginNotComplete(){

        CSnackbar.createSnackbarTop(getView(),
                Color.WHITE, "#E18585", "Isi Username dan Password");

    }



}
