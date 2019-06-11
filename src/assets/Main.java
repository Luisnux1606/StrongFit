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
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
/*from   w ww.  j  a v  a  2  s  .  c o m*/
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import modelos.Persona;

public class Main extends JFrame implements ActionListener {
  public Main() {
    Vector model = new Vector();
    model.addElement(new Item(1, "A"));
    model.addElement(new Item(2, "B"));
    model.addElement(new Item(3, "C"));
    model.addElement(new Item(4, "D"));

    JComboBox comboBox = new JComboBox(model);
    comboBox.addActionListener(this);
    comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
    getContentPane().add(comboBox, BorderLayout.NORTH);

    comboBox = new JComboBox(model);
    comboBox.setRenderer(new ItemRenderer());
    comboBox.addActionListener(this);
    getContentPane().add(comboBox, BorderLayout.SOUTH);
  }

  public void actionPerformed(ActionEvent e) {
    JComboBox comboBox = (JComboBox) e.getSource();
    Persona item = (Persona) comboBox.getSelectedItem();
    System.out.println(item.getId() + " : " + item.getApellido());
  }

  public static void main(String[] args) {
    JFrame frame = new Main();
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

}
/*
class ItemRenderer extends BasicComboBoxRenderer {
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, value, index, isSelected,
        cellHasFocus);

    if (value != null) {
      Item item = (Item) value;
      setText(item.getDescription().toUpperCase());
    }

    if (index == -1) {
      Item item = (Item) value;
      setText("" + item.getId());
    }

    return this;
  }
}
*/
class Item {
  private int id;
  private String description;

  public Item(int id, String description) {
    this.id = id;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String toString() {
    return description;
  }
}
