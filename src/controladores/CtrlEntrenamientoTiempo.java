/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Calculos;
import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import consultas.ConsAnalisis;
import consultas.ConsEntrenamientoTiempo;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.Analisis;
import modelos.FacturaCab;
import modelos.Ficha;
import modelos.EntrenamientoTiempo;
import modelos.Persona;
import vistas.VisFicha;
import vistas.VisEntrenamientoTiempo;

/**
 *
 * @author Administrator
 */
public class CtrlEntrenamientoTiempo implements ActionListener{
    EntrenamientoTiempo modEntrenamientoTiempo;
    ArrayList<EntrenamientoTiempo> lstEntrenamientoTiempo;
    ConsEntrenamientoTiempo consEntrenamientoTiempo;
    VisEntrenamientoTiempo visEntrenamientoTiempo;
    VisFicha visFicha;

    String cadBus;
    int locale;

    public CtrlEntrenamientoTiempo(EntrenamientoTiempo modEntrenamientoTiempo, ConsEntrenamientoTiempo consEntrenamientoTiempo, VisEntrenamientoTiempo visEntrenamientoTiempo,VisFicha visFicha)
    {
        this.modEntrenamientoTiempo = modEntrenamientoTiempo;
        this.consEntrenamientoTiempo = consEntrenamientoTiempo;
        this.visEntrenamientoTiempo = visEntrenamientoTiempo;
        this.visFicha = visFicha;
                
        this.visEntrenamientoTiempo.btnGuardar.addActionListener(this);
        this.visEntrenamientoTiempo.btnEliminar.addActionListener(this);
        this.visEntrenamientoTiempo.btnLimpiar.addActionListener(this);
        this.visEntrenamientoTiempo.btnModificar.addActionListener(this);

        locale = 0; // 0: menu , 1: btnEnt
        
        cadBus = "";
        showTable();
        setFocus();
        setListener();    
        setTableModel();
        iniciar();
    }
    public void iniciar()
    {
        visEntrenamientoTiempo.setTitle("MEMBRESIAS");
        visEntrenamientoTiempo.setLocationRelativeTo(null);
        visEntrenamientoTiempo.setSize(1000,700);
        visEntrenamientoTiempo.setVisible(true);        
        visEntrenamientoTiempo.txt_id.setVisible(false);                     
        visEntrenamientoTiempo.btnGuardar.setToolTipText("Guardar el registro");
        visEntrenamientoTiempo.btnModificar.setToolTipText("Modificar el registro");
        visEntrenamientoTiempo.btnEliminar.setToolTipText("Eliminar el registro");
        visEntrenamientoTiempo.btnLimpiar.setToolTipText("Limpiar el registro");
    }
    
    public void setTableModel()
    {

      visEntrenamientoTiempo.tbl_entTiempo.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                return c;
            }
            });

    }
    
    public void limpiar()
    {
        visEntrenamientoTiempo.txt_nombre.setText("");                
        visEntrenamientoTiempo.txtEntrenCosto.setText("");        
    }
        
    public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visEntrenamientoTiempo.tbl_entTiempo.getModel();
        int a = visEntrenamientoTiempo.tbl_entTiempo.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
    
    public void setListener(){
        KeyListener keyListenertxtBuscarNombre = new KeyListener() {
          public void keyPressed(KeyEvent keyEvent) {
            printIt("Pressed", keyEvent);
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
            String m=(e.getKeyChar()+"").toUpperCase();
            char c =m.charAt(0);
					
            limpiarTabla();
            if((c+"").equals("")==false&&(c+"").equals(null)==false)
                    cadBus+=c;	            
            else
                if((c+"").equals("")==true){
                    if(cadBus.length()>0)
                    cadBus=cadBus.substring(0, cadBus.length()-1);
                }
            //showTableByFecha(cadBus);
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        visEntrenamientoTiempo.txtBuscarNombre.addKeyListener(keyListenertxtBuscarNombre);

        KeyListener keyListenerTblPersonas = new KeyListener() {
          public void keyPressed(KeyEvent e) {
           
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
            
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           // System.out.println(title + " : " + keyText + " / " + keyEvent.getKeyChar());
          }


        };
        
        MouseListener mouseListTblMembresia = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                     getTableToTxts();
                     desabilitaHabilita(visEntrenamientoTiempo.btnGuardar,false);
                     desabilitaHabilita(visEntrenamientoTiempo.btnModificar,true);
                     
                    /* visEntrenamientoTiempo.tabp_ficha.setEnabledAt(1, true);
                     visAnalisis.tabp_ficha.setEnabledAt(2, true);*/
                }
                
                if(e.getClickCount()==2)
                {
                    int idMemb = Integer.parseInt(visEntrenamientoTiempo.tbl_entTiempo.getValueAt(visEntrenamientoTiempo.tbl_entTiempo.getSelectedRow(), 0)+"");
                    double  dsctoMemb = Double.parseDouble(visEntrenamientoTiempo.tbl_entTiempo.getValueAt(visEntrenamientoTiempo.tbl_entTiempo.getSelectedRow(), 2)+"");
                    visFicha.txtValDscto.setText(dsctoMemb+"");
                    
                    double valMasDscto = Calculos.getDscto(new Double(visFicha.txtValPagar.getText()).doubleValue(), dsctoMemb);
                    visFicha.txtValConDsctoFicha.setText(valMasDscto+"");
                    visEntrenamientoTiempo.dispose();
                     
                    /* visEntrenamientoTiempo.tabp_ficha.setEnabledAt(1, true);
                     visAnalisis.tabp_ficha.setEnabledAt(2, true);*/
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

           

            @Override
            public void mouseExited(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }
        };
       
        visEntrenamientoTiempo.tbl_entTiempo.addMouseListener(mouseListTblMembresia);
        
      
    }
    public void getTableToTxts()
    {
        JTable tblD = visEntrenamientoTiempo.tbl_entTiempo;
        visEntrenamientoTiempo.txt_id.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));       
        visEntrenamientoTiempo.txt_nombre.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1)));
        visEntrenamientoTiempo.txtEntrenCosto.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 2)));
        
    }
    
    public void setFocus()
    {
        visEntrenamientoTiempo.txt_nombre.requestFocus();
        visEntrenamientoTiempo.txt_nombre.setNextFocusableComponent(visEntrenamientoTiempo.txtEntrenCosto);
       
    }
     public void showTable()
    {
        limpiarTabla();                            
           ArrayList<EntrenamientoTiempo> listEntrenamientoTiempo = consEntrenamientoTiempo.buscarTodos(modEntrenamientoTiempo);
           DefaultTableModel model =  (DefaultTableModel)visEntrenamientoTiempo.tbl_entTiempo.getModel();
           Object cols[] = new Object[3];

           for (int i = 0; i < listEntrenamientoTiempo.size(); i++) {
               cols[0] = listEntrenamientoTiempo.get(i).getId_entTmp();
               cols[1] = Validaciones.isNumVoid4(listEntrenamientoTiempo.get(i).getDescripcion_entTiempo()).toUpperCase();
               cols[2] = listEntrenamientoTiempo.get(i).getCosto_entTiempo();             
               
               model.addRow(cols);                    
           }   
    
    }
     
    
    
     public void desabilitaHabilita(JButton btn,boolean estado)
     {
         btn.setEnabled(estado);
     }
     
    @Override
    public void actionPerformed(ActionEvent e) {
        
       if (e.getSource() == visEntrenamientoTiempo.btnGuardar) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
          
           
               if (Validaciones.isDateChooserVoid(jdc)) 
               {
                    modEntrenamientoTiempo.setDescripcion_entTiempo(visEntrenamientoTiempo.txt_nombre.getText().toUpperCase());                
                    modEntrenamientoTiempo.setCosto_entTiempo(Validaciones.isNumVoid2(visEntrenamientoTiempo.txtEntrenCosto.getText()));                     
                    modEntrenamientoTiempo.setEstado(1);
 
                    if (consEntrenamientoTiempo.registrar(modEntrenamientoTiempo)) {
                        JOptionPane.showMessageDialog(null, "Registro Guardado!");
                        visEntrenamientoTiempo.txt_id.setText(consEntrenamientoTiempo.getLastId()+"");
                       // limpiar();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error al Guardar");
                       // limpiar();
                    }
                    showTable();
               }        
        }
       
       if (e.getSource() == visEntrenamientoTiempo.btnModificar) 
       {
           System.out.println(visEntrenamientoTiempo.txt_id.getText()+"");
            modEntrenamientoTiempo.setDescripcion_entTiempo(visEntrenamientoTiempo.txt_nombre.getText().toUpperCase());                
            modEntrenamientoTiempo.setCosto_entTiempo(Validaciones.isNumVoid2(visEntrenamientoTiempo.txtEntrenCosto.getText()));
            modEntrenamientoTiempo.setId_entTmp(Integer.parseInt(visEntrenamientoTiempo.txt_id.getText()+""));
           

            if (consEntrenamientoTiempo.modificar(modEntrenamientoTiempo)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado!");
                limpiar();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
            showTable();
        }
       
       if (e.getSource() == visEntrenamientoTiempo.btnEliminar) 
       {
            modEntrenamientoTiempo.setId_entTmp(Integer.parseInt(visEntrenamientoTiempo.txt_id.getText()));
            modEntrenamientoTiempo.setEstado(0);
            int o= JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar el registro?", "Confirmar eliminar?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);            
            if (o ==0) 
            {           
                if (consEntrenamientoTiempo.eliminar(modEntrenamientoTiempo)) {
                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    limpiar();                    
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error al Eliminar");
                    limpiar();
                }
                showTable();
            }
            visEntrenamientoTiempo.btnGuardar.setEnabled(true);
        }
                      
       if (e.getSource() == visEntrenamientoTiempo.btnLimpiar) 
        {
           limpiar();
           desabilitaHabilita(visEntrenamientoTiempo.btnGuardar,true);
           desabilitaHabilita(visEntrenamientoTiempo.btnModificar,false);
           
         //  visEntrenamientoTiempo.tabp_ficha.setEnabledAt(1, true);
          // visEntrenamientoTiempo.tabp_ficha.setEnabledAt(2, false);
        }
    }
}
