/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Report;

import java.io.File;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

/**
 *
 * @author valer
 */
public class EmailUtil {
    public static void sendEmailWithAttachment(String[] toEmail, String filePath) {
        System.setProperty("https.protocols", "TLSv1.2"); // memaksa semua koneksi menggunakan TLSv1.2
        System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        
        final String fromEmail = "682023006@student.uksw.edu"; // email
        final String password = "dfcl ewpe wgph cmcw";// App Password Gmail
        
        for (String email : toEmail) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            
             if (!(email.endsWith("@gmail.com") || email.endsWith("@student.uksw.edu"))) {
            JOptionPane.showMessageDialog(null, "Domain tidak diizinkan: " + email, "Error", JOptionPane.ERROR_MESSAGE);
            return;
            }
             
            } catch (AddressException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Email: " + email, "Error", JOptionPane.ERROR_MESSAGE);
            return;
            }
        }
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");// hanya bisa untuk gmail
        props.put("mail.smtp.port", "587");// sebagai port
        props.put("mail.smtp.auth", "true");// mengaktifkan autentikasi
        props.put("mail.smtp.starttls.enable", "true"); // aktifkan STARTTLS
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // paksa TLS 1.2

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            InternetAddress[] recipientAddresses = new InternetAddress[toEmail.length];
            for (int i = 0; i < toEmail.length; i++) {
                recipientAddresses[i] = new InternetAddress(toEmail[i]);
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddresses);

            message.setSubject("Laporan hasil penjualan Antari Space");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Berikut ini laporan hasil penjualan dalam bentuk pdf.");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePath));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email berhasil dikirim ke: " + String.join(", ", toEmail));//untuk menggabungkan string dalam beberapa list
            JOptionPane.showMessageDialog(null, "Email Already Sent: " + String.join(", ", toEmail));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengirim email: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
