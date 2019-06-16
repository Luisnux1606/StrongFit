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
public class CalculoFechaServicio {
   private int  id_calServ ;
   private String  descripcion_calServ ;
   private int estado_calServ ;

 
    public CalculoFechaServicio(int id_calServ, String descripcion_calServ, int estado_calServ) {
        this.id_calServ = id_calServ;
        this.descripcion_calServ = descripcion_calServ;
        this.estado_calServ = estado_calServ;
    }  
     
    /**
     * @return the id_calServ
     */
   public CalculoFechaServicio()
   {
   
   }
   
    public int getId_calServ() {
        return id_calServ;
    }

    /**
     * @param id_calServ the id_calServ to set
     */
    public void setId_calServ(int id_calServ) {
        this.id_calServ = id_calServ;
    }

    /**
     * @return the descripcion_calServ
     */
    public String getDescripcion_calServ() {
        return descripcion_calServ;
    }

    /**
     * @param descripcion_calServ the descripcion_calServ to set
     */
    public void setDescripcion_calServ(String descripcion_calServ) {
        this.descripcion_calServ = descripcion_calServ;
    }

    /**
     * @return the estado_calServ
     */
    public int getEstado_calServ() {
        return estado_calServ;
    }

    /**
     * @param estado_calServ the estado_calServ to set
     */
    public void setEstado_calServ(int estado_calServ) {
        this.estado_calServ = estado_calServ;
    }
   
   public String toString()
   {
       return this.getDescripcion_calServ().toUpperCase();
   
   }
}
