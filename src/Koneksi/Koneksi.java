/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author valer
 */
public class Koneksi {
    public static Connection getConnection(){
         Connection conn = null;
         try {
            String url = "jdbc:mysql://localhost:3306/antari";
            String username = "root";
            String password = "";
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("koneksi berhasil");
        } catch (SQLException e){
            System.out.println("koneksi gagal ges" + e);
        }
         return conn;
        //konidisnya null kalo kondisinya berhasil tidak null
    }
    public static void main(String[] args) {
        getConnection();
        // TODO code application logic here
        
    }
    
}
