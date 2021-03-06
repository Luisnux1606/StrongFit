/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Calculos;
import assets.Configuracion;
import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import consultas.ConsAnalisis;
import consultas.ConsFacturaCab;
import consultas.ConsFacturaCabCompras;
import consultas.ConsFacturaDet;
import consultas.ConsFacturaDetCompras;
import consultas.ConsHistorialPersonaServicio;
import consultas.ConsMembresias;
import consultas.ConsPersona;
import consultas.ConsProductos;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.Analisis;
import modelos.Categoria;
import modelos.FacturaCab;
import modelos.FacturaCabCompras;
import modelos.FacturaDetalle;
import modelos.FacturaDetalleCompras;
import modelos.Ficha;
import modelos.HistorialPersonaServicio;
import modelos.Iva;
import modelos.Medidas;
import modelos.Membresias;
import modelos.Persona;
import modelos.Producto;

import vistas.VisFicha;
import vistas.VisHistorialPersonaServicio;
import vistas.VisMembresia;
import vistas.VisPersona;
import vistas.VisProductos;
import vistas.VisReportes;


public class CtrlFacturaCabCompras implements ActionListener{

    FacturaCabCompras modFacCabCompras;
    ArrayList<FacturaCabCompras> lstFicha;
    ConsFacturaCabCompras consFicha;
    VisFicha visFicha;
    VisReportes visReportes;
    VisMembresia visMemb;
    Membresias memb;
    Iva iva;
    
    Persona persona;
    String cadBus;
    
     ConsFacturaDetCompras consFacDetCompras ;     
     CtrlFacturaDetalleCompras facDetalleCompras; 
    
    public CtrlFacturaCabCompras(FacturaCabCompras modFicha,ConsFacturaCabCompras consFicha,VisFicha visFicha,VisMembresia visMemb,Persona persona)
    {
        this.modFacCabCompras = modFicha;
        this.consFicha = consFicha;
        this.visFicha = visFicha;
        this.persona = persona;
        this.visMemb =  visMemb;
        this.memb = new Membresias();
        this.iva = new Iva();
              
         consFacDetCompras= new ConsFacturaDetCompras();  
        facDetalleCompras = new CtrlFacturaDetalleCompras(consFacDetCompras, visFicha);
        
        this.visFicha.btnGuardarFacCabCompras.addActionListener(this);
//        this.visFicha.btnEliminarFacCab.addActionListener(this);
        this.visFicha.btnLimpiarFacCabComp.addActionListener(this);
 //       this.visFicha.btnModificarFacCab.addActionListener(this);
        this.visFicha.btnBuscarDsctoCompra.addActionListener(this);
        this.visFicha.btnCalcularCompras.addActionListener(this);
        this.visFicha.btnBuscarClienteFacturaComp.addActionListener(this);
        this.visFicha.btnAgregarFilasComp.addActionListener(this);
        this.visFicha.btnEliminarFilasComp.addActionListener(this);
       

        
         
              
        cadBus = "";
       
        setFocus();
        setListener();    
        setTableModel();
        iniciar();
        
        visFicha.txt_id_persona_u.setText(persona.getId()+"");

    }
    
    
    
    public ArrayList<String> getAnonimos()
    {        
        ArrayList<String> listFicha = consFicha.buscarAnonyms();
        return listFicha;
    }
    
    public void iniciar()
    {
        visFicha.setTitle( Configuracion.nomEmp + "FACTURA CABECERA");
        
        visFicha.dtcFechaFacCab.setDate(Calculos.getCurrentDate2());     
        visFicha.txt_id_FacCab.setVisible(false);
       
        visFicha.btnGuardarFacCab.setToolTipText("Guardar el registro");
//        visFicha.btnModificarFacCab.setToolTipText("Modificar el registro");
//        visFicha.btnEliminarFacCab.setToolTipText("Eliminar el registro");
        visFicha.btnLimpiarFacCab.setToolTipText("Limpiar el registro");
        //visFicha.tabp_ficha.setSelectedIndex(2);
        limpiar();       
    }
    
    public void setCmbxMembresias()
    {
        
    }
    
    public void setTableModel()
    {   /*         
       Color rojo = new Color(254,000,000);  
       Color amarillo = new Color(255,255,000);
       visFicha.tblFicha.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
              
                
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
               
                
                
                return c;
            }
            
            
            });
            */
     
    }
    /*
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
    */
     public void desabilitaHabilita(JButton btn,boolean estado)
     {
         btn.setEnabled(estado);
     }
     
    
    
    public void setListener(){
              
        
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
                  Calculos.setPendienteComp(visFicha);                                    
              }  
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        visFicha.txt_valCanceloComp.addKeyListener(keyListenertxtValCacncelo);
        
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
                  
                 
                //  visFicha.txtConceptoFicha.requestFocusInWindow();
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
                 Calculos.setTotalesCabecera(visFicha.tblFacturaDetalleCompras, visFicha);
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
      
          }


        };
        /*
        MouseListener mouseListTblFicha = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                    getTableToTxts();
                     desabilitaHabilita(visFicha.btnGuardarFacCab,false);
                     desabilitaHabilita(visFicha.btnModificarFacCab,true);
                                          
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
      */
    }
    /*
     public void getTableToTxts()
     {
         JTable tblD = visFicha.tblFicha;
         visFicha.txt_id_FacCab.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));
         visFicha.dtcFechaIniFacCab.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 3))));
         visFicha.dtcFechaFinFicha.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 4))));
         visFicha.txtConceptoFicha.setText(Validaciones.isNumVoid4(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 5))).toUpperCase());
         visFicha.txtValConDsctoFicha.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 6)));
         visFicha.txtValPendienteFicha.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 7)));
         
     }
    */
    
     public void setFocus()
    {
        visFicha.dtcFechaFacCab.requestFocus();
        visFicha.dtcFechaFacCab.setNextFocusableComponent(visFicha.cmb_clienteFac);
       
    }
     
   
    
    /*
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
     */
    /*
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
    */
     /*
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
    */
     /*
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
    */
     /*
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
    */
     /*
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
     
    }
    */
    public void setFacturaCabecera(VisFicha visFich)
    {
         String numFac = visFicha.lblNroFacCompras.getText();
        
        modFacCabCompras.setFecha_facCabComp(Validaciones.setFormatFecha(visFicha.dtcFechaFacCabComp.getDate()));                
        modFacCabCompras.setNum_facCabComp(numFac);

        persona.setId(Validaciones.isNumVoid(visFicha.lblPersonaIdComp.getText()));
        modFacCabCompras.setPersonaComp(persona);

        modFacCabCompras.setValPagar_facCabComp(Validaciones.isNumVoid3(visFicha.txtValPagarComp.getText()));

        memb.setId(Validaciones.isNumVoid(visFicha.lblDsctoIdComp.getText()));
        modFacCabCompras.setMembresiaComp(memb); // 1  = 0

        modFacCabCompras.setSubTotal_facCabComp(Validaciones.isNumVoid3(visFicha.txtValConDsctoFichaComp.getText()));

        iva.setId_ivas(Validaciones.isNumVoid(visFicha.lblIvaIdComp.getText()));
        modFacCabCompras.setIvasComp(iva); //1 = 0
        

        modFacCabCompras.setTotal_facCabComp(Validaciones.isNumVoid3(visFicha.txtTotalConIvaComp.getText()));
        modFacCabCompras.setValCancelo_facCabComp(Validaciones.isNumVoid3(visFicha.txt_valCanceloComp.getText()));
        modFacCabCompras.setValPendiente_facCabComp(Validaciones.isNumVoid3(visFicha.txtValPendienteFichaComp.getText()));       
        modFacCabCompras.setEstadoComp(1);      
    }
    
    public void setFacturaDetalle(VisFicha visFicha)
    {
       
        ConsFacturaDet consFacDet = new ConsFacturaDet();        
        CtrlFacturaDetalle facDetalle = new CtrlFacturaDetalle(consFacDet, visFicha);
        facDetalle.setDetalles(visFicha, cadBus);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == visFicha.btnGuardarFacCabCompras) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
           jdc.add(visFicha.dtcFechaFacCabComp);
          
                System.out.println(visFicha.cmb_clienteFac.getName());
               if (Validaciones.isDateChooserVoid(jdc) &&  Validaciones.isVoidJTxt(visFicha.txt_clienteFacComp) && Validaciones.isDetalleNullComp(visFicha.tblFacturaDetalleCompras)) 
               {                                        
                    setFacturaCabecera(visFicha);   
                                       
                    if (consFicha.registrar(modFacCabCompras)) 
                    {
                        ArrayList<FacturaDetalleCompras> facDets = facDetalleCompras.setDetalles(visFicha, "");
                        
                        if(consFacDetCompras.registrar(facDets)){
                            JOptionPane.showMessageDialog(null, "Registro Guardado!");
                            if(consFacDetCompras.actualizarEntradas(facDets)){
                                consFacDetCompras.actualizarStock(facDets);
                                System.out.println("actualizado entradas");
                            }
                            else
                                System.out.println("NO actualizado entradas");                            
                        }
                        
                         
                        limpiar();
                        facDetalleCompras.limpiarTablaDetalles();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error al Guardar");
                        limpiar();
                    }
                   
               }        
        }
      
/*      if (e.getSource() == visFicha.btnModificarFacCab) 
       {            
                modFacCab.setFecha_facCab(Validaciones.setFormatFecha(visFicha.dtcFecha.getDate()));                
                modFacCab.setNum_facCab("sera numero");
                modFacCab.setSubTotal_facCab(Validaciones.isNumVoid3(visFicha.txtValConDsctoFicha.getText()));
                modFacCab.setTotal_facCab(Validaciones.isNumVoid3(visFicha.txtTotalConIva.getText()));
                modFacCab.setValPendiente_facCab(Validaciones.isNumVoid3(visFicha.txtValPendienteFicha.getText()));       
                modFacCab.setEstado(1);          

            if (consFicha.modificar(modFacCab)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado!");
                limpiar();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
        }
      
      if (e.getSource() == visFicha.btnEliminarFacCab) 
       {
           
            modFacCab.setId_facCab(Integer.parseInt(visFicha.txt_id_FacCab.getText()));
            modFacCab.setEstado(2);
                      
            if (consFicha.eliminar(modFacCab)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado !");
                limpiar();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al Eliminar...");
                limpiar();
            }
          
        }*/
      
       if (e.getSource() == visFicha.btnLimpiarFacCabComp) 
        {
           limpiar();
           desabilitaHabilita(visFicha.btnGuardarFacCab,true);
          // desabilitaHabilita(visFicha.btnModificarFacCab,false);
        }

        if (e.getSource() == visFicha.btnBuscarDsctoCompra) 
        {
            VisMembresia visMem = new VisMembresia();
            Membresias memMod  = new Membresias();
            ConsMembresias consMem = new ConsMembresias();
            Ficha ficha  =  new Ficha();                        
            CtrlMembresias ctrMemb=new CtrlMembresias(memMod,consMem,visMem,ficha,visFicha);
        } 
        
        if (e.getSource() == visFicha.btnCalcularCompras) 
        {       
            if (Validaciones.isVoidJTxt(visFicha.txt_cambio)) {
                double txtValEntregado = Double.parseDouble(visFicha.txt_cambio.getText());
                double txtValCancelo = Double.parseDouble(visFicha.txt_valCancelo.getText());
                double txtCambio = txtValEntregado -txtValCancelo ;
                visFicha.txt_valEntregado.setText(Calculos.setTwoDecimals(txtCambio)+"");
               }
        }
        if (e.getSource() == visFicha.btnBuscarClienteFacturaComp) 
        {
           
            VisPersona visPer = new VisPersona();
            Persona per  = new Persona();
            ConsPersona consPer = new ConsPersona();
                
            Ficha ficha = new Ficha();
            CtrlPersonas ctrPer=new CtrlPersonas(persona, consPer, visPer,visFicha);
            ctrPer.locale = 4;
            ctrPer.iniciar();
            
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
        visFicha.txt_clienteFacComp.setText("");
        visFicha.dtcFechaFacCabComp.setDate(Calculos.getCurrentDate2()); 
        visFicha.txtValConDsctoFichaComp.setText("0.0");
        visFicha.txtValPendienteFichaComp.setText("0.0");
        visFicha.txtValPagarComp.setText("0.0");
        visFicha.txtValDsctoComp.setText("0.0");
        visFicha.txt_valCanceloComp.setText("0.0");   
        
        visFicha.lblDsctoId.setText("1");
        
        
    }
    
}
