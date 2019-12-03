/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.pembeli;

import setani.generic.DataPanen;
import setani.petani.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import setani.koneksi.koneksi;
import setani.generic.DataAkun;

/**
 *
 * @author TheBrokenMaster
 */
public class Transaksi extends javax.swing.JFrame {

    /**
     * Creates new form JFrameTambahHasilPanen
     */
    DataAkun infoLogin;
    int idHasilPanen, harga, idPenjual, baris, berat;
    String namaKomoditas;
    MainDashboardPembeli mdp;
    private Connection conn;

    public Transaksi(MainDashboardPembeli mainDashboardPembeli, DataAkun login, int barisKe, DataPanen dataPanen) {
        initComponents();
        conn = koneksi.bukaKoneksi();
        infoLogin = login;
        namaPembeli.setText(infoLogin.getNama());
        namaPembeli.setEditable(false);
        noTelepone.setText(infoLogin.getNomerTelepon());
        noTelepone.setEditable(false);
        idHasilPanen = dataPanen.getId_hasilpanen();
        harga = dataPanen.getHarga_jual_perkilo();
        idPenjual = dataPanen.getId_akun();
        berat = dataPanen.getBerat_komoditas_panen();
        namaKomoditas = dataPanen.getNama_komoditas_panen();
        mdp = mainDashboardPembeli;
        baris = barisKe;
    }

    

    public void loadpanen() {
        if (conn != null) {
            String kueri = "SELECT * FROM tb_hasil_panen INNER JOIN tb_tipe_hasil_panen ON tb_tipe_hasil_panen.id_tipe_hasil_panen = tb_hasil_panen.id_tipe_hasil_panen INNER JOIN tb_akun ON tb_akun.id_akun = tb_hasil_panen.id_akun WHERE id_hasil_panen = '" + idHasilPanen + "'";
            try {
                java.sql.PreparedStatement ps = conn.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id_hasilpanen = rs.getInt("id_hasil_panen");
                    int id_akun = rs.getInt("id_akun");
                    String nama_komoditas_panen = rs.getString("nama_komoditas_panen");
                    String tipe_komoditas_panen = rs.getString("tipe_komoditas_panen");
                    int berat_komoditas_panen = Integer.parseInt(jumlahkg.getText());
                    int harga_jual_perkilo = rs.getInt("harga_jual_kg");
                    String tanggal_panen = rs.getString("tanggal_panen");
                    String petani = rs.getString("nama");
                    DataPanen data = new DataPanen(id_hasilpanen, id_akun, berat_komoditas_panen, nama_komoditas_panen, tipe_komoditas_panen, harga_jual_perkilo, tanggal_panen, petani);
                    mdp.daftarPanenMauDiBeli.add(data);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(MainDashboardPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        namaPembeli = new javax.swing.JTextField();
        noTelepone = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jumlahkg = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jBeli = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        hargatotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Form Pembelian Barang");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Masukan Nama Anda");

        namaPembeli.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        namaPembeli.setFocusable(false);
        namaPembeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaPembeliActionPerformed(evt);
            }
        });

        noTelepone.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        noTelepone.setFocusable(false);
        noTelepone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noTeleponeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Masukan No Telepone Anda");

        jumlahkg.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jumlahkg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jumlahkgKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Jumlah Kg Hasil Panen Yang Akan di Beli");

        jBeli.setBackground(new java.awt.Color(1, 87, 155));
        jBeli.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBeli.setText("Beli");
        jBeli.setPreferredSize(new java.awt.Dimension(125, 30));
        jBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBeliActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(223, 32, 34));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Batal");
        jButton2.setPreferredSize(new java.awt.Dimension(125, 30));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        hargatotal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Harga Total");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(namaPembeli)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                            .addComponent(noTelepone)
                            .addComponent(jumlahkg)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(hargatotal, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(31, 31, 31))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noTelepone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jumlahkg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hargatotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBeliActionPerformed
        // TODO add your handling code here:
        if (Integer.parseInt(jumlahkg.getText()) <= berat) {
            loadpanen();
            int totalBerat = mdp.daftarpanen.get(baris).getBerat_komoditas_panen() - Integer.parseInt(jumlahkg.getText());
            mdp.tampilDataPanen2();
            mdp.daftarpanen.get(baris).setBerat_komoditas_panen(totalBerat);
            mdp.tampilDataPanen();
            setVisible(false);
            dispose();        
        } else {
            JOptionPane.showMessageDialog(null, "Stok komoditas hasil panen '" + namaKomoditas + "' tidak mencukupi, Silahkan pilih sesuai dengan beratnya!", "Pesan", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jBeliActionPerformed

    private void namaPembeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaPembeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaPembeliActionPerformed

    private void noTeleponeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noTeleponeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noTeleponeActionPerformed

    private void jumlahkgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahkgKeyReleased
        // TODO add your handling code here:
        System.out.println(harga);
        int hargaTotal = Integer.parseInt(jumlahkg.getText()) * harga;
        hargatotal.setText(Integer.toString(hargaTotal));
//hargatotal.setText(t);
    }//GEN-LAST:event_jumlahkgKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new JFrameHasilPanen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField hargatotal;
    private javax.swing.JButton jBeli;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jumlahkg;
    private javax.swing.JTextField namaPembeli;
    private javax.swing.JTextField noTelepone;
    // End of variables declaration//GEN-END:variables
}
