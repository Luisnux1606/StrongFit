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
import consultas.ConsFacturaDet;
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
import modelos.FacturaDetalle;
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
    VisIngresoEgreso visIngEgr;
    FacturaCab modFacCab; 
    
       
    
    public ButtonTableIngresosEgresos(VisIngresoEgreso visIngEgr)
    {
        consFacCab = new ConsFacturaCab();
        this.visIngEgr = visIngEgr;
        modFacCab = new FacturaCab();
      
       

        ButtonColumn buttonColumn = new ButtonColumn(visIngEgr.tblIngresosEgresos, 16);
        ButtonColumn buttonColumn1 = new ButtonColumn(visIngEgr.tblIngresosEgresos, 17);
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
                String prodId = table.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 15)+"";            
                ConsProductos consProd = new ConsProductos();
                String desc = table.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 4)+""; 
                if (!consProd.existeProducto(desc))
                   prodId ="1"; // 1 for default product 999
            

                if (consProd.getTipoProdServ(prodId).equals("SERVICIO GIMNASIO"))
                {                    
                    String fIni = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 5)+"";
                    String fFin = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 6)+"";   
                    String codPer = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 14)+"";

                    if (!Validaciones.isCadnull(fIni)&&!Validaciones.isCadnull(fFin) && !Validaciones.isCadnull(codPer)) {                
                        setDatos();                                           
                    }
                    else
                        Validaciones.getMensaje("Revise si hay fechas inicio y fin y si eligio la persona");
                }
                if (consProd.getTipoProdServ(prodId).equals("PRODUCTO")){
                     setDatos();                        
                }
        }
        
        public void setDatosGuardar()
        {
                String prodId = table.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 15)+"";            
                ConsProductos consProd = new ConsProductos();
                String desc = table.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 4)+""; 
                if (!consProd.existeProducto(desc))
                   prodId ="1"; // 1 for default product 999
            

                if (consProd.getTipoProdServ(prodId).equals("SERVICIO GIMNASIO"))
                {                    
                    String fIni = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 5)+"";
                    String fFin = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 6)+"";   
                    String codPer = visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 14)+"";

                    if (!Validaciones.isCadnull(fIni)&&!Validaciones.isCadnull(fFin) && !Validaciones.isCadnull(codPer)) {                
                        setDatos();                                           
                    }
                    else
                        Validaciones.getMensaje("Revise si hay fechas inicio y fin y si eligio la persona");
                }
                if (consProd.getTipoProdServ(prodId).equals("PRODUCTO")){
                     setDatos();                        
                }
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
                    
                }
                if(Validaciones.isNumVoid(codFac)==0)
                {                                            
                  setDatosGuardar();
                }
              
                    
               
                
            }
            if (command.equals( "Eliminar")) {
               System.out.println("aqui se eliminara!!!" +visIngEgr.tblIngresosEgresos.getSelectedRow() );
               setAnulado();
            }                        
        }
        
        
        public String getIngresoEgreso()
        {
           String ing= visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 7)+"";
           String egr= visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 8)+"";
           String tipo = "";
           if (ing.trim().length()>0){
               if (Validaciones.isNumVoid10(ing)!=0) {
                   tipo = "ingreso";
               }
           }                
           else  {              
            if (egr.trim().length()>0){
               if (Validaciones.isNumVoid10(ing)!=0) {
                 tipo = "egreso";
               }
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
            modFacCab.setEstado(1);      
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
            
            if (getIngresoEgreso().equals("egreso")) {
                
            }
           
        }
        
        
        
        
        public void setAnulado()
        {
            FacturaCab modFacCab=new FacturaCab();
                      
            double ajuste = Validaciones.isNumVoid10(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 8)+"");
            int idFacCab = Validaciones.isNumVoid(visIngEgr.tblIngresosEgresos.getValueAt(visIngEgr.tblIngresosEgresos.getSelectedRow(), 0)+"");
            modFacCab.setEstado(3); //anulado 
            modFacCab.setId_facCab(idFacCab);       
           // consFacCab.modificarAjuste(modFacCab);
            
            if (consFacCab.modificarAnulado(modFacCab)) {
                JOptionPane.showMessageDialog(null, "Registro Anulado!");
                
                 showTableFacturasCabeceras();
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al Anular");
               
            }
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
        }                               
            return facDet;
       
    }
    
}
