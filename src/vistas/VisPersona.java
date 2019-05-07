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
public class VisPersona extends javax.swing.JFrame {

    /**
     * Creates new form VisPersona
     */
    public VisPersona() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        tbl_personas = new javax.swing.JTable();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_cedula = new javax.swing.JTextField();
        txt_nombres = new javax.swing.JTextField();
        txt_apellidos = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_edad = new javax.swing.JTextField();
        txt_nro_fono = new javax.swing.JTextField();
        dtc_fechaNac = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        txtCorreoElect = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cmbxGenero = new javax.swing.JComboBox<String>();
        txtBuscarCedula = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mniMembresia = new javax.swing.JMenuItem();
        mniFicha = new javax.swing.JMenuItem();
        mniReportes = new javax.swing.JMenuItem();
        mniSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        tbl_personas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "CEDULA", "NOMBRE", "APELLIDOS", "GENERO", "CORREO ELEC.", "TELEFONO", "EDAD", "FECHA_NACIMIENTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_personas.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tbl_personas);
        tbl_personas.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

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

        jLabel2.setText("CEDULA : ");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(12, 13, 70, 20);

        jLabel3.setText("NOMBRES:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(12, 48, 70, 16);

        jLabel4.setText("APELLIDOS:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(12, 78, 80, 16);

        txt_cedula.setName("cedula"); // NOI18N
        txt_cedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cedulaActionPerformed(evt);
            }
        });
        jPanel1.add(txt_cedula);
        txt_cedula.setBounds(142, 13, 190, 22);

        txt_nombres.setName("nombre"); // NOI18N
        txt_nombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombresActionPerformed(evt);
            }
        });
        jPanel1.add(txt_nombres);
        txt_nombres.setBounds(142, 48, 190, 22);

        txt_apellidos.setName("apellido"); // NOI18N
        jPanel1.add(txt_apellidos);
        txt_apellidos.setBounds(142, 78, 190, 22);

        btnBuscar.setBackground(new java.awt.Color(255, 255, 204));
        btnBuscar.setForeground(new java.awt.Color(255, 255, 204));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lupa6.png"))); // NOI18N
        jPanel1.add(btnBuscar);
        btnBuscar.setBounds(350, 13, 50, 40);

        jLabel6.setText("EDAD:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 110, 36, 16);

        jLabel5.setText("NRO_FONO:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(470, 80, 80, 16);

        jLabel7.setText("FECHA DE NACIMIENTO:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(470, 110, 160, 16);

        txt_edad.setName("edad"); // NOI18N
        jPanel1.add(txt_edad);
        txt_edad.setBounds(142, 110, 190, 22);

        txt_nro_fono.setName("fono"); // NOI18N
        jPanel1.add(txt_nro_fono);
        txt_nro_fono.setBounds(650, 80, 190, 22);

        dtc_fechaNac.setDateFormatString("dd/MM/yyyy");
        dtc_fechaNac.setName("fechaNac"); // NOI18N
        jPanel1.add(dtc_fechaNac);
        dtc_fechaNac.setBounds(650, 110, 190, 22);

        jLabel9.setText("CORREO ELECTR.:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(470, 50, 120, 16);
        jPanel1.add(txtCorreoElect);
        txtCorreoElect.setBounds(650, 50, 190, 20);

        jLabel10.setText("GENERO:");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(470, 20, 70, 16);

        cmbxGenero.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MASCULINO", "FEMENINO" }));
        jPanel1.add(cmbxGenero);
        cmbxGenero.setBounds(650, 10, 190, 22);

        pnl_personas.add(jPanel1);
        jPanel1.setBounds(10, 70, 870, 170);

        txtBuscarCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarCedulaKeyTyped(evt);
            }
        });
        pnl_personas.add(txtBuscarCedula);
        txtBuscarCedula.setBounds(10, 280, 150, 22);

        jLabel8.setText("Buscar por nombres:");
        pnl_personas.add(jLabel8);
        jLabel8.setBounds(10, 260, 110, 16);

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

        mniMembresia.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        mniMembresia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lupa6_1.png"))); // NOI18N
        mniMembresia.setText("Membresias");
        mniMembresia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniMembresiaActionPerformed(evt);
            }
        });
        jMenu1.add(mniMembresia);

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

    private void txt_nombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombresActionPerformed

    private void txt_cedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cedulaActionPerformed

    private void txtBuscarCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCedulaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCedulaKeyTyped

    private void mniReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniReportesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mniReportesActionPerformed

    private void mniFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniFichaActionPerformed
        
    }//GEN-LAST:event_mniFichaActionPerformed

    private void mniSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSalirActionPerformed
       // this.dispose();
    }//GEN-LAST:event_mniSalirActionPerformed

    private void mniMembresiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniMembresiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mniMembresiaActionPerformed

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
            java.util.logging.Logger.getLogger(VisPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisPersona().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnLimpiar;
    public javax.swing.JButton btnModificar;
    public javax.swing.JComboBox<String> cmbxGenero;
    public com.toedter.calendar.JDateChooser dtc_fechaNac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JMenuItem mniFicha;
    public javax.swing.JMenuItem mniMembresia;
    public javax.swing.JMenuItem mniReportes;
    public javax.swing.JMenuItem mniSalir;
    public javax.swing.JPanel pnl_personas;
    public javax.swing.JTable tbl_personas;
    public javax.swing.JTextField txtBuscarCedula;
    public javax.swing.JTextField txtCorreoElect;
    public javax.swing.JTextField txt_apellidos;
    public javax.swing.JTextField txt_cedula;
    public javax.swing.JTextField txt_edad;
    public javax.swing.JTextField txt_id;
    public javax.swing.JTextField txt_nombres;
    public javax.swing.JTextField txt_nro_fono;
    // End of variables declaration//GEN-END:variables
}
