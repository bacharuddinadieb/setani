/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.pembeli;

import setani.petani.*;
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
import setani.login.informasiLogin;

/**
 *
 * @author matohdev
 */
public class MainDashboardPembeli extends javax.swing.JFrame {

    /**
     * Creates new form MainDashboardAdmin
     */
    private final CardLayout cardLayout;
    informasiLogin informasilogin;
    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel model2 = new DefaultTableModel();

    private Connection conn;
    ArrayList<datapanen> daftarpanen = new ArrayList<>();
    ArrayList<datapanen> daftarPanenMauDiBeli = new ArrayList<>();

    public MainDashboardPembeli(informasiLogin login) {
        initComponents();
        cardLayout = (CardLayout) (panCard.getLayout());
        conn = koneksi.bukaKoneksi();
        jtHasilPanen.setModel(model);
        jTableTransaksi.setModel(model);
        loadkolom();
        loadpanen();
        tampilDataPanen();
        informasilogin = login;
        jLabelWelcomeHomeName.setText(informasilogin.getNama());
        jLabelTotalHasilPanen.setText(Integer.toString(daftarpanen.size()) + " Komoditas Panen");
    }

    private void loadkolom() {
        model.addColumn("nama_komoditas_panen");
        model.addColumn("tipe_komoditas_panen");
        model.addColumn("berat_komoditas_panen");
        model.addColumn("harga_jual_kg");
        model.addColumn("tanggal_panen");
        model2.addColumn("nama_komoditas_panen");
        model2.addColumn("tipe_komoditas_panen");
        model2.addColumn("berat_komoditas_panen");
        model2.addColumn("harga_jual_kg");
        model2.addColumn("tanggal_panen");
    }

    private void loadpanen() {
        if (conn != null) {
            String kueri = "SELECT * FROM tb_hasil_panen";
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
                    datapanen data = new datapanen(id_hasilpanen, id_akun, berat_komoditas_panen, nama_komoditas_panen, tipe_komoditas_panen, harga_jual_perkilo, tanggal_panen);
                    daftarpanen.add(data);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainDashboardPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void tampilDataPanen() {
        model.setRowCount(0);
        for (datapanen b : daftarpanen) {
            model.addRow(new Object[]{
                b.getNama_komoditas_panen(),
                b.getTipe_komoditas_panen(),
                b.getBerat_komoditas_panen(),
                b.getHarga_jual_perkilo(),
                b.getTanggal_panen()

            });
        }
    }

    void tampilDataPanen2() {
        model2.setRowCount(0);
        for (datapanen b : daftarPanenMauDiBeli) {
            model2.addRow(new Object[]{
                b.getNama_komoditas_panen(),
                b.getTipe_komoditas_panen(),
                b.getBerat_komoditas_panen(),
                b.getHarga_jual_perkilo(),
                b.getTanggal_panen()

            });
        }
    }

    public void loadTabelHasilPanenYangMauDibeli() {
        jTabelBarangLaku.setModel(model2);
    }

    private void gantiWarnaSidePanel(JPanel jPanel, JPanel panelIndikator) {
        JPanel[] sideButtonElem = {sideBtnBeranda, sideBtnHasilPanen, sideBtnTransaksi, sideBtnPengaturan};
        JPanel[] sideButtonIndikator = {panIndikatorBeranda, panIndikatorHasilPanen, panIndikatorTransaksi, panIndikatorPengaturan};
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
        sideBtnPengaturan = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        panIndikatorPengaturan = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
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
        jLabelWelcomeHomeName = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabelTotalHasilPanen = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabelTotalTransaksiBeli = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        panCardHasilPanen = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtHasilPanen = new javax.swing.JTable();
        panCardTransaksi = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableTransaksi = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTabelBarangLaku = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTabelHistoryTransaksi = new javax.swing.JTable();
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

        sideBtnPengaturan.setBackground(new java.awt.Color(27, 27, 57));
        sideBtnPengaturan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sideBtnPengaturan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sideBtnPengaturanMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(254, 254, 254));
        jLabel10.setText("Pengaturan");

        panIndikatorPengaturan.setOpaque(false);

        javax.swing.GroupLayout panIndikatorPengaturanLayout = new javax.swing.GroupLayout(panIndikatorPengaturan);
        panIndikatorPengaturan.setLayout(panIndikatorPengaturanLayout);
        panIndikatorPengaturanLayout.setHorizontalGroup(
            panIndikatorPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        panIndikatorPengaturanLayout.setVerticalGroup(
            panIndikatorPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_settings_24px.png"))); // NOI18N

        javax.swing.GroupLayout sideBtnPengaturanLayout = new javax.swing.GroupLayout(sideBtnPengaturan);
        sideBtnPengaturan.setLayout(sideBtnPengaturanLayout);
        sideBtnPengaturanLayout.setHorizontalGroup(
            sideBtnPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBtnPengaturanLayout.createSequentialGroup()
                .addComponent(panIndikatorPengaturan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap())
        );
        sideBtnPengaturanLayout.setVerticalGroup(
            sideBtnPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBtnPengaturanLayout.createSequentialGroup()
                .addGroup(sideBtnPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panIndikatorPengaturan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(sideBtnPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
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
            .addComponent(sideBtnPengaturan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(sideBtnPengaturan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sideBtnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(48, 63, 159));
        jPanel2.setMinimumSize(new java.awt.Dimension(290, 42));

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

        jLabelWelcomeHomeName.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabelWelcomeHomeName.setText("Selamat datang, Fulan bin Fulan");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelWelcomeHomeName)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelWelcomeHomeName)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(187, 187, 187));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 3));

        jPanel5.setPreferredSize(new java.awt.Dimension(400, 100));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/hasil_panenx48.png"))); // NOI18N

        jLabelTotalHasilPanen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTotalHasilPanen.setText("1300 Kg");

        jLabel29.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel29.setText("Hasil Panen");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(jLabelTotalHasilPanen))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotalHasilPanen)
                .addContainerGap(25, Short.MAX_VALUE))
            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel5);

        jPanel6.setPreferredSize(new java.awt.Dimension(400, 100));

        jLabel31.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel31.setText("Transaksi Beli");

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/transaksi_masukx48.png"))); // NOI18N

        jLabelTotalTransaksiBeli.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTotalTransaksiBeli.setText("27 Transaksi");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabelTotalTransaksiBeli))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotalTransaksiBeli)
                .addContainerGap(25, Short.MAX_VALUE))
            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel6);

        jLabel17.setFont(new java.awt.Font("Ubuntu", 0, 22)); // NOI18N
        jLabel17.setText("Aktifitas Terakhir");

        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_chevron_right_48px_2.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel19.setText("Hasil Panen");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Fulan bin Fulan, ada hasil panen baru berupa JAGUNG dengan Berat 100Kg");

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/hasil_panenx40.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel27)
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(28, 28, 28))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_chevron_right_48px_2.png"))); // NOI18N

        jLabel41.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel41.setText("Transaksi");

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel42.setText("Fulan bin Fulan, ada pembeli baru memborong jJAGUNG 1 TON");

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/transaksix40.png"))); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel43)
                .addGap(30, 30, 30)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel40)
                .addGap(28, 28, 28))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panCardBerandaLayout = new javax.swing.GroupLayout(panCardBeranda);
        panCardBeranda.setLayout(panCardBerandaLayout);
        panCardBerandaLayout.setHorizontalGroup(
            panCardBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panCardBerandaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panCardBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panCardBerandaLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panCardBerandaLayout.createSequentialGroup()
                        .addGroup(panCardBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE))
                        .addGap(27, 27, 27))))
        );
        panCardBerandaLayout.setVerticalGroup(
            panCardBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardBerandaLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        panCard.add(panCardBeranda, "panCardBeranda");

        panCardHasilPanen.setBackground(new java.awt.Color(249, 249, 249));

        jPanel9.setBackground(new java.awt.Color(249, 249, 249));

        jLabel22.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel22.setText("Hasil Panen");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel22)
                .addContainerGap(707, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel22)
                .addContainerGap(18, Short.MAX_VALUE))
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
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
                .addContainerGap())
        );
        panCardHasilPanenLayout.setVerticalGroup(
            panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardHasilPanenLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        panCard.add(panCardHasilPanen, "panCardHasilPanen");

        panCardTransaksi.setBackground(new java.awt.Color(249, 249, 249));

        jPanel10.setBackground(new java.awt.Color(249, 249, 249));

        jLabel23.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel23.setText("Transaksi");

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

        jTabelBarangLaku.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(jTabelBarangLaku);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Daftar Hasil Panen Yang Dibeli");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Daftar Hasil Panen");

        jToggleButton1.setBackground(new java.awt.Color(0, 153, 153));
        jToggleButton1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jToggleButton1.setText("BELI PRODUK");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setBackground(new java.awt.Color(0, 153, 153));
        jToggleButton2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jToggleButton2.setText("Proses Pembelian");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("History Transaksi");

        jTabelHistoryTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(jTabelHistoryTransaksi);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton1))
                    .addComponent(jScrollPane9)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel23)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel10);

        javax.swing.GroupLayout panCardTransaksiLayout = new javax.swing.GroupLayout(panCardTransaksi);
        panCardTransaksi.setLayout(panCardTransaksiLayout);
        panCardTransaksiLayout.setHorizontalGroup(
            panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panCardTransaksiLayout.setVerticalGroup(
            panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                .addContainerGap())
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
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        panCardPengaturanLayout.setVerticalGroup(
            panCardPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardPengaturanLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
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
    }//GEN-LAST:event_sideBtnHasilPanenMouseClicked

    private void sideBtnTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sideBtnTransaksiMouseClicked
        gantiWarnaSidePanel(sideBtnTransaksi, panIndikatorTransaksi);
        cardLayout.show(panCard, "panCardTransaksi");
    }//GEN-LAST:event_sideBtnTransaksiMouseClicked

    private void sideBtnPengaturanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sideBtnPengaturanMouseClicked
        gantiWarnaSidePanel(sideBtnPengaturan, panIndikatorPengaturan);
        cardLayout.show(panCard, "panCardPengaturan");
    }//GEN-LAST:event_sideBtnPengaturanMouseClicked

    private void sideBtnKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sideBtnKeluarMouseClicked

    }//GEN-LAST:event_sideBtnKeluarMouseClicked

    private void sideBtnBerandaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sideBtnBerandaMouseClicked
        gantiWarnaSidePanel(sideBtnBeranda, panIndikatorBeranda);
        cardLayout.show(panCard, "panCardBeranda");
    }//GEN-LAST:event_sideBtnBerandaMouseClicked

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
//        int selected = jTableTransaksi.getSelectedRow();
//        int nomor = Integer.parseInt(model.getValueAt(selected, 1).toString());

        int bariTerpilih = jTableTransaksi.getSelectedRow();
        System.out.println(bariTerpilih);
        if (bariTerpilih == -1) {
            JOptionPane.showMessageDialog(null, "Pilih daftar hasil panen terlebih dahulu!", "Pesan", JOptionPane.ERROR_MESSAGE);
        } else {
            Transaksi transaksi = new Transaksi(this, informasilogin, bariTerpilih, daftarpanen.get(bariTerpilih).getId_hasilpanen(), daftarpanen.get(bariTerpilih).getHarga_jual_perkilo(), daftarpanen.get(bariTerpilih).getId_akun());
            transaksi.setLocationRelativeTo(null);
            transaksi.setVisible(true);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
        gantiWarnaSidePanel(sideBtnHasilPanen, panIndikatorHasilPanen);
        cardLayout.show(panCard, "panCardHasilPanen");
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        // TODO add your handling code here:
        gantiWarnaSidePanel(sideBtnTransaksi, panIndikatorTransaksi);
        cardLayout.show(panCard, "panCardTransaksi");
    }//GEN-LAST:event_jPanel16MouseClicked

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
            java.util.logging.Logger.getLogger(MainDashboardPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainDashboardPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainDashboardPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainDashboardPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
//                new MainDashboardAdmin().setVisible(true);
//                MainDashboardPembeli mainDashboardPetani = new MainDashboardPembeli();
//                mainDashboardPetani.setLocationRelativeTo(null);
//                mainDashboardPetani.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelTotalHasilPanen;
    private javax.swing.JLabel jLabelTotalTransaksiBeli;
    private javax.swing.JLabel jLabelWelcomeHomeName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTabelBarangLaku;
    private javax.swing.JTable jTabelHistoryTransaksi;
    private javax.swing.JTable jTable11;
    private javax.swing.JTable jTableTransaksi;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JTable jtHasilPanen;
    private javax.swing.JLabel lblIconCariAtas;
    private javax.swing.JPanel panCard;
    private javax.swing.JPanel panCardBeranda;
    private javax.swing.JPanel panCardHasilPanen;
    private javax.swing.JPanel panCardPengaturan;
    private javax.swing.JPanel panCardTransaksi;
    private javax.swing.JPanel panIndikatorBeranda;
    private javax.swing.JPanel panIndikatorHasilPanen;
    private javax.swing.JPanel panIndikatorPengaturan;
    private javax.swing.JPanel panIndikatorTransaksi;
    private javax.swing.JPanel sideBtnBeranda;
    private javax.swing.JPanel sideBtnHasilPanen;
    private javax.swing.JPanel sideBtnKeluar;
    private javax.swing.JPanel sideBtnPengaturan;
    private javax.swing.JPanel sideBtnTransaksi;
    private javax.swing.JTextField tfCari;
    // End of variables declaration//GEN-END:variables
}
