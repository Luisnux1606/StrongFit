/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Configuracion;
import javax.swing.JFrame;
import vistas.VisDiarioGeneral;

/**
 *
 * @author aplaza
 */
public class CtrlDiarioGeneral 
{
    VisDiarioGeneral visDiario;
            
    public CtrlDiarioGeneral(VisDiarioGeneral visDiario)
    {
        this.visDiario = visDiario;
    }
    
    public void iniciar()
    {
        visDiario.setTitle(Configuracion.nomEmp +" Diario General");
        visDiario.setLocationRelativeTo(null);
        visDiario.setSize(1000,700);
        visDiario.setVisible(true);
        visDiario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        /*
        visCat.txt_id.setVisible(false);
        
        visCat.btnBuscar.setToolTipText("Buscar el registro");
        visCat.btnGuardar.setToolTipText("Guardar el registro");
        visCat.btnModificar.setToolTipText("Modificar el registro");
        visCat.btnEliminar.setToolTipText("Eliminar el registro");
        visCat.btnLimpiar.setToolTipText("Limpiar el registro");
        
        
        showTable();*/
    }
}
