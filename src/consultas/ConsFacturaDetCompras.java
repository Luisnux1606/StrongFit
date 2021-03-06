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
import modelos.FacturaCab;
import modelos.FacturaCabCompras;
import modelos.FacturaDetalle;
import modelos.FacturaDetalleCompras;

/**
 *
 * @author Administrator
 */
public class ConsFacturaDetCompras extends Conexion {
    
    
    public boolean registrar(ArrayList<FacturaDetalleCompras> facDet)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "INSERT INTO FACTURADETALLECOMPRAS (ID_FACDETCOMP,CANTIDAD_FACDETCOMP, DESCRIPCION_FACDETCOMP,VALUNITARIO_FACDETCOMP,VTOTAL_FACDETCOMP,PRODUCTO_ID_PRODCOMP,FACTURA_ID_FACCOMP,ESTADO_FACDETCOMP) "
                + " VALUES(detalleComp_id_seq.NEXTVAL,?,?,?,?,?,?,?)";

        try 
        {            
            ps = con.prepareStatement(sql);            
            for (FacturaDetalleCompras listDets : facDet) 
            {
                ps.setInt(1,listDets.getCantidad_facDetComp());
                ps.setString(2,listDets.getDescripcion_facDetComp());
                ps.setDouble(3,listDets.getValUnitario_facDetComp());
                ps.setDouble(4,listDets.getvTotal_facDetComp());
                ps.setInt(5,listDets.getProducto_id_prodComp().getId_prod());
                ps.setInt(6,listDets.getFactura_id_facComp().getId_facCabComp());               
                ps.setInt(7, listDets.getEstado_facDetComp());               
                ps.execute();
            }
           
                        
                                                   
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
    
    public boolean actualizarEntradas(ArrayList<FacturaDetalleCompras> facDet)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "update producto set entradas = ?" +
                      "where id_prod = ?";

        int entradasActual = 0;
        int entradas = 0;
        try 
        {            
            ps = con.prepareStatement(sql);            
            for (FacturaDetalleCompras listDets : facDet) 
            {
                System.out.println(listDets.getCantidad_facDetComp()+"  "+listDets.getProducto_id_prodComp().getId_prod());                
                entradasActual = getExisIniEntradasSalidas(listDets.getProducto_id_prodComp().getId_prod())[1];
                entradas = entradasActual + listDets.getCantidad_facDetComp();
                ps.setInt(1,entradas);                
                ps.setInt(2,listDets.getProducto_id_prodComp().getId_prod());
                
                ps.execute();
            }
           
                        
                                                   
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
    public int[] getExisIniEntradasSalidas(int idProd){
        String sql;
        int entradasSalidas[] = new int[3];
        PreparedStatement ps = null;
        con = getConexion();
        ResultSet rs = null; 

        sql="select p.EXISTINI, p.entradas,p.salidas " +
            "from producto p " +
            "where p.id_prod = "+idProd+" and p.estado_prod = 1";						
                try 
                {
                    ps = con.prepareStatement(sql);                            
                    rs = ps.executeQuery();
                        while(rs.next()){
                            entradasSalidas[0]=rs.getInt(1);
                            entradasSalidas[1]=rs.getInt(2);
                            entradasSalidas[2]=rs.getInt(3);
                        }
                        con.close();
                } catch (SQLException e) 
                {e.printStackTrace();}			

        return entradasSalidas;
    }
    public int calculaStock(int idProd)
    {
        int existIniEntradasSalidas[] = getExisIniEntradasSalidas(idProd);
        int stock =(existIniEntradasSalidas[0] + existIniEntradasSalidas[1])-existIniEntradasSalidas[2];
        
        return stock;    
    }
    
    public boolean actualizarStock(ArrayList<FacturaDetalleCompras> facDet)
    {
        PreparedStatement ps,ps2 = null;
        Connection con = getConexion();
        String sql = "update producto set stock = ?" +
                      "where id_prod = ?";

        try 
        {            
            ps = con.prepareStatement(sql);            
            for (FacturaDetalleCompras listDets : facDet) 
            {
                System.out.println(listDets.getCantidad_facDetComp()+"  "+listDets.getProducto_id_prodComp().getId_prod());
                
                ps.setInt(1,calculaStock(listDets.getProducto_id_prodComp().getId_prod()));                
                ps.setInt(2,listDets.getProducto_id_prodComp().getId_prod());                
                ps.execute();
            }                                       
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
    
    public boolean modificar(ArrayList<FacturaDetalleCompras> facDetComp)
    {
        PreparedStatement ps = null; // FACTURADETALLECOMPRAS (ID_FACDETCOMP
        Connection con = getConexion(); //ID_FACDETCOMP,CANTIDAD_FACDETCOMP, DESCRIPCION_FACDETCOMP,VALUNITARIO_FACDETCOMP,VTOTAL_FACDETCOMP,PRODUCTO_ID_PRODCOMP,FACTURA_ID_FACCOMP,ESTADO_FACDETCOMP
        String sql = "update FACTURADETALLECOMPRAS SET CANTIDAD_FACDETCOMP=?, CANTIDAD_FACDETCOMP=?, DESCRIPCION_FACDETCOMP=?,VALUNITARIO_FACDETCOMP=?,VTOTAL_FACDETCOMP=?,PRODUCTO_ID_PRODCOMP=?,FACTURA_ID_FACCOMP=?,ESTADO_FACDETCOMP=?"
                + " WHERE ID_FACDETCOMP=?";
        
        try 
        {
            
            ps = con.prepareStatement(sql);
            for (FacturaDetalleCompras listDets : facDetComp) 
            {
                ps.setInt(1,listDets.getCantidad_facDetComp());
                ps.setString(2,listDets.getDescripcion_facDetComp());
                ps.setDouble(3,listDets.getValUnitario_facDetComp());
                ps.setDouble(4,listDets.getvTotal_facDetComp());
                ps.setInt(5,listDets.getProducto_id_prodComp().getId_prod());
                ps.setInt(6,listDets.getFactura_id_facComp().getId_facCabComp());               
                ps.setInt(7, listDets.getEstado_facDetComp()); 
                ps.setInt(8, listDets.getId_facDetComp());  
                ps.execute();
            }
            
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
        String sql = " SELECT f.id_fac,   p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaInicio_fac, f.fechaFin_fac,, f.valPago_fac, f.valPendiente_fac,f.concepto_fac \n" +
                        "FROM factura f, persona p \n" +
                        "where upper(p.nom_per) like upper('%"+nom+"%')  and p.id_per=f.persona_id_per and f.estado_fac=1\n" +
                        "UNION\n" +
                        "SELECT f.id_fac,p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaInicio_fac , f.fechaFin_fac,, f.valPago_fac, f.valPendiente_fac,f.concepto_fac \n" +
                        "FROM factura f, persona p \n" +
                        "where upper(p.ced_per) like upper('%"+nom+"%')  and p.id_per=f.persona_id_per and f.estado_fac=1\n" +
                        "UNION\n" +
                        "SELECT f.id_fac,p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaInicio_fac , f.fechaFin_fac,, f.valPago_fac, f.valPendiente_fac,f.concepto_fac \n" +
                        "FROM factura f, persona p \n" +
                        "where upper(f.fechaInicio_fac) like upper('%"+nom+"%')  and p.id_per=f.persona_id_per and f.estado_fac=1\n" +
                        "UNION\n" +
                        "SELECT f.id_fac,p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaInicio_fac , f.fechaFin_fac,, f.valPago_fac, f.valPendiente_fac,f.concepto_fac \n" +
                        "FROM factura f, persona p \n" +
                        "where upper(f.fechaFin_fac) like upper('%"+nom+"%')  and p.id_per=f.persona_id_per and f.estado_fac=1";
                
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
        String sql = " SELECT f.id_fac,   p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaInicio_fac, f.fechaFin_fac,, f.valPago_fac, f.valPendiente_fac,f.concepto_fac \n" +
                        "FROM factura f, persona p \n" +
                        "where  p.id_per=f.persona_id_per and f.valPendiente_fac>0 and f.estado_fac=1" ;
                       
                
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
        String sql = " SELECT f.id_fac, p.ced_per,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombres, f.fechaInicio_fac AS fechaIni_fac, f.fechaFin_fac AS fechaFin_fac, f.total_fac, f.valPendiente_fac" +
                    " FROM FacturaCabecera f, persona p " +
                    " where p.id_per = f.Persona_id_per and f.estado_facCab=1 " +
                    " order by id_fac asc ";
                
        
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
    
   public boolean getLastInvoice(FacturaCabCompras fCab)
    {
        PreparedStatement ps = null;
        Connection con = getConexion();
        ResultSet rs = null;
        String sql = "select max(fC.ID_FACCABCOMP) as Id_FaccabComp " +
                    "from FACTURACABECERACOMPRAS fC " +
                    "order by fC.ID_FACCABCOMP asc";
        
        try 
        {
            
            ps = con.prepareStatement(sql);                     
          
            rs = ps.executeQuery();
            if (rs.next()) { //ced_per, nom_per, ape_per, nroFono_per,edad_per,fechaNac_per
                fCab.setId_facCabComp(rs.getInt("Id_FaccabComp"));                            
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
