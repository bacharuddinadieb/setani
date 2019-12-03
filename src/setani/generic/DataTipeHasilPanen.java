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
public class DataTipeHasilPanen {

    int idTipeHasilPanen;
    String tipeHasilPanen;

    public DataTipeHasilPanen(int idTipeHasilPanen, String tipeHasilPanen) {
        this.idTipeHasilPanen = idTipeHasilPanen;
        this.tipeHasilPanen = tipeHasilPanen;
    }

    @Override
    public String toString() {
        return this.tipeHasilPanen;
    }

    public int getIdTipeHasilPanen() {
        return idTipeHasilPanen;
    }

    public void setIdTipeHasilPanen(int idTipeHasilPanen) {
        this.idTipeHasilPanen = idTipeHasilPanen;
    }

    public String getTipeHasilPanen() {
        return tipeHasilPanen;
    }

    public void setTipeHasilPanen(String tipeHasilPanen) {
        this.tipeHasilPanen = tipeHasilPanen;
    }

}
