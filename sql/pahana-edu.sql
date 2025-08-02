-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Aug 02, 2025 at 07:56 AM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pahana-edu`
--

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
CREATE TABLE IF NOT EXISTS `bills` (
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `account_number` int NOT NULL,
                                       `total_amount` decimal(10,2) NOT NULL,
    `bill_date` datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `account_number` (`account_number`)
    ) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bills`
--

INSERT INTO `bills` (`id`, `account_number`, `total_amount`, `bill_date`) VALUES
                                                                              (13, 102, 2500.00, '2025-08-02 12:39:04'),
                                                                              (15, 1001, 2500.00, '2025-08-02 12:40:01'),
                                                                              (17, 1001, 3000.00, '2025-08-02 12:40:01'),
                                                                              (16, 1002, 1200.50, '2025-08-02 12:40:01'),
                                                                              (6, 11, 14820.00, '2025-07-28 23:19:07'),
                                                                              (7, 12, 16000.00, '2025-07-28 23:24:22'),
                                                                              (18, 1001, 2500.00, '2025-08-02 12:40:01'),
                                                                              (9, 6, 3100.00, '2025-07-28 23:38:37'),
                                                                              (14, 101, 1500.00, '2025-08-02 12:39:04'),
                                                                              (19, 1001, 2500.00, '2025-08-02 12:40:01');

-- --------------------------------------------------------

--
-- Table structure for table `bill_items`
--

DROP TABLE IF EXISTS `bill_items`;
CREATE TABLE IF NOT EXISTS `bill_items` (
                                            `id` int NOT NULL AUTO_INCREMENT,
                                            `bill_id` int DEFAULT NULL,
                                            `item_id` int DEFAULT NULL,
                                            `quantity` int NOT NULL,
                                            `price` decimal(10,2) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `bill_id` (`bill_id`),
    KEY `item_id` (`item_id`)
    ) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bill_items`
--

INSERT INTO `bill_items` (`id`, `bill_id`, `item_id`, `quantity`, `price`) VALUES
                                                                               (1, 1, 1, 3, 600.00),
                                                                               (3, 5, 3, 6, 2250.00),
                                                                               (17, 6, 2, 19, 780.00),
                                                                               (5, 7, 3, 2, 2250.00),
                                                                               (6, 8, 3, 2, 2250.00),
                                                                               (7, 9, 9, 2, 1550.00),
                                                                               (9, 7, 6, 10, 1600.00),
                                                                               (19, 1, 2, 1, 750.00),
                                                                               (18, 1, 1, 2, 500.00);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
                                           `account_number` int NOT NULL AUTO_INCREMENT,
                                           `name` varchar(100) NOT NULL,
    `address` text NOT NULL,
    `telephone` varchar(20) DEFAULT NULL,
    `units_consumed` int DEFAULT '0',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`account_number`)
    ) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`account_number`, `name`, `address`, `telephone`, `units_consumed`, `created_at`) VALUES
                                                                                                               (11, 'Updated Name', '481/2,Meedeniya,Hettimulla.', '0760374950', 0, '2025-07-24 17:08:44'),
                                                                                                               (2, 'chandupa', '726', '1990', 0, '2025-07-19 06:33:26'),
                                                                                                               (3, 'customer1', '234', '242', 0, '2025-07-19 06:34:33'),
                                                                                                               (4, 'customer1', 'vve', '234', 0, '2025-07-19 06:35:45'),
                                                                                                               (5, 'punura', '149/4c', '119', 0, '2025-07-19 09:19:35'),
                                                                                                               (6, 'punura', '149/4c', '1990', 0, '2025-07-19 09:19:46'),
                                                                                                               (7, 'Thakshila Dilrukshi', '481/2,Meedeniya', '0760374950', 0, '2025-07-20 06:59:48'),
                                                                                                               (8, 'Test User', '115', '0712345678', 0, '2025-07-20 07:28:07'),
                                                                                                               (12, 'new customer', 'colombo', '0765678908', 0, '2025-07-28 17:53:37'),
                                                                                                               (13, 'kkk', 'rey5r454', '54q', 0, '2025-07-29 16:37:31'),
                                                                                                               (14, 'tyhr', 'eddfv', '3445t56y656', 0, '2025-08-02 05:38:07'),
                                                                                                               (15, 'Test User', '115,koswatta', '0712345678', 0, '2025-08-02 07:18:23');

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
CREATE TABLE IF NOT EXISTS `items` (
                                       `item_id` int NOT NULL AUTO_INCREMENT,
                                       `item_name` varchar(100) NOT NULL,
    `item_description` text,
    `unit_price` decimal(10,2) NOT NULL,
    `stock_quantity` int DEFAULT '0',
    PRIMARY KEY (`item_id`)
    ) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`item_id`, `item_name`, `item_description`, `unit_price`, `stock_quantity`) VALUES
                                                                                                     (16, 'New Item', 'New item for test', 300.00, 7),
                                                                                                     (15, 'Test Item', 'Test Description', 100.00, 5),
                                                                                                     (3, 'Clean Code', 'A Handbook of Agile Software Craftsmanship by Robert C. Martin, focusing on writing clean and maintainable code.', 2250.00, 10),
                                                                                                     (4, 'Deep Work', 'Cal Newport explores the benefits of focused success in a distracted world.', 1800.00, 12),
                                                                                                     (5, 'The Catcher in the Rye', 'A novel by J.D. Salinger about teenage alienation and loss of innocence.', 1450.00, 8),
                                                                                                     (6, 'The Psychology of Money', 'Timeless lessons on wealth, greed, and happiness by Morgan Housel.', 1600.00, 20),
                                                                                                     (7, 'Educated', 'A memoir by Tara Westover detailing her journey from a survivalist family to earning a PhD from Cambridge.', 1900.00, 15),
                                                                                                     (8, 'Start With Why', 'Simon Sinek explains how great leaders inspire action by starting with why.', 1750.00, 11),
                                                                                                     (9, 'The Hobbit', 'J.R.R. Tolkien\'s fantasy classic introducing the world of Middle-earth.', 1550.00, 18),
(10, 'Thinking, Fast and Slow', 'Daniel Kahneman explores how we make decisions using two systems of thinking.', 2000.00, 14),
(14, 'Test Book', 'A test book description', 500.00, 10),
(12, 'A Brief History of Time', 'Stephen Hawking explains the origins and structure of the universe in layman\'s terms.', 2100.00, 9),
                                                                                                     (17, 'Updated Item', 'Updated description', 200.00, 10),
                                                                                                     (18, 'Test Item', 'Test Description', 100.00, 5),
                                                                                                     (19, 'Test Item', 'Test Description', 100.00, 5);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `username` varchar(50) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
    ) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
    (1, 'admin', 'admin@123');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
