create or replace procedure IMPORTBLOBINDI is
      src_file BFILE;
      dst_file BLOB;
      lgh_file BINARY_INTEGER;
      id_per_file NUMBER;
     begin
     
     FOR i IN (SELECT p.id_per id_per_file from persona p)
     LOOP
          DBMS_OUTPUT.PUT_LINE (i.id_per_file||'.txt');

          src_file := bfilename('TEMP_DIR1', i.id_per_file||'.txt'); 
          update persona set huella_per = EMPTY_BLOB() where id_per = i.id_per_file
           
          RETURNING huella_per INTO dst_file;
     
          DBMS_LOB.OPEN(src_file, dbms_lob.file_readonly);
          lgh_file := dbms_lob.getlength(src_file);
          IF lgh_file>0 THEN
            DBMS_LOB.LOADFROMFILE(dst_file, src_file, lgh_file);
            dbms_lob.close(src_file);
            commit;
          END IF;
          dbms_lob.close(src_file);
       END LOOP;   
     
end IMPORTBLOBINDI;
/
