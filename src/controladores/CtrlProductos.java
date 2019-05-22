/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Calculos;
import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelos.Analisis;
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
  

    String cadBus;
    int locale;
    
    public CtrlProductos(Producto prod, ConsProductos consProd,VisProductos visProd,VisFicha visFicha)
    {
       
        this.prod = prod;
        this.consProd = consProd;        
        this.visProd =  visProd;
        this.visFicha = visFicha;
        
                
        this.visProd.btnGuardar.addActionListener(this);
        this.visProd.btnEliminar.addActionListener(this);
        this.visProd.btnLimpiar.addActionListener(this);
        this.visProd.btnModificar.addActionListener(this);
        
       
        
       catProd = new Categoria();
              
        cadBus = "";
        locale = 0; //1:menu , 2:factura
       
        setFocus();
        setListener();    

        limpiarTabla();
        showTable();
        showComboCategorias();
        setFormatTable(visProd.tbl_productos);
        escribirCombos();
        setTableModel();
    }
    
    private void escribirCombos()
      {
        AutoCompleteDecorator.decorate(visProd.cbxCategoria);        
           
      }  
    
    
    public void iniciar()
    {
        visProd.setTitle("PRODUCTOS/SERVICIOS");            
 
        visProd.btnGuardar.setToolTipText("Guardar el registro");
        visProd.btnModificar.setToolTipText("Modificar el registro");
        visProd.btnEliminar.setToolTipText("Eliminar el registro");
        visProd.btnLimpiar.setToolTipText("Limpiar el registro");
        //visEnt.tabp_ficha.setSelectedIndex(2);
        limpiar();
        visProd.setLocation(300,10); 
        visProd.setSize(1000,600);                
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
            Object cols[] = new Object[5];
            
            while (listProd.next()) {
                try {
                   cols[0] = listProd.getInt("id_prod");
                   cols[1] = listProd.getString("descripcion_prod");
                   cols[2] = listProd.getString("precio_prod").toUpperCase();
                   cols[3] = listProd.getString("tipo_cat");
                   cols[4] = listProd.getString("id_cat");
                 
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
                                                           
                    int filaDetalle = visFicha.tblFacturaDetalle.getRowCount()-1;
                    switch(locale)
                    {
                        case 0 :
                            break;
                        case 1:
                            break;
                        case 2:
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
             System.out.println("hola");
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
        colHide[0]=0;
        colHide[1]=4;
        setHideJtableColumn(table,colHide);
        
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
    public void setHideJtableColumn(JTable table, int col[])
    {
        for (int i = 0; i < col.length; i++) {
            table.getColumnModel().getColumn(col[i]).setMaxWidth(0);
            table.getColumnModel().getColumn(col[i]).setMinWidth(0);
            table.getColumnModel().getColumn(col[i]).setPreferredWidth(0);
        }
       
    
    }
     public void getTableToTxts()
     {
         JTable tblD = visProd.tbl_productos;
         visProd.txt_id.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));
         visProd.txtDescripcionProd.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1)));
         visProd.txtPrecioProd.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 2)));
         visProd.cbxCategoria.setSelectedItem(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 3)));
         visProd.lblIdCat.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 4)));
     }
    
     public void setFocus()
    {
        visProd.txtBuscarCualquierCampo.requestFocus();
        
        visProd.txtDescripcionProd.setNextFocusableComponent(visProd.txtPrecioProd); 
        visProd.txtPrecioProd.setNextFocusableComponent(visProd.cbxCategoria); 
        
        
    }
     public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visProd.tbl_productos.getModel();
        int a = visProd.tbl_productos.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
    public void showComboCategorias()
    {
        System.out.println("aqui");
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
    
    public void showTable()
    {
        try {
            limpiarTabla();
            ResultSet listProd = consProd.buscarTodos();
            
            DefaultTableModel model =  (DefaultTableModel)visProd.tbl_productos.getModel();
            Object cols[] = new Object[5];
            
            while (listProd.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                   cols[0] = listProd.getInt("id_prod");
                   cols[1] = listProd.getString("descripcion_prod");
                   cols[2] = listProd.getString("precio_prod").toUpperCase();
                   cols[3] = listProd.getString("tipo_cat");
                   cols[4] = listProd.getString("id_cat");
                   
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == visProd.btnGuardar) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
                     
                       
                    int tE = consProd.getIdByNom(visProd.cbxCategoria.getSelectedItem()+"");                     
                    catProd.setId_cat(tE);
                    prod.setCategoria(catProd);
                    prod.setDescripcion_prod(visProd.txtDescripcionProd.getText());
                    prod.setPrecio_prod(Validaciones.isNumVoid10(visProd.txtPrecioProd.getText()));
                    prod.setFechaIni(Validaciones.setFormatFecha(visProd.dchFechaIni.getDate()));
                    prod.setFechaIni(Validaciones.setFormatFecha(visProd.dchFechaFin.getDate()));
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
                    showTable();
                       
        }
      
      if (e.getSource() == visProd.btnModificar) 
       {            
            int tE = consProd.getIdByNom(visProd.cbxCategoria.getSelectedItem()+"");
            catProd.setId_cat(tE);
            prod.setId_prod(Validaciones.isNumVoid(visProd.txt_id.getText()));
            prod.setCategoria(catProd);
            prod.setDescripcion_prod(visProd.txtDescripcionProd.getText());
            prod.setPrecio_prod(Validaciones.isNumVoid10(visProd.txtPrecioProd.getText()));  
            prod.setFechaIni(Validaciones.setFormatFecha(visProd.dchFechaIni.getDate()));
            prod.setFechaIni(Validaciones.setFormatFecha(visProd.dchFechaFin.getDate()));
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
            showTable();
        }
      
      if (e.getSource() == visProd.btnEliminar) 
       {
           
            prod.setId_prod(Integer.parseInt(visProd.txt_id.getText()));
            prod.setEstado_prod(0);
                      
            if (consProd.eliminar(prod)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado !");
                limpiar();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al Eliminar...");
                limpiar();
            }
            showTable();
        }
      
       if (e.getSource() == visProd.btnLimpiar) 
        {
           limpiar();
           desabilitaHabilita(visProd.btnGuardar,true);
           desabilitaHabilita(visProd.btnModificar,false);
        }

       
                               
    }
    public void limpiar()
    {
        visProd.txtDescripcionProd.setText("");
        visProd.txtPrecioProd.setText("");
        visProd.cbxCategoria.setSelectedIndex(0);
       
       
    }
    
   
}
