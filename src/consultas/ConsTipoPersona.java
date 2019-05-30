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
    
    public boolean modificar(TipoPersona modTipPer)
    {
        PreparedStatement ps = null;
        Connection con = getConexion(); 
        String sql = "UPDATE TipoPersona SET descripcion_tipoPer=? WHERE id_tipPer=?";
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
