package com.example.ginanjarpr.siapnonmvp.adapter;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ginanjarpr.siapnonmvp.R;
import com.example.ginanjarpr.siapnonmvp.function.tiket.belumditerima.BelumDiterimaFragment;
import com.example.ginanjarpr.siapnonmvp.models.Tiket;
import com.example.ginanjarpr.siapnonmvp.utils.Constants;

import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class TiketAdapterBelumDiterima extends RecyclerView.Adapter<TiketAdapterBelumDiterima.MyViewHolder> {

    private List<Tiket> dataTiket;
    int position;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView counterTiket, nomorTiket, namaPengadu,
                alamatPengadu, noTeleponPengadu, kategoriAduan, isiAduan;

        public SharedPreferences pref;
        public String rolespref;
        public Button btnHapus, btnTerima;

        public MyViewHolder(View view) {
            super(view);



            counterTiket = (TextView) view.findViewById(R.id.counterTiket);
            nomorTiket = (TextView) view.findViewById(R.id.txtNomorTiket);
            namaPengadu = (TextView) view.findViewById(R.id.txtNamaPengadu);
            alamatPengadu = (TextView) view.findViewById(R.id.txtAlamatPengadu);
            noTeleponPengadu = (TextView) view.findViewById(R.id.txtNomorTelepon);
            kategoriAduan = (TextView) view.findViewById(R.id.txtKategoriTiket);
            isiAduan = (TextView) view.findViewById(R.id.txtIsiTiket);
            btnHapus = (Button) view.findViewById(R.id.btnHapus);
            btnTerima = (Button) view.findViewById(R.id.btnTerima);

            pref = getDefaultSharedPreferences(view.getContext());
            rolespref = pref.getString(Constants.ROLES,"").toString();

            if(rolespref.equalsIgnoreCase("2")) {
                btnHapus.setText("Kembalikan");
                btnHapus.setWidth(dpToPx(180));
            }else{

            }


        }

//        @Override
//        public void onClick(final View view) {
//            position = getPosition();

//            final Dialog dialogS = new Dialog(view.getContext());
//            dialogS.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialogS.setContentView(R.layout.m_dialog_jasa_air_kapal);
//
//            TextView statusBayarMerahD = (TextView) dialogS.findViewById(R.id.StatusPembayaranMerahMDAir);
//            TextView statusBayarBiruD = (TextView) dialogS.findViewById(R.id.StatusPembayaranBiruMDAir);
//            TextView statusBayarOrangeD = (TextView) dialogS.findViewById(R.id.StatusPembayaranORangeMDAir);
//
//            TextView statusMohonMerahD = (TextView) dialogS.findViewById(R.id.StatusPemohonanMerahMDAir);
//            TextView statusMohonBiruD = (TextView) dialogS.findViewById(R.id.StatusPemohonanBiruMDAir);
//            TextView statusMohonOrangeD = (TextView) dialogS.findViewById(R.id.StatusPemohonanOrangeMDAir);
//
//            Button buttonDismiss = (Button) dialogS.findViewById(R.id.VDismissMJasaAirKapal);
//
//            TextView textDetailPPK = (TextView) dialogS.findViewById(R.id.VDetailPMHMJasaAirKapal);
//            TextView textDetailNamaKapal = (TextView) dialogS.findViewById(R.id.kapalDMAir);
//            TextView textDetailPengguna = (TextView) dialogS.findViewById(R.id.penggunaJasaMAirDetail);
//            TextView textDetailGt = (TextView) dialogS.findViewById(R.id.GTDMAir);
//            TextView textDetailLoa = (TextView) dialogS.findViewById(R.id.LOADMAir);
//            TextView textDetailLokasi = (TextView) dialogS.findViewById(R.id.lokasiIsiMDAir);
//            TextView textDetailVolume = (TextView) dialogS.findViewById(R.id.volumeDialogMAir);
//            TextView textDetailWaktuIsi = (TextView) dialogS.findViewById(R.id.waktuDetailMAir);
//            TextView textDetailWaktu = (TextView) dialogS.findViewById(R.id.valueDateDMAir);
//
//
//            if(dataAir.get(position).getStatus_pembayaran().toString().equalsIgnoreCase("Belum Lunas")){
//                statusBayarBiruD.setVisibility(View.INVISIBLE);
//                statusBayarOrangeD.setVisibility(View.INVISIBLE);
//                statusBayarMerahD.setVisibility(View.VISIBLE);
//            } else if (dataAir.get(position).getStatus_pembayaran().toString().equalsIgnoreCase("Lunas")){
//                statusBayarMerahD.setVisibility(View.INVISIBLE);
//                statusBayarOrangeD.setVisibility(View.INVISIBLE);
//                statusBayarBiruD.setVisibility(View.VISIBLE);
//            }
//
//            if(dataAir.get(position).getStatus_permohonan().toString().equalsIgnoreCase("Menunggu")){
//                statusMohonBiruD.setVisibility(View.INVISIBLE);
//                statusMohonMerahD.setVisibility(View.INVISIBLE);
//                statusMohonOrangeD.setVisibility(View.VISIBLE);
//            } else if (dataAir.get(position).getStatus_permohonan().toString().equalsIgnoreCase("Ditetapkan")){
//                statusMohonMerahD.setVisibility(View.INVISIBLE);
//                statusMohonOrangeD.setVisibility(View.INVISIBLE);
//                statusMohonBiruD.setVisibility(View.VISIBLE);
//            }
//
//            textDetailPPK.setText(dataAir.get(position).getAlat() + " | PMH " + dataAir.get(position).getNo_ppk());
//            textDetailWaktuIsi.setText(dataAir.get(position).getWaktu());
//            textDetailNamaKapal.setText(dataAir.get(position).getNama_kapal());
//            textDetailVolume.setText(dataAir.get(position).getVolume());
//            textDetailGt.setText(dataAir.get(position).getGt());
//            textDetailLoa.setText(dataAir.get(position).getLoa());
//            textDetailLokasi.setText(dataAir.get(position).getLokasi_pengisian());
//            textDetailPengguna.setText(dataAir.get(position).getPengguna_jasa());
//            textDetailWaktu.setText(dataAir.get(position).getWaktu());
//
//            buttonDismiss.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialogS.dismiss();
//                }
//            });
//
//            int widthinPixels = (int) view.getResources().getDimension(R.dimen.MDAirWidth);
//            widthinPixels = dpToPx(widthinPixels);
//
//            int heightinPixels = (int) view.getResources().getDimension(R.dimen.MDAirHeight);
//            heightinPixels = dpToPx(heightinPixels);
//
//            dialogS.getWindow().setLayout(widthinPixels, heightinPixels);
//            dialogS.show();
//
//        }

    }


    public TiketAdapterBelumDiterima(List<Tiket> dataTiket) {
        this.dataTiket = dataTiket;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_tiket_terima, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Tiket tiket = dataTiket.get(position);
//
//        if(dataTiket.get(position).getAlamat_pengadu().toString().equalsIgnoreCase("Belum Lunas")){
//            holder.statusBayarBiru.setVisibility(View.INVISIBLE);
//            holder.statusBayarOrange.setVisibility(View.INVISIBLE);
//            holder.statusBayarMerah.setVisibility(View.VISIBLE);
//        } else if (dataTiket.get(position).getAlamat_pengadu().toString().equalsIgnoreCase("Lunas")){
//            holder.statusBayarMerah.setVisibility(View.INVISIBLE);
//            holder.statusBayarOrange.setVisibility(View.INVISIBLE);
//            holder.statusBayarBiru.setVisibility(View.VISIBLE);
//        }
//
//        if(dataTiket.get(position).getAlamat_pengadu().toString().equalsIgnoreCase("Menunggu")){
//            holder.statusMohonBiru.setVisibility(View.INVISIBLE);
//            holder.statusMohonMerah.setVisibility(View.INVISIBLE);
//            holder.statusMohonOrange.setVisibility(View.VISIBLE);
//        } else if (dataTiket.get(position).getAlamat_pengadu().toString().equalsIgnoreCase("Ditetapkan")){
//            holder.statusMohonMerah.setVisibility(View.INVISIBLE);
//            holder.statusMohonOrange.setVisibility(View.INVISIBLE);
//            holder.statusMohonBiru.setVisibility(View.VISIBLE);
//        }

        holder.counterTiket.setText(String.valueOf(position+1));
        holder.nomorTiket.setText(tiket.getNo_tiket());
        holder.namaPengadu.setText(tiket.getNama_pengadu());
        holder.noTeleponPengadu.setText(tiket.getNo_telepon_pengadu());
        holder.alamatPengadu.setText(tiket.getAlamat_pengadu());
        holder.kategoriAduan.setText(tiket.getTopik_aduan());
        holder.isiAduan.setText(tiket.getIsi_aduan());

        final BelumDiterimaFragment fragments = new BelumDiterimaFragment();

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.rolespref.equalsIgnoreCase("2")) {
                    fragments.showDialogKonfirmasiKembalikanSKPD(view.getContext(), tiket.getNo_tiket().toString());
                }else{
                    fragments.showDialogKonfirmasiHapusPool(view.getContext(), tiket.getNo_tiket().toString());
                }

            }
        });

        holder.btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    fragments.showDialogKonfirmasiTerima(view.getContext(), tiket.getNo_tiket().toString());

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
