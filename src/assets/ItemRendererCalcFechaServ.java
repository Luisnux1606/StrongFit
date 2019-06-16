/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import modelos.CalculoFechaServicio;
import modelos.Persona;

/**
 *
 * @author Administrator
 */
public class ItemRendererCalcFechaServ extends BasicComboBoxRenderer {
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, value, index, isSelected,
        cellHasFocus);

    if (value != null) {
        CalculoFechaServicio item = (CalculoFechaServicio) value;
        setText(item.getDescripcion_calServ().toUpperCase());
    }

    if (index == -1) {
      CalculoFechaServicio item = (CalculoFechaServicio) value;
      setText(item.getId_calServ()+"" );//item.getId_calServ());
    }

    return this;
  }
}