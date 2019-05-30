/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Validaciones;
import consultas.consPlanAlimenticio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import modelos.PlanAlimenticio;
import vistas.VisFicha;

/**
 *
 * @author aplaza
 */
public class CtrlPlanAlimenticio implements ActionListener
{
    VisFicha visFicha;
    
    public CtrlPlanAlimenticio(PlanAlimenticio modPlanAli, VisFicha visFicha, consPlanAlimenticio consPlanAli)
    {
        this.visFicha = visFicha;
         
        this.visFicha.btnGuardar.addActionListener(this);
        this.visFicha.btnEliminar.addActionListener(this);
        this.visFicha.btnLimpiar.addActionListener(this);
        this.visFicha.btnModificar.addActionListener(this);
        
        setListener();  
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == visFicha.btnGuardarPlanAli) //GUARDAR
        {
            
           ArrayList<JTextArea> jtx=new ArrayList<>();
           jtx.add(visFicha.txtDesayuno);
           
           //VALIDA QUE LA CAJA DE TEXTO NO SE ENCUENTRE VACIA
            if (Validaciones.isVoidTextArea(jtx))
            {/*
                modTipPer.setDescripcion_tipoPer(visTipPer.txt_nombre.getText().toUpperCase());
                modTipPer.setEstado_tipoPer(1);
                if (consTipPer.guardar(modTipPer)) 
                {
                    JOptionPane.showMessageDialog(null, "Registro Guardado!");
                }else
                {
                    JOptionPane.showMessageDialog(null, "Error al Guardar");
                }
                limpiar();
                showTable();*/
            }
        }
    }

    private void setListener() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
