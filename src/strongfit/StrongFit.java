/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongfit;

import consultas.ConsAnalisis;
import consultas.ConsFacturaCab;
import consultas.ConsFicha;
import consultas.ConsMedidas;
import consultas.ConsPersona;
import controladores.CtrlAnalisis;
import controladores.CtrlFacturaCab;
import controladores.CtrlFicha;
import controladores.CtrlMedidas;
import controladores.CtrlPersonas;
import modelos.Analisis;
import modelos.FacturaCab;
import modelos.Ficha;
import modelos.Medidas;
import modelos.Membresias;
import modelos.Persona;
import vistas.VisFicha;
import vistas.VisMembresia;
import vistas.VisPersona;

/**
 *
 * @author Administrator
 */
public class StrongFit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        Persona per = new Persona();
        Ficha ficha =  new Ficha();
        Medidas med =  new Medidas();
        Analisis ana = new Analisis();
       
        
        ConsPersona consPer = new ConsPersona();
        ConsFicha consFicha = new ConsFicha();
        ConsMedidas consMed = new ConsMedidas();
        ConsAnalisis consAna = new ConsAnalisis();
        
        VisPersona visPer = new VisPersona();   
        VisFicha visFicha = new VisFicha();        
        VisMembresia visMemb = new VisMembresia();
        
       
        
        CtrlPersonas ctrlPersonas = new CtrlPersonas(per, consPer, visPer);
        CtrlMedidas ctrlMed = new CtrlMedidas(med, consMed, visFicha);        
        CtrlAnalisis ctrlAna = new CtrlAnalisis(ana, consAna, visFicha);
        
        
        
        CtrlFicha ctrlFicha = new CtrlFicha(ficha, consFicha, visFicha );
        ctrlFicha.iniciar();
       // CtrlPersonas ctrlPer = new CtrlPersonas(per, consPer,visMemb, visPer);               
       // ctrlPer.iniciar();
        //visPer.setVisible(true);
    }
    
}
