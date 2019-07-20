/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultas;

import assets.Validaciones;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelos.Analisis;
import modelos.Conexion;
import modelos.Persona;
import modelos.TipoPersona;

/**
 *
 * @author Administrator
 */
public class ConsPersona extends Conexion 
{     
    public boolean registrar(Persona p)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "INSERT INTO persona (id_per,ced_per, nom_per, ape_per, nroFono_per,edad_per,fechaNac_per,mail_per,genero_per,TIPOPERSONA_ID_TIPOPER,estado_per,estadosalud_per) VALUES(persona_id_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?)";

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
            ps.setInt(9, p.getTipoPersona().getId_tipoPer());
            ps.setInt(10, p.getEstado());  
            ps.setString(11, p.getEstadoSalud());
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
        String sql = "UPDATE persona SET ced_per=?, nom_per=?, ape_per=?, nroFono_per=?,"
                + " edad_per=?,fechaNac_per=?,mail_per=?,genero_per=?, TIPOPERSONA_ID_TIPOPER=?, ESTADOSALUD_PER =? "
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
            ps.setInt(9, p.getTipoPersona().getId_tipoPer()); 
            System.out.println(p.getEstadoSalud());
            ps.setString(10, p.getEstadoSalud().trim());
            ps.setInt(11, p.getId()); 

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
                TipoPersona tipoPer = new TipoPersona();
                    tipoPer.setId_tipoPer(rs.getInt("TIPOPERSONA_ID_TIPOPER"));
                    tipoPer.setDescripcion_tipoPer(getNomByIdPerByNom(tipoPer.getId_tipoPer()+"")+"");
               p.setTipoPersona(tipoPer);
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
    public int getIdTipoPerByNom(String cat){
		String sql;
		int result=0;
                PreparedStatement ps = null;
                con = getConexion();
                ResultSet rs = null; 
                
		sql="select tp.id_tipoper " +
                    "from tipoPersona tp " +
                    "where tp.estado_tipoper =1 and upper(tp.DESCRIPCION_TIPOPER) like upper('"+cat+"')";						
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
    
    public String getNomByIdPerByNom(String cat){
		String sql;
		String result="";
                PreparedStatement ps = null;
               // con = getConexion();
                ResultSet rs = null; 
                
		sql="select tp.DESCRIPCION_TIPOPER " +
                    "from tipoPersona tp " +
                    "where tp.estado_tipoper =1 and tp.id_tipoper = "+cat+"";						
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
    
    
    public ResultSet buscarPersonasClientes()
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = "select * " +
                    "from Persona p " +
                    "where p.estado_per = 1 order by ape_per";
                
        
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
    public ArrayList<Persona> buscarTodos(Persona p)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM Persona where estado_per=1 order by ape_per asc";
        ArrayList<Persona> personas = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                p = new Persona();
                p.setId(rs.getInt("id_per"));
                p.setCedula(Validaciones.isNumVoid4(rs.getString("ced_per")));
                p.setNombre(Validaciones.isNumVoid4(rs.getString("nom_per")));
                p.setApellido(Validaciones.isNumVoid4(rs.getString("ape_per")));
                p.setGenero(rs.getString("genero_per"));
                p.setMail(Validaciones.isNumVoid4(rs.getString("mail_per")));
                p.setNro_fono(Validaciones.isNumVoid4(rs.getString("nroFono_per")));
                p.setEdad(rs.getInt("edad_per"));                
                p.setFecha_nac(rs.getString("fechaNac_per")+"");    
                    TipoPersona tipoPer = new TipoPersona();
                    tipoPer.setId_tipoPer(rs.getInt("TIPOPERSONA_ID_TIPOPER"));
                    tipoPer.setDescripcion_tipoPer(getNomByIdPerByNom(tipoPer.getId_tipoPer()+"")+"");
                p.setTipoPersona(tipoPer);  
                p.setEstadoSalud(Validaciones.isNumVoid4(rs.getString("ESTADOSALUD_PER")));
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
                p.setEstadoSalud(rs.getString("ESTADOSALUD_PER"));
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
        String sql = "SELECT * FROM Persona p , TIPOPERSONA tp  " +
                    "  where upper(concat(concat(concat(concat(nom_per,' '),ape_per), ' '),p.ced_per)) like upper('%"+nom+"%') and estado_per=1 and tp.ID_TIPOPER = p.TIPOPERSONA_ID_TIPOPER " +
                    "  order by ape_per asc ";
        ArrayList<Persona> personas = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                p = new Persona();
                p.setId(rs.getInt("id_per"));
                p.setCedula(Validaciones.isNumVoid4(rs.getString("ced_per")));
                p.setNombre(Validaciones.isNumVoid4(rs.getString("nom_per")));
                p.setApellido(Validaciones.isNumVoid4(rs.getString("ape_per")));
                p.setGenero(Validaciones.isNumVoid4(rs.getString("genero_per")));
                p.setMail(Validaciones.isNumVoid4(rs.getString("mail_per")));
                p.setNro_fono(Validaciones.isNumVoid4(rs.getString("nroFono_per")));
                p.setEdad(rs.getInt("edad_per"));                
                p.setFecha_nac(Validaciones.isNumVoid4(rs.getString("fechaNac_per")+""));         
                TipoPersona tipoPer = new TipoPersona();
                    tipoPer.setId_tipoPer(rs.getInt("TIPOPERSONA_ID_TIPOPER"));
                    tipoPer.setDescripcion_tipoPer(getNomByIdPerByNom(tipoPer.getId_tipoPer()+"")+"");
                p.setTipoPersona(tipoPer);  
                p.setEstadoSalud(Validaciones.isNumVoid4(rs.getString("ESTADOSALUD_PER")));
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
     public String getCedByCed(String ced){
		String sql;
		String result="";
                PreparedStatement ps = null;
                con = getConexion();
                ResultSet rs = null; 
                
		sql="select p.ced_per " +
                    "from persona p " +
                    "where p.ced_per like '"+ced+"' and p.estado_per=1";						
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
     
     
}
