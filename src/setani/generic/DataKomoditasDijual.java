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
public class DataKomoditasDijual {
    int idKomoditasDijual, berat, idTransaksi, idHasilPanen;

    public DataKomoditasDijual(int idKomoditasDijual, int berat, int idTransaksi, int idHasilPanen) {
        this.idKomoditasDijual = idKomoditasDijual;
        this.berat = berat;
        this.idTransaksi = idTransaksi;
        this.idHasilPanen = idHasilPanen;
    }

    public int getIdKomoditasDijual() {
        return idKomoditasDijual;
    }

    public int getBerat() {
        return berat;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public int getIdHasilPanen() {
        return idHasilPanen;
    }
    
    
}
