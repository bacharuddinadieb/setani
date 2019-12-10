/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.petani;

import setani.admin.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import setani.generic.DataAkun;
import setani.generic.DataPanen;
import setani.koneksi.koneksi;

/**
 *
 * @author TheBrokenMaster
 */
public class JFrameHasilPanen extends javax.swing.JFrame {

    /**
     * Creates new form JFrameTambahHasilPanen
     */
    private Connection conn;
    DataAkun informasilogin;
    MainDashboardPetani mainDashboardPetani;
    DataPanen dataPanen;
    ArrayList<DataPanen> daftarHasilPanen = new ArrayList<>();

    public JFrameHasilPanen(MainDashboardPetani mdp, DataAkun da) {
        initComponents();
        informasilogin = da;
        mainDashboardPetani = mdp;
        conn = koneksi.bukaKoneksi();
        loadDataHasilPanen();
    }

    public JFrameHasilPanen(MainDashboardPetani mda, DataAkun da, DataPanen dp) {
        initComponents();
        informasilogin = da;
        mainDashboardPetani = mda;
        dataPanen = dp;
        jButton1.setText("Perbarui Data");
        conn = koneksi.bukaKoneksi();
        loadDataHasilPanen();

        jTextField1.setText(dp.getNama_komoditas_panen());
        jTextField3.setText(Integer.toString(dp.getBerat_komoditas_panen()));
        jTextField4.setText(Integer.toString(dp.getHarga_jual_perkilo()));
    }

    public void loadDataHasilPanen() {
        if (conn != null) {
            daftarHasilPanen.clear();
            String kueri = "SELECT * FROM tb_hasil_panen INNER JOIN tb_tipe_hasil_panen ON tb_tipe_hasil_panen.id_tipe_hasil_panen = tb_hasil_panen.id_tipe_hasil_panen WHERE tb_hasil_panen.id_akun = 3";
            int i = 0;
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id_hasilpanen = rs.getInt("id_hasil_panen");
                    int id_akun = rs.getInt("id_akun");
                    String nama_komoditas_panen = rs.getString("nama_komoditas_panen");
                    String tipe_komoditas_panen = rs.getString("tipe_komoditas_panen");
                    int berat_komoditas_panen = rs.getInt("berat_komoditas_panen");
                    int harga_jual_perkilo = rs.getInt("harga_jual_kg");
                    int id_tipe_hasil_panen = rs.getInt("id_tipe_hasil_panen");
                    String tanggal_panen = rs.getString("tanggal_panen");
                    DataPanen data = new DataPanen(id_hasilpanen, id_akun, berat_komoditas_panen, nama_komoditas_panen, tipe_komoditas_panen, harga_jual_perkilo, tanggal_panen);
                    data.setId_tipe_hasil_panen(id_tipe_hasil_panen);
                    daftarHasilPanen.add(data);
                    comboNamaHasilPanen.addItem(data.getId_hasilpanen() + "-" + data.getNama_komoditas_panen());
                    if (jButton1.getText().equals("Perbarui Data")) {
                        if (dataPanen.getNama_komoditas_panen().equals(data.getNama_komoditas_panen())) {
                            comboNamaHasilPanen.setSelectedIndex(i);
                        }
                    }
                    i++;
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    private void tambahData(String nama, int berat, int harga, int idTipe, int idAkun) {
        if (conn != null) {
            try {
                String kueri = "INSERT INTO tb_hasil_panen(nama_komoditas_panen, berat_komoditas_panen, harga_jual_kg, id_tipe_hasil_panen, id_akun) VALUES (?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(kueri);
                ps.setString(1, nama);
                ps.setInt(2, berat);
                ps.setInt(3, harga);
                ps.setInt(4, idTipe);
                ps.setInt(5, idAkun);
                int hasil = ps.executeUpdate();
                if (hasil > 0) {
                    JOptionPane.showMessageDialog(this, "Input Berhasil");
                    setVisible(false);
                    dispose();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    void updatepanen(String nama, int berat, int harga, int idTipe, int idAkun, int idHasilPanen) {
        if (conn != null) {
            String kueri = "UPDATE tb_hasil_panen SET nama_komoditas_panen = ?, berat_komoditas_panen = ?, harga_jual_kg = ?, id_tipe_hasil_panen = ?, id_akun = ? WHERE id_hasil_panen = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ps.setString(1, nama);
                ps.setInt(2, berat);
                ps.setInt(3, harga);
                ps.setInt(4, idTipe);
                ps.setInt(5, idAkun);
                ps.setInt(6, idHasilPanen);
                int hasil = ps.executeUpdate();
                if (hasil > 0) {
                    JOptionPane.showMessageDialog(this, "Input Berhasil");
                    setVisible(false);
                    dispose();
                }
            } catch (SQLException ex) {
                System.err.println(ex);
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
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        comboNamaHasilPanen = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(0, 33, 113));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 33, 113));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tambah Data Hasil Panen");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nama Hasil Panen");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Tipe Hasil Panen");

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Berat Hasil Panen");

        jButton1.setBackground(new java.awt.Color(1, 87, 155));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Tambah Data");
        jButton1.setPreferredSize(new java.awt.Dimension(125, 30));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        comboNamaHasilPanen.setMaximumRowCount(10);
        comboNamaHasilPanen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboNamaHasilPanenItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Harga per Kilo");

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(comboNamaHasilPanen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1))))
                .addGap(31, 31, 31))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboNamaHasilPanen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        // TODO add your handling code here:
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.out.println(comboNamaHasilPanen.getSelectedItem());
        int berat = Integer.parseInt(jTextField3.getText());
        if (berat < 0) {
            JOptionPane.showMessageDialog(this, "Tidak Boleh Mines");
            setVisible(false);
            dispose();
        } else {
            int harga = Integer.parseInt(jTextField4.getText());
            int idtipe = 0;
            
            String[] selected = comboNamaHasilPanen.getSelectedItem().toString().split("-");
            for (int i = 0; i < daftarHasilPanen.size(); i++) {
                if (Integer.parseInt(selected[0]) == daftarHasilPanen.get(i).getId_hasilpanen()) {
                    idtipe = daftarHasilPanen.get(i).getId_tipe_hasil_panen();
                }

            }
            
            if (jButton1.getText().equals("Tambah Data")) {
                tambahData(jTextField1.getText(), berat, harga, idtipe, informasilogin.getIdAkun());
            } else {
                updatepanen(jTextField1.getText(), berat, harga, idtipe, informasilogin.getIdAkun(), dataPanen.getId_hasilpanen());
            }
            mainDashboardPetani.loadpanen();
            mainDashboardPetani.tampilDataPanen();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void comboNamaHasilPanenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboNamaHasilPanenItemStateChanged
        // TODO add your handling code here:
        String[] selected = comboNamaHasilPanen.getSelectedItem().toString().split("-");
        for (int i = 0; i < daftarHasilPanen.size(); i++) {
            if (Integer.parseInt(selected[0]) == daftarHasilPanen.get(i).getId_hasilpanen()) {
                jTextField1.setText(daftarHasilPanen.get(i).getTipe_komoditas_panen());
            }

        }
    }//GEN-LAST:event_comboNamaHasilPanenItemStateChanged

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
            java.util.logging.Logger.getLogger(JFrameHasilPanen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameHasilPanen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameHasilPanen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameHasilPanen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    private javax.swing.JComboBox<String> comboNamaHasilPanen;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
