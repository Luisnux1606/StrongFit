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
import modelos.Entrenamiento;
import modelos.EntrenamientoTiempo;
import modelos.FacturaCab;
import modelos.Ficha;
import modelos.Medidas;
import modelos.Membresias;
import modelos.Persona;
import vistas.VisBuscarVentas;
import vistas.VisEntrenamiento;
import vistas.VisFicha;
import vistas.VisMembresia;
import vistas.VisPersona;
import vistas.VisReportes;

/**
 *
 * @author Administrator
 */
public class CtrlProductos implements ActionListener{

    
    VisEntrenamiento visEnt;
    Entrenamiento ent;
    ConsEntrenamiento consEnt;
    Persona per;
    VisFicha visFicha;
    EntrenamientoTiempo entTmp;
    

    String cadBus;
    int locale;
    
    public CtrlProductos(Entrenamiento ent, ConsEntrenamiento consEnt,VisEntrenamiento visEnt,Persona per,VisFicha visFicha)
    {
       
        this.ent = ent;
        this.consEnt = consEnt;        
        this.visEnt =  visEnt;
        this.per = per;
   
        this.visFicha = visFicha;
        
                
        this.visEnt.btnGuardar.addActionListener(this);
        this.visEnt.btnEliminar.addActionListener(this);
        this.visEnt.btnLimpiar.addActionListener(this);
        this.visEnt.btnModificar.addActionListener(this);     
        this.visEnt.btnElegirPersona.addActionListener(this);
        
       entTmp = new EntrenamientoTiempo();
              
        cadBus = "";
        locale = 0; //1:ficha
       
        setFocus();
        setListener();    
//        setTableModel();
       // iniciar();
        
       // visEnt.txtCodPersona.setText(persona.getId()+"");
        
        limpiarTabla();
        showTable();
        showComboEntTiempos();
        
     
    }
    
    
    
    
    public void iniciar()
    {
        visEnt.setTitle("TIEMPOS ENTRENAMIENTOS");            
 
        visEnt.btnGuardar.setToolTipText("Guardar el registro");
        visEnt.btnModificar.setToolTipText("Modificar el registro");
        visEnt.btnEliminar.setToolTipText("Eliminar el registro");
        visEnt.btnLimpiar.setToolTipText("Limpiar el registro");
        //visEnt.tabp_ficha.setSelectedIndex(2);
        limpiar();
        visEnt.setLocation(300,10); 
        visEnt.setSize(1400,1000);                
        visEnt.setVisible(true);
    }
    
    public void showTableByNom(String cad)
    {
        try {
            limpiarTabla();
            
            ResultSet listEntrenamiento = consEnt.buscarTodosPorNomTabla(cad);
            
            DefaultTableModel model =  (DefaultTableModel)visEnt.tbl_entrenamiento.getModel();
            Object cols[] = new Object[5];
            
            while (listEntrenamiento.next()) {
                try {
                   cols[0] = listEntrenamiento.getInt("id_ent");
                   cols[1] = listEntrenamiento.getString("fechaini_en");
                   cols[2] = listEntrenamiento.getString("fechafin_ent").toUpperCase();
                   cols[3] = listEntrenamiento.getString("descripcion_enttiempo");
                   cols[4] = listEntrenamiento.getString("nombres");
                 
                    model.addRow(cols);
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consEnt.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public void desabilitaHabilita(JButton btn,boolean estado)
     {
         btn.setEnabled(estado);
     }
     
     public void cargarEntrenamientoDetalle(JTable table,int idEnt)
     {
                         
           this.ent.setId_ent(idEnt);
                        
           try {
           
            ResultSet rs = consEnt.buscarEntrenamientoCosto(ent);    
            
            DefaultTableModel model =  (DefaultTableModel)visFicha.tblFacturaDetalle.getModel();
            Object cols[] = new Object[8];
            
            while (rs.next()) {
                try {
                    cols[0] = rs.getInt("num");
                    cols[1] = rs.getString("descr").toUpperCase();
                    cols[2] = rs.getDouble("costo_enttiempo");
                                         
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            table.setValueAt(1, table.getRowCount()-1, 0); //1 for id product 999999999
            table.setValueAt(cols[0], table.getRowCount()-1, 1);
            table.setValueAt(cols[1], table.getRowCount()-1, 2);
            table.setValueAt(cols[2], table.getRowCount()-1, 3);
            Calculos.calcularTotalDetalles(table);
            consEnt.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
       
     }
        
     
    public void setListener(){
        KeyListener keyListenertxtBuscarFichaPorCualquierCampo = new KeyListener() {
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
        visEnt.txtBuscarCualquierCampo.addKeyListener(keyListenertxtBuscarFichaPorCualquierCampo);

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
         JTable tblD = visEnt.tbl_entrenamiento;
         visEnt.txt_id.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));
         visEnt.dtchFechaInicio.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1))));
         visEnt.dtchFechaFin.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 2))));
         visEnt.cmbTipoEnt.setSelectedItem(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 3)));
         visEnt.txtPersona.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 4)));
     }
    
     public void setFocus()
    {
        visEnt.cmbTipoEnt.requestFocus();
        visEnt.cmbTipoEnt.setNextFocusableComponent(visEnt.dtchFechaInicio); 
        visEnt.dtchFechaInicio.setNextFocusableComponent(visEnt.dtchFechaFin); 
        visEnt.dtchFechaFin.setNextFocusableComponent(visEnt.txtPersona);
        
    }
     public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visEnt.tbl_entrenamiento.getModel();
        int a = visEnt.tbl_entrenamiento.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
    public void showComboEntTiempos()
    {
        try {
           
            ResultSet listEntrenamiento = consEnt.buscarEntrenamientosTiempos();
            
            DefaultComboBoxModel model =  (DefaultComboBoxModel)visEnt.cmbTipoEnt.getModel();
           
            
            while (listEntrenamiento.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                    
                    model.addElement(listEntrenamiento.getString("descripcion_enttiempo"));
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consEnt.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        visEnt.cmbTipoEnt.updateUI();
    }
     
     public void addRows(JTable table)
    {
        
            Object cols[] = new Object[6];
         DefaultTableModel tb = (DefaultTableModel) visEnt.tbl_entrenamiento.getModel();
         
         for (int i = 0; i <= 5; i++) {
            cols[i] = new String();
        }
    tb.addRow(cols);
    }
    
    
    public void showTable()
    {
        try {
            limpiarTabla();
            ResultSet listEntrenamiento = consEnt.buscarTodos();
            
            DefaultTableModel model =  (DefaultTableModel)visEnt.tbl_entrenamiento.getModel();
            Object cols[] = new Object[6];
            
            while (listEntrenamiento.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                   cols[0] = listEntrenamiento.getInt("id_ent");
                   cols[1] = listEntrenamiento.getString("fechaini_ent");
                   cols[2] = listEntrenamiento.getString("fechafin_ent").toUpperCase();
                   cols[3] = listEntrenamiento.getString("descripcion_enttiempo");
                   cols[4] = listEntrenamiento.getString("id_per");
                   cols[5] = listEntrenamiento.getString("nombres");
                   
                    model.addRow(cols);
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consEnt.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        visEnt.tbl_entrenamiento.updateUI();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == visEnt.btnGuardar) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
           jdc.add(visEnt.dtchFechaInicio);
           jdc.add(visEnt.dtchFechaFin);
           
               if (Validaciones.isDateChooserVoid(jdc)) 
               {        
                    int tE = consEnt.getIdByNom(visEnt.cmbTipoEnt.getSelectedItem()+"");
                     
                    entTmp.setId_entTmp(tE);
                    ent.setEntrenTiempo_id_entTmp(entTmp);
                    ent.setFechaIni_ent(Validaciones.setFormatFecha(visEnt.dtchFechaInicio.getDate()));
                    ent.setFechaFin_ent(Validaciones.setFormatFecha(visEnt.dtchFechaFin.getDate()));  
                    
                    per.setId(Validaciones.isNumVoid(visEnt.txtPersona.getText()));                    
                    ent.setPersona_id_per(per);
                    ent.setEstado_ent(1);
                    if (consEnt.registrar(ent)) {
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
