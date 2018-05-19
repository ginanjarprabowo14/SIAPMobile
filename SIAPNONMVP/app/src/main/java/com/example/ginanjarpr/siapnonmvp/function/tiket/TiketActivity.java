package com.example.ginanjarpr.siapnonmvp.function.tiket;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ginanjarpr.siapnonmvp.R;
import com.example.ginanjarpr.siapnonmvp.adapter.SectionFragmentPagerAdapter;
import com.example.ginanjarpr.siapnonmvp.function.MainActivity;
import com.example.ginanjarpr.siapnonmvp.function.admin.AdminFragment;
import com.example.ginanjarpr.siapnonmvp.models.ServerRequest;
import com.example.ginanjarpr.siapnonmvp.models.ServerResponse;
import com.example.ginanjarpr.siapnonmvp.models.Tiket;
import com.example.ginanjarpr.siapnonmvp.models.User;
import com.example.ginanjarpr.siapnonmvp.network.ApiClient;
import com.example.ginanjarpr.siapnonmvp.network.RequestInterface;
import com.example.ginanjarpr.siapnonmvp.utils.CNetwork;
import com.example.ginanjarpr.siapnonmvp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class TiketActivity extends AppCompatActivity {

    public SharedPreferences pref;
    public String rolespref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket);

        pref = getDefaultSharedPreferences(getApplicationContext());

        if (CNetwork.isNetworkAvailable(getApplicationContext())) {
            Constants.connect = 1;
        } else {
            Constants.connect = 0;
        }

        Constants.globalLayout = findViewById(R.id.layoutGlobalTiket);
        Constants.tabView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.custom_tab, null, false);

        //Set System Status Tampil
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        ImageView imageBack = (ImageView) findViewById(R.id.buttonBackAduan);
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

        Constants.mSectionsPagerAdapter = new SectionFragmentPagerAdapter(getSupportFragmentManager());

        Constants.mViewPager = (ViewPager) Constants.globalLayout.findViewById(R.id.container);

        initViewPager();

        rolespref = pref.getString(Constants.ROLES,"").toString();


        RelativeLayout linearLayout1 = (RelativeLayout) Constants.tabView.findViewById(R.id.ll);
        LinearLayout linearLayout2 = (LinearLayout) Constants.tabView.findViewById(R.id.ll2);

        Constants.tabLayout = (TabLayout) Constants.globalLayout.findViewById(R.id.tabs);
        Constants.tabLayout.setupWithViewPager(Constants.mViewPager);

        Constants.tabLayout.getTabAt(0).setCustomView(linearLayout1);
        Constants.tabLayout.getTabAt(1).setCustomView(linearLayout2);

        final TextView textView1S = (TextView) Constants.tabLayout.findViewById(R.id.tvtab1);
        final TextView textView1U = (TextView) Constants.tabLayout.findViewById(R.id.tvtab2);

        Constants.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if(position==0){
                    textView1S.setTextColor(getResources().getColor(R.color.colorSelect));
                    textView1U.setTextColor(getResources().getColor(R.color.colorUnselect));

                }
                else {
                    textView1S.setTextColor(getResources().getColor(R.color.colorUnselect));
                    textView1U.setTextColor(getResources().getColor(R.color.colorSelect));

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(getApplicationContext(), MainActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(a);

    }

    public void initViewPager(){

        Constants.mViewPager.setAdapter(Constants.mSectionsPagerAdapter);

    }

}
