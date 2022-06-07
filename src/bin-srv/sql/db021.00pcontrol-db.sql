-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: peritcontrol
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asegurado`
--

DROP TABLE IF EXISTS `asegurado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asegurado` (
  `codAsegurado` int unsigned NOT NULL AUTO_INCREMENT,
  `nombreAseg` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `direccionRiesgo` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `direccionContacto` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `contactoAseg` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`codAsegurado`)
) ENGINE=InnoDB AUTO_INCREMENT=112354 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `compania`
--

DROP TABLE IF EXISTS `compania`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compania` (
  `codCompania` int unsigned NOT NULL AUTO_INCREMENT,
  `nombreCom` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `estadoCom` enum('ACTIVO','INACTIVO') CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (`codCompania`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura` (
  `codFactura` int unsigned NOT NULL AUTO_INCREMENT,
  `numFactura` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `codHGabinete` int unsigned NOT NULL,
  `codHPerito` int unsigned NOT NULL,
  `estadoPagoFac` enum('PAGO','IMPAGO') CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL DEFAULT 'IMPAGO',
  `mesFacPerito` smallint DEFAULT NULL,
  `anoFacPerito` smallint DEFAULT NULL,
  PRIMARY KEY (`codFactura`),
  KEY `idk_HGab_idx` (`codHGabinete`),
  KEY `idk_HPer_idx` (`codHPerito`),
  KEY `idk_numFact` (`numFactura`),
  CONSTRAINT `idk_HGab` FOREIGN KEY (`codHGabinete`) REFERENCES `hgabinete` (`codHGabinete`),
  CONSTRAINT `idk_HPer` FOREIGN KEY (`codHPerito`) REFERENCES `hperito` (`codHPerito`)
) ENGINE=InnoDB AUTO_INCREMENT=112354 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hgabinete`
--

DROP TABLE IF EXISTS `hgabinete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hgabinete` (
  `codHGabinete` int unsigned NOT NULL AUTO_INCREMENT,
  `honorarioGab` decimal(10,2) DEFAULT '0.00',
  `locomocionGab` decimal(10,2) DEFAULT '0.00',
  `variosGab` decimal(10,2) DEFAULT '0.00',
  `sumaGab` decimal(10,2) DEFAULT '0.00',
  `ivaGab` decimal(10,2) DEFAULT '0.00',
  `totalGab` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`codHGabinete`)
) ENGINE=InnoDB AUTO_INCREMENT=112401 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hperito`
--

DROP TABLE IF EXISTS `hperito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hperito` (
  `codHPerito` int unsigned NOT NULL AUTO_INCREMENT,
  `honorarioPer` decimal(10,2) DEFAULT '0.00',
  `locomocionPer` decimal(10,2) DEFAULT '0.00',
  `ajustesPer` decimal(10,2) DEFAULT '0.00',
  `sumaPer` decimal(10,2) DEFAULT '0.00',
  `ivaPer` decimal(10,2) DEFAULT '0.00',
  `irpfPer` decimal(10,2) DEFAULT '0.00',
  `totalPer` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`codHPerito`)
) ENGINE=InnoDB AUTO_INCREMENT=112401 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `perito`
--

DROP TABLE IF EXISTS `perito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perito` (
  `codPerito` int unsigned NOT NULL AUTO_INCREMENT,
  `nombrePer` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `nifPer` varchar(9) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `direccionPer` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `telefonoPer` int DEFAULT NULL,
  `emailPer` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `estadoPer` enum('ACTIVO','INACTIVO') CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT 'ACTIVO',
  PRIMARY KEY (`codPerito`),
  KEY `idx_Perito` (`codPerito`),
  KEY `idx_nomP` (`nombrePer`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ramo`
--

DROP TABLE IF EXISTS `ramo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ramo` (
  `codRamo` int unsigned NOT NULL AUTO_INCREMENT,
  `descripcionRam` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `codCompania` int unsigned NOT NULL,
  `estadoRam` enum('ACTIVO','INACTIVO') CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (`codRamo`),
  KEY `idk_Ram_Com_idx` (`codCompania`),
  CONSTRAINT `idk_Ram_Com` FOREIGN KEY (`codCompania`) REFERENCES `compania` (`codCompania`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reapertura`
--

DROP TABLE IF EXISTS `reapertura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reapertura` (
  `codReapertura` int unsigned NOT NULL AUTO_INCREMENT,
  `codRegistroAsociado` int unsigned NOT NULL,
  `codReaperturaReg` int unsigned NOT NULL,
  `codTipoReapertura` int unsigned NOT NULL,
  PRIMARY KEY (`codReapertura`),
  KEY `idk_TipoReapertura_idx` (`codTipoReapertura`),
  KEY `idk_RegistroAsoc_idx` (`codReaperturaReg`),
  CONSTRAINT `fk_tiporeap` FOREIGN KEY (`codTipoReapertura`) REFERENCES `tiporeapertura` (`codTipoReapertura`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registro`
--

DROP TABLE IF EXISTS `registro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro` (
  `codRegistro` int unsigned NOT NULL AUTO_INCREMENT,
  `codPerito` int unsigned NOT NULL,
  `fechaApertura` date NOT NULL,
  `referenciaCia` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `codAsegCom` int unsigned NOT NULL,
  `descripcionReg` varchar(3000) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `fechaEntregaPer` date DEFAULT NULL,
  `estadoEntrega` enum('PENDIENTE','ENTREGADO') CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL DEFAULT 'PENDIENTE',
  `estadoReg` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL DEFAULT 'ABIERTO',
  `codFactura` int unsigned NOT NULL,
  `fechaCierre` date DEFAULT NULL,
  `observaciones` varchar(3000) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `codUsuario` int unsigned DEFAULT NULL,
  `numeroReaperturas` smallint NOT NULL DEFAULT '0',
  `tipoRegistro` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL DEFAULT 'REGISTRO',
  `tipoInt` int unsigned NOT NULL DEFAULT '0',
  `contactoReg` varchar(3000) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`codRegistro`),
  KEY `fk_perito_idx` (`codPerito`),
  KEY `fk_siniestro_idx` (`codAsegCom`),
  KEY `fk_factura_idx` (`codFactura`),
  KEY `fk_usuario_idx` (`codUsuario`),
  KEY `fk_tipointervencion_idx` (`tipoInt`),
  CONSTRAINT `fk_perito` FOREIGN KEY (`codPerito`) REFERENCES `perito` (`codPerito`),
  CONSTRAINT `fk_siniestro` FOREIGN KEY (`codAsegCom`) REFERENCES `siniestro` (`codSiniestro`),
  CONSTRAINT `fk_tipointervencion` FOREIGN KEY (`tipoInt`) REFERENCES `tipointervencion` (`codTipoInt`)
) ENGINE=InnoDB AUTO_INCREMENT=112354 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `siniestro`
--

DROP TABLE IF EXISTS `siniestro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `siniestro` (
  `codSiniestro` int unsigned NOT NULL AUTO_INCREMENT,
  `codAsegurado` int unsigned NOT NULL,
  `codCompania` int unsigned NOT NULL,
  `codRamo` int unsigned NOT NULL,
  `codTipoSin` int unsigned NOT NULL,
  `numPoliza` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`codSiniestro`),
  KEY `idk_numpoliza` (`numPoliza`)
) ENGINE=InnoDB AUTO_INCREMENT=112354 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `telefono`
--

DROP TABLE IF EXISTS `telefono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `telefono` (
  `codTelefono` int unsigned NOT NULL AUTO_INCREMENT,
  `telefonoAseg` int NOT NULL,
  `codAsegurado` int unsigned NOT NULL,
  PRIMARY KEY (`codTelefono`),
  KEY `idk_Aseg_Telf_idx` (`codAsegurado`)
) ENGINE=InnoDB AUTO_INCREMENT=62326 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipointervencion`
--

DROP TABLE IF EXISTS `tipointervencion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipointervencion` (
  `codTipoInt` int unsigned NOT NULL AUTO_INCREMENT,
  `descripTipoInt` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `estadoTipoInt` enum('ACTIVO','INACTIVO') CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (`codTipoInt`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tiporeapertura`
--

DROP TABLE IF EXISTS `tiporeapertura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiporeapertura` (
  `codTipoReapertura` int unsigned NOT NULL AUTO_INCREMENT,
  `tipoReapertura` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `estadoTipoReap` enum('ACTIVO','INACTIVO') CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (`codTipoReapertura`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tiposiniestro`
--

DROP TABLE IF EXISTS `tiposiniestro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiposiniestro` (
  `codTipoSin` int unsigned NOT NULL AUTO_INCREMENT,
  `descripTSiniestro` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `estadoTSin` enum('ACTIVO','INACTIVO') CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (`codTipoSin`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `codUsuario` int unsigned NOT NULL AUTO_INCREMENT,
  `nombreUsr` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `passUsr` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `estadoUsr` enum('ACTIVO','INACTIVO') CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL DEFAULT 'ACTIVO',
  `tipoUsr` enum('ADMIN','COMUN') CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL DEFAULT 'COMUN',
  PRIMARY KEY (`codUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-07 13:02:21
