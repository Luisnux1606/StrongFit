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
    /*
    ("jdbc:oracle:thin:@localhost:1521:xe","usr_transmendez", "transmendez2010");

    
    1*/
    String base = "troyagym";
    String usr = "root";
    String pass = "luisnux1606";
    //String url = "jdbc:mysql://localhost:3306/"+base;
    String url ="jdbc:oracle:thin:@localhost:1521:xe";
    Connection con =  null;
    
    public Connection getConexion()
    {
        try 
        {            
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url,"usr_strongfit", "strongfit");
            
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        return con;
    }
}
