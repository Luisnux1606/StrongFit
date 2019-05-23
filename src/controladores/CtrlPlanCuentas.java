/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import javax.swing.JFrame;
import vistas.VisPlanCuentas;

/**
 *
 * @author aplaza
 */
public class CtrlPlanCuentas 
{
    VisPlanCuentas visPlan;
            
    public CtrlPlanCuentas(VisPlanCuentas visPlan)
    {
        this.visPlan = visPlan;
    }
    
    public void iniciar()
    {
        visPlan.setTitle("Plan de Cuentas");
        visPlan.setLocationRelativeTo(null);
        visPlan.setSize(1000,700);
        visPlan.setVisible(true);
        visPlan.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        /*
        visCat.txt_id.setVisible(false);
        
        visCat.btnBuscar.setToolTipText("Buscar el registro");
        visCat.btnGuardar.setToolTipText("Guardar el registro");
        visCat.btnModificar.setToolTipText("Modificar el registro");
        visCat.btnEliminar.setToolTipText("Eliminar el registro");
        visCat.btnLimpiar.setToolTipText("Limpiar el registro");
        
        visCat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showTable();*/
    }    
}
