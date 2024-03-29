/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.pembeli;

import setani.generic.DataPanen;
import com.mysql.jdbc.Statement;
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
import setani.generic.DataAkun;
import setani.generic.DataTransaksi;
import setani.login.Login1;

/**
 *
 * @author matohdev
 */
public class MainDashboardPembeli extends javax.swing.JFrame {

    /**
     * Creates new form MainDashboardAdmin
     */
    private final CardLayout cardLayout;
    DataAkun informasilogin;
    private int indexCard = 0;
    private DefaultTableModel modelHasilPanen = new DefaultTableModel();
    private DefaultTableModel modelTransaksiHasilPanen = new DefaultTableModel();
    private DefaultTableModel modelTransaksiHistory = new DefaultTableModel();

    private Connection conn;
    ArrayList<DataPanen> daftarpanen = new ArrayList<>();
    ArrayList<DataPanen> daftarPanenMauDiBeli = new ArrayList<>();
    ArrayList<DataTransaksi> daftarTransaksi = new ArrayList<>();

    int statusFilterTransaksi = 0;

    public MainDashboardPembeli(DataAkun login) {
        initComponents();
        cardLayout = (CardLayout) (panCard.getLayout());
        conn = koneksi.bukaKoneksi();
        jTableTransaksi.setModel(modelHasilPanen);
        jTabelBarangLaku.setModel(modelTransaksiHasilPanen);
        jTabelHistoryTransaksi.setModel(modelTransaksiHistory);
        loadkolom();
        loadpanen();
        tampilDataPanen();
        loadTransaksiHistory();
        tampilDataTransaksiHistory();
        informasilogin = login;
        jLabelWelcomeHomeName.setText("Selamat datang, " + informasilogin.getNama());
        jLabelTotalHasilPanen.setText(Integer.toString(daftarpanen.size()) + " Komoditas Panen");
        jrbProses.setSelected(true);
    }

    private void loadkolom() {
        modelHasilPanen.addColumn("Nama Komoditas Panen");
        modelHasilPanen.addColumn("Tipe Komoditas Panen");
        modelHasilPanen.addColumn("Berat Komoditas Panen");
        modelHasilPanen.addColumn("Harga Jual/Kg");
        modelHasilPanen.addColumn("Petani");
        modelHasilPanen.addColumn("Tanggal Panen");
        modelTransaksiHasilPanen.addColumn("Nama Komoditas Panen");
        modelTransaksiHasilPanen.addColumn("Tipe Komoditas Panen");
        modelTransaksiHasilPanen.addColumn("Berat Komoditas Panen");
        modelTransaksiHasilPanen.addColumn("Harga Jual/Kg");
        modelTransaksiHasilPanen.addColumn("Petani");
        modelTransaksiHasilPanen.addColumn("Tanggal Panen");
        modelTransaksiHistory.addColumn("Id Transaksi");
        modelTransaksiHistory.addColumn("Tanggal Transaksi");
        modelTransaksiHistory.addColumn("Pembeli");
        modelTransaksiHistory.addColumn("Status");

    }

    private void loadpanen() {
        if (conn != null) {
            daftarpanen.clear();
            String kueri = "SELECT * FROM tb_hasil_panen INNER JOIN tb_tipe_hasil_panen ON tb_tipe_hasil_panen.id_tipe_hasil_panen = tb_hasil_panen.id_tipe_hasil_panen INNER JOIN tb_akun ON tb_akun.id_akun = tb_hasil_panen.id_akun";
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
                    String petani = rs.getString("nama");
                    DataPanen data = new DataPanen(id_hasilpanen, id_akun, berat_komoditas_panen, nama_komoditas_panen, tipe_komoditas_panen, harga_jual_perkilo, tanggal_panen, petani);
                    daftarpanen.add(data);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainDashboardPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loadpanenCari(String Komoditas) {
        if (conn != null) {
            daftarpanen.clear();
            String kueri = "SELECT * FROM tb_hasil_panen INNER JOIN tb_tipe_hasil_panen ON tb_tipe_hasil_panen.id_tipe_hasil_panen = tb_hasil_panen.id_tipe_hasil_panen INNER JOIN tb_akun ON tb_akun.id_akun = tb_hasil_panen.id_akun WHERE nama_komoditas_panen LIKE ? OR tipe_komoditas_panen LIKE ?";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ps.setString(1, "%" + Komoditas + "%");
                ps.setString(2, "%" + Komoditas + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id_hasilpanen = rs.getInt("id_hasil_panen");
                    int id_akun = rs.getInt("id_akun");
                    String nama_komoditas_panen = rs.getString("nama_komoditas_panen");
                    String tipe_komoditas_panen = rs.getString("tipe_komoditas_panen");
                    int berat_komoditas_panen = rs.getInt("berat_komoditas_panen");
                    int harga_jual_perkilo = rs.getInt("harga_jual_kg");
                    String tanggal_panen = rs.getString("tanggal_panen");
                    String petani = rs.getString("nama");
                    DataPanen data = new DataPanen(id_hasilpanen, id_akun, berat_komoditas_panen, nama_komoditas_panen, tipe_komoditas_panen, harga_jual_perkilo, tanggal_panen, petani);
                    daftarpanen.add(data);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainDashboardPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loadTransaksiHistory() {
        if (conn != null) {
            daftarTransaksi.clear();
            String kueri = "SELECT * FROM tb_transaksi INNER JOIN tb_akun ON tb_akun.id_akun = tb_transaksi.id_pembeli";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int idTransaksi = rs.getInt("id_transaksi");
                    String tanggalTransaksi = rs.getString("tanggal_transaksi");
                    int idPembeli = rs.getInt("id_pembeli");
                    String namaPembeli = rs.getString("nama");
                    int status = rs.getInt("status");
                    DataTransaksi dataTransaksi = new DataTransaksi(idTransaksi, idPembeli, tanggalTransaksi, namaPembeli, status);
                    daftarTransaksi.add(dataTransaksi);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainDashboardPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loadTransaksiHistoryCari(String transaksi) {
        if (conn != null) {
            daftarTransaksi.clear();
            String kueri = "SELECT * FROM tb_transaksi INNER JOIN tb_akun ON tb_akun.id_akun = tb_transaksi.id_pembeli WHERE tb_transaksi.id_transaksi LIKE ? OR tb_transaksi.tanggal_transaksi LIKE ? OR nama LIKE ?";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ps.setString(1, "%" + transaksi + "%");
                ps.setString(2, "%" + transaksi + "%");
                ps.setString(3, "%" + transaksi + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int idTransaksi = rs.getInt("id_transaksi");
                    String tanggalTransaksi = rs.getString("tanggal_transaksi");
                    int idPembeli = rs.getInt("id_pembeli");
                    String namaPembeli = rs.getString("nama");
                    int status = rs.getInt("status");
                    DataTransaksi dataTransaksi = new DataTransaksi(idTransaksi, idPembeli, tanggalTransaksi, namaPembeli, status);
                    daftarTransaksi.add(dataTransaksi);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainDashboardPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void tampilDataPanen() {
        modelHasilPanen.setRowCount(0);
        for (DataPanen b : daftarpanen) {
            modelHasilPanen.addRow(new Object[]{
                b.getNama_komoditas_panen(),
                b.getTipe_komoditas_panen(),
                b.getBerat_komoditas_panen(),
                b.getHarga_jual_perkilo(),
                b.getPetani(),
                b.getTanggal_panen()

            });
        }
    }

    void tampilDataPanen2() {
        modelTransaksiHasilPanen.setRowCount(0);
        for (DataPanen b : daftarPanenMauDiBeli) {
            modelTransaksiHasilPanen.addRow(new Object[]{
                b.getNama_komoditas_panen(),
                b.getTipe_komoditas_panen(),
                b.getBerat_komoditas_panen(),
                b.getHarga_jual_perkilo(),
                b.getPetani(),
                b.getTanggal_panen()

            });
        }
    }

    void tampilDataTransaksiHistory() {
        modelTransaksiHistory.setRowCount(0);
        for (DataTransaksi b : daftarTransaksi) {
            String status = "Belum Terproses";
            if (statusFilterTransaksi == 1) {
                status = "Terproses";
                if (b.getStatus() == 1) {
                    modelTransaksiHistory.addRow(new Object[]{
                        b.getIdTransaksi(),
                        b.getTanggalTransaksi(),
                        b.getNamaPembeli(),
                        status
                    });
                }
            } else {
                if (b.getStatus() == 0) {
                    modelTransaksiHistory.addRow(new Object[]{
                        b.getIdTransaksi(),
                        b.getTanggalTransaksi(),
                        b.getNamaPembeli(),
                        status
                    });
                }
            }

        }
    }

//    public void loadTabelHasilPanenYangMauDibeli() {
//        jTabelBarangLaku.setModel(modelTransaksiHasilPanen);
//    }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
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
        panCardHasilPanen = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTableTransaksi = new javax.swing.JTable();
        btnProsesPembelia = new javax.swing.JToggleButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTabelBarangLaku = new javax.swing.JTable();
        btnBeliHasilPanen = new javax.swing.JToggleButton();
        btnHapusHasilPanenBeli = new javax.swing.JToggleButton();
        panCardTransaksi = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTabelHistoryTransaksi = new javax.swing.JTable();
        jLabel56 = new javax.swing.JLabel();
        jrbProses = new javax.swing.JRadioButton();
        jrbSelesai = new javax.swing.JRadioButton();
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

        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 100));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

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

        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.setPreferredSize(new java.awt.Dimension(400, 100));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

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

        javax.swing.GroupLayout panCardBerandaLayout = new javax.swing.GroupLayout(panCardBeranda);
        panCardBeranda.setLayout(panCardBerandaLayout);
        panCardBerandaLayout.setHorizontalGroup(
            panCardBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panCardBerandaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );
        panCardBerandaLayout.setVerticalGroup(
            panCardBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardBerandaLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(340, Short.MAX_VALUE))
        );

        panCard.add(panCardBeranda, "panCardBeranda");

        panCardHasilPanen.setBackground(new java.awt.Color(249, 249, 249));

        jPanel9.setBackground(new java.awt.Color(249, 249, 249));

        jLabel22.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel22.setText("Hasil Panen");

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
        jScrollPane12.setViewportView(jTableTransaksi);

        btnProsesPembelia.setBackground(new java.awt.Color(0, 153, 153));
        btnProsesPembelia.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnProsesPembelia.setText("Proses Pembelian");
        btnProsesPembelia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesPembeliaActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Daftar Hasil Panen Yang Dibeli");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Daftar Hasil Panen");

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
        jScrollPane13.setViewportView(jTabelBarangLaku);

        btnBeliHasilPanen.setBackground(new java.awt.Color(0, 153, 153));
        btnBeliHasilPanen.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnBeliHasilPanen.setText("BELI PRODUK");
        btnBeliHasilPanen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeliHasilPanenActionPerformed(evt);
            }
        });

        btnHapusHasilPanenBeli.setBackground(new java.awt.Color(223, 32, 34));
        btnHapusHasilPanenBeli.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnHapusHasilPanenBeli.setText("Hapus");
        btnHapusHasilPanenBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusHasilPanenBeliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBeliHasilPanen))
                    .addComponent(jScrollPane13)
                    .addComponent(btnProsesPembelia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHapusHasilPanenBeli))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(0, 684, Short.MAX_VALUE)))
                .addGap(33, 33, 33))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(btnBeliHasilPanen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel12))
                    .addComponent(btnHapusHasilPanenBeli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProsesPembelia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panCardHasilPanenLayout = new javax.swing.GroupLayout(panCardHasilPanen);
        panCardHasilPanen.setLayout(panCardHasilPanenLayout);
        panCardHasilPanenLayout.setHorizontalGroup(
            panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panCardHasilPanenLayout.setVerticalGroup(
            panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panCard.add(panCardHasilPanen, "panCardHasilPanen");

        panCardTransaksi.setBackground(new java.awt.Color(249, 249, 249));

        jLabel23.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel23.setText("Transaksi");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Silahkan klik transaksi dibawah untuk melihat detail");

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
        jTabelHistoryTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelHistoryTransaksiMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(jTabelHistoryTransaksi);

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel56.setText("Filter Status");

        buttonGroup1.add(jrbProses);
        jrbProses.setText("Proses");
        jrbProses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbProsesMouseClicked(evt);
            }
        });

        buttonGroup1.add(jrbSelesai);
        jrbSelesai.setText("Selesai");
        jrbSelesai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbSelesaiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panCardTransaksiLayout = new javax.swing.GroupLayout(panCardTransaksi);
        panCardTransaksi.setLayout(panCardTransaksiLayout);
        panCardTransaksiLayout.setHorizontalGroup(
            panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardTransaksiLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panCardTransaksiLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panCardTransaksiLayout.createSequentialGroup()
                        .addGroup(panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panCardTransaksiLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel56)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jrbProses)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jrbSelesai))
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 863, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panCardTransaksiLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(37, 37, 37))))
        );
        panCardTransaksiLayout.setVerticalGroup(
            panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardTransaksiLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbSelesai)
                    .addComponent(jrbProses)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(29, 29, 29))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel26)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        panCardPengaturanLayout.setVerticalGroup(
            panCardPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardPengaturanLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        panCard.add(panCardPengaturan, "panCardPengaturan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
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
        loadpanen();
        tampilDataPanen();
        gantiWarnaSidePanel(sideBtnHasilPanen, panIndikatorHasilPanen);
        cardLayout.show(panCard, "panCardHasilPanen");
        indexCard = 1;
    }//GEN-LAST:event_sideBtnHasilPanenMouseClicked

    private void sideBtnTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sideBtnTransaksiMouseClicked
        loadTransaksiHistory();
        tampilDataTransaksiHistory();
        gantiWarnaSidePanel(sideBtnTransaksi, panIndikatorTransaksi);
        cardLayout.show(panCard, "panCardTransaksi");
        indexCard = 2;
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
        indexCard = 0;
    }//GEN-LAST:event_sideBtnBerandaMouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        loadpanen();
        tampilDataPanen();
        gantiWarnaSidePanel(sideBtnHasilPanen, panIndikatorHasilPanen);
        cardLayout.show(panCard, "panCardHasilPanen");
        indexCard = 1;
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
        loadTransaksiHistory();
        tampilDataTransaksiHistory();
        gantiWarnaSidePanel(sideBtnTransaksi, panIndikatorTransaksi);
        cardLayout.show(panCard, "panCardTransaksi");
        indexCard = 2;
    }//GEN-LAST:event_jPanel6MouseClicked

    private void btnBeliHasilPanenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeliHasilPanenActionPerformed
        // TODO add your handling code here:
        int bariTerpilih = jTableTransaksi.getSelectedRow();
        if (bariTerpilih == -1) {
            JOptionPane.showMessageDialog(null, "Pilih daftar hasil panen terlebih dahulu!", "Pesan", JOptionPane.ERROR_MESSAGE);
        } else {
            Transaksi transaksi = new Transaksi(this, informasilogin, bariTerpilih, daftarpanen.get(bariTerpilih));
            transaksi.setLocationRelativeTo(null);
            transaksi.setVisible(true);
        }
    }//GEN-LAST:event_btnBeliHasilPanenActionPerformed

    private void btnProsesPembeliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesPembeliaActionPerformed
        // TODO add your handling code here:
        if (jTabelBarangLaku.getRowCount() > 0) {
            if (conn != null) {
                try {
                    String kueri = "INSERT INTO tb_transaksi(id_pembeli) VALUES ('" + informasilogin.getIdAkun() + "')";
                    PreparedStatement ps = conn.prepareStatement(kueri, Statement.RETURN_GENERATED_KEYS);
                    int hasil = ps.executeUpdate();
                    if (hasil > 0) {
                        int idTransaksi = 0;
                        JOptionPane.showMessageDialog(this, "Berhasil Memproses Pembelian");
                        ResultSet rs = ps.getGeneratedKeys();
                        if (rs.next()) {
                            idTransaksi = rs.getInt(1);
                        }
                        for (int i = 0; i < daftarPanenMauDiBeli.size(); i++) {
                            int beratTotal = 0;
                            int idHasilPanen = 0;
                            for (int j = 0; j < daftarpanen.size(); j++) {
                                if (daftarpanen.get(j).getId_hasilpanen() == daftarPanenMauDiBeli.get(i).getId_hasilpanen()) {
                                    beratTotal = daftarpanen.get(j).getBerat_komoditas_panen();
                                    idHasilPanen = daftarpanen.get(j).getId_hasilpanen();
                                }
                            }
                            String kueri2 = "INSERT INTO tb_komoditas_dijual (berat, id_transaksi, id_hasil_panen) VALUES(?,?,?)";
                            String kueri3 = "UPDATE tb_hasil_panen SET berat_komoditas_panen = '" + beratTotal + "' WHERE id_hasil_panen = '" + idHasilPanen + "'";
                            PreparedStatement ps2 = conn.prepareStatement(kueri2);
                            PreparedStatement ps3 = conn.prepareStatement(kueri3);
                            ps2.setInt(1, daftarPanenMauDiBeli.get(i).getBerat_komoditas_panen());
                            ps2.setInt(2, idTransaksi);
                            ps2.setInt(3, daftarPanenMauDiBeli.get(i).getId_hasilpanen());
                            int hasil2 = ps2.executeUpdate();
                            int hasil3 = ps3.executeUpdate();
                            if (hasil2 > 0) {
                                System.out.println(daftarPanenMauDiBeli.get(i).getNama_komoditas_panen());
                            }

                            if (hasil3 > 0) {
                                System.out.println(beratTotal);
                            }
                        }
                        System.out.println(idTransaksi);
                        daftarPanenMauDiBeli.clear();

                    }

                    tampilDataPanen();
                    tampilDataPanen2();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan beli hasil panen terlebih dahulu!", "Pesan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnProsesPembeliaActionPerformed

    private void btnHapusHasilPanenBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusHasilPanenBeliActionPerformed
        // TODO add your handling code here:
        int barisTerpilih = jTabelBarangLaku.getSelectedRow();

        if (barisTerpilih == -1) {
            JOptionPane.showMessageDialog(null, "Pilih daftar hasil panen terlebih dahulu!", "Pesan", JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 0; i < daftarpanen.size(); i++) {
                if (daftarpanen.get(i).getId_hasilpanen() == daftarPanenMauDiBeli.get(barisTerpilih).getId_hasilpanen()) {
                    int berat = daftarpanen.get(i).getBerat_komoditas_panen() + daftarPanenMauDiBeli.get(barisTerpilih).getBerat_komoditas_panen();
                    daftarpanen.get(i).setBerat_komoditas_panen(berat);
                }
            }
            daftarPanenMauDiBeli.remove(barisTerpilih);
            tampilDataPanen2();
            tampilDataPanen();
        }

    }//GEN-LAST:event_btnHapusHasilPanenBeliActionPerformed

    private void jTabelHistoryTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelHistoryTransaksiMouseClicked
        // TODO add your handling code here:
        int baris = jTabelHistoryTransaksi.getSelectedRow();
        int idTransaksi = Integer.parseInt(jTabelHistoryTransaksi.getValueAt(baris, 0).toString());

        for (int i = 0; i < daftarTransaksi.size(); i++) {
            if (daftarTransaksi.get(i).getIdTransaksi() == idTransaksi) {
                DetailTransaksi dt = new DetailTransaksi(daftarTransaksi.get(i));
                dt.setVisible(true);
            }

        }
    }//GEN-LAST:event_jTabelHistoryTransaksiMouseClicked

    private void tfCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariKeyReleased
        // TODO add your handling code here:
        String keyword = tfCari.getText();
        System.out.println(keyword);
        if (indexCard == 1) {
            if (keyword.length() == 0) {
                loadpanen();
                tampilDataPanen();
            } else {
                loadpanenCari(keyword);
                tampilDataPanen();
            }
        } else if (indexCard == 2) {
            if (keyword.length() == 0) {
                loadTransaksiHistory();
                tampilDataTransaksiHistory();
            } else {
                loadTransaksiHistoryCari(keyword);
                tampilDataTransaksiHistory();
            }
        }
    }//GEN-LAST:event_tfCariKeyReleased

    private void jrbProsesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbProsesMouseClicked
        // TODO add your handling code here:
        statusFilterTransaksi = 0;
        loadTransaksiHistory();
        tampilDataTransaksiHistory();
    }//GEN-LAST:event_jrbProsesMouseClicked

    private void jrbSelesaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbSelesaiMouseClicked
        // TODO add your handling code here:
        statusFilterTransaksi = 1;
        loadTransaksiHistory();
        tampilDataTransaksiHistory();
    }//GEN-LAST:event_jrbSelesaiMouseClicked

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
    private javax.swing.JToggleButton btnBeliHasilPanen;
    private javax.swing.JToggleButton btnHapusHasilPanenBeli;
    private javax.swing.JToggleButton btnProsesPembelia;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelTotalHasilPanen;
    private javax.swing.JLabel jLabelTotalTransaksiBeli;
    private javax.swing.JLabel jLabelWelcomeHomeName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JTable jTabelBarangLaku;
    private javax.swing.JTable jTabelHistoryTransaksi;
    private javax.swing.JTable jTable11;
    private javax.swing.JTable jTableTransaksi;
    private javax.swing.JRadioButton jrbProses;
    private javax.swing.JRadioButton jrbSelesai;
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
