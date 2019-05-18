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
import modelos.Entrenamiento;
import modelos.Producto;

/**
 *
 * @author Administrator
 */
public class ConsProductos extends Conexion {
    
    public boolean registrar(Producto prod)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "INSERT INTO Entrenamiento(id_ent,fechaIni_ent,fechaFin_ent, EntrenTiempo_id_entTmp, Persona_id_per, estado_ent) "
                + " VALUES(entrenamiento_id_seq.NEXTVAL,?,?,?,?,?)";

        try 
        {            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, ent.getFechaIni_ent());         
            ps.setString(2, ent.getFechaFin_ent());
            ps.setInt(3, ent.getEntrenTiempo_id_entTmp().getId_entTmp());
            ps.setInt(4, ent.getPersona_id_per().getId());    
            ps.setInt(5, ent.getEstado_ent());
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
    
    public boolean modificar(Entrenamiento ent)
    {
        PreparedStatement ps = null;
        Connection con = getConexion(); //id_ent,fechaIni_ent,fechaFin_ent, EntrenTiempo_id_entTmp, Persona_id_per,estado_ent
        String sql = "UPDATE Entrenamiento SET fechaIni_ent = ?,fechaFin_ent = ?, EntrenTiempo_id_entTmp = ?, Persona_id_per = ?,estado_ent = ?"
                + " WHERE id_ent=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, ent.getFechaIni_ent());         
            ps.setString(2, ent.getFechaFin_ent());
            ps.setInt(3, ent.getEntrenTiempo_id_entTmp().getId_entTmp());
            ps.setInt(4, ent.getPersona_id_per().getId()); 
            ps.setInt(5, ent.getEstado_ent());
            ps.setInt(6, ent.getId_ent());
          
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
    
     public boolean eliminar(Entrenamiento ent)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE  Entrenamiento SET estado_ent =? WHERE id_ent=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);  
            ps.setInt(1, ent.getEstado_ent());
            ps.setInt(2, ent.getId_ent());
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
       
    public ArrayList<Entrenamiento> buscarTodos(Entrenamiento ent)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM Entrenamiento where estado_ent=1";
        ArrayList<Entrenamiento> entrenamientos = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                ent.setId_ent(rs.getInt("id_ent"));
               
                
                entrenamientos.add(ent);               
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
       return entrenamientos ;
    }
   
   
       
     Connection con;
   
    
    public ResultSet buscarTodosPorNomTabla(String cad)
    {
        System.out.println(cad); //'%"+cad+"%'
        PreparedStatement ps = null;
        con = getConexion();
        ResultSet rs = null; 
        String sql = "select p.id_prod,p.descripcion_prod,p.precio_prod,c.tipo_cat,c.id_cat " +
                    "from  categoria c,producto p " +
                    "where c.id_cat = p.categoria_id_cat and p.id_prod like '%"+cad+"%'  " +
                    "union " +
                    "select p.id_prod,p.descripcion_prod,p.precio_prod,c.tipo_cat,c.id_cat\n" +
                    "from  categoria c,producto p " +
                    "where c.id_cat = p.categoria_id_cat and p.descripcion_prod like '%"+cad+"%' \n" +
                    "union " +
                    "select p.id_prod,p.descripcion_prod,p.precio_prod,c.tipo_cat,c.id_cat\n" +
                    "from  categoria c,producto p " +
                    "where c.id_cat = p.categoria_id_cat and c.tipo_cat like '%"+cad+"%' ";
                
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
    public int getIdByNom(String cat){
		String sql;
		int result=0;
                PreparedStatement ps = null;
                con = getConexion();
                ResultSet rs = null; 
                
		sql="select c.id_cat " +
                    "from categoria c " +
                    "where upper(c.tipo_cat) like upper('"+cat+"')";						
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
        String sql = "select p.id_prod,p.descripcion_prod,p.precio_prod,c.tipo_cat,c.id_cat\n" +
                    "from categoria c, producto p\n" +
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
    
    public ResultSet buscarEntrenamientoCosto(Entrenamiento ent)
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "select '1' as num,et.costo_enttiempo,concat('ENTRENAMIENTO ',et.descripcion_enttiempo) as descr " +
                    "from entrenamiento e, entrentiempo et " +
                    "where et.id_enttmp = e.entrentiempo_id_enttmp and e.id_ent ="+ent.getId_ent()+"";

        
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

    
    
}
