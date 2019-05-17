/*
ARCHIVO MODIFICADO Viernes>>>>>>>> 17-mayo 2019
*/
connect system/manager@xe
connect usr_strongfit/strongfit
-- ***********************************************CONTABILIDAD*********************

prompt **********************************************
prompt Loading CONTA_TIPO_ASIENTOS...
insert into CONTA_TIPO_ASIENTOS (id_tipoasiento, descripcion_tipoasiento)
values (TIPO_ASIENTO_SEQ_ID.NEXTVAL, 'A.Normal');
insert into CONTA_TIPO_ASIENTOS (id_tipoasiento, descripcion_tipoasiento)
values (TIPO_ASIENTO_SEQ_ID.NEXTVAL, 'A.Inicial');
insert into CONTA_TIPO_ASIENTOS (id_tipoasiento, descripcion_tipoasiento)
values (TIPO_ASIENTO_SEQ_ID.NEXTVAL, 'A.Ajuste');
insert into CONTA_TIPO_ASIENTOS (id_tipoasiento, descripcion_tipoasiento)
values (TIPO_ASIENTO_SEQ_ID.NEXTVAL, 'A.Cierre');
insert into CONTA_TIPO_ASIENTOS (id_tipoasiento, descripcion_tipoasiento)
values (TIPO_ASIENTO_SEQ_ID.NEXTVAL, 'Comp.Egreso');
insert into CONTA_TIPO_ASIENTOS (id_tipoasiento, descripcion_tipoasiento)
values (TIPO_ASIENTO_SEQ_ID.NEXTVAL, 'Comp.Ingreso');
insert into CONTA_TIPO_ASIENTOS (id_tipoasiento, descripcion_tipoasiento)
values (TIPO_ASIENTO_SEQ_ID.NEXTVAL, 'Comp.Traspaso');
commit;

prompt***********************************************************
prompt  INSERTA CONTA_TIPOCUENTAS...
insert into CONTA_TIPOCUENTAS (id_tipocuenta, descripcion_tipocuenta)
values (TIPOCUENTA_SEQ_ID.NEXTVAL, 'Activo');
insert into CONTA_TIPOCUENTAS (id_tipocuenta, descripcion_tipocuenta)
values (TIPOCUENTA_SEQ_ID.NEXTVAL, 'Pasivo');
insert into CONTA_TIPOCUENTAS (id_tipocuenta, descripcion_tipocuenta)
values (TIPOCUENTA_SEQ_ID.NEXTVAL, 'Patrimonio');
insert into CONTA_TIPOCUENTAS (id_tipocuenta, descripcion_tipocuenta)
values (TIPOCUENTA_SEQ_ID.NEXTVAL, 'Ingresos');
insert into CONTA_TIPOCUENTAS (id_tipocuenta, descripcion_tipocuenta)
values (TIPOCUENTA_SEQ_ID.NEXTVAL, 'Gastos y Costos');
commit;


prompt***********************************************************
prompt Loading CONTA_CTA_MOVIMIENTOS...
insert into CONTA_CTA_MOVIMIENTOS (id_ctamov, codigo_ctamov, descripcion_ctamov)
values (1, 'M', 'Movimiento');
insert into CONTA_CTA_MOVIMIENTOS (id_ctamov, codigo_ctamov, descripcion_ctamov)
values (2, 'C', 'Control');
commit;

prompt***********************************************************
prompt Loading CONTA_NIVELES...
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (1, 1, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (2, 2, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (3, 3, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (4, 4, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (5, 5, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (6, 6, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (7, 7, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (8, 8, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (9, 9, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (10, 10, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (11, 11, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (12, 12, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (13, 13, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (14, 14, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (15, 15, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (16, 16, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (17, 17, ' ');
insert into CONTA_NIVELES (id_nivel, valor_nivel, DESCRIPCION_NIVEL)
values (18, 18, ' ');
commit;

prompt***********************************************************
prompt Loading CONTA_CUENTA...
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (1, '1', 'Activos', null, 1, 1, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (2, '11', 'Activo Corriente', 1, 1, 2, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (3, '111', 'Disponibles', 2, 1, 3, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (4, '11101', 'Caja', 3, 1, 4, 1, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (5, '1110101', 'Cajas Chicas', 4, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (6, '111010101', 'Caja Chica De Cuenca', 5, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (7, '111010102', 'Caja Chica Quito', 5, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (8, '111010103', 'Caja Viaticos', 5, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (9, '111010104', 'Caja Chica Mendez', 5, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (10, '1110102', 'Caja General', 4, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (11, '111010201', 'Caja General Efectivo', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (12, '111010202', 'Caja General Cheques', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (13, '111010203', 'Caja General Cheqs Posfechados', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (14, '111010204', 'Caja General Cheqs Protestados', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (15, '111010205', 'Cheques En Garantia Clientes', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (16, '111010206', 'Caja General Tarjetas/Credito', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (17, '111010207', 'Cheques En Custodia Cajera', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (18, '111010208', 'Efectivo En Custodia Cajera', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (19, '111010209', 'Certificados C/Plazo', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (20, '111010210', 'Fondos Por Depositar', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (21, '111010211', 'Caja Tesoreria/Temporal', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (22, '111010212', 'Caja Liquidaciones', 10, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (23, '1110103', 'Caja Mayor', 4, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (24, '111010301', 'Caja Efectivo (CC)', 23, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (25, '111010302', 'Caja Cheques (C.C)', 23, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (26, '111010303', 'Caja Cheqs. Posfechados (C.C)', 23, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (27, '111010304', 'Caja Cheqs  Protestados (C.C)', 23, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (28, '111010305', 'Caja Tarjetas De Credito (C.C)', 23, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (29, '111010306', 'Certificados C/Plazo (C.C)', 23, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (30, '11102', 'Bancos', 3, 1, 4, 1, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (31, '1110201', 'Cuentas Corrientes', 30, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (32, '111020101', 'Banco Pichincha', 31, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (33, '111020102', 'Banco Internacional', 31, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (34, '111020103', 'Banco Del Austro', 31, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (35, '111020104', 'Banco De Machala', 31, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (36, '111020105', 'Banco De Guayauqil', 31, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (37, '111020106', 'Banco Bolivariano', 31, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (38, '111020107', 'Banco Citibank', 31, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (39, '11103', 'Ctas Por Cob Tarjetas Credito', 3, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (40, '1110301', 'Tarjetas De Credito', 39, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (41, '111030101', 'Vouchers Por Cobrar Clientes', 40, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (42, '11104', 'Inversiones', 3, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (43, '1110401', 'Inversiones Corto Plazo', 42, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (44, '111040101', 'Certificados De Depòsito C/P', 43, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (45, '111040102', 'Transferencias Internas', 43, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (46, '111040103', 'Deposito Garantia Para Sobregiros', 43, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (47, '112', 'Exigibles', 2, 1, 3, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (48, '11201', 'Cuentas Por Cobrar', 47, 1, 4, 1, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (49, '11202', 'Anticipos', 47, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (50, '1120201', 'Anticipos  Proveedores Nacionales', 49, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (51, '1120202', 'Anticipos  Proveedores Extranjeros', 49, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (52, '1120203', 'Anticipos Empleados', 49, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (53, '112020301', 'Anticipo Sueldos', 52, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (54, '112020302', 'Anticipo Comisiones', 52, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (55, '112020303', 'Retenciones A Cobrar Empleados', 52, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (56, '112020304', 'Anticipo Para Viajes', 52, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (57, '11203', 'Credito Tributario', 47, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (58, '1120301', 'Credito Tributario Iva', 57, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (59, '112030101', 'Iva Pagado En Compras De Bienes', 58, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (60, '112030102', 'Iva Pagado Servicios', 58, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (61, '112030103', 'Iva Pagado Activos Fijos', 58, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (62, '112030104', 'Iva En Importaciones', 58, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (63, '112030105', 'Retenciones En La Fuente Iva', 58, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (64, '112030106', 'Iva Diferido', 58, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (65, '112030107', 'Credito Tributario', 58, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (66, '112030108', 'Pago Indevido X Reclamar Sri', 58, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (67, '1120302', 'Credito Tributario Impuesto A La Renta', 57, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (68, '112030201', 'Anticipo Impuesto Renta Ejercicio', 67, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (69, '112030202', 'Anticipo Impuesto  Renta Anios Anteriores', 67, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (70, '112030203', 'Retenciones En La Fuente IRenta', 67, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (71, '11204', 'Gastos Anticipados', 47, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (72, '1120401', 'Seguros Anticipados', 71, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (73, '1120402', 'Anticipo Arriendos', 71, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (74, '1120403', 'Anticipo Publicidad', 71, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (75, '1120404', 'Anticipo Gastos De Promocion', 71, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (76, '11205', 'Prestamos Compañias Relacionadas', 47, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (77, '113', 'Realizables', 2, 1, 3, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (78, '11301', 'Inventarios', 77, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (79, '1130101', 'Inventario De Mercaderia', 78, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (80, '113010101', 'Inventario Materia Prima', 79, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (81, '113010102', 'Inventario Productos Proceso', 79, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (82, '113010103', 'Inventario De Carros', 79, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (83, '113010104', 'Inventario De Motos', 79, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (84, '113010105', 'Inventario De Repuestos', 79, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (85, '113010106', 'Inventario De Tolvas', 79, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (86, '113010107', 'Inventario En Consignacion', 79, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (87, '113010108', 'Inventario Mercaderia (Obsoleta)', 79, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (88, '1130102', 'Importaciones En Transito', 78, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (89, '1130103', 'Compras En Transito', 78, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (90, '12', 'Activo No Corriente', 1, 1, 2, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (91, '121', 'Activos Fijos', 90, 1, 3, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (92, '12101', 'Activos Fijos No Depreciables', 91, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (93, '1210101', 'Terrenos', 92, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (94, '12102', 'Activos Fijos Depreciables Tangibles', 91, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (95, '1210201', 'Edificios', 94, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (96, '121020101', 'Depreciacion Acumulada Edificios', 95, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (97, '1210202', 'Vehiculos', 94, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (98, '121020201', 'Dep Acumulada Vehiculos', 97, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (99, '1210203', 'Equipos De Computacion', 94, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (100, '121020301', 'Dep. Acumul. Equipos Computacion', 99, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (101, '1210204', 'Equipo De Oficina', 94, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (102, '121020401', 'Dep. Acumul. Equipos De Oficina', 101, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (103, '1210205', 'Muebles Y Enseres', 94, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (104, '121020501', 'Dep. Acumul. Muebles Y Enseres', 103, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (105, '1210206', 'Equipos De Taller', 94, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (106, '121020601', 'Dep. Acumul. Equipos De Taller', 105, 1, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (107, '1210207', 'Instalaciones', 94, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (108, '12103', 'Activos  Intangible', 91, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (109, '1210301', 'Dominios Direccion Electronica', 108, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (110, '1210302', 'Lineas Telefonicas ', 108, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (111, '12104', 'Garantias', 91, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (112, '122', 'Activo Diferido', 90, 1, 3, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (113, '12201', 'Gastos Preoperacionales', 112, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (114, '12202', 'Gastos Operacionales Iniciales', 112, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (115, '12203', 'Gastos De Constitucion', 112, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (116, '123', 'Por Cobrar A Largo Plazo', 90, 1, 3, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (117, '12301', 'Documentos Y Ctas X Cobrar', 116, 1, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (118, '1230101', 'Doc Y Ctas X Cobrar A Socios', 117, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (119, '1230102', 'Otros Activos', 117, 1, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (120, '124', 'Cuentas De Orden', 90, 1, 3, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (121, '2', 'Pasivos', null, 2, 1, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (122, '21', 'Pasivo Corriente', 121, 2, 2, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (123, '211', 'Prestamos Por Pagar', 122, 2, 3, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (124, '21101', 'Prestamos Y Sobregiros Bancarios', 123, 2, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (125, '2110101', 'Sobregiro Bancario', 124, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (126, '2110102', 'Prestamos Bancarios C/Plazo', 124, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (127, '212', 'Cuentas Y Documentos Por Pagar', 122, 2, 3, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (128, '21201', 'Cuentas Por Pagar Proveedores', 127, 2, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (129, '21202', 'Cuentas Por Pagar Terceros', 127, 2, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (130, '2120201', 'Acreedores Varios', 129, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (131, '2120202', 'Gastos Acumulados Por Pagar', 129, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (132, '213', 'Obligaciones Por Pagar', 122, 2, 3, 1, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (133, '21301', 'Con La Administracion Tributaria', 132, 2, 4, 1, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (134, '2130101', 'Retenciones Del Iva', 133, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (135, '213010101', 'Iva Cobrado En Ventas', 134, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (136, '213010102', 'Iva Cobrado En Venta  Servicios', 134, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (137, '213010103', 'Iva Cobrado En Venta Activos Fijos', 134, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (138, '213010104', '30% Del Iva En Compra De Bienes', 134, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (139, '213010105', '70% Del Iva En Prestacion De Servicios', 134, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (140, '213010106', '100% Del Iva En La Prest. Servicios', 134, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (141, '213010107', '100% Del Iva Arrend. Persona. Naturales', 134, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (142, '213010108', '100% Del Iva Com. Bienes Y Serv. Liquidaciones', 134, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (143, '213010109', 'Impuesto Iva Por Pagar', 134, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (144, '2130102', 'Retenciones Del Impuesto A La Renta', 133, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (145, '213010201', '(302) En Relacion De Dependencia', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (146, '213010202', '(303) Honorarios Proefesionales Y Dietas', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (147, '213010203', '(304) Predomina El Intelecto', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (148, '213010204', '(307) Predomina La Mano De Obra', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (149, '213010205', '(308) Entre Sociedades', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (150, '213010206', '(309) Publicidad Y Comunicacion', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (151, '213010208', '(310) Transporte Privado De Carga', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (152, '213010209', '(312) Trasferencia De Bienes', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (153, '213010210', '(319) Arrendamiento Mercantil', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (154, '213010211', '(320) Arrendamiento Bienes Inmuebles', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (155, '213010212', '(322) Seguoros Y Reaseguros', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (156, '213010213', '(323) Rendimientos Financieros', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (157, '213010214', '(325) Loterias Rifas Similares', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (158, '213010215', '(327) Venta Combustibles Comercializadoras', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (159, '213010216', '(328) Venta Combustibles Distribuidores', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (160, '213010217', '(332) Pagos No Sujetos A Retencion', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (161, '213010218', '(333) Por Convenio De Debito O', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (162, '213010219', '(334) Pago Con Tarjeta / Crèdito', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (163, '213010220', '(340) Aplicables El 1%', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (164, '213010221', '(341) Aplicables El 2%', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (165, '213010222', '(342) Aplicables El 8%', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (166, '213010223', '(343) Aplicables El 25%', 144, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (167, '2130103', 'Retenciones en la Fuente', 133, 2, 5, 1, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (168, '213010301', '25% Impuesto A La Renta', 167, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (169, '2130104', 'Impto. Consumos Especiales', 133, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (170, '213010401', 'Ice Por Pagar', 169, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (171, '2130105', 'Sri Por Pagar', 133, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (172, '21302', 'Obligaciones Con El Iess', 132, 2, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (173, '2130201', 'Aportes Sobre Nomina', 172, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (174, '213020101', 'Aporte Patronal Y Personal', 173, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (175, '213020102', 'Fondo De Reserva', 173, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (176, '213020103', 'Prestamos Quirografarios', 173, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (177, '21303', 'Obligaciones Con El Personal', 132, 2, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (178, '2130301', 'Remuneraciones', 177, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (179, '213030101', 'Sueldos Por Pagar', 178, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (180, '213030102', 'Comisiones Por Pagar', 178, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (181, '213030103', 'Liquidaciones Por Pagar', 178, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (182, '2130302', 'Beneficios Sociales', 177, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (183, '213030201', 'Decimo Tercer Sueldo Por Pagar  ', 182, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (184, '213030202', 'Decimo Cuarto Sueldo Por Pagar', 182, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (185, '213030203', 'Vacaciones Por Pagar', 182, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (186, '213030204', 'Participacion Utilidades Trabajadores Por Pagar ', 182, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (187, '214', 'Obligaciones Con Accionistas', 122, 2, 3, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (188, '2140101', 'Prestamo De Accionistas', 187, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (189, '2140102', 'Dividendos Y Rendimientos', 187, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (190, '214010201', 'Cuentas Por Pagar Participacion', 189, 2, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (191, '215', 'Intereses Por Efectivizarse', 122, 2, 3, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (192, '22', 'Pasivo No Corriente', 121, 2, 2, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (193, '222', 'Prestamos Y Docume. X Pagar', 192, 2, 3, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (194, '22201', 'Prestamos X Pagar', 193, 2, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (195, '22202', 'Pasivo Diferido', 193, 2, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (196, '22203', 'Otros Pasivos', 193, 2, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (197, '2220301', 'Transitoria Deudora', 196, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (198, '2220302', 'Otros Activos', 196, 2, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (199, '23', 'Cuentas De Orden', 121, 2, 2, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (200, '3', 'Patrimonio', null, 3, 1, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (201, '31', 'Capital Social Reservas Y Utilidades', 200, 3, 2, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (202, '311', 'Capital Y Reservas', 201, 3, 3, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (203, '31101', 'Capital Social', 202, 3, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (204, '3110101', 'Capital Suscrito Y Pagado', 203, 3, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (205, '3110102', 'Aportes Para Futuras Capitalizaciones', 203, 3, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (206, '3110103', 'Capital Adicional', 203, 3, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (207, '31102', 'Reservas', 202, 3, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (208, '312', 'Resultados', 201, 3, 3, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (209, '31201', 'Ejercicios Anteriores', 208, 3, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (210, '31202', 'Resultados Presente Ejercicio', 208, 3, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (211, '3120201', 'Utilidad Y/O Perdida Presente Ejercicio', 210, 3, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (212, '4', 'Ingresos', null, 4, 1, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (213, '41', 'Operacionales', 212, 4, 2, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (214, '411', 'Ventas Netas', 213, 4, 3, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (215, '41101', 'Ventas', 214, 4, 4, 1, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (216, '4110101', 'Ventas Gravada 12%', 215, 4, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (217, '411010101', 'Venta De Carros Tarifa 12%', 216, 4, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (218, '411010103', 'Venta De Repuestos 12%', 216, 4, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (219, '411010104', 'Venta De Servicios 12%', 216, 4, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (220, '4110102', 'Ventas Tarifa 0%', 215, 4, 5, 1, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (221, '4110103', 'Comision Ventas', 215, 4, 5, 1, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (222, '4110104', 'Descuentos en Compras', 215, 4, 5, 1, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (223, '42', 'Ingresos Financieros', 212, 4, 2, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (224, '421', 'Ingresos Financieros Netos', 223, 4, 3, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (225, '42101', 'Intereses Ganados', 224, 4, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (226, '4210101', 'Intereses Ganados Por Financiamiento', 225, 4, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (227, '4210102', 'Intereses Ganados Por Mora', 225, 4, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (228, '4210103', 'Intereses En Inversiones Bancarias', 225, 4, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (229, '4210104', 'Intereses Otros', 225, 4, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (230, '42102', 'Descuento En Intereses', 224, 4, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (231, '4210201', 'Descuento Por Intereses', 230, 4, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (232, '4210202', 'Descuento Por Liquidacion Cuentas', 230, 4, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (233, '43', 'No Operacionales', 212, 4, 2, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (234, '431', 'Otros Ingresos', 233, 4, 3, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (235, '43101', 'Ingresos Ocasionales', 234, 4, 4, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (236, '4310101', 'Ingreso Por Administrativos', 235, 4, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (237, '4310102', 'Otros Ingresos Ocasionales', 235, 4, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (238, '5', 'Gastos y Costos', null, 5, 1, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (239, '51', 'Gastos Operacionales', 238, 5, 2, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (240, '511', 'Gastos Administracion', 239, 5, 3, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (241, '51101', 'Gastos Administrativos', 240, 5, 4, 0, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (242, '5110101', 'Remuneraciones ', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (243, '511010101', 'Sueldos Y Salarios', 242, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (244, '511010102', 'Horas Extras', 242, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (245, '511010103', 'Comisiones', 242, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (246, '511010104', 'Bonos', 242, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (247, '5110102', 'Beneficios Sociales', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (248, '511010201', 'Decimo Tercer Sueldo', 247, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (249, '511010202', 'Decimo Cuarto Sueldo', 247, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (250, '511010203', 'Vacaciones', 247, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (251, '511010204', 'Fondo De Reserva', 247, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (252, '511010205', 'Aporte Patronal', 247, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (253, '511010206', 'Indeminizaciones', 247, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (254, '511010207', 'Participacion De Trabajadores', 247, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (255, '5110103', 'Otros Gastos Del Personal', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (256, '511010301', 'Uniformes Y Ropa De Trabajo', 255, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (257, '511010302', 'Cursos Y Entrenamiento', 255, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (258, '511010303', 'Botellones De Agua', 255, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (259, '511010304', 'Agasajos Y Breaks', 255, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (260, '511010305', 'Publicidad Contrataciones', 255, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (261, '511010306', 'Movilizacion', 255, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (262, '5110104', 'Servicios Externos', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (263, '5110105', 'Arrendamiento De Inmuebles', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (264, '5110106', 'Mantenimiento Y Reparaciones', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (265, '511010601', 'Mantenimiento Edificios', 264, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (266, '511010602', 'Mantenimiento Vehiculos Afijo', 264, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (267, '511010603', 'Mantenimiento Equipos Computacion', 264, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (268, '511010604', 'Mantenimiento Equipos Oficina', 264, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (269, '511010605', 'Mantenimiento Muebles Ofic.', 264, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (270, '511010606', 'Mantenimiento Equipos De Taller', 264, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (271, '5110107', 'Combustibles', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (272, '5110108', 'Promocion Y Publicidad', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (273, '5110109', 'Suministros Y Materiales', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (274, '511010901', 'Suministros De Oficina', 273, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (275, '511010902', 'Suministros De Limpieza', 273, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (276, '5110110', 'Transporte', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (277, '511011001', 'Envio De Encomiendas', 276, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (278, '511011002', 'Fletes', 276, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (279, '5110111', 'Provisiones Juvilacion Patronal', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (280, '5110112', 'Provisiones Desahucio', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (281, '5110113', 'Provision Cuentas Incobrables', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (282, '5110114', 'Otras Provisiones', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (283, '5110115', 'Perdida En La Venta De Activos', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (284, '5110116', 'Otras Perdidas', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (285, '5110117', 'Seguros Y Reaseguros', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (286, '511011701', 'Seguro De Edificios', 285, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (287, '511011702', 'Seguros  Vehiculos', 285, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (288, '511011703', 'Soat', 285, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (289, '511011704', 'Perdidas Por Siniestros', 285, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (290, '511011705', 'Seguros Varios', 285, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (291, '5110118', 'Gastos De Gestion', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (292, '5110119', 'Impuestos', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (293, '511011901', 'Matriculas De Vehiculos', 292, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (294, '5110120', 'Gastos De Viaje', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (295, '5110121', 'Iva Que Se Carga Al Gasto', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (296, '5110122', 'Depreciaciones Activos Fijos', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (297, '511012201', 'Depreciacion De Edificios', 296, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (298, '511012202', 'Depreciacion De Vehiculos', 296, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (299, '511012203', 'Depreciacion De Equi De Compu', 296, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (300, '511012204', 'Depreciacion De Equipo Oficina', 296, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (301, '511012205', 'Depreciacion De Muebles Y Ense', 296, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (302, '511012206', 'Depreciacion Equipos De Taller', 296, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (303, '5110123', 'Amortizaciones', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (304, '5110124', 'Servicios Publicos', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (305, '511012401', 'Energia Electrica', 304, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (306, '511012402', 'Agua ', 304, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (307, '511012403', 'Telefono', 304, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (308, '5110125', 'Pagos Por Otros Servicios', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (309, '511012501', 'Consumo De Celular', 308, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (310, '511012502', 'Consumo De Internet', 308, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (311, '511012503', 'Frecuencia De Radio', 308, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (312, '511012504', 'Servicio De Parqueo', 308, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (313, '511012505', 'Servicio Buro De Credito', 308, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (314, '511012506', 'Otros Servicios (Liq/Comp)', 308, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (315, '511012507', 'Lavado De Vehiculos', 308, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (316, '5110126', 'Pagos Por Otros Bienes', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (317, '511012601', 'Otros Gastos Varios', 216, 5, 6, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (318, '5110127', 'Gastos Por Intermediacion', 241, 5, 5, 0, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (319, '51102', 'Compras', 240, 5, 4, 1, 1);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (320, '52', 'Gastos Financieros', 238, 5, 2, 1, 2);
insert into CONTA_CUENTAS (id_cuenta, codigo_cuenta, nombre_cuenta, fk_id_cuenta, id_tipocuenta_fk, id_nivel_fk, est_enlace_cuenta, id_ctamov_fk)
values (321, '53', 'Gasto No Operacional', 238, 5, 2, 0, 2);
commit;
prompt Done.
