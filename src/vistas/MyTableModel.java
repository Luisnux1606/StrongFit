package visual.facturacion;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {
    
    /**
	 * 
	 */
	public MyTableModel(){
		
	}
	private static final long serialVersionUID = 1L;

	public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
    	if (col == 1) {
    		return false;
    		} else {
            return true;
        }
    }
}

	