/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.generic;

/**
 *
 * @author matohdev
 */
public class DataTransaksiPetani {
    int id_komoditas_dijual, berat, id_transaksi, id_hasil_panen, harga_jual_kg;
    String nama_komoditas_panen;

    public DataTransaksiPetani(int id_komoditas_dijual, int berat, int id_transaksi, int id_hasil_panen, int harga_jual_kg, String nama_komoditas_panen) {
        this.id_komoditas_dijual = id_komoditas_dijual;
        this.berat = berat;
        this.id_transaksi = id_transaksi;
        this.id_hasil_panen = id_hasil_panen;
        this.harga_jual_kg = harga_jual_kg;
        this.nama_komoditas_panen = nama_komoditas_panen;
    }

    public int getId_komoditas_dijual() {
        return id_komoditas_dijual;
    }

    public void setId_komoditas_dijual(int id_komoditas_dijual) {
        this.id_komoditas_dijual = id_komoditas_dijual;
    }

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public int getId_hasil_panen() {
        return id_hasil_panen;
    }

    public void setId_hasil_panen(int id_hasil_panen) {
        this.id_hasil_panen = id_hasil_panen;
    }

    public int getHarga_jual_kg() {
        return harga_jual_kg;
    }

    public void setHarga_jual_kg(int harga_jual_kg) {
        this.harga_jual_kg = harga_jual_kg;
    }

    public String getNama_komoditas_panen() {
        return nama_komoditas_panen;
    }

    public void setNama_komoditas_panen(String nama_komoditas_panen) {
        this.nama_komoditas_panen = nama_komoditas_panen;
    }

    
}
