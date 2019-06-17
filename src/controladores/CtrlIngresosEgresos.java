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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import modelos.Analisis;
import modelos.Categoria;
import modelos.FacturaCab;
import modelos.Medidas;
import modelos.Persona;
import modelos.Producto;
import vistas.VisBuscarPersonas;
import vistas.VisBuscarVentas;
import vistas.VisFicha;
import vistas.VisIngresoEgreso;
import vistas.VisPersona;
import vistas.VisProductos;
import vistas.VisReportes;
import visual.facturacion.MyTableModel;

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
        
        f = new FacturaCab();
        
        cadBus = "";
//        
        setListener();
        iniciar();             
        
        int colHide[] = new int[5];
        colHide[0]=0; 
        colHide[1]=3; 
        colHide[2]=15; 
        colHide[3]=16;
        colHide[4]=17;
        
        
       // colHide[1]=6;
        setHideJtableColumn(visIngEgr.tblIngresosEgresos,colHide);        
        //setHideJtableColumn(visIngEgr.tbl_BuscarVentas,colHide);
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
                /*
                  VisPersona visPer = new VisPersona();
                  ConsPersona consPer = new ConsPersona();
                  Persona per=new Persona();
                  
                  CtrlPersonas ctrPer=new CtrlPersonas(per,consPer, visPer, visIngEgr);
                  ctrPer.locale = 5;
                  ctrPer.iniciar();
                  */
              }
              
              if (e.getKeyCode()==KeyEvent.VK_F2 )
              {
                /*
                VisProductos visProd = new VisProductos();
                ConsProductos consProd = new ConsProductos();
                Producto prod=new Producto();
                Categoria cat=new Categoria();

                CtrlProductos ctrProd=new CtrlProductos(prod,consProd, visProd, visFicha,visFicha);
                ctrProd.locale = 2;
                ctrProd.iniciar();
                        */
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
                    case 4: 
                        
                        VisProductos visProd = new VisProductos();
                        ConsProductos consProd = new ConsProductos();
                        Producto prod=new Producto();
                        
                        CtrlProductos ctrProd=new CtrlProductos(prod,consProd, visProd, visFicha,visIngEgr);
                        ctrProd.locale = 2;
                        ctrProd.iniciar();
                        break;
                   case 5:                            
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
                            double valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 9)+"");
                            double valIng=Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 7)+"");
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valIng,valCanc), row, 10);
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valIng,valCanc), row, 13);
                        break;
                    case 11:                            
                        break;
                    case 12:    
                            double valAju = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 11)+"");
                            double valPen=Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 10)+"");
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valPen,valAju ), row, 13);
                        break;
                        
                    case 13:       
                           
                        break;
                    case 14:                            
                            
                        break;
                    case 15:                            
                            
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
    
        //mouse listener tableingegr
        
        MouseListener mouseListTblIngEgr;
        mouseListTblIngEgr = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int col =facDet.getSelectedColumn();
                int  row =facDet.getSelectedRow();
                double valCanc = 0;
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
                    case 4: 
                        
                        VisProductos visProd = new VisProductos();
                        ConsProductos consProd = new ConsProductos();
                        Producto prod=new Producto();
                        
                        CtrlProductos ctrProd=new CtrlProductos(prod,consProd, visProd, visFicha,visIngEgr);
                        ctrProd.locale = 2;
                        ctrProd.iniciar();
                        break;
                   case 5:                            
                          break;
                    case 6:                            
                         break;
                    case 7:                            
                        break;
                    case 8:                            
                        break;
                    case 9:  
                             valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 9)+"");
                             valIng=Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 7)+"");
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valIng,valCanc), row, 10);
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valIng,valCanc), row, 13);
                            
                        break;
                     case 10:         
                             valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 9)+"");
                             valIng=Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 7)+"");
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valIng,valCanc), row, 10);
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valIng,valCanc), row, 13);
                        break;
                    case 11:                            
                        break;
                    case 12:    
                            double valAju = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 11)+"");
                            double valPen=Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(row, 10)+"");
                            visIngEgr.tblIngresosEgresos.setValueAt(Calculos.getDiferencia(valPen,valAju ), row, 13);
                        break;
                        
                    case 13:       
                           
                        break;
                    case 14:                            
                            
                        break;
                    case 15:                            
                            
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
    }
    
    public void addRows(JTable table)
    {        
         Object cols[] = new Object[19];
         table.getColumnModel().getColumn(1).setCellEditor(new JDateChooserCellEditor());
         table.getColumnModel().getColumn(5).setCellEditor(new JDateChooserCellEditor());
         table.getColumnModel().getColumn(6).setCellEditor(new JDateChooserCellEditor());
         
         DefaultTableModel tb = (DefaultTableModel) table.getModel();         
         for (int i = 0; i <= 15; i++) {
            cols[i] = new String();
        }
         cols[17]="Guardar";
         cols[18]="Anular";
         
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
        table.getColumnModel().getColumn(4).setCellRenderer(tcr);
        table.getColumnModel().getColumn(3).setCellRenderer(tcr);                        
        
        int colHide[] = new int[1];
        colHide[0]=0;
       
        setHideJtableColumn(table,colHide);
        
        initColumnSizes(table);
        
        table.setCellSelectionEnabled(false);
        
    } 
    
    private void initColumnSizes(JTable table) {
		TableColumn column = null;
        for (int i = 0; i < 3; i++) {
        	column = table.getColumnModel().getColumn(i);
            if(i==2){
            	column.setPreferredWidth(400);
            }
        }
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
        visIngEgr.setSize(1400, 800);
        visIngEgr.setLocation(300, 100);
        visIngEgr.setVisible(true);
       
        showTableIngresosEgresos();
                
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
            ResultSet listFicha = consIngEgr.buscarIngresosEgresos();
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresos.getModel();
            Object cols[] = new Object[20];
           
            while (listFicha.next()) {
                try { 
                                      
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("Fecha_Faccab");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("Num_Faccab"));
                    cols[4] = listFicha.getString("Descripcion_Facdet");
                    cols[5] = listFicha.getString("FechaInicio");
                    cols[6] = listFicha.getString("FechaFin");
                    cols[7] = listFicha.getString("Total_Faccab");
                    cols[8] = listFicha.getDouble("egreso");
                    cols[9] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[10] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[11] = listFicha.getDouble("Valajuste_Faccab");
                    cols[12] = listFicha.getDouble("estadoEnt");
                    cols[13] = listFicha.getDouble("VALPENDIENTE_FACCAB");
                    cols[14] = listFicha.getDouble("saldoP");
                    cols[15] = listFicha.getInt("id_per");
                    cols[16] = listFicha.getInt("id_prod");
                    cols[17] = listFicha.getInt("codHist");
                    
                    cols[18] = "Guardar";
                    cols[19] = "Anular";
                    cols[13] = Calculos.getDiferencia((double)cols[10], (double)cols[11]);  
                    
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
            Object cols[] = new Object[19];
           
            while (listFicha.next()) {
                try { 
                                      
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("Fecha_Faccab");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("Num_Faccab"));
                    cols[4] = listFicha.getString("Descripcion_Facdet");
                    cols[5] = listFicha.getString("FechaInicio");
                    cols[6] = listFicha.getString("FechaFin");
                    cols[7] = listFicha.getString("Total_Faccab");
                    cols[8] = listFicha.getDouble("egreso");
                    cols[9] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[10] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[11] = listFicha.getDouble("Valajuste_Faccab");
                    cols[12] = listFicha.getDouble("estadoEnt");
                    cols[13] = listFicha.getDouble("VALPENDIENTE_FACCAB");
                    cols[14] = listFicha.getDouble("saldoP");
                    cols[15] = listFicha.getInt("id_per");
                    cols[16] = listFicha.getInt("id_prod");
                    cols[17] = listFicha.getInt("codHist");
                    
                    cols[18] = "Guardar";
                    cols[19] = "Anular";
                    cols[13] = Calculos.getDiferencia((double)cols[10], (double)cols[11]);
                   
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
            ResultSet listFicha = consIngEgr.buscarIngresosEgresos();
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresos.getModel();
            Object cols[] = new Object[20];
            double diffAjusteCanc;
            while (listFicha.next()) {
                try {
                  
                    
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("Fecha_Faccab");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("Num_Faccab"));
                    cols[4] = listFicha.getString("Descripcion_Facdet");
                    cols[5] = listFicha.getString("FechaInicio");
                    cols[6] = listFicha.getString("FechaFin");
                    cols[7] = listFicha.getString("Total_Faccab");
                    cols[8] = listFicha.getDouble("egreso");
                    cols[9] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[10] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[11] = listFicha.getDouble("Valajuste_Faccab");
                    cols[12] = listFicha.getDouble("estadoEnt");
                    cols[13] = listFicha.getDouble("VALPENDIENTE_FACCAB");
                    cols[14] = listFicha.getDouble("saldoP");
                    cols[15] = listFicha.getInt("id_per");
                    cols[16] = listFicha.getInt("id_prod");
                    cols[17] = listFicha.getInt("codHist");
                    
                    cols[18] = "Guardar";
                    cols[19] = "Anular";
                    cols[13] = Calculos.getDiferencia((double)cols[9], (double)cols[11]);   
                   
                    diffAjusteCanc = (double)cols[10] - (double)cols[11];
                    
                    if (Math.abs(diffAjusteCanc)>0) {
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
