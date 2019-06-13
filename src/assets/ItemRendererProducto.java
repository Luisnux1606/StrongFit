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
import modelos.Producto;

/**
 *
 * @author Administrator
 */
public class ItemRendererProducto extends BasicComboBoxRenderer {
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, value, index, isSelected,
        cellHasFocus);

    if (value != null) {
      Producto item = (Producto) value;
      setText(item.getDescripcion_prod().toUpperCase());
    }

    if (index == -1) {
      Producto item = (Producto) value;
      setText("" + item.getId_prod());
    }

    return this;
  }
}