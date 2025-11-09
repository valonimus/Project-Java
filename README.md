## 📋 Prasyarat (Prerequisites)

Sebelum menjalankan proyek ini, pastikan komputer Anda telah terinstal *software* berikut:

* **Java Development Kit (JDK)** (Minimal versi 8, disarankan versi [sebutkan versi yang kamu pakai, misal: 17]).
* **Apache NetBeans IDE** (Disarankan versi terbaru).
* **MySQL Server** (Bisa menggunakan paket **XAMPP** atau instalasi MySQL standalone).
  
## 🗄️ Persiapan Database

Proyek ini memerlukan database agar dapat berjalan. File SQL telah disertakan di dalam repo ini dengan nama DB_ANTARI.sql

1.  Aktifkan layanan **MySQL** dan **Apache** (jika perlu) pada XAMPP Control Panel Anda.
2.  Buka **phpMyAdmin** di browser (biasanya di `http://localhost/phpmyadmin`).
3.  Buat database baru dengan nama: `[nama_database_anda]`
    > **Penting:** Pastikan nama database sama persis agar tidak perlu mengubah kodingan koneksi.
4.  Pilih database yang baru dibuat, lalu klik tab **Import**.
5.  Klik **Choose File**, arahkan ke file SQL 
6.  Klik **Go** atau **Kirim** untuk melakukan impor tabel dan data.

## ⚙️ Konfigurasi Koneksi (Jika Diperlukan)

Jika Anda menggunakan *username* atau *password* MySQL yang berbeda (default XAMPP biasanya user: `root`, password: kosong), silakan sesuaikan konfigurasi di kodingan Java:

* Buka file: `src/[package_anda]/KoneksiDB.java` (atau nama file koneksimu).
* Ubah bagian berikut sesuai pengaturan lokal Anda:
    ```java
    String url = "jdbc:mysql://localhost:3306/[nama_database_anda]";
    String user = "root"; // Sesuaikan username database lokal Anda
    String pass = "";     // Sesuaikan password database lokal Anda
    ```

## 🚀 Cara Menjalankan (Installation & Run)

1.  **Clone** atau **Download ZIP** repositori ini ke komputer Anda.
2.  Buka **Apache NetBeans IDE**.
3.  Pilih menu **File > Open Project...** (atau tekan `Ctrl + Shift + O`).
4.  Arahkan ke folder tempat Anda menyimpan hasil download repositori ini. NetBeans akan mengenali ikon proyeknya (ikon cangkir kopi).
5.  Klik **Open Project**.
6.  Setelah proyek terbuka di panel kiri, klik kanan pada nama proyek, lalu pilih **Clean and Build**.
    > Ini akan memastikan semua library dan dependensi termuat dengan benar.
7.  Untuk menjalankan, klik kanan pada file utama (misalnya `Main.java` atau `LoginForm.java`) lalu pilih **Run File**, atau cukup klik tombol **Play** (Run Project) hijau di toolbar jika *Main Class* sudah diatur.

## 📚 Dokumentasi Tambahan

Untuk detail lengkap mengenai analisis sistem, diagram alur, dan manfaat proyek, silakan baca laporannya di sini:
* [📄 Laporan Lengkap Proyek (PDF)](docs/Laporan_Akhir_Kelompok_4.pdf)
  
created by: Valerian
