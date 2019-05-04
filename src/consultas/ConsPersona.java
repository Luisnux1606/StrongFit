/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultas;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelos.Analisis;
import modelos.Conexion;
import modelos.Persona;

/**
 *
 * @author Administrator
 */
public class ConsPersona extends Conexion {
    
     
    public boolean registrar(Persona p)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "INSERT INTO persona (id_per,ced_per, nom_per, ape_per, nroFono_per,edad_per,fechaNac_per,mail_per,genero_per,estado_per) VALUES(persona_id_seq.NEXTVAL,?,?,?,?,?,?,?,?,?)";

        try 
        {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getCedula());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellido());
            ps.setString(4, p.getNro_fono());
            ps.setInt(5, p.getEdad());
            ps.setString(6, p.getFecha_nac());  
            ps.setString(7, p.getMail());     
            ps.setString(8, p.getGenero());  
            ps.setInt(9, p.getEstado());  
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
    
    public boolean modificar(Persona p)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE persona SET ced_per=?, nom_per=?, ape_per=?, nroFono_per=?,edad_per=?,fechaNac_per=?,mail_per=?,genero_per=?"
                + " WHERE id_per=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getCedula());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellido());
            ps.setString(4, p.getNro_fono());
            ps.setInt(5, p.getEdad());
            ps.setString(6, p.getFecha_nac());
            ps.setString(7, p.getMail());     
            ps.setString(8, p.getGenero()); 
            ps.setInt(9, p.getId());   
            System.out.println("id "+p.getId()+" edad"+p.getEdad());
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
    
     public boolean eliminar(Persona p)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE persona SET ESTADO_PER=? WHERE id_per=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getEstado());
            ps.setInt(2, p.getId());
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
     
    public boolean buscar(Persona p)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM persona WHERE ced_per=? and estado_per=1";
        
        try 
        {
            
            ps = con.prepareStatement(sql);                     
            ps.setString(1, p.getCedula());
            rs = ps.executeQuery();
            if (rs.next()) { //ced_per, nom_per, ape_per, nroFono_per,edad_per,fechaNac_per
                p.setId(rs.getInt("id_per"));
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
        
    }
    
    
    public ArrayList<Persona> buscarTodos(Persona p)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM Persona where estado_per=1 order by id_per asc";
        ArrayList<Persona> personas = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                p = new Persona();
                p.setId(rs.getInt("id_per"));
                p.setCedula(rs.getString("ced_per"));
                p.setNombre(rs.getString("nom_per"));
                p.setApellido(rs.getString("ape_per"));
                p.setGenero(rs.getString("genero_per"));
                p.setMail(rs.getString("mail_per"));
                p.setNro_fono(rs.getString("nroFono_per"));
                p.setEdad(rs.getInt("edad_per"));                
                p.setFecha_nac(rs.getString("fechaNac_per")+"");         
                
                personas.add(p);               
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
       return personas ;
    }
    
     public ArrayList<Persona> buscarTodosPorCed(Persona p,String ced)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null; //'%"+cad+"%'
        String sql = "SELECT * FROM Persona "
                + " where ced_per like '%"+ced+"%' and estado_per=1"
                + " order by id_per asc ";
        ArrayList<Persona> personas = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                p = new Persona();
                p.setId(rs.getInt("id_per"));
                p.setCedula(rs.getString("ced_per"));
                p.setNombre(rs.getString("nom_per"));
                p.setApellido(rs.getString("ape_per"));
                p.setGenero(rs.getString("genero_per"));
                p.setMail(rs.getString("mail_per"));
                p.setNro_fono(rs.getString("nroFono_per"));
                p.setEdad(rs.getInt("edad_per"));                
                p.setFecha_nac(rs.getString("fechaNac_per")+"");         
                
                personas.add(p);               
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
       return personas ;
    }
     
     public ArrayList<Persona> buscarTodosPorNom(Persona p,String nom)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null; //'%"+cad+"%'
        String sql = "SELECT * FROM Persona "
                + " where upper(nom_per) like upper('%"+nom+"%') and estado_per=1"
                + " order by id_per asc ";
        ArrayList<Persona> personas = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                p = new Persona();
                p.setId(rs.getInt("id_per"));
                p.setCedula(rs.getString("ced_per"));
                p.setNombre(rs.getString("nom_per"));
                p.setApellido(rs.getString("ape_per"));
                p.setGenero(rs.getString("genero_per"));
                p.setMail(rs.getString("mail_per"));
                p.setNro_fono(rs.getString("nroFono_per"));
                p.setEdad(rs.getInt("edad_per"));                
                p.setFecha_nac(rs.getString("fechaNac_per")+"");         
                
                personas.add(p);               
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
       return personas ;
    }
    
}
