 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Status_Kasir;

import Dapur.DapurModel;
import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author valer
 */
public class StatusKasir_Con {
    public void updatstatus(JTable tabel){
        int row = tabel.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Please Choose A Menu First!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    
   if (!"DONE".equalsIgnoreCase(
        tabel.getValueAt(row, 5) != null ? tabel.getValueAt(row, 5).toString().trim() : ""
    ) &&
    !"DONE".equalsIgnoreCase(
        tabel.getValueAt(row, 6) != null ? tabel.getValueAt(row, 6).toString().trim() : ""
    )) {
    
    JOptionPane.showMessageDialog(null, "Food or drink status is not yet completed!", "Peringatan", JOptionPane.WARNING_MESSAGE);
    return;
    }
    // Ambil id_pesanan dan nama_menu dari tabel
    String idPesanan = tabel.getValueAt(row, 0).toString();
    String namaMenu = tabel.getValueAt(row, 2).toString();

    int idStatusBaru = 2; 
    String status = "Done";
    String durasi = "00m 00s";

       try (Connection con = Koneksi.getConnection()) {
        // Ambil waktu mulai dari pesanan
        String sqlWaktu = "SELECT tanggal FROM pesanan WHERE id_pesanan = ?";
        Timestamp waktuDipesan = null;
        try (PreparedStatement psWaktu = con.prepareStatement(sqlWaktu)) {
            psWaktu.setString(1, idPesanan);
            ResultSet rsWaktu = psWaktu.executeQuery();
            if (rsWaktu.next()) {
                waktuDipesan = rsWaktu.getTimestamp("tanggal");
            }
        }

        if (waktuDipesan != null) {
            long diff = System.currentTimeMillis() - waktuDipesan.getTime();
            long menit = diff / 60000;
            long detik = (diff / 1000) % 60;
            durasi = String.format("%02dm %02ds", menit, detik);
        }

        // Update status dan durasi
        String sqlUpdate = "UPDATE detail_pesanan SET id_status = ?, durasi_pesanan = ? " +
                           "WHERE id_pesanan = ? AND id_menu = (SELECT id_menu FROM menu WHERE nama_menu = ? LIMIT 1)";
        
        try (PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
            ps.setInt(1, idStatusBaru);
            ps.setString(2, durasi);
            ps.setString(3, idPesanan);
            ps.setString(4, namaMenu);

            int hasil = ps.executeUpdate();
            if (hasil > 0) {
                JOptionPane.showMessageDialog(null, "Food status changed to \"" + status + "\"", "Success", JOptionPane.INFORMATION_MESSAGE);
                DefaultTableModel model = (DefaultTableModel) tabel.getModel();
                model.setValueAt(status, row, 4);
                model.setValueAt(durasi, row, 8); // Update kolom durasi di tabel
                tampilkandata(tabel);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to change status.", "Failed", JOptionPane.ERROR_MESSAGE);
            }
         }
        } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
    }
       
    public void tampilkandata(JTable tabel){
        StatusKasir_Model model = new StatusKasir_Model();
        DefaultTableModel data = model.getData();
        tabel.setModel(data);
      tabel.getColumnModel().getColumn(7).setMinWidth(0);
     tabel.getColumnModel().getColumn(7).setMaxWidth(0);
      tabel.getColumnModel().getColumn(7).setWidth(0);
    }
}
