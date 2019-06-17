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
public class ConsIngresosEgresos extends Conexion {
    
    public boolean registrar(FacturaCab f)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "INSERT INTO FacturaCabecera (id_facCab,fecha_facCab, num_facCab,valPagar_facCab,subTotal_facCab,total_facCab,valPendiente_facCab,valCancelo_facCab, Persona_id_per, Membresia_id_memb, Ivas_id_ivas,estado_facCab) "
                + " VALUES(factura_id_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?)";

        try 
        {            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, f.getFecha_facCab());           
            ps.setString(2, f.getNum_facCab());
            ps.setDouble(3, f.getValPagar_facCab());
            ps.setDouble(4, f.getSubTotal_facCab());
            ps.setDouble(5, f.getTotal_facCab());
            ps.setDouble(6, f.getValPendiente_facCab());
            ps.setDouble(7, f.getValCancelo_facCab());
            ps.setInt(8, f.getPersona().getId());
            ps.setInt(9, f.getMembresia().getId());
            ps.setInt(10, f.getIvas().getId_ivas());
            ps.setInt(11, f.getEstado());
                        
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
        Connection con = getConexion(); //id_facCab,fecha_facCab, num_facCab, subTotal_facCab,valPagar_facCab,subTotal_facCab,total_facCab,valPendiente_facCab,valCancelo_facCab, Persona_id_per, Membresia_id_memb, Ivas_id_ivas,estado_facCab
        String sql = "update FacturaCabecera SET fecha_facCab=?, num_facCab=?, subTotal_facCab=?,valPagar_facCab=?,subTotal_facCab=?,total_facCab=?,valPendiente_facCab=?,valCancelo_facCab=?,Persona_id_per=?,Membresia_id_memb=?,Ivas_id_ivas=?,estado_facCab=?"
                + " WHERE id_facCab=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, f.getFecha_facCab());           
            ps.setString(2, f.getNum_facCab());
            ps.setDouble(3, f.getSubTotal_facCab());
            ps.setDouble(4, f.getValPagar_facCab());
            ps.setDouble(5, f.getSubTotal_facCab());
            ps.setDouble(6, f.getTotal_facCab());
            ps.setDouble(7, f.getValPendiente_facCab());
            ps.setDouble(8, f.getValCancelo_facCab());
            ps.setInt(9, f.getPersona().getId());
            ps.setInt(10, f.getMembresia().getId());
            ps.setInt(11, f.getIvas().getId_ivas());
            ps.setInt(12, f.getEstado());
            
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
        String sql = "UPDATE  FacturaCabecera SET estado_facCab =? WHERE id_facCab=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);  
            ps.setInt(1, f.getEstado());
            ps.setInt(2, f.getId_facCab());
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
     /*
    public boolean buscar(FacturaCab f)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM factura WHERE id_fac=? and estado_fac=1";
        
        try 
        {
            
            ps = con.prepareStatement(sql);                     
            ps.setInt(1, f.getId());
            rs = ps.executeQuery();
            if (rs.next()) { 
                f.setId(rs.getInt("id_fac"));
                f.setFecha_ini(rs.getString("fechaIni_fac"));
                f.setFecha_fin(rs.getString("fechaFin_fac"));
                f.setVal_pago(rs.getDouble("valPago_fac"));
                f.setVal_pendiente(rs.getInt("valPendiente_fac"));
                
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
        
    }*/
    
    /*
    public ArrayList<FacturaCab> buscarTodos(FacturaCab f)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM factura where estado_fac=1";
        ArrayList<FacturaCab> fichas = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                f.setId(rs.getInt("id_fac"));
                f.setFecha_fin(rs.getString("fechaFin_fac"));
                f.setVal_pago(rs.getDouble("valPago_fac"));
                f.setVal_pendiente(rs.getInt("valPendiente_fac"));
                
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
    */

    /*
    public ArrayList<FacturaCab> buscarTodosPorFec(FacturaCab f,String fech)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null; 
        String sql = "SELECT * FROM factura "
                + " where fechaIni_fac like '%"+fech+"%' and estado_fac=1"
                + " order by id_fac asc ";
        ArrayList<FacturaCab> ficha = new ArrayList<>();
        
        
        try 
        {
            
            ps = con.prepareStatement(sql);                            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                f = new FacturaCab();
                f.setId(rs.getInt("id_fac"));
                f.setFecha_fin(rs.getString("fechaFin_fac"));
                f.setVal_pago(rs.getDouble("valPago_fac"));
                f.setVal_pendiente(rs.getInt("valPendiente_fac"));
                
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
    }*/
    
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
        String sql = "SELECT f.id_fac, p.ced_per, p.nom_per, f.fechaInicio_fac, f.fechaFin_fac,, f.valPago_fac, f.valPendiente_fac,f.concepto_fac " +
                    " FROM factura f, persona p " +
                    " where p.id_per = f.Persona_id_per and f.fechaInicio_fac like '%"+fech+"%' and f.estado_fac=1" +
                    " order by id_fac asc ";
                
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
   
        PreparedStatement ps = null;
        con = getConexion();
        ResultSet rs = null; 
        String sql = " select fC.Id_Faccab,p.ced_per,concat(concat(p.nom_per,' '),p.ape_per) as nombres,' ' as fechaini_hisperser,' ' as fechafin_hisperser,fC.Concepto_Faccab,fC.Fecha_Faccab,fC.Total_Faccab,fC.Valcancelo_Faccab,fC.Valpendiente_Faccab " +
                    "from persona p, facturacabecera fC,FacturaDetalle fD,producto pr, categoria c " +
                    "where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccab = fD.Factura_Id_Fac and c.id_cat=pr.categoria_id_cat and pr.id_prod=fD.Producto_Id_Prod and p.ced_per like'%"+nom+"%' and c.id_cat=2 " +
                    "union " +
                    "select fC.Id_Faccab,p.ced_per,concat(concat(p.nom_per,' '),p.ape_per)as nombres,h.fechaini_hisperser,h.fechafin_hisperser,fC.Concepto_Faccab,fC.Fecha_Faccab,fC.Total_Faccab,fC.Valcancelo_Faccab,fC.Valpendiente_Faccab " +
                    "from persona p, facturacabecera fC,FacturaDetalle fD,producto pr, categoria c,histpersserv h " +
                    "where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccab = fD.Factura_Id_Fac and c.id_cat=pr.categoria_id_cat and pr.id_prod=fD.Producto_Id_Prod and p.ced_per like'%"+nom+"%' and c.id_cat=1 " +
                    "and p.id_per=h.persona_id_hisperser and pr.id_prod=h.producto_id_hisperser " +
                    "UNION " +
                    "select fC.Id_Faccab,p.ced_per,concat(concat(p.nom_per,' '),p.ape_per)as nombres,' 'as fechaini_hisperser,' ' as fechafin_hisperser,fC.Concepto_Faccab,fC.Fecha_Faccab,fC.Total_Faccab,fC.Valcancelo_Faccab,fC.Valpendiente_Faccab " +
                    "from persona p, facturacabecera fC,FacturaDetalle fD,producto pr, categoria c " +
                    "where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccab = fD.Factura_Id_Fac and c.id_cat=pr.categoria_id_cat and pr.id_prod=fD.Producto_Id_Prod and upper(p.nom_per) like upper('%"+nom+"%') and c.id_cat=2 " +
                    "union " +
                    "select fC.Id_Faccab,p.ced_per,concat(concat(p.nom_per,' '),p.ape_per)as nombres,h.fechaini_hisperser,h.fechafin_hisperser,fC.Concepto_Faccab,fC.Fecha_Faccab,fC.Total_Faccab,fC.Valcancelo_Faccab,fC.Valpendiente_Faccab " +
                    "from persona p, facturacabecera fC,FacturaDetalle fD,producto pr, categoria c,histpersserv h " +
                    "where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccab = fD.Factura_Id_Fac and c.id_cat=pr.categoria_id_cat and pr.id_prod=fD.Producto_Id_Prod and upper(p.nom_per) like upper('%"+nom+"%') and c.id_cat=1 " +
                    "and p.id_per=h.persona_id_hisperser and pr.id_prod=h.producto_id_hisperser";
                
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
        String sql = " SELECT f.id_fac,   p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaInicio_faccab, f.fechaFin_faccab, f.VALCANCELO_FACCAB, f.valPendiente_faccab,f.concepto_faccab " +
                        "FROM FACTURACABECERA f, persona p " +
                        "where  p.id_per=f.persona_id_per and f.valPendiente_faccab>0 and f.estado_faccab=1" ;
                       
                
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
        String sql = " select distinct fC.Id_Faccab,p.ced_per,concat(concat(p.nom_per,' '),p.ape_per) as nombres ,h.fechaini_hisperser,h.fechafin_hisperser,fC.Concepto_Faccab,fC.Fecha_Faccab,fC.Total_Faccab,fC.Valcancelo_Faccab,fC.Valpendiente_Faccab  " +
                    "from persona p, facturacabecera fC,FacturaDetalle fD,producto pr, categoria c,histpersserv h " +
                    "where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccab = fD.Factura_Id_Fac and c.id_cat=pr.categoria_id_cat and pr.id_prod=fD.Producto_Id_Prod and c.categoria_id_cat=1 " +
                    "and p.id_per=h.persona_id_hisperser and pr.id_prod=h.producto_id_hisperser and h.ESTADO_HISPERSER=1 and fC.ESTADO_FACCAB=1 " +
                    "order by id_faccab";                       
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
    
    public ResultSet buscarIngresosEgresos()
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = " select fC.Id_Faccab,fC.Fecha_Faccab,concat(concat(p.nom_per,' '),p.ape_per) as nombres,fC.Num_Faccab,"
                        + " fD.Descripcion_Facdet,' 'as FechaInicio,''as FechaFin,fC.Total_Faccab,'' as egreso,fC.Valcancelo_Faccab,"
                        + " fC.Valpendiente_Faccab,fC.Valajuste_Faccab,'' as estadoEnt, " 
                            + " (select sum(fc2.valpendiente_faccab - fc2.valajuste_faccab + 0.0) " 
                            + " from facturacabecera fC2 " 
                            +  " ) as saldoP , p.id_per,pro.id_prod,1 as codHist " +
                    " from persona p, facturacabecera fC,FacturaDetalle fD,producto pro,categoria ca  " +
                    " where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccab = fD.Factura_Id_Fac and pro.id_prod=fD.Producto_Id_Prod and "
                           + "ca.id_cat=pro.CATEGORIA_ID_CAT and ca.CATEGORIA_ID_CAT=2 and fC.ESTADO_FACCAB=1 " +                    
                    " union " +                
                    " select fC1.Id_Faccab, fC1.Fecha_Faccab,concat(concat(p.nom_per,' '),p.ape_per)as nombres,fC1.Num_Faccab,"
                             + " fD.Descripcion_Facdet,h.fechaini_hisperser,h.fechafin_hisperser,fC1.Total_Faccab,'' as egreso,"
                             + " fC1.Valcancelo_Faccab,fC1.Valpendiente_Faccab,fC1.Valajuste_Faccab,h.estadoDias_HisPerSer,"
                             + " (select sum(fc2.valpendiente_faccab - fc2.valajuste_faccab + 0.0) " 
                             + " from facturacabecera fC2 " 
                             + " )as saldoP, p.id_per,pro.id_prod,h.ID_HISPERSER " 
                    + " from persona p, facturacabecera fC1,FacturaDetalle fD,producto pro,histpersserv h,categoria ca " +
                    " where p.id_per = fC1.Persona_Id_Per  and fC1.Id_Faccab = fD.Factura_Id_Fac and pro.id_prod=fD.Producto_Id_Prod and ca.id_cat=pro.CATEGORIA_ID_CAT and ca.CATEGORIA_ID_CAT=1 " +
                    " and pro.id_prod=h.producto_id_hisperser and p.id_per=h.persona_id_hisperser and ROWNUM <= 30 and fC1.ESTADO_FACCAB=1 " +
                    " order by 1 desc ";                       
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
    
     public ResultSet buscarIngresosEgresosEliminados()
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = " select fC.Id_Faccab,fC.Fecha_Faccab,concat(concat(p.nom_per,' '),p.ape_per) as nombres,fC.Num_Faccab,fD.Descripcion_Facdet,' 'as FechaInicio,''as FechaFin,fC.Total_Faccab,'' as egreso,fC.Valcancelo_Faccab,fC.Valpendiente_Faccab,fC.Valajuste_Faccab,'' as estadoEnt ,p.id_per,pro.id_prod,1 as codHist " +
                        " from persona p, facturacabecera fC,FacturaDetalle fD,producto pro,categoria ca  " +
                        " where p.id_per = fC.Persona_Id_Per  and fC.Id_Faccab = fD.Factura_Id_Fac and pro.id_prod=fD.Producto_Id_Prod and ca.id_cat=pro.CATEGORIA_ID_CAT and ca.CATEGORIA_ID_CAT=2 and fC.ESTADO_FACCAB=3 " +
                        " union " +
                        " select fC1.Id_Faccab, fC1.Fecha_Faccab,concat(concat(p.nom_per,' '),p.ape_per)as nombres,fC1.Num_Faccab,fD.Descripcion_Facdet,h.fechaini_hisperser,h.fechafin_hisperser,fC1.Total_Faccab,'' as egreso,fC1.Valcancelo_Faccab,fC1.Valpendiente_Faccab,fC1.Valajuste_Faccab,h.estadoDias_HisPerSer,p.id_per,pro.id_prod,h.ID_HISPERSER " +
                        " from persona p, facturacabecera fC1,FacturaDetalle fD,producto pro,histpersserv h,categoria ca " +
                        " where p.id_per = fC1.Persona_Id_Per  and fC1.Id_Faccab = fD.Factura_Id_Fac and pro.id_prod=fD.Producto_Id_Prod and ca.id_cat=pro.CATEGORIA_ID_CAT and ca.CATEGORIA_ID_CAT=1 " +
                        " and pro.id_prod=h.producto_id_hisperser and p.id_per=h.persona_id_hisperser and ROWNUM <= 30 and fC1.ESTADO_FACCAB=3 " +
                        " order by 1 desc ";                       
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
    
    public ResultSet buscarFacturasByNom(String nom)
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = " select  fC.Id_Faccab, concat(concat(p.nom_per,' '),p.ape_per)as nombres,fC.Fecha_Faccab, fC.Num_Faccab,fC.Concepto_Faccab,fC.Total_Faccab,fC.Valcancelo_Faccab,fC.Valpendiente_Faccab,fC.VALAJUSTE_FACCAB " +
                        "from persona p, facturacabecera fC " +
                        "where p.id_per = fC.Persona_Id_Per and fC.ESTADO_FACCAB = 1 and upper(p.nom_per) like upper('%"+nom+"%')" +
                        "order by fC.Id_Faccab desc";                       
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
    
        public ResultSet buscarFacturasPendientesPago()
        {
            PreparedStatement ps = null;
             con = getConexion();
            ResultSet rs = null; 
            String sql = " select  fC.Id_Faccab, concat(concat(p.nom_per,' '),p.ape_per)as nombres,fC.Fecha_Faccab, fC.Num_Faccab,fC.Concepto_Faccab,fC.Total_Faccab,fC.Valcancelo_Faccab,fC.Valpendiente_Faccab\n" +
                            "from persona p, facturacabecera fC " +
                            "where p.id_per = fC.Persona_Id_Per " +
                            "order by fC.Id_Faccab desc";                       
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
    
    public ResultSet buscarDetallesByIdFac(int idFac)
    {
        PreparedStatement ps = null;
         con = getConexion();
        ResultSet rs = null; 
        String sql = " select fD.Id_Facdet, fD.Descripcion_Facdet, fD.Valunitario_Facdet,fD.Vtotal_Facdet " +
                        "from facturacabecera fC, facturaDetalle fD " +
                        "where  fC.Id_Faccab = fD.Factura_Id_Fac and fC.Id_Faccab ="+idFac+" and fC.estado_faccab = 1";
                
        
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
    
    public boolean getLastInvoice(FacturaCab fCab)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "select fC.Id_Faccab\n" +
                    "from facturaCabecera fC\n" +
                    "order by Id_Faccab asc";
        
        try 
        {
            
            ps = con.prepareStatement(sql);                     
          
            rs = ps.executeQuery();
            if (rs.next()) { //ced_per, nom_per, ape_per, nroFono_per,edad_per,fechaNac_per
                fCab.setId_facCab(rs.getInt("Id_Faccab"));                            
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
}
