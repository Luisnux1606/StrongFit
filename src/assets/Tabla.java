/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Ejemplo.Render;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Tabla{
    
    public void ver_tabla(JTable tabla){
        
        tabla.setDefaultRenderer(Object.class, new Render());
        
        JButton btn1 = new JButton("Modificar");
        btn1.setName("m");
        JButton btn2 = new JButton("Eliminar");
        btn2.setName("e");
        
        DefaultTableModel d = new DefaultTableModel
        (
              /*  new Object[][]{{"1","Pedro",btn1,btn2},{"2","Juan",btn1,btn2},{"3","Rosa",btn1,btn2},{"4","Maria",btn1,btn2}},
                new Object[]{"Codigo","Nombre","M","E"}
        */
        )
        {
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tabla.setModel(d);
        
        tabla.setPreferredScrollableViewportSize(tabla.getPreferredSize());
  

    }
}
