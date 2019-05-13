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
    private double subTotal_facCab;
    private double total_facCab;
    private double valPendiente_facCab;
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
     * @return the Persona_id_per
     */
    public Persona getPersona() {
        return Persona;
    }

    /**
     * @param Persona_id_per the Persona_id_per to set
     */
    public void setPersona(Persona Persona_id_per) {
        this.Persona = Persona_id_per;
    }

    /**
     * @return the Membresia_id_memb
     */
    public Membresias getMembresia() {
        return Membresia;
    }

    /**
     * @param Membresia_id_memb the Membresia_id_memb to set
     */
    public void setMembresia(Membresias Membresia_id_memb) {
        this.Membresia = Membresia_id_memb;
    }

    /**
     * @return the Ivas_id_ivas
     */
    public Iva getIva() {
        return Ivas;
    }

    /**
     * @param Ivas_id_ivas the Ivas_id_ivas to set
     */
    public void setIva(Iva Ivas_id_ivas) {
        this.Ivas = Ivas_id_ivas;
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
