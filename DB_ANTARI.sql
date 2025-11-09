-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 15, 2025 at 05:41 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `antari`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`username`, `password`, `role`) VALUES
('rian', 'bebek', 'cashier'),
('vale', '123', 'Kitchen'),
('steve', '123', 'Barista\r\n'),
('rudi', '123', 'barista\r\n'),
('carol', '123', 'cashier');

-- --------------------------------------------------------

--
-- Table structure for table `detail_pesanan`
--

CREATE TABLE `detail_pesanan` (
  `Id_Detail_Pesanan` int(11) NOT NULL,
  `id_pesanan` int(11) NOT NULL,
  `No_Table` int(10) NOT NULL,
  `Id_Menu` int(10) NOT NULL,
  `Deskripsi` varchar(100) NOT NULL,
  `QTY` int(10) NOT NULL,
  `Subtotal` double(10,2) NOT NULL,
  `id_status` int(11) NOT NULL,
  `Id_Status_Makanan` int(10) DEFAULT NULL,
  `Id_Status_Minuman` int(10) DEFAULT NULL,
  `Durasi_Minuman` varchar(100) DEFAULT NULL,
  `Durasi_Makanan` varchar(100) DEFAULT NULL,
  `Durasi_Pesanan` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `detail_pesanan`
--

INSERT INTO `detail_pesanan` (`Id_Detail_Pesanan`, `id_pesanan`, `No_Table`, `Id_Menu`, `Deskripsi`, `QTY`, `Subtotal`, `id_status`, `Id_Status_Makanan`, `Id_Status_Minuman`, `Durasi_Minuman`, `Durasi_Makanan`, `Durasi_Pesanan`) VALUES
(53, 57, 4, 10, 'es dikit', 10, 220000.00, 2, 2, NULL, NULL, '00m 32s', '05m 20s'),
(54, 57, 4, 25, '', 2, 44000.00, 2, NULL, 2, '01m 46s', NULL, '05m 30s'),
(55, 57, 4, 10, '', 1, 22000.00, 2, 2, NULL, NULL, '00m 32s', '05m 20s'),
(56, 58, 3, 20, '', 12, 276000.00, 2, NULL, 2, '01m 35s', NULL, '02m 45s'),
(57, 59, 1, 31, '', 1, 20000.00, 2, NULL, 2, '01m 06s', NULL, '02m 22s'),
(58, 60, 5, 5, '', 3, 69000.00, 2, 2, NULL, NULL, '00m 49s', '01m 43s'),
(59, 60, 5, 10, '', 5, 110000.00, 2, 2, NULL, NULL, '00m 43s', '01m 52s'),
(60, 60, 5, 34, '', 1, 17000.00, 2, NULL, 2, '00m 32s', NULL, '01m 47s'),
(61, 60, 5, 29, '', 1, 20000.00, 2, NULL, 2, '00m 27s', NULL, '01m 19s'),
(62, 61, 1, 8, '', 2, 40000.00, 1, 1, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `Id_Menu` int(11) NOT NULL,
  `Nama_Menu` varchar(100) NOT NULL,
  `Kategori` varchar(100) NOT NULL,
  `Harga` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`Id_Menu`, `Nama_Menu`, `Kategori`, `Harga`) VALUES
(1, 'red velvet', 'Food', 15000.00),
(2, 'french fries', 'Beverage', 13000.00),
(3, 'Dimsum', 'Food', 15000.00),
(4, 'Mentai Rice Chiken', 'Food', 23000.00),
(5, 'Salted Egg Chicken', 'Food', 23000.00),
(6, 'Nashville Chicken', 'Food', 21000.00),
(7, 'Dimsum Mentai', 'Food', 21000.00),
(8, 'Dimsum Nashville', 'Food', 20000.00),
(9, 'Brownies Bites ', 'Food', 18000.00),
(10, 'Brownies Bites IceCream', 'Food', 22000.00),
(11, 'Antari Fries', 'Food', 13000.00),
(12, 'Nashville Fries', 'Food', 15000.00),
(13, 'Nachos Mentai', 'Food', 14000.00),
(14, 'Cireng', 'Food', 13000.00),
(15, 'Manual Brew', 'Beverage', 20000.00),
(16, 'Coffe Latte', 'Beverage', 20000.00),
(17, 'Split Shot', 'Beverage', 28000.00),
(18, 'Cappucino', 'Beverage', 20000.00),
(19, 'Ekspresso', 'Beverage', 13000.00),
(20, 'Magic', 'Beverage', 23000.00),
(21, 'Black Coffee', 'Beverage', 17000.00),
(22, 'Caramel Macchatio', 'Beverage', 22000.00),
(23, 'Antari Popcorn Koffie', 'Beverage', 24000.00),
(24, 'Koffie Cheesecake', 'Beverage', 24000.00),
(25, 'Mochaccino', 'Beverage', 22000.00),
(26, 'Kopi susu Antari', 'Beverage', 20000.00),
(27, 'Antari Creamy Koffie', 'Beverage', 20000.00),
(28, 'Kopi susu jelajah rasa', 'Beverage', 21000.00),
(29, 'Malini', 'Beverage', 20000.00),
(30, 'Antasena', 'Beverage', 18000.00),
(31, 'Kisa', 'Beverage', 20000.00),
(32, 'Sananta Orange', 'Beverage', 15000.00),
(33, 'Antaberry', 'Beverage', 17000.00),
(34, 'Butterfly pea passion', 'Beverage', 17000.00),
(35, 'Creamy Strawberry Milk', 'Beverage', 18000.00),
(39, 'Antari Creamy Popcorn', 'Beverage', 22000.00),
(40, 'Caramella Cheesecake', 'Beverage', 22000.00),
(41, 'Matcha', 'Beverage', 18000.00),
(42, 'Chocolate', 'Beverage', 16000.00),
(43, 'RedVelvet', 'Beverage', 17000.00);

-- --------------------------------------------------------

--
-- Table structure for table `pesanan`
--

CREATE TABLE `pesanan` (
  `id_pesanan` int(11) NOT NULL,
  `Tanggal` timestamp NULL DEFAULT NULL,
  `Total` double(10,2) NOT NULL,
  `Bayar` double(10,2) NOT NULL,
  `Kembalian` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pesanan`
--

INSERT INTO `pesanan` (`id_pesanan`, `Tanggal`, `Total`, `Bayar`, `Kembalian`) VALUES
(49, '2025-06-15 04:11:13', 30000.00, 40000.00, 10000.00),
(50, '2025-06-15 04:21:17', 15000.00, 16000.00, 1000.00),
(51, '2025-06-15 08:39:26', 13000.00, 300000.00, 287000.00),
(52, '2025-06-15 09:39:07', 30000.00, 400000.00, 370000.00),
(53, '2025-06-15 10:03:52', 15000.00, 15000.00, 0.00),
(54, '2025-06-15 12:04:43', 444000.00, 900000.00, 456000.00),
(55, '2025-06-15 12:05:08', 308000.00, 890000.00, 582000.00),
(56, '2025-06-15 12:05:29', 22000.00, 80000.00, 58000.00),
(57, '2025-06-15 12:12:15', 286000.00, 800000.00, 514000.00),
(58, '2025-06-15 12:14:55', 276000.00, 500000.00, 224000.00),
(59, '2025-06-15 12:15:28', 20000.00, 25000.00, 5000.00),
(60, '2025-06-15 12:16:12', 216000.00, 300000.00, 84000.00),
(61, '2025-06-15 14:47:56', 40000.00, 50000.00, 10000.00);

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `Id_Status` int(10) NOT NULL,
  `Status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`Id_Status`, `Status`) VALUES
(1, 'Preparing'),
(2, 'Done'),
(3, 'Served'),
(4, 'Waiting');

-- --------------------------------------------------------

--
-- Table structure for table `status_barista`
--

CREATE TABLE `status_barista` (
  `Id_Status_Minuman` int(10) NOT NULL,
  `Status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `status_barista`
--

INSERT INTO `status_barista` (`Id_Status_Minuman`, `Status`) VALUES
(1, 'Preparing'),
(2, 'Done');

-- --------------------------------------------------------

--
-- Table structure for table `status_dapur`
--

CREATE TABLE `status_dapur` (
  `Id_Status_Makanan` int(10) NOT NULL,
  `Status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `status_dapur`
--

INSERT INTO `status_dapur` (`Id_Status_Makanan`, `Status`) VALUES
(1, 'Preparing'),
(2, 'Done');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detail_pesanan`
--
ALTER TABLE `detail_pesanan`
  ADD PRIMARY KEY (`Id_Detail_Pesanan`),
  ADD KEY `id_pesanan` (`id_pesanan`),
  ADD KEY `id_status` (`id_status`),
  ADD KEY `Id_Menu` (`Id_Menu`),
  ADD KEY `Id_Status_Makanan` (`Id_Status_Makanan`),
  ADD KEY `Id_Status_Minuman` (`Id_Status_Minuman`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`Id_Menu`);

--
-- Indexes for table `pesanan`
--
ALTER TABLE `pesanan`
  ADD PRIMARY KEY (`id_pesanan`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`Id_Status`);

--
-- Indexes for table `status_barista`
--
ALTER TABLE `status_barista`
  ADD PRIMARY KEY (`Id_Status_Minuman`);

--
-- Indexes for table `status_dapur`
--
ALTER TABLE `status_dapur`
  ADD PRIMARY KEY (`Id_Status_Makanan`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `detail_pesanan`
--
ALTER TABLE `detail_pesanan`
  MODIFY `Id_Detail_Pesanan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `Id_Menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `pesanan`
--
ALTER TABLE `pesanan`
  MODIFY `id_pesanan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `Id_Status` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_pesanan`
--
ALTER TABLE `detail_pesanan`
  ADD CONSTRAINT `detail_pesanan_ibfk_1` FOREIGN KEY (`id_pesanan`) REFERENCES `pesanan` (`id_pesanan`),
  ADD CONSTRAINT `detail_pesanan_ibfk_2` FOREIGN KEY (`id_status`) REFERENCES `status` (`Id_Status`),
  ADD CONSTRAINT `detail_pesanan_ibfk_3` FOREIGN KEY (`Id_Menu`) REFERENCES `menu` (`Id_Menu`),
  ADD CONSTRAINT `detail_pesanan_ibfk_4` FOREIGN KEY (`Id_Status_Makanan`) REFERENCES `status_dapur` (`Id_Status_Makanan`),
  ADD CONSTRAINT `detail_pesanan_ibfk_5` FOREIGN KEY (`Id_Status_Minuman`) REFERENCES `status_barista` (`Id_Status_Minuman`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
