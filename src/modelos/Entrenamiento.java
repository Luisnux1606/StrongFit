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
public class Entrenamiento {
    
  private int id_entTmp;
  private String descripcion_entTiempo;
  private double costo_entTiempo;  

    /**
     * @return the id_entTmp
     */
    public int getId_entTmp() {
        return id_entTmp;
    }

    /**
     * @param id_entTmp the id_entTmp to set
     */
    public void setId_entTmp(int id_entTmp) {
        this.id_entTmp = id_entTmp;
    }

    /**
     * @return the descripcion_entTiempo
     */
    public String getDescripcion_entTiempo() {
        return descripcion_entTiempo;
    }

    /**
     * @param descripcion_entTiempo the descripcion_entTiempo to set
     */
    public void setDescripcion_entTiempo(String descripcion_entTiempo) {
        this.descripcion_entTiempo = descripcion_entTiempo;
    }

    /**
     * @return the costo_entTiempo
     */
    public double getCosto_entTiempo() {
        return costo_entTiempo;
    }

    /**
     * @param costo_entTiempo the costo_entTiempo to set
     */
    public void setCosto_entTiempo(double costo_entTiempo) {
        this.costo_entTiempo = costo_entTiempo;
    }
  
  
  
}
