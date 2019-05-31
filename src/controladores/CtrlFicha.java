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
import consultas.ConsBuscarVentas;
import consultas.ConsCategoria;

import consultas.ConsFacturaCab;
import consultas.ConsFicha;
import consultas.ConsHistorialPersonaServicio;
import consultas.ConsMedidas;
import consultas.ConsMembresias;
import consultas.ConsPersona;
import consultas.ConsProductos;
import consultas.ConsTipoPersona;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.paint.Color.color;
import javax.swing.JButton;
import javax.swing.JColorChooser;
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
import modelos.TipoPersona;
import vistas.VisBuscarVentas;
import vistas.VisCategoria;
import vistas.VisDiarioGeneral;

import vistas.VisFicha;
import vistas.VisHistorialPersonaServicio;
import vistas.VisMembresia;
import vistas.VisPersona;
import vistas.VisPlanCuentas;
import vistas.VisProductos;
import vistas.VisReportes;
import vistas.VisTipoPersona;

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
    
    String nomEmpresa;
    
    public CtrlFicha(Ficha modFicha,ConsFicha consFicha,VisFicha visFicha)
    {
        nomEmpresa = Configuracion.nomEmp;
        
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
        this.visFicha.btnCargarFichas.addActionListener(this);
        
        this.visFicha.mniMembresias.addActionListener(this);
        this.visFicha.mniPersonas.addActionListener(this);
        this.visFicha.mniTipoPersona.addActionListener(this);
        
        this.visFicha.mniReportes.addActionListener(this);
        this.visFicha.mniConsultasClientes.addActionListener(this);
        this.visFicha.mniDiarioGeneral.addActionListener(this);
        this.visFicha.mniPlanCuentas.addActionListener(this);
        this.visFicha.menuSalir.addActionListener(this);

        this.visFicha.mniProductos.addActionListener(this);
        this.visFicha.mniCategoria.addActionListener(this);
        
        this.visFicha.mniColores.addActionListener(this);
        this.visFicha.mniEntrenamientos.addActionListener(this);
        this.visFicha.txtCodPersona.setVisible(false);
        
        this.visFicha.tabp_ficha.setSelectedIndex(3);
        this.visFicha.tabFichaVentas.setSelectedIndex(1);
        
        this.visFicha.lblPersonaId.setVisible(false);
        this.visFicha.lblDsctoIdComp.setVisible(false);
        this.visFicha.txt_id_FacCabComp.setVisible(false);
        this.visFicha.lblIvaIdComp.setVisible(false);
        this.visFicha.lblInfoFechaAna.setToolTipText(" aqui se cargara la fecha de analisis automaticamente");
        this.visFicha.lblInfoFechaMed.setToolTipText(" aqui se cargara la fecha de medidas automaticamente");
        
        habilitaMedAlimAnalisis();     
        cadBus = "";
       
        setFocus();
        setListener();    
        setTableModel(visFicha.tblFacturaDetalleCompras);
       // iniciar();
        
       // visFicha.txtCodPersona.setText(persona.getId()+"");
        
        limpiarTabla(visFicha.tblFichas);
       // showTable();
        
        int colHide[] = new int[1];
        colHide[0]=0;
       
        setHideJtableColumn(visFicha.tblFichas,colHide);
    }
    
    public void habilitaMedAlimAnalisis()
    {
        this.visFicha.tabp_ficha.setEnabledAt(0, false);
        this.visFicha.tabp_ficha.setEnabledAt(1, false);
        this.visFicha.tabp_ficha.setEnabledAt(2, false);
    }
    
    public void deshabilitaMedAlimAnalisis()
    {
        this.visFicha.tabp_ficha.setEnabledAt(0, true);
        this.visFicha.tabp_ficha.setEnabledAt(1, true);
        this.visFicha.tabp_ficha.setEnabledAt(2, true);
    }
    
    public void setMedidasAnalisisFichas()
    {
        showTableByIdPer();
        showTableMedidas();
        showTableAnalisis();
        
    }
    
    public  void showTableMedidas()
    {
        ConsMedidas consMedidas = new ConsMedidas();
        Medidas modMedidas = new Medidas();
        int idPer =Validaciones.isNumVoid(visFicha.txtCodPersona.getText());
        limpiarTabla(visFicha.tblDatos);                  
        //persona.setId(visMedidas.txt_id_persona_u);
           ArrayList<Medidas> listMed = consMedidas.buscarTodosByIdPer(modMedidas,idPer);
           DefaultTableModel model =  (DefaultTableModel)visFicha.tblDatos.getModel();
           Object cols[] = new Object[19];

           for (int i = 0; i < listMed.size(); i++) {
               cols[0] = listMed.get(i).getId();
               cols[1] = listMed.get(i).getFecha();
               cols[2] = listMed.get(i).getPeso();
               cols[3] = listMed.get(i).getEstatura();
               cols[4] = listMed.get(i).getNro_hijos();
               cols[5] = listMed.get(i).getPecho();
               cols[6] = listMed.get(i).getAbdomen_alto();
               cols[7] = listMed.get(i).getCintura();
               cols[8] = listMed.get(i).getAbdomen_bajo();
               cols[9] = listMed.get(i).getCadera();
               cols[10] = listMed.get(i).getPiernas();
               cols[11] = listMed.get(i).getPantorrilla();
               cols[12] = listMed.get(i).getBrazo();
               cols[13] = listMed.get(i).getAntebrazo();
               cols[14] = listMed.get(i).getCuello();
               cols[15] = listMed.get(i).getEspalda();
               cols[16] = listMed.get(i).getPorcentaje_grasa();
               cols[17] = listMed.get(i).getPorcentaje_kgs();        

               model.addRow(cols);                    
           }   
    
    }
    
    public  void showTableAnalisis()
    {
        ConsAnalisis consAnalisis = new ConsAnalisis();
        Analisis modAnalisis = new Analisis();
        limpiarTabla(visFicha.tblAnalisis);                            
        ArrayList<Analisis> listAnalisis = consAnalisis.buscarTodosByIdPer(modAnalisis,Validaciones.isNumVoid(visFicha.txtCodPersona.getText()));
        DefaultTableModel model =  (DefaultTableModel)visFicha.tblAnalisis.getModel();
        Object cols[] = new Object[8];

        for (int i = 0; i < listAnalisis.size(); i++) {
            cols[0] = listAnalisis.get(i).getId();
            cols[1] = listAnalisis.get(i).getFecha();
            cols[2] = listAnalisis.get(i).getExeso_grasa();
            cols[3] = listAnalisis.get(i).getExeso_liquido();
            cols[4] = listAnalisis.get(i).getExeso_total();
            cols[5] = Validaciones.isNumVoid4(listAnalisis.get(i).getRecomendacion_pesas()).toUpperCase();
            cols[6] = Validaciones.isNumVoid4(listAnalisis.get(i).getRecomendacion_cardio()).toUpperCase();
            cols[7] = Validaciones.isNumVoid4(listAnalisis.get(i).getRecomendacion_funcional()).toUpperCase();
            model.addRow(cols);                    
        }   
    
    
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


        visFicha.setTitle(Configuracion.nomEmp  + "FICHA");
        visFicha.lblNomEmpresa.setText(Configuracion.nomEmp);
        visFicha.dtcFecha.setDate(Calculos.getCurrentDate2());     
        visFicha.txt_id_FacCab.setVisible(false);
        visFicha.txt_id_analisis.setVisible(false);
        visFicha.txt_id_datos.setVisible(false);   
        visFicha.lblDsctoId.setVisible(false);
        visFicha.lblIvaId.setVisible(false);
       

        visFicha.btnGuardarFichaG.setToolTipText("Guardar el registro");
        visFicha.btnModificarFichaG.setToolTipText("Modificar el registro");
        visFicha.btnEliminarFichaG.setToolTipText("Eliminar el registro");
        visFicha.btnLimpiarFichaG.setToolTipText("Limpiar el registro");
        
        visFicha.lblNomEmpresa.setText(nomEmpresa);
        limpiar();
        

        visFicha.setLocation(300,10); 
        visFicha.setSize(1400,1000);                
        visFicha.setVisible(true);
    
        visFicha.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
     public void limpiar()
    {
        //visFicha.txtNomPersonaFicha.setText(""); 
        visFicha.dchFecha.setDate(null);
        visFicha.txtInfoFechaAna.setText("");
        visFicha.txtInfoFechaMed.setText("");
        visFicha.tabp_ficha.setEnabledAt(0, false);
        visFicha.tabp_ficha.setEnabledAt(1, false);
        visFicha.tabp_ficha.setEnabledAt(2, false);
        
        //limpiarTabla(visFicha.tblFichas);
       
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
            limpiarTabla(visFicha.tblFichas);
            
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
					
            limpiarTabla(visFicha.tblFichas);
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
         visFicha.lblIdFicha.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));
         
     }
     public void setHideJtableColumn(JTable table, int col[])
    {
        for (int i = 0; i < col.length; i++) {
            table.getColumnModel().getColumn(col[i]).setMaxWidth(0);
            table.getColumnModel().getColumn(col[i]).setMinWidth(0);
            table.getColumnModel().getColumn(col[i]).setPreferredWidth(0);
        }       
    
    }
    
     public void setFocus()
    {
        visFicha.txtCodPersona.requestFocus();
        visFicha.txtCodPersona.setNextFocusableComponent(visFicha.dchFecha);       
    }
     public void limpiarTabla(JTable table){
        DefaultTableModel tb = (DefaultTableModel) table.getModel();
        int a = table.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
     
    public void showTable()
    {
        try {
            limpiarTabla(visFicha.tblFichas);
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
    

    public void showTableByIdPer()
    {
         limpiarTabla(visFicha.tblFichas);
        try {
           
            ResultSet listFicha = consFicha.buscarTodos2ByIdPer(Validaciones.isNumVoid(visFicha.txtCodPersona.getText()));
            System.out.println(visFicha.txtCodPersona.getText());
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
    
    public int guardaMedida()
    {
        Medidas modMedidas = new Medidas();
        ConsMedidas consMed = new ConsMedidas();
        int ultFila = visFicha.tblDatos.getRowCount()-1;
        int lastId=0;
        if (existeMedida()) 
        {
            modMedidas.setFecha(visFicha.tblDatos.getValueAt(ultFila, 1)+"");                
            modMedidas.setPeso(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 2)+""));
            modMedidas.setEstatura(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 3)+""));
            modMedidas.setNro_hijos(Validaciones.isNumVoid(visFicha.tblDatos.getValueAt(ultFila, 4)+""));
            modMedidas.setPecho(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 5)+""));
            modMedidas.setAbdomen_alto(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 6)+""));
            modMedidas.setCintura(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 7)+""));
            modMedidas.setAbdomen_bajo(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 8)+""));
            modMedidas.setCadera(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 9)+""));
            modMedidas.setPiernas(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 10)+""));
            modMedidas.setPantorrilla(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 11)+""));
            modMedidas.setBrazo(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 12)+""));
            modMedidas.setAntebrazo(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 13)+""));
            modMedidas.setCuello(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 14)+""));
            modMedidas.setEspalda(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 15)+""));
            modMedidas.setPorcentaje_grasa(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 16)+""));
            modMedidas.setPorcentaje_kgs(Validaciones.isNumVoid3(visFicha.tblDatos.getValueAt(ultFila, 17)+""));
            modMedidas.setEstado(1);
            
            consMed.registrar(modMedidas);
            lastId = consMed.getLastId();
        }
        else
            lastId = 1;
        
       return lastId;
    }
    
    public int guardaAnalisis()
    {
        Analisis modAnalisis = new Analisis();
        ConsAnalisis consAnalisis = new ConsAnalisis();
        int ultFila = visFicha.tblAnalisis.getRowCount()-1;
        int lastId=0;
        if (existeAnalisis()) 
        {                        
            modAnalisis.setFecha(visFicha.tblAnalisis.getValueAt(ultFila, 1)+"");                
            modAnalisis.setExeso_grasa(Validaciones.isNumVoid2(visFicha.tblAnalisis.getValueAt(ultFila, 2)+""));
            modAnalisis.setExeso_liquido(Validaciones.isNumVoid2(visFicha.tblAnalisis.getValueAt(ultFila, 3)+""));
            modAnalisis.setExeso_total(Validaciones.isNumVoid2(visFicha.tblAnalisis.getValueAt(ultFila, 4)+""));
            modAnalisis.setRecomendacion_pesas(visFicha.tblAnalisis.getValueAt(ultFila, 5)+"".toUpperCase());
            modAnalisis.setRecomendacion_cardio(visFicha.tblAnalisis.getValueAt(ultFila, 6)+"".toUpperCase());
            modAnalisis.setRecomendacion_funcional(visFicha.tblAnalisis.getValueAt(ultFila, 7)+"".toUpperCase());
            modAnalisis.setEstado(1);       
            
            
            consAnalisis.registrar(modAnalisis);
            lastId = consAnalisis.getLastId();
        }
        else
            lastId = 1;
        
       return lastId;
    }
    
    public boolean existeMedida()
    {        
        int id=0;
        int a = visFicha.tblDatos.getRowCount()-1;
     
            id = Validaciones.isNumVoid(visFicha.tblDatos.getValueAt(a, 0)+"");
            if (id==0) 
                return true;
            else
                return false;                        
    } 
        
    public boolean existeAnalisis()
    {        
        int id=0;
        int a = visFicha.tblAnalisis.getRowCount()-1;
     
            id = Validaciones.isNumVoid(visFicha.tblAnalisis.getValueAt(a, 0)+"");
            if (id==0) 
                return true;
            else
                return false;                        
    } 
  public void limpiarMedida()
    {
        
        visFicha.dtcFecha.setDate(null);                
        visFicha.txtPeso.setText("");
        visFicha.txtEstatura.setText("");
        visFicha.txtNroHijos.setText("");
        visFicha.txtPecho.setText("");
        visFicha.txtAbdomAlto.setText("");
        visFicha.txtCintura.setText("");
        visFicha.txtAbdomBajo.setText("");
        visFicha.txtCadera.setText("");
        visFicha.txtPierna.setText("");
        visFicha.txtPantorrilla.setText("");
        visFicha.txtBrazo.setText("");
        visFicha.txtAntebrazo.setText("");
        visFicha.txtCuello.setText("");
        visFicha.txtEspalda.setText("");
        visFicha.txtPorGrasa.setText("");
        visFicha.txtPorKilogs.setText("");
  
        limpiarTablaAgregadoMedidas();
       // limpiarTabla();
        
    }      
    public void limpiarTablaAgregadoAnalisis(){
        DefaultTableModel tb = (DefaultTableModel) visFicha.tblAnalisis.getModel();
        int a = visFicha.tblAnalisis.getRowCount()-1;
        int id = 0;
        for (int i = a; i >= 0; i--) {    
            id = Validaciones.isNumVoid(visFicha.tblAnalisis.getValueAt(i, 0)+"");
            if (id==0) 
                tb.removeRow(tb.getRowCount()-1);
            
            
        } 
    }     
  
  
    public void limpiarTablaAgregadoMedidas(){
        DefaultTableModel tb = (DefaultTableModel) visFicha.tblDatos.getModel();
        int a = visFicha.tblDatos.getRowCount()-1;
        int id = 0;
        for (int i = a; i >= 0; i--) {    
            id = Validaciones.isNumVoid(visFicha.tblDatos.getValueAt(i, 0)+"");
            if (id==0) 
                tb.removeRow(tb.getRowCount()-1);
            
            
        } 
    }
    
    public void limpiarAnalisis()
    {
        visFicha.dtcFechaAnalisis.setDate(null);                
        visFicha.txtExcesoGrasa.setText("");
        visFicha.txtExcesoLiquido.setText("");
        visFicha.txtExcesoTotal.setText("");
        visFicha.txtRecomPesas.setText("");
        visFicha.txtRecomCardio.setText("");
        visFicha.txtRecomFuncional.setText("");
        limpiarTablaAgregadoAnalisis();
        //limpiarTabla();
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
                   
                    int lastMed = guardaMedida();
                    medidas.setId(lastMed);
                    modFicha.setMedidas(medidas);
                    
                    int lastAna = guardaAnalisis();
                    analisis.setId(lastAna);
                    modFicha.setAnalisis(analisis);
                    
                    persona.setId(Integer.parseInt(visFicha.txtCodPersona.getText()));
                    modFicha.setPersona(persona);
                    
                    modFicha.setFecha(Validaciones.setFormatFecha(visFicha.dchFecha.getDate()));                               
                    modFicha.setEstado(1);
                    //valido > si es nullo pongo anon, llenoen mod i guardo
                    //validaAnonimos();
                   
                    if (consFicha.registrar(modFicha)) {
                        JOptionPane.showMessageDialog(null, "Registro Guardado!");
                       
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error al Guardar");
                       
                    }
                //    showTable();
                    setMedidasAnalisisFichas();
                    limpiar();
                    
                    
                    //limpiar medidas
                    limpiarMedida();
                    desabilitaHabilita(visFicha.btnGuardar,true);
                    desabilitaHabilita(visFicha.btnModificar,true);
                    desabilitaHabilita(visFicha.btnEliminar,true);      
                    
                    //limpiar analisis
                    limpiarAnalisis();
                    desabilitaHabilita(visFicha.btnGuardarAnalisis,true);
                    desabilitaHabilita(visFicha.btnModificarAnalisis,true);
                    desabilitaHabilita(visFicha.btnEliminarAnalisis,true); 
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
           
            modFicha.setId(Validaciones.isNumVoid(visFicha.lblIdFicha.getText()));
            modFicha.setEstado(0);
            int o= JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar el registro?", "Confirmar eliminar?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);          
            if (o ==0) 
            {              
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
         if (e.getSource() == visFicha.btnCargarFichas) 
        {           
            setMedidasAnalisisFichas();
            if (!Validaciones.isNumVoid1(visFicha.txtCodPersona.getText())) {
                deshabilitaMedAlimAnalisis();
            }
        }
                
         if (e.getSource() == visFicha.mniReportes) 
         {
            
            VisReportes visRepo = new VisReportes();
           
            CtrlReportes ctrlRepo = new CtrlReportes(visRepo);
            
            
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
         
        if (e.getSource()==visFicha.mniTipoPersona) //Cuando toca el menú Tipo de Persona
        {
            VisTipoPersona visTipPer= new VisTipoPersona();
            TipoPersona modtipPer=new TipoPersona();
            ConsTipoPersona constipPer=new ConsTipoPersona();
            
            Ficha ficha=new Ficha();
            CtrlTipoPersonas ctrTipPer= new CtrlTipoPersonas(modtipPer, visTipPer, constipPer, visFicha);
            ctrTipPer.iniciar();
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
            VisHistorialPersonaServicio visHis = new VisHistorialPersonaServicio();
            ConsHistorialPersonaServicio consHPS = new ConsHistorialPersonaServicio();
            HistorialPersonaServicio hisPS = new HistorialPersonaServicio();

            CtrlHistorialPersServicio ctlHis = new CtrlHistorialPersServicio(visHis, hisPS, consHPS, visFicha,persona);         
             ctlHis.locale = 1;
            ctlHis.iniciar();
           
        }
        
        if (e.getSource()==visFicha.mniDiarioGeneral) //Cuando toca el menú diario general
        {
            VisDiarioGeneral visDiario=new VisDiarioGeneral();
                    
            CtrlDiarioGeneral ctrDiario=new CtrlDiarioGeneral(visDiario);
            ctrDiario.iniciar();
        }
        
        if (e.getSource()==visFicha.mniPlanCuentas) //Cuando toca el menú diario general
        {
            VisPlanCuentas visPlan=new VisPlanCuentas();
                    
            CtrlPlanCuentas ctrPlan=new CtrlPlanCuentas(visPlan);
            ctrPlan.iniciar();
        }
        
        if (e.getSource()==visFicha.mniColores) //Cuando toca el menú diario general
        {
            JColorChooser ventanaDeColores=new JColorChooser();
            Color color=ventanaDeColores.showDialog(null, "Seleccione un Color", Color.gray);
            visFicha.pnlFicha.setBackground(color);
            visFicha.pnlVentas.setBackground(color);
            visFicha.pnlMedidas.setBackground(color);
            visFicha.pnlAnalisis.setBackground(color);
            visFicha.pnlPlanAlim.setBackground(color);
            visFicha.pnlFichaCrear.setBackground(color);
            visFicha.pnlVentas.setBackground(color);
            visFicha.pnlVentasInterno.setBackground(color);
            visFicha.pnlVentasComponentes.setBackground(color);             
            visFicha.pnlMedidasComponentes.setBackground(color);
            visFicha.pnlMedidasComponentes2.setBackground(color);
            visFicha.pnlAnalisisComponentes.setBackground(color);
            visFicha.pnlFichaComponentes.setBackground(color);
            
           // visPer.pnl_personas.setBackground(color);
            //visMemb.pnl_personas.setBackground(color);
            
                    
            
        }
         
         if (e.getSource() == visFicha.menuSalir) 
         {
            visFicha.dispose();
         }
                               
    }
   
    
   

    
}
