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
-- Table structure for table `tb_licenca_autorizacao`
--

DROP TABLE IF EXISTS `tb_licenca_autorizacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_licenca_autorizacao` (
  `id_licenca_autorizacao` int(11) NOT NULL AUTO_INCREMENT,
  `id_orgao_fk` int(11) NOT NULL,
  `id_funcionario` int(11) NOT NULL,
  `descricao` varchar(155) DEFAULT NULL,
  `data_inicio` date DEFAULT NULL,
  `data_expiracao` date DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `descricao_responsavel_renovacao` varchar(155) DEFAULT NULL,
  `informacao_adicional` varchar(155) DEFAULT NULL,
  `arquivo_pdf` varchar(2083) DEFAULT NULL,
  PRIMARY KEY (`id_licenca_autorizacao`),
  KEY `fk_tb_licenca_autorizacao_tb_funcionario1_idx` (`id_funcionario`),
  KEY `fk_tb_licenca_autorizacao_tb_orgao1_idx` (`id_orgao_fk`),
  CONSTRAINT `fk_tb_licenca_autorizacao_tb_funcionario1` FOREIGN KEY (`id_funcionario`) REFERENCES `tb_funcionario` (`id_funcionario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_licenca_autorizacao_tb_orgao1` FOREIGN KEY (`id_orgao_fk`) REFERENCES `tb_orgao` (`id_orgao`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_licenca_autorizacao`
--

LOCK TABLES `tb_licenca_autorizacao` WRITE;
/*!40000 ALTER TABLE `tb_licenca_autorizacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_licenca_autorizacao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-17 12:09:37
