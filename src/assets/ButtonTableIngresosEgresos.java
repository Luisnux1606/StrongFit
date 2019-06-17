/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

/**
 *
 * @author Administrator
 */
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDateChooserCellEditor;
import consultas.ConsBuscarVentas;
import consultas.ConsFacturaCab;
import consultas.ConsFacturaCabCompras;
import consultas.ConsFacturaDet;
import consultas.ConsFacturaDetCompras;
import consultas.ConsHistorialPersonaServicio;
import consultas.ConsIngresosEgresos;
import consultas.ConsProductos;
import controladores.CtrlFacturaCab;
import controladores.CtrlFacturaDetalle;
import java.awt.*;
import java.awt.event.*;
import static java.awt.image.ImageObserver.WIDTH;
import java.lang.reflect.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;
import modelos.FacturaCab;
import modelos.FacturaCabCompras;
import modelos.FacturaDetalle;
import modelos.FacturaDetalleCompras;
import modelos.HistorialPersonaServicio;
import modelos.Iva;
import modelos.Membresias;
import modelos.Persona;
import modelos.Producto;
import vistas.VisBuscarVentas;
import vistas.VisFicha;
import vistas.VisIngresoEgreso;

public class ButtonTableIngresosEgresos extends JFrame
{
    
    ConsFacturaCab consFacCab;
    ConsFacturaCabCompras consFacCabComp;
    VisIngresoEgreso visIngEgr;
    FacturaCab modFacCab; 
    FacturaCabCompras modFacCabComp;
    
       
    
    public ButtonTableIngresosEgresos(VisIngresoEgreso visIngEgr)
    {
        consFacCab = new ConsFacturaCab();
        modFacCabComp = new FacturaCabCompras();
        this.visIngEgr = visIngEgr;
        modFacCab = new FacturaCab();
      
       

        ButtonColumn buttonColumn = new ButtonColumn(visIngEgr.tblIngresosEgresos, 17);
        ButtonColumn buttonColumn1 = new ButtonColumn(visIngEgr.tblIngresosEgresos, 18);
    }

    

    class ButtonColumn extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener
    {
        JTable table;
        JButton renderButton;
        JButton editButton;
        String text;

        public ButtonColumn(JTable table, int column)
        {
            super();
            this.table = table;
            renderButton = new JButton();

            editButton = new JButton();
            editButton.setFocusPainted( false );
            editButton.addActionListener( this );

            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(column).setCellRenderer( this );
            columnModel.getColumn(column).setCellEditor( this );
        }

        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            if (hasFocus)
            {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }
            else if (isSelected)
            {
                renderButton.setForeground(table.getSelectionForeground());
                 renderButton.setBackground(table.getSelectionBackground());
               
            }
            else
            {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }

            renderButton.setText( (value == null) ? "" : value.toString() );
            return renderButton;
        }

        public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)
        {
            text = (value == null) ? "" : value.toString();
            editButton.setText( text );
            return editButton;
        }

        public Object getCellEditorValue()
        {
            return text;
        }
        public void setDatosModificar()
        {
            if (getIngresoEgreso().equals("ingreso")) 
            {         
               System.out.println("entro a ingreso");
               ConsFacturaCab consFicha = new ConsFacturaCab();
               ConsFacturaDet consFacDet = new ConsFacturaDet();
               int idFac = Validaciones.isNumVoid(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 0)+"");
               String fechaAct = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 1)+"";
               
               String cliente =  visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 2)+"";
               String detalle =  visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 4)+"";
               double valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 9)+"");
               double valIngre = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 7)+"");
                
               double valAjuste = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 11)+"");
               double valPendiente = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 10)+"");
                
               if (!Validaciones.isNumVoid1(fechaAct) &&  !Validaciones.isNumVoid1(cliente) && !Validaciones.isNumVoid1(detalle)
                   && !Validaciones.isValCancHigherIngreso(valCanc,valIngre) && !Validaciones.isValAjusteHigherPendiente(valAjuste, valPendiente)) 
               {
                    String tanggal = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 1)+"";
                    fechaAct = tanggal;
                    setFacturaCabecera(fechaAct);
                    modFacCab.setId_facCab(idFac);
                    if (consFicha.modificar(modFacCab)) 
                    {
                        ArrayList<FacturaDetalle> facDets =setDetallesModificar(visIngEgr);
                        if(consFacDet.modificar(facDets))
                        {                      
                            JOptionPane.showMessageDialog(null, "Registro Modificado!");
                            showTableIngresosEgresos();
                            if(consFacDet.actualizarSalidas(facDets)){
                                consFacDet.actualizarStock(facDets);
                                System.out.println("actualizado salidas");
                            }
                            else
                                System.out.println("NO actualizado salidas");      
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error al Modificar");  
                    }
               } 
                
            }
            
            if (getIngresoEgreso().equals("egreso")) {
               System.out.println("entro a egreso");
               ConsFacturaCabCompras consFichaComp = new ConsFacturaCabCompras();
               ConsFacturaDetCompras consFacDetComp = new ConsFacturaDetCompras();
               int idFac = Validaciones.isNumVoid(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 0)+"");
               String fechaAct = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 1)+"";
               
               String cliente =  visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 2)+"";
               String detalle =  visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 4)+"";
                double valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 9)+"");
                    double valIngre = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 7)+"");
               if (!Validaciones.isNumVoid1(fechaAct) &&  !Validaciones.isNumVoid1(cliente) && !Validaciones.isNumVoid1(detalle)&& !Validaciones.isValCancHigherIngreso(valCanc,valIngre)) 
               {
                    String tanggal = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 1)+"";
                    fechaAct = tanggal;
                    setFacturaCabeceraCompra(fechaAct);
                    modFacCabComp.setId_facCabComp(idFac);
                    if (consFichaComp.modificar(modFacCabComp)) 
                    {
                        ArrayList<FacturaDetalleCompras> facDets =setDetallesModificarCompras(visIngEgr);
                        if(consFacDetComp.modificar(facDets))
                        {                      
                            JOptionPane.showMessageDialog(null, "Registro Modificado!");
                            if(consFacDetComp.actualizarEntradas(facDets)){
                                consFacDetComp.actualizarStock(facDets);
                                System.out.println("actualizado entradas");
                            }
                            else
                                System.out.println("NO actualizado entradas");      
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error al Modificar");  
                    }
               } 
            }
        }
        
        public void setDatosGuardar()
        {
                String prodId = table.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 15)+"";            
                ConsProductos consProd = new ConsProductos();
                String desc = table.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 4)+""; 
                if (!consProd.existeProducto(desc))
                   prodId ="1"; // 1 for default product 999
            

                if (consProd.getTipoProdServ(prodId).equals("1"))
                {                    
                    String fIni = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 5)+"";
                    String fFin = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 6)+"";   
                    String codPer = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 14)+"";
                    double valCanc = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 9)+"");
                    double valIngre = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 7)+"");
                    double valAjuste = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 11)+"");
                    double valPendiente = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 10)+"");
                    if (!Validaciones.isCadnull(fIni)&&!Validaciones.isCadnull(fFin) && !Validaciones.isCadnull(codPer) 
                        && !Validaciones.isValCancHigherIngreso(valCanc,valIngre) && !Validaciones.isValAjusteHigherPendiente(valAjuste, valPendiente)) {   
                       
                        setDatos();                                           
                    }
                    else
                        Validaciones.getMensaje("Revise si hay fechas inicio y fin y si eligio la persona");
                }
                if (consProd.getTipoProdServ(prodId).equals("2")){
                    System.out.println("entro a producto");
                     setDatos();                        
                }
        }
        
    public void showTableIngresosEgresos()
    {
        ConsIngresosEgresos consIngEgr = new ConsIngresosEgresos();
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresos);
            ResultSet listFicha = consIngEgr.buscarIngresosEgresos();
            
            DefaultTableModel model =  (DefaultTableModel)visIngEgr.tblIngresosEgresos.getModel();
            Object cols[] = new Object[19];
           
            while (listFicha.next()) {
                try { 
                                      
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("Fecha_Faccab");
                    cols[2] = listFicha.getString("nombres").toUpperCase();
                    cols[3] = Validaciones.isNumVoid4(listFicha.getString("Num_Faccab"));
                    cols[4] = listFicha.getString("Descripcion_Facdet");
                    cols[5] = listFicha.getString("FechaInicio");
                    cols[6] = listFicha.getString("FechaFin");
                    cols[7] = listFicha.getString("Total_Faccab");
                    cols[8] = listFicha.getDouble("egreso");
                    cols[9] = listFicha.getDouble("Valcancelo_Faccab");
                    cols[10] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[11] = listFicha.getDouble("Valajuste_Faccab");
                    cols[12] = listFicha.getDouble("estadoEnt");
                    cols[13] = listFicha.getDouble("VALPENDIENTE_FACCAB");
                    cols[14] = listFicha.getInt("id_per");
                    cols[15] = listFicha.getInt("id_prod");
                    cols[16] = listFicha.getInt("codHist");
                    
                    cols[17] = "Guardar";
                    cols[18] = "Anular";
                    cols[13] = Calculos.getDiferencia((double)cols[10], (double)cols[11]);              
                    model.addRow(cols);
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consIngEgr.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTableIngresosEgresos(visIngEgr);
        visIngEgr.tblIngresosEgresos.updateUI();
    }  
        public void actionPerformed(ActionEvent e)
        {
            fireEditingStopped();
            String command = ((JButton) e.getSource()).getActionCommand();
            if (command.equals( "Guardar")) 
            {
                String codFac = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 0)+"";
                if(Validaciones.isNumVoid(codFac)!=0)
                {                                            
                    setDatosModificar();
                }
                if(Validaciones.isNumVoid(codFac)==0)
                {                                            
                  setDatosGuardar();
                }   
                
                
            }
            if (command.equals( "Anular")) {
               System.out.println("aqui se eliminara!!!" +visIngEgr.tblIngresosEgresos.getSelectedRow() );
               setAnulado();
            }                        
        }
        
        
        public String getIngresoEgreso()
        {
           String ing= visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 7)+"";
           String egr= visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 8)+"";
           System.out.println("ing "+ing + "egr "+egr);
           String tipo = "";
           if (ing.trim().length()>0){
               if (Validaciones.isNumVoid10(ing)!=0) {
                   tipo = "ingreso";
               }
           }                
                         
            if (egr.trim().length()>0){
               if (Validaciones.isNumVoid10(egr)!=0) {
                 tipo = "egreso";
               }
            }     
                     
        return tipo;
        }
        
        public void setFacturaCabecera(String fechaAct)
        {
            
            Persona persona = new Persona();
            String numFac = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 3)+"";
            Membresias memb = new Membresias();
            Iva iva = new Iva();
            
            modFacCab.setFecha_facCab(fechaAct);                
            modFacCab.setNum_facCab(numFac);
            
            String codPer = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 14)+"";
            persona.setId(Validaciones.isNumVoid(codPer));
            modFacCab.setPersona(persona);
            
            String valPagar = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 7)+"".trim();
            modFacCab.setValPagar_facCab(Validaciones.isNumVoid10(valPagar));

            memb.setId(1);
            modFacCab.setMembresia(memb); // 1  = 0

            modFacCab.setSubTotal_facCab(Validaciones.isNumVoid3(valPagar));

            iva.setId_ivas(1);
            modFacCab.setIvas(iva); //1 = 0

            String valCancelo = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 9)+"".trim();
            modFacCab.setTotal_facCab(Validaciones.isNumVoid3(valPagar));
            modFacCab.setValCancelo_facCab(Validaciones.isNumVoid3(valCancelo));
            
            String valPend = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 10)+"".trim();
            modFacCab.setValPendiente_facCab(Validaciones.isNumVoid3(valPend));       
            
            String valAjuste = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 11)+"".trim();
            modFacCab.setValAjuste_facCab(Validaciones.isNumVoid10(valAjuste));
            
            modFacCab.setEstado(1);
        }
        
        public void setFacturaCabeceraCompra(String fechaAct)
        {
            
            Persona persona = new Persona();
            String numFac = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 3)+"";
            Membresias memb = new Membresias();
            Iva iva = new Iva();
            
            modFacCabComp.setFecha_facCabComp(fechaAct);                
            modFacCab.setNum_facCab(numFac);
            
            String codPer = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 14)+"";
            persona.setId(Validaciones.isNumVoid(codPer));
            modFacCabComp.setPersonaComp(persona);
            
            String valPagar = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 7)+"".trim();
            modFacCabComp.setValPagar_facCabComp(Validaciones.isNumVoid10(valPagar));

            memb.setId(1);
            modFacCabComp.setMembresiaComp(memb); // 1  = 0

            modFacCabComp.setSubTotal_facCabComp(Validaciones.isNumVoid3(valPagar));

            iva.setId_ivas(1);
            modFacCabComp.setIvasComp(iva); //1 = 0

            String valCancelo = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 9)+"".trim();
            modFacCabComp.setTotal_facCabComp(Validaciones.isNumVoid3(valPagar));
            modFacCabComp.setValCancelo_facCabComp(Validaciones.isNumVoid3(valCancelo));
            
            String valPend = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 10)+"".trim();
            modFacCabComp.setValPendiente_facCabComp(Validaciones.isNumVoid3(valPend));       
            
            String valAjuste = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 11)+"".trim();
            modFacCabComp.setValAjuste_facCabComp(Validaciones.isNumVoid10(valAjuste));
            
            modFacCabComp.setEstadoComp(1);
        }
        
        
        
        public void setDatos()
        {            
            if (getIngresoEgreso().equals("ingreso")) 
            {         
               System.out.println("entro a ingreso");
               ConsFacturaCab consFicha = new ConsFacturaCab();
               ConsFacturaDet consFacDet = new ConsFacturaDet();
               String fechaAct = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 1)+"";
               
               String cliente =  visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 2)+"";
               String detalle =  visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 4)+"";
               
               if (!Validaciones.isNumVoid1(fechaAct) &&  !Validaciones.isNumVoid1(cliente) && !Validaciones.isNumVoid1(detalle)) 
               {
                    Date tanggal = (Date) visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 1);
                    fechaAct = Validaciones.setFormatFecha(tanggal);
                    setFacturaCabecera(fechaAct);
                    if (consFicha.registrar(modFacCab)) 
                    {
                        ArrayList<FacturaDetalle> facDets =setDetalles(visIngEgr);
                        if(consFacDet.registrar(facDets))
                        {                      
                            JOptionPane.showMessageDialog(null, "Registro Guardado!");
                            showTableIngresosEgresos();
                            if(consFacDet.actualizarSalidas(facDets)){
                                consFacDet.actualizarStock(facDets);
                                System.out.println("actualizado salidas");
                            }
                            else
                                System.out.println("NO actualizado salidas");      
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error al Guardar");  
                    }
               } 
                
            }
            System.out.println("ing egre = "+getIngresoEgreso());
            if (getIngresoEgreso().equals("egreso")) {
                System.out.println("entro a egreso");
               ConsFacturaCabCompras consFichaComp = new ConsFacturaCabCompras();
               ConsFacturaDetCompras consFacDetComp = new ConsFacturaDetCompras();
               
               String fechaAct = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 1)+"";
               System.out.println(fechaAct);
               String cliente =  visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 2)+"";
               String detalle =  visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 4)+"";
               
               if (!Validaciones.isNumVoid1(fechaAct) &&  !Validaciones.isNumVoid1(cliente) && !Validaciones.isNumVoid1(detalle)) 
               {
                    Date tanggal = (Date) visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 1);
                    fechaAct = Validaciones.setFormatFecha(tanggal);
                    setFacturaCabeceraCompra(fechaAct);
                    if (consFichaComp.registrar(modFacCabComp)) 
                    {
                        ArrayList<FacturaDetalleCompras> facDetsComps =setDetallesCompras(visIngEgr);
                        if(consFacDetComp.registrar(facDetsComps))
                        {                      
                            JOptionPane.showMessageDialog(null, "Registro Guardado!");
                            if(consFacDetComp.actualizarEntradas(facDetsComps)){
                                consFacDetComp.actualizarStock(facDetsComps);
                                System.out.println("actualizado entradas");
                            }
                            else
                                System.out.println("NO actualizado entradas");      
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error al Guardar");  
                    }
               } 
            }
           
        }
 
        public void setAnulado()
        {
            int idFacCab = Validaciones.isNumVoid(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 0)+"");
            if (getIngresoEgreso().equals("ingreso")) 
            {
                FacturaCab modFacCab=new FacturaCab();

                double ajuste = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 8)+"");
                // idFacCab = Validaciones.isNumVoid(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 0)+"");
                modFacCab.setEstado(3); //anulado 
                modFacCab.setId_facCab(idFacCab);       
               // consFacCab.modificarAjuste(modFacCab);

                if (consFacCab.modificarAnulado(modFacCab)) {
                    JOptionPane.showMessageDialog(null, "Registro Anulado!");
                   // showTableIngresosEgresos();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error al Anular");               
                }
            }
            else
            if (getIngresoEgreso().equals("egreso")) 
            {
                FacturaCabCompras modFacCab=new FacturaCabCompras();

                double ajuste = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 8)+"");
                // idFacCab = Validaciones.isNumVoid(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 0)+"");
                modFacCab.setEstadoComp(3); //anulado 
                modFacCab.setId_facCabComp(idFacCab);       
               // consFacCab.modificarAjuste(modFacCab);

                if (consFacCabComp.modificarAnulado(modFacCab)) {
                    JOptionPane.showMessageDialog(null, "Registro Anulado!");
                   // showTableIngresosEgresos();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error al Anular");               
                }
            }
            
            
            if (idFacCab==0) {
                deleteRows(visIngEgr.tblIngresosEgresos);
            }
            
             showTableIngresosEgresos();
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
        
         public void limpiarTabla(JTable table)
         {
            DefaultTableModel tb = (DefaultTableModel) table.getModel();
            int a = table.getRowCount()-1;
            for (int i = a; i >= 0; i--) {           
                tb.removeRow(tb.getRowCount()-1);
            } 
         }
        public void showTableFacturasCabeceras()
        {
        ConsBuscarVentas consBuscarVentas = new ConsBuscarVentas();
         DefaultTableModel model=null; 
        try {
            limpiarTabla(visIngEgr.tblIngresosEgresos);
            ResultSet listFicha = consBuscarVentas.buscarFacturas();
            
           model=  (DefaultTableModel)visIngEgr.tblIngresosEgresos.getModel();
            Object cols[] = new Object[11];
           
            while (listFicha.next()) {
                try {
                  
                    
                    cols[0] = listFicha.getInt("Id_Faccab");
                    cols[1] = listFicha.getString("nombres");
                    cols[2] = listFicha.getString("Fecha_Faccab").toUpperCase();
                    cols[3] = listFicha.getString("Num_Faccab");
                    cols[4] = listFicha.getString("Concepto_Faccab");
                    cols[5] = listFicha.getString("Total_Faccab");
                    cols[6] = listFicha.getString("Valcancelo_Faccab");
                    cols[7] = listFicha.getDouble("Valpendiente_Faccab");
                    cols[8] = listFicha.getDouble("valajuste_faccab");
                    cols[9] = "Guardar";
                    cols[10] = "Anular";
                    
                
                    model.addRow(cols);
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTableIngresosEgresos(visIngEgr);
        visIngEgr.tblIngresosEgresos.setModel(model);
       // visIngEgr.tbl_BuscarVentas.updateUI();
        
        }  
    }
    
    public ArrayList<FacturaDetalleCompras> setDetallesCompras(VisIngresoEgreso visIngEgr)
    {        
        JTable table = visIngEgr.tblIngresosEgresos;
        int selectedRow = table.getSelectedRow();
        String num,desc,valU,valT,prodId;
        int facCabId = 0;
        ConsFacturaDetCompras consFacDetComp = new ConsFacturaDetCompras();
        ConsProductos consProd = new ConsProductos();
        HistorialPersonaServicio hisPerServ = new HistorialPersonaServicio();
        ConsHistorialPersonaServicio consHisPerServ = new ConsHistorialPersonaServicio();
        Persona persona = new Persona();
        
        Producto prod = new Producto();
        FacturaCabCompras facCabComp = new FacturaCabCompras();
        FacturaDetalleCompras detalle ;
        
        ArrayList<FacturaDetalleCompras> detalles=new ArrayList<>();
        ArrayList<Producto> p = new ArrayList<>();      
        ArrayList<FacturaDetalleCompras> facDetComp = new ArrayList<>();
        
        
        if (consFacDetComp.getLastInvoice(facCabComp))               
            facCabId = facCabComp.getId_facCabComp();
        
        facCabComp.setId_facCabComp(facCabId);        

        for (int i = selectedRow; i <= selectedRow; i++) 
        {
            
            prodId =table.getValueAt(i, 15)+""; //si existe or not
            num = "1";//table.getValueAt(i, 1)+"";
            desc = table.getValueAt(i, 4)+"";     
            valU = table.getValueAt(i, 8)+"";
            valT = table.getValueAt(i, 8)+"";
            
            if (!consProd.existeProducto(desc))
                prodId ="1";
            
         
            if (Validaciones.isNumVoid10(num)!=0 && Validaciones.isNumVoid10(valU)!=0 && Validaciones.isNumVoid10(valT)!=0 ) {
                
                detalle = new FacturaDetalleCompras();
                prod = new Producto();
               
                detalle.setCantidad_facDetComp(Validaciones.isNumVoid(num));
                detalle.setDescripcion_facDetComp(desc);
                detalle.setValUnitario_facDetComp(Validaciones.isNumVoid10(valU));
                detalle.setvTotal_facDetComp(Validaciones.isNumVoid10(valT));
               
                prod.setId_prod(Validaciones.isNumVoid(prodId));
                p.add(prod);
                detalle.setProducto_id_prodComp(p.get(p.size()-1));
                
                hisPerServ.setProducto_id_HisPerSer(prod);
                
                String codPer = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 14)+"";
                persona.setId(Validaciones.isNumVoid(codPer));

                hisPerServ.setPersona_id_HisPerSer(persona);
                hisPerServ.setEstado_HisPerSer(1);
                prod.setEstado_prod(1);

                String fIni = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 5)+"";
                String fFin = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 6)+"";
                
                if (!Validaciones.isNumVoid1(fIni)&&!Validaciones.isNumVoid1(fFin)) {
                    Date fechaIniD = (Date) visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 5);
                    Date fechaFinD = (Date) visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 6);
                    String fechaIni = Validaciones.setFormatFecha(fechaIniD);
                    String fechaFin = Validaciones.setFormatFecha(fechaFinD);
                    hisPerServ.setFechaIni_HisPerSer(fechaIni);
                    hisPerServ.setFechaFin_HisPerSer(fechaFin);
                    if (consHisPerServ.registrar(hisPerServ)) {
                        JOptionPane.showMessageDialog(null, "Entrenamiento Guardado!");

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Error al Guardar entrenamiento");
                        }
                }
                
                detalle.setFactura_id_facComp(facCabComp);
                detalle.setEstado_facDetComp(1);
                
                detalles.add(detalle);
                facDetComp.add(detalles.get(detalles.size()-1));

            }
        }                               
            return facDetComp;
       
    }
    
    public ArrayList<FacturaDetalle> setDetalles(VisIngresoEgreso visIngEgr)
    {        
        JTable table = visIngEgr.tblIngresosEgresos;
        int selectedRow = table.getSelectedRow();
        String num,desc,valU,valT,prodId;
        int facCabId = 0;
        ConsFacturaDet consFacDet = new ConsFacturaDet();
        ConsProductos consProd = new ConsProductos();
        HistorialPersonaServicio hisPerServ = new HistorialPersonaServicio();
        ConsHistorialPersonaServicio consHisPerServ = new ConsHistorialPersonaServicio();
        Persona persona = new Persona();
        
        Producto prod = new Producto();
        FacturaCab facCab = new FacturaCab();
        FacturaDetalle detalle ;
        
        ArrayList<FacturaDetalle> detalles=new ArrayList<>();
        ArrayList<Producto> p = new ArrayList<>();      
        ArrayList<FacturaDetalle> facDet = new ArrayList<>();
        
        
        if (consFacDet.getLastInvoice(facCab))               
            facCabId = facCab.getId_facCab();
        
        facCab.setId_facCab(facCabId);        

        for (int i = selectedRow; i <= selectedRow; i++) 
        {
            
            prodId =table.getValueAt(i, 15)+""; //si existe or not
            num = "1";//table.getValueAt(i, 1)+"";
            desc = table.getValueAt(i, 4)+"";     
            valU = table.getValueAt(i, 7)+"";
            valT = table.getValueAt(i, 7)+"";
            
            if (!consProd.existeProducto(desc))
                prodId ="1";
            
         
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
                
                hisPerServ.setProducto_id_HisPerSer(prod);
                
                String codPer = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 14)+"";
                persona.setId(Validaciones.isNumVoid(codPer));

                hisPerServ.setPersona_id_HisPerSer(persona);
                hisPerServ.setEstado_HisPerSer(1);
                prod.setEstado_prod(1);

                String fIni = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 5)+"";
                String fFin = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 6)+"";
                
                if (!Validaciones.isNumVoid1(fIni)&&!Validaciones.isNumVoid1(fFin)) {
                    Date fechaIniD = (Date) visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 5);
                    Date fechaFinD = (Date) visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 6);
                    String fechaIni = Validaciones.setFormatFecha(fechaIniD);
                    String fechaFin = Validaciones.setFormatFecha(fechaFinD);
                    hisPerServ.setFechaIni_HisPerSer(fechaIni);
                    hisPerServ.setFechaFin_HisPerSer(fechaFin);
                    if (consHisPerServ.registrar(hisPerServ)) {
                        JOptionPane.showMessageDialog(null, "Entrenamiento Guardado!");

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Error al Guardar entrenamiento");
                        }
                }
                
                detalle.setFactura_id_fac(facCab);
                detalle.setEstado_facDet(1);
                
                detalles.add(detalle);
                facDet.add(detalles.get(detalles.size()-1));

            }
             else
                {
                    JOptionPane.showMessageDialog(null, "Revisar valores de fila seleccionada");
                }
        }                               
            return facDet;
       
    }
    
    public ArrayList<FacturaDetalle> setDetallesModificar(VisIngresoEgreso visIngEgr)
    {        
        JTable table = visIngEgr.tblIngresosEgresos;
        int selectedRow = table.getSelectedRow();
        String num,desc,valU,valT,prodId;
        int facCabId = 0;
        ConsFacturaDet consFacDet = new ConsFacturaDet();
        ConsProductos consProd = new ConsProductos();
        HistorialPersonaServicio hisPerServ = new HistorialPersonaServicio();
        ConsHistorialPersonaServicio consHisPerServ = new ConsHistorialPersonaServicio();
        Persona persona = new Persona();
        
        Producto prod = new Producto();
        FacturaCab facCab = new FacturaCab();
        FacturaDetalle detalle ;
        
        ArrayList<FacturaDetalle> detalles=new ArrayList<>();
        ArrayList<Producto> p = new ArrayList<>();      
        ArrayList<FacturaDetalle> facDet = new ArrayList<>();
        
        
        if (consFacDet.getLastInvoice(facCab))               
            facCabId = facCab.getId_facCab();
        
        facCab.setId_facCab(facCabId);        

        for (int i = selectedRow; i <= selectedRow; i++) 
        {
            
            prodId =table.getValueAt(i, 15)+""; //si existe or not
            num = "1";//table.getValueAt(i, 1)+"";
            desc = table.getValueAt(i, 4)+"";     
            valU = table.getValueAt(i, 7)+"";
            valT = table.getValueAt(i, 7)+"";
            
            if (!consProd.existeProducto(desc))
                prodId ="1";
            
         
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
                
                hisPerServ.setProducto_id_HisPerSer(prod);
                
                String codPer = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 14)+"";
                persona.setId(Validaciones.isNumVoid(codPer));

                hisPerServ.setPersona_id_HisPerSer(persona);
                hisPerServ.setEstado_HisPerSer(1);
                prod.setEstado_prod(1);

                String fIni = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 5)+"";
                String fFin = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 6)+"";
                
                if (!Validaciones.isNumVoid1(fIni)&&!Validaciones.isNumVoid1(fFin)) {
                    String fechaIniD = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 5)+"";
                    String fechaFinD = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 6)+"";
                    String fechaIni = (fechaIniD);
                    String fechaFin = (fechaFinD);
                    int idHisPerServ =Validaciones.isNumVoid(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 16)+"");
                    hisPerServ.setFechaIni_HisPerSer(fechaIni);
                    hisPerServ.setFechaFin_HisPerSer(fechaFin);
                    hisPerServ.setId_HisPerSer(idHisPerServ);
                    if (consHisPerServ.modificar(hisPerServ)) {
                        JOptionPane.showMessageDialog(null, "Entrenamiento Modificado!");

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Error al Modificar entrenamiento");
                        }
                }
                
                detalle.setFactura_id_fac(facCab);
                detalle.setEstado_facDet(1);
                
                detalles.add(detalle);
                facDet.add(detalles.get(detalles.size()-1));

            }
        }                               
            return facDet;
       
    }
    public ArrayList<FacturaDetalleCompras> setDetallesModificarCompras(VisIngresoEgreso visIngEgr)
    {        
        JTable table = visIngEgr.tblIngresosEgresos;
        int selectedRow = table.getSelectedRow();
        String num,desc,valU,valT,prodId;
        int facCabId = 0;
        ConsFacturaDetCompras consFacDetComp = new ConsFacturaDetCompras();
        ConsProductos consProd = new ConsProductos();
        HistorialPersonaServicio hisPerServ = new HistorialPersonaServicio();
        ConsHistorialPersonaServicio consHisPerServ = new ConsHistorialPersonaServicio();
        Persona persona = new Persona();
        
        Producto prod = new Producto();
        FacturaCabCompras facCabComp = new FacturaCabCompras();
        FacturaDetalleCompras detalle ;
        
        ArrayList<FacturaDetalleCompras> detalles=new ArrayList<>();
        ArrayList<Producto> p = new ArrayList<>();      
        ArrayList<FacturaDetalleCompras> facDet = new ArrayList<>();
        
        
        if (consFacDetComp.getLastInvoice(facCabComp))               
            facCabId = facCabComp.getId_facCabComp();
        
        facCabComp.setId_facCabComp(facCabId);        

        for (int i = selectedRow; i <= selectedRow; i++) 
        {
            
            prodId =table.getValueAt(i, 15)+""; //si existe or not
            num = "1";//table.getValueAt(i, 1)+"";
            desc = table.getValueAt(i, 4)+"";     
            valU = table.getValueAt(i, 7)+"";
            valT = table.getValueAt(i, 7)+"";
            
            if (!consProd.existeProducto(desc))
                prodId ="1";
            
         
            if (Validaciones.isNumVoid10(num)!=0 && Validaciones.isNumVoid10(valU)!=0 && Validaciones.isNumVoid10(valT)!=0 ) {
                
                detalle = new FacturaDetalleCompras();
                prod = new Producto();
               
                detalle.setCantidad_facDetComp(Validaciones.isNumVoid(num));
                detalle.setDescripcion_facDetComp(desc);
                detalle.setValUnitario_facDetComp(Validaciones.isNumVoid10(valU));
                detalle.setvTotal_facDetComp(Validaciones.isNumVoid10(valT));
               
                prod.setId_prod(Validaciones.isNumVoid(prodId));
                p.add(prod);
                detalle.setProducto_id_prodComp(p.get(p.size()-1));
                
                hisPerServ.setProducto_id_HisPerSer(prod);
                
                String codPer = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 14)+"";
                persona.setId(Validaciones.isNumVoid(codPer));

                hisPerServ.setPersona_id_HisPerSer(persona);
                hisPerServ.setEstado_HisPerSer(1);
                prod.setEstado_prod(1);

                
                
                detalle.setFactura_id_facComp(facCabComp);
                detalle.setEstado_facDetComp(1);
                
                detalles.add(detalle);
                facDet.add(detalles.get(detalles.size()-1));

            }
        }                               
            return facDet;
       
    }
}
