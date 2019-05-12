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
import modelos.EntrenamientoTiempo;
import modelos.FacturaCab;
import modelos.Medidas;
import modelos.Membresias;

/**
 *
 * @author Administrator
 */
public class ConsEntrenamientoTiempo extends Conexion{
    
    
    public boolean registrar(EntrenamientoTiempo m)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "INSERT INTO EntrenTiempo (id_entTmp ,descripcion_entTiempo , costo_entTiempo,estado_enTiempo) "
                + " VALUES(membresia_id_seq.NEXTVAL,?,?,?)";    
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, m.getDescripcion_entTiempo());
            ps.setDouble(2, m.getCosto_entTiempo());   
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
    
    public boolean modificar(EntrenamientoTiempo m)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();//id_entTmp ,descripcion_entTiempo , costo_entTiempo,estado_enTiempo
        String sql = "UPDATE EntrenTiempo SET descripcion_entTiempo=?, costo_entTiempo=? "
                + " WHERE id_entTmp=?";
        
        try 
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getDescripcion_entTiempo());
            ps.setDouble(2, m.getCosto_entTiempo());              
            ps.setInt(3, m.getId_entTmp());
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
    
     public boolean eliminar(EntrenamientoTiempo m)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE  EntrenTiempo SET estado_enTiempo   = ? WHERE id_entTmp=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);           
            ps.setInt(1, m.getEstado());
            ps.setDouble(2, m.getId_entTmp());
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
    
    
    public ArrayList<EntrenamientoTiempo> buscarTodos(EntrenamientoTiempo m)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM EntrenTiempo where estado_enTiempo=1";
        ArrayList<EntrenamientoTiempo> membresia = new ArrayList<>();
               
        try 
        {            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                m = new EntrenamientoTiempo();
                m.setId_entTmp(rs.getInt("id_entTmp"));
                m.setDescripcion_entTiempo(rs.getString("descripcion_entTiempo"));
                m.setCosto_entTiempo(rs.getDouble("costo_entTiempo"));
                             
                
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
