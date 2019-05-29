/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Configuracion;
import assets.Validaciones;
import consultas.ConsTipoPersona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
import modelos.TipoPersona;
import vistas.VisTipoPersona;

/**
 *
 * @author aplaza
 */
public class CtrlTipoPersonas implements ActionListener
{
    TipoPersona modTipPer;
    VisTipoPersona visTipPer;
    ConsTipoPersona consTipPer;
    Object visFicha;
    
    public CtrlTipoPersonas (TipoPersona modtipPer, VisTipoPersona visTipPer, ConsTipoPersona constipPer, Object visFicha)
    {
        this.modTipPer = modtipPer;
        this.visTipPer = visTipPer;
        this.consTipPer = constipPer;        
        this.visFicha = visFicha;
    }
    
    public void iniciar()
    {
        visTipPer.setTitle(Configuracion.nomEmp + "GESTION TIPOS DE PERSONAS");        
        visTipPer.setLocationRelativeTo(null);
        visTipPer.setSize(1000,700);
        visTipPer.setVisible(true);
        
        visTipPer.txt_id.setVisible(false);
        
        visTipPer.btnGuardar.setToolTipText("Guardar el registro");
        visTipPer.btnModificar.setToolTipText("Modificar el registro");
        visTipPer.btnEliminar.setToolTipText("Eliminar el registro");
        visTipPer.btnLimpiar.setToolTipText("Limpiar el registro");
        
        visTipPer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == visTipPer.btnGuardar) 
        {
            //VALIDA QUE LA CAJA DE TEXTO NO SE ENCUENTRE VACIA
           ArrayList<JTextField> jtx=new ArrayList<>();
           jtx.add(visTipPer.txt_nombre);
           
            if (Validaciones.isVoid(jtx))
            {
                       
            }
        }
    }
}
