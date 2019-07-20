create or replace procedure exportBlob is

   v_blob        BLOB;
   v_start       NUMBER             := 1;
   v_bytelen     NUMBER             := 2000;
   v_len         NUMBER;
   v_raw         RAW (2000);
   v_x           NUMBER;
   v_output      UTL_FILE.file_type;
   v_file_name   NUMBER;
BEGIN
     
   FOR i IN (SELECT DBMS_LOB.getlength (huella_per) v_len, id_per v_file_name,
                    huella_per v_blob
               FROM persona)
 
   LOOP
 
      v_output := UTL_FILE.fopen ('TEMP_DIR1', i.v_file_name || '.txt', 'wb', 32760);
      v_x := i.v_len;
      v_start := 1;
      v_bytelen := 2000;
        

      WHILE v_start < i.v_len AND v_bytelen > 0
      LOOP
      
         DBMS_LOB.READ (i.v_blob, v_bytelen, v_start, v_raw);
         UTL_FILE.put_raw (v_output, v_raw);
         DBMS_OUTPUT.PUT_LINE('Loop number = ' || i.v_len); 
         UTL_FILE.fflush (v_output);
         v_start := v_start + v_bytelen;
         v_x := v_x - v_bytelen;

         IF v_x < 2000
         THEN
            v_bytelen := v_x;
         END IF;
      END LOOP;

      UTL_FILE.fclose (v_output);
   END LOOP;
end exportBlob;
/
