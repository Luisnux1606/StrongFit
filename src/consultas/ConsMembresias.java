/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultas;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelos.Analisis;
import modelos.Conexion;
import modelos.Ficha;
import modelos.Medidas;
import modelos.Membresias;

/**
 *
 * @author Administrator
 */
public class ConsMembresias extends Conexion{
    
    
    public boolean registrar(Membresias m)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "INSERT INTO membresia (id_memb ,nom_memb , dscto_memb,ESTADO_MEMB) "
                + " VALUES(membresia_id_seq.NEXTVAL,?,?,?)";
     //   String sql2 = "INSERT INTO Ficha (fecha_ini,fecha_fin,val_pago,val_pendiente) VALUES(?,?,?,?)";
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, m.getNombre());
            ps.setDouble(2, m.getDscto());   
            ps.setInt(3, m.getEstado());
            ps.execute();                                       
            return true;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            try 
            {
                con.close();
            } catch (Exception e) 
            {
                System.err.println(e);
            }
        }
        
    }
    
    public boolean modificar(Membresias m)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE membresia SET nom_memb=?, dscto_memb=? "
                + " WHERE id_memb=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getNombre());
            ps.setDouble(2, m.getDscto());
            ps.setInt(3, m.getId());
            ps.execute();
            return true;
        } 
        catch (Exception e) 
        {
            System.err.println(e);
            return false;
        }
        finally
        {
            try 
            {
                con.close();
            } catch (Exception e) 
            {
                System.err.println(e);
            }
        }
        
    }
    
     public boolean eliminar(Membresias m)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE  membresia SET ESTADO_MEMB   = ? WHERE id_memb=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);           
            ps.setInt(1, m.getEstado());
            ps.setDouble(2, m.getId());
            ps.execute();
            return true;
        } 
        catch (Exception e) 
        {
            
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se puede actulizar porque ya se esta usando...");
            return false;
        }
      
        finally
        {
            try 
            {
                con.close();
            } catch (Exception e) 
            {
                System.err.println(e);
            }
        }
        
    }
     
    public int getLastId()
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = " select max(id_memb) as ID_MEMB from membresia " +
                     " order by id_memb asc " +
                     " ";
        
        int idMemb = 0;
       
        try 
        {
            
            ps = con.prepareStatement(sql);                                 
            rs = ps.executeQuery() ;

            if (rs.next()) { 
               idMemb = rs.getInt("ID_MEMB");
            }
           
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
          
        }
        finally
        {
            try 
            {
                con.close();
            } catch (Exception e) 
            {
                System.err.println(e);
            }
        }
       return idMemb; 
    }
     
    public boolean buscar(Membresias m)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM membresia WHERE nom_memb=? and estado_memb=1";
        
        try 
        {
            
            ps = con.prepareStatement(sql);                     
            ps.setString(1, m.getNombre());
            rs = ps.executeQuery();
            if (rs.next()) {
                m.setId(rs.getInt("id_memb"));
                m.setNombre(rs.getString("nom_memb"));
                m.setDscto(rs.getDouble("dscto_memb"));               
                return true;
            }
            return false;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            try 
            {
                con.close();
            } catch (Exception e) 
            {
                System.err.println(e);
            }
        }
        
    }
    
    
    public ArrayList<Membresias> buscarTodos(Membresias m)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM membresia where estado_memb=1";
        ArrayList<Membresias> membresia = new ArrayList<>();
               
        try 
        {            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                m = new Membresias();
                m.setId(rs.getInt("id_memb"));
                m.setNombre(rs.getString("nom_memb"));
                m.setDscto(rs.getDouble("dscto_memb"));
                             
                
                membresia.add(m);               
            }
             
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
           
        }
        finally
        {
            try 
            {
                con.close();
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
       return membresia ;
    }
        
}
