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
  CONSTRAINT `fk_tb_pedido_venda_tb_cliente_fisica1` FOREIGN KEY (`id_cliente_fisica_fk`) REFERENCES `tb_cliente_fisica` (`id_cliente_fisica`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_tb_pedido_venda_tb_cliente_juridica1` FOREIGN KEY (`id_cliente_juridica_fk`) REFERENCES `tb_cliente_juridica` (`id_cliente_juridica`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_tb_pedido_venda_tb_fluxo_caixa1` FOREIGN KEY (`id_fluxo_caixa_fk`) REFERENCES `tb_fluxo_caixa` (`id_fluxo_caixa`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_tb_pedido_venda_tb_funcionario1` FOREIGN KEY (`id_funcionario_fk`) REFERENCES `tb_funcionario` (`id_funcionario`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_pedido_venda`
--

LOCK TABLES `tb_pedido_venda` WRITE;
/*!40000 ALTER TABLE `tb_pedido_venda` DISABLE KEYS */;
INSERT INTO `tb_pedido_venda` VALUES (10,5,NULL,7,NULL,121.50,0.00,'debito','cliente_fisica'),(11,5,NULL,7,NULL,121.50,0.00,'debito','cliente_fisica'),(12,5,NULL,7,NULL,121.50,0.00,'debito','cliente_fisica'),(13,5,NULL,7,NULL,150.00,0.00,'debito','cliente_fisica'),(14,5,NULL,7,NULL,600.00,0.00,'debito','cliente_fisica'),(15,5,NULL,7,NULL,1800.00,0.00,'dinheiro','cliente_fisica'),(16,5,NULL,7,NULL,450.00,0.00,'credito','cliente_fisica'),(17,5,NULL,7,NULL,540.00,60.00,'debito','cliente_fisica'),(18,5,NULL,7,NULL,6240.00,4500.00,'debito','cliente_fisica'),(19,5,NULL,7,NULL,16650.00,0.00,'debito','cliente_fisica'),(20,5,NULL,7,NULL,300.00,0.00,'debito','cliente_fisica'),(21,5,NULL,7,NULL,32.97,0.00,'debito','cliente_fisica'),(22,5,NULL,7,NULL,331.40,0.00,'debito','cliente_fisica'),(23,6,NULL,7,NULL,55.26,13.82,'debito','cliente_fisica'),(24,6,NULL,7,NULL,2500.00,0.00,'dinheiro','cliente_fisica'),(25,6,NULL,7,NULL,50.00,0.00,'debito','cliente_fisica'),(26,5,NULL,7,NULL,2250.00,0.00,'debito','cliente_fisica'),(27,5,NULL,7,NULL,1800.00,0.00,'dinheiro','cliente_fisica'),(28,NULL,13,7,NULL,1750.00,0.00,'credito','cliente_juridica'),(29,NULL,NULL,7,NULL,2050.00,0.00,'debito','cliente_juridica'),(30,7,NULL,7,NULL,2500.00,0.00,'debito','cliente_fisica'),(31,5,NULL,7,NULL,2750.00,0.00,'debito','cliente_fisica'),(32,NULL,NULL,7,NULL,600.00,0.00,'dinheiro','Anônimo'),(33,6,NULL,7,NULL,50.00,0.00,'debito','cliente_fisica'),(34,NULL,NULL,7,NULL,270950.00,0.00,'debito','Anônimo'),(35,NULL,NULL,7,NULL,25000.00,0.00,'debito','Anônimo'),(36,NULL,NULL,7,NULL,0.00,0.00,'debito','Anônimo'),(37,6,NULL,7,NULL,2657.00,0.00,'debito','cliente_fisica'),(38,NULL,NULL,7,NULL,1200.00,300.00,'debito','Anônimo'),(39,5,NULL,7,NULL,850.00,50.00,'debito','cliente_fisica'),(40,6,NULL,7,2,900.00,0.00,'debito','cliente_fisica'),(41,5,NULL,7,4,900.00,0.00,'debito','cliente_fisica'),(42,5,NULL,7,5,94.20,0.00,'debito','cliente_fisica'),(43,5,NULL,7,6,1500.00,0.00,'debito','cliente_fisica'),(44,5,NULL,7,7,1500.00,0.00,'debito','cliente_fisica'),(45,6,NULL,7,8,1500.00,0.00,'debito','cliente_fisica'),(46,5,NULL,7,9,30.00,0.00,'dinheiro','cliente_fisica'),(47,6,NULL,7,10,600.00,0.00,'dinheiro','cliente_fisica'),(48,5,NULL,7,11,600.00,0.00,'credito','cliente_fisica'),(49,5,NULL,7,12,720.00,0.00,'credito','cliente_fisica'),(50,5,NULL,7,13,1500.00,0.00,'credito','cliente_fisica'),(51,5,NULL,7,14,1500.00,0.00,'dinheiro','cliente_fisica');
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

-- Dump completed on 2017-06-23 18:58:18
