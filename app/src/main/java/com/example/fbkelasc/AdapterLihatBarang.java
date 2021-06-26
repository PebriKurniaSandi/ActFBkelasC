package com.example.testbarang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbkelasc.database.Teman;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterLihatBarang extends RecyclerView.Adapter<AdapterLihatBarang.ViewHolder> {

    private ArrayList<Teman> daftarBarang;
    private Context context;
    private DatabaseReference databaseReference;

    public AdapterLihatBarang(ArrayList<Teman> barangs, Context ctx){
        //inisiasi data var yang akan di gunakan
        daftarBarang = barangs;
        context = ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //inisiasi view, akan menggunakan data str tiap item, viewnya hanya satu textView

        TextView tvTitle;
        ViewHolder(View v){
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_namabarang);

            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
    }

    @Override
    public AdapterLihatBarang.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        //inisiasi ViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLihatBarang.ViewHolder holder, int position) {

        String key, barang, kode;
        key = daftarBarang.get(position).getKey();
        barang = daftarBarang.get(position).getNama();
        kode = daftarBarang.get(position).getKode();

        //menampilkan data pada View
        //final String name = daftarBarang.get(position).getNama();
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //detail data
            }
        });
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.menubarang);
                //untuk del dan update
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.mnEdit:
                                Bundle bundle = new Bundle();
                                bundle.putString("kunci1", key);
                                bundle.putString("kunci2", barang);
                                bundle.putString("kunci3", kode);

                                Intent intent = new Intent(v.getContext(), EditBarang.class);
                                intent.putExtras(bundle);
                                v.getContext().startActivity(intent);
                                break;
                            case R.id.mnHapus:
                                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                                alert.setTitle("Yakin "+ barang + " akan dihapus ?");
                                alert.setMessage("Tekan Ya untuk mengahapus")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DeleteData(key);
                                                Toast.makeText(v.getContext(), "Data "+ barang + " berhasil dihapus", Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(v.getContext(), com.example.testbarang.MainActivity.class);
                                                v.getContext().startActivity(i);
                                            }
                                        })
                                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alertDialog = alert.create();
                                alertDialog.show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
        holder.tvTitle.setText(barang);
    }

    @Override
    public int getItemCount() {
        //mengembalikan jumlah ktem barang
        return daftarBarang.size();
    }

    public void DeleteData(String key){
        if (databaseReference != null){
            databaseReference.child("Barang").child(key).removeValue();
        }
    }
}
