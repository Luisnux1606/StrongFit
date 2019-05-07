/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Calculos {
    
     public static double getDscto(double val, double valDscto){
         double valTotal;
         valTotal = val   - (val * valDscto)/100;
             
       
        return valTotal;
     }
     
      public static double getDiferencia(double val, double val2){
         double valTotal;
         valTotal = val   - val2;
             
       
        return valTotal;
     }
      
      public static String getCurrentDate()
      {
        Date date = Calendar.getInstance().getTime();  
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        return today;
      }
    
      public static Date getCurrentDate2()
      {
        Date date = null;
         try {
              date = new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDate());
            
         } catch (ParseException ex) {
             Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
         }
          return date;
      }
}
