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
import consultas.ConsMembresias;
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
import modelos.Ficha;
import modelos.Membresias;
import modelos.Persona;
import vistas.VisFicha;
import vistas.VisMembresia;

/**
 *
 * @author Administrator
 */
public class CtrlMembresias implements ActionListener{
    Membresias modMembresias;
    ArrayList<Membresias> lstMembresias;
    ConsMembresias consMembresias;
    VisMembresia visMembresias;
    VisFicha visFicha;
    Ficha ficha;
    
    String cadBus;

    public CtrlMembresias(Membresias modMembresias, ConsMembresias consMembresias, VisMembresia visMembresias,Ficha f,VisFicha visFicha)
    {
        this.modMembresias = modMembresias;
        this.consMembresias = consMembresias;
        this.visMembresias = visMembresias;
        this.ficha = f;
        this.visFicha = visFicha;
        
        
        this.visMembresias.btnGuardar.addActionListener(this);
        this.visMembresias.btnEliminar.addActionListener(this);
        this.visMembresias.btnLimpiar.addActionListener(this);
        this.visMembresias.btnModificar.addActionListener(this);
        this.visMembresias.btnBuscar.addActionListener(this);
        cadBus = "";
        showTable();
        setFocus();
        setListener();    
        setTableModel();
        iniciar();
    }
    public void iniciar()
    {
        visMembresias.setTitle("MEMBRESIAS");
        visMembresias.setLocationRelativeTo(null);
        visMembresias.setSize(1000,700);
        visMembresias.setVisible(true);
        
        visMembresias.txt_id.setVisible(false);
                 
        
        visMembresias.btnBuscar.setToolTipText("Buscar analisis por nombre");
        visMembresias.btnGuardar.setToolTipText("Guardar el registro");
        visMembresias.btnModificar.setToolTipText("Modificar el registro");
        visMembresias.btnEliminar.setToolTipText("Eliminar el registro");
        visMembresias.btnLimpiar.setToolTipText("Limpiar el registro");
    }
    
    public void setTableModel()
    {

      visMembresias.tbl_membresias.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
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
        visMembresias.txt_nombre.setText("");                
        visMembresias.txt_dscto.setText("");        
    }
        
    public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visMembresias.tbl_membresias.getModel();
        int a = visMembresias.tbl_membresias.getRowCount()-1;
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
        visMembresias.txtBuscarNombre.addKeyListener(keyListenertxtBuscarNombre);

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
                     desabilitaHabilita(visMembresias.btnGuardar,false);
                     desabilitaHabilita(visMembresias.btnModificar,true);
                     
                    /* visMembresias.tabp_ficha.setEnabledAt(1, true);
                     visAnalisis.tabp_ficha.setEnabledAt(2, true);*/
                }
                
                if(e.getClickCount()==2)
                {
                    int idMemb = Integer.parseInt(visMembresias.tbl_membresias.getValueAt(visMembresias.tbl_membresias.getSelectedRow(), 0)+"");
                    double  dsctoMemb = Double.parseDouble(visMembresias.tbl_membresias.getValueAt(visMembresias.tbl_membresias.getSelectedRow(), 2)+"");
                    visFicha.txtValDscto.setText(dsctoMemb+"");
                    
                    double valMasDscto = Calculos.getDscto(new Double(visFicha.txtValPagar.getText()).doubleValue(), dsctoMemb);
                    visFicha.txtValConDsctoFicha.setText(valMasDscto+"");
                    visMembresias.dispose();
                     
                    /* visMembresias.tabp_ficha.setEnabledAt(1, true);
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
       
        visMembresias.tbl_membresias.addMouseListener(mouseListTblMembresia);
        
      
    }
    public void getTableToTxts()
    {
        JTable tblD = visMembresias.tbl_membresias;
        visMembresias.txt_id.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));       
        visMembresias.txt_nombre.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1)));
        visMembresias.txt_dscto.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 2)));
        
    }
    
    public void setFocus()
    {
        visMembresias.txt_nombre.requestFocus();
        visMembresias.txt_nombre.setNextFocusableComponent(visMembresias.txt_dscto);
       
    }
     public void showTable()
    {
        limpiarTabla();                            
           ArrayList<Membresias> listMembresias = consMembresias.buscarTodos(modMembresias);
           DefaultTableModel model =  (DefaultTableModel)visMembresias.tbl_membresias.getModel();
           Object cols[] = new Object[3];

           for (int i = 0; i < listMembresias.size(); i++) {
               cols[0] = listMembresias.get(i).getId();
               cols[1] = Validaciones.isNumVoid4(listMembresias.get(i).getNombre()).toUpperCase();
               cols[2] = listMembresias.get(i).getDscto();             
               
               model.addRow(cols);                    
           }   
    
    }
     
    
    
     public void desabilitaHabilita(JButton btn,boolean estado)
     {
         btn.setEnabled(estado);
     }
     
    @Override
    public void actionPerformed(ActionEvent e) {
        
       if (e.getSource() == visMembresias.btnGuardar) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
          
           
               if (Validaciones.isDateChooserVoid(jdc)) 
               {
                    modMembresias.setNombre(visMembresias.txt_nombre.getText().toUpperCase());                
                    modMembresias.setDscto(Validaciones.isNumVoid2(visMembresias.txt_dscto.getText())); 
                    
                    modMembresias.setEstado(1);
 
                    if (consMembresias.registrar(modMembresias)) {
                        JOptionPane.showMessageDialog(null, "Registro Guardado!");
                        visMembresias.txt_id.setText(consMembresias.getLastId()+"");
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
       
       if (e.getSource() == visMembresias.btnModificar) 
       {
            modMembresias.setId(Integer.parseInt(visMembresias.txt_id.getText()));
            modMembresias.setNombre(visMembresias.txt_nombre.getText().toUpperCase());                
            modMembresias.setDscto(Validaciones.isNumVoid2(visMembresias.txt_dscto.getText())); 

            if (consMembresias.modificar(modMembresias)) {
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
       
       if (e.getSource() == visMembresias.btnEliminar) 
       {
            modMembresias.setId(Integer.parseInt(visMembresias.txt_id.getText()));
            modMembresias.setEstado(0);
            int o= JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar el registro?", "Confirmar eliminar?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);            
            if (o ==0) 
            {           
                if (consMembresias.eliminar(modMembresias)) {
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
            visMembresias.btnGuardar.setEnabled(true);
        }
       
       if (e.getSource() == visMembresias.btnBuscar) 
         {                      
            if (consMembresias.buscar(modMembresias)){    

                visMembresias.txt_id.setText(String.valueOf(modMembresias.getId()));
                visMembresias.txt_nombre.setText(String.valueOf(modMembresias.getNombre()).toUpperCase());  
                visMembresias.txt_dscto.setText(String.valueOf(modMembresias.getDscto()));                        
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se encotro registro");
                limpiar();
            }
            showTable();
         }
 
        
       if (e.getSource() == visMembresias.btnLimpiar) 
        {
           limpiar();
           desabilitaHabilita(visMembresias.btnGuardar,true);
           desabilitaHabilita(visMembresias.btnModificar,false);
           
         //  visMembresias.tabp_ficha.setEnabledAt(1, true);
          // visMembresias.tabp_ficha.setEnabledAt(2, false);
        }
    }
}
