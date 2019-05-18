/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Validaciones;
import consultas.ConsProductos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelos.Categoria;
import modelos.Producto;
import vistas.VisProductos;


/**
 *
 * @author aplaza
 */
public class CtrlProducto implements ActionListener
{
    Producto modProducto;
    Categoria modCategoria;
    ArrayList<Producto> lstProducto;
    ConsProductos consProducto;
    VisProductos visProducto;
    Object visFicha;
    
    public CtrlProducto(Producto modProducto, Categoria modCategoria, ConsProductos consProducto, VisProductos visProductos, Object visFicha)
    {
        this.modProducto=modProducto;  
        this.modCategoria=modCategoria;
        this.consProducto = consProducto;
        this.visProducto = visProductos;        
        this.visFicha = visFicha;
                
        this.visProducto.btnGuardar.addActionListener(this);
        setFocus();
    }
    
    public void setFocus()
    {
        visProducto.txt_descripcionProd.requestFocus();
        visProducto.txtPrecioProd.setNextFocusableComponent(visProducto.txtCategoria);
    }

    public void iniciar()
    {
        visProducto.setTitle("PRODUCTOS");
        visProducto.setLocationRelativeTo(null);
        visProducto.setSize(1000,700);
        visProducto.setVisible(true);
        
        visProducto.btnGuardar.setToolTipText("Guardar el registro");
        
       /* visProducto.txt_id.setVisible(false);
        
        visProducto.btnBuscar.setToolTipText("Buscar el registro");
        visProducto.btnGuardar.setToolTipText("Guardar el registro");
        visProducto.btnModificar.setToolTipText("Modificar el registro");
        visProducto.btnEliminar.setToolTipText("Eliminar el registro");
        visProducto.btnLimpiar.setToolTipText("Limpiar el registro");*/
        
        visProducto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showTable();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == visProducto.btnGuardar) 
        {
            ArrayList<JTextField> jtx=new ArrayList<>();
            jtx.add(visProducto.txt_descripcionProd);
            jtx.add(visProducto.txtPrecioProd);
           
            if (Validaciones.isVoid(jtx)) 
            {
                modProducto.setDescripcion_prod(visProducto.txt_descripcionProd.getText());
                modProducto.setPrecio_prod(Double.parseDouble(visProducto.txtPrecioProd.getText()));
               
                modCategoria.setId_cat(visProducto.cbxCategoria.getSelectedIndex()+1);
                modProducto.setCategoria(modCategoria);
                
                modProducto.setEstado_prod(1);
                
                if (consProducto.registrar(modProducto)) {
                        JOptionPane.showMessageDialog(null, "Registro Guardado!");
                }else{
                        JOptionPane.showMessageDialog(null, "Error al Guardar");
                }
                limpiar();
                showTable();
           }
        }
        
        if (e.getSource() == visProducto.btnEliminar) 
        {
            modProducto.setId_prod(Integer.parseInt(visProducto.txt_id.getText()));
            modProducto.setEstado_prod(0);
            int o= JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar el registro?", "Confirmar eliminar?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);            
            if (o ==0) 
            {           
                if (consProducto.eliminar(modProducto)) {
                    JOptionPane.showMessageDialog(null, "Registro Eliminado !");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al Eliminar");
                }
                limpiar();
                showTable();
            }
            
        }
    }
    
    public void limpiar()
    {
        visProducto.txt_descripcionProd.setText("");
        visProducto.txtPrecioProd.setText("");
    }
    
    
    public void showTable()
    {
        limpiarTabla();                            
        ArrayList<Producto> listProducto = consProducto.buscarTodos(modProducto, modCategoria); 
        DefaultTableModel model =  (DefaultTableModel)visProducto.tbl_productos.getModel();
        Object cols[] = new Object[4];

        for (int i = 0; i < listProducto.size(); i++) 
        {
            cols[0] = listProducto.get(i).getId_prod();
            cols[1] = listProducto.get(i).getDescripcion_prod().toUpperCase();
            cols[2] = listProducto.get(i).getPrecio_prod();
            cols[3] = listProducto.get(i).getCategoria().getId_cat();
            model.addRow(cols);                    
        }   
    }
    
    public void limpiarTabla()
    {
        DefaultTableModel tb = (DefaultTableModel) visProducto.tbl_productos.getModel();
        int a = visProducto.tbl_productos.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
}
