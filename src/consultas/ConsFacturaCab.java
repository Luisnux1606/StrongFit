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
import modelos.Medidas;
import modelos.Persona;

/**
 *
 * @author Administrator
 */
public class ConsFacturaCab extends Conexion {
    
    public boolean registrar(FacturaCab f)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "INSERT INTO ficha (id_ficha,fechaIni_ficha, fechaFin_ficha, valPago_ficha, valPendiente_ficha,concepto_ficha, Persona_id_per, Analisis_id_ana, Medidas_id_med,estado_ficha) "
                + " VALUES(ficha_id_seq.NEXTVAL,?,?,?,?,?,?,?,?,?)";

        try 
        {            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, f.getFecha_ini());
            ps.setString(2, f.getFecha_fin());
            ps.setDouble(3, f.getVal_pago());
            ps.setDouble(4, f.getVal_pendiente());
            ps.setString(5, f.getConcepto());
            ps.setInt(6, f.getPersona().getId());
            ps.setInt(7, f.getAnalisis().getId());
            ps.setInt(8, f.getMedidas().getId());
            ps.setInt(9, f.getEstado());
            
            
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
    
    public boolean modificar(FacturaCab f)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE ficha SET fechaIni_ficha=?, fechaFin_ficha=?, valPago_ficha=?, valPendiente_ficha=?,concepto_ficha=?"
                + " WHERE id_ficha=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, f.getFecha_ini());
            ps.setString(2, f.getFecha_fin());
            ps.setDouble(3, f.getVal_pago());
            ps.setDouble(4, f.getVal_pendiente());
            ps.setString(5, f.getConcepto());
           // ps.setInt(6, f.getPersona().getId());
           // ps.setInt(7, f.getAnalisis().getId());
            //ps.setInt(8, f.getMedidas().getId());
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
    
     public boolean eliminar(FacturaCab f)
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
     
    public boolean buscar(FacturaCab f)
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
                f.setFecha_ini(rs.getString("fechaIni_ficha"));
                f.setFecha_fin(rs.getString("fechaFin_ficha"));
                f.setVal_pago(rs.getDouble("valPago_ficha"));
                f.setVal_pendiente(rs.getInt("valPendiente_ficha"));
                
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
    
    
    public ArrayList<FacturaCab> buscarTodos(FacturaCab f)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM Ficha where estado_ficha=1";
        ArrayList<FacturaCab> fichas = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                f.setId(rs.getInt("id_ficha"));
                f.setFecha_fin(rs.getString("fechaFin_ficha"));
                f.setVal_pago(rs.getDouble("valPago_ficha"));
                f.setVal_pendiente(rs.getInt("valPendiente_ficha"));
                
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
    
    public ArrayList<FacturaCab> buscarTodosPorFec(FacturaCab f,String fech)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null; 
        String sql = "SELECT * FROM ficha "
                + " where fechaIni_ficha like '%"+fech+"%' and estado_ficha=1"
                + " order by id_ficha asc ";
        ArrayList<FacturaCab> ficha = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                f = new FacturaCab();
                f.setId(rs.getInt("id_ficha"));
                f.setFecha_fin(rs.getString("fechaFin_ficha"));
                f.setVal_pago(rs.getDouble("valPago_ficha"));
                f.setVal_pendiente(rs.getInt("valPendiente_ficha"));
                
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
    
    public ResultSet buscarTodosPorNomTabla(String nom)
    {
        System.out.println(nom);
        PreparedStatement ps = null;
        con = getConexion();
        ResultSet rs = null; 
        String sql = " SELECT f.id_ficha,   p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaIni_ficha, f.fechaFin_ficha,f.concepto_ficha, f.valPago_ficha, f.valPendiente_ficha,f.concepto_ficha \n" +
                        "FROM ficha f, persona p \n" +
                        "where upper(p.nom_per) like upper('%"+nom+"%')  and p.id_per=f.persona_id_per and f.estado_ficha=1\n" +
                        "UNION\n" +
                        "SELECT f.id_ficha,p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaIni_ficha , f.fechaFin_ficha,f.concepto_ficha, f.valPago_ficha, f.valPendiente_ficha,f.concepto_ficha \n" +
                        "FROM ficha f, persona p \n" +
                        "where upper(p.ced_per) like upper('%"+nom+"%')  and p.id_per=f.persona_id_per and f.estado_ficha=1\n" +
                        "UNION\n" +
                        "SELECT f.id_ficha,p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaIni_ficha , f.fechaFin_ficha,f.concepto_ficha, f.valPago_ficha, f.valPendiente_ficha,f.concepto_ficha \n" +
                        "FROM ficha f, persona p \n" +
                        "where upper(f.fechaIni_ficha) like upper('%"+nom+"%')  and p.id_per=f.persona_id_per and f.estado_ficha=1\n" +
                        "UNION\n" +
                        "SELECT f.id_ficha,p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaIni_ficha , f.fechaFin_ficha,f.concepto_ficha, f.valPago_ficha, f.valPendiente_ficha,f.concepto_ficha \n" +
                        "FROM ficha f, persona p \n" +
                        "where upper(f.fechaFin_ficha) like upper('%"+nom+"%')  and p.id_per=f.persona_id_per and f.estado_ficha=1";
                
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
        String sql = " SELECT f.id_ficha, p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaIni_ficha AS fechaIni_ficha, f.fechaFin_ficha AS fechaFin_ficha, f.valPago_ficha, f.valPendiente_ficha,f.concepto_ficha " +
                    " FROM ficha f, persona p " +
                    " where p.id_per = f.Persona_id_per and f.estado_ficha=1 " +
                    " order by id_ficha asc ";
                
        
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
