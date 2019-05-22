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
public class HistorialPeronaServicio {
    /*fechaIni_prod Varchar2(100),
  fechaFin_prod Varchar2(100),
  Persona_id_per Number,
  Producto_id_prod Number,
  estado_prod Number */
    private int id_HisPerSer;
    private String fechaIni_HisPerSer;
    private String fechaFin_HisPerSer;
    private Persona Persona_id_HisPerSer;
    private Producto Producto_id_HisPerSer;
    private int estado_HisPerSer;

    /**
     * @return the id_HisPerSer
     */
    public int getId_HisPerSer() {
        return id_HisPerSer;
    }

    /**
     * @param id_HisPerSer the id_HisPerSer to set
     */
    public void setId_HisPerSer(int id_HisPerSer) {
        this.id_HisPerSer = id_HisPerSer;
    }

    /**
     * @return the fechaIni_HisPerSer
     */
    public String getFechaIni_HisPerSer() {
        return fechaIni_HisPerSer;
    }

    /**
     * @param fechaIni_HisPerSer the fechaIni_HisPerSer to set
     */
    public void setFechaIni_HisPerSer(String fechaIni_HisPerSer) {
        this.fechaIni_HisPerSer = fechaIni_HisPerSer;
    }

    /**
     * @return the fechaFin_HisPerSer
     */
    public String getFechaFin_HisPerSer() {
        return fechaFin_HisPerSer;
    }

    /**
     * @param fechaFin_HisPerSer the fechaFin_HisPerSer to set
     */
    public void setFechaFin_HisPerSer(String fechaFin_HisPerSer) {
        this.fechaFin_HisPerSer = fechaFin_HisPerSer;
    }

    /**
     * @return the Persona_id_HisPerSer
     */
    public Persona getPersona_id_HisPerSer() {
        return Persona_id_HisPerSer;
    }

    /**
     * @param Persona_id_HisPerSer the Persona_id_HisPerSer to set
     */
    public void setPersona_id_HisPerSer(Persona Persona_id_HisPerSer) {
        this.Persona_id_HisPerSer = Persona_id_HisPerSer;
    }

    /**
     * @return the Producto_id_HisPerSer
     */
    public Producto getProducto_id_HisPerSer() {
        return Producto_id_HisPerSer;
    }

    /**
     * @param Producto_id_HisPerSer the Producto_id_HisPerSer to set
     */
    public void setProducto_id_HisPerSer(Producto Producto_id_HisPerSer) {
        this.Producto_id_HisPerSer = Producto_id_HisPerSer;
    }

    /**
     * @return the estado_HisPerSer
     */
    public int getEstado_HisPerSer() {
        return estado_HisPerSer;
    }

    /**
     * @param estado_HisPerSer the estado_HisPerSer to set
     */
    public void setEstado_HisPerSer(int estado_HisPerSer) {
        this.estado_HisPerSer = estado_HisPerSer;
    }
    
    
    
}
