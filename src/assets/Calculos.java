/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

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
    
}
