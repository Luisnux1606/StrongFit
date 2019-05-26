/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;



import assets.Calculos;
import assets.Validaciones;
import consultas.ConsCategoria;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelos.Categoria;
import modelos.Persona;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import vistas.VisCategoria;


/**
 *
 * @author aplaza
 */
public class CtrlCategoria implements ActionListener
{
    Categoria modCat;
    VisCategoria visCat;
    ConsCategoria consCat;
    Object vis;
    String cadBus;
            
    public CtrlCategoria(Categoria modCategoria, VisCategoria visCategoria, ConsCategoria consCategoria,Object vis)
    {
        this.modCat = modCategoria;
        this.visCat = visCategoria;
        this.consCat = consCategoria;        
        this.vis = vis;
        
        cadBus="";
        
        this.visCat.btnGuardar.addActionListener(this);
        this.visCat.btnEliminar.addActionListener(this);
        this.visCat.btnModificar.addActionListener(this);
        this.visCat.btnLimpiar.addActionListener(this);
        
        setListener();  
        showComboCategoriaSuperior();
        
        int colHide[] = new int[2];
        colHide[0]=0;
        colHide[1]=3;
        setHideJtableColumn(visCat.tbl_categoria,colHide);
        escribirCombos();
    }
    
    private void escribirCombos(){
        AutoCompleteDecorator.decorate(visCat.cmbCatSuperior);
        
        
    }

    
    public void showComboCategoriaSuperior()
    {
      limpiarComboCat();
        try {
           
            ResultSet listCategorias = consCat.buscarCategorias();
            
            DefaultComboBoxModel model =  (DefaultComboBoxModel)visCat.cmbCatSuperior.getModel();
           
            
            while (listCategorias.next()) {
                try { // f.id_ficha, f.fecha_ficha,CONCAT(CONCAT(p.nom_per,' '),p.ape_per) as nombresApellidos,p.id_per,m.fecha_med,m.id_med,a.fecha_ana,a.id_ana\n
                    
                    model.addElement(listCategorias.getString("tipo_cat"));
                                        
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consCat.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        visCat.cmbCatSuperior.updateUI();
    }
    
    public void iniciar()
    {
        visCat.setTitle("Categoria");
        visCat.setLocationRelativeTo(null);
        visCat.setSize(1000,700);
        visCat.setVisible(true);
        
        visCat.txt_id.setVisible(false);
             
        visCat.btnGuardar.setToolTipText("Guardar el registro");
        visCat.btnModificar.setToolTipText("Modificar el registro");
        visCat.btnEliminar.setToolTipText("Eliminar el registro");
        visCat.btnLimpiar.setToolTipText("Limpiar el registro");
        
        visCat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showTable();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == visCat.btnGuardar) //GUARDAR
        {       
           ArrayList<JTextField> jtx=new ArrayList<>();
           jtx.add(visCat.txt_nombre);  
           Categoria c = new Categoria();
            if (Validaciones.isVoid(jtx)) //validación que no esté el campo vacío
            {
                modCat.setTipo_cat(visCat.txt_nombre.getText().toUpperCase());
                c.setId_cat(consCat.getIdByNom(visCat.cmbCatSuperior.getSelectedItem()+"")); //idByNom
                
                modCat.setCategoria_id_cat(c);
                modCat.setEstado_cat(1);

                if (consCat.guardar(modCat)) {
                    JOptionPane.showMessageDialog(null, "Registro Guardado!");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al Guardar");
                }
                limpiar();
                showTable();
                showComboCategoriaSuperior();
                
            }
        }
        
        if (e.getSource() == visCat.btnEliminar) //ELIMINAR
        {
            modCat.setId_cat(Integer.parseInt(visCat.txt_id.getText()));
            modCat.setEstado_cat(0);
            int o=JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar el registro?", "Confirmar eliminar?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);            
            if (o==0) 
            {           
                if (consCat.eliminar(modCat)) {
                    JOptionPane.showMessageDialog(null, "Registro Eliminado !");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al Eliminar");
                }
                limpiar();
                showTable();
            }
        }
        
        if (e.getSource() == visCat.btnModificar)  //MODIFICAR
        {
            Categoria c = new Categoria();
            modCat.setId_cat(Validaciones.isNumVoid(visCat.tbl_categoria.getValueAt(visCat.tbl_categoria.getSelectedRow(), 0)+""));
            modCat.setTipo_cat(visCat.txt_nombre.getText().toUpperCase());
            c.setId_cat(consCat.getIdByNom(visCat.cmbCatSuperior.getSelectedItem()+"")); //idByNom
            modCat.setCategoria_id_cat(c);
            modCat.setEstado_cat(1);
            if (consCat.modificar(modCat)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado!");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
            }
            limpiar();
            showTable();
        }
        
        if (e.getSource() == visCat.btnLimpiar)   //LIMPIAR
        {
           limpiar();
           desabilitaHabilita(visCat.btnGuardar,true);
           desabilitaHabilita(visCat.btnModificar,false);
        }
    }
    
    public void limpiar()
    {
        visCat.txt_nombre.setText("");
        visCat.cmbCatSuperior.setSelectedIndex(0);
    }
    
     public void setHideJtableColumn(JTable table, int col[])
    {
        for (int i = 0; i < col.length; i++) {
            table.getColumnModel().getColumn(col[i]).setMaxWidth(0);
            table.getColumnModel().getColumn(col[i]).setMinWidth(0);
            table.getColumnModel().getColumn(col[i]).setPreferredWidth(0);
        }       
    
    }
    
    public void showTable()
    {
        limpiarTabla();                            
        ArrayList<Categoria> catList = consCat.buscarTodos(modCat);
        DefaultTableModel model =  (DefaultTableModel)visCat.tbl_categoria.getModel();
        Object cols[] = new Object[4];

        for (int i = 0; i < catList.size(); i++) 
        {
            cols[0] = catList.get(i).getId_cat();
            cols[1] = catList.get(i).getTipo_cat().toUpperCase();
            cols[2] = catList.get(i).getCategoria_id_cat().getTipo_cat().toUpperCase();
            cols[3] = catList.get(i).getCategoria_id_cat().getId_cat();
            model.addRow(cols);                    
        }   
        visCat.tbl_categoria.updateUI();
    }
    
    public void limpiarTabla()
    {
        DefaultTableModel tb = (DefaultTableModel) visCat.tbl_categoria.getModel();
        int a = visCat.tbl_categoria.getRowCount()-1;
        for (int i = a; i >= 0; i--) {           
            tb.removeRow(tb.getRowCount()-1);
        } 
    }
    public void limpiarComboCat()
    {
        DefaultComboBoxModel tb = (DefaultComboBoxModel) visCat.cmbCatSuperior.getModel();
        int a = visCat.tbl_categoria.getRowCount()-1;
                  
        tb.removeAllElements();
         
    }
    
    public void setListener()
    {
        //CLICK EN LA TABLA 
        MouseListener mouseListTblCategoria = new MouseListener() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {    
                if(e.getClickCount()==1)
                {
                     getTableToTxts();
                     desabilitaHabilita(visCat.btnGuardar,false);
                     desabilitaHabilita(visCat.btnModificar,true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
        };
        visCat.tbl_categoria.addMouseListener(mouseListTblCategoria);
        
        
        KeyListener keyListenertxtBuscarCedula = new KeyListener() 
        {
            public void keyPressed(KeyEvent keyEvent) {
                printIt("Pressed", keyEvent);}

            public void keyReleased(KeyEvent keyEvent) {
                printIt("Released", keyEvent);}

            public void keyTyped(KeyEvent e) 
            {
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
        visCat.txtBuscarNombre.addKeyListener(keyListenertxtBuscarCedula);
    }
    
    public void getTableToTxts()
    {
        JTable tblCat = visCat.tbl_categoria;
        visCat.txt_id.setText(String.valueOf(tblCat.getValueAt(tblCat.getSelectedRow(), 0)));
        visCat.txt_nombre.setText(String.valueOf(tblCat.getValueAt(tblCat.getSelectedRow(), 1)));
        if (String.valueOf(tblCat.getValueAt(tblCat.getSelectedRow(), 2)+"").length()==0) {
            visCat.cmbCatSuperior.setSelectedIndex(0);
        }
        else
            visCat.cmbCatSuperior.setSelectedItem(String.valueOf(tblCat.getValueAt(tblCat.getSelectedRow(), 2)+""));
            
        
    }
    
    public void desabilitaHabilita(JButton btn,boolean estado)
    {
         btn.setEnabled(estado);
    }
    
    public void showTableByNom(String nom)
    {
        limpiarTabla();                            
        ArrayList<Categoria> catList = consCat.buscarTodosPorNom(modCat,nom);
        DefaultTableModel model =  (DefaultTableModel)visCat.tbl_categoria.getModel();
        Object cols[] = new Object[4];

        for (int i = 0; i < catList.size(); i++) 
        {
            cols[0] = catList.get(i).getId_cat();
            cols[1] = catList.get(i).getTipo_cat().toUpperCase();
            cols[2] = catList.get(i).getCategoria_id_cat().getTipo_cat().toUpperCase();
            cols[3] = catList.get(i).getCategoria_id_cat().getId_cat();
            model.addRow(cols);                    
        }   
    }
    
    
}
