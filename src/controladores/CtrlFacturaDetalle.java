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
import modelos.FacturaDetalle;
import modelos.Producto;
import vistas.VisFicha;

/**
 *
 * @author Administrator
 */
public class CtrlFacturaDetalle implements ActionListener {
    
    
    private ArrayList<FacturaDetalle> facDet; 
    private ConsFacturaDet consFacDet;
    private VisFicha visFicha;
    
    public CtrlFacturaDetalle(ArrayList<FacturaDetalle> facDet,ConsFacturaDet consFacDet,VisFicha visFicha)
    {
        this.facDet = facDet;
        this.consFacDet = consFacDet;
        this.visFicha = visFicha;
        
        this.visFicha.btnGuardarFacCab.addActionListener(this);
        this.visFicha.btnEliminarFacCab.addActionListener(this);
        this.visFicha.btnLimpiarFacCab.addActionListener(this);
        this.visFicha.btnModificarFacCab.addActionListener(this);
        this.visFicha.btnBuscarDscto.addActionListener(this);
        this.visFicha.btnCalcular.addActionListener(this);
        this.visFicha.btnBuscarClienteFactura.addActionListener(this);
        this.visFicha.btnAgregarFilas.addActionListener(this);
        this.visFicha.btnEliminarFilas.addActionListener(this);
        this.visFicha.chkEntrenamiento.addActionListener(this);
        
        setListener();
       
    }
    
    public void setListener()
    {               

        int col = 0;      
        JTable facDet = visFicha.tblFacturaDetalle;
        facDet.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        facDet.setColumnSelectionAllowed(true);
        facDet.setRowSelectionAllowed(true);
        
        KeyListener keyListenerTblDetalle = new KeyListener() {
          public void keyPressed(KeyEvent e) {
              Calculos.calcularTotalDetalles(facDet);             
              Calculos.calcularValorPagar(facDet,visFicha);
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
              if (m == KeyEvent.VK_ENTER || m == KeyEvent.VK_TAB ) {
                              
                  switch(col)
                  {
                    case 2: 

                          break;
                    case 3:
                          row = facDet.getSelectedRow()-1;                                                   
                          addRows(visFicha.tblFacturaDetalle);
                          col = 0;
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
        
        if (e.getSource() == visFicha.btnLimpiarFacCab) 
        {
            limpiarTabla();
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
    
    public void limpiarTabla(){
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
        int n = table.getSelectedRow();
        
            tb.removeRow(n);
        
        
    }
    
    public void setFormatTable(JTable table)
    {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(3).setCellRenderer(tcr);
        table.getColumnModel().getColumn(2).setCellRenderer(tcr);
        table.setCellSelectionEnabled(false);
    }   
    
    
    public void setDetalles(VisFicha visFicha,String numFac)
    {
        JTable table = visFicha.tblFacturaDetalle;
        String num;
        String desc;
        String valU;
        String valT;
        int idProd;
        int idFac = 1; //consulta factura id by numFac
                
        FacturaDetalle detalle;
        ArrayList<Producto> p = new ArrayList<>();
        for (int i = 0; i <= table.getRowCount()-1; i++) 
        {
            num = table.getValueAt(i, 0)+"";
            desc = table.getValueAt(i, 1)+"";
            valU = table.getValueAt(i, 2)+"";
            valT = table.getValueAt(i, 3)+"";
            idProd = 1;//consulra productos idProd by nomProd
            

            if (Validaciones.isNumVoid10(num)!=0 && Validaciones.isNumVoid10(valU)!=0 && Validaciones.isNumVoid10(valT)!=0 ) {
                
                detalle = new FacturaDetalle();
                detalle.setCantidad_facDet(Validaciones.isNumVoid(num));
                detalle.setDescripcion_facDet(desc);
                detalle.setValUnitario_facDet(Validaciones.isNumVoid10(valU));
                detalle.setvTotal_facDet(Validaciones.isNumVoid10(valT));
                detalle.setProducto_id_prod(idProd);
                detalle.setFactura_id_fac(idFac);
                detalle.setEstado_facDet(1);
                facDet.add(detalle);

            }
        }                 
        consFacDet.registrar(facDet);
    }
  
    
    
    
    
      
}
