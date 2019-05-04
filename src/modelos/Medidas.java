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
public class Medidas {
    
    private int id;
    private String fecha;
    private double peso;
    private double estatura;
    private int edad;
    private int nro_hijos;
    private double pecho;
    private double abdomen_alto;
    private double cintura;
    private double abdomen_bajo;
    private double cadera;
    private double piernas;
    private double pantorrilla;
    private double brazo;
    private double antebrazo;
    private double cuello;
    private double espalda;
    private double porcentaje_grasa;
    private double porcentaje_kgs;
    private int estado;

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @return the peso
     */
    public double getPeso() {
        return peso;
    }

    /**
     * @return the estatura
     */
    public double getEstatura() {
        return estatura;
    }

    /**
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @return the nro_hijos
     */
    public int getNro_hijos() {
        return nro_hijos;
    }

    /**
     * @return the pecho
     */
    public double getPecho() {
        return pecho;
    }

    /**
     * @return the abdomen_alto
     */
    public double getAbdomen_alto() {
        return abdomen_alto;
    }

    /**
     * @return the cintura
     */
    public double getCintura() {
        return cintura;
    }

    /**
     * @return the abdomen_bajo
     */
    public double getAbdomen_bajo() {
        return abdomen_bajo;
    }

    /**
     * @return the cadera
     */
    public double getCadera() {
        return cadera;
    }

    /**
     * @return the piernas
     */
    public double getPiernas() {
        return piernas;
    }

    /**
     * @return the pantorrilla
     */
    public double getPantorrilla() {
        return pantorrilla;
    }

    /**
     * @return the brazo
     */
    public double getBrazo() {
        return brazo;
    }

    /**
     * @return the antebrazo
     */
    public double getAntebrazo() {
        return antebrazo;
    }

    /**
     * @return the cuello
     */
    public double getCuello() {
        return cuello;
    }

    /**
     * @return the espalda
     */
    public double getEspalda() {
        return espalda;
    }

    /**
     * @return the porcentaje_grasa
     */
    public double getPorcentaje_grasa() {
        return porcentaje_grasa;
    }

    /**
     * @return the porcentaje_kgs
     */
    public double getPorcentaje_kgs() {
        return porcentaje_kgs;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * @param estatura the estatura to set
     */
    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * @param nro_hijos the nro_hijos to set
     */
    public void setNro_hijos(int nro_hijos) {
        this.nro_hijos = nro_hijos;
    }

    /**
     * @param pecho the pecho to set
     */
    public void setPecho(double pecho) {
        this.pecho = pecho;
    }

    /**
     * @param abdomen_alto the abdomen_alto to set
     */
    public void setAbdomen_alto(double abdomen_alto) {
        this.abdomen_alto = abdomen_alto;
    }

    /**
     * @param cintura the cintura to set
     */
    public void setCintura(double cintura) {
        this.cintura = cintura;
    }

    /**
     * @param abdomen_bajo the abdomen_bajo to set
     */
    public void setAbdomen_bajo(double abdomen_bajo) {
        this.abdomen_bajo = abdomen_bajo;
    }

    /**
     * @param cadera the cadera to set
     */
    public void setCadera(double cadera) {
        this.cadera = cadera;
    }

    /**
     * @param piernas the piernas to set
     */
    public void setPiernas(double piernas) {
        this.piernas = piernas;
    }

    /**
     * @param pantorrilla the pantorrilla to set
     */
    public void setPantorrilla(double pantorrilla) {
        this.pantorrilla = pantorrilla;
    }

    /**
     * @param brazo the brazo to set
     */
    public void setBrazo(double brazo) {
        this.brazo = brazo;
    }

    /**
     * @param antebrazo the antebrazo to set
     */
    public void setAntebrazo(double antebrazo) {
        this.antebrazo = antebrazo;
    }

    /**
     * @param cuello the cuello to set
     */
    public void setCuello(double cuello) {
        this.cuello = cuello;
    }

    /**
     * @param espalda the espalda to set
     */
    public void setEspalda(double espalda) {
        this.espalda = espalda;
    }

    /**
     * @param porcentaje_grasa the porcentaje_grasa to set
     */
    public void setPorcentaje_grasa(double porcentaje_grasa) {
        this.porcentaje_grasa = porcentaje_grasa;
    }

    /**
     * @param porcentaje_kgs the porcentaje_kgs to set
     */
    public void setPorcentaje_kgs(double porcentaje_kgs) {
        this.porcentaje_kgs = porcentaje_kgs;
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
