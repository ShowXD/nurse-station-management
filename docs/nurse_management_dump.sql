CREATE DATABASE  IF NOT EXISTS `nurse_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `nurse_management`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: nurse_management
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `nurse`
--

DROP TABLE IF EXISTS `nurse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nurse` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `employee_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nurse`
--

LOCK TABLES `nurse` WRITE;
/*!40000 ALTER TABLE `nurse` DISABLE KEYS */;
INSERT INTO `nurse` VALUES (1,'N005','張小給','2025-06-04 23:54:09'),(2,'N001','張小芳','2025-06-04 19:45:23'),(3,'006','阿ㄑ','2025-06-05 20:41:44'),(4,'asdf','阿扁','2025-06-05 20:48:24'),(5,'asdf','asdf','2025-06-05 20:56:22'),(6,'N001','王小明','2025-06-05 22:21:05');
/*!40000 ALTER TABLE `nurse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nurse_station_assignment`
--

DROP TABLE IF EXISTS `nurse_station_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nurse_station_assignment` (
  `nurse_id` bigint NOT NULL,
  `station_id` bigint NOT NULL,
  `assigned_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`nurse_id`,`station_id`),
  KEY `fk_station` (`station_id`),
  CONSTRAINT `fk_nurse` FOREIGN KEY (`nurse_id`) REFERENCES `nurse` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_station` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nurse_station_assignment`
--

LOCK TABLES `nurse_station_assignment` WRITE;
/*!40000 ALTER TABLE `nurse_station_assignment` DISABLE KEYS */;
INSERT INTO `nurse_station_assignment` VALUES (1,2,'2025-06-06 00:17:38','2025-06-06 00:17:38'),(1,3,'2025-06-06 01:39:09','2025-06-06 01:39:09'),(2,2,'2025-06-05 23:37:54','2025-06-05 23:37:54'),(2,3,'2025-06-06 01:39:09','2025-06-06 01:39:09'),(2,4,'2025-06-05 23:37:55','2025-06-05 23:37:55'),(3,2,'2025-06-05 23:39:13','2025-06-05 23:39:13'),(4,2,'2025-06-06 00:16:43','2025-06-06 00:16:43'),(5,2,'2025-06-06 00:16:46','2025-06-06 00:16:46'),(5,5,'2025-06-06 00:09:58','2025-06-06 00:09:58'),(6,2,'2025-06-06 00:16:45','2025-06-06 00:16:45');
/*!40000 ALTER TABLE `nurse_station_assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nurse_station_assignment_backup`
--

DROP TABLE IF EXISTS `nurse_station_assignment_backup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nurse_station_assignment_backup` (
  `id` bigint NOT NULL DEFAULT '0',
  `nurse_id` bigint DEFAULT NULL,
  `station_id` bigint DEFAULT NULL,
  `assigned_at` datetime DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nurse_station_assignment_backup`
--

LOCK TABLES `nurse_station_assignment_backup` WRITE;
/*!40000 ALTER TABLE `nurse_station_assignment_backup` DISABLE KEYS */;
INSERT INTO `nurse_station_assignment_backup` VALUES (85,1,1,NULL,NULL),(86,1,2,NULL,NULL),(87,3,1,NULL,NULL),(88,3,2,NULL,NULL),(89,3,3,NULL,NULL),(90,4,1,NULL,NULL),(91,5,3,NULL,NULL),(92,5,1,NULL,NULL);
/*!40000 ALTER TABLE `nurse_station_assignment_backup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station`
--

DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station`
--

LOCK TABLES `station` WRITE;
/*!40000 ALTER TABLE `station` DISABLE KEYS */;
INSERT INTO `station` VALUES (2,'外科','2025-06-05 22:09:52'),(3,'手術室','2025-06-05 22:09:52'),(4,'內科','2025-06-05 22:18:12'),(5,'asdfzxcv','2025-06-05 22:26:25'),(6,'心臟內科','2025-06-06 01:25:23');
/*!40000 ALTER TABLE `station` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-06 16:35:47
