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
import modelos.Analisis;
import modelos.Conexion;
import modelos.Entrenamiento;
import modelos.FacturaCab;
import modelos.Ficha;
import modelos.Medidas;
import modelos.Persona;

/**
 *
 * @author Administrator
 */
public class ConsEntrenamiento extends Conexion {
    
    public boolean registrar(Entrenamiento ent)
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
        String sql = " select e.id_ent, e.fechaini_ent,e.fechafin_ent, et.descripcion_enttiempo,p.id_per,concat(concat(p.nom_per,' '),p.ape_per)  as nombres \n" +
                        "from  entrenamiento e,entrentiempo et,persona p\n" +
                        "where e.id_ent like '%"+cad+"%' and et.id_enttmp = e.entrentiempo_id_enttmp and p.id_per = e.persona_id_per\n" +
                        "union\n" +
                        "select e.id_ent, e.fechaini_ent,e.fechafin_ent, et.descripcion_enttiempo,p.id_per,concat(concat(p.nom_per,' '),p.ape_per  as nombres)\n" +
                        "from  entrenamiento e,entrentiempo et,persona p\n" +
                        "where e.fechaini_ent like '%"+cad+"%' and et.id_enttmp = e.entrentiempo_id_enttmp and p.id_per = e.persona_id_per\n" +
                        "union\n" +
                        "select e.id_ent, e.fechaini_ent,e.fechafin_ent, et.descripcion_enttiempo,p.id_per,concat(concat(p.nom_per,' '),p.ape_per)  as nombres \n" +
                        "from  entrenamiento e,entrentiempo et,persona p\n" +
                        "where e.fechafin_ent like '%"+cad+"%' and et.id_enttmp = e.entrentiempo_id_enttmp and p.id_per = e.persona_id_per\n" +
                        "union\n" +
                        "select e.id_ent, e.fechaini_ent,e.fechafin_ent, et.descripcion_enttiempo,p.id_per,concat(concat(p.nom_per,' '),p.ape_per)  as nombres \n" +
                        "from  entrenamiento e,entrentiempo et,persona p\n" +
                        "where upper(et.descripcion_enttiempo) like upper('%"+cad+"%') and et.id_enttmp = e.entrentiempo_id_enttmp and p.id_per = e.persona_id_per\n" +
                        "union\n" +
                        "select e.id_ent, e.fechaini_ent,e.fechafin_ent, et.descripcion_enttiempo,p.id_per,concat(concat(p.nom_per,' '),p.ape_per)  as nombres \n" +
                        "from  entrenamiento e,entrentiempo et,persona p\n" +
                        "where upper(et.descripcion_enttiempo) like upper('%"+cad+"%') and et.id_enttmp = e.entrentiempo_id_enttmp and p.id_per = e.persona_id_per\n" +
                        "union\n" +
                        "select e.id_ent, e.fechaini_ent,e.fechafin_ent, et.descripcion_enttiempo,p.id_per,concat(concat(p.nom_per,' '),p.ape_per)  as nombres \n" +
                        "from  entrenamiento e,entrentiempo et,persona p\n" +
                        "where upper(concat(concat(p.nom_per,' '),p.ape_per)) like upper('%"+cad+"%') and et.id_enttmp = e.entrentiempo_id_enttmp and p.id_per = e.persona_id_per";
                
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
    public int getIdByNom(String entTmp){
		String sql;
		int result=0;
                PreparedStatement ps = null;
                con = getConexion();
                ResultSet rs = null; 
                
		sql="select et.id_enttmp \n" +
                        "from entrenTiempo et \n" +
                        "where upper(et.descripcion_enttiempo) like upper('"+entTmp+"')";						
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
        String sql = "select e.id_ent, e.fechaini_ent,e.fechafin_ent,et.descripcion_enttiempo,p.id_per,concat(concat(p.nom_per,' '),p.ape_per)  as nombres \n" +
                    "from  entrenamiento e,entrentiempo et,persona p\n" +
                    "where et.id_enttmp = e.entrentiempo_id_enttmp and p.id_per = e.persona_id_per";
                
        
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
    
    public ResultSet buscarEntrenamientosTiempos()
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "select et.descripcion_enttiempo " +
                    "from entrentiempo et " +
                    "where et.estado_entiempo = 1";
                
        
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
        String sql = "select '1',et.costo_enttiempo,concat('ENTRENAMIENTO ',et.descripcion_enttiempo) as descr " +
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
    
    //public
}
