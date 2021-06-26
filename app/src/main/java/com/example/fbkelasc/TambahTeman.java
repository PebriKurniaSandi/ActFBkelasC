package com.example.fbkelasc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fbkelasc.database.Teman;

public class TambahTeman extends AppCompatActivity {

    private DatabaseReference db;
    private Button btSubmit;
    private EditText etKode,etNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_teman);

        etKode =(EditText) findViewById(R.id.editNo);
        etNama =(EditText) findViewById(R.id.editNama);
        btSubmit =(Button) findViewById(R.id.btnOk);

        //ref ke firebase
        db = FirebaseDatabase.getInstance().getReference();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(etKode.getText().toString().isEmpty()) && !(etNama.getText().toString().isEmpty())){
                    submitBrg(new Teman(etKode.getText().toString(),etNama.getText().toString()));
                }else{
                    Toast.makeText(getApplicationContext(),"Data tidak boleh kosong",Toast.LENGTH_LONG).show();
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etKode.getWindowToken(),0);
            }
        });
    }

    public void submitBrg(Teman brg){
        //kirim data ke firebase
        db.child("Barang").push().setValue(brg).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etKode.setText("");
                etNama.setText("");
                Toast.makeText(getApplicationContext(),"Data Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
            }
        });
    }

    public static Intent getActIntent(Activity activity){
        return new Intent(activity,TambahTeman.class);
    }
}