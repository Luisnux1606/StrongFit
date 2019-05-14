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
    
  private int id_ent;
  private String fechaIni_ent;
  private String fechaFin_ent;
  private EntrenamientoTiempo EntrenTiempo_id_entTmp;
  private Persona Persona_id_per;
  private int estado_ent;
    /**
     * @return the id_ent
     */
    public int getId_ent() {
        return id_ent;
    }

    /**
     * @param id_ent the id_ent to set
     */
    public void setId_ent(int id_ent) {
        this.id_ent = id_ent;
    }

    /**
     * @return the fechaIni_ent
     */
    public String getFechaIni_ent() {
        return fechaIni_ent;
    }

    /**
     * @param fechaIni_ent the fechaIni_ent to set
     */
    public void setFechaIni_ent(String fechaIni_ent) {
        this.fechaIni_ent = fechaIni_ent;
    }

    /**
     * @return the fechaFin_ent
     */
    public String getFechaFin_ent() {
        return fechaFin_ent;
    }

    /**
     * @param fechaFin_ent the fechaFin_ent to set
     */
    public void setFechaFin_ent(String fechaFin_ent) {
        this.fechaFin_ent = fechaFin_ent;
    }

    /**
     * @return the EntrenTiempo_id_entTmp
     */
    public EntrenamientoTiempo getEntrenTiempo_id_entTmp() {
        return EntrenTiempo_id_entTmp;
    }

    /**
     * @param EntrenTiempo_id_entTmp the EntrenTiempo_id_entTmp to set
     */
    public void setEntrenTiempo_id_entTmp(EntrenamientoTiempo EntrenTiempo_id_entTmp) {
        this.EntrenTiempo_id_entTmp = EntrenTiempo_id_entTmp;
    }

    /**
     * @return the Persona_id_per
     */
    public Persona getPersona_id_per() {
        return Persona_id_per;
    }

    /**
     * @param Persona_id_per the Persona_id_per to set
     */
    public void setPersona_id_per(Persona Persona_id_per) {
        this.Persona_id_per = Persona_id_per;
    }

    /**
     * @return the estado_ent
     */
    public int getEstado_ent() {
        return estado_ent;
    }

    /**
     * @param estado_ent the estado_ent to set
     */
    public void setEstado_ent(int estado_ent) {
        this.estado_ent = estado_ent;
    }

}