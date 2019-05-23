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
import consultas.ConsBuscarVentas;
import consultas.ConsCategoria;

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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
import vistas.VisBuscarVentas;
import vistas.VisCategoria;

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
public class CtrlFicha implements ActionListener{

    Ficha modFicha;
    ArrayList<FacturaCab> lstFicha;
    ConsFicha consFicha;
    VisFicha visFicha;
    VisReportes visReportes;
    VisMembresia visMemb;
    VisPersona visPer;
    
    Persona persona;
    Analisis analisis;
    Medidas medidas;
    
    String cadBus;
    
    public CtrlFicha(Ficha modFicha,ConsFicha consFicha,VisFicha visFicha)
    {
        this.modFicha = modFicha;
        this.consFicha = consFicha;
        this.visFicha = visFicha;
        this.persona = new Persona();
        this.visMemb =  visMemb;
       
        
       // persona = new Persona();
        analisis = new Analisis();
        medidas =  new Medidas();
        
        this.visFicha.btnGuardarFichaG.addActionListener(this);
        this.visFicha.btnEliminarFichaG.addActionListener(this);
        this.visFicha.btnLimpiarFichaG.addActionListener(this);
        this.visFicha.btnModificarFichaG.addActionListener(this);     
        this.visFicha.btnElegirPersonaG.addActionListener(this);
        
        this.visFicha.mniMembresias.addActionListener(this);
        this.visFicha.mniPersonas.addActionListener(this);
        this.visFicha.mniReportes.addActionListener(this);
        this.visFicha.mniConsultasClientes.addActionListener(this);
        this.visFicha.menuSalir.addActionListener(this);

        this.visFicha.mniProductos.addActionListener(this);
        this.visFicha.mniCategoria.addActionListener(this);
        
        this.visFicha.tabp_ficha.setSelectedIndex(2);
        this.visFicha.tabFichaVentas.setSelectedIndex(1);
              
        cadBus = "";
       
        setFocus();
        setListener();    
        setTableModel(visFicha.tblFacturaDetalle);
       // iniciar();
        
       // visFicha.txtCodPersona.setText(persona.getId()+"");
        
        limpiarTabla();
        showTable();
    }
    
    public void setTableModel(JTable table)
    {
       
        
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(3).setCellRenderer(tcr);
        table.getColumnModel().getColumn(2).setCellRenderer(tcr);

        table.updateUI();
    }
    
    
    
    public ArrayList<String> getAnonimos()
    {        
        ArrayList<String> listFicha = consFicha.buscarAnonyms();
        return listFicha;
    }
    
    public void iniciar()
    {
        
      try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
               // UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }


        visFicha.setTitle("FICHA");
        
        visFicha.dtcFecha.setDate(Calculos.getCurrentDate2());     
        visFicha.txt_id_FacCab.setVisible(false);
        visFicha.txt_id_analisis.setVisible(false);
        visFicha.txt_id_datos.setVisible(false);       
       

        visFicha.lbl_personaFicha.setText("");

        visFicha.lbl_personaFicha.setText("");

 
        visFicha.btnGuardarFichaG.setToolTipText("Guardar el registro");
        visFicha.btnModificarFichaG.setToolTipText("Modificar el registro");
        visFicha.btnEliminarFichaG.setToolTipText("Eliminar el registro");
        visFicha.btnLimpiarFichaG.setToolTipText("Limpiar el registro");
        //visFicha.tabp_ficha.setSelectedIndex(2);
        limpiar();
        

        visFicha.setLocation(300,10); 
        visFicha.setSize(1400,1000);                
        visFicha.setVisible(true);
    
        visFicha.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
     
    }
    
    public void setCmbxMembresias()
    {
        
    }
    /*
    public void setTableModel()
    {            
       Color rojo = new Color(254,000,000);  
       Color amarillo = new Color(255,255,000);
       visFicha.tblFicha.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
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
            
            DefaultTableModel model =  (DefaultTableModel)visFicha.tblFichas.getModel();
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
                    Logger.getLogger(CtrlFicha.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consFicha.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFicha.class.getName()).log(Level.SEVERE, null, ex);
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
        visFicha.txtBuscarFichaPorCualquierCampo.addKeyListener(keyListenertxtBuscarFichaPorCualquierCampo);

        MouseListener mouseListTblFicha = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(e.getClickCount()==1)
                {
                    getTableToTxts();
                     desabilitaHabilita(visFicha.btnGuardarFichaG,false);
                     desabilitaHabilita(visFicha.btnModificarFichaG,true);
                                          
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
       
        visFicha.tblFichas.addMouseListener(mouseListTblFicha);
      
    }
     public void getTableToTxts()
     {
         JTable tblD = visFicha.tblFichas;
         visFicha.txtCodPersona.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 3)));
         visFicha.dchFecha.setDate(Validaciones.setStringToDate(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1))));
         
         
     }
    
     public void setFocus()
    {
        visFicha.txtCodPersona.requestFocus();
        visFicha.txtCodPersona.setNextFocusableComponent(visFicha.dchFecha);       
    }
     public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visFicha.tblFichas.getModel();
        int a = visFicha.tblFichas.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
     
    public void showTable()
    {
        try {
            limpiarTabla();
            ResultSet listFicha = consFicha.buscarTodos2();
            
            DefaultTableModel model =  (DefaultTableModel)visFicha.tblFichas.getModel();
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
                    Logger.getLogger(CtrlFicha.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consFicha.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFicha.class.getName()).log(Level.SEVERE, null, ex);
        }
        visFicha.tblFichas.updateUI();
    }
    
    
    
    public void validaAnonimos()
    {
        
      
            persona.setId(Integer.parseInt(visFicha.txtCodPersona.getText()));
            modFicha.setPersona(persona); 
      
        ///*******
        
            analisis.setId(Integer.parseInt(visFicha.txt_id_analisis.getText()));
            modFicha.setAnalisis(analisis);
       
        //*********
      
           medidas.setId(Integer.parseInt(visFicha.txt_id_datos.getText()));
           modFicha.setMedidas(medidas);
        

    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == visFicha.btnGuardarFichaG) 
        {       
           ArrayList<JDateChooser> jdc=new ArrayList<>();
           jdc.add(visFicha.dchFecha);
           
               if (Validaciones.isDateChooserVoid(jdc)) 
               {                   
                    modFicha.setFecha(Validaciones.setFormatFecha(visFicha.dchFecha.getDate()));                               
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
      
      if (e.getSource() == visFicha.btnModificarFichaG) 
       {            
            modFicha.setId(Integer.parseInt(visFicha.tblFichas.getValueAt(visFicha.tblFichas.getSelectedRow(), 0)+""));
            modFicha.setFecha(Validaciones.setFormatFecha(visFicha.dchFecha.getDate()));                       
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
      
      if (e.getSource() == visFicha.btnEliminarFichaG) 
       {
           
            modFicha.setId(Integer.parseInt(visFicha.txt_id_FacCab.getText()));
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
      
       if (e.getSource() == visFicha.btnLimpiarFichaG) 
        {
           limpiar();
           desabilitaHabilita(visFicha.btnGuardarFichaG,true);
           desabilitaHabilita(visFicha.btnModificarFichaG,false);
        }

        if (e.getSource() == visFicha.btnElegirPersonaG) 
        {
           
            VisPersona visPer = new VisPersona();
            Persona per  = new Persona();
            ConsPersona consPer = new ConsPersona();
                
            Ficha ficha = new Ficha();
            CtrlPersonas ctrPer=new CtrlPersonas(persona, consPer, visPer,visFicha);
            ctrPer.iniciar();
            ctrPer.locale = 1;
        }                 
        
                
         if (e.getSource() == visFicha.mniReportes) 
         {
            
            VisReportes visRepo = new VisReportes();
          
            CtrlReportes ctrlRepo = new CtrlReportes(visRepo,"C:/Users/Administrator/Documents/NetBeansProjects/TroyaGym/src/reportes/");
            
            
         }
         //////
         if (e.getSource() == visFicha.mniMembresias) 
         {   
            VisMembresia visMem = new VisMembresia();            
            VisFicha visFicha= new VisFicha();
            Membresias memMod  = new Membresias();
            ConsMembresias consMem = new ConsMembresias();
            Ficha ficha  =  new Ficha();
            CtrlMembresias ctrlMem = new CtrlMembresias(memMod,consMem,visMem,ficha,visFicha);
            
         }
         
         if (e.getSource() == visFicha.mniPersonas) 
         {
            
            VisPersona visPer = new VisPersona();
            Persona per  = new Persona();
            ConsPersona consPer = new ConsPersona();
           
            Ficha ficha  =  new Ficha();
            CtrlPersonas ctrPer=new CtrlPersonas(persona, consPer, visPer,visFicha);
            ctrPer.iniciar();
        }
         
         if (e.getSource() == visFicha.mniConsultasClientes) {
            
            VisBuscarVentas visBuscarVentas = new VisBuscarVentas();
            
            ConsBuscarVentas consFacCab = new ConsBuscarVentas();
           
            Ficha ficha  =  new Ficha();
            CtrlBuscarVentas ctrBuscarVentas=new CtrlBuscarVentas(consFacCab, visBuscarVentas,visFicha);
            ctrBuscarVentas.iniciar();
        }
         
        if (e.getSource() == visFicha.mniProductos) //Cuando toca el menú productos
        {   
            VisProductos visProd = new VisProductos();
            ConsProductos consProd = new ConsProductos();
            Producto prod=new Producto();
            Categoria cat=new Categoria();
            
            CtrlProductos ctrProd=new CtrlProductos(prod,consProd, visProd, visFicha);
            ctrProd.locale = 1;
            ctrProd.iniciar();
        }
        
        if (e.getSource()==visFicha.mniCategoria) //Cuando toca el menú categorías
        {
            Categoria modCat=new Categoria();
            VisCategoria visCat=new VisCategoria();
            ConsCategoria consCat=new ConsCategoria();
            
            CtrlCategoria ctrCat=new CtrlCategoria (modCat, visCat, consCat, visFicha);
            ctrCat.iniciar();
        }
        
        if (e.getSource()==visFicha.mniEntrenamientos) //Cuando toca el menú categorías
        {
            HistorialPersonaServicio hisPerServ=new HistorialPersonaServicio();
            VisHistorialPersonaServicio visCat=new VisHistorialPersonaServicio();
            ConsHistorialPersonaServicio consCat=new ConsHistorialPersonaServicio();
            
            CtrlHistorialPersServicio ctrCat=new CtrlHistorialPersServicio (visCat, hisPerServ, consCat, visFicha,persona);
            ctrCat.iniciar();
        }
         
         if (e.getSource() == visFicha.menuSalir) 
         {
            visFicha.dispose();
         }
                               
    }
    public void limpiar()
    {
        visFicha.txtCodPersona.setText(""); 
        visFicha.dchFecha.setDate(Calculos.getCurrentDate2());
       
    }
    
   

    
}
