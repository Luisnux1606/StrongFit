/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
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
      
      public static double getDiffDaysToFinish(String finishDate)
      {
           double daysDiff = 0;
         try {
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
             Date currentDate = sdf.parse(getCurrentDate());
             Date fDate = sdf.parse(finishDate);
            
             
             long diffInMillies = Math.abs(fDate.getTime() - currentDate.getTime());
             long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
             
             daysDiff = setTwoDecimals(diff);
         } catch (ParseException ex) {
             Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
         }

      return daysDiff;
      }
      
      public static double setTwoDecimals(double num)
      {
        DecimalFormat df2 = new DecimalFormat("#.##");
        double input = num;

        return Double.parseDouble(df2.format(input));
      }
      
      public static boolean dateGreaterThanCurrent(String dateEnd)
      {
          boolean isTrue=false;
         try {
             
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
             Date date1 = sdf.parse(dateEnd);
             Date date2 = sdf.parse(getCurrentDate());
                                     
             if (date1.compareTo(date2) > 0) {
                 isTrue = true;
                 //System.out.println("Date1 is after Date2");
             } else if (date1.compareTo(date2) < 0) {
                 isTrue = false; 
                 //System.out.println("Date1 is before Date2");
             } else if (date1.compareTo(date2) == 0) {
               isTrue = true;
             } else {
                // System.out.println("How to get here?");
             }
         } catch (ParseException ex) {
             Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
         }
      return isTrue;
      }
      
      public static int getYearsFromDateOfBirth(String dateOfBirt)
      {
          
        String s = dateOfBirt;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
         try {
             d = sdf.parse(s);
         } catch (ParseException ex) {
             Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
         }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        LocalDate l1 = LocalDate.of(year, month, date);
        LocalDate now1 = LocalDate.now();
        Period diff1 = Period.between(l1, now1);
       
        return diff1.getYears();
      }
}
