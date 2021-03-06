package com.example.ginanjarpr.siapmvpdev.function.tiket.belumditerima;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ginanjarpr.siapmvpdev.R;
import com.example.ginanjarpr.siapmvpdev.adapter.TiketAdapterBelumDiterima;
import com.example.ginanjarpr.siapmvpdev.function.login.LoginPresenter;
import com.example.ginanjarpr.siapmvpdev.function.tiket.TiketActivity;
import com.example.ginanjarpr.siapmvpdev.function.tiket.diterima.DiterimaPresenter;
import com.example.ginanjarpr.siapmvpdev.models.ServerRequest;
import com.example.ginanjarpr.siapmvpdev.models.ServerResponse;
import com.example.ginanjarpr.siapmvpdev.models.ServerResponseGet;
import com.example.ginanjarpr.siapmvpdev.models.Tiket;
import com.example.ginanjarpr.siapmvpdev.network.ApiClient;
import com.example.ginanjarpr.siapmvpdev.network.RequestInterface;
import com.example.ginanjarpr.siapmvpdev.utils.CDialog;
import com.example.ginanjarpr.siapmvpdev.utils.Constants;
import com.example.ginanjarpr.siapmvpdev.utils.Converter;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class BelumDiterimaFragment extends Fragment implements BelumDiterimaContract.View {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView recyclerviewAduanBelumDiterima;

    TextView txtTidakDitemukan;

    public SharedPreferences pref;
    public String rolespref;

    private BelumDiterimaPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_belum_diterima, container, false);

        mPresenter = new BelumDiterimaPresenter(this);

        pref = getDefaultSharedPreferences(rootView.getContext());
        rolespref = pref.getString(Constants.ROLES, "").toString();

        txtTidakDitemukan = (TextView) rootView.findViewById(R.id.noDataToShow);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.belum_diterima_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorOrange, R.color.colorAgree);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Constants.connect == 0) {

                    Snackbar snackbars = Snackbar.make(rootView, "No Internet Connection", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null);
                    View snackbarViewk = snackbars.getView();
                    snackbarViewk.setBackgroundColor(Color.parseColor("#EB9377"));
                    snackbars.show();
                    mSwipeRefreshLayout.setRefreshing(false);

                } else {

                    Snackbar snackbar = Snackbar.make(rootView, "Loading data...", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#515558"));
                    snackbar.show();

                    txtTidakDitemukan.animate()
                            .alpha(0f)
                            .scaleX(1)
                            .scaleY(1)
                            .translationX(0f)
                            .translationY(70)
                            .translationZ(0f)
                            .setInterpolator(new FastOutSlowInInterpolator())
                            .setStartDelay(100)
                            .start();

                    recyclerviewAduanBelumDiterima.animate()
                            .alpha(0f)
                            .scaleX(1f)
                            .scaleY(1f)
                            .translationX(-200f)
                            .translationY(0f)
                            .translationZ(0f)
                            .setInterpolator(new FastOutSlowInInterpolator())
                            .setStartDelay(000)
                            .start();

                    //Animasi Swipe Refresh
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms

                            if (rolespref.equalsIgnoreCase("1")) {

                                mPresenter.refreshDataBelumDiterimaPool();

                            } else {
                                mPresenter.refreshDataBelumDiterimaSKPD();

                            }

                            mSwipeRefreshLayout.setRefreshing(false);

                            recyclerviewAduanBelumDiterima.animate()
                                    .alpha(1f)
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .translationX(0f)
                                    .translationY(0f)
                                    .translationZ(0f)
                                    .setInterpolator(new FastOutSlowInInterpolator())
                                    .setStartDelay(300)
                                    .start();

                        }
                    }, 3000);

                }

            }
        });

        recyclerviewAduanBelumDiterima = (RecyclerView) rootView.findViewById(R.id.recyclerviewAduanBelumDiterima);
        recyclerviewAduanBelumDiterima.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        horizontal layout manager
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerviewAduanBelumDiterima.setLayoutManager(mLayoutManager);

        recyclerviewAduanBelumDiterima.setAlpha(0f);
        recyclerviewAduanBelumDiterima.setTranslationX(-200f);
        recyclerviewAduanBelumDiterima.setTranslationY(0);
        recyclerviewAduanBelumDiterima.setVisibility(View.VISIBLE);

        recyclerviewAduanBelumDiterima.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0f)
                .translationY(0f)
                .translationZ(10f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(400)
                .start();

        if (rolespref.equalsIgnoreCase("1")) {

            mPresenter.refreshDataBelumDiterimaPool();

        } else {
            mPresenter.refreshDataBelumDiterimaSKPD();

        }

        return rootView;

    }

    public void setAdapterRecycler(RecyclerView.Adapter adapters){

        recyclerviewAduanBelumDiterima.setAdapter(adapters);

    }

    public void showDialogKonfirmasiKembalikanSKPD(Context context, final String no_tiket){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogKembaliAduan = CDialog.createDialog(context, R.layout.d_confirm_hapus_terima_aduan, widthinPixels, heightinPixels);

        dialogKembaliAduan.show();

        final ImageView imageTanda = (ImageView) dialogKembaliAduan.getWindow().findViewById(R.id.imageTanda);
        final TextView textInfoKonfirmasi = (TextView) dialogKembaliAduan.getWindow().findViewById(R.id.txtInfoKonfirmasi);
        final TextView textDetailKonfirmasi = (TextView) dialogKembaliAduan.getWindow().findViewById(R.id.txtDetailKonfirmasi);
        final TextView textBtnKonfirm = (TextView) dialogKembaliAduan.getWindow().findViewById(R.id.txtBtnKonfirm);
        final ImageView logoBtnKonfirm = (ImageView) dialogKembaliAduan.getWindow().findViewById(R.id.logoBtnKonfirm);

        imageTanda.setImageResource(R.drawable.ic_autorenew_large);
        textInfoKonfirmasi.setText("Kembalikan Aduan");
        textDetailKonfirmasi.setText("Anda yakin akan mengembalikan\naduan dengan nomor\n"+no_tiket+" ?");
        textBtnKonfirm.setText("Kembalikan");
        logoBtnKonfirm.setImageResource(R.drawable.ic_autorenew_white);

        mPresenter = new BelumDiterimaPresenter(this);

        pref = getDefaultSharedPreferences(context);
        rolespref = pref.getString(Constants.ROLES, "").toString();

        LinearLayout btnCancel = (LinearLayout) dialogKembaliAduan.findViewById(R.id.btnCancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKembaliAduan.dismiss();
            }
        });

        LinearLayout btnKirim = (LinearLayout) dialogKembaliAduan.findViewById(R.id.btnDeleteDialog);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKembaliAduan.dismiss();
                mPresenter.kembaliTiket(view.getContext(), no_tiket);

            }
        });

    }

    public void showDialogKonfirmasiTerima(Context context, final String no_tiket){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogTerimaAduan = CDialog.createDialog(context, R.layout.d_confirm_hapus_terima_aduan, widthinPixels, heightinPixels);

        dialogTerimaAduan.show();

        final ImageView imageTanda = (ImageView) dialogTerimaAduan.getWindow().findViewById(R.id.imageTanda);
        final TextView textInfoKonfirmasi = (TextView) dialogTerimaAduan.getWindow().findViewById(R.id.txtInfoKonfirmasi);
        final TextView textDetailKonfirmasi = (TextView) dialogTerimaAduan.getWindow().findViewById(R.id.txtDetailKonfirmasi);
        final TextView textBtnKonfirm = (TextView) dialogTerimaAduan.getWindow().findViewById(R.id.txtBtnKonfirm);
        final ImageView logoBtnKonfirm = (ImageView) dialogTerimaAduan.getWindow().findViewById(R.id.logoBtnKonfirm);

        imageTanda.setImageResource(R.drawable.ic_playlist_add);
        textInfoKonfirmasi.setText(R.string.info_konfirmasi_terima);
        textDetailKonfirmasi.setText("Anda yakin akan menerima\naduan dengan nomor\n"+no_tiket+" ?");
        textBtnKonfirm.setText("Terima");
        logoBtnKonfirm.setImageResource(R.drawable.ic_playlist_add_white);

        mPresenter = new BelumDiterimaPresenter(this);

        pref = getDefaultSharedPreferences(context);
        rolespref = pref.getString(Constants.ROLES, "").toString();

        LinearLayout btnCancel = (LinearLayout) dialogTerimaAduan.findViewById(R.id.btnCancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTerimaAduan.dismiss();
            }
        });

        LinearLayout btnKirim = (LinearLayout) dialogTerimaAduan.findViewById(R.id.btnDeleteDialog);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTerimaAduan.dismiss();
                mPresenter.terimaTiket(view.getContext(), no_tiket);
            }
        });

    }

    public void showDialogSuksesTerima(Context context, String no_tiket){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogSuksesTerima = CDialog.createDialog(context, R.layout.d_selesai_hapus_terima, widthinPixels, heightinPixels);

        dialogSuksesTerima.show();

        final TextView txtNotiket = (TextView) dialogSuksesTerima.findViewById(R.id.txtNoTiket);
        txtNotiket.setText(no_tiket);

        LinearLayout btnCancel = (LinearLayout) dialogSuksesTerima.findViewById(R.id.btnDismissDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSuksesTerima.dismiss();
                TiketActivity tikets = new TiketActivity();
                tikets.initViewPager();
            }
        });


    }

    public void showDialogSuksesKembalikan(Context context, String no_tiket){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogSuksesKembalikan = CDialog.createDialog(context, R.layout.d_selesai_kembalikan_terima, widthinPixels, heightinPixels);

        dialogSuksesKembalikan.show();

        final TextView txtNotiket = (TextView) dialogSuksesKembalikan.findViewById(R.id.txtNoTiket);
        txtNotiket.setText(no_tiket);

        LinearLayout btnCancel = (LinearLayout) dialogSuksesKembalikan.findViewById(R.id.btnDismissDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSuksesKembalikan.dismiss();
                TiketActivity tikets = new TiketActivity();
                tikets.initViewPager();
            }
        });


    }

    public void showDialogSuksesTerimaSKPD(Context context, String no_tiket){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogSuksesTerimaSKPD = CDialog.createDialog(context, R.layout.d_selesai_kembalikan_terima, widthinPixels, heightinPixels);

        dialogSuksesTerimaSKPD.show();

        final TextView txtdetailKon = (TextView) dialogSuksesTerimaSKPD.findViewById(R.id.txtDetailKonfirmasi);
        final TextView txtNotiket = (TextView) dialogSuksesTerimaSKPD.findViewById(R.id.txtNoTiket);
        txtNotiket.setText(no_tiket);
        txtdetailKon.setText("Telah Diterima");

        LinearLayout btnCancel = (LinearLayout) dialogSuksesTerimaSKPD.findViewById(R.id.btnDismissDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSuksesTerimaSKPD.dismiss();
                TiketActivity tikets = new TiketActivity();
                tikets.initViewPager();
            }
        });


    }

    public void showDialogKonfirmasiHapusPool(Context context, final String no_tiket){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogHapusAduan = CDialog.createDialog(context, R.layout.d_confirm_hapus_terima_aduan, widthinPixels, heightinPixels);

        dialogHapusAduan.show();

        final TextView textDetailKonfirmasi = (TextView) dialogHapusAduan.getWindow().findViewById(R.id.txtDetailKonfirmasi);
        textDetailKonfirmasi.setText("Anda yakin akan menghapus\naduan dengan nomor\n"+no_tiket+" ?");

        mPresenter = new BelumDiterimaPresenter(this);

        LinearLayout btnCancel = (LinearLayout) dialogHapusAduan.findViewById(R.id.btnCancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHapusAduan.dismiss();
            }
        });

        LinearLayout btnKirim = (LinearLayout) dialogHapusAduan.findViewById(R.id.btnDeleteDialog);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHapusAduan.dismiss();
                mPresenter.hapusTiket(view.getContext(), no_tiket);

            }
        });

    }

    public void showDialogSuksesHapus(Context context, String no_tiket){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogSuksesHapus = CDialog.createDialog(context, R.layout.d_selesai_hapus_terima, widthinPixels, heightinPixels);

        dialogSuksesHapus.show();

        final ImageView imageTanda = (ImageView) dialogSuksesHapus.getWindow().findViewById(R.id.imageTanda);
        final TextView txtDetailInfo = (TextView) dialogSuksesHapus.findViewById(R.id.txtDetailKonfirmasi);
        final TextView txtNotiket = (TextView) dialogSuksesHapus.findViewById(R.id.txtNoTiket);
        txtNotiket.setText(no_tiket);
        txtDetailInfo.setText("Telah Dihapus");
        imageTanda.setImageResource(R.drawable.ic_delete_large);



        LinearLayout btnDismiss = (LinearLayout) dialogSuksesHapus.findViewById(R.id.btnDismissDialog);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSuksesHapus.dismiss();
                TiketActivity tikets = new TiketActivity();
                tikets.initViewPager();

            }
        });


    }

    public void showTidakDitemukan(){

        txtTidakDitemukan.setVisibility(View.VISIBLE);
        txtTidakDitemukan.setAlpha(0);

        txtTidakDitemukan.setTranslationY(70);
        txtTidakDitemukan.animate()
                .alpha(1f)
                .scaleX(1)
                .scaleY(1)
                .translationX(0f)
                .translationY(0)
                .translationZ(0f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(400)
                .start();

    }

    public void hideTidakDitemukan(){

        txtTidakDitemukan.setVisibility(View.GONE);
        txtTidakDitemukan.setAlpha(0);

        txtTidakDitemukan.setTranslationY(70);
        txtTidakDitemukan.animate()
                .alpha(0f)
                .scaleX(1)
                .scaleY(1)
                .translationX(0f)
                .translationY(0)
                .translationZ(0f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(400)
                .start();

    }



}