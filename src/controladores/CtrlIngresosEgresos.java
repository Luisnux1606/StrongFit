/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Calculos;

import assets.ButtonTable;
import assets.ButtonTableIngresosEgresos;
import assets.Configuracion;
import assets.ItemRendererClienteFac;
import assets.ItemRendererClienteIngEgr;

import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDateChooserCellEditor;
import consultas.ConsAnalisis;
import consultas.ConsBuscarVentas;
import consultas.ConsFacturaCab;
import consultas.ConsIngresosEgresos;
import consultas.ConsMedidas;
import consultas.ConsPersona;
import consultas.ConsProductos;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;
import modelos.Analisis;
import modelos.Categoria;
import modelos.FacturaCab;
import modelos.Medidas;
import modelos.Persona;
import modelos.Producto;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import vistas.VisBuscarPersonas;
import vistas.VisBuscarVentas;
import vistas.VisFicha;
import vistas.VisIngresoEgreso;
import vistas.VisPersona;
import vistas.VisProductos;
import vistas.VisReportes;


/**
 *
 * @author Administrator
 */
public class CtrlIngresosEgresos implements ActionListener {
    
    
    ArrayList<Persona> lstPersonas;
    ConsIngresosEgresos consIngEgr;
    FacturaCab facCab;
    VisIngresoEgreso visIngEgr;
    VisFicha visFicha;
     FacturaCab f;
    String cadBus;
    
  
    //facCab, consFacCab, visBuscarVentas,visFicha
    
    public CtrlIngresosEgresos(ConsIngresosEgresos consIngEgr, VisIngresoEgreso visIngEgr, VisFicha visFicha)
    {
        
        this.consIngEgr = consIngEgr;
        this.visIngEgr = visIngEgr;       
        this.visFicha = visFicha;
        
        this.visIngEgr.cmbTipoBusqueda.addActionListener(this);    
        this.visIngEgr.cmbElegirBusquedaFac.addActionListener(this);
        this.visIngEgr.btnAgregarTrans.addActionListener(this);
        this.visIngEgr.btnEliminarTrans.addActionListener(this);
        this.visIngEgr.cmbPersonasIngEgr.addActionListener(this);
        this.visIngEgr.btnBuscarPersonaIngEgr.addActionListener(this);

        f = new FacturaCab();        
        cadBus = "";
//        
        setListener();
        iniciar();             
        
        int colHide[] = new int[5];
        colHide[0]=0; 
        colHide[1]=3; 
        colHide[2]=16; 
        colHide[3]=17;
        colHide[4]=18;
        

       setHideJtableColumn(visIngEgr.tblIngresosEgresos,colHide);        
       //setHideJtableColumn(visIngEgr.tblIngresosEgresosCons,colHide); 
       
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
        
      if (e.getSource() == visIngEgr.cmbTipoBusqueda) 
       {       
           String tipo = visIngEgr.cmbTipoBusqueda.getSelectedItem()+"";
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
           
               
        }
      
       if (e.getSource() == visIngEgr.cmbElegirBusquedaFac) 
       {
           String tipo = visIngEgr.cmbElegirBusquedaFac.getSelectedItem()+"";
           if (tipo.equals("pendientes")) {
               showTableIngrEgrePendientes();
               
            }
           if (tipo.equals("todos")) {
               showTableIngresosEgresos();
            }
           if (tipo.equals("eliminados")) {
               showTableIngresosEgresosAnulados();
            }
           
       }
       if (e.getSource() == visIngEgr.btnAgregarTrans) 
        {
            System.out.println("entro add");
           addRows(visIngEgr.tblIngresosEgresos);
          
        } 
        if (e.getSource() == visIngEgr.btnEliminarTrans) 
        {
           deleteRows(visIngEgr.tblIngresosEgresos);
           
        }  
                    
        
        if (e.getSource()==visIngEgr.btnBuscarPersonaIngEgr)
        {            
             Persona item = (Persona) visIngEgr.cmbPersonasIngEgr.getSelectedItem();
             if (item!=null) {
                  System.out.println(item.getId() + " : " + item.getApellido()+" "+item.getCedula());
                  showTableByIdPer(item.getCedula());
                  
            }
            
           
            
        }   
    }
    
    public void setListener()
    {               
        int col = 1;      
        JTable facDet = visIngEgr.tblIngresosEgresos;
        facDet.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        facDet.setColumnSelectionAllowed(true);
        facDet.setRowSelectionAllowed(true);
        
        KeyListener keyListenerTblDetalle = new KeyListener() {
          public void keyPressed(KeyEvent e) {

            
              if (e.getKeyCode()==KeyEvent.VK_F1 )
              {
               
              }
              
              if (e.getKeyCode()==KeyEvent.VK_F2 )
              {
               
              }
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
             int m=e.getKeyChar();
             System.out.println ("------------------------------------- "+m + " "+visIngEgr.tblIngresosEgresos.getSelectedColumn());
             
             
             int col =facDet.getSelectedColumn();
             int  row =0;
             if(m == KeyEvent.VK_ENTER) col = col+1;
             
              if (m == KeyEvent.VK_ENTER || m == KeyEvent.VK_TAB ) 
              {                              
                  switch(col)
                  {
                      case 2:
                        VisPersona visPer = new VisPersona();
                        ConsPersona consPer = new ConsPersona();
                        Persona per=new Persona();

                        CtrlPersonas ctrPer=new CtrlPersonas(per,consPer, visPer, visIngEgr);
                        ctrPer.locale = 5;
                        ctrPer.iniciar();
              
                          break;
                    case 3:                            
                          break;    
                    case 5: 
                        
                        VisProductos visProd = new VisProductos();
                        ConsProductos consProd = new ConsProductos();
                        Producto prod=new Producto();
                        
                        CtrlProductos ctrProd=new CtrlProductos(prod,consProd, visProd, visFicha,visIngEgr);
                        ctrProd.locale = 2;
                        ctrProd.iniciar();
                        break;
                   case 6:                            
                          break;
                    case 7:                            
                         break;
                    case 8:                            
                        break;
                    case 9:                            
                        break;
                    case 10:  
                            double valAju = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 12)+"");
                            double valPen=Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 11)+"");
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valPen,valAju ), row, 14);
                        break;
                     case 11:         
                            double valIng = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 8)+"");
                            double valEgr = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 9)+"");
                          
                            if (valIng>0 && valIng>valEgr) {                                                      
                                double valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 10)+"");                                
                                visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valIng,valCanc), row, 11);                                
                            }
                            if (valEgr>0 && valEgr>valIng) {    
                                double valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 10)+"");                                
                                visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valEgr,valCanc), row, 11);                              
                            }
                        break;
                    case 12:      
                        valAju = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 12)+"");
                             valPen=Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 11)+"");
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valPen,valAju ), row, 14);
                        break;
                    case 13:    
                            valAju = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 12)+"");
                             valPen=Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 11)+"");
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valPen,valAju ), row, 14);
                        break;
                        
                    case 14:       
                           
                        break;
                    case 15:                            
                            
                        break;
                    case 16:                            
                            
                        break;
                    default:
                        break;
                  }
                visIngEgr.tblIngresosEgresos.changeSelection(row, col,false,false);
              }                
              
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        visIngEgr.tblIngresosEgresos.addKeyListener(keyListenerTblDetalle);
    
        
        //cmblistener personas

       
        
        
        ///////combo
        
       
        
        
        //mouse listener tableingegr
        
        MouseListener mouseListTblIngEgr;
        mouseListTblIngEgr = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int col =facDet.getSelectedColumn();
                int  row =facDet.getSelectedRow();
                double valCanc = 0;
                double valEgr = 0;
                double valIng = 0;
               switch(col)
                  {
                      case 2:
                        VisPersona visPer = new VisPersona();
                        ConsPersona consPer = new ConsPersona();
                        Persona per=new Persona();

                        CtrlPersonas ctrPer=new CtrlPersonas(per,consPer, visPer, visIngEgr);
                        ctrPer.locale = 5;
                        ctrPer.iniciar();
              
                          break;
                    case 3:                            
                          break;    
                    case 5: 
                        
                        VisProductos visProd = new VisProductos();
                        ConsProductos consProd = new ConsProductos();
                        Producto prod=new Producto();
                        
                        CtrlProductos ctrProd=new CtrlProductos(prod,consProd, visProd, visFicha,visIngEgr);
                        ctrProd.locale = 2;
                        ctrProd.iniciar();
                        break;
                   case 6:                            
                          break;
                    case 7:                            
                         break;
                    case 8:                            
                        break;
                    case 9:                            
                        break;
                    case 10:                                                          
                             valIng = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 8)+"");
                             valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row,10)+"");
                          
                            if (valIng>0 && valIng>valEgr) {                                                      
                                 valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 10)+"");                                
                                visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valIng,valCanc), row, 11);
                              
                            }
                            if (valEgr>0 && valEgr>valIng) {    
                                 valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 10)+"");                                
                                visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valEgr,valCanc), row, 11);
                               
                            }
                            
                        break;
                     case 11:         
                             valIng = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 8)+"");
                             valEgr = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 9)+"");
                          
                            if (valIng>0 && valIng>valEgr) {                                                      
                                 valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 10)+"");                                
                                visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valIng,valCanc), row, 11);
                                visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valIng,valCanc), row, 14);
                            }
                            if (valEgr>0 && valEgr>valIng) {    
                                 valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 10)+"");                                
                                visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valEgr,valCanc), row, 11);
                                visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valEgr,valCanc), row, 14);
                            }
                        break;
                    case 12:        
                            double valAju = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 12)+"");
                            double valPen=Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 11)+"");
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valPen,valAju ), row, 14);
                        break;
                    case 13:    
                             valAju = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 12)+"");
                             valPen=Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 11)+"");
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valPen,valAju ), row, 14);
                        break;
                        
                    case 14:       
                           
                        break;
                    case 15:                            
                            
                        break;
                    case 16:                            
                            
                        break;
                    default:
                        break;
                  }
                System.out.println(visIngEgr.tblIngresosEgresos.getSelectedColumn()+"");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
               
            }
        
        };
        visIngEgr.tblIngresosEgresos.addMouseListener(mouseListTblIngEgr);
        
        
        
        ///**********************TXTBUSCARPORCAMPOS*************************
        
        KeyListener keyListenerTxtBuscarCampos = new KeyListener() {
          public void keyPressed(KeyEvent e) {

            
              if (e.getKeyCode()==KeyEvent.VK_F1 )
              {
               
              }
              
              if (e.getKeyCode()==KeyEvent.VK_F2 )
              {
               
              }
          }

          public void keyReleased(KeyEvent keyEvent) {
           
          }

          public void keyTyped(KeyEvent e) {
             int m=e.getKeyChar();                                
             int col =facDet.getSelectedColumn();
             int  row =0;
             if(m == KeyEvent.VK_ENTER) 
             {
                 String cadCamp = Validaciones.isNumVoid4(visIngEgr.txtBuscarCampo.getText());
                 showTableIngresosEgresosCampos(cadCamp);
                 System.out.println("aqui estoy...");
             }
          }         
                                                              
        };
        visIngEgr.txtBuscarCampo.addKeyListener(keyListenerTxtBuscarCampos);
    }
    
    public void addRows(JTable table)
    {        
         Object cols[] = new Object[21];
         table.getColumnModel().getColumn(1).setCellEditor(new JDateChooserCellEditor());
         table.getColumnModel().getColumn(6).setCellEditor(new JDateChooserCellEditor());
         table.getColumnModel().getColumn(7).setCellEditor(new JDateChooserCellEditor());
         
         DefaultTableModel tb = (DefaultTableModel) table.getModel();         
         for (int i = 0; i <= 16; i++) {
            cols[i] = new String();
        }
         cols[1] = Calculos.getCurrentDate2();
         cols[19]="Guardar";
         cols[20]="Anular";
         
       //  setFormatTable(table);
        tb.insertRow(0, cols);
        //tb.addRow(cols);
        
        new ButtonTableIngresosEgresos(visIngEgr);
        visIngEgr.tblIngresosEgresos.updateUI();
      // System.out.println("llego");
    }
    
    public void setFormatTable(JTable table)
    {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(5).setCellRenderer(tcr);
        table.getColumnModel().getColumn(3).setCellRenderer(tcr);                        
        
        int colHide[] = new int[1];
        colHide[0]=0;       
        setHideJtableColumn(table,colHide);
        
        //initColumnSizes(table);        
        table.setCellSelectionEnabled(false);        
    } 
    
    private void initColumnSizes(JTable table) {
        
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(250);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(7).setPreferredWidth(100);
      
    
    }
    public void deleteRows(JTable table)
    {
        DefaultTableModel tb = (DefaultTableModel) table.getModel(); 
        int n =0 ;
        n = table.getSelectedRow();
        if (n>=0) 
            tb.removeRow(n);
        else
            Validaciones.getMensaje("Debe seleccionar una fila para eliminar");  
    }
    public void iniciar()
    {
        visIngEgr.setTitle(Configuracion.nomEmp +" INGRESOS EGRESOS TRANSACCIONALES");        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;

        visIngEgr.setSize(width, 600);
        visIngEgr.setLocationRelativeTo(null);
        visIngEgr.setVisible(true);        
        showTableIngresosEgresos();    
        initColumnSizes(visIngEgr.tblIngresosEgresos);
        
        showComboPersonas();
        AutoCompleteDecorator.decorate(visIngEgr.cmbPersonasIngEgr); 
                
    }
    
    public void showTablePendientes()
    {
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresosCons);
            ResultSet listFicha = consIngEgr.buscarPendientes();
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresosCons.getModel();
            Object cols[] = new Object[10];
            double diff;
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
                    diff = (double)cols[8]-(double)cols[9];
                    if (Math.abs(diff)>0) {
                         model.addRow(cols);
                    }
                   
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visIngEgr.tblIngresosEgresosCons.updateUI();
    }
    
    public void showTableProximosVencer()
    {
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresosCons);
            ResultSet listFicha = consIngEgr.buscarTodos2();
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresosCons.getModel();
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
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visIngEgr.tblIngresosEgresosCons.updateUI();
    }
    
    public void showTableCursando()
    {
        try {
             limpiarTabla(visIngEgr.tblIngresosEgresosCons);
            ResultSet listFicha = consIngEgr.buscarTodos2();
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresosCons.getModel();
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
                    if (Calculos.dateGreaterThanCurrent(cols[4].toString())) {
                        model.addRow(cols);                     
                    }                       
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visIngEgr.tblIngresosEgresosCons.updateUI();
    }
    public void showTableByNom(String nom)
    {
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresosCons);
            
            ResultSet listFicha = consIngEgr.buscarTodosPorNomTabla(nom);
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresosCons.getModel();
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
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void showTableByIdPer(String id)
    {
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresos);
            String nombres = Validaciones.isNumVoid4(visIngEgr.txtBuscarCampo.getText());
            ResultSet listFicha = consIngEgr.buscarIngresosEgresosId(id);
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresos.getModel();
            Object cols[] = new Object[21];
         
           
            while (listFicha.next()) {
                try { 
                                      
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("Fecha_Faccab");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("Num_Faccab"));
                    cols[4] = listFicha.getString("cant");
                    cols[5] = listFicha.getString("Descripcion_Facdet");
                    cols[6] = listFicha.getString("FechaInicio");
                    cols[7] = listFicha.getString("FechaFin");
                    cols[8] = listFicha.getString("Total_Faccab");
                    cols[9] = listFicha.getDouble("egreso");
                    cols[10] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[11] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[12] = listFicha.getDouble("Valajuste_Faccab");
                    cols[13] = listFicha.getDouble("estadoEnt");
                    cols[14] = listFicha.getDouble("VALPENDIENTE_FACCAB");
                    cols[15] = listFicha.getDouble("saldoP");
                    cols[16] = listFicha.getInt("id_per");
                    cols[17] = listFicha.getInt("id_prod");
                    cols[18] = listFicha.getInt("codHist");
                    
                    cols[19] = "Guardar";
                    cols[20] = "Anular";
                    cols[14] = Calculos.getDiferencia((double)cols[11], (double)cols[12]);  
                    

                    model.addRow(cols);
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTableIngresosEgresos(visIngEgr);
        visIngEgr.tblIngresosEgresos.updateUI();

    }
     
      public void limpiarTabla(JTable table){
        DefaultTableModel tb = (DefaultTableModel) table.getModel();
        int a = table.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
      
    
    public void showTableIngresosEgresos()
    {
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresos);
            String nombres = Validaciones.isNumVoid4(visIngEgr.txtBuscarCampo.getText());
            ResultSet listFicha = consIngEgr.buscarIngresosEgresos(nombres);
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresos.getModel();
            Object cols[] = new Object[21];
         
           
            while (listFicha.next()) {
                try { 
                                      
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("Fecha_Faccab");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("Num_Faccab"));
                    cols[4] = listFicha.getString("cant");
                    cols[5] = listFicha.getString("Descripcion_Facdet");
                    cols[6] = listFicha.getString("FechaInicio");
                    cols[7] = listFicha.getString("FechaFin");
                    cols[8] = listFicha.getString("Total_Faccab");
                    cols[9] = listFicha.getDouble("egreso");
                    cols[10] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[11] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[12] = listFicha.getDouble("Valajuste_Faccab");
                    cols[13] = listFicha.getDouble("estadoEnt");
                    cols[14] = listFicha.getDouble("VALPENDIENTE_FACCAB");
                    cols[15] = listFicha.getDouble("saldoP");
                    cols[16] = listFicha.getInt("id_per");
                    cols[17] = listFicha.getInt("id_prod");
                    cols[18] = listFicha.getInt("codHist");
                    
                    cols[19] = "Guardar";
                    cols[20] = "Anular";
                    cols[14] = Calculos.getDiferencia((double)cols[11], (double)cols[12]);  
                    

                    model.addRow(cols);
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTableIngresosEgresos(visIngEgr);
        visIngEgr.tblIngresosEgresos.updateUI();
    }
    
    public void showComboPersonas()
    {
       
        ConsPersona consPer = new ConsPersona();
        visIngEgr.cmbPersonasIngEgr.removeAllItems();
        try {
           
            ResultSet listCategorias = consPer.buscarPersonasClientes();
            
            DefaultComboBoxModel model =  (DefaultComboBoxModel)visIngEgr.cmbPersonasIngEgr.getModel();
           
            
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
        visIngEgr.cmbPersonasIngEgr.setRenderer(new ItemRendererClienteIngEgr());
        this.visIngEgr.cmbPersonasIngEgr.addActionListener(this);        
        visIngEgr.cmbPersonasIngEgr.updateUI();
    }
    public void showTableIngresosEgresosCampos(String cadCampo)
    {
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresos);
            ResultSet listFicha = consIngEgr.buscarIngresosEgresosCampos(cadCampo);
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresos.getModel();
            Object cols[] = new Object[21];
           
            while (listFicha.next()) {
                try { 
                                      
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("Fecha_Faccab");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("Num_Faccab"));
                    cols[4] = listFicha.getString("cant");
                    cols[5] = listFicha.getString("Descripcion_Facdet");
                    cols[6] = listFicha.getString("FechaInicio");
                    cols[7] = listFicha.getString("FechaFin");
                    cols[8] = listFicha.getString("Total_Faccab");
                    cols[9] = listFicha.getDouble("egreso");
                    cols[10] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[11] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[12] = listFicha.getDouble("Valajuste_Faccab");
                    cols[13] = listFicha.getDouble("estadoEnt");
                    cols[14] = listFicha.getDouble("VALPENDIENTE_FACCAB");
                    cols[15] = listFicha.getDouble("saldoP");
                    cols[16] = listFicha.getInt("id_per");
                    cols[17] = listFicha.getInt("id_prod");
                    cols[18] = listFicha.getInt("codHist");
                    
                    cols[19] = "Guardar";
                    cols[20] = "Anular";
                    cols[14] = Calculos.getDiferencia((double)cols[11], (double)cols[12]);    
                    
                    model.addRow(cols);
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTableIngresosEgresos(visIngEgr);
        visIngEgr.tblIngresosEgresos.updateUI();
    }
    
    
    public void showTableIngresosEgresosAnulados()
    {
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresos);
            ResultSet listFicha = consIngEgr.buscarIngresosEgresosEliminados();
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresos.getModel();
            Object cols[] = new Object[21];
           
            while (listFicha.next()) {
                try { 
                                      
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("Fecha_Faccab");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("Num_Faccab"));
                    cols[4] = listFicha.getString("cant");
                    cols[5] = listFicha.getString("Descripcion_Facdet");
                    cols[6] = listFicha.getString("FechaInicio");
                    cols[7] = listFicha.getString("FechaFin");
                    cols[8] = listFicha.getString("Total_Faccab");
                    cols[9] = listFicha.getDouble("egreso");
                    cols[10] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[11] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[12] = listFicha.getDouble("Valajuste_Faccab");
                    cols[13] = listFicha.getDouble("estadoEnt");
                    cols[14] = listFicha.getDouble("VALPENDIENTE_FACCAB");
                    cols[15] = listFicha.getDouble("saldoP");
                    cols[16] = listFicha.getInt("id_per");
                    cols[17] = listFicha.getInt("id_prod");
                    cols[18] = listFicha.getInt("codHist");
                    
                    cols[19] = "Guardar";
                    cols[20] = "Anular";
                    cols[14] = Calculos.getDiferencia((double)cols[11], (double)cols[12]);  
                    
                    model.addRow(cols);
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTableIngresosEgresos(visIngEgr);
        visIngEgr.tblIngresosEgresos.updateUI();
    } 
    
    public void showTableFacturasCabecerasByNom(String nom)
    {
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresos);
            ResultSet listFicha = consIngEgr.buscarFacturasByNom(nom);
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresos.getModel();
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
            
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTableIngresosEgresos(visIngEgr);
        visIngEgr.tblIngresosEgresos.updateUI();
    } 
      
    public void showTable()
    {
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresosCons);
            ResultSet listFicha = consIngEgr.buscarTodos2();
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresosCons.getModel();
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
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visIngEgr.tblIngresosEgresosCons.updateUI();
    }
    
    public void showTableIngrEgrePendientes()
    {
         try {
            limpiarTabla(visIngEgr.tblIngresosEgresos);
            String nombres = Validaciones.isNumVoid4(visIngEgr.txtBuscarCampo.getText());
            ResultSet listFicha = consIngEgr.buscarIngresosEgresos(nombres);
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresos.getModel();
            Object cols[] = new Object[21];
            double diffAjusteCanc;
            while (listFicha.next()) {
                try {
                  
                    
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("Fecha_Faccab");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("Num_Faccab"));
                    cols[4] = listFicha.getString("cant");
                    cols[5] = listFicha.getString("Descripcion_Facdet");
                    cols[6] = listFicha.getString("FechaInicio");
                    cols[7] = listFicha.getString("FechaFin");
                    cols[8] = listFicha.getString("Total_Faccab");
                    cols[9] = listFicha.getDouble("egreso");
                    cols[10] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[11] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[12] = listFicha.getDouble("Valajuste_Faccab");
                    cols[13] = listFicha.getDouble("estadoEnt");
                    cols[14] = listFicha.getDouble("VALPENDIENTE_FACCAB");
                    cols[15] = listFicha.getDouble("saldoP");
                    cols[16] = listFicha.getInt("id_per");
                    cols[17] = listFicha.getInt("id_prod");
                    cols[18] = listFicha.getInt("codHist");
                    
                    cols[19] = "Guardar";
                    cols[20] = "Anular";
                    cols[14] = Calculos.getDiferencia((double)cols[11], (double)cols[12]);   
                   
                    diffAjusteCanc =Calculos.getDiferencia( (double)Validaciones.isNumVoid2(cols[8]+""), (double)Validaciones.isNumVoid2(cols[10]+""));
                    
                    if (Math.abs(diffAjusteCanc)>0 && (double)Validaciones.isNumVoid2(cols[8]+"")>0) {
                         model.addRow(cols);
                    }
                   
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTableIngresosEgresos(visIngEgr);
        visIngEgr.tblIngresosEgresos.updateUI();
    }
     
     public void showTableVencidos()
    {
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresosCons);
            ResultSet listFicha = consIngEgr.buscarTodos2();
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresosCons.getModel();
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
                    if (!Calculos.dateGreaterThanCurrent(cols[4].toString()))
                        model.addRow(cols);
                     
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visIngEgr.tblIngresosEgresosCons.updateUI();
    }
     
    public void showTableDetalles(int idFac)
    {
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresosCons);
            ResultSet listFicha = consIngEgr.buscarDetallesByIdFac(idFac);
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresosCons.getModel();
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
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
        visIngEgr.tblIngresosEgresosCons.updateUI();
    }  
}
