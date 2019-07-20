/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;

/**
 *
 * @author Administrator
 */
public class PrtoSerial {
    
    public  static PanamaHitek_Arduino ino;
    
    public PrtoSerial()
    {
        try 
        {
            ino = new PanamaHitek_Arduino();
            ino.arduinoTX("COM3", 9600);
        } catch (ArduinoException ex) 
        {
           // Logger.getLogger(PrtoSerial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
    
    public static void sendData(int d)
    {
        try 
        {            
            ino.sendData(d+"");
        } catch (ArduinoException ex) {
             Logger.getLogger(PrtoSerial.class.getName()).log(Level.SEVERE, null, ex);
            Validaciones.getMensaje("PUERTO NO ABIERTO ");
        } catch (SerialPortException ex) {
            Logger.getLogger(PrtoSerial.class.getName()).log(Level.SEVERE, null, ex);
            Validaciones.getMensaje("PUERTO NO ABIERTO ");
        }
    }
    
}
