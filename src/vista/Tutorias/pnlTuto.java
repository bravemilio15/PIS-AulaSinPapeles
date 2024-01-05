/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.Tutorias;

import controlador.ControlarEstudiante;
import controlador.ControlarMatricula;
import controlador.dao.CicloDAO;
import controlador.dao.CuentaDao;
import controlador.dao.EstudianteDao;
import controlador.dao.ParaleloDAO;
import controlador.dao.RolDao;
import controlador.dao.TutoriaDao;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.awt.Component;
import java.awt.PopupMenu;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import modelo.Ciclo;
import modelo.Paralelo;
import modelo.tabla.ModeloTablaEstudiante;
import modelo.tabla.ModeloTablaMatricula;
import modelo.tabla.ModeloTablaTutorias;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import vista.utilidades.DateLabelFormatter;
import vista.utilidades.Utilidades;

/**
 *
 * @author cristian
 */
public class pnlTuto extends javax.swing.JPanel {

    private CicloDAO cd = new CicloDAO();
    private ParaleloDAO pd = new ParaleloDAO();
    private CuentaDao cuentad = new CuentaDao();
    private RolDao rld = new RolDao();
    private EstudianteDao ed = new EstudianteDao();
    private TutoriaDao td = new TutoriaDao();
    private ModeloTablaTutorias modelo = new ModeloTablaTutorias();
    private int pos = -1;

    /**
     * Creates new form pnlHome
     */
    public pnlTuto() {
        initComponents();
        initDatePickerForFecha();
        cargarCombos();

    }

    public void cargarCombos() {
        Utilidades.cargarMaterias(cbxCiclo.getSelectedItem().toString(), cbxMaterias);
        Utilidades.cargarParalelo(cbxParalelo);
        Utilidades.cargarCiclos(cbxCiclo);
        cbxCiclo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Cuando se selecciona un ciclo, cargar las materias correspondientes
                    try {
                        String cicloSeleccionado = cbxCiclo.getSelectedItem().toString();
                        Utilidades.cargarMaterias(cicloSeleccionado, cbxMaterias);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public void guardarTutoria() throws EmptyException, PositionException, IOException {

        String paraleloSeleccionado = cbxParalelo.getSelectedItem().toString();
        Paralelo paralelo = pd.obtenerParaleloPorNombre(paraleloSeleccionado);

        td.getTutoria().setTemaGeneral(txtTemaGeneral.getText());
        td.getTutoria().setDescripcion(txtDescripcion.getText());
        td.getTutoria().setAsistencia(true);
        td.getTutoria().setEstado(true);
        td.getTutoria().setFecha(txtFecha.getText());
        td.getTutoria().setParalelo(paralelo);

        td.guardar();
        updateUI();

    }

    private void initDatePickerForFecha() {
        JDatePickerImpl datePicker = Utilidades.createDatePicker();

        datePicker.addActionListener(e -> {
            Date selectedDate = (Date) datePicker.getModel().getValue();
            txtFecha.setText(Utilidades.formatDateString(selectedDate, "yyyy-MM-dd"));
            // También puedes llamar a actualizarEdad() aquí si es necesario
        });

        txtFecha.setEditable(false);

        txtFecha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Utilidades.showDatePickerPopup(txtFecha, datePicker);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblMatriculas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTemaGeneral = new javax.swing.JTextField();
        cbxMaterias = new javax.swing.JComboBox<>();
        cbxParalelo = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        txtFecha = new javax.swing.JTextField();
        cbxCiclo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();

        tblMatriculas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblMatriculas);

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 255));
        jLabel1.setText("ASIGNAR TUTORIAS");

        jLabel2.setForeground(new java.awt.Color(0, 153, 255));
        jLabel2.setText("TEMA GENERAL");

        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("MATERIA");

        jLabel4.setForeground(new java.awt.Color(0, 153, 255));
        jLabel4.setText("CURSO");

        jLabel5.setForeground(new java.awt.Color(0, 153, 255));
        jLabel5.setText("DESCRIPCION");

        jLabel6.setForeground(new java.awt.Color(0, 153, 255));
        jLabel6.setText("FECHA");

        cbxMaterias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxParalelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane2.setViewportView(txtDescripcion);

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cbxCiclo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setForeground(new java.awt.Color(0, 153, 255));
        jLabel7.setText("CICLO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel5)))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxMaterias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addComponent(cbxParalelo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTemaGeneral, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addComponent(cbxCiclo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTemaGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(cbxCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(cbxMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(cbxParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            guardarTutoria();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
        } catch (EmptyException ex) {
            Logger.getLogger(pnlTuto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PositionException ex) {
            Logger.getLogger(pnlTuto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pnlTuto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxCiclo;
    private javax.swing.JComboBox<String> cbxMaterias;
    private javax.swing.JComboBox<String> cbxParalelo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblMatriculas;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtTemaGeneral;
    // End of variables declaration//GEN-END:variables
}
