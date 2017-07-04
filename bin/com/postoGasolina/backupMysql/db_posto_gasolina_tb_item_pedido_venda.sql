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
-- Table structure for table `tb_item_pedido_venda`
--

DROP TABLE IF EXISTS `tb_item_pedido_venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_item_pedido_venda` (
  `id_pedido_venda_fk` int(11) NOT NULL,
  `id_produto_fk` int(11) DEFAULT NULL,
  `id_combustivel_fk` int(11) DEFAULT NULL,
  `tipo_produto` varchar(20) NOT NULL,
  `preco_unitario` decimal(10,2) DEFAULT NULL,
  `quantidade` decimal(10,2) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  KEY `fk_tb_item_pedido_venda_tb_pedido_venda1_idx` (`id_pedido_venda_fk`),
  KEY `fk_tb_item_pedido_venda_tb_produto1_idx` (`id_combustivel_fk`),
  KEY `fk_tb_item_pedido_venda_tb_produto1` (`id_produto_fk`),
  CONSTRAINT `fk_tb_item_pedido_venda_tb_combustivel1` FOREIGN KEY (`id_combustivel_fk`) REFERENCES `tb_combustivel` (`id_combustivel`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tb_item_pedido_venda_tb_pedido_venda1` FOREIGN KEY (`id_pedido_venda_fk`) REFERENCES `tb_pedido_venda` (`id_pedido_venda`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tb_item_pedido_venda_tb_produto1` FOREIGN KEY (`id_produto_fk`) REFERENCES `tb_produto` (`id_produto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_item_pedido_venda`
--

LOCK TABLES `tb_item_pedido_venda` WRITE;
/*!40000 ALTER TABLE `tb_item_pedido_venda` DISABLE KEYS */;
INSERT INTO `tb_item_pedido_venda` VALUES (15,NULL,8,'combustivel',30.00,60.00,1800.00),(16,NULL,8,'combustivel',30.00,15.00,450.00),(17,NULL,8,'combustivel',30.00,20.00,600.00),(18,NULL,8,'combustivel',30.00,300.00,9000.00),(18,NULL,8,'combustivel',30.00,50.00,1500.00),(18,NULL,8,'combustivel',30.00,8.00,240.00),(19,NULL,8,'combustivel',30.00,555.00,16650.00),(20,NULL,8,'combustivel',30.00,10.00,300.00),(21,NULL,9,'combustivel',3.14,10.50,32.97),(22,NULL,8,'combustivel',30.00,10.00,300.00),(22,NULL,9,'combustivel',3.14,10.00,31.40),(23,NULL,9,'combustivel',3.14,22.00,69.08),(24,4,NULL,'produto',50.00,50.00,2500.00),(25,3,NULL,'produto',50.00,1.00,50.00),(26,2,NULL,'produto',250.00,1.00,250.00),(26,4,NULL,'produto',50.00,40.00,2000.00),(27,3,NULL,'produto',50.00,1.00,50.00),(27,5,NULL,'produto',30.00,50.00,1500.00),(27,2,NULL,'produto',250.00,1.00,250.00),(28,2,NULL,'produto',250.00,1.00,250.00),(28,5,NULL,'produto',30.00,50.00,1500.00),(29,3,NULL,'produto',50.00,1.00,50.00),(29,4,NULL,'produto',50.00,40.00,2000.00),(30,4,NULL,'produto',50.00,50.00,2500.00),(31,4,NULL,'produto',50.00,50.00,2500.00),(31,2,NULL,'produto',250.00,1.00,250.00),(32,5,NULL,'produto',30.00,20.00,600.00),(33,3,NULL,'produto',50.00,1.00,50.00),(34,2,NULL,'produto',250.00,1.00,250.00),(34,2,NULL,'produto',250.00,1.00,250.00),(34,2,NULL,'produto',250.00,1.00,250.00),(34,4,NULL,'produto',50.00,5404.00,270200.00),(35,4,NULL,'produto',50.00,500.00,25000.00),(37,NULL,9,'combustivel',3.14,50.00,157.00),(37,4,NULL,'produto',50.00,50.00,2500.00),(38,NULL,8,'combustivel',30.00,50.00,1500.00),(39,NULL,8,'combustivel',30.00,30.00,900.00),(40,NULL,8,'combustivel',30.00,30.00,900.00),(41,NULL,8,'combustivel',30.00,30.00,900.00),(42,NULL,9,'combustivel',3.14,30.00,94.20),(43,NULL,8,'combustivel',30.00,50.00,1500.00),(44,NULL,8,'combustivel',30.00,50.00,1500.00),(45,NULL,8,'combustivel',30.00,50.00,1500.00),(46,NULL,8,'combustivel',30.00,1.00,30.00),(47,NULL,8,'combustivel',30.00,20.00,600.00),(48,NULL,8,'combustivel',30.00,20.00,600.00),(49,NULL,8,'combustivel',30.00,24.00,720.00),(50,NULL,8,'combustivel',30.00,50.00,1500.00),(51,NULL,8,'combustivel',30.00,50.00,1500.00);
/*!40000 ALTER TABLE `tb_item_pedido_venda` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-23 18:58:19
