-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_posto_gasolina
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.19-MariaDB

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
-- Table structure for table `tb_telefone_cliente_fisica`
--

DROP TABLE IF EXISTS `tb_telefone_cliente_fisica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_telefone_cliente_fisica` (
  `id_telefone` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente_fisica_fk` int(11) NOT NULL,
  `telefone` varchar(14) DEFAULT NULL,
  PRIMARY KEY (`id_telefone`,`id_cliente_fisica_fk`),
  KEY `fk_tb_telefone_copy1_tb_cliente_fisica1_idx` (`id_cliente_fisica_fk`),
  CONSTRAINT `fk_tb_telefone_copy1_tb_cliente_fisica1` FOREIGN KEY (`id_cliente_fisica_fk`) REFERENCES `tb_cliente_fisica` (`id_cliente_fisica`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_telefone_cliente_fisica`
--

LOCK TABLES `tb_telefone_cliente_fisica` WRITE;
/*!40000 ALTER TABLE `tb_telefone_cliente_fisica` DISABLE KEYS */;
INSERT INTO `tb_telefone_cliente_fisica` VALUES (1,5,'(41)88899-6555'),(2,5,'(33)2255-4488'),(3,5,'(44)55887-799 '),(4,5,'(43)55553-3333'),(5,6,'(41)98777-6400'),(6,5,'(66)66666-6666'),(7,5,'(33)3333-3333'),(8,5,'(66)66666-6666'),(17,7,'(65)65656-565 '),(18,7,'(65)6565-6565');
/*!40000 ALTER TABLE `tb_telefone_cliente_fisica` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-23 18:58:16
