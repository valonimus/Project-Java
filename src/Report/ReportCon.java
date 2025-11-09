/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Report;

import Status_Kasir.StatusKasir_Model;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author valer
 */
public class ReportCon {
    public void tampilkandata(JTable tabel, String filterTanggal){
        ReportModel model = new ReportModel();
        DefaultTableModel data = model.getData(filterTanggal);
        tabel.setModel(data);
    }
    public JRBeanCollectionDataSource getDataSourceFromTable(JTable table, JTextField idpesanan, JTextField menu, JTextField dur, JTextField favorite) {
    List<ReportData> list = new ArrayList<>();
    DefaultTableModel model = (DefaultTableModel) table.getModel();

    for (int i = 0; i < table.getRowCount(); i++) {
    int modelIndex = table.convertRowIndexToModel(i); // untuk memasukkan data ke jrbeancollection dengan mengambil data di tabel menurut urutan bukan data default

    String id = model.getValueAt(modelIndex, 0).toString();
    String tanggal = model.getValueAt(modelIndex, 1).toString();
    String nama = model.getValueAt(modelIndex, 2).toString();
    String qty = model.getValueAt(modelIndex, 3).toString();
    String durasi = model.getValueAt(modelIndex, 4).toString();
    String subtotal = model.getValueAt(modelIndex, 5).toString();
    Double total = Double.parseDouble(model.getValueAt(modelIndex, 6).toString());

    String idpes = idpesanan.getText();
    String men = menu.getText();
    String dura = dur.getText();
    String fav = favorite.getText();

    list.add(new ReportData(id, nama, qty, durasi, subtotal, total, idpes, men, dura, tanggal, fav));
    }

    return new JRBeanCollectionDataSource(list);
    }
    
    private String toStr(Object val) {
    return val == null ? "" : val.toString();
    }
        
    public void exportToPDF(JTable table, String outputPath, String filterLabel, JTextField idpesanan, JTextField menu, JTextField dur, JTextField favorite) {
    try {
        JRBeanCollectionDataSource dataSource = getDataSourceFromTable(table, idpesanan, menu, dur, favorite);

        // Load jasper template
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("src/report/report.jasper");

        // Kirim parameter ke laporan Jasper
        Map<String, Object> param = new HashMap<>(); // untuk menyimpan key value dari string dan object
        param.put("FilterTanggal", filterLabel);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, param, dataSource);

        JasperExportManager.exportReportToPdfFile(print, outputPath);

        JOptionPane.showMessageDialog(null, "PDF created successfully!");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to created PDF: " + e.getMessage());
    }
}
    public void setMaxDurasiFormatted(JTable table, JTextField textField, JTextField namamenu, JTextField id) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    int maxDetik = 0;
    String durasiTerpanjang = "";
    String namaMenu = "";
    String idpesanan = "";

    for (int i = 0; i < model.getRowCount(); i++) {
        Object val = model.getValueAt(i, 4); // ambil dari kolom ke-4

        if (val == null) continue;
        String durasi = val.toString().trim();
        if (durasi.isEmpty()) continue;

        int totalDetik = quickParseDurasi(durasi);
        if (totalDetik > maxDetik) {
            maxDetik = totalDetik;
            durasiTerpanjang = durasi;
            namaMenu = table.getValueAt(i, 2).toString();
            idpesanan = table.getValueAt(i, 0).toString();
            
        }
        
    }

    textField.setText(durasiTerpanjang); // hasil ditampilkan ke TextField
    namamenu.setText(namaMenu);
    id.setText(idpesanan);
    }
  
    
    private int quickParseDurasi(String durasiStr) {
    try {
        String[] parts = durasiStr.replace("m", "").replace("s", "").split(" ");
        int menit = Integer.parseInt(parts[0].trim());
        int detik = Integer.parseInt(parts[1].trim());
        return menit * 60 + detik;
    } catch (Exception e) {
        return 0;
    }
    }
    
    public void sortByLongestDuration(JTable table) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    table.setRowSorter(sorter);

    sorter.setComparator(4, (a, b) -> quickParseDurasi(b.toString()) - quickParseDurasi(a.toString())); // descending
    sorter.toggleSortOrder(4); // kolom ke-4 = kolom durasi
}
    public void setMenuFavorit(JTable table, JTextField favorite) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();

    String favorit = "";
    int qtyterbanyak = 0;

    for (int i = 0; i < model.getRowCount(); i++) {
        String menu = model.getValueAt(i,2).toString(); // kolom nama menu
        int qtytotal = 0;

        for (int j = 0; j < model.getRowCount(); j++) {
            if (menu.equals(model.getValueAt(j, 2).toString())) {
                try {
                    qtytotal += Integer.parseInt(model.getValueAt(j, 3).toString()); // kolom qty
                    System.out.println("Menu: " + menu + ", QTY: " + model.getValueAt(j, 3));
                } catch (Exception e) {
                    // skip jika qty tidak valid
                }
            }
        }

        if (qtytotal > qtyterbanyak) {
            favorit = menu;
            qtyterbanyak = qtytotal;
        }
    }

    favorite.setText(favorit + " (" + qtyterbanyak + "x)"); // tampilkan ke TextField
    
    }

}
