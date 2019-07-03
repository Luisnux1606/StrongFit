/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;


import assets.Calculos;
import assets.Configuracion;
import assets.ItemRendererClienteFac;
import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import consultas.ConsAnalisis;
import consultas.ConsBuscarVentas;
import consultas.ConsFacturaCab;
import consultas.ConsFacturaDet;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import modelos.FacturaDetalle;
import modelos.Ficha;
import modelos.HistorialPersonaServicio;
import modelos.Iva;
import modelos.Medidas;
import modelos.Membresias;
import modelos.Persona;
import modelos.Producto;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import vistas.VisBuscarVentas;

import vistas.VisFicha;
import vistas.VisHistorialPersonaServicio;
import vistas.VisMembresia;
import vistas.VisPersona;
import vistas.VisProductos;
import vistas.VisReportes;

/**
 *
 * @author Administrator
 */
public class CtrlFacturaCab implements ActionListener{

    FacturaCab modFacCab;
    ArrayList<FacturaCab> lstFicha;
    ConsFacturaCab consFicha;
    VisFicha visFicha;
    VisReportes visReportes;
    VisMembresia visMemb;
    Membresias memb;
    Iva iva;
    
    Persona persona;
    String cadBus;
    
    ConsPersona consPer;
    ConsFacturaDet consFacDet ;     
    CtrlFacturaDetalle facDetalle; 
    
    public CtrlFacturaCab(FacturaCab modFicha,ConsFacturaCab consFicha,VisFicha visFicha,VisMembresia visMemb,Persona persona)
    {
        this.modFacCab = modFicha;
        this.consFicha = consFicha;
        this.visFicha = visFicha;
        this.persona = persona;
        this.visMemb =  visMemb;
        this.memb = new Membresias();
        this.iva = new Iva();
              
        consPer = new ConsPersona();
        consFacDet= new ConsFacturaDet();  
        facDetalle = new CtrlFacturaDetalle(consFacDet, visFicha);
        
        this.visFicha.btnGuardarFacCab.addActionListener(this);
        this.visFicha.btnLimpiarFacCab.addActionListener(this); 
        this.visFicha.btnBuscarDscto.addActionListener(this);
        this.visFicha.btnCalcular.addActionListener(this);
        this.visFicha.btnBuscarClienteFactura.addActionListener(this);
        this.visFicha.btnAgregarFilas.addActionListener(this);
        this.visFicha.btnEliminarFilas.addActionListener(this);
        this.visFicha.btnEntrenamiento.addActionListener(this);
        this.visFicha.btnDeudas.addActionListener(this);
        this.visFicha.btnEntrenSaldos.addActionListener(this);
        
        cadBus = "";
       
        setFocus();
        setListener();    
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
        visFicha.btnLimpiarFacCab.setToolTipText("Limpiar el registro");
        showComboPersonas();
        AutoCompleteDecorator.decorate(visFicha.cmb_clienteFac);  
        limpiar();       
    }
    
    public void showComboPersonas()
    {
        visFicha.cmb_clienteFac.removeAllItems();
        try {
           
            ResultSet listCategorias = consPer.buscarPersonasClientes();
            
            DefaultComboBoxModel model =  (DefaultComboBoxModel)visFicha.cmb_clienteFac.getModel();
           
            
            while (listCategorias.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                    
                    model.addElement(new Persona(listCategorias.getString("ape_per"),listCategorias.getString("nom_per"),listCategorias.getInt("id_per"),listCategorias.getString("ced_per")));
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consPer.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  visFicha.cmb_clienteFac.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        visFicha.cmb_clienteFac.setRenderer(new ItemRendererClienteFac());
        this.visFicha.cmb_clienteFac.addActionListener(this);
        visFicha.cmb_clienteFac.updateUI();
    }
    
   
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
                  Calculos.setPendiente(visFicha);                                    
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
       
    }
    
    
     public void setFocus()
    {
        visFicha.dtcFechaFacCab.requestFocus();
        visFicha.dtcFechaFacCab.setNextFocusableComponent(visFicha.cmb_clienteFac);
       
    }
    
    public void setFacturaCabecera(VisFicha visFich)
    {
        String numFac = visFicha.lblNroFactura.getText();
        
        modFacCab.setFecha_facCab(Validaciones.setFormatFecha(visFicha.dtcFecha.getDate()));                
        modFacCab.setNum_facCab(numFac);
        
        Persona p = ((Persona)visFicha.cmb_clienteFac.getSelectedItem());
        persona.setId(Validaciones.isNumVoid(p.getId()+""));
        modFacCab.setPersona(persona);

        modFacCab.setValPagar_facCab(Validaciones.isNumVoid3(visFicha.txtValPagar.getText()));

        memb.setId(Validaciones.isNumVoid(visFicha.lblDsctoId.getText()));
        modFacCab.setMembresia(memb); // 1  = 0

        modFacCab.setSubTotal_facCab(Validaciones.isNumVoid3(visFicha.txtValConDsctoFicha.getText()));

        iva.setId_ivas(Validaciones.isNumVoid(visFicha.lblIvaId.getText()));
        modFacCab.setIvas(iva); //1 = 0
        

        modFacCab.setTotal_facCab(Validaciones.isNumVoid3(visFicha.txtTotalConIva.getText()));
        modFacCab.setValCancelo_facCab(Validaciones.isNumVoid3(visFicha.txt_valCancelo.getText()));
        modFacCab.setValPendiente_facCab(Validaciones.isNumVoid3(visFicha.txtValPendienteFicha.getText()));       
        modFacCab.setEstado(1);      
    }
    
    public void setFacturaDetalle(VisFicha visFicha)
    {
       
        ConsFacturaDet consFacDet = new ConsFacturaDet();        
        CtrlFacturaDetalle facDetalle = new CtrlFacturaDetalle(consFacDet, visFicha);
        facDetalle.setDetalles(visFicha, cadBus);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == visFicha.btnGuardarFacCab) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
           jdc.add(visFicha.dtcFechaFacCab);
          
                System.out.println(((Persona)visFicha.cmb_clienteFac.getSelectedItem()).getApellido());
               if (Validaciones.isDateChooserVoid(jdc) && !Validaciones.isNumVoid1(visFicha.cmb_clienteFac.getSelectedItem()+"") && Validaciones.isDetalleNull(visFicha.tblFacturaDetalle)) 
               {                                        
                    setFacturaCabecera(visFicha);   
                                       
                    if (consFicha.registrar(modFacCab)) 
                    {
                        ArrayList<FacturaDetalle> facDets = facDetalle.setDetalles(visFicha, "");
                        if(consFacDet.registrar(facDets))
                        {                      //txt_valCanceloComp
                            JOptionPane.showMessageDialog(null, "Registro Guardado!");
                            if(consFacDet.actualizarSalidas(facDets)){
                                consFacDet.actualizarStock(facDets);
                                System.out.println("actualizado salidas");
                            }
                            else
                                System.out.println("NO actualizado salidas");      
                        }
                         
                        limpiar();
                        facDetalle.limpiarTablaDetalles();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error al Guardar");
                        limpiar();
                    }
                   
               } 
               else
                 Validaciones.getMensaje("Seleccione la persona");
        }
       if (e.getSource() == visFicha.btnLimpiarFacCab) 
        {
           limpiar();
           desabilitaHabilita(visFicha.btnGuardarFacCab,true);
          // desabilitaHabilita(visFicha.btnModificarFacCab,false);
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
            if (Validaciones.isVoidJTxt(visFicha.txt_cambio)) {
                double txtValEntregado = Double.parseDouble(visFicha.txt_cambio.getText());
                double txtValCancelo = Double.parseDouble(visFicha.txt_valCancelo.getText());
                double txtCambio = txtValEntregado -txtValCancelo ;
                visFicha.txt_valEntregado.setText(Calculos.setTwoDecimals(txtCambio)+"");
               }
        }
        
        if (e.getSource() == visFicha.btnBuscarClienteFactura) 
        {
           
            VisPersona visPer = new VisPersona();
            Persona per  = new Persona();
            ConsPersona consPer = new ConsPersona();
                
            Ficha ficha = new Ficha();
            CtrlPersonas ctrPer=new CtrlPersonas(persona, consPer, visPer,visFicha);
            ctrPer.locale = 2;
            ctrPer.iniciar();
            
        } 
        if (e.getSource() == visFicha.btnEntrenamiento) 
        {

            VisHistorialPersonaServicio visHis = new VisHistorialPersonaServicio();
            ConsHistorialPersonaServicio consHPS = new ConsHistorialPersonaServicio();
            HistorialPersonaServicio hisPS = new HistorialPersonaServicio();
            
            Persona item = (Persona)(visFicha.cmb_clienteFac.getSelectedItem());
            CtrlHistorialPersServicio ctlHis = new CtrlHistorialPersServicio(visHis, hisPS, consHPS, visFicha,item);                        
            ctlHis.locale = 0;
            ctlHis.iniciar();                       

        } 
        if (e.getSource() == visFicha.btnDeudas) 
        {
            VisBuscarVentas visBuscarVentas = new VisBuscarVentas();
            
            ConsBuscarVentas consFacCab = new ConsBuscarVentas();
           
            
            CtrlBuscarVentas ctrlBuscarVentas=new CtrlBuscarVentas(consFacCab, visBuscarVentas,visFicha);
            ctrlBuscarVentas.locale = 1; 
            ctrlBuscarVentas.p = (Persona)visFicha.cmb_clienteFac.getSelectedItem();
            ctrlBuscarVentas.iniciar();
        }
        
        if (e.getSource()==visFicha.cmb_clienteFac)
        {
            if (visFicha!=null) {                
                Persona item = (Persona) visFicha.cmb_clienteFac.getSelectedItem();
              //  visFicha.lblPersonaId.setText(item.getId()+"");
              //  System.out.println(item.getId() + " : " + item.getApellido());
            }
        }                
      
    }
    public void limpiar()
    {
        visFicha.cmb_clienteFac.setSelectedIndex(0);
        visFicha.dtcFechaFacCab.setDate(Calculos.getCurrentDate2()); 
        visFicha.txtValConDsctoFicha.setText("0.0");
        visFicha.txtValPendienteFicha.setText("0.0");
        visFicha.txtValPagar.setText("0.0");
        visFicha.txtValDscto.setText("0.0");
        visFicha.txt_valCancelo.setText("0.0");   
        visFicha.lblDsctoId.setText("1");
        
        
    }
    
}
