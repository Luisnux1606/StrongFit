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
import javax.swing.JFrame;
import javax.swing.JTable;
import vistas.VisFicha;
import vistas.VisMembresia;

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
             
       
        return getTwoDecimals(valTotal);
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
      
      
      public static double round(double value, int places) 
      {
        if (places < 0) throw new IllegalArgumentException();
            long factor = (long) Math.pow(10, places);
            value = value * factor;
            long tmp = Math.round(value);
        return (double) tmp / factor;
    }
     public static double calcularTotalDetalle(double num, double vU)
     {
         double total = num * vU;
         return total;
     }
     public static void calcularTotalDetalles(JTable table)
     {
         String valU;
         String num;
         double totDet;
         for (int i = 0; i <= table.getRowCount()-1; i++) {
             num = table.getValueAt(i, 0)+"";
             valU = table.getValueAt(i, 2)+"";
             
             
             if (Validaciones.isNumVoid10(num)!=0 && Validaciones.isNumVoid10(valU)!=0) {
                 totDet = calcularTotalDetalle(Validaciones.isNumVoid10(num),Validaciones.isNumVoid10(valU));
                 table.setValueAt(getTwoDecimals(totDet), i,3);
             }
         }
     } 
     
     public static double getTwoDecimals(double val)
     {
         return round(val,2);
     
     }
     public static double calcularValorPagar(JTable table,VisFicha visFicha)
     {
         String valTotDet;      
         double totDetalles = 0;
         
         for (int i = 0; i <= table.getRowCount()-1; i++) {
             valTotDet = table.getValueAt(i, 3)+"";
             
             if (Validaciones.isNumVoid10(valTotDet)!=0 ) {
                 totDetalles = totDetalles + Validaciones.isNumVoid10(valTotDet);
               
             }
         }
         return getTwoDecimals(totDetalles);
     } 
               
    public static double setDescuentoFromDetEnt(VisFicha visFicha)
    {
                          
        double dsctoMembtxt = Validaciones.isNumVoid10(visFicha.txtValDscto.getText());
        double valMasDscto = Calculos.getDscto(new Double(visFicha.txtValPagar.getText()).doubleValue(), dsctoMembtxt);
        
        return getTwoDecimals(valMasDscto);
    }
    
    public static void setDsctuentoFromMemb(VisMembresia visMemb,VisFicha visFicha)
    {
        double  dsctoMemb = Double.parseDouble(visMemb.tbl_membresias.getValueAt(visMemb.tbl_membresias.getSelectedRow(), 2)+"");                   
        visFicha.txtValDscto.setText(getTwoDecimals(dsctoMemb)+"");
    }
    
    public static double setTotalConIva(VisFicha visFicha)
    {
        double iva = Validaciones.isNumVoid10(visFicha.txtIVA.getText());
        double valConDscto = Validaciones.isNumVoid10(visFicha.txtValConDsctoFicha.getText());
        double totMasIva = ((valConDscto*iva)/100) +valConDscto;        
        return getTwoDecimals(totMasIva);
    }
    
    public static double getValCancelo(VisFicha visFicha)
    {
        double valCancelo = Validaciones.isNumVoid10(visFicha.txt_valCancelo.getText());
        return getTwoDecimals(valCancelo);
    
    }
     public static void setTotalesCabecera(JTable facDet,VisFicha visFicha)
    {
        visFicha.txtValPagar.setText(calcularValorPagar(facDet,visFicha)+"");
        visFicha.txtValConDsctoFicha.setText(setDescuentoFromDetEnt(visFicha)+"");
        visFicha.txtTotalConIva.setText(setTotalConIva(visFicha)+"");
        
        visFicha.txt_valCancelo.setText(setTotalConIva(visFicha)+"");
        visFicha.txtValPendienteFicha.setText(getDiferencia(setTotalConIva(visFicha),getValCancelo(visFicha) )+"");
       
    }
     
     
}
