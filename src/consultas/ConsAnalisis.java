/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultas;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelos.Analisis;
import modelos.Conexion;
import modelos.Ficha;
import modelos.Medidas;

/**
 *
 * @author Administrator
 */
public class ConsAnalisis extends Conexion{
    
    
    public boolean registrar(Analisis a)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "INSERT INTO analisis (id_ana,fecha_ana, exesoGrasa_ana, exesoLiquido_ana, exesoTotal_ana,recomendacionPesas_ana,recomendacionCardio_ana,recomendacionFuncional_ana,estado_ana) "
                + " VALUES(analisis_id_seq.NEXTVAL,?,?,?,?,?,?,?,?)";
     //   String sql2 = "INSERT INTO Ficha (fecha_ini,fecha_fin,val_pago,val_pendiente) VALUES(?,?,?,?)";
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            
            ps.setString(1, a.getFecha());
            ps.setDouble(2, a.getExeso_grasa());
            ps.setDouble(3, a.getExeso_liquido());
            ps.setDouble(4, a.getExeso_total());
            ps.setString(5, a.getRecomendacion_pesas());
            ps.setString(6, a.getRecomendacion_cardio());
            ps.setString(7, a.getRecomendacion_funcional());
            ps.setInt(8, a.getEstado());
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
    
    public boolean modificar(Analisis a)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE analisis SET fecha_ana=?, exesoGrasa_ana=?, exesoLiquido_ana=?, exesoTotal_ana=?,recomendacionPesas_ana=?,recomendacionCardio_ana=?,recomendacionFuncional_ana=?"
                + " WHERE id_ana=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, a.getFecha());
            ps.setDouble(2, a.getExeso_grasa());
            ps.setDouble(3, a.getExeso_liquido());
            ps.setDouble(4, a.getExeso_total());
            ps.setString(5, a.getRecomendacion_pesas());
            ps.setString(6, a.getRecomendacion_cardio());
            ps.setString(7, a.getRecomendacion_funcional());
            ps.setInt(8, a.getId());
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
    
     public boolean eliminar(Analisis a)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE  analisis SET ESTADO_ANA  = ? WHERE id_ana=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);           
            ps.setInt(1, a.getEstado());
            ps.setInt(2, a.getId());
            ps.execute();
            return true;
        } 
        catch (Exception e) 
        {
            
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Es posible que este registro se este usando en una ficha...");
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
        String sql = " select max(id_ana) as ID_ANA from analisis " +
                     " order by id_ana asc " +
                     " ";
        
        int idMedida = 0;
       
        try 
        {
            
            ps = con.prepareStatement(sql);                                 
            rs = ps.executeQuery();

            if (rs.next()) { 
               idMedida = rs.getInt("ID_ANA");
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
     
    public boolean buscar(Analisis a)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM analisis WHERE fecha_ana=? and estado_ana=1";
        
        try 
        {
            
            ps = con.prepareStatement(sql);                     
            ps.setString(1, a.getFecha());
            rs = ps.executeQuery();
            if (rs.next()) {
                a.setId(rs.getInt("id_ana"));
                a.setFecha(rs.getString("fecha_ana"));
                a.setExeso_grasa(rs.getDouble("exesoGrasa_ana"));
                a.setExeso_liquido(rs.getDouble("exesoLiquido_ana"));
                a.setExeso_total(rs.getDouble("exesoTotal_ana"));
                a.setRecomendacion_pesas(rs.getString("recomendacionPesas_ana"));
                a.setRecomendacion_cardio(rs.getString("recomendacionCardio_ana"));
                a.setRecomendacion_funcional(rs.getString("recomendacionFuncional_ana"));
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
    
    
    public ArrayList<Analisis> buscarTodos(Analisis a)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM analisis where estado_ana=1";
        ArrayList<Analisis> analisis = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                a = new Analisis();
                a.setId(rs.getInt("id_ana"));
                a.setFecha(rs.getString("fecha_ana"));
                a.setExeso_grasa(rs.getDouble("exesoGrasa_ana"));
                a.setExeso_liquido(rs.getDouble("exesoLiquido_ana"));
                a.setExeso_total(rs.getDouble("exesoTotal_ana"));
                a.setRecomendacion_pesas(rs.getString("recomendacionPesas_ana"));
                a.setRecomendacion_cardio(rs.getString("recomendacionCardio_ana"));
                a.setRecomendacion_funcional(rs.getString("recomendacionFuncional_ana"));                
                
                analisis.add(a);               
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
       return analisis ;
    }
    
    public ArrayList<Analisis> buscarTodosPorFec(Analisis a,String fech)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null; //'%"+cad+"%'
        String sql = "SELECT * FROM Analisis "
                + " where fecha_ana like '%"+fech+"%' and estado_ana=1"
                + " order by id_ana asc ";
        ArrayList<Analisis> analisis = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                a = new Analisis();
                a.setId(rs.getInt("id_ana"));
                a.setFecha(rs.getString("fecha_ana"));
                a.setExeso_grasa(rs.getDouble("exesoGrasa_ana"));
                a.setExeso_liquido(rs.getDouble("exesoLiquido_ana"));
                a.setExeso_total(rs.getDouble("exesoTotal_ana"));
                a.setRecomendacion_pesas(rs.getString("recomendacionPesas_ana"));
                a.setRecomendacion_cardio(rs.getString("recomendacionCardio_ana"));
                a.setRecomendacion_funcional(rs.getString("recomendacionFuncional_ana"));  
                
                analisis.add(a);                     
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
       return analisis ;
    }
}
