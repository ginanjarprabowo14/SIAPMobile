-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 19, 2018 at 06:25 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_loginRegister`
--

-- --------------------------------------------------------

--
-- Table structure for table `jawaban_tiket`
--

CREATE TABLE `jawaban_tiket` (
  `id` int(11) NOT NULL,
  `no_tiket` varchar(25) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(25) NOT NULL,
  `isi` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jawaban_tiket`
--

INSERT INTO `jawaban_tiket` (`id`, `no_tiket`, `created_date`, `created_by`, `isi`) VALUES
(1, 'K53124HH129', '2018-03-22 06:00:00', 'ginanjarpr', 'Okay pak laporan sudah diterima oleh SKPD dan harap menunggu pengumuman lebih lanjut terkait pembangunan jembatan ancol.'),
(6, 'K53124HH125', '2018-04-03 19:35:27', 'sumsit', 'kkkkk');

-- --------------------------------------------------------

--
-- Table structure for table `nik`
--

CREATE TABLE `nik` (
  `id` int(11) NOT NULL,
  `nik` varchar(25) NOT NULL,
  `nama_lengkap` varchar(35) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `nik`
--

INSERT INTO `nik` (`id`, `nik`, `nama_lengkap`) VALUES
(1, '5214100115', 'Ginanjar Prabowo'),
(2, '5214100117', 'Rysma Aditya Widjaja'),
(3, '5214100119', 'Rama Rahmanda'),
(4, '5214100129', 'Garuh');

-- --------------------------------------------------------

--
-- Table structure for table `tiket`
--

CREATE TABLE `tiket` (
  `id` int(11) NOT NULL,
  `no_tiket` varchar(25) NOT NULL,
  `nama_pengadu` varchar(35) NOT NULL,
  `alamat_pengadu` varchar(50) NOT NULL,
  `no_telepon_pengadu` varchar(15) NOT NULL,
  `topik_aduan` varchar(25) NOT NULL,
  `isi_aduan` varchar(250) NOT NULL,
  `status_aduan` varchar(1) NOT NULL,
  `pool_terima` varchar(25) NOT NULL,
  `skpd_balik` varchar(25) NOT NULL,
  `skpd_terima` varchar(25) NOT NULL,
  `departemen` varchar(25) NOT NULL,
  `status_jawab` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tiket`
--

INSERT INTO `tiket` (`id`, `no_tiket`, `nama_pengadu`, `alamat_pengadu`, `no_telepon_pengadu`, `topik_aduan`, `isi_aduan`, `status_aduan`, `pool_terima`, `skpd_balik`, `skpd_terima`, `departemen`, `status_jawab`) VALUES
(2, 'K53124HH125', 'Ali Mustofa', 'Jalan Kelinci 11 Surabaya', '08225434191', 'Sarana dan Prasarana', 'Parkiran motor di kantor pelayanan desa sangat kecil, mohon ditambah agar kendaraan bermotor nyaman.', '5', 'ginanjarpr', '', 'sumsit', 'Change Management', 's'),
(3, 'K53124HH127', 'Zamrudin Sutomo', 'Jalan Kutubuku 11 Sidoarjo', '08123458193', 'Sarana dan Prasarana', 'Terdapat jalan lubang di daerah priuk sidoarjo barat, harap segera diperbaiki agar tidak membahayakan netijen budiman.', '2', '', 'sumsit', 'sumsit', 'Keuangan', ''),
(4, 'K53124HH129', 'Halim Perdana Kusuma', 'Jalan Raisangukuru 99 Magelang', '085571241345', 'Sarana dan Prasarana', 'Tumbuhan di sekitar jalan jagorawi terlihat rusak akibat event yang diselenggarakan kemarin lusa, mohon petugas cepat mengambil tindakan.', '5', 'ginanjarpr', '', 'sumsit', '', 's'),
(5, 'K53124HH135', 'Waluyo Primarasa', 'Jalan Ahmad Yani 55 Semarang', '081275194823', 'Sarana dan Prasarana', 'Trotoar di sekitar jalan ahmad yani beberapa ada yang rusak akibat ditabrak truk, tolong petugass segera memperbaiki untuk menghindari macet + marah netijen budiman', '5', 'ginanjarpr', '', 'sumsit', '', ''),
(9, 'K53124HH107', 'Udin Sedunia', 'Jalan Turunojoyo 33 Surabaya', '085716364912', 'Layanan Pelayanan', 'Saya kemarin ke rumah sakit pucuk permai, layanan yang diberikan sangat tidak baik, mohon perbaiki.', '1', 'kadir', '', '', '', ''),
(28, 'K53124HH124', 'Marzuki Ali', 'Jalan Tombro 56 Surabaya', '08225473821', 'Layanan Masyarakat', 'Saya kemarin ke rumah sakit pucuk permai, layanan yang diberikan sangat tidak baik, mohon perbaiki.', '1', '', '', '', '', ''),
(29, 'KH12e21ed1', '5214100115', '5214100115', '5214100115', '5214100115', '5214100115', '1', '', '', '', '', ''),
(30, 'KH12018316339', 'asdad', 'asdasd', 'asdasd', 'Fasilitas', 'asdasd', '1', '', '', '', '', ''),
(32, 'KH12e21ed1', '5214100115', '5214100115', '5214100115', '5214100115', '5214100115', '1', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `username` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `roles` varchar(5) NOT NULL,
  `password` varchar(256) NOT NULL,
  `created_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `username`, `email`, `roles`, `password`, `created_at`) VALUES
(12, 'Admin SKPD', 'adminskpd', 'adminskpd@gmail.com', '2', '$2a$12$8BLbQ9D20z9oKDOCYObdDexni431zdFmyuiFPf3fAzDZEiEI.TyI2', '2018-05-19 11:11:06'),
(13, 'Admin Pool', 'adminpool', 'adminpool@gmail.com', '1', '$2a$12$QIaEBlWosFT.e1Pwzh3i7eRx/3iSYT/bB5bQ5kmpNElB.tD83uc0S', '2018-05-19 11:14:12');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jawaban_tiket`
--
ALTER TABLE `jawaban_tiket`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `nik`
--
ALTER TABLE `nik`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tiket`
--
ALTER TABLE `tiket`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `jawaban_tiket`
--
ALTER TABLE `jawaban_tiket`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `nik`
--
ALTER TABLE `nik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tiket`
--
ALTER TABLE `tiket`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
