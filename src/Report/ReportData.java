/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Report;

/**
 *
 * @author valer
 */
public class ReportData {
    private String idPesanan;
    private String namaMenu;
    private String qty;
    private String durasiPesanan;
    private String subtotal;
    private double total;
    private String idpesanan;
    private String namamenu;
    private String durasi;
    private String tanggal;
    private String favorite;

    public ReportData(String idPesanan, String namaMenu, String qty, String durasiPesanan, String subtotal, double total, String idpesanan, String namamenu, String durasi, String tanggal, String favorite) {
        this.idPesanan = idPesanan;
        this.namaMenu = namaMenu;
        this.qty = qty;
        this.durasiPesanan = durasiPesanan;
        this.subtotal = subtotal;
        this.total = total;
        this.idpesanan = idpesanan;
        this.namamenu = namamenu;
        this.durasi = durasi;
        this.tanggal = tanggal;
        this.favorite = favorite;
    }

   

    public String getIdPesanan() { return idPesanan; }
    public String getNamaMenu() { return namaMenu; }
    public String getQty() { return qty; }
    public String getDurasiPesanan() { return durasiPesanan; }
    public String getSubtotal() { return subtotal; }
    public double getTotal() { return total; }
    public String getIdpesanan() { return idpesanan; }
    public String getNamamenu() {return namamenu;}
    public String getdurasi() {return durasi;}
    public String gettanggal() {return tanggal;}
    public String getfavorite() {return favorite;}
}
