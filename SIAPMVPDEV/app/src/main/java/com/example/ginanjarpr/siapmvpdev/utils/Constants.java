package com.example.ginanjarpr.siapmvpdev.utils;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ginanjarpr.siapmvpdev.adapter.SectionFragmentPagerAdapter;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public class Constants {

    public static final String BASE_URL = "http://10.0.2.2/";
    public static final String LOGIN_OPERATION = "login";
    public static final String DELETE_OPERATION = "deleteTiket";

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String IS_LOGGED_IN = "isLoggedIn";

    public static String NAME_FIELD = "";
    public static String ROLES_FIELD = "";
    public static String LOGGED = "";
    public static String NOTIFCOUNT = "0";

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String ROLES = "roles";

    public static final String TAG = "Service Desk";

    public static int connect;

    public static ViewPager mViewPager;
    public static TabLayout tabLayout;
    public static CoordinatorLayout globalLayout;
    public static SectionFragmentPagerAdapter mSectionsPagerAdapter;

    public static View tabView;

    public static int counterSKPDBelumDiterima;
    public static int counterSKPDDiterima;

    public static int counterPoolBelumDiterima;
    public static int counterPoolDiterima;

    public static String flagVerifJalan="no";


}
