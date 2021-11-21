SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
CREATE DATABASE IF NOT EXISTS `scorpio` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `scorpio`;

CREATE TABLE `accion` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `descripcion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `accion`;
INSERT INTO `accion` (`id`, `nombre`, `descripcion`) VALUES
(1, 'Registrar', ''),
(2, 'Modificar', ''),
(3, 'Listar', ''),
(4, 'Inhabilitar', ''),
(5, 'Eliminar', ''),
(6, 'Auditar', NULL),
(7, 'Habilitar', NULL),
(8, 'Cerrar', NULL);

CREATE TABLE `artefacto` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `marca` varchar(45) NOT NULL,
  `modelo` varchar(45) DEFAULT NULL,
  `serie` varchar(45) DEFAULT NULL,
  `estado_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `artefacto`;
INSERT INTO `artefacto` (`id`, `nombre`, `marca`, `modelo`, `serie`, `estado_id`) VALUES
(1, 'televisor', 'LG', '4k', '542', 1),
(2, 'computadora', 'Hp', 'Core7', '5156', 1),
(3, 'ferafaawdad', 'dwa', 'daw', '23', 1);

CREATE TABLE `categoria_entidad` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `descripcion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `categoria_entidad`;
INSERT INTO `categoria_entidad` (`id`, `nombre`, `descripcion`) VALUES
(1, 'Maestros', ''),
(2, 'Entrada de Actividades', ''),
(3, 'Administracion del Sitio', ''),
(4, 'Reportes', ''),
(5, 'Seguridad', '');

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `apellidoPaterno` varchar(45) DEFAULT NULL,
  `apellidoMaterno` varchar(45) DEFAULT NULL,
  `numero_documento` varchar(13) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `telefono` int(9) DEFAULT NULL,
  `correo` varchar(45) DEFAULT NULL,
  `otro` varchar(45) DEFAULT NULL,
  `fecha_registro` date DEFAULT NULL,
  `aval` varchar(45) DEFAULT NULL,
  `tipo_documento_identidad_id` int(11) NOT NULL,
  `estado_id` int(11) NOT NULL,
  `distrito_id` int(11) NOT NULL,
  `tipo_persona_id` int(11) NOT NULL,
  `tipo_vivienda_id` int(11) NOT NULL,
  `zona_cobranza_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `cliente`;
INSERT INTO `cliente` (`id`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `numero_documento`, `direccion`, `telefono`, `correo`, `otro`, `fecha_registro`, `aval`, `tipo_documento_identidad_id`, `estado_id`, `distrito_id`, `tipo_persona_id`, `tipo_vivienda_id`, `zona_cobranza_id`) VALUES
(4418, 'Andres', 'Abanto', 'Villacencio', '47806359', 'Guardia peruana Dsp 1050 Lt. 19 ', 4043316, 'fernando.supo6@gmail.com', 'dad', '2030-11-16', 'NO', 1, 1, 1, 1, 1, 1),
(4419, 'Fernando', 'Supo', 'Palomino', '47806359', 'huertos de lurin', 965012556, 'fernando.supo6@gmail.com', NULL, NULL, NULL, 1, 1, 1, 1, 1, 1),
(4420, 'asd', 'asa', 'asd', '47806350', 'daawdawdawd', 132, 'das', NULL, NULL, NULL, 1, 1, 1, 2, 1, 1);

CREATE TABLE `cobrador` (
  `id` int(11) NOT NULL,
  `idEmpleado` int(11) DEFAULT NULL,
  `estado` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `cobrador`;
CREATE TABLE `concepto_gasto` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `estado_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `concepto_gasto`;
INSERT INTO `concepto_gasto` (`id`, `nombre`, `estado_id`) VALUES
(1, 'Accesorios para computadoras', 1),
(2, 'H2O', 1),
(3, 'Alquiler', 1),
(4, 'Chocolates por el dia de la madre', 1),
(5, 'Computadoras', 1),
(6, 'Grati x 28 Julio', 1),
(7, 'Luz', 1),
(8, 'Menu Oficina', 1),
(9, 'Recarga de celulares', 1),
(10, 'Sueldo Alicia', 1),
(11, 'Sueldo Betsy', 1),
(12, 'Sueldo Griselda', 1),
(13, 'Sueldo Leyda', 1),
(15, 'Sueldo Maria', 1);

CREATE TABLE `dato_comercial` (
  `id` int(11) NOT NULL,
  `ruc` int(11) DEFAULT NULL,
  `razon_social` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `referencia` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `linea_credito` varchar(45) DEFAULT NULL,
  `otro` varchar(100) DEFAULT NULL,
  `tipo_vivienda_id` int(11) NOT NULL,
  `giro_id` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `estado_id` int(11) NOT NULL,
  `aval` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `dato_comercial`;
INSERT INTO `dato_comercial` (`id`, `ruc`, `razon_social`, `direccion`, `referencia`, `telefono`, `linea_credito`, `otro`, `tipo_vivienda_id`, `giro_id`, `cliente_id`, `estado_id`, `aval`) VALUES
(1, 123456789, 'razon socials', 'direccion uno', 'referencia uno', '4303262', 'linea', 'awda', 2, 13, 4418, 1, 4419),
(2, 123456789, 'razon social', 'direccion uno', 'referencia uno', '123456789', 'credito uno', NULL, 1, 1, 4418, 1, 4419),
(3, 1234567891, 'aaa', 'sdfsdf', 'sdfsf', '788786', '5555555', NULL, 2, 2, 4419, 1, 4420);

CREATE TABLE `distrito` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `distrito`;
INSERT INTO `distrito` (`id`, `nombre`) VALUES
(1, 'San Miguel'),
(2, 'Lince'),
(3, 'Villa El Salvado');

CREATE TABLE `empleado` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidoPaterno` varchar(50) NOT NULL,
  `apellidoMaterno` varchar(50) DEFAULT NULL,
  `numero_documento` varchar(13) NOT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `telefono` varchar(80) DEFAULT NULL,
  `id_estado` int(11) NOT NULL,
  `id_tipo_documento_identidad` int(11) NOT NULL,
  `id_sede` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `empleado`;
INSERT INTO `empleado` (`id`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `numero_documento`, `correo`, `telefono`, `id_estado`, `id_tipo_documento_identidad`, `id_sede`) VALUES
(1, 'Soporte', 'Scorpio', 'Scorpio', '00000000', 'soporte@scorpio.com', '000000', 1, 1, 1),
(2, 'Pepito', 'Pepito', 'Pepito', '483225506', 'pepito@gmail.com', '987654321', 1, 1, 1);

CREATE TABLE `entidad` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `descripcion` varchar(150) DEFAULT NULL,
  `id_categoria_entidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `entidad`;
INSERT INTO `entidad` (`id`, `nombre`, `descripcion`, `id_categoria_entidad`) VALUES
(1, 'Mes', '', 2),
(2, 'Periodo', '', 2),
(3, 'INFODAF', '', 4),
(4, 'Presupuesto', '', 3),
(5, 'Cliente', '', 1),
(6, 'Sitio', '', 3),
(7, 'Empleado', '', 1),
(8, 'Actividad', '', 1),
(9, 'Segmento', '', 1),
(10, 'Empresa', '', 1),
(11, 'Usuario', '', 1),
(12, 'Perfil', '', 5),
(13, 'R. General', '', 5);

CREATE TABLE `estado` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `descripcion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `estado`;
INSERT INTO `estado` (`id`, `nombre`, `descripcion`) VALUES
(1, 'Activo', ''),
(2, 'Inactivo', ''),
(3, 'En espera', 'Usado para mencionar que aun no se registrara un Periodo'),
(4, 'Cerrado', 'Usado para mencionar que ya se registro un Periodo'),
(5, 'Inactivo por Dependencia', 'Inactivado por motivo de dependencia. Otra Entidad fue inactivada y esta fue necesaria ser desactivada para ello.');

CREATE TABLE `giro` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `estado_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `giro`;
INSERT INTO `giro` (`id`, `descripcion`, `estado_id`) VALUES
(1, 'Abarrotes', 1),
(2, 'Artesania', 1),
(4, 'Abonoo', 1),
(5, 'Accesorios de celulares', 1),
(6, 'Accesorios y motores', 1),
(7, 'Alfombras y cortinas', 1),
(8, 'Alquiler equipo de sonido', 1),
(9, 'Animalitos', 1),
(10, 'Art. deportivos', 1),
(11, 'Art. Tex. y plastico', 1),
(12, 'Art. de Limpieza', 1),
(13, 'Artefactos', 1),
(14, 'Art. para hogar', 1),
(15, 'Art. de cuero', 1),
(16, 'Autoradio', 1);

CREATE TABLE `pago` (
  `id` int(11) NOT NULL,
  `idPrestamo` int(11) DEFAULT NULL,
  `idCobrador` int(11) DEFAULT NULL,
  `fechaPago` date DEFAULT NULL,
  `pago` double DEFAULT NULL,
  `flagSupervisor` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `pago`;
CREATE TABLE `perfil` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `descripcion` varchar(150) DEFAULT NULL,
  `id_estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `perfil`;
INSERT INTO `perfil` (`id`, `nombre`, `descripcion`, `id_estado`) VALUES
(1, 'Soporte', 'Soporte Técnico del sistema.', 1);

CREATE TABLE `perfil_por_usuario` (
  `id` int(11) NOT NULL,
  `id_perfil` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `perfil_por_usuario`;
INSERT INTO `perfil_por_usuario` (`id`, `id_perfil`, `id_usuario`) VALUES
(4620, 1, 1);

CREATE TABLE `permiso` (
  `id` int(11) NOT NULL,
  `id_entidad` int(11) NOT NULL,
  `id_accion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `permiso`;
INSERT INTO `permiso` (`id`, `id_entidad`, `id_accion`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 2, 1),
(7, 2, 2),
(8, 2, 3),
(9, 2, 4),
(10, 2, 5),
(11, 3, 1),
(12, 3, 2),
(13, 3, 3),
(14, 3, 4),
(15, 3, 5),
(16, 4, 1),
(17, 4, 2),
(18, 4, 3),
(19, 4, 4),
(20, 4, 5),
(21, 5, 1),
(22, 5, 2),
(23, 5, 3),
(24, 5, 4),
(25, 5, 5),
(26, 6, 1),
(27, 6, 2),
(28, 6, 3),
(29, 6, 4),
(30, 6, 5),
(31, 7, 1),
(32, 7, 2),
(33, 7, 3),
(34, 7, 4),
(35, 7, 5),
(36, 8, 1),
(37, 8, 2),
(38, 8, 3),
(39, 8, 4),
(40, 8, 5),
(41, 9, 1),
(42, 9, 2),
(43, 9, 3),
(44, 9, 4),
(45, 9, 5),
(46, 10, 1),
(47, 10, 2),
(48, 10, 3),
(49, 10, 4),
(50, 10, 5),
(51, 11, 1),
(52, 11, 2),
(53, 11, 3),
(54, 11, 4),
(55, 11, 5),
(56, 12, 1),
(57, 12, 2),
(58, 12, 3),
(59, 12, 4),
(60, 12, 5),
(61, 11, 6),
(62, 11, 6),
(63, 11, 6),
(64, 11, 6),
(65, 11, 6),
(66, 11, 6),
(67, 11, 6),
(68, 12, 6),
(70, 12, 6),
(71, 12, 6),
(72, 12, 6),
(73, 12, 6),
(74, 12, 6),
(75, 12, 6);

CREATE TABLE `permiso_por_perfil` (
  `id` int(11) NOT NULL,
  `id_perfil` int(11) NOT NULL,
  `id_permiso` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `permiso_por_perfil`;
INSERT INTO `permiso_por_perfil` (`id`, `id_perfil`, `id_permiso`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 1, 9),
(10, 1, 10),
(11, 1, 11),
(12, 1, 12),
(13, 1, 13),
(14, 1, 14),
(15, 1, 15),
(16, 1, 16),
(17, 1, 17),
(18, 1, 18),
(19, 1, 19),
(20, 1, 20),
(21, 1, 21),
(22, 1, 22),
(23, 1, 23),
(24, 1, 24),
(25, 1, 25),
(26, 1, 26),
(27, 1, 27),
(28, 1, 28),
(29, 1, 29),
(30, 1, 30),
(31, 1, 31),
(32, 1, 32),
(33, 1, 33),
(34, 1, 34),
(35, 1, 35),
(36, 1, 36),
(37, 1, 37),
(38, 1, 38),
(39, 1, 39),
(40, 1, 40),
(41, 1, 41),
(42, 1, 42),
(43, 1, 43),
(108, 1, 44),
(109, 1, 45),
(110, 1, 46),
(111, 1, 47),
(112, 1, 48),
(113, 1, 49),
(114, 1, 50),
(115, 1, 51),
(116, 1, 52),
(117, 1, 53),
(118, 1, 54),
(119, 1, 55),
(120, 1, 56),
(121, 1, 57),
(122, 1, 58),
(123, 1, 59),
(124, 1, 60),
(125, 1, 61),
(126, 1, 62),
(127, 1, 63),
(128, 1, 64),
(129, 1, 65),
(130, 1, 66),
(131, 1, 67),
(132, 1, 68),
(133, 1, 70),
(134, 1, 71),
(135, 1, 72),
(136, 1, 73),
(137, 1, 74),
(138, 1, 75);

CREATE TABLE `prestamo` (
  `id` int(11) NOT NULL,
  `fecha` date DEFAULT NULL,
  `prestamo` double DEFAULT NULL,
  `cuota` double DEFAULT NULL,
  `monto_pagar` double DEFAULT NULL,
  `id_empleado` int(11) NOT NULL,
  `estado_id` int(11) NOT NULL,
  `artefacto_id` int(11) NOT NULL,
  `tipo_prestamo_id` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `garante` int(11) DEFAULT NULL,
  `frecuencia` int(11) DEFAULT NULL COMMENT 'frecuencia en que se realizará el cobro de la deuda.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `prestamo`;
INSERT INTO `prestamo` (`id`, `fecha`, `prestamo`, `cuota`, `monto_pagar`, `id_empleado`, `estado_id`, `artefacto_id`, `tipo_prestamo_id`, `cliente_id`, `garante`, `frecuencia`) VALUES
(2, '2018-03-09', 1500.5, NULL, NULL, 1, 1, 1, 1, 4420, 4418, 1),
(3, '2018-03-10', 100, 3.3333333333333335, 83.33333333333333, 1, 1, 1, 1, 4418, 4418, 1),
(4, '2018-03-10', 100, 3.3333333333333335, 83.33333333333333, 1, 1, 1, 1, 4418, 4418, 1),
(5, '2018-03-10', 1000, 41.666666666666664, 1250, 1, 1, 1, 1, 4418, 4418, 1),
(6, '2018-03-10', 100, 4.166666666666667, 125, 1, 1, 1, 1, 4418, 4418, 1);

CREATE TABLE `sede` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `ruc` int(11) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `telefono` int(9) DEFAULT NULL,
  `id_estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `sede`;
INSERT INTO `sede` (`id`, `nombre`, `ruc`, `direccion`, `telefono`, `id_estado`) VALUES
(1, 'Inversiones Escorpio Salamanca', 14, 'categoria Z', 14, 1),
(2, 'Inversiones Escorpio V. Salvador', 14, 'categoria B', 14, 1),
(3, 'Inversiones Escorpio SJL', 14, 'categoria C', 14, 2);

CREATE TABLE `sede_gasto` (
  `id` int(11) NOT NULL,
  `sede_id` int(11) NOT NULL,
  `concepto_gasto_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `sede_gasto`;
CREATE TABLE `tipo_documento_identidad` (
  `id` int(11) NOT NULL,
  `nombre_documento` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `tipo_documento_identidad`;
INSERT INTO `tipo_documento_identidad` (`id`, `nombre_documento`) VALUES
(1, 'DNI'),
(2, 'Extranjero'),
(3, 'Pasaporte');

CREATE TABLE `tipo_persona` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `tipo_persona`;
INSERT INTO `tipo_persona` (`id`, `nombre`) VALUES
(1, 'Juridica'),
(2, 'Natural');

CREATE TABLE `tipo_prestamo` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `tiempo` int(11) DEFAULT NULL,
  `interes` int(11) DEFAULT NULL,
  `cobra` int(11) DEFAULT NULL,
  `estado_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `tipo_prestamo`;
INSERT INTO `tipo_prestamo` (`id`, `nombre`, `tiempo`, `interes`, `cobra`, `estado_id`) VALUES
(1, 'D.E. 12 semanas', 30, 25, 1, 2),
(2, 'D.E. 15 dias', 45, 15, 1, 1),
(3, 'D.E. 2 quincenas', 15, 5, 2, 1);

CREATE TABLE `tipo_vivienda` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `tipo_vivienda`;
INSERT INTO `tipo_vivienda` (`id`, `nombre`) VALUES
(1, 'Propia'),
(2, 'Alquilada');

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `fecha_registro` date NOT NULL,
  `contrasenia` varchar(50) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `id_estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `usuario`;
INSERT INTO `usuario` (`id`, `nombre`, `fecha_registro`, `contrasenia`, `id_empleado`, `id_estado`) VALUES
(1, 'soporte', '2017-01-03', 'soporte', 1, 1);

CREATE TABLE `zona_cobranza` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `abreviatura` varchar(45) DEFAULT NULL,
  `estado_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `zona_cobranza`;
INSERT INTO `zona_cobranza` (`id`, `nombre`, `abreviatura`, `estado_id`) VALUES
(1, 'Villa el salvador', 'A', 1),
(2, 'Miraflores / Surquillo', 'B', 1),
(3, 'Chorrillos / Surco', 'C', 1),
(4, 'Lurin / Galvez', 'D', 1);


ALTER TABLE `accion`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `artefacto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_artefacto_estado1_idx` (`estado_id`);

ALTER TABLE `categoria_entidad`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_cliente_tipo_documento_identidad_idx` (`tipo_documento_identidad_id`),
  ADD KEY `fk_cliente_estado1_idx` (`estado_id`),
  ADD KEY `fk_cliente_distrito1_idx` (`distrito_id`),
  ADD KEY `fk_cliente_tipo_persona1_idx` (`tipo_persona_id`),
  ADD KEY `fk_cliente_tipo_vivienda1_idx` (`tipo_vivienda_id`),
  ADD KEY `fk_cliente_zona_cobranza1_idx` (`zona_cobranza_id`);

ALTER TABLE `cobrador`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_cobrador_empleado1` (`idEmpleado`);

ALTER TABLE `concepto_gasto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_concepto_gasto_estado1_idx` (`estado_id`);

ALTER TABLE `dato_comercial`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_datos_comerciales_tipo_vivienda1_idx` (`tipo_vivienda_id`),
  ADD KEY `fk_datos_comerciales_giro1_idx` (`giro_id`),
  ADD KEY `fk_dato_comercial_cliente1_idx` (`cliente_id`),
  ADD KEY `fk_dato_comercial_estado1_idx` (`estado_id`);

ALTER TABLE `distrito`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `empleado`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nroDocumentoEmpleadoUnico` (`numero_documento`),
  ADD KEY `FK__empleado__id_est__5165187F` (`id_estado`),
  ADD KEY `FK__empleado__id_tip__52593CB8` (`id_tipo_documento_identidad`),
  ADD KEY `FK__empleado__id_zona_idx` (`id_sede`);

ALTER TABLE `entidad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK__entidad__id_cate__74AE54BC` (`id_categoria_entidad`);

ALTER TABLE `estado`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `giro`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_giro_estado1_idx` (`estado_id`);

ALTER TABLE `pago`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pago_prestamo1` (`idPrestamo`),
  ADD KEY `fk_pago_cobrador1` (`idCobrador`);

ALTER TABLE `perfil`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombreUnico` (`nombre`),
  ADD KEY `FK__perfil__id_estad__7A672E12` (`id_estado`);

ALTER TABLE `perfil_por_usuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK__perfil_po__id_pe__7C4F7684` (`id_perfil`),
  ADD KEY `FK__perfil_po__id_US__7B5B524C` (`id_usuario`);

ALTER TABLE `permiso`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK__permiso__id_acci__04E4BC85` (`id_accion`),
  ADD KEY `FK__permiso__id_enti__06CD04F7` (`id_entidad`);

ALTER TABLE `permiso_por_perfil`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK__permiso_p__id_pe__09A971A2` (`id_perfil`),
  ADD KEY `FK__permiso_p__id_pe__0A9D95DB` (`id_permiso`);

ALTER TABLE `prestamo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK__empleado__id_empl_idx` (`id_empleado`),
  ADD KEY `fk_prestamo_artefacto1_idx` (`artefacto_id`),
  ADD KEY `fk_prestamo_estado1_idx` (`estado_id`),
  ADD KEY `fk_prestamo_tipo_prestamo1_idx` (`tipo_prestamo_id`),
  ADD KEY `fk_prestamo_cliente1_idx` (`cliente_id`);

ALTER TABLE `sede`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK__empleado__id_est_idx` (`id_estado`);

ALTER TABLE `sede_gasto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_sede_gasto_sede1_idx` (`sede_id`),
  ADD KEY `fk_sede_gasto_concepto_gasto1_idx` (`concepto_gasto_id`);

ALTER TABLE `tipo_documento_identidad`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `tipo_persona`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `tipo_prestamo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_tipo_prestamo_estado1_idx` (`estado_id`);

ALTER TABLE `tipo_vivienda`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `AK_UsuarioUnico` (`nombre`),
  ADD KEY `FK__usuario__id_esta__1F98B2C1` (`id_estado`),
  ADD KEY `FK__usuario__id_empl__6D0D32F4` (`id_empleado`);

ALTER TABLE `zona_cobranza`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_zona_cobranza_estado1_idx` (`estado_id`);


ALTER TABLE `accion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

ALTER TABLE `artefacto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `categoria_entidad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4421;

ALTER TABLE `cobrador`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `concepto_gasto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

ALTER TABLE `dato_comercial`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `empleado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `entidad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

ALTER TABLE `estado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

ALTER TABLE `giro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

ALTER TABLE `pago`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `perfil`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE `perfil_por_usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4621;

ALTER TABLE `permiso`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

ALTER TABLE `permiso_por_perfil`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=139;

ALTER TABLE `prestamo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

ALTER TABLE `sede`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `tipo_documento_identidad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `tipo_persona`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `tipo_prestamo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `tipo_vivienda`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE `zona_cobranza`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;


ALTER TABLE `artefacto`
  ADD CONSTRAINT `fk_artefacto_estado1` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `cliente`
  ADD CONSTRAINT `fk_cliente_distrito1` FOREIGN KEY (`distrito_id`) REFERENCES `distrito` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cliente_estado1` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cliente_tipo_documento_identidad` FOREIGN KEY (`tipo_documento_identidad_id`) REFERENCES `tipo_documento_identidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cliente_tipo_persona1` FOREIGN KEY (`tipo_persona_id`) REFERENCES `tipo_persona` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cliente_tipo_vivienda1` FOREIGN KEY (`tipo_vivienda_id`) REFERENCES `tipo_vivienda` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cliente_zona_cobranza1` FOREIGN KEY (`zona_cobranza_id`) REFERENCES `zona_cobranza` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `cobrador`
  ADD CONSTRAINT `fk_cobrador_empleado1` FOREIGN KEY (`idEmpleado`) REFERENCES `empleado` (`id`);

ALTER TABLE `concepto_gasto`
  ADD CONSTRAINT `fk_concepto_gasto_estado1` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `dato_comercial`
  ADD CONSTRAINT `fk_dato_comercial_cliente1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_dato_comercial_estado1` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_datos_comerciales_giro1` FOREIGN KEY (`giro_id`) REFERENCES `giro` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_datos_comerciales_tipo_vivienda1` FOREIGN KEY (`tipo_vivienda_id`) REFERENCES `tipo_vivienda` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `empleado`
  ADD CONSTRAINT `FK__empleado__id_est__5165187F` FOREIGN KEY (`id_estado`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK__empleado__id_sede` FOREIGN KEY (`id_sede`) REFERENCES `sede` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK__empleado__id_tip__52593CB8` FOREIGN KEY (`id_tipo_documento_identidad`) REFERENCES `tipo_documento_identidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `entidad`
  ADD CONSTRAINT `FK__entidad__id_cate__73BA3083` FOREIGN KEY (`id_categoria_entidad`) REFERENCES `categoria_entidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `giro`
  ADD CONSTRAINT `fk_giro_estado1` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `pago`
  ADD CONSTRAINT `fk_pago_cobrador1` FOREIGN KEY (`idCobrador`) REFERENCES `cobrador` (`id`),
  ADD CONSTRAINT `fk_pago_prestamo1` FOREIGN KEY (`idPrestamo`) REFERENCES `prestamo` (`id`);

ALTER TABLE `perfil`
  ADD CONSTRAINT `FK__perfil__id_estad__797309D9` FOREIGN KEY (`id_estado`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `perfil_por_usuario`
  ADD CONSTRAINT `FK__perfil_po__id_US__7B5B524C` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK__perfil_po__id_pe__7B5B524B` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `permiso`
  ADD CONSTRAINT `FK__permiso__id_acci__03F0984C` FOREIGN KEY (`id_accion`) REFERENCES `accion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK__permiso__id_enti__05D8E0BE` FOREIGN KEY (`id_entidad`) REFERENCES `entidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `permiso_por_perfil`
  ADD CONSTRAINT `FK__permiso_p__id_pe__07C12930` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK__permiso_p__id_pe__08B54D69` FOREIGN KEY (`id_permiso`) REFERENCES `permiso` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `prestamo`
  ADD CONSTRAINT `FK__empleado__id_empl` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_prestamo_artefacto1` FOREIGN KEY (`artefacto_id`) REFERENCES `artefacto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_prestamo_cliente1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_prestamo_estado1` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_prestamo_tipo_prestamo1` FOREIGN KEY (`tipo_prestamo_id`) REFERENCES `tipo_prestamo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `sede`
  ADD CONSTRAINT `FK__empleado__id_est` FOREIGN KEY (`id_estado`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `sede_gasto`
  ADD CONSTRAINT `fk_sede_gasto_concepto_gasto` FOREIGN KEY (`concepto_gasto_id`) REFERENCES `concepto_gasto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_sede_gasto_sede` FOREIGN KEY (`sede_id`) REFERENCES `sede` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `tipo_prestamo`
  ADD CONSTRAINT `fk_tipo_prestamo_estado1` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `usuario`
  ADD CONSTRAINT `FK__usuario__id_empl__6D0D32F4` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK__usuario__id_esta__1EA48E88` FOREIGN KEY (`id_estado`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `zona_cobranza`
  ADD CONSTRAINT `fk_zona_cobranza_estado1` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
