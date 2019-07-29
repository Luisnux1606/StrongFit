/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Configuracion {
    
    public static String nomEmp ;
    
    public Configuracion()
    {
        rutaRepos();
        setEmpresa();
    
    }
    public static String rutaRepos()
    {
        String ruta = "C:/troyagym/configuracion/reportes/"; 
        
        return ruta;
    }
    
    public static void setEmpresa() 
    {
        
        BufferedReader br = null;  
        try {
            File file = new File("C:/troyagym/configuracion/empresa/nomEmp.txt");
            br = new BufferedReader(new FileReader(file));
            nomEmp = br.readLine();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    
    
 
}