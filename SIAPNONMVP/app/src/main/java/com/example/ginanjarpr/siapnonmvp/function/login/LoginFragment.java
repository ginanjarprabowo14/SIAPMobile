package com.example.ginanjarpr.siapnonmvp.function.login;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.ginanjarpr.siapnonmvp.R;
import com.example.ginanjarpr.siapnonmvp.function.admin.AdminFragment;
import com.example.ginanjarpr.siapnonmvp.models.ServerRequest;
import com.example.ginanjarpr.siapnonmvp.models.ServerResponse;
import com.example.ginanjarpr.siapnonmvp.models.User;
import com.example.ginanjarpr.siapnonmvp.network.ApiClient;
import com.example.ginanjarpr.siapnonmvp.network.RequestInterface;
import com.example.ginanjarpr.siapnonmvp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton btn_login;
    private EditText et_email,et_password;
    private TextView tv_register;
    private TextView labelLogin;
    private ProgressBar progress;
    private SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);
        initViews(view);

        return view;
    }

    private void initViews(View view){

        pref = getActivity().getPreferences(0);

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
//        tv_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_login:
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()) {

                    loginProcess(email,password);

                } else {

                    loginNotComplete();

                }
                break;

        }
    }

    private void loginProcess(String email,String password){

        progress.setVisibility(View.VISIBLE);

        RequestInterface requestInterface = ApiClient.getClient().create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, final retrofit2.Response<ServerResponse> response) {

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ServerResponse resp = response.body();
                        if(resp.getMessage().equalsIgnoreCase("Invalid Login Credentials")){

                            progress.setVisibility(View.INVISIBLE);
                            loginWrongCredentials();

                        }

//                        Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                        if(resp.getResult().equals(Constants.SUCCESS)){
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean(Constants.IS_LOGGED_IN,true);
                            editor.putString(Constants.EMAIL,resp.getUser().getEmail());
                            editor.putString(Constants.NAME,resp.getUser().getName());
                            editor.putString(Constants.ROLES,resp.getUser().getRoles());
                            editor.putString("notifcount","3");
                            editor.putString("status_home", "DONE");
                            editor.apply();

                            final TSnackbar snackbarsd = TSnackbar.make(getView(), "Selamat Datang, " + resp.getUser().getName(), TSnackbar.LENGTH_LONG);
                            snackbarsd.setActionTextColor(Color.WHITE);
                            Constants.NAME_FIELD=", " + resp.getUser().getName();
                            Constants.ROLES_FIELD=resp.getUser().getRoles();
                            Constants.NOTIFCOUNT="3";
                            Constants.LOGGED="OK";


                            NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                            View header=navigationView.getHeaderView(0);


                            final TextView namaDrawer = (TextView) header.findViewById(R.id.TextViewNamaDrawer);
                            final TextView emailDrawer = (TextView) header.findViewById(R.id.TextViewEmailDrawer);


                            namaDrawer.setText(resp.getUser().getName().toString());
                            emailDrawer.setText(resp.getUser().getEmail().toString());

                            View snackbarView = snackbarsd.getView();
                            snackbarView.setBackgroundColor(Color.parseColor("#5D5D5D"));
                            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                            textView.setTextColor(Color.parseColor("#FFFFFF"));
                            snackbarsd.show();

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    progress.setVisibility(View.INVISIBLE);
                                    goToAdmin();

                                }
                            }, 3000);

                        }

                    }
                }, 1000);

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
//                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                loginErrorConnection();

            }
        });
    }

    private void goToAdmin() {

        Fragment admin = new AdminFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.fragment_frame, admin);
        ft.commit();
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


    public void loginErrorConnection(){

        final TSnackbar snackbarsd = TSnackbar.make(getView(), "Koneksi Error", TSnackbar.LENGTH_LONG);
        snackbarsd.setActionTextColor(Color.WHITE);
        View snackbarView = snackbarsd.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#E18585"));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbarsd.show();

    }

    public void loginWrongCredentials(){

        final TSnackbar snackbarsd = TSnackbar.make(getView(), "Username / Password Salah", TSnackbar.LENGTH_LONG);
        snackbarsd.setActionTextColor(Color.WHITE);
        View snackbarView = snackbarsd.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#E18585"));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbarsd.show();

    }

    public void loginNotComplete(){

        final TSnackbar snackbarsd = TSnackbar.make(getView(), "Isi Username dan Password", TSnackbar.LENGTH_LONG);
        snackbarsd.setActionTextColor(Color.WHITE);
        View snackbarView = snackbarsd.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#E18585"));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbarsd.show();

    }


}
