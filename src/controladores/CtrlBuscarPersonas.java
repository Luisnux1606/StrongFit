/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Validaciones;
import consultas.ConsAnalisis;
import consultas.ConsFicha;
import consultas.ConsMedidas;
import consultas.ConsPersona;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelos.Analisis;
import modelos.Ficha;
import modelos.Medidas;
import modelos.Persona;
import vistas.VisBuscarPersonas;
import vistas.VisFicha;
import vistas.VisPersona;
import vistas.VisReportes;
import visual.facturacion.MyTableModel;

/**
 *
 * @author Administrator
 */
public class CtrlBuscarPersonas {
    
    Persona modPer;
    ArrayList<Persona> lstPersonas;
    ConsPersona consPer;
    VisBuscarPersonas visPersona;
    VisReportes visPer;
    
    String cadBus;
   
    
    public CtrlBuscarPersonas(VisReportes visPer )
    {
        
        this.consPer = new ConsPersona();
        this.visPersona = new VisBuscarPersonas();
        this.visPer = visPer;
        
        cadBus = "";
        showTable();
        setListener();
        iniciar();
    }
    
    public void iniciar()
    {
        visPersona.setTitle("BUSQUEDA DE PERSONAS");
        visPersona.setSize(700, 400);
        visPersona.setLocation(400, 200);
        visPersona.setVisible(true);
       
                
    }
     public void showTable()
    {
        limpiarTabla();                            
           ArrayList<Persona> prodList = consPer.buscarTodos(modPer);
           DefaultTableModel model =  (DefaultTableModel)visPersona.tbl_BuscarPersona.getModel();
           Object cols[] = new Object[9];

           for (int i = 0; i < prodList.size(); i++) {
               cols[0] = prodList.get(i).getId();
               cols[1] = Validaciones.isNumVoid4(prodList.get(i).getCedula());
               cols[2] = prodList.get(i).getNombre().toUpperCase();
               cols[3] = prodList.get(i).getApellido().toUpperCase();
               cols[4] = prodList.get(i).getGenero().toUpperCase();
               cols[5] = Validaciones.isNumVoid4(prodList.get(i).getMail());
               cols[6] = Validaciones.isNumVoid4(prodList.get(i).getNro_fono());
               cols[7] = Validaciones.isNumVoid(prodList.get(i).getEdad()+"");
               cols[8] = Validaciones.isNumVoid4(prodList.get(i).getFecha_nac());

               model.addRow(cols);                    
           }   
        //   model.remove;
    }
     
      public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visPersona.tbl_BuscarPersona.getModel();
        int a = visPersona.tbl_BuscarPersona.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
      
     public void showTableByNom(String nom)
    {
        limpiarTabla();                            
           ArrayList<Persona> prodList = consPer.buscarTodosPorNom(modPer,nom);
           DefaultTableModel model =  (DefaultTableModel)visPersona.tbl_BuscarPersona.getModel();
           Object cols[] = new Object[9];

           for (int i = 0; i < prodList.size(); i++) {
               cols[0] = prodList.get(i).getId();
               cols[1] = prodList.get(i).getCedula();
               cols[2] = prodList.get(i).getNombre().toUpperCase();
               cols[3] = prodList.get(i).getApellido().toUpperCase();
               cols[4] = prodList.get(i).getGenero().toUpperCase();
               cols[5] = prodList.get(i).getMail();
               cols[6] = prodList.get(i).getNro_fono();
               cols[7] = prodList.get(i).getEdad();
               cols[8] = prodList.get(i).getFecha_nac();

               model.addRow(cols);                    
           }   
    
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
        visPersona.txt_buscarPersonaNombres.addKeyListener(keyListenertxtBuscarCedula);
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
   
                    int idPer = Integer.parseInt(visPersona.tbl_BuscarPersona.getValueAt(visPersona.tbl_BuscarPersona.getSelectedRow(), 0)+"");
                    
                    visPer.txtCedulaReportePersonas.setText(idPer+"");
                    visPersona.dispose();
                     
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
       
        visPersona.tbl_BuscarPersona.addMouseListener(mouseListTblPersonas);
      
    }
}
