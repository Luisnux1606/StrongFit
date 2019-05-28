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
import modelos.HistorialPersonaServicio;

import modelos.Producto;


public class ConsHistorialPersonaServicio extends Conexion
{
    public boolean registrar(HistorialPersonaServicio hisPerServ)
    {
        PreparedStatement ps= null;
        Connection con = getConexion();
        String sql = "INSERT INTO HISTPERSSERV (ID_HISPERSER,FECHAINI_HISPERSER, FECHAFIN_HISPERSER, PERSONA_ID_HISPERSER, PRODUCTO_ID_HISPERSER,ESTADO_HISPERSER) VALUES(HistPersServ_id_seq.NEXTVAL,?,?,?,?,?)";
        try 
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, hisPerServ.getFechaIni_HisPerSer());
            ps.setString(2, hisPerServ.getFechaFin_HisPerSer());
            ps.setInt(3, hisPerServ.getPersona_id_HisPerSer().getId());
            ps.setInt(4, hisPerServ.getProducto_id_HisPerSer().getId_prod());  
            ps.setInt(5, hisPerServ.getEstado_HisPerSer()); 

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
    
    public boolean modificar(HistorialPersonaServicio hisPerServ)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();//HistorialPeronaServicio (ID_HISPERSER,FECHAINI_HISPERSER, FECHAFIN_HISPERSER, PERSONA_ID_HISPERSER, PRODUCTO_ID_HISPERSER,ESTADO_HISPERSER) VALUES(HistPersServ_id_seq.NEXTVAL,?,?,?,?,?)";
        String sql = "UPDATE HISTPERSSERV SET FECHAINI_HISPERSER = ?,FECHAFIN_HISPERSER = ?, PERSONA_ID_HISPERSER = ?, PRODUCTO_ID_HISPERSER = ?, ESTADO_HISPERSER = ?"
                + " WHERE ID_HISPERSER=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            ps = con.prepareStatement(sql);
            ps.setString(1, hisPerServ.getFechaIni_HisPerSer());
            ps.setString(2, hisPerServ.getFechaFin_HisPerSer());
            ps.setInt(3, hisPerServ.getPersona_id_HisPerSer().getId());
            ps.setInt(4, hisPerServ.getProducto_id_HisPerSer().getId_prod());  
            ps.setInt(5, hisPerServ.getEstado_HisPerSer()); 
            ps.setInt(6, hisPerServ.getId_HisPerSer());
          
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
    
     public boolean eliminar(HistorialPersonaServicio hisPerServ)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE  HISTPERSSERV SET ESTADO_HISPERSER =? WHERE ID_HISPERSER=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);  
            ps.setInt(1, hisPerServ.getEstado_HisPerSer());
            ps.setInt(2, hisPerServ.getId_HisPerSer());
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
      
        PreparedStatement ps = null;
        con = getConexion();
        ResultSet rs = null; 
        String sql = "select h.id_hisperser,concat(concat(p.nom_per,' '),p.ape_per) as nombres,pr.descripcion_prod,h.fechaini_hisperser,h.fechafin_hisperser,pr.id_prod,p.id_per\n" +
                    "from  persona p, histpersserv h, producto pr\n" +
                    "where p.id_per = h.persona_id_hisperser and pr.id_prod = h.producto_id_hisperser and   upper(concat(concat(p.nom_per,' '),p.ape_per)) like upper('%"+cad+"%') \n" +
                    "union\n" +
                    "select h.id_hisperser,concat(concat(p.nom_per,' '),p.ape_per) as nombres,pr.descripcion_prod,h.fechaini_hisperser,h.fechafin_hisperser,pr.id_prod,p.id_per\n" +
                    "from  persona p, histpersserv h, producto pr\n" +
                    "where p.id_per = h.persona_id_hisperser and pr.id_prod = h.producto_id_hisperser and   upper(pr.descripcion_prod) like upper('%"+cad+"%') \n" +
                    "union\n" +
                    "select h.id_hisperser,concat(concat(p.nom_per,' '),p.ape_per) as nombres,pr.descripcion_prod,h.fechaini_hisperser,h.fechafin_hisperser,pr.id_prod,p.id_per\n" +
                    "from  persona p, histpersserv h, producto pr\n" +
                    "where p.id_per = h.persona_id_hisperser and pr.id_prod = h.producto_id_hisperser and   h.fechaini_hisperser like '%"+cad+"%' ";
                
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
                
		sql="select p.id_prod " +
                    "from producto p " +
                    "where upper(p.DESCRIPCION_PROD) like upper('%"+prod+"%')";						
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
    
     public double getPrecioByCat(String cat){
		String sql;
		double result=0;
                PreparedStatement ps = null;
                con = getConexion();
                ResultSet rs = null; 
                
		sql="select p.precio_prod\n" +
                    "from producto p, categoria c " +
                    "where c.id_cat = p.categoria_id_cat and upper(p.descripcion_prod) like upper('%"+cat+"%')";						
                        try 
                        {
                            ps = con.prepareStatement(sql);                            
                            rs = ps.executeQuery();
                                while(rs.next()){
                                        result=rs.getDouble(1);
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
        String sql = "select h.id_hisperser,concat(concat(p.nom_per,' '),p.ape_per) as nombres,pr.descripcion_prod,h.fechaini_hisperser,h.fechafin_hisperser,pr.id_prod,p.id_per\n" +
                    "from  persona p, histpersserv h, producto pr\n" +
                    "where p.id_per = h.persona_id_hisperser and pr.id_prod = h.producto_id_hisperser";
                
        
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
    
    public ResultSet buscarTodosByIdPer(int idPer)
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "select h.id_hisperser,concat(concat(p.nom_per,' '),p.ape_per) as nombres,pr.descripcion_prod,h.fechaini_hisperser,h.fechafin_hisperser,pr.id_prod,p.id_per " +
                "     from  persona p, histpersserv h, producto pr " +
                "     where p.id_per = h.persona_id_hisperser and pr.id_prod = h.producto_id_hisperser and p.id_per="+idPer+"  and h.ESTADO_HISPERSER=1";
                
        
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
    
    public ResultSet buscarServicios()
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "select p.descripcion_prod " +
                    "  from categoria c, producto p " +
                    "  where c.id_cat =p.categoria_id_cat  and c.estado_cat = 1";
                
        
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
    
    public ResultSet buscarServiciosTrain()
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "select p.descripcion_prod " +
                    "  from categoria c, producto p " +
                    "  where c.id_cat =p.categoria_id_cat and c.categoria_id_cat=1 and c.estado_cat = 1";
                
        
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

    
   
}
