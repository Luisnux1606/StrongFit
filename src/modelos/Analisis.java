/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.sql.Date;

/**
 *
 * @author Administrator
 */
public class Analisis {
    
    private int id;
    private String fecha;
    private double exeso_grasa;
    private double exeso_liquido;
    private double exeso_total;
    private String recomendacion_pesas;
    private String recomendacion_cardio;
    private String recomendacion_funcional;
    private int estado;

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @return the exeso_grasa
     */
    public double getExeso_grasa() {
        return exeso_grasa;
    }

    /**
     * @return the exeso_liquido
     */
    public double getExeso_liquido() {
        return exeso_liquido;
    }

    /**
     * @return the exeso_total
     */
    public double getExeso_total() {
        return exeso_total;
    }

    /**
     * @return the recomendacion_pesas
     */
    public String getRecomendacion_pesas() {
        return recomendacion_pesas;
    }

    /**
     * @return the recomendacion_cardio
     */
    public String getRecomendacion_cardio() {
        return recomendacion_cardio;
    }

    /**
     * @return the recomendacion_funcional
     */
    public String getRecomendacion_funcional() {
        return recomendacion_funcional;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @param exeso_grasa the exeso_grasa to set
     */
    public void setExeso_grasa(double exeso_grasa) {
        this.exeso_grasa = exeso_grasa;
    }

    /**
     * @param exeso_liquido the exeso_liquido to set
     */
    public void setExeso_liquido(double exeso_liquido) {
        this.exeso_liquido = exeso_liquido;
    }

    /**
     * @param exeso_total the exeso_total to set
     */
    public void setExeso_total(double exeso_total) {
        this.exeso_total = exeso_total;
    }

    /**
     * @param recomendacion_pesas the recomendacion_pesas to set
     */
    public void setRecomendacion_pesas(String recomendacion_pesas) {
        this.recomendacion_pesas = recomendacion_pesas;
    }

    /**
     * @param recomendacion_cardio the recomendacion_cardio to set
     */
    public void setRecomendacion_cardio(String recomendacion_cardio) {
        this.recomendacion_cardio = recomendacion_cardio;
    }

    /**
     * @param recomendacion_funcional the recomendacion_funcional to set
     */
    public void setRecomendacion_funcional(String recomendacion_funcional) {
        this.recomendacion_funcional = recomendacion_funcional;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
