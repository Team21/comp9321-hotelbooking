-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1:33066
-- Generation Time: May 05, 2012 at 11:21 PM
-- Server version: 5.1.59
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mydb`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE IF NOT EXISTS `booking` (
  `bookingid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `totalprice` decimal(19,4) NOT NULL,
  `bookingdate` datetime NOT NULL,
  PRIMARY KEY (`bookingid`),
  KEY `userid` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`bookingid`, `userid`, `totalprice`, `bookingdate`) VALUES
(1, 7, 220.0000, '2012-07-01 13:00:00'),
(2, 8, 650.0000, '2012-08-01 13:00:00'),
(3, 9, 500.0000, '2012-09-01 13:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `hotel`
--

CREATE TABLE IF NOT EXISTS `hotel` (
  `hotelid` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `city` varchar(20) DEFAULT NULL,
  `ownerid` int(11) NOT NULL,
  `managerid` int(11) NOT NULL,
  `phoneno` int(11) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`hotelid`),
  KEY `ownerid` (`ownerid`),
  KEY `managerid` (`managerid`),
  KEY `ownerid_2` (`ownerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hotel`
--

INSERT INTO `hotel` (`hotelid`, `name`, `city`, `ownerid`, `managerid`, `phoneno`, `address`) VALUES
(1, 'a', 'Sydney', 1, 2, 2345, '1 george st'),
(2, 'b', 'Melbourne', 1, 3, 3456, '1 mel st'),
(3, 'c', 'Brisbane', 1, 4, 4567, '1 bri st'),
(4, 'd', 'Adelaide', 1, 5, 5678, '1 ade st'),
(5, 'e', 'Hobart', 1, 6, 6789, '1 hob st');

-- --------------------------------------------------------

--
-- Table structure for table `record`
--

CREATE TABLE IF NOT EXISTS `record` (
  `recordid` int(11) NOT NULL,
  `bookingid` int(11) NOT NULL,
  `hotelid` int(11) NOT NULL,
  `roomtypeid` int(11) NOT NULL,
  `extrabed` int(11) NOT NULL,
  `price` decimal(19,4) NOT NULL,
  `checkindate` datetime NOT NULL,
  `checkoutdate` datetime NOT NULL,
  PRIMARY KEY (`recordid`),
  KEY `recordid` (`recordid`),
  KEY `bookingid` (`bookingid`),
  KEY `hotelid` (`hotelid`),
  KEY `roomtypeid` (`roomtypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `record`
--

INSERT INTO `record` (`recordid`, `bookingid`, `hotelid`, `roomtypeid`, `extrabed`, `price`, `checkindate`, `checkoutdate`) VALUES
(1, 1, 1, 1, 0, 100.0000, '2012-07-06 13:00:00', '2012-07-08 11:00:00'),
(2, 1, 2, 6, 0, 120.0000, '2012-07-08 13:00:00', '2012-07-10 11:00:00'),
(3, 2, 2, 8, 1, 250.0000, '2012-08-15 13:00:00', '2012-08-18 11:00:00'),
(4, 2, 3, 14, 1, 400.0000, '2012-08-18 13:00:00', '2012-08-19 11:00:00'),
(5, 3, 3, 15, 0, 500.0000, '2012-09-20 13:00:00', '2012-09-22 11:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE IF NOT EXISTS `room` (
  `roomid` int(11) NOT NULL,
  `roomno` int(11) NOT NULL,
  `roomtypeid` int(11) NOT NULL,
  `condition` varchar(15) NOT NULL,
  `hotelid` int(11) NOT NULL,
  PRIMARY KEY (`roomid`),
  KEY `roomtypeid` (`roomtypeid`),
  KEY `hotelid` (`hotelid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`roomid`, `roomno`, `roomtypeid`, `condition`, `hotelid`) VALUES
(1, 101, 1, 'maintainence', 1),   (27, 101, 6, 'available', 2),      (53, 101, 11, 'maintainence', 3),   (79, 101, 16, 'available', 4),      (105, 101, 21, 'maintainence', 5),
(2, 102, 1, 'available', 1),      (28, 102, 6, 'available', 2),      (54, 102, 11, 'maintainence', 3),   (80, 102, 16, 'available', 4),      (106, 102, 21, 'maintainence', 5),
(3, 103, 1, 'available', 1),      (29, 103, 6, 'available', 2),      (55, 103, 11, 'maintainence', 3),   (81, 103, 16, 'available', 4),      (107, 103, 21, 'maintainence', 5),
(4, 104, 1, 'available', 1),      (30, 104, 6, 'available', 2),      (56, 104, 11, 'maintainence', 3),   (82, 104, 16, 'available', 4),      (108, 104, 21, 'maintainence', 5),
(5, 105, 1, 'available', 1),      (31, 105, 6, 'available', 2),      (57, 105, 11, 'available', 3),      (83, 105, 16, 'maintainence', 4),   (109, 105, 21, 'maintainence', 5),
(6, 106, 1, 'available', 1),      (32, 106, 6, 'available', 2),      (58, 106, 11, 'available', 3),      (84, 106, 16, 'maintainence', 4),   (110, 106, 21, 'maintainence', 5),
(7, 107, 1, 'available', 1),      (33, 107, 6, 'available', 2),      (59, 107, 11, 'available', 3),      (85, 107, 16, 'maintainence', 4),   (111, 107, 21, 'maintainence', 5),
(8, 108, 1, 'available', 1),      (34, 108, 6, 'available', 2),      (60, 108, 11, 'available', 3),      (86, 108, 16, 'maintainence', 4),   (112, 108, 21, 'maintainence', 5),
(9, 109, 1, 'available', 1),      (35, 109, 6, 'available', 2),      (61, 109, 11, 'available', 3),      (87, 109, 16, 'maintainence', 4),   (113, 109, 21, 'maintainence', 5),
(10, 110, 1, 'available', 1),     (36, 110, 6, 'available', 2),      (62, 110, 11, 'available', 3),      (88, 110, 16, 'maintainence', 4),   (114, 110, 21, 'maintainence', 5),

(11, 201, 2, 'available', 1),      (37, 201, 7, 'available', 2),      (63, 201, 12, 'available', 3),      (89, 201, 17, 'available', 4),      (115, 201, 22, 'available', 5),
(12, 202, 2, 'available', 1),      (38, 202, 7, 'available', 2),      (64, 202, 12, 'available', 3),      (90, 202, 17, 'available', 4),      (116, 202, 22, 'available', 5),
(13, 203, 2, 'available', 1),      (39, 203, 7, 'available', 2),      (65, 203, 12, 'available', 3),      (91, 203, 17, 'available', 4),      (117, 203, 22, 'available', 5),
(14, 204, 2, 'available', 1),      (40, 204, 7, 'available', 2),      (66, 204, 12, 'available', 3),      (92, 204, 17, 'available', 4),      (118, 204, 22, 'available', 5),
(15, 205, 2, 'available', 1),      (41, 205, 7, 'available', 2),      (67, 205, 12, 'available', 3),      (93, 205, 17, 'available', 4),      (119, 205, 22, 'available', 5),
(16, 206, 2, 'available', 1),      (42, 206, 7, 'available', 2),      (68, 206, 12, 'available', 3),      (94, 206, 17, 'available', 4),      (120, 206, 22, 'available', 5),
(17, 207, 2, 'available', 1),      (43, 207, 7, 'available', 2),      (69, 207, 12, 'available', 3),      (95, 207, 17, 'available', 4),      (121, 207, 22, 'available', 5),
(18, 208, 2, 'available', 1),      (44, 208, 7, 'available', 2),      (70, 208, 12, 'available', 3),      (96, 208, 17, 'available', 4),      (122, 208, 22, 'available', 5),

(19, 301, 3, 'available', 1),      (45, 301, 8, 'available', 2),      (71, 301, 13, 'available', 3),      (97, 301, 18, 'available', 4),      (123, 301, 23, 'available', 5),
(20, 302, 3, 'available', 1),      (46, 302, 8, 'available', 2),      (72, 302, 13, 'available', 3),      (98, 302, 18, 'available', 4),      (124, 302, 23, 'available', 5),
(21, 303, 3, 'available', 1),      (47, 303, 8, 'available', 2),      (73, 303, 13, 'available', 3),      (99, 303, 18, 'available', 4),      (125, 303, 23, 'available', 5),
(22, 303, 3, 'available', 1),      (48, 303, 8, 'available', 2),      (74, 303, 13, 'available', 3),      (100, 303, 18, 'available', 4),     (126, 303, 23, 'available', 5),
(23, 304, 3, 'available', 1),      (49, 304, 8, 'available', 2),      (75, 304, 13, 'available', 3),      (101, 304, 18, 'available', 4),     (127, 304, 23, 'available', 5),

(24, 401, 4, 'available', 1),      (50, 401, 9, 'available', 2),      (76, 401, 14, 'available', 3),      (102, 401, 19, 'available', 4),     (128, 401, 24, 'available', 5),
(25, 402, 4, 'available', 1),      (51, 402, 9, 'available', 2),      (77, 402, 14, 'available', 3),      (103, 402, 19, 'available', 4),     (129, 402, 24, 'available', 5),

(26, 501, 5, 'available', 1),      (52, 501, 10, 'available', 2),     (78, 501, 15, 'available', 3),      (104, 501, 20, 'available', 4),     (130, 501, 25, 'available', 5);

-- --------------------------------------------------------

--
-- Table structure for table `roomcalendar`
--

CREATE TABLE IF NOT EXISTS `roomcalendar` (
  `roomcalid` int(11) NOT NULL,
  `bookingid` int(11) NOT NULL,
  `roomtypeid` int(11) NOT NULL,
  `checkindate` datetime DEFAULT NULL,
  `checkoutdate` datetime DEFAULT NULL,
  PRIMARY KEY (`roomcalid`),
  KEY `bookingid` (`bookingid`),
  KEY `roomtypeid` (`roomtypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roomcalendar`
--

INSERT INTO `roomcalendar` (`roomcalid`, `bookingid`, `roomtypeid`, `checkindate`, `checkoutdate`) VALUES
(1, 1, 1, '2012-07-06 13:00:00', '2012-07-08 11:00:00'),
(2, 1, 6, '2012-07-08 13:00:00', '2012-07-10 11:00:00'),
(3, 2, 8, '2012-08-15 13:00:00', '2012-08-18 11:00:00'),
(4, 2, 14, '2012-08-18 13:00:00', '2012-08-19 11:00:00'),
(5, 3, 15, '2012-09-20 13:00:00', '2012-09-22 11:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `roomtype`
--

CREATE TABLE IF NOT EXISTS `roomtype` (
  `roomtypeid` int(11) NOT NULL,
  `amount` int(4) NOT NULL,
  `hotelid` int(11) NOT NULL,
  `type` enum('single','twin','queen','executive','suite') NOT NULL,
  `price` decimal(19,4) NOT NULL,
  `discountrate` double DEFAULT NULL,
  `discountfrom` datetime DEFAULT NULL,
  `discountto` datetime DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`roomtypeid`),
  KEY `hotelid` (`hotelid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roomtype`
--

INSERT INTO `roomtype` (`roomtypeid`, `amount`, `hotelid`, `type`, `price`, `discountrate`, `discountfrom`, `discountto`, `description`) VALUES
(1, 10, 1, 'single', 100.0000, NULL, NULL, NULL, 'single of Hotel 1'),
(2, 8, 1, 'twin', 150.0000, NULL, NULL, NULL, 'twin of Hotel 1'),
(3, 5, 1, 'queen', 200.0000, NULL, NULL, NULL, 'queen of Hotel 1'),
(4, 2, 1, 'executive', 300.0000, NULL, NULL, NULL, 'executive of Hotel 1'),
(5, 1, 1, 'suite', 400.0000, NULL, NULL, NULL, 'suite of Hotel 1'),
(6, 10, 2, 'single', 120.0000, NULL, NULL, NULL, 'single of Hotel 2'),
(7, 8, 2, 'twin', 180.0000, NULL, NULL, NULL, 'twin of Hotel 2'),
(8, 5, 2, 'queen', 250.0000, NULL, NULL, NULL, 'queen of Hotel 2'),
(9, 2, 2, 'executive', 350.0000, NULL, NULL, NULL, 'executive of Hotel 2'),
(10, 1, 2, 'suite', 450.0000, NULL, NULL, NULL, 'suite of Hotel 2'),
(11, 10, 3, 'single', 160.0000, NULL, NULL, NULL, 'single of Hotel 3'),
(12, 8, 3, 'twin', 200.0000, NULL, NULL, NULL, 'twin of Hotel 3'),
(13, 5, 3, 'queen', 300.0000, NULL, NULL, NULL, 'queen of Hotel 3'),
(14, 2, 3, 'executive', 400.0000, NULL, NULL, NULL, 'executive of Hotel 3'),
(15, 1, 3, 'suite', 500.0000, NULL, NULL, NULL, 'suite of Hotel 3'),
(16, 10, 4, 'single', 120.0000, NULL, NULL, NULL, 'single of Hotel 4'),
(17, 8, 4, 'twin', 180.0000, NULL, NULL, NULL, 'twin of Hotel 4'),
(18, 5, 4, 'queen', 250.0000, NULL, NULL, NULL, 'queen of Hotel 4'),
(19, 2, 4, 'executive', 350.0000, NULL, NULL, NULL, 'executive of Hotel 4'),
(20, 1, 4, 'suite', 450.0000, NULL, NULL, NULL, 'suite of Hotel 4'),
(21, 10, 5, 'single', 160.0000, NULL, NULL, NULL, 'single of Hotel 5'),
(22, 8, 5, 'twin', 200.0000, NULL, NULL, NULL, 'twin of Hotel 5'),
(23, 5, 5, 'queen', 300.0000, NULL, NULL, NULL, 'queen of Hotel 5'),
(24, 2, 5, 'executive', 400.0000, NULL, NULL, NULL, 'executive of Hotel 5'),
(25, 1, 5, 'suite', 500.0000, NULL, NULL, NULL, 'suite of Hotel 5');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(20) NOT NULL,
  `lname` varchar(20) NOT NULL,
  `security_level` enum('user','manager','owner') NOT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userid`, `fname`, `lname`, `security_level`, `email`, `username`, `password`, `address`) VALUES
(1, 'steven', 'jobs', 'owner', 'sj@sj.com', 'sj', 'sj', 'us'),
(2, 'tim', 'wu', 'manager', 'tw@tw.com', 'tw', 'tw', 'cn'),
(3, 'jack', 'ma', 'manager', 'jm@jm.com', 'jm', 'jm', 'cn'),
(4, 'kate', 'wong', 'manager', 'kw@kw.com', 'kw', 'kw', 'cn'),
(5, 'tom', 'li', 'manager', 'tl@tl.com', 'tl', 'tl', 'cn'),
(6, 'bill', 'yong', 'manager', 'by@by.com', 'by', 'by', 'cn'),
(7, 'mike', 'zhuang', 'user', 'mz@mz.com', 'mz', 'mz', 'oz'),
(8, 'hank', 'jia', 'user', 'hj@hj.com', 'hj', 'hj', 'oz'),
(9, 'alex', 'xu', 'user', 'alexxu@alexxu.com', 'alexxu', 'alexxu', 'oz');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `hotel`
--
ALTER TABLE `hotel`
  ADD CONSTRAINT `managerid` FOREIGN KEY (`managerid`) REFERENCES `user` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `ownerid` FOREIGN KEY (`ownerid`) REFERENCES `user` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `record`
--
ALTER TABLE `record`
  ADD CONSTRAINT `record_ibfk_2` FOREIGN KEY (`hotelid`) REFERENCES `hotel` (`hotelid`),
  ADD CONSTRAINT `record_ibfk_3` FOREIGN KEY (`roomtypeid`) REFERENCES `roomtype` (`roomtypeid`),
  ADD CONSTRAINT `record_ibfk_4` FOREIGN KEY (`bookingid`) REFERENCES `booking` (`bookingid`);

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `room_ibfk_2` FOREIGN KEY (`roomtypeid`) REFERENCES `roomtype` (`roomtypeid`),
  ADD CONSTRAINT `room_ibfk_3` FOREIGN KEY (`hotelid`) REFERENCES `hotel` (`hotelid`);

--
-- Constraints for table `roomcalendar`
--
ALTER TABLE `roomcalendar`
  ADD CONSTRAINT `bookingid` FOREIGN KEY (`bookingid`) REFERENCES `booking` (`bookingid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `roomtypeid` FOREIGN KEY (`roomtypeid`) REFERENCES `roomtype` (`roomtypeid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `roomtype`
--
ALTER TABLE `roomtype`
  ADD CONSTRAINT `hotelid` FOREIGN KEY (`hotelid`) REFERENCES `hotel` (`hotelid`) ON DELETE NO ACTION ON UPDATE NO ACTION;
