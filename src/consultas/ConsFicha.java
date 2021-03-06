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
import modelos.Analisis;
import modelos.Conexion;
import modelos.FacturaCab;
import modelos.Ficha;
import modelos.Medidas;
import modelos.Persona;

/**
 *
 * @author Administrator
 */
public class ConsFicha extends Conexion {
    
    public boolean registrar(Ficha f)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "INSERT INTO ficha (id_ficha,fecha_ficha,Persona_id_per, Analisis_id_ana, Medidas_id_med,estado_ficha) "
                + " VALUES(ficha_id_seq.NEXTVAL,?,?,?,?,?)";

        try 
        {            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, f.getFecha());         
            ps.setInt(2, f.getPersona().getId());
            ps.setInt(3, f.getAnalisis().getId());
            ps.setInt(4, f.getMedidas().getId());
            ps.setInt(5, f.getEstado());
            
            
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
    
    public boolean modificar(Ficha f)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE ficha SET fecha_ficha = ?,Persona_id_per = ?, Analisis_id_ana = ?, Medidas_id_med = ?,estado_ficha = ?"
                + " WHERE id_ficha=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, f.getFecha());                     
            ps.setInt(2, f.getPersona().getId());
            ps.setInt(3, f.getAnalisis().getId());
            ps.setInt(4, f.getMedidas().getId());
            ps.setInt(5, 1);
            ps.setInt(6, f.getId());
          
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
    
    public boolean modificarFichaMedidas(Ficha f)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE ficha SET Medidas_id_med = ?"
                + " WHERE id_ficha=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, f.getMedidas().getId());            
            ps.setInt(2, f.getId());
          
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
    
    public boolean modificarFichaAnalisis(Ficha f)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE ficha SET Analisis_id_ana = ?"
                + " WHERE id_ficha=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, f.getAnalisis().getId());            
            ps.setInt(2, f.getId());
          
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
    
     public boolean eliminar(Ficha f)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE  ficha SET ESTADO_FICHA =? WHERE id_ficha=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);  
            ps.setInt(1, f.getEstado());
            ps.setInt(2, f.getId());
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
     
    public boolean buscar(Ficha f)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM ficha WHERE id_ficha=? and estado_ficha=1";
        /*
             fechaIni_ficha, fechaFin_ficha, valPago_ficha, valPendiente_ficha, Persona_id_per, Analisis_id_ana, Medidas_id_med
        */
        try 
        {
            
            ps = con.prepareStatement(sql);                     
            ps.setInt(1, f.getId());
            rs = ps.executeQuery();
            if (rs.next()) { 
                f.setId(rs.getInt("id_ficha"));
                                
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
    
    
    public ArrayList<Ficha> buscarTodos(Ficha f)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM Ficha where estado_ficha=1";
        ArrayList<Ficha> fichas = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                f.setId(rs.getInt("id_ficha"));
               
                
                fichas.add(f);               
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
       return fichas ;
    }
    
    /*
    
    SELECT p.ced_per, p.nom_per, f.fechaIni_ficha, f.fechaFin_ficha, f.valPago_ficha, f.valPendiente_ficha
FROM ficha f, persona p
where p.id_per = f.Persona_id_per and f.fechaIni_ficha like '%11%'
order by id_ficha asc ;
    */
    
    public ArrayList<Ficha> buscarTodosPorFec(Ficha f,String fech)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null; 
        String sql = "SELECT * FROM ficha "
                + " where fechaIni_ficha like '%"+fech+"%' and estado_ficha=1"
                + " order by id_ficha asc ";
        ArrayList<Ficha> ficha = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                f = new Ficha();
                f.setId(rs.getInt("id_ficha"));
                              
                ficha.add(f);                     
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
       return ficha ;
    }
    
    public ArrayList<String> buscarAnonyms()
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null; 
        String sql = " select  p.id_per,a.id_ana,m.id_med " +
                    " from persona p, medidas m, analisis a " +
                    " where p.ced_per like '999999999' and p.nom_per like 'anonimo' and " +
                    " m.fecha_med like '999999999' and m.edad_med = 999 and a.fecha_ana like '999999999' " +
                    " and a.recomendacionCardio_ana like '999999999' ";
        ArrayList<String> anoni = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
               anoni.add(0,rs.getInt("id_per")+"");
               anoni.add(1,rs.getInt("id_ana")+"");
               anoni.add(2, rs.getInt("id_med")+"");
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
       return anoni ;
    }
    
    public ArrayList<String> buscarMemgresias()
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null; 
        String sql = " select  p.id_per,a.id_ana,m.id_med " +
                    " from persona p, medidas m, analisis a " +
                    " where p.ced_per like '999999999' and p.nom_per like 'anonimo' and " +
                    " m.fecha_med like '999999999' and m.edad_med = 999 and a.fecha_ana like '999999999' " +
                    " and a.recomendacionCardio_ana like '999999999' ";
        ArrayList<String> anoni = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
               anoni.add(0,rs.getInt("id_per")+"");
               anoni.add(1,rs.getInt("id_ana")+"");
               anoni.add(2, rs.getInt("id_med")+"");
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
       return anoni ;
    }
    
     Connection con;
    public ResultSet buscarTodosPorFecTabla(String fech)
    {
        PreparedStatement ps = null;
        con = getConexion();
        ResultSet rs = null; 
        String sql = "SELECT f.id_ficha, p.ced_per, p.nom_per, f.fechaIni_ficha, f.fechaFin_ficha,f.concepto_ficha, f.valPago_ficha, f.valPendiente_ficha,f.concepto_ficha " +
                    " FROM ficha f, persona p " +
                    " where p.id_per = f.Persona_id_per and f.fechaIni_ficha like '%"+fech+"%' and f.estado_ficha=1" +
                    " order by id_ficha asc ";
                
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
    
    public ResultSet buscarTodosPorNomTabla(String cad)
    {
   
        PreparedStatement ps = null;
        con = getConexion();
        ResultSet rs = null; 
        String sql = " SELECT f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n" +
                    "FROM  ficha f, medidas m,analisis a, persona p\n" +
                    "where f.id_Ficha  like '%"+cad+"%' and p.id_per = f.Persona_id_per and f.estado_ficha=1 and a.id_ana = f.analisis_id_ana and m.id_med = f.medidas_id_med\n" +
                    "UNION\n" +
                    "SELECT f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n" +
                    "FROM  ficha f, medidas m,analisis a, persona p\n" +
                    "where upper(f.fecha_ficha) like upper('%"+cad+"%')  and p.id_per = f.Persona_id_per and f.estado_ficha=1 and a.id_ana = f.analisis_id_ana and m.id_med = f.medidas_id_med\n" +
                    "UNION\n" +
                    "SELECT f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n" +
                    "FROM  ficha f, medidas m,analisis a, persona p\n" +
                    "where upper(p.nom_per) like upper('%"+cad+"%') and p.id_per = f.Persona_id_per and f.estado_ficha=1 and a.id_ana = f.analisis_id_ana and m.id_med = f.medidas_id_med\n" +
                    "UNION\n" +
                    "SELECT f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n" +
                    "FROM  ficha f, medidas m,analisis a, persona p\n" +
                    "where upper(p.ape_per) like upper('%"+cad+"%') and p.id_per = f.Persona_id_per and f.estado_ficha=1 and a.id_ana = f.analisis_id_ana and m.id_med = f.medidas_id_med\n" +
                    "UNION\n" +
                    "SELECT f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n" +
                    "FROM  ficha f, medidas m,analisis a, persona p\n" +
                    "where p.id_per like '%"+cad+"%' and p.id_per = f.Persona_id_per and f.estado_ficha=1 and a.id_ana = f.analisis_id_ana and m.id_med = f.medidas_id_med\n" +
                    "UNION\n" +
                    "SELECT f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n" +
                    "FROM  ficha f, medidas m,analisis a, persona p\n" +
                    "where m.fecha_med like upper('%"+cad+"%') and p.id_per = f.Persona_id_per and f.estado_ficha=1 and a.id_ana = f.analisis_id_ana and m.id_med = f.medidas_id_med\n" +
                    "UNION\n" +
                    "SELECT f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n" +
                    "FROM  ficha f, medidas m,analisis a, persona p\n" +
                    "where m.id_med like '%"+cad+"%' and p.id_per = f.Persona_id_per and f.estado_ficha=1 and a.id_ana = f.analisis_id_ana and m.id_med = f.medidas_id_med\n" +
                    "UNION\n" +
                    "SELECT f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n" +
                    "FROM  ficha f, medidas m,analisis a, persona p\n" +
                    "where a.fecha_ana like upper('%"+cad+"%') and p.id_per = f.Persona_id_per and f.estado_ficha=1 and a.id_ana = f.analisis_id_ana and m.id_med = f.medidas_id_med\n" +
                    "UNION\n" +
                    "SELECT f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n" +
                    "FROM  ficha f, medidas m,analisis a, persona p\n" +
                    "where a.id_ana  like '%"+cad+"%' and p.id_per = f.Persona_id_per and f.estado_ficha=1 and a.id_ana = f.analisis_id_ana and m.id_med = f.medidas_id_med";
                
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
    
    public ResultSet buscarPendientes()
    {
        
        PreparedStatement ps = null;
        con = getConexion();
        ResultSet rs = null; 
        String sql = " SELECT f.id_ficha,   p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaIni_ficha, f.fechaFin_ficha,f.concepto_ficha, f.valPago_ficha, f.valPendiente_ficha,f.concepto_ficha \n" +
                        "FROM ficha f, persona p \n" +
                        "where  p.id_per=f.persona_id_per and f.valPendiente_ficha>0 and f.estado_ficha=1" ;
                       
                
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
    
    public ResultSet buscarTodos2()
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "  SELECT f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana " +
                        "FROM ficha f, medidas m,analisis a, persona p " +
                        "where p.id_per = f.Persona_id_per and f.estado_ficha=1 and a.id_ana = f.analisis_id_ana and m.id_med = f.medidas_id_med " +
                        "order by id_ficha asc ";
                
        
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
    
    public ResultSet buscarTodos2ByIdPer(int idPer)
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "  SELECT f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana " +
                        "FROM ficha f, medidas m,analisis a, persona p " +
                        "where p.id_per = f.Persona_id_per and f.estado_ficha=1 and p.estado_per=1 and m.estado_med=1 and a.estado_ana=1 and a.id_ana = f.analisis_id_ana and m.id_med = f.medidas_id_med and p.id_per="+idPer+"" +
                        "order by id_ficha desc ";
                
        
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
