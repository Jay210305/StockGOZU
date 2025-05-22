-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: localhost    Database: inventario_db
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.24.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `inventario`
--

DROP TABLE IF EXISTS `inventario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventario` (
  `sucursal_id` int NOT NULL,
  `producto_id` bigint NOT NULL,
  `cantidad` int NOT NULL DEFAULT '0',
  `stock_minimo` int NOT NULL DEFAULT '0',
  `actualizado_en` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sucursal_id`,`producto_id`),
  KEY `producto_id` (`producto_id`),
  KEY `idx_inventario_sucursal_producto` (`sucursal_id`,`producto_id`),
  CONSTRAINT `inventario_ibfk_1` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursal` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `inventario_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario`
--

LOCK TABLES `inventario` WRITE;
/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
INSERT INTO `inventario` VALUES (1,1,12,0,'2025-05-08 01:01:30');
/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimiento_inventario`
--

DROP TABLE IF EXISTS `movimiento_inventario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento_inventario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sucursal_id` int NOT NULL,
  `producto_id` bigint NOT NULL,
  `usuario_id` int NOT NULL,
  `tipo` enum('INGRESO','SALIDA') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `cantidad` int NOT NULL,
  `precio_compra` double DEFAULT NULL,
  `precio_venta` double DEFAULT NULL,
  `fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `comentario` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `producto_id` (`producto_id`),
  KEY `usuario_id` (`usuario_id`),
  KEY `idx_mov_fecha` (`fecha`),
  KEY `idx_mov_tipo` (`tipo`),
  KEY `idx_movimiento_sucursal_producto` (`sucursal_id`,`producto_id`),
  CONSTRAINT `movimiento_inventario_ibfk_1` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursal` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `movimiento_inventario_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `movimiento_inventario_ibfk_3` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento_inventario`
--

LOCK TABLES `movimiento_inventario` WRITE;
/*!40000 ALTER TABLE `movimiento_inventario` DISABLE KEYS */;
INSERT INTO `movimiento_inventario` VALUES (1,1,1,3,'INGRESO',12,18,19,'2025-05-08 00:58:12','prueba 1'),(2,1,1,3,'INGRESO',12,18,19,'2025-05-08 01:01:31','prueba');
/*!40000 ALTER TABLE `movimiento_inventario` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `prevent_negative_stock` BEFORE INSERT ON `movimiento_inventario` FOR EACH ROW BEGIN
  DECLARE current_stock INT;

  
  SELECT cantidad INTO current_stock
  FROM inventario
  WHERE sucursal_id = NEW.sucursal_id
    AND producto_id = NEW.producto_id;

  
  IF NEW.tipo = 'SALIDA' AND (current_stock - NEW.cantidad) < 0 THEN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Insufficient stock for this operation';
  END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `update_inventory_after_movement` AFTER INSERT ON `movimiento_inventario` FOR EACH ROW BEGIN
  IF NEW.tipo = 'INGRESO' THEN
    UPDATE inventario
    SET cantidad = cantidad + NEW.cantidad
    WHERE sucursal_id = NEW.sucursal_id
      AND producto_id = NEW.producto_id;
  ELSE
    UPDATE inventario
    SET cantidad = cantidad - NEW.cantidad
    WHERE sucursal_id = NEW.sucursal_id
      AND producto_id = NEW.producto_id;
  END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `descripcion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `codigo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `categoria` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unidad_medida` enum('kg','litro','unidad','metro','caja','otro') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'unidad',
  `precio_compra` double DEFAULT NULL,
  `precio_venta` double DEFAULT NULL,
  `creado_en` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_en` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sucursal_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`),
  KEY `FKairo81aonoew8q2rx9qmoh43m` (`sucursal_id`),
  CONSTRAINT `FKairo81aonoew8q2rx9qmoh43m` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursal` (`id`),
  CONSTRAINT `check_categoria` CHECK ((regexp_replace(`categoria`,_utf8mb4'[^a-zA-Z]+$',_utf8mb4'') = `categoria`)),
  CONSTRAINT `check_codigo` CHECK ((regexp_replace(`codigo`,_utf8mb4'[^a-zA-Z]+$',_utf8mb4'') = `codigo`)),
  CONSTRAINT `check_desc` CHECK ((regexp_replace(`descripcion`,_utf8mb4'[^a-zA-Z]+$',_utf8mb4'') = `descripcion`)),
  CONSTRAINT `check_nombre` CHECK ((regexp_replace(`nombre`,_utf8mb4'[^a-zA-Z]+$',_utf8mb4'') = `nombre`))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Test','Test','Test','Test','unidad',17,18,NULL,'2025-05-22 12:14:52',NULL),(6,'pan','a','aa','q','kg',1,185,NULL,'2025-05-22 12:44:25',NULL);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sucursal`
--

DROP TABLE IF EXISTS `sucursal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sucursal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ubicacion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creado_en` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sucursal`
--

LOCK TABLES `sucursal` WRITE;
/*!40000 ALTER TABLE `sucursal` DISABLE KEYS */;
INSERT INTO `sucursal` VALUES (1,'Cato','Catositio','2025-05-08 03:25:00',1);
/*!40000 ALTER TABLE `sucursal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `rol` enum('ADMIN','OPERADOR') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'OPERADOR',
  `creado_en` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  CONSTRAINT `check_nombreUser` CHECK ((regexp_replace(`nombre`,_utf8mb4'[^a-zA-Z]+$',_utf8mb4'') = `nombre`)),
  CONSTRAINT `check_usr` CHECK ((regexp_replace(`username`,_utf8mb4'[^a-zA-Z]+$',_utf8mb4'') = `username`))
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (3,'pepe','pepedos','$2a$10$1ov17YcTVy7cfb3f.27LyOhkhCFlOXDza4bvdUVvVN4cnyRU.ZsFu','ADMIN','2025-05-08 03:37:31',0),(4,'alonso','Jay','$2a$10$FsWmfJWu0qrxpxz554moy.Wd28DQ2wMO7dv2OAxp9hNi1PkH6SP0a','ADMIN','2025-05-08 07:56:09',1),(5,'robo','robert','$2a$10$1N0iCG0zYFbNZ0ZZ6A5YX.6jTec80UZDViN51MXx4vbXNmULPnqlW','OPERADOR','2025-05-22 16:36:56',0),(8,'pedro','pedro','$2a$10$UMmNKHwxbatL8APnRv9tQeIQ4b.DjWTQTxYxP61o5VyiTU.shGipy','OPERADOR','2025-05-22 16:45:38',0),(9,'pedro','paco','$2a$10$Qd59fMbC.Ii7W0irD2guX.DNWz4fa96cL2c3xi7wduA32njgXePJO','OPERADOR','2025-05-22 12:21:23',0),(10,'juan','juan','$2a$10$DECCE/jwb8kzpdVwAPzJ8.yn5TUJsmhofKzGXN3RaGqXYqsSZ4wCq','OPERADOR','2025-05-22 12:21:39',1),(11,'hola','hola','$2a$10$5H2eJDtmL6e.f6vCFrtAeOACQNKUI1NecbtxpeRuRjQ/VnQvXo3tm','OPERADOR','2025-05-22 12:23:02',0),(14,'rico','rico','$2a$10$twW7YeuxiSqWOJ5869c5p.lZU6KBVlVdXh2DmGQennwAkxKLHjqfK','OPERADOR','2025-05-22 12:35:36',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-22  7:54:41
