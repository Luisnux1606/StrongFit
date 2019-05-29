/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public class VisProductos extends javax.swing.JFrame {

    /**
     * Creates new form VisPersona
     */
    public VisProductos() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnl_personas = new javax.swing.JPanel();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_productos = new javax.swing.JTable();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblDescripcionProducto = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDescripcionProd = new javax.swing.JTextField();
        txtPrecioProd = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        Categoria = new javax.swing.JLabel();
        cbxCategoria = new javax.swing.JComboBox<String>();
        dchFechaIni = new com.toedter.calendar.JDateChooser();
        lblFechaIni = new javax.swing.JLabel();
        lblFechaFin = new javax.swing.JLabel();
        dchFechaFin = new com.toedter.calendar.JDateChooser();
        txtEntradas = new javax.swing.JTextField();
        txtSalidas = new javax.swing.JTextField();
        lblSalidas = new javax.swing.JLabel();
        lblEntradas = new javax.swing.JLabel();
        lblExistentes = new javax.swing.JLabel();
        txtExistentes = new javax.swing.JTextField();
        txtBuscarCualquierCampo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblIdCat = new javax.swing.JLabel();
        rdbProductos = new javax.swing.JRadioButton();
        rdbServicios = new javax.swing.JRadioButton();
        lblNomEmp = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(java.awt.Color.lightGray);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        pnl_personas.setBackground(new java.awt.Color(204, 204, 204));
        pnl_personas.setLayout(null);

        btnModificar.setBackground(new java.awt.Color(102, 102, 102));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/modificar.png"))); // NOI18N
        pnl_personas.add(btnModificar);
        btnModificar.setBounds(60, 20, 40, 40);

        btnEliminar.setBackground(new java.awt.Color(102, 102, 102));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eliminar.png"))); // NOI18N
        pnl_personas.add(btnEliminar);
        btnEliminar.setBounds(110, 20, 50, 40);

        tbl_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "DESCRIPCION", "PRECIO", "FECHA INI.", "FECHA FIN.", "CATEGORIA", "EXISTENTES INICIALES", "ENTRADAS", "SALIDAS", "STOCK", "IDCAT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, false, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_productos.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tbl_productos);
        tbl_productos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        pnl_personas.add(jScrollPane1);
        jScrollPane1.setBounds(10, 410, 880, 180);

        btnLimpiar.setBackground(new java.awt.Color(102, 102, 102));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/limpiar2.png"))); // NOI18N
        pnl_personas.add(btnLimpiar);
        btnLimpiar.setBounds(170, 20, 40, 41);

        btnGuardar.setBackground(new java.awt.Color(102, 102, 102));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/guardar7.png"))); // NOI18N
        pnl_personas.add(btnGuardar);
        btnGuardar.setBounds(10, 20, 40, 40);
        pnl_personas.add(txt_id);
        txt_id.setBounds(430, 10, 30, 22);

        jPanel1.setBackground(java.awt.Color.gray);
        jPanel1.setLayout(null);

        lblDescripcionProducto.setText("Descripcion:");
        jPanel1.add(lblDescripcionProducto);
        lblDescripcionProducto.setBounds(12, 13, 130, 20);

        jLabel3.setText("Precio:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 40, 70, 16);

        txtDescripcionProd.setName("cedula"); // NOI18N
        txtDescripcionProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionProdActionPerformed(evt);
            }
        });
        jPanel1.add(txtDescripcionProd);
        txtDescripcionProd.setBounds(140, 10, 190, 25);

        txtPrecioProd.setName("nombre"); // NOI18N
        txtPrecioProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioProdActionPerformed(evt);
            }
        });
        jPanel1.add(txtPrecioProd);
        txtPrecioProd.setBounds(140, 40, 190, 25);

        btnBuscar.setBackground(new java.awt.Color(255, 255, 204));
        btnBuscar.setForeground(new java.awt.Color(255, 255, 204));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lupa6.png"))); // NOI18N
        jPanel1.add(btnBuscar);
        btnBuscar.setBounds(350, 13, 50, 40);

        Categoria.setText("Categoria:");
        jPanel1.add(Categoria);
        Categoria.setBounds(10, 160, 70, 16);
        jPanel1.add(cbxCategoria);
        cbxCategoria.setBounds(140, 160, 190, 25);
        jPanel1.add(dchFechaIni);
        dchFechaIni.setBounds(140, 70, 190, 25);

        lblFechaIni.setText("Fecha inicio:");
        jPanel1.add(lblFechaIni);
        lblFechaIni.setBounds(10, 70, 100, 16);

        lblFechaFin.setText("Fecha fin:");
        jPanel1.add(lblFechaFin);
        lblFechaFin.setBounds(10, 100, 80, 16);
        jPanel1.add(dchFechaFin);
        dchFechaFin.setBounds(140, 100, 190, 25);
        jPanel1.add(txtEntradas);
        txtEntradas.setBounds(140, 190, 190, 25);
        jPanel1.add(txtSalidas);
        txtSalidas.setBounds(140, 220, 190, 25);

        lblSalidas.setText("Salidas:");
        jPanel1.add(lblSalidas);
        lblSalidas.setBounds(10, 220, 70, 16);

        lblEntradas.setText("Entradas:");
        jPanel1.add(lblEntradas);
        lblEntradas.setBounds(10, 190, 60, 16);

        lblExistentes.setText("Existentes Iniciales:");
        jPanel1.add(lblExistentes);
        lblExistentes.setBounds(10, 130, 120, 16);
        jPanel1.add(txtExistentes);
        txtExistentes.setBounds(140, 130, 190, 25);

        pnl_personas.add(jPanel1);
        jPanel1.setBounds(10, 70, 870, 260);

        txtBuscarCualquierCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarCualquierCampoKeyTyped(evt);
            }
        });
        pnl_personas.add(txtBuscarCualquierCampo);
        txtBuscarCualquierCampo.setBounds(10, 380, 150, 22);

        jLabel8.setText("Buscar por nombre:");
        pnl_personas.add(jLabel8);
        jLabel8.setBounds(10, 360, 130, 16);
        pnl_personas.add(lblIdCat);
        lblIdCat.setBounds(470, 10, 41, 20);

        rdbProductos.setText("productos");
        pnl_personas.add(rdbProductos);
        rdbProductos.setBounds(200, 380, 85, 25);

        rdbServicios.setText("servicios");
        pnl_personas.add(rdbServicios);
        rdbServicios.setBounds(330, 380, 77, 25);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 938;
        gridBagConstraints.ipady = 595;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 103, 0, 79);
        getContentPane().add(pnl_personas, gridBagConstraints);

        lblNomEmp.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        lblNomEmp.setText("PRODUCTOS/SERVICIOS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 234, 0, 0);
        getContentPane().add(lblNomEmp, gridBagConstraints);
        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPrecioProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioProdActionPerformed

    private void txtDescripcionProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionProdActionPerformed

    private void txtBuscarCualquierCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCualquierCampoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCualquierCampoKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VisProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel Categoria;
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnLimpiar;
    public javax.swing.JButton btnModificar;
    public javax.swing.JComboBox<String> cbxCategoria;
    public com.toedter.calendar.JDateChooser dchFechaFin;
    public com.toedter.calendar.JDateChooser dchFechaIni;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblDescripcionProducto;
    private javax.swing.JLabel lblEntradas;
    public javax.swing.JLabel lblExistentes;
    public javax.swing.JLabel lblFechaFin;
    public javax.swing.JLabel lblFechaIni;
    public javax.swing.JLabel lblIdCat;
    public javax.swing.JLabel lblNomEmp;
    private javax.swing.JLabel lblSalidas;
    public javax.swing.JPanel pnl_personas;
    public javax.swing.JRadioButton rdbProductos;
    public javax.swing.JRadioButton rdbServicios;
    public javax.swing.JTable tbl_productos;
    public javax.swing.JTextField txtBuscarCualquierCampo;
    public javax.swing.JTextField txtDescripcionProd;
    public javax.swing.JTextField txtEntradas;
    public javax.swing.JTextField txtExistentes;
    public javax.swing.JTextField txtPrecioProd;
    public javax.swing.JTextField txtSalidas;
    public javax.swing.JTextField txt_id;
    // End of variables declaration//GEN-END:variables
}
