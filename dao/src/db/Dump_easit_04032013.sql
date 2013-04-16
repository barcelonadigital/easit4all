CREATE DATABASE  IF NOT EXISTS `asit-ps` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `asit-ps`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: asit
-- ------------------------------------------------------
-- Server version	5.5.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `userId` bigint(10) unsigned NOT NULL,
  `age` int(11) DEFAULT NULL,
  `technologySkills` int(11) DEFAULT NULL,
  `visualSkills` int(11) DEFAULT NULL,
  `hearingSkills` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`userId`),
  UNIQUE KEY `userId_UNIQUE` (`userId`),
  KEY `fk_profile_account1` (`userId`),
  CONSTRAINT `fk_profile_account1` FOREIGN KEY (`userId`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `logoperations`
--

DROP TABLE IF EXISTS `logoperations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logoperations` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `userId` bigint(10) unsigned NOT NULL,
  `providerId` varchar(100) DEFAULT NULL,
  `operation` varchar(200) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`userId`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_logoperations_account1` (`userId`),
  CONSTRAINT `fk_logoperations_account1` FOREIGN KEY (`userId`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9618 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `firstName` varchar(200) NOT NULL,
  `lastName` varchar(200) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `userToken` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `applicationpreferences`
--

DROP TABLE IF EXISTS `applicationpreferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applicationpreferences` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `userId` bigint(10) unsigned NOT NULL,
  `textFont` varchar(45) DEFAULT NULL,
  `genericTextFont` varchar(45) DEFAULT NULL,
  `theme` varchar(45) DEFAULT NULL,
  `textSize` double(2,1) DEFAULT NULL,
  `lineSpacing` double(2,1) DEFAULT NULL,
  `layout` tinyint(1) DEFAULT NULL,
  `toc` tinyint(1) DEFAULT NULL,
  `links` tinyint(1) DEFAULT NULL,
  `inputsLarger` tinyint(1) DEFAULT NULL,
  `invertImages` tinyint(1) DEFAULT NULL,
  `tracking` varchar(10) DEFAULT NULL,
  `magnification` double(2,1) DEFAULT NULL,
  PRIMARY KEY (`id`,`userId`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `account_id_UNIQUE` (`userId`),
  KEY `fk_applicationpreferences_account1` (`userId`),
  CONSTRAINT `fk_applicationpreferences_account1` FOREIGN KEY (`userId`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userconnection`
--

DROP TABLE IF EXISTS `userconnection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userconnection` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userId` varchar(255) NOT NULL,
  `providerId` varchar(255) NOT NULL,
  `providerUserId` varchar(255) NOT NULL DEFAULT '',
  `rank` int(11) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `profileUrl` varchar(1024) DEFAULT NULL,
  `imageUrl` varchar(1024) DEFAULT NULL,
  `accessToken` varchar(1024) NOT NULL,
  `secret` varchar(1024) DEFAULT NULL,
  `refreshToken` varchar(1024) DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId_providerId_rank` (`userId`,`providerId`,`rank`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-02-15 10:12:34
