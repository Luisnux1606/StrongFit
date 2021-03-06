/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import modelos.Persona;

/**
 *
 * @author Administrator
 */
public class ItemRendererClienteIngEgr extends BasicComboBoxRenderer {
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, value, index, isSelected,
        cellHasFocus);

    if (value != null) {
      Persona item = (Persona) value;
        if (item!=null) {
            if (item.getApellido()==null) {
                item.setApellido("");
            }
            if (item.getNombre()==null) {
                item.setNombre("");
            }
            if(item.getCedula()==null){
                item.setCedula("");
            }
            setText(item.getApellido().toUpperCase()+" "+item.getNombre().toUpperCase());
        }
    }

    if (index == -1) {
      Persona item = (Persona) value;
      setText("" + item.getId());
    }

    return this;
  }
}