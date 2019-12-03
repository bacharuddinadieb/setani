/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.pembeli;

import java.io.File;
import java.sql.Connection;
import setani.generic.DataTransaksi;
import setani.koneksi.koneksi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import setani.generic.DataAkun;
import setani.generic.DataPanen;


/**
 *
 * @author matohdev
 */
public class DetailTransaksi extends javax.swing.JFrame {

    /**
     * Creates new form DetailTransaksi
     */
    private DataTransaksi dataTransaksi;
    private Connection conn;
    private DataAkun da;// data pembeli
    ArrayList<DataPanen> daftarKomoditasDijual = new ArrayList<>();
    private DefaultTableModel modelKomoditasDijual = new DefaultTableModel();
    private int totalHarga = 0;
    
    JasperReport jr;
    JasperPrint jp;
    JasperDesign jd;
    Map param = new HashMap();

    public DetailTransaksi(DataTransaksi dt) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Detail Transaksi");

        conn = koneksi.bukaKoneksi();
        dataTransaksi = dt;
        loadAkun();
        loadkolom();
        loadDataKomoditasDijual();
        tampilDataKomoditasDijual();
        inpTanggalTransaksi.setText(dataTransaksi.getTanggalTransaksi());
        inpNamaPembeli.setText(da.getNama());
        inpNoTelepone.setText(da.getNomerTelepon());
        inpAlamat.setText(da.getAlamat());
        inpTotalHarga.setText("Rp." + totalHarga);
    }

    private void loadkolom() {
        modelKomoditasDijual.addColumn("Nama Komoditas Panen");
        modelKomoditasDijual.addColumn("Tipe Komoditas Panen");
        modelKomoditasDijual.addColumn("Berat Komoditas Panen");
        modelKomoditasDijual.addColumn("Total Harga");
        modelKomoditasDijual.addColumn("Tanggal Panen");
        jtListHasilPanen.setModel(modelKomoditasDijual);
    }

    private void loadAkun() {
        if (conn != null) {
            String kueri = "SELECT * FROM tb_akun WHERE id_akun = '" + dataTransaksi.getIdPembeli() + "'";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    da = new DataAkun(rs.getInt("id_akun"), rs.getInt("role"), rs.getInt("status"), rs.getString("nama"), rs.getString("username"), rs.getString("alamat"), rs.getString("nomer_telepon"));
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainDashboardPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loadDataKomoditasDijual() {
        if (conn != null) {
            daftarKomoditasDijual.clear();
            String kueri = "SELECT * FROM tb_komoditas_dijual INNER JOIN tb_hasil_panen ON tb_hasil_panen.id_hasil_panen = tb_komoditas_dijual.id_hasil_panen INNER JOIN tb_tipe_hasil_panen ON tb_tipe_hasil_panen.id_tipe_hasil_panen = tb_hasil_panen.id_tipe_hasil_panen WHERE id_transaksi = '" + dataTransaksi.getIdTransaksi() + "'";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id_hasilpanen = rs.getInt("id_hasil_panen");
                    int id_akun = rs.getInt("id_akun");
                    String nama_komoditas_panen = rs.getString("nama_komoditas_panen");
                    String tipe_komoditas_panen = rs.getString("tipe_komoditas_panen");
                    int berat_komoditas_panen = rs.getInt("berat");
                    int harga_jual_perkilo = rs.getInt("harga_jual_kg");
                    String tanggal_panen = rs.getString("tanggal_panen");
                    DataPanen data = new DataPanen(id_hasilpanen, id_akun, berat_komoditas_panen, nama_komoditas_panen, tipe_komoditas_panen, harga_jual_perkilo, tanggal_panen);
                    daftarKomoditasDijual.add(data);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainDashboardPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void tampilDataKomoditasDijual() {
        modelKomoditasDijual.setRowCount(0);
        for (DataPanen b : daftarKomoditasDijual) {
            modelKomoditasDijual.addRow(new Object[]{
                b.getNama_komoditas_panen(),
                b.getTipe_komoditas_panen(),
                b.getBerat_komoditas_panen(),
                b.getHarga_jual_perkilo() * b.getBerat_komoditas_panen(),
                b.getTanggal_panen()

            });
            totalHarga += b.getBerat_komoditas_panen() * b.getHarga_jual_perkilo();
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
        inpNamaPembeli = new javax.swing.JTextField();
        inpNoTelepone = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnCetakNota = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        inpAlamat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtListHasilPanen = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        inpTanggalTransaksi = new javax.swing.JTextField();
        inpTotalHarga = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Detail Transaksi");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Pembeli");

        inpNamaPembeli.setEditable(false);
        inpNamaPembeli.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        inpNamaPembeli.setFocusable(false);
        inpNamaPembeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpNamaPembeliActionPerformed(evt);
            }
        });

        inpNoTelepone.setEditable(false);
        inpNoTelepone.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        inpNoTelepone.setFocusable(false);
        inpNoTelepone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpNoTeleponeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("No Telepone");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Alamat");

        btnCetakNota.setBackground(new java.awt.Color(1, 87, 155));
        btnCetakNota.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCetakNota.setText("Cetak Nota");
        btnCetakNota.setPreferredSize(new java.awt.Dimension(125, 30));
        btnCetakNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakNotaActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(223, 32, 34));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Tutup");
        jButton2.setPreferredSize(new java.awt.Dimension(125, 30));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        inpAlamat.setEditable(false);
        inpAlamat.setColumns(20);
        inpAlamat.setRows(5);
        jScrollPane1.setViewportView(inpAlamat);

        jtListHasilPanen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jtListHasilPanen);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("List Hasil Panen Yang Dibeli");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Tanggal Transaksi");

        inpTanggalTransaksi.setEditable(false);
        inpTanggalTransaksi.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        inpTanggalTransaksi.setFocusable(false);
        inpTanggalTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpTanggalTransaksiActionPerformed(evt);
            }
        });

        inpTotalHarga.setEditable(false);
        inpTotalHarga.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        inpTotalHarga.setFocusable(false);
        inpTotalHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpTotalHargaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Total Harga");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(inpTanggalTransaksi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(inpNamaPembeli)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpNoTelepone)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(inpTotalHarga, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCetakNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(31, 31, 31))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inpTanggalTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inpNamaPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inpNoTelepone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inpTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCetakNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
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

    private void inpNamaPembeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpNamaPembeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpNamaPembeliActionPerformed

    private void inpNoTeleponeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpNoTeleponeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpNoTeleponeActionPerformed

    private void btnCetakNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakNotaActionPerformed
        // TODO add your handling code here:
        try {
            param.put("idTransaksi", dataTransaksi.getIdTransaksi());
            jp = JasperFillManager.fillReport(getClass().getResourceAsStream("reportDetailTransaksiPembeli.jasper"), param, conn);
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            System.err.println(e);
        }
        
    }//GEN-LAST:event_btnCetakNotaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void inpTanggalTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpTanggalTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpTanggalTransaksiActionPerformed

    private void inpTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpTotalHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpTotalHargaActionPerformed

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
            java.util.logging.Logger.getLogger(DetailTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetailTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetailTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetailTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetakNota;
    private javax.swing.JTextArea inpAlamat;
    private javax.swing.JTextField inpNamaPembeli;
    private javax.swing.JTextField inpNoTelepone;
    private javax.swing.JTextField inpTanggalTransaksi;
    private javax.swing.JTextField inpTotalHarga;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtListHasilPanen;
    // End of variables declaration//GEN-END:variables
}
