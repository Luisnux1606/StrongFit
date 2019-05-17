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
public class Categoria {
    
  private int id_cat;
  private String tipo_cat;
  private int estado_cat;

    /**
     * @return the id_cat
     */
    public int getId_cat() {
        return id_cat;
    }

    /**
     * @param id_cat the id_cat to set
     */
    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    /**
     * @return the tipo_cat
     */
    public String getTipo_cat() {
        return tipo_cat;
    }

    /**
     * @param tipo_cat the tipo_cat to set
     */
    public void setTipo_cat(String tipo_cat) {
        this.tipo_cat = tipo_cat;
    }

    /**
     * @return the estado_cat
     */
    public int getEstado_cat() {
        return estado_cat;
    }

    /**
     * @param estado_cat the estado_cat to set
     */
    public void setEstado_cat(int estado_cat) {
        this.estado_cat = estado_cat;
    }
  
  
}
