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
     
     
    public ResultSet buscarIngresosEgresosCampos(String cadCamp)
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql =    " select  fC.Id_Faccab,fC.Fecha_Faccab,concat(concat(p.nom_per,' '),p.ape_per) as nombres,fC.Num_Faccab, " +
                        " fD.CANTIDAD_FACDET as cant,fD.Descripcion_Facdet,' 'as FechaInicio,''as FechaFin,fC.Total_Faccab, 0 as egreso,fC.Valcancelo_Faccab," +
                        " fC.Valpendiente_Faccab,fC.Valajuste_Faccab,'' as estadoEnt, " +
                        " (select sum(fc2.valpendiente_faccab - fc2.valajuste_faccab + 0.0) " +
                        " from facturacabecera fC2 " +
                        " where p.id_per = fC2.persona_id_per and fC2.ESTADO_FACCAB=1 " +
                        " ) as saldoP , p.id_per,pro.id_prod,1 as codHist ,fC.NUM_REGISTRO_FACCAB " +
                        " from persona p, facturacabecera fC,FacturaDetalle fD,producto pro,categoria ca  " +
                        " where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccab = fD.Factura_Id_Fac and pro.id_prod=fD.Producto_Id_Prod and " +
                        " ca.id_cat=pro.CATEGORIA_ID_CAT  and fC.ESTADO_FACCAB=1 and  pro.ESTADO_PROD=1 and upper(concat(concat(p.ape_per,' '),p.nom_per)) like upper('%"+cadCamp+"%') and ROWNUM <= 30  " +
"                    union                 " +
"                    select fC1.Id_Faccab, fC1.Fecha_Faccab,concat(concat(p.nom_per,' '),p.ape_per)as nombres,fC1.Num_Faccab, " +
                               "fD.CANTIDAD_FACDET as cant,fD.Descripcion_Facdet,h.fechaini_hisperser,h.fechafin_hisperser,fC1.Total_Faccab,0 as egreso, " +
"                              fC1.Valcancelo_Faccab,fC1.Valpendiente_Faccab,fC1.Valajuste_Faccab,h.estadoDias_HisPerSer, " +
"                              (select sum(fc2.valpendiente_faccab - fc2.valajuste_faccab + 0.0) " +
"                              from facturacabecera fC2 " +
"                               where p.id_per = fC2.persona_id_per and fC2.ESTADO_FACCAB=1)as saldoP, p.id_per,pro.id_prod,h.ID_HISPERSER ,h.NUM_REGISTRO_HISPERSER  " +
"                     from persona p, facturacabecera fC1,FacturaDetalle fD,producto pro,histpersserv h,categoria ca " +
"                    where p.id_per = fC1.Persona_Id_Per  and fC1.Id_Faccab = fD.Factura_Id_Fac and pro.id_prod=fD.Producto_Id_Prod and ca.id_cat=pro.CATEGORIA_ID_CAT and ca.CATEGORIA_ID_CAT=1 " +
"                    and pro.id_prod=h.producto_id_hisperser and p.id_per=h.persona_id_hisperser and fC1.ESTADO_FACCAB=1 and pro.ESTADO_PROD=1 and h.ESTADO_HISPERSER=1   "
                + " and h.Factura_id_fac = fc1.id_faccab and upper(concat(concat(p.ape_per,' '),p.nom_per)) like upper('%"+cadCamp+"%') and ROWNUM <= 30 " +
                " union "
                + " select  fC.Id_Faccabcomp,fC.Fecha_Faccabcomp,concat(concat(p.nom_per,' '),p.ape_per) as nombres,fC.Num_Faccabcomp,  " +
                " fD.CANTIDAD_FACDETCOMP as cant,fD.Descripcion_Facdetcomp,' 'as FechaInicio,''as FechaFin,0 as ingreso,fC.Total_Faccabcompr,fC.Valcancelo_Faccabcompr, " +
"                         fC.Valpendiente_Faccabcompr,fC.Valajuste_Faccabcompr,'' as estadoEnt, " +
"                             (select sum(fc2.valpendiente_faccab - fc2.valajuste_faccab + 0.0)  " +
"                             from facturacabecera fC2  " +
"                             where p.id_per = fC2.persona_id_per and fC2.ESTADO_FACCAB=1  " +
"                              ) as saldoP , p.id_per,pro.id_prod,1 as codHist ,fC.NUM_REGISTRO_FACCABCOMPR  " +
"                    from persona p, facturacabeceracompras fC,FacturaDetalleCompras fD,producto pro,categoria ca   " +
"                    where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccabcomp = fD.Factura_Id_Faccomp and pro.id_prod=fD.Producto_Id_Prodcomp and  " +
"                           ca.id_cat=pro.CATEGORIA_ID_CAT  and fC.Estado_Faccabcompr=1  and pro.ESTADO_PROD=1 and upper(concat(concat(p.ape_per,' '),p.nom_per)) like upper('%"+cadCamp+"%') and ROWNUM <= 30 "+
"                    order by 19 desc ";  
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
    
    public ResultSet buscarIngresosEgresosCamposGimnasio(String cadCamp)
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql =    " select  " +
                        " (select sum(fc2.valpendiente_faccab - fc2.valajuste_faccab + 0.0) " +
                        " from facturacabecera fC2,facturaDetalle fD0,producto pro,categoria ca" +
                        " where p.id_per = fC2.persona_id_per and fC2.ESTADO_FACCAB=1 and ca.id_cat=pro.CATEGORIA_ID_CAT and ca.tipo_cat like 'PRODUCTO' and fC2.Id_Faccab = fD0.Factura_Id_Fac and pro.id_prod=fD0.Producto_Id_Prod" +
                        " ) as saldoP , p.id_per,pro.id_prod,1 as codHist ,fC.NUM_REGISTRO_FACCAB " +
                        " from persona p, facturacabecera fC,FacturaDetalle fD,producto pro,categoria ca  " +
                        " where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccab = fD.Factura_Id_Fac and pro.id_prod=fD.Producto_Id_Prod and " +
                        " ca.id_cat=pro.CATEGORIA_ID_CAT and ca.tipo_cat like 'PRODUCTO' and fC.ESTADO_FACCAB=1 and  pro.ESTADO_PROD=1 and upper(concat(concat(p.ape_per,' '),p.nom_per)) like upper('%"+cadCamp+"%') and ROWNUM <= 30  " +
"                    union                 " +
"                    select "
                + "(select sum(fc2.valpendiente_faccab - fc2.valajuste_faccab + 0.0) " +
"                              from facturacabecera fC2,facturaDetalle fD0,producto pro,categoria ca " +
"                               where p.id_per = fC2.persona_id_per and fC2.ESTADO_FACCAB=1 and fC2.Id_Faccab = fD0.Factura_Id_Fac and pro.id_prod=fD0.Producto_Id_Prod)as saldoP, p.id_per,pro.id_prod,h.ID_HISPERSER ,h.NUM_REGISTRO_HISPERSER  " +
"                     from persona p, facturacabecera fC1,FacturaDetalle fD,producto pro,histpersserv h,categoria ca " +
"                    where p.id_per = fC1.Persona_Id_Per  and fC1.Id_Faccab = fD.Factura_Id_Fac and pro.id_prod=fD.Producto_Id_Prod and ca.id_cat=pro.CATEGORIA_ID_CAT and ca.CATEGORIA_ID_CAT=1 " +
"                    and pro.id_prod=h.producto_id_hisperser and p.id_per=h.persona_id_hisperser and fC1.ESTADO_FACCAB=1 and pro.ESTADO_PROD=1 and h.ESTADO_HISPERSER=1   "
                + " and h.Factura_id_fac = fc1.id_faccab and upper(concat(concat(p.ape_per,' '),p.nom_per)) like upper('%"+cadCamp+"%') and ROWNUM <= 30 " +
                " union "
                + " select  "
                + " (select sum(fc2.valpendiente_faccab - fc2.valajuste_faccab + 0.0)  " +
"                             from facturacabecera fC2,facturaDetalle fD0 ,producto pro,categoria ca " +
"                             where p.id_per = fC2.persona_id_per and fC2.ESTADO_FACCAB=1 and ca.id_cat=pro.CATEGORIA_ID_CAT and  ca.tipo_cat like 'PRODUCTO' and fC2.Id_Faccab = fD0.Factura_Id_Fac and pro.id_prod=fD0.Producto_Id_Prod " +
"                              ) as saldoP , p.id_per,pro.id_prod,1 as codHist ,fC.NUM_REGISTRO_FACCABCOMPR  " +
"                    from persona p, facturacabeceracompras fC,FacturaDetalleCompras fD,producto pro,categoria ca   " +
"                    where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccabcomp = fD.Factura_Id_Faccomp and pro.id_prod=fD.Producto_Id_Prodcomp and  " +
"                           ca.id_cat=pro.CATEGORIA_ID_CAT  and ca.tipo_cat like 'PRODUCTO' and fC.Estado_Faccabcompr=1  and pro.ESTADO_PROD=1 and upper(concat(concat(p.ape_per,' '),p.nom_per)) like upper('%"+cadCamp+"%') and ROWNUM <= 30 "+
"                    order by 1 desc ";  
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
    
    public ResultSet buscarIngresosEgresosCamposRopa(String cadCamp)
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql =    " select  " +
                        " (select sum(fc2.valpendiente_faccab - fc2.valajuste_faccab + 0.0) " +
                        " from facturacabecera fC2,facturaDetalle fD0,producto pro,categoria ca" +
                        " where p.id_per = fC2.persona_id_per and fC2.ESTADO_FACCAB=1 and ca.id_cat=pro.CATEGORIA_ID_CAT and ca.tipo_cat like 'PRODUCTO ROPA' and fC2.Id_Faccab = fD0.Factura_Id_Fac and pro.id_prod=fD0.Producto_Id_Prod" +
                        " ) as saldoP , p.id_per,pro.id_prod,1 as codHist ,fC.NUM_REGISTRO_FACCAB " +
                        " from persona p, facturacabecera fC,FacturaDetalle fD,producto pro,categoria ca  " +
                        " where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccab = fD.Factura_Id_Fac and pro.id_prod=fD.Producto_Id_Prod and " +
                        " ca.id_cat=pro.CATEGORIA_ID_CAT and ca.tipo_cat like 'PRODUCTO ROPA' and fC.ESTADO_FACCAB=1 and  pro.ESTADO_PROD=1 and upper(concat(concat(p.ape_per,' '),p.nom_per)) like upper('%"+cadCamp+"%') and ROWNUM <= 30  " +
"                    union                 " +
"                    select "
                + "(select sum(fc2.valpendiente_faccab - fc2.valajuste_faccab + 0.0) " +
"                              from facturacabecera fC2,facturaDetalle fD0,producto pro,categoria ca " +
"                               where p.id_per = fC2.persona_id_per and fC2.ESTADO_FACCAB=1 and fC2.Id_Faccab = fD0.Factura_Id_Fac and pro.id_prod=fD0.Producto_Id_Prod)as saldoP, p.id_per,pro.id_prod,h.ID_HISPERSER ,h.NUM_REGISTRO_HISPERSER  " +
"                     from persona p, facturacabecera fC1,FacturaDetalle fD,producto pro,histpersserv h,categoria ca " +
"                    where p.id_per = fC1.Persona_Id_Per  and fC1.Id_Faccab = fD.Factura_Id_Fac and pro.id_prod=fD.Producto_Id_Prod and ca.id_cat=pro.CATEGORIA_ID_CAT and ca.CATEGORIA_ID_CAT=1 " +
"                    and pro.id_prod=h.producto_id_hisperser and p.id_per=h.persona_id_hisperser and fC1.ESTADO_FACCAB=1 and pro.ESTADO_PROD=1 and h.ESTADO_HISPERSER=1   "
                + " and h.Factura_id_fac = fc1.id_faccab and upper(concat(concat(p.ape_per,' '),p.nom_per)) like upper('%"+cadCamp+"%') and ROWNUM <= 30 " +
                " union "
                + " select  "
                + " (select sum(fc2.valpendiente_faccab - fc2.valajuste_faccab + 0.0)  " +
"                             from facturacabecera fC2,facturaDetalle fD0 ,producto pro,categoria ca " +
"                             where p.id_per = fC2.persona_id_per and fC2.ESTADO_FACCAB=1 and ca.id_cat=pro.CATEGORIA_ID_CAT and  ca.tipo_cat like 'PRODUCTO ROPA' and fC2.Id_Faccab = fD0.Factura_Id_Fac and pro.id_prod=fD0.Producto_Id_Prod " +
"                              ) as saldoP , p.id_per,pro.id_prod,1 as codHist ,fC.NUM_REGISTRO_FACCABCOMPR  " +
"                    from persona p, facturacabeceracompras fC,FacturaDetalleCompras fD,producto pro,categoria ca   " +
"                    where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccabcomp = fD.Factura_Id_Faccomp and pro.id_prod=fD.Producto_Id_Prodcomp and  " +
"                           ca.id_cat=pro.CATEGORIA_ID_CAT  and ca.tipo_cat like 'PRODUCTO ROPA' and fC.Estado_Faccabcompr=1  and pro.ESTADO_PROD=1 and upper(concat(concat(p.ape_per,' '),p.nom_per)) like upper('%"+cadCamp+"%') and ROWNUM <= 30 "+
"                    order by 1 desc ";  
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
