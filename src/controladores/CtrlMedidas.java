
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import consultas.ConsAnalisis;
import consultas.ConsFacturaCab;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.Analisis;
import modelos.FacturaCab;
import modelos.Medidas;
import modelos.Persona;
import vistas.VisFicha;
import vistas.VisPersona;

/**
 *
 * @author Administrator
 */
public class CtrlMedidas implements ActionListener{
    
    Medidas modMedidas;
    ArrayList<Medidas> lstMedidas;
    ConsMedidas consMedidas;
    VisFicha visMedidas;
    Persona persona;
            
    int lastIdMedidas;
    String cadBus;
    
    public CtrlMedidas(Medidas modMedidas, ConsMedidas consMedidas, VisFicha visMedidas)
    {
        this.modMedidas = modMedidas;
        this.consMedidas = consMedidas;
        this.visMedidas = visMedidas;
      
        
        this.visMedidas.btnGuardar.addActionListener(this);
        this.visMedidas.btnEliminar.addActionListener(this);
        this.visMedidas.btnLimpiar.addActionListener(this);
        this.visMedidas.btnModificar.addActionListener(this);
        this.visMedidas.btnBuscarMed.addActionListener(this);
        cadBus = "";
        showTable();
        setFocus();
        setListener();    
        setTableModel();
        iniciar();
        
        lastIdMedidas = 0;
        setTableModel();
    }
    
    public void setTableModel()
    {
      
      visMedidas.tblDatos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
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
    public void desabilitaHabilita(JButton btn,boolean estado)
     {
         btn.setEnabled(estado);
     }
    
    public void showTableByFecha(String fecha)
    {
        limpiarTabla();                            
           ArrayList<Medidas> listMed = consMedidas.buscarTodosPorFec(modMedidas,fecha);
           DefaultTableModel model =  (DefaultTableModel)visMedidas.tblDatos.getModel();
           Object cols[] = new Object[19];

           for (int i = 0; i < listMed.size(); i++) {
               cols[0] = listMed.get(i).getId();
               cols[1] = listMed.get(i).getFecha();
               cols[2] = listMed.get(i).getPeso();
               cols[3] = listMed.get(i).getEstatura();
               cols[4] = listMed.get(i).getNro_hijos();
               cols[5] = listMed.get(i).getPecho();
               cols[6] = listMed.get(i).getAbdomen_alto();
               cols[7] = listMed.get(i).getCintura();
               cols[8] = listMed.get(i).getAbdomen_bajo();
               cols[9] = listMed.get(i).getCadera();
               cols[10] = listMed.get(i).getPiernas();
               cols[11] = listMed.get(i).getPantorrilla();
               cols[12] = listMed.get(i).getBrazo();
               cols[13] = listMed.get(i).getAntebrazo();
               cols[14] = listMed.get(i).getCuello();
               cols[15] = listMed.get(i).getEspalda();
               cols[16] = listMed.get(i).getPorcentaje_grasa();
               cols[17] = listMed.get(i).getPorcentaje_kgs();


               model.addRow(cols);                    
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
           // System.out.println(title + " : " + keyText + " / " + keyEvent.getKeyChar());
          }
        };
        visMedidas.txtBuscarFecha.addKeyListener(keyListenertxtBuscarFecha);
         /////TBLDATOS
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
        
        MouseListener mouseListTblPersonas = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                     getTableToTxts();
                     desabilitaHabilita(visMedidas.btnGuardar,false);
                     desabilitaHabilita(visMedidas.btnModificar,true);
                     
                     //visMedidas.tabp_ficha.setEnabledAt(1, true);
                     //visMedidas.tabp_ficha.setEnabledAt(2, false);
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
       
        visMedidas.tblDatos.addMouseListener(mouseListTblPersonas);
      
    }
     
     public void getTableToTxts()
     {
          JTable tblD = visMedidas.tblDatos;
      
        
        //  modMedidas.setId(Integer.parseInt(visMedidas.txt_id_datos.getText()));
         visMedidas.txt_id_datos.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),0)));
         visMedidas.dtcFecha.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),1))));
         visMedidas.txtPeso.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),2)));
         visMedidas.txtEstatura.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),3)));      
         visMedidas.txtNroHijos.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),4)));
         visMedidas.txtPecho.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),5)));
         visMedidas.txtAbdomAlto.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),6)));
         visMedidas.txtCintura.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),7)));
         visMedidas.txtAbdomBajo.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),8)));
         visMedidas.txtCadera.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),9)));
         visMedidas.txtPierna.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),10)));
         visMedidas.txtPantorrilla.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),11)));
         visMedidas.txtBrazo.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),12)));
         visMedidas.txtAntebrazo.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),13)));
         visMedidas.txtCuello.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),14)));
         visMedidas.txtEspalda.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),15)));
         visMedidas.txtPorGrasa.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),16)));
         visMedidas.txtPorKilogs.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(),17)));
         
     }
    
    public void setFocus()
    {
        visMedidas.dtcFecha.requestFocus();
        visMedidas.dtcFecha.setNextFocusableComponent(visMedidas.txtPeso);
        visMedidas.txtPeso.setNextFocusableComponent(visMedidas.txtEstatura);  
        visMedidas.txtNroHijos.setNextFocusableComponent(visMedidas.txtPecho); 
        visMedidas.txtPecho.setNextFocusableComponent(visMedidas.txtAbdomAlto); 
        visMedidas.txtAbdomAlto.setNextFocusableComponent(visMedidas.txtCintura); 
        visMedidas.txtCintura.setNextFocusableComponent(visMedidas.txtAbdomBajo);
        visMedidas.txtAbdomBajo.setNextFocusableComponent(visMedidas.txtCadera);
        visMedidas.txtCadera.setNextFocusableComponent(visMedidas.txtPierna);
        visMedidas.txtPierna.setNextFocusableComponent(visMedidas.txtPantorrilla);
        visMedidas.txtPantorrilla.setNextFocusableComponent(visMedidas.txtBrazo);
        visMedidas.txtBrazo.setNextFocusableComponent(visMedidas.txtAntebrazo);
        visMedidas.txtAntebrazo.setNextFocusableComponent(visMedidas.txtCuello);
        visMedidas.txtCuello.setNextFocusableComponent(visMedidas.txtEspalda);
        visMedidas.txtEspalda.setNextFocusableComponent(visMedidas.txtPorGrasa);
        visMedidas.txtPorGrasa.setNextFocusableComponent(visMedidas.txtPorKilogs);
    }
    public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visMedidas.tblDatos.getModel();
        int a = visMedidas.tblDatos.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
    public void showTable()
    {
        limpiarTabla();                  
        //persona.setId(visMedidas.txt_id_persona_u);
           ArrayList<Medidas> listMed = consMedidas.buscarTodos(modMedidas);
           DefaultTableModel model =  (DefaultTableModel)visMedidas.tblDatos.getModel();
           Object cols[] = new Object[19];

           for (int i = 0; i < listMed.size(); i++) {
               cols[0] = listMed.get(i).getId();
               cols[1] = listMed.get(i).getFecha();
               cols[2] = listMed.get(i).getPeso();
               cols[3] = listMed.get(i).getEstatura();
               cols[4] = listMed.get(i).getNro_hijos();
               cols[5] = listMed.get(i).getPecho();
               cols[6] = listMed.get(i).getAbdomen_alto();
               cols[7] = listMed.get(i).getCintura();
               cols[8] = listMed.get(i).getAbdomen_bajo();
               cols[9] = listMed.get(i).getCadera();
               cols[10] = listMed.get(i).getPiernas();
               cols[11] = listMed.get(i).getPantorrilla();
               cols[12] = listMed.get(i).getBrazo();
               cols[13] = listMed.get(i).getAntebrazo();
               cols[14] = listMed.get(i).getCuello();
               cols[15] = listMed.get(i).getEspalda();
               cols[16] = listMed.get(i).getPorcentaje_grasa();
               cols[17] = listMed.get(i).getPorcentaje_kgs();        

               model.addRow(cols);                    
           }   
    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == visMedidas.btnGuardar) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
           jdc.add(visMedidas.dtcFecha);
           
               if (Validaciones.isDateChooserVoid(jdc)) 
               {
                    modMedidas.setFecha(Validaciones.setFormatFecha(visMedidas.dtcFecha.getDate()));                
                    modMedidas.setPeso(Validaciones.isNumVoid3(visMedidas.txtPeso.getText()));
                    modMedidas.setEstatura(Validaciones.isNumVoid3(visMedidas.txtEstatura.getText()));
                    modMedidas.setNro_hijos(Validaciones.isNumVoid(visMedidas.txtNroHijos.getText()));
                    modMedidas.setPecho(Validaciones.isNumVoid3(visMedidas.txtPecho.getText()));
                    modMedidas.setAbdomen_alto(Validaciones.isNumVoid3(visMedidas.txtAbdomAlto.getText()));
                    modMedidas.setCintura(Validaciones.isNumVoid3(visMedidas.txtCintura.getText()));
                    modMedidas.setAbdomen_bajo(Validaciones.isNumVoid3(visMedidas.txtAbdomBajo.getText()));
                    modMedidas.setCadera(Validaciones.isNumVoid3(visMedidas.txtCadera.getText()));
                    modMedidas.setPiernas(Validaciones.isNumVoid3(visMedidas.txtPierna.getText()));
                    modMedidas.setPantorrilla(Validaciones.isNumVoid3(visMedidas.txtPantorrilla.getText()));
                    modMedidas.setBrazo(Validaciones.isNumVoid3(visMedidas.txtBrazo.getText()));
                    modMedidas.setAntebrazo(Validaciones.isNumVoid3(visMedidas.txtAntebrazo.getText()));
                    modMedidas.setCuello(Validaciones.isNumVoid3(visMedidas.txtCuello.getText()));
                    modMedidas.setEspalda(Validaciones.isNumVoid3(visMedidas.txtEspalda.getText()));
                    modMedidas.setPorcentaje_grasa(Validaciones.isNumVoid3(visMedidas.txtPorGrasa.getText()));
                    modMedidas.setPorcentaje_kgs(Validaciones.isNumVoid3(visMedidas.txtPorKilogs.getText()));
                    modMedidas.setEstado(1);

                    if (consMedidas.registrar(modMedidas)) {
                        JOptionPane.showMessageDialog(null, "Registro Guardado!");
                        visMedidas.txt_id_medidas_u.setText(consMedidas.getLastId()+"");
                        
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
       
       if (e.getSource() == visMedidas.btnModificar) 
       {
            modMedidas.setId(Integer.parseInt(visMedidas.txt_id_datos.getText()));
            modMedidas.setFecha(Validaciones.setFormatFecha(visMedidas.dtcFecha.getDate()));                
            modMedidas.setPeso(Validaciones.isNumVoid2(visMedidas.txtPeso.getText()));
            modMedidas.setEstatura(Validaciones.isNumVoid2(visMedidas.txtEstatura.getText()));
            modMedidas.setNro_hijos(Validaciones.isNumVoid(visMedidas.txtNroHijos.getText()));
            modMedidas.setPecho(Validaciones.isNumVoid2(visMedidas.txtPecho.getText()));
            modMedidas.setAbdomen_alto(Validaciones.isNumVoid2(visMedidas.txtAbdomAlto.getText()));
            modMedidas.setCintura(Validaciones.isNumVoid2(visMedidas.txtCintura.getText()));
            modMedidas.setAbdomen_bajo(Validaciones.isNumVoid2(visMedidas.txtAbdomBajo.getText()));
            modMedidas.setCadera(Validaciones.isNumVoid2(visMedidas.txtCadera.getText()));
            modMedidas.setPiernas(Validaciones.isNumVoid2(visMedidas.txtPierna.getText()));
            modMedidas.setPantorrilla(Validaciones.isNumVoid2(visMedidas.txtPantorrilla.getText()));
            modMedidas.setBrazo(Validaciones.isNumVoid2(visMedidas.txtBrazo.getText()));
            modMedidas.setAntebrazo(Validaciones.isNumVoid2(visMedidas.txtAntebrazo.getText()));
            modMedidas.setCuello(Validaciones.isNumVoid2(visMedidas.txtCuello.getText()));
            modMedidas.setEspalda(Validaciones.isNumVoid2(visMedidas.txtEspalda.getText()));
            modMedidas.setPorcentaje_grasa(Validaciones.isNumVoid2(visMedidas.txtPorGrasa.getText()));
            modMedidas.setPorcentaje_kgs(Validaciones.isNumVoid2(visMedidas.txtPorKilogs.getText()));
            
            if (consMedidas.modificar(modMedidas)) {
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
       
       if (e.getSource() == visMedidas.btnEliminar) 
       {
           if (Validaciones.isNumVoid1(visMedidas.txt_id_datos.getText())) {
               Validaciones.getMensaje("Debe seleccionar un dato de la tabla.");
           }
           else
           {
                modMedidas.setId(Integer.parseInt(visMedidas.txt_id_datos.getText()));
                modMedidas.setEstado(0);
                if (consMedidas.eliminar(modMedidas)) {
                    JOptionPane.showMessageDialog(null, "Registro Eliminado !");
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
       
       if (e.getSource() == visMedidas.btnBuscarMed) 
         {
          ArrayList<JDateChooser> dtcFecha = new ArrayList<>();
          dtcFecha.add(visMedidas.dtcFecha);
             if (Validaciones.isDateChooserVoid(dtcFecha)) {
                  modMedidas.setFecha(Validaciones.setFormatFecha(visMedidas.dtcFecha.getDate()));
                    
                if (consMedidas.buscar(modMedidas)){    
                    visMedidas.txt_id_datos.setText(String.valueOf(modMedidas.getId()));
                    visMedidas.dtcFecha.setDate(Validaciones.setStringToDate(modMedidas.getFecha()));  
                    visMedidas.txtPeso.setText(String.valueOf(modMedidas.getPeso()));
                    visMedidas.txtEstatura.setText(String.valueOf(modMedidas.getEstatura()));
                    visMedidas.txtNroHijos.setText(String.valueOf(modMedidas.getNro_hijos()));
                    visMedidas.txtPecho.setText(String.valueOf(modMedidas.getPecho()));
                    visMedidas.txtAbdomAlto.setText(String.valueOf(modMedidas.getAbdomen_alto()));
                    visMedidas.txtCintura.setText(String.valueOf(modMedidas.getCintura()));
                    visMedidas.txtAbdomBajo.setText(String.valueOf(modMedidas.getAbdomen_bajo()));
                    visMedidas.txtCadera.setText(String.valueOf(modMedidas.getCadera()));
                    visMedidas.txtPierna.setText(String.valueOf(modMedidas.getPiernas()));
                    visMedidas.txtPantorrilla.setText(String.valueOf(modMedidas.getPantorrilla()));
                    visMedidas.txtBrazo.setText(String.valueOf(modMedidas.getBrazo()));
                    visMedidas.txtAntebrazo.setText(String.valueOf(modMedidas.getAntebrazo()));
                    visMedidas.txtCuello.setText(String.valueOf(modMedidas.getCuello()));
                    visMedidas.txtEspalda.setText(String.valueOf(modMedidas.getEspalda()));
                    visMedidas.txtPorGrasa.setText(String.valueOf(modMedidas.getPorcentaje_grasa()));
                    visMedidas.txtPorKilogs.setText(String.valueOf(modMedidas.getPorcentaje_kgs()));
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "No se encotro registro");
                    limpiar();
                }
                showTable();
            }            
        }
        if (e.getSource() == visMedidas.btnLimpiar) 
        {
           limpiar();
           desabilitaHabilita(visMedidas.btnGuardar,true);
           desabilitaHabilita(visMedidas.btnModificar,false);
           
           visMedidas.tabp_ficha.setEnabledAt(1, true);
           visMedidas.tabp_ficha.setEnabledAt(2, true);
        }
    }
    
    public void iniciar()
    {
        visMedidas.setTitle("CARACTERISTICAS");
        visMedidas.setLocationRelativeTo(null);
        visMedidas.txt_id_datos.setVisible(false);
        visMedidas.txt_id_medidas_u.setVisible(false);
        visMedidas.txt_id_analisis_u.setVisible(false);
        visMedidas.txt_id_persona_u.setVisible(false);
        
        visMedidas.lbl_fichaDatos.setText("");
              
        visMedidas.btnBuscarMed.setToolTipText("Buscar analisis por fecha");
        visMedidas.btnGuardar.setToolTipText("Guardar el registro");
        visMedidas.btnModificar.setToolTipText("Modificar el registro");
        visMedidas.btnEliminar.setToolTipText("Eliminar el registro");
        visMedidas.btnLimpiar.setToolTipText("Limpiar el registro");
        
        visMedidas.tabp_ficha.setEnabledAt(1, true);
        visMedidas.tabp_ficha.setEnabledAt(2, true);
    }
    public void limpiar()
    {
        
        visMedidas.dtcFecha.setDate(null);                
        visMedidas.txtPeso.setText("");
        visMedidas.txtEstatura.setText("");
        visMedidas.txtNroHijos.setText("");
        visMedidas.txtPecho.setText("");
        visMedidas.txtAbdomAlto.setText("");
        visMedidas.txtCintura.setText("");
        visMedidas.txtAbdomBajo.setText("");
        visMedidas.txtCadera.setText("");
        visMedidas.txtPierna.setText("");
        visMedidas.txtPantorrilla.setText("");
        visMedidas.txtBrazo.setText("");
        visMedidas.txtAntebrazo.setText("");
        visMedidas.txtCuello.setText("");
        visMedidas.txtEspalda.setText("");
        visMedidas.txtPorGrasa.setText("");
        visMedidas.txtPorKilogs.setText("");
  
     
       // limpiarTabla();
    }
    
    
}
