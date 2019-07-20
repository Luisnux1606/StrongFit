/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import consultas.ConsFacturaDet;
import vistas.VisFicha;

/**
 *
 * @author Administrator
 */
public class FacturaDetalle {
    
  private int   id_facDet;
  private int cantidad_facDet;
  private String descripcion_facDet;
  private double valUnitario_facDet;
  private double vTotal_facDet;
  private Producto Producto_id_prod;
  private FacturaCab Factura_id_fac;
  private int estado_facDet;
  private int Historial_id_hist;
  
  
  //FacturaCab modFicha,ConsFacturaCab consFicha,VisFicha visFicha,VisMembresia visMemb,Persona persona
  public FacturaDetalle()
  {
  
  }

    /**
     * @return the id_facDet
     */
    public int getId_facDet() {
        return id_facDet;
    }

    /**
     * @param id_facDet the id_facDet to set
     */
    public void setId_facDet(int id_facDet) {
        this.id_facDet = id_facDet;
    }

    /**
     * @return the cantidad_facDet
     */
    public int getCantidad_facDet() {
        return cantidad_facDet;
    }

    /**
     * @param cantidad_facDet the cantidad_facDet to set
     */
    public void setCantidad_facDet(int cantidad_facDet) {
        this.cantidad_facDet = cantidad_facDet;
    }

    /**
     * @return the descripcion_facDet
     */
    public String getDescripcion_facDet() {
        return descripcion_facDet;
    }

    /**
     * @param descripcion_facDet the descripcion_facDet to set
     */
    public void setDescripcion_facDet(String descripcion_facDet) {
        this.descripcion_facDet = descripcion_facDet;
    }

    /**
     * @return the valUnitario_facDet
     */
    public double getValUnitario_facDet() {
        return valUnitario_facDet;
    }

    /**
     * @param valUnitario_facDet the valUnitario_facDet to set
     */
    public void setValUnitario_facDet(double valUnitario_facDet) {
        this.valUnitario_facDet = valUnitario_facDet;
    }

    /**
     * @return the vTotal_facDet
     */
    public double getvTotal_facDet() {
        return vTotal_facDet;
    }

    /**
     * @param vTotal_facDet the vTotal_facDet to set
     */
    public void setvTotal_facDet(double vTotal_facDet) {
        this.vTotal_facDet = vTotal_facDet;
    }

    /**
     * @return the Producto_id_prod
     */
    public Producto getProducto_id_prod() {
        return Producto_id_prod;
    }

    /**
     * @param Producto_id_prod the Producto_id_prod to set
     */
    public void setProducto_id_prod(Producto Producto_id_prod) {
        this.Producto_id_prod = Producto_id_prod;
    }

    /**
     * @return the Factura_id_fac
     */
    public FacturaCab getFactura_id_fac() {
        return Factura_id_fac;
    }

    /**
     * @param Factura_id_fac the Factura_id_fac to set
     */
    public void setFactura_id_fac(FacturaCab Factura_id_fac) {
        this.Factura_id_fac = Factura_id_fac;
    }

    /**
     * @return the estado_facDet
     */
    public int getEstado_facDet() {
        return estado_facDet;
    }

    /**
     * @param estado_facDet the estado_facDet to set
     */
    public void setEstado_facDet(int estado_facDet) {
        this.estado_facDet = estado_facDet;
    }

    /**
     * @return the Historial_id_hist
     */
    public int getHistorial_id_hist() {
        return Historial_id_hist;
    }

    /**
     * @param Historial_id_hist the Historial_id_hist to set
     */
    public void setHistorial_id_hist(int Historial_id_hist) {
        this.Historial_id_hist = Historial_id_hist;
    }
  
  
}
