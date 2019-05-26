/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Calculos;
import static assets.Calculos.calcularTotalDetalle;
import static assets.Calculos.getTwoDecimals;
import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import consultas.ConsFacturaDet;
import consultas.ConsProductos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelos.Categoria;
import modelos.FacturaCab;
import modelos.FacturaDetalle;
import modelos.Producto;
import vistas.VisFicha;
import vistas.VisProductos;

/**
 *
 * @author Administrator
 */
public class CtrlFacturaDetalle implements ActionListener {
    
    
    private ArrayList<FacturaDetalle> facDet; 
    private ConsFacturaDet consFacDet;
    private VisFicha visFicha;
    
    public CtrlFacturaDetalle(ConsFacturaDet consFacDet,VisFicha visFicha)
    {        
        this.consFacDet = consFacDet;
        this.visFicha = visFicha;
        
      //  this.visFicha.btnGuardarFacCab.addActionListener(this);
        this.visFicha.btnEliminarFacCab.addActionListener(this);
        this.visFicha.btnLimpiarFacCab.addActionListener(this);
        this.visFicha.btnModificarFacCab.addActionListener(this);
        this.visFicha.btnBuscarDscto.addActionListener(this);
        this.visFicha.btnCalcular.addActionListener(this);
        this.visFicha.btnBuscarClienteFactura.addActionListener(this);
        this.visFicha.btnAgregarFilas.addActionListener(this);
        this.visFicha.btnEliminarFilas.addActionListener(this);
        this.visFicha.btnEntrenamiento.addActionListener(this);
        
        setListener();
        limpiarTablaDetalles();
        setFormatTable(visFicha.tblFacturaDetalle);
        
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
        JTable facDet = visFicha.tblFacturaDetalle;
        facDet.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        facDet.setColumnSelectionAllowed(true);
        facDet.setRowSelectionAllowed(true);
        
        KeyListener keyListenerTblDetalle = new KeyListener() {
          public void keyPressed(KeyEvent e) {

              Calculos.calcularTotalDetalles(facDet);             
              Calculos.calcularValorPagar(facDet,visFicha);
              
              
              if (e.getKeyCode()==KeyEvent.VK_F1 )
              {
                
                  VisProductos visProd = new VisProductos();
                  ConsProductos consProd = new ConsProductos();
                  Producto prod=new Producto();
                  Categoria cat=new Categoria();

                  CtrlProductos ctrProd=new CtrlProductos(prod,consProd, visProd, visFicha);
                  ctrProd.locale = 0;
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

                        CtrlProductos ctrProd=new CtrlProductos(prod,consProd, visProd, visFicha);
                        ctrProd.locale = 0;
                        ctrProd.iniciar();
              
                          break;
                    case 3: 
                           
                          break;
                    case 4:
                          row = facDet.getSelectedRow()-1;                                                   
                          addRows(visFicha.tblFacturaDetalle);
                          col = 1;
                          row = visFicha.tblFacturaDetalle.getRowCount()-1;
                          visFicha.tblFacturaDetalle.changeSelection(row, col,false,false);                        
                          break;
                    default:
                        break;
                  }
                visFicha.tblFacturaDetalle.changeSelection(row, col,false,false);
              }
                
              
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
           
          }
        };
        visFicha.tblFacturaDetalle.addKeyListener(keyListenerTblDetalle);
    
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == visFicha.btnGuardarFacCab) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
           jdc.add(visFicha.dtcFechaFacCab);
           
               if (Validaciones.isDateChooserVoid(jdc)) 
               {                                        
                       
                  //    setDetalles(visFicha, visFicha.lblNroFactura.getText());
                
                 
               }        
        }
        if (e.getSource() == visFicha.btnLimpiarFacCab) 
        {
            limpiarTablaDetalles();
        }
        if (e.getSource() == visFicha.btnAgregarFilas) 
        {
           addRows(visFicha.tblFacturaDetalle);
           Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalle);
           Calculos.setTotalesCabecera(visFicha.tblFacturaDetalle,visFicha);
        } 
        if (e.getSource() == visFicha.btnEliminarFilas) 
        {
           deleteRows(visFicha.tblFacturaDetalle);
           Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalle);
           Calculos.setTotalesCabecera(visFicha.tblFacturaDetalle,visFicha);
        } 

    }
    
    public void limpiarTablaDetalles(){
        DefaultTableModel tb = (DefaultTableModel) visFicha.tblFacturaDetalle.getModel();
        int a = visFicha.tblFacturaDetalle.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        }
         addRows(visFicha.tblFacturaDetalle);
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
    
    
    
    public ArrayList<FacturaDetalle> setDetalles(VisFicha visFicha,String numFac)
    {
        JTable table = visFicha.tblFacturaDetalle;
        String num,desc,valU,valT,prodId;
        int facCabId = 0;
                
        Producto prod = new Producto();
        FacturaCab facCab = new FacturaCab();
        FacturaDetalle detalle ;
        
        ArrayList<FacturaDetalle> detalles=new ArrayList<>();
        ArrayList<Producto> p = new ArrayList<>();      
        facDet = new ArrayList<>();
        
        
        if (consFacDet.getLastInvoice(facCab))               
            facCabId = facCab.getId_facCab();
        
        facCab.setId_facCab(facCabId);        
        System.out.println(facCab.getId_facCab());
        for (int i = 0; i <= table.getRowCount()-1; i++) 
        {
            prodId =table.getValueAt(i, 0)+"";
            num = table.getValueAt(i, 1)+"";
            desc = table.getValueAt(i, 2)+"";     
            valU = table.getValueAt(i, 3)+"";
            valT = table.getValueAt(i, 4)+"";
         
            if (Validaciones.isNumVoid10(num)!=0 && Validaciones.isNumVoid10(valU)!=0 && Validaciones.isNumVoid10(valT)!=0 ) {
                
                detalle = new FacturaDetalle();
                prod = new Producto();
               
                detalle.setCantidad_facDet(Validaciones.isNumVoid(num));
                detalle.setDescripcion_facDet(desc);
                detalle.setValUnitario_facDet(Validaciones.isNumVoid10(valU));
                detalle.setvTotal_facDet(Validaciones.isNumVoid10(valT));
               
                prod.setId_prod(Validaciones.isNumVoid(prodId));
                p.add(prod);
                detalle.setProducto_id_prod(p.get(p.size()-1));
                
                detalle.setFactura_id_fac(facCab);
                detalle.setEstado_facDet(1);
                
                detalles.add(detalle);
                facDet.add(detalles.get(detalles.size()-1));

            }
        }                               
            return facDet;
       
    }
  
    
    
    
    
      
}
