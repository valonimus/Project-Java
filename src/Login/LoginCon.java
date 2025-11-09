/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;


import Barista.BaristaView;
import Dapur.Dapur_View;
import Koneksi.Koneksi;
import home_kasir.homeview;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author valer
 */
public class LoginCon {
    private LoginModel model;
    private LoginView view;
    
    
    public LoginModel check(JPasswordField password, JCheckBox box){
        LoginView view = new LoginView();
        if(box.isSelected()){
            password.setEchoChar ((char)0);
            box.setText("Hide Password");
        }
        else{
            password.setEchoChar('*');
            box.setText("Show Password");
        }
        return model;
    }
    
    public LoginModel login(LoginView view, String username, String password, String Role){
        if(username.equals("")||password.equals("")){
            JOptionPane.showMessageDialog(null, "Please make sure the username and password have been entered.","Perhatian", JOptionPane.WARNING_MESSAGE);
        }else{
            try {
                LoginModel model = new LoginModel();
                model.setUsername(username);
                model.setPassword(password);
                model.setRole(Role);
                String SQL = ("select * from admin where binary username = '" + model.getUsername() + "' and binary password = '"+model.getPassword()+"'");// * mengambil semua di tabel.
                Connection conn = Koneksi.getConnection();
                Statement stat = conn.createStatement();
                ResultSet ress = stat.executeQuery(SQL);

                if (ress.next()) {//next itu untuk melihat inputan username sudah benar atau belum
                    String role = ress.getString("role");
                        
                    if (!role.trim().equalsIgnoreCase(Role.trim())) {
                    JOptionPane.showMessageDialog(null, "Role does not match the account.! " + role, " Login Failed", JOptionPane.ERROR_MESSAGE);
                    return null;
}
                    if(role.trim().equalsIgnoreCase("Cashier")){
                        homeview v = new homeview();
                        v.setVisible(true);
                        String nama = ress.getString("username");
                        v.setNamaUser(nama);
                        view.dispose();
                        JOptionPane.showMessageDialog(null, "Login successful as " + role,  "Info", JOptionPane.INFORMATION_MESSAGE);
                    }else if(role.trim().equalsIgnoreCase("Kitchen")){
                        new Dapur_View().setVisible(true);
                        view.dispose();
                        JOptionPane.showMessageDialog(null, "Login successful as " + role,  "Info", JOptionPane.INFORMATION_MESSAGE);
                    }else if(role.trim().equalsIgnoreCase("Barista")){
                        new BaristaView().setVisible(true);
                        view.dispose();
                        JOptionPane.showMessageDialog(null, "Login successful as " + role,  "Info", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "Unknown role " + role, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Failed Login", "Attention", JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return model;
    }
    }