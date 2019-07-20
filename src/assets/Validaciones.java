/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import com.toedter.calendar.JDateChooser;
import consultas.ConsPersona;
import controladores.CtrlPersonas;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelos.FacturaDetalle;
import modelos.Producto;

/**
 *
 * @author Administrator
 */
public class Validaciones {
    
     public static String setFormatFecha(Date fecha){
         String fechaNac;
         if (fecha!=null) {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
             fechaNac = formatter.format(fecha);
         }
         else
         {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            fechaNac = "";
         }
             
       
        return fechaNac;
     }
     
     public static int isNumVoid(String txt)
     {
         int val = 0;
         if (txt.length()==0 || txt.trim().equals("0")||txt=="0" ||txt==null || txt.trim().length()==0 ||txt.equals("null")||txt.equals("0")) {
             val = 0;
         }
         else
             val = Integer.parseInt(txt.trim());
         
         return val;
     }
     
      public static double isNumVoid10(String txt)
     {
         double val = 0;
         if (txt.length()==0 || txt.trim().equals("0")||txt=="0" ||txt==null || txt.trim().length()==0 ||txt.equals("null")||txt.trim().equals("0.0") ) {
             val = 0;
         }
         else
             val = Double.parseDouble(txt.trim());
         
         return val;
     }
     
     
     
     public static String isNumVoid4(String txt)
     {
         String  val = "";
         if (txt==null || txt.toString().equalsIgnoreCase("null") || txt.length()==0) {
             val = "";
         }
         else
             val = txt;

         return val;
     }
     
     public static boolean isCadnull(String txt)
     {
         boolean  val = false;
         if (txt==null || txt.toString().equalsIgnoreCase("null") || txt.length()==0) {
             val = true;
         }
         else
             val = false;

         return val;
     }
     
      public static boolean isCedulaPersonaVoid(JTextField txt)
     {
         boolean  val = false;
         if (txt==null || txt.toString().equalsIgnoreCase("null") || txt.getText().length()==0) {
             val = true;
             getMensaje("Necesita ingresar una cedula");
         }
         else
             val = false;

         return val;
     }
     
     
     
     public static double isNumVoid3(String txt)
     {
         double val = 0;
         if (txt.length()==0) {
             val = 0;
         }
         else
             val = Double.parseDouble(txt);
         
         return val;
     }
     
     public static boolean isNumVoid1(String txt)
     {
         boolean estado = false;
         if (txt.trim().length()==0 || txt.trim().equals("0")||txt=="0" ||txt==null || txt.length()==0 ||txt.equals("null")) {
             estado = true;
             
         }
         else
             estado = false;
         
         return estado;
     }
     
     public static double isNumVoid2(String txt)
     {
         
         double val = 0;
         if (txt.length()==0) {
             val = 0;
         }
         else
             
             val = Double.parseDouble(txt);
         
         return val;
     }
     public static boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
    }
     
     public static boolean isVoid(ArrayList<JTextField> txt)
     {
         boolean estado = true;
         String msg = "";
         for (JTextField jTextField : txt) 
         {
             if (jTextField.getText()==null||jTextField.getText().length()==0)                     
                msg = msg+" Debe ingresar un "+jTextField.getName()+" ";
         }
         if (msg.length()>0) {
             JOptionPane.showMessageDialog(null, msg);
             estado = false;
         }
         return estado;
     }
     
     public static boolean isVoidTextArea(ArrayList<JTextArea> txt)
     {
         boolean estado = true;
         String msg = "";
         for (JTextArea jTextField : txt) 
         {
             if (jTextField.getText()==null||jTextField.getText().length()==0)                     
                msg = msg+" Debe ingresar un "+jTextField.getName()+" ";
         }
         if (msg.length()>0) {
             JOptionPane.showMessageDialog(null, msg);
             estado = false;
         }
         return estado;
     }
     
      public static boolean isVoidJTxt(JTextField jTextField)
     {
         boolean estado = true;
         String msg = "";
        
        if (jTextField.getText().trim()==null||jTextField.getText().trim().length()==0)                     
           msg = msg+" Debe ingresar un "+jTextField.getName()+" ";
       
         if (msg.length()>0) {
             JOptionPane.showMessageDialog(null, msg);
             estado = false;
         }
         return estado;
     }
      
      public static boolean isDetalleNull(JTable table)
      {
          String num,desc,valU,valT,prodId; 
          
          boolean estado=false;
          for (int i = 0; i <= table.getRowCount()-1; i++) 
           {
               num = table.getValueAt(i, 2)+"";
               desc = table.getValueAt(i, 3)+"";     
               valU = table.getValueAt(i, 4)+"";
               valT = table.getValueAt(i, 5)+"";

               if (Validaciones.isNumVoid10(num)!=0 && Validaciones.isNumVoid10(valU)!=0 && Validaciones.isNumVoid10(valT)!=0 )
               {
                    estado = true;
                    break;
               }
               else 
               {
                   estado  = false;
                   
               }
               if (estado == false)
                   getMensaje("La factura debe tener almenos un detalle.");
               
           }      
      return estado;
      }
      
      public static boolean isDetalleNullComp(JTable table)
      {
          String num,desc,valU,valT,prodId; 
          
          boolean estado=false;
          for (int i = 0; i <= table.getRowCount()-1; i++) 
           {
               num = table.getValueAt(i, 1)+"";
               desc = table.getValueAt(i, 2)+"";     
               valU = table.getValueAt(i, 3)+"";
               valT = table.getValueAt(i, 4)+"";

               if (Validaciones.isNumVoid10(num)!=0 && Validaciones.isNumVoid10(valU)!=0 && Validaciones.isNumVoid10(valT)!=0 )
               {
                    estado = true;
                    break;
               }
               else 
               {
                   estado  = false;
                   
               }
               if (estado == false)
                   getMensaje("La factura debe tener almenos un detalle.");
               
           }      
      return estado;
      }

      
      public static boolean isVoidDateChooser(JDateChooser dateChooser)
     {
         boolean estado = true;
         String msg = "";
        
        if (dateChooser.getDateFormatString()==null||dateChooser.getDateFormatString().trim().length()==0 || dateChooser.getDate() == null)                     
           msg = msg+" Debe ingresar un "+dateChooser.getName()+" ";
       
         if (msg.length()>0) {
             JOptionPane.showMessageDialog(null, msg);
             estado = false;
         }
         return estado;
     }
      
      public static boolean isHigher(double a, double b)
      {
          if (a>b) 
              return true;              
          else
              return false;          
      }
      public static boolean isValCancHigherIngreso(double a, double b)
      {
          if(isHigher(a, b)){     
              getMensaje("EL valor a cancelar no puede sr mayor a " + b);
              return true;              
          }
          else{
              
              return false;
          }
      }
      
       public static boolean isValAjusteHigherPendiente(double a, double b)
      {
          if(isHigher(a, b)){     
              getMensaje("EL valor de ajuste no puede sr mayor a " + b);
              return true;              
          }
          else{
              
              return false;
          }
      }
      
      public static Date setStringToDate(String txt)
      {
          if (isNumVoid1(txt)) 
              return null;
          else
          {
  
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            try 
           {
                date = formatter.parse(txt);

           } catch (ParseException ex) {
            System.out.println(ex);
           }
             return date;
          }
      }
    
      public static boolean isDate(String cad)
      {
            String regex = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"; 
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(cad);
            
        return matcher.matches();
      }
      
      public static boolean isDateChooserVoid(ArrayList<JDateChooser> dtc)
     {
         boolean estado = true;
         String msg = "";
         for (JDateChooser jDateChooser : dtc) 
         {
             if (jDateChooser.getDate()==null||(jDateChooser.getDate()+"").length()==0)                     
                msg = msg+" Debe ingresar un "+jDateChooser.getName()+" ";
         }
         if (msg.length()>0) {
             JOptionPane.showMessageDialog(null, msg);
             estado = false;
         }
         return estado;
     }
      
      public static boolean validaMail(String txtMail)
      {
        String emailRegexp = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
        boolean resultado;
        resultado= Pattern.matches(emailRegexp, txtMail)?true:false;
       
        
        return resultado;
      }
      
      public static boolean validaNums(String cad)
      {
        String regex = "[0-9]";;
        boolean resultado;
        resultado= Pattern.matches(regex, cad)?true:false;
       
        
        return resultado;
      }
      
      public static boolean existeCedula(String ced)
      {
          ConsPersona cP=new ConsPersona();
            if(!isCadnull(cP.getCedByCed(ced))) {           
                getMensaje("El usuario con cedula "+ced+" ya existe.");
                return true;            
            }
            else
                return false;
      }
      
      public static void getMensaje(String msg)
      {
            JOptionPane.showMessageDialog(null, msg);
      }
      
      public int getEdad(String fecha)
      {
          
          return 0;
      }
      
      public static String getMsgOption(){
        Object[] possibleValues = { "Ingreso", "Egreso"};
        Object selectedValue = JOptionPane.showInputDialog(null,"Eliga una opcion", "INGRESO/EGRESO",JOptionPane.INFORMATION_MESSAGE, null,possibleValues, possibleValues[0]);
        return selectedValue.toString();
      }
}
