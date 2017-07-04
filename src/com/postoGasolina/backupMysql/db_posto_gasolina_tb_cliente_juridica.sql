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
-- Table structure for table `tb_cliente_juridica`
--

DROP TABLE IF EXISTS `tb_cliente_juridica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_cliente_juridica` (
  `id_cliente_juridica` int(11) NOT NULL AUTO_INCREMENT,
  `id_endereco_fk` int(11) DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `cnpj` varchar(18) DEFAULT NULL,
  `ie` varchar(30) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `situacao` varchar(45) DEFAULT NULL,
  `data_situacao` date DEFAULT NULL,
  PRIMARY KEY (`id_cliente_juridica`),
  KEY `fk_tb_cliente_juridica_tb_endereco1_idx` (`id_endereco_fk`),
  CONSTRAINT `fk_tb_cliente_juridica_tb_endereco1` FOREIGN KEY (`id_endereco_fk`) REFERENCES `tb_endereco` (`id_endereco`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_cliente_juridica`
--

LOCK TABLES `tb_cliente_juridica` WRITE;
/*!40000 ALTER TABLE `tb_cliente_juridica` DISABLE KEYS */;
INSERT INTO `tb_cliente_juridica` VALUES (13,27,'STF BRASIL - COMERCIO E SERVICOS LTDA - ME','07.784.255/0001-31','','aratuelas@uol.com.br','Comércio atacadista de ferragens e ferramentas','ATIVA','2005-12-12'),(18,49,'Hahaha','07.784.255/0001-31','','aratuelas@uol.com.br','Comércio atacadista de ferragens e ferramentas\n\n\n\n','ATIVA','2005-12-12');
/*!40000 ALTER TABLE `tb_cliente_juridica` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-23 18:58:15