/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import consultas.ConsAnalisis;
import consultas.ConsMedidas;
import consultas.ConsPersona;
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
import modelos.Medidas;
import modelos.Persona;
import vistas.VisFicha;
import vistas.VisPersona;

/**
 *
 * @author Administrator
 */
public class CtrlAnalisis implements ActionListener{
    Analisis modAnalisis;
    ArrayList<Analisis> lstAnalisis;
    ConsAnalisis consAnalisis;
    VisFicha visAnalisis;
    Persona persona;
    
    String cadBus;

    public CtrlAnalisis(Analisis modAnalisis, ConsAnalisis consAnalisis, VisFicha visAnalisis,Persona p)
    {
        this.modAnalisis = modAnalisis;
        this.consAnalisis = consAnalisis;
        this.visAnalisis = visAnalisis;
        this.persona = p;
        
        this.visAnalisis.btnGuardarAnalisis.addActionListener(this);
        this.visAnalisis.btnEliminarAnalisis.addActionListener(this);
        this.visAnalisis.btnLimpiarAnalisis.addActionListener(this);
        this.visAnalisis.btnModificarAnalisis.addActionListener(this);
        this.visAnalisis.btnBuscarAnalisis.addActionListener(this);
        cadBus = "";
        showTable();
        setFocus();
        setListener();    
        setTableModel();
        iniciar();
    }
    public void iniciar()
    {
        visAnalisis.setTitle("FICHA");
        visAnalisis.setLocationRelativeTo(null);
        visAnalisis.txt_id_analisis.setVisible(false);
        visAnalisis.lbl_PersonaAnalisis.setText(persona.getNombre().toUpperCase() +" "+persona.getApellido().toUpperCase());         
        
        visAnalisis.btnBuscarAnalisis.setToolTipText("Buscar analisis por fecha");
        visAnalisis.btnGuardarAnalisis.setToolTipText("Guardar el registro");
        visAnalisis.btnModificarAnalisis.setToolTipText("Modificar el registro");
        visAnalisis.btnEliminarAnalisis.setToolTipText("Eliminar el registro");
        visAnalisis.btnLimpiarAnalisis.setToolTipText("Limpiar el registro");
    }
    
    public void setTableModel()
    {

      visAnalisis.tblAnalisis.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
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
        visAnalisis.dtcFechaAnalisis.setDate(null);                
        visAnalisis.txtExcesoGrasa.setText("");
        visAnalisis.txtExcesoLiquido.setText("");
        visAnalisis.txtExcesoTotal.setText("");
        visAnalisis.txtRecomPesas.setText("");
        visAnalisis.txtRecomCardio.setText("");
        visAnalisis.txtRecomFuncional.setText("");
 
        limpiarTabla();
    }
        
    public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visAnalisis.tblAnalisis.getModel();
        int a = visAnalisis.tblAnalisis.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
    
    public void setListener(){
        KeyListener keyListenertxtBuscarFecha = new KeyListener() {
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
            showTableByFecha(cadBus);
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        visAnalisis.txtFechaAnalisis.addKeyListener(keyListenertxtBuscarFecha);

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
        
        MouseListener mouseListTblAnalisis = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                     getTableToTxts();
                     desabilitaHabilita(visAnalisis.btnGuardarAnalisis,false);
                     desabilitaHabilita(visAnalisis.btnModificarAnalisis,true);
                     
                     visAnalisis.tabp_ficha.setEnabledAt(1, true);
                     visAnalisis.tabp_ficha.setEnabledAt(2, true);
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
       
        visAnalisis.tblAnalisis.addMouseListener(mouseListTblAnalisis);
        
        /////
        MouseListener mouseTxtTotal= new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                    double exGra = Validaciones.isNumVoid2(visAnalisis.txtExcesoGrasa.getText().trim());
                    double exLiq = Validaciones.isNumVoid2(visAnalisis.txtExcesoLiquido.getText().trim());
                    double exTot = exGra + exLiq;
                    visAnalisis.txtExcesoTotal.setText(exTot+"");
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
       
        visAnalisis.txtExcesoTotal.addMouseListener(mouseTxtTotal);
      
    }
    public void getTableToTxts()
    {
        JTable tblD = visAnalisis.tblAnalisis;
        visAnalisis.txt_id_analisis.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));
        visAnalisis.dtcFechaAnalisis.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1))));
        visAnalisis.txtExcesoGrasa.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 2)));
        visAnalisis.txtExcesoLiquido.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 3)));
        visAnalisis.txtExcesoTotal.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 4)));
        visAnalisis.txtRecomPesas.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 5)).toUpperCase());
        visAnalisis.txtRecomCardio.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 6)).toUpperCase());
        visAnalisis.txtRecomFuncional.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 7)).toUpperCase());
        
    }
    
    public void setFocus()
    {
        visAnalisis.dtcFechaAnalisis.requestFocus();
        visAnalisis.dtcFecha.setNextFocusableComponent(visAnalisis.txtExcesoGrasa);
        visAnalisis.txtExcesoGrasa.setNextFocusableComponent(visAnalisis.txtExcesoLiquido);
        visAnalisis.txtExcesoLiquido.setNextFocusableComponent(visAnalisis.txtExcesoTotal);    
        visAnalisis.txtExcesoTotal.setNextFocusableComponent(visAnalisis.txtRecomPesas);
        visAnalisis.txtRecomPesas.setNextFocusableComponent(visAnalisis.txtRecomCardio); 
        visAnalisis.txtRecomCardio.setNextFocusableComponent(visAnalisis.txtRecomFuncional); 

    }
     public void showTable()
    {
        limpiarTabla();                            
           ArrayList<Analisis> listAnalisis = consAnalisis.buscarTodos(modAnalisis);
           DefaultTableModel model =  (DefaultTableModel)visAnalisis.tblAnalisis.getModel();
           Object cols[] = new Object[8];

           for (int i = 0; i < listAnalisis.size(); i++) {
               cols[0] = listAnalisis.get(i).getId();
               cols[1] = listAnalisis.get(i).getFecha();
               cols[2] = listAnalisis.get(i).getExeso_grasa();
               cols[3] = listAnalisis.get(i).getExeso_liquido();
               cols[4] = listAnalisis.get(i).getExeso_total();
               cols[5] = Validaciones.isNumVoid4(listAnalisis.get(i).getRecomendacion_pesas()).toUpperCase();
               cols[6] = Validaciones.isNumVoid4(listAnalisis.get(i).getRecomendacion_cardio()).toUpperCase();
               cols[7] = Validaciones.isNumVoid4(listAnalisis.get(i).getRecomendacion_funcional()).toUpperCase();
               
               model.addRow(cols);                    
           }   
    
    }
     
     public void showTableByFecha(String fecha)
    {
        limpiarTabla();                            
           ArrayList<Analisis> listAnalisis = consAnalisis.buscarTodosPorFec(modAnalisis,fecha);
           DefaultTableModel model =  (DefaultTableModel)visAnalisis.tblAnalisis.getModel();
           Object cols[] = new Object[19];

           for (int i = 0; i < listAnalisis.size(); i++) {
                cols[0] = listAnalisis.get(i).getId();
               cols[1] = listAnalisis.get(i).getFecha();
               cols[2] = listAnalisis.get(i).getExeso_grasa();
               cols[3] = listAnalisis.get(i).getExeso_liquido();
               cols[4] = listAnalisis.get(i).getExeso_total();
               cols[5] = Validaciones.isNumVoid4(listAnalisis.get(i).getRecomendacion_cardio()).toUpperCase();
               cols[6] = Validaciones.isNumVoid4(listAnalisis.get(i).getRecomendacion_cardio()).toUpperCase();
               cols[7] = Validaciones.isNumVoid4(listAnalisis.get(i).getRecomendacion_funcional()).toUpperCase();


               model.addRow(cols);                    
           }   
    
    }
    
     public void desabilitaHabilita(JButton btn,boolean estado)
     {
         btn.setEnabled(estado);
     }
     
    @Override
    public void actionPerformed(ActionEvent e) {
        
       if (e.getSource() == visAnalisis.btnGuardarAnalisis) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
           jdc.add(visAnalisis.dtcFechaAnalisis);
           
               if (Validaciones.isDateChooserVoid(jdc)) 
               {
                    modAnalisis.setFecha(Validaciones.setFormatFecha(visAnalisis.dtcFechaAnalisis.getDate()));                
                    modAnalisis.setExeso_grasa(Validaciones.isNumVoid2(visAnalisis.txtExcesoGrasa.getText()));
                    modAnalisis.setExeso_liquido(Validaciones.isNumVoid2(visAnalisis.txtExcesoLiquido.getText()));
                    modAnalisis.setExeso_total(Validaciones.isNumVoid2(visAnalisis.txtExcesoTotal.getText()));
                    modAnalisis.setRecomendacion_pesas(visAnalisis.txtRecomPesas.getText().toUpperCase());
                    modAnalisis.setRecomendacion_cardio(visAnalisis.txtRecomCardio.getText().toUpperCase());
                    modAnalisis.setRecomendacion_funcional(visAnalisis.txtRecomFuncional.getText().toUpperCase());
                    modAnalisis.setEstado(1);
 
                    if (consAnalisis.registrar(modAnalisis)) {
                        JOptionPane.showMessageDialog(null, "Registro Guardado!");
                        visAnalisis.txt_id_analisis_u.setText(consAnalisis.getLastId()+"");
                        limpiar();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error al Guardar");
                        limpiar();
                    }
                    showTable();
               }        
        }
       
       if (e.getSource() == visAnalisis.btnModificarAnalisis) 
       {
            modAnalisis.setId(Integer.parseInt(visAnalisis.txt_id_analisis.getText()));
            modAnalisis.setFecha(Validaciones.setFormatFecha(visAnalisis.dtcFechaAnalisis.getDate()));                
            modAnalisis.setExeso_grasa(Validaciones.isNumVoid2(visAnalisis.txtExcesoGrasa.getText()));
            modAnalisis.setExeso_liquido(Validaciones.isNumVoid2(visAnalisis.txtExcesoLiquido.getText()));
            modAnalisis.setExeso_total(Validaciones.isNumVoid2(visAnalisis.txtExcesoTotal.getText()));
            modAnalisis.setRecomendacion_pesas(String.valueOf(visAnalisis.txtRecomPesas.getText().toUpperCase()));
            modAnalisis.setRecomendacion_cardio(String.valueOf(visAnalisis.txtRecomCardio.getText().toUpperCase()));
            modAnalisis.setRecomendacion_funcional(String.valueOf(visAnalisis.txtRecomFuncional.getText()));

            if (consAnalisis.modificar(modAnalisis)) {
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
       
       if (e.getSource() == visAnalisis.btnEliminarAnalisis) 
       {
            modAnalisis.setId(Integer.parseInt(visAnalisis.txt_id_analisis.getText()));
            modAnalisis.setEstado(0);
            int o= JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar el registro?", "Confirmar eliminar?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);            
            if (o ==0) 
            {           
                if (consAnalisis.eliminar(modAnalisis)) {
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
        }
       
       if (e.getSource() == visAnalisis.btnBuscarAnalisis) 
         {
          ArrayList<JDateChooser> dtcFecha = new ArrayList<>();
          dtcFecha.add(visAnalisis.dtcFechaAnalisis);
             if (Validaciones.isDateChooserVoid(dtcFecha)) {
                  modAnalisis.setFecha(Validaciones.setFormatFecha(visAnalisis.dtcFechaAnalisis.getDate()));
                    
                if (consAnalisis.buscar(modAnalisis)){    
                    
                    visAnalisis.txt_id_analisis.setText(String.valueOf(modAnalisis.getId()));
                    visAnalisis.dtcFechaAnalisis.setDate(Validaciones.setStringToDate(modAnalisis.getFecha()));  
                    visAnalisis.txtExcesoGrasa.setText(String.valueOf(modAnalisis.getExeso_grasa()));
                    visAnalisis.txtExcesoLiquido.setText(String.valueOf(modAnalisis.getExeso_liquido()));
                    visAnalisis.txtExcesoTotal.setText(String.valueOf(modAnalisis.getExeso_total()));
                    visAnalisis.txtRecomPesas.setText(String.valueOf(modAnalisis.getRecomendacion_pesas()).toUpperCase());
                    visAnalisis.txtRecomCardio.setText(String.valueOf(modAnalisis.getRecomendacion_cardio()).toUpperCase());     
                    visAnalisis.txtRecomFuncional.setText(String.valueOf(modAnalisis.getRecomendacion_funcional()).toUpperCase());       
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "No se encotro registro");
                    limpiar();
                }
                showTable();
            }
 
        }
       if (e.getSource() == visAnalisis.btnLimpiarAnalisis) 
        {
           limpiar();
           desabilitaHabilita(visAnalisis.btnGuardarAnalisis,true);
           desabilitaHabilita(visAnalisis.btnModificarAnalisis,false);
           
           visAnalisis.tabp_ficha.setEnabledAt(1, true);
           visAnalisis.tabp_ficha.setEnabledAt(2, false);
        }
    }
    
    
}
