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
public class VisHistorialPersonaServicio extends javax.swing.JFrame {

    /**
     * Creates new form VisPersona
     */
    public VisHistorialPersonaServicio() {
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
        tbl_historialPerServ = new javax.swing.JTable();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblTipoEntrenam = new javax.swing.JLabel();
        cbxServicio = new javax.swing.JComboBox<String>();
        dchFechaIni = new com.toedter.calendar.JDateChooser();
        lblFechaIni = new javax.swing.JLabel();
        lblFechaFin = new javax.swing.JLabel();
        dchFechaFin = new com.toedter.calendar.JDateChooser();
        lblPersona = new javax.swing.JLabel();
        txtPersona = new javax.swing.JTextField();
        btnBuscarPerona = new javax.swing.JButton();
        lblPrecio = new javax.swing.JLabel();
        lblPrecioIndic = new javax.swing.JLabel();
        txtBuscarCualquierCampo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblIdProd = new javax.swing.JLabel();
        lblIdPersona = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
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

        tbl_historialPerServ.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "PERSONA", "TIPO ENTRENAMIENTO", "FECHA INICIO", "FECHA FIN", "IDTIPOENT", "IDPERSONA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_historialPerServ.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tbl_historialPerServ);
        tbl_historialPerServ.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        pnl_personas.add(jScrollPane1);
        jScrollPane1.setBounds(10, 350, 880, 180);

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

        lblTipoEntrenam.setText("Tipo entrenamiento:");
        jPanel1.add(lblTipoEntrenam);
        lblTipoEntrenam.setBounds(10, 40, 120, 16);

        cbxServicio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        jPanel1.add(cbxServicio);
        cbxServicio.setBounds(140, 40, 190, 20);
        jPanel1.add(dchFechaIni);
        dchFechaIni.setBounds(140, 80, 190, 22);

        lblFechaIni.setText("Fecha inicio:");
        jPanel1.add(lblFechaIni);
        lblFechaIni.setBounds(10, 80, 100, 16);

        lblFechaFin.setText("Fecha fin:");
        jPanel1.add(lblFechaFin);
        lblFechaFin.setBounds(10, 120, 80, 16);
        jPanel1.add(dchFechaFin);
        dchFechaFin.setBounds(140, 120, 190, 22);

        lblPersona.setText("Persona:");
        jPanel1.add(lblPersona);
        lblPersona.setBounds(10, 160, 70, 16);
        jPanel1.add(txtPersona);
        txtPersona.setBounds(140, 160, 190, 22);

        btnBuscarPerona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/personas.png"))); // NOI18N
        jPanel1.add(btnBuscarPerona);
        btnBuscarPerona.setBounds(340, 151, 50, 40);

        lblPrecio.setText("$");
        jPanel1.add(lblPrecio);
        lblPrecio.setBounds(420, 40, 90, 20);

        lblPrecioIndic.setText("Precio:");
        jPanel1.add(lblPrecioIndic);
        lblPrecioIndic.setBounds(360, 40, 40, 16);

        pnl_personas.add(jPanel1);
        jPanel1.setBounds(10, 70, 870, 210);

        txtBuscarCualquierCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarCualquierCampoKeyTyped(evt);
            }
        });
        pnl_personas.add(txtBuscarCualquierCampo);
        txtBuscarCualquierCampo.setBounds(10, 320, 150, 22);

        jLabel8.setText("Buscar por nombre:");
        pnl_personas.add(jLabel8);
        jLabel8.setBounds(10, 300, 130, 16);
        pnl_personas.add(lblIdProd);
        lblIdProd.setBounds(540, 10, 41, 20);
        pnl_personas.add(lblIdPersona);
        lblIdPersona.setBounds(490, 10, 30, 20);

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
        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(VisHistorialPersonaServicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisHistorialPersonaServicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisHistorialPersonaServicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisHistorialPersonaServicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisHistorialPersonaServicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscarPerona;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnLimpiar;
    public javax.swing.JButton btnModificar;
    public javax.swing.JComboBox<String> cbxServicio;
    public com.toedter.calendar.JDateChooser dchFechaFin;
    public com.toedter.calendar.JDateChooser dchFechaIni;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblFechaFin;
    public javax.swing.JLabel lblFechaIni;
    public javax.swing.JLabel lblIdPersona;
    public javax.swing.JLabel lblIdProd;
    public javax.swing.JLabel lblPersona;
    public javax.swing.JLabel lblPrecio;
    public javax.swing.JLabel lblPrecioIndic;
    public javax.swing.JLabel lblTipoEntrenam;
    public javax.swing.JPanel pnl_personas;
    public javax.swing.JTable tbl_historialPerServ;
    public javax.swing.JTextField txtBuscarCualquierCampo;
    public javax.swing.JTextField txtPersona;
    public javax.swing.JTextField txt_id;
    // End of variables declaration//GEN-END:variables
}
