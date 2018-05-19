package com.example.ginanjarpr.siapnonmvp.adapter;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ginanjarpr.siapnonmvp.R;
import com.example.ginanjarpr.siapnonmvp.function.tiket.diterima.DiterimaFragment;
import com.example.ginanjarpr.siapnonmvp.models.Tiket;
import com.example.ginanjarpr.siapnonmvp.utils.Constants;

import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class TiketAdapterDiterima extends RecyclerView.Adapter<TiketAdapterDiterima.MyViewHolder> {

    private List<Tiket> dataTiket;
    int position;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView counterTiket, nomorTiket, namaPengadu,
                alamatPengadu, noTeleponPengadu, kategoriAduan, isiAduan;

        public SharedPreferences pref;
        public String rolespref;
        public Button btnKelompok, btnKirim, btnJawab;

        public MyViewHolder(View view) {
            super(view);



            counterTiket = (TextView) view.findViewById(R.id.counterTiket);
            nomorTiket = (TextView) view.findViewById(R.id.txtNomorTiket);
            namaPengadu = (TextView) view.findViewById(R.id.txtNamaPengadu);
            alamatPengadu = (TextView) view.findViewById(R.id.txtAlamatPengadu);
            noTeleponPengadu = (TextView) view.findViewById(R.id.txtNomorTelepon);
            kategoriAduan = (TextView) view.findViewById(R.id.txtKategoriTiket);
            isiAduan = (TextView) view.findViewById(R.id.txtIsiTiket);
            btnKelompok = (Button) view.findViewById(R.id.btnKelompok);
            btnKirim = (Button) view.findViewById(R.id.btnKirim);
            btnJawab = (Button) view.findViewById(R.id.btnJawab);

            pref = getDefaultSharedPreferences(view.getContext());
            rolespref = pref.getString(Constants.ROLES,"").toString();

            if(rolespref.equalsIgnoreCase("2")) {
                btnKelompok.setVisibility(View.GONE);
                btnKirim.setVisibility(View.GONE);
                btnJawab.setVisibility(View.VISIBLE);
            }else{

            }




        }

    }


    public TiketAdapterDiterima(List<Tiket> dataTiket) {
        this.dataTiket = dataTiket;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_tiket, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Tiket tiket = dataTiket.get(position);

        holder.counterTiket.setText(String.valueOf(position+1));
        holder.nomorTiket.setText(tiket.getNo_tiket());
        holder.namaPengadu.setText(tiket.getNama_pengadu());
        holder.noTeleponPengadu.setText(tiket.getNo_telepon_pengadu());
        holder.alamatPengadu.setText(tiket.getAlamat_pengadu());
        holder.kategoriAduan.setText(tiket.getTopik_aduan());
        holder.isiAduan.setText(tiket.getIsi_aduan());

        if(tiket.getStatus_jawab().equalsIgnoreCase("s")){
            holder.btnJawab.setEnabled(false);
            holder.btnJawab.setBackgroundTintList(ContextCompat.getColorStateList(holder.btnJawab.getContext(), R.color.colorUnselect));
//            holder.btnJawab.setBackgroundTint(0xFFFF0000);
        }

        final DiterimaFragment fragments = new DiterimaFragment();

        holder.btnKelompok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragments.showDialogKelompokkan(view.getContext(), tiket.getNo_tiket().toString());

            }
        });

        holder.btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragments.showDialogKirim(view.getContext(), tiket.getNo_tiket().toString());

            }
        });



        holder.btnJawab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragments.showDialogJawabAduan(view.getContext(), tiket.getNo_tiket().toString());

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataTiket.size();
    }

    public static int dpToPx(int dp) {
        if(Resources.getSystem().getDisplayMetrics().density>=2){
            return (int) (dp * 2);
        }
        else {
            return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
        }
    }


}

