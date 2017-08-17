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
-- Table structure for table `tb_pedido_venda`
--

DROP TABLE IF EXISTS `tb_pedido_venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_pedido_venda` (
  `id_pedido_venda` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente_fisica_fk` int(11) DEFAULT NULL,
  `id_cliente_juridica_fk` int(11) DEFAULT NULL,
  `id_funcionario_fk` int(11) DEFAULT NULL,
  `id_fluxo_caixa_fk` int(11) DEFAULT NULL,
  `total_pagar` decimal(10,2) DEFAULT NULL,
  `desconto` decimal(10,2) DEFAULT NULL,
  `forma_pagamento` varchar(15) DEFAULT NULL,
  `tipo_cliente` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_pedido_venda`),
  KEY `fk_tb_pedido_venda_tb_funcionario1_idx` (`id_funcionario_fk`),
  KEY `fk_tb_pedido_venda_tb_fluxo_caixa1_idx` (`id_fluxo_caixa_fk`),
  KEY `fk_tb_pedido_venda_tb_cliente_fisica1` (`id_cliente_fisica_fk`),
  KEY `fk_tb_pedido_venda_tb_cliente_juridica1` (`id_cliente_juridica_fk`),
  CONSTRAINT `fk_tb_pedido_venda_tb_cliente_fisica1` FOREIGN KEY (`id_cliente_fisica_fk`) REFERENCES `tb_cliente_fisica` (`id_cliente_fisica`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_tb_pedido_venda_tb_cliente_juridica1` FOREIGN KEY (`id_cliente_juridica_fk`) REFERENCES `tb_cliente_juridica` (`id_cliente_juridica`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_tb_pedido_venda_tb_fluxo_caixa1` FOREIGN KEY (`id_fluxo_caixa_fk`) REFERENCES `tb_fluxo_caixa` (`id_fluxo_caixa`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_tb_pedido_venda_tb_funcionario1` FOREIGN KEY (`id_funcionario_fk`) REFERENCES `tb_funcionario` (`id_funcionario`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_pedido_venda`
--

LOCK TABLES `tb_pedido_venda` WRITE;
/*!40000 ALTER TABLE `tb_pedido_venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_pedido_venda` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-17 12:09:39
