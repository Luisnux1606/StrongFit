/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Calculos;

import assets.ButtonTable;
import assets.Configuracion;

import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import consultas.ConsAnalisis;
import consultas.ConsBuscarVentas;
import consultas.ConsFacturaCab;
import consultas.ConsMedidas;
import consultas.ConsPersona;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import modelos.Analisis;
import modelos.FacturaCab;
import modelos.Medidas;
import modelos.Persona;
import vistas.VisBuscarPersonas;
import vistas.VisBuscarVentas;
import vistas.VisFicha;
import vistas.VisPersona;
import vistas.VisReportes;


/**
 *
 * @author Administrator
 */
public class CtrlBuscarVentas implements ActionListener {
    
    
    ArrayList<Persona> lstPersonas;
    ConsBuscarVentas consBuscarVentas;
    FacturaCab facCab;
    VisBuscarVentas visVentas;
    VisFicha visFicha;
    FacturaCab f;
    String cadBus;
    int locale;
    Persona p;
    //facCab, consFacCab, visBuscarVentas,visFicha
    
    public CtrlBuscarVentas(ConsBuscarVentas consVentas, VisBuscarVentas visVentas, VisFicha visFicha)
    {
        
        this.facCab = facCab;
        this.consBuscarVentas = consVentas;
        this.visVentas = visVentas;       
        this.visFicha = visFicha;
        
        this.visVentas.cmbTipoBusqueda.addActionListener(this);
        this.visVentas.cmbElegirBusquedaFac.addActionListener(this);
        
        f = new FacturaCab();
        p = new Persona();
        
        cadBus = "";
        locale = 0;
//        
        setListener();
        iniciar();             
        
        int colHide[] = new int[1];
        colHide[0]=0;            
        setHideJtableColumn(visVentas.tblFacturasCabeceras,colHide);        
        setHideJtableColumn(visVentas.tbl_BuscarVentas,colHide);
    }
    
    public void setHideJtableColumn(JTable table, int col[])
    {
        for (int i = 0; i < col.length; i++) {
            table.getColumnModel().getColumn(col[i]).setMaxWidth(0);
            table.getColumnModel().getColumn(col[i]).setMinWidth(0);
            table.getColumnModel().getColumn(col[i]).setPreferredWidth(0);
        }       
    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == visVentas.cmbTipoBusqueda) 
       {       
           String tipo = visVentas.cmbTipoBusqueda.getSelectedItem()+"";
           String nomb = visVentas.txt_buscarPersonaNombres.getText()+"".trim();
            if (tipo.equals("todos")) {
             showTable(nomb);
            }
            if (tipo.equals("cursando")) {
                
                showTableCursando(nomb);
            }
            if (tipo.equals("pendientes")) {
                showTablePendientes();
            }
            if (tipo.equals("proximos a vencer")) {
                showTableProximosVencer(nomb);
            }
            if (tipo.equals("vencidos")) {
                showTableVencidos(nomb);
            }
           
               
        }
      
       if (e.getSource() == visVentas.cmbElegirBusquedaFac) 
       {
           String tipo = visVentas.cmbElegirBusquedaFac.getSelectedItem()+"";
           if (tipo.equals("pendientes")) {
               showTableFacPendientes();
               
            }
           if (tipo.equals("todos")) {
               showTableFacturasCabeceras();
            }
           if (tipo.equals("eliminados")) {
               showTableFacturasCabecerasEliminados();
            }
           
       }
       
    
    }
    
    public void iniciar()
    {
        visVentas.setTitle(Configuracion.nomEmp +" BUSQUEDA DE VENTAS");
        visVentas.setSize(1200, 600);
        visVentas.setLocation(100, 100);
        visVentas.setVisible(true);
        String nom = visVentas.txt_buscarPersonaNombres.getText().trim();
        
        switch(locale)
        {
            case 0:
                showTable(nom);
            break;
                
            case 1:
                showTableFacturasCabecerasById(p.getCedula());
            break;            
            case 2:
                showTableCursandoByCed(p.getCedula());
                visVentas.tbpVentas.setSelectedIndex(1);
            break;   
                
            default:
            break;
        }
        
                
    }
    
    public void showTablePendientes()
    {
        try {
            limpiarTabla(visVentas.tbl_BuscarVentas);
            ResultSet listFicha = consBuscarVentas.buscarPendientes();
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tbl_BuscarVentas.getModel();
            Object cols[] = new Object[10];
            double diff;
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = Validaciones.isNumVoid4(listFicha.getString("ced_per"));
                    cols[2] = Validaciones.isNumVoid4(listFicha.getString("nombres")).toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("fechaini_hisperser"));
                    cols[4] = Validaciones.isNumVoid4(listFicha.getString("fechafin_hisperser"));
                    cols[5] = Validaciones.isNumVoid4(listFicha.getString("Concepto_Faccab")).toUpperCase();
                    cols[6] = Validaciones.isNumVoid4( listFicha.getString("Fecha_Faccab"));
                    cols[7] = Validaciones.isNumVoid4(listFicha.getDouble("Total_Faccab")+"");
                    cols[8] = Validaciones.isNumVoid4(listFicha.getDouble("Valcancelo_Faccab")+"");
                    cols[9] = Validaciones.isNumVoid10(listFicha.getDouble("Valpendiente_Faccab")+"");
                    diff = (double)cols[8]-(double)cols[9];
                    if (Math.abs(diff)>0) {
                         model.addRow(cols);
                    }
                   
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visVentas.tbl_BuscarVentas.updateUI();
    }
    
    public void showTableProximosVencer(String nom)
    {
        try {
            limpiarTabla(visVentas.tbl_BuscarVentas);
            ResultSet listFicha = consBuscarVentas.buscarTodos2(nom);
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tbl_BuscarVentas.getModel();
            Object cols[] = new Object[10];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = Validaciones.isNumVoid4(listFicha.getString("ced_per"));
                    cols[2] = Validaciones.isNumVoid4(listFicha.getString("nombres")).toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("fechaini_hisperser"));
                    cols[4] = Validaciones.isNumVoid4(listFicha.getString("fechafin_hisperser"));
                    cols[5] = Validaciones.isNumVoid4(listFicha.getString("Concepto_Faccab")).toUpperCase();
                    cols[6] = Validaciones.isNumVoid4( listFicha.getString("Fecha_Faccab"));
                    cols[7] = Validaciones.isNumVoid4(listFicha.getDouble("Total_Faccab")+"");
                    cols[8] = Validaciones.isNumVoid4(listFicha.getDouble("Valcancelo_Faccab")+"");
                    cols[9] = Validaciones.isNumVoid10(listFicha.getDouble("Valpendiente_Faccab")+"");
                    if (!Validaciones.isCadnull(cols[4]+"")) {
                        if (Calculos.dateGreaterThanCurrent(cols[4].toString())) {
                            double days = Calculos.getDiffDaysToFinish(cols[4].toString());
                            if (days<=10) {
                                model.addRow(cols);
                            }
                        }  
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visVentas.tbl_BuscarVentas.updateUI();
    }
    
    public void showTableCursando(String nom)
    {
        try {
             limpiarTabla(visVentas.tbl_BuscarVentas);
            ResultSet listFicha = consBuscarVentas.buscarTodos2(nom);
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tbl_BuscarVentas.getModel();
            Object cols[] = new Object[10];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = Validaciones.isNumVoid4(listFicha.getString("ced_per"));
                    cols[2] = Validaciones.isNumVoid4(listFicha.getString("nombres")).toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("fechaini_hisperser"));
                    cols[4] = Validaciones.isNumVoid4(listFicha.getString("fechafin_hisperser"));
                    cols[5] = Validaciones.isNumVoid4(listFicha.getString("Concepto_Faccab")).toUpperCase();
                    cols[6] = Validaciones.isNumVoid4( listFicha.getString("Fecha_Faccab"));
                    cols[7] = Validaciones.isNumVoid4(listFicha.getDouble("Total_Faccab")+"");
                    cols[8] = Validaciones.isNumVoid4(listFicha.getDouble("Valcancelo_Faccab")+"");
                    cols[9] = Validaciones.isNumVoid10(listFicha.getDouble("Valpendiente_Faccab")+"");
                    if (!Validaciones.isCadnull(cols[4]+"")) {
                         if (Calculos.dateGreaterThanCurrent(cols[4].toString())) {
                            model.addRow(cols);                     
                         }   
                    }
                                       
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visVentas.tbl_BuscarVentas.updateUI();
    }
    
    public void showTableCursandoByCed(String ced)
    {
        try {
             limpiarTabla(visVentas.tbl_BuscarVentas);
            ResultSet listFicha = consBuscarVentas.buscarTodos2ByCed(ced);
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tbl_BuscarVentas.getModel();
            Object cols[] = new Object[10];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = Validaciones.isNumVoid4(listFicha.getString("ced_per"));
                    cols[2] = Validaciones.isNumVoid4(listFicha.getString("nombres")).toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("fechaini_hisperser"));
                    cols[4] = Validaciones.isNumVoid4(listFicha.getString("fechafin_hisperser"));
                    cols[5] = Validaciones.isNumVoid4(listFicha.getString("Concepto_Faccab")).toUpperCase();
                    cols[6] = Validaciones.isNumVoid4( listFicha.getString("Fecha_Faccab"));
                    cols[7] = Validaciones.isNumVoid4(listFicha.getDouble("Total_Faccab")+"");
                    cols[8] = Validaciones.isNumVoid4(listFicha.getDouble("Valcancelo_Faccab")+"");
                    cols[9] = Validaciones.isNumVoid10(listFicha.getDouble("Valpendiente_Faccab")+"");
                    if (!Validaciones.isCadnull(cols[4]+"")) {
                         if (Calculos.dateGreaterThanCurrent(cols[4].toString())) {
                            model.addRow(cols);                     
                         }   
                    }
                                       
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visVentas.tbl_BuscarVentas.updateUI();
    }
    
    public void showTableByNom(String nom)
    {
        try {
            limpiarTabla(visVentas.tbl_BuscarVentas);
            
            ResultSet listFicha = consBuscarVentas.buscarTodosPorNomTabla(nom);
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tbl_BuscarVentas.getModel();
            Object cols[] = new Object[10];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("ced_per");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = listFicha.getString("fechaini_hisperser");
                    cols[4] = listFicha.getString("fechafin_hisperser");
                    cols[5] = listFicha.getString("Concepto_Faccab");
                    cols[6] = listFicha.getString("Fecha_Faccab");
                    cols[7] = listFicha.getDouble("Total_Faccab");
                    cols[8] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[9] = listFicha.getDouble("Valpendiente_Faccab");
                    model.addRow(cols);
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
      public void limpiarTabla(JTable table){
        DefaultTableModel tb = (DefaultTableModel) table.getModel();
        int a = table.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
      
    
    public void showTableFacturasCabeceras()
    {
        try {
            limpiarTabla(visVentas.tblFacturasCabeceras);
            ResultSet listFicha = consBuscarVentas.buscarFacturas();
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tblFacturasCabeceras.getModel();
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
                    
                
                    model.addRow(cols);
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTable(visVentas);
        visVentas.tblFacturasCabeceras.updateUI();
    }  
    
    public void showTableFacturasCabecerasEliminados()
    {
        try {
            limpiarTabla(visVentas.tblFacturasCabeceras);
            ResultSet listFicha = consBuscarVentas.buscarFacturasEliminadas();
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tblFacturasCabeceras.getModel();
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
                    
                
                    model.addRow(cols);
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTable(visVentas);
        visVentas.tblFacturasCabeceras.updateUI();
    } 
    
    public void showTableFacturasCabecerasByNom(String nom)
    {
        try {
            limpiarTabla(visVentas.tblFacturasCabeceras);
            ResultSet listFicha = consBuscarVentas.buscarFacturasByNom(nom);
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tblFacturasCabeceras.getModel();
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
                    
                
                    model.addRow(cols);
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTable(visVentas);
        visVentas.tblFacturasCabeceras.updateUI();
    } 
    
    public void showTableFacturasCabecerasById(String id)
    {
        try {
            limpiarTabla(visVentas.tblFacturasCabeceras);
            ResultSet listFicha = consBuscarVentas.buscarFacturasById(id);
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tblFacturasCabeceras.getModel();
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
                        model.addRow(cols);
                    }
                    
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTable(visVentas);
        visVentas.tblFacturasCabeceras.updateUI();
    } 
      
    public void showTable(String nom)
    {
        try {
            limpiarTabla(visVentas.tbl_BuscarVentas);
            ResultSet listFicha = consBuscarVentas.buscarTodos2(nom);
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tbl_BuscarVentas.getModel();
            Object cols[] = new Object[10];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("ced_per");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = listFicha.getString("fechaini_hisperser");
                    cols[4] = listFicha.getString("fechafin_hisperser");
                    cols[5] = listFicha.getString("Concepto_Faccab");
                    cols[6] = listFicha.getString("Fecha_Faccab");
                    cols[7] = listFicha.getDouble("Total_Faccab");
                    cols[8] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[9] = listFicha.getDouble("Valpendiente_Faccab");
                  
                    model.addRow(cols);
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visVentas.tbl_BuscarVentas.updateUI();
    }
    
    public void showTableFacPendientes()
    {
         try {
            limpiarTabla(visVentas.tblFacturasCabeceras);
            ResultSet listFicha = consBuscarVentas.buscarFacturas();
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tblFacturasCabeceras.getModel();
            Object cols[] = new Object[11];
            double diffAjusteCanc;
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
                    
                    diffAjusteCanc = (double)cols[8] - (double)cols[7];
                    
                    if (Math.abs(diffAjusteCanc)>0) {
                         model.addRow(cols);
                    }
                   
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTable(visVentas);
        visVentas.tblFacturasCabeceras.updateUI();
    }
     
     public void showTableVencidos(String nom)
    {
        try {
            limpiarTabla(visVentas.tbl_BuscarVentas);
            ResultSet listFicha = consBuscarVentas.buscarTodos2(nom);
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tbl_BuscarVentas.getModel();
            Object cols[] = new Object[10];
            
            while (listFicha.next()) {
                try {
                   
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("ced_per");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = listFicha.getString("fechaini_hisperser");
                    cols[4] = listFicha.getString("fechafin_hisperser");
                    cols[5] = listFicha.getString("Concepto_Faccab");
                    cols[6] = listFicha.getString("Fecha_Faccab");
                    cols[7] = listFicha.getDouble("Total_Faccab");
                    cols[8] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[9] = listFicha.getDouble("Valpendiente_Faccab");
                    if (!Validaciones.isCadnull(cols[4]+"")) {
                        if (!Calculos.dateGreaterThanCurrent(cols[4].toString()))
                            model.addRow(cols);
                    }
                     
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visVentas.tbl_BuscarVentas.updateUI();
    }
     
    public void showTableDetalles(int idFac)
    {
        try {
            limpiarTabla(visVentas.tblDetalles);
            ResultSet listFicha = consBuscarVentas.buscarDetallesByIdFac(idFac);
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tblDetalles.getModel();
            Object cols[] = new Object[4];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("Id_Facdet");
                    cols[1] = listFicha.getString("Descripcion_Facdet");
                    cols[2] = listFicha.getDouble("Valunitario_Facdet");
                    cols[3] = listFicha.getDouble("Vtotal_Facdet");
                    
                    model.addRow(cols);
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visVentas.tblDetalles.updateUI();
    }
    
    public void showTableFacturaDetalles(int idFac)
    {
        try {
            limpiarTabla(visVentas.tblFacturaDetalles);
            ResultSet listFicha = consBuscarVentas.buscarDetallesByIdFac(idFac);
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tblFacturaDetalles.getModel();
            Object cols[] = new Object[4];
            
            while (listFicha.next()) {
                try {
                    cols[0] = listFicha.getInt("Id_Facdet");
                    cols[1] = listFicha.getString("Descripcion_Facdet");
                    cols[2] = listFicha.getDouble("Valunitario_Facdet");
                    cols[3] = listFicha.getDouble("Vtotal_Facdet");
                    
                    model.addRow(cols);
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visVentas.tblFacturaDetalles.updateUI();
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
             int m=e.getKeyChar();                                
             int  row =0;
             if(m == KeyEvent.VK_ENTER) 
             {
                 limpiarTabla(visVentas.tbl_BuscarVentas);
                 String cadCamp = Validaciones.isNumVoid4(visVentas.txt_buscarPersonaNombres.getText());
                 showTableByNom(cadCamp);
                 visVentas.txt_buscarPersonaNombres.updateUI();
                 System.out.println("aqui estoy...");
             }
             
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
         
          }
        };
        visVentas.txt_buscarPersonaNombres.addKeyListener(keyListenertxtBuscarCedula);
        
        //LISTENER TXTBUSCARCAMPO VENTAS REALIZADAS
        KeyListener keyListenertxtBuscarCampoVentas = new KeyListener() {
          public void keyPressed(KeyEvent keyEvent) {
            printIt("Pressed", keyEvent);
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
               
            
            int m=e.getKeyChar();                                
             int  row =0;
             if(m == KeyEvent.VK_ENTER) 
             {
                 limpiarTabla(visVentas.tblFacturasCabeceras);
                 String cadCamp = Validaciones.isNumVoid4(visVentas.txtBuscarCampo.getText());
                 showTableFacturasCabecerasByNom(cadCamp);
                 System.out.println("aqui estoy...");
             }
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
         
          }
        };
        visVentas.txtBuscarCampo.addKeyListener(keyListenertxtBuscarCampoVentas);
        
        
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
           
          }


        };
        
        MouseListener mouseListTblPersonas;
        mouseListTblPersonas = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                   int idFac = Validaciones.isNumVoid(visVentas.tbl_BuscarVentas.getValueAt(visVentas.tbl_BuscarVentas.getSelectedRow(), 0)+"");                    
                   f.setId_facCab(idFac);                       
                   showTableDetalles(idFac);    
                  
                   
                }
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
        
        
        ////
        
        MouseListener mouseListTblFacDetalles;
        mouseListTblFacDetalles = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                   int idFac = Validaciones.isNumVoid(visVentas.tblFacturasCabeceras.getValueAt(visVentas.tblFacturasCabeceras.getSelectedRow(), 0)+"");                    
                   f.setId_facCab(idFac);                       
                   showTableFacturaDetalles(idFac);     
                    
                }
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
       
        visVentas.tblFacturasCabeceras.addMouseListener(mouseListTblFacDetalles);
      
    }

}
