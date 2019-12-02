/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.generic;

/**
 *
 * @author user
 */
public class DataPanen {
     private int id_hasilpanen, id_akun, berat_komoditas_panen,harga_jual_perkilo;
     private String nama_komoditas_panen, tipe_komoditas_panen,tanggal_panen;

    public DataPanen(int id_hasilpanen, int id_akun, int berat_komoditas_panen, String nama_komoditas_panen, String tipe_komoditas_panen, int harga_jual_perkilo, String tanggal_panen) {
        this.id_hasilpanen = id_hasilpanen;
        this.id_akun = id_akun;
        this.berat_komoditas_panen = berat_komoditas_panen;
        this.nama_komoditas_panen = nama_komoditas_panen;
        this.tipe_komoditas_panen = tipe_komoditas_panen;
        this.harga_jual_perkilo = harga_jual_perkilo;
        this.tanggal_panen = tanggal_panen;
    }
    
    public DataPanen(){
        
    }

    public void setId_hasilpanen(int id_hasilpanen) {
        this.id_hasilpanen = id_hasilpanen;
    }

    public void setId_akun(int id_akun) {
        this.id_akun = id_akun;
    }

    public void setBerat_komoditas_panen(int berat_komoditas_panen) {
        this.berat_komoditas_panen = berat_komoditas_panen;
    }

    public void setHarga_jual_perkilo(int harga_jual_perkilo) {
        this.harga_jual_perkilo = harga_jual_perkilo;
    }

    public void setNama_komoditas_panen(String nama_komoditas_panen) {
        this.nama_komoditas_panen = nama_komoditas_panen;
    }

    public void setTipe_komoditas_panen(String tipe_komoditas_panen) {
        this.tipe_komoditas_panen = tipe_komoditas_panen;
    }

    public void setTanggal_panen(String tanggal_panen) {
        this.tanggal_panen = tanggal_panen;
    }
    
    

    public int getHarga_jual_perkilo() {
        return harga_jual_perkilo;
    }

    public String getTanggal_panen() {
        return tanggal_panen;
    }
    public int getId_hasilpanen() {
        return id_hasilpanen;
    }

    public int getId_akun() {
        return id_akun;
    }

    public int getBerat_komoditas_panen() {
        return berat_komoditas_panen;
    }

    public String getNama_komoditas_panen() {
        return nama_komoditas_panen;
    }

    public String getTipe_komoditas_panen() {
        return tipe_komoditas_panen;
    }
}