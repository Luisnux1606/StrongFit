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
import modelos.Conexion;
import modelos.Medidas;
import modelos.Persona;

/**
 *
 * @author Administrator
 */
public class ConsMedidas extends Conexion {
    
    public boolean registrar(Medidas m)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "INSERT INTO medidas (id_med,fecha_med, peso_med, estatura_med, edad_med,nroHijos_med,pecho_med,abdomenAlto_med,cintura_med,"
                +                       "  abdomenBajo_med,cadera_med,pierna_med,pantorrilla_med,brazo_med,antebrazo_med,cuello_med,espalda_med,"
                +                       "  porcentajeGrasa_med,porcentajeklgs_med,estado_med ) VALUES(medidas_id_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try 
        {            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, m.getFecha());
            ps.setDouble(2, m.getPeso());
            ps.setDouble(3, m.getEstatura());
            ps.setInt(4, m.getEdad());
            ps.setInt(5, m.getNro_hijos());     
            ps.setDouble(6, m.getPecho());   
            ps.setDouble(7, m.getAbdomen_alto());   
            ps.setDouble(8, m.getCintura());   
            ps.setDouble(9, m.getAbdomen_bajo());   
            ps.setDouble(10, m.getCadera());   
            ps.setDouble(11, m.getPiernas());   
            ps.setDouble(12, m.getPantorrilla());   
            ps.setDouble(13, m.getBrazo());   
            ps.setDouble(14, m.getAntebrazo());   
            ps.setDouble(15, m.getCuello());   
            ps.setDouble(16, m.getEspalda());   
            ps.setDouble(17, m.getPorcentaje_grasa());   
            ps.setDouble(18, m.getPorcentaje_kgs());   
            ps.setInt(19, m.getEstado()); 
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
    
    public int getLastId()
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = " select max(id_med) as ID_MED from medidas  " +
                     " order by id_med" +
                     " ";
        
        int idMedida = 0;
       
        try 
        {
            
            ps = con.prepareStatement(sql);                                 
            rs = ps.executeQuery();

            if (rs.next()) { 
               idMedida = rs.getInt("ID_MED");
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
                System.err.println(e);
            }
        }
       return idMedida; 
    }
    
    public boolean modificar(Medidas m)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE medidas SET fecha_med=?, peso_med=?, estatura_med=?, edad_med=?,nroHijos_med=?,pecho_med=?,abdomenAlto_med=?,cintura_med=?,"
                +                       "  abdomenBajo_med=?,cadera_med=?,pierna_med=?,pantorrilla_med=?,brazo_med=?,antebrazo_med=?,cuello_med=?,espalda_med=?,"
                +                       "  porcentajeGrasa_med=?,porcentajeklgs_med=? "
                + " WHERE id_med=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, m.getFecha());
            ps.setDouble(2, m.getPeso());
            ps.setDouble(3, m.getEstatura());
            ps.setInt(4, m.getEdad());
            ps.setInt(5, m.getNro_hijos());     
            ps.setDouble(6, m.getPecho());   
            ps.setDouble(7, m.getAbdomen_alto());   
            ps.setDouble(8, m.getCintura());   
            ps.setDouble(9, m.getAbdomen_bajo());   
            ps.setDouble(10, m.getCadera());   
            ps.setDouble(11, m.getPiernas());   
            ps.setDouble(12, m.getPantorrilla());   
            ps.setDouble(13, m.getBrazo());   
            ps.setDouble(14, m.getAntebrazo());   
            ps.setDouble(15, m.getCuello());   
            ps.setDouble(16, m.getEspalda());   
            ps.setDouble(17, m.getPorcentaje_grasa());   
            ps.setDouble(18, m.getPorcentaje_kgs());   
            ps.setInt(19, m.getId());
            
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
    
     public boolean eliminar(Medidas m)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE medidas SET ESTADO_MED=? "
                + " WHERE id_med=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);  
            ps.setInt(1, m.getEstado());
            ps.setInt(2, m.getId());
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
     
    public boolean buscar(Medidas m)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM medidas where estado_med=1 "
                + " WHERE fecha_med=?";
        /*
        echa_med, peso_med, estatura_med, edad_med,nroHijos_med,pecho_med,abdomenAlto_med,cintura_med,"
                +                       "  abdomenBajo_med,cadera_med,pierna_med,pantorrilla_med,brazo_med,antebrazo_med,cuello_med,espalda_med,"
                +                       "  porcentajeGrasa_med,porcentajeklgs_med 
        */
        try 
        {
            
            ps = con.prepareStatement(sql);                     
            ps.setString(1, m.getFecha());
            rs = ps.executeQuery();
            if (rs.next()) { 
                m.setId(rs.getInt("id_med"));
                m.setFecha(rs.getString("fecha_med"));
                m.setPeso(rs.getDouble("peso_med"));
                m.setEstatura(rs.getDouble("estatura_med"));
                m.setEdad(rs.getInt("edad_med"));
                m.setNro_hijos(rs.getInt("nroHijos_med"));
                m.setPecho(rs.getDouble("pecho_med"));
                m.setAbdomen_alto(rs.getDouble("abdomenAlto_med"));
                m.setCintura(rs.getDouble("cintura_med"));
                m.setAbdomen_bajo(rs.getDouble("abdomenBajo_med"));
                m.setCadera(rs.getDouble("cadera_med"));
                m.setPiernas(rs.getDouble("pierna_med"));
                m.setPantorrilla(rs.getDouble("pantorrilla_med"));
                m.setBrazo(rs.getDouble("brazo_med"));
                m.setAntebrazo(rs.getDouble("antebrazo_med"));
                m.setCuello(rs.getDouble("cuello_med"));
                m.setEspalda(rs.getDouble("espalda_med"));
                m.setPorcentaje_grasa(rs.getDouble("porcentajeGrasa_med"));
                m.setPorcentaje_kgs(rs.getDouble("porcentajeklgs_med"));
                
               
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
    
    
    public ArrayList<Medidas> buscarTodos(Medidas m)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM medidas where estado_med=1";
        ArrayList<Medidas> medidas = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
               
                m=new Medidas();
                
                m.setId(rs.getInt("id_med"));
                m.setFecha(rs.getString("fecha_med"));
                m.setPeso(rs.getDouble("peso_med"));
                m.setEstatura(rs.getDouble("estatura_med"));
                m.setEdad(rs.getInt("edad_med"));
                m.setNro_hijos(rs.getInt("nroHijos_med"));
                m.setPecho(rs.getDouble("pecho_med"));
                m.setAbdomen_alto(rs.getDouble("abdomenAlto_med"));
                m.setCintura(rs.getDouble("cintura_med"));
                m.setAbdomen_bajo(rs.getDouble("abdomenBajo_med"));
                m.setCadera(rs.getDouble("cadera_med"));
                m.setPiernas(rs.getDouble("pierna_med"));
                m.setPantorrilla(rs.getDouble("pantorrilla_med"));
                m.setBrazo(rs.getDouble("brazo_med"));
                m.setAntebrazo(rs.getDouble("antebrazo_med"));
                m.setCuello(rs.getDouble("cuello_med"));
                m.setEspalda(rs.getDouble("espalda_med"));
                m.setPorcentaje_grasa(rs.getDouble("porcentajeGrasa_med"));
                m.setPorcentaje_kgs(rs.getDouble("porcentajeklgs_med"));  
                
                medidas.add(m);               
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
       return medidas ;
    }
    
    public ArrayList<Medidas> buscarTodosPorFec(Medidas m,String fech)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null; //'%"+cad+"%'
        String sql = "SELECT * FROM Medidas "
                + " where fecha_med like '%"+fech+"%' and estado_med=1"
                + " order by id_med asc ";
        ArrayList<Medidas> medidas = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                m = new Medidas();
                m.setId(rs.getInt("id_med"));
                m.setFecha(rs.getString("fecha_med"));
                m.setPeso(rs.getDouble("peso_med"));
                m.setEstatura(rs.getDouble("estatura_med"));
                m.setEdad(rs.getInt("edad_med"));
                m.setNro_hijos(rs.getInt("nroHijos_med"));
                m.setPecho(rs.getDouble("pecho_med"));
                m.setAbdomen_alto(rs.getDouble("abdomenAlto_med"));
                m.setCintura(rs.getDouble("cintura_med"));
                m.setAbdomen_bajo(rs.getDouble("abdomenBajo_med"));
                m.setCadera(rs.getDouble("cadera_med"));
                m.setPiernas(rs.getDouble("pierna_med"));
                m.setPantorrilla(rs.getDouble("pantorrilla_med"));
                m.setBrazo(rs.getDouble("brazo_med"));
                m.setAntebrazo(rs.getDouble("antebrazo_med"));
                m.setCuello(rs.getDouble("cuello_med"));
                m.setEspalda(rs.getDouble("espalda_med"));
                m.setPorcentaje_grasa(rs.getDouble("porcentajeGrasa_med"));
                m.setPorcentaje_kgs(rs.getDouble("porcentajeklgs_med"));  
                
                medidas.add(m);                     
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
       return medidas ;
    }
    
}
