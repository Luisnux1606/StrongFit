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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.Analisis;
import modelos.Entrenamiento;
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
public class CtrlEntrenamiento implements ActionListener{

    
    VisEntrenamiento visEnt;
    Entrenamiento ent;
    ConsEntrenamiento consEnt;
    
    String cadBus;
    
    public CtrlEntrenamiento(Entrenamiento ent, ConsEntrenamiento consEnt,VisEntrenamiento visEnt)
    {
       
        this.ent = ent;
        this.consEnt = consEnt;        
        this.visEnt =  visEnt;
       
        
        
        this.visEnt.btnGuardar.addActionListener(this);
        this.visEnt.btnEliminar.addActionListener(this);
        this.visEnt.btnLimpiar.addActionListener(this);
        this.visEnt.btnModificar.addActionListener(this);     
        this.visEnt.btnElegirPersona.addActionListener(this);
        
       
              
        cadBus = "";
       
        setFocus();
        setListener();    
//        setTableModel();
       // iniciar();
        
       // visEnt.txtCodPersona.setText(persona.getId()+"");
        
        limpiarTabla();
        showTable();
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
    
        visEnt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
     
    }
    
    public void setCmbxMembresias()
    {
        
    }
    /*
    public void setTableModel()
    {            
       Color rojo = new Color(254,000,000);  
       Color amarillo = new Color(255,255,000);
       visEnt.tblFicha.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
         
                
                if(Calculos.getDiffDaysToFinish(table.getValueAt(row, 4)+"")<=5 &&Calculos.getDiffDaysToFinish(table.getValueAt(row, 4)+"")>=0)
                {
                     c.setBackground(amarillo); //proximos a terminarse
                }
                else
                    if (Calculos.dateGreaterThanCurrent(table.getValueAt(row, 4)+"")==true) {
                     c.setBackground(rojo);
                    }
                if(Double.parseDouble(table.getValueAt(row, 7)+"")!=0 )
                {
                 c.setBackground(rojo); //proximos a terminarse o pendientes de pago
                }
               
               
                
                return c;
            }
            
            
            });
     
    }*/
    
    public void showTableByNom(String cad)
    {
        try {
            limpiarTabla();
            
            ResultSet listFicha = consFicha.buscarTodosPorNomTabla(cad);
            
            DefaultTableModel model =  (DefaultTableModel)visEnt.tblFichas.getModel();
            Object cols[] = new Object[8];
            
            while (listFicha.next()) {
                try {
                   cols[0] = listFicha.getInt("id_ficha");
                    cols[1] = listFicha.getString("fecha_ficha");
                    cols[2] = listFicha.getString("nombresApellidos").toUpperCase();
                    cols[3] = listFicha.getString("id_per");
                    cols[4] = listFicha.getString("fecha_med");
                    cols[5] = listFicha.getString("id_med");
                    cols[6] = listFicha.getString("fecha_ana");
                    cols[7] = listFicha.getString("id_ana");
                    model.addRow(cols);
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlEntrenamiento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consFicha.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlEntrenamiento.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public void desabilitaHabilita(JButton btn,boolean estado)
     {
         btn.setEnabled(estado);
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
        visEnt.txtBuscarFichaPorCualquierCampo.addKeyListener(keyListenertxtBuscarFichaPorCualquierCampo);

        MouseListener mouseListTblFicha = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                    getTableToTxts();
                     desabilitaHabilita(visEnt.btnGuardar,false);
                     desabilitaHabilita(visEnt.btnModificar,true);
                                          
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
       
        visEnt.tblFichas.addMouseListener(mouseListTblFicha);
      
    }
     public void getTableToTxts()
     {
         JTable tblD = visEnt.tblFichas;
         visEnt.txtCodPersona.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 3)));
         visEnt.dchFecha.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1))));
         
         
     }
    
     public void setFocus()
    {
        visEnt.txtCodPersona.requestFocus();
        visEnt.txtCodPersona.setNextFocusableComponent(visEnt.dchFecha);       
    }
     public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visEnt.tblFichas.getModel();
        int a = visEnt.tblFichas.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
    
    public void showTable()
    {
        try {
            limpiarTabla();
            ResultSet listFicha = consFicha.buscarTodos2();
            
            DefaultTableModel model =  (DefaultTableModel)visEnt.tblFichas.getModel();
            Object cols[] = new Object[8];
            
            while (listFicha.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                    cols[0] = listFicha.getInt("id_ficha");
                    cols[1] = listFicha.getString("fecha_ficha");
                    cols[2] = listFicha.getString("nombresApellidos").toUpperCase();
                    cols[3] = listFicha.getString("id_per");
                    cols[4] = listFicha.getString("fecha_med");
                    cols[5] = listFicha.getString("id_med");
                    cols[6] = listFicha.getString("fecha_ana");
                    cols[7] = listFicha.getString("id_ana");
                    model.addRow(cols);
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlEntrenamiento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consFicha.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlEntrenamiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        visEnt.tblFichas.updateUI();
    }
    
    
    
    public void validaAnonimos()
    {
        
      
            persona.setId(Integer.parseInt(visEnt.txtCodPersona.getText()));
            modFicha.setPersona(persona); 
      
        ///*******
        
            analisis.setId(Integer.parseInt(visEnt.txt_id_analisis.getText()));
            modFicha.setAnalisis(analisis);
       
        //*********
      
           medidas.setId(Integer.parseInt(visEnt.txt_id_datos.getText()));
           modFicha.setMedidas(medidas);
        
        System.out.println("personar: "+persona.getId()+" anal: "+analisis.getId()+" med: "+medidas.getId());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == visEnt.btnGuardar) 
       {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
           jdc.add(visEnt.dchFecha);
           
               if (Validaciones.isDateChooserVoid(jdc)) 
               {                   
                    modFicha.setFecha(Validaciones.setFormatFecha(visEnt.dchFecha.getDate()));                               
                    modFicha.setEstado(1);
                    //valido > si es nullo pongo anon, llenoen mod i guardo
                    validaAnonimos();

                    if (consFicha.registrar(modFicha)) {
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
            modFicha.setId(Integer.parseInt(visEnt.tblFichas.getValueAt(visEnt.tblFichas.getSelectedRow(), 0)+""));
            modFicha.setFecha(Validaciones.setFormatFecha(visEnt.dchFecha.getDate()));                       
             validaAnonimos();
            if (consFicha.modificar(modFicha)) {
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
           
            modFicha.setId(Integer.parseInt(visEnt.txt_id_FacCab.getText()));
            modFicha.setEstado(0);
                      
            if (consFicha.eliminar(modFicha)) {
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

        if (e.getSource() == visEnt.btnElegirPersonaG) 
        {
           
            VisPersona visPer = new VisPersona();
            Persona per  = new Persona();
            ConsPersona consPer = new ConsPersona();
                
            Ficha ficha = new Ficha();
            CtrlPersonas ctrPer=new CtrlPersonas(persona, consPer, visPer,visEnt);
            ctrPer.iniciar();
            ctrPer.locale = 1;
        } 
        
          
         if (e.getSource() == visEnt.mniReportes) 
         {
            
            VisReportes visRepo = new VisReportes();
          
            CtrlReportes ctrlRepo = new CtrlReportes(visRepo,"C:/Users/Administrator/Documents/NetBeansProjects/TroyaGym/src/reportes/");
            
            
         }
         //////
         if (e.getSource() == visEnt.mniMembresias) 
         {   
            VisMembresia visMem = new VisMembresia();            
            VisFicha visEnt= new VisFicha();
            Membresias memMod  = new Membresias();
            ConsMembresias consMem = new ConsMembresias();
            Ficha ficha  =  new Ficha();
            CtrlMembresias ctrlMem = new CtrlMembresias(memMod,consMem,visMem,ficha,visEnt);
            
         }
         
         if (e.getSource() == visEnt.mniPersonas) {
            
            VisPersona visPer = new VisPersona();
            Persona per  = new Persona();
            ConsPersona consPer = new ConsPersona();
           
            Ficha ficha  =  new Ficha();
            CtrlPersonas ctrPer=new CtrlPersonas(persona, consPer, visPer,visEnt);
            ctrPer.iniciar();
        }
         
         if (e.getSource() == visEnt.mniConsultasClientes) {
            
            VisBuscarVentas visBuscarVentas = new VisBuscarVentas();
            FacturaCab facCab  = new FacturaCab();
            ConsFacturaCab consFacCab = new ConsFacturaCab();
           
            Ficha ficha  =  new Ficha();
            CtrlBuscarVentas ctrBuscarVentas=new CtrlBuscarVentas(facCab, consFacCab, visBuscarVentas,visEnt);
            ctrBuscarVentas.iniciar();
        }

         
         if (e.getSource() == visEnt.menuSalir) 
         {
            visEnt.dispose();
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
