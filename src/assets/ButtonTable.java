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
import consultas.ConsBuscarVentas;
import consultas.ConsFacturaCab;
import controladores.CtrlFacturaCab;
import java.awt.*;
import java.awt.event.*;
import static java.awt.image.ImageObserver.WIDTH;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;
import modelos.FacturaCab;
import vistas.VisBuscarVentas;

public class ButtonTable extends JFrame
{
    
    ConsFacturaCab consFacCab;
    VisBuscarVentas visVentas;
    
    public ButtonTable(VisBuscarVentas visVentas)
    {
        consFacCab = new ConsFacturaCab();
        this.visVentas = visVentas;
        

        ButtonColumn buttonColumn = new ButtonColumn(visVentas.tblFacturasCabeceras, 9);
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

        public void actionPerformed(ActionEvent e)
        {
            fireEditingStopped();
            System.out.println( e.getActionCommand() + " : " + table.getSelectedRow());
            
            FacturaCab modFacCab=new FacturaCab();
            /*
            
            //fecha_facCab, num_facCab,valPagar_facCab,subTotal_facCab,total_facCab,valPendiente_facCab,valCancelo_facCab, Persona_id_per, Membresia_id_memb, Ivas_id_ivas,estado_facCab
            String fechaFac = Validaciones.isNumVoid4(visVentas.tblFacturasCabeceras.getValueAt(visVentas.tblFacturasCabeceras.getSelectedRow(), 2)+"");
            String numFac = "";
            double valConDsto;
            double totalConIva;
            double valPend;
            
            
            modFacCab.setFecha_facCab(Validaciones.setFormatFecha(visFicha.dtcFecha.getDate()));                
            modFacCab.setNum_facCab("sera numero");
            modFacCab.setSubTotal_facCab(Validaciones.isNumVoid3(visFicha.txtValConDsctoFicha.getText()));
            modFacCab.setTotal_facCab(Validaciones.isNumVoid3(visFicha.txtTotalConIva.getText()));
            modFacCab.setValPendiente_facCab(Validaciones.isNumVoid3(visFicha.txtValPendienteFicha.getText()));       
            modFacCab.setEstado(1);     
            */
            
            double ajuste = Validaciones.isNumVoid10(visVentas.tblFacturasCabeceras.getValueAt(visVentas.tblFacturasCabeceras.getSelectedRow(), 8)+"");
            int idFacCab = Validaciones.isNumVoid(visVentas.tblFacturasCabeceras.getValueAt(visVentas.tblFacturasCabeceras.getSelectedRow(), 0)+"");
            modFacCab.setValAjuste_facCab(ajuste); 
            modFacCab.setId_facCab(idFacCab);       
           // consFacCab.modificarAjuste(modFacCab);
            
            if (consFacCab.modificarAjuste(modFacCab)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado!");
                try{
               showTableFacturasCabeceras();
                }catch(Exception e1){System.out.println("...");}
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
               
            }
            
            
                        
        }
         public void limpiarTabla(JTable table){
        DefaultTableModel tb = (DefaultTableModel) table.getModel();
        int a = table.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
        public void showTableFacturasCabeceras()
        {
        ConsBuscarVentas consBuscarVentas = new ConsBuscarVentas();
        try {
            limpiarTabla(visVentas.tblFacturasCabeceras);
            ResultSet listFicha = consBuscarVentas.buscarFacturas();
            
            DefaultTableModel model =  (DefaultTableModel)visVentas.tblFacturasCabeceras.getModel();
            Object cols[] = new Object[10];
            JButton b=new JButton();
            b.setName("g");
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
                    
                
                    model.addRow(cols);
                    
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        new ButtonTable(visVentas);
        visVentas.tblFacturasCabeceras.updateUI();
    }  
    }
    
    
}
