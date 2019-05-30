/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelos.Conexion;
import modelos.TipoPersona;


public class ConsTipoPersona extends Conexion
{
    //GUARDA EN LA BD
    public boolean guardar(TipoPersona modTipPer)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO TipoPersona (id_tipoper, descripcion_tipoper, estado_tipoper)VALUES(tipoPersona_id_seq.NEXTVAL,?,?)";

        try 
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, modTipPer.getDescripcion_tipoPer());
            ps.setInt(2, modTipPer.getEstado_tipoPer()); 
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
    
    //BUSCA TODOS LOS REGISTROS PARA MOSTRARLES EN UNA TABLA A POSTERIOR
    public ArrayList<TipoPersona> buscarTodos(TipoPersona modTipPer)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM TipoPersona where estado_tipoPer=1 order by id_tipoPer asc";
        ArrayList<TipoPersona> tipoPerList = new ArrayList<>();

        try 
        {   
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) 
            {    
                modTipPer = new TipoPersona();
                modTipPer.setId_tipoPer(rs.getInt("id_tipoPer"));
                modTipPer.setDescripcion_tipoPer(rs.getString("descripcion_tipoPer"));
                tipoPerList.add(modTipPer);               
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       return tipoPerList;
    }
    
    //ELIMINA LOS REGISTROS DE LA BASE DE DATOS. 
    public boolean eliminar(TipoPersona modTipPer)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE TipoPersona SET ESTADO_tipoPer=? WHERE id_tipoPer=?";
        
        try 
        {
            ps = con.prepareStatement(sql);
            ps.setInt(1, modTipPer.getEstado_tipoPer());
            ps.setInt(2, modTipPer.getId_tipoPer());
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
    
    //MODIFICA LOS REGISTROS DE LA BASE DE DATOS.
    public boolean modificar(TipoPersona modTipPer)
    {
        PreparedStatement ps = null;
        Connection con = getConexion(); 
        String sql = "UPDATE TipoPersona SET descripcion_tipoPer=? WHERE id_tipoPer=?";
        try 
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, modTipPer.getDescripcion_tipoPer());
            ps.setInt(2, modTipPer.getId_tipoPer());
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
    
    //BUSCA EN LA BD POR EL NOMBRE DEL TIPO DE PERSONA
    public ArrayList<TipoPersona> buscarTodosByNom(TipoPersona modTipoPer,String nom)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "select * from TipoPersona m " +
                    "where upper(m.descripcion_tipoper) like upper('%"+nom+"%') and estado_tipoper = 1";
        ArrayList<TipoPersona> listTipPer = new ArrayList<>();
               
        try 
        {            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                modTipoPer = new TipoPersona();
                modTipoPer.setId_tipoPer(rs.getInt("id_tipoper"));
                modTipoPer.setDescripcion_tipoPer(rs.getString("descripcion_tipoper"));
                listTipPer.add(modTipoPer);               
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
       return listTipPer;
    }
    
    
    
    
    public ResultSet buscarTipoPersona()
    {
        PreparedStatement ps = null;
        Connection  con = getConexion();
        ResultSet rs = null; 
        String sql = "select tp.descripcion_tipoper " +
                    "from tipoPersona tp " +
                    "where tp.estado_tipoper =1 ";
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
             
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
           
        }
        finally
        {
           
        }
        return rs;
    }
    
    //CONEXION.
    public void closeConection()
    {
        
          try 
            {
                con.close();
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
    }
}
