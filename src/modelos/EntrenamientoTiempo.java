
package modelos;

import java.sql.Date;

/**
 *
 * @author Administrator
 */
public class EntrenamientoTiempo {
   
    private int id_entTmp;
    private String descripcion_entTiempo;
    private double costo_entTiempo ;
    private int estado;

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

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
}