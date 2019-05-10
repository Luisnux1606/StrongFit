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
import consultas.ConsFacturaCab;
import consultas.ConsMembresias;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.Analisis;
import modelos.FacturaCab;
import modelos.Ficha;
import modelos.Medidas;
import modelos.Membresias;
import modelos.Persona;
import vistas.VisFicha;
import vistas.VisMembresia;
import vistas.VisReportes;

/**
 *
 * @author Administrator
 */
public class CtrlFacturaCab implements ActionListener{

    FacturaCab modFicha;
    ArrayList<FacturaCab> lstFicha;
    ConsFacturaCab consFicha;
    VisFicha visFicha;
    VisReportes visReportes;
    VisMembresia visMemb;
    
    
    Persona persona;
    Analisis analisis;
    Medidas medidas;
    
    String cadBus;
    
    public CtrlFacturaCab(FacturaCab modFicha,ConsFacturaCab consFicha,VisFicha visFicha,VisMembresia visMemb,Persona persona)
    {
        this.modFicha = modFicha;
        this.consFicha = consFicha;
        this.visFicha = visFicha;
        this.persona = persona;
        this.visMemb =  visMemb;
        
       // persona = new Persona();
        analisis = new Analisis();
        medidas =  new Medidas();
        
        this.visFicha.btnGuardarFicha.addActionListener(this);
        this.visFicha.btnEliminarFicha.addActionListener(this);
        this.visFicha.btnLimpiarFicha.addActionListener(this);
        this.visFicha.btnModificarFicha.addActionListener(this);
        this.visFicha.btnBuscarDscto.addActionListener(this);
        this.visFicha.btnCalcular.addActionListener(this);
       // this.visFicha.cmbTipoBusqueda.addActionListener(this);
        
         
              
        cadBus = "";
       
        setFocus();
        setListener();    
        setTableModel();
       // iniciar();
        
        visFicha.txt_id_persona_u.setText(persona.getId()+"");
        
        limpiarTabla();
        showTable();
    }
    
    
    
    public ArrayList<String> getAnonimos()
    {        
        ArrayList<String> listFicha = consFicha.buscarAnonyms();
        return listFicha;
    }
    
    public void iniciar()
    {
        visFicha.setTitle("FICHA");
        
        visFicha.dtcFechaIniFicha.setDate(Calculos.getCurrentDate2());     
        visFicha.txt_id_Ficha.setVisible(false);
        visFicha.txt_id_analisis.setVisible(false);
        visFicha.txt_id_datos.setVisible(false);
        visFicha.txt_id_persona_u.setVisible(false);
        visFicha.txt_id_medidas_u.setVisible(false);
        visFicha.txt_id_analisis_u.setVisible(false);

        visFicha.lbl_personaFicha.setText("");

        visFicha.lbl_personaFicha.setText("");

 
        visFicha.btnGuardarFicha.setToolTipText("Guardar el registro");
        visFicha.btnModificarFicha.setToolTipText("Modificar el registro");
        visFicha.btnEliminarFicha.setToolTipText("Eliminar el registro");
        visFicha.btnLimpiarFicha.setToolTipText("Limpiar el registro");
        //visFicha.tabp_ficha.setSelectedIndex(2);
        limpiar();
        
    
        visFicha.setLocation(400,100); 
        visFicha.setSize(1400,800);                
        visFicha.setVisible(true);
    
        
    }
    
    public void setCmbxMembresias()
    {
        
    }
    
    public void setTableModel()
    {            
       Color rojo = new Color(254,000,000);  
       Color amarillo = new Color(255,255,000);
       visFicha.tblFicha.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
              /*
                
                if(Calculos.getDiffDaysToFinish(table.getValueAt(row, 4)+"")<=5 &&Calculos.getDiffDaysToFinish(table.getValueAt(row, 4)+"")>=0)
                {
                     c.setBackground(amarillo); //proximos a terminarse
                }
                else
                    if (Calculos.dateGreaterThanCurrent(table.getValueAt(row, 4)+"")==true) {
                     c.setBackground(rojo);
                    }
                if(Double.parseDouble(table.getValueAt(row, 7)+"")!=0 )
                {
                 c.setBackground(rojo); //proximos a terminarse o pendientes de pago
                }
               
                */
                
                return c;
            }
            
            
            });
     
    }
    
    public void showTableByNom(String nom)
    {
        try {
            limpiarTabla();
            
            ResultSet listFicha = consFicha.buscarTodosPorNomTabla(nom);
            
            DefaultTableModel model =  (DefaultTableModel)visFicha.tblFicha.getModel();
            Object cols[] = new Object[8];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("id_ficha");
                    cols[1] = listFicha.getString("ced_per");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = listFicha.getString("fechaIni_ficha");
                    cols[4] = listFicha.getString("fechaFin_ficha");
                    cols[5] = Validaciones.isNumVoid4(listFicha.getString("concepto_ficha")).toUpperCase();
                    cols[6] = listFicha.getDouble("valPago_ficha");
                    cols[7] = listFicha.getDouble("valPendiente_ficha");
                    model.addRow(cols);
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consFicha.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public void desabilitaHabilita(JButton btn,boolean estado)
     {
         btn.setEnabled(estado);
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
            showTableByNom(cadBus);
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        //visFicha.txtBuscarFechaFicha.addKeyListener(keyListenertxtBuscarFecha);

        
        //**********listener enter ************
        
        KeyListener keyListenertxtValCacncelo=new KeyListener() {
          public void keyPressed(KeyEvent keyEvent) {
            printIt("Pressed", keyEvent);
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
            int m=e.getKeyChar();
              if (m == KeyEvent.VK_ENTER) {
                  
                  double dif = Calculos.getDiferencia(new Double(visFicha.txtValConDsctoFicha.getText()).doubleValue(),new Double(visFicha.txt_valCancelo.getText()).doubleValue());
                  visFicha.txtValPendienteFicha.setText(dif+"");
                  visFicha.txtValPendienteFicha.requestFocusInWindow();
              }  
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        visFicha.txt_valCancelo.addKeyListener(keyListenertxtValCacncelo);
        
        //**********listener txtValPendienteFicha ************
        
        KeyListener keyListenertxtValPendieteFicha=new KeyListener() {
          public void keyPressed(KeyEvent keyEvent) {
            printIt("Pressed", keyEvent);
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
            int m=e.getKeyChar();
              if (m == KeyEvent.VK_ENTER) {
                  
                 
                  visFicha.txtConceptoFicha.requestFocusInWindow();
              }  
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        visFicha.txtValPendienteFicha.addKeyListener(keyListenertxtValPendieteFicha);
        
        //**********listener txtValCancelo ************
        
        KeyListener keyListenertxtValPagar=new KeyListener() {
          public void keyPressed(KeyEvent keyEvent) {
            printIt("Pressed", keyEvent);
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
            int m=e.getKeyChar();
              if (m == KeyEvent.VK_ENTER || m == KeyEvent.VK_TAB) {
                                  
                 visFicha.txtValDscto.requestFocusInWindow();
           
              }  
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        visFicha.txtValPagar.addKeyListener(keyListenertxtValPagar);
        
         //**********listener txtValDscto ************
        
        KeyListener keyListenertxtValDscto=new KeyListener() {
          public void keyPressed(KeyEvent keyEvent) {
            printIt("Pressed", keyEvent);
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
            int m=e.getKeyChar();
              if (m == KeyEvent.VK_ENTER || m == KeyEvent.VK_TAB) {
                                  
                 visFicha.txt_valCancelo.requestFocusInWindow();
                 double  dsctoMemb = (Validaciones.isNumVoid10(visFicha.txtValDscto.getText()));
                 double valMasDscto = Calculos.getDscto(new Double(visFicha.txtValPagar.getText()).doubleValue(), dsctoMemb);
                 visFicha.txtValConDsctoFicha.setText(valMasDscto+"");
              }  
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        visFicha.txtValDscto.addKeyListener(keyListenertxtValDscto);
        
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
        
        MouseListener mouseListTblFicha = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                    getTableToTxts();
                     desabilitaHabilita(visFicha.btnGuardarFicha,false);
                     desabilitaHabilita(visFicha.btnModificarFicha,true);
                                          
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
       
        visFicha.tblFicha.addMouseListener(mouseListTblFicha);
      
    }
     public void getTableToTxts()
     {
         JTable tblD = visFicha.tblFicha;
         visFicha.txt_id_Ficha.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));
         visFicha.dtcFechaIniFicha.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 3))));
         visFicha.dtcFechaFinFicha.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 4))));
         visFicha.txtConceptoFicha.setText(Validaciones.isNumVoid4(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 5))).toUpperCase());
         visFicha.txtValConDsctoFicha.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 6)));
         visFicha.txtValPendienteFicha.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 7)));
         
     }
    
     public void setFocus()
    {
        visFicha.dtcFechaIniFicha.requestFocus();
        visFicha.dtcFechaIniFicha.setNextFocusableComponent(visFicha.dtcFechaFinFicha);
        visFicha.dtcFechaFinFicha.setNextFocusableComponent(visFicha.txtValConDsctoFicha);
        visFicha.txtValConDsctoFicha.setNextFocusableComponent(visFicha.txtValPendienteFicha);    
        visFicha.txtValPendienteFicha.setNextFocusableComponent(visFicha.txtConceptoFicha); 
    }
     public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visFicha.tblFicha.getModel();
        int a = visFicha.tblFicha.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
    
    public void showTable()
    {
        try {
            limpiarTabla();
            ResultSet listFicha = consFicha.buscarTodos2();
            
            DefaultTableModel model =  (DefaultTableModel)visFicha.tblFicha.getModel();
            Object cols[] = new Object[8];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("id_ficha");
                    cols[1] = listFicha.getString("ced_per");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = listFicha.getString("fechaIni_ficha");
                    cols[4] = listFicha.getString("fechaFin_ficha");
                    cols[5] = Validaciones.isNumVoid4(listFicha.getString("concepto_ficha")).toUpperCase();
                    cols[6] = listFicha.getDouble("valPago_ficha");
                    cols[7] = listFicha.getDouble("valPendiente_ficha");
                    model.addRow(cols);
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consFicha.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visFicha.tblFicha.updateUI();
    }
    
    public void showTablePendientes()
    {
        try {
            limpiarTabla();
            ResultSet listFicha = consFicha.buscarPendientes();
            
            DefaultTableModel model =  (DefaultTableModel)visFicha.tblFicha.getModel();
            Object cols[] = new Object[8];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("id_ficha");
                    cols[1] = listFicha.getString("ced_per");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = listFicha.getString("fechaIni_ficha");
                    cols[4] = listFicha.getString("fechaFin_ficha");
                    cols[5] = Validaciones.isNumVoid4(listFicha.getString("concepto_ficha")).toUpperCase();
                    cols[6] = listFicha.getDouble("valPago_ficha");
                    cols[7] = listFicha.getDouble("valPendiente_ficha");
                    model.addRow(cols);
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consFicha.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visFicha.tblFicha.updateUI();
    }
    
    public void showTableProximosVencer()
    {
        try {
            limpiarTabla();
            ResultSet listFicha = consFicha.buscarTodos2();
            
            DefaultTableModel model =  (DefaultTableModel)visFicha.tblFicha.getModel();
            Object cols[] = new Object[8];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("id_ficha");
                    cols[1] = listFicha.getString("ced_per");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = listFicha.getString("fechaIni_ficha");
                    cols[4] = listFicha.getString("fechaFin_ficha");
                    cols[5] = Validaciones.isNumVoid4(listFicha.getString("concepto_ficha")).toUpperCase();
                    cols[6] = listFicha.getDouble("valPago_ficha");
                    cols[7] = listFicha.getDouble("valPendiente_ficha");
                    if (Calculos.dateGreaterThanCurrent(cols[4].toString())) {
                        double days = Calculos.getDiffDaysToFinish(cols[4].toString());
                        if (days<=5) {
                            model.addRow(cols);
                        }
                    }                       
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consFicha.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visFicha.tblFicha.updateUI();
    }
    
     public void showTableCursando()
    {
        try {
            limpiarTabla();
            ResultSet listFicha = consFicha.buscarTodos2();
            
            DefaultTableModel model =  (DefaultTableModel)visFicha.tblFicha.getModel();
            Object cols[] = new Object[8];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("id_ficha");
                    cols[1] = listFicha.getString("ced_per");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = listFicha.getString("fechaIni_ficha");
                    cols[4] = listFicha.getString("fechaFin_ficha");
                    cols[5] = Validaciones.isNumVoid4(listFicha.getString("concepto_ficha")).toUpperCase();
                    cols[6] = listFicha.getDouble("valPago_ficha");
                    cols[7] = listFicha.getDouble("valPendiente_ficha");
                    if (Calculos.dateGreaterThanCurrent(cols[4].toString())) {
                        model.addRow(cols);                     
                    }                       
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consFicha.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visFicha.tblFicha.updateUI();
    }
    
     public void showTableVencidos()
    {
        try {
            limpiarTabla();
            ResultSet listFicha = consFicha.buscarTodos2();
            
            DefaultTableModel model =  (DefaultTableModel)visFicha.tblFicha.getModel();
            Object cols[] = new Object[8];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("id_ficha");
                    cols[1] = listFicha.getString("ced_per");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = listFicha.getString("fechaIni_ficha");
                    cols[4] = listFicha.getString("fechaFin_ficha");
                    cols[5] = Validaciones.isNumVoid4(listFicha.getString("concepto_ficha")).toUpperCase();
                    cols[6] = listFicha.getDouble("valPago_ficha");
                    cols[7] = listFicha.getDouble("valPendiente_ficha");
                    if (!Calculos.dateGreaterThanCurrent(cols[4].toString()))
                        model.addRow(cols);
                     
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consFicha.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visFicha.tblFicha.updateUI();
    }
    
    public void validaAnonimos()
    {
        
       // System.out.println("personar: "+getAnonimos().get(0)+" anal: "+getAnonimos().get(1)+" med: "+getAnonimos().get(2));
        if(Validaciones.isNumVoid1(visFicha.txt_id_persona_u.getText()))
        {
           persona.setId(Integer.parseInt(getAnonimos().get(0)));
           modFicha.setPersona(persona); 
        }
        else
        {
            persona.setId(Integer.parseInt(visFicha.txt_id_persona_u.getText()));
            modFicha.setPersona(persona); 
        }
        ///*******
        if(Validaciones.isNumVoid1(visFicha.txt_id_analisis_u.getText()))
        {
            analisis.setId(Integer.parseInt(getAnonimos().get(1)));
            modFicha.setAnalisis(analisis);
        }
        else
        {
            analisis.setId(Integer.parseInt(visFicha.txt_id_analisis_u.getText()));
            modFicha.setAnalisis(analisis);
        }
        //*********
        if(Validaciones.isNumVoid1(visFicha.txt_id_medidas_u.getText()))
        {
            medidas.setId(Integer.parseInt(getAnonimos().get(2)));
            modFicha.setMedidas(medidas);
        }
        else
        {
           medidas.setId(Integer.parseInt(visFicha.txt_id_medidas_u.getText()));
           modFicha.setMedidas(medidas);
        }
        System.out.println("personar: "+persona.getId()+" anal: "+analisis.getId()+" med: "+medidas.getId());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == visFicha.btnGuardarFicha) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
           jdc.add(visFicha.dtcFechaIniFicha);
           
               if (Validaciones.isDateChooserVoid(jdc)) 
               {                   
                    modFicha.setFecha_ini(Validaciones.setFormatFecha(visFicha.dtcFechaIniFicha.getDate()));                
                    modFicha.setFecha_fin(Validaciones.setFormatFecha(visFicha.dtcFechaFinFicha.getDate()));
                    modFicha.setVal_pago(Validaciones.isNumVoid3(visFicha.txtValConDsctoFicha.getText()));
                    modFicha.setVal_pendiente(Validaciones.isNumVoid3(visFicha.txtValPendienteFicha.getText()));
                    modFicha.setConcepto(visFicha.txtConceptoFicha.getText().toUpperCase());
                    modFicha.setEstado(1);
                    //valido > si es nullo pongo anon, llenoen mod i guardo
                    validaAnonimos();

                    if (consFicha.registrar(modFicha)) {
                        JOptionPane.showMessageDialog(null, "Registro Guardado!");
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
      
      if (e.getSource() == visFicha.btnModificarFicha) 
       {            
            modFicha.setId(Integer.parseInt(visFicha.txt_id_Ficha.getText()));
            modFicha.setFecha_ini(Validaciones.setFormatFecha(visFicha.dtcFechaIniFicha.getDate()));                
            modFicha.setFecha_fin(Validaciones.setFormatFecha(visFicha.dtcFechaFinFicha.getDate()));
            modFicha.setVal_pago(Validaciones.isNumVoid3(visFicha.txtValConDsctoFicha.getText()));
            modFicha.setVal_pendiente(Validaciones.isNumVoid3(visFicha.txtValPendienteFicha.getText()));
            modFicha.setConcepto(visFicha.txtConceptoFicha.getText().toUpperCase());

//            validaAnonimos();
            if (consFicha.modificar(modFicha)) {
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
      
      if (e.getSource() == visFicha.btnEliminarFicha) 
       {
           
            modFicha.setId(Integer.parseInt(visFicha.txt_id_Ficha.getText()));
            modFicha.setEstado(0);
                      
            if (consFicha.eliminar(modFicha)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado !");
                limpiar();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al Eliminar...");
                limpiar();
            }
            showTable();
        }
      
       if (e.getSource() == visFicha.btnLimpiarFicha) 
        {
           limpiar();
           desabilitaHabilita(visFicha.btnGuardarFicha,true);
           desabilitaHabilita(visFicha.btnModificarFicha,false);
        }

        if (e.getSource() == visFicha.btnBuscarDscto) 
        {
            VisMembresia visMem = new VisMembresia();
            Membresias memMod  = new Membresias();
            ConsMembresias consMem = new ConsMembresias();
            Ficha ficha  =  new Ficha();                        
            CtrlMembresias ctrMemb=new CtrlMembresias(memMod,consMem,visMem,ficha,visFicha);
        } 
        
        if (e.getSource() == visFicha.btnCalcular) 
        {       
            if (Validaciones.isVoidJTxt(visFicha.txt_valEntregado)) {
                double txtValEntregado = Double.parseDouble(visFicha.txt_valEntregado.getText());
                double txtValCancelo = Double.parseDouble(visFicha.txt_valCancelo.getText());
                double txtCambio = txtValEntregado -txtValCancelo ;
                visFicha.txt_cambio.setText(Calculos.setTwoDecimals(txtCambio)+"");
               }
        }
        /*
         if (e.getSource() == visFicha.cmbTipoBusqueda) 
        {       
           String tipo = visFicha.cmbTipoBusqueda.getSelectedItem()+"";
            if (tipo.equals("todos")) {
             showTable();
            }
            if (tipo.equals("cursando")) {
                showTableCursando();
            }
            if (tipo.equals("pendientes")) {
                showTablePendientes();
            }
            if (tipo.equals("proximos a vencer")) {
                showTableProximosVencer();
            }
            if (tipo.equals("vencidos")) {
                showTableVencidos();
            }
        }*/
      
    }
    public void limpiar()
    {
        visFicha.dtcFechaIniFicha.setDate(Calculos.getCurrentDate2()); 
        visFicha.txtValConDsctoFicha.setText("0.0");
        visFicha.txtValPendienteFicha.setText("0.0");
        visFicha.txtValPagar.setText("0.0");
        visFicha.txtValDscto.setText("0.0");
        visFicha.txt_valCancelo.setText("0.0");
        visFicha.txtConceptoFicha.setText("");

    }
    
}
