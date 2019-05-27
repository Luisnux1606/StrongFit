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
public class VisMembresia extends javax.swing.JFrame {

    /**
     * Creates new form VisPersona
     */
    public VisMembresia() {
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
        tbl_membresias = new javax.swing.JTable();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        txt_dscto = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        txtBuscarNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
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

        tbl_membresias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "DSCTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_membresias.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tbl_membresias);
        tbl_membresias.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

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

        jLabel2.setText("Nombre membresia:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(12, 13, 130, 20);

        jLabel3.setText("Descuento:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(12, 48, 70, 16);

        txt_nombre.setName("cedula"); // NOI18N
        txt_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombreActionPerformed(evt);
            }
        });
        jPanel1.add(txt_nombre);
        txt_nombre.setBounds(142, 13, 190, 22);

        txt_dscto.setName("nombre"); // NOI18N
        txt_dscto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dsctoActionPerformed(evt);
            }
        });
        jPanel1.add(txt_dscto);
        txt_dscto.setBounds(142, 48, 190, 22);

        btnBuscar.setBackground(new java.awt.Color(255, 255, 204));
        btnBuscar.setForeground(new java.awt.Color(255, 255, 204));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lupa6.png"))); // NOI18N
        jPanel1.add(btnBuscar);
        btnBuscar.setBounds(350, 13, 50, 40);

        pnl_personas.add(jPanel1);
        jPanel1.setBounds(10, 70, 870, 170);

        txtBuscarNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarNombreKeyTyped(evt);
            }
        });
        pnl_personas.add(txtBuscarNombre);
        txtBuscarNombre.setBounds(10, 280, 150, 22);

        jLabel8.setText("Buscar por nombre:");
        pnl_personas.add(jLabel8);
        jLabel8.setBounds(10, 260, 130, 16);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 938;
        gridBagConstraints.ipady = 595;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 54, 13, 30);
        getContentPane().add(pnl_personas, gridBagConstraints);

        lblNomEmp.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        lblNomEmp.setText("MEMBRESIAS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 346, 0, 0);
        getContentPane().add(lblNomEmp, gridBagConstraints);
        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_dsctoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dsctoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dsctoActionPerformed

    private void txt_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreActionPerformed

    private void txtBuscarNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarNombreKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarNombreKeyTyped

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
            java.util.logging.Logger.getLogger(VisMembresia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisMembresia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisMembresia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisMembresia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisMembresia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnLimpiar;
    public javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblNomEmp;
    public javax.swing.JPanel pnl_personas;
    public javax.swing.JTable tbl_membresias;
    public javax.swing.JTextField txtBuscarNombre;
    public javax.swing.JTextField txt_dscto;
    public javax.swing.JTextField txt_id;
    public javax.swing.JTextField txt_nombre;
    // End of variables declaration//GEN-END:variables
}
