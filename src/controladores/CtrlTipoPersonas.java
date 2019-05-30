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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
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
    String cadBus;
    
    public CtrlTipoPersonas (TipoPersona modtipPer, VisTipoPersona visTipPer, ConsTipoPersona constipPer, Object visFicha)
    {
        cadBus = "";
        
        this.modTipPer = modtipPer;
        this.visTipPer = visTipPer;
        this.consTipPer = constipPer;        
        this.visFicha = visFicha;
        
        this.visTipPer.btnGuardar.addActionListener(this);
        this.visTipPer.btnEliminar.addActionListener(this);
        this.visTipPer.btnLimpiar.addActionListener(this);
        this.visTipPer.btnModificar.addActionListener(this);
        
        setListener();  
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
        
        showTable();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == visTipPer.btnGuardar) //GUARDAR
        {
            //VALIDA QUE LA CAJA DE TEXTO NO SE ENCUENTRE VACIA
           ArrayList<JTextField> jtx=new ArrayList<>();
           jtx.add(visTipPer.txt_nombre);
           
            if (Validaciones.isVoid(jtx))
            {
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
                showTable();
            }
        }
        if (e.getSource() == visTipPer.btnEliminar) //ELIMINAR
        {   
            modTipPer.setId_tipoPer(Integer.parseInt(visTipPer.txt_id.getText()));
            modTipPer.setEstado_tipoPer(0);
            int o= JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar el registro?", "Confirmar eliminar?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);            
            if (o==0) 
            {           
                if (consTipPer.eliminar(modTipPer)) {
                    JOptionPane.showMessageDialog(null, "Registro Eliminado !");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error al Eliminar");
                }
                limpiar();
                showTable();
            }
        }
        
        if (e.getSource() == visTipPer.btnModificar)  //MODIFICAR
       {
            modTipPer.setId_tipoPer(Integer.parseInt(visTipPer.txt_id.getText()));
            modTipPer.setDescripcion_tipoPer(visTipPer.txt_nombre.getText());
            if (consTipPer.modificar(modTipPer)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado!");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al Modificar");                
            }
            limpiar();
            showTable();
        }
        
        if (e.getSource() == visTipPer.btnLimpiar) 
        {
           limpiar();
           desabilitaHabilita(visTipPer.btnGuardar,true);
           desabilitaHabilita(visTipPer.btnModificar,false);
        }
    }
    
    public void limpiar()
    {
        visTipPer.txt_nombre.setText("");
    }
    
    public void showTable()
    {
        limpiarTabla();                            
        ArrayList<TipoPersona> tipList = consTipPer.buscarTodos(modTipPer);
        DefaultTableModel model =  (DefaultTableModel)visTipPer.tbl_tipoPersona.getModel();
        Object cols[] = new Object[2];

           for (int i = 0; i < tipList.size(); i++) 
           {
               cols[0] = tipList.get(i).getId_tipoPer();
               cols[1] = tipList.get(i).getDescripcion_tipoPer().toUpperCase();
               model.addRow(cols);                    
           }   
    }
    
    public void limpiarTabla()
    {
        DefaultTableModel tb = (DefaultTableModel) visTipPer.tbl_tipoPersona.getModel();
        int a = visTipPer.tbl_tipoPersona.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
    
    public void setListener()
    {
        //CLICK EN LA TABLA
        MouseListener mouseListTblTipPer= new MouseListener() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {    
                if(e.getClickCount()==1)
                {
                     getTableToTxts();
                     desabilitaHabilita(visTipPer.btnGuardar,false);
                     desabilitaHabilita(visTipPer.btnModificar,true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}
        };
       visTipPer.tbl_tipoPersona.addMouseListener(mouseListTblTipPer);
       
       //BUSCAR POR LA CAJA DE TEXTO.
        KeyListener keyListenertxtBuscarCedula = new KeyListener() 
        {
            public void keyPressed(KeyEvent keyEvent) {
                printIt("Pressed", keyEvent);
            }

            public void keyReleased(KeyEvent keyEvent) {
                printIt("Released", keyEvent);
            }

            public void keyTyped(KeyEvent e) 
            {
                String m=(e.getKeyChar()+"").toUpperCase();
                char c =m.charAt(0);
					
                limpiarTabla();
                if((c+"").equals("")==false&&(c+"").equals(null)==false)
                    cadBus+=c;	            
                else{
                    if((c+"").equals("")==true){
                        if(cadBus.length()>0)
                            cadBus=cadBus.substring(0, cadBus.length()-1);
                        }
                    }
                    showTableByNom(cadBus);
                    
                    if(visTipPer.txtBuscarNombre.getText().length()==0){
                        cadBus="";
                        showTable();
                    }
                    else
                        showTableByNom(cadBus);
                    }
          
          private void printIt(String title, KeyEvent keyEvent) 
          {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
          }
        };
        visTipPer.txtBuscarNombre.addKeyListener(keyListenertxtBuscarCedula);
        
       
    }
    
    public void showTableByNom(String nom)
    {
        limpiarTabla();                            
        ArrayList<TipoPersona> listTipPer = consTipPer.buscarTodosByNom(modTipPer,nom);
        DefaultTableModel model =  (DefaultTableModel)visTipPer.tbl_tipoPersona.getModel();
        Object cols[] = new Object[3];

        for (int i = 0; i < listTipPer.size(); i++) {
            cols[0] = listTipPer.get(i).getId_tipoPer();
            cols[1] = Validaciones.isNumVoid4(listTipPer.get(i).getDescripcion_tipoPer().toUpperCase());
            model.addRow(cols);                    
        }   
    }
    
    
    
    public void desabilitaHabilita(JButton btn,boolean estado)
    {
         btn.setEnabled(estado);
    }
    
    public void getTableToTxts()
    {
        JTable tblD = visTipPer.tbl_tipoPersona;
        visTipPer.txt_id.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));       
        visTipPer.txt_nombre.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1)));
    }
}
