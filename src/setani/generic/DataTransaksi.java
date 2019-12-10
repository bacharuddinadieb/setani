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
public class DataTransaksi {

    int idTransaksi, idPembeli, status;
    String tanggalTransaksi, namaPembeli;

    public DataTransaksi(int idTransaksi, int idPembeli, String tanggalTransaksi) {
        this.idTransaksi = idTransaksi;
        this.idPembeli = idPembeli;
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public DataTransaksi(int idTransaksi, int idPembeli, String tanggalTransaksi, String namaPembeli, int status) {
        this.idTransaksi = idTransaksi;
        this.idPembeli = idPembeli;
        this.tanggalTransaksi = tanggalTransaksi;
        this.namaPembeli = namaPembeli;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public int getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(int idPembeli) {
        this.idPembeli = idPembeli;
    }

    public String getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(String tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

}
