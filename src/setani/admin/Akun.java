/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.admin;

import java.security.MessageDigest;

/**
 *
 * @author TheBrokenMaster
 */
public class Akun {
    private int idAkun, role, status;
    private String username, password, nama, nomerTelepon, alamat;

    public Akun(int idAkun, int role, int status, String username, String password, String nama, String nomerTelepon, String alamat) {
        this.idAkun = idAkun;
        this.role = role;
        this.status = status;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.nomerTelepon = nomerTelepon;
        this.alamat = alamat;
    }

    public int getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(int idAkun) {
        this.idAkun = idAkun;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomerTelepon() {
        return nomerTelepon;
    }

    public void setNomerTelepon(String nomerTelepon) {
        this.nomerTelepon = nomerTelepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    
}
