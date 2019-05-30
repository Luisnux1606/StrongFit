/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author aplaza
 */
public class PlanAlimenticio 
{
    private int id;
    private String fecha;
    private String desayuno;
    private double mcolacion;
    private double almuerzo;
    private String tcolacion;
    private String merienda;
    private String detalle_adicional;
    private int estado;  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDesayuno() {
        return desayuno;
    }

    public void setDesayuno(String desayuno) {
        this.desayuno = desayuno;
    }

    public double getMcolacion() {
        return mcolacion;
    }

    public void setMcolacion(double mcolacion) {
        this.mcolacion = mcolacion;
    }

    public double getAlmuerzo() {
        return almuerzo;
    }

    public void setAlmuerzo(double almuerzo) {
        this.almuerzo = almuerzo;
    }

    public String getTcolacion() {
        return tcolacion;
    }

    public void setTcolacion(String tcolacion) {
        this.tcolacion = tcolacion;
    }

    public String getMerienda() {
        return merienda;
    }

    public void setMerienda(String merienda) {
        this.merienda = merienda;
    }

    public String getDetalle_adicional() {
        return detalle_adicional;
    }

    public void setDetalle_adicional(String detalle_adicional) {
        this.detalle_adicional = detalle_adicional;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
}
