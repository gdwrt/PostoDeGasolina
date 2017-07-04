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
-- Table structure for table `tb_endereco`
--

DROP TABLE IF EXISTS `tb_endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_endereco` (
  `id_endereco` int(11) NOT NULL AUTO_INCREMENT,
  `cep` varchar(9) DEFAULT NULL,
  `endereco` varchar(150) DEFAULT NULL,
  `numero` varchar(20) DEFAULT NULL,
  `complemento` varchar(150) DEFAULT NULL,
  `bairro` varchar(50) DEFAULT NULL,
  `uf` varchar(2) DEFAULT NULL,
  `cidade` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_endereco`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_endereco`
--

LOCK TABLES `tb_endereco` WRITE;
/*!40000 ALTER TABLE `tb_endereco` DISABLE KEYS */;
INSERT INTO `tb_endereco` VALUES (11,'83704-520','Rua Ana Saliba Nassar','','','Boqueirão','PR','Araucária'),(12,'83704-520','Rua Arapongas','100','bloco 6','pinheirinho','PR','curitiba'),(13,'83000-311','Rua Emanuel caetano','666','666','Agua de coco','AC','Salvador'),(14,'','R NILO CARDOSO BACELAR','195','BRCAO','CAPELA VELHA','PR','ARAUCARIA'),(27,'83705-576','R NILO CARDOSO BACELAR','195','BRCAO','CAPELA VELHA','PR','ARAUCARIA'),(28,'83704-520','Rua Ana Saliba Nassar','','','Boqueirão','PR','Araucária'),(33,'83705-576','R NILO CARDOSO BACELAR','195','BRCAO','CAPELA VELHA','PR','ARAUCARIA'),(34,'83705-576','R NILO CARDOSO BACELAR','195','BRCAO','CAPELA VELHA','PR','ARAUCARIA'),(35,'83705-576','R NILO CARDOSO BACELAR','195','BRCAO','CAPELA VELHA','PR','ARAUCARIA'),(36,'83705-576','R NILO CARDOSO BACELAR','195','BRCAO','CAPELA VELHA','PR','ARAUCARIA'),(37,'83705-576','R NILO CARDOSO BACELAR','195','BRCAO','CAPELA VELHA','PR','ARAUCARIA'),(48,'83705-576','R NILO CARDOSO BACELAR','195','BRCAO','CAPELA VELHA','PR','ARAUCARIA'),(49,'83705-576','R NILO CARDOSO BACELAR','195','BRCAO','CAPELA VELHA','PR','ARAUCARIA');
/*!40000 ALTER TABLE `tb_endereco` ENABLE KEYS */;
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