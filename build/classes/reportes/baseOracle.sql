/*
ARCHIVO MODIFICADO Jueves >>>>>>>> 19-agosto 2018
Contiene de:

		analisis
		fichas
		medidas
		persona
		usuarios
		

=========================================

*/


connect system/manager@xe

prompt ************************BORRADO DE LOS USUARIOS****************...

ALTER SYSTEM SET PROCESSES=150 SCOPE=SPFILE;
drop user usr_troyagym cascade;  

prompt ************************BORRADO DE LOS PERFILES Y TABLESPACES****************...

drop profile prf_usr_troyagym cascade;  
drop tablespace tbs_usr_troyagym_p including contents and datafiles; 
drop tablespace tbs_usr_troyagym_t including contents and datafiles; 
drop tablespace tbs_usr_troyagym_inx including contents and datafiles;


 
prompt ************************CREACION DE LOS TABLESPACES****************...


create tablespace tbs_usr_troyagym_p   
datafile 'C:/troyagym/tablespaces/tbs_usr_troyagym_p.dbf'   
size 32m   
autoextend on   
next 32m maxsize 9048m ;   

create temporary tablespace tbs_usr_troyagym_t  
tempfile 'C:/troyagym/tablespaces/tbs_usr_troyagym_t.dbf'   
size 32m 
autoextend on    
next 32m maxsize 9048m ;

prompt ************************CREACION DE LOS PERFILES****************...

create tablespace tbs_usr_transmendez_inx   datafile 'C:/troyagym/tablespaces/tbs_usr_troyagym_inx.dbf'   size 32m   autoextend on   next 32m maxsize 4048m ;
 CREATE PROFILE prf_usr_troyagym LIMIT 
   SESSIONS_PER_USER          UNLIMITED 
   CPU_PER_SESSION            UNLIMITED 
   CPU_PER_CALL               3000 
   CONNECT_TIME               45 
   LOGICAL_READS_PER_SESSION  DEFAULT 
   LOGICAL_READS_PER_CALL     1000
   PRIVATE_SGA                15K
   COMPOSITE_LIMIT            5000000;

prompt ************************CREACION DE LOS USUARIOS****************...

CREATE USER usr_troyagym IDENTIFIED BY luisnux1606 
default tablespace tbs_usr_troyagym_p  
temporary tablespace tbs_usr_troyagym_t  
profile prf_usr_troyagym;

GRANT CONNECT, RESOURCE TO usr_troyagym;
GRANT DBA TO usr_troyagym;

connect usr_troyagym/luisnux1606



-- Create sequences Compras Retenciones Imp -------------------------------------------------


CREATE SEQUENCE analisis_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

CREATE SEQUENCE ficha_id_seq
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

CREATE SEQUENCE usuarios_id_seq
 INCREMENT BY 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
 ORDER
/

-- Create tables section -------------------------------------------------

-- Table analisis

CREATE TABLE Analisis(
  id_ana Number NOT NULL,
  fecha_ana Varchar2(350 ),
  exesoGrasa_ana Number(10,2),
  exesoLiquido_ana Number(10,2),
  exesoTotal_ana Number(10,2),
  recomendacionPesas_ana Varchar2(500 ),
  recomendacionCardio_ana Varchar2(500 ),
  recomendacionFuncional_ana Varchar2(500 ),
  ESTADO_ANA Number  
)
TABLESPACE tbs_usr_troyagym_p
/

-- Add keys for table com_Cabeceras

ALTER TABLE analisis ADD CONSTRAINT Key1 PRIMARY KEY (id_ana)
/

-- Table medidas

CREATE TABLE medidas(
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
  ESTADO_MED Number 
)
TABLESPACE tbs_usr_troyagym_p
/

-- Create indexes for table medidas

CREATE INDEX idx_id_med ON medidas(id_med)
/

-- Add keys for table medidas

ALTER TABLE medidas ADD CONSTRAINT Key2 PRIMARY KEY (id_med)
/

-- Table Persona

CREATE TABLE Persona(
  id_per Number NOT NULL,
  ced_per Varchar2(350 ),
  nom_per Varchar2(350 ),
  ape_per Varchar2(350 ),
  nroFono_per Varchar2(350 ),
  edad_per Number,
  fechanac_per Varchar2(350 ),
  genero_per Varchar2(350 ),
  mail_per Varchar2(350 ),
  ESTADO_PER Number   
)
TABLESPACE tbs_usr_troyagym_p
/

-- Create indexes for table ret_Cabeceras

CREATE INDEX idx_id_per ON Persona(id_per)
/

-- Add keys for table ret_Cabeceras

ALTER TABLE Persona ADD CONSTRAINT Key3 PRIMARY KEY (id_per)
/

-- Table ficha

CREATE TABLE ficha(
  id_ficha Number NOT NULL,
  fechaIni_ficha Varchar2(350),
  fechaFin_ficha Varchar2(350),
  valPago_ficha Number(10,2),
  valPendiente_ficha Number(10,2),
  concepto_ficha Varchar2(350 ),
  Persona_id_per Number,
  Analisis_id_ana Number,
  Medidas_id_med Number,
  ESTADO_FICHA Number 
)
TABLESPACE tbs_usr_troyagym_p
/

CREATE INDEX idx_id_ficha ON ficha (id_ficha)
/

-- Add keys for table ficha

ALTER TABLE ficha ADD CONSTRAINT id_ficha_pk PRIMARY KEY (id_ficha)
/

-- Create relationships section  ------------------------------------------------- 

ALTER TABLE ficha ADD CONSTRAINT Relationship2 FOREIGN KEY (Persona_id_per) REFERENCES Persona (id_per)
/

ALTER TABLE ficha ADD CONSTRAINT Relationship3 FOREIGN KEY (Analisis_id_ana) REFERENCES Analisis (id_ana)
/

ALTER TABLE ficha ADD CONSTRAINT Relationship4 FOREIGN KEY (Medidas_id_med) REFERENCES medidas(id_med)
/


-- inserts

insert into Persona(id_per,ced_per,nom_per, ape_per, nroFono_per,edad_per, fechaNac_per, genero_per, mail_per,ESTADO_PER )
values (persona_id_seq.NEXTVAL,'999999999','anonimo', 'anonimo', 'anonimo', '99999999', 9, '999999999', '  ',1);

insert into Analisis(id_ana,fecha_ana,exesoGrasa_ana,exesoLiquido_ana,exesoTotal_ana,recomendacionPesas_ana,recomendacionCardio_ana,recomendacionFuncional_ana,ESTADO_ANA)
values (analisis_id_seq.NEXTVAL, '999999999', 9, 9, 9, '9', '9', '999999999',1);

insert into Medidas(id_med,fecha_med,peso_med,estatura_med,edad_med,nroHijos_med,pecho_med,abdomenAlto_med,cintura_med,abdomenBajo_med,cadera_med,pierna_med,pantorrilla_med,brazo_med,antebrazo_med,cuello_med,espalda_med,porcentajeGrasa_med,porcentajeKlgs_med,ESTADO_MED )
values (medidas_id_seq.NEXTVAL, '999999999', 9, 9, 999, 9, 9, 9,9,9,9,9,9,9,9,9,9,9,9,1);

commit;
