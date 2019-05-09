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


connect system/root@xe

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

CREATE TABLE Factura(
  id_fac Number NOT NULL,
  fechaInicio_fac Varchar2(350 ),
  fechaFin_fac Varchar2(350 ),
  subTotal_fac Number(10,2),
  total_fac Number(10,2),
  valPendiente_fac Number(10,2),
  Persona_id_per Number,
  Membresia_id_memb Number,
  estado_fac Number 
)
TABLESPACE tbs_usr_strongfit_p
/
-- Add keys for table factura
-- Create indexes for table factura
CREATE INDEX idx_id_fac ON Factura(id_fac)
/
-- Add keys for table factura
ALTER TABLE Factura ADD CONSTRAINT pk_id_fac PRIMARY KEY (id_fac)
/
-- Create relationships section  ------------------------------------------------- 
ALTER TABLE Factura ADD CONSTRAINT fk_id_pers FOREIGN KEY (Persona_id_per) REFERENCES Persona (id_per)
/
ALTER TABLE Factura ADD CONSTRAINT fk_id_memb FOREIGN KEY (Membresia_id_memb) REFERENCES Membresia (id_memb)
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
  stock_prod Number,
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

CREATE TABLE Detalle(
  id_det Number NOT NULL,
  cantidad_det Number,
  total_det Number(10,2),
  Producto_id_prod Number, 
  Factura_id_fac Number,
  estado_det Number 
)
TABLESPACE tbs_usr_strongfit_p
/
-- Create indexes for table detalle
CREATE INDEX idx_id_det ON Detalle(id_det)
/
-- Add keys for table detalle
ALTER TABLE Detalle ADD CONSTRAINT pk_id_det PRIMARY KEY (id_det)
/
-- Create relationships section  ------------------------------------------------- 
ALTER TABLE Detalle ADD CONSTRAINT fk_id_prod FOREIGN KEY (Producto_id_prod) REFERENCES Producto (id_prod)
/
-- Create relationships section  ------------------------------------------------- 
ALTER TABLE Detalle ADD CONSTRAINT fk_id_fac FOREIGN KEY (Factura_id_fac) REFERENCES Factura (id_fac)
/


-- inserts

insert into Persona(id_per,ced_per,nom_per, ape_per, nroFono_per,edad_per, fechaNac_per, genero_per, mail_per,ESTADO_PER )
values (persona_id_seq.NEXTVAL,'999999999','anonimo', 'anonimo', 'anonimo', '99999999', 9, '999999999', '  ',1);

insert into Analisis(id_ana,fecha_ana,excesoGrasa_ana,excesoLiquido_ana,excesoTotal_ana,recomendacionPesas_ana,recomendacionCardio_ana,recomendacionFuncional_ana,ESTADO_ANA)
values (analisis_id_seq.NEXTVAL, '999999999', 9, 9, 9, '9', '9', '999999999',1);

insert into Medidas(id_med,fecha_med,peso_med,estatura_med,edad_med,nroHijos_med,pecho_med,abdomenAlto_med,cintura_med,abdomenBajo_med,cadera_med,pierna_med,pantorrilla_med,brazo_med,antebrazo_med,cuello_med,espalda_med,porcentajeGrasa_med,porcentajeKlgs_med,ESTADO_MED )
values (medidas_id_seq.NEXTVAL, '999999999', 9, 9, 999, 9, 9, 9,9,9,9,9,9,9,9,9,9,9,9,1);

insert into Membresia(id_memb,descripcion_memb,dscto_memb,ESTADO_MEMB)
values (membresia_id_seq.NEXTVAL,'diario',0,1);


commit;
