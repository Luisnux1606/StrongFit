/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;


import assets.ButtonTable;
import assets.ButtonTableIngresosEgresos;
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
        this.visFicha.btnPagarAjustar.addActionListener(this);
        this.visFicha.btnPagarAjustarGimnasio.addActionListener(this);
        this.visFicha.btnPagarAjustarRopa.addActionListener(this);
        
        cadBus = "";
       
        setFocus();
        setListener();    
        iniciar();
        
        visFicha.txt_id_persona_u.setText(persona.getId()+"");
        
        /// desabilitado para TroyaGYM
        
         this.visFicha.lblGimnasio.setVisible(false);
         this.visFicha.lblDeudaAjustarGimnasio.setVisible(false);
         this.visFicha.btnPagarAjustarGimnasio.setVisible(false);
         this.visFicha.lblDeudaRopa.setVisible(false);
         this.visFicha.lblDeudaAjustarRopa.setVisible(false);
         this.visFicha.btnPagarAjustarRopa.setVisible(false);



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
    
    public void ajustarPagarDeudas(String idPersona,double valAjustePago)
    {
        
        ConsBuscarVentas consBuscarVentas = new ConsBuscarVentas();
        ArrayList<String> idsFacturasAjustar = new ArrayList<String>();
        try {
            
            ResultSet listFicha = consBuscarVentas.buscarFacturasById(idPersona);                        
            Object cols[] = new Object[11];
            
            while (listFicha.next()) {
                try {
                                      
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("nombres");
                    cols[2] = listFicha.getString("Fecha_Faccab").toUpperCase();
                    cols[3] = listFicha.getString("Num_Faccab");
                    cols[4] = listFicha.getString("Concepto_Faccab");
                    cols[5] = listFicha.getString("Total_Faccab");
                    cols[6] = listFicha.getString("Valcancelo_Faccab");
                    cols[7] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[8] = listFicha.getDouble("valajuste_faccab");
                    cols[9] = "Guardar";
                    cols[10] = "Anular";
                    
                    double valCancelo = new Double(cols[6]+"").doubleValue();
                    double valPend = new Double(cols[7]+"").doubleValue();
                    double valAjus = new Double(cols[8]+"").doubleValue();
                    double totPagar = new Double(cols[5]+"").doubleValue();
                    
                    if (Math.abs(totPagar - (valAjus+valCancelo))>0) {                       
                        idsFacturasAjustar.add(cols[0]+";"+cols[5]+";"+cols[7]+";"+cols[8]);
                    }
                   
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }      
        /*
                recorro facturas
             se comprueba que valAjuste>0
             se ve el valajuste si es menor que val pend
             si el (valorPendiente-valAjuste) < valAjustePagar >> valAjustePagar = ValAjustePagar - (valorPend-valAjuste)
                valorAjusteFac = valorPendiente;
              si el (valorPendiente-valAjuste)>   valAjustePagar  >>valAjusteFac = valAjustefAC + valAjustePagar
                valAjustePagr = valAjustePagar - valAjusteFac

             si es igual pasa al siguiente factura

            */
        double valAjustePagar = valAjustePago;
        for (int i = 0; i < idsFacturasAjustar.size(); i++) {
            String[] arrDat = idsFacturasAjustar.get(i).split(";"); //id; total;valPend;valAjust
            int idFac = new Integer(arrDat[0]).intValue();
            double totFac = new Double(arrDat[1]).doubleValue();
            double valPend = new Double(arrDat[2]).doubleValue();
            double valAjuste = new Double(arrDat[3]).doubleValue(); //valAjuste de la factura
             FacturaCab facCab = new FacturaCab();
            if (valAjustePagar>0)   //paraetro para pagar/ajustar
            {
                 if ((valPend-valAjuste) <= valAjustePagar) {
                    valAjustePagar = valAjustePagar - (valPend-valAjuste);
                    valAjuste = valPend;
                    System.out.println("factura "+idFac + " nuevo ValAjuste "+valAjuste);
                    //falta hacer update en esa factura con  valAjuste                                               
                }
                if((valPend-valAjuste)> valAjustePagar)
                {
                    valAjuste = valAjuste + valAjustePagar;
                    valAjustePagar = valAjustePagar - valAjuste;
                    //falta hace update en esa factura del valAjuste con valAjuste                     
                    System.out.println("factura "+idFac + " nuevo ValAjuste "+valAjuste);
                }
                
                facCab.setValAjuste_facCab(valAjuste);
                facCab.setId_facCab(idFac);
                consFicha.modificarAjuste(facCab);
                
            }
        }
        getValDeuda();
    }
    
    public void ajustarPagarDeudasGimnasio(String idPersona,double valAjustePago)
    {
        
        ConsBuscarVentas consBuscarVentas = new ConsBuscarVentas();
        ArrayList<String> idsFacturasAjustar = new ArrayList<String>();
        try {
            
            ResultSet listFicha = consBuscarVentas.buscarFacturasById(idPersona);                        
            Object cols[] = new Object[11];
            
            while (listFicha.next()) {
                try {
                                      
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("nombres");
                    cols[2] = listFicha.getString("Fecha_Faccab").toUpperCase();
                    cols[3] = listFicha.getString("Num_Faccab");
                    cols[4] = listFicha.getString("Concepto_Faccab");
                    cols[5] = listFicha.getString("Total_Faccab");
                    cols[6] = listFicha.getString("Valcancelo_Faccab");
                    cols[7] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[8] = listFicha.getDouble("valajuste_faccab");
                    cols[9] = "Guardar";
                    cols[10] = "Anular";
                    
                    double valCancelo = new Double(cols[6]+"").doubleValue();
                    double valPend = new Double(cols[7]+"").doubleValue();
                    double valAjus = new Double(cols[8]+"").doubleValue();
                    double totPagar = new Double(cols[5]+"").doubleValue();
                    
                    if (Math.abs(totPagar - (valAjus+valCancelo))>0) {                       
                        idsFacturasAjustar.add(cols[0]+";"+cols[5]+";"+cols[7]+";"+cols[8]);
                    }
                   
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }      
        /*
                recorro facturas
             se comprueba que valAjuste>0
             se ve el valajuste si es menor que val pend
             si el (valorPendiente-valAjuste) < valAjustePagar >> valAjustePagar = ValAjustePagar - (valorPend-valAjuste)
                valorAjusteFac = valorPendiente;
              si el (valorPendiente-valAjuste)>   valAjustePagar  >>valAjusteFac = valAjustefAC + valAjustePagar
                valAjustePagr = valAjustePagar - valAjusteFac

             si es igual pasa al siguiente factura

            */
        double valAjustePagar = valAjustePago;
        for (int i = 0; i < idsFacturasAjustar.size(); i++) {
            String[] arrDat = idsFacturasAjustar.get(i).split(";"); //id; total;valPend;valAjust
            int idFac = new Integer(arrDat[0]).intValue();
            double totFac = new Double(arrDat[1]).doubleValue();
            double valPend = new Double(arrDat[2]).doubleValue();
            double valAjuste = new Double(arrDat[3]).doubleValue(); //valAjuste de la factura
             FacturaCab facCab = new FacturaCab();
            if (valAjustePagar>0)   //paraetro para pagar/ajustar
            {
                 if ((valPend-valAjuste) <= valAjustePagar) {
                    valAjustePagar = valAjustePagar - (valPend-valAjuste);
                    valAjuste = valPend;
                    System.out.println("factura "+idFac + " nuevo ValAjuste "+valAjuste);
                    //falta hacer update en esa factura con  valAjuste                                               
                }
                if((valPend-valAjuste)> valAjustePagar)
                {
                    valAjuste = valAjuste + valAjustePagar;
                    valAjustePagar = valAjustePagar - valAjuste;
                    //falta hace update en esa factura del valAjuste con valAjuste                     
                    System.out.println("factura "+idFac + " nuevo ValAjuste "+valAjuste);
                }
                
                facCab.setValAjuste_facCab(valAjuste);
                facCab.setId_facCab(idFac);
                consFicha.modificarAjuste(facCab);
                
            }
        }
        getValDeuda();
    }
    
    
    public void getValDeuda()
    {
    
        Persona item = (Persona) visFicha.cmb_clienteFac.getSelectedItem();
       String nombre = item.getApellido()+" "+item.getNombre();
       String deuda = showTotalDeudaPersona(nombre);                
       visFicha.lblDedudaTotalValor.setText(Validaciones.isNumVoid10(deuda)+"");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == visFicha.btnGuardarFacCab) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
           jdc.add(visFicha.dtcFechaFacCab);
          
                System.out.println(((Persona)visFicha.cmb_clienteFac.getSelectedItem()).getApellido());
                String ced = ((Persona)visFicha.cmb_clienteFac.getSelectedItem()).getCedula();
               
               if (Validaciones.isDateChooserVoid(jdc) && !Validaciones.isNumVoid1(visFicha.cmb_clienteFac.getSelectedItem()+"") && Validaciones.isDetalleNull(visFicha.tblFacturaDetalle)) 
               {                                                
                    setFacturaCabecera(visFicha);                       
                    
                    if (consFicha.registrar(modFacCab)) 
                    {
                        ArrayList<FacturaDetalle> facDets = facDetalle.setDetalles(visFicha, "");
                        
                        for (FacturaDetalle facDet : facDets) {
                            if (facDet.getDescripcion_facDet().equals("AJUSTAR DEUDA")) {
                                ajustarPagarDeudas(ced, facDet.getValUnitario_facDet());
                            }
                            if (facDet.getDescripcion_facDet().equals("AJUSTAR DEUDA GIMNASIO")) {
                                ajustarPagarDeudas(ced, facDet.getValUnitario_facDet());
                            }
                            if (facDet.getDescripcion_facDet().equals("AJUSTAR DEUDA ROPA")) {
                                ajustarPagarDeudas(ced, facDet.getValUnitario_facDet());
                            }
                        }
                        
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
        if (e.getSource()==visFicha.cmb_clienteFac)
        {
            if (visFicha!=null) {                
                Persona item = (Persona) visFicha.cmb_clienteFac.getSelectedItem();
                String nombre = item.getApellido()+" "+item.getNombre();
                String deudaTot = showTotalDeudaPersona(nombre);                
                String deudaTotGim = showTotalDeudaPersonaGimnasio(nombre);
                String deudaTotRop = showTotalDeudaPersonaRopa(nombre);
                visFicha.lblDedudaTotalValor.setText(Validaciones.isNumVoid10(deudaTot)+"");  
                visFicha.lblDeudaAjustarGimnasio.setText(Validaciones.isNumVoid10(deudaTotGim)+"");
                visFicha.lblDeudaAjustarRopa.setText(Validaciones.isNumVoid10(deudaTotRop)+"");
                System.out.println(item.getId() + " ::::: " + item.getApellido());
            }
        } 
        
        if (e.getSource()==visFicha.btnPagarAjustar)
        {
            String totDeuda = visFicha.lblDedudaTotalValor.getText()+"".trim();
            double totDeudaD = Calculos.getTwoDecimals(new Double(totDeuda).doubleValue());
            if (totDeudaD>0) 
            {
                setFilaAjustarPagar();
                Persona item = (Persona) visFicha.cmb_clienteFac.getSelectedItem();
                String ced = item.getCedula();                
            }
        } 
        if (e.getSource()==visFicha.btnPagarAjustarGimnasio)
        {
            String totDeuda = visFicha.lblDeudaAjustarGimnasio.getText()+"".trim();
            double totDeudaD = Calculos.getTwoDecimals(new Double(totDeuda).doubleValue());
            if (totDeudaD>0) 
            {
                setFilaAjustarPagarGimnasio();                     
            }
        } 
        if (e.getSource()==visFicha.btnPagarAjustarRopa)
        {
            String totDeuda = visFicha.lblDeudaAjustarRopa.getText()+"".trim();
            double totDeudaD = Calculos.getTwoDecimals(new Double(totDeuda).doubleValue());
            if (totDeudaD>0) 
            {
                setFilaAjustarPagarRopa();                           
            }
        } 
        if (e.getSource()==visFicha.btnDeudas)
        {
            VisBuscarVentas visBuscarVentas = new VisBuscarVentas();            
            ConsBuscarVentas consFacCab = new ConsBuscarVentas();                       
            CtrlBuscarVentas ctrlBuscarVentas=new CtrlBuscarVentas(consFacCab, visBuscarVentas,visFicha);
            ctrlBuscarVentas.locale = 1; 
            ctrlBuscarVentas.p = (Persona)visFicha.cmb_clienteFac.getSelectedItem();
            ctrlBuscarVentas.iniciar();
        } 
        
        if (e.getSource()==visFicha.btnEntrenSaldos)
        {
            VisBuscarVentas visBuscarVentas = new VisBuscarVentas();            
            ConsBuscarVentas consFacCab = new ConsBuscarVentas();                       
            CtrlBuscarVentas ctrlBuscarVentas=new CtrlBuscarVentas(consFacCab, visBuscarVentas,visFicha);
            ctrlBuscarVentas.locale = 2; 
            ctrlBuscarVentas.p = (Persona)visFicha.cmb_clienteFac.getSelectedItem();
            ctrlBuscarVentas.iniciar();
        } 
      
    }
    
    public void setFilaAjustarPagar()
    {
        ConsProductos consProd = new ConsProductos();
        Persona item = (Persona) visFicha.cmb_clienteFac.getSelectedItem();
        
        int filaDetalle = visFicha.tblFacturaDetalleCompras.getRowCount()-1;
        int idProd = consProd.getIdProdByNom("AJUSTAR DEUDA");
        int idPer = item.getId();
        
        String descripcion = "AJUSTAR DEUDA";
        double precio =  Validaciones.isNumVoid10(visFicha.lblDedudaTotalValor.getText());


        visFicha.tblFacturaDetalle.setValueAt(idProd, filaDetalle, 0);
        visFicha.tblFacturaDetalle.setValueAt(0, filaDetalle, 1);
        visFicha.tblFacturaDetalle.setValueAt(1, filaDetalle, 2); 
        visFicha.tblFacturaDetalle.setValueAt(descripcion, filaDetalle, 3);
        visFicha.tblFacturaDetalle.setValueAt(precio, filaDetalle, 4);

        Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalle);                            
        Calculos.setTotalesCabecera(visFicha.tblFacturaDetalle, visFicha);
    
    }
    public void setFilaAjustarPagarGimnasio()
    {
        ConsProductos consProd = new ConsProductos();
        Persona item = (Persona) visFicha.cmb_clienteFac.getSelectedItem();
        
        int filaDetalle = visFicha.tblFacturaDetalleCompras.getRowCount()-1;
        int idProd = consProd.getIdProdByNom("AJUSTAR DEUDA GIMNASIO");
        int idPer = item.getId();
        
        String descripcion = "AJUSTAR DEUDA GIMNASIO";
        double precio =  Validaciones.isNumVoid10(visFicha.lblDeudaAjustarGimnasio.getText());


        visFicha.tblFacturaDetalle.setValueAt(idProd, filaDetalle, 0);
        visFicha.tblFacturaDetalle.setValueAt(0, filaDetalle, 1);
        visFicha.tblFacturaDetalle.setValueAt(1, filaDetalle, 2); 
        visFicha.tblFacturaDetalle.setValueAt(descripcion, filaDetalle, 3);
        visFicha.tblFacturaDetalle.setValueAt(precio, filaDetalle, 4);

        Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalle);                            
        Calculos.setTotalesCabecera(visFicha.tblFacturaDetalle, visFicha);
    
    }
    public void setFilaAjustarPagarRopa()
    {
        ConsProductos consProd = new ConsProductos();
        Persona item = (Persona) visFicha.cmb_clienteFac.getSelectedItem();
        
        int filaDetalle = visFicha.tblFacturaDetalleCompras.getRowCount()-1;
        int idProd = consProd.getIdProdByNom("AJUSTAR DEUDA ROPA");
        int idPer = item.getId();
        
        String descripcion = "AJUSTAR DEUDA ROPA";
        double precio =  Validaciones.isNumVoid10(visFicha.lblDeudaAjustarRopa.getText());


        visFicha.tblFacturaDetalle.setValueAt(idProd, filaDetalle, 0);
        visFicha.tblFacturaDetalle.setValueAt(0, filaDetalle, 1);
        visFicha.tblFacturaDetalle.setValueAt(1, filaDetalle, 2); 
        visFicha.tblFacturaDetalle.setValueAt(descripcion, filaDetalle, 3);
        visFicha.tblFacturaDetalle.setValueAt(precio, filaDetalle, 4);

        Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalle);                            
        Calculos.setTotalesCabecera(visFicha.tblFacturaDetalle, visFicha);
    
    }
    
    public String showTotalDeudaPersona(String cadCampo)
    {
        Object cols[] = new Object[21];
        try {
            visFicha.lblDedudaTotalValor.setText("$");
            ResultSet listFicha = consPer.buscarIngresosEgresosCampos(cadCampo);
            while (listFicha.next()) {
                try { 
                    
                    cols[15] = listFicha.getDouble("saldoP");
                                                           
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consPer.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cols[15]+"";
    }
    
    public String showTotalDeudaPersonaGimnasio(String cadCampo)
    {
        Object cols[] = new Object[21];
        try {
            visFicha.lblDedudaTotalValor.setText("$");
            ResultSet listFicha = consPer.buscarIngresosEgresosCamposGimnasio(cadCampo);
            while (listFicha.next()) {
                try { 
                    
                    cols[15] = listFicha.getDouble("saldoP");
                                                           
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consPer.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cols[15]+"";
    }
     
    public String showTotalDeudaPersonaRopa(String cadCampo)
    {
        Object cols[] = new Object[21];
        try {
            visFicha.lblDedudaTotalValor.setText("$");
            ResultSet listFicha = consPer.buscarIngresosEgresosCamposRopa(cadCampo);
            while (listFicha.next()) {
                try { 
                    
                    cols[15] = listFicha.getDouble("saldoP");
                                                           
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consPer.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cols[15]+"";
    }
    
    public void limpiar()
    {
//        visFicha.cmb_clienteFac.setSelectedIndex(0);
        visFicha.dtcFechaFacCab.setDate(Calculos.getCurrentDate2()); 
        visFicha.txtValConDsctoFicha.setText("0.0");
        visFicha.txtValPendienteFicha.setText("0.0");
        visFicha.txtValPagar.setText("0.0");
        visFicha.txtValDscto.setText("0.0");
        visFicha.txt_valCancelo.setText("0.0");   
        visFicha.lblDsctoId.setText("1");
        
        
    }
    
}
