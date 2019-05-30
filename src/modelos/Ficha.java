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
public class Ficha {
    
    private int id;
    private String fecha;
    private Analisis analisis;
    private Persona persona;
    private Medidas medidas;
    private PlanAlimenticio planAli;
    private int estado;

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
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the analisis
     */
    public Analisis getAnalisis() {
        return analisis;
    }

    /**
     * @param analisis the analisis to set
     */
    public void setAnalisis(Analisis analisis) {
        this.analisis = analisis;
    }

    /**
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * @return the medidas
     */
    public Medidas getMedidas() {
        return medidas;
    }

    /**
     * @param medidas the medidas to set
     */
    public void setMedidas(Medidas medidas) {
        this.medidas = medidas;
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

    public PlanAlimenticio getPlanAli() {
        return planAli;
    }

    public void setPlanAli(PlanAlimenticio planAli) {
        this.planAli = planAli;
    }
}
