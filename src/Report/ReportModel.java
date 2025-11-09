/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Report;

import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author valer
 */
public class ReportModel {
    public DefaultTableModel getData(String filterTanggal) {
        
        DefaultTableModel Tabel = new DefaultTableModel(){
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Cegah edit manual dari JTable
        }
    };
        Tabel.addColumn("Order ID");
        Tabel.addColumn("Date");
        Tabel.addColumn("Menu");
        Tabel.addColumn("QTY");
        Tabel.addColumn("Duration");
        Tabel.addColumn("SubTotal");
        Tabel.addColumn("Total");
 

        try {
        Connection con = Koneksi.getConnection();

        String whereClause = "";
        if (filterTanggal.equalsIgnoreCase("Today")) {
            whereClause = "WHERE DATE(p.tanggal) = CURDATE()";
        } else if (filterTanggal.equalsIgnoreCase("Last Month")) {
            whereClause = "WHERE MONTH(p.tanggal) = MONTH(CURDATE() - INTERVAL 1 MONTH) " +
                          "AND YEAR(p.tanggal) = YEAR(CURDATE() - INTERVAL 1 MONTH)";
        }else if (filterTanggal.equalsIgnoreCase("Last 30 Days")) {
            whereClause = "WHERE p.tanggal >= CURDATE() - INTERVAL 30 DAY";
        } else {
            whereClause = ""; // "All" tidak pakai filter
        }

        String sql = "SELECT d.id_pesanan,p.tanggal, mn.nama_menu AS nama, d.qty, d.subtotal, p.total, d.durasi_pesanan " +
                     "FROM detail_pesanan d " +
                     "JOIN menu mn ON d.id_menu = mn.id_menu " +
                     "JOIN status s ON d.id_status = s.id_status " +
                     "JOIN pesanan p ON d.id_pesanan = p.id_pesanan " +
                     whereClause + 
                     " ORDER BY d.id_pesanan ASC";

        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(sql);

        while (rs.next()) {
            Tabel.addRow(new Object[]{
                rs.getString("id_pesanan"),
                rs.getString("tanggal"),
                rs.getString("nama"),
                rs.getString("qty"),
                rs.getString("durasi_pesanan"),
                rs.getString("subtotal"),
                rs.getString("total")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

        return Tabel;
    }
}
