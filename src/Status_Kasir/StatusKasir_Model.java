/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Status_Kasir;

import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author valer
 */
public class StatusKasir_Model {
    public DefaultTableModel getData() {
        DefaultTableModel Tabel = new DefaultTableModel(){
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Cegah edit manual dari JTable
        }
    };
        Tabel.addColumn("Order_Id");
        Tabel.addColumn("No.Table");
        Tabel.addColumn("Menu");
        Tabel.addColumn("QTY");
        Tabel.addColumn("Status Order");
        Tabel.addColumn("Food Status");
        Tabel.addColumn("Beverage Status");
        Tabel.addColumn("Order Time");
        Tabel.addColumn("Duration");
 

        try {
            Connection con = Koneksi.getConnection();
            String sql = "SELECT d.id_pesanan, d.no_table, mn.nama_menu AS nama, d.qty, s.status AS status_pesanan, " +
                         "r.status AS status_makanan, m.status AS status_minuman " +
                         "FROM detail_pesanan d " +
                         "JOIN menu mn ON d.id_menu = mn.id_menu " +
                         "JOIN status s ON d.id_status = s.id_status " +
                         "JOIN pesanan p ON d.id_pesanan = p.id_pesanan " +
                         "LEFT JOIN status_dapur r ON d.id_status_makanan = r.id_status_makanan " +
                         "LEFT JOIN status_barista m ON d.id_status_minuman = m.id_status_minuman " +
                         "WHERE DATE(p.tanggal) = CURDATE()" +
                         "ORDER BY d.id_pesanan ASC ";
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);

            while (rs.next()) {
                String waktumakanan = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Tabel.addRow(new Object[]{
                    rs.getString("id_pesanan"),
                    rs.getString("no_table"),
                    rs.getString("nama"),
                    rs.getString("qty"),
                    rs.getString("status_pesanan"),
                    rs.getString("status_makanan"),
                    rs.getString("status_minuman"),
                    waktumakanan.toString(),  // kolom waktu dipesan (bisa disembunyikan nanti)
                    ""                      
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Tabel;
    }
}
