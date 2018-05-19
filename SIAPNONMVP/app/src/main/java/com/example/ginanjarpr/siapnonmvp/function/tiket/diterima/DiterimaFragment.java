package com.example.ginanjarpr.siapnonmvp.function.tiket.diterima;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ginanjarpr.siapnonmvp.R;
import com.example.ginanjarpr.siapnonmvp.adapter.TiketAdapterDiterima;
import com.example.ginanjarpr.siapnonmvp.function.tiket.TiketActivity;
import com.example.ginanjarpr.siapnonmvp.models.ServerRequest;
import com.example.ginanjarpr.siapnonmvp.models.ServerResponse;
import com.example.ginanjarpr.siapnonmvp.models.ServerResponseGet;
import com.example.ginanjarpr.siapnonmvp.models.Tiket;
import com.example.ginanjarpr.siapnonmvp.network.ApiClient;
import com.example.ginanjarpr.siapnonmvp.network.RequestInterface;
import com.example.ginanjarpr.siapnonmvp.utils.CDialog;
import com.example.ginanjarpr.siapnonmvp.utils.Constants;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class DiterimaFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView recyclerviewAduanDiterima;

    TextView txtTidakDitemukan;

    private SharedPreferences pref;

    private RequestInterface requestInterface;
    List<Tiket> tiketList;
    private RecyclerView.Adapter mAdapterTiket;
    public String rolespref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_diterima, container, false);

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

                    recyclerviewAduanDiterima.animate()
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

                                refreshDataDiterimaPool();

                            } else {
                                refreshDataDiterimaSKPD();

                            }

                            mSwipeRefreshLayout.setRefreshing(false);

                            recyclerviewAduanDiterima.animate()
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

        recyclerviewAduanDiterima = (RecyclerView) rootView.findViewById(R.id.recyclerviewAduanBelumDiterima);
        recyclerviewAduanDiterima.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerviewAduanDiterima.setLayoutManager(mLayoutManager);

        recyclerviewAduanDiterima.setAlpha(0f);
        recyclerviewAduanDiterima.setTranslationX(-200f);
        recyclerviewAduanDiterima.setTranslationY(0);
        recyclerviewAduanDiterima.setVisibility(View.VISIBLE);

        recyclerviewAduanDiterima.animate()
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

            refreshDataDiterimaPool();

        } else {
            refreshDataDiterimaSKPD();

        }

        return rootView;
    }

    public void setAdapterRecycler(RecyclerView.Adapter adapters){

        recyclerviewAduanDiterima.setAdapter(adapters);

    }

    public void showDialogKonfirmasiKembalikanSKPD(Context context, final String no_tiket){

        int widthinPixels = (int) getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogKembaliAduan = CDialog.createDialog(getActivity(), R.layout.d_confirm_hapus_terima_aduan, widthinPixels, heightinPixels);

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
                kembaliTiket(view.getContext(), no_tiket);
//                showDialogTelahDikirim();
            }
        });

    }

    public void showDialogKonfirmasiKelompok(Context context, final String no_tiket, final String departemen){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogKirimAduan = CDialog.createDialog(context, R.layout.d_confirm_hapus_terima_aduan, widthinPixels, heightinPixels);

        dialogKirimAduan.show();

        final ImageView imageTanda = (ImageView) dialogKirimAduan.getWindow().findViewById(R.id.imageTanda);
        final TextView textInfoKonfirmasi = (TextView) dialogKirimAduan.getWindow().findViewById(R.id.txtInfoKonfirmasi);
        final TextView textDetailKonfirmasi = (TextView) dialogKirimAduan.getWindow().findViewById(R.id.txtDetailKonfirmasi);
        final TextView textBtnKonfirm = (TextView) dialogKirimAduan.getWindow().findViewById(R.id.txtBtnKonfirm);
        final ImageView logoBtnKonfirm = (ImageView) dialogKirimAduan.getWindow().findViewById(R.id.logoBtnKonfirm);

        imageTanda.setImageResource(R.drawable.ic_view_carousel);
        textInfoKonfirmasi.setText("Pengelompokan Aduan");
        textDetailKonfirmasi.setText("Anda yakin akan mengelompokkan\naduan dengan nomor\n"+no_tiket+" ?");
        textBtnKonfirm.setText("Submit");
        logoBtnKonfirm.setImageResource(R.drawable.ic_done_all_white);

        LinearLayout btnCancel = (LinearLayout) dialogKirimAduan.findViewById(R.id.btnCancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKirimAduan.dismiss();
            }
        });

        LinearLayout btnKirim = (LinearLayout) dialogKirimAduan.findViewById(R.id.btnDeleteDialog);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKirimAduan.dismiss();
                kelompokkanTiket(view.getContext(), no_tiket, departemen);

            }
        });

    }

    public void showDialogKonfirmasiJawab(Context context, final String no_tiket, final String isi){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogJawabAduan = CDialog.createDialog(context, R.layout.d_confirm_hapus_terima_aduan, widthinPixels, heightinPixels);

        dialogJawabAduan.show();

        final ImageView imageTanda = (ImageView) dialogJawabAduan.getWindow().findViewById(R.id.imageTanda);
        final TextView textInfoKonfirmasi = (TextView) dialogJawabAduan.getWindow().findViewById(R.id.txtInfoKonfirmasi);
        final TextView textDetailKonfirmasi = (TextView) dialogJawabAduan.getWindow().findViewById(R.id.txtDetailKonfirmasi);
        final TextView textBtnKonfirm = (TextView) dialogJawabAduan.getWindow().findViewById(R.id.txtBtnKonfirm);
        final ImageView logoBtnKonfirm = (ImageView) dialogJawabAduan.getWindow().findViewById(R.id.logoBtnKonfirm);

        imageTanda.setImageResource(R.drawable.ic_send_large);
        textInfoKonfirmasi.setText("Jawab Aduan");
        textDetailKonfirmasi.setText("Anda yakin akan menjawab\naduan dengan nomor\n"+no_tiket+" ?");
        textBtnKonfirm.setText("Jawab");
        logoBtnKonfirm.setImageResource(R.drawable.ic_send_white);

        LinearLayout btnCancel = (LinearLayout) dialogJawabAduan.findViewById(R.id.btnCancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogJawabAduan.dismiss();
            }
        });

        LinearLayout btnKirim = (LinearLayout) dialogJawabAduan.findViewById(R.id.btnDeleteDialog);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogJawabAduan.dismiss();
                jawabTiket(view.getContext(), no_tiket, isi);

            }
        });

    }

    public void showDialogKonfirmasiKirim(Context context, final String no_tiket, final String departemen){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogKirimAduan = CDialog.createDialog(context, R.layout.d_confirm_hapus_terima_aduan, widthinPixels, heightinPixels);

        dialogKirimAduan.show();

        final ImageView imageTanda = (ImageView) dialogKirimAduan.getWindow().findViewById(R.id.imageTanda);
        final TextView textInfoKonfirmasi = (TextView) dialogKirimAduan.getWindow().findViewById(R.id.txtInfoKonfirmasi);
        final TextView textDetailKonfirmasi = (TextView) dialogKirimAduan.getWindow().findViewById(R.id.txtDetailKonfirmasi);
        final TextView textBtnKonfirm = (TextView) dialogKirimAduan.getWindow().findViewById(R.id.txtBtnKonfirm);
        final ImageView logoBtnKonfirm = (ImageView) dialogKirimAduan.getWindow().findViewById(R.id.logoBtnKonfirm);

        imageTanda.setImageResource(R.drawable.ic_send_large);
        textInfoKonfirmasi.setText("Pengiriman Aduan");
        textDetailKonfirmasi.setText("Anda yakin akan mengirimkan\naduan dengan nomor\n"+no_tiket+" ?");
        textBtnKonfirm.setText("Kirim");
        logoBtnKonfirm.setImageResource(R.drawable.ic_send_white);

        LinearLayout btnCancel = (LinearLayout) dialogKirimAduan.findViewById(R.id.btnCancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKirimAduan.dismiss();
            }
        });

        LinearLayout btnKirim = (LinearLayout) dialogKirimAduan.findViewById(R.id.btnDeleteDialog);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKirimAduan.dismiss();
                kirimTiket(view.getContext(), no_tiket, departemen);

            }
        });

    }

    public void showDialogKirim(Context context, final String no_tiket){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeightInput);

        final Dialog dialogKirimAduan = CDialog.createDialog(context, R.layout.d_confirm_isi_pengiriman, widthinPixels, heightinPixels);

        dialogKirimAduan.show();

        final TextView textBtnKonfirm = (TextView) dialogKirimAduan.getWindow().findViewById(R.id.txtBtnKonfirm);
        final ImageView logoBtnKonfirm = (ImageView) dialogKirimAduan.getWindow().findViewById(R.id.logoBtnKonfirm);
        final Spinner spns = (Spinner) dialogKirimAduan.getWindow().findViewById(R.id.spinnerDepartemen);

        textBtnKonfirm.setText("Submit");
        logoBtnKonfirm.setImageResource(R.drawable.ic_done_all_white);

        LinearLayout btnCancel = (LinearLayout) dialogKirimAduan.findViewById(R.id.btnCancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKirimAduan.dismiss();
            }
        });

        LinearLayout btnKirim = (LinearLayout) dialogKirimAduan.findViewById(R.id.btnDeleteDialog);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKirimAduan.dismiss();
                showDialogKonfirmasiKirim(view.getContext(), no_tiket, spns.getSelectedItem().toString());

            }
        });

    }

    public void showDialogKelompokkan(Context context, final String no_tiket){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeightInput);

        final Dialog dialogKelompokAduan = CDialog.createDialog(context, R.layout.d_confirm_isi_pengelompokkan, widthinPixels, heightinPixels);

        dialogKelompokAduan.show();

        final Spinner spns = (Spinner) dialogKelompokAduan.getWindow().findViewById(R.id.spinnerKategori);

        LinearLayout btnCancel = (LinearLayout) dialogKelompokAduan.findViewById(R.id.btnCancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKelompokAduan.dismiss();
            }
        });

        LinearLayout btnKirim = (LinearLayout) dialogKelompokAduan.findViewById(R.id.btnDeleteDialog);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKelompokAduan.dismiss();
                showDialogKonfirmasiKelompok(view.getContext(), no_tiket, spns.getSelectedItem().toString());

            }
        });

    }

    public void showDialogSuksesKirim(Context context, String no_tiket, String keterangan){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogSuksesKirim = CDialog.createDialog(context, R.layout.d_selesai_hapus_terima, widthinPixels, heightinPixels);
        dialogSuksesKirim.show();

        TextView txtLabel = (TextView) dialogSuksesKirim.findViewById(R.id.labelRole);

        final TextView txtDetailkon = (TextView) dialogSuksesKirim.findViewById(R.id.txtDetailKonfirmasi);
        final TextView txtNotiket = (TextView) dialogSuksesKirim.findViewById(R.id.txtNoTiket);
        txtNotiket.setText(no_tiket);
        txtDetailkon.setText(keterangan);

        pref = getDefaultSharedPreferences(context);
        rolespref = pref.getString(Constants.ROLES, "").toString();

        if(rolespref.equalsIgnoreCase("2")){
            txtLabel.setText("Admin SKPD");
            txtLabel.setBackgroundResource(R.drawable.shape6);
        }

        dialogSuksesKirim.show();

        LinearLayout btnCancel = (LinearLayout) dialogSuksesKirim.findViewById(R.id.btnDismissDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSuksesKirim.dismiss();
                TiketActivity tikets = new TiketActivity();
                tikets.initViewPager();
            }
        });


    }

    public void showDialogJawabAduan(final Context context, final String no_tiket){

        int widthinPixels = (int) context.getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) context.getResources().getDimension(R.dimen.ModalHeightInput);

        final Dialog dialogJawabAduan = CDialog.createDialog(context, R.layout.d_confirm_jawab, widthinPixels, heightinPixels);
        dialogJawabAduan.show();

        final EditText spns = (EditText) dialogJawabAduan.getWindow().findViewById(R.id.et_jawabanAduan);

        LinearLayout btnCancel = (LinearLayout) dialogJawabAduan.findViewById(R.id.btnCancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogJawabAduan.dismiss();
            }
        });

        LinearLayout btnKirim = (LinearLayout) dialogJawabAduan.findViewById(R.id.btnDeleteDialog);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogJawabAduan.dismiss();
                showDialogKonfirmasiJawab(view.getContext(), no_tiket, spns.getText().toString());

            }
        });

    }

    public void showDialogSuksesKembalikan(Context context, String no_tiket){

        int widthinPixels = (int) getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogSuksesKembalikan = CDialog.createDialog(getActivity(), R.layout.d_selesai_kembalikan_terima, widthinPixels, heightinPixels);

        final TextView txtNotiket = (TextView) dialogSuksesKembalikan.findViewById(R.id.txtNoTiket);
        txtNotiket.setText(no_tiket);

        dialogSuksesKembalikan.show();

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

        int widthinPixels = (int) getResources().getDimension(R.dimen.ModalWidth);
        int heightinPixels = (int) getResources().getDimension(R.dimen.ModalHeight);

        final Dialog dialogSuksesTerimaSKPD = CDialog.createDialog(getActivity(), R.layout.d_selesai_kembalikan_terima, widthinPixels, heightinPixels);

        final TextView txtdetailKon = (TextView) dialogSuksesTerimaSKPD.findViewById(R.id.txtDetailKonfirmasi);
        final TextView txtNotiket = (TextView) dialogSuksesTerimaSKPD.findViewById(R.id.txtNoTiket);
        txtNotiket.setText(no_tiket);
        txtdetailKon.setText("Telah Diterima");

        dialogSuksesTerimaSKPD.show();

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

    public void refreshDataDiterimaPool() {

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        final Call<ServerResponseGet> tiketCall = requestInterface.getTiketPoolDiterima();
        tiketCall.enqueue(new Callback<ServerResponseGet>() {
            @Override
            public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                    response) {

                if (response.body() != null) {
                    tiketList = response.body().getListDataTiket();

                    mAdapterTiket = new TiketAdapterDiterima(tiketList);
                    setAdapterRecycler(mAdapterTiket);
                }else {

                    tiketList = Collections.emptyList();
                    mAdapterTiket = new TiketAdapterDiterima(tiketList);
                    setAdapterRecycler(mAdapterTiket);

                    showTidakDitemukan();

                }



            }

            @Override
            public void onFailure(Call<ServerResponseGet> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }

        });

    }

    public void refreshDataDiterimaSKPD() {

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        final Call<ServerResponseGet> skpdCall = requestInterface.getTiketSKPDDiterima();
        skpdCall.enqueue(new Callback<ServerResponseGet>() {
            @Override
            public void onResponse(Call<ServerResponseGet> call, Response<ServerResponseGet>
                    response) {

                if (response.body() != null) {
                    hideTidakDitemukan();
                    tiketList = response.body().getListDataTiket();
                    mAdapterTiket = new TiketAdapterDiterima(tiketList);
                    setAdapterRecycler(mAdapterTiket);
                } else {

                    tiketList = Collections.emptyList();
                    mAdapterTiket = new TiketAdapterDiterima(tiketList);
                    setAdapterRecycler(mAdapterTiket);

                    showTidakDitemukan();

                }


            }

            @Override
            public void onFailure(Call<ServerResponseGet> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }

        });

    }

    public void kembaliTiket(final Context context, final String no_tiket){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        pref = getDefaultSharedPreferences(context);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        tiket.setUsername(pref.getString("username","").toString());
        ServerRequest request = new ServerRequest();
        request.setOperation("kembaliSKPD");
        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {


                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    showDialogSuksesKembalikan(context, no_tiket);
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

    public void kirimTiket(final Context context, final String no_tiket, final String departemen){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        pref = getDefaultSharedPreferences(context);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        tiket.setUsername(pref.getString("username","").toString());
        tiket.setDepartemen(departemen);
        ServerRequest request = new ServerRequest();

        rolespref = pref.getString(Constants.ROLES, "").toString();


        request.setOperation("belumTerimaSKPD");

        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    if (rolespref.equalsIgnoreCase("1")) {

                        showDialogSuksesKirim(context, no_tiket, "Telah Dikirim");

                    } else {

                        showDialogSuksesTerimaSKPD(context, no_tiket);

                    }

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

    public void kelompokkanTiket(final Context context, final String no_tiket, final String kategori){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        pref = getDefaultSharedPreferences(context);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        tiket.setUsername(pref.getString("username","").toString());
        tiket.setTopik_aduan(kategori);
        ServerRequest request = new ServerRequest();

        rolespref = pref.getString(Constants.ROLES, "").toString();


        request.setOperation("kategoriAduan");

        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    showDialogSuksesKirim(context, no_tiket, "Telah Dikelompokkan");

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

    public void jawabTiket(final Context context, final String no_tiket, final String isi){

        requestInterface = ApiClient.getClient().create(RequestInterface.class);

        pref = getDefaultSharedPreferences(context);

        Tiket tiket = new Tiket();
        tiket.setNo_tiket(no_tiket);
        tiket.setUsername(pref.getString("username","").toString());
        tiket.setIsi(isi);
        ServerRequest request = new ServerRequest();

        rolespref = pref.getString(Constants.ROLES, "").toString();


        request.setOperation("jawabTiket");

        request.setTiket(tiket);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    showDialogSuksesKirim(context, no_tiket, "Telah Dijawab");


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
