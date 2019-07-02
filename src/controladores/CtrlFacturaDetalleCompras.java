/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Calculos;
import static assets.Calculos.calcularTotalDetalle;
import static assets.Calculos.getTwoDecimals;
import assets.ItemRendererProducto;
import assets.Java2sAutoComboBox;
import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import consultas.ConsFacturaDet;
import consultas.ConsFacturaDetCompras;
import consultas.ConsProductos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelos.CalculoFechaServicio;
import modelos.Categoria;
import modelos.FacturaCab;
import modelos.FacturaCabCompras;
import modelos.FacturaDetalle;
import modelos.FacturaDetalleCompras;
import modelos.Producto;
import vistas.VisFicha;
import vistas.VisProductos;

/**
 *
 * @author Administrator
 */
public class CtrlFacturaDetalleCompras implements ActionListener {
    
    
    private ArrayList<FacturaDetalleCompras> facDetCompras; 
    private ConsFacturaDetCompras consFacDetCompras;
    private VisFicha visFicha;
    
    public CtrlFacturaDetalleCompras(ConsFacturaDetCompras consFacDet,VisFicha visFicha)
    {        
        this.consFacDetCompras = consFacDet;
        this.visFicha = visFicha;
        
        this.visFicha.btnLimpiarFacCabComp.addActionListener(this);
        this.visFicha.btnBuscarDsctoCompra.addActionListener(this);
        this.visFicha.btnCalcularCompras.addActionListener(this);
        this.visFicha.btnBuscarClienteFacturaComp.addActionListener(this);
        this.visFicha.btnAgregarFilasComp.addActionListener(this);
        this.visFicha.btnEliminarFilasComp.addActionListener(this);
       
        
        setListener();
        limpiarTablaDetalles();
        setFormatTable(visFicha.tblFacturaDetalleCompras);
        crearTablaCombo();       
    }
    
    
    public ArrayList<Producto> getProductosServicios()
    {
        ConsProductos consProd=new ConsProductos();
        ArrayList<Producto> listSomeString = new ArrayList<Producto>();
        try 
        {
            ResultSet listProd = consProd.buscarTodos();
            Object cols[] = new Object[11];

             while (listProd.next()) {
            try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                Categoria c = new Categoria();
                CalculoFechaServicio calcF = new CalculoFechaServicio();
                calcF.setId_calServ(listProd.getInt("id_calserv"));
                c.setId_cat(listProd.getInt("id_cat"));
                listSomeString.add(new Producto(listProd.getInt("ID_PROD"),Validaciones.isNumVoid4(listProd.getString("descripcion_prod")).toUpperCase(),
                                                  Validaciones.isNumVoid4(listProd.getString("FECHAINI_PROD")),Validaciones.isNumVoid4(listProd.getString("FECHAFIN_PROD")),
                                                  Validaciones.isNumVoid10(listProd.getDouble("PRECIO_PROD")+""),c,calcF  ));

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
     private void crearTablaCombo() {

        JTable tabDet = visFicha.tblFacturaDetalleCompras;      
        ArrayList<Producto>  listSomeString = getProductosServicios();
        Java2sAutoComboBox comboBox1 = new Java2sAutoComboBox(listSomeString);
        comboBox1.setDataList(listSomeString);
        comboBox1.setMaximumRowCount(5);
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
                            try
                            {
                                tabDet.setValueAt(idProd,tabDet.getSelectedRow(), 0);
                                tabDet.setValueAt(precioPro,tabDet.getSelectedRow(), 3);                     
                            }catch(Exception e){System.out.println("index en -1");}
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
        visFicha.tblFacturaDetalleCompras.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox1));      
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click para ver producto");  
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
    public void setListener()
    {               
        int col = 1;      
        JTable facDet = visFicha.tblFacturaDetalleCompras;
        facDet.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        facDet.setColumnSelectionAllowed(true);
        facDet.setRowSelectionAllowed(true);
        
        KeyListener keyListenerTblDetalle = new KeyListener() {
          public void keyPressed(KeyEvent e) {

              Calculos.calcularTotalDetalles(facDet);             
              Calculos.setTotalesCabeceraCompras(facDet,visFicha);
              
              
              if (e.getKeyCode()==KeyEvent.VK_F1 )
              {
                
                  VisProductos visProd = new VisProductos();
                  ConsProductos consProd = new ConsProductos();
                  Producto prod=new Producto();
                  Categoria cat=new Categoria();

                  CtrlProductos ctrProd=new CtrlProductos(prod,consProd, visProd, visFicha,visFicha);
                  ctrProd.locale = 1; 
                  ctrProd.iniciar();
              }
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
             int m=e.getKeyChar();
             Calculos.calcularTotalDetalles(facDet);
             Calculos.setTotalesCabecera(facDet,visFicha);
             
             int col =facDet.getSelectedColumn();
             int  row =facDet.getRowCount()-1;
             
              if (m == KeyEvent.VK_ENTER || m == KeyEvent.VK_TAB ) 
              {
                              
                  switch(col)
                  {
                      case 2:
                        VisProductos visProd = new VisProductos();
                        ConsProductos consProd = new ConsProductos();
                        Producto prod=new Producto();
                        Categoria cat=new Categoria();

                        CtrlProductos ctrProd=new CtrlProductos(prod,consProd, visProd, visFicha,visFicha);
                        ctrProd.locale = 1;
                        ctrProd.iniciar();
              
                          break;
                    case 3: 
                           
                          break;
                    case 4:
                          row = facDet.getSelectedRow()-1;                                                   
                          addRows(visFicha.tblFacturaDetalleCompras);
                          col = 1;
                          row = visFicha.tblFacturaDetalleCompras.getRowCount()-1;
                          visFicha.tblFacturaDetalleCompras.changeSelection(row, col,false,false);                        
                          break;
                    default:
                        break;
                  }
                visFicha.tblFacturaDetalleCompras.changeSelection(row, col,false,false);
              }
                
              
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        visFicha.tblFacturaDetalleCompras.addKeyListener(keyListenerTblDetalle);
    
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == visFicha.btnGuardarFacCabCompras) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
           jdc.add(visFicha.dtcFechaFacCab);
           
               if (Validaciones.isDateChooserVoid(jdc)) 
               {                                        
                       
                  //    setDetalles(visFicha, visFicha.lblNroFactura.getText());
                
                 
               }        
        }
        if (e.getSource() == visFicha.btnLimpiarFacCabComp) 
        {
            limpiarTablaDetalles();
        }
        if (e.getSource() == visFicha.btnAgregarFilasComp) 
        {
           addRows(visFicha.tblFacturaDetalleCompras);
           Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalleCompras);
           Calculos.setTotalesCabecera(visFicha.tblFacturaDetalleCompras,visFicha);
        } 
        if (e.getSource() == visFicha.btnEliminarFilasComp) 
        {
           deleteRows(visFicha.tblFacturaDetalleCompras);
           Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalleCompras);
           Calculos.setTotalesCabecera(visFicha.tblFacturaDetalleCompras,visFicha);
        } 

    }
    
    public void limpiarTablaDetalles(){
        DefaultTableModel tb = (DefaultTableModel) visFicha.tblFacturaDetalleCompras.getModel();
        int a = visFicha.tblFacturaDetalleCompras.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        }
         addRows(visFicha.tblFacturaDetalleCompras);
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
    
    public void setHideJtableColumn(JTable table, int col[])
    {
        for (int i = 0; i < col.length; i++) {
            table.getColumnModel().getColumn(col[i]).setMaxWidth(0);
            table.getColumnModel().getColumn(col[i]).setMinWidth(0);
            table.getColumnModel().getColumn(col[i]).setPreferredWidth(0);
        }       
    
    }
    
    
    
    public ArrayList<FacturaDetalleCompras> setDetalles(VisFicha visFicha,String numFac)
    {
        JTable table = visFicha.tblFacturaDetalleCompras;
        String num,desc,valU,valT,prodId;
        int facCabId = 0;
                
        Producto prod = new Producto();
        FacturaCabCompras facCab = new FacturaCabCompras();
        FacturaDetalleCompras detalle ;
        
        ArrayList<FacturaDetalleCompras> detalles=new ArrayList<>();
        ArrayList<Producto> p = new ArrayList<>();      
        facDetCompras = new ArrayList<>();
        
        
        if (consFacDetCompras.getLastInvoice(facCab))               
            facCabId = facCab.getId_facCabComp();
        
        facCab.setId_facCabComp(facCabId);        
        System.out.println(facCab.getId_facCabComp());
        for (int i = 0; i <= table.getRowCount()-1; i++) 
        {
            prodId =table.getValueAt(i, 0)+"";
            num = table.getValueAt(i, 1)+"";
            desc = table.getValueAt(i, 2)+"";     
            valU = table.getValueAt(i, 3)+"";
            valT = table.getValueAt(i, 4)+"";
         
            if (Validaciones.isNumVoid10(num)!=0 && Validaciones.isNumVoid10(valU)!=0 && Validaciones.isNumVoid10(valT)!=0 ) {
                
                detalle = new FacturaDetalleCompras();
                prod = new Producto();
               
                detalle.setCantidad_facDetComp(Validaciones.isNumVoid(num));
                detalle.setDescripcion_facDetComp(desc);
                detalle.setValUnitario_facDetComp(Validaciones.isNumVoid10(valU));
                detalle.setvTotal_facDetComp(Validaciones.isNumVoid10(valT));
               
                prod.setId_prod(Validaciones.isNumVoid(prodId));
                p.add(prod);
                detalle.setProducto_id_prodComp(p.get(p.size()-1));
                
                detalle.setFactura_id_facComp(facCab);
                detalle.setEstado_facDetComp(1);
                
                detalles.add(detalle);
                facDetCompras.add(detalles.get(detalles.size()-1));

            }
        }                               
            return facDetCompras;
       
    }
  
    public void setEntradasInventarios()
    {
        
    
    }
    
    
    
    
      
}
