/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.Administracion;

import controlador.ControlarDocente;
import controlador.ControlarDocente;
import controlador.ControlarMatricula;
import controlador.dao.MateriaDao;
import controlador.dao.MatriculaDao;
import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Ciclo;
import modelo.Docente;
import modelo.Materia;
import modelo.Matricula;
import modelo.tabla.ModeloTablaDocente;
import modelo.tabla.ModeloTablaDocente;
import modelo.tabla.ModeloTablaMatricula;
import vista.utilidades.Utilidades;
import static vista.utilidades.Utilidades.cargarCursosPorCicloEItinerario;

/**
 *
 * @author cristian
 */
public class pnlAsignar extends javax.swing.JPanel {

    private ControlarDocente controlDocente = new ControlarDocente();
    private ModeloTablaDocente modeloDocente = new ModeloTablaDocente();
    private MateriaDao materiad = new MateriaDao();
    private Integer pos = -1;

    /**
     * Creates new form pnlHome
     */
    public pnlAsignar() {
        initComponents();
        cargarCombos();
        cargarTablaDocente();

    }

    private void cargarTablaDocente() {
        modeloDocente.setDatos(controlDocente.listar());
        tblDocentes.setModel(modeloDocente);
        tblDocentes.updateUI();
        actualizarTablaDocentes();
    }

    private void actualizarTablaDocentes() {
        tblDocentes.setModel(modeloDocente);
        tblDocentes.updateUI();
    }

    private void cargarCombos() {
        Utilidades.cargarCriterio(cbxCriterio);
        Utilidades.cargarCiclos(cbxCiclos);

        // Agregar un listener al ComboBox de ciclos
        cbxCiclos.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Cuando se selecciona un ciclo, cargar las materias correspondientes
                    try {
                        String cicloSeleccionado = cbxCiclos.getSelectedItem().toString();
                        Utilidades.cargarMaterias(cicloSeleccionado, cbxMateria);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        try {
            String cicloSeleccionado = cbxCiclos.getSelectedItem().toString();
            Utilidades.cargarMaterias(cicloSeleccionado, cbxMateria);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiar() {

    }

    private void mostrarDocente() throws EmptyException, PositionException {
        int pos = tblDocentes.getSelectedRow();

        if (pos >= controlDocente.getDocentes().size()) {
            throw new EmptyException("El estudiante no existe");
        }

        if (pos >= 0) {
            Docente estudianteSeleccionado = modeloDocente.getDatos().get(pos);
            controlDocente.setDocente(estudianteSeleccionado);
            limpiar();

        } else {
            throw new PositionException("Seleccione una fila (estudiante)");
        }
    }

    public void buscarBinaria() {

        switch (cbxCriterio.getSelectedItem().toString()) {
            case "Nombre":
                modeloDocente.setDatos(controlDocente.buscarPorNombreBinaria(txtBuscar.getText()));
                limpiar();
                actualizarTablaDocentes();
                break;
            case "Cedula":
                modeloDocente.setDatos(controlDocente.buscarPorCedulaBinaria(txtBuscar.getText()));
                limpiar();
                actualizarTablaDocentes();
                break;
        }
    }

    private void guardar() {
        int pos = tblDocentes.getSelectedRow();

        try {
            if (pos >= 0 && pos < controlDocente.getDocentes().size()) {
                Docente docenteSeleccionado = obtenerDocenteSeleccionado(pos);

                if (docenteSeleccionado != null) {
                    Materia materiaSeleccionada = obtenerMateriaSeleccionada();

                    if (materiaSeleccionada != null) {
                        if (docenteSeleccionado.getMaterias() == null) {
                            docenteSeleccionado.setMaterias(new ListaEnlazada<>());
                        }

                        docenteSeleccionado.getMaterias().insertar(materiaSeleccionada);

                        // Imprime la lista de materias del docente después de la inserción
                        System.out.println("Materias del Docente después de la inserción: " + docenteSeleccionado.getMaterias());

                        cargarTablaDocente();
                        limpiar();
                    } else {
                        System.out.println("Materia no encontrada.");
                    }
                } else {
                    System.out.println("Docente no encontrado.");
                }

            } else {
                System.out.println("Posición no válida: " + pos);
            }
        } catch (EmptyException | PositionException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private Materia obtenerMateriaSeleccionada() throws EmptyException, PositionException {
        String nombreMateriaSeleccionada = cbxMateria.getSelectedItem().toString();
        return buscarMateriaPorNombre(nombreMateriaSeleccionada);
    }

    private Materia buscarMateriaPorNombre(String nombre) throws EmptyException, PositionException {
        ListaEnlazada<Materia> materias = materiad.listar();
        for (int i = 0; i < materias.size(); i++) {
            Materia materia = materias.get(i);
            if (materia.getNombre().equals(nombre)) {
                return materia;
            }
        }
        throw new EmptyException("La materia seleccionada no existe");
    }

    private Docente obtenerDocenteSeleccionado(int pos) throws EmptyException, PositionException {
        ListaEnlazada<Docente> docentes = controlDocente.getDocentes();
        if (pos >= 0 && pos < docentes.size()) {
            return docentes.get(pos);
        } else {
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbCriterio = new javax.swing.JLabel();
        cbxCriterio = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDocentes = new javax.swing.JTable();
        btnMatricula = new javax.swing.JButton();
        panelMatri1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbxCiclos = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbxMateria = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda de Docente"));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbCriterio.setFont(new java.awt.Font("Roboto Black", 0, 12)); // NOI18N
        lbCriterio.setText("Buscar por:");

        cbxCriterio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCriterioActionPerformed(evt);
            }
        });

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lbCriterio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
                .addComponent(btnBuscar)
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbCriterio)
                        .addComponent(cbxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblDocentes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblDocentes);

        btnMatricula.setText("Seleccionar Docente");
        btnMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMatriculaActionPerformed(evt);
            }
        });

        panelMatri1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Ciclos");

        cbxCiclos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Materia");

        cbxMateria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelMatri1Layout = new javax.swing.GroupLayout(panelMatri1);
        panelMatri1.setLayout(panelMatri1Layout);
        panelMatri1Layout.setHorizontalGroup(
            panelMatri1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMatri1Layout.createSequentialGroup()
                .addGroup(panelMatri1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMatri1Layout.createSequentialGroup()
                        .addGap(389, 389, 389)
                        .addComponent(jLabel9))
                    .addGroup(panelMatri1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelMatri1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelMatri1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxCiclos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelMatri1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelMatri1Layout.setVerticalGroup(
            panelMatri1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMatri1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMatri1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxCiclos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(panelMatri1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jLabel9))
        );

        btnGuardar.setText("guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(panelMatri1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnMatricula)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnMatricula)
                            .addComponent(btnGuardar))
                        .addContainerGap())
                    .addComponent(panelMatri1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxCriterioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCriterioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxCriterioActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarBinaria();
        limpiar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMatriculaActionPerformed

        try {
            mostrarDocente();

        } catch (EmptyException ex) {
            Logger.getLogger(pnlAsignar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PositionException ex) {
            Logger.getLogger(pnlAsignar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnMatriculaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMatricula;
    private javax.swing.JComboBox<String> cbxCiclos;
    private javax.swing.JComboBox<String> cbxCriterio;
    private javax.swing.JComboBox<String> cbxMateria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbCriterio;
    private javax.swing.JPanel panelMatri1;
    private javax.swing.JTable tblDocentes;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
