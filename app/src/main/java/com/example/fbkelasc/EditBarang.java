package com.example.testbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbkelasc.database.Teman;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditBarang extends AppCompatActivity {

    TextView tvKey;
    EditText edBarang, edKode;
    Button btnEdit;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String key, kode, barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_barang);

        edBarang = findViewById(R.id.edBarang);
        edKode = findViewById(R.id.edKode);
        tvKey = findViewById(R.id.tv_key);
        btnEdit = findViewById(R.id.btEdit);

        Bundle bundle = this.getIntent().getExtras();
        key = bundle.getString("kunci1");
        barang = bundle.getString("kunci2");
        kode = bundle.getString("kunci3");

        // menampilkan isi ke layout EditText TextView
        tvKey.setText(key);
        edBarang.setText(barang);
        edKode.setText(kode);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBarang(new Barang(edKode.getText().toString(), edBarang.getText().toString()));
            }
        });
    }
    public void editBarang(Teman barang){
        databaseReference.child("Barang")
                .child(key)
                .setValue(barang)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditBarang.this, "Data Berhasil di Ubah", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}