/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import modelos.Conexion;


import java.util.ArrayList;
import modelos.Categoria;
import modelos.Conexion;
import modelos.Persona;

import modelos.Producto;


public class ConsProductos extends Conexion
{
    public boolean registrar(Producto modProducto)
    {
        PreparedStatement ps= null;
        Connection con = getConexion();
        String sql = "INSERT INTO Producto (id_prod,descripcion_prod, precio_prod,FECHAINI_PROD,FECHAFIN_PROD, Categoria_id_cat, estado_prod) VALUES(producto_id_seq.NEXTVAL,?,?,?,?,?,?)";
        try 
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, modProducto.getDescripcion_prod());
            ps.setDouble(2, modProducto.getPrecio_prod());
            ps.setString(3, modProducto.getFechaIni());
            ps.setString(4, modProducto.getFechaFin());
            ps.setInt(5, modProducto.getCategoria().getId_cat());
            ps.setInt(6, modProducto.getEstado_prod());  

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
    
    public boolean modificar(Producto modProducto)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();// descripcion_prod, precio_prod, Categoria_id_cat, estado_prod) VALUES(producto_id_seq.NEXTVAL,?,?,?,?)
        String sql = "UPDATE Producto SET descripcion_prod = ?,precio_prod = ?, Categoria_id_cat = ?, estado_prod = ?"
                + " WHERE id_prod=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, modProducto.getDescripcion_prod());
            ps.setDouble(2, modProducto.getPrecio_prod());
            ps.setInt(3, modProducto.getCategoria().getId_cat());
            ps.setInt(4, modProducto.getEstado_prod());  
            ps.setInt(5, modProducto.getId_prod());
          
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
    
     public boolean eliminar(Producto prod)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE  Producto SET estado_prod =? WHERE id_prod=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);  
            ps.setInt(1, prod.getEstado_prod());
            ps.setInt(2, prod.getId_prod());
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
       
   
   
   
       
     Connection con;
   
    
    public ResultSet buscarTodosPorNomTabla(String cad)
    {
        System.out.println(cad); //'%"+cad+"%'
        PreparedStatement ps = null;
        con = getConexion();
        ResultSet rs = null; 
        String sql = "select p.id_prod,p.descripcion_prod,p.precio_prod,p.FECHAINI_PROD,p.FECHAFIN_PROD,c.tipo_cat,c.id_cat " +
                    "from  categoria c,producto p " +
                    "where c.id_cat = p.categoria_id_cat and upper(p.id_prod) like upper('%"+cad+"%')  " +
                    "union " +
                    "select p.id_prod,p.descripcion_prod,p.precio_prod,p.FECHAINI_PROD,p.FECHAFIN_PROD,c.tipo_cat,c.id_cat " +
                    "from  categoria c,producto p " +
                    "where c.id_cat = p.categoria_id_cat and upper(p.descripcion_prod) like upper('%"+cad+"%') " +
                    "union " +
                    "select p.id_prod,p.descripcion_prod,p.precio_prod,p.FECHAINI_PROD,p.FECHAFIN_PROD,c.tipo_cat,c.id_cat " +
                    "from  categoria c,producto p " +
                    "where c.id_cat = p.categoria_id_cat and upper(c.tipo_cat) like upper('%"+cad+"%') ";
                
        ArrayList datos = new ArrayList();
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
    
    //getIdByNom
    public int getIdByNom(String prod){
		String sql;
		int result=0;
                PreparedStatement ps = null;
                con = getConexion();
                ResultSet rs = null; 
                
		sql="select c.id_cat " +
                    "from categoria c " +
                    "where upper(c.tipo_cat) like upper('"+prod+"')";						
                        try 
                        {
                            ps = con.prepareStatement(sql);                            
                            rs = ps.executeQuery();
                                while(rs.next()){
                                        result=rs.getInt(1);
                                }

                        } catch (SQLException e) 
                        {e.printStackTrace();}			
		
		return result;
	}
    
    
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
    
    public ResultSet buscarTodos()
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "select p.id_prod,p.descripcion_prod,p.precio_prod,p.FECHAINI_PROD,p.FECHAFIN_PROD,c.tipo_cat,c.id_cat " +
                    "from categoria c, producto p " +
                    "where c.id_cat = p.categoria_id_cat and p.estado_prod = 1";
                
        
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
    
    public ResultSet buscarProductos()
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "select p.id_prod,p.descripcion_prod,p.precio_prod,p.FECHAINI_PROD,p.FECHAFIN_PROD,c.tipo_cat,c.id_cat " +
                    "from categoria c, producto p " +
                    "where c.id_cat = p.categoria_id_cat and c.id_cat=2  and p.estado_prod = 1 ";
                
        
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
    
    public ResultSet buscarCategorias()
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "select c.tipo_cat \n" +
                    "from categoria c \n" +
                    "where c.estado_cat = 1";
                
        
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
    public boolean buscar(Producto p)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM producto p WHERE upper(p.descripcion_prod) = ? and estado_prod=1";
        /*
        try 
        {
            
            ps = con.prepareStatement(sql);                     
            ps.setString(1, p.getDescripcion_prod());
            rs = ps.executeQuery();
            if (rs.next()) { //ced_per, nom_per, ape_per, nroFono_per,edad_per,fechaNac_per
                p.setId_prod(rs.getInt("id_prod"));
                p.setCedula(rs.getString("ced_per"));
                p.setNombre(rs.getString("nom_per"));
                p.setApellido(rs.getString("ape_per"));
                p.setGenero(rs.getString("genero_per"));
                p.setMail(rs.getString("mail_per"));
                p.setNro_fono(rs.getString("nroFono_per"));
                p.setEdad(rs.getInt("edad_per"));
                p.setFecha_nac(rs.getString("fechaNac_per")+"");
               
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
        */
        return false;
    }
    
   
}
