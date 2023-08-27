CREATE DATABASE  IF NOT EXISTS `do_an_2020` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `do_an_2020`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: do_an_2020
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `apis`
--

DROP TABLE IF EXISTS `apis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `method` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apis`
--

LOCK TABLES `apis` WRITE;
/*!40000 ALTER TABLE `apis` DISABLE KEYS */;
/*!40000 ALTER TABLE `apis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn2na60ukhs76ibtpt9burkm27` (`author_id`),
  KEY `FKh4c7lvsc298whoyd4w9ta25cr` (`post_id`),
  CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `FKn2na60ukhs76ibtpt9burkm27` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competitions`
--

DROP TABLE IF EXISTS `competitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competitions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sport_id` bigint(20) DEFAULT NULL,
  `tournament_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg1pp9v08yx040a2m53udyejv` (`sport_id`),
  KEY `FK5e0i6jyt8xxpgm5pb0ayuofax` (`tournament_id`),
  CONSTRAINT `FK5e0i6jyt8xxpgm5pb0ayuofax` FOREIGN KEY (`tournament_id`) REFERENCES `tournaments` (`id`),
  CONSTRAINT `FKg1pp9v08yx040a2m53udyejv` FOREIGN KEY (`sport_id`) REFERENCES `sports` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competitions`
--

LOCK TABLES `competitions` WRITE;
/*!40000 ALTER TABLE `competitions` DISABLE KEYS */;
/*!40000 ALTER TABLE `competitions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matches`
--

DROP TABLE IF EXISTS `matches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matches` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `expected_date` datetime DEFAULT NULL,
  `expected_place` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `num_of_set` int(11) NOT NULL,
  `real_date` datetime DEFAULT NULL,
  `real_place` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `competition_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2yx8g5hn2q4hfx6mi59o1fo0y` (`competition_id`),
  CONSTRAINT `FK2yx8g5hn2q4hfx6mi59o1fo0y` FOREIGN KEY (`competition_id`) REFERENCES `competitions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matches`
--

LOCK TABLES `matches` WRITE;
/*!40000 ALTER TABLE `matches` DISABLE KEYS */;
/*!40000 ALTER TABLE `matches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `can_delete` bit(1) NOT NULL,
  `can_edit` bit(1) NOT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `players` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `dob` datetime DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `gender` bit(1) NOT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `tournament_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6xvn0811tkyo3nfjk2xvqx6ns` (`author_id`),
  KEY `FKfdq3sf2qrgsb9959ug7inkeuy` (`tournament_id`),
  CONSTRAINT `FK6xvn0811tkyo3nfjk2xvqx6ns` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKfdq3sf2qrgsb9959ug7inkeuy` FOREIGN KEY (`tournament_id`) REFERENCES `tournaments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `link` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `receiver_id` bigint(20) DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnwe0b64nkrq28sv3dafhnooi5` (`receiver_id`),
  KEY `FKaf5vwr2r9722vw8buhtjmf3do` (`sender_id`),
  CONSTRAINT `FKaf5vwr2r9722vw8buhtjmf3do` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKnwe0b64nkrq28sv3dafhnooi5` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `results`
--

DROP TABLE IF EXISTS `results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `results` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `is_winner` bit(1) NOT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `score` float NOT NULL,
  `set_number` int(11) NOT NULL,
  `match_id` bigint(20) DEFAULT NULL,
  `team_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3026o6t0j31iy471tarbnqb4t` (`match_id`),
  KEY `FK5hiyaldmhykduom77dauyq1qo` (`team_id`),
  CONSTRAINT `FK3026o6t0j31iy471tarbnqb4t` FOREIGN KEY (`match_id`) REFERENCES `matches` (`id`),
  CONSTRAINT `FK5hiyaldmhykduom77dauyq1qo` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `results`
--

LOCK TABLES `results` WRITE;
/*!40000 ALTER TABLE `results` DISABLE KEYS */;
/*!40000 ALTER TABLE `results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_api`
--

DROP TABLE IF EXISTS `role_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_api` (
  `role_id` bigint(20) NOT NULL,
  `api_id` bigint(20) NOT NULL,
  KEY `FK33k10yjkuja7mdwsjbjqqsiec` (`api_id`),
  KEY `FK643fmg45xe56qfex2b0slqdn2` (`role_id`),
  CONSTRAINT `FK33k10yjkuja7mdwsjbjqqsiec` FOREIGN KEY (`api_id`) REFERENCES `apis` (`id`),
  CONSTRAINT `FK643fmg45xe56qfex2b0slqdn2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_api`
--

LOCK TABLES `role_api` WRITE;
/*!40000 ALTER TABLE `role_api` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,NULL,NULL,NULL,NULL,NULL,'ROLE_ADMIN'),(2,NULL,NULL,NULL,NULL,NULL,'ROLE_USER'),(3,NULL,NULL,NULL,NULL,NULL,'ROLE_GUEST');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scoringunits`
--

DROP TABLE IF EXISTS `scoringunits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scoringunits` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `short_name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scoringunits`
--

LOCK TABLES `scoringunits` WRITE;
/*!40000 ALTER TABLE `scoringunits` DISABLE KEYS */;
/*!40000 ALTER TABLE `scoringunits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sports`
--

DROP TABLE IF EXISTS `sports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sports` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `short_name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `scoringunit_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgsegfpadbxihfmowtjuta5ytj` (`scoringunit_id`),
  CONSTRAINT `FKgsegfpadbxihfmowtjuta5ytj` FOREIGN KEY (`scoringunit_id`) REFERENCES `scoringunits` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sports`
--

LOCK TABLES `sports` WRITE;
/*!40000 ALTER TABLE `sports` DISABLE KEYS */;
/*!40000 ALTER TABLE `sports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_competition`
--

DROP TABLE IF EXISTS `team_competition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team_competition` (
  `team_id` bigint(20) NOT NULL,
  `competition_id` bigint(20) NOT NULL,
  KEY `FK27j2xl4f4wo78ominj9hx0ye2` (`competition_id`),
  KEY `FK56sa7wudr5y17dn2yhwxvxhl9` (`team_id`),
  CONSTRAINT `FK27j2xl4f4wo78ominj9hx0ye2` FOREIGN KEY (`competition_id`) REFERENCES `competitions` (`id`),
  CONSTRAINT `FK56sa7wudr5y17dn2yhwxvxhl9` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_competition`
--

LOCK TABLES `team_competition` WRITE;
/*!40000 ALTER TABLE `team_competition` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_competition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_match`
--

DROP TABLE IF EXISTS `team_match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team_match` (
  `team_id` bigint(20) NOT NULL,
  `match_id` bigint(20) NOT NULL,
  KEY `FKdvur595003eyia8xd51b2m6s6` (`match_id`),
  KEY `FKjdpymxvt6u8b3spuwn32k8wo9` (`team_id`),
  CONSTRAINT `FKdvur595003eyia8xd51b2m6s6` FOREIGN KEY (`match_id`) REFERENCES `matches` (`id`),
  CONSTRAINT `FKjdpymxvt6u8b3spuwn32k8wo9` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_match`
--

LOCK TABLES `team_match` WRITE;
/*!40000 ALTER TABLE `team_match` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_match` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_player`
--

DROP TABLE IF EXISTS `team_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team_player` (
  `team_id` bigint(20) NOT NULL,
  `player_id` bigint(20) NOT NULL,
  KEY `FKeod5eh6snj2a99mr93bdvm5lg` (`player_id`),
  KEY `FK1c43dee4soyxhyv34jw3mbxya` (`team_id`),
  CONSTRAINT `FK1c43dee4soyxhyv34jw3mbxya` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`),
  CONSTRAINT `FKeod5eh6snj2a99mr93bdvm5lg` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_player`
--

LOCK TABLES `team_player` WRITE;
/*!40000 ALTER TABLE `team_player` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teams` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `short_name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa8ijb59i7uledc1ctita0knf3` (`creator_id`),
  CONSTRAINT `FKa8ijb59i7uledc1ctita0knf3` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams`
--

LOCK TABLES `teams` WRITE;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
INSERT INTO `teams` VALUES (1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tournaments`
--

DROP TABLE IF EXISTS `tournaments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tournaments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `closing_location` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `closing_time` datetime DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `donor` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `opening_location` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `opening_time` datetime DEFAULT NULL,
  `short_name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2bwgf3e0af10o90l9w655102n` (`creator_id`),
  CONSTRAINT `FK2bwgf3e0af10o90l9w655102n` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tournaments`
--

LOCK TABLES `tournaments` WRITE;
/*!40000 ALTER TABLE `tournaments` DISABLE KEYS */;
INSERT INTO `tournaments` VALUES (1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `tournaments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_post`
--

DROP TABLE IF EXISTS `user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_post` (
  `user_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  KEY `FK5v5ofbgts9mga5qqmuv0ga3ch` (`post_id`),
  KEY `FK4rsm4o22uyvrutr0xeybcrm50` (`user_id`),
  CONSTRAINT `FK4rsm4o22uyvrutr0xeybcrm50` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK5v5ofbgts9mga5qqmuv0ga3ch` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_post`
--

LOCK TABLES `user_post` WRITE;
/*!40000 ALTER TABLE `user_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tournament`
--

DROP TABLE IF EXISTS `user_tournament`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tournament` (
  `user_id` bigint(20) NOT NULL,
  `tournament_id` bigint(20) NOT NULL,
  KEY `FKfxknrygroocyqvc6i90bucacb` (`tournament_id`),
  KEY `FKh2vtyvre1ad7i75pdox6605ph` (`user_id`),
  CONSTRAINT `FKfxknrygroocyqvc6i90bucacb` FOREIGN KEY (`tournament_id`) REFERENCES `tournaments` (`id`),
  CONSTRAINT `FKh2vtyvre1ad7i75pdox6605ph` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tournament`
--

LOCK TABLES `user_tournament` WRITE;
/*!40000 ALTER TABLE `user_tournament` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_tournament` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `background` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `dob` datetime DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `gender` bit(1) NOT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,_binary '','Phu Tho',NULL,'background.jpg',NULL,NULL,'1998-12-12 07:00:00','Hieu@gmail.com',NULL,_binary '\0',NULL,NULL,NULL,'$2a$10$IUxOY6B0tRgXLlYYT1QPau74YY2jSiKuIHX8MdggeHuF.CO8lL6q6',NULL,'Hieu',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_token`
--

DROP TABLE IF EXISTS `verification_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `confirmed_date_time` datetime DEFAULT NULL,
  `expired_date_time` datetime DEFAULT NULL,
  `issued_date_time` datetime DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `token` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3asw9wnv76uxu3kr1ekq4i1ld` (`user_id`),
  CONSTRAINT `FK3asw9wnv76uxu3kr1ekq4i1ld` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_token`
--

LOCK TABLES `verification_token` WRITE;
/*!40000 ALTER TABLE `verification_token` DISABLE KEYS */;
INSERT INTO `verification_token` VALUES (1,NULL,'2020-07-23 18:19:46','2020-07-22 18:19:46','PENDING','b1b45952-c8aa-4cc3-abac-809769c00a0b',1);
/*!40000 ALTER TABLE `verification_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'do_an_2020'
--

--
-- Dumping routines for database 'do_an_2020'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-22 18:58:34
