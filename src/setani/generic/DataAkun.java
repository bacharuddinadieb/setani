/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.generic;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class DataAkun {
//   public ArrayList<informasiLogin> arrDataLogin = new ArrayList<>();
   private int idAkun, role, status;
   private String nama, username, alamat, nomerTelepon;

    public DataAkun(int idAkun, int role, int status, String nama, String username, String alamat, String nomerTelepon) {
        this.idAkun = idAkun;
        this.role = role;
        this.status = status;
        this.nama = nama;
        this.username = username;
        this.alamat = alamat;
        this.nomerTelepon = nomerTelepon;
    }

    public DataAkun() {
        
    }
    

    public int getStatus() {
        return status;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNomerTelepon() {
        return nomerTelepon;
    }

   

    public int getIdAkun() {
        return idAkun;
    }

    public int getRole() {
        return role;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }
   
    
}
