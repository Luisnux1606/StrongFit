/*
ARCHIVO MODIFICADO Jueves >>>>>>>> 9-mayo2019
Contiene:
1 Factura
1.1 Detalle
1.2 Membresia
1.1.1 Producto
1.1.2 Categoria
1.3 Persona
1. Ficha
1.1 Analisis
1.2 Medidas
=========================================
*/


connect system/manager@xe

prompt ************************BORRADO DE LOS USUARIOS****************...
ALTER SYSTEM SET PROCESSES=150 SCOPE=SPFILE;
drop user usr_strongfit cascade;  

prompt ************************BORRADO DE LOS PERFILES Y TABLESPACES****************...
drop profile prf_usr_strongfit cascade;  
drop tablespace tbs_usr_strongfit_p including contents and datafiles; 
drop tablespace tbs_usr_strongfit_t including contents and datafiles; 
drop tablespace tbs_usr_strongfit_inx including contents and datafiles;


prompt ************************CREACION DE LOS TABLESPACES****************...
create tablespace tbs_usr_strongfit_p   
datafile 'C:/strongfit/tablespaces/tbs_usr_strongfit_p.dbf'   
size 32m   
autoextend on   
next 32m maxsize 9048m ;   

create temporary tablespace tbs_usr_strongfit_t  
tempfile 'C:/strongfit/tablespaces/tbs_usr_strongfit_t.dbf'   
size 32m 
autoextend on    
next 32m maxsize 9048m ;

prompt ************************CREACION DE LOS PERFILES****************...
create tablespace tbs_usr_strongfit_inx   datafile 'C:/strongfit/tablespaces/tbs_usr_strongfit_inx.dbf'   size 32m   autoextend on   next 32m maxsize 4048m ;
CREATE PROFILE prf_usr_strongfit LIMIT 
   SESSIONS_PER_USER          UNLIMITED 
   CPU_PER_SESSION            UNLIMITED 
   CPU_PER_CALL               3000 
   CONNECT_TIME               45 
   LOGICAL_READS_PER_SESSION  DEFAULT 
   LOGICAL_READS_PER_CALL     1000
   PRIVATE_SGA                15K
   COMPOSITE_LIMIT            5000000;


prompt ************************CREACION DE LOS USUARIOS****************...
CREATE USER usr_strongfit IDENTIFIED BY strongfit
default tablespace tbs_usr_strongfit_p  
temporary tablespace tbs_usr_strongfit_t  
profile prf_usr_strongfit;

GRANT CONNECT, RESOURCE TO usr_strongfit;
GRANT DBA TO usr_strongfit;

connect usr_strongfit/strongfit



-- Create sequences Compras Retenciones Imp -------------------------------------------------
CREATE SEQUENCE ficha_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

CREATE SEQUENCE analisis_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

CREATE SEQUENCE medidas_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

CREATE SEQUENCE persona_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

CREATE SEQUENCE membresia_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

CREATE SEQUENCE factura_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

CREATE SEQUENCE detalle_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

CREATE SEQUENCE producto_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

CREATE SEQUENCE categoria_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

CREATE SEQUENCE entrenTiempo_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/
CREATE SEQUENCE entrenamiento_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/
CREATE SEQUENCE iva_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

-- Create sequences CONTABILIDAD -------------------------------------------------
-- Create sequences CONTABILIDAD -------------------------------------------------
CREATE SEQUENCE TIPO_ASIENTO_SEQ_ID
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE ASIENTO_SEQ_ID
 INCREMENT BY 1
 MAXVALUE 1000000000
 MINVALUE 0
 CACHE 20
/

CREATE SEQUENCE TIPODOC_SEQ_ID
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE DOCREFERENCIA_SEQ_ID
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE ASIENTO_CUENTA_SEQ_ID
 INCREMENT BY 1
 NOMAXVALUE
 MINVALUE 0
 CACHE 20
/

CREATE SEQUENCE TIPOCUENTA_SEQ_ID
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE NIVEL_SEQ_ID
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE CTA_MOVIMIENTOS_SEQ_ID
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE CUENTAS_SEQ_ID
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE MAYORDETALLE_SEQ_ID
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

-- Create tables section -------------------------------------------------
CREATE TABLE Analisis(
  id_ana Number NOT NULL,
  fecha_ana Varchar2(350 ),
  excesoGrasa_ana Number(10,2),
  excesoLiquido_ana Number(10,2),
  excesoTotal_ana Number(10,2),
  recomendacionPesas_ana Varchar2(500 ),
  recomendacionCardio_ana Varchar2(500 ),
  recomendacionFuncional_ana Varchar2(500 ),
  estado_ana Number  
)
TABLESPACE tbs_usr_strongfit_p
/
-- Create indexes for table analisis
CREATE INDEX idx_id_ana ON analisis(id_ana)
/
-- Add keys for table analisis
ALTER TABLE Analisis ADD CONSTRAINT Key1 PRIMARY KEY (id_ana)
/

CREATE TABLE Medidas(
  id_med Number NOT NULL,
  fecha_med Varchar2(350 ),
  peso_med Number(10,2),
  estatura_med Number(10,2),
  edad_med Number,
  nroHijos_med Number,
  pecho_med Number(10,2),
  abdomenAlto_med Number(10,2),
  cintura_med Number(10,2),
  abdomenBajo_med Number(10,2),
  cadera_med Number(10,2),
  pierna_med Number(10,2),
  pantorrilla_med Number(10,2),
  brazo_med Number(10,2),
  antebrazo_med Number(10,2),
  cuello_med Number(10,2),
  espalda_med Number(10,2),
  porcentajeGrasa_med Number(10,2),
  porcentajeKlgs_med Number(10,2),
  estado_med Number 
)
TABLESPACE tbs_usr_strongfit_p
/
-- Create indexes for table medidas
CREATE INDEX idx_id_med ON Medidas (id_med)
/
-- Add keys for table medidas
ALTER TABLE Medidas ADD CONSTRAINT pk_id_med PRIMARY KEY (id_med)
/

CREATE TABLE Persona(
  id_per Number NOT NULL,
  ced_per Varchar2(45 ),
  nom_per Varchar2(350 ),
  ape_per Varchar2(350 ),
  nroFono_per Varchar2(45 ),
  edad_per Number,
  fechaNac_per Varchar2(350 ),
  genero_per Varchar2(45 ),
  mail_per Varchar2(350 ),
  huella_per Varchar2(350 ),
  estado_per Number   
)
TABLESPACE tbs_usr_strongfit_p
/

-- Create indexes for table persona
CREATE INDEX idx_id_per ON Persona(id_per)
/

-- Add keys for table persona
ALTER TABLE Persona ADD CONSTRAINT pk_id_per PRIMARY KEY (id_per)
/

-- Add keys for table EntrenamientoTiempo
CREATE TABLE EntrenTiempo(
  id_entTmp Number NOT NULL,
  descripcion_entTiempo Varchar2(45 ),
  costo_entTiempo Number(10,2), 
  estado_enTiempo Number
)
TABLESPACE tbs_usr_strongfit_p
/

-- Create indexes for table EntrenamientoTiempo
CREATE INDEX idx_id_entTmp ON EntrenTiempo(id_entTmp)
/

-- Add keys for table EntrenamientoTiempo
ALTER TABLE EntrenTiempo ADD CONSTRAINT pk_id_entTiempo PRIMARY KEY (id_entTmp)
/

--Add keys for table entrenamiento
CREATE TABLE Entrenamiento(
  id_ent Number NOT NULL,
  fechaIni_ent Varchar2(45 ),
  fechaFin_ent Varchar2(350 ),  
  EntrenTiempo_id_entTmp Number,
  Persona_id_per Number,
  estado_ent Number
)
TABLESPACE tbs_usr_strongfit_p
/

-- Create indexes for table entrenamiento
CREATE INDEX idx_id_ent ON Entrenamiento(id_ent)
/

-- Add keys for table entrenamiento
ALTER TABLE Entrenamiento ADD CONSTRAINT pk_id_ent PRIMARY KEY (id_ent)
/
-- Add keys for table entrenamiento
ALTER TABLE Entrenamiento ADD CONSTRAINT fk_id_ent FOREIGN KEY (EntrenTiempo_id_entTmp) REFERENCES EntrenTiempo(id_entTmp)
/
-- Add keys for table entrenamiento
ALTER TABLE Entrenamiento ADD CONSTRAINT fk_Ent_id_per FOREIGN KEY (Persona_id_per) REFERENCES Persona(id_per)
/


CREATE TABLE Ficha(
  id_ficha Number NOT NULL,
  fecha_ficha Varchar2(350 ),
  Persona_id_per Number,
  Analisis_id_ana Number,
  Medidas_id_med Number,
  estado_ficha Number
)
TABLESPACE tbs_usr_strongfit_p
/

-- Create indexes for table ficha
CREATE INDEX idx_id_ficha ON Ficha (id_ficha)
/

-- Add keys for table ficha
ALTER TABLE Ficha ADD CONSTRAINT pk_id_ficha PRIMARY KEY (id_ficha)
/

-- Create relationships section  ------------------------------------------------- 
ALTER TABLE Ficha ADD CONSTRAINT fk_id_per FOREIGN KEY (Persona_id_per) REFERENCES Persona(id_per)
/

ALTER TABLE Ficha ADD CONSTRAINT fk_id_ana FOREIGN KEY (Analisis_id_ana) REFERENCES Analisis (id_ana)
/

ALTER TABLE Ficha ADD CONSTRAINT fk_id_med FOREIGN KEY (Medidas_id_med) REFERENCES Medidas (id_med)
/



CREATE TABLE Membresia(
  id_memb Number NOT NULL,
  descripcion_memb Varchar2(350 ),
  valor_memb Number(10,2),
  dscto_memb Number(10,2),
  estado_memb Number   
)
TABLESPACE tbs_usr_strongfit_p
/
-- Create indexes for table membresia
CREATE INDEX idx_id_memb ON Membresia(id_memb)
/
-- Add keys for table membresia
ALTER TABLE Membresia ADD CONSTRAINT pk_id_memb PRIMARY KEY (id_memb)
/

CREATE TABLE IVAS(
  id_ivas Number NOT NULL,
  val_ivas Number(10,2),
  estado_ivas Number
)
TABLESPACE tbs_usr_strongfit_p
/
CREATE INDEX idx_id_ivas ON IVAS(id_ivas)
/
-- Add keys for table membresia
ALTER TABLE IVAS ADD CONSTRAINT pk_id_ivas PRIMARY KEY (id_ivas)
/


CREATE TABLE FacturaCabecera(
  id_facCab Number NOT NULL,
  fecha_facCab Varchar2(350 ),
  num_facCab Varchar2(10),
  valPagar_facCab Number(10,2),          --1. valpagar
  subTotal_facCab Number(10,2),   --3. val+dscto
  total_facCab Number(10,2),        --5. total+iva
  valPendiente_facCab Number(10,2),  --7. valPendiente
  valCancelo_facCab Number(10,2),     --6. valCancelo
  Persona_id_per Number,
  Membresia_id_memb Number,        --2. descuento
  Ivas_id_ivas Number,            --4. iva
  estado_facCab Number 
)
TABLESPACE tbs_usr_strongfit_p
/
-- Add keys for table factura
-- Create indexes for table factura
CREATE INDEX idx_id_fac ON FacturaCabecera(id_facCab)
/
-- Add keys for table factura
ALTER TABLE FacturaCabecera ADD CONSTRAINT pk_id_fac PRIMARY KEY (id_facCab)
/
-- Create relationships section  ------------------------------------------------- 
ALTER TABLE FacturaCabecera ADD CONSTRAINT fk_id_pers FOREIGN KEY (Persona_id_per) REFERENCES Persona (id_per)
/
ALTER TABLE FacturaCabecera ADD CONSTRAINT fk_id_memb FOREIGN KEY (Membresia_id_memb) REFERENCES Membresia (id_memb)
/
ALTER TABLE FacturaCabecera ADD CONSTRAINT fk_id_ivas FOREIGN KEY (Ivas_id_ivas) REFERENCES IVAS (id_ivas)
/

CREATE TABLE Categoria(
  id_cat Number NOT NULL,
  tipo_cat  Varchar2(45 ),
  estado_cat Number 
)
TABLESPACE tbs_usr_strongfit_p
/
-- Create indexes for table categoria
CREATE INDEX idx_id_cat ON Categoria(id_cat)
/
-- Add keys for table categoria
ALTER TABLE Categoria ADD CONSTRAINT pk_id_cat PRIMARY KEY (id_cat)
/

CREATE TABLE Producto(
  id_prod Number NOT NULL,
  descripcion_prod  Varchar2(350 ),
  precio_prod Number(10,2),
  Categoria_id_cat Number,
  estado_prod Number 
)
TABLESPACE tbs_usr_strongfit_p
/
-- Create indexes for table producto
CREATE INDEX idx_id_prod ON Producto(id_prod)
/
-- Add keys for table producto
ALTER TABLE Producto ADD CONSTRAINT pk_id_prod PRIMARY KEY (id_prod)
/
-- Create relationships section  ------------------------------------------------- 
ALTER TABLE Producto ADD CONSTRAINT fk_id_cat FOREIGN KEY (Categoria_id_cat) REFERENCES Categoria (id_cat)
/

CREATE TABLE FacturaDetalle(
  id_facDet Number NOT NULL,
  cantidad_facDet Number,
  descripcion_facDet Varchar2(50),
  valUnitario_facDet Number(10,2),
  vTotal_facDet Number(10,2),
  Producto_id_prod Number, 
  Factura_id_fac Number,
  estado_facDet Number 
)
TABLESPACE tbs_usr_strongfit_p
/
-- Create indexes for table detalle
CREATE INDEX idx_id_det ON FacturaDetalle(id_facDet)
/
-- Add keys for table detalle
ALTER TABLE FacturaDetalle ADD CONSTRAINT pk_id_det PRIMARY KEY (id_facDet)
/
-- Create relationships section  ------------------------------------------------- 
ALTER TABLE FacturaDetalle ADD CONSTRAINT fk_id_prod FOREIGN KEY (Producto_id_prod) REFERENCES Producto (id_prod)
/
-- Create relationships section  ------------------------------------------------- 
ALTER TABLE FacturaDetalle ADD CONSTRAINT fk_id_fac FOREIGN KEY (Factura_id_fac) REFERENCES FacturaCabecera (id_facCab)
/


-- Create tables CONTABILIDAD -------------------------------------------------

CREATE TABLE CONTA_TIPO_ASIENTOS(
  ID_TIPOASIENTO Number,
  DESCRIPCION_TIPOASIENTO Varchar2(100 )
)
TABLESPACE tbs_usr_strongfit_p
/
ALTER TABLE CONTA_TIPO_ASIENTOS ADD CONSTRAINT ID_TIPOASIENTO_PK PRIMARY KEY (ID_TIPOASIENTO)
/

CREATE TABLE CONTA_ASIENTOS(
  ID_ASIENTO Number CONSTRAINT SYS_C004382 NOT NULL,
  CONCEPTO_ASIENTO Varchar2(300 ),
  NUMERO_ASIENTO Number,
  FECHA_ASIENTO Date,
  ESTADO_ASIENTO number default 1,
  ID_TIPOASIENTO Number
)
TABLESPACE tbs_usr_strongfit_p
/
ALTER TABLE CONTA_ASIENTOS ADD CONSTRAINT ASIENTO_PK PRIMARY KEY (ID_ASIENTO)
/
ALTER TABLE CONTA_ASIENTOS ADD CONSTRAINT FK_Asiento_TipAsiento FOREIGN KEY (ID_TIPOASIENTO) REFERENCES CONTA_TIPO_ASIENTOS (ID_TIPOASIENTO)
/

CREATE TABLE CONTA_TIPODOCS(
  ID_TIPODOC Number CONSTRAINT SYS_C004394 NOT NULL,
  DESCRIPCION_TIPODOC Varchar2(100 )
)
TABLESPACE tbs_usr_strongfit_p
/
ALTER TABLE CONTA_TIPODOCS ADD CONSTRAINT TIPODOC_PK PRIMARY KEY (ID_TIPODOC)
/

CREATE TABLE CONTA_DOC_REFERENCIAS(
  ID_DOCREFERENCIA Number CONSTRAINT SYS_C004390 NOT NULL,
  NUM_DOCREFERENCIA Varchar2(100 ),
  DESCRIPCION_DOCREFERENCIA Varchar2(100 ),
  ID_TIPODOC_FK Number
)
TABLESPACE tbs_usr_strongfit_p
/
ALTER TABLE CONTA_DOC_REFERENCIAS ADD CONSTRAINT DOCREFERENCIA_PK PRIMARY KEY (ID_DOCREFERENCIA)
/
ALTER TABLE CONTA_DOC_REFERENCIAS ADD CONSTRAINT FK_DocRef_TipDoc FOREIGN KEY (ID_TIPODOC_FK) REFERENCES CONTA_TIPODOCS (ID_TIPODOC)
/

CREATE TABLE CONTA_TIPOCUENTAS(
  ID_TIPOCUENTA Number CONSTRAINT SYS_C004388 NOT NULL,
  DESCRIPCION_TIPOCUENTA Varchar2(100 )
)
TABLESPACE tbs_usr_strongfit_p
/
ALTER TABLE CONTA_TIPOCUENTAS ADD CONSTRAINT ID_TIPOCUENTA_PK PRIMARY KEY (ID_TIPOCUENTA)
/

CREATE TABLE CONTA_NIVELES(
  ID_NIVEL Number CONSTRAINT SYS_NIVEL NOT NULL,
  VALOR_NIVEL Number,
  DESCRIPCION_NIVEL Varchar2(100)
)
TABLESPACE tbs_usr_strongfit_p
/
ALTER TABLE CONTA_NIVELES ADD CONSTRAINT ID_NIVEL_PK PRIMARY KEY (ID_NIVEL)
/

CREATE TABLE CONTA_CTA_MOVIMIENTOS(
  ID_CTAMOV Number CONSTRAINT SYS_CTAMOV NOT NULL,
  CODIGO_CTAMOV Varchar2(100 ),
  DESCRIPCION_CTAMOV Varchar2(100 )
  )
TABLESPACE tbs_usr_strongfit_p
/
ALTER TABLE CONTA_CTA_MOVIMIENTOS ADD CONSTRAINT CTA_MOVIMIENTOS_PK PRIMARY KEY (ID_CTAMOV)
/

CREATE TABLE CONTA_CUENTAS(
  ID_CUENTA Number CONSTRAINT SYS_C004384 NOT NULL,
  CODIGO_CUENTA Varchar2(100 ),
  NOMBRE_CUENTA Varchar2(100 ),
  FK_ID_CUENTA Number,
  ID_TIPOCUENTA_FK Number,
  ID_NIVEL_FK Number,
  EST_ENLACE_CUENTA Number,
  ID_CTAMOV_FK Number
)
TABLESPACE tbs_usr_strongfit_p
/
ALTER TABLE CONTA_CUENTAS ADD CONSTRAINT CUENTA_PK PRIMARY KEY (ID_CUENTA)
/
ALTER TABLE CONTA_CUENTAS ADD CONSTRAINT FK_Ctas_Movimientos FOREIGN KEY (ID_CTAMOV_FK) REFERENCES CONTA_CTA_MOVIMIENTOS (ID_CTAMOV)
/
ALTER TABLE CONTA_CUENTAS ADD CONSTRAINT FK_Cuentas_Niveles FOREIGN KEY (ID_NIVEL_FK) REFERENCES CONTA_NIVELES (ID_NIVEL)
/
ALTER TABLE CONTA_CUENTAS ADD CONSTRAINT FK_Recursiva_Cuentas FOREIGN KEY (FK_ID_CUENTA) REFERENCES CONTA_CUENTAS (ID_CUENTA)
/
ALTER TABLE CONTA_CUENTAS ADD CONSTRAINT FK_Cuentas_TipCuentas FOREIGN KEY (ID_TIPOCUENTA_FK) REFERENCES CONTA_TIPOCUENTAS (ID_TIPOCUENTA)
/


CREATE TABLE CONTA_ASIENTO_CUENTAS(
  ID_ASIENTOCUENTA Number CONSTRAINT SYS_C004386 NOT NULL,
  FECHA_ASIENTOCUENTA Date,
  DEBE_ASIENTOCUENTA Number(10,2),
  HABER_ASIENTOCUENTA Number(10,2),
  ID_CUENTA_AUX_ASIENTOCUENTA Number(10,2),
  ID_ASIENTO_FK Number,
  ID_CUENTA_FK Number,
  ID_DOCREFERENCIA_FK Number,
  ID_CTA_ELEGIDA_ASIENTOCUENTA Number,
  NOTAS_ASIENTOCUENTA Varchar2(100 ),
  ordena_dh_asientocuenta Varchar2(100 )
)
TABLESPACE tbs_usr_strongfit_p
/
ALTER TABLE CONTA_ASIENTO_CUENTAS ADD CONSTRAINT ASIENTO_CUENTA_PK PRIMARY KEY (ID_ASIENTOCUENTA)
/
ALTER TABLE CONTA_ASIENTO_CUENTAS ADD CONSTRAINT FK_AsientoCuenta_Asientos FOREIGN KEY (ID_ASIENTO_FK) REFERENCES CONTA_ASIENTOS (ID_ASIENTO)
/
ALTER TABLE CONTA_ASIENTO_CUENTAS ADD CONSTRAINT FK_AsieCuentas_Cuentas FOREIGN KEY (ID_CUENTA_FK) REFERENCES CONTA_CUENTAS (ID_CUENTA)
/
ALTER TABLE CONTA_ASIENTO_CUENTAS ADD CONSTRAINT FK_AsientoCuentas_DocRef FOREIGN KEY (ID_DOCREFERENCIA_FK) REFERENCES CONTA_DOC_REFERENCIAS (ID_DOCREFERENCIA)
/

CREATE TABLE CONTA_MAYOR_DETALLES(
  ID_MAYDETALLE Number CONSTRAINT SYS_C004392 NOT NULL,
  FECHA_MAYDETALLE Date,
  DEBE_MAYDETALLE Number(10,2),
  HABER_MAYDETALLE Number(10,2),
  SALDO_MAYDETALLE Number(10,2),
  SALDO_CIERREASIENTO_MAYDETALLE Number(10,2),
  SALDO_DEBEBALCOMP_MAYDETALLE Number(10,2),
  SALDO_HABERBALCOMP_MAYDETALLE Number(10,2),
  TOTAL_DEBEBALCOMP_MAYDETALLE Number(10,2),
  TOTAL_HABERBALCOMP_MAYDETALLE Number(10,2),
  ASIENTOCIERRE_MAYDETALLE Varchar2(50 ),
  ID_CUENTA_FK Number
)
TABLESPACE tbs_usr_strongfit_p
/
ALTER TABLE CONTA_MAYOR_DETALLES ADD CONSTRAINT MAYOR_DETALLE_PK PRIMARY KEY (ID_MAYDETALLE)
/
ALTER TABLE CONTA_MAYOR_DETALLES ADD CONSTRAINT FK_MayDet_Cuentas FOREIGN KEY (ID_CUENTA_FK) REFERENCES CONTA_CUENTAS (ID_CUENTA)
/

CREATE TABLE CONTA_RETENCIONES(
  ID_RETENCION Number CONSTRAINT SYS_retencion NOT NULL,
  VALOR_RETENCION Number(10,2),
  DESCRIPCION_RETENCION Varchar2(100)
)
TABLESPACE tbs_usr_strongfit_p
/
ALTER TABLE CONTA_RETENCIONES ADD CONSTRAINT ID_RETENCION_PK PRIMARY KEY (ID_RETENCION)
/


-- inserts

insert into Persona(id_per,ced_per,nom_per, ape_per, nroFono_per,edad_per, fechaNac_per, genero_per, mail_per,ESTADO_PER )
values (persona_id_seq.NEXTVAL,'999999999','anonimo', 'anonimo', 'anonimo', '99999999', 9, '999999999', '  ',1);

insert into Analisis(id_ana,fecha_ana,excesoGrasa_ana,excesoLiquido_ana,excesoTotal_ana,recomendacionPesas_ana,recomendacionCardio_ana,recomendacionFuncional_ana,ESTADO_ANA)
values (analisis_id_seq.NEXTVAL, '999999999', 9, 9, 9, '9', '9', '999999999',1);

insert into Medidas(id_med,fecha_med,peso_med,estatura_med,edad_med,nroHijos_med,pecho_med,abdomenAlto_med,cintura_med,abdomenBajo_med,cadera_med,pierna_med,pantorrilla_med,brazo_med,antebrazo_med,cuello_med,espalda_med,porcentajeGrasa_med,porcentajeKlgs_med,ESTADO_MED )
values (medidas_id_seq.NEXTVAL, '999999999', 9, 9, 999, 9, 9, 9,9,9,9,9,9,9,9,9,9,9,9,1);

insert into Membresia(id_memb,descripcion_memb,dscto_memb,ESTADO_MEMB)
values (membresia_id_seq.NEXTVAL,'999999999',0,1);

insert into Membresia(id_memb,descripcion_memb,dscto_memb,ESTADO_MEMB)
values (membresia_id_seq.NEXTVAL,'diario',2,1);

insert into IVAS(id_ivas,val_ivas,estado_ivas)
values (iva_id_seq.NEXTVAL,0,1);

insert into entrentiempo(id_enttmp,descripcion_enttiempo,costo_enttiempo,estado_entiempo)
values (entrenTiempo_id_seq.NEXTVAL,'diario',2,1);

insert into Categoria(id_cat,tipo_cat,estado_cat)
values (categoria_id_seq.NEXTVAL,'servicio gimnasio',1);

insert into Categoria(id_cat,tipo_cat,estado_cat)
values (categoria_id_seq.NEXTVAL,'producto',1);

commit;
