/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dapur;

import Koneksi.Koneksi;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author valer
 */
public class DapurModel {
     
    public DefaultTableModel getDataMakanan() {
        DefaultTableModel Tabel = new DefaultTableModel(){
    };
        
        Tabel.addColumn("Order_Id");
        Tabel.addColumn("Menu");
        Tabel.addColumn("QTY");
        Tabel.addColumn("Description");
        Tabel.addColumn("Status Order");
        Tabel.addColumn("Food Status");
        Tabel.addColumn("Order Time");
        Tabel.addColumn("Duration");

        try {
            Connection con = Koneksi.getConnection();
            String sql = "SELECT d.id_pesanan, m.nama_menu AS menu, d.qty, s.status AS status_pesanan, r.status AS makanan, d.deskripsi " +
                         "FROM detail_pesanan d " +
                         "JOIN status s ON d.id_status = s.id_status " +
                         "JOIN status_dapur r ON d.id_status_makanan = r.id_status_makanan " +
                         "JOIN pesanan p ON d.id_pesanan = p.id_pesanan " +
                         "JOIN menu m ON d.id_menu = m.id_menu " +
                         "WHERE m.kategori = 'food' AND DATE(p.tanggal) = CURDATE()" +
                         "ORDER BY d.id_pesanan ASC ";
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);

            while (rs.next()) {
                String waktuSekarang = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Tabel.addRow(new Object[]{
                    rs.getString("id_pesanan"),
                    rs.getString("menu"),
                    rs.getString("qty"),
                    rs.getString("deskripsi"),
                    rs.getString("status_pesanan"),
                    rs.getString("makanan"),
                    waktuSekarang.toString(),  
                    ""                      
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Tabel;
    }
}
