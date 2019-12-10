/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.petani;

import setani.generic.DataPanen;
import setani.admin.*;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;
import setani.koneksi.koneksi;
import setani.generic.DataAkun;
import setani.generic.DataTransaksiPetani;
import setani.login.Login1;

/**
 *
 * @author matohdev
 */
public class MainDashboardPetani extends javax.swing.JFrame {

    /**
     * Creates new form MainDashboardAdmin
     */
    private final CardLayout cardLayout;
    DataAkun informasilogin;
    int cardIndex = 0;

    public MainDashboardPetani(DataAkun login) {
        initComponents();
        informasilogin = login;
        cardLayout = (CardLayout) (panCard.getLayout());
        conn = koneksi.bukaKoneksi();
        jtHasilPanen.setModel(model);
        jTableTransaksi.setModel(modelTransaksiPetani);
        loadkolom();
        loadpanen();
        tampilDataPanen();
        loadTransaksi();
        tampilDataTransaksi();

        jLabel16.setText("Selamat datang, " + informasilogin.getNama());
        jLabelTotalHasilPanen.setText(Integer.toString(daftarpanen.size()) + " Komoditas Panen");
        jLabelTotalTransaksiBeli.setText(Integer.toString(daftarTransaksi.size()) + " Transaksi");
    }

    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel modelTransaksiPetani = new DefaultTableModel();
    private Connection conn;
    private ArrayList<DataPanen> daftarpanen = new ArrayList<>();
    ArrayList<DataTransaksiPetani> daftarTransaksi = new ArrayList<>();

    private void loadkolom() {
        model.addColumn("nama_komoditas_panen");
        model.addColumn("tipe_komoditas_panen");
        model.addColumn("berat_komoditas_panen");
        model.addColumn("harga_jual_kg");
        model.addColumn("tanggal_panen");
        modelTransaksiPetani.addColumn("Nama Hasil Panen");
        modelTransaksiPetani.addColumn("Berat Hasil Panen");
        modelTransaksiPetani.addColumn("Harga Per Kilo");
        modelTransaksiPetani.addColumn("Harga Total");
    }

    void loadpanen() {
        if (conn != null) {
            daftarpanen.clear();
            String kueri = "SELECT * FROM tb_hasil_panen INNER JOIN tb_tipe_hasil_panen ON tb_tipe_hasil_panen.id_tipe_hasil_panen = tb_hasil_panen.id_tipe_hasil_panen WHERE tb_hasil_panen.id_akun = '" + informasilogin.getIdAkun() + "'";
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
                    String tanggal_panen = rs.getString("tanggal_panen");
                    DataPanen data = new DataPanen(id_hasilpanen, id_akun, berat_komoditas_panen, nama_komoditas_panen, tipe_komoditas_panen, harga_jual_perkilo, tanggal_panen);
                    daftarpanen.add(data);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
    }

    void loadpanenCari(String keyword) {
        if (conn != null) {
            daftarpanen.clear();
            String kueri = "SELECT * FROM tb_hasil_panen INNER JOIN tb_tipe_hasil_panen ON tb_tipe_hasil_panen.id_tipe_hasil_panen = tb_hasil_panen.id_tipe_hasil_panen WHERE tb_hasil_panen.id_akun = ? AND nama_komoditas_panen LIKE ? OR tipe_komoditas_panen LIKE ?";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ps.setInt(1, informasilogin.getIdAkun());
                ps.setString(2, "%" + keyword + "%");
                ps.setString(3, "%" + keyword + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id_hasilpanen = rs.getInt("id_hasil_panen");
                    int id_akun = rs.getInt("id_akun");
                    String nama_komoditas_panen = rs.getString("nama_komoditas_panen");
                    String tipe_komoditas_panen = rs.getString("tipe_komoditas_panen");
                    int berat_komoditas_panen = rs.getInt("berat_komoditas_panen");
                    int harga_jual_perkilo = rs.getInt("harga_jual_kg");
                    String tanggal_panen = rs.getString("tanggal_panen");

                    if (informasilogin.getIdAkun() == id_akun) {
                        DataPanen data = new DataPanen(id_hasilpanen, id_akun, berat_komoditas_panen, nama_komoditas_panen, tipe_komoditas_panen, harga_jual_perkilo, tanggal_panen);
                        daftarpanen.add(data);
                    }
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
    }

    void tampilDataPanen() {
        model.setRowCount(0);
        for (DataPanen b : daftarpanen) {
            model.addRow(new Object[]{
                b.getNama_komoditas_panen(),
                b.getTipe_komoditas_panen(),
                b.getBerat_komoditas_panen(),
                b.getHarga_jual_perkilo(),
                b.getTanggal_panen()

            });
        }
    }

    void tampilDataTransaksi() {
        modelTransaksiPetani.setRowCount(0);
        for (DataTransaksiPetani b : daftarTransaksi) {
            modelTransaksiPetani.addRow(new Object[]{
                b.getNama_komoditas_panen(),
                b.getBerat(),
                b.getHarga_jual_kg(),
                b.getBerat() * b.getHarga_jual_kg()
            });
        }
    }

    private void loadTransaksi() {
        if (conn != null) {
            daftarTransaksi.clear();
            String kueri = "SELECT * FROM tb_komoditas_dijual INNER JOIN tb_hasil_panen ON tb_komoditas_dijual.id_hasil_panen = tb_hasil_panen.id_hasil_panen WHERE tb_hasil_panen.id_akun = '" + informasilogin.getIdAkun() + "'";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id_komoditas_dijual, berat, id_transaksi, id_hasil_panen, harga_jual_kg;
                    String nama_komoditas_panen;
                    id_komoditas_dijual = rs.getInt("id_komoditas_dijual");
                    berat = rs.getInt("berat");
                    id_transaksi = rs.getInt("id_transaksi");
                    id_hasil_panen = rs.getInt("id_hasil_panen");
                    harga_jual_kg = rs.getInt("harga_jual_kg");
                    nama_komoditas_panen = rs.getString("nama_komoditas_panen");
                    DataTransaksiPetani dataTransaksi = new DataTransaksiPetani(id_komoditas_dijual, berat, id_transaksi, id_hasil_panen, harga_jual_kg, nama_komoditas_panen);
                    daftarTransaksi.add(dataTransaksi);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainDashboardPetani.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void gantiWarnaSidePanel(JPanel jPanel, JPanel panelIndikator) {
        JPanel[] sideButtonElem = {sideBtnBeranda, sideBtnHasilPanen, sideBtnTransaksi};
        JPanel[] sideButtonIndikator = {panIndikatorBeranda, panIndikatorHasilPanen, panIndikatorTransaksi};
        // untuk menambahkan warna pada sideButton yang dipilih
        jPanel.setBackground(new Color(53, 53, 106));
        panelIndikator.setOpaque(true);

        // untuk mereset warna sideButton selainnya
        for (int i = 0; i < sideButtonElem.length; i++) {
            if (!jPanel.equals(sideButtonElem[i])) {
                sideButtonElem[i].setBackground(new Color(27, 27, 57));
            }
        }
        // untuk mereset side indikator nya
        for (int i = 0; i < sideButtonIndikator.length; i++) {
            if (!panelIndikator.equals(sideButtonIndikator[i])) {
                sideButtonIndikator[i].setOpaque(false);
            }
        }

        if (jPanel.equals(sideButtonElem[0])) {
            lblIconCariAtas.setVisible(false);
            tfCari.setVisible(false);
        } else {
            lblIconCariAtas.setVisible(true);
            tfCari.setVisible(true);
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
        sideBtnHasilPanen = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panIndikatorHasilPanen = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        sideBtnTransaksi = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panIndikatorTransaksi = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        sideBtnKeluar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        sideBtnBeranda = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        panIndikatorBeranda = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tfCari = new javax.swing.JTextField();
        lblIconCariAtas = new javax.swing.JLabel();
        panCard = new javax.swing.JPanel();
        panCardBeranda = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabelTotalHasilPanen = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabelTotalTransaksiBeli = new javax.swing.JLabel();
        panCardHasilPanen = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        btnTambahHasilPanen = new javax.swing.JButton();
        btnTambahHasilPanen1 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtHasilPanen = new javax.swing.JTable();
        panCardTransaksi = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableTransaksi = new javax.swing.JTable();
        panCardPengaturan = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable11 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 200, 83));

        jPanel1.setBackground(new java.awt.Color(27, 27, 57));

        sideBtnHasilPanen.setBackground(new java.awt.Color(27, 27, 57));
        sideBtnHasilPanen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sideBtnHasilPanen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sideBtnHasilPanenMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(254, 254, 254));
        jLabel1.setText("Hasil Panen");

        panIndikatorHasilPanen.setOpaque(false);

        javax.swing.GroupLayout panIndikatorHasilPanenLayout = new javax.swing.GroupLayout(panIndikatorHasilPanen);
        panIndikatorHasilPanen.setLayout(panIndikatorHasilPanenLayout);
        panIndikatorHasilPanenLayout.setHorizontalGroup(
            panIndikatorHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        panIndikatorHasilPanenLayout.setVerticalGroup(
            panIndikatorHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_natural_food_24px.png"))); // NOI18N

        javax.swing.GroupLayout sideBtnHasilPanenLayout = new javax.swing.GroupLayout(sideBtnHasilPanen);
        sideBtnHasilPanen.setLayout(sideBtnHasilPanenLayout);
        sideBtnHasilPanenLayout.setHorizontalGroup(
            sideBtnHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBtnHasilPanenLayout.createSequentialGroup()
                .addComponent(panIndikatorHasilPanen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );
        sideBtnHasilPanenLayout.setVerticalGroup(
            sideBtnHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panIndikatorHasilPanen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sideBtnHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        sideBtnTransaksi.setBackground(new java.awt.Color(27, 27, 57));
        sideBtnTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sideBtnTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sideBtnTransaksiMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(254, 254, 254));
        jLabel4.setText("Transaksi");

        panIndikatorTransaksi.setOpaque(false);

        javax.swing.GroupLayout panIndikatorTransaksiLayout = new javax.swing.GroupLayout(panIndikatorTransaksi);
        panIndikatorTransaksi.setLayout(panIndikatorTransaksiLayout);
        panIndikatorTransaksiLayout.setHorizontalGroup(
            panIndikatorTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        panIndikatorTransaksiLayout.setVerticalGroup(
            panIndikatorTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_left_and_right_arrows_24px.png"))); // NOI18N

        javax.swing.GroupLayout sideBtnTransaksiLayout = new javax.swing.GroupLayout(sideBtnTransaksi);
        sideBtnTransaksi.setLayout(sideBtnTransaksiLayout);
        sideBtnTransaksiLayout.setHorizontalGroup(
            sideBtnTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBtnTransaksiLayout.createSequentialGroup()
                .addComponent(panIndikatorTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );
        sideBtnTransaksiLayout.setVerticalGroup(
            sideBtnTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panIndikatorTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sideBtnTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        sideBtnKeluar.setBackground(new java.awt.Color(223, 32, 34));
        sideBtnKeluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sideBtnKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sideBtnKeluarMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(254, 254, 254));
        jLabel8.setText("Keluar");

        jPanel12.setOpaque(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_exit_24px.png"))); // NOI18N

        javax.swing.GroupLayout sideBtnKeluarLayout = new javax.swing.GroupLayout(sideBtnKeluar);
        sideBtnKeluar.setLayout(sideBtnKeluarLayout);
        sideBtnKeluarLayout.setHorizontalGroup(
            sideBtnKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBtnKeluarLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addContainerGap())
        );
        sideBtnKeluarLayout.setVerticalGroup(
            sideBtnKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sideBtnKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        sideBtnBeranda.setBackground(new java.awt.Color(53, 53, 106));
        sideBtnBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sideBtnBeranda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sideBtnBerandaMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(254, 254, 254));
        jLabel14.setText("Beranda");

        javax.swing.GroupLayout panIndikatorBerandaLayout = new javax.swing.GroupLayout(panIndikatorBeranda);
        panIndikatorBeranda.setLayout(panIndikatorBerandaLayout);
        panIndikatorBerandaLayout.setHorizontalGroup(
            panIndikatorBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        panIndikatorBerandaLayout.setVerticalGroup(
            panIndikatorBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_natural_food_24px.png"))); // NOI18N

        javax.swing.GroupLayout sideBtnBerandaLayout = new javax.swing.GroupLayout(sideBtnBeranda);
        sideBtnBeranda.setLayout(sideBtnBerandaLayout);
        sideBtnBerandaLayout.setHorizontalGroup(
            sideBtnBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBtnBerandaLayout.createSequentialGroup()
                .addComponent(panIndikatorBeranda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addContainerGap())
        );
        sideBtnBerandaLayout.setVerticalGroup(
            sideBtnBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panIndikatorBeranda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sideBtnBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBtnHasilPanen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sideBtnTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sideBtnKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sideBtnBeranda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(sideBtnBeranda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sideBtnHasilPanen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sideBtnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sideBtnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(48, 63, 159));
        jPanel2.setMinimumSize(new java.awt.Dimension(290, 42));

        tfCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCariKeyReleased(evt);
            }
        });

        lblIconCariAtas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_search_20px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIconCariAtas)
                .addGap(35, 35, 35))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIconCariAtas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfCari))
                .addContainerGap())
        );

        panCard.setBackground(new java.awt.Color(254, 254, 254));
        panCard.setLayout(new java.awt.CardLayout());

        panCardBeranda.setBackground(new java.awt.Color(249, 249, 249));

        jPanel8.setBackground(new java.awt.Color(249, 249, 249));

        jLabel21.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel21.setText("Beranda");

        jLabel16.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel16.setText("Selamat datang, Fulan bin Fulan");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(187, 187, 187));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 3));

        jPanel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel11.setPreferredSize(new java.awt.Dimension(400, 100));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/hasil_panenx48.png"))); // NOI18N

        jLabelTotalHasilPanen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTotalHasilPanen.setText("1300 Kg");

        jLabel49.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel49.setText("Hasil Panen");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addComponent(jLabelTotalHasilPanen))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotalHasilPanen)
                .addContainerGap(27, Short.MAX_VALUE))
            .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel11);

        jPanel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.setPreferredSize(new java.awt.Dimension(400, 100));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel50.setText("Transaksi Beli");

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/transaksi_masukx48.png"))); // NOI18N

        jLabelTotalTransaksiBeli.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTotalTransaksiBeli.setText("27 Transaksi");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(jLabelTotalTransaksiBeli))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotalTransaksiBeli)
                .addContainerGap(27, Short.MAX_VALUE))
            .addComponent(jLabel51, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel13);

        javax.swing.GroupLayout panCardBerandaLayout = new javax.swing.GroupLayout(panCardBeranda);
        panCardBeranda.setLayout(panCardBerandaLayout);
        panCardBerandaLayout.setHorizontalGroup(
            panCardBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panCardBerandaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );
        panCardBerandaLayout.setVerticalGroup(
            panCardBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardBerandaLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(313, Short.MAX_VALUE))
        );

        panCard.add(panCardBeranda, "panCardBeranda");

        panCardHasilPanen.setBackground(new java.awt.Color(249, 249, 249));

        jPanel9.setBackground(new java.awt.Color(249, 249, 249));

        jLabel22.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel22.setText("Hasil Panen");

        btnTambahHasilPanen.setBackground(new java.awt.Color(1, 87, 155));
        btnTambahHasilPanen.setForeground(new java.awt.Color(246, 246, 246));
        btnTambahHasilPanen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_add_24px.png"))); // NOI18N
        btnTambahHasilPanen.setText("Tambah Hasil Panen");
        btnTambahHasilPanen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambahHasilPanen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahHasilPanenActionPerformed(evt);
            }
        });

        btnTambahHasilPanen1.setBackground(new java.awt.Color(1, 87, 155));
        btnTambahHasilPanen1.setForeground(new java.awt.Color(246, 246, 246));
        btnTambahHasilPanen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_add_24px.png"))); // NOI18N
        btnTambahHasilPanen1.setText("Edit");
        btnTambahHasilPanen1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambahHasilPanen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahHasilPanen1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTambahHasilPanen1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTambahHasilPanen)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel22)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahHasilPanen, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahHasilPanen1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jtHasilPanen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane7.setViewportView(jtHasilPanen);

        javax.swing.GroupLayout panCardHasilPanenLayout = new javax.swing.GroupLayout(panCardHasilPanen);
        panCardHasilPanen.setLayout(panCardHasilPanenLayout);
        panCardHasilPanenLayout.setHorizontalGroup(
            panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panCardHasilPanenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 947, Short.MAX_VALUE)
                .addContainerGap())
        );
        panCardHasilPanenLayout.setVerticalGroup(
            panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardHasilPanenLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        panCard.add(panCardHasilPanen, "panCardHasilPanen");

        panCardTransaksi.setBackground(new java.awt.Color(249, 249, 249));

        jPanel10.setBackground(new java.awt.Color(249, 249, 249));

        jLabel23.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel23.setText("Transaksi");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel23)
                .addContainerGap(776, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel23)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jTableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(jTableTransaksi);

        javax.swing.GroupLayout panCardTransaksiLayout = new javax.swing.GroupLayout(panCardTransaksi);
        panCardTransaksi.setLayout(panCardTransaksiLayout);
        panCardTransaksiLayout.setHorizontalGroup(
            panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panCardTransaksiLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        panCardTransaksiLayout.setVerticalGroup(
            panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardTransaksiLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        panCard.add(panCardTransaksi, "panCardTransaksi");

        panCardPengaturan.setBackground(new java.awt.Color(249, 249, 249));

        jPanel14.setBackground(new java.awt.Color(249, 249, 249));

        jLabel26.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel26.setText("Pengaturan");

        jButton11.setBackground(new java.awt.Color(1, 87, 155));
        jButton11.setForeground(new java.awt.Color(246, 246, 246));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_add_24px.png"))); // NOI18N
        jButton11.setText("Tambah Data");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton11)
                .addGap(30, 30, 30))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jTable11.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(jTable11);

        javax.swing.GroupLayout panCardPengaturanLayout = new javax.swing.GroupLayout(panCardPengaturan);
        panCardPengaturan.setLayout(panCardPengaturanLayout);
        panCardPengaturanLayout.setHorizontalGroup(
            panCardPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panCardPengaturanLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        panCardPengaturanLayout.setVerticalGroup(
            panCardPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardPengaturanLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        panCard.add(panCardPengaturan, "panCardPengaturan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sideBtnHasilPanenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sideBtnHasilPanenMouseClicked
        gantiWarnaSidePanel(sideBtnHasilPanen, panIndikatorHasilPanen);
        cardLayout.show(panCard, "panCardHasilPanen");
        cardIndex = 1;
    }//GEN-LAST:event_sideBtnHasilPanenMouseClicked

    private void sideBtnTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sideBtnTransaksiMouseClicked
        gantiWarnaSidePanel(sideBtnTransaksi, panIndikatorTransaksi);
        cardLayout.show(panCard, "panCardTransaksi");
        cardIndex = 2;
    }//GEN-LAST:event_sideBtnTransaksiMouseClicked

    private void sideBtnKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sideBtnKeluarMouseClicked

        dispose();
        Login1 login = new Login1();
        login.setLocationRelativeTo(null);
        login.setTitle("Login");
        login.setVisible(true);
    }//GEN-LAST:event_sideBtnKeluarMouseClicked

    private void sideBtnBerandaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sideBtnBerandaMouseClicked
        gantiWarnaSidePanel(sideBtnBeranda, panIndikatorBeranda);
        cardLayout.show(panCard, "panCardBeranda");
        cardIndex = 0;
    }//GEN-LAST:event_sideBtnBerandaMouseClicked

    private void btnTambahHasilPanenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahHasilPanenActionPerformed
        // TODO add your handling code here:
        JFrameHasilPanen jFrameHasilPanen = new JFrameHasilPanen(this, informasilogin);
        jFrameHasilPanen.setLocationRelativeTo(null);
        jFrameHasilPanen.setVisible(true);
    }//GEN-LAST:event_btnTambahHasilPanenActionPerformed

    private void tfCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariKeyReleased
        // TODO add your handling code here:
        String keyword = tfCari.getText();
        if (cardIndex == 1) {
            loadpanenCari(keyword);
            tampilDataPanen();
        } else if (cardIndex == 2) {

        }
    }//GEN-LAST:event_tfCariKeyReleased

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        // TODO add your handling code here:
//        loadpanen();
//        tampilDataPanen();
//        gantiWarnaSidePanel(sideBtnHasilPanen, panIndikatorHasilPanen);
//        cardLayout.show(panCard, "panCardHasilPanen");
//        indexCard = 1;
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
        // TODO add your handling code here:
//        loadTransaksiHistory();
//        tampilDataTransaksiHistory();
//        gantiWarnaSidePanel(sideBtnTransaksi, panIndikatorTransaksi);
//        cardLayout.show(panCard, "panCardTransaksi");
//        indexCard = 2;
    }//GEN-LAST:event_jPanel13MouseClicked

    private void btnTambahHasilPanen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahHasilPanen1ActionPerformed
        // TODO add your handling code here:
        JFrameHasilPanen jFrameHasilPanen = new JFrameHasilPanen(this, informasilogin, daftarpanen.get(jtHasilPanen.getSelectedRow()));
        jFrameHasilPanen.setLocationRelativeTo(null);
        jFrameHasilPanen.setVisible(true);
    }//GEN-LAST:event_btnTambahHasilPanen1ActionPerformed

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
            java.util.logging.Logger.getLogger(MainDashboardPetani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainDashboardPetani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainDashboardPetani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainDashboardPetani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new MainDashboardAdmin().setVisible(true);
//                MainDashboardPetani mainDashboardPetani = new MainDashboardPetani();
//                mainDashboardPetani.setLocationRelativeTo(null);
//                mainDashboardPetani.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTambahHasilPanen;
    private javax.swing.JButton btnTambahHasilPanen1;
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelTotalHasilPanen;
    private javax.swing.JLabel jLabelTotalTransaksiBeli;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable11;
    private javax.swing.JTable jTableTransaksi;
    private javax.swing.JTable jtHasilPanen;
    private javax.swing.JLabel lblIconCariAtas;
    private javax.swing.JPanel panCard;
    private javax.swing.JPanel panCardBeranda;
    private javax.swing.JPanel panCardHasilPanen;
    private javax.swing.JPanel panCardPengaturan;
    private javax.swing.JPanel panCardTransaksi;
    private javax.swing.JPanel panIndikatorBeranda;
    private javax.swing.JPanel panIndikatorHasilPanen;
    private javax.swing.JPanel panIndikatorTransaksi;
    private javax.swing.JPanel sideBtnBeranda;
    private javax.swing.JPanel sideBtnHasilPanen;
    private javax.swing.JPanel sideBtnKeluar;
    private javax.swing.JPanel sideBtnTransaksi;
    private javax.swing.JTextField tfCari;
    // End of variables declaration//GEN-END:variables
}
