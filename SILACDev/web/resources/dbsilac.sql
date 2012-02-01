CREATE DATABASE  IF NOT EXISTS `dbsilacTEST` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `dbsilacTEST`;
-- MySQL dump 10.13  Distrib 5.1.40, for Win32 (ia32)
--
-- Host: 127.0.0.1    Database: dbsilac
-- ------------------------------------------------------
-- Server version	5.5.13

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
-- Table structure for table `tblpagina`
--

DROP TABLE IF EXISTS `tblpagina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblpagina` (
  `idPagina` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la pagina.',
  `idObjeto` smallint(5) unsigned NOT NULL COMMENT 'Identificador del objeto.',
  `url` text NOT NULL COMMENT 'URL (Uniform Resources Locator) o direccion de la pagina Web.',
  `titulo` varchar(254) DEFAULT NULL COMMENT 'Titulo de la página web.',
  `descripcion` text COMMENT 'Descripción del proposito de la página Web.',
  PRIMARY KEY (`idPagina`),
  KEY `FK_Objeto_Pagina` (`idObjeto`),
  CONSTRAINT `FK_Objeto_Pagina` FOREIGN KEY (`idObjeto`) REFERENCES `tblobjeto` (`idObjeto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblpagina`
--

LOCK TABLES `tblpagina` WRITE;
/*!40000 ALTER TABLE `tblpagina` DISABLE KEYS */;
INSERT INTO `tblpagina` VALUES (1,1,'produccion/index.xhtml','Producción',''),(2,2,'bancoSangre/index.xhtml','Banco de Sangre',''),(3,3,'inventario/index.xhtml','Inventario',''),(4,4,'rh/index.xhtml','Recursos Humanos',''),(5,5,'admon/index.xhtml','Mantenimiento',''),(6,6,'admon/index.xhtml','Administración',''),(7,7,'admon/frmSeccion.xhtml','Secciones de laboratorio',''),(8,8,'admon/frmInstituciones.xhtml','Instituciones externas',''),(9,9,'admon/frmLaboratorios.xhtml','Laboratorios externos',''),(10,10,'admon/frmMedicos.xhtml','Medicos',''),(11,11,'admon/frmDeptos.xhtml','Departamentos',''),(12,12,'admon/frmMunicipio.xhtml','Munucipios',''),(13,13,'admon/frmModalidades.xhtml','Modalidades',''),(14,14,'admon/frmTipoServicios.xhtml','Tipo de Servicio',''),(15,15,'admon/frmServicios.xhtml','Sevicios',''),(16,16,'admon/frmEspecialidades.xhtml','Especialidades',''),(17,17,'admon/frmUsuarios.xhtml','Cuentas de usuario',''),(18,18,'admon/frmObjetos.xhtml','Objetos',''),(19,19,'admon/frmRoles.xhtml','Roles',''),(20,20,'admon/frmAnioLaboral.xhtml','Año Laboral',''),(21,21,'rh/frmPlanificacion.xhtml','Planificación',''),(22,22,'rh/frmAsisCapacitacion.xhtml','Capacitaciones',''),(23,23,'rh/frmPermisos.xhtml','Permisos',''),(24,24,'rh/frmAsigPuesto.xhtml','Asignación de puestos',''),(25,25,'rh/frmActSalario.xhtml','Actualización de salario',''),(26,26,'rh/frmEmpleado.xhtml','Empleados',''),(27,27,'rh/frmPuesto.xhtml','Puestos',''),(28,28,'inventario/frmArticulos.xhtml','Reactivos e insumos',''),(29,29,'inventario/frmRegEntradas.xhtml','Entradas',''),(30,30,'inventario/frmRegSalidas.xhtml','Salidas',''),(31,31,'inventario/frmCatalogo.xhtml','Catalogo',''),(32,32,'inventario/frmUnidadMedida.xhtml','Unidades de medidad',''),(33,33,'inventario/frmProcedenciaArticulo.xhtml','Procedencia',''),(34,34,'produccion/frmRegSolicitud.xhtml','Registro de sulicitudes',''),(35,35,'admon/frmExamenes.xhtml','Examenes',''),(38,38,'objeto','análisis sanguíneo','ddddddddddddddd'),(39,39,'rh/frmAsignarSeccion.xhtml','Asignar Seccion',''),(40,40,'produccion/frmRegResultado.xhtml','Registro de Resultados',''),(41,41,'produccion/frmRegResultado.xhtml','Prueba',''),(42,42,'produccion/frmRegEntrega.xhtml','Entrega de resultados',''),(43,43,'produccion/frmGenEstadisticas.xhtml','Informes estadísticos ',''),(44,44,'produccion/frmGenResultado.xhtml','Generar resultados','');
/*!40000 ALTER TABLE `tblpagina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblbancosangre`
--

DROP TABLE IF EXISTS `tblbancosangre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblbancosangre` (
  `idBancoSangre` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del banco de sangre.',
  `idDonanteAutoExcluido` bigint(19) unsigned DEFAULT NULL COMMENT 'Identificador del donante excluido.',
  `estadoSangre` varchar(25) NOT NULL COMMENT 'Indica el estado de la sangre, este puede ser: transfundida, disponible, descartada.',
  `numeroBolsa` int(11) NOT NULL COMMENT 'Numero de la bolsa que contiene la sangre.',
  `tipoComponente` varchar(25) NOT NULL COMMENT 'Tipo de componente sanguieno, este puede ser: Completo, Globulos Rojos, Plasma sanguineo, etc.',
  PRIMARY KEY (`idBancoSangre`),
  KEY `FK_AutoExcluido_BancoSangre` (`idDonanteAutoExcluido`),
  CONSTRAINT `FK_AutoExcluido_BancoSangre` FOREIGN KEY (`idDonanteAutoExcluido`) REFERENCES `tblautoexcluido` (`idDonanteAutoExcluido`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblbancosangre`
--

LOCK TABLES `tblbancosangre` WRITE;
/*!40000 ALTER TABLE `tblbancosangre` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblbancosangre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblincidencia`
--

DROP TABLE IF EXISTS `tblincidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblincidencia` (
  `idIncidencia` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la incidencia.',
  `fecha` datetime NOT NULL COMMENT 'Fecha en que ocurrio la incidencia.',
  `motivo` text NOT NULL,
  `descripcion` text COMMENT 'Descripción del suceso ocurrido.',
  PRIMARY KEY (`idIncidencia`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblincidencia`
--

LOCK TABLES `tblincidencia` WRITE;
/*!40000 ALTER TABLE `tblincidencia` DISABLE KEYS */;
INSERT INTO `tblincidencia` VALUES (1,'2011-12-07 12:14:21','Muestra incompleta',NULL),(2,'2011-12-07 12:17:28','Muestra equivocada',NULL),(3,'2011-12-07 12:17:29','Muestra equivocada',NULL),(4,'2011-12-11 21:06:30','Muestra incompleta',NULL),(5,'2011-12-12 12:33:21','Muestra descompuesta',NULL),(6,'2011-12-12 12:34:07','Muestra equivocada',NULL),(7,'2011-12-12 12:35:02','Muestra incompleta',NULL);
/*!40000 ALTER TABLE `tblincidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblentregaresultado`
--

DROP TABLE IF EXISTS `tblentregaresultado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblentregaresultado` (
  `idEntregaResultado` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entrega de resultados.',
  `idPersona` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la persona.',
  `idExamenOrden` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la orden.',
  `fechaEntrega` datetime NOT NULL COMMENT 'Fecha en la que se realizó la entrega.',
  PRIMARY KEY (`idEntregaResultado`),
  KEY `FK_ExamenOrden_EntregaResultados` (`idExamenOrden`),
  KEY `FK_Persona_EntregaResultados` (`idPersona`),
  CONSTRAINT `FK_ExamenOrden_EntregaResultados` FOREIGN KEY (`idExamenOrden`) REFERENCES `tblexamen_orden` (`idExamenOrden`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Persona_EntregaResultados` FOREIGN KEY (`idPersona`) REFERENCES `tblpersona` (`idPersona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblentregaresultado`
--

LOCK TABLES `tblentregaresultado` WRITE;
/*!40000 ALTER TABLE `tblentregaresultado` DISABLE KEYS */;
INSERT INTO `tblentregaresultado` VALUES (10,171,134,'2011-12-11 15:08:38'),(11,6,135,'2011-12-11 15:08:54'),(12,176,145,'2011-12-11 15:09:39'),(14,8,134,'2011-12-20 12:39:54');
/*!40000 ALTER TABLE `tblentregaresultado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblproduccion`
--

DROP TABLE IF EXISTS `tblproduccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblproduccion` (
  `idProduccion` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del resultado.',
  `idParametro` int(11) unsigned NOT NULL,
  `idExamenOrden` bigint(19) unsigned NOT NULL,
  `valor` varchar(255) NOT NULL COMMENT 'Valor o resultado obtenido en la prueba.',
  PRIMARY KEY (`idProduccion`),
  KEY `fk_tblproduccion_tblexamen_orden` (`idExamenOrden`),
  KEY `fk_tblproduccion_tblparametro` (`idParametro`),
  CONSTRAINT `fk_tblproduccion_tblexamen_orden` FOREIGN KEY (`idExamenOrden`) REFERENCES `tblexamen_orden` (`idExamenOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblproduccion_tblparametro` FOREIGN KEY (`idParametro`) REFERENCES `tblparametro` (`idParametro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblproduccion`
--

LOCK TABLES `tblproduccion` WRITE;
/*!40000 ALTER TABLE `tblproduccion` DISABLE KEYS */;
INSERT INTO `tblproduccion` VALUES (77,1,134,'123.0'),(78,3,136,'Cafe'),(79,4,136,'Pastosa'),(80,1,135,'1.0'),(81,3,142,'Verde'),(82,4,142,'Pastosa'),(83,1,154,'12345.0'),(84,6,156,'Positivo'),(85,4,157,'Pastosa'),(86,3,157,'Verde');
/*!40000 ALTER TABLE `tblproduccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcargalaboral`
--

DROP TABLE IF EXISTS `tblcargalaboral`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcargalaboral` (
  `idCargaLaboral` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la carga laboral.',
  `horasAsignadas` smallint(5) unsigned NOT NULL COMMENT 'Horas asignadas  por el departamento de recursos humanos.',
  `mes` smallint(5) unsigned NOT NULL,
  `anio` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`idCargaLaboral`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcargalaboral`
--

LOCK TABLES `tblcargalaboral` WRITE;
/*!40000 ALTER TABLE `tblcargalaboral` DISABLE KEYS */;
INSERT INTO `tblcargalaboral` VALUES (1,120,0,2011),(2,20,1,2011),(3,52,2,2011),(4,57,3,2011),(5,180,10,2011),(6,180,11,2011);
/*!40000 ALTER TABLE `tblcargalaboral` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbldonante`
--

DROP TABLE IF EXISTS `tbldonante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbldonante` (
  `idDonate` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del donante',
  `idPersona` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la persona.',
  PRIMARY KEY (`idDonate`),
  KEY `FK_Persona_Donante` (`idPersona`),
  CONSTRAINT `FK_Persona_Donante` FOREIGN KEY (`idPersona`) REFERENCES `tblpersona` (`idPersona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbldonante`
--

LOCK TABLES `tbldonante` WRITE;
/*!40000 ALTER TABLE `tbldonante` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbldonante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbldetallepedido`
--

DROP TABLE IF EXISTS `tbldetallepedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbldetallepedido` (
  `idDetallePedido` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador del detalle.',
  `idPedido` int(11) NOT NULL COMMENT 'Identificador del articulo.',
  `idArticulo` int(11) unsigned NOT NULL COMMENT 'Identificador del articulo.',
  `consumoReal` double NOT NULL COMMENT 'Consumo real de materiales.',
  `cantidadSolicitada` double NOT NULL COMMENT 'Cantidad solicitada de materiales.',
  PRIMARY KEY (`idDetallePedido`),
  KEY `FK_Pedido_DetallePedido` (`idPedido`),
  KEY `FK_Articulo_DetallePedido` (`idArticulo`),
  CONSTRAINT `FK_Articulo_DetallePedido` FOREIGN KEY (`idArticulo`) REFERENCES `tblarticulo` (`idArticulo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Pedido_DetallePedido` FOREIGN KEY (`idPedido`) REFERENCES `tblpedido` (`idPedido`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbldetallepedido`
--

LOCK TABLES `tbldetallepedido` WRITE;
/*!40000 ALTER TABLE `tbldetallepedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbldetallepedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblorden`
--

DROP TABLE IF EXISTS `tblorden`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblorden` (
  `idOrden` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la orden.',
  `idEspecialidad` smallint(5) unsigned NOT NULL COMMENT 'Identificador de la especialidad.',
  `idPaciente` bigint(19) unsigned NOT NULL COMMENT 'Identificador del paciente.',
  `idServicio` smallint(5) unsigned NOT NULL COMMENT 'Identificador del servicio',
  `idMedico` int(11) unsigned NOT NULL COMMENT 'Identificador del médico.',
  `codigoMarcacion` smallint(5) unsigned NOT NULL,
  `fechaRecepcion` datetime NOT NULL COMMENT 'Fecha en la que se recibio la orden.',
  `observacionesRecepcion` text COMMENT 'Observaciones o comentarios sobre la recepción de la orden.',
  `observaiconesResultados` text COMMENT 'Observaciones o comentarios durante el registro de los resultados.',
  `numeroCama` int(11) DEFAULT NULL COMMENT 'Número de cama. Solo para pacientes hospitalizados.',
  PRIMARY KEY (`idOrden`),
  KEY `FK_Paciente_Orden` (`idPaciente`),
  KEY `FK_Medico_Orden` (`idMedico`),
  KEY `FK_Servicio_Orden` (`idServicio`),
  KEY `FK_Especialidad_Orden` (`idEspecialidad`),
  KEY `fk_tblorden_tblempleado` (`codigoMarcacion`),
  CONSTRAINT `FK_Especialidad_Orden` FOREIGN KEY (`idEspecialidad`) REFERENCES `tblespecialidad` (`idEspecialidad`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Medico_Orden` FOREIGN KEY (`idMedico`) REFERENCES `tblmedico` (`idMedico`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Paciente_Orden` FOREIGN KEY (`idPaciente`) REFERENCES `tblpaciente` (`idPaciente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Servicio_Orden` FOREIGN KEY (`idServicio`) REFERENCES `tblservicio` (`idServicio`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tblorden_tblempleado` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblorden`
--

LOCK TABLES `tblorden` WRITE;
/*!40000 ALTER TABLE `tblorden` DISABLE KEYS */;
INSERT INTO `tblorden` VALUES (151,3,161,3,1,1,'2011-09-30 14:50:12',NULL,NULL,NULL),(152,3,161,3,2,1,'2011-12-30 15:01:12',NULL,NULL,NULL),(153,1,161,3,1,1,'2011-12-30 16:13:40',NULL,NULL,NULL),(154,1,164,1,1,1,'2011-11-30 16:40:01',NULL,NULL,NULL),(157,4,167,3,2,1,'2011-11-30 17:08:51',NULL,NULL,NULL),(158,1,168,3,1,1,'2011-11-30 17:29:36',NULL,NULL,NULL),(159,1,169,1,1,1,'2011-11-30 18:50:17',NULL,NULL,NULL),(160,1,170,1,1,1,'2011-11-30 18:51:36',NULL,NULL,NULL),(161,1,171,2,1,1,'2011-11-30 18:53:33',NULL,NULL,NULL),(162,3,172,3,1,1,'2011-12-01 11:46:28',NULL,NULL,NULL),(163,3,173,4,2,1,'2011-12-01 11:51:44',NULL,NULL,NULL),(164,1,174,7,1,1,'2011-12-01 11:59:56',NULL,NULL,NULL),(165,8,175,3,6,1,'2011-12-01 14:05:15',NULL,NULL,NULL),(166,1,176,7,3,1,'2011-12-01 14:07:53',NULL,NULL,NULL),(167,1,177,3,5,1,'2011-12-01 14:12:11',NULL,NULL,NULL),(168,8,178,7,6,1,'2011-12-01 14:14:52',NULL,NULL,NULL),(169,8,179,1,3,1,'2011-12-02 15:36:06',NULL,NULL,NULL),(170,5,180,7,3,1,'2011-12-02 15:37:19',NULL,NULL,NULL),(171,5,181,1,1,1,'2011-12-27 20:23:10','Tiene malestar general',NULL,34),(173,1,183,2,1,1,'2012-01-01 17:53:47',NULL,NULL,NULL),(174,1,168,3,1,1,'2012-01-02 10:07:02','',NULL,234),(175,1,183,1,1,1,'2012-01-02 10:37:16','',NULL,NULL);
/*!40000 ALTER TABLE `tblorden` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblinstitucionservicio`
--

DROP TABLE IF EXISTS `tblinstitucionservicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblinstitucionservicio` (
  `idServicio` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador del servicio.',
  `idInstitucion` smallint(5) unsigned NOT NULL COMMENT 'Identificador de la institución.',
  `tipoInstitucion` varchar(25) NOT NULL COMMENT 'Tipo de institucion. Indica si el servicio lo presta el laboratorio u otra institución.',
  `fecha` datetime NOT NULL COMMENT 'Fecha en la que se recibió o prestó el servicio.',
  PRIMARY KEY (`idServicio`),
  KEY `FK_InstitucionServicio_Institucion` (`idInstitucion`),
  CONSTRAINT `FK_InstitucionServicio_Institucion` FOREIGN KEY (`idInstitucion`) REFERENCES `tblinstitucion` (`idInstitucion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblinstitucionservicio`
--

LOCK TABLES `tblinstitucionservicio` WRITE;
/*!40000 ALTER TABLE `tblinstitucionservicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblinstitucionservicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblsolicitudvih`
--

DROP TABLE IF EXISTS `tblsolicitudvih`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsolicitudvih` (
  `idSolicitudVIH` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la solicitud.',
  `idOrden` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la orden.',
  `motivo` text NOT NULL COMMENT 'Motivo por el cual se realiza la prueba.',
  PRIMARY KEY (`idSolicitudVIH`),
  KEY `FK_Orden_SolicitudVIH` (`idOrden`),
  CONSTRAINT `FK_Orden_SolicitudVIH` FOREIGN KEY (`idOrden`) REFERENCES `tblorden` (`idOrden`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblsolicitudvih`
--

LOCK TABLES `tblsolicitudvih` WRITE;
/*!40000 ALTER TABLE `tblsolicitudvih` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblsolicitudvih` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblbitacora`
--

DROP TABLE IF EXISTS `tblbitacora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblbitacora` (
  `idBitacora` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la bitácora.',
  `idUsuario` smallint(5) unsigned NOT NULL COMMENT 'Identificador del usuario.',
  `tipoOperacion` varchar(254) NOT NULL COMMENT 'Operación realizada por el usuario.',
  `fecha` datetime NOT NULL COMMENT 'Fecha en la que se realizó la operación.',
  `hora` datetime NOT NULL COMMENT 'Hora en la que se realizó la operación.',
  PRIMARY KEY (`idBitacora`),
  KEY `FK_Usuario_Bitacora` (`idUsuario`),
  CONSTRAINT `FK_Usuario_Bitacora` FOREIGN KEY (`idUsuario`) REFERENCES `tblusuario` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1322 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblbitacora`
--

LOCK TABLES `tblbitacora` WRITE;
/*!40000 ALTER TABLE `tblbitacora` DISABLE KEYS */;
INSERT INTO `tblbitacora` VALUES (1,1,'Inicio de sessión','2011-11-23 22:55:02','2011-11-23 22:55:02'),(2,1,'Inicio de sessión','2011-11-23 23:04:14','2011-11-23 23:04:14'),(3,1,'Cierre de sesión','2011-11-23 23:11:53','2011-11-23 23:11:53'),(4,1,'Inicio de sessión','2011-11-23 23:12:06','2011-11-23 23:12:06'),(5,1,'Cierre de sesión','2011-11-23 23:12:48','2011-11-23 23:12:48'),(6,1,'Inicio de sessión','2011-11-23 23:12:52','2011-11-23 23:12:52'),(7,1,'Inicio de sessión','2011-11-24 07:50:29','2011-11-24 07:50:29'),(8,1,'Cierre de sesión','2011-11-24 08:09:02','2011-11-24 08:09:02'),(9,1,'Inicio de sessión','2011-11-24 08:09:06','2011-11-24 08:09:06'),(10,1,'Cierre de sesión','2011-11-24 08:27:47','2011-11-24 08:27:47'),(11,1,'Inicio de sessión','2011-11-24 08:27:51','2011-11-24 08:27:51'),(12,1,'Cierre de sesión','2011-11-24 08:32:14','2011-11-24 08:32:14'),(13,1,'Inicio de sessión','2011-11-24 08:38:40','2011-11-24 08:38:40'),(14,1,'Cierre de sesión','2011-11-24 08:50:40','2011-11-24 08:50:40'),(15,1,'Inicio de sessión','2011-11-24 08:50:47','2011-11-24 08:50:47'),(16,1,'Cierre de sesión','2011-11-24 08:51:50','2011-11-24 08:51:50'),(17,1,'Inicio de sessión','2011-11-24 08:51:54','2011-11-24 08:51:54'),(18,1,'Inicio de sessión','2011-11-24 09:10:57','2011-11-24 09:10:57'),(19,1,'Cierre de sesión','2011-11-24 09:28:00','2011-11-24 09:28:00'),(20,1,'Inicio de sessión','2011-11-24 09:32:35','2011-11-24 09:32:35'),(21,1,'Cierre de sesión','2011-11-24 09:33:46','2011-11-24 09:33:46'),(22,1,'Inicio de sessión','2011-11-24 09:33:52','2011-11-24 09:33:52'),(23,1,'Inicio de sessión','2011-11-24 09:49:54','2011-11-24 09:49:54'),(24,1,'Cierre de sesión','2011-11-24 09:50:58','2011-11-24 09:50:58'),(25,1,'Inicio de sessión','2011-11-24 09:52:00','2011-11-24 09:52:00'),(26,1,'Cierre de sesión','2011-11-24 10:03:05','2011-11-24 10:03:05'),(27,1,'Inicio de sessión','2011-11-24 10:03:12','2011-11-24 10:03:12'),(28,1,'Cierre de sesión','2011-11-24 10:03:30','2011-11-24 10:03:30'),(29,1,'Inicio de sessión','2011-11-24 10:03:35','2011-11-24 10:03:35'),(30,1,'Inicio de sessión','2011-11-24 10:26:30','2011-11-24 10:26:30'),(31,1,'Inicio de sessión','2011-11-24 13:54:57','2011-11-24 13:54:57'),(32,1,'Inicio de sessión','2011-11-24 14:12:08','2011-11-24 14:12:08'),(33,1,'Inicio de sessión','2011-11-24 14:52:39','2011-11-24 14:52:39'),(34,1,'Inicio de sessión','2011-11-25 09:22:29','2011-11-25 09:22:29'),(35,1,'Cierre de sesión','2011-11-25 09:36:01','2011-11-25 09:36:01'),(36,1,'Inicio de sessión','2011-11-25 09:36:46','2011-11-25 09:36:46'),(37,1,'Inicio de sessión','2011-11-25 10:27:00','2011-11-25 10:27:00'),(38,1,'Inicio de sessión','2011-11-25 10:45:06','2011-11-25 10:45:06'),(39,1,'Cierre de sesión','2011-11-25 10:46:40','2011-11-25 10:46:40'),(40,1,'Inicio de sessión','2011-11-25 10:46:45','2011-11-25 10:46:45'),(41,1,'Cierre de sesión','2011-11-25 10:48:32','2011-11-25 10:48:32'),(42,1,'Inicio de sessión','2011-11-25 10:48:45','2011-11-25 10:48:45'),(43,1,'Inicio de sessión','2011-11-25 13:56:27','2011-11-25 13:56:27'),(44,1,'Cierre de sesión','2011-11-25 14:02:15','2011-11-25 14:02:15'),(45,1,'Inicio de sessión','2011-11-25 14:02:20','2011-11-25 14:02:20'),(46,1,'Cierre de sesión','2011-11-25 14:04:42','2011-11-25 14:04:42'),(47,1,'Inicio de sessión','2011-11-25 14:04:46','2011-11-25 14:04:46'),(48,1,'Cierre de sesión','2011-11-25 14:10:21','2011-11-25 14:10:21'),(49,1,'Inicio de sessión','2011-11-25 14:10:35','2011-11-25 14:10:35'),(50,1,'Cierre de sesión','2011-11-25 14:17:54','2011-11-25 14:17:54'),(51,1,'Inicio de sessión','2011-11-25 14:17:58','2011-11-25 14:17:58'),(52,1,'Inicio de sessión','2011-11-25 14:38:18','2011-11-25 14:38:18'),(53,1,'Inicio de sessión','2011-11-25 15:09:44','2011-11-25 15:09:44'),(54,1,'Inicio de sessión','2011-11-25 15:35:28','2011-11-25 15:35:28'),(55,1,'Cierre de sesión','2011-11-25 15:41:23','2011-11-25 15:41:23'),(56,1,'Inicio de sessión','2011-11-25 15:41:34','2011-11-25 15:41:34'),(57,1,'Cierre de sesión','2011-11-25 15:48:03','2011-11-25 15:48:03'),(58,1,'Inicio de sessión','2011-11-25 15:48:07','2011-11-25 15:48:07'),(59,1,'Cierre de sesión','2011-11-25 15:54:42','2011-11-25 15:54:42'),(60,1,'Inicio de sessión','2011-11-25 15:56:06','2011-11-25 15:56:06'),(61,1,'Cierre de sesión','2011-11-25 16:06:21','2011-11-25 16:06:21'),(62,1,'Inicio de sessión','2011-11-25 16:06:34','2011-11-25 16:06:34'),(63,1,'Cierre de sesión','2011-11-25 16:11:37','2011-11-25 16:11:37'),(64,1,'Inicio de sessión','2011-11-25 16:11:52','2011-11-25 16:11:52'),(65,1,'Cierre de sesión','2011-11-25 16:21:14','2011-11-25 16:21:14'),(66,1,'Inicio de sessión','2011-11-25 16:21:28','2011-11-25 16:21:28'),(67,1,'Inicio de sessión','2011-11-25 16:38:17','2011-11-25 16:38:17'),(68,1,'Cierre de sesión','2011-11-25 16:47:52','2011-11-25 16:47:52'),(69,1,'Inicio de sessión','2011-11-25 16:48:01','2011-11-25 16:48:01'),(70,1,'Cierre de sesión','2011-11-25 16:49:44','2011-11-25 16:49:44'),(71,1,'Inicio de sessión','2011-11-25 16:51:01','2011-11-25 16:51:01'),(72,1,'Cierre de sesión','2011-11-25 16:59:08','2011-11-25 16:59:08'),(73,1,'Inicio de sessión','2011-11-25 16:59:13','2011-11-25 16:59:13'),(74,1,'Cierre de sesión','2011-11-25 17:05:42','2011-11-25 17:05:42'),(75,1,'Inicio de sessión','2011-11-25 17:06:06','2011-11-25 17:06:06'),(76,1,'Cierre de sesión','2011-11-25 17:13:05','2011-11-25 17:13:05'),(77,1,'Inicio de sessión','2011-11-25 17:13:31','2011-11-25 17:13:31'),(78,1,'Inicio de sessión','2011-11-25 19:42:20','2011-11-25 19:42:20'),(79,1,'Inicio de sessión','2011-11-25 19:56:18','2011-11-25 19:56:18'),(80,1,'Cierre de sesión','2011-11-25 20:18:35','2011-11-25 20:18:35'),(81,1,'Inicio de sessión','2011-11-25 20:18:39','2011-11-25 20:18:39'),(82,1,'Inicio de sessión','2011-11-25 20:35:24','2011-11-25 20:35:24'),(83,1,'Inicio de sessión','2011-11-25 20:42:51','2011-11-25 20:42:51'),(84,1,'Inicio de sessión','2011-11-25 20:53:56','2011-11-25 20:53:56'),(85,1,'Cierre de sesión','2011-11-25 20:58:34','2011-11-25 20:58:34'),(86,1,'Inicio de sessión','2011-11-25 21:10:16','2011-11-25 21:10:16'),(87,1,'Inicio de sessión','2011-11-25 21:17:22','2011-11-25 21:17:22'),(88,1,'Inicio de sessión','2011-11-26 07:20:20','2011-11-26 07:20:20'),(89,1,'Inicio de sessión','2011-11-26 07:33:48','2011-11-26 07:33:48'),(90,1,'Cierre de sesión','2011-11-26 07:47:31','2011-11-26 07:47:31'),(91,1,'Inicio de sessión','2011-11-26 07:47:42','2011-11-26 07:47:42'),(92,1,'Inicio de sessión','2011-11-26 08:00:44','2011-11-26 08:00:44'),(93,1,'Cierre de sesión','2011-11-26 08:04:43','2011-11-26 08:04:43'),(94,1,'Inicio de sessión','2011-11-26 08:04:55','2011-11-26 08:04:55'),(95,1,'Inicio de sessión','2011-11-26 08:25:40','2011-11-26 08:25:40'),(96,1,'Inicio de sessión','2011-11-26 08:38:17','2011-11-26 08:38:17'),(97,1,'Cierre de sesión','2011-11-26 08:42:13','2011-11-26 08:42:13'),(98,1,'Inicio de sessión','2011-11-26 08:42:17','2011-11-26 08:42:17'),(99,1,'Inicio de sessión','2011-11-26 09:04:59','2011-11-26 09:04:59'),(100,1,'Inicio de sessión','2011-11-26 09:46:03','2011-11-26 09:46:03'),(101,1,'Inicio de sessión','2011-11-26 13:10:24','2011-11-26 13:10:24'),(102,1,'Inicio de sessión','2011-11-26 13:39:15','2011-11-26 13:39:15'),(103,1,'Inicio de sessión','2011-11-26 14:11:15','2011-11-26 14:11:15'),(104,1,'Cierre de sesión','2011-11-26 14:36:05','2011-11-26 14:36:05'),(105,1,'Inicio de sessión','2011-11-26 14:36:13','2011-11-26 14:36:13'),(106,1,'Inicio de sessión','2011-11-26 15:45:19','2011-11-26 15:45:19'),(107,1,'Inicio de sessión','2011-11-26 15:47:22','2011-11-26 15:47:22'),(108,1,'Inicio de sessión','2011-11-26 16:03:35','2011-11-26 16:03:35'),(109,1,'Inicio de sessión','2011-11-26 16:03:42','2011-11-26 16:03:42'),(110,1,'Cierre de sesión','2011-11-26 16:04:17','2011-11-26 16:04:17'),(111,1,'Inicio de sessión','2011-11-26 16:04:22','2011-11-26 16:04:22'),(112,1,'Cierre de sesión','2011-11-26 16:09:18','2011-11-26 16:09:18'),(113,1,'Inicio de sessión','2011-11-26 16:09:33','2011-11-26 16:09:33'),(114,1,'Cierre de sesión','2011-11-26 16:14:49','2011-11-26 16:14:49'),(115,1,'Inicio de sessión','2011-11-26 16:14:54','2011-11-26 16:14:54'),(116,1,'Cierre de sesión','2011-11-26 16:25:21','2011-11-26 16:25:21'),(117,1,'Inicio de sessión','2011-11-26 16:25:38','2011-11-26 16:25:38'),(118,1,'Cierre de sesión','2011-11-26 16:35:04','2011-11-26 16:35:04'),(119,1,'Inicio de sessión','2011-11-26 16:35:19','2011-11-26 16:35:19'),(120,1,'Cierre de sesión','2011-11-26 16:44:06','2011-11-26 16:44:06'),(121,1,'Inicio de sessión','2011-11-26 16:44:18','2011-11-26 16:44:18'),(122,1,'Cierre de sesión','2011-11-26 16:44:21','2011-11-26 16:44:21'),(123,1,'Inicio de sessión','2011-11-26 16:44:29','2011-11-26 16:44:29'),(124,1,'Inicio de sessión','2011-11-26 17:37:22','2011-11-26 17:37:22'),(125,1,'Cierre de sesión','2011-11-26 17:42:30','2011-11-26 17:42:30'),(126,1,'Inicio de sessión','2011-11-26 17:50:07','2011-11-26 17:50:07'),(127,1,'Cierre de sesión','2011-11-26 17:56:37','2011-11-26 17:56:37'),(128,1,'Inicio de sessión','2011-11-26 17:58:59','2011-11-26 17:58:59'),(129,1,'Cierre de sesión','2011-11-26 18:08:24','2011-11-26 18:08:24'),(130,1,'Inicio de sessión','2011-11-26 18:08:36','2011-11-26 18:08:36'),(131,1,'Inicio de sessión','2011-11-26 18:24:46','2011-11-26 18:24:46'),(132,1,'Cierre de sesión','2011-11-26 18:26:25','2011-11-26 18:26:25'),(133,1,'Inicio de sessión','2011-11-26 18:31:19','2011-11-26 18:31:19'),(134,1,'Inicio de sessión','2011-11-26 18:52:42','2011-11-26 18:52:42'),(135,1,'Cierre de sesión','2011-11-26 18:55:34','2011-11-26 18:55:34'),(136,1,'Inicio de sessión','2011-11-26 18:55:49','2011-11-26 18:55:49'),(137,1,'Cierre de sesión','2011-11-26 18:56:46','2011-11-26 18:56:46'),(138,1,'Inicio de sessión','2011-11-26 19:31:19','2011-11-26 19:31:19'),(139,1,'Cierre de sesión','2011-11-26 19:39:28','2011-11-26 19:39:28'),(140,1,'Inicio de sessión','2011-11-26 19:39:47','2011-11-26 19:39:47'),(141,1,'Inicio de sessión','2011-11-26 20:09:22','2011-11-26 20:09:22'),(142,1,'Inicio de sessión','2011-11-26 20:25:20','2011-11-26 20:25:20'),(143,1,'Cierre de sesión','2011-11-26 20:26:04','2011-11-26 20:26:04'),(144,1,'Inicio de sessión','2011-11-26 20:27:17','2011-11-26 20:27:17'),(145,1,'Inicio de sessión','2011-11-26 20:50:46','2011-11-26 20:50:46'),(146,1,'Cierre de sesión','2011-11-26 20:56:21','2011-11-26 20:56:21'),(147,1,'Inicio de sessión','2011-11-26 21:20:08','2011-11-26 21:20:08'),(148,1,'Cierre de sesión','2011-11-26 21:28:56','2011-11-26 21:28:56'),(149,1,'Inicio de sessión','2011-11-26 21:29:11','2011-11-26 21:29:11'),(150,1,'Cierre de sesión','2011-11-26 21:41:37','2011-11-26 21:41:37'),(151,1,'Inicio de sessión','2011-11-26 21:41:48','2011-11-26 21:41:48'),(152,1,'Inicio de sessión','2011-11-26 22:14:01','2011-11-26 22:14:01'),(153,1,'Inicio de sessión','2011-11-26 22:16:55','2011-11-26 22:16:55'),(154,1,'Inicio de sessión','2011-11-26 22:26:06','2011-11-26 22:26:06'),(155,1,'Inicio de sessión','2011-11-26 22:42:11','2011-11-26 22:42:11'),(156,1,'Inicio de sessión','2011-11-26 22:55:38','2011-11-26 22:55:38'),(157,1,'Inicio de sessión','2011-11-26 23:13:49','2011-11-26 23:13:49'),(158,1,'Inicio de sessión','2011-11-26 23:31:28','2011-11-26 23:31:28'),(159,1,'Inicio de sessión','2011-11-26 23:32:37','2011-11-26 23:32:37'),(160,1,'Cierre de sesión','2011-11-26 23:35:09','2011-11-26 23:35:09'),(161,1,'Inicio de sessión','2011-11-26 23:35:14','2011-11-26 23:35:14'),(162,1,'Cierre de sesión','2011-11-26 23:46:50','2011-11-26 23:46:50'),(163,1,'Inicio de sessión','2011-11-26 23:46:58','2011-11-26 23:46:58'),(164,1,'Cierre de sesión','2011-11-26 23:54:14','2011-11-26 23:54:14'),(165,1,'Inicio de sessión','2011-11-26 23:54:18','2011-11-26 23:54:18'),(166,1,'Inicio de sessión','2011-11-26 23:55:57','2011-11-26 23:55:57'),(167,1,'Cierre de sesión','2011-11-26 23:58:49','2011-11-26 23:58:49'),(168,1,'Inicio de sessión','2011-11-26 23:59:25','2011-11-26 23:59:25'),(169,1,'Cierre de sesión','2011-11-27 00:06:58','2011-11-27 00:06:58'),(170,1,'Inicio de sessión','2011-11-27 00:08:05','2011-11-27 00:08:05'),(171,1,'Cierre de sesión','2011-11-27 00:12:11','2011-11-27 00:12:11'),(172,1,'Inicio de sessión','2011-11-27 00:12:15','2011-11-27 00:12:15'),(173,1,'Inicio de sessión','2011-11-27 00:14:46','2011-11-27 00:14:46'),(174,1,'Cierre de sesión','2011-11-27 00:15:39','2011-11-27 00:15:39'),(175,1,'Inicio de sessión','2011-11-27 00:15:43','2011-11-27 00:15:43'),(176,1,'Cierre de sesión','2011-11-27 00:21:11','2011-11-27 00:21:11'),(177,1,'Inicio de sessión','2011-11-27 00:21:23','2011-11-27 00:21:23'),(178,1,'Inicio de sessión','2011-11-27 00:41:33','2011-11-27 00:41:33'),(179,1,'Inicio de sessión','2011-11-27 00:42:39','2011-11-27 00:42:39'),(180,1,'Cierre de sesión','2011-11-27 00:46:17','2011-11-27 00:46:17'),(181,1,'Inicio de sessión','2011-11-27 00:46:29','2011-11-27 00:46:29'),(182,1,'Cierre de sesión','2011-11-27 00:52:52','2011-11-27 00:52:52'),(183,1,'Inicio de sessión','2011-11-27 00:52:56','2011-11-27 00:52:56'),(184,1,'Cierre de sesión','2011-11-27 00:56:52','2011-11-27 00:56:52'),(185,1,'Inicio de sessión','2011-11-27 00:56:56','2011-11-27 00:56:56'),(186,1,'Cierre de sesión','2011-11-27 01:02:53','2011-11-27 01:02:53'),(187,1,'Inicio de sessión','2011-11-27 01:03:05','2011-11-27 01:03:05'),(188,1,'Cierre de sesión','2011-11-27 01:07:57','2011-11-27 01:07:57'),(189,1,'Inicio de sessión','2011-11-27 01:08:03','2011-11-27 01:08:03'),(190,1,'Cierre de sesión','2011-11-27 01:11:29','2011-11-27 01:11:29'),(191,1,'Inicio de sessión','2011-11-27 01:12:06','2011-11-27 01:12:06'),(192,1,'Cierre de sesión','2011-11-27 01:16:17','2011-11-27 01:16:17'),(193,1,'Inicio de sessión','2011-11-27 01:16:55','2011-11-27 01:16:55'),(194,1,'Inicio de sessión','2011-11-27 14:42:43','2011-11-27 14:42:43'),(195,1,'Cierre de sesión','2011-11-27 14:45:49','2011-11-27 14:45:49'),(196,1,'Inicio de sessión','2011-11-27 14:46:00','2011-11-27 14:46:00'),(197,1,'Cierre de sesión','2011-11-27 14:49:41','2011-11-27 14:49:41'),(198,1,'Inicio de sessión','2011-11-27 14:49:59','2011-11-27 14:49:59'),(199,1,'Cierre de sesión','2011-11-27 15:01:49','2011-11-27 15:01:49'),(200,1,'Inicio de sessión','2011-11-27 15:02:03','2011-11-27 15:02:03'),(201,1,'Cierre de sesión','2011-11-27 15:06:03','2011-11-27 15:06:03'),(202,1,'Inicio de sessión','2011-11-27 15:06:17','2011-11-27 15:06:17'),(203,1,'Cierre de sesión','2011-11-27 15:14:29','2011-11-27 15:14:29'),(204,1,'Inicio de sessión','2011-11-27 15:14:40','2011-11-27 15:14:40'),(205,1,'Cierre de sesión','2011-11-27 15:20:46','2011-11-27 15:20:46'),(206,1,'Inicio de sessión','2011-11-27 15:21:01','2011-11-27 15:21:01'),(207,1,'Cierre de sesión','2011-11-27 15:31:03','2011-11-27 15:31:03'),(208,1,'Inicio de sessión','2011-11-27 15:31:15','2011-11-27 15:31:15'),(209,1,'Cierre de sesión','2011-11-27 15:40:51','2011-11-27 15:40:51'),(210,1,'Inicio de sessión','2011-11-27 15:41:02','2011-11-27 15:41:02'),(211,1,'Cierre de sesión','2011-11-27 15:44:05','2011-11-27 15:44:05'),(212,1,'Inicio de sessión','2011-11-27 15:46:53','2011-11-27 15:46:53'),(213,1,'Inicio de sessión','2011-11-27 16:03:07','2011-11-27 16:03:07'),(214,1,'Cierre de sesión','2011-11-27 16:08:29','2011-11-27 16:08:29'),(215,1,'Inicio de sessión','2011-11-27 16:08:38','2011-11-27 16:08:38'),(216,1,'Inicio de sessión','2011-11-27 22:14:12','2011-11-27 22:14:12'),(217,1,'Inicio de sessión','2011-11-27 22:37:48','2011-11-27 22:37:48'),(218,1,'Cierre de sesión','2011-11-27 22:51:01','2011-11-27 22:51:01'),(219,1,'Inicio de sessión','2011-11-27 22:51:57','2011-11-27 22:51:57'),(220,1,'Inicio de sessión','2011-11-27 23:08:33','2011-11-27 23:08:33'),(221,1,'Cierre de sesión','2011-11-27 23:12:50','2011-11-27 23:12:50'),(222,1,'Inicio de sessión','2011-11-27 23:13:04','2011-11-27 23:13:04'),(223,1,'Cierre de sesión','2011-11-27 23:20:30','2011-11-27 23:20:30'),(224,1,'Inicio de sessión','2011-11-27 23:24:30','2011-11-27 23:24:30'),(225,1,'Cierre de sesión','2011-11-27 23:33:36','2011-11-27 23:33:36'),(226,1,'Inicio de sessión','2011-11-27 23:33:39','2011-11-27 23:33:39'),(227,1,'Inicio de sessión','2011-11-28 08:47:17','2011-11-28 08:47:17'),(228,1,'Cierre de sesión','2011-11-28 08:58:03','2011-11-28 08:58:03'),(229,1,'Inicio de sessión','2011-11-28 08:58:08','2011-11-28 08:58:08'),(230,1,'Inicio de sessión','2011-11-28 09:21:08','2011-11-28 09:21:08'),(231,1,'Cierre de sesión','2011-11-28 09:25:01','2011-11-28 09:25:01'),(232,1,'Inicio de sessión','2011-11-28 09:27:20','2011-11-28 09:27:20'),(233,1,'Cierre de sesión','2011-11-28 09:30:46','2011-11-28 09:30:46'),(234,1,'Inicio de sessión','2011-11-28 09:30:58','2011-11-28 09:30:58'),(235,1,'Cierre de sesión','2011-11-28 09:40:48','2011-11-28 09:40:48'),(236,1,'Inicio de sessión','2011-11-28 09:41:19','2011-11-28 09:41:19'),(237,1,'Cierre de sesión','2011-11-28 09:47:17','2011-11-28 09:47:17'),(238,1,'Inicio de sessión','2011-11-28 09:47:21','2011-11-28 09:47:21'),(239,1,'Cierre de sesión','2011-11-28 09:50:55','2011-11-28 09:50:55'),(240,1,'Inicio de sessión','2011-11-28 09:51:25','2011-11-28 09:51:25'),(241,1,'Cierre de sesión','2011-11-28 09:53:42','2011-11-28 09:53:42'),(242,1,'Inicio de sessión','2011-11-28 09:53:59','2011-11-28 09:53:59'),(243,1,'Cierre de sesión','2011-11-28 10:04:46','2011-11-28 10:04:46'),(244,1,'Inicio de sessión','2011-11-28 10:04:50','2011-11-28 10:04:50'),(245,1,'Inicio de sessión','2011-11-28 10:13:55','2011-11-28 10:13:55'),(246,1,'Inicio de sessión','2011-11-28 10:34:03','2011-11-28 10:34:03'),(247,1,'Cierre de sesión','2011-11-28 10:37:00','2011-11-28 10:37:00'),(248,1,'Inicio de sessión','2011-11-28 10:41:05','2011-11-28 10:41:05'),(249,1,'Cierre de sesión','2011-11-28 10:43:02','2011-11-28 10:43:02'),(250,1,'Inicio de sessión','2011-11-28 10:43:06','2011-11-28 10:43:06'),(251,1,'Cierre de sesión','2011-11-28 10:46:11','2011-11-28 10:46:11'),(252,1,'Inicio de sessión','2011-11-28 10:46:15','2011-11-28 10:46:15'),(253,1,'Cierre de sesión','2011-11-28 10:54:10','2011-11-28 10:54:10'),(254,1,'Inicio de sessión','2011-11-28 10:54:19','2011-11-28 10:54:19'),(255,1,'Cierre de sesión','2011-11-28 11:05:27','2011-11-28 11:05:27'),(256,1,'Inicio de sessión','2011-11-28 11:05:33','2011-11-28 11:05:33'),(257,1,'Cierre de sesión','2011-11-28 11:26:39','2011-11-28 11:26:39'),(258,1,'Inicio de sessión','2011-11-28 11:26:48','2011-11-28 11:26:48'),(259,1,'Cierre de sesión','2011-11-28 11:27:43','2011-11-28 11:27:43'),(260,1,'Inicio de sessión','2011-11-28 11:27:57','2011-11-28 11:27:57'),(261,1,'Inicio de sessión','2011-11-28 11:55:23','2011-11-28 11:55:23'),(262,1,'Cierre de sesión','2011-11-28 11:55:41','2011-11-28 11:55:41'),(263,1,'Inicio de sessión','2011-11-28 11:55:47','2011-11-28 11:55:47'),(264,1,'Cierre de sesión','2011-11-28 12:08:13','2011-11-28 12:08:13'),(265,1,'Inicio de sessión','2011-11-28 12:08:24','2011-11-28 12:08:24'),(266,1,'Cierre de sesión','2011-11-28 12:11:24','2011-11-28 12:11:24'),(267,1,'Inicio de sessión','2011-11-28 12:11:32','2011-11-28 12:11:32'),(268,1,'Cierre de sesión','2011-11-28 12:12:35','2011-11-28 12:12:35'),(269,1,'Inicio de sessión','2011-11-28 12:12:38','2011-11-28 12:12:38'),(270,1,'Cierre de sesión','2011-11-28 12:14:08','2011-11-28 12:14:08'),(271,1,'Inicio de sessión','2011-11-28 12:14:12','2011-11-28 12:14:12'),(272,1,'Cierre de sesión','2011-11-28 12:18:54','2011-11-28 12:18:54'),(273,1,'Inicio de sessión','2011-11-28 12:19:04','2011-11-28 12:19:04'),(274,1,'Inicio de sessión','2011-11-28 13:41:07','2011-11-28 13:41:07'),(275,1,'Inicio de sessión','2011-11-28 13:41:12','2011-11-28 13:41:12'),(276,1,'Cierre de sesión','2011-11-28 13:48:16','2011-11-28 13:48:16'),(277,1,'Inicio de sessión','2011-11-28 13:48:22','2011-11-28 13:48:22'),(278,1,'Cierre de sesión','2011-11-28 13:54:37','2011-11-28 13:54:37'),(279,1,'Inicio de sessión','2011-11-28 13:54:41','2011-11-28 13:54:41'),(280,1,'Cierre de sesión','2011-11-28 13:59:27','2011-11-28 13:59:27'),(281,1,'Inicio de sessión','2011-11-28 14:00:02','2011-11-28 14:00:02'),(282,1,'Cierre de sesión','2011-11-28 14:00:28','2011-11-28 14:00:28'),(283,1,'Inicio de sessión','2011-11-28 14:00:33','2011-11-28 14:00:33'),(284,1,'Cierre de sesión','2011-11-28 14:07:37','2011-11-28 14:07:37'),(285,1,'Inicio de sessión','2011-11-28 14:07:42','2011-11-28 14:07:42'),(286,1,'Cierre de sesión','2011-11-28 14:09:37','2011-11-28 14:09:37'),(287,1,'Inicio de sessión','2011-11-28 14:10:55','2011-11-28 14:10:55'),(288,1,'Inicio de sessión','2011-11-28 14:26:19','2011-11-28 14:26:19'),(289,1,'Cierre de sesión','2011-11-28 14:30:17','2011-11-28 14:30:17'),(290,1,'Inicio de sessión','2011-11-28 14:30:23','2011-11-28 14:30:23'),(291,1,'Cierre de sesión','2011-11-28 14:41:14','2011-11-28 14:41:14'),(292,1,'Inicio de sessión','2011-11-28 14:41:44','2011-11-28 14:41:44'),(293,1,'Cierre de sesión','2011-11-28 14:47:11','2011-11-28 14:47:11'),(294,1,'Cierre de sesión','2011-11-28 14:47:11','2011-11-28 14:47:11'),(295,1,'Inicio de sessión','2011-11-28 14:47:19','2011-11-28 14:47:19'),(296,1,'Inicio de sessión','2011-11-28 14:47:40','2011-11-28 14:47:40'),(297,1,'Cierre de sesión','2011-11-28 14:50:06','2011-11-28 14:50:06'),(298,1,'Inicio de sessión','2011-11-28 14:50:12','2011-11-28 14:50:12'),(299,1,'Cierre de sesión','2011-11-28 14:51:06','2011-11-28 14:51:06'),(300,1,'Cierre de sesión','2011-11-28 14:51:14','2011-11-28 14:51:14'),(301,1,'Inicio de sessión','2011-11-28 14:51:18','2011-11-28 14:51:18'),(302,1,'Inicio de sessión','2011-11-28 14:51:21','2011-11-28 14:51:21'),(303,1,'Cierre de sesión','2011-11-28 14:57:42','2011-11-28 14:57:42'),(304,1,'Inicio de sessión','2011-11-28 14:57:54','2011-11-28 14:57:54'),(305,1,'Cierre de sesión','2011-11-28 15:09:03','2011-11-28 15:09:03'),(306,1,'Inicio de sessión','2011-11-28 15:09:44','2011-11-28 15:09:44'),(307,1,'Inicio de sessión','2011-11-28 15:33:14','2011-11-28 15:33:14'),(308,1,'Inicio de sessión','2011-11-28 15:55:40','2011-11-28 15:55:40'),(309,1,'Inicio de sessión','2011-11-28 16:04:38','2011-11-28 16:04:38'),(310,1,'Cierre de sesión','2011-11-28 16:14:54','2011-11-28 16:14:54'),(311,1,'Inicio de sessión','2011-11-28 16:15:23','2011-11-28 16:15:23'),(312,1,'Cierre de sesión','2011-11-28 16:16:22','2011-11-28 16:16:22'),(313,1,'Inicio de sessión','2011-11-28 16:16:26','2011-11-28 16:16:26'),(314,1,'Cierre de sesión','2011-11-28 16:32:40','2011-11-28 16:32:40'),(315,1,'Inicio de sessión','2011-11-28 16:32:48','2011-11-28 16:32:48'),(316,1,'Inicio de sessión','2011-11-28 16:32:49','2011-11-28 16:32:49'),(317,1,'Inicio de sessión','2011-11-28 20:26:39','2011-11-28 20:26:39'),(318,1,'Inicio de sessión','2011-11-29 13:57:15','2011-11-29 13:57:15'),(319,1,'Inicio de sessión','2011-11-29 14:28:35','2011-11-29 14:28:35'),(320,1,'Cierre de sesión','2011-11-29 14:28:45','2011-11-29 14:28:45'),(321,1,'Inicio de sessión','2011-11-29 14:28:48','2011-11-29 14:28:48'),(322,1,'Cierre de sesión','2011-11-29 14:31:03','2011-11-29 14:31:03'),(323,1,'Inicio de sessión','2011-11-29 14:33:33','2011-11-29 14:33:33'),(324,1,'Inicio de sessión','2011-11-29 15:19:10','2011-11-29 15:19:10'),(325,1,'Cierre de sesión','2011-11-29 15:43:14','2011-11-29 15:43:14'),(326,1,'Inicio de sessión','2011-11-29 15:43:18','2011-11-29 15:43:18'),(327,1,'Cierre de sesión','2011-11-29 15:45:38','2011-11-29 15:45:38'),(328,1,'Inicio de sessión','2011-11-29 15:47:06','2011-11-29 15:47:06'),(329,1,'Cierre de sesión','2011-11-29 15:48:30','2011-11-29 15:48:30'),(330,1,'Inicio de sessión','2011-11-29 15:48:44','2011-11-29 15:48:44'),(331,1,'Cierre de sesión','2011-11-29 15:54:44','2011-11-29 15:54:44'),(332,1,'Inicio de sessión','2011-11-29 15:55:03','2011-11-29 15:55:03'),(333,1,'Cierre de sesión','2011-11-29 16:10:58','2011-11-29 16:10:58'),(334,1,'Inicio de sessión','2011-11-29 16:13:05','2011-11-29 16:13:05'),(335,1,'Cierre de sesión','2011-11-29 16:17:10','2011-11-29 16:17:10'),(336,1,'Inicio de sessión','2011-11-29 16:17:13','2011-11-29 16:17:13'),(337,1,'Cierre de sesión','2011-11-29 16:18:58','2011-11-29 16:18:58'),(338,1,'Inicio de sessión','2011-11-29 16:19:10','2011-11-29 16:19:10'),(339,1,'Inicio de sessión','2011-11-29 20:55:11','2011-11-29 20:55:11'),(340,1,'Cierre de sesión','2011-11-29 22:21:26','2011-11-29 22:21:26'),(341,1,'Inicio de sessión','2011-11-29 22:21:39','2011-11-29 22:21:39'),(342,1,'Inicio de sessión','2011-11-30 10:42:02','2011-11-30 10:42:02'),(343,1,'Inicio de sessión','2011-11-30 12:43:33','2011-11-30 12:43:33'),(344,1,'Cierre de sesión','2011-11-30 12:45:27','2011-11-30 12:45:27'),(345,1,'Inicio de sessión','2011-11-30 12:46:00','2011-11-30 12:46:00'),(346,1,'Inicio de sessión','2011-11-30 12:50:48','2011-11-30 12:50:48'),(347,1,'Cierre de sesión','2011-11-30 12:52:55','2011-11-30 12:52:55'),(348,1,'Inicio de sessión','2011-11-30 12:53:06','2011-11-30 12:53:06'),(349,1,'Cierre de sesión','2011-11-30 12:55:16','2011-11-30 12:55:16'),(350,1,'Inicio de sessión','2011-11-30 12:55:24','2011-11-30 12:55:24'),(351,1,'Cierre de sesión','2011-11-30 12:58:19','2011-11-30 12:58:19'),(352,1,'Inicio de sessión','2011-11-30 12:58:22','2011-11-30 12:58:22'),(353,1,'Cierre de sesión','2011-11-30 13:05:39','2011-11-30 13:05:39'),(354,1,'Inicio de sessión','2011-11-30 13:05:44','2011-11-30 13:05:44'),(355,1,'Inicio de sessión','2011-11-30 14:28:50','2011-11-30 14:28:50'),(356,1,'Cierre de sesión','2011-11-30 14:36:05','2011-11-30 14:36:05'),(357,1,'Inicio de sessión','2011-11-30 14:36:08','2011-11-30 14:36:08'),(358,1,'Cierre de sesión','2011-11-30 14:42:48','2011-11-30 14:42:48'),(359,1,'Inicio de sessión','2011-11-30 14:42:53','2011-11-30 14:42:53'),(360,1,'Cierre de sesión','2011-11-30 14:49:07','2011-11-30 14:49:07'),(361,1,'Inicio de sessión','2011-11-30 14:49:19','2011-11-30 14:49:19'),(362,1,'Inicio de sessión','2011-11-30 14:51:37','2011-11-30 14:51:37'),(363,1,'Inicio de sessión','2011-11-30 14:58:11','2011-11-30 14:58:11'),(364,1,'Cierre de sesión','2011-11-30 15:19:58','2011-11-30 15:19:58'),(365,1,'Inicio de sessión','2011-11-30 15:20:01','2011-11-30 15:20:01'),(366,1,'Inicio de sessión','2011-11-30 16:11:39','2011-11-30 16:11:39'),(367,1,'Inicio de sessión','2011-11-30 16:15:58','2011-11-30 16:15:58'),(368,1,'Inicio de sessión','2011-11-30 16:24:17','2011-11-30 16:24:17'),(369,1,'Inicio de sessión','2011-11-30 16:31:59','2011-11-30 16:31:59'),(370,1,'Cierre de sesión','2011-11-30 16:43:18','2011-11-30 16:43:18'),(371,1,'Inicio de sessión','2011-11-30 16:43:21','2011-11-30 16:43:21'),(372,1,'Cierre de sesión','2011-11-30 16:44:26','2011-11-30 16:44:26'),(373,1,'Inicio de sessión','2011-11-30 16:44:26','2011-11-30 16:44:26'),(374,1,'Inicio de sessión','2011-11-30 16:44:31','2011-11-30 16:44:31'),(375,1,'Inicio de sessión','2011-11-30 16:54:15','2011-11-30 16:54:15'),(376,1,'Inicio de sessión','2011-11-30 17:01:51','2011-11-30 17:01:51'),(377,1,'Inicio de sessión','2011-11-30 17:02:18','2011-11-30 17:02:18'),(378,1,'Cierre de sesión','2011-11-30 17:08:33','2011-11-30 17:08:33'),(379,1,'Inicio de sessión','2011-11-30 17:08:36','2011-11-30 17:08:36'),(380,1,'Cierre de sesión','2011-11-30 17:16:55','2011-11-30 17:16:55'),(381,1,'Inicio de sessión','2011-11-30 17:17:05','2011-11-30 17:17:05'),(382,1,'Cierre de sesión','2011-11-30 17:24:12','2011-11-30 17:24:12'),(383,1,'Inicio de sessión','2011-11-30 17:25:13','2011-11-30 17:25:13'),(384,1,'Inicio de sessión','2011-11-30 17:35:55','2011-11-30 17:35:55'),(385,1,'Inicio de sessión','2011-11-30 18:44:34','2011-11-30 18:44:34'),(386,1,'Cierre de sesión','2011-11-30 19:29:38','2011-11-30 19:29:38'),(387,1,'Inicio de sessión','2011-11-30 19:29:42','2011-11-30 19:29:42'),(388,1,'Cierre de sesión','2011-11-30 19:49:43','2011-11-30 19:49:43'),(389,1,'Inicio de sessión','2011-11-30 19:49:48','2011-11-30 19:49:48'),(390,1,'Cierre de sesión','2011-11-30 20:07:38','2011-11-30 20:07:38'),(391,1,'Inicio de sessión','2011-11-30 20:07:42','2011-11-30 20:07:42'),(392,1,'Cierre de sesión','2011-11-30 20:18:03','2011-11-30 20:18:03'),(393,1,'Inicio de sessión','2011-11-30 20:18:18','2011-11-30 20:18:18'),(394,1,'Cierre de sesión','2011-11-30 20:30:48','2011-11-30 20:30:48'),(395,1,'Inicio de sessión','2011-11-30 20:31:03','2011-11-30 20:31:03'),(396,1,'Cierre de sesión','2011-11-30 20:33:07','2011-11-30 20:33:07'),(397,1,'Inicio de sessión','2011-11-30 20:33:17','2011-11-30 20:33:17'),(398,1,'Inicio de sessión','2011-11-30 21:48:07','2011-11-30 21:48:07'),(399,1,'Cierre de sesión','2011-11-30 21:57:36','2011-11-30 21:57:36'),(400,1,'Inicio de sessión','2011-11-30 21:58:16','2011-11-30 21:58:16'),(401,1,'Cierre de sesión','2011-11-30 22:00:25','2011-11-30 22:00:25'),(402,1,'Inicio de sessión','2011-11-30 22:00:29','2011-11-30 22:00:29'),(403,1,'Cierre de sesión','2011-11-30 22:03:38','2011-11-30 22:03:38'),(404,1,'Inicio de sessión','2011-11-30 22:03:49','2011-11-30 22:03:49'),(405,1,'Cierre de sesión','2011-11-30 22:08:10','2011-11-30 22:08:10'),(406,1,'Inicio de sessión','2011-11-30 22:08:20','2011-11-30 22:08:20'),(407,1,'Cierre de sesión','2011-11-30 22:15:05','2011-11-30 22:15:05'),(408,1,'Inicio de sessión','2011-11-30 22:15:15','2011-11-30 22:15:15'),(409,1,'Cierre de sesión','2011-11-30 22:16:57','2011-11-30 22:16:57'),(410,1,'Inicio de sessión','2011-11-30 22:17:21','2011-11-30 22:17:21'),(411,1,'Cierre de sesión','2011-11-30 22:19:24','2011-11-30 22:19:24'),(412,1,'Inicio de sessión','2011-11-30 22:19:37','2011-11-30 22:19:37'),(413,1,'Cierre de sesión','2011-11-30 22:22:31','2011-11-30 22:22:31'),(414,1,'Inicio de sessión','2011-11-30 22:22:35','2011-11-30 22:22:35'),(415,1,'Cierre de sesión','2011-11-30 22:26:30','2011-11-30 22:26:30'),(416,1,'Inicio de sessión','2011-11-30 22:26:42','2011-11-30 22:26:42'),(417,1,'Cierre de sesión','2011-11-30 22:28:22','2011-11-30 22:28:22'),(418,1,'Inicio de sessión','2011-11-30 22:28:48','2011-11-30 22:28:48'),(419,1,'Cierre de sesión','2011-11-30 22:31:17','2011-11-30 22:31:17'),(420,1,'Inicio de sessión','2011-11-30 22:31:27','2011-11-30 22:31:27'),(421,1,'Cierre de sesión','2011-11-30 22:33:43','2011-11-30 22:33:43'),(422,1,'Inicio de sessión','2011-11-30 22:33:54','2011-11-30 22:33:54'),(423,1,'Cierre de sesión','2011-11-30 22:36:49','2011-11-30 22:36:49'),(424,1,'Inicio de sessión','2011-11-30 22:36:58','2011-11-30 22:36:58'),(425,1,'Cierre de sesión','2011-11-30 22:40:18','2011-11-30 22:40:18'),(426,1,'Inicio de sessión','2011-11-30 22:40:45','2011-11-30 22:40:45'),(427,1,'Inicio de sessión','2011-11-30 22:46:19','2011-11-30 22:46:19'),(428,1,'Cierre de sesión','2011-11-30 22:52:55','2011-11-30 22:52:55'),(429,1,'Inicio de sessión','2011-11-30 22:53:06','2011-11-30 22:53:06'),(430,1,'Cierre de sesión','2011-11-30 23:03:43','2011-11-30 23:03:43'),(431,1,'Inicio de sessión','2011-11-30 23:03:54','2011-11-30 23:03:54'),(432,1,'Inicio de sessión','2011-12-01 09:49:45','2011-12-01 09:49:45'),(433,1,'Cierre de sesión','2011-12-01 09:58:07','2011-12-01 09:58:07'),(434,1,'Inicio de sessión','2011-12-01 09:59:32','2011-12-01 09:59:32'),(435,1,'Cierre de sesión','2011-12-01 10:02:20','2011-12-01 10:02:20'),(436,1,'Inicio de sessión','2011-12-01 10:02:31','2011-12-01 10:02:31'),(437,1,'Cierre de sesión','2011-12-01 10:11:22','2011-12-01 10:11:22'),(438,1,'Inicio de sessión','2011-12-01 10:11:42','2011-12-01 10:11:42'),(439,1,'Cierre de sesión','2011-12-01 10:12:53','2011-12-01 10:12:53'),(440,1,'Inicio de sessión','2011-12-01 10:13:04','2011-12-01 10:13:04'),(441,1,'Cierre de sesión','2011-12-01 10:14:32','2011-12-01 10:14:32'),(442,1,'Inicio de sessión','2011-12-01 10:15:07','2011-12-01 10:15:07'),(443,1,'Cierre de sesión','2011-12-01 10:16:56','2011-12-01 10:16:56'),(444,1,'Inicio de sessión','2011-12-01 10:17:13','2011-12-01 10:17:13'),(445,1,'Cierre de sesión','2011-12-01 11:41:03','2011-12-01 11:41:03'),(446,1,'Inicio de sessión','2011-12-01 11:41:09','2011-12-01 11:41:09'),(447,1,'Inicio de sessión','2011-12-01 11:45:00','2011-12-01 11:45:00'),(448,1,'Cierre de sesión','2011-12-01 11:56:23','2011-12-01 11:56:23'),(449,1,'Inicio de sessión','2011-12-01 11:56:27','2011-12-01 11:56:27'),(450,1,'Inicio de sessión','2011-12-01 12:40:41','2011-12-01 12:40:41'),(451,1,'Inicio de sessión','2011-12-01 12:42:02','2011-12-01 12:42:02'),(452,1,'Inicio de sessión','2011-12-01 12:42:07','2011-12-01 12:42:07'),(453,1,'Cierre de sesión','2011-12-01 13:09:22','2011-12-01 13:09:22'),(454,1,'Inicio de sessión','2011-12-01 13:09:26','2011-12-01 13:09:26'),(455,1,'Cierre de sesión','2011-12-01 13:10:50','2011-12-01 13:10:50'),(456,1,'Inicio de sessión','2011-12-01 13:11:23','2011-12-01 13:11:23'),(457,1,'Inicio de sessión','2011-12-01 13:53:39','2011-12-01 13:53:39'),(458,1,'Inicio de sessión','2011-12-01 13:56:09','2011-12-01 13:56:09'),(459,1,'Inicio de sessión','2011-12-01 13:56:20','2011-12-01 13:56:20'),(460,1,'Inicio de sessión','2011-12-01 14:02:22','2011-12-01 14:02:22'),(461,1,'Inicio de sessión','2011-12-01 14:04:15','2011-12-01 14:04:15'),(462,1,'Cierre de sesión','2011-12-01 14:17:28','2011-12-01 14:17:28'),(463,1,'Inicio de sessión','2011-12-01 14:17:34','2011-12-01 14:17:34'),(464,1,'Cierre de sesión','2011-12-01 14:20:00','2011-12-01 14:20:00'),(465,1,'Inicio de sessión','2011-12-01 14:20:05','2011-12-01 14:20:05'),(466,1,'Cierre de sesión','2011-12-01 14:22:09','2011-12-01 14:22:09'),(467,1,'Inicio de sessión','2011-12-01 14:22:13','2011-12-01 14:22:13'),(468,1,'Inicio de sessión','2011-12-01 14:26:17','2011-12-01 14:26:17'),(469,1,'Inicio de sessión','2011-12-01 14:26:54','2011-12-01 14:26:54'),(470,1,'Inicio de sessión','2011-12-01 16:15:19','2011-12-01 16:15:19'),(471,1,'Cierre de sesión','2011-12-01 16:16:29','2011-12-01 16:16:29'),(472,1,'Inicio de sessión','2011-12-01 16:16:34','2011-12-01 16:16:34'),(473,1,'Cierre de sesión','2011-12-01 16:17:26','2011-12-01 16:17:26'),(474,1,'Inicio de sessión','2011-12-01 16:17:30','2011-12-01 16:17:30'),(475,1,'Inicio de sessión','2011-12-01 20:21:53','2011-12-01 20:21:53'),(476,1,'Cierre de sesión','2011-12-01 20:31:33','2011-12-01 20:31:33'),(477,1,'Inicio de sessión','2011-12-01 20:31:44','2011-12-01 20:31:44'),(478,1,'Cierre de sesión','2011-12-01 20:38:40','2011-12-01 20:38:40'),(479,1,'Inicio de sessión','2011-12-01 20:38:51','2011-12-01 20:38:51'),(480,1,'Cierre de sesión','2011-12-01 20:41:55','2011-12-01 20:41:55'),(481,1,'Inicio de sessión','2011-12-01 20:42:59','2011-12-01 20:42:59'),(482,1,'Inicio de sessión','2011-12-02 09:17:00','2011-12-02 09:17:00'),(483,1,'Cierre de sesión','2011-12-02 09:37:03','2011-12-02 09:37:03'),(484,1,'Inicio de sessión','2011-12-02 09:37:17','2011-12-02 09:37:17'),(485,1,'Cierre de sesión','2011-12-02 09:40:58','2011-12-02 09:40:58'),(486,1,'Inicio de sessión','2011-12-02 09:41:30','2011-12-02 09:41:30'),(487,1,'Cierre de sesión','2011-12-02 09:45:58','2011-12-02 09:45:58'),(488,1,'Inicio de sessión','2011-12-02 09:46:11','2011-12-02 09:46:11'),(489,1,'Cierre de sesión','2011-12-02 09:50:12','2011-12-02 09:50:12'),(490,1,'Inicio de sessión','2011-12-02 09:50:21','2011-12-02 09:50:21'),(491,1,'Cierre de sesión','2011-12-02 09:54:35','2011-12-02 09:54:35'),(492,1,'Inicio de sessión','2011-12-02 09:56:12','2011-12-02 09:56:12'),(493,1,'Cierre de sesión','2011-12-02 10:01:48','2011-12-02 10:01:48'),(494,1,'Inicio de sessión','2011-12-02 10:01:57','2011-12-02 10:01:57'),(495,1,'Cierre de sesión','2011-12-02 10:04:46','2011-12-02 10:04:46'),(496,1,'Inicio de sessión','2011-12-02 10:05:12','2011-12-02 10:05:12'),(497,1,'Inicio de sessión','2011-12-02 10:06:16','2011-12-02 10:06:16'),(498,1,'Inicio de sessión','2011-12-02 10:07:37','2011-12-02 10:07:37'),(499,1,'Cierre de sesión','2011-12-02 10:14:49','2011-12-02 10:14:49'),(500,1,'Inicio de sessión','2011-12-02 10:14:53','2011-12-02 10:14:53'),(501,1,'Inicio de sessión','2011-12-02 10:23:47','2011-12-02 10:23:47'),(502,1,'Cierre de sesión','2011-12-02 10:30:00','2011-12-02 10:30:00'),(503,1,'Inicio de sessión','2011-12-02 10:32:34','2011-12-02 10:32:34'),(504,1,'Cierre de sesión','2011-12-02 10:35:38','2011-12-02 10:35:38'),(505,1,'Inicio de sessión','2011-12-02 10:35:52','2011-12-02 10:35:52'),(506,1,'Cierre de sesión','2011-12-02 10:37:35','2011-12-02 10:37:35'),(507,1,'Inicio de sessión','2011-12-02 10:37:47','2011-12-02 10:37:47'),(508,1,'Cierre de sesión','2011-12-02 10:40:34','2011-12-02 10:40:34'),(509,1,'Inicio de sessión','2011-12-02 10:41:25','2011-12-02 10:41:25'),(510,1,'Inicio de sessión','2011-12-02 11:03:10','2011-12-02 11:03:10'),(511,1,'Cierre de sesión','2011-12-02 11:05:30','2011-12-02 11:05:30'),(512,1,'Inicio de sessión','2011-12-02 11:05:45','2011-12-02 11:05:45'),(513,1,'Cierre de sesión','2011-12-02 11:13:43','2011-12-02 11:13:43'),(514,1,'Inicio de sessión','2011-12-02 11:13:52','2011-12-02 11:13:52'),(515,1,'Inicio de sessión','2011-12-02 11:25:15','2011-12-02 11:25:15'),(516,1,'Cierre de sesión','2011-12-02 11:29:44','2011-12-02 11:29:44'),(517,1,'Inicio de sessión','2011-12-02 11:29:49','2011-12-02 11:29:49'),(518,1,'Cierre de sesión','2011-12-02 11:30:02','2011-12-02 11:30:02'),(519,1,'Inicio de sessión','2011-12-02 11:30:06','2011-12-02 11:30:06'),(520,1,'Cierre de sesión','2011-12-02 11:38:52','2011-12-02 11:38:52'),(521,1,'Inicio de sessión','2011-12-02 11:38:57','2011-12-02 11:38:57'),(522,1,'Cierre de sesión','2011-12-02 11:53:48','2011-12-02 11:53:48'),(523,1,'Inicio de sessión','2011-12-02 11:53:58','2011-12-02 11:53:58'),(524,1,'Cierre de sesión','2011-12-02 12:04:40','2011-12-02 12:04:40'),(525,1,'Inicio de sessión','2011-12-02 12:04:50','2011-12-02 12:04:50'),(526,1,'Inicio de sessión','2011-12-02 12:43:10','2011-12-02 12:43:10'),(527,1,'Inicio de sessión','2011-12-02 13:11:05','2011-12-02 13:11:05'),(528,1,'Inicio de sessión','2011-12-02 13:14:10','2011-12-02 13:14:10'),(529,1,'Cierre de sesión','2011-12-02 13:14:18','2011-12-02 13:14:18'),(530,1,'Inicio de sessión','2011-12-02 13:14:23','2011-12-02 13:14:23'),(531,1,'Cierre de sesión','2011-12-02 13:19:10','2011-12-02 13:19:10'),(532,1,'Inicio de sessión','2011-12-02 13:19:19','2011-12-02 13:19:19'),(533,1,'Cierre de sesión','2011-12-02 13:21:41','2011-12-02 13:21:41'),(534,1,'Inicio de sessión','2011-12-02 13:21:49','2011-12-02 13:21:49'),(535,1,'Cierre de sesión','2011-12-02 13:23:33','2011-12-02 13:23:33'),(536,1,'Inicio de sessión','2011-12-02 13:23:44','2011-12-02 13:23:44'),(537,1,'Cierre de sesión','2011-12-02 13:25:25','2011-12-02 13:25:25'),(538,1,'Inicio de sessión','2011-12-02 13:25:56','2011-12-02 13:25:56'),(539,1,'Inicio de sessión','2011-12-02 13:28:38','2011-12-02 13:28:38'),(540,1,'Inicio de sessión','2011-12-02 13:52:54','2011-12-02 13:52:54'),(541,1,'Cierre de sesión','2011-12-02 13:55:24','2011-12-02 13:55:24'),(542,1,'Inicio de sessión','2011-12-02 13:55:37','2011-12-02 13:55:37'),(543,1,'Inicio de sessión','2011-12-02 14:03:37','2011-12-02 14:03:37'),(544,1,'Cierre de sesión','2011-12-02 14:05:21','2011-12-02 14:05:21'),(545,1,'Inicio de sessión','2011-12-02 14:05:28','2011-12-02 14:05:28'),(546,1,'Cierre de sesión','2011-12-02 14:08:52','2011-12-02 14:08:52'),(547,1,'Inicio de sessión','2011-12-02 14:08:59','2011-12-02 14:08:59'),(548,1,'Cierre de sesión','2011-12-02 14:16:23','2011-12-02 14:16:23'),(549,1,'Inicio de sessión','2011-12-02 14:18:01','2011-12-02 14:18:01'),(550,1,'Inicio de sessión','2011-12-02 14:30:36','2011-12-02 14:30:36'),(551,1,'Cierre de sesión','2011-12-02 14:31:52','2011-12-02 14:31:52'),(552,1,'Inicio de sessión','2011-12-02 14:31:58','2011-12-02 14:31:58'),(553,1,'Cierre de sesión','2011-12-02 14:32:44','2011-12-02 14:32:44'),(554,1,'Inicio de sessión','2011-12-02 14:33:50','2011-12-02 14:33:50'),(555,1,'Cierre de sesión','2011-12-02 14:38:16','2011-12-02 14:38:16'),(556,1,'Inicio de sessión','2011-12-02 14:38:21','2011-12-02 14:38:21'),(557,1,'Cierre de sesión','2011-12-02 15:00:21','2011-12-02 15:00:21'),(558,1,'Inicio de sessión','2011-12-02 15:00:32','2011-12-02 15:00:32'),(559,1,'Cierre de sesión','2011-12-02 15:02:20','2011-12-02 15:02:20'),(560,1,'Inicio de sessión','2011-12-02 15:06:02','2011-12-02 15:06:02'),(561,1,'Cierre de sesión','2011-12-02 15:09:56','2011-12-02 15:09:56'),(562,1,'Inicio de sessión','2011-12-02 15:10:05','2011-12-02 15:10:05'),(563,1,'Cierre de sesión','2011-12-02 15:24:44','2011-12-02 15:24:44'),(564,1,'Inicio de sessión','2011-12-02 15:24:53','2011-12-02 15:24:53'),(565,1,'Cierre de sesión','2011-12-02 15:31:56','2011-12-02 15:31:56'),(566,1,'Inicio de sessión','2011-12-02 15:32:23','2011-12-02 15:32:23'),(567,1,'Inicio de sessión','2011-12-02 15:33:49','2011-12-02 15:33:49'),(568,1,'Inicio de sessión','2011-12-02 16:01:12','2011-12-02 16:01:12'),(569,1,'Inicio de sessión','2011-12-02 16:02:40','2011-12-02 16:02:40'),(570,1,'Cierre de sesión','2011-12-02 16:04:49','2011-12-02 16:04:49'),(571,1,'Inicio de sessión','2011-12-02 16:04:59','2011-12-02 16:04:59'),(572,1,'Inicio de sessión','2011-12-02 16:10:47','2011-12-02 16:10:47'),(573,1,'Cierre de sesión','2011-12-02 16:13:06','2011-12-02 16:13:06'),(574,1,'Inicio de sessión','2011-12-02 16:13:09','2011-12-02 16:13:09'),(575,1,'Cierre de sesión','2011-12-02 16:14:06','2011-12-02 16:14:06'),(576,1,'Inicio de sessión','2011-12-02 16:14:58','2011-12-02 16:14:58'),(577,1,'Inicio de sessión','2011-12-02 16:15:11','2011-12-02 16:15:11'),(578,1,'Cierre de sesión','2011-12-02 16:17:40','2011-12-02 16:17:40'),(579,1,'Inicio de sessión','2011-12-02 16:17:50','2011-12-02 16:17:50'),(580,1,'Cierre de sesión','2011-12-02 16:34:28','2011-12-02 16:34:28'),(581,1,'Inicio de sessión','2011-12-02 16:34:36','2011-12-02 16:34:36'),(582,1,'Inicio de sessión','2011-12-02 21:46:06','2011-12-02 21:46:06'),(583,1,'Cierre de sesión','2011-12-02 22:15:16','2011-12-02 22:15:16'),(584,1,'Inicio de sessión','2011-12-02 22:15:27','2011-12-02 22:15:27'),(585,1,'Inicio de sessión','2011-12-02 22:19:02','2011-12-02 22:19:02'),(586,1,'Cierre de sesión','2011-12-02 23:16:56','2011-12-02 23:16:56'),(587,1,'Inicio de sessión','2011-12-02 23:17:22','2011-12-02 23:17:22'),(588,1,'Cierre de sesión','2011-12-03 00:26:35','2011-12-03 00:26:35'),(589,1,'Inicio de sessión','2011-12-05 12:12:45','2011-12-05 12:12:45'),(590,1,'Cierre de sesión','2011-12-05 12:21:28','2011-12-05 12:21:28'),(591,1,'Inicio de sessión','2011-12-05 12:21:32','2011-12-05 12:21:32'),(592,1,'Inicio de sessión','2011-12-05 12:26:59','2011-12-05 12:26:59'),(593,1,'Inicio de sessión','2011-12-06 09:13:51','2011-12-06 09:13:51'),(594,1,'Cierre de sesión','2011-12-06 09:22:21','2011-12-06 09:22:21'),(595,1,'Inicio de sessión','2011-12-06 09:22:28','2011-12-06 09:22:28'),(596,1,'Inicio de sessión','2011-12-06 09:27:42','2011-12-06 09:27:42'),(597,1,'Cierre de sesión','2011-12-06 09:29:18','2011-12-06 09:29:18'),(598,1,'Inicio de sessión','2011-12-06 09:29:26','2011-12-06 09:29:26'),(599,1,'Inicio de sessión','2011-12-06 09:33:32','2011-12-06 09:33:32'),(600,1,'Cierre de sesión','2011-12-06 09:44:53','2011-12-06 09:44:53'),(601,1,'Inicio de sessión','2011-12-06 09:44:59','2011-12-06 09:44:59'),(602,1,'Inicio de sessión','2011-12-06 10:36:23','2011-12-06 10:36:23'),(603,1,'Cierre de sesión','2011-12-06 12:15:08','2011-12-06 12:15:08'),(604,1,'Inicio de sessión','2011-12-06 13:34:23','2011-12-06 13:34:23'),(605,1,'Cierre de sesión','2011-12-06 13:56:22','2011-12-06 13:56:22'),(606,1,'Inicio de sessión','2011-12-06 13:57:16','2011-12-06 13:57:16'),(607,1,'Cierre de sesión','2011-12-06 14:06:01','2011-12-06 14:06:01'),(608,1,'Inicio de sessión','2011-12-06 14:06:06','2011-12-06 14:06:06'),(609,1,'Cierre de sesión','2011-12-06 14:23:22','2011-12-06 14:23:22'),(610,1,'Inicio de sessión','2011-12-06 14:23:36','2011-12-06 14:23:36'),(611,1,'Cierre de sesión','2011-12-06 14:54:19','2011-12-06 14:54:19'),(612,1,'Inicio de sessión','2011-12-06 14:54:23','2011-12-06 14:54:23'),(613,1,'Cierre de sesión','2011-12-06 15:19:30','2011-12-06 15:19:30'),(614,1,'Inicio de sessión','2011-12-06 15:19:41','2011-12-06 15:19:41'),(615,1,'Cierre de sesión','2011-12-06 15:45:37','2011-12-06 15:45:37'),(616,1,'Inicio de sessión','2011-12-06 15:45:40','2011-12-06 15:45:40'),(617,1,'Cierre de sesión','2011-12-06 15:46:40','2011-12-06 15:46:40'),(618,1,'Inicio de sessión','2011-12-06 15:46:43','2011-12-06 15:46:43'),(619,1,'Cierre de sesión','2011-12-06 15:47:36','2011-12-06 15:47:36'),(620,1,'Inicio de sessión','2011-12-06 15:47:42','2011-12-06 15:47:42'),(621,1,'Cierre de sesión','2011-12-06 15:48:09','2011-12-06 15:48:09'),(622,1,'Inicio de sessión','2011-12-06 15:48:18','2011-12-06 15:48:18'),(623,1,'Cierre de sesión','2011-12-06 15:49:35','2011-12-06 15:49:35'),(624,1,'Inicio de sessión','2011-12-06 15:49:39','2011-12-06 15:49:39'),(625,1,'Inicio de sessión','2011-12-06 21:45:56','2011-12-06 21:45:56'),(626,1,'Cierre de sesión','2011-12-06 21:52:50','2011-12-06 21:52:50'),(627,1,'Inicio de sessión','2011-12-06 21:52:57','2011-12-06 21:52:57'),(628,1,'Inicio de sessión','2011-12-07 09:47:42','2011-12-07 09:47:42'),(629,1,'Cierre de sesión','2011-12-07 09:51:58','2011-12-07 09:51:58'),(630,1,'Inicio de sessión','2011-12-07 09:52:10','2011-12-07 09:52:10'),(631,1,'Inicio de sessión','2011-12-07 11:41:08','2011-12-07 11:41:08'),(632,1,'Cierre de sesión','2011-12-07 11:56:25','2011-12-07 11:56:25'),(633,1,'Inicio de sessión','2011-12-07 11:56:29','2011-12-07 11:56:29'),(634,1,'Cierre de sesión','2011-12-07 12:07:20','2011-12-07 12:07:20'),(635,1,'Inicio de sessión','2011-12-07 12:07:38','2011-12-07 12:07:38'),(636,1,'Cierre de sesión','2011-12-07 12:15:58','2011-12-07 12:15:58'),(637,1,'Inicio de sessión','2011-12-07 12:16:02','2011-12-07 12:16:02'),(638,1,'Inicio de sessión','2011-12-07 14:26:44','2011-12-07 14:26:44'),(639,1,'Cierre de sesión','2011-12-07 14:41:19','2011-12-07 14:41:19'),(640,1,'Inicio de sessión','2011-12-07 14:41:29','2011-12-07 14:41:29'),(641,1,'Cierre de sesión','2011-12-07 15:59:04','2011-12-07 15:59:04'),(642,1,'Inicio de sessión','2011-12-07 23:33:37','2011-12-07 23:33:37'),(643,1,'Inicio de sessión','2011-12-07 23:40:32','2011-12-07 23:40:32'),(644,1,'Inicio de sessión','2011-12-07 23:49:14','2011-12-07 23:49:14'),(645,1,'Inicio de sessión','2011-12-08 00:21:06','2011-12-08 00:21:06'),(646,1,'Inicio de sessión','2011-12-08 00:24:35','2011-12-08 00:24:35'),(647,1,'Inicio de sessión','2011-12-08 00:34:16','2011-12-08 00:34:16'),(648,1,'Inicio de sessión','2011-12-08 00:45:30','2011-12-08 00:45:30'),(649,1,'Inicio de sessión','2011-12-08 00:52:07','2011-12-08 00:52:07'),(650,1,'Inicio de sessión','2011-12-08 01:02:41','2011-12-08 01:02:41'),(651,1,'Inicio de sessión','2011-12-08 01:11:45','2011-12-08 01:11:45'),(652,1,'Inicio de sessión','2011-12-08 08:59:38','2011-12-08 08:59:38'),(653,1,'Inicio de sessión','2011-12-08 09:24:18','2011-12-08 09:24:18'),(654,1,'Inicio de sessión','2011-12-08 10:30:24','2011-12-08 10:30:24'),(655,1,'Inicio de sessión','2011-12-08 11:13:37','2011-12-08 11:13:37'),(656,1,'Cierre de sesión','2011-12-08 11:14:20','2011-12-08 11:14:20'),(657,1,'Inicio de sessión','2011-12-08 13:29:29','2011-12-08 13:29:29'),(658,1,'Inicio de sessión','2011-12-08 13:36:12','2011-12-08 13:36:12'),(659,1,'Inicio de sessión','2011-12-08 13:41:53','2011-12-08 13:41:53'),(660,1,'Inicio de sessión','2011-12-08 13:57:47','2011-12-08 13:57:47'),(661,1,'Inicio de sessión','2011-12-08 14:00:09','2011-12-08 14:00:09'),(662,1,'Inicio de sessión','2011-12-08 14:39:50','2011-12-08 14:39:50'),(663,1,'Cierre de sesión','2011-12-08 14:49:13','2011-12-08 14:49:13'),(664,1,'Inicio de sessión','2011-12-08 14:49:17','2011-12-08 14:49:17'),(665,1,'Cierre de sesión','2011-12-08 14:58:51','2011-12-08 14:58:51'),(666,1,'Inicio de sessión','2011-12-08 14:58:57','2011-12-08 14:58:57'),(667,1,'Inicio de sessión','2011-12-08 19:58:52','2011-12-08 19:58:52'),(668,1,'Cierre de sesión','2011-12-08 20:47:20','2011-12-08 20:47:20'),(669,1,'Inicio de sessión','2011-12-08 20:48:10','2011-12-08 20:48:10'),(670,1,'Cierre de sesión','2011-12-08 21:02:44','2011-12-08 21:02:44'),(671,1,'Inicio de sessión','2011-12-08 21:02:48','2011-12-08 21:02:48'),(672,1,'Inicio de sessión','2011-12-08 22:26:14','2011-12-08 22:26:14'),(673,1,'Cierre de sesión','2011-12-08 22:31:30','2011-12-08 22:31:30'),(674,1,'Inicio de sessión','2011-12-08 22:31:37','2011-12-08 22:31:37'),(675,1,'Cierre de sesión','2011-12-08 23:05:12','2011-12-08 23:05:12'),(676,1,'Inicio de sessión','2011-12-08 23:05:22','2011-12-08 23:05:22'),(677,1,'Cierre de sesión','2011-12-08 23:22:07','2011-12-08 23:22:07'),(678,1,'Inicio de sessión','2011-12-08 23:22:20','2011-12-08 23:22:20'),(679,1,'Cierre de sesión','2011-12-08 23:50:35','2011-12-08 23:50:35'),(680,1,'Inicio de sessión','2011-12-08 23:50:39','2011-12-08 23:50:39'),(681,1,'Inicio de sessión','2011-12-09 08:27:24','2011-12-09 08:27:24'),(682,1,'Cierre de sesión','2011-12-09 08:44:15','2011-12-09 08:44:15'),(683,1,'Inicio de sessión','2011-12-09 08:44:21','2011-12-09 08:44:21'),(684,1,'Cierre de sesión','2011-12-09 08:55:39','2011-12-09 08:55:39'),(685,1,'Inicio de sessión','2011-12-09 08:55:50','2011-12-09 08:55:50'),(686,1,'Cierre de sesión','2011-12-09 08:58:26','2011-12-09 08:58:26'),(687,1,'Inicio de sessión','2011-12-09 08:58:33','2011-12-09 08:58:33'),(688,1,'Cierre de sesión','2011-12-09 09:11:04','2011-12-09 09:11:04'),(689,1,'Inicio de sessión','2011-12-09 09:11:09','2011-12-09 09:11:09'),(690,1,'Inicio de sessión','2011-12-09 09:42:56','2011-12-09 09:42:56'),(691,1,'Cierre de sesión','2011-12-09 09:45:07','2011-12-09 09:45:07'),(692,1,'Inicio de sessión','2011-12-09 09:45:13','2011-12-09 09:45:13'),(693,1,'Cierre de sesión','2011-12-09 09:50:32','2011-12-09 09:50:32'),(694,1,'Inicio de sessión','2011-12-09 09:50:36','2011-12-09 09:50:36'),(695,1,'Cierre de sesión','2011-12-09 10:50:14','2011-12-09 10:50:14'),(696,1,'Inicio de sessión','2011-12-09 10:50:19','2011-12-09 10:50:19'),(697,1,'Cierre de sesión','2011-12-09 11:00:27','2011-12-09 11:00:27'),(698,1,'Inicio de sessión','2011-12-09 11:00:32','2011-12-09 11:00:32'),(699,1,'Cierre de sesión','2011-12-09 11:04:37','2011-12-09 11:04:37'),(700,1,'Inicio de sessión','2011-12-09 11:04:43','2011-12-09 11:04:43'),(701,1,'Cierre de sesión','2011-12-09 11:08:34','2011-12-09 11:08:34'),(702,1,'Inicio de sessión','2011-12-09 11:08:42','2011-12-09 11:08:42'),(703,1,'Cierre de sesión','2011-12-09 11:34:52','2011-12-09 11:34:52'),(704,1,'Inicio de sessión','2011-12-09 11:34:55','2011-12-09 11:34:55'),(705,1,'Cierre de sesión','2011-12-09 12:18:10','2011-12-09 12:18:10'),(706,1,'Inicio de sessión','2011-12-09 12:19:37','2011-12-09 12:19:37'),(707,1,'Cierre de sesión','2011-12-09 12:26:23','2011-12-09 12:26:23'),(708,1,'Inicio de sessión','2011-12-09 12:26:29','2011-12-09 12:26:29'),(709,1,'Cierre de sesión','2011-12-09 12:54:59','2011-12-09 12:54:59'),(710,1,'Inicio de sessión','2011-12-09 12:55:04','2011-12-09 12:55:04'),(711,1,'Cierre de sesión','2011-12-09 12:57:06','2011-12-09 12:57:06'),(712,1,'Inicio de sessión','2011-12-09 12:59:15','2011-12-09 12:59:15'),(713,1,'Inicio de sessión','2011-12-09 13:03:31','2011-12-09 13:03:31'),(714,1,'Cierre de sesión','2011-12-09 13:07:32','2011-12-09 13:07:32'),(715,1,'Inicio de sessión','2011-12-09 13:07:38','2011-12-09 13:07:38'),(716,1,'Cierre de sesión','2011-12-09 13:09:55','2011-12-09 13:09:55'),(717,1,'Inicio de sessión','2011-12-09 13:10:00','2011-12-09 13:10:00'),(718,1,'Inicio de sessión','2011-12-09 15:43:39','2011-12-09 15:43:39'),(719,1,'Cierre de sesión','2011-12-09 16:13:44','2011-12-09 16:13:44'),(720,1,'Inicio de sessión','2011-12-09 16:13:50','2011-12-09 16:13:50'),(721,1,'Cierre de sesión','2011-12-09 17:14:17','2011-12-09 17:14:17'),(722,1,'Inicio de sessión','2011-12-09 17:14:20','2011-12-09 17:14:20'),(723,1,'Inicio de sessión','2011-12-09 19:08:58','2011-12-09 19:08:58'),(724,1,'Cierre de sesión','2011-12-09 20:24:46','2011-12-09 20:24:46'),(725,1,'Inicio de sessión','2011-12-09 20:24:51','2011-12-09 20:24:51'),(726,1,'Inicio de sessión','2011-12-09 21:56:40','2011-12-09 21:56:40'),(727,1,'Cierre de sesión','2011-12-09 21:59:20','2011-12-09 21:59:20'),(728,1,'Inicio de sessión','2011-12-09 21:59:25','2011-12-09 21:59:25'),(729,1,'Cierre de sesión','2011-12-09 22:24:09','2011-12-09 22:24:09'),(730,1,'Inicio de sessión','2011-12-09 22:25:20','2011-12-09 22:25:20'),(731,1,'Cierre de sesión','2011-12-09 22:30:52','2011-12-09 22:30:52'),(732,1,'Inicio de sessión','2011-12-09 22:31:03','2011-12-09 22:31:03'),(733,1,'Cierre de sesión','2011-12-09 23:14:25','2011-12-09 23:14:25'),(734,1,'Inicio de sessión','2011-12-09 23:14:35','2011-12-09 23:14:35'),(735,1,'Cierre de sesión','2011-12-09 23:29:45','2011-12-09 23:29:45'),(736,1,'Inicio de sessión','2011-12-09 23:29:49','2011-12-09 23:29:49'),(737,1,'Cierre de sesión','2011-12-09 23:35:33','2011-12-09 23:35:33'),(738,1,'Inicio de sessión','2011-12-09 23:35:44','2011-12-09 23:35:44'),(739,1,'Cierre de sesión','2011-12-09 23:50:03','2011-12-09 23:50:03'),(740,1,'Inicio de sessión','2011-12-09 23:50:17','2011-12-09 23:50:17'),(741,1,'Cierre de sesión','2011-12-09 23:54:34','2011-12-09 23:54:34'),(742,1,'Inicio de sessión','2011-12-09 23:54:59','2011-12-09 23:54:59'),(743,1,'Inicio de sessión','2011-12-10 10:11:32','2011-12-10 10:11:32'),(744,1,'Cierre de sesión','2011-12-10 10:14:46','2011-12-10 10:14:46'),(745,1,'Inicio de sessión','2011-12-10 10:14:58','2011-12-10 10:14:58'),(746,1,'Cierre de sesión','2011-12-10 10:26:27','2011-12-10 10:26:27'),(747,1,'Inicio de sessión','2011-12-10 10:26:44','2011-12-10 10:26:44'),(748,1,'Cierre de sesión','2011-12-10 10:45:39','2011-12-10 10:45:39'),(749,1,'Inicio de sessión','2011-12-10 10:45:50','2011-12-10 10:45:50'),(750,1,'Cierre de sesión','2011-12-10 10:59:38','2011-12-10 10:59:38'),(751,1,'Inicio de sessión','2011-12-10 10:59:47','2011-12-10 10:59:47'),(752,1,'Inicio de sessión','2011-12-10 11:03:30','2011-12-10 11:03:30'),(753,1,'Cierre de sesión','2011-12-10 11:05:20','2011-12-10 11:05:20'),(754,1,'Inicio de sessión','2011-12-10 11:05:34','2011-12-10 11:05:34'),(755,1,'Cierre de sesión','2011-12-10 11:26:40','2011-12-10 11:26:40'),(756,1,'Inicio de sessión','2011-12-10 11:26:54','2011-12-10 11:26:54'),(757,1,'Cierre de sesión','2011-12-10 11:28:28','2011-12-10 11:28:28'),(758,1,'Inicio de sessión','2011-12-10 11:29:11','2011-12-10 11:29:11'),(759,1,'Cierre de sesión','2011-12-10 11:31:07','2011-12-10 11:31:07'),(760,1,'Inicio de sessión','2011-12-10 11:31:17','2011-12-10 11:31:17'),(761,1,'Cierre de sesión','2011-12-10 11:34:59','2011-12-10 11:34:59'),(762,1,'Inicio de sessión','2011-12-10 11:35:12','2011-12-10 11:35:12'),(763,1,'Cierre de sesión','2011-12-10 12:07:55','2011-12-10 12:07:55'),(764,1,'Inicio de sessión','2011-12-10 12:08:00','2011-12-10 12:08:00'),(765,1,'Cierre de sesión','2011-12-10 12:37:27','2011-12-10 12:37:27'),(766,1,'Inicio de sessión','2011-12-10 12:37:36','2011-12-10 12:37:36'),(767,1,'Inicio de sessión','2011-12-10 15:20:48','2011-12-10 15:20:48'),(768,1,'Cierre de sesión','2011-12-10 15:24:35','2011-12-10 15:24:35'),(769,1,'Inicio de sessión','2011-12-10 15:24:46','2011-12-10 15:24:46'),(770,1,'Cierre de sesión','2011-12-10 15:27:45','2011-12-10 15:27:45'),(771,1,'Inicio de sessión','2011-12-10 15:28:11','2011-12-10 15:28:11'),(772,1,'Cierre de sesión','2011-12-10 15:47:00','2011-12-10 15:47:00'),(773,1,'Inicio de sessión','2011-12-10 15:47:11','2011-12-10 15:47:11'),(774,1,'Cierre de sesión','2011-12-10 15:47:58','2011-12-10 15:47:58'),(775,1,'Inicio de sessión','2011-12-10 15:48:03','2011-12-10 15:48:03'),(776,1,'Cierre de sesión','2011-12-10 16:20:41','2011-12-10 16:20:41'),(777,1,'Inicio de sessión','2011-12-10 16:20:57','2011-12-10 16:20:57'),(778,1,'Cierre de sesión','2011-12-10 16:22:46','2011-12-10 16:22:46'),(779,1,'Inicio de sessión','2011-12-10 16:23:08','2011-12-10 16:23:08'),(780,1,'Inicio de sessión','2011-12-10 16:31:53','2011-12-10 16:31:53'),(781,1,'Cierre de sesión','2011-12-10 16:53:37','2011-12-10 16:53:37'),(782,1,'Inicio de sessión','2011-12-10 16:53:47','2011-12-10 16:53:47'),(783,1,'Cierre de sesión','2011-12-10 16:56:24','2011-12-10 16:56:24'),(784,1,'Inicio de sessión','2011-12-10 16:56:37','2011-12-10 16:56:37'),(785,1,'Cierre de sesión','2011-12-10 18:12:34','2011-12-10 18:12:34'),(786,1,'Inicio de sessión','2011-12-10 18:13:03','2011-12-10 18:13:03'),(787,1,'Cierre de sesión','2011-12-10 18:13:19','2011-12-10 18:13:19'),(788,1,'Inicio de sessión','2011-12-10 18:13:22','2011-12-10 18:13:22'),(789,1,'Cierre de sesión','2011-12-10 19:09:44','2011-12-10 19:09:44'),(790,1,'Inicio de sessión','2011-12-10 19:10:04','2011-12-10 19:10:04'),(791,1,'Cierre de sesión','2011-12-10 19:11:09','2011-12-10 19:11:09'),(792,1,'Inicio de sessión','2011-12-10 19:11:15','2011-12-10 19:11:15'),(793,1,'Cierre de sesión','2011-12-10 19:17:33','2011-12-10 19:17:33'),(794,1,'Inicio de sessión','2011-12-10 19:20:16','2011-12-10 19:20:16'),(795,1,'Cierre de sesión','2011-12-10 19:35:38','2011-12-10 19:35:38'),(796,1,'Inicio de sessión','2011-12-10 19:35:47','2011-12-10 19:35:47'),(797,1,'Cierre de sesión','2011-12-10 19:41:57','2011-12-10 19:41:57'),(798,1,'Inicio de sessión','2011-12-10 19:42:02','2011-12-10 19:42:02'),(799,1,'Inicio de sessión','2011-12-11 09:25:19','2011-12-11 09:25:19'),(800,1,'Cierre de sesión','2011-12-11 09:28:01','2011-12-11 09:28:01'),(801,1,'Inicio de sessión','2011-12-11 09:28:14','2011-12-11 09:28:14'),(802,1,'Cierre de sesión','2011-12-11 09:30:05','2011-12-11 09:30:05'),(803,1,'Inicio de sessión','2011-12-11 09:30:16','2011-12-11 09:30:16'),(804,1,'Cierre de sesión','2011-12-11 10:31:41','2011-12-11 10:31:41'),(805,1,'Inicio de sessión','2011-12-11 11:08:20','2011-12-11 11:08:20'),(806,1,'Cierre de sesión','2011-12-11 11:23:11','2011-12-11 11:23:11'),(807,1,'Inicio de sessión','2011-12-11 11:23:21','2011-12-11 11:23:21'),(808,1,'Cierre de sesión','2011-12-11 11:31:58','2011-12-11 11:31:58'),(809,1,'Inicio de sessión','2011-12-11 11:32:01','2011-12-11 11:32:01'),(810,1,'Cierre de sesión','2011-12-11 11:32:31','2011-12-11 11:32:31'),(811,1,'Inicio de sessión','2011-12-11 11:32:34','2011-12-11 11:32:34'),(812,1,'Inicio de sessión','2011-12-11 14:38:12','2011-12-11 14:38:12'),(813,1,'Cierre de sesión','2011-12-11 14:49:18','2011-12-11 14:49:18'),(814,1,'Inicio de sessión','2011-12-11 14:49:30','2011-12-11 14:49:30'),(815,1,'Cierre de sesión','2011-12-11 14:51:10','2011-12-11 14:51:10'),(816,1,'Inicio de sessión','2011-12-11 14:51:21','2011-12-11 14:51:21'),(817,1,'Cierre de sesión','2011-12-11 14:54:03','2011-12-11 14:54:03'),(818,1,'Inicio de sessión','2011-12-11 14:54:06','2011-12-11 14:54:06'),(819,1,'Cierre de sesión','2011-12-11 14:57:06','2011-12-11 14:57:06'),(820,1,'Inicio de sessión','2011-12-11 14:57:21','2011-12-11 14:57:21'),(821,1,'Cierre de sesión','2011-12-11 15:00:40','2011-12-11 15:00:40'),(822,1,'Inicio de sessión','2011-12-11 15:00:48','2011-12-11 15:00:48'),(823,1,'Cierre de sesión','2011-12-11 15:17:00','2011-12-11 15:17:00'),(824,1,'Inicio de sessión','2011-12-11 15:17:12','2011-12-11 15:17:12'),(825,1,'Cierre de sesión','2011-12-11 15:53:49','2011-12-11 15:53:49'),(826,1,'Inicio de sessión','2011-12-11 15:53:52','2011-12-11 15:53:52'),(827,1,'Cierre de sesión','2011-12-11 17:23:37','2011-12-11 17:23:37'),(828,1,'Inicio de sessión','2011-12-11 17:23:40','2011-12-11 17:23:40'),(829,1,'Inicio de sessión','2011-12-11 21:05:50','2011-12-11 21:05:50'),(830,1,'Inicio de sessión','2011-12-11 22:35:42','2011-12-11 22:35:42'),(831,1,'Inicio de sessión','2011-12-12 10:21:29','2011-12-12 10:21:29'),(832,1,'Inicio de sessión','2011-12-12 12:05:46','2011-12-12 12:05:46'),(833,1,'Cierre de sesión','2011-12-12 12:24:15','2011-12-12 12:24:15'),(834,1,'Inicio de sessión','2011-12-12 12:24:18','2011-12-12 12:24:18'),(835,1,'Cierre de sesión','2011-12-12 12:34:28','2011-12-12 12:34:28'),(836,1,'Inicio de sessión','2011-12-12 12:34:33','2011-12-12 12:34:33'),(837,1,'Cierre de sesión','2011-12-12 12:36:17','2011-12-12 12:36:17'),(838,1,'Inicio de sessión','2011-12-12 12:36:21','2011-12-12 12:36:21'),(839,1,'Cierre de sesión','2011-12-12 12:39:49','2011-12-12 12:39:49'),(840,1,'Inicio de sessión','2011-12-12 12:40:26','2011-12-12 12:40:26'),(841,1,'Cierre de sesión','2011-12-12 12:56:33','2011-12-12 12:56:33'),(842,1,'Inicio de sessión','2011-12-12 12:56:40','2011-12-12 12:56:40'),(843,1,'Cierre de sesión','2011-12-12 15:14:31','2011-12-12 15:14:31'),(844,1,'Inicio de sessión','2011-12-12 15:14:35','2011-12-12 15:14:35'),(845,1,'Inicio de sessión','2011-12-12 20:13:17','2011-12-12 20:13:17'),(846,1,'Cierre de sesión','2011-12-12 20:38:40','2011-12-12 20:38:40'),(847,1,'Inicio de sessión','2011-12-12 20:38:44','2011-12-12 20:38:44'),(848,1,'Cierre de sesión','2011-12-12 20:58:00','2011-12-12 20:58:00'),(849,1,'Inicio de sessión','2011-12-12 20:58:07','2011-12-12 20:58:07'),(850,1,'Inicio de sessión','2011-12-12 21:14:29','2011-12-12 21:14:29'),(851,1,'Cierre de sesión','2011-12-12 22:20:41','2011-12-12 22:20:41'),(852,1,'Inicio de sessión','2011-12-12 22:20:47','2011-12-12 22:20:47'),(853,1,'Cierre de sesión','2011-12-12 22:21:08','2011-12-12 22:21:08'),(854,1,'Inicio de sessión','2011-12-12 22:21:14','2011-12-12 22:21:14'),(855,1,'Cierre de sesión','2011-12-12 22:22:30','2011-12-12 22:22:30'),(856,1,'Inicio de sessión','2011-12-12 22:22:35','2011-12-12 22:22:35'),(857,1,'Cierre de sesión','2011-12-12 22:29:21','2011-12-12 22:29:21'),(858,1,'Inicio de sessión','2011-12-12 22:29:33','2011-12-12 22:29:33'),(859,1,'Cierre de sesión','2011-12-12 22:34:47','2011-12-12 22:34:47'),(860,1,'Inicio de sessión','2011-12-12 22:34:57','2011-12-12 22:34:57'),(861,1,'Inicio de sessión','2011-12-12 22:36:05','2011-12-12 22:36:05'),(862,1,'Cierre de sesión','2011-12-12 22:40:35','2011-12-12 22:40:35'),(863,1,'Inicio de sessión','2011-12-12 22:40:45','2011-12-12 22:40:45'),(864,1,'Cierre de sesión','2011-12-12 22:43:31','2011-12-12 22:43:31'),(865,1,'Inicio de sessión','2011-12-12 22:43:42','2011-12-12 22:43:42'),(866,1,'Cierre de sesión','2011-12-12 22:52:01','2011-12-12 22:52:01'),(867,1,'Inicio de sessión','2011-12-12 22:52:11','2011-12-12 22:52:11'),(868,1,'Inicio de sessión','2011-12-06 11:00:50','2011-12-06 11:00:50'),(869,1,'Inicio de sessión','2011-12-06 12:19:39','2011-12-06 12:19:39'),(870,1,'Inicio de sessión','2011-12-06 12:23:52','2011-12-06 12:23:52'),(871,1,'Inicio de sessión','2011-12-13 14:05:53','2011-12-13 14:05:53'),(872,1,'Inicio de sessión','2011-12-13 14:08:56','2011-12-13 14:08:56'),(873,1,'Inicio de sessión','2011-12-13 14:13:47','2011-12-13 14:13:47'),(874,1,'Cierre de sesión','2011-12-13 14:22:05','2011-12-13 14:22:05'),(875,1,'Inicio de sessión','2011-12-13 14:23:04','2011-12-13 14:23:04'),(876,1,'Cierre de sesión','2011-12-13 14:28:16','2011-12-13 14:28:16'),(877,1,'Inicio de sessión','2011-12-13 14:28:27','2011-12-13 14:28:27'),(878,1,'Cierre de sesión','2011-12-13 14:31:49','2011-12-13 14:31:49'),(879,1,'Inicio de sessión','2011-12-13 14:32:00','2011-12-13 14:32:00'),(880,1,'Cierre de sesión','2011-12-13 14:34:17','2011-12-13 14:34:17'),(881,1,'Inicio de sessión','2011-12-13 14:34:22','2011-12-13 14:34:22'),(882,1,'Inicio de sessión','2011-12-13 14:35:26','2011-12-13 14:35:26'),(883,1,'Cierre de sesión','2011-12-13 14:40:13','2011-12-13 14:40:13'),(884,1,'Inicio de sessión','2011-12-13 14:40:16','2011-12-13 14:40:16'),(885,1,'Inicio de sessión','2011-12-13 14:40:35','2011-12-13 14:40:35'),(886,1,'Cierre de sesión','2011-12-13 14:45:28','2011-12-13 14:45:28'),(887,1,'Inicio de sessión','2011-12-13 14:45:38','2011-12-13 14:45:38'),(888,1,'Cierre de sesión','2011-12-13 14:55:57','2011-12-13 14:55:57'),(889,1,'Inicio de sessión','2011-12-13 14:56:07','2011-12-13 14:56:07'),(890,1,'Cierre de sesión','2011-12-13 15:03:03','2011-12-13 15:03:03'),(891,1,'Inicio de sessión','2011-12-13 15:03:12','2011-12-13 15:03:12'),(892,1,'Cierre de sesión','2011-12-13 15:04:42','2011-12-13 15:04:42'),(893,1,'Inicio de sessión','2011-12-13 15:04:54','2011-12-13 15:04:54'),(894,1,'Cierre de sesión','2011-12-13 15:08:17','2011-12-13 15:08:17'),(895,1,'Inicio de sessión','2011-12-13 15:08:31','2011-12-13 15:08:31'),(896,1,'Cierre de sesión','2011-12-13 15:20:52','2011-12-13 15:20:52'),(897,1,'Inicio de sessión','2011-12-13 15:21:03','2011-12-13 15:21:03'),(898,1,'Cierre de sesión','2011-12-13 15:26:43','2011-12-13 15:26:43'),(899,1,'Inicio de sessión','2011-12-13 15:26:47','2011-12-13 15:26:47'),(900,1,'Inicio de sessión','2011-12-13 15:28:23','2011-12-13 15:28:23'),(901,1,'Inicio de sessión','2011-12-14 11:32:37','2011-12-14 11:32:37'),(902,1,'Cierre de sesión','2011-12-14 11:34:58','2011-12-14 11:34:58'),(903,1,'Inicio de sessión','2011-12-14 11:35:02','2011-12-14 11:35:02'),(904,1,'Cierre de sesión','2011-12-14 11:52:41','2011-12-14 11:52:41'),(905,1,'Inicio de sessión','2011-12-14 11:52:44','2011-12-14 11:52:44'),(906,1,'Cierre de sesión','2011-12-14 12:06:27','2011-12-14 12:06:27'),(907,1,'Inicio de sessión','2011-12-14 12:07:01','2011-12-14 12:07:01'),(908,1,'Cierre de sesión','2011-12-14 12:11:46','2011-12-14 12:11:46'),(909,1,'Inicio de sessión','2011-12-14 12:11:49','2011-12-14 12:11:49'),(910,1,'Cierre de sesión','2011-12-14 12:32:47','2011-12-14 12:32:47'),(911,1,'Inicio de sessión','2011-12-14 12:33:04','2011-12-14 12:33:04'),(912,1,'Cierre de sesión','2011-12-14 12:39:20','2011-12-14 12:39:20'),(913,1,'Inicio de sessión','2011-12-14 12:39:32','2011-12-14 12:39:32'),(914,1,'Cierre de sesión','2011-12-14 12:54:45','2011-12-14 12:54:45'),(915,1,'Inicio de sessión','2011-12-14 12:55:00','2011-12-14 12:55:00'),(916,1,'Cierre de sesión','2011-12-14 13:19:43','2011-12-14 13:19:43'),(917,1,'Inicio de sessión','2011-12-14 13:19:52','2011-12-14 13:19:52'),(918,1,'Cierre de sesión','2011-12-14 13:38:34','2011-12-14 13:38:34'),(919,1,'Inicio de sessión','2011-12-14 13:39:02','2011-12-14 13:39:02'),(920,1,'Inicio de sessión','2011-12-14 14:43:05','2011-12-14 14:43:05'),(921,1,'Cierre de sesión','2011-12-14 14:47:56','2011-12-14 14:47:56'),(922,1,'Inicio de sessión','2011-12-14 14:48:01','2011-12-14 14:48:01'),(923,1,'Cierre de sesión','2011-12-14 14:58:02','2011-12-14 14:58:02'),(924,1,'Inicio de sessión','2011-12-14 14:58:21','2011-12-14 14:58:21'),(925,1,'Cierre de sesión','2011-12-14 15:02:54','2011-12-14 15:02:54'),(926,1,'Inicio de sessión','2011-12-14 15:04:18','2011-12-14 15:04:18'),(927,1,'Cierre de sesión','2011-12-14 15:10:02','2011-12-14 15:10:02'),(928,1,'Inicio de sessión','2011-12-14 15:10:14','2011-12-14 15:10:14'),(929,1,'Cierre de sesión','2011-12-14 15:16:19','2011-12-14 15:16:19'),(930,1,'Inicio de sessión','2011-12-14 15:16:32','2011-12-14 15:16:32'),(931,1,'Cierre de sesión','2011-12-14 15:54:03','2011-12-14 15:54:03'),(932,1,'Inicio de sessión','2011-12-14 15:54:06','2011-12-14 15:54:06'),(933,1,'Cierre de sesión','2011-12-14 16:46:33','2011-12-14 16:46:33'),(934,1,'Inicio de sessión','2011-12-14 16:46:37','2011-12-14 16:46:37'),(935,1,'Cierre de sesión','2011-12-14 16:48:40','2011-12-14 16:48:40'),(936,1,'Inicio de sessión','2011-12-14 16:48:53','2011-12-14 16:48:53'),(937,1,'Inicio de sessión','2011-12-14 16:56:32','2011-12-14 16:56:32'),(938,1,'Cierre de sesión','2011-12-14 17:00:01','2011-12-14 17:00:01'),(939,1,'Inicio de sessión','2011-12-14 17:00:06','2011-12-14 17:00:06'),(940,1,'Cierre de sesión','2011-12-14 17:13:02','2011-12-14 17:13:02'),(941,1,'Inicio de sessión','2011-12-14 17:13:13','2011-12-14 17:13:13'),(942,1,'Cierre de sesión','2011-12-14 17:18:21','2011-12-14 17:18:21'),(943,1,'Inicio de sessión','2011-12-14 17:18:24','2011-12-14 17:18:24'),(944,1,'Cierre de sesión','2011-12-14 18:03:13','2011-12-14 18:03:13'),(945,1,'Inicio de sessión','2011-12-14 18:06:15','2011-12-14 18:06:15'),(946,1,'Cierre de sesión','2011-12-14 18:19:53','2011-12-14 18:19:53'),(947,1,'Inicio de sessión','2011-12-14 18:19:58','2011-12-14 18:19:58'),(948,1,'Cierre de sesión','2011-12-14 18:23:56','2011-12-14 18:23:56'),(949,1,'Inicio de sessión','2011-12-14 18:24:07','2011-12-14 18:24:07'),(950,1,'Cierre de sesión','2011-12-14 18:27:56','2011-12-14 18:27:56'),(951,1,'Inicio de sessión','2011-12-14 18:28:45','2011-12-14 18:28:45'),(952,1,'Cierre de sesión','2011-12-14 18:31:36','2011-12-14 18:31:36'),(953,1,'Inicio de sessión','2011-12-14 18:31:47','2011-12-14 18:31:47'),(954,1,'Cierre de sesión','2011-12-14 18:37:51','2011-12-14 18:37:51'),(955,1,'Inicio de sessión','2011-12-14 18:38:00','2011-12-14 18:38:00'),(956,1,'Cierre de sesión','2011-12-14 18:39:15','2011-12-14 18:39:15'),(957,1,'Inicio de sessión','2011-12-14 18:39:26','2011-12-14 18:39:26'),(958,1,'Cierre de sesión','2011-12-14 18:41:40','2011-12-14 18:41:40'),(959,1,'Inicio de sessión','2011-12-14 18:41:51','2011-12-14 18:41:51'),(960,1,'Cierre de sesión','2011-12-14 18:46:28','2011-12-14 18:46:28'),(961,1,'Inicio de sessión','2011-12-14 18:46:38','2011-12-14 18:46:38'),(962,1,'Cierre de sesión','2011-12-14 19:31:22','2011-12-14 19:31:22'),(963,1,'Inicio de sessión','2011-12-14 19:57:31','2011-12-14 19:57:31'),(964,1,'Cierre de sesión','2011-12-14 20:12:10','2011-12-14 20:12:10'),(965,1,'Inicio de sessión','2011-12-14 20:12:19','2011-12-14 20:12:19'),(966,1,'Cierre de sesión','2011-12-14 20:13:50','2011-12-14 20:13:50'),(967,1,'Inicio de sessión','2011-12-14 20:13:53','2011-12-14 20:13:53'),(968,1,'Cierre de sesión','2011-12-14 20:15:15','2011-12-14 20:15:15'),(969,1,'Inicio de sessión','2011-12-14 20:15:18','2011-12-14 20:15:18'),(970,1,'Inicio de sessión','2011-12-14 20:23:20','2011-12-14 20:23:20'),(971,1,'Inicio de sessión','2011-12-14 20:27:28','2011-12-14 20:27:28'),(972,1,'Cierre de sesión','2011-12-14 20:32:03','2011-12-14 20:32:03'),(973,1,'Inicio de sessión','2011-12-14 20:32:14','2011-12-14 20:32:14'),(974,1,'Cierre de sesión','2011-12-14 22:14:19','2011-12-14 22:14:19'),(975,1,'Inicio de sessión','2011-12-14 22:14:25','2011-12-14 22:14:25'),(976,1,'Inicio de sessión','2011-12-15 20:46:09','2011-12-15 20:46:09'),(977,1,'Cierre de sesión','2011-12-15 21:07:37','2011-12-15 21:07:37'),(978,1,'Inicio de sessión','2011-12-15 21:07:41','2011-12-15 21:07:41'),(979,1,'Cierre de sesión','2011-12-15 22:25:42','2011-12-15 22:25:42'),(980,1,'Inicio de sessión','2011-12-15 22:25:52','2011-12-15 22:25:52'),(981,1,'Cierre de sesión','2011-12-15 22:27:20','2011-12-15 22:27:20'),(982,1,'Inicio de sessión','2011-12-15 22:27:30','2011-12-15 22:27:30'),(983,1,'Cierre de sesión','2011-12-15 23:06:46','2011-12-15 23:06:46'),(984,1,'Inicio de sessión','2011-12-15 23:06:50','2011-12-15 23:06:50'),(985,1,'Cierre de sesión','2011-12-15 23:15:51','2011-12-15 23:15:51'),(986,1,'Inicio de sessión','2011-12-15 23:16:01','2011-12-15 23:16:01'),(987,1,'Cierre de sesión','2011-12-15 23:18:02','2011-12-15 23:18:02'),(988,1,'Inicio de sessión','2011-12-15 23:18:13','2011-12-15 23:18:13'),(989,1,'Cierre de sesión','2011-12-15 23:25:42','2011-12-15 23:25:42'),(990,1,'Inicio de sessión','2011-12-15 23:25:53','2011-12-15 23:25:53'),(991,1,'Inicio de sessión','2011-12-15 23:28:29','2011-12-15 23:28:29'),(992,1,'Cierre de sesión','2011-12-15 23:33:17','2011-12-15 23:33:17'),(993,1,'Inicio de sessión','2011-12-15 23:33:27','2011-12-15 23:33:27'),(994,1,'Cierre de sesión','2011-12-15 23:36:33','2011-12-15 23:36:33'),(995,1,'Inicio de sessión','2011-12-15 23:36:54','2011-12-15 23:36:54'),(996,1,'Inicio de sessión','2011-12-18 15:19:56','2011-12-18 15:19:56'),(997,1,'Cierre de sesión','2011-12-18 15:30:42','2011-12-18 15:30:42'),(998,1,'Inicio de sessión','2011-12-18 15:31:08','2011-12-18 15:31:08'),(999,1,'Cierre de sesión','2011-12-18 15:39:21','2011-12-18 15:39:21'),(1000,1,'Inicio de sessión','2011-12-18 15:39:33','2011-12-18 15:39:33'),(1001,1,'Cierre de sesión','2011-12-18 15:46:43','2011-12-18 15:46:43'),(1002,1,'Inicio de sessión','2011-12-18 15:46:58','2011-12-18 15:46:58'),(1003,1,'Cierre de sesión','2011-12-18 15:51:12','2011-12-18 15:51:12'),(1004,1,'Inicio de sessión','2011-12-18 15:51:25','2011-12-18 15:51:25'),(1005,1,'Cierre de sesión','2011-12-18 15:56:55','2011-12-18 15:56:55'),(1006,1,'Inicio de sessión','2011-12-18 15:57:06','2011-12-18 15:57:06'),(1007,1,'Cierre de sesión','2011-12-18 16:02:26','2011-12-18 16:02:26'),(1008,1,'Inicio de sessión','2011-12-18 16:02:36','2011-12-18 16:02:36'),(1009,1,'Cierre de sesión','2011-12-18 16:07:27','2011-12-18 16:07:27'),(1010,1,'Inicio de sessión','2011-12-18 16:07:38','2011-12-18 16:07:38'),(1011,1,'Cierre de sesión','2011-12-18 16:21:26','2011-12-18 16:21:26'),(1012,1,'Inicio de sessión','2011-12-18 16:21:36','2011-12-18 16:21:36'),(1013,1,'Cierre de sesión','2011-12-18 16:31:48','2011-12-18 16:31:48'),(1014,1,'Inicio de sessión','2011-12-18 16:32:00','2011-12-18 16:32:00'),(1015,1,'Cierre de sesión','2011-12-18 16:34:59','2011-12-18 16:34:59'),(1016,1,'Inicio de sessión','2011-12-18 16:35:13','2011-12-18 16:35:13'),(1017,1,'Cierre de sesión','2011-12-18 16:46:50','2011-12-18 16:46:50'),(1018,1,'Inicio de sessión','2011-12-18 16:46:54','2011-12-18 16:46:54'),(1019,1,'Cierre de sesión','2011-12-18 16:55:11','2011-12-18 16:55:11'),(1020,1,'Inicio de sessión','2011-12-18 16:55:14','2011-12-18 16:55:14'),(1021,1,'Inicio de sessión','2011-12-18 20:37:16','2011-12-18 20:37:16'),(1022,1,'Inicio de sessión','2011-12-18 21:29:44','2011-12-18 21:29:44'),(1023,1,'Cierre de sesión','2011-12-18 21:30:59','2011-12-18 21:30:59'),(1024,1,'Inicio de sessión','2011-12-18 21:33:20','2011-12-18 21:33:20'),(1025,1,'Cierre de sesión','2011-12-18 21:42:11','2011-12-18 21:42:11'),(1026,1,'Inicio de sessión','2011-12-18 21:42:21','2011-12-18 21:42:21'),(1027,1,'Inicio de sessión','2011-12-18 22:13:27','2011-12-18 22:13:27'),(1028,1,'Cierre de sesión','2011-12-18 22:25:12','2011-12-18 22:25:12'),(1029,1,'Inicio de sessión','2011-12-18 22:25:23','2011-12-18 22:25:23'),(1030,1,'Cierre de sesión','2011-12-18 22:41:30','2011-12-18 22:41:30'),(1031,1,'Inicio de sessión','2011-12-18 22:41:34','2011-12-18 22:41:34'),(1032,1,'Inicio de sessión','2011-12-19 06:24:01','2011-12-19 06:24:01'),(1033,1,'Cierre de sesión','2011-12-19 06:27:14','2011-12-19 06:27:14'),(1034,1,'Inicio de sessión','2011-12-19 06:28:28','2011-12-19 06:28:28'),(1035,1,'Cierre de sesión','2011-12-19 06:40:43','2011-12-19 06:40:43'),(1036,1,'Inicio de sessión','2011-12-19 06:41:47','2011-12-19 06:41:47'),(1037,1,'Cierre de sesión','2011-12-19 06:45:00','2011-12-19 06:45:00'),(1038,1,'Inicio de sessión','2011-12-19 06:45:12','2011-12-19 06:45:12'),(1039,1,'Cierre de sesión','2011-12-19 06:57:27','2011-12-19 06:57:27'),(1040,1,'Inicio de sessión','2011-12-19 07:48:37','2011-12-19 07:48:37'),(1041,1,'Cierre de sesión','2011-12-19 07:51:16','2011-12-19 07:51:16'),(1042,1,'Inicio de sessión','2011-12-19 07:51:29','2011-12-19 07:51:29'),(1043,1,'Inicio de sessión','2011-12-19 15:07:20','2011-12-19 15:07:20'),(1044,1,'Cierre de sesión','2011-12-19 15:12:42','2011-12-19 15:12:42'),(1045,1,'Inicio de sessión','2011-12-19 15:13:28','2011-12-19 15:13:28'),(1046,1,'Cierre de sesión','2011-12-19 15:36:35','2011-12-19 15:36:35'),(1047,1,'Inicio de sessión','2011-12-19 15:36:39','2011-12-19 15:36:39'),(1048,1,'Cierre de sesión','2011-12-19 15:38:02','2011-12-19 15:38:02'),(1049,1,'Inicio de sessión','2011-12-19 15:38:15','2011-12-19 15:38:15'),(1050,1,'Cierre de sesión','2011-12-19 16:15:05','2011-12-19 16:15:05'),(1051,1,'Inicio de sessión','2011-12-19 16:15:32','2011-12-19 16:15:32'),(1052,1,'Cierre de sesión','2011-12-19 16:33:27','2011-12-19 16:33:27'),(1053,1,'Inicio de sessión','2011-12-19 16:33:37','2011-12-19 16:33:37'),(1054,1,'Cierre de sesión','2011-12-19 16:55:25','2011-12-19 16:55:25'),(1055,1,'Inicio de sessión','2011-12-19 16:55:36','2011-12-19 16:55:36'),(1056,1,'Cierre de sesión','2011-12-19 17:32:26','2011-12-19 17:32:26'),(1057,1,'Inicio de sessión','2011-12-19 17:32:43','2011-12-19 17:32:43'),(1058,1,'Cierre de sesión','2011-12-19 17:47:35','2011-12-19 17:47:35'),(1059,1,'Inicio de sessión','2011-12-19 17:47:54','2011-12-19 17:47:54'),(1060,1,'Cierre de sesión','2011-12-19 18:08:34','2011-12-19 18:08:34'),(1061,1,'Inicio de sessión','2011-12-19 18:08:45','2011-12-19 18:08:45'),(1062,1,'Cierre de sesión','2011-12-19 18:26:19','2011-12-19 18:26:19'),(1063,1,'Inicio de sessión','2011-12-19 18:26:24','2011-12-19 18:26:24'),(1064,1,'Cierre de sesión','2011-12-19 18:37:32','2011-12-19 18:37:32'),(1065,1,'Inicio de sessión','2011-12-19 18:37:45','2011-12-19 18:37:45'),(1066,1,'Cierre de sesión','2011-12-19 18:40:09','2011-12-19 18:40:09'),(1067,1,'Inicio de sessión','2011-12-19 18:40:28','2011-12-19 18:40:28'),(1068,1,'Cierre de sesión','2011-12-19 18:43:13','2011-12-19 18:43:13'),(1069,1,'Inicio de sessión','2011-12-19 18:43:31','2011-12-19 18:43:31'),(1070,1,'Cierre de sesión','2011-12-19 18:48:30','2011-12-19 18:48:30'),(1071,1,'Inicio de sessión','2011-12-19 18:49:56','2011-12-19 18:49:56'),(1072,1,'Inicio de sessión','2011-12-19 18:52:17','2011-12-19 18:52:17'),(1073,1,'Cierre de sesión','2011-12-19 19:00:25','2011-12-19 19:00:25'),(1074,1,'Inicio de sessión','2011-12-19 19:00:35','2011-12-19 19:00:35'),(1075,1,'Cierre de sesión','2011-12-19 19:02:41','2011-12-19 19:02:41'),(1076,1,'Inicio de sessión','2011-12-19 19:03:02','2011-12-19 19:03:02'),(1077,1,'Cierre de sesión','2011-12-19 19:04:39','2011-12-19 19:04:39'),(1078,1,'Inicio de sessión','2011-12-19 19:04:52','2011-12-19 19:04:52'),(1079,1,'Cierre de sesión','2011-12-19 19:09:26','2011-12-19 19:09:26'),(1080,1,'Inicio de sessión','2011-12-19 19:09:38','2011-12-19 19:09:38'),(1081,1,'Inicio de sessión','2011-12-19 19:35:21','2011-12-19 19:35:21'),(1082,1,'Cierre de sesión','2011-12-19 19:36:58','2011-12-19 19:36:58'),(1083,1,'Inicio de sessión','2011-12-19 19:37:02','2011-12-19 19:37:02'),(1084,1,'Cierre de sesión','2011-12-19 19:39:00','2011-12-19 19:39:00'),(1085,1,'Inicio de sessión','2011-12-19 19:39:10','2011-12-19 19:39:10'),(1086,1,'Cierre de sesión','2011-12-19 19:45:05','2011-12-19 19:45:05'),(1087,1,'Inicio de sessión','2011-12-19 19:45:15','2011-12-19 19:45:15'),(1088,1,'Cierre de sesión','2011-12-19 19:49:24','2011-12-19 19:49:24'),(1089,1,'Inicio de sessión','2011-12-19 19:49:37','2011-12-19 19:49:37'),(1090,1,'Cierre de sesión','2011-12-19 19:51:08','2011-12-19 19:51:08'),(1091,1,'Inicio de sessión','2011-12-19 19:51:19','2011-12-19 19:51:19'),(1092,1,'Inicio de sessión','2011-12-19 19:53:42','2011-12-19 19:53:42'),(1093,1,'Cierre de sesión','2011-12-19 19:58:26','2011-12-19 19:58:26'),(1094,1,'Inicio de sessión','2011-12-19 19:58:38','2011-12-19 19:58:38'),(1095,1,'Inicio de sessión','2011-12-19 20:08:48','2011-12-19 20:08:48'),(1096,1,'Cierre de sesión','2011-12-19 20:16:59','2011-12-19 20:16:59'),(1097,1,'Inicio de sessión','2011-12-19 20:18:12','2011-12-19 20:18:12'),(1098,1,'Cierre de sesión','2011-12-19 20:19:38','2011-12-19 20:19:38'),(1099,1,'Inicio de sessión','2011-12-19 20:19:49','2011-12-19 20:19:49'),(1100,1,'Cierre de sesión','2011-12-19 20:23:04','2011-12-19 20:23:04'),(1101,1,'Inicio de sessión','2011-12-19 20:23:19','2011-12-19 20:23:19'),(1102,1,'Cierre de sesión','2011-12-19 20:35:56','2011-12-19 20:35:56'),(1103,1,'Inicio de sessión','2011-12-19 20:35:57','2011-12-19 20:35:57'),(1104,1,'Cierre de sesión','2011-12-19 20:39:39','2011-12-19 20:39:39'),(1105,1,'Inicio de sessión','2011-12-19 20:39:50','2011-12-19 20:39:50'),(1106,1,'Cierre de sesión','2011-12-19 20:43:58','2011-12-19 20:43:58'),(1107,1,'Inicio de sessión','2011-12-19 20:44:58','2011-12-19 20:44:58'),(1108,1,'Cierre de sesión','2011-12-19 21:06:24','2011-12-19 21:06:24'),(1109,1,'Inicio de sessión','2011-12-19 21:06:40','2011-12-19 21:06:40'),(1110,1,'Cierre de sesión','2011-12-19 21:10:08','2011-12-19 21:10:08'),(1111,1,'Inicio de sessión','2011-12-19 21:11:09','2011-12-19 21:11:09'),(1112,1,'Cierre de sesión','2011-12-19 21:32:57','2011-12-19 21:32:57'),(1113,1,'Inicio de sessión','2011-12-19 21:33:45','2011-12-19 21:33:45'),(1114,1,'Cierre de sesión','2011-12-19 21:56:17','2011-12-19 21:56:17'),(1115,1,'Inicio de sessión','2011-12-19 21:56:41','2011-12-19 21:56:41'),(1116,1,'Cierre de sesión','2011-12-19 22:00:38','2011-12-19 22:00:38'),(1117,1,'Inicio de sessión','2011-12-19 22:00:48','2011-12-19 22:00:48'),(1118,1,'Cierre de sesión','2011-12-19 22:10:31','2011-12-19 22:10:31'),(1119,1,'Inicio de sessión','2011-12-19 22:10:42','2011-12-19 22:10:42'),(1120,1,'Inicio de sessión','2011-12-20 09:39:12','2011-12-20 09:39:12'),(1121,1,'Cierre de sesión','2011-12-20 09:46:25','2011-12-20 09:46:25'),(1122,1,'Inicio de sessión','2011-12-20 09:46:30','2011-12-20 09:46:30'),(1123,1,'Cierre de sesión','2011-12-20 09:49:16','2011-12-20 09:49:16'),(1124,1,'Inicio de sessión','2011-12-20 09:49:26','2011-12-20 09:49:26'),(1125,1,'Cierre de sesión','2011-12-20 10:00:31','2011-12-20 10:00:31'),(1126,1,'Inicio de sessión','2011-12-20 10:00:34','2011-12-20 10:00:34'),(1127,1,'Cierre de sesión','2011-12-20 10:04:44','2011-12-20 10:04:44'),(1128,1,'Inicio de sessión','2011-12-20 10:06:52','2011-12-20 10:06:52'),(1129,1,'Cierre de sesión','2011-12-20 10:10:09','2011-12-20 10:10:09'),(1130,1,'Inicio de sessión','2011-12-20 10:10:29','2011-12-20 10:10:29'),(1131,1,'Cierre de sesión','2011-12-20 10:25:52','2011-12-20 10:25:52'),(1132,1,'Inicio de sessión','2011-12-20 10:25:59','2011-12-20 10:25:59'),(1133,1,'Cierre de sesión','2011-12-20 10:32:40','2011-12-20 10:32:40'),(1134,1,'Inicio de sessión','2011-12-20 10:32:53','2011-12-20 10:32:53'),(1135,1,'Cierre de sesión','2011-12-20 10:48:38','2011-12-20 10:48:38'),(1136,1,'Inicio de sessión','2011-12-20 10:48:51','2011-12-20 10:48:51'),(1137,1,'Cierre de sesión','2011-12-20 12:12:16','2011-12-20 12:12:16'),(1138,1,'Inicio de sessión','2011-12-20 12:12:27','2011-12-20 12:12:27'),(1139,1,'Cierre de sesión','2011-12-20 12:39:06','2011-12-20 12:39:06'),(1140,1,'Inicio de sessión','2011-12-20 12:39:10','2011-12-20 12:39:10'),(1141,1,'Inicio de sessión','2011-12-20 13:13:59','2011-12-20 13:13:59'),(1142,1,'Cierre de sesión','2011-12-20 14:39:34','2011-12-20 14:39:34'),(1143,1,'Inicio de sessión','2011-12-20 14:40:38','2011-12-20 14:40:38'),(1144,1,'Inicio de sessión','2011-12-20 16:50:19','2011-12-20 16:50:19'),(1145,1,'Cierre de sesión','2011-12-20 16:59:10','2011-12-20 16:59:10'),(1146,1,'Inicio de sessión','2011-12-20 17:01:28','2011-12-20 17:01:28'),(1147,1,'Inicio de sessión','2011-12-20 20:28:55','2011-12-20 20:28:55'),(1148,1,'Cierre de sesión','2011-12-20 20:45:11','2011-12-20 20:45:11'),(1149,1,'Inicio de sessión','2011-12-20 20:45:15','2011-12-20 20:45:15'),(1150,1,'Inicio de sessión','2011-12-20 21:24:37','2011-12-20 21:24:37'),(1151,1,'Inicio de sessión','2011-12-21 10:55:52','2011-12-21 10:55:52'),(1152,1,'Cierre de sesión','2011-12-21 12:18:03','2011-12-21 12:18:03'),(1153,1,'Inicio de sessión','2011-12-21 12:18:07','2011-12-21 12:18:07'),(1154,1,'Cierre de sesión','2011-12-21 12:22:25','2011-12-21 12:22:25'),(1155,1,'Inicio de sessión','2011-12-21 12:22:37','2011-12-21 12:22:37'),(1156,1,'Cierre de sesión','2011-12-21 12:44:02','2011-12-21 12:44:02'),(1157,1,'Inicio de sessión','2011-12-21 12:44:13','2011-12-21 12:44:13'),(1158,1,'Cierre de sesión','2011-12-21 13:27:42','2011-12-21 13:27:42'),(1159,1,'Inicio de sessión','2011-12-21 13:27:53','2011-12-21 13:27:53'),(1160,1,'Inicio de sessión','2011-12-21 14:15:39','2011-12-21 14:15:39'),(1161,1,'Cierre de sesión','2011-12-21 14:51:10','2011-12-21 14:51:10'),(1162,1,'Inicio de sessión','2011-12-21 14:51:22','2011-12-21 14:51:22'),(1163,1,'Cierre de sesión','2011-12-21 14:54:18','2011-12-21 14:54:18'),(1164,1,'Inicio de sessión','2011-12-21 14:54:29','2011-12-21 14:54:29'),(1165,1,'Cierre de sesión','2011-12-21 15:35:08','2011-12-21 15:35:08'),(1166,1,'Inicio de sessión','2011-12-21 15:35:11','2011-12-21 15:35:11'),(1167,1,'Cierre de sesión','2011-12-21 15:55:11','2011-12-21 15:55:11'),(1168,1,'Inicio de sessión','2011-12-21 15:55:49','2011-12-21 15:55:49'),(1169,1,'Cierre de sesión','2011-12-21 16:08:55','2011-12-21 16:08:55'),(1170,1,'Inicio de sessión','2011-12-21 16:08:58','2011-12-21 16:08:58'),(1171,1,'Inicio de sessión','2011-12-21 16:13:17','2011-12-21 16:13:17'),(1172,1,'Cierre de sesión','2011-12-21 16:16:37','2011-12-21 16:16:37'),(1173,1,'Inicio de sessión','2011-12-21 16:16:54','2011-12-21 16:16:54'),(1174,1,'Cierre de sesión','2011-12-21 16:34:26','2011-12-21 16:34:26'),(1175,1,'Inicio de sessión','2011-12-21 16:38:23','2011-12-21 16:38:23'),(1176,1,'Cierre de sesión','2011-12-21 16:51:51','2011-12-21 16:51:51'),(1177,1,'Inicio de sessión','2011-12-21 16:52:16','2011-12-21 16:52:16'),(1178,1,'Cierre de sesión','2011-12-21 17:04:48','2011-12-21 17:04:48'),(1179,1,'Inicio de sessión','2011-12-21 17:11:45','2011-12-21 17:11:45'),(1180,1,'Inicio de sessión','2011-12-22 14:28:11','2011-12-22 14:28:11'),(1181,1,'Cierre de sesión','2011-12-22 14:59:22','2011-12-22 14:59:22'),(1182,1,'Inicio de sessión','2011-12-22 14:59:26','2011-12-22 14:59:26'),(1183,1,'Cierre de sesión','2011-12-22 15:26:55','2011-12-22 15:26:55'),(1184,1,'Inicio de sessión','2011-12-22 15:31:51','2011-12-22 15:31:51'),(1185,1,'Cierre de sesión','2011-12-22 15:47:37','2011-12-22 15:47:37'),(1186,1,'Inicio de sessión','2011-12-22 15:47:48','2011-12-22 15:47:48'),(1187,1,'Cierre de sesión','2011-12-22 16:31:49','2011-12-22 16:31:49'),(1188,1,'Inicio de sessión','2011-12-22 16:32:05','2011-12-22 16:32:05'),(1189,1,'Cierre de sesión','2011-12-22 16:34:20','2011-12-22 16:34:20'),(1190,1,'Inicio de sessión','2011-12-22 16:34:23','2011-12-22 16:34:23'),(1191,1,'Cierre de sesión','2011-12-22 16:36:11','2011-12-22 16:36:11'),(1192,1,'Inicio de sessión','2011-12-22 16:36:24','2011-12-22 16:36:24'),(1193,1,'Cierre de sesión','2011-12-22 16:42:58','2011-12-22 16:42:58'),(1194,1,'Inicio de sessión','2011-12-22 16:43:13','2011-12-22 16:43:13'),(1195,1,'Cierre de sesión','2011-12-22 16:46:13','2011-12-22 16:46:13'),(1196,1,'Inicio de sessión','2011-12-22 16:46:25','2011-12-22 16:46:25'),(1197,1,'Inicio de sessión','2011-12-22 17:34:28','2011-12-22 17:34:28'),(1198,1,'Cierre de sesión','2011-12-22 17:38:40','2011-12-22 17:38:40'),(1199,1,'Inicio de sessión','2011-12-22 17:38:55','2011-12-22 17:38:55'),(1200,1,'Inicio de sessión','2011-12-22 17:45:37','2011-12-22 17:45:37'),(1201,1,'Cierre de sesión','2011-12-22 17:56:13','2011-12-22 17:56:13'),(1202,1,'Inicio de sessión','2011-12-22 17:56:23','2011-12-22 17:56:23'),(1203,1,'Cierre de sesión','2011-12-22 18:01:18','2011-12-22 18:01:18'),(1204,1,'Inicio de sessión','2011-12-22 18:01:41','2011-12-22 18:01:41'),(1205,1,'Cierre de sesión','2011-12-22 18:13:16','2011-12-22 18:13:16'),(1206,1,'Inicio de sessión','2011-12-22 18:13:25','2011-12-22 18:13:25'),(1207,1,'Cierre de sesión','2011-12-22 18:23:10','2011-12-22 18:23:10'),(1208,1,'Inicio de sessión','2011-12-22 18:23:23','2011-12-22 18:23:23'),(1209,1,'Cierre de sesión','2011-12-22 18:31:55','2011-12-22 18:31:55'),(1210,1,'Inicio de sessión','2011-12-22 18:32:10','2011-12-22 18:32:10'),(1211,1,'Cierre de sesión','2011-12-22 18:33:42','2011-12-22 18:33:42'),(1212,1,'Inicio de sessión','2011-12-22 18:33:46','2011-12-22 18:33:46'),(1213,1,'Cierre de sesión','2011-12-22 18:37:20','2011-12-22 18:37:20'),(1214,1,'Inicio de sessión','2011-12-22 18:37:27','2011-12-22 18:37:27'),(1215,1,'Cierre de sesión','2011-12-22 18:44:56','2011-12-22 18:44:56'),(1216,1,'Inicio de sessión','2011-12-22 18:44:59','2011-12-22 18:44:59'),(1217,1,'Inicio de sessión','2011-12-22 21:07:03','2011-12-22 21:07:03'),(1218,1,'Cierre de sesión','2011-12-22 22:48:02','2011-12-22 22:48:02'),(1219,1,'Inicio de sessión','2011-12-22 22:48:07','2011-12-22 22:48:07'),(1220,1,'Cierre de sesión','2011-12-22 22:59:25','2011-12-22 22:59:25'),(1221,1,'Inicio de sessión','2011-12-22 22:59:50','2011-12-22 22:59:50'),(1222,1,'Cierre de sesión','2011-12-22 23:01:54','2011-12-22 23:01:54'),(1223,1,'Inicio de sessión','2011-12-22 23:01:59','2011-12-22 23:01:59'),(1224,1,'Cierre de sesión','2011-12-22 23:05:31','2011-12-22 23:05:31'),(1225,1,'Inicio de sessión','2011-12-22 23:06:31','2011-12-22 23:06:31'),(1226,1,'Inicio de sessión','2011-12-23 11:44:48','2011-12-23 11:44:48'),(1227,1,'Cierre de sesión','2011-12-23 12:26:45','2011-12-23 12:26:45'),(1228,1,'Inicio de sessión','2011-12-23 12:26:58','2011-12-23 12:26:58'),(1229,1,'Cierre de sesión','2011-12-23 13:20:12','2011-12-23 13:20:12'),(1230,1,'Inicio de sessión','2011-12-23 13:20:18','2011-12-23 13:20:18'),(1231,1,'Cierre de sesión','2011-12-23 13:34:23','2011-12-23 13:34:23'),(1232,1,'Inicio de sessión','2011-12-23 13:34:35','2011-12-23 13:34:35'),(1233,1,'Cierre de sesión','2011-12-23 13:37:29','2011-12-23 13:37:29'),(1234,1,'Inicio de sessión','2011-12-23 13:38:26','2011-12-23 13:38:26'),(1235,1,'Inicio de sessión','2011-12-23 17:01:40','2011-12-23 17:01:40'),(1236,1,'Cierre de sesión','2011-12-23 17:12:07','2011-12-23 17:12:07'),(1237,1,'Inicio de sessión','2011-12-23 17:12:17','2011-12-23 17:12:17'),(1238,1,'Cierre de sesión','2011-12-23 17:16:01','2011-12-23 17:16:01'),(1239,1,'Inicio de sessión','2011-12-23 17:16:15','2011-12-23 17:16:15'),(1240,1,'Cierre de sesión','2011-12-23 17:20:21','2011-12-23 17:20:21'),(1241,1,'Inicio de sessión','2011-12-23 17:20:39','2011-12-23 17:20:39'),(1242,1,'Inicio de sessión','2011-12-23 19:30:53','2011-12-23 19:30:53'),(1243,1,'Cierre de sesión','2011-12-23 19:46:11','2011-12-23 19:46:11'),(1244,1,'Inicio de sessión','2011-12-23 19:46:19','2011-12-23 19:46:19'),(1245,1,'Cierre de sesión','2011-12-23 19:58:43','2011-12-23 19:58:43'),(1246,1,'Inicio de sessión','2011-12-23 19:58:53','2011-12-23 19:58:53'),(1247,1,'Cierre de sesión','2011-12-23 21:22:34','2011-12-23 21:22:34'),(1248,1,'Inicio de sessión','2011-12-23 21:22:39','2011-12-23 21:22:39'),(1249,1,'Cierre de sesión','2011-12-23 21:36:26','2011-12-23 21:36:26'),(1250,1,'Inicio de sessión','2011-12-23 21:36:36','2011-12-23 21:36:36'),(1251,1,'Cierre de sesión','2011-12-23 21:53:30','2011-12-23 21:53:30'),(1252,1,'Inicio de sessión','2011-12-23 21:53:40','2011-12-23 21:53:40'),(1253,1,'Cierre de sesión','2011-12-23 21:55:25','2011-12-23 21:55:25'),(1254,1,'Inicio de sessión','2011-12-23 21:55:35','2011-12-23 21:55:35'),(1255,1,'Cierre de sesión','2011-12-23 21:57:37','2011-12-23 21:57:37'),(1256,1,'Inicio de sessión','2011-12-23 21:57:53','2011-12-23 21:57:53'),(1257,1,'Cierre de sesión','2011-12-23 22:00:17','2011-12-23 22:00:17'),(1258,1,'Inicio de sessión','2011-12-23 22:00:40','2011-12-23 22:00:40'),(1259,1,'Cierre de sesión','2011-12-23 22:03:34','2011-12-23 22:03:34'),(1260,1,'Inicio de sessión','2011-12-23 22:04:21','2011-12-23 22:04:21'),(1261,1,'Inicio de sessión','2011-12-23 22:19:22','2011-12-23 22:19:22'),(1262,1,'Cierre de sesión','2011-12-23 22:24:58','2011-12-23 22:24:58'),(1263,1,'Inicio de sessión','2011-12-23 22:25:08','2011-12-23 22:25:08'),(1264,1,'Inicio de sessión','2011-12-25 09:00:54','2011-12-25 09:00:54'),(1265,1,'Inicio de sessión','2011-12-25 09:27:38','2011-12-25 09:27:38'),(1266,1,'Inicio de sessión','2011-12-26 06:34:55','2011-12-26 06:34:55'),(1267,1,'Inicio de sessión','2011-12-26 14:29:33','2011-12-26 14:29:33'),(1268,1,'Cierre de sesión','2011-12-26 16:07:36','2011-12-26 16:07:36'),(1269,1,'Inicio de sessión','2011-12-26 16:07:55','2011-12-26 16:07:55'),(1270,1,'Inicio de sessión','2011-12-26 22:30:03','2011-12-26 22:30:03'),(1271,1,'Cierre de sesión','2011-12-26 22:35:08','2011-12-26 22:35:08'),(1272,1,'Inicio de sessión','2011-12-26 22:35:18','2011-12-26 22:35:18'),(1273,1,'Cierre de sesión','2011-12-26 22:46:54','2011-12-26 22:46:54'),(1274,1,'Inicio de sessión','2011-12-26 22:47:38','2011-12-26 22:47:38'),(1275,1,'Inicio de sessión','2011-12-26 23:13:36','2011-12-26 23:13:36'),(1276,1,'Inicio de sessión','2011-12-26 23:19:47','2011-12-26 23:19:47'),(1277,1,'Cierre de sesión','2011-12-26 23:19:47','2011-12-26 23:19:47'),(1278,1,'Cierre de sesión','2011-12-26 23:22:34','2011-12-26 23:22:34'),(1279,1,'Inicio de sessión','2011-12-26 23:23:32','2011-12-26 23:23:32'),(1280,1,'Inicio de sessión','2011-12-26 23:25:44','2011-12-26 23:25:44'),(1281,1,'Cierre de sesión','2011-12-26 23:25:44','2011-12-26 23:25:44'),(1282,1,'Inicio de sessión','2011-12-27 20:21:45','2011-12-27 20:21:45'),(1283,1,'Inicio de sessión','2011-12-28 09:59:33','2011-12-28 09:59:33'),(1284,1,'Inicio de sessión','2011-12-28 11:14:16','2011-12-28 11:14:16'),(1285,1,'Cierre de sesión','2011-12-28 11:52:13','2011-12-28 11:52:13'),(1286,1,'Inicio de sessión','2011-12-28 11:52:23','2011-12-28 11:52:23'),(1287,1,'Inicio de sessión','2011-12-31 11:37:01','2011-12-31 11:37:01'),(1288,1,'Inicio de sessión','2012-01-01 11:13:26','2012-01-01 11:13:26'),(1289,1,'Cierre de sesión','2012-01-01 12:08:33','2012-01-01 12:08:33'),(1290,1,'Inicio de sessión','2012-01-01 12:08:37','2012-01-01 12:08:37'),(1291,1,'Cierre de sesión','2012-01-01 12:26:33','2012-01-01 12:26:33'),(1292,1,'Inicio de sessión','2012-01-01 12:26:44','2012-01-01 12:26:44'),(1293,1,'Inicio de sessión','2012-01-01 17:42:36','2012-01-01 17:42:36'),(1294,1,'Cierre de sesión','2012-01-01 17:52:50','2012-01-01 17:52:50'),(1295,1,'Inicio de sessión','2012-01-01 17:53:00','2012-01-01 17:53:00'),(1296,1,'Cierre de sesión','2012-01-01 19:51:03','2012-01-01 19:51:03'),(1297,1,'Inicio de sessión','2012-01-01 20:05:19','2012-01-01 20:05:19'),(1298,1,'Cierre de sesión','2012-01-01 20:53:30','2012-01-01 20:53:30'),(1299,1,'Inicio de sessión','2012-01-01 20:53:33','2012-01-01 20:53:33'),(1300,1,'Cierre de sesión','2012-01-01 21:22:46','2012-01-01 21:22:46'),(1301,1,'Inicio de sessión','2012-01-01 21:22:50','2012-01-01 21:22:50'),(1302,1,'Cierre de sesión','2012-01-01 21:56:09','2012-01-01 21:56:09'),(1303,1,'Inicio de sessión','2012-01-01 21:56:19','2012-01-01 21:56:19'),(1304,1,'Cierre de sesión','2012-01-01 22:00:36','2012-01-01 22:00:36'),(1305,1,'Inicio de sessión','2012-01-01 22:00:39','2012-01-01 22:00:39'),(1306,1,'Cierre de sesión','2012-01-01 22:45:59','2012-01-01 22:45:59'),(1307,1,'Inicio de sessión','2012-01-01 23:58:44','2012-01-01 23:58:44'),(1308,1,'Cierre de sesión','2012-01-02 00:06:19','2012-01-02 00:06:19'),(1309,1,'Inicio de sessión','2012-01-02 00:06:30','2012-01-02 00:06:30'),(1310,1,'Cierre de sesión','2012-01-02 00:14:00','2012-01-02 00:14:00'),(1311,1,'Inicio de sessión','2012-01-02 00:16:33','2012-01-02 00:16:33'),(1312,1,'Cierre de sesión','2012-01-02 00:22:54','2012-01-02 00:22:54'),(1313,1,'Inicio de sessión','2012-01-02 00:23:04','2012-01-02 00:23:04'),(1314,1,'Inicio de sessión','2012-01-02 09:47:20','2012-01-02 09:47:20'),(1315,1,'Cierre de sesión','2012-01-02 09:53:41','2012-01-02 09:53:41'),(1316,1,'Inicio de sessión','2012-01-02 09:53:45','2012-01-02 09:53:45'),(1317,1,'Cierre de sesión','2012-01-02 09:56:14','2012-01-02 09:56:14'),(1318,1,'Inicio de sessión','2012-01-02 09:56:30','2012-01-02 09:56:30'),(1319,1,'Cierre de sesión','2012-01-02 10:35:21','2012-01-02 10:35:21'),(1320,1,'Inicio de sessión','2012-01-02 10:35:33','2012-01-02 10:35:33'),(1321,1,'Cierre de sesión','2012-01-02 11:21:20','2012-01-02 11:21:20');
/*!40000 ALTER TABLE `tblbitacora` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblrespuestacriterio`
--

DROP TABLE IF EXISTS `tblrespuestacriterio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblrespuestacriterio` (
  `idRespuestaCriterio` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la respuesta.',
  `idRespuesta` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la respuesta.',
  `idCriterio` int(11) unsigned NOT NULL COMMENT 'Identificador del criterio.',
  `respuesta` tinyint(1) NOT NULL COMMENT 'Indica si el donante respondió afirmativa o negativamente.',
  `observacion` text COMMENT 'Texto explicativo de la respuesta del donante.',
  PRIMARY KEY (`idRespuestaCriterio`),
  KEY `FK_Criterio_RespuestaCriterio` (`idCriterio`),
  KEY `FK_RespuestaCuestionario_RespuestaCriterio` (`idRespuesta`),
  CONSTRAINT `FK_Criterio_RespuestaCriterio` FOREIGN KEY (`idCriterio`) REFERENCES `tblcriterio` (`idCriterio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_RespuestaCuestionario_RespuestaCriterio` FOREIGN KEY (`idRespuesta`) REFERENCES `tblrespuestacuestionario` (`idRespuesta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblrespuestacriterio`
--

LOCK TABLES `tblrespuestacriterio` WRITE;
/*!40000 ALTER TABLE `tblrespuestacriterio` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblrespuestacriterio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbllaboratorio`
--

DROP TABLE IF EXISTS `tbllaboratorio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbllaboratorio` (
  `idLaboratorio` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del laboratorio.',
  `nombreLaboratorio` varchar(254) NOT NULL COMMENT 'Nombre del laboratorio.',
  `telefono` varchar(9) DEFAULT NULL COMMENT 'Teléfono del laboratorio.',
  PRIMARY KEY (`idLaboratorio`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbllaboratorio`
--

LOCK TABLES `tbllaboratorio` WRITE;
/*!40000 ALTER TABLE `tbllaboratorio` DISABLE KEYS */;
INSERT INTO `tbllaboratorio` VALUES (1,'laboratorio Velasquez','2567-4321'),(2,'Laboratorio Aguillon','2376-3456'),(3,'Laboratorio Olmos','2367-4565'),(4,'Laboratorio Henrriquez','2367-5432'),(5,'Laboratorios Vasquez','2367-1090');
/*!40000 ALTER TABLE `tbllaboratorio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblmunicipio`
--

DROP TABLE IF EXISTS `tblmunicipio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblmunicipio` (
  `idMunicipio` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del municipio.',
  `idDepartamento` smallint(5) unsigned NOT NULL COMMENT 'Identificador del departamento.',
  `nomMunicipio` varchar(120) NOT NULL COMMENT 'Nombre del municipio.',
  PRIMARY KEY (`idMunicipio`),
  UNIQUE KEY `nomMunicipio_UNIQUE` (`nomMunicipio`),
  KEY `FK_Departamento_Municipio` (`idDepartamento`),
  CONSTRAINT `FK_Departamento_Municipio` FOREIGN KEY (`idDepartamento`) REFERENCES `tbldepartamento` (`idDepartamento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblmunicipio`
--

LOCK TABLES `tblmunicipio` WRITE;
/*!40000 ALTER TABLE `tblmunicipio` DISABLE KEYS */;
INSERT INTO `tblmunicipio` VALUES (2,1,'San Vicente'),(3,1,'Tecoluca'),(4,1,'San Cayetano'),(5,1,'Apastepeque'),(6,1,'Guadalupe'),(7,1,'Verapaz'),(8,1,'Tepetitan'),(9,1,'Santa Clara'),(10,1,'Santo Domingo'),(11,1,'San Lorenzo'),(12,1,'San Ildelfonso'),(13,1,'San Sebastian'),(14,2,'Zacatecoluca'),(15,5,'Cojutepeque'),(16,4,'Ilopango'),(17,5,'El Carmen'),(18,5,'Cujuapa'),(19,5,' Candelaria'),(20,5,'San Ramon'),(21,2,'Sensuntepeque'),(22,2,'Cabañas'),(23,4,'Soyapango'),(24,4,'San Martin'),(25,6,'Santa Ana'),(26,6,'Metapan'),(27,6,'Aguilares');
/*!40000 ALTER TABLE `tblmunicipio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcontrolcalidad`
--

DROP TABLE IF EXISTS `tblcontrolcalidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontrolcalidad` (
  `idControlCalidad` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador del control de calidad.',
  `idEquipo` int(11) NOT NULL COMMENT 'Identificador del equipo.',
  `fecha` datetime NOT NULL COMMENT 'Fecha en la que se realizó el control de calidad.',
  `observaciones` text COMMENT 'Observaciones o comentarios sobre el control de calidad realizado.',
  `codigoMarcacion` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`idControlCalidad`),
  KEY `FK_EquipoAutomatizado_ControlCalidad` (`idEquipo`),
  KEY `fk_tblcontrolcalidad_tblempleado` (`codigoMarcacion`),
  CONSTRAINT `FK_EquipoAutomatizado_ControlCalidad` FOREIGN KEY (`idEquipo`) REFERENCES `tblequipoautomatizado` (`idEquipo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tblcontrolcalidad_tblempleado` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcontrolcalidad`
--

LOCK TABLES `tblcontrolcalidad` WRITE;
/*!40000 ALTER TABLE `tblcontrolcalidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblcontrolcalidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbltabla`
--

DROP TABLE IF EXISTS `tbltabla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbltabla` (
  `idTabla` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la tabla.',
  `idConexion` int(11) NOT NULL COMMENT 'Identificador de la conexión.',
  `nombreTabla` varchar(60) NOT NULL COMMENT 'Nombre de la tabla.',
  `tablaAsociada` varchar(60) NOT NULL COMMENT 'Tabla asociada en el sistema.',
  PRIMARY KEY (`idTabla`),
  KEY `FK_Conexion_Tabla` (`idConexion`),
  CONSTRAINT `FK_Conexion_Tabla` FOREIGN KEY (`idConexion`) REFERENCES `tblconexion` (`idConexion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbltabla`
--

LOCK TABLES `tbltabla` WRITE;
/*!40000 ALTER TABLE `tbltabla` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbltabla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblresultado_incidencia`
--

DROP TABLE IF EXISTS `tblresultado_incidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblresultado_incidencia` (
  `idResultado` bigint(19) unsigned NOT NULL COMMENT 'Identificador del resultado.',
  `idIncidencia` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la incidencia.',
  PRIMARY KEY (`idResultado`,`idIncidencia`),
  KEY `FK_Resultado_Incidencia_Incidencia` (`idIncidencia`),
  CONSTRAINT `FK_Resultado_Incidencia_Incidencia` FOREIGN KEY (`idIncidencia`) REFERENCES `tblincidencia` (`idIncidencia`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Resultado_Incidencia_Resultado` FOREIGN KEY (`idResultado`) REFERENCES `tblresultado` (`idResultado`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblresultado_incidencia`
--

LOCK TABLES `tblresultado_incidencia` WRITE;
/*!40000 ALTER TABLE `tblresultado_incidencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblresultado_incidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbljefe`
--

DROP TABLE IF EXISTS `tbljefe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbljefe` (
  `idJefe` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idEmpleado` smallint(5) unsigned NOT NULL,
  `idEstablecimiento` int(10) unsigned NOT NULL,
  `fechaInicio` datetime NOT NULL,
  `fechaFinal` datetime NOT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idJefe`),
  KEY `FK_Establecimiento_Jefe` (`idEstablecimiento`),
  KEY `FK_Empleado_Jefe` (`idEmpleado`),
  CONSTRAINT `FK_Empleado_Jefe` FOREIGN KEY (`idEmpleado`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Establecimiento_Jefe` FOREIGN KEY (`idEstablecimiento`) REFERENCES `tblestablecimiento` (`idEstablecimiento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbljefe`
--

LOCK TABLES `tbljefe` WRITE;
/*!40000 ALTER TABLE `tbljefe` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbljefe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblclinicaexterna`
--

DROP TABLE IF EXISTS `tblclinicaexterna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblclinicaexterna` (
  `idClinicaExterna` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la clinica.',
  `idServicio` smallint(5) unsigned NOT NULL COMMENT 'Identificador del servicio',
  `tipoClinica` varchar(30) NOT NULL COMMENT 'Tipo de clínica. Esta puede ser (Unidad de salud, Clinica Empresarial ISSS, Penal de maxima seguridad, otro)',
  `nombreClinica` varchar(254) NOT NULL COMMENT 'Nombre de la clinica.',
  `direccionClinica` text COMMENT 'Dirección de la clinica.',
  `telefonoClinica` varchar(9) DEFAULT NULL COMMENT 'Telefono de la clinica.',
  `encargado` varchar(60) DEFAULT NULL COMMENT 'Persona de contacto de la clincia.',
  PRIMARY KEY (`idClinicaExterna`),
  KEY `FK_Servicio_ClinicaExterna` (`idServicio`),
  CONSTRAINT `FK_Servicio_ClinicaExterna` FOREIGN KEY (`idServicio`) REFERENCES `tblservicio` (`idServicio`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblclinicaexterna`
--

LOCK TABLES `tblclinicaexterna` WRITE;
/*!40000 ALTER TABLE `tblclinicaexterna` DISABLE KEYS */;
INSERT INTO `tblclinicaexterna` VALUES (1,3,'Unidad de Salud','Unidad de Salud Periferica','Carretera a San Vicente','23340005','Jorge Antonio Ramos');
/*!40000 ALTER TABLE `tblclinicaexterna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblpermiso`
--

DROP TABLE IF EXISTS `tblpermiso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblpermiso` (
  `idPermiso` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del permiso.',
  `codigoMarcacion` smallint(5) unsigned NOT NULL COMMENT 'Código asignado por el departamento de recursos humanos para identificar a los empleados.',
  `fechaSolicitud` datetime NOT NULL COMMENT 'Fecha en la que se solicitó el permiso.',
  `fechaInicio` datetime NOT NULL COMMENT 'Fecha de inicio del periodo solicitado.',
  `fechaFinal` datetime NOT NULL COMMENT 'Fecha final del periodo solicitado.',
  `observaciones` text COMMENT 'Observaciones o comentarios adicionales sobre el permiso solicitado.',
  `motivo` varchar(100) NOT NULL COMMENT 'Tipo de permiso solicitado, este puede ser: Personal, vacaciones, por duelo, por maternidad, por estudio, etc.',
  `tipoLicencia` varchar(15) NOT NULL,
  `goceSueldo` tinyint(1) NOT NULL,
  `tipoPermiso` tinyint(1) NOT NULL,
  `jornadaCompleta` tinyint(1) NOT NULL,
  PRIMARY KEY (`idPermiso`),
  KEY `FK_Empleado_Permiso` (`codigoMarcacion`),
  CONSTRAINT `FK_Empleado_Permiso` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblpermiso`
--

LOCK TABLES `tblpermiso` WRITE;
/*!40000 ALTER TABLE `tblpermiso` DISABLE KEYS */;
INSERT INTO `tblpermiso` VALUES (1,1,'2011-11-26 14:10:43','2011-11-26 00:00:00','2011-11-27 00:00:00','muerte de padre','Misión oficial','Ausentarse',1,0,1),(2,2,'2011-11-26 16:07:22','2011-11-28 07:30:00','2011-11-30 17:30:00','','Duelo','Ausentarse',0,0,0),(3,17,'2011-11-26 16:08:33','2011-11-30 00:00:00','2011-12-09 00:00:00','','Enfermedad','Ausentarse',0,0,1),(4,455,'2011-12-06 11:54:11','2011-12-06 00:00:00','2011-12-17 00:00:00','','Tengo problemasfamiliares','Ausentarse',1,1,1);
/*!40000 ALTER TABLE `tblpermiso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblpuestoempleado`
--

DROP TABLE IF EXISTS `tblpuestoempleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblpuestoempleado` (
  `idPuestoEmpleado` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del puesto asignado al empleado.',
  `idPuesto` smallint(5) unsigned NOT NULL COMMENT 'Identificador del puesto.',
  `codigoMarcacion` smallint(5) unsigned NOT NULL COMMENT 'Código asignado por el departamento de recursos humanos para identificar a los empleados.',
  `fechaAsignacion` datetime NOT NULL COMMENT 'Fecha en la que fue asignado el puesto al empleado.',
  `unidadPresupuestaria` varchar(50) DEFAULT NULL COMMENT 'Unidad presupuestaria donde se contempla el puesto asignado.',
  `partida` varchar(15) DEFAULT NULL COMMENT 'Partida del presupuesto donde se contempla el puesto asignado.',
  `lineaTrabajo` varchar(15) DEFAULT NULL COMMENT 'Categoria que identifica el tipo de trabajo realizado por el empleado.',
  `estado` tinyint(1) NOT NULL COMMENT 'Indica si el empleado esta ejerciendo actualmente el puesto.',
  PRIMARY KEY (`idPuestoEmpleado`),
  KEY `FK_Empleado_PuestoEmpleado` (`codigoMarcacion`),
  KEY `FK_Puesto_Empleado` (`idPuesto`),
  CONSTRAINT `FK_Empleado_PuestoEmpleado` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Puesto_Empleado` FOREIGN KEY (`idPuesto`) REFERENCES `tblpuesto` (`idPuesto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblpuestoempleado`
--

LOCK TABLES `tblpuestoempleado` WRITE;
/*!40000 ALTER TABLE `tblpuestoempleado` DISABLE KEYS */;
INSERT INTO `tblpuestoempleado` VALUES (1,3,2,'2011-11-01 00:00:00','0101-98','1001','189-A',1),(2,1,1,'2011-11-22 00:00:00','administracion','123','asdasdasd',1),(3,1,17,'2011-11-09 00:00:00','fdfdfdfdf','145','adasdasdasd',1),(4,1,455,'2011-12-02 00:00:00','10092010','1-2010','I-2010',0),(5,4,455,'2011-12-07 00:00:00','02','02','12-2',1);
/*!40000 ALTER TABLE `tblpuestoempleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblextraccion`
--

DROP TABLE IF EXISTS `tblextraccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblextraccion` (
  `idExtraccion` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la extracción.',
  `idDonate` bigint(19) unsigned NOT NULL COMMENT 'Identificador del donante',
  `idBancoSangre` bigint(19) unsigned NOT NULL COMMENT 'Identificador del banco de sangre.',
  `codigoMarcacion` smallint(5) unsigned NOT NULL,
  `fechaObtencion` datetime NOT NULL COMMENT 'Fecha en la que se realizó la extracción.',
  `unidadCompleta` tinyint(1) NOT NULL COMMENT 'Indica si el donante completo la bolsa de sangre o la dejo incompleta.',
  `horaInicio` datetime NOT NULL COMMENT 'Hora en que dió inicio la extracción.',
  `horaFinal` datetime NOT NULL COMMENT 'Hora en la que finaliza la extracción.',
  `reaccionAdversa` tinyint(1) NOT NULL COMMENT 'Indica si el paciente tuvo una reaccion adversa durante la extracción.',
  `observaciones` text COMMENT 'Observaciones o comentarios adicionales sobre la extracción.',
  `lugExtraccion` varchar(254) NOT NULL COMMENT 'Lugar donde se realizo la extracción, si fue en las instalaciones del laboratorio se indicara eso o el lugar donde se haya hecho.',
  `tipoDonante` varchar(25) NOT NULL COMMENT 'Tipo de donante. Indica si el donante es Autologo, Aferesis, etc.',
  `unidadMovil` varchar(100) DEFAULT NULL COMMENT 'Indica si la donacion se realizo en una unidad movil.',
  PRIMARY KEY (`idExtraccion`),
  KEY `FK_BancoSangre_Extraccion` (`idBancoSangre`),
  KEY `FK_Donante_Extraccion` (`idDonate`),
  KEY `FK_Empleado_Extraccion` (`codigoMarcacion`),
  CONSTRAINT `FK_BancoSangre_Extraccion` FOREIGN KEY (`idBancoSangre`) REFERENCES `tblbancosangre` (`idBancoSangre`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Donante_Extraccion` FOREIGN KEY (`idDonate`) REFERENCES `tbldonante` (`idDonate`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Empleado_Extraccion` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblextraccion`
--

LOCK TABLES `tblextraccion` WRITE;
/*!40000 ALTER TABLE `tblextraccion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblextraccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblexamen_orden`
--

DROP TABLE IF EXISTS `tblexamen_orden`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblexamen_orden` (
  `idExamenOrden` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `idExamen` bigint(19) unsigned NOT NULL COMMENT 'Identificador del examen.',
  `idOrden` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la orden.',
  `numeroControl` bigint(19) unsigned NOT NULL,
  `estado` varchar(20) DEFAULT NULL,
  `fechaRegistro` datetime DEFAULT NULL,
  `codigoMarcacion` smallint(5) unsigned DEFAULT NULL,
  PRIMARY KEY (`idExamenOrden`),
  KEY `FK_Examen_Orden` (`idOrden`),
  KEY `FK_Examen_Orden_Examen` (`idExamen`),
  KEY `fk_Examen_Orden_tblempleado` (`codigoMarcacion`),
  CONSTRAINT `FK_Examen_Orden` FOREIGN KEY (`idOrden`) REFERENCES `tblorden` (`idOrden`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Examen_Orden_Examen` FOREIGN KEY (`idExamen`) REFERENCES `tblexamen` (`idExamen`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Examen_Orden_tblempleado` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblexamen_orden`
--

LOCK TABLES `tblexamen_orden` WRITE;
/*!40000 ALTER TABLE `tblexamen_orden` DISABLE KEYS */;
INSERT INTO `tblexamen_orden` VALUES (134,1,151,1,'Entregado','2011-12-14 20:14:17',17),(135,1,152,2,'Finalizado','2011-12-14 22:14:50',17),(136,3,153,1,'Finalizado','2011-12-14 20:15:00',1),(137,5,154,2,'Pendiente','2011-12-12 12:22:59',NULL),(140,2,157,1,'Pendiente','2011-12-12 12:10:36',NULL),(141,5,158,3,'Pendiente','2011-12-14 17:24:41',NULL),(142,3,159,4,'Finalizado','2011-12-22 17:47:50',17),(143,1,160,4,'Pendiente','2011-12-12 12:33:21',NULL),(144,2,161,2,'Pendiente','2011-12-12 12:29:44',NULL),(145,1,162,1,'Pendiente','2011-12-12 12:54:56',NULL),(146,2,163,1,'Pendiente','2011-12-12 12:34:07',NULL),(147,1,164,2,'Pendiente','2011-12-12 12:55:10',NULL),(148,1,165,3,'Pendiente','2011-12-12 12:55:22',NULL),(149,1,166,4,'Pendiente','2011-12-12 12:55:32',NULL),(150,1,167,5,'Pendiente','2011-12-12 13:04:20',NULL),(151,1,168,6,'Pendiente','2011-12-12 13:04:37',NULL),(152,1,169,7,'Pendiente','2011-12-12 12:35:02',NULL),(153,3,170,8,'Pendiente','2011-12-12 12:40:46',NULL),(154,1,171,0,'Finalizado','2011-12-27 20:23:10',17),(155,4,173,1,'Pendiente',NULL,NULL),(156,5,174,4,'Finalizado','2012-01-02 10:07:02',17),(157,3,175,2,'Finalizado','2012-01-02 10:37:16',17);
/*!40000 ALTER TABLE `tblexamen_orden` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblorden_domicilio`
--

DROP TABLE IF EXISTS `tblorden_domicilio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblorden_domicilio` (
  `idOrden` bigint(19) unsigned NOT NULL,
  `idDomicilio` bigint(19) unsigned NOT NULL,
  PRIMARY KEY (`idOrden`,`idDomicilio`),
  KEY `fk_tblorden_has_tbldomicilio_tbldomicilio1` (`idDomicilio`),
  CONSTRAINT `fk_tblorden_has_tbldomicilio_tbldomicilio1` FOREIGN KEY (`idDomicilio`) REFERENCES `tbldomicilio` (`idDomicilio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblorden_has_tbldomicilio_tblorden1` FOREIGN KEY (`idOrden`) REFERENCES `tblorden` (`idOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblorden_domicilio`
--

LOCK TABLES `tblorden_domicilio` WRITE;
/*!40000 ALTER TABLE `tblorden_domicilio` DISABLE KEYS */;
INSERT INTO `tblorden_domicilio` VALUES (152,6),(154,7);
/*!40000 ALTER TABLE `tblorden_domicilio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbldepartamento`
--

DROP TABLE IF EXISTS `tbldepartamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbldepartamento` (
  `idDepartamento` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del departamento.',
  `nomDepartamento` varchar(120) NOT NULL COMMENT 'Nombre del departamento.',
  PRIMARY KEY (`idDepartamento`),
  UNIQUE KEY `nomDepartamento_UNIQUE` (`nomDepartamento`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbldepartamento`
--

LOCK TABLES `tbldepartamento` WRITE;
/*!40000 ALTER TABLE `tbldepartamento` DISABLE KEYS */;
INSERT INTO `tbldepartamento` VALUES (5,'Cuscatlán'),(2,'La Paz'),(4,'San Salvador'),(1,'San Vicente'),(6,'Santa Ana'),(3,'Usulutan');
/*!40000 ALTER TABLE `tbldepartamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblevaluaciondonantes_domicilio`
--

DROP TABLE IF EXISTS `tblevaluaciondonantes_domicilio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblevaluaciondonantes_domicilio` (
  `idEvaluacionDonantes` bigint(19) unsigned NOT NULL,
  `idDomicilio` bigint(19) unsigned NOT NULL,
  PRIMARY KEY (`idEvaluacionDonantes`,`idDomicilio`),
  KEY `fk_Domicilio_EvaluacionDonante` (`idDomicilio`),
  CONSTRAINT `fk_Domicilio_EvaluacionDonante` FOREIGN KEY (`idDomicilio`) REFERENCES `tbldomicilio` (`idDomicilio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_EvaluacionDonante_Domicilio` FOREIGN KEY (`idEvaluacionDonantes`) REFERENCES `tblevaluaciondonante` (`idEvaluacionDonante`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblevaluaciondonantes_has_tbldomicilio_tblevaluaciondonant1` FOREIGN KEY (`idEvaluacionDonantes`) REFERENCES `tblevaluaciondonantes` (`idEvaluacionDonantes`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblevaluaciondonantes_domicilio`
--

LOCK TABLES `tblevaluaciondonantes_domicilio` WRITE;
/*!40000 ALTER TABLE `tblevaluaciondonantes_domicilio` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblevaluaciondonantes_domicilio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbldomicilio`
--

DROP TABLE IF EXISTS `tbldomicilio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbldomicilio` (
  `idDomicilio` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de domicilio.',
  `idPersona` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la persona.',
  `idMunicipio` smallint(5) unsigned NOT NULL COMMENT 'Identificador del municipio.',
  `direccion` text COMMENT 'Dirección de la persona.',
  `telefonoParticular` varchar(9) DEFAULT NULL COMMENT 'Teléfono particular.',
  PRIMARY KEY (`idDomicilio`),
  KEY `FK_Municipio_Domicilio` (`idMunicipio`),
  KEY `FK_Persona_Domicilio` (`idPersona`),
  CONSTRAINT `FK_Municipio_Domicilio` FOREIGN KEY (`idMunicipio`) REFERENCES `tblmunicipio` (`idMunicipio`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Persona_Domicilio` FOREIGN KEY (`idPersona`) REFERENCES `tblpersona` (`idPersona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbldomicilio`
--

LOCK TABLES `tbldomicilio` WRITE;
/*!40000 ALTER TABLE `tbldomicilio` DISABLE KEYS */;
INSERT INTO `tbldomicilio` VALUES (3,6,2,'Barrio y calle concepcion #35','2393-6841'),(4,42,2,'callae al lago frente a alcaldia','7912-4832'),(5,149,2,'Barrio el Centro, 1a calle pte. Tecoluca, San Vicente.','2393-0000'),(6,170,15,NULL,NULL),(7,172,14,NULL,NULL),(8,193,14,'Residencial chinchontepec, #35','');
/*!40000 ALTER TABLE `tbldomicilio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblconexion`
--

DROP TABLE IF EXISTS `tblconexion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblconexion` (
  `idConexion` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la conexión.',
  `idEquipo` int(11) DEFAULT NULL COMMENT 'Identificador del equipo.',
  `ip` varchar(60) NOT NULL COMMENT 'Dirección IP de la terminal que guarda la base de datos del equipo de análisis automatizado.',
  `usuario` varchar(60) NOT NULL COMMENT 'Nombre de usuario para acceder a la base de datos.',
  `password` varchar(254) NOT NULL COMMENT 'Contraseña para acceder a la base de datos.',
  `esquema` varchar(60) NOT NULL COMMENT 'Nombre del esquema o base de datos que contiene la información.',
  `url` text,
  PRIMARY KEY (`idConexion`),
  KEY `FK_EquipoAutomatizado_Conexion` (`idEquipo`),
  CONSTRAINT `FK_EquipoAutomatizado_Conexion` FOREIGN KEY (`idEquipo`) REFERENCES `tblequipoautomatizado` (`idEquipo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblconexion`
--

LOCK TABLES `tblconexion` WRITE;
/*!40000 ALTER TABLE `tblconexion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblconexion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcatalogo`
--

DROP TABLE IF EXISTS `tblcatalogo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcatalogo` (
  `idCatalogo` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de catálogo.',
  `categoria` varchar(254) NOT NULL COMMENT 'Nombre de la categoría.',
  PRIMARY KEY (`idCatalogo`),
  UNIQUE KEY `categoria_UNIQUE` (`categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcatalogo`
--

LOCK TABLES `tblcatalogo` WRITE;
/*!40000 ALTER TABLE `tblcatalogo` DISABLE KEYS */;
INSERT INTO `tblcatalogo` VALUES (2,'Cristaleria'),(1,'Descartables'),(4,'Material de limpieza'),(3,'Reactivos');
/*!40000 ALTER TABLE `tblcatalogo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblexcluido`
--

DROP TABLE IF EXISTS `tblexcluido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblexcluido` (
  `idDonanteExcluido` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del donante excluido.',
  `idDonate` bigint(19) unsigned NOT NULL COMMENT 'Identificador del donante',
  `catExclusion` varchar(25) NOT NULL COMMENT 'Categoria de la exclusión, esta puede ser: Diferida o Permanente.',
  `lugExclusion` varchar(254) NOT NULL COMMENT 'Lugar donde se realiza la exclusión. Por defecto es el propio laboratorio.',
  `fecExclusion` datetime NOT NULL COMMENT 'Fecha en la que se excluyo al donante.',
  `tiempoExclusion` int(11) NOT NULL COMMENT 'Tiempo que durará la exclusión, solo para los donantes Diferidos.',
  `fecElegibilidad` datetime NOT NULL COMMENT 'Fecha en la que el donante puede ser elegible para donar.',
  `motivoExclusion` text COMMENT 'Motivo o razón por la que se realiza la exclusión.',
  PRIMARY KEY (`idDonanteExcluido`),
  KEY `FK_Donante_Excluido` (`idDonate`),
  CONSTRAINT `FK_Donante_Excluido` FOREIGN KEY (`idDonate`) REFERENCES `tbldonante` (`idDonate`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblexcluido`
--

LOCK TABLES `tblexcluido` WRITE;
/*!40000 ALTER TABLE `tblexcluido` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblexcluido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblconfirmacion`
--

DROP TABLE IF EXISTS `tblconfirmacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblconfirmacion` (
  `idConfirmacion` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificación de la muestra enviada a confirmación.',
  `idOrden` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la orden.',
  `idLaboratorio` smallint(5) unsigned NOT NULL COMMENT 'Identificador del laboratorio.',
  `fechaEnvio` datetime NOT NULL COMMENT 'Fecha en que se envió la muestra.',
  `fecharecepcion` datetime NOT NULL COMMENT 'Fecha en la que se recibieron los resultados de la confirmación.',
  `resultado` text NOT NULL COMMENT 'Resultado obtenido de la confirmación.',
  `observaciones` text COMMENT 'Observaciones o comentarios adicionales.',
  PRIMARY KEY (`idConfirmacion`),
  KEY `FK_Laboratorio_Confirmacion` (`idLaboratorio`),
  KEY `FK_Orden_Confirmacion` (`idOrden`),
  CONSTRAINT `FK_Laboratorio_Confirmacion` FOREIGN KEY (`idLaboratorio`) REFERENCES `tbllaboratorio` (`idLaboratorio`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Orden_Confirmacion` FOREIGN KEY (`idOrden`) REFERENCES `tblorden` (`idOrden`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblconfirmacion`
--

LOCK TABLES `tblconfirmacion` WRITE;
/*!40000 ALTER TABLE `tblconfirmacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblconfirmacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbllugtrabajo`
--

DROP TABLE IF EXISTS `tbllugtrabajo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbllugtrabajo` (
  `idLugarTrabajo` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del lugar de trabajo.',
  `idPersona` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la persona.',
  `lugarTrabajo` varchar(254) NOT NULL COMMENT 'Lugar de trabajo.',
  `direccionTrabajo` text COMMENT 'Dirección del lugar de trabajo.',
  `telefonoTrabajo` varchar(9) DEFAULT NULL COMMENT 'Telefono de lugar de trabajo.',
  PRIMARY KEY (`idLugarTrabajo`),
  KEY `FK_Persona_LugarTrabajo` (`idPersona`),
  CONSTRAINT `FK_Persona_LugarTrabajo` FOREIGN KEY (`idPersona`) REFERENCES `tblpersona` (`idPersona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbllugtrabajo`
--

LOCK TABLES `tbllugtrabajo` WRITE;
/*!40000 ALTER TABLE `tbllugtrabajo` DISABLE KEYS */;
INSERT INTO `tbllugtrabajo` VALUES (1,149,'Industrias la constancia','Carretera a la Costa del Sol, Zactecoluca, La Paz','2334-0000');
/*!40000 ALTER TABLE `tbllugtrabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblpedido`
--

DROP TABLE IF EXISTS `tblpedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblpedido` (
  `idPedido` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador del articulo.',
  `fecha` datetime NOT NULL COMMENT 'Fecha de solicitud del articulo.',
  PRIMARY KEY (`idPedido`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblpedido`
--

LOCK TABLES `tblpedido` WRITE;
/*!40000 ALTER TABLE `tblpedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblpedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblprocedenciaarticulo`
--

DROP TABLE IF EXISTS `tblprocedenciaarticulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblprocedenciaarticulo` (
  `idProcedencia` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del lugar de procedencia.',
  `lugarProcedencia` varchar(254) NOT NULL COMMENT 'Nombre del lugar de procedencia.',
  PRIMARY KEY (`idProcedencia`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblprocedenciaarticulo`
--

LOCK TABLES `tblprocedenciaarticulo` WRITE;
/*!40000 ALTER TABLE `tblprocedenciaarticulo` DISABLE KEYS */;
INSERT INTO `tblprocedenciaarticulo` VALUES (2,'ministerio de salud'),(3,'fondos propios');
/*!40000 ALTER TABLE `tblprocedenciaarticulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcambioturno`
--

DROP TABLE IF EXISTS `tblcambioturno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcambioturno` (
  `idCambioTurno` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del cambio de turno.',
  `idPlanificacionTrabajo` bigint(19) unsigned NOT NULL COMMENT 'Identificador del plan de trabajo.',
  `idSolicitud` bigint(19) unsigned NOT NULL,
  `solicitante` tinyint(1) NOT NULL COMMENT 'Indica si el empledo es el solicitante del cambio o no.',
  PRIMARY KEY (`idCambioTurno`),
  KEY `FK_PlanificacionTrabajo_CambioTurno` (`idPlanificacionTrabajo`),
  KEY `fk_tblcambioturno_tblsolicitudcambioturno1` (`idSolicitud`),
  CONSTRAINT `FK_PlanificacionTrabajo_CambioTurno` FOREIGN KEY (`idPlanificacionTrabajo`) REFERENCES `tblplanificaciontrabajo` (`idPlanificacionTrabajo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tblcambioturno_tblsolicitudcambioturno1` FOREIGN KEY (`idSolicitud`) REFERENCES `tblsolicitudcambioturno` (`idSolicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcambioturno`
--

LOCK TABLES `tblcambioturno` WRITE;
/*!40000 ALTER TABLE `tblcambioturno` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblcambioturno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblevaluacionfisica`
--

DROP TABLE IF EXISTS `tblevaluacionfisica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblevaluacionfisica` (
  `idEvaluacionFisica` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de evaluación física.',
  `idEvaluacionDonantes` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la evaluación del donante.',
  `peso` double NOT NULL COMMENT 'Peso del donante en kilogramos.',
  `presionArterial` double NOT NULL COMMENT 'Presión arterial del donante.',
  `pulso` double NOT NULL COMMENT 'Pulso cardiaco del donante.',
  `temperatura` double NOT NULL COMMENT 'Temperatura corporal del donante.',
  `hemograma` double NOT NULL COMMENT 'Valor obtenido en el hemograma del donante.',
  `hemoglobina` double NOT NULL COMMENT 'Valor de hemoglobina del paciente, obtenido en los resultados.',
  `hematocrito` double NOT NULL COMMENT 'Porcentaje de hematocrito obtenido en los resultados.',
  `inspeccionBrazos` text NOT NULL COMMENT 'Descripción de estado de los brazos del paciente.',
  `tomoAlimentos` tinyint(1) NOT NULL COMMENT 'Indica si el donante ingerio alimentos antes de someterse al proceso de donación.',
  `observaciones` text COMMENT 'Observaciones o comentarios sobre la evaluación física.',
  PRIMARY KEY (`idEvaluacionFisica`),
  UNIQUE KEY `idEvaluacionDonantes_UNIQUE` (`idEvaluacionDonantes`),
  KEY `FK_EvaluacionDonantes_EvaluacionFisica` (`idEvaluacionDonantes`),
  CONSTRAINT `FK_EvaluacionDonantes_EvaluacionFisica` FOREIGN KEY (`idEvaluacionDonantes`) REFERENCES `tblevaluaciondonante` (`idEvaluacionDonante`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblevaluacionfisica`
--

LOCK TABLES `tblevaluacionfisica` WRITE;
/*!40000 ALTER TABLE `tblevaluacionfisica` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblevaluacionfisica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblequipoautomatizado`
--

DROP TABLE IF EXISTS `tblequipoautomatizado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblequipoautomatizado` (
  `idEquipo` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador del equipo.',
  `nombreEquipo` varchar(60) NOT NULL COMMENT 'Nombre del equipo automatizado.',
  PRIMARY KEY (`idEquipo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblequipoautomatizado`
--

LOCK TABLES `tblequipoautomatizado` WRITE;
/*!40000 ALTER TABLE `tblequipoautomatizado` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblequipoautomatizado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblrespuestacuestionario`
--

DROP TABLE IF EXISTS `tblrespuestacuestionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblrespuestacuestionario` (
  `idRespuesta` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la respuesta.',
  `idEvaluacionDonante` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la evaluación del donante.',
  `idCuestionario` int(11) unsigned NOT NULL COMMENT 'Identificador del cuestionario.',
  `respuesta` tinyint(1) NOT NULL COMMENT 'Respuesta dada por el donante a la pregunta realizada. Las respuestas son cerradas (Si o No)',
  PRIMARY KEY (`idRespuesta`),
  KEY `FK_CuestionarioEvaluacion_RespuestaCuestionario` (`idCuestionario`),
  KEY `FK_EvaluacionDonante_RespuestaCuestionario` (`idEvaluacionDonante`),
  CONSTRAINT `FK_CuestionarioEvaluacion_RespuestaCuestionario` FOREIGN KEY (`idCuestionario`) REFERENCES `tblcuestionarioevaluacion` (`idCuestionario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_EvaluacionDonante_RespuestaCuestionario` FOREIGN KEY (`idEvaluacionDonante`) REFERENCES `tblevaluaciondonante` (`idEvaluacionDonante`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblrespuestacuestionario`
--

LOCK TABLES `tblrespuestacuestionario` WRITE;
/*!40000 ALTER TABLE `tblrespuestacuestionario` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblrespuestacuestionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblexamenarticulo`
--

DROP TABLE IF EXISTS `tblexamenarticulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblexamenarticulo` (
  `idExamenarticulo` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador del reactivo o insumo asignado a un examen.',
  `idExamen` bigint(19) unsigned NOT NULL,
  `idArticulo` int(11) unsigned NOT NULL,
  `cantidad` double NOT NULL COMMENT 'Cantidad del reactivo o insumo necesaria para realizar el examen.',
  PRIMARY KEY (`idExamenarticulo`),
  KEY `fk_tblexamenarticulo_tblexamen1` (`idExamen`),
  KEY `fk_tblexamenarticulo_tblarticulo1` (`idArticulo`),
  CONSTRAINT `fk_tblexamenarticulo_tblarticulo1` FOREIGN KEY (`idArticulo`) REFERENCES `tblarticulo` (`idArticulo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tblexamenarticulo_tblexamen1` FOREIGN KEY (`idExamen`) REFERENCES `tblexamen` (`idExamen`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblexamenarticulo`
--

LOCK TABLES `tblexamenarticulo` WRITE;
/*!40000 ALTER TABLE `tblexamenarticulo` DISABLE KEYS */;
INSERT INTO `tblexamenarticulo` VALUES (1,1,2,1),(2,2,1,1),(3,3,1,1),(4,3,3,1),(5,4,2,1),(6,5,1,1);
/*!40000 ALTER TABLE `tblexamenarticulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblpuesto`
--

DROP TABLE IF EXISTS `tblpuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblpuesto` (
  `idPuesto` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del puesto.',
  `nombrePuesto` varchar(50) NOT NULL COMMENT 'Nombre del puesto.',
  `sueldoBase` double NOT NULL COMMENT 'Sueldo base asignado al puesto.',
  PRIMARY KEY (`idPuesto`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblpuesto`
--

LOCK TABLES `tblpuesto` WRITE;
/*!40000 ALTER TABLE `tblpuesto` DISABLE KEYS */;
INSERT INTO `tblpuesto` VALUES (1,'Tecnico de laboratorio',350),(2,'Jefe de laboratorio',800),(3,'Profesional de laboratorio',650),(4,'personal administrativo',250);
/*!40000 ALTER TABLE `tblpuesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblobjeto`
--

DROP TABLE IF EXISTS `tblobjeto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblobjeto` (
  `idObjeto` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del objeto.',
  `tbl_idObjeto` smallint(5) unsigned DEFAULT NULL COMMENT 'Identificador del objeto padre.',
  `tipoObjeto` varchar(25) NOT NULL COMMENT 'Tipode Objeto, puede ser: Menu, Submenu, Boton, Pagina Web, etc.',
  `nombreObjeto` varchar(100) NOT NULL COMMENT 'Nombre del objeto.',
  `grupo` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`idObjeto`),
  KEY `FK_Objetos_Objetos` (`tbl_idObjeto`),
  CONSTRAINT `FK_Objetos_Objetos` FOREIGN KEY (`tbl_idObjeto`) REFERENCES `tblobjeto` (`idObjeto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblobjeto`
--

LOCK TABLES `tblobjeto` WRITE;
/*!40000 ALTER TABLE `tblobjeto` DISABLE KEYS */;
INSERT INTO `tblobjeto` VALUES (1,NULL,'menu','Producción',NULL),(2,NULL,'menu','Banco de Sangre',NULL),(3,NULL,'menu','Inventario',NULL),(4,NULL,'menu','Recursos Humanos',NULL),(5,NULL,'menu','Mantenimiento',NULL),(6,NULL,'menu','Administración',NULL),(7,5,'submenu','Secciones de laboratorio','tarea'),(8,5,'submenu','Instituciones externas','tarea'),(9,5,'submenu','Laboratorios externos','tarea'),(10,5,'submenu','Medicos','tarea'),(11,5,'submenu','Departamentos','tarea'),(12,5,'submenu','Munucipios','tarea'),(13,5,'submenu','Modalidades','tarea'),(14,5,'submenu','Tipo de Servicio','tarea'),(15,5,'submenu','Sevicios','tarea'),(16,5,'submenu','Especialidades','tarea'),(17,6,'submenu','Cuentas de usuario','tarea'),(18,6,'submenu','Objetos','tarea'),(19,6,'submenu','Roles','tarea'),(20,6,'submenu','Año Laboral','tarea'),(21,4,'submenu','Planificación','tarea'),(22,4,'submenu','Capacitaciones','tarea'),(23,4,'submenu','Permisos','tarea'),(24,4,'submenu','Asignación de puestos','tarea'),(25,4,'submenu','Actualización de salario','tarea'),(26,4,'submenu','Empleados','tarea'),(27,4,'submenu','Puestos','tarea'),(28,3,'submenu','Reactivos e insumos','tarea'),(29,3,'submenu','Entradas','tarea'),(30,3,'submenu','Salidas','tarea'),(31,3,'submenu','Catalogo','tarea'),(32,3,'submenu','Unidades de medidad','tarea'),(33,3,'submenu','Procedencia','tarea'),(34,1,'submenu','Registro de sulicitudes','tarea'),(35,1,'submenu','Exmanes','tarea'),(38,2,'submenu','análisis sanguíneo','tarea'),(39,4,'submenu','Asignar Seccion','tarea'),(40,1,'submenu','Registro de Resultados','tarea'),(41,1,'submenu','Prueba','tarea'),(42,1,'submenu','Entrega de resultados','tarea'),(43,1,'submenu','Informes estadísticos ','reporte'),(44,1,'submenu','Generar resultados','reporte');
/*!40000 ALTER TABLE `tblobjeto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblorden_lugtrabajo`
--

DROP TABLE IF EXISTS `tblorden_lugtrabajo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblorden_lugtrabajo` (
  `idOrden` bigint(19) unsigned NOT NULL,
  `idLugarTrabajo` bigint(19) unsigned NOT NULL,
  PRIMARY KEY (`idOrden`,`idLugarTrabajo`),
  KEY `fk_tblorden_has_tbllugtrabajo_tbllugtrabajo1` (`idLugarTrabajo`),
  CONSTRAINT `fk_tblorden_has_tbllugtrabajo_tbllugtrabajo1` FOREIGN KEY (`idLugarTrabajo`) REFERENCES `tbllugtrabajo` (`idLugarTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblorden_has_tbllugtrabajo_tblorden1` FOREIGN KEY (`idOrden`) REFERENCES `tblorden` (`idOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblorden_lugtrabajo`
--

LOCK TABLES `tblorden_lugtrabajo` WRITE;
/*!40000 ALTER TABLE `tblorden_lugtrabajo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblorden_lugtrabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblestablecimiento`
--

DROP TABLE IF EXISTS `tblestablecimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblestablecimiento` (
  `idEstablecimiento` int(10) unsigned NOT NULL COMMENT 'Identificador del establecimiento.',
  `idMunicipio` smallint(5) unsigned NOT NULL,
  `nombre` varchar(254) NOT NULL COMMENT 'Nombre del establecimiento.',
  `codigo` varchar(25) NOT NULL COMMENT 'Código del establecimiento.',
  `direccion` text NOT NULL COMMENT 'Dirección del establecimiento.',
  `telefono` varchar(9) NOT NULL COMMENT 'Telefono del establecimiento.',
  `tema` varchar(50) DEFAULT NULL,
  `logo` tinyint(4) DEFAULT NULL COMMENT 'Logo de la institución.',
  `obejtivoGeneral` text COMMENT 'Objetivo general del laboratorio.',
  `objetivos` text COMMENT 'Objetivos especificos del laboratorio.',
  `metas` text COMMENT 'Metas del laboratorio.',
  `politicas` text COMMENT 'Politicas del laboratorio.',
  PRIMARY KEY (`idEstablecimiento`),
  KEY `FK_Municipio_Establecimiento` (`idMunicipio`),
  CONSTRAINT `FK_Municipio_Establecimiento` FOREIGN KEY (`idMunicipio`) REFERENCES `tblmunicipio` (`idMunicipio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblestablecimiento`
--

LOCK TABLES `tblestablecimiento` WRITE;
/*!40000 ALTER TABLE `tblestablecimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblestablecimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblresultado`
--

DROP TABLE IF EXISTS `tblresultado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblresultado` (
  `idResultado` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del resultado.',
  `idTipoResultadoExamen` smallint(5) unsigned NOT NULL COMMENT 'Identificador del tipo de resultado.',
  `codigoMarcacion` smallint(5) unsigned NOT NULL,
  `idExamenOrden` bigint(19) unsigned NOT NULL,
  `fechaResultado` datetime NOT NULL COMMENT 'Fecha en la que se obtuvo el resultado.',
  PRIMARY KEY (`idResultado`),
  KEY `fk_tblresultado_tblempleado` (`codigoMarcacion`),
  KEY `FK_TipoResultado_Resultado` (`idTipoResultadoExamen`),
  KEY `FK_ExamenOrden_Resultado` (`idExamenOrden`),
  CONSTRAINT `FK_ExamenOrden_Resultado` FOREIGN KEY (`idExamenOrden`) REFERENCES `tblexamen_orden` (`idExamenOrden`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tblresultado_tblempleado` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TipoResultado_Resultado` FOREIGN KEY (`idTipoResultadoExamen`) REFERENCES `tbltiporesultado` (`idTipoResultado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblresultado`
--

LOCK TABLES `tblresultado` WRITE;
/*!40000 ALTER TABLE `tblresultado` DISABLE KEYS */;
INSERT INTO `tblresultado` VALUES (81,1,1,134,'2011-12-14 20:14:17'),(82,1,1,136,'2011-12-14 20:15:00'),(83,3,1,135,'2011-12-14 22:14:50'),(84,1,1,142,'2011-12-22 17:47:50'),(85,1,1,154,'2011-12-27 20:23:10'),(86,4,1,156,'2012-01-02 10:07:02'),(87,3,1,157,'2012-01-02 10:37:16');
/*!40000 ALTER TABLE `tblresultado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbltransfusion_domicilio`
--

DROP TABLE IF EXISTS `tbltransfusion_domicilio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbltransfusion_domicilio` (
  `idTransfusion` bigint(19) unsigned NOT NULL,
  `idDomicilio` bigint(19) unsigned NOT NULL,
  PRIMARY KEY (`idTransfusion`,`idDomicilio`),
  KEY `fk_tbltransfusion_has_tbldomicilio_tbldomicilio1` (`idDomicilio`),
  KEY `fk_Transfuision_Domicilio` (`idTransfusion`),
  CONSTRAINT `fk_tbltransfusion_has_tbldomicilio_tbldomicilio1` FOREIGN KEY (`idDomicilio`) REFERENCES `tbldomicilio` (`idDomicilio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Transfuision_Domicilio` FOREIGN KEY (`idTransfusion`) REFERENCES `tbltransfusion` (`idTransfusion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbltransfusion_domicilio`
--

LOCK TABLES `tbltransfusion_domicilio` WRITE;
/*!40000 ALTER TABLE `tbltransfusion_domicilio` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbltransfusion_domicilio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblplanificaciontrabajo`
--

DROP TABLE IF EXISTS `tblplanificaciontrabajo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblplanificaciontrabajo` (
  `idPlanificacionTrabajo` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del plan de trabajo.',
  `codigoMarcacion` smallint(5) unsigned NOT NULL COMMENT 'Código asignado por el departamento de recursos humanos para identificar a los empleados.',
  `idCargaLaboral` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la carga laboral.',
  `fechaEntrada` datetime NOT NULL COMMENT 'Fecha que se esta programando.',
  `fechaSalida` datetime NOT NULL COMMENT 'Observaciones o comentarios adicionales sobre el turno programado.',
  `horaDuracion` smallint(6) NOT NULL,
  `minsDuracion` smallint(6) NOT NULL,
  PRIMARY KEY (`idPlanificacionTrabajo`),
  KEY `FK_Empleado_PlanificacionTrabajo` (`codigoMarcacion`),
  KEY `FK_CargaLaboral_PlanificacionTrabajo` (`idCargaLaboral`),
  CONSTRAINT `FK_CargaLaboral_PlanificacionTrabajo` FOREIGN KEY (`idCargaLaboral`) REFERENCES `tblcargalaboral` (`idCargaLaboral`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Empleado_PlanificacionTrabajo` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblplanificaciontrabajo`
--

LOCK TABLES `tblplanificaciontrabajo` WRITE;
/*!40000 ALTER TABLE `tblplanificaciontrabajo` DISABLE KEYS */;
INSERT INTO `tblplanificaciontrabajo` VALUES (1,1,1,'2011-01-01 07:00:00','2011-01-01 15:00:00',8,0),(2,1,4,'2011-04-01 12:00:00','2011-04-01 22:00:00',10,0),(3,2,4,'2011-04-02 02:00:00','2011-04-02 12:00:00',10,0),(4,2,1,'2011-01-03 08:00:00','2011-01-03 18:00:00',10,0),(5,1,6,'2011-12-06 07:00:00','2011-12-06 15:00:00',8,0),(6,17,6,'2011-12-06 08:00:00','2011-12-06 16:00:00',8,0),(7,455,6,'2011-12-06 08:00:00','2011-12-06 16:00:00',8,0);
/*!40000 ALTER TABLE `tblplanificaciontrabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblevaluaciondonantes_lugtrabajo`
--

DROP TABLE IF EXISTS `tblevaluaciondonantes_lugtrabajo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblevaluaciondonantes_lugtrabajo` (
  `idEvaluacionDonantes` bigint(19) unsigned NOT NULL,
  `idLugarTrabajo` bigint(19) unsigned NOT NULL,
  PRIMARY KEY (`idEvaluacionDonantes`,`idLugarTrabajo`),
  KEY `fk_Lugtrabajo_EvaluacionDonantes` (`idLugarTrabajo`),
  CONSTRAINT `fk_EvaluacionDonantes_Lugtrabajo` FOREIGN KEY (`idEvaluacionDonantes`) REFERENCES `tblevaluaciondonante` (`idEvaluacionDonante`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Lugtrabajo_EvaluacionDonantes` FOREIGN KEY (`idLugarTrabajo`) REFERENCES `tbllugtrabajo` (`idLugarTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblevaluaciondonantes_has_tbllugtrabajo_tblevaluaciondonan1` FOREIGN KEY (`idEvaluacionDonantes`) REFERENCES `tblevaluaciondonantes` (`idEvaluacionDonantes`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblevaluaciondonantes_has_tbllugtrabajo_tbllugtrabajo1` FOREIGN KEY (`idLugarTrabajo`) REFERENCES `tbllugtrabajo` (`idLugarTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblevaluaciondonantes_lugtrabajo`
--

LOCK TABLES `tblevaluaciondonantes_lugtrabajo` WRITE;
/*!40000 ALTER TABLE `tblevaluaciondonantes_lugtrabajo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblevaluaciondonantes_lugtrabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblmovimiento`
--

DROP TABLE IF EXISTS `tblmovimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblmovimiento` (
  `idMovimiento` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del movimiento.',
  `idArticulo` int(11) unsigned NOT NULL COMMENT 'Identificador del articulo.',
  `tipoMovimiento` tinyint(1) NOT NULL COMMENT 'Indica el tipo de movimiento realizado. si fue entrada o salida.',
  `fecha` datetime NOT NULL COMMENT 'Fecha en la que se realizó el movimiento.',
  `concepto` text COMMENT 'Concepto o razón por la cual se realiza el movimiento.',
  PRIMARY KEY (`idMovimiento`),
  KEY `FK_Articulo_Movimiento` (`idArticulo`),
  CONSTRAINT `FK_Articulo_Movimiento` FOREIGN KEY (`idArticulo`) REFERENCES `tblarticulo` (`idArticulo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblmovimiento`
--

LOCK TABLES `tblmovimiento` WRITE;
/*!40000 ALTER TABLE `tblmovimiento` DISABLE KEYS */;
INSERT INTO `tblmovimiento` VALUES (1,2,1,'2011-11-26 16:36:37','nbnbbbnbnb'),(2,2,1,'2011-11-26 16:37:47','entrada'),(3,2,0,'2011-11-26 16:49:10','utilizar para examen');
/*!40000 ALTER TABLE `tblmovimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblasignarseccion`
--

DROP TABLE IF EXISTS `tblasignarseccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblasignarseccion` (
  `idPlanificacionTrabajo` bigint(19) unsigned NOT NULL,
  `idSeccionLaboratorio` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`idPlanificacionTrabajo`,`idSeccionLaboratorio`),
  KEY `fk_tblplanificaciontrabajo_has_tblseccion_tblseccion1` (`idSeccionLaboratorio`),
  CONSTRAINT `fk_tblplanificaciontrabajo_has_tblseccion_tblplanificaciontra1` FOREIGN KEY (`idPlanificacionTrabajo`) REFERENCES `tblplanificaciontrabajo` (`idPlanificacionTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblplanificaciontrabajo_has_tblseccion_tblseccion1` FOREIGN KEY (`idSeccionLaboratorio`) REFERENCES `tblseccion` (`idSeccionLaboratorio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblasignarseccion`
--

LOCK TABLES `tblasignarseccion` WRITE;
/*!40000 ALTER TABLE `tblasignarseccion` DISABLE KEYS */;
INSERT INTO `tblasignarseccion` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,8),(2,8);
/*!40000 ALTER TABLE `tblasignarseccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblinstitucion`
--

DROP TABLE IF EXISTS `tblinstitucion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblinstitucion` (
  `idInstitucion` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la institución.',
  `nombre` varchar(254) NOT NULL COMMENT 'Nombre de la institución.',
  `telefono` varchar(9) DEFAULT NULL COMMENT 'Telefono de la institución.',
  `direccion` text COMMENT 'Dirección de la institución.',
  PRIMARY KEY (`idInstitucion`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblinstitucion`
--

LOCK TABLES `tblinstitucion` WRITE;
/*!40000 ALTER TABLE `tblinstitucion` DISABLE KEYS */;
INSERT INTO `tblinstitucion` VALUES (1,'zona franca el pedregal','2345-6789','col. las cañas'),(2,'Centro penal zacatraz','2534-6789','calle a zacatecoluca, el beneficio'),(3,'ISSS, Zacatecoluca','2367-8909','colonia las peñas, barrio san juan'),(4,'zona franca, san luis','2378-5432','calle al estadio, col. las brisas ');
/*!40000 ALTER TABLE `tblinstitucion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcontrolcalidad_produccion`
--

DROP TABLE IF EXISTS `tblcontrolcalidad_produccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcontrolcalidad_produccion` (
  `idControlCalidad` int(11) NOT NULL COMMENT 'Identificador del control de calidad.',
  `idParametro` int(11) unsigned NOT NULL,
  `valor` varchar(45) NOT NULL,
  `fechaRegistro` datetime NOT NULL,
  PRIMARY KEY (`idParametro`,`idControlCalidad`),
  KEY `fk_tblcontrolcalidad_produccion_controlcalidad` (`idControlCalidad`),
  KEY `fk_tblcontrolcalidad_produccion_parametro` (`idParametro`),
  CONSTRAINT `fk_tblcontrolcalidad_produccion_controlcalidad` FOREIGN KEY (`idControlCalidad`) REFERENCES `tblcontrolcalidad` (`idControlCalidad`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblcontrolcalidad_produccion_parametro` FOREIGN KEY (`idParametro`) REFERENCES `tblparametro` (`idParametro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcontrolcalidad_produccion`
--

LOCK TABLES `tblcontrolcalidad_produccion` WRITE;
/*!40000 ALTER TABLE `tblcontrolcalidad_produccion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblcontrolcalidad_produccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblorden_incidencia`
--

DROP TABLE IF EXISTS `tblorden_incidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblorden_incidencia` (
  `idIncidencia` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la incidencia.',
  `idExamenOrden` bigint(19) unsigned NOT NULL,
  KEY `FK_Orden_Incidencia_Incidencia` (`idIncidencia`),
  KEY `FK_Examen_Orden_Orden_Incidencia` (`idExamenOrden`),
  CONSTRAINT `FK_Examen_Orden_Orden_Incidencia` FOREIGN KEY (`idExamenOrden`) REFERENCES `tblexamen_orden` (`idExamenOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Orden_Incidencia_Incidencia` FOREIGN KEY (`idIncidencia`) REFERENCES `tblincidencia` (`idIncidencia`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblorden_incidencia`
--

LOCK TABLES `tblorden_incidencia` WRITE;
/*!40000 ALTER TABLE `tblorden_incidencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblorden_incidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblusuario`
--

DROP TABLE IF EXISTS `tblusuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblusuario` (
  `idUsuario` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del usuario.',
  `codigoMarcacion` smallint(5) unsigned NOT NULL COMMENT 'Código asignado por el departamento de recursos humanos para identificar a los empleados.',
  `login` varchar(60) NOT NULL COMMENT 'Nombre de inicio de sesión del usuario.',
  `pass` varchar(254) NOT NULL COMMENT 'Contraseña del usuario.',
  `estado` varchar(10) NOT NULL COMMENT 'Estado del usuario en el sistema, puede ser activo o inactivo.\r\n            ',
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `codigoMarcacion_UNIQUE` (`codigoMarcacion`),
  KEY `FK_Empleado_Usuario` (`codigoMarcacion`),
  CONSTRAINT `FK_Empleado_Usuario` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblusuario`
--

LOCK TABLES `tblusuario` WRITE;
/*!40000 ALTER TABLE `tblusuario` DISABLE KEYS */;
INSERT INTO `tblusuario` VALUES (1,1,'admin','admin','activo'),(2,17,'marin','maumarin','Activo'),(3,2,'jose','12345678','Activo'),(4,455,'fercho','w6ca86yn','Activo');
/*!40000 ALTER TABLE `tblusuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcriterio`
--

DROP TABLE IF EXISTS `tblcriterio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcriterio` (
  `idCriterio` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del criterio.',
  `idCuestionario` int(11) unsigned NOT NULL COMMENT 'Identificador del cuestionario.',
  `idRespuestaCuestionario` bigint(19) unsigned NOT NULL,
  `criterio` text NOT NULL COMMENT 'Texto del criterio',
  `tiempoExclusion` int(11) NOT NULL COMMENT 'Tiempo de exclusión en caso que el donante no cumpla con el criterio.',
  `observaciones` text COMMENT 'Observaciones o comentarios sobre el criterio.',
  PRIMARY KEY (`idCriterio`),
  KEY `FK_CuestionarioEvaluacion_Criterio` (`idCuestionario`),
  KEY `FK_RespuestaCuestionario_ResupuestaCriterio` (`idRespuestaCuestionario`),
  CONSTRAINT `FK_CuestionarioEvaluacion_Criterio` FOREIGN KEY (`idCuestionario`) REFERENCES `tblcuestionarioevaluacion` (`idCuestionario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_RespuestaCuestionario_ResupuestaCriterio` FOREIGN KEY (`idRespuestaCuestionario`) REFERENCES `tblrespuestacuestionario` (`idRespuesta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcriterio`
--

LOCK TABLES `tblcriterio` WRITE;
/*!40000 ALTER TABLE `tblcriterio` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblcriterio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblrol`
--

DROP TABLE IF EXISTS `tblrol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblrol` (
  `idRol` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del rol.',
  `nombreRol` varchar(25) NOT NULL COMMENT 'Nombre del rol.',
  PRIMARY KEY (`idRol`),
  UNIQUE KEY `nombreRol_UNIQUE` (`nombreRol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblrol`
--

LOCK TABLES `tblrol` WRITE;
/*!40000 ALTER TABLE `tblrol` DISABLE KEYS */;
INSERT INTO `tblrol` VALUES (1,'Administrador'),(3,'Secretaria'),(2,'tecnico');
/*!40000 ALTER TABLE `tblrol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcuestionarioevaluacion`
--

DROP TABLE IF EXISTS `tblcuestionarioevaluacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcuestionarioevaluacion` (
  `idCuestionario` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del cuestionario.',
  `pregunta` text NOT NULL COMMENT 'Texto de la prengunta.',
  `numeroPregunta` int(11) NOT NULL COMMENT 'Numero correlativo de la pregunta.',
  PRIMARY KEY (`idCuestionario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcuestionarioevaluacion`
--

LOCK TABLES `tblcuestionarioevaluacion` WRITE;
/*!40000 ALTER TABLE `tblcuestionarioevaluacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblcuestionarioevaluacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblmedico`
--

DROP TABLE IF EXISTS `tblmedico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblmedico` (
  `idMedico` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del médico.',
  `idPersona` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la persona.',
  `codMedico` varchar(20) NOT NULL COMMENT 'Código del medico asignado por el ministerio de salud.',
  PRIMARY KEY (`idMedico`),
  UNIQUE KEY `codMedico_UNIQUE` (`codMedico`),
  KEY `FK_Persona_Medico` (`idPersona`),
  CONSTRAINT `FK_Persona_Medico` FOREIGN KEY (`idPersona`) REFERENCES `tblpersona` (`idPersona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblmedico`
--

LOCK TABLES `tblmedico` WRITE;
/*!40000 ALTER TABLE `tblmedico` DISABLE KEYS */;
INSERT INTO `tblmedico` VALUES (1,2,'5041'),(2,3,'6042'),(3,183,'1015'),(4,184,'1131'),(5,185,'1283'),(6,186,'256');
/*!40000 ALTER TABLE `tblmedico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbldirector`
--

DROP TABLE IF EXISTS `tbldirector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbldirector` (
  `idDirector` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idPersona` bigint(19) unsigned NOT NULL,
  `idEstablecimiento` int(10) unsigned NOT NULL,
  `fechaInicio` datetime NOT NULL,
  `fechaFinal` datetime DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idDirector`),
  KEY `FK_Establecimiento_Director` (`idEstablecimiento`),
  KEY `FK_Persona_Director` (`idPersona`),
  CONSTRAINT `FK_Establecimiento_Director` FOREIGN KEY (`idEstablecimiento`) REFERENCES `tblestablecimiento` (`idEstablecimiento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Persona_Director` FOREIGN KEY (`idPersona`) REFERENCES `tblpersona` (`idPersona`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbldirector`
--

LOCK TABLES `tbldirector` WRITE;
/*!40000 ALTER TABLE `tbldirector` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbldirector` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblespecialidad`
--

DROP TABLE IF EXISTS `tblespecialidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblespecialidad` (
  `idEspecialidad` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la especialidad.',
  `codigoEspecialidad` varchar(3) NOT NULL COMMENT 'Código de la especialidad.',
  `nombreEspecialidad` varchar(60) NOT NULL COMMENT 'Nombre de la especialidad.',
  PRIMARY KEY (`idEspecialidad`),
  UNIQUE KEY `nombreEspecialidad_UNIQUE` (`nombreEspecialidad`),
  UNIQUE KEY `codigoEspecialidad_UNIQUE` (`codigoEspecialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblespecialidad`
--

LOCK TABLES `tblespecialidad` WRITE;
/*!40000 ALTER TABLE `tblespecialidad` DISABLE KEYS */;
INSERT INTO `tblespecialidad` VALUES (1,'1','Cardiologia'),(3,'2','Pediagria'),(4,'3','Ginecologia'),(5,'4','Cirugia Hombres'),(6,'5','Cirugía Mujeres'),(8,'6','Cirugía Vascular'),(9,'7','Cirugía de torax');
/*!40000 ALTER TABLE `tblespecialidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblsolicitudcambioturno`
--

DROP TABLE IF EXISTS `tblsolicitudcambioturno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsolicitudcambioturno` (
  `idSolicitud` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la solicitud para cambio de turno.',
  `fechaSolicitud` datetime NOT NULL COMMENT 'Fecha en la que se realizó la solicitud.',
  `motivo` text NOT NULL COMMENT 'Motivo por el cual se solicita el cambio de turno.',
  `tiempoSolicitado` int(11) NOT NULL COMMENT 'Tiempo que el empleado solicita para realizar el cambio.',
  `fechaSolicitada` datetime NOT NULL COMMENT 'Fecha solicitada por el empleado para ser intercambiada.',
  `horaInicioSolicitada` datetime DEFAULT NULL COMMENT 'Inicio de la jornada que desea intercambiar el empleado.',
  `horaFinalSolicitada` datetime DEFAULT NULL COMMENT 'Hora en que finaliza la jornada que desea intercambiar el empleado.',
  `tiempoResposicion` int(11) NOT NULL COMMENT 'Tiempo que repondra el empleado por le cambio realizado.',
  `fechaReposicion` datetime NOT NULL COMMENT 'Fecha en la que se realizará la reposición.',
  `horaInicioReposicion` datetime DEFAULT NULL COMMENT 'Hora en que da inicio la jornada que será repuesta.',
  `horaFinalReposicion` datetime NOT NULL COMMENT 'Hora en la que finaliza la jornada que será repuesta.',
  `servicioReposicion` varchar(50) NOT NULL COMMENT 'Servicio o labor que repondrá el empleado.',
  PRIMARY KEY (`idSolicitud`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblsolicitudcambioturno`
--

LOCK TABLES `tblsolicitudcambioturno` WRITE;
/*!40000 ALTER TABLE `tblsolicitudcambioturno` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblsolicitudcambioturno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblinstitucionservicio_bancosangre`
--

DROP TABLE IF EXISTS `tblinstitucionservicio_bancosangre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblinstitucionservicio_bancosangre` (
  `idServicio` int(11) NOT NULL COMMENT 'Identificador del servicio.',
  `idBancoSangre` bigint(19) unsigned NOT NULL COMMENT 'Identificador del banco de sangre.',
  PRIMARY KEY (`idServicio`,`idBancoSangre`),
  KEY `FK_BancoSangre_InstittucionServicio` (`idBancoSangre`),
  CONSTRAINT `FK_BancoSangre_InstittucionServicio` FOREIGN KEY (`idBancoSangre`) REFERENCES `tblbancosangre` (`idBancoSangre`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_InstitucionServicio_BancoSangre_InstitucionServicio` FOREIGN KEY (`idServicio`) REFERENCES `tblinstitucionservicio` (`idServicio`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblinstitucionservicio_bancosangre`
--

LOCK TABLES `tblinstitucionservicio_bancosangre` WRITE;
/*!40000 ALTER TABLE `tblinstitucionservicio_bancosangre` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblinstitucionservicio_bancosangre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblusuario_rol`
--

DROP TABLE IF EXISTS `tblusuario_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblusuario_rol` (
  `idUsuario` smallint(5) unsigned NOT NULL COMMENT 'Identificador del usuario.',
  `idRol` smallint(5) unsigned NOT NULL COMMENT 'Identificador del rol.',
  PRIMARY KEY (`idUsuario`,`idRol`),
  KEY `FK_Usuario_Rol_Rol` (`idRol`),
  CONSTRAINT `FK_Usuario_Rol_Rol` FOREIGN KEY (`idRol`) REFERENCES `tblrol` (`idRol`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Usuario_Rol_Usuario` FOREIGN KEY (`idUsuario`) REFERENCES `tblusuario` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblusuario_rol`
--

LOCK TABLES `tblusuario_rol` WRITE;
/*!40000 ALTER TABLE `tblusuario_rol` DISABLE KEYS */;
INSERT INTO `tblusuario_rol` VALUES (1,1),(2,1),(3,1),(4,1),(4,2);
/*!40000 ALTER TABLE `tblusuario_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblentregaresultados_incidencia`
--

DROP TABLE IF EXISTS `tblentregaresultados_incidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblentregaresultados_incidencia` (
  `idEntregaResultado` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la entrega de resultados.',
  `idIncidencia` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la incidencia.',
  PRIMARY KEY (`idEntregaResultado`,`idIncidencia`),
  KEY `FK_EntregaResultados_Incidencia` (`idIncidencia`),
  CONSTRAINT `FK_EntregaResultados_Incidencia` FOREIGN KEY (`idIncidencia`) REFERENCES `tblincidencia` (`idIncidencia`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_EntregaResultados_Incidencia_EntregaResultados` FOREIGN KEY (`idEntregaResultado`) REFERENCES `tblentregaresultado` (`idEntregaResultado`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblentregaresultados_incidencia`
--

LOCK TABLES `tblentregaresultados_incidencia` WRITE;
/*!40000 ALTER TABLE `tblentregaresultados_incidencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblentregaresultados_incidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblpaciente`
--

DROP TABLE IF EXISTS `tblpaciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblpaciente` (
  `idPaciente` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del paciente.',
  `idPersona` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la persona.',
  `numExpediente` varchar(15) NOT NULL COMMENT 'Numero de expediente del paciente. Se usará el número de expediente del lugar de donde proceda el paciente.',
  PRIMARY KEY (`idPaciente`),
  KEY `FK_Persona_Paciente` (`idPersona`),
  CONSTRAINT `FK_Persona_Paciente` FOREIGN KEY (`idPersona`) REFERENCES `tblpersona` (`idPersona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblpaciente`
--

LOCK TABLES `tblpaciente` WRITE;
/*!40000 ALTER TABLE `tblpaciente` DISABLE KEYS */;
INSERT INTO `tblpaciente` VALUES (2,8,'123'),(5,11,'234'),(6,12,'456'),(10,16,'567'),(133,141,'56748-9'),(134,142,'56748-9'),(135,143,'4567'),(136,144,'6789-0'),(141,149,'4567'),(157,165,'9870'),(161,169,'4567'),(162,170,'1-234234-3'),(163,171,'5678'),(164,172,'mauricio'),(167,175,'124354565'),(168,176,'45345'),(169,177,'56789'),(170,178,'7896'),(171,179,'456'),(172,180,'123400'),(173,181,'78076'),(174,182,'78456'),(175,187,'123'),(176,188,'34-567'),(177,189,'8956'),(178,190,'223-567'),(179,191,'21212'),(180,192,'8976'),(181,194,'666'),(183,196,'3234');
/*!40000 ALTER TABLE `tblpaciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblsueldoempleado`
--

DROP TABLE IF EXISTS `tblsueldoempleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsueldoempleado` (
  `idSueldoEmpleado` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del sueldo asignado al empleado.',
  `idPuestoEmpleado` int(11) unsigned NOT NULL COMMENT 'Identificador del puesto asignado al empleado.',
  `fechaAsignacion` datetime NOT NULL COMMENT 'Fecha en la que se asigno el nuevo sueldo.',
  `sueldo` double NOT NULL COMMENT 'Sueldo asignado al empleado.',
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idSueldoEmpleado`),
  KEY `FK_PuestoEmpleado_SueldoEmpleado` (`idPuestoEmpleado`),
  CONSTRAINT `FK_PuestoEmpleado_SueldoEmpleado` FOREIGN KEY (`idPuestoEmpleado`) REFERENCES `tblpuestoempleado` (`idPuestoEmpleado`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblsueldoempleado`
--

LOCK TABLES `tblsueldoempleado` WRITE;
/*!40000 ALTER TABLE `tblsueldoempleado` DISABLE KEYS */;
INSERT INTO `tblsueldoempleado` VALUES (1,1,'2011-11-01 00:00:00',650,0),(2,2,'2011-11-22 00:00:00',295,1),(3,3,'2011-11-09 00:00:00',800,0),(4,4,'2011-12-02 00:00:00',350,0),(5,5,'2011-12-07 00:00:00',1,0),(6,5,'2011-12-07 00:00:00',450,1);
/*!40000 ALTER TABLE `tblsueldoempleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblmodalidad`
--

DROP TABLE IF EXISTS `tblmodalidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblmodalidad` (
  `idModalidad` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la modalidad.',
  `nombreModalidad` varchar(60) NOT NULL COMMENT 'Nombe de la modalidad.',
  PRIMARY KEY (`idModalidad`),
  UNIQUE KEY `nombreModalidad_UNIQUE` (`nombreModalidad`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblmodalidad`
--

LOCK TABLES `tblmodalidad` WRITE;
/*!40000 ALTER TABLE `tblmodalidad` DISABLE KEYS */;
INSERT INTO `tblmodalidad` VALUES (1,'Hospital'),(2,'Seguro Social');
/*!40000 ALTER TABLE `tblmodalidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblservicio`
--

DROP TABLE IF EXISTS `tblservicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblservicio` (
  `idServicio` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del servicio',
  `idTipoServicio` smallint(5) unsigned NOT NULL COMMENT 'Identificador del tipo de servicio.',
  `codigoServicio` varchar(3) NOT NULL COMMENT 'Código del servicio',
  `nombreServicio` varchar(60) NOT NULL COMMENT 'Nombre del servicio.',
  PRIMARY KEY (`idServicio`),
  UNIQUE KEY `codigoServicio_UNIQUE` (`codigoServicio`),
  KEY `FK_TipoServicio_Servicio` (`idTipoServicio`),
  CONSTRAINT `FK_TipoServicio_Servicio` FOREIGN KEY (`idTipoServicio`) REFERENCES `tbltiposervicio` (`idTipoServicio`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblservicio`
--

LOCK TABLES `tblservicio` WRITE;
/*!40000 ALTER TABLE `tblservicio` DISABLE KEYS */;
INSERT INTO `tblservicio` VALUES (1,1,'1','Medicina Hombres'),(2,1,'2','Medicina Mujeres'),(3,6,'3','Unidad de Salud periferica'),(4,4,'14','examen orina'),(7,4,'32','examen de sangre'),(9,1,'10','Cirugia Hombres'),(10,1,'9','Cirugia Mujeres');
/*!40000 ALTER TABLE `tblservicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblempleado_capacitacion`
--

DROP TABLE IF EXISTS `tblempleado_capacitacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblempleado_capacitacion` (
  `codigoMarcacion` smallint(5) unsigned NOT NULL,
  `idCapacitacion` bigint(19) unsigned NOT NULL,
  `tipo` varchar(25) NOT NULL,
  PRIMARY KEY (`codigoMarcacion`,`idCapacitacion`),
  KEY `fk_tblempleado_capacitacion_tblcapacitacion` (`idCapacitacion`),
  CONSTRAINT `fk_tblempleado_capacitacion_tblcapacitacion` FOREIGN KEY (`idCapacitacion`) REFERENCES `tblcapacitacioncontinua` (`idCapacitacion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblempleado_capacitacion_tblempleado` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblempleado_capacitacion`
--

LOCK TABLES `tblempleado_capacitacion` WRITE;
/*!40000 ALTER TABLE `tblempleado_capacitacion` DISABLE KEYS */;
INSERT INTO `tblempleado_capacitacion` VALUES (2,1,'Receptor'),(17,1,'Receptor');
/*!40000 ALTER TABLE `tblempleado_capacitacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbltiporesultado`
--

DROP TABLE IF EXISTS `tbltiporesultado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbltiporesultado` (
  `idTipoResultado` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del tipo de resultado.',
  `tipoResultado` smallint(5) unsigned NOT NULL COMMENT 'Número que identifica al tipo de resultado, según los catálogos de pruebas del Ministerio de Salud.',
  `nombreResultado` varchar(25) NOT NULL COMMENT 'Nombre del resultado.',
  PRIMARY KEY (`idTipoResultado`),
  UNIQUE KEY `tipoResultado_UNIQUE` (`tipoResultado`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbltiporesultado`
--

LOCK TABLES `tbltiporesultado` WRITE;
/*!40000 ALTER TABLE `tbltiporesultado` DISABLE KEYS */;
INSERT INTO `tbltiporesultado` VALUES (1,1,'Normal'),(2,2,'Negativo'),(3,3,'Anormal'),(4,4,'Positivo'),(5,5,'Muestra insuficiente'),(6,6,'Muestra en mal estado'),(7,7,'No se realizó'),(8,8,'Otro');
/*!40000 ALTER TABLE `tbltiporesultado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblprivilegio`
--

DROP TABLE IF EXISTS `tblprivilegio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblprivilegio` (
  `idPrivilegio` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del privilegio.',
  `idObjeto` smallint(5) unsigned NOT NULL COMMENT 'Identificador del objeto.',
  `idRol` smallint(5) unsigned NOT NULL COMMENT 'Identificador del rol.',
  `permiso` tinyint(1) NOT NULL COMMENT 'Indica si el usuario tiene acceso al objeto.',
  PRIMARY KEY (`idPrivilegio`),
  KEY `FK_Objeto_Privilegio` (`idObjeto`),
  KEY `FK_Rol_Privilegio` (`idRol`),
  CONSTRAINT `FK_Objeto_Privilegio` FOREIGN KEY (`idObjeto`) REFERENCES `tblobjeto` (`idObjeto`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Rol_Privilegio` FOREIGN KEY (`idRol`) REFERENCES `tblrol` (`idRol`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblprivilegio`
--

LOCK TABLES `tblprivilegio` WRITE;
/*!40000 ALTER TABLE `tblprivilegio` DISABLE KEYS */;
INSERT INTO `tblprivilegio` VALUES (1,1,1,1),(2,2,1,1),(3,3,1,1),(4,4,1,1),(5,5,1,1),(6,6,1,1),(7,7,1,1),(8,8,1,1),(9,9,1,1),(10,10,1,1),(11,11,1,1),(12,12,1,1),(13,13,1,1),(14,14,1,1),(15,15,1,1),(16,16,1,1),(17,17,1,1),(18,18,1,1),(19,19,1,1),(20,20,1,1),(21,21,1,1),(22,22,1,1),(23,23,1,1),(24,24,1,1),(25,25,1,1),(26,26,1,1),(27,27,1,1),(28,28,1,1),(29,29,1,1),(30,30,1,1),(31,31,1,1),(32,32,1,1),(33,33,1,1),(34,34,1,1),(35,35,1,1),(36,1,2,0),(37,34,2,0),(38,35,2,0),(39,2,2,0),(40,38,2,0),(41,3,2,0),(42,28,2,0),(43,29,2,0),(44,30,2,0),(45,31,2,0),(46,32,2,0),(47,33,2,0),(48,4,2,0),(49,21,2,0),(50,22,2,0),(51,23,2,0),(52,24,2,0),(53,25,2,0),(54,26,2,0),(55,27,2,0),(56,5,2,0),(57,7,2,0),(58,8,2,0),(59,9,2,0),(60,10,2,0),(61,11,2,0),(62,12,2,0),(63,13,2,0),(64,14,2,0),(65,15,2,0),(66,16,2,0),(67,6,2,0),(68,17,2,0),(69,18,2,0),(70,19,2,0),(71,20,2,0),(72,38,1,0),(73,39,1,1),(74,40,1,1),(75,41,1,0),(76,42,1,1),(77,43,1,1),(78,44,1,1);
/*!40000 ALTER TABLE `tblprivilegio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblantecendentesdonante`
--

DROP TABLE IF EXISTS `tblantecendentesdonante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblantecendentesdonante` (
  `idAntecedentesDonantes` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de antecentes del donante.',
  `idEvaluacionDonantes` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la evaluación del donante.',
  `totalDonaciones` int(11) NOT NULL COMMENT 'Total de donaciones realizadas por el donante con anterioridad.',
  `donacionesAnuales` int(11) NOT NULL COMMENT 'Número de veces que el donante realiza donaciones en el año.',
  `donantePrimerizo` tinyint(1) NOT NULL COMMENT 'Indica si el donante se somete por primera vez al proceso.',
  `donanteVoluntario` tinyint(1) NOT NULL COMMENT 'Indica si el donante es voluntario o realiza la donación a solicitud de otra persona.',
  `donanteHabitual` tinyint(1) NOT NULL COMMENT 'Indica si el donante realiza donaciones con regularidad, de forma altruista.',
  `lugarDonacion` varchar(254) NOT NULL COMMENT 'Lugar donde se realizará la donación.',
  PRIMARY KEY (`idAntecedentesDonantes`),
  UNIQUE KEY `idAntecedentesDonantes_UNIQUE` (`idAntecedentesDonantes`),
  UNIQUE KEY `idEvaluacionDonantes_UNIQUE` (`idEvaluacionDonantes`),
  KEY `FK_EvaluacionDonantes_AntecedentesDonante` (`idEvaluacionDonantes`),
  CONSTRAINT `FK_EvaluacionDonantes_AntecedentesDonante` FOREIGN KEY (`idEvaluacionDonantes`) REFERENCES `tblevaluaciondonante` (`idEvaluacionDonante`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblantecendentesdonante`
--

LOCK TABLES `tblantecendentesdonante` WRITE;
/*!40000 ALTER TABLE `tblantecendentesdonante` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblantecendentesdonante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblevaluaciondonante`
--

DROP TABLE IF EXISTS `tblevaluaciondonante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblevaluaciondonante` (
  `idEvaluacionDonante` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la evaluación del donante.',
  `idDonate` bigint(19) unsigned NOT NULL COMMENT 'Identificador del donante',
  `idPaciente` bigint(19) unsigned NOT NULL COMMENT 'Identificador del paciente.',
  `codigoMarcacion` smallint(5) unsigned NOT NULL,
  `fechaEvaluacion` datetime NOT NULL COMMENT 'Lugar donde se realiza la evaluación.',
  `lugarReferencia` varchar(254) DEFAULT NULL COMMENT 'Lugar que refiere al donante.',
  `observaciones` text COMMENT 'Observaciones o comentarios sobre la evaluación.',
  PRIMARY KEY (`idEvaluacionDonante`),
  KEY `FK_Paciente_EvaluacionDonante` (`idPaciente`),
  KEY `fk_Empleado_EvaluacionDonante` (`codigoMarcacion`),
  KEY `FK_Donante_EvaluacionDonante` (`idDonate`),
  CONSTRAINT `FK_Donante_EvaluacionDonante` FOREIGN KEY (`idDonate`) REFERENCES `tbldonante` (`idDonate`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empleado_EvaluacionDonante` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Paciente_EvaluacionDonante` FOREIGN KEY (`idPaciente`) REFERENCES `tblpaciente` (`idPaciente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblevaluaciondonante`
--

LOCK TABLES `tblevaluaciondonante` WRITE;
/*!40000 ALTER TABLE `tblevaluaciondonante` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblevaluaciondonante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbltransfusion_lugtrabajo`
--

DROP TABLE IF EXISTS `tbltransfusion_lugtrabajo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbltransfusion_lugtrabajo` (
  `idLugarTrabajo` bigint(19) unsigned NOT NULL,
  `idTransfusion` bigint(19) unsigned NOT NULL,
  PRIMARY KEY (`idLugarTrabajo`,`idTransfusion`),
  KEY `fk_tbltransfusion_has_tbllugtrabajo_tbllugtrabajo1` (`idLugarTrabajo`),
  KEY `fk_Transfusion_LugTrabajo` (`idTransfusion`),
  CONSTRAINT `fk_tbltransfusion_has_tbllugtrabajo_tbllugtrabajo1` FOREIGN KEY (`idLugarTrabajo`) REFERENCES `tbllugtrabajo` (`idLugarTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Transfusion_LugTrabajo` FOREIGN KEY (`idTransfusion`) REFERENCES `tbltransfusion` (`idTransfusion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbltransfusion_lugtrabajo`
--

LOCK TABLES `tbltransfusion_lugtrabajo` WRITE;
/*!40000 ALTER TABLE `tbltransfusion_lugtrabajo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbltransfusion_lugtrabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblorden_produccion`
--

DROP TABLE IF EXISTS `tblorden_produccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblorden_produccion` (
  `idOrden` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la orden.',
  `idProduccion` bigint(19) unsigned NOT NULL COMMENT 'Identificador del resultado.',
  PRIMARY KEY (`idOrden`,`idProduccion`),
  KEY `FK_Orden_Produccion_Produccion` (`idProduccion`),
  CONSTRAINT `FK_Orden_Produccion_Orden` FOREIGN KEY (`idOrden`) REFERENCES `tblorden` (`idOrden`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Orden_Produccion_Produccion` FOREIGN KEY (`idProduccion`) REFERENCES `tblproduccion` (`idProduccion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblorden_produccion`
--

LOCK TABLES `tblorden_produccion` WRITE;
/*!40000 ALTER TABLE `tblorden_produccion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblorden_produccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblseccion`
--

DROP TABLE IF EXISTS `tblseccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblseccion` (
  `idSeccionLaboratorio` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la sección.',
  `nombreSeccion` varchar(50) NOT NULL COMMENT 'Nombre de la sección de laboratorio.',
  `codigoSeccion` varchar(5) NOT NULL,
  `reinicioNumeroControl` varchar(25) NOT NULL,
  PRIMARY KEY (`idSeccionLaboratorio`),
  UNIQUE KEY `nombreSeccion_UNIQUE` (`nombreSeccion`),
  UNIQUE KEY `codigoSeccion_UNIQUE` (`codigoSeccion`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblseccion`
--

LOCK TABLES `tblseccion` WRITE;
/*!40000 ALTER TABLE `tblseccion` DISABLE KEYS */;
INSERT INTO `tblseccion` VALUES (1,'Hematologia','1','Quincenal'),(2,'Bactereologia','2','Quincenal'),(3,'Urianalisis','3','Quincenal'),(4,'Coprologia','4','Quincenal'),(5,'Inmunología','7','Diario'),(6,'Quimica','8','Diario'),(8,'Banco de Sangre','9','Diario');
/*!40000 ALTER TABLE `tblseccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblautoexcluido`
--

DROP TABLE IF EXISTS `tblautoexcluido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblautoexcluido` (
  `idDonanteAutoExcluido` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del donante excluido.',
  `idDonate` bigint(19) unsigned NOT NULL COMMENT 'Identificador del donante',
  `fecha` datetime NOT NULL COMMENT 'Fecha en la que se realiza la exclusión.',
  `confirmacion` tinyint(1) NOT NULL COMMENT 'Indica si el donante ha confirmado por escrito su decisión.',
  `numeroBolsa` int(11) NOT NULL COMMENT 'Numero de la bolsa que el donante habia llenado con anterioridad y que será excluida.',
  PRIMARY KEY (`idDonanteAutoExcluido`),
  KEY `FK_Donante_AutoExcluido` (`idDonate`),
  CONSTRAINT `FK_Donante_AutoExcluido` FOREIGN KEY (`idDonate`) REFERENCES `tbldonante` (`idDonate`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblautoexcluido`
--

LOCK TABLES `tblautoexcluido` WRITE;
/*!40000 ALTER TABLE `tblautoexcluido` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblautoexcluido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblparametro`
--

DROP TABLE IF EXISTS `tblparametro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblparametro` (
  `idParametro` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del parametro.',
  `idExamen` bigint(19) unsigned NOT NULL COMMENT 'Identificador del examen.',
  `idUnidadMedida` smallint(5) unsigned DEFAULT NULL COMMENT 'Identificador de la unidad de medida.',
  `nombreParametro` varchar(60) NOT NULL COMMENT 'Nombre del parametro.',
  `tipoParametro` varchar(15) NOT NULL COMMENT 'Tipo de parametro, puede ser (Valor, Logico o Descripción textual)',
  `valorMinimo` double NOT NULL COMMENT 'Valor mínimo esperado en la prueba.',
  `valorMaximo` double NOT NULL COMMENT 'Valor máximo esperado en la prueba.',
  `valorNormal` double NOT NULL COMMENT 'Valor normal esperado en la prueba.',
  `fijo` tinyint(1) NOT NULL COMMENT 'Indica si el parametro es fijo o variable para la prueba. Fijo indica que aparecera en la captura y en el resultado, variable indica que queda a opción del técnico su inclusión.',
  PRIMARY KEY (`idParametro`),
  KEY `FK_Examen_Parametros` (`idExamen`),
  KEY `FK_UnidadMedida_Parametros` (`idUnidadMedida`),
  CONSTRAINT `FK_Examen_Parametros` FOREIGN KEY (`idExamen`) REFERENCES `tblexamen` (`idExamen`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_UnidadMedida_Parametros` FOREIGN KEY (`idUnidadMedida`) REFERENCES `tblunidadmedida` (`idUnidadMedida`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblparametro`
--

LOCK TABLES `tblparametro` WRITE;
/*!40000 ALTER TABLE `tblparametro` DISABLE KEYS */;
INSERT INTO `tblparametro` VALUES (1,1,6,'Cantidad de globulos rojos','Valor',1000,6000,5000,1),(2,2,NULL,'Densidad','Texto',0,0,0,1),(3,3,NULL,'Color','Texto',0,0,0,0),(4,3,NULL,'Consistencia','Texto',0,0,0,1),(5,4,2,'Biobacterias','Valor',23.44,29.78,23,0),(6,5,NULL,'bacterias fecales','Logico',0,0,0,1);
/*!40000 ALTER TABLE `tblparametro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblunidadmedida`
--

DROP TABLE IF EXISTS `tblunidadmedida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblunidadmedida` (
  `idUnidadMedida` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la unidad de medida.',
  `unidadMedida` varchar(25) NOT NULL COMMENT 'Nombre de la unidad de medida usada.',
  `abreviatura` varchar(5) DEFAULT NULL COMMENT 'Abreviatura que representa la unidad de medida.',
  PRIMARY KEY (`idUnidadMedida`),
  UNIQUE KEY `unidadMedida_UNIQUE` (`unidadMedida`),
  UNIQUE KEY `abreviatura_UNIQUE` (`abreviatura`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblunidadmedida`
--

LOCK TABLES `tblunidadmedida` WRITE;
/*!40000 ALTER TABLE `tblunidadmedida` DISABLE KEYS */;
INSERT INTO `tblunidadmedida` VALUES (1,'Litros','L'),(2,'Mililitros','ml'),(3,'Decilitros','dl'),(4,'Centrimetro','cm'),(5,'Centrimetro cubico','cc'),(6,'Unidad','u'),(7,'Pieza','p');
/*!40000 ALTER TABLE `tblunidadmedida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbltransfusion`
--

DROP TABLE IF EXISTS `tbltransfusion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbltransfusion` (
  `idTransfusion` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la transfusión.',
  `idDonate` bigint(19) unsigned NOT NULL COMMENT 'Identificador del donante',
  `idBancoSangre` bigint(19) unsigned NOT NULL COMMENT 'Identificador del banco de sangre.',
  `idPaciente` bigint(19) unsigned NOT NULL COMMENT 'Identificador del paciente.',
  `codigoMarcacion` smallint(5) unsigned NOT NULL,
  `tipComponenteSanguineo` varchar(25) NOT NULL COMMENT 'Tipo del componente a transfundir (Sangre completa, globulos rojos, plasma, etc.)',
  `responsableRegistro` varchar(60) NOT NULL COMMENT 'Persona encargada que realizo el registro de la transfusión.',
  `fechaTransfusion` datetime NOT NULL COMMENT 'Fecha en la que se realizo la transfusión.',
  PRIMARY KEY (`idTransfusion`),
  KEY `FK_Paciente_Transfusion` (`idPaciente`),
  KEY `fk_tbltransfusion_tblempleado` (`codigoMarcacion`),
  KEY `FK_BancoSangre_Transfusion` (`idBancoSangre`),
  KEY `FK_Donante_Transfusion` (`idDonate`),
  CONSTRAINT `FK_BancoSangre_Transfusion` FOREIGN KEY (`idBancoSangre`) REFERENCES `tblbancosangre` (`idBancoSangre`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Donante_Transfusion` FOREIGN KEY (`idDonate`) REFERENCES `tbldonante` (`idDonate`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Paciente_Transfusion` FOREIGN KEY (`idPaciente`) REFERENCES `tblpaciente` (`idPaciente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbltransfusion_tblempleado` FOREIGN KEY (`codigoMarcacion`) REFERENCES `tblempleado` (`codigoMarcacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbltransfusion`
--

LOCK TABLES `tbltransfusion` WRITE;
/*!40000 ALTER TABLE `tbltransfusion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbltransfusion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblempleado`
--

DROP TABLE IF EXISTS `tblempleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblempleado` (
  `codigoMarcacion` smallint(5) unsigned NOT NULL COMMENT 'Código asignado por el departamento de recursos humanos para identificar a los empleados.',
  `idPersona` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la persona.',
  `fechaIngreso` datetime NOT NULL COMMENT 'Fecha en que el empleado empezo a laborar en el hospital.',
  `nombreAfp` varchar(50) DEFAULT NULL COMMENT 'Nombre de la AFP a la que se encuentra afiliado el empleado en la actualidad.',
  `nup` bigint(20) DEFAULT NULL COMMENT 'Número único previsional del empleado',
  `isss` bigint(20) DEFAULT NULL COMMENT 'Número de Seguro Social del empleado.',
  `codigoProfesional` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`codigoMarcacion`),
  UNIQUE KEY `idPersona_UNIQUE` (`idPersona`),
  KEY `FK_Persona_Empleado` (`idPersona`),
  CONSTRAINT `FK_Persona_Empleado` FOREIGN KEY (`idPersona`) REFERENCES `tblpersona` (`idPersona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblempleado`
--

LOCK TABLES `tblempleado` WRITE;
/*!40000 ALTER TABLE `tblempleado` DISABLE KEYS */;
INSERT INTO `tblempleado` VALUES (1,1,'2011-11-23 22:48:55',NULL,NULL,NULL,NULL),(2,6,'2011-11-02 00:00:00','123456',123456,123456,NULL),(17,42,'2011-11-11 00:00:00','123456',52,33,NULL),(455,193,'2011-12-02 00:00:00','Confia',123456,1234567,NULL);
/*!40000 ALTER TABLE `tblempleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblpersona`
--

DROP TABLE IF EXISTS `tblpersona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblpersona` (
  `idPersona` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la persona.',
  `primerNombre` varchar(20) NOT NULL,
  `segundoNombre` varchar(20) DEFAULT NULL,
  `primerApellido` varchar(20) NOT NULL,
  `segundoApellido` varchar(20) DEFAULT NULL,
  `fechaNacimiento` datetime DEFAULT NULL COMMENT 'Fecha de nacimiento.',
  `sexo` char(1) DEFAULT NULL COMMENT 'sexo de la persona.',
  `tipoSangre` varchar(5) DEFAULT NULL COMMENT 'Tipo de sangre de la persona.',
  `tipoDocId` varchar(25) DEFAULT NULL COMMENT 'Tipo de documento de identidad.',
  `docId` varchar(30) DEFAULT NULL COMMENT 'Numero del documento de identidad.',
  `estadoCivil` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`idPersona`)
) ENGINE=InnoDB AUTO_INCREMENT=197 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblpersona`
--

LOCK TABLES `tblpersona` WRITE;
/*!40000 ALTER TABLE `tblpersona` DISABLE KEYS */;
INSERT INTO `tblpersona` VALUES (1,'admin',NULL,'admin',NULL,'2011-11-23 22:48:29','m',NULL,NULL,NULL,NULL),(2,'Raul','Antonio','Granillo','',NULL,NULL,NULL,NULL,NULL,NULL),(3,'Maria','Elena','de Granillo','',NULL,NULL,NULL,NULL,NULL,NULL),(6,'Jose','Santos','Mejia','Angel','1982-01-11 00:00:00','M',NULL,'DUI','02248141-8',NULL),(8,'Juan','','Valdez','','2011-11-05 00:00:00','M',NULL,'DUI','1234',NULL),(11,'Juan','','Valdez','','2011-11-05 00:00:00','M',NULL,NULL,NULL,NULL),(12,'Varlia','','Quintanilla','','2011-11-04 00:00:00','F',NULL,NULL,NULL,NULL),(16,'Valeria','','Quintanilla','','2011-11-04 00:00:00','F',NULL,NULL,NULL,NULL),(42,'mauricio','eduardo','marin','cruz','2011-11-25 00:00:00','M',NULL,'DUI','022661733',NULL),(100,'carlos','manuel','bonilla','ramirez',NULL,NULL,NULL,NULL,NULL,NULL),(141,'Edgardo','Amilcar','Merino','Morales','1995-11-02 00:00:00','M',NULL,NULL,NULL,NULL),(142,'Edgardo','Amilcar','Merino','Morales','1995-11-02 00:00:00','M',NULL,NULL,NULL,NULL),(143,'Ricardo','Ernesto','Paz','Quintanilla','2011-11-05 00:00:00','M',NULL,NULL,NULL,NULL),(144,'Edgardo','Amilcar','Merino','Constanza','1991-11-01 00:00:00','M',NULL,NULL,NULL,NULL),(149,'Daniel','Ernesto','Martinez','Sanchez','2011-11-05 00:00:00','M',NULL,'DUI','02248141-8',NULL),(165,'Maria','Isabel','Mejia','Angel','1984-05-30 00:00:00','F',NULL,NULL,NULL,NULL),(169,'Valeria','Alejandra','Quintanilla','Mejia','2007-11-06 00:00:00','F',NULL,NULL,NULL,NULL),(170,'patricia','iveth','morales','pichinte','1983-11-23 00:00:00','F',NULL,NULL,NULL,NULL),(171,'Efrain','Antonio','Mejia','Cornejo','1936-12-08 00:00:00','M',NULL,'DUI','02349234',NULL),(172,'eduardo','marin','marin','cruz','2001-11-02 00:00:00','M',NULL,NULL,NULL,NULL),(175,'ernesto','raul','velasques','muñoz','2001-11-06 00:00:00','M',NULL,NULL,NULL,NULL),(176,'Ana','Marina','Constanza','de Penate','1971-11-06 00:00:00','F',NULL,NULL,'',NULL),(177,'Ana','Alicia','Angel','Jimenez','2011-11-05 00:00:00','F',NULL,NULL,NULL,NULL),(178,'Saul','Ovideo','Henriquez','Mejia','1971-03-03 00:00:00','M',NULL,NULL,NULL,NULL),(179,'Goretti','Betzabe','Saavedra','de Henriquez','2011-11-05 00:00:00','F',NULL,NULL,NULL,NULL),(180,'marcos','antonio','guerrero','jaimes','2001-12-06 00:00:00','M',NULL,NULL,NULL,NULL),(181,'Raul','Edgardo','Guadron','Santin','2001-12-12 00:00:00','M',NULL,NULL,NULL,NULL),(182,'Juan ','Carlos','Arias','Alvarenga','1991-04-03 00:00:00','M',NULL,NULL,NULL,NULL),(183,'Miguel','Ernesto','Bolaños','Marquez',NULL,NULL,NULL,NULL,NULL,NULL),(184,'Jose','Isrrael','Melendez','Soriano',NULL,NULL,NULL,'DUI','5555555',NULL),(185,'Alfredo ','Heriberto','Ortiz','Mendez',NULL,NULL,NULL,NULL,NULL,NULL),(186,'Oscar','Renato','Marin','Rosales',NULL,NULL,NULL,NULL,NULL,NULL),(187,'sadasd','dasdasd','asdasds','asdasdad','2001-12-06 00:00:00','F',NULL,NULL,NULL,NULL),(188,'Cristobal','Eugenio','Santos','Chavez','2001-12-07 00:00:00','M',NULL,NULL,NULL,NULL),(189,'Petty ','Alrjandrina','Marin','Cruz','2002-12-10 00:00:00','F',NULL,NULL,NULL,NULL),(190,'Jonatan','Aurelio','Pichinte','Marroquin','2003-12-16 00:00:00','M',NULL,NULL,NULL,NULL),(191,'mark','antony','chavez','cosme','1991-12-05 00:00:00','M',NULL,NULL,NULL,NULL),(192,'luis ','eduardo','pereira','monje','2001-12-28 00:00:00','M',NULL,NULL,NULL,NULL),(193,'Fernando','Emerson','Ortiz','Baron','1982-12-08 00:00:00','M',NULL,'DUI','000000-2',NULL),(194,'Roberto','Abraham','Mendez','Climaco','1982-10-02 00:00:00','M',NULL,NULL,NULL,NULL),(196,'Jorge','Alberto','Rodriguez','Munguia','1982-10-02 00:00:00','M',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tblpersona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblunidadesdescartadas`
--

DROP TABLE IF EXISTS `tblunidadesdescartadas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblunidadesdescartadas` (
  `idUnidadesDescartadas` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de unidades descargadas.',
  `idBancoSangre` bigint(19) unsigned NOT NULL COMMENT 'Identificador del banco de sangre.',
  `fecha` datetime NOT NULL COMMENT 'Fecha en la que se realizó el descarte.',
  `motivo` text NOT NULL COMMENT 'Motivo o razón por la que se realizó el descarte.',
  PRIMARY KEY (`idUnidadesDescartadas`),
  KEY `FK_BancoSangre_UnidadesDescartadas` (`idBancoSangre`),
  CONSTRAINT `FK_BancoSangre_UnidadesDescartadas` FOREIGN KEY (`idBancoSangre`) REFERENCES `tblbancosangre` (`idBancoSangre`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblunidadesdescartadas`
--

LOCK TABLES `tblunidadesdescartadas` WRITE;
/*!40000 ALTER TABLE `tblunidadesdescartadas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblunidadesdescartadas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblexamen`
--

DROP TABLE IF EXISTS `tblexamen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblexamen` (
  `idExamen` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del examen.',
  `idSeccionLaboratorio` smallint(5) unsigned NOT NULL COMMENT 'Identificador de la sección.',
  `codigoExamen` varchar(4) NOT NULL COMMENT 'Código del examen.',
  `nombreExamen` varchar(120) NOT NULL COMMENT 'Nombre del examen.',
  PRIMARY KEY (`idExamen`),
  UNIQUE KEY `codigoExamen_UNIQUE` (`codigoExamen`),
  KEY `FK_Seccion_Examen` (`idSeccionLaboratorio`),
  CONSTRAINT `FK_Seccion_Examen` FOREIGN KEY (`idSeccionLaboratorio`) REFERENCES `tblseccion` (`idSeccionLaboratorio`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblexamen`
--

LOCK TABLES `tblexamen` WRITE;
/*!40000 ALTER TABLE `tblexamen` DISABLE KEYS */;
INSERT INTO `tblexamen` VALUES (1,1,'H01','Glucosa'),(2,3,'U01','Examen general de orina'),(3,4,'C01','General de heces'),(4,2,'M24','Antibiograma'),(5,4,'C2','examen de heces');
/*!40000 ALTER TABLE `tblexamen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbltiposervicio`
--

DROP TABLE IF EXISTS `tbltiposervicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbltiposervicio` (
  `idTipoServicio` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del tipo de servicio.',
  `idModalidad` smallint(5) unsigned NOT NULL COMMENT 'Identificador de la modalidad.',
  `codigoTipoServicio` varchar(3) NOT NULL COMMENT 'Código del tipo de servicio.',
  `nombreTipoServicio` varchar(60) NOT NULL COMMENT 'Nombre del tipo de servicio.',
  PRIMARY KEY (`idTipoServicio`),
  UNIQUE KEY `codigoTipoServicio_UNIQUE` (`codigoTipoServicio`),
  KEY `FK_Modalidad_TipoServicio` (`idModalidad`),
  CONSTRAINT `FK_Modalidad_TipoServicio` FOREIGN KEY (`idModalidad`) REFERENCES `tblmodalidad` (`idModalidad`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbltiposervicio`
--

LOCK TABLES `tbltiposervicio` WRITE;
/*!40000 ALTER TABLE `tbltiposervicio` DISABLE KEYS */;
INSERT INTO `tbltiposervicio` VALUES (1,1,'1','Hospitalizacion'),(2,1,'2','Emergencia'),(3,2,'3','Hospitalizacion'),(4,2,'4','Emergencia'),(6,1,'5','Consulta externa');
/*!40000 ALTER TABLE `tbltiposervicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcampo`
--

DROP TABLE IF EXISTS `tblcampo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcampo` (
  `idCampo` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador del campo.',
  `idTabla` int(11) NOT NULL COMMENT 'Identificador de la tabla.',
  `tipoDato` varchar(30) NOT NULL COMMENT 'Tipo de dato contenido en el campo.',
  `nombreCampo` varchar(60) NOT NULL COMMENT 'Nombre del campo.',
  `campoAsociado` varchar(60) NOT NULL COMMENT 'Campo asociado en el sistema.',
  PRIMARY KEY (`idCampo`),
  KEY `FK_Tabla_Campo` (`idTabla`),
  CONSTRAINT `FK_Tabla_Campo` FOREIGN KEY (`idTabla`) REFERENCES `tbltabla` (`idTabla`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcampo`
--

LOCK TABLES `tblcampo` WRITE;
/*!40000 ALTER TABLE `tblcampo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblcampo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblarticulo`
--

DROP TABLE IF EXISTS `tblarticulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblarticulo` (
  `idArticulo` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador del articulo.',
  `idCatalogo` smallint(5) unsigned NOT NULL COMMENT 'Identificador de catálogo.',
  `unidadMinimoUso` smallint(5) unsigned NOT NULL,
  `unidadMinimoCompra` smallint(5) unsigned NOT NULL,
  `codigoArticulo` varchar(15) NOT NULL COMMENT 'Código del articulo usado por el Almacén del Hospital.',
  `nombreArticulo` varchar(254) NOT NULL COMMENT 'Nombre del articulo.',
  `presentacion` varchar(25) NOT NULL COMMENT 'Forma en la que es distribuido el articulo (Bote, frasco, pastilla, solución, etc.)',
  `cantidadMinimaUso` double NOT NULL COMMENT 'Valor mínimo del articulo que debe exisitir en el inventario del laboratorio.',
  `cantidadMinimaCompra` double NOT NULL COMMENT 'Valor máximo que debe existir en el inventario del laboratorio.',
  `existencias` double NOT NULL DEFAULT '0',
  `perecedero` tinyint(1) NOT NULL,
  `factorConversion` double NOT NULL,
  `relacion` varchar(1) NOT NULL,
  PRIMARY KEY (`idArticulo`),
  KEY `FK_Catalago_Articulo` (`idCatalogo`),
  KEY `fk_tblarticulo_tblunidadmedida1` (`unidadMinimoUso`),
  KEY `fk_tblarticulo_tblunidadmedida2` (`unidadMinimoCompra`),
  CONSTRAINT `FK_Catalago_Articulo` FOREIGN KEY (`idCatalogo`) REFERENCES `tblcatalogo` (`idCatalogo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tblarticulo_tblunidadmedida1` FOREIGN KEY (`unidadMinimoUso`) REFERENCES `tblunidadmedida` (`idUnidadMedida`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblarticulo_tblunidadmedida2` FOREIGN KEY (`unidadMinimoCompra`) REFERENCES `tblunidadmedida` (`idUnidadMedida`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblarticulo`
--

LOCK TABLES `tblarticulo` WRITE;
/*!40000 ALTER TABLE `tblarticulo` DISABLE KEYS */;
INSERT INTO `tblarticulo` VALUES (1,2,7,7,'11111','Matraz de vidrio','Piezas',1,1,0,0,1,'/'),(2,1,6,6,'22222','Jeringas','Paquetes',1,1,30,1,1,'/'),(3,3,5,1,'33333','Azul de metileno','Frasco',1,1,0,1,1,'/');
/*!40000 ALTER TABLE `tblarticulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblentrada`
--

DROP TABLE IF EXISTS `tblentrada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblentrada` (
  `idEntrada` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entrada.',
  `idProcedencia` smallint(5) unsigned NOT NULL COMMENT 'Identificador lugar procedencia.',
  `idMovimiento` bigint(19) unsigned NOT NULL COMMENT 'Identificador del movimiento.',
  `numeroVale` int(11) NOT NULL COMMENT 'Número del vale donde se detalla el articulo recibido.',
  `numeroLote` int(11) DEFAULT NULL COMMENT 'Número de lote de donde proviene el articulo.',
  `fechaVencimiento` date DEFAULT NULL COMMENT 'Fechad de caducidad del producto.',
  `cantidad` double NOT NULL COMMENT 'Cantidad de articulos recibidas.',
  `precio` double NOT NULL COMMENT 'Precio unitario del articulo.',
  `fechaRecepcion` date NOT NULL,
  `existencia` double NOT NULL DEFAULT '0',
  `vencimientoMaximo` date DEFAULT NULL,
  PRIMARY KEY (`idEntrada`),
  KEY `FK_Movimiento_Entrada` (`idMovimiento`),
  KEY `FK_ProcedenciaArticulo_Entrada` (`idProcedencia`),
  CONSTRAINT `FK_Movimiento_Entrada` FOREIGN KEY (`idMovimiento`) REFERENCES `tblmovimiento` (`idMovimiento`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ProcedenciaArticulo_Entrada` FOREIGN KEY (`idProcedencia`) REFERENCES `tblprocedenciaarticulo` (`idProcedencia`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblentrada`
--

LOCK TABLES `tblentrada` WRITE;
/*!40000 ALTER TABLE `tblentrada` DISABLE KEYS */;
INSERT INTO `tblentrada` VALUES (1,2,1,67,89,'2011-11-28',2,0.23,'2011-11-01',0,'2011-11-30'),(2,3,2,7,78,'2011-11-27',48,0.23,'2011-11-26',30,'2011-11-30');
/*!40000 ALTER TABLE `tblentrada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblsalida`
--

DROP TABLE IF EXISTS `tblsalida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblsalida` (
  `idSalida` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la salida.',
  `idEntrada` bigint(19) unsigned NOT NULL COMMENT 'Identificador de la entrada.',
  `idMovimiento` bigint(19) unsigned NOT NULL COMMENT 'Identificador del movimiento.',
  `destino` varchar(100) NOT NULL COMMENT 'Destino donde sera usado el articulo.',
  `cantidad` double NOT NULL COMMENT 'Cantidad solicitada del articulo.',
  PRIMARY KEY (`idSalida`),
  KEY `FK_Movimiento_Salida` (`idMovimiento`),
  KEY `FK_Entrada_Salida` (`idEntrada`),
  CONSTRAINT `FK_Entrada_Salida` FOREIGN KEY (`idEntrada`) REFERENCES `tblentrada` (`idEntrada`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Movimiento_Salida` FOREIGN KEY (`idMovimiento`) REFERENCES `tblmovimiento` (`idMovimiento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblsalida`
--

LOCK TABLES `tblsalida` WRITE;
/*!40000 ALTER TABLE `tblsalida` DISABLE KEYS */;
INSERT INTO `tblsalida` VALUES (1,1,3,'Hematologia',2),(2,2,3,'Hematologia',18);
/*!40000 ALTER TABLE `tblsalida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcapacitacioncontinua`
--

DROP TABLE IF EXISTS `tblcapacitacioncontinua`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcapacitacioncontinua` (
  `idCapacitacion` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la capacitación.',
  `idInstitucion` smallint(5) unsigned NOT NULL COMMENT 'Identificador de la institución.',
  `nombreEvento` text NOT NULL COMMENT 'Nombre de la capacitación o evento.',
  `lugarEvento` varchar(254) NOT NULL COMMENT 'Lugar donde se realizará la capacitación.',
  `direccionEvento` text COMMENT 'Dirección donde se llevará a cabo la capacitación.',
  `fechaInicio` datetime NOT NULL COMMENT 'Fecha en la que inicia la capacitación.',
  `fechaFinal` datetime NOT NULL COMMENT 'Fecha de finalización de la capactiación.',
  PRIMARY KEY (`idCapacitacion`),
  KEY `FK_Institucion_Capacitacion` (`idInstitucion`),
  CONSTRAINT `FK_Institucion_Capacitacion` FOREIGN KEY (`idInstitucion`) REFERENCES `tblinstitucion` (`idInstitucion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcapacitacioncontinua`
--

LOCK TABLES `tblcapacitacioncontinua` WRITE;
/*!40000 ALTER TABLE `tblcapacitacioncontinua` DISABLE KEYS */;
INSERT INTO `tblcapacitacioncontinua` VALUES (1,3,'Uso adecuado del reativo M16','Hotel Radisson','San Salvador','2011-12-01 09:30:00','2011-12-03 18:00:00');
/*!40000 ALTER TABLE `tblcapacitacioncontinua` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-01-02 11:59:08
