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
import consultas.ConsEntrenamiento;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.Analisis;
import modelos.Categoria;
import modelos.Entrenamiento;
import modelos.EntrenamientoTiempo;
import modelos.FacturaCab;
import modelos.Ficha;
import modelos.Medidas;
import modelos.Membresias;
import modelos.Persona;
import modelos.Producto;
import vistas.VisBuscarVentas;
import vistas.VisEntrenamiento;
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
    /*
    VisProductos visProd = new VisProductos();
            Producto entT  = new Producto();
            ConsProductos consEntT = new ConsProductos();
    
    */

    String cadBus;
    int locale;
    
    public CtrlProductos(Producto prod, ConsProductos consProd,VisProductos visProd,VisFicha visFicha)
    {
       
        this.prod = prod;
        this.consProd = consProd;        
        this.visProd =  visProd;
        this.visFicha = visFicha;
        
                
        this.visFicha.btnGuardar.addActionListener(this);
        this.visFicha.btnEliminar.addActionListener(this);
        this.visFicha.btnLimpiar.addActionListener(this);
        this.visFicha.btnModificar.addActionListener(this);     
       
        
       catProd = new Categoria();
              
        cadBus = "";
        locale = 0; //1:ficha
       
        setFocus();
        setListener();    
//        setTableModel();
       // iniciar();
        
       // visEnt.txtCodPersona.setText(persona.getId()+"");
        
        limpiarTabla();
        showTable();
        showComboCategorias();
        
     
    }
    
    
    
    
    public void iniciar()
    {
        visProd.setTitle("PRODUCTOS");            
 
        visProd.btnGuardar.setToolTipText("Guardar el registro");
        visProd.btnModificar.setToolTipText("Modificar el registro");
        visProd.btnEliminar.setToolTipText("Eliminar el registro");
        visProd.btnLimpiar.setToolTipText("Limpiar el registro");
        //visEnt.tabp_ficha.setSelectedIndex(2);
        limpiar();
        visProd.setLocation(300,10); 
        visProd.setSize(1400,1000);                
        visProd.setVisible(true);
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

        MouseListener mouseListTblFicha = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                     getTableToTxts();
                     desabilitaHabilita(visEnt.btnGuardar,false);
                     desabilitaHabilita(visEnt.btnModificar,true);
                                          
                }
                if(e.getClickCount()==2)
                {
                    int idEnt = Integer.parseInt(visEnt.tbl_entrenamiento.getValueAt(visEnt.tbl_entrenamiento.getSelectedRow(), 0)+"");                    
                    visFicha.lblEntrenamientoGenerado.setText(idEnt+ " "+visEnt.tbl_entrenamiento.getValueAt(visEnt.tbl_entrenamiento.getSelectedRow(), 2)+" "+ visEnt.tbl_entrenamiento.getValueAt(visEnt.tbl_entrenamiento.getSelectedRow(), 5)+" ");
                    cargarEntrenamientoDetalle(visFicha.tblFacturaDetalle,idEnt);
                    Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalle);
                    Calculos.setTotalesCabecera(visFicha.tblFacturaDetalle,visFicha);
                   
                    visEnt.dispose();
                
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
       
        visEnt.tbl_entrenamiento.addMouseListener(mouseListTblFicha);
      
    }
     public void getTableToTxts()
     {
         JTable tblD = visProd.tbl_productos;
         visProd.txt_id.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));
         visProd.txtDescripcionProd.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1)));
         visProd.txtPrecioProd.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 2)));
         visProd.txtCategoria.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 3)));
         visProd.lblIdCat.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 4)));
     }
    
     public void setFocus()
    {
        visProd.txtDescripcionProd.requestFocus();
        visProd.txtDescripcionProd.setNextFocusableComponent(visProd.txtPrecioProd); 
        visProd.txtPrecioProd.setNextFocusableComponent(visProd.txtCategoria); 
        
        
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
        try {
           
            ResultSet listCategorias = consProd.buscarCategorias();
            
            DefaultComboBoxModel model =  (DefaultComboBoxModel)visProd.cmbCategoria.getModel();
           
            
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
        visProd.cmbCategoria.updateUI();
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
                     
                       
                    int tE = consProd.getIdByNom(visProd.cmbCategoria.getSelectedItem()+"");                     
                    catProd.setId_cat(tE);
                    prod.setCategoria_id_cat(catProd);
                    prod.setDescripcion_prod(visProd.txtDescripcionProd.getText());
                    prod.setPrecio_prod(Validaciones.isNumVoid10(visProd.txtPrecioProd.getText()));
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
      
      if (e.getSource() == visEnt.btnModificar) 
       {            
            int tE = consEnt.getIdByNom(visEnt.cmbTipoEnt.getSelectedItem()+"");
            entTmp.setId_entTmp(tE);
            
            ent.setEntrenTiempo_id_entTmp(entTmp);
            ent.setFechaIni_ent(Validaciones.setFormatFecha(visEnt.dtchFechaInicio.getDate()));
            ent.setFechaFin_ent(Validaciones.setFormatFecha(visEnt.dtchFechaFin.getDate()));  
            
            per.setId(Validaciones.isNumVoid(visEnt.txtPersona.getText()));                    
            ent.setPersona_id_per(per);
            
            ent.setEstado_ent(1);
            ent.setId_ent(Validaciones.isNumVoid(visEnt.txt_id.getText()));
            
            if (consEnt.modificar(ent)) {
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
      
      if (e.getSource() == visEnt.btnEliminar) 
       {
           
            ent.setId_ent(Integer.parseInt(visEnt.txt_id.getText()));
            ent.setEstado_ent(0);
                      
            if (consEnt.eliminar(ent)) {
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
      
       if (e.getSource() == visEnt.btnLimpiar) 
        {
           limpiar();
           desabilitaHabilita(visEnt.btnGuardar,true);
           desabilitaHabilita(visEnt.btnModificar,false);
        }

        if (e.getSource() == visEnt.btnElegirPersona) 
        {
           
            VisPersona visPer = new VisPersona();
            Persona per  = new Persona();
            ConsPersona consPer = new ConsPersona();
                
            Ficha ficha = new Ficha();
            CtrlPersonas ctrPer=new CtrlPersonas(per, consPer, visPer,visEnt);
            ctrPer.iniciar();
            ctrPer.locale = 3;
        } 
                               
    }
    public void limpiar()
    {
        visEnt.dtchFechaInicio.setDate(null);
        visEnt.dtchFechaFin.setDate(null); 
        visEnt.txtPersona.setText("");
        visEnt.cmbTipoEnt.setSelectedIndex(0);
       
    }
    
   
}
