/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Administrator
 */
public class Conexion {
    
    //String url = "jdbc:mysql://localhost:3306/"+base;
    String url ="jdbc:oracle:thin:@localhost:1521:xe";
    public Connection con =  null;
    
    public Connection getConexion()
    {
        try 
        {            
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url,"usr_troyagym", "troyagym");
            
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        return con;
    }
    
     public void desconectar(){
        con = null;
    //System.out.println("Desconexion a base de datos listo...");
    }
}
