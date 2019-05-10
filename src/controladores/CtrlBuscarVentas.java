/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Validaciones;
import consultas.ConsAnalisis;
import consultas.ConsFacturaCab;
import consultas.ConsMedidas;
import consultas.ConsPersona;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelos.Analisis;
import modelos.FacturaCab;
import modelos.Medidas;
import modelos.Persona;
import vistas.VisBuscarPersonas;
import vistas.VisBuscarVentas;
import vistas.VisFicha;
import vistas.VisPersona;
import vistas.VisReportes;
import visual.facturacion.MyTableModel;

/**
 *
 * @author Administrator
 */
public class CtrlBuscarVentas {
    
    
    ArrayList<Persona> lstPersonas;
    ConsFacturaCab consFacCab;
    FacturaCab facCab;
    VisBuscarVentas visVentas;
    VisFicha visFicha;
    
    String cadBus;
    //facCab, consFacCab, visBuscarVentas,visFicha
    
    public CtrlBuscarVentas(FacturaCab facCab,ConsFacturaCab consFacCab, VisBuscarVentas visVentas, VisFicha visFicha)
    {
        
        this.facCab = facCab;
        this.consFacCab = consFacCab;
        this.visVentas = visVentas;
        this.visVentas = visVentas;
        this.visFicha = visFicha;
        
        cadBus = "";
        
        setListener();
        iniciar();
    }
    
    public void iniciar()
    {
        visVentas.setTitle("BUSQUEDA DE VENTAS");
        visVentas.setSize(1200, 800);
        visVentas.setLocation(400, 200);
        visVentas.setVisible(true);
       
        showTable();
                
    }
    public void showTableByNom(String nom)
    {
        try {
            limpiarTabla();
            
            ResultSet listFicha = consFacCab.buscarTodosPorNomTabla(nom);
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tbl_BuscarVentas.getModel();
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
            consFacCab.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
      public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visVentas.tbl_BuscarVentas.getModel();
        int a = visVentas.tbl_BuscarVentas.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
      
     public void showTable()
    {
        try {
            limpiarTabla();
            ResultSet listFicha = consFacCab.buscarTodos2();
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tbl_BuscarVentas.getModel();
            Object cols[] = new Object[8];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("id_fac");
                    cols[1] = listFicha.getString("ced_per");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = listFicha.getString("fechaIni_fac");
                    cols[4] = listFicha.getString("fechaFin_fac");
                   
                    cols[5] = listFicha.getDouble("total_fac");
                    cols[6] = listFicha.getDouble("valPendiente_fac");
                    model.addRow(cols);
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consFacCab.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visVentas.tbl_BuscarVentas.updateUI();
    }
      
       public void setListener(){
        KeyListener keyListenertxtBuscarCedula = new KeyListener() {
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
           // System.out.println(title + " : " + keyText + " / " + keyEvent.getKeyChar());
          }
        };
        visVentas.txt_buscarPersonaNombres.addKeyListener(keyListenertxtBuscarCedula);
         /////TBLPERSONAS
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
        
        MouseListener mouseListTblPersonas;
        mouseListTblPersonas = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2)
                {
   
                                        
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
       
        visVentas.tbl_BuscarVentas.addMouseListener(mouseListTblPersonas);
      
    }
}
