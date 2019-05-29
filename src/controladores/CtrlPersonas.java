/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Calculos;
import assets.Configuracion;
import assets.Validaciones;
import consultas.ConsAnalisis;
import consultas.ConsFacturaCab;
import consultas.ConsMedidas;
import consultas.ConsMembresias;
import consultas.ConsPersona;
import consultas.ConsTipoPersona;
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
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.Analisis;
import modelos.FacturaCab;
import modelos.Ficha;
import modelos.Medidas;
import modelos.Membresias;
import modelos.Persona;
import modelos.TipoPersona;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import vistas.VisFicha;
import vistas.VisHistorialPersonaServicio;
import vistas.VisMembresia;
import vistas.VisPersona;
import vistas.VisProductos;
import vistas.VisReportes;
import visual.facturacion.MyTableModel;

/**
 *
 * @author Administrator
 */
public class CtrlPersonas implements ActionListener {

    
    Persona modPer;
    ArrayList<Persona> lstPersonas;
    ConsPersona consPer;
    VisPersona visPersona;
    VisMembresia visMemb;
    VisFicha visFicha;   
    VisHistorialPersonaServicio visHisPerServ;
//    VisEntrenamiento visEnt;
    Object vis;
    String cadBus;
    MyTableModel dT;
    
    int locale;
    public CtrlPersonas(Persona modPersona, ConsPersona consPersona,VisPersona visPersona,Object vis)
    {
        this.modPer = modPersona;
        this.consPer = consPersona;
        this.visPersona = visPersona;        
        this.vis = vis;
        
        this.visPersona.btnGuardar.addActionListener(this);
        this.visPersona.btnEliminar.addActionListener(this);
        this.visPersona.btnLimpiar.addActionListener(this);
        this.visPersona.btnModificar.addActionListener(this);
        this.visPersona.btnBuscar.addActionListener(this);
       
        locale = 0; // 1: ficha , 2: factura, 3: entrenamiento
        
       
        cadBus = "";
        showTable();
        setFocus();
        setListener();
        setTableModel();
        showComboTipoPersonas();
        escribirCombos();
        int colHide[] = new int[1];
        colHide[0]=10;
       
        setHideJtableColumn(visPersona.tbl_personas,colHide);
       
    }
    
      private void escribirCombos()
      {
        AutoCompleteDecorator.decorate(visPersona.cmbxGenero);   
        AutoCompleteDecorator.decorate(visPersona.cmbTipoPersona);   
           
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
        visPersona.txt_cedula.requestFocus();
        visPersona.txt_cedula.setNextFocusableComponent(visPersona.txt_nombres);
        visPersona.txt_nombres.setNextFocusableComponent(visPersona.txt_apellidos);
        visPersona.txt_apellidos.setNextFocusableComponent(visPersona.dtc_fechaNac);    
        visPersona.dtc_fechaNac.setNextFocusableComponent(visPersona.cmbxGenero);
        visPersona.cmbxGenero.setNextFocusableComponent(visPersona.txtCorreoElect);
        visPersona.txtCorreoElect.setNextFocusableComponent(visPersona.txt_nro_fono); 
        visPersona.txt_nro_fono.setNextFocusableComponent(visPersona.txt_edad); 
    }
    
    public void iniciar()
    {
        visPersona.setTitle(Configuracion.nomEmp + " GESTION DE PERSONAS");        
        visPersona.setLocationRelativeTo(null);
        visPersona.setSize(1000,700);
        visPersona.setVisible(true);
        
        visPersona.txt_id.setVisible(false);
        
        visPersona.btnBuscar.setToolTipText("Buscar el registro");
        visPersona.btnGuardar.setToolTipText("Guardar el registro");
        visPersona.btnModificar.setToolTipText("Modificar el registro");
        visPersona.btnEliminar.setToolTipText("Eliminar el registro");
        visPersona.btnLimpiar.setToolTipText("Limpiar el registro");
        
        visPersona.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void showComboTipoPersonas()
    {
        ConsTipoPersona cons = new ConsTipoPersona();
        try {
           
            ResultSet listTipoPersonas = cons.buscarTipoPersona();
            
            DefaultComboBoxModel model =  (DefaultComboBoxModel)visPersona.cmbTipoPersona.getModel();           
            
            while (listTipoPersonas.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                    
                    model.addElement(listTipoPersonas.getString("descripcion_tipoper").toUpperCase());
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            cons.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        visPersona.cmbTipoPersona.updateUI();
    } 
    
    @Override
    public void actionPerformed(ActionEvent e) {
         
       if (e.getSource() == visPersona.btnGuardar) 
       {       
           ArrayList<JTextField> jtx=new ArrayList<>();
           jtx.add(visPersona.txt_cedula);
           
               if (Validaciones.isVoid(jtx) && !Validaciones.existeCedula(visPersona.txt_cedula.getText()) ) {
                 
                    modPer.setCedula(visPersona.txt_cedula.getText());                
                    modPer.setNombre(visPersona.txt_nombres.getText().toUpperCase());
                    modPer.setApellido(Validaciones.isNumVoid4(visPersona.txt_apellidos.getText().toUpperCase()));
                    modPer.setNro_fono(visPersona.txt_nro_fono.getText());
                    modPer.setEdad(Validaciones.isNumVoid(visPersona.txt_edad.getText()));                  
                    modPer.setFecha_nac(Validaciones.setFormatFecha(visPersona.dtc_fechaNac.getDate()));
                    modPer.setMail(visPersona.txtCorreoElect.getText());
                    modPer.setGenero(visPersona.cmbxGenero.getSelectedItem()+"".toUpperCase());
                    TipoPersona tPer = new TipoPersona();
                    int idTipoPer = consPer.getIdTipoPerByNom(visPersona.cmbTipoPersona.getSelectedItem()+"");
                    tPer.setId_tipoPer(idTipoPer);
                    modPer.setTipoPersona(tPer);
                    modPer.setEstado(1);

                    if (consPer.registrar(modPer)) {
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
       
       if (e.getSource() == visPersona.btnModificar) 
       {
          
            modPer.setId(Integer.parseInt(visPersona.txt_id.getText()));
            modPer.setCedula(visPersona.txt_cedula.getText());
            modPer.setNombre(visPersona.txt_nombres.getText().toUpperCase());
            modPer.setApellido(visPersona.txt_apellidos.getText().toUpperCase());
            modPer.setNro_fono(visPersona.txt_nro_fono.getText());
            modPer.setEdad(Integer.parseInt(Validaciones.isNumVoid(visPersona.txt_edad.getText())+""));
            modPer.setFecha_nac(Validaciones.setFormatFecha(visPersona.dtc_fechaNac.getDate()));
            modPer.setMail(visPersona.txtCorreoElect.getText());
            modPer.setGenero(visPersona.cmbxGenero.getSelectedItem()+"".toUpperCase());
            TipoPersona tPer = new TipoPersona();
            int idTipoPer = consPer.getIdTipoPerByNom(visPersona.cmbTipoPersona.getSelectedItem()+"");
            tPer.setId_tipoPer(idTipoPer);
            modPer.setTipoPersona(tPer);
            
            if (consPer.modificar(modPer)) {
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
       
        if (e.getSource() == visPersona.btnEliminar) {
            modPer.setId(Integer.parseInt(visPersona.txt_id.getText()));
            modPer.setEstado(0);
            int o= JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar el registro?", "Confirmar eliminar?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);            
            if (o ==0) 
            {           
                if (consPer.eliminar(modPer)) {
                    JOptionPane.showMessageDialog(null, "Registro Eliminado !");
                    limpiar();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error al Eliminar");
                    limpiar();
                }
                showTable();
            }
            
        }
        
         if (e.getSource() == visPersona.btnBuscar) 
         {
          
             if (Validaciones.isVoidJTxt(visPersona.txt_cedula)) {
                  modPer.setCedula(visPersona.txt_cedula.getText());
        
                if (consPer.buscar(modPer)){                   
                    visPersona.txt_id.setText(String.valueOf(modPer.getId()));  
                    visPersona.txt_cedula.setText(Validaciones.isNumVoid4(String.valueOf(modPer.getCedula())));
                    visPersona.txt_nombres.setText(Validaciones.isNumVoid4(String.valueOf(modPer.getNombre()).toUpperCase()));
                    visPersona.txt_apellidos.setText(Validaciones.isNumVoid4(String.valueOf(modPer.getApellido()).toUpperCase()));
                    visPersona.txt_edad.setText(Validaciones.isNumVoid4(String.valueOf(modPer.getEdad())));
                    visPersona.txt_nro_fono.setText(Validaciones.isNumVoid4(String.valueOf(modPer.getNro_fono())));                    
                    visPersona.dtc_fechaNac.setDate(Validaciones.setStringToDate(modPer.getFecha_nac()));

                    visPersona.txtCorreoElect.setText(Validaciones.isNumVoid4(String.valueOf(modPer.getMail())));  
                    visPersona.cmbxGenero.setSelectedItem(String.valueOf(modPer.getGenero()).toUpperCase());  
                    visPersona.cmbTipoPersona.setSelectedItem(modPer.getTipoPersona().getDescripcion_tipoPer());
                    
                    desabilitaHabilita(visPersona.btnGuardar,false);
                    desabilitaHabilita(visPersona.btnModificar,true);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "No se encotro registro");
                    limpiar();
                }
                showTable();
            }
        }         
         
         if (e.getSource() == visPersona.btnLimpiar) 
         {
            limpiar();
             desabilitaHabilita(visPersona.btnGuardar,true);
           desabilitaHabilita(visPersona.btnModificar,false);
         }
       
    }
    
     public void limpiar()
    {
        visPersona.txt_cedula.setText("");
        visPersona.txt_nombres.setText("");
        visPersona.txt_apellidos.setText("");
        visPersona.txt_edad.setText("");
        visPersona.dtc_fechaNac.setDate(null);
        visPersona.txtCorreoElect.setText("");
        visPersona.txt_nro_fono.setText("");
       // limpiarTabla();
    }
    
    public void limpiarTabla(){
        DefaultTableModel tb = (DefaultTableModel) visPersona.tbl_personas.getModel();
        int a = visPersona.tbl_personas.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
     public void getTableToTxts()
     {
         JTable tblD = visPersona.tbl_personas;
         visPersona.txt_id.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 0)));
         visPersona.txt_cedula.setText(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 1)));
         visPersona.txt_nombres.setText(Validaciones.isNumVoid4(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 2)).toUpperCase()));
         visPersona.txt_apellidos.setText(Validaciones.isNumVoid4(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 3)).toUpperCase()));
         visPersona.txt_edad.setText(Validaciones.isNumVoid4(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 7))));
         visPersona.cmbxGenero.setSelectedItem(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 4)).toUpperCase());
         visPersona.txtCorreoElect.setText(Validaciones.isNumVoid4(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 5))));
         visPersona.txt_nro_fono.setText(Validaciones.isNumVoid4(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 6))));         
         visPersona.dtc_fechaNac.setDate(Validaciones.setStringToDate(Validaciones.isNumVoid4(String.valueOf(tblD.getValueAt(tblD.getSelectedRow(), 8)))));                        
         visPersona.cmbTipoPersona.setSelectedItem(tblD.getValueAt(tblD.getSelectedRow(), 9));
     }
    
    
    public void showTable()
    {
        limpiarTabla();                            
           ArrayList<Persona> prodList = consPer.buscarTodos(modPer);
           DefaultTableModel model =  (DefaultTableModel)visPersona.tbl_personas.getModel();
           Object cols[] = new Object[11];

           for (int i = 0; i < prodList.size(); i++) {
               cols[0] = prodList.get(i).getId();
               cols[1] = Validaciones.isNumVoid4(prodList.get(i).getCedula());
               cols[2] = prodList.get(i).getNombre().toUpperCase();
               cols[3] = prodList.get(i).getApellido();
               cols[4] = prodList.get(i).getGenero().toUpperCase();
               cols[5] = Validaciones.isNumVoid4(prodList.get(i).getMail());
               cols[6] = Validaciones.isNumVoid4(prodList.get(i).getNro_fono());
               cols[7] = Validaciones.isNumVoid(prodList.get(i).getEdad()+"");
               cols[8] = Validaciones.isNumVoid4(prodList.get(i).getFecha_nac());
               cols[9] = Validaciones.isNumVoid4(prodList.get(i).getTipoPersona().getDescripcion_tipoPer());
               cols[10] = Validaciones.isNumVoid4(prodList.get(i).getTipoPersona().getId_tipoPer()+"");

               model.addRow(cols);                    
           }   
        //   model.remove;
    }
    
    public void showTableByCed(String ced)
    {
        limpiarTabla();                            
           ArrayList<Persona> prodList = consPer.buscarTodosPorCed(modPer,ced);
           DefaultTableModel model =  (DefaultTableModel)visPersona.tbl_personas.getModel();
           Object cols[] = new Object[9];

           for (int i = 0; i < prodList.size(); i++) {
               cols[0] = prodList.get(i).getId();
               cols[1] = prodList.get(i).getCedula();
               cols[2] = prodList.get(i).getNombre().toUpperCase();
               cols[3] = prodList.get(i).getApellido().toUpperCase();
               cols[4] = prodList.get(i).getGenero().toUpperCase();
               cols[5] = prodList.get(i).getMail();
               cols[6] = prodList.get(i).getNro_fono();
               cols[7] = prodList.get(i).getEdad();
               cols[8] = prodList.get(i).getFecha_nac();

               model.addRow(cols);                    
           }   
    
    }
    
    public void showTableByNom(String nom)
    {
        limpiarTabla();                            
           ArrayList<Persona> prodList = consPer.buscarTodosPorNom(modPer,nom);
           DefaultTableModel model =  (DefaultTableModel)visPersona.tbl_personas.getModel();
           Object cols[] = new Object[9];

           for (int i = 0; i < prodList.size(); i++) {
               cols[0] = prodList.get(i).getId();
               cols[1] = prodList.get(i).getCedula();
               cols[2] = prodList.get(i).getNombre().toUpperCase();
               cols[3] = prodList.get(i).getApellido().toUpperCase();
               cols[4] = prodList.get(i).getGenero().toUpperCase();
               cols[5] = prodList.get(i).getMail();
               cols[6] = prodList.get(i).getNro_fono();
               cols[7] = prodList.get(i).getEdad();
               cols[8] = prodList.get(i).getFecha_nac();

               model.addRow(cols);                    
           }   
    
    }
    
    
    
    public void setTableModel()
    {
      visPersona.tbl_personas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
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
    
     public void desabilitaHabilita(JButton btn,boolean estado)
     {
         btn.setEnabled(estado);
     }
    
    
    public void setListener(){
        KeyListener keyListenertxtBuscarCedula = new KeyListener() 
        {
          public void keyPressed(KeyEvent keyEvent) {
            printIt("Pressed", keyEvent);
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
              System.out.println ("-------------------------------------");
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
           // System.out.println(title + " : " + keyText + " / " + keyEvent.getKeyChar());
          }
        };
        visPersona.txtBuscarCedula.addKeyListener(keyListenertxtBuscarCedula);
        

        // LISTENER OF TXTDATE OF BIRTH        
         KeyListener keyListenerfechaNac = new KeyListener() {
          public void keyPressed(KeyEvent keyEvent) {
            printIt("Pressed", keyEvent);
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
            int m=e.getKeyChar();
              if (m == KeyEvent.VK_ENTER || m == KeyEvent.VK_TAB) {
                  System.out.println("asdf");
                  if(Validaciones.isVoidDateChooser(visPersona.dtc_fechaNac))
                  {
                    int ages = Calculos.getYearsFromDateOfBirth(Validaciones.setFormatFecha(visPersona.dtc_fechaNac.getDate()));
                    visPersona.txt_edad.setText(ages+"");
                    visPersona.cmbxGenero.requestFocusInWindow();
                  }
                 
              }  
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
     
          }
        };
        visPersona.dtc_fechaNac.getDateEditor().getUiComponent().addKeyListener(keyListenerfechaNac);
        
        
         /////TBLPERSONAS
        KeyListener keyListenerTblPersonas = new KeyListener() {
          public void keyPressed(KeyEvent e) {
           
          }

          public void keyReleased(KeyEvent keyEvent) {
            printIt("Released", keyEvent);
          }

          public void keyTyped(KeyEvent e) {
            
          }
          
          private void printIt(String title, KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            String keyText = KeyEvent.getKeyText(keyCode);
       
          }


        };
        
        MouseListener mouseListTblPersonas;
        mouseListTblPersonas = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2)
                {
                    int idPer = Integer.parseInt(visPersona.tbl_personas.getValueAt(visPersona.tbl_personas.getSelectedRow(), 0)+"");
                    String nombre = Validaciones.isNumVoid4(visPersona.tbl_personas.getValueAt(visPersona.tbl_personas.getSelectedRow(), 2)+"");
                    String apellido = Validaciones.isNumVoid4(visPersona.tbl_personas.getValueAt(visPersona.tbl_personas.getSelectedRow(), 3)+"");
                    
                    switch(locale){
                        case 1:
                            visFicha = (VisFicha)vis;
                            modPer.setId(idPer);
                            modPer.setNombre(nombre);
                            modPer.setApellido(apellido);
                            visFicha.txtCodPersona.setText(modPer.getId()+"");
                            visFicha.txtNomPersonaFicha.setText(modPer.getNombre() + " "+modPer.getApellido());
                            //CtrlFicha.setMedidasAnalisis();
                            break;
                        case 2:
                            visFicha = (VisFicha)vis;
                            modPer.setId(idPer);
                            modPer.setNombre(nombre);
                            modPer.setApellido(apellido);
                            visFicha.lblPersonaId.setText(idPer+"");
                            visFicha.txt_clienteFac.setText(modPer.getNombre() + " "+modPer.getApellido());
                            
                            
                            break;
                        case 3:  
                            visHisPerServ = (VisHistorialPersonaServicio)vis;
                            modPer.setId(idPer);
                            modPer.setNombre(nombre);
                            modPer.setApellido(apellido);
                            visHisPerServ.txtPersona.setText(nombre+" "+apellido);
                            visHisPerServ.lblIdPersona.setText(idPer+"");
                    //        visEnt.txtPersona.setText(idPer+"");
                           break; 
                        default:    
                            break;
                    }
                    visPersona.dispose();
                    
                }
                if (e.getClickCount()==1) {
                    getTableToTxts();
                    desabilitaHabilita(visPersona.btnGuardar,false);
                    desabilitaHabilita(visPersona.btnModificar,true);
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
       
        visPersona.tbl_personas.addMouseListener(mouseListTblPersonas);
        
        
        //LISTENER OF DATE OF BIRTH
        
         MouseListener mouseDateBirth;
        mouseDateBirth = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               
                if (e.getClickCount()==1) {
                    
                    if(Validaciones.isVoidDateChooser(visPersona.dtc_fechaNac))
                    {
                        int ages = Calculos.getYearsFromDateOfBirth(Validaciones.setFormatFecha(visPersona.dtc_fechaNac.getDate()));
                        visPersona.txt_edad.setText(ages+"");
                        visPersona.cmbxGenero.requestFocusInWindow();
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
       
        visPersona.dtc_fechaNac.getDateEditor().getUiComponent().addMouseListener(mouseDateBirth);
      
    }
    
    
}
