/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongfit;

import assets.Configuracion;
import consultas.ConsAnalisis;
import consultas.ConsFacturaCab;
import consultas.ConsFacturaCabCompras;
import consultas.ConsFacturaDet;
import consultas.ConsFicha;
import consultas.ConsMedidas;
import consultas.ConsPersona;
import controladores.CtrlAnalisis;
import controladores.CtrlFacturaCab;
import controladores.CtrlFacturaCabCompras;
import controladores.CtrlFacturaDetalle;
import controladores.CtrlFicha;
import controladores.CtrlMedidas;
import controladores.CtrlPersonas;
import controladores.CtrlRegistroEntrada;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelos.Analisis;
import modelos.FacturaCab;
import modelos.FacturaCabCompras;
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
    public static void main(String[] args) 
    {
        new Configuracion();
               
        Persona per = new Persona();
        Ficha ficha =  new Ficha();
        Medidas med =  new Medidas();
        Analisis ana = new Analisis();
        FacturaCab facCab = new FacturaCab();
        FacturaCabCompras facCabComp = new FacturaCabCompras();
       
        
        ConsPersona consPer = new ConsPersona();
        ConsFicha consFicha = new ConsFicha();
        ConsMedidas consMed = new ConsMedidas();
        ConsAnalisis consAna = new ConsAnalisis();
        ConsFacturaCab consFacCab = new ConsFacturaCab();
        ConsFacturaDet consFacDet = new ConsFacturaDet();
        ConsFacturaCabCompras consFacCabComp = new ConsFacturaCabCompras();
        
        VisPersona visPer = new VisPersona();   
        VisFicha visFicha = new VisFicha();        
        VisMembresia visMemb = new VisMembresia();
        

        
        CtrlMedidas ctrlMed = new CtrlMedidas(med, consMed, visFicha);        
        CtrlAnalisis ctrlAna = new CtrlAnalisis(ana, consAna, visFicha);
        CtrlFacturaCab ctrlFacturaCab = new CtrlFacturaCab(facCab, consFacCab, visFicha, visMemb, per);
        CtrlFacturaCabCompras ctrlFacCabComp = new CtrlFacturaCabCompras(facCabComp, consFacCabComp, visFicha, visMemb, per);
        CtrlRegistroEntrada ctrlRegEnt = new CtrlRegistroEntrada(visFicha);
        CtrlPersonas ctrlPersonas = new CtrlPersonas(per, consPer, visPer, visFicha);
                
        CtrlFicha ctrlFicha = new CtrlFicha(ficha, consFicha, visFicha,ctrlRegEnt );
        ctrlFicha.iniciar();

    }
    
}
