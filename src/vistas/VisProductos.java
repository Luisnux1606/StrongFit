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
        txtBuscarCualquierCampo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblIdCat = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mniFicha = new javax.swing.JMenuItem();
        mniReportes = new javax.swing.JMenuItem();
        mniSalir = new javax.swing.JMenuItem();

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
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "DESCRIPCION", "PRECIO", "CATEGORIA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_productos.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tbl_productos);
        tbl_productos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        pnl_personas.add(jScrollPane1);
        jScrollPane1.setBounds(10, 310, 880, 220);

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
        jLabel3.setBounds(12, 48, 70, 16);

        txtDescripcionProd.setName("cedula"); // NOI18N
        txtDescripcionProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionProdActionPerformed(evt);
            }
        });
        jPanel1.add(txtDescripcionProd);
        txtDescripcionProd.setBounds(142, 13, 190, 22);

        txtPrecioProd.setName("nombre"); // NOI18N
        txtPrecioProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioProdActionPerformed(evt);
            }
        });
        jPanel1.add(txtPrecioProd);
        txtPrecioProd.setBounds(142, 48, 190, 22);

        btnBuscar.setBackground(new java.awt.Color(255, 255, 204));
        btnBuscar.setForeground(new java.awt.Color(255, 255, 204));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lupa6.png"))); // NOI18N
        jPanel1.add(btnBuscar);
        btnBuscar.setBounds(350, 13, 50, 40);

        Categoria.setText("Categoria:");
        jPanel1.add(Categoria);
        Categoria.setBounds(10, 90, 70, 16);

        cbxCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Servicio Gimnasio", "Producto"}));
        jPanel1.add(cbxCategoria);
        cbxCategoria.setBounds(140, 90, 190, 20);

        pnl_personas.add(jPanel1);
        jPanel1.setBounds(10, 70, 870, 170);

        txtBuscarCualquierCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarCualquierCampoKeyTyped(evt);
            }
        });
        pnl_personas.add(txtBuscarCualquierCampo);
        txtBuscarCualquierCampo.setBounds(10, 280, 150, 22);

        jLabel8.setText("Buscar por nombre:");
        pnl_personas.add(jLabel8);
        jLabel8.setBounds(10, 260, 130, 16);
        pnl_personas.add(lblIdCat);
        lblIdCat.setBounds(470, 10, 41, 20);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 938;
        gridBagConstraints.ipady = 595;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 54, 13, 30);
        getContentPane().add(pnl_personas, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setText("STRONGFIT");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 346, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        jMenu1.setText("Archivo");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        mniFicha.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        mniFicha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/nuevo7.png"))); // NOI18N
        mniFicha.setText("Ficha");
        mniFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFichaActionPerformed(evt);
            }
        });
        jMenu1.add(mniFicha);

        mniReportes.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        mniReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reportes.png"))); // NOI18N
        mniReportes.setText("Reportes");
        mniReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniReportesActionPerformed(evt);
            }
        });
        jMenu1.add(mniReportes);

        mniSalir.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        mniSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/salir2.png"))); // NOI18N
        mniSalir.setText("Salir");
        mniSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSalirActionPerformed(evt);
            }
        });
        jMenu1.add(mniSalir);

        jMenuBar1.add(jMenu1);

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

    private void mniReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniReportesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mniReportesActionPerformed

    private void mniFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniFichaActionPerformed
        
    }//GEN-LAST:event_mniFichaActionPerformed

    private void mniSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSalirActionPerformed
       // this.dispose();
    }//GEN-LAST:event_mniSalirActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblDescripcionProducto;
    public javax.swing.JLabel lblIdCat;
    public javax.swing.JMenuItem mniFicha;
    public javax.swing.JMenuItem mniReportes;
    public javax.swing.JMenuItem mniSalir;
    public javax.swing.JPanel pnl_personas;
    public javax.swing.JTable tbl_productos;
    public javax.swing.JTextField txtBuscarCualquierCampo;
    public javax.swing.JTextField txtDescripcionProd;
    public javax.swing.JTextField txtPrecioProd;
    public javax.swing.JTextField txt_id;
    // End of variables declaration//GEN-END:variables
}