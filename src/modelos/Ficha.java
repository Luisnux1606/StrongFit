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
    private String fecha_ini;
    private String fecha_fin;
    private double val_pago;
    private double val_pendiente;
    private String concepto;
    private Analisis analisis;
    private Persona persona;
    private Medidas medidas;
    private int estado;

    /**
     * @return the fecha_ini
     */
    public String getFecha_ini() {
        return fecha_ini;
    }

    /**
     * @return the fecha_fin
     */
    public String getFecha_fin() {
        return fecha_fin;
    }

    /**
     * @return the val_pago
     */
    public double getVal_pago() {
        return val_pago;
    }

    /**
     * @return the val_pendiente
     */
    public double getVal_pendiente() {
        return val_pendiente;
    }

    /**
     * @param fecha_ini the fecha_ini to set
     */
    public void setFecha_ini(String fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    /**
     * @param fecha_fin the fecha_fin to set
     */
    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    /**
     * @param val_pago the val_pago to set
     */
    public void setVal_pago(double val_pago) {
        this.val_pago = val_pago;
    }

    /**
     * @param val_pendiente the val_pendiente to set
     */
    public void setVal_pendiente(double val_pendiente) {
        this.val_pendiente = val_pendiente;
    }

    /**
     * @return the analisis
     */
    public Analisis getAnalisis() {
        return analisis;
    }

    /**
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @return the medidas
     */
    public Medidas getMedidas() {
        return medidas;
    }

    /**
     * @param analisis the analisis to set
     */
    public void setAnalisis(Analisis analisis) {
        this.analisis = analisis;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * @param medidas the medidas to set
     */
    public void setMedidas(Medidas medidas) {
        this.medidas = medidas;
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
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
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
