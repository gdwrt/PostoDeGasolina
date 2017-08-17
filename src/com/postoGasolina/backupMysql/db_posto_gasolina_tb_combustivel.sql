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
-- Table structure for table `tb_combustivel`
--

DROP TABLE IF EXISTS `tb_combustivel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_combustivel` (
  `id_combustivel` int(11) NOT NULL AUTO_INCREMENT,
  `id_tipo_combustivel_fk` int(11) NOT NULL,
  `id_bomba_fk` int(11) NOT NULL,
  `id_bico_fk` int(11) NOT NULL,
  `descricao` varchar(155) DEFAULT NULL,
  `preco_venda` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_combustivel`),
  KEY `fk_tb_combustivel_tb_tipo_combustivel1_idx` (`id_tipo_combustivel_fk`),
  KEY `fk_tb_combustivel_tb_bomba1_idx` (`id_bomba_fk`),
  KEY `fk_tb_combustivel_tb_bico1_idx` (`id_bico_fk`),
  CONSTRAINT `fk_tb_combustivel_tb_bico1` FOREIGN KEY (`id_bico_fk`) REFERENCES `tb_bico` (`id_bico`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_combustivel_tb_bomba1` FOREIGN KEY (`id_bomba_fk`) REFERENCES `tb_bomba` (`id_bomba`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_combustivel_tb_tipo_combustivel1` FOREIGN KEY (`id_tipo_combustivel_fk`) REFERENCES `tb_tipo_combustivel` (`id_tipo_combustivel`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_combustivel`
--

LOCK TABLES `tb_combustivel` WRITE;
/*!40000 ALTER TABLE `tb_combustivel` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_combustivel` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-17 12:09:38
