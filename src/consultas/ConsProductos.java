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
import modelos.Producto;

/**
 *
 * @author Administrator
 */
public class ConsProductos extends Conexion
{
    public boolean registrar(Producto modProducto)
    {
        PreparedStatement ps= null;
        Connection con = getConexion();
        String sql = "INSERT INTO Producto (id_prod,descripcion_prod, precio_prod, Categoria_id_cat, estado_prod) VALUES(producto_id_seq.NEXTVAL,?,?,?,?)";
        try 
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, modProducto.getDescripcion_prod());
            ps.setDouble(2, modProducto.getPrecio_prod());
            ps.setInt(3, modProducto.getCategoria().getId_cat());
            ps.setInt(4, modProducto.getEstado_prod());  
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
    
    public ArrayList<Producto> buscarTodos(Producto modProducto, Categoria modCategoria)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM Producto where estado_prod=1";
        ArrayList<Producto> prod = new ArrayList<>();
        
        
        try 
        {
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                modProducto = new Producto();
                modProducto.setId_prod(rs.getInt("id_prod"));
                modProducto.setDescripcion_prod(rs.getString("descripcion_prod"));
                modProducto.setPrecio_prod(rs.getDouble("precio_prod"));
                
                int c=rs.getInt("categoria_id_cat");
                modCategoria.setId_cat(c);
                modProducto.setCategoria(modCategoria);
                
                prod.add(modProducto);               
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
       return prod;
    }
    
    public boolean eliminar(Producto modProducto)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE Producto SET ESTADO_PROD=? WHERE ID_PROD=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, modProducto.getEstado_prod());
            ps.setInt(2, modProducto.getId_prod());
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
}
