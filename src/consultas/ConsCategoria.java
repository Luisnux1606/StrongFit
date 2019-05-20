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
import modelos.Categoria;
import modelos.Conexion;
import modelos.Persona;


/**
 *
 * @author aplaza
 */
public class ConsCategoria extends Conexion
{
    public boolean guardar(Categoria modCat)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO categoria (id_cat, tipo_cat, estado_cat) VALUES(categoria_id_seq.NEXTVAL,?,?)";

        try 
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, modCat.getTipo_cat());
            ps.setInt(2, modCat.getEstado_cat()); 
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
    
    public ArrayList<Categoria> buscarTodos(Categoria modCat)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM Categoria where estado_cat=1 order by id_cat asc";
        ArrayList<Categoria> categoria = new ArrayList<>();
        
        try 
        {   
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                modCat = new Categoria();
                modCat.setId_cat(rs.getInt("id_cat"));
                modCat.setTipo_cat(rs.getString("tipo_cat"));
                categoria.add(modCat);               
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
       return categoria;
    }
    
    public boolean eliminar(Categoria modCat)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE categoria SET ESTADO_CAT=? WHERE id_cat=?";
        
        try 
        {
            ps = con.prepareStatement(sql);
            ps.setInt(1, modCat.getEstado_cat());
            ps.setInt(2, modCat.getId_cat());
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
    
    public boolean modificar(Categoria modCat)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE categoria SET tipo_cat=?"
                + " WHERE id_cat=?";
        try 
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, modCat.getTipo_cat());
            ps.setInt(2, modCat.getId_cat());
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
    
    public ArrayList<Categoria> buscarTodosPorNom(Categoria modCat,String nom)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null; //'%"+cad+"%'
        String sql = "SELECT * FROM Categoria "
                + " where upper(tipo_cat) like upper('%"+nom+"%') and estado_cat=1"
                + " order by id_cat asc ";
        ArrayList<Categoria> cat = new ArrayList<>();
        
        try 
        {
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                modCat = new Categoria();
                modCat.setId_cat(rs.getInt("id_cat"));
                modCat.setTipo_cat(rs.getString("tipo_cat"));
                cat.add(modCat);               
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
       return cat;
    }
}
