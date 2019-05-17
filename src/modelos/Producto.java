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
   private Categoria Categoria_id_cat;
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
     * @return the Categoria_id_cat
     */
    public Categoria getCategoria_id_cat() {
        return Categoria_id_cat;
    }

    /**
     * @param Categoria_id_cat the Categoria_id_cat to set
     */
    public void setCategoria_id_cat(Categoria Categoria_id_cat) {
        this.Categoria_id_cat = Categoria_id_cat;
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
   
   
}
