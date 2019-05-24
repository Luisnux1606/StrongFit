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
public class FacturaCab {
    
    private int id_facCab; 
    private String fecha_facCab;
    private String num_facCab;
    private double valPagar_facCab;    
    private double subTotal_facCab;
    private double total_facCab;
    private double valCancelo_facCab;
    private double valPendiente_facCab;
    private double valAjuste_facCab;
    private Persona Persona;
    private Membresias Membresia;
    private Iva Ivas;     
    private int estado;

    /**
     * @return the id_facCab
     */
    public int getId_facCab() {
        return id_facCab;
    }

    /**
     * @param id_facCab the id_facCab to set
     */
    public void setId_facCab(int id_facCab) {
        this.id_facCab = id_facCab;
    }

    /**
     * @return the fecha_facCab
     */
    public String getFecha_facCab() {
        return fecha_facCab;
    }

    /**
     * @param fecha_facCab the fecha_facCab to set
     */
    public void setFecha_facCab(String fecha_facCab) {
        this.fecha_facCab = fecha_facCab;
    }

    /**
     * @return the num_facCab
     */
    public String getNum_facCab() {
        return num_facCab;
    }

    /**
     * @param num_facCab the num_facCab to set
     */
    public void setNum_facCab(String num_facCab) {
        this.num_facCab = num_facCab;
    }

    /**
     * @return the valPagar_facCab
     */
    public double getValPagar_facCab() {
        return valPagar_facCab;
    }

    /**
     * @param valPagar_facCab the valPagar_facCab to set
     */
    public void setValPagar_facCab(double valPagar_facCab) {
        this.valPagar_facCab = valPagar_facCab;
    }

    /**
     * @return the subTotal_facCab
     */
    public double getSubTotal_facCab() {
        return subTotal_facCab;
    }

    /**
     * @param subTotal_facCab the subTotal_facCab to set
     */
    public void setSubTotal_facCab(double subTotal_facCab) {
        this.subTotal_facCab = subTotal_facCab;
    }

    /**
     * @return the total_facCab
     */
    public double getTotal_facCab() {
        return total_facCab;
    }

    /**
     * @param total_facCab the total_facCab to set
     */
    public void setTotal_facCab(double total_facCab) {
        this.total_facCab = total_facCab;
    }

    /**
     * @return the valCancelo_facCab
     */
    public double getValCancelo_facCab() {
        return valCancelo_facCab;
    }

    /**
     * @param valCancelo_facCab the valCancelo_facCab to set
     */
    public void setValCancelo_facCab(double valCancelo_facCab) {
        this.valCancelo_facCab = valCancelo_facCab;
    }

    /**
     * @return the valPendiente_facCab
     */
    public double getValPendiente_facCab() {
        return valPendiente_facCab;
    }

    /**
     * @param valPendiente_facCab the valPendiente_facCab to set
     */
    public void setValPendiente_facCab(double valPendiente_facCab) {
        this.valPendiente_facCab = valPendiente_facCab;
    }

    /**
     * @return the Persona
     */
    public Persona getPersona() {
        return Persona;
    }

    /**
     * @param Persona the Persona to set
     */
    public void setPersona(Persona Persona) {
        this.Persona = Persona;
    }

    /**
     * @return the Membresia
     */
    public Membresias getMembresia() {
        return Membresia;
    }

    /**
     * @param Membresia the Membresia to set
     */
    public void setMembresia(Membresias Membresia) {
        this.Membresia = Membresia;
    }

    /**
     * @return the Ivas
     */
    public Iva getIvas() {
        return Ivas;
    }

    /**
     * @param Ivas the Ivas to set
     */
    public void setIvas(Iva Ivas) {
        this.Ivas = Ivas;
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

    /**
     * @return the valAjuste_facCab
     */
    public double getValAjuste_facCab() {
        return valAjuste_facCab;
    }

    /**
     * @param valAjuste_facCab the valAjuste_facCab to set
     */
    public void setValAjuste_facCab(double valAjuste_facCab) {
        this.valAjuste_facCab = valAjuste_facCab;
    }

    
    
}
