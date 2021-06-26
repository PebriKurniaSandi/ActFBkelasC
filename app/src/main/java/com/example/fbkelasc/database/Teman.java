package com.example.fbkelasc.database;

import java.io.Serializable;

public class Teman implements Serializable {
    private String key, kode, nama;

    public Teman(){}

    public String getKode() {
        return kode;
    }

    public void setKode(String kd) {
        this.kode = kd;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " "+kode+"\n"+" "+nama;
    }

    public Teman(String kd,String nm){
        kode = kd;
        nama = nm;
    }
}
