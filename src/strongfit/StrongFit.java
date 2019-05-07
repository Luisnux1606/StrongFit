/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongfit;

import consultas.ConsPersona;
import controladores.CtrlPersonas;
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
        ConsPersona consPer = new ConsPersona();
        VisPersona visPer = new VisPersona();    
        VisMembresia visMemb = new VisMembresia();
    
        CtrlPersonas ctrlPer = new CtrlPersonas(per, consPer,visMemb, visPer);               
        ctrlPer.iniciar();
        visPer.setVisible(true);
    }
    
}
