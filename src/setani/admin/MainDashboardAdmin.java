/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setani.admin;

import com.mysql.jdbc.Connection;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import setani.koneksi.koneksi;
import setani.generic.DataAkun;
import setani.pembeli.MainDashboardPembeli;
import setani.generic.DataPanen;
import setani.generic.DataTipeHasilPanen;
import setani.generic.DataTransaksi;
import setani.login.Login1;

/**
 *
 * @author matohdev
 */
public class MainDashboardAdmin extends javax.swing.JFrame {

    /**
     * Creates new form MainDashboardAdmin
     */
    private final CardLayout cardLayout;
    DataAkun informasilogin;
    private ArrayList<DataAkun> arrAkun = new ArrayList<>();
    ArrayList<DataPanen> daftarpanen = new ArrayList<>();
    ArrayList<DataTipeHasilPanen> daftarTipeHasilPanen = new ArrayList<>();
    ArrayList<DataTransaksi> daftarTransaksi = new ArrayList<>();
    private Connection conn;
    private DefaultTableModel modelAkun = new DefaultTableModel();
    private DefaultTableModel modelHasilPanen = new DefaultTableModel();
    private DefaultTableModel modelTipeHasilPanen = new DefaultTableModel();
    private DefaultTableModel modelTransaksiHistory = new DefaultTableModel();
    int cardPosition = 0;
    int statusFilterTransaksi = 0;

    public MainDashboardAdmin(DataAkun login) {
        initComponents();
        lblIconCariAtas.setVisible(false);
        tfCari.setVisible(false);
        cardLayout = (CardLayout) (panCard.getLayout());
        informasilogin = login;
        conn = koneksi.bukaKoneksi();
        jtHasilPanen.setModel(modelHasilPanen);
        jtTransaksi.setModel(modelTransaksiHistory);
        jtHasilPanen1.setModel(modelTipeHasilPanen);
        loadkolom();
        loadKolomAkun();
        loadDataAkun();
        loadpanen();
        tampilDataPanen();
        loadTransaksiHistory();
        tampilDataTransaksiHistory();
        loadTipeHasilPanen();
        tampilDataTipeHasilPanen();

        jLabel16.setText("Selamat datang, " + informasilogin.getNama());
        jLabel28.setText(Integer.toString(daftarpanen.size()) + " Komoditas Panen");
        jLabel33.setText(Integer.toString(daftarTransaksi.size()) + " Transaksi");
        jLabel36.setText(Integer.toString(daftarTransaksi.size()) + " Transaksi");
        jLabel39.setText(Integer.toString(arrAkun.size()) + " Akun");

        jrbProses.setSelected(true);
    }

    private void loadKolomAkun() {
        modelAkun.addColumn("No");
        modelAkun.addColumn("Username");
        modelAkun.addColumn("Nama");
        modelAkun.addColumn("Nomer Telepon");
        modelAkun.addColumn("Tipe Akun");
        modelAkun.addColumn("Status");
        modelTransaksiHistory.addColumn("Id Transaksi");
        modelTransaksiHistory.addColumn("Tanggal Transaksi");
        modelTransaksiHistory.addColumn("Pembeli");
        modelTransaksiHistory.addColumn("Status");
        modelTipeHasilPanen.addColumn("No");
        modelTipeHasilPanen.addColumn("Tipe Hasil Panen");
    }

    public void loadDataAkun() {
        if (conn != null) {
            arrAkun = new ArrayList<>();
            String kueri = "SELECT * FROM tb_akun;";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int idAkun = rs.getInt("id_akun");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String nama = rs.getString("nama");
                    String nomerTelepon = rs.getString("nomer_telepon");
                    String alamat = rs.getString("alamat");
                    int role = rs.getInt("role");
                    int status = rs.getInt("status");
                    DataAkun akun = new DataAkun(idAkun, role, status, nama, username, alamat, nomerTelepon);
                    arrAkun.add(akun);
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public void loadDataAkunCari(String keyword) {
        if (conn != null) {
            arrAkun = new ArrayList<>();
            String kueri = "SELECT * FROM tb_akun WHERE username LIKE ? OR nama LIKE ?;";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int idAkun = rs.getInt("id_akun");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String nama = rs.getString("nama");
                    String nomerTelepon = rs.getString("nomer_telepon");
                    String alamat = rs.getString("alamat");
                    int role = rs.getInt("role");
                    int status = rs.getInt("status");
                    DataAkun akun = new DataAkun(idAkun, role, status, nama, username, alamat, nomerTelepon);
                    arrAkun.add(akun);
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    void loadTransaksiHistory() {
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

    public void tampilDataAkun() {
        modelAkun.setRowCount(0);
        int nomer = 1;
        for (DataAkun a : arrAkun) {
            modelAkun.addRow(new Object[]{nomer, a.getUsername(), a.getNama(), a.getNomerTelepon(), a.getRole(), a.getStatus()});
            nomer++;
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

    //data Panen
    private void loadkolom() {
        modelHasilPanen.addColumn("nama_komoditas_panen");
        modelHasilPanen.addColumn("tipe_komoditas_panen");
        modelHasilPanen.addColumn("berat_komoditas_panen");
        modelHasilPanen.addColumn("harga_jual_kg");
        modelHasilPanen.addColumn("tanggal_panen");

    }

    void loadpanen() {
        if (conn != null) {
            daftarpanen.clear();
            String kueri = "SELECT * FROM tb_hasil_panen INNER JOIN tb_tipe_hasil_panen ON tb_tipe_hasil_panen.id_tipe_hasil_panen = tb_hasil_panen.id_tipe_hasil_panen";
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
                Logger.getLogger(MainDashboardPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void loadpanenCari(String keyword) {
        if (conn != null) {
            daftarpanen.clear();
            String kueri = "SELECT * FROM tb_hasil_panen INNER JOIN tb_tipe_hasil_panen ON tb_tipe_hasil_panen.id_tipe_hasil_panen = tb_hasil_panen.id_tipe_hasil_panen WHERE nama_komoditas_panen LIKE ? OR tipe_komoditas_panen LIKE ?";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");
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
                b.getTanggal_panen()

            });
        }
    }

    void loadTipeHasilPanen() {
        if (conn != null) {
            daftarTipeHasilPanen.clear();
            String kueri = "SELECT * FROM tb_tipe_hasil_panen";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id_tipe = rs.getInt("id_tipe_hasil_panen");
                    String tipe_komoditas_panen = rs.getString("tipe_komoditas_panen");
                    DataTipeHasilPanen data = new DataTipeHasilPanen(id_tipe, tipe_komoditas_panen);
                    daftarTipeHasilPanen.add(data);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainDashboardPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void loadTipeHasilPanenCari(String keyword) {
        if (conn != null) {
            daftarTipeHasilPanen.clear();
            String kueri = "SELECT * FROM tb_tipe_hasil_panen WHERE tipe_komoditas_panen LIKE ?";
            try {
                PreparedStatement ps = conn.prepareStatement(kueri);
                ps.setString(1, "%" + keyword + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id_tipe = rs.getInt("id_tipe_hasil_panen");
                    String tipe_komoditas_panen = rs.getString("tipe_komoditas_panen");
                    DataTipeHasilPanen data = new DataTipeHasilPanen(id_tipe, tipe_komoditas_panen);
                    daftarTipeHasilPanen.add(data);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(MainDashboardPembeli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void tampilDataTipeHasilPanen() {
        modelTipeHasilPanen.setRowCount(0);
        int i = 1;
        for (DataTipeHasilPanen b : daftarTipeHasilPanen) {
            modelTipeHasilPanen.addRow(new Object[]{
                i,
                b.getTipeHasilPanen()

            });
            i++;
        }
    }

    private void gantiWarnaSidePanel(JPanel jPanel, JPanel panelIndikator) {
        JPanel[] sideButtonElem = {sideBtnBeranda, sideBtnHasilPanen, sideBtnTransaksi, sideBtnAkun};
        JPanel[] sideButtonIndikator = {panIndikatorBeranda, panIndikatorHasilPanen, panIndikatorTransaksi, panIndikatorAkun};
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

        btnGroupFilterStatus = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        sideBtnHasilPanen = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panIndikatorHasilPanen = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        sideBtnTransaksi = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panIndikatorTransaksi = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        sideBtnAkun = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        panIndikatorAkun = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
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
        lblIconCariAtas1 = new javax.swing.JLabel();
        panCard = new javax.swing.JPanel();
        panCardBeranda = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        panCardHasilPanen = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtHasilPanen = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        jtHasilPanen1 = new javax.swing.JTable();
        btnTambahHasilPanen1 = new javax.swing.JButton();
        btnTambahHasilPanen = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        btnTambahHasilPanen2 = new javax.swing.JButton();
        btnTambahHasilPanen3 = new javax.swing.JButton();
        panCardTransaksi = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jtTransaksi = new javax.swing.JTable();
        jrbSelesai = new javax.swing.JRadioButton();
        jrbProses = new javax.swing.JRadioButton();
        jLabel56 = new javax.swing.JLabel();
        panCardAkun = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        btnTambahAkun = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTAkun = new javax.swing.JTable();
        panCardCetakLaporan = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        panCardPengaturan = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel18 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfNamaUser = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        tfUsername = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        tfPassword = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        tfKonfPassword = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        tfNoTelp = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taAlamat = new javax.swing.JTextArea();
        btnEditDataPengaturan = new javax.swing.JButton();
        btnBatalEditDataPengaturan = new javax.swing.JButton();

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
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

        sideBtnAkun.setBackground(new java.awt.Color(27, 27, 57));
        sideBtnAkun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sideBtnAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sideBtnAkunMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(254, 254, 254));
        jLabel6.setText("Akun");

        panIndikatorAkun.setOpaque(false);

        javax.swing.GroupLayout panIndikatorAkunLayout = new javax.swing.GroupLayout(panIndikatorAkun);
        panIndikatorAkun.setLayout(panIndikatorAkunLayout);
        panIndikatorAkunLayout.setHorizontalGroup(
            panIndikatorAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        panIndikatorAkunLayout.setVerticalGroup(
            panIndikatorAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_male_user_24px.png"))); // NOI18N

        javax.swing.GroupLayout sideBtnAkunLayout = new javax.swing.GroupLayout(sideBtnAkun);
        sideBtnAkun.setLayout(sideBtnAkunLayout);
        sideBtnAkunLayout.setHorizontalGroup(
            sideBtnAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBtnAkunLayout.createSequentialGroup()
                .addComponent(panIndikatorAkun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );
        sideBtnAkunLayout.setVerticalGroup(
            sideBtnAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panIndikatorAkun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sideBtnAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(sideBtnAkun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sideBtnAkun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        lblIconCariAtas1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblIconCariAtas1.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lblIconCariAtas1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIconCariAtas)
                .addGap(35, 35, 35))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIconCariAtas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(lblIconCariAtas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jPanel5.setPreferredSize(new java.awt.Dimension(180, 100));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/hasil_panenx48.png"))); // NOI18N

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("1300 Kg");

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
                    .addComponent(jLabel28))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addContainerGap(25, Short.MAX_VALUE))
            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel5);

        jPanel6.setPreferredSize(new java.awt.Dimension(205, 100));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel31.setText("Transaksi Beli");

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/transaksi_masukx48.png"))); // NOI18N

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setText("27 Transaksi");

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
                    .addComponent(jLabel33))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addContainerGap(25, Short.MAX_VALUE))
            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel6);

        jPanel7.setPreferredSize(new java.awt.Dimension(205, 100));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/transaksi_keluarx48.png"))); // NOI18N

        jLabel35.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel35.setText("Transaksi Jual");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("30 Transaksi");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addContainerGap(25, Short.MAX_VALUE))
            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel7);

        jPanel15.setPreferredSize(new java.awt.Dimension(180, 100));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel15MouseClicked(evt);
            }
        });

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/userx48.png"))); // NOI18N

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel39.setText("75 Orang");

        jLabel38.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jLabel38.setText("Akun");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39)
                .addContainerGap(25, Short.MAX_VALUE))
            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel15);

        javax.swing.GroupLayout panCardBerandaLayout = new javax.swing.GroupLayout(panCardBeranda);
        panCardBeranda.setLayout(panCardBerandaLayout);
        panCardBerandaLayout.setHorizontalGroup(
            panCardBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panCardBerandaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );
        panCardBerandaLayout.setVerticalGroup(
            panCardBerandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardBerandaLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(326, Short.MAX_VALUE))
        );

        panCard.add(panCardBeranda, "panCardBeranda");

        panCardHasilPanen.setBackground(new java.awt.Color(249, 249, 249));

        jPanel9.setBackground(new java.awt.Color(249, 249, 249));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );

        jtHasilPanen.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jtHasilPanen);

        jtHasilPanen1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(jtHasilPanen1);

        btnTambahHasilPanen1.setBackground(new java.awt.Color(1, 87, 155));
        btnTambahHasilPanen1.setForeground(new java.awt.Color(246, 246, 246));
        btnTambahHasilPanen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_add_24px.png"))); // NOI18N
        btnTambahHasilPanen1.setText("Tambah Tipe Hasil Panen");
        btnTambahHasilPanen1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambahHasilPanen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahHasilPanen1ActionPerformed(evt);
            }
        });

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

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel54.setText("Daftar Hasil Panen");

        jLabel22.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel22.setText("Hasil Panen");

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel55.setText("Daftar Tipe Hasil Panen");

        btnTambahHasilPanen2.setBackground(new java.awt.Color(1, 87, 155));
        btnTambahHasilPanen2.setForeground(new java.awt.Color(246, 246, 246));
        btnTambahHasilPanen2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_add_24px.png"))); // NOI18N
        btnTambahHasilPanen2.setText("Edit");
        btnTambahHasilPanen2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambahHasilPanen2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahHasilPanen2ActionPerformed(evt);
            }
        });

        btnTambahHasilPanen3.setBackground(new java.awt.Color(1, 87, 155));
        btnTambahHasilPanen3.setForeground(new java.awt.Color(246, 246, 246));
        btnTambahHasilPanen3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_add_24px.png"))); // NOI18N
        btnTambahHasilPanen3.setText("Edit");
        btnTambahHasilPanen3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambahHasilPanen3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahHasilPanen3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panCardHasilPanenLayout = new javax.swing.GroupLayout(panCardHasilPanen);
        panCardHasilPanen.setLayout(panCardHasilPanenLayout);
        panCardHasilPanenLayout.setHorizontalGroup(
            panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panCardHasilPanenLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panCardHasilPanenLayout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTambahHasilPanen3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahHasilPanen1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panCardHasilPanenLayout.createSequentialGroup()
                        .addGroup(panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahHasilPanen2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahHasilPanen))
                    .addComponent(jScrollPane7)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(30, 30, 30))
        );
        panCardHasilPanenLayout.setVerticalGroup(
            panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardHasilPanenLayout.createSequentialGroup()
                .addGroup(panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panCardHasilPanenLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panCardHasilPanenLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnTambahHasilPanen, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnTambahHasilPanen2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panCardHasilPanenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTambahHasilPanen1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTambahHasilPanen3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        panCard.add(panCardHasilPanen, "panCardHasilPanen");

        panCardTransaksi.setBackground(new java.awt.Color(249, 249, 249));

        jPanel10.setBackground(new java.awt.Color(249, 249, 249));

        jLabel23.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel23.setText("Transaksi");

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel57.setText("Silahkan tekan transaksi dibawah ini untuk melihat detail");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel57)
                    .addComponent(jLabel23))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jtTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtTransaksiMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(jtTransaksi);

        btnGroupFilterStatus.add(jrbSelesai);
        jrbSelesai.setText("Selesai");
        jrbSelesai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbSelesaiMouseClicked(evt);
            }
        });

        btnGroupFilterStatus.add(jrbProses);
        jrbProses.setText("Proses");
        jrbProses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbProsesMouseClicked(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel56.setText("Filter Status");

        javax.swing.GroupLayout panCardTransaksiLayout = new javax.swing.GroupLayout(panCardTransaksi);
        panCardTransaksi.setLayout(panCardTransaksiLayout);
        panCardTransaksiLayout.setHorizontalGroup(
            panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panCardTransaksiLayout.createSequentialGroup()
                .addGroup(panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panCardTransaksiLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jrbProses)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jrbSelesai))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panCardTransaksiLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 868, Short.MAX_VALUE)))
                .addGap(30, 30, 30))
        );
        panCardTransaksiLayout.setVerticalGroup(
            panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardTransaksiLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(panCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbSelesai)
                    .addComponent(jrbProses)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        panCard.add(panCardTransaksi, "panCardTransaksi");

        panCardAkun.setBackground(new java.awt.Color(249, 249, 249));

        jPanel11.setBackground(new java.awt.Color(249, 249, 249));

        jLabel24.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel24.setText("Akun");

        btnTambahAkun.setBackground(new java.awt.Color(1, 87, 155));
        btnTambahAkun.setForeground(new java.awt.Color(246, 246, 246));
        btnTambahAkun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_add_24px.png"))); // NOI18N
        btnTambahAkun.setText("Tambah Akun");
        btnTambahAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahAkunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTambahAkun)
                .addGap(32, 32, 32))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel24)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnTambahAkun, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTAkun.setAutoCreateRowSorter(true);
        jTAkun.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Username", "Nama", "Nomer Telepon", "Tipe Akun", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane9.setViewportView(jTAkun);

        javax.swing.GroupLayout panCardAkunLayout = new javax.swing.GroupLayout(panCardAkun);
        panCardAkun.setLayout(panCardAkunLayout);
        panCardAkunLayout.setHorizontalGroup(
            panCardAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panCardAkunLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 868, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        panCardAkunLayout.setVerticalGroup(
            panCardAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardAkunLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9)
                .addGap(30, 30, 30))
        );

        panCard.add(panCardAkun, "panCardAkun");

        panCardCetakLaporan.setBackground(new java.awt.Color(249, 249, 249));

        jPanel13.setBackground(new java.awt.Color(249, 249, 249));

        jLabel25.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel25.setText("Cetak Laporan");

        jButton10.setBackground(new java.awt.Color(1, 87, 155));
        jButton10.setForeground(new java.awt.Color(246, 246, 246));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/setani/gambar/icons8_add_24px.png"))); // NOI18N
        jButton10.setText("Tambah Data");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton10)
                .addGap(30, 30, 30))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(jTable10);

        javax.swing.GroupLayout panCardCetakLaporanLayout = new javax.swing.GroupLayout(panCardCetakLaporan);
        panCardCetakLaporan.setLayout(panCardCetakLaporanLayout);
        panCardCetakLaporanLayout.setHorizontalGroup(
            panCardCetakLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panCardCetakLaporanLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 868, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        panCardCetakLaporanLayout.setVerticalGroup(
            panCardCetakLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCardCetakLaporanLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10)
                .addGap(30, 30, 30))
        );

        panCard.add(panCardCetakLaporan, "panCardCetakLaporan");

        panCardPengaturan.setBackground(new java.awt.Color(249, 249, 249));

        jPanel14.setBackground(new java.awt.Color(249, 249, 249));

        jLabel26.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel26.setText("Pengaturan");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel18.setBackground(new java.awt.Color(249, 249, 249));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nama");

        tfNamaUser.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel48.setText("Username");

        tfUsername.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel49.setText("Password");

        tfPassword.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel50.setText("Konfirmasi Password");

        tfKonfPassword.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel51.setText("Nomer Telepon");

        tfNoTelp.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel53.setText("Alamat");

        taAlamat.setColumns(20);
        taAlamat.setRows(5);
        jScrollPane2.setViewportView(taAlamat);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfNamaUser, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfKonfPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfNoTelp, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel48)
                            .addComponent(jLabel49)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51)
                            .addComponent(jLabel53))
                        .addGap(0, 671, Short.MAX_VALUE)))
                .addGap(34, 34, 34))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfNamaUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfKonfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel18);

        btnEditDataPengaturan.setBackground(new java.awt.Color(1, 87, 155));
        btnEditDataPengaturan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEditDataPengaturan.setText("Edit Data");
        btnEditDataPengaturan.setPreferredSize(new java.awt.Dimension(125, 30));
        btnEditDataPengaturan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditDataPengaturanActionPerformed(evt);
            }
        });

        btnBatalEditDataPengaturan.setBackground(new java.awt.Color(223, 32, 34));
        btnBatalEditDataPengaturan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBatalEditDataPengaturan.setText("Batal");
        btnBatalEditDataPengaturan.setPreferredSize(new java.awt.Dimension(125, 30));
        btnBatalEditDataPengaturan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalEditDataPengaturanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBatalEditDataPengaturan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditDataPengaturan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
                            .addComponent(jLabel26))))
                .addGap(32, 32, 32))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatalEditDataPengaturan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditDataPengaturan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panCardPengaturanLayout = new javax.swing.GroupLayout(panCardPengaturan);
        panCardPengaturan.setLayout(panCardPengaturanLayout);
        panCardPengaturanLayout.setHorizontalGroup(
            panCardPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panCardPengaturanLayout.setVerticalGroup(
            panCardPengaturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        cardPosition = 1;
    }//GEN-LAST:event_sideBtnHasilPanenMouseClicked

    private void sideBtnTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sideBtnTransaksiMouseClicked
        gantiWarnaSidePanel(sideBtnTransaksi, panIndikatorTransaksi);
        cardLayout.show(panCard, "panCardTransaksi");
        cardPosition = 2;
    }//GEN-LAST:event_sideBtnTransaksiMouseClicked

    private void sideBtnAkunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sideBtnAkunMouseClicked
        gantiWarnaSidePanel(sideBtnAkun, panIndikatorAkun);
        cardLayout.show(panCard, "panCardAkun");
        jTAkun.setModel(modelAkun);
        tampilDataAkun();
        cardPosition = 3;
    }//GEN-LAST:event_sideBtnAkunMouseClicked

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
    }//GEN-LAST:event_sideBtnBerandaMouseClicked

    private void btnTambahHasilPanenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahHasilPanenActionPerformed
        // TODO add your handling code here:
        JFrameHasilPanen jFrameHasilPanen = new JFrameHasilPanen(this, informasilogin);
        jFrameHasilPanen.setLocationRelativeTo(null);
        jFrameHasilPanen.setVisible(true);
    }//GEN-LAST:event_btnTambahHasilPanenActionPerformed

    private void btnTambahAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahAkunActionPerformed
        // TODO add your handling code here:
        JFrameAkun jFrameAkun = new JFrameAkun(this);
        jFrameAkun.setLocationRelativeTo(null);
        jFrameAkun.setVisible(true);

    }//GEN-LAST:event_btnTambahAkunActionPerformed

    private void btnEditDataPengaturanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditDataPengaturanActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnEditDataPengaturanActionPerformed

    private void btnTambahHasilPanen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahHasilPanen1ActionPerformed
        // TODO add your handling code here:
        JFrameTipeHasilPanen frameTipeHasilPanen = new JFrameTipeHasilPanen(this);
        frameTipeHasilPanen.setLocationRelativeTo(null);
        frameTipeHasilPanen.setVisible(true);
    }//GEN-LAST:event_btnTambahHasilPanen1ActionPerformed

    private void btnTambahHasilPanen2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahHasilPanen2ActionPerformed
        // TODO add your handling code here:
        int baris = jtHasilPanen.getSelectedRow();
        if (baris >= 0) {
            JFrameHasilPanen jFrameHasilPanen = new JFrameHasilPanen(this, informasilogin, daftarpanen.get(baris));
            jFrameHasilPanen.setLocationRelativeTo(null);
            jFrameHasilPanen.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih Daftar Hasil Panen", "Pesan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTambahHasilPanen2ActionPerformed

    private void btnTambahHasilPanen3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahHasilPanen3ActionPerformed
        // TODO add your handling code here:
        int baris = jtHasilPanen1.getSelectedRow();
        if (baris >= 0) {
            JFrameTipeHasilPanen jFrameTipeHasilPanen = new JFrameTipeHasilPanen(this, daftarTipeHasilPanen.get(baris).getTipeHasilPanen(), daftarTipeHasilPanen.get(baris).getIdTipeHasilPanen());
            jFrameTipeHasilPanen.setLocationRelativeTo(null);
            jFrameTipeHasilPanen.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih Daftar Tipe Hasil Panen", "Pesan", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnTambahHasilPanen3ActionPerformed

    private void jtTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtTransaksiMouseClicked
        // TODO add your handling code here:
        int baris = jtTransaksi.getSelectedRow();
        int idTransaksi = Integer.parseInt(jtTransaksi.getValueAt(baris, 0).toString());

        for (int i = 0; i < daftarTransaksi.size(); i++) {
            if (daftarTransaksi.get(i).getIdTransaksi() == idTransaksi) {
                DetailTransaksi dt = new DetailTransaksi(daftarTransaksi.get(i), this);
                dt.setVisible(true); 
            }

        }
    }//GEN-LAST:event_jtTransaksiMouseClicked

    private void tfCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariKeyReleased
        // TODO add your handling code here:
        String keyword = tfCari.getText();
        if (cardPosition == 1) {
            loadTipeHasilPanenCari(keyword);
            loadpanenCari(keyword);
            tampilDataPanen();
            tampilDataTipeHasilPanen();
        } else if (cardPosition == 2) {
            loadTransaksiHistoryCari(keyword);
            tampilDataTransaksiHistory();
        } else if (cardPosition == 3) {
            loadDataAkunCari(keyword);
            tampilDataAkun();
        }
    }//GEN-LAST:event_tfCariKeyReleased

    private void btnBatalEditDataPengaturanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalEditDataPengaturanActionPerformed
        // TODO add your handling code here:
        tfNamaUser.setText(informasilogin.getNama());
        tfUsername.setText(informasilogin.getUsername());
        tfNoTelp.setText(informasilogin.getNomerTelepon());
        taAlamat.setText(informasilogin.getAlamat());
    }//GEN-LAST:event_btnBatalEditDataPengaturanActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        gantiWarnaSidePanel(sideBtnHasilPanen, panIndikatorHasilPanen);
        cardLayout.show(panCard, "panCardHasilPanen");
        cardPosition = 1;
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
        gantiWarnaSidePanel(sideBtnTransaksi, panIndikatorTransaksi);
        cardLayout.show(panCard, "panCardTransaksi");
        cardPosition = 2;
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
        gantiWarnaSidePanel(sideBtnTransaksi, panIndikatorTransaksi);
        cardLayout.show(panCard, "panCardTransaksi");
        cardPosition = 2;
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked
        // TODO add your handling code here:
        gantiWarnaSidePanel(sideBtnAkun, panIndikatorAkun);
        cardLayout.show(panCard, "panCardAkun");
        jTAkun.setModel(modelAkun);
        tampilDataAkun();
        cardPosition = 3;
    }//GEN-LAST:event_jPanel15MouseClicked

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
            java.util.logging.Logger.getLogger(MainDashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainDashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainDashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainDashboardAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new MainDashboardAdmin().setVisible(true);
//                MainDashboardAdmin mainDashboardAdmin = new MainDashboardAdmin();
//                mainDashboardAdmin.setLocationRelativeTo(null);
//                mainDashboardAdmin.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatalEditDataPengaturan;
    private javax.swing.JButton btnEditDataPengaturan;
    private javax.swing.ButtonGroup btnGroupFilterStatus;
    private javax.swing.JButton btnTambahAkun;
    private javax.swing.JButton btnTambahHasilPanen;
    private javax.swing.JButton btnTambahHasilPanen1;
    private javax.swing.JButton btnTambahHasilPanen2;
    private javax.swing.JButton btnTambahHasilPanen3;
    private javax.swing.JButton jButton10;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTAkun;
    private javax.swing.JTable jTable10;
    private javax.swing.JRadioButton jrbProses;
    private javax.swing.JRadioButton jrbSelesai;
    private javax.swing.JTable jtHasilPanen;
    private javax.swing.JTable jtHasilPanen1;
    private javax.swing.JTable jtTransaksi;
    private javax.swing.JLabel lblIconCariAtas;
    private javax.swing.JLabel lblIconCariAtas1;
    private javax.swing.JPanel panCard;
    private javax.swing.JPanel panCardAkun;
    private javax.swing.JPanel panCardBeranda;
    private javax.swing.JPanel panCardCetakLaporan;
    private javax.swing.JPanel panCardHasilPanen;
    private javax.swing.JPanel panCardPengaturan;
    private javax.swing.JPanel panCardTransaksi;
    private javax.swing.JPanel panIndikatorAkun;
    private javax.swing.JPanel panIndikatorBeranda;
    private javax.swing.JPanel panIndikatorHasilPanen;
    private javax.swing.JPanel panIndikatorTransaksi;
    private javax.swing.JPanel sideBtnAkun;
    private javax.swing.JPanel sideBtnBeranda;
    private javax.swing.JPanel sideBtnHasilPanen;
    private javax.swing.JPanel sideBtnKeluar;
    private javax.swing.JPanel sideBtnTransaksi;
    private javax.swing.JTextArea taAlamat;
    private javax.swing.JTextField tfCari;
    private javax.swing.JTextField tfKonfPassword;
    private javax.swing.JTextField tfNamaUser;
    private javax.swing.JTextField tfNoTelp;
    private javax.swing.JTextField tfPassword;
    private javax.swing.JTextField tfUsername;
    // End of variables declaration//GEN-END:variables
}
