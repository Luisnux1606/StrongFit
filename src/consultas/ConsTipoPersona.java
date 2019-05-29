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
import modelos.Categoria;
import modelos.Conexion;
import modelos.Persona;



public class ConsTipoPersona extends Conexion
{
    public boolean guardar(Categoria modCat)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO categoria (id_cat, tipo_cat,CATEGORIA_ID_CAT, estado_cat) VALUES(categoria_id_seq.NEXTVAL,?,?,?)";

        try 
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, modCat.getTipo_cat());
            ps.setInt(2, modCat.getCategoria_id_cat().getId_cat()); 
            ps.setInt(3, modCat.getEstado_cat());
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
        String sql = "SELECT c.id_cat, c.tipo_cat,c.categoria_id_cat,c.estado_cat " +
                    "FROM Categoria c " +
                    "where estado_cat=1 " +
                    "order by id_cat asc";
        ArrayList<Categoria> categoria = new ArrayList<>();
        Categoria c;

        try 
        {   
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                c  = new Categoria();
               
                modCat = new Categoria();
                modCat.setId_cat(rs.getInt("id_cat"));
                modCat.setTipo_cat(rs.getString("tipo_cat"));
                    c.setId_cat(rs.getInt("CATEGORIA_ID_CAT"));                    
                    c.setTipo_cat(getNomById(c.getId_cat()));
                modCat.setCategoria_id_cat(c);
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
    public String getNomById(int idCat){
		String sql;
		String result="";
                PreparedStatement ps = null;
                con = getConexion();
                ResultSet rs = null; 
                
		sql="select c.tipo_cat " +
                    "from categoria c " +
                    "where c.id_cat =  "+idCat+"";						
                        try 
                        {
                            ps = con.prepareStatement(sql);                            
                            rs = ps.executeQuery();
                                while(rs.next()){
                                        result=rs.getString(1);
                                }

                        } catch (SQLException e) 
                        {e.printStackTrace();}			
		
		return result;
	}
    
    public int getIdByNom(String cat){
		String sql;
		int result=0;
                PreparedStatement ps = null;
                con = getConexion();
                ResultSet rs = null; 
                
		sql="select tp.id_tipoper " +
                    "from tipoPersona tp " +
                    "where tp.estado_tipoper =1 and tp.id_tipoper like '"+cat+"'";						
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
        Connection con = getConexion(); //id_cat, tipo_cat,CATEGORIA_ID_CAT, estado_cat
        String sql = "UPDATE categoria SET tipo_cat=?,CATEGORIA_ID_CAT = ? "
                + " WHERE id_cat=?";
        try 
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, modCat.getTipo_cat());
            ps.setInt(2, modCat.getCategoria_id_cat().getId_cat());
            ps.setInt(3, modCat.getId_cat());
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
        ResultSet rs = null;
        String sql = "SELECT c.id_cat, c.tipo_cat,c.categoria_id_cat,c.estado_cat " +
                    "FROM Categoria c " +
                    "where estado_cat=1 and upper(c.tipo_cat) like upper('%"+nom+"%') " +
                    "order by id_cat asc";
        ArrayList<Categoria> categoria = new ArrayList<>();
        Categoria c;

        try 
        {   
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                c  = new Categoria();
               
                modCat = new Categoria();
                modCat.setId_cat(rs.getInt("id_cat"));
                modCat.setTipo_cat(rs.getString("tipo_cat"));
                    c.setId_cat(rs.getInt("CATEGORIA_ID_CAT"));                    
                    c.setTipo_cat(getNomById(c.getId_cat()));
                modCat.setCategoria_id_cat(c);
                categoria.add(modCat);   
                closeConection();
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
    public ResultSet buscarTipoPersonas()
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "select c.tipo_cat " +
                    "from categoria c " +
                    "where c.categoria_id_cat=2 and c.estado_cat = 1";
                
        
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
