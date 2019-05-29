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
public class FacturaCabCompras {
    
    private int id_facCabComp; 
    private String fecha_facCabComp;
    private String num_facCabComp;
    private double valPagar_facCabComp;    
    private double subTotal_facCabComp;
    private double total_facCabComp;
    private double valCancelo_facCabComp;
    private double valPendiente_facCabComp;
    private double valAjuste_facCabComp;
    private Persona PersonaComp;
    private Membresias MembresiaComp;
    private Iva IvasComp;     
    private int estadoComp;

    /**
     * @return the id_facCabComp
     */
    public int getId_facCabComp() {
        return id_facCabComp;
    }

    /**
     * @param id_facCabComp the id_facCabComp to set
     */
    public void setId_facCabComp(int id_facCabComp) {
        this.id_facCabComp = id_facCabComp;
    }

    /**
     * @return the fecha_facCabComp
     */
    public String getFecha_facCabComp() {
        return fecha_facCabComp;
    }

    /**
     * @param fecha_facCabComp the fecha_facCabComp to set
     */
    public void setFecha_facCabComp(String fecha_facCabComp) {
        this.fecha_facCabComp = fecha_facCabComp;
    }

    /**
     * @return the num_facCabComp
     */
    public String getNum_facCabComp() {
        return num_facCabComp;
    }

    /**
     * @param num_facCabComp the num_facCabComp to set
     */
    public void setNum_facCabComp(String num_facCabComp) {
        this.num_facCabComp = num_facCabComp;
    }

    /**
     * @return the valPagar_facCabComp
     */
    public double getValPagar_facCabComp() {
        return valPagar_facCabComp;
    }

    /**
     * @param valPagar_facCabComp the valPagar_facCabComp to set
     */
    public void setValPagar_facCabComp(double valPagar_facCabComp) {
        this.valPagar_facCabComp = valPagar_facCabComp;
    }

    /**
     * @return the subTotal_facCabComp
     */
    public double getSubTotal_facCabComp() {
        return subTotal_facCabComp;
    }

    /**
     * @param subTotal_facCabComp the subTotal_facCabComp to set
     */
    public void setSubTotal_facCabComp(double subTotal_facCabComp) {
        this.subTotal_facCabComp = subTotal_facCabComp;
    }

    /**
     * @return the total_facCabComp
     */
    public double getTotal_facCabComp() {
        return total_facCabComp;
    }

    /**
     * @param total_facCabComp the total_facCabComp to set
     */
    public void setTotal_facCabComp(double total_facCabComp) {
        this.total_facCabComp = total_facCabComp;
    }

    /**
     * @return the valCancelo_facCabComp
     */
    public double getValCancelo_facCabComp() {
        return valCancelo_facCabComp;
    }

    /**
     * @param valCancelo_facCabComp the valCancelo_facCabComp to set
     */
    public void setValCancelo_facCabComp(double valCancelo_facCabComp) {
        this.valCancelo_facCabComp = valCancelo_facCabComp;
    }

    /**
     * @return the valPendiente_facCabComp
     */
    public double getValPendiente_facCabComp() {
        return valPendiente_facCabComp;
    }

    /**
     * @param valPendiente_facCabComp the valPendiente_facCabComp to set
     */
    public void setValPendiente_facCabComp(double valPendiente_facCabComp) {
        this.valPendiente_facCabComp = valPendiente_facCabComp;
    }

    /**
     * @return the valAjuste_facCabComp
     */
    public double getValAjuste_facCabComp() {
        return valAjuste_facCabComp;
    }

    /**
     * @param valAjuste_facCabComp the valAjuste_facCabComp to set
     */
    public void setValAjuste_facCabComp(double valAjuste_facCabComp) {
        this.valAjuste_facCabComp = valAjuste_facCabComp;
    }

    /**
     * @return the PersonaComp
     */
    public Persona getPersonaComp() {
        return PersonaComp;
    }

    /**
     * @param PersonaComp the PersonaComp to set
     */
    public void setPersonaComp(Persona PersonaComp) {
        this.PersonaComp = PersonaComp;
    }

    /**
     * @return the MembresiaComp
     */
    public Membresias getMembresiaComp() {
        return MembresiaComp;
    }

    /**
     * @param MembresiaComp the MembresiaComp to set
     */
    public void setMembresiaComp(Membresias MembresiaComp) {
        this.MembresiaComp = MembresiaComp;
    }

    /**
     * @return the IvasComp
     */
    public Iva getIvasComp() {
        return IvasComp;
    }

    /**
     * @param IvasComp the IvasComp to set
     */
    public void setIvasComp(Iva IvasComp) {
        this.IvasComp = IvasComp;
    }

    /**
     * @return the estadoComp
     */
    public int getEstadoComp() {
        return estadoComp;
    }

    /**
     * @param estadoComp the estadoComp to set
     */
    public void setEstadoComp(int estadoComp) {
        this.estadoComp = estadoComp;
    }
    
    
}