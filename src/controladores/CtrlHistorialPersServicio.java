/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Calculos;
import assets.Configuracion;
import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import consultas.ConsAnalisis;

import consultas.ConsFacturaCab;
import consultas.ConsFicha;
import consultas.ConsHistorialPersonaServicio;
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
import modelos.HistorialPersonaServicio;
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
public class CtrlHistorialPersServicio implements ActionListener{

    
            
    VisHistorialPersonaServicio visHisPerServ;
    HistorialPersonaServicio hisPerServ;
    ConsHistorialPersonaServicio consHisPerServ;
    
    VisFicha visFicha;
    Producto prod;
    Persona per;
   
    String cadBus;
    int locale;
    
    public CtrlHistorialPersServicio(VisHistorialPersonaServicio visHisPerServ, HistorialPersonaServicio hisPerServ,ConsHistorialPersonaServicio consHisPerServ,VisFicha visFicha,Persona persona)
    {
       
        this.consHisPerServ = consHisPerServ;
        this.hisPerServ = hisPerServ;        
        this.consHisPerServ =  consHisPerServ;
        this.visHisPerServ = visHisPerServ;
        this.visFicha = visFicha;
        this.per = persona;
        this.prod = new Producto();
        
        visHisPerServ.txtPersona.setText(per.getNombre()+" "+per.getApellido() );
        visHisPerServ.lblIdPersona.setText(per.getId()+"");
        
                
        this.visHisPerServ.btnGuardar.addActionListener(this);
        this.visHisPerServ.btnEliminar.addActionListener(this);
        this.visHisPerServ.btnLimpiar.addActionListener(this);
        this.visHisPerServ.btnModificar.addActionListener(this);     
        this.visHisPerServ.cbxServicio.addActionListener(this);
        this.visHisPerServ.btnBuscarPerona.addActionListener(this);
        

              
        cadBus = "";
        locale = 0; //1:menu , 2:factura
       
        setFocus();
        setListener();    

        limpiarTabla();
        showTable();
        showComboServicio();
        setFormatTable(visHisPerServ.tbl_historialPerServ);
        escribirCombos();
    }
    
    private void escribirCombos()
      {
        AutoCompleteDecorator.decorate(visHisPerServ.cbxServicio);        
           
      }  
    
    
    public void iniciar()
    {
        visHisPerServ.setTitle(Configuracion.nomEmp +" SERVICIOS");            
        visHisPerServ.lblNomEmp.setText(Configuracion.nomEmp );
        visHisPerServ.btnGuardar.setToolTipText("Guardar el registro");
        visHisPerServ.btnModificar.setToolTipText("Modificar el registro");
        visHisPerServ.btnEliminar.setToolTipText("Eliminar el registro");
        visHisPerServ.btnLimpiar.setToolTipText("Limpiar el registro");
        //visEnt.tabp_ficha.setSelectedIndex(2);
        limpiar();
        visHisPerServ.setLocation(300,10); 
        visHisPerServ.setSize(1000,600);                
        visHisPerServ.setVisible(true);
    }
    
    public void showTableByNom(String cad)
    {
        try {
            limpiarTabla();
            
            ResultSet listProd = consHisPerServ.buscarTodosPorNomTabla(cad);
            
            DefaultTableModel model =  (DefaultTableModel)visHisPerServ.tbl_historialPerServ.getModel();
            Object cols[] = new Object[7];
            
            while (listProd.next()) {
                try {
                   cols[0] = listProd.getInt("id_hisperser");
                   cols[1] = listProd.getString("nombres");
                   cols[2] = listProd.getString("descripcion_prod").toUpperCase();
                   cols[3] = listProd.getString("fechaini_hisperser");
                   cols[4] = listProd.getString("fechafin_hisperser");
                   cols[5] = listProd.getString("id_prod");
                   cols[6] = listProd.getString("id_per");
                   
                    model.addRow(cols);
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlHistorialPersServicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consHisPerServ.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlHistorialPersServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public void desabilitaHabilita(JButton btn,boolean estado)
     {
         btn.setEnabled(estado);
     }
     
     
        
     public void setProductoServicioFacCab()
     {
        int filaDetalle = visFicha.tblFacturaDetalle.getRowCount()-1;
        int idProd = Integer.parseInt(visHisPerServ.tbl_historialPerServ.getValueAt(visHisPerServ.tbl_historialPerServ.getSelectedRow(), 5)+"");
        int idPer = Integer.parseInt(visHisPerServ.tbl_historialPerServ.getValueAt(visHisPerServ.tbl_historialPerServ.getSelectedRow(), 6)+"");
        String descripcion = visHisPerServ.tbl_historialPerServ.getValueAt(visHisPerServ.tbl_historialPerServ.getSelectedRow(), 2)+"";
        double precio =  Validaciones.isNumVoid10(visHisPerServ.lblPrecio.getText());


        visFicha.tblFacturaDetalle.setValueAt(idProd, filaDetalle, 0);                            
        visFicha.tblFacturaDetalle.setValueAt(1, filaDetalle, 1);
        visFicha.tblFacturaDetalle.setValueAt(descripcion, filaDetalle, 2);
        visFicha.tblFacturaDetalle.setValueAt(precio, filaDetalle, 3);

        Calculos.calcularTotalDetalles(visFicha.tblFacturaDetalle);                            
        Calculos.setTotalesCabecera(visFicha.tblFacturaDetalle, visFicha);
        visHisPerServ.dispose();
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
        visHisPerServ.txtBuscarCualquierCampo.addKeyListener(keyListenertxtBuscarProductosPorCualquierCampo);

        MouseListener mouseListTblProd = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                     getTableToTxts();
                     desabilitaHabilita(visHisPerServ.btnGuardar,false);
                     desabilitaHabilita(visHisPerServ.btnModificar,true);
                                          
                }
                if(e.getClickCount()==2)
                {
                                                           
                    
                    switch(locale)
                    {
                        case 0 :
                            break;
                        case 1:
                            setProductoServicioFacCab();
                            break;
                        case 2:
                            
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
       
        visHisPerServ.tbl_historialPerServ.addMouseListener(mouseListTblProd);
      
    }
    
    public void setFormatTable(JTable table)
    {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.LEFT);
        table.getColumnModel().getColumn(4).setCellRenderer(tcr);
        table.getColumnModel().getColumn(3).setCellRenderer(tcr);                        
        
        int colHide[] = new int[3];
        colHide[0]=0;
        colHide[1]=5;
        colHide[2]=6;
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
         JTable tblD = visHisPerServ.tbl_historialPerServ;
         visHisPerServ.txt_id.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));
         visHisPerServ.txtPersona.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1)));
         visHisPerServ.cbxServicio.setSelectedItem(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 2)));
         visHisPerServ.dchFechaIni.setDateFormatString(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 3)));
         visHisPerServ.dchFechaFin.setDateFormatString(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 4)));
         visHisPerServ.lblIdProd.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 5)));
         visHisPerServ.lblIdPersona.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 6)));
     }
    
     public void setFocus()
    {
        visHisPerServ.cbxServicio.requestFocus();
        visHisPerServ.cbxServicio.setNextFocusableComponent(visHisPerServ.dchFechaIni); 
        visHisPerServ.dchFechaIni.setNextFocusableComponent(visHisPerServ.dchFechaFin); 
        
        
    }
     public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visHisPerServ.tbl_historialPerServ.getModel();
        int a = visHisPerServ.tbl_historialPerServ.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
    public void showComboServicio()
    {

        try {
           
            ResultSet listCategorias = consHisPerServ.buscarServicios();
            
            DefaultComboBoxModel model =  (DefaultComboBoxModel)visHisPerServ.cbxServicio.getModel();
           
            
            while (listCategorias.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                    
                    model.addElement(listCategorias.getString("descripcion_prod"));
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlHistorialPersServicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consHisPerServ.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlHistorialPersServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        visHisPerServ.cbxServicio.updateUI();
    }
    
    public void showTable()
    {
        try {
            limpiarTabla();
            ResultSet listProd = consHisPerServ.buscarTodosByIdPer(Validaciones.isNumVoid(visHisPerServ.lblIdPersona.getText()));
            
            DefaultTableModel model =  (DefaultTableModel)visHisPerServ.tbl_historialPerServ.getModel();
            Object cols[] = new Object[7];
            
            while (listProd.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                    cols[0] = listProd.getInt("id_hisperser");
                   cols[1] = listProd.getString("nombres");
                   cols[2] = listProd.getString("descripcion_prod").toUpperCase();
                   cols[3] = listProd.getString("fechaini_hisperser");
                   cols[4] = listProd.getString("fechafin_hisperser");
                   cols[5] = listProd.getString("id_prod");
                   cols[6] = listProd.getString("id_per");
                   
                    model.addRow(cols);
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlHistorialPersServicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consHisPerServ.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlHistorialPersServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        visHisPerServ.tbl_historialPerServ.updateUI();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == visHisPerServ.btnGuardar) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
                     
                       
                    int tE = consHisPerServ.getIdByNom(visHisPerServ.cbxServicio.getSelectedItem()+"");     
                  
                    prod.setId_prod(tE);
                    hisPerServ.setProducto_id_HisPerSer(prod);
                    
                    hisPerServ.setFechaIni_HisPerSer(Validaciones.setFormatFecha(visHisPerServ.dchFechaIni.getDate()));
                    hisPerServ.setFechaFin_HisPerSer(Validaciones.setFormatFecha(visHisPerServ.dchFechaFin.getDate()));
                    
                    per.setId(Validaciones.isNumVoid(visHisPerServ.lblIdPersona.getText()));
                    hisPerServ.setPersona_id_HisPerSer(per);
                                     
                    prod.setEstado_prod(1);
                  
                   
                    if (consHisPerServ.registrar(hisPerServ)) {
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
      
      if (e.getSource() == visHisPerServ.btnModificar) 
       {            
            int tE = consHisPerServ.getIdByNom(visHisPerServ.cbxServicio.getSelectedItem()+"");                                         
            prod.setId_prod(tE);
            hisPerServ.setProducto_id_HisPerSer(prod);

            hisPerServ.setFechaIni_HisPerSer(Validaciones.setFormatFecha(visHisPerServ.dchFechaIni.getDate()));
            hisPerServ.setFechaIni_HisPerSer(Validaciones.setFormatFecha(visHisPerServ.dchFechaFin.getDate()));

            per.setId(Validaciones.isNumVoid(visHisPerServ.lblIdPersona.getText()));
            hisPerServ.setPersona_id_HisPerSer(per);
            hisPerServ.setId_HisPerSer(Validaciones.isNumVoid(visHisPerServ.txt_id.getText()));
            prod.setEstado_prod(1);
            
            if (consHisPerServ.modificar(hisPerServ)) {
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
      
      if (e.getSource() == visHisPerServ.btnEliminar) 
       {
           
            hisPerServ.setId_HisPerSer(Integer.parseInt(visHisPerServ.txt_id.getText()));
            hisPerServ.setEstado_HisPerSer(0);
                      
            if (consHisPerServ.eliminar(hisPerServ)) {
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
       if (e.getSource() == visHisPerServ.cbxServicio) 
       {    
           String prod = visHisPerServ.cbxServicio.getSelectedItem()+"";          
           double precio = consHisPerServ.getPrecioByCat(prod);
           visHisPerServ.lblPrecio.setText(precio+"");
           
           if(prod.equals("entrenamiento diario"))
           {
               visHisPerServ.dchFechaIni.setDate(Calculos.getCurrentDate2());
               visHisPerServ.dchFechaFin.setDate(Calculos.getCurrentDate2());
           }

      
       }
       if (e.getSource() == visHisPerServ.btnBuscarPerona) 
       {    
            VisPersona visPer = new VisPersona();
           
            ConsPersona consPer = new ConsPersona();
           
            CtrlPersonas ctrPer=new CtrlPersonas(per, consPer, visPer,visHisPerServ);
            ctrPer.iniciar();
            ctrPer.locale = 3;
      
       }
       
        //
      
       if (e.getSource() == visHisPerServ.btnLimpiar) 
        {
           limpiar();
           desabilitaHabilita(visHisPerServ.btnGuardar,true);
           desabilitaHabilita(visHisPerServ.btnModificar,false);
        }

       
                               
    }
    public void limpiar()
    {
        visHisPerServ.dchFechaFin.setDate(null);
        visHisPerServ.dchFechaIni.setDate(null);
        visHisPerServ.cbxServicio.setSelectedIndex(0);
       
       
    }
    
   
}
