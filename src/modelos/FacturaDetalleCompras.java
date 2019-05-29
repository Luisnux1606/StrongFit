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
public class FacturaDetalleCompras {
    
  private int   id_facDetComp;
  private int cantidad_facDetComp;
  private String descripcion_facDetComp;
  private double valUnitario_facDetComp;
  private double vTotal_facDetComp;
  private Producto Producto_id_prodComp;
  private FacturaCabCompras Factura_id_facComp;
  private int estado_facDetComp;

    /**
     * @return the id_facDetComp
     */
    public int getId_facDetComp() {
        return id_facDetComp;
    }

    /**
     * @param id_facDetComp the id_facDetComp to set
     */
    public void setId_facDetComp(int id_facDetComp) {
        this.id_facDetComp = id_facDetComp;
    }

    /**
     * @return the cantidad_facDetComp
     */
    public int getCantidad_facDetComp() {
        return cantidad_facDetComp;
    }

    /**
     * @param cantidad_facDetComp the cantidad_facDetComp to set
     */
    public void setCantidad_facDetComp(int cantidad_facDetComp) {
        this.cantidad_facDetComp = cantidad_facDetComp;
    }

    /**
     * @return the descripcion_facDetComp
     */
    public String getDescripcion_facDetComp() {
        return descripcion_facDetComp;
    }

    /**
     * @param descripcion_facDetComp the descripcion_facDetComp to set
     */
    public void setDescripcion_facDetComp(String descripcion_facDetComp) {
        this.descripcion_facDetComp = descripcion_facDetComp;
    }

    /**
     * @return the valUnitario_facDetComp
     */
    public double getValUnitario_facDetComp() {
        return valUnitario_facDetComp;
    }

    /**
     * @param valUnitario_facDetComp the valUnitario_facDetComp to set
     */
    public void setValUnitario_facDetComp(double valUnitario_facDetComp) {
        this.valUnitario_facDetComp = valUnitario_facDetComp;
    }

    /**
     * @return the vTotal_facDetComp
     */
    public double getvTotal_facDetComp() {
        return vTotal_facDetComp;
    }

    /**
     * @param vTotal_facDetComp the vTotal_facDetComp to set
     */
    public void setvTotal_facDetComp(double vTotal_facDetComp) {
        this.vTotal_facDetComp = vTotal_facDetComp;
    }

    /**
     * @return the Producto_id_prodComp
     */
    public Producto getProducto_id_prodComp() {
        return Producto_id_prodComp;
    }

    /**
     * @param Producto_id_prodComp the Producto_id_prodComp to set
     */
    public void setProducto_id_prodComp(Producto Producto_id_prodComp) {
        this.Producto_id_prodComp = Producto_id_prodComp;
    }

    /**
     * @return the Factura_id_facComp
     */
    public FacturaCabCompras getFactura_id_facComp() {
        return Factura_id_facComp;
    }

    /**
     * @param Factura_id_facComp the Factura_id_facComp to set
     */
    public void setFactura_id_facComp(FacturaCabCompras Factura_id_facComp) {
        this.Factura_id_facComp = Factura_id_facComp;
    }

    /**
     * @return the estado_facDetComp
     */
    public int getEstado_facDetComp() {
        return estado_facDetComp;
    }

    /**
     * @param estado_facDetComp the estado_facDetComp to set
     */
    public void setEstado_facDetComp(int estado_facDetComp) {
        this.estado_facDetComp = estado_facDetComp;
    }
 
  
}