/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Calculos;
import assets.Configuracion;
import assets.ItemRendererCalcFechaServ;
import assets.ItemRendererProducto;
import assets.Java2sAutoComboBox;
import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDateChooserCellEditor;
import consultas.ConsAnalisis;

import consultas.ConsFacturaCab;
import consultas.ConsFicha;
import consultas.ConsMedidas;
import consultas.ConsMembresias;
import consultas.ConsPersona;
import consultas.ConsProductos;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import modelos.Analisis;
import modelos.CalculoFechaServicio;
import modelos.Categoria;

import modelos.FacturaCab;
import modelos.Ficha;
import modelos.Medidas;
import modelos.Membresias;
import modelos.Persona;
import modelos.Producto;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import vistas.VisBuscarVentas;

import vistas.VisFicha;
import vistas.VisIngresoEgreso;
import vistas.VisMembresia;
import vistas.VisPersona;
import vistas.VisProductos;
import vistas.VisReportes;

/**
 *
 * @author Administrator
 */
public class CtrlProductos implements ActionListener{

    
    VisProductos visProd;
    Producto prod;
    ConsProductos consProd;
   
    VisFicha visFicha;
    Categoria catProd;
    VisIngresoEgreso visIngEgr;
    Object vis;
    
    String cadBus;
    int locale;
    
    public CtrlProductos(Producto prod, ConsProductos consProd,VisProductos visProd,VisFicha visFicha,Object vis)
    {
       
        this.prod = prod;
        this.consProd = consProd;        
        this.visProd =  visProd;
        this.visFicha = visFicha;
                        
        this.visProd.btnGuardar.addActionListener(this);
        this.visProd.btnEliminar.addActionListener(this);
        this.visProd.btnLimpiar.addActionListener(this);
        this.visProd.btnModificar.addActionListener(this);
        this.visProd.rdbProductos.addActionListener(this);
        this.visProd.rdbServicios.addActionListener(this);
        this.visProd.rdbTodos.addActionListener(this);
        this.visProd.cmbCalcFechaServ.addActionListener(this);
        
        this.visProd.txt_id.setVisible(false);
        this.visProd.lblIdCat.setVisible(false);
        this.vis = vis;
        
        catProd = new Categoria();
              
        cadBus = "";
        locale = 0; //1:menu , 2:factura
       
        setFocus();
        setListener();    

        limpiarTabla();
                
        setFormatTable(visProd.tbl_productos);
        escribirCombos();
     //   setTableModel();
        
        int colHide[] = new int[3];
        colHide[0]=0;
        colHide[1]=10;
        colHide[2]=11;
       
        setHideJtableColumn(visProd.tbl_productos,colHide);
    }
    
    private void escribirCombos()
      {
        AutoCompleteDecorator.decorate(visProd.cbxCategoria); 
        AutoCompleteDecorator.decorate(visProd.cmbCalcFechaServ); 
      }  
    
    public void setHideJtableColumn(JTable table, int col[])
    {
        for (int i = 0; i < col.length; i++) {
            table.getColumnModel().getColumn(col[i]).setMaxWidth(0);
            table.getColumnModel().getColumn(col[i]).setMinWidth(0);
            table.getColumnModel().getColumn(col[i]).setPreferredWidth(0);
        }       
    
    }
    public void iniciar()
    {
        visProd.setTitle(Configuracion.nomEmp +" PRODUCTOS/SERVICIOS");    
        //visProd.lblNomEmp.setText(Configuracion.nomEmp);
        visProd.btnGuardar.setToolTipText("Guardar el registro");
        visProd.btnModificar.setToolTipText("Modificar el registro");
        visProd.btnEliminar.setToolTipText("Eliminar el registro");
        visProd.btnLimpiar.setToolTipText("Limpiar el registro");
        
        showDatosComboTable();
        showComboCalcFechaServ();
        limpiar();
        
        visProd.setLocation(300,10); 
        visProd.setSize(1000,700);                
        visProd.setVisible(true);
    }
    
    public void setTableModel()
    {
      visProd.tbl_productos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
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
    public void showTableByNom(String cad)
    {
        try {
            limpiarTabla();
            
            ResultSet listProd = consProd.buscarTodosPorNomTabla(cad);
            
            DefaultTableModel model =  (DefaultTableModel)visProd.tbl_productos.getModel();
            Object cols[] = new Object[11];
            
            while (listProd.next()) {
                try {
                    cols[0] = listProd.getInt("id_prod");
                    cols[1] = listProd.getString("descripcion_prod").toUpperCase();
                    cols[2] = listProd.getString("precio_prod").toUpperCase();
                    cols[3] = listProd.getString("FECHAINI_PROD");
                    cols[4] = listProd.getString("FECHAFIN_PROD");
                    cols[5] = listProd.getString("tipo_cat").toUpperCase();
                    cols[6] = listProd.getString("EXISTINI").toUpperCase();
                    cols[7] = listProd.getString("ENTRADAS").toUpperCase();
                    cols[8] = listProd.getString("SALIDAS").toUpperCase();
                    cols[9] = listProd.getString("STOCK").toUpperCase();
                    cols[10] = listProd.getString("id_cat");                                                       
                 
                    model.addRow(cols);                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consProd.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public void desabilitaHabilita(JButton btn,boolean estado)
     {
         btn.setEnabled(estado);
     }
     
     public void setProductoServicioIngresoEgreso()
     {
       JTable table = ((VisIngresoEgreso)vis).tblIngresosEgresos;
       int filaDetalle = table.getSelectedRow();
       
       int idProd = Integer.parseInt(visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 0)+"");
       int idCat = Integer.parseInt(visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 10)+"");
       String descripcionProd = visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 1)+"";
       double precioProd =  Validaciones.isNumVoid10(visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 2)+"");
       
       String fechaIni = visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 3)+"";
       String fechaFin = visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 4)+"";
       
       table.setValueAt(descripcionProd, filaDetalle, 5);
       table.setValueAt(idProd, filaDetalle, 17);
       int cant = Validaciones.isNumVoid(table.getValueAt(table.getSelectedRow(), 4)+"");
       String ingEgr = Validaciones.getMsgOption().toUpperCase()+"";
         if (ingEgr.equals("INGRESO")) {
             table.setValueAt(precioProd*cant, filaDetalle, 8);
             table.setValueAt("0.0", filaDetalle, 9);
         }
         if (ingEgr.equals("EGRESO")) {
              table.setValueAt(precioProd*cant, filaDetalle, 9);
              table.setValueAt("0.0", filaDetalle, 8);
         }
         System.out.println(">>> "+idCat);
         if (idCat==1) 
             table.changeSelection(filaDetalle, 6,false,false);        
         else             
            table.changeSelection(filaDetalle, 10,false,false);
        
       
       /*
       JDateChooserCellEditor jd = new JDateChooserCellEditor();
       
       DefaultTableModel tb = (DefaultTableModel) table.getModel();   
       
       Date m = null;
       
       jd = (JDateChooserCellEditor)table.getColumnModel().getColumn(7).getCellEditor();
       JDateChooser jDate = (JDateChooser)jd.getTableCellEditorComponent(table, jd, true, 1, 7);
       jDate.setDate(Validaciones.setStringToDate(fechaIni));
       
       System.out.println(jDate.getDate().toString());*/
       //jd.setDate(Validaciones.setStringToDate(fechaIni));
       //table.getColumnModel().getColumn(7).setCellEditor(new JDateChooserCellEditor());
      //table.setValueAt(jd, filaDetalle, 7);
       
       /*
       public Object getCellEditorValue() 
        {
          return new String(((JTextField)date.getDateEditor().getUiComponent()).getText());
        }
       
       */
       //visFicha.tblFacturaDetalle.setValueAt(precio, filaDetalle, 3);

     //  Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalle);                            
      // Calculos.setTotalesCabecera(visFicha.tblFacturaDetalle, visFicha);
       visProd.dispose();
     }
     
     public void setProductoServicioFactura()
     {
        int filaDetalle = visFicha.tblFacturaDetalle.getRowCount()-1;
       int idProd = Integer.parseInt(visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 0)+"");
       String descripcion = visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 1)+"";
       double precio =  Validaciones.isNumVoid10(visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 2)+"");


       visFicha.tblFacturaDetalle.setValueAt(idProd, filaDetalle, 0);                            
     //  visFicha.tblFacturaDetalle.setValueAt(1, filaDetalle, 1);
       visFicha.tblFacturaDetalle.setValueAt(descripcion, filaDetalle, 3);
       visFicha.tblFacturaDetalle.setValueAt(precio, filaDetalle, 4);

       Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalle);                            
       Calculos.setTotalesCabecera(visFicha.tblFacturaDetalle, visFicha);
       visProd.dispose();
     }
     
      public void setProductoServicioFacturaCompra()
     {
        int filaDetalle = visFicha.tblFacturaDetalleCompras.getRowCount()-1;
       int idProd = Integer.parseInt(visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 0)+"");
       String descripcion = visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 1)+"";
       double precio =  Validaciones.isNumVoid10(visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 2)+"");


       visFicha.tblFacturaDetalleCompras.setValueAt(idProd, filaDetalle, 0);                            
       //visFicha.tblFacturaDetalleCompras.setValueAt(1, filaDetalle, 1);
       visFicha.tblFacturaDetalleCompras.setValueAt(descripcion, filaDetalle, 2);
       visFicha.tblFacturaDetalleCompras.setValueAt(precio, filaDetalle, 3);

       Calculos.calcularTotalDetallesComp(visFicha.tblFacturaDetalleCompras);                            
       Calculos.setTotalesCabeceraCompras(visFicha.tblFacturaDetalleCompras, visFicha);
       visProd.dispose();
     }
        
     
    public void setListener(){
        KeyListener keyListenertxtBuscarProductosPorCualquierCampo = new KeyListener() {
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
        visProd.txtBuscarCualquierCampo.addKeyListener(keyListenertxtBuscarProductosPorCualquierCampo);

        MouseListener mouseListTblProd = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                     getTableToTxts();
                     desabilitaHabilita(visProd.btnGuardar,false);
                     desabilitaHabilita(visProd.btnModificar,true);
                                          
                }
                if(e.getClickCount()==2)
                {
                    switch(locale)
                    {
                        case 0 :
                             setProductoServicioFactura();
                            break;
                        case 1:
                             setProductoServicioFacturaCompra();
                            break;
                        case 2:
                            setProductoServicioIngresoEgreso();
                            break;
                        case 3:
                            
                            break;
                        default:
                            break;
                    }
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
       
        visProd.tbl_productos.addMouseListener(mouseListTblProd);
      //key listener 
        
        KeyListener keyListenerTblDetalle = new KeyListener() {
          public void keyPressed(KeyEvent e) {

             
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
             int m=e.getKeyChar();
             int filaDetalle = visFicha.tblFacturaDetalle.getRowCount()-1;
          
              if (m == KeyEvent.VK_ENTER  ) 
              {
                  int idProd = Integer.parseInt(visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 0)+"");
                String descripcion = visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 1)+"";
                double precio =  Validaciones.isNumVoid10(visProd.tbl_productos.getValueAt(visProd.tbl_productos.getSelectedRow(), 2)+"");


                visFicha.tblFacturaDetalle.setValueAt(idProd, filaDetalle, 0);                            
                visFicha.tblFacturaDetalle.setValueAt(1, filaDetalle, 1);
                visFicha.tblFacturaDetalle.setValueAt(descripcion, filaDetalle, 2);
                visFicha.tblFacturaDetalle.setValueAt(precio, filaDetalle, 3);

                Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalle);                            
                Calculos.setTotalesCabecera(visFicha.tblFacturaDetalle, visFicha);
                visProd.dispose();
              }
                
              
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        visProd.tbl_productos.addKeyListener(keyListenerTblDetalle);
    }
    
    public void setFormatTable(JTable table)
    {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.LEFT);
        //table.getColumnModel().getColumn(4).setCellRenderer(tcr);
       // table.getColumnModel().getColumn(3).setCellRenderer(tcr);                        
        
        int colHide[] = new int[2];
      //  colHide[0]=0;
        colHide[1]=10;
       // setHideJtableColumn(table,colHide);
        
        //initColumnSizes(table);
        
        table.setCellSelectionEnabled(false);
        
    }  
     private void initColumnSizes(JTable table) {
		TableColumn column = null;
        for (int i = 0; i < 3; i++) {
        	column = table.getColumnModel().getColumn(i);
            if(i==0){
            	column.setPreferredWidth(100);
            }
        }
    }
  
     public void getTableToTxts()
     {
        // limpiar();
         JTable tblD = visProd.tbl_productos;
         System.out.println(tblD.getSelectedRow());
         visProd.txt_id.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));
         visProd.txtDescripcionProd.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1)));
         visProd.txtPrecioProd.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 2)));
         visProd.dchFechaIni.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 3))));
         visProd.dchFechaFin.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 4))));
         visProd.cbxCategoria.setSelectedItem(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 5)));
         visProd.txtExistentes.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 6)));
         visProd.txtEntradas.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 7)));
         visProd.txtSalidas.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 8)));       
         visProd.lblIdCat.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 10)));
         int idCalcFecha = Validaciones.isNumVoid(tblD.getValueAt(tblD.getSelectedRow(), 11)+""); //0 nada
         visProd.cmbCalcFechaServ.setSelectedItem(getCalcFechaServicio(idCalcFecha));
     }
    
     public CalculoFechaServicio getCalcFechaServicio(int idC)
     {
         //(Persona) visFicha.cmb_clienteFac.getSelectedItem();
         DefaultComboBoxModel model =  (DefaultComboBoxModel) visProd.cmbCalcFechaServ.getModel();
         CalculoFechaServicio calc = new CalculoFechaServicio();
         for (int i = 0; i < model.getSize(); i++) {
              calc = (CalculoFechaServicio)model.getElementAt(i);
             if (calc.getId_calServ()==idC) {
                 System.out.println(" " +calc.getId_calServ() + " "+calc.getDescripcion_calServ());
                calc = (CalculoFechaServicio)model.getElementAt(i);
                break;
             }
           else
                 calc = null;
         }
         return calc;
     }
     
     
     public void setFocus()
    {
        visProd.txtBuscarCualquierCampo.requestFocus();
        
        visProd.txtDescripcionProd.setNextFocusableComponent(visProd.txtPrecioProd); 
        visProd.txtPrecioProd.setNextFocusableComponent(visProd.cbxCategoria); 
        
        
    }
     
     public void limpiarComboProdServ()
     {
         visProd.cbxCategoria.removeAllItems();
     }
     
     public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visProd.tbl_productos.getModel();
        int a = visProd.tbl_productos.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
    public void showComboProductos()
    {
        visProd.cbxCategoria.removeAllItems();
        try {
           
            ResultSet listCategorias = consProd.buscarCategoriasProductos();
            
            DefaultComboBoxModel model =  (DefaultComboBoxModel)visProd.cbxCategoria.getModel();           
            
            while (listCategorias.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                    
                    model.addElement(listCategorias.getString("tipo_cat"));
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consProd.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        visProd.cbxCategoria.updateUI();
    } 
    
    public void showComboCalcFechaServ()
    {
        visProd.cmbCalcFechaServ.removeAllItems();
        try {
           
            ResultSet listCategorias = consProd.buscarCalcFechaServ();
            
            DefaultComboBoxModel model =  (DefaultComboBoxModel)visProd.cmbCalcFechaServ.getModel();           
            
            while (listCategorias.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                    
                    model.addElement(new CalculoFechaServicio(listCategorias.getInt("ID_CALSERV"),listCategorias.getString("DESCRIPCION_CALSERV"),listCategorias.getInt("ESTADO_CALSERV")));
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consProd.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        visProd.cmbCalcFechaServ.setRenderer(new ItemRendererCalcFechaServ());                  
        visProd.cmbCalcFechaServ.updateUI();
    } 
     
    public void showComboCategorias()
    {
        visProd.cbxCategoria.removeAllItems();
        try {
           
            ResultSet listCategorias = consProd.buscarCategorias();
            
            DefaultComboBoxModel model =  (DefaultComboBoxModel)visProd.cbxCategoria.getModel();
           
            
            while (listCategorias.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                    
                    model.addElement(listCategorias.getString("tipo_cat"));
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consProd.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        visProd.cbxCategoria.updateUI();
    }
    public void showComboServicios()
    {
        visProd.cbxCategoria.removeAllItems();
        try {
           
            ResultSet listCategorias = consProd.buscarSoloServicios();
            
            DefaultComboBoxModel model =  (DefaultComboBoxModel)visProd.cbxCategoria.getModel();
           
            
            while (listCategorias.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                    
                    model.addElement(listCategorias.getString("tipo_cat"));
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consProd.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        visProd.cbxCategoria.updateUI();
    }
    
    
    public void getProductos(){
    try {
                limpiarTabla();
                ResultSet listProd = consProd.buscarProductos();

                DefaultTableModel model =  (DefaultTableModel)visProd.tbl_productos.getModel();
                Object cols[] = new Object[12];

                while (listProd.next()) {
                    try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                       cols[0] = listProd.getInt("id_prod");
                       cols[1] = listProd.getString("descripcion_prod").toUpperCase();
                       cols[2] = listProd.getString("precio_prod").toUpperCase();
                       cols[3] = listProd.getString("FECHAINI_PROD");
                       cols[4] = listProd.getString("FECHAFIN_PROD");
                       cols[5] = listProd.getString("tipo_cat").toUpperCase();
                       cols[6] = Validaciones.isNumVoid10(listProd.getString("EXISTINI")+"");
                       cols[7] = Validaciones.isNumVoid10(listProd.getString("ENTRADAS")+"");
                       cols[8] = Validaciones.isNumVoid10(listProd.getString("SALIDAS")+"");
                       cols[9] = Validaciones.isNumVoid(listProd.getString("STOCK")+"");
                       cols[10] = listProd.getString("id_cat");
                       cols[11] = listProd.getString("id_calserv");

                        model.addRow(cols);

                    } catch (SQLException ex) {
                        Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                consProd.closeConection();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
            visProd.tbl_productos.updateUI();
    
    }
    
    public void getProductosServicios(){
    try {
                limpiarTabla();
                ResultSet listProd = consProd.buscarTodos();

                DefaultTableModel model =  (DefaultTableModel)visProd.tbl_productos.getModel();
                Object cols[] = new Object[12];

                while (listProd.next()) {
                    try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                       cols[0] = listProd.getInt("id_prod");
                       cols[1] = listProd.getString("descripcion_prod").toUpperCase();
                       cols[2] = listProd.getString("precio_prod").toUpperCase();
                       cols[3] = listProd.getString("FECHAINI_PROD");
                       cols[4] = listProd.getString("FECHAFIN_PROD");
                       cols[5] = listProd.getString("tipo_cat").toUpperCase();
                       cols[6] = Validaciones.isNumVoid10(listProd.getString("EXISTINI")+"");
                       cols[7] = Validaciones.isNumVoid10(listProd.getString("ENTRADAS")+"");
                       cols[8] = Validaciones.isNumVoid10(listProd.getString("SALIDAS")+"");
                       cols[9] = Validaciones.isNumVoid10(listProd.getString("STOCK")+"");
                       cols[10] = listProd.getString("id_cat");
                       cols[11] = listProd.getString("id_calserv");
                        model.addRow(cols);

                    } catch (SQLException ex) {
                        Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                consProd.closeConection();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
            visProd.tbl_productos.updateUI();
    
    }
    
    public void getServicios(){
    try {
                limpiarTabla();
                ResultSet listProd = consProd.buscarServicios();

                DefaultTableModel model =  (DefaultTableModel)visProd.tbl_productos.getModel();
                Object cols[] = new Object[12];

                while (listProd.next()) {
                    try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                       cols[0] = listProd.getInt("id_prod");
                       cols[1] = Validaciones.isNumVoid4(listProd.getString("descripcion_prod")).toUpperCase();
                       cols[2] = listProd.getString("precio_prod").toUpperCase();
                       cols[3] = listProd.getString("FECHAINI_PROD");
                       cols[4] = listProd.getString("FECHAFIN_PROD");
                       cols[5] = listProd.getString("tipo_cat").toUpperCase();
                       cols[6] = listProd.getString("EXISTINI").toUpperCase();
                       cols[7] = listProd.getString("ENTRADAS").toUpperCase();
                       cols[8] = listProd.getString("SALIDAS").toUpperCase();
                       cols[9] = listProd.getString("STOCK").toUpperCase();
                       cols[10] = listProd.getString("id_cat");
                       cols[11] = listProd.getString("id_calserv");
                        model.addRow(cols);

                    } catch (SQLException ex) {
                        Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                consProd.closeConection();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
            visProd.tbl_productos.updateUI();
    
    }
    
    public void showDatosComboTable()
    {
        limpiarComboProdServ();
        switch(locale)
        {
            case 0: //muestra productos solamente
                getProductos();
                showComboProductos();
                break;
            case 1: //muestra todos
                getProductosServicios();
                showComboCategorias();
                break;
            case 2:
                getProductosServicios();
                showComboCategorias();
                break;
            default :
                break;
                        
        }  
        
    }
    
    public Producto getIndexByItemCombo(JComboBox comboBox,String cad)
    {
        DefaultComboBoxModel model =  (DefaultComboBoxModel)comboBox.getModel();
        
        Producto p=new Producto();
        for (int i = 0; i < model.getSize(); i++) {
            if((((Producto)model.getElementAt(i)).getDescripcion_prod()).equals(cad))
            {
                p= (Producto)model.getElementAt(i);
            }
        }
        return p;
    }
    public ArrayList<Producto> getProductosServicios2()
    {
        ConsProductos consProd=new ConsProductos();
        ArrayList<Producto> listSomeString = new ArrayList<Producto>();
        try 
        {
            //jc.removeAllItems();
            ResultSet listProd = consProd.buscarTodos();
               
           // DefaultComboBoxModel model =  (DefaultComboBoxModel)jc.getModel();
            Object cols[] = new Object[11];

             while (listProd.next()) {
            try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                Categoria c = new Categoria();
                CalculoFechaServicio calcF = new CalculoFechaServicio();
                calcF.setId_calServ(listProd.getInt("id_calserv"));
                c.setId_cat(listProd.getInt("id_cat"));
                listSomeString.add(new Producto(listProd.getInt("ID_PROD"),listProd.getString("descripcion_prod").toUpperCase(),
                                                  listProd.getString("FECHAINI_PROD"),listProd.getString("FECHAFIN_PROD"),
                                                  listProd.getDouble("PRECIO_PROD"),c,calcF ));

            } catch (SQLException ex) {
                Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
               
        consProd.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSomeString;
    }
    public void addRows(JTable table)
    {        
         Object cols[] = new Object[6];
         DefaultTableModel tb = (DefaultTableModel) table.getModel();         
         for (int i = 0; i <= 5; i++) {
            cols[i] = new String();
        }
         setFormatTable(table);
       tb.addRow(cols);
    }
    public void limpiarTablaDetalles(){
        DefaultTableModel tb = (DefaultTableModel) visFicha.tblFacturaDetalle.getModel();
        int a = visFicha.tblFacturaDetalle.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        }
         addRows(visFicha.tblFacturaDetalle);
    }
    private void crearTablaCombo() {

        limpiarTablaDetalles();
        JTable tabDet = visFicha.tblFacturaDetalle;      
        ArrayList<Producto>  listSomeString = getProductosServicios2();
        Java2sAutoComboBox comboBox1 = new Java2sAutoComboBox(listSomeString);
        comboBox1.setDataList(listSomeString);
        comboBox1.setMaximumRowCount(5);       
        //comboBox1.setRenderer(new ItemRendererProducto());
        comboBox1.setRenderer(new ItemRendererProducto());
       
        ActionListener comboAct = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               DefaultComboBoxModel model =  (DefaultComboBoxModel)comboBox1.getModel();
                if (comboBox1.getSelectedIndex()!=-1) {
                    System.out.println("dataenter "+((Producto)model.getElementAt(comboBox1.getSelectedIndex())).getId_prod());
                }              
            }
        };
        
         comboBox1.addActionListener(comboAct);
         comboBox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
               JComboBox comboBox = (JComboBox) event.getSource();
                  Object item = event.getItem();
                   DefaultComboBoxModel model =  (DefaultComboBoxModel)comboBox.getModel();
                   if (event.getStateChange() == ItemEvent.SELECTED) {
                       System.out.println(item.toString() + " selected." +getIndexByItemCombo(comboBox,item.toString()).getId_prod());
                       //if (comboBox.getSelectedIndex()!=-1) {                                                  
                            int idProd = getIndexByItemCombo(comboBox,item.toString()).getId_prod();
                            double precioPro = getIndexByItemCombo(comboBox,item.toString()).getPrecio_prod();
                            tabDet.setValueAt(idProd,tabDet.getSelectedRow(), 0);
                            tabDet.setValueAt(precioPro,tabDet.getSelectedRow(), 4);                     
                            try {
                                  System.out.println("dataenterclik "+((Producto)model.getElementAt(comboBox.getSelectedIndex())).getId_prod());
                            } catch (Exception e) {
                                System.out.println("seleccinado null: ");
                        //    }
                       }
                   }

                   if (event.getStateChange() == ItemEvent.DESELECTED) {
                    System.out.println(item.toString() + " deselected.");
                   }

            }
        });
       visFicha.tblFacturaDetalle.getColumnModel().getColumn(3).setCellEditor(null);
       visFicha.tblFacturaDetalle.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboBox1));            
      
        visFicha.tblFacturaDetalle.updateUI();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == visProd.btnGuardar) 
       {       
           double exis=0;
           double ent = 0;
           double sali = 0;
           exis = Validaciones.isNumVoid10(visProd.txtExistentes.getText());
           ent = Validaciones.isNumVoid10(visProd.txtEntradas.getText());
           sali = Validaciones.isNumVoid10(visProd.txtSalidas.getText());
                      
           ArrayList<JDateChooser> jdc=new ArrayList<>();
                                            
            int tE = consProd.getIdByNom(visProd.cbxCategoria.getSelectedItem()+"");                     
            catProd.setId_cat(tE);
            prod.setCategoria(catProd);
            prod.setDescripcion_prod(visProd.txtDescripcionProd.getText());
            prod.setPrecio_prod(Validaciones.isNumVoid10(visProd.txtPrecioProd.getText()));            
            prod.setFechaIni(Validaciones.setFormatFecha(visProd.dchFechaIni.getDate()));
            prod.setFechaFin(Validaciones.setFormatFecha(visProd.dchFechaFin.getDate()));
            prod.setExistIni(Validaciones.isNumVoid10(visProd.txtExistentes.getText()));
            prod.setEntradas(Validaciones.isNumVoid10(visProd.txtEntradas.getText()));
            prod.setSalidas(Validaciones.isNumVoid10(visProd.txtSalidas.getText()));
            prod.setStock(Calculos.getStock(exis,ent,sali));
            prod.setCalcFechServ(((CalculoFechaServicio) visProd.cmbCalcFechaServ.getSelectedItem()));
            prod.setEstado_prod(1);
            

            if (consProd.registrar(prod)) {
                JOptionPane.showMessageDialog(null, "Registro Guardado!");
                limpiar();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
                limpiar();
            }
            showDatosComboTable();
            crearTablaCombo();
                       
        }
      if (e.getSource() == visProd.btnBuscar) 
         {
          
             if (Validaciones.isVoidJTxt(visProd.txtDescripcionProd)) {
                  prod.setDescripcion_prod(visProd.txtDescripcionProd.getText());
        
                if (consProd.buscar(prod)){                   
                   
                    //falta
                    desabilitaHabilita(visProd.btnGuardar,false);
                    desabilitaHabilita(visProd.btnModificar,true);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "No se encotro registro");
                    limpiar();
                }
                showDatosComboTable();
            }
         }
      
      if (e.getSource() == visProd.btnModificar) 
       {            
           double exis=0;
           double ent = 0;
           double sali = 0;
           exis = Validaciones.isNumVoid10(visProd.txtExistentes.getText());
           ent = Validaciones.isNumVoid10(visProd.txtEntradas.getText());
           sali = Validaciones.isNumVoid10(visProd.txtSalidas.getText());
           
            int tE = consProd.getIdByNom(visProd.cbxCategoria.getSelectedItem()+"");
            catProd.setId_cat(tE);
            prod.setId_prod(Validaciones.isNumVoid(visProd.txt_id.getText()));
            prod.setCategoria(catProd);
            prod.setDescripcion_prod(visProd.txtDescripcionProd.getText());
            prod.setPrecio_prod(Validaciones.isNumVoid10(visProd.txtPrecioProd.getText()));  
            prod.setFechaIni(Validaciones.setFormatFecha(visProd.dchFechaIni.getDate()));
            prod.setFechaFin(Validaciones.setFormatFecha(visProd.dchFechaFin.getDate()));
            prod.setExistIni(Validaciones.isNumVoid10(visProd.txtExistentes.getText()));
            prod.setEntradas(Validaciones.isNumVoid10(visProd.txtEntradas.getText()));
            prod.setSalidas(Validaciones.isNumVoid10(visProd.txtSalidas.getText()));
            prod.setStock(Calculos.getStock(exis,ent,sali));
            System.out.println(((CalculoFechaServicio) visProd.cmbCalcFechaServ.getSelectedItem()));
            prod.setCalcFechServ(((CalculoFechaServicio) visProd.cmbCalcFechaServ.getSelectedItem()));
            prod.setEstado_prod(1);
            
            if (consProd.modificar(prod)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado!");
                limpiar();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
            showDatosComboTable();
        }
      
      if (e.getSource() == visProd.btnEliminar) 
       {
           
            prod.setId_prod(Integer.parseInt(visProd.txt_id.getText()));
            prod.setEstado_prod(0);
            int o= JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar el registro?", "Confirmar eliminar?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);          
            if (o ==0) 
            {    
                if (consProd.eliminar(prod)) {
                    JOptionPane.showMessageDialog(null, "Registro Eliminado !");
                    limpiar();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error al Eliminar...");
                    limpiar();
                }
                showDatosComboTable();
            }
        }
      
       if (e.getSource() == visProd.btnLimpiar) 
        {
           limpiar();
           desabilitaHabilita(visProd.btnGuardar,true);
           desabilitaHabilita(visProd.btnModificar,false);
           showDatosComboTable();
        }
       
        if (e.getSource() == visProd.rdbProductos) 
        {
            visProd.rdbServicios.setSelected(false); 
            visProd.rdbTodos.setSelected(false);
            getProductos();
            showComboProductos();      
        }
        
        if (e.getSource() == visProd.cmbCalcFechaServ) 
        {
            CalculoFechaServicio item = (CalculoFechaServicio) visProd.cmbCalcFechaServ.getSelectedItem();           
            System.out.println(item.getId_calServ() + " : " + item.getDescripcion_calServ());
        }
        if (e.getSource() == visProd.rdbServicios) 
        {
            visProd.rdbProductos.setSelected(false);
            visProd.rdbTodos.setSelected(false);
            getServicios();
            showComboServicios();
        }
        if (e.getSource() ==visProd.rdbTodos) {
            visProd.rdbProductos.setSelected(false);
            visProd.rdbServicios.setSelected(false);
            getProductosServicios();
            showComboCategorias();
            
            
        }

       
                           
    }
    public void limpiar()
    {
        visProd.txtDescripcionProd.setText("");
        visProd.txtPrecioProd.setText("");
        visProd.dchFechaIni.setDate(null);
        visProd.dchFechaFin.setDate(null);
        visProd.txtEntradas.setText("");
        visProd.txtExistentes.setText("");
        visProd.txtSalidas.setText("");
        
        if (visProd.rdbProductos.isSelected()) {
            visProd.rdbServicios.setSelected(false); 
            visProd.rdbTodos.setSelected(false);
             getProductos();
             showComboProductos();    
        }
        if (visProd.rdbServicios.isSelected()) {
            visProd.rdbProductos.setSelected(false);
             visProd.rdbTodos.setSelected(false);
            getServicios();
            showComboServicios();
        }
        if (visProd.rdbTodos.isSelected()) {
            visProd.rdbProductos.setSelected(false);
            visProd.rdbServicios.setSelected(false);
            getProductosServicios();
            showComboCategorias();
        }
        
        //visProd.cbxCategoria.setSelectedIndex(0);    
    }
    
   
}
