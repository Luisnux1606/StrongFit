/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author Administrator
 */
public class Producto {
    
   private int  id_prod;
   private String descripcion_prod;
   private double precio_prod;
   private Categoria categoria;
   private double entradas;
   private double salidas;
   private double existIni;
   private double stock;
   private String fechaIni;
   private String fechaFin;
   private int estado_prod;

    /**
     * @return the id_prod
     */
    public int getId_prod() {
        return id_prod;
    }

    /**
     * @param id_prod the id_prod to set
     */
    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    /**
     * @return the descripcion_prod
     */
    public String getDescripcion_prod() {
        return descripcion_prod;
    }

    /**
     * @param descripcion_prod the descripcion_prod to set
     */
    public void setDescripcion_prod(String descripcion_prod) {
        this.descripcion_prod = descripcion_prod;
    }

    /**
     * @return the precio_prod
     */
    public double getPrecio_prod() {
        return precio_prod;
    }

    /**
     * @param precio_prod the precio_prod to set
     */
    public void setPrecio_prod(double precio_prod) {
        this.precio_prod = precio_prod;
    }

    /**
     * @return the Categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @param Categoria the Categoria_id_cat to set
     */
    public void setCategoria(Categoria Categoria) {
        this.categoria = Categoria;
    }

    /**
     * @return the estado_prod
     */
    public int getEstado_prod() {
        return estado_prod;
    }

    /**
     * @param estado_prod the estado_prod to set
     */
    public void setEstado_prod(int estado_prod) {
        this.estado_prod = estado_prod;
    }

    /**
     * @return the fechaIni
     */
    public String getFechaIni() {
        return fechaIni;
    }

    /**
     * @param fechaIni the fechaIni to set
     */
    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    /**
     * @return the fechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the entradas
     */
    public double getEntradas() {
        return entradas;
    }

    /**
     * @param entradas the entradas to set
     */
    public void setEntradas(double entradas) {
        this.entradas = entradas;
    }

    /**
     * @return the salidas
     */
    public double getSalidas() {
        return salidas;
    }

    /**
     * @param salidas the salidas to set
     */
    public void setSalidas(double salidas) {
        this.salidas = salidas;
    }

    /**
     * @return the existIni
     */
    public double getExistIni() {
        return existIni;
    }

    /**
     * @param existIni the existIni to set
     */
    public void setExistIni(double existIni) {
        this.existIni = existIni;
    }

    /**
     * @return the stock
     */
    public double getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(double stock) {
        this.stock = stock;
    }
   
   
}
