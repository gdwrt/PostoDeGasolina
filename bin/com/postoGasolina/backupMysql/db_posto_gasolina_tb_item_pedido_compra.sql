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
-- Table structure for table `tb_item_pedido_compra`
--

DROP TABLE IF EXISTS `tb_item_pedido_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_item_pedido_compra` (
  `id_pedido_compra_fk` int(11) NOT NULL,
  `id_produto_fk` int(11) DEFAULT NULL,
  `id_combustivel_fk` int(11) DEFAULT NULL,
  `tipo_produto` varchar(20) NOT NULL,
  `preco_unitario` decimal(10,2) DEFAULT NULL,
  `quantidade` decimal(10,2) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  KEY `fk_tb_item_pedido_compra_tb_pedido_compra1_idx` (`id_pedido_compra_fk`),
  KEY `fk_tb_item_pedido_compra_tb_produto1_idx` (`id_produto_fk`),
  KEY `fk_combustivel` (`id_combustivel_fk`),
  CONSTRAINT `fk_combustivel` FOREIGN KEY (`id_combustivel_fk`) REFERENCES `tb_combustivel` (`id_combustivel`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_item_pedido_compra_tb_pedido_compra1` FOREIGN KEY (`id_pedido_compra_fk`) REFERENCES `tb_pedido_compra` (`id_pedido_compra`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_item_pedido_compra_tb_produto1` FOREIGN KEY (`id_produto_fk`) REFERENCES `tb_produto` (`id_produto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_item_pedido_compra`
--

LOCK TABLES `tb_item_pedido_compra` WRITE;
/*!40000 ALTER TABLE `tb_item_pedido_compra` DISABLE KEYS */;
INSERT INTO `tb_item_pedido_compra` VALUES (1,NULL,9,'combustivel',3.14,50.00,157.00),(2,NULL,8,'combustivel',30.00,10.00,300.00),(2,NULL,9,'combustivel',3.14,10.00,31.40),(3,5,NULL,'produto',30.00,20.00,600.00),(3,4,NULL,'produto',50.00,100.00,5000.00),(3,3,NULL,'produto',50.00,1.00,50.00),(3,NULL,8,'combustivel',30.00,10.00,300.00),(3,NULL,9,'combustivel',3.14,10.00,31.40),(4,NULL,8,'combustivel',30.00,30.00,900.00),(5,NULL,8,'combustivel',30.00,30.00,900.00);
/*!40000 ALTER TABLE `tb_item_pedido_compra` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-23 18:58:17
