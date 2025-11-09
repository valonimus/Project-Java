/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_kasir;

import Koneksi.Koneksi;
import Status_Kasir.StatusKasir_View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author valer
 */
public class Pos_Con {
    private Pos_View view;
    
    
    public void add(JTable tabel, JTextField qty, JTextField table, JTextField deskripsi, JTable detail, JTextField total, JTextField cari) {
    int row = tabel.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Please choose a menu first", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    String id_menu = tabel.getValueAt(row, 0).toString();
    String nama = tabel.getValueAt(row, 1).toString();
    String kategori = tabel.getValueAt(row, 2).toString();
    double harga = Double.parseDouble(tabel.getValueAt(row, 3).toString());

    String strQty = qty.getText().trim();
    String strTab = table.getText().trim();

    if (strQty.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please Enter The Qty First!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (strTab.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Table Number Is Required!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int QTY, tab;
    try {
        QTY = Integer.parseInt(strQty);
        if (QTY <= 0) {
            JOptionPane.showMessageDialog(null, "Qty Must Not Be Zero", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Qty Must Be Number", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        tab = Integer.parseInt(strTab);
        if (tab <= 0 || tab > 12) {
            JOptionPane.showMessageDialog(null, "Table number must be between 1 and 12!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            table.setEnabled(true);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Table Number Must Be Number!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String Desk = deskripsi.getText();
    double subtotal = QTY * harga;
    DefaultTableModel orderModel = (DefaultTableModel) detail.getModel();
    orderModel.addRow(new Object[]{tab, id_menu, nama, kategori, harga, Desk, QTY, subtotal});

    // Reset form input
    qty.setText("");
    cari.setText("");
    deskripsi.setText("");

    // Update total harga
    updateTotalFromDetail(detail, total);
    table.setEnabled(false);
    }
    
    public void updateTotalFromDetail(JTable detail, JTextField total) {
    Pos_Model m = new Pos_Model();
    DefaultTableModel model = (DefaultTableModel) detail.getModel();
        try {
            for (int i = 0; i < model.getRowCount(); i++) {
                double subtotal = Double.parseDouble(model.getValueAt(i, 7).toString());
                m.total(subtotal);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    total.setText(String.valueOf(m.getTotalharga()));
    }
   
    public void bayar(JTextField bayar, JTextField total, JTextField kembalian, JTable detail, JTextField table){
        try {
            if (bayar.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Pay First!","Infomation", JOptionPane.ERROR_MESSAGE);
                return;
            }if (total.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Buy First!","Information", JOptionPane.ERROR_MESSAGE);
            }
            double Total = Double.parseDouble(total.getText());
            double Bayar = Double.parseDouble(bayar.getText());
            if (Bayar < Total) {
                JOptionPane.showMessageDialog(null, "Not enough money!","Information", JOptionPane.ERROR_MESSAGE);
                kembalian.setText("");
                return;
            } 
            Double Kembalian = Bayar - Total;
            kembalian.setText(String.valueOf(Kembalian));
            JOptionPane.showMessageDialog(null, "Payment Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
           
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Must Be Number!","Information", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error lain: " + e.getMessage());
        }
    }
    public void simpanPesananKeDatabase(JTable detail, JTextField total, JTextField bayar, JTextField kembalian, JTextField table) {
    try {
        Connection con = Koneksi.getConnection();
        con.setAutoCommit(false);
        if (bayar.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Pay First!","Information", JOptionPane.ERROR_MESSAGE);
                return;
            }
        if (total.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Buy First!","Information", JOptionPane.ERROR_MESSAGE);
                return;
            }
        if (kembalian.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Pay First!","Information", JOptionPane.ERROR_MESSAGE);
                return;
            }
        // Parse nilai-nilai dari JTextField
        double totalValue = Double.parseDouble(total.getText());
        double bayarValue = Double.parseDouble(bayar.getText());
        double kembalianValue = Double.parseDouble(kembalian.getText());

        // 1. Insert ke tabel pesanan
        String sqlPesanan = "INSERT INTO pesanan (tanggal, total, bayar, kembalian) VALUES (?, ?, ?, ?)";
        PreparedStatement psPesanan = con.prepareStatement(sqlPesanan, Statement.RETURN_GENERATED_KEYS);
        

        psPesanan.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        psPesanan.setDouble(2, totalValue);
        psPesanan.setDouble(3, bayarValue);
        psPesanan.setDouble(4, kembalianValue);
        psPesanan.executeUpdate();

        ResultSet rs = psPesanan.getGeneratedKeys();
        int idPesanan = 0;
        if (rs.next()) {
            idPesanan = rs.getInt(1);
            System.out.println("ID Pesanan baru: " + idPesanan);
            } else {
            System.out.println("Gagal mendapatkan ID pesanan");
        }
        
       

        // 2. Insert detail pesanan
        String sqlDetail = "INSERT INTO detail_pesanan (id_pesanan, no_table, id_menu, deskripsi, qty, subtotal, id_status, id_status_makanan, id_status_minuman) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement psDetail = con.prepareStatement(sqlDetail);
        
        int idMakanan = getIdDapurByName("Preparing");
        int idStatus = getIdStatusByName("Preparing");
        int idMinuman = getIdBaristaByName("Preparing");
        
        DefaultTableModel model = (DefaultTableModel) detail.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            
            psDetail.setInt(1, idPesanan);
            psDetail.setInt(2, Integer.parseInt(model.getValueAt(i, 0).toString())); // no table
            psDetail.setInt(3, Integer.parseInt(model.getValueAt(i, 1).toString())); // id menu
            psDetail.setString(4, model.getValueAt(i, 5).toString()); //deskripsi
            String kategori = model.getValueAt(i, 3).toString();
            psDetail.setInt(5, Integer.parseInt(model.getValueAt(i, 6).toString())); // qty
            psDetail.setDouble(6, Double.parseDouble(model.getValueAt(i, 7).toString())); // subtotal
            psDetail.setInt(7, idStatus); // id_status umum

            if (kategori.equalsIgnoreCase("food")) {
                psDetail.setInt(8, idMakanan); // id_status_makanan
                psDetail.setNull(9, java.sql.Types.INTEGER); // id_status_minuman NULL
                } else {
                psDetail.setNull(8, java.sql.Types.INTEGER); // id_status_makanan NULL
                psDetail.setInt(9, idMinuman); // id_status_minuman
                }

        psDetail.addBatch();
        }
        psDetail.executeBatch();

        con.commit();

        // Bersihkan inputan GUI
        model.setRowCount(0);
        total.setText("");
        bayar.setText("");
        kembalian.setText("");
        table.setText("");

        JOptionPane.showMessageDialog(null, "Order Has Been Successfully Saved To The Database!");

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed To Save To Database: " + e.getMessage());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Must Be Number: " + e.getMessage());
    }
    }
    

    private int getIdStatusByName(String statusNama) throws SQLException {
    Connection con = Koneksi.getConnection();
    String query = "SELECT id_status FROM status WHERE status = ?";
    PreparedStatement ps = con.prepareStatement(query);
    ps.setString(1, statusNama);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return rs.getInt("id_status");
    } else {
        throw new SQLException("Status Not Found: " + statusNama);
    }
    }
    
    private int getIdDapurByName(String status) throws SQLException {
    Connection con = Koneksi.getConnection();
    String query = "SELECT id_status_makanan FROM status_dapur WHERE status = ?";
    PreparedStatement ps = con.prepareStatement(query);
    ps.setString(1, status);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return rs.getInt("id_status_makanan");
    } else {
        throw new SQLException("Status Not Found: " + status);
    }
    }
    
    private int getIdBaristaByName(String status) throws SQLException {
    Connection con = Koneksi.getConnection();
    String query = "SELECT id_status_minuman FROM status_barista WHERE status = ?";
    PreparedStatement ps = con.prepareStatement(query);
    ps.setString(1, status);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return rs.getInt("id_status_minuman");
    } else {
        throw new SQLException("Status tidak ditemukan: " + status);
    }
}

    public void search(JTable tabel, JTextField cari){
      DefaultTableModel ob = (DefaultTableModel) tabel.getModel();
      TableRowSorter<DefaultTableModel> obj=new TableRowSorter<>(ob);//untuk mengurutkan dan memfilter baris dalam tabel
      tabel.setRowSorter(obj);
      obj.setRowFilter(RowFilter.regexFilter("(?i)"+ cari.getText()));
    }
    public void tanggal(JTextField date){
        Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = now.format(formatter);
            date.setText(formattedDate);
        }
    });
    timer.start();
    }
    public void tampildata(JTable tabel){
        DefaultTableModel Tabel = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
        Tabel.addColumn("Id_Menu");
        Tabel.addColumn("Menu");
        Tabel.addColumn("Category");
        Tabel.addColumn("Price");
        
        try {
            Connection con = Koneksi.getConnection();
            String sql = "select * from menu";
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            
            while (rs.next()) {
                Tabel.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)});
            }
            tabel.setModel(Tabel);
        } catch (Exception e) {
            System.out.println("");
        }
    }
   
}
