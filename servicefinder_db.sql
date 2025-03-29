/*
 Navicat Premium Dump SQL

 Source Server         : Localhost Mysql 8
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : servicefinder_db

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 30/03/2025 00:40:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bookings
-- ----------------------------
DROP TABLE IF EXISTS `bookings`;
CREATE TABLE `bookings`  (
  `BookingID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NULL DEFAULT NULL,
  `ServiceProviderID` int NULL DEFAULT NULL,
  `ServiceID` int NULL DEFAULT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `Status` enum('Upcoming','Completed','Cancelled') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TotalPrice` decimal(10, 2) NOT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`BookingID`) USING BTREE,
  INDEX `UserID`(`UserID` ASC) USING BTREE,
  INDEX `ServiceProviderID`(`ServiceProviderID` ASC) USING BTREE,
  INDEX `ServiceID`(`ServiceID` ASC) USING BTREE,
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`ServiceProviderID`) REFERENCES `serviceproviders` (`ServiceProviderID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bookings_ibfk_3` FOREIGN KEY (`ServiceID`) REFERENCES `services` (`ServiceID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bookingshistory
-- ----------------------------
DROP TABLE IF EXISTS `bookingshistory`;
CREATE TABLE `bookingshistory`  (
  `BookingHistoryID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NULL DEFAULT NULL,
  `ServiceProviderID` int NULL DEFAULT NULL,
  `ServiceID` int NULL DEFAULT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `Status` enum('Completed','Cancelled') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TotalPrice` decimal(10, 2) NOT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`BookingHistoryID`) USING BTREE,
  INDEX `UserID`(`UserID` ASC) USING BTREE,
  INDEX `ServiceProviderID`(`ServiceProviderID` ASC) USING BTREE,
  INDEX `ServiceID`(`ServiceID` ASC) USING BTREE,
  CONSTRAINT `bookingshistory_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bookingshistory_ibfk_2` FOREIGN KEY (`ServiceProviderID`) REFERENCES `serviceproviders` (`ServiceProviderID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bookingshistory_ibfk_3` FOREIGN KEY (`ServiceID`) REFERENCES `services` (`ServiceID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cartitems
-- ----------------------------
DROP TABLE IF EXISTS `cartitems`;
CREATE TABLE `cartitems`  (
  `CartItemID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NULL DEFAULT NULL,
  `ServiceProviderID` int NULL DEFAULT NULL,
  `ServiceID` int NULL DEFAULT NULL,
  `Quantity` int NOT NULL DEFAULT 1,
  `TotalPrice` decimal(10, 2) NOT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CartItemID`) USING BTREE,
  INDEX `ServiceProviderID`(`ServiceProviderID` ASC) USING BTREE,
  INDEX `ServiceID`(`ServiceID` ASC) USING BTREE,
  INDEX `UserID`(`UserID` ASC, `ServiceProviderID` ASC, `ServiceID` ASC) USING BTREE,
  CONSTRAINT `cartitems_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `cartitems_ibfk_2` FOREIGN KEY (`ServiceProviderID`) REFERENCES `serviceproviders` (`ServiceProviderID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `cartitems_ibfk_3` FOREIGN KEY (`ServiceID`) REFERENCES `services` (`ServiceID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `CategoryID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`CategoryID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category_services
-- ----------------------------
DROP TABLE IF EXISTS `category_services`;
CREATE TABLE `category_services`  (
  `CategoryID` int NOT NULL,
  `ServiceID` int NOT NULL,
  PRIMARY KEY (`CategoryID`, `ServiceID`) USING BTREE,
  INDEX `CategoryID`(`CategoryID` ASC) USING BTREE,
  INDEX `ServiceID`(`ServiceID` ASC) USING BTREE,
  CONSTRAINT `category_services_ibfk_1` FOREIGN KEY (`CategoryID`) REFERENCES `categories` (`CategoryID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `category_services_ibfk_2` FOREIGN KEY (`ServiceID`) REFERENCES `services` (`ServiceID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for faqs
-- ----------------------------
DROP TABLE IF EXISTS `faqs`;
CREATE TABLE `faqs`  (
  `FAQID` int NOT NULL AUTO_INCREMENT,
  `Question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`FAQID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gallery
-- ----------------------------
DROP TABLE IF EXISTS `gallery`;
CREATE TABLE `gallery`  (
  `GalleryID` int NOT NULL AUTO_INCREMENT,
  `ServiceProviderID` int NULL DEFAULT NULL,
  `ImageURL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`GalleryID`) USING BTREE,
  INDEX `ServiceProviderID`(`ServiceProviderID` ASC) USING BTREE,
  CONSTRAINT `gallery_ibfk_1` FOREIGN KEY (`ServiceProviderID`) REFERENCES `serviceproviders` (`ServiceProviderID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for integrations
-- ----------------------------
DROP TABLE IF EXISTS `integrations`;
CREATE TABLE `integrations`  (
  `IntegrationID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NULL DEFAULT NULL,
  `ServiceProviderID` int NULL DEFAULT NULL,
  `GoogleCalendar` tinyint(1) NOT NULL,
  `SocialMedia` tinyint(1) NOT NULL,
  `Chat` tinyint(1) NOT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`IntegrationID`) USING BTREE,
  INDEX `UserID`(`UserID` ASC) USING BTREE,
  INDEX `ServiceProviderID`(`ServiceProviderID` ASC) USING BTREE,
  CONSTRAINT `integrations_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `integrations_ibfk_2` FOREIGN KEY (`ServiceProviderID`) REFERENCES `serviceproviders` (`ServiceProviderID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for locations
-- ----------------------------
DROP TABLE IF EXISTS `locations`;
CREATE TABLE `locations`  (
  `LocationID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `City` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Country` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Latitude` decimal(10, 8) NULL DEFAULT NULL,
  `Longitude` decimal(11, 8) NULL DEFAULT NULL,
  PRIMARY KEY (`LocationID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for loyaltypoints
-- ----------------------------
DROP TABLE IF EXISTS `loyaltypoints`;
CREATE TABLE `loyaltypoints`  (
  `UserID` int NOT NULL,
  `Points` int NOT NULL DEFAULT 0,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`UserID`) USING BTREE,
  CONSTRAINT `loyaltypoints_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for notifications
-- ----------------------------
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications`  (
  `NotificationID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NULL DEFAULT NULL,
  `Message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Type` enum('Booking','Review','Loyalty') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Status` enum('Unread','Read') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Unread',
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`NotificationID`) USING BTREE,
  INDEX `UserID`(`UserID` ASC) USING BTREE,
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for offers
-- ----------------------------
DROP TABLE IF EXISTS `offers`;
CREATE TABLE `offers`  (
  `OfferID` int NOT NULL AUTO_INCREMENT,
  `ServiceProviderID` int NULL DEFAULT NULL,
  `Title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `Discount` decimal(4, 2) NOT NULL,
  `ExpiryDate` date NULL DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`OfferID`) USING BTREE,
  INDEX `ServiceProviderID`(`ServiceProviderID` ASC) USING BTREE,
  CONSTRAINT `offers_ibfk_1` FOREIGN KEY (`ServiceProviderID`) REFERENCES `serviceproviders` (`ServiceProviderID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for paymentgateways
-- ----------------------------
DROP TABLE IF EXISTS `paymentgateways`;
CREATE TABLE `paymentgateways`  (
  `GatewayID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `APIKey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`GatewayID`) USING BTREE,
  UNIQUE INDEX `Name`(`Name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for reviews
-- ----------------------------
DROP TABLE IF EXISTS `reviews`;
CREATE TABLE `reviews`  (
  `ReviewID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NULL DEFAULT NULL,
  `ServiceProviderID` int NULL DEFAULT NULL,
  `Rating` tinyint NOT NULL,
  `Comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ReviewID`) USING BTREE,
  INDEX `UserID`(`UserID` ASC) USING BTREE,
  INDEX `ServiceProviderID`(`ServiceProviderID` ASC) USING BTREE,
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`ServiceProviderID`) REFERENCES `serviceproviders` (`ServiceProviderID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `reviews_chk_1` CHECK (`Rating` between 1 and 5)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for serviceproviders
-- ----------------------------
DROP TABLE IF EXISTS `serviceproviders`;
CREATE TABLE `serviceproviders`  (
  `ServiceProviderID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NULL DEFAULT NULL,
  `BusinessName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `WorkingHours` time NULL DEFAULT NULL,
  `Holidays` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `StaffAvailability` int NULL DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isAvailable` tinyint(1) NULL DEFAULT NULL,
  `isActive` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`ServiceProviderID`) USING BTREE,
  UNIQUE INDEX `UserID`(`UserID` ASC) USING BTREE,
  CONSTRAINT `serviceproviders_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for serviceprovidersocialmedia
-- ----------------------------
DROP TABLE IF EXISTS `serviceprovidersocialmedia`;
CREATE TABLE `serviceprovidersocialmedia`  (
  `ServiceProviderID` int NOT NULL,
  `PlatformID` int NOT NULL,
  `Link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ServiceProviderID`, `PlatformID`) USING BTREE,
  INDEX `PlatformID`(`PlatformID` ASC) USING BTREE,
  CONSTRAINT `serviceprovidersocialmedia_ibfk_1` FOREIGN KEY (`ServiceProviderID`) REFERENCES `serviceproviders` (`ServiceProviderID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `serviceprovidersocialmedia_ibfk_2` FOREIGN KEY (`PlatformID`) REFERENCES `socialmediaplatforms` (`PlatformID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for services
-- ----------------------------
DROP TABLE IF EXISTS `services`;
CREATE TABLE `services`  (
  `ServiceID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NULL DEFAULT NULL,
  `Name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CategoryID` int NOT NULL,
  `SubcategoryID` int NULL DEFAULT NULL,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `Price` decimal(10, 2) NOT NULL,
  `EstimatedTime` int NULL DEFAULT NULL,
  `ReminderTime` time NULL DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint(1) NULL DEFAULT NULL,
  `isAvailable` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`ServiceID`) USING BTREE,
  INDEX `UserID`(`UserID` ASC) USING BTREE,
  INDEX `services_ibfk_1`(`CategoryID` ASC) USING BTREE,
  INDEX `services_ibfk_2`(`SubcategoryID` ASC) USING BTREE,
  CONSTRAINT `services_ibfk_1` FOREIGN KEY (`CategoryID`) REFERENCES `categories` (`CategoryID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `services_ibfk_2` FOREIGN KEY (`SubcategoryID`) REFERENCES `subcategories` (`SubcategoryID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for smsgateways
-- ----------------------------
DROP TABLE IF EXISTS `smsgateways`;
CREATE TABLE `smsgateways`  (
  `GatewayID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `APIKey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`GatewayID`) USING BTREE,
  UNIQUE INDEX `Name`(`Name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for socialmediaplatforms
-- ----------------------------
DROP TABLE IF EXISTS `socialmediaplatforms`;
CREATE TABLE `socialmediaplatforms`  (
  `PlatformID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`PlatformID`) USING BTREE,
  UNIQUE INDEX `Name`(`Name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for subcategories
-- ----------------------------
DROP TABLE IF EXISTS `subcategories`;
CREATE TABLE `subcategories`  (
  `SubcategoryID` int NOT NULL AUTO_INCREMENT,
  `CategoryID` int NOT NULL,
  `Name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`SubcategoryID`) USING BTREE,
  INDEX `CategoryID`(`CategoryID` ASC) USING BTREE,
  CONSTRAINT `subcategories_ibfk_1` FOREIGN KEY (`CategoryID`) REFERENCES `categories` (`CategoryID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for subcategory_services
-- ----------------------------
DROP TABLE IF EXISTS `subcategory_services`;
CREATE TABLE `subcategory_services`  (
  `SubcategoryID` int NOT NULL,
  `ServiceID` int NOT NULL,
  PRIMARY KEY (`SubcategoryID`, `ServiceID`) USING BTREE,
  INDEX `SubcategoryID`(`SubcategoryID` ASC) USING BTREE,
  INDEX `ServiceID`(`ServiceID` ASC) USING BTREE,
  CONSTRAINT `subcategory_services_ibfk_1` FOREIGN KEY (`SubcategoryID`) REFERENCES `subcategories` (`SubcategoryID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `subcategory_services_ibfk_2` FOREIGN KEY (`ServiceID`) REFERENCES `services` (`ServiceID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for userbookmarks
-- ----------------------------
DROP TABLE IF EXISTS `userbookmarks`;
CREATE TABLE `userbookmarks`  (
  `UserID` int NOT NULL,
  `ServiceID` int NOT NULL,
  PRIMARY KEY (`UserID`, `ServiceID`) USING BTREE,
  INDEX `ServiceID`(`ServiceID` ASC) USING BTREE,
  CONSTRAINT `userbookmarks_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `userbookmarks_ibfk_2` FOREIGN KEY (`ServiceID`) REFERENCES `services` (`ServiceID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `UserID` int NOT NULL,
  `Username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PasswordHash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `FirstName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `LastName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Role` enum('Customer','ServiceProvider') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`UserID`) USING BTREE,
  UNIQUE INDEX `Username`(`Username` ASC) USING BTREE,
  UNIQUE INDEX `Email`(`Email` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
