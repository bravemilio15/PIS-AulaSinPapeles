/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.Administracion;

import controlador.ControlarEstudiante;
import controlador.dao.CicloDAO;
import controlador.dao.CuentaDao;
import controlador.dao.EstudianteDao;
import controlador.dao.ParaleloDAO;
import controlador.dao.RolDao;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.awt.Component;
import java.awt.PopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import modelo.Ciclo;
import modelo.Estudiante;
import modelo.Paralelo;
import modelo.Rol;
import modelo.tabla.ModeloTablaEstudiante;
import org.jdatepicker.impl.JDatePickerImpl;
import vista.utilidades.Utilidades;

/**
 *
 * @author cristian
 */
public class pnlEstudiante extends javax.swing.JPanel {

    private ModeloTablaEstudiante modelo = new ModeloTablaEstudiante();
    private ControlarEstudiante control = new ControlarEstudiante();
    private CicloDAO cd = new CicloDAO();
    private ParaleloDAO pd = new ParaleloDAO();
    private CuentaDao cuentad = new CuentaDao();
    private RolDao rld = new RolDao();
    private EstudianteDao ed = new EstudianteDao();
    private int pos = -1;

    public pnlEstudiante() {
        initComponents();
        initDatePickerForNacimiento();
        cargarTabla();
        cargarCombos();
    }

    private void cargarTabla() {
        modelo.setDatos(control.getEstudiantes());
        tblUsuarios.setModel(modelo);
        tblUsuarios.updateUI();
    }

    private void actualizarTabla() {
        tblUsuarios.setModel(modelo);
        tblUsuarios.updateUI();
    }

    private void cargarCombos() {
        Utilidades.cargarGenero(cbxGenero);
        Utilidades.cargarEstado(cbxEstado);
        Utilidades.cargarCiclos(cbxCiclo);
        Utilidades.cargarParalelo(cbxParalelo);
        Utilidades.cargarModalidad(cbxModalidad);
        Utilidades.cargarRolEstudiante(cbxRol);
    }

    private void limpiar() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");
        txtCelular.setText("");
        txtNacimiento.setText("");
        txtPersonal.setText("");
        txtEdad.setText("");
        txtInstitucional.setText("");
        txtNacimiento.setText("");
    }

    private void guardar() throws IOException, EmptyException, PositionException {
        if (validar()) {
            String cicloSeleccionado = cbxCiclo.getSelectedItem().toString();
            Ciclo ciclo = cd.obtenerCicloPorNombre(cicloSeleccionado);
            String modalidadSeleccionada = cbxModalidad.getSelectedItem().toString();

            String paraleloSeleccionado = cbxParalelo.getSelectedItem().toString();
            Paralelo paralelo = pd.obtenerParaleloPorNombre(paraleloSeleccionado);

            // Dividir nombres y apellidos
            String[] nombres = txtNombre.getText().split("\\s+");
            String[] apellidos = txtApellido.getText().split("\\s+");

            // Configurar los valores en el objeto Estudiante
            control.getEstudiante().setPrimer_nombre(nombres.length > 0 ? nombres[0] : "");
            control.getEstudiante().setSegundo_nombre(nombres.length > 1 ? nombres[1] : "");
            control.getEstudiante().setPrimer_apellido(apellidos.length > 0 ? apellidos[0] : "");
            control.getEstudiante().setSegundo_apellido(apellidos.length > 1 ? apellidos[1] : "");

            control.getEstudiante().setCedula(txtCedula.getText());
            control.getEstudiante().setCelular(txtCelular.getText());
            control.getEstudiante().setNacimiento(txtNacimiento.getText());
            control.getEstudiante().setGenero((String) cbxGenero.getSelectedItem());

            Integer edad = Utilidades.calcularEdad(txtNacimiento.getText());
            control.getEstudiante().setEdad(edad);

            control.getEstudiante().setCorreoIns(txtInstitucional.getText());
            control.getEstudiante().setCorreoPer(txtPersonal.getText());
            control.getEstudiante().setCiclo(ciclo);
            control.getEstudiante().setParalelo(paralelo);
            control.getEstudiante().setModalidad(modalidadSeleccionada);

            String rolSeleccionado = cbxRol.getSelectedItem().toString();
            Rol rol = rld.obtenerRolPorNombre(rolSeleccionado);
            control.getEstudiante().setRol(rol);

            if (control.registrar() == true) {
                Integer id_Estudiante = ed.obtenerId_EstudiantePorCorreoCedula(txtInstitucional.getText(),
                        txtCedula.getText());

                cuentad.getCuenta().setNombre_usuario(txtInstitucional.getText());
                cuentad.getCuenta().setContra(txtCedula.getText());
                cuentad.getCuenta().setId_estudiante(id_Estudiante);

                cuentad.guardar();
            }

            actualizarTabla();
            limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos antes de guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarUsuario() {
        int fila = tblUsuarios.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Estudiante usuarioAModificar = modelo.getDatos().get(fila);

                control.getEstudiante().setId(usuarioAModificar.getId());

                // Dividir nombres y apellidos
                String[] nombres = txtNombre.getText().split("\\s+");
                String[] apellidos = txtApellido.getText().split("\\s+");

                // Configurar los valores en el objeto Estudiante
                control.getEstudiante().setPrimer_nombre(nombres.length > 0 ? nombres[0] : "");
                control.getEstudiante().setSegundo_nombre(nombres.length > 1 ? nombres[1] : "");
                control.getEstudiante().setPrimer_apellido(apellidos.length > 0 ? apellidos[0] : "");
                control.getEstudiante().setSegundo_apellido(apellidos.length > 1 ? apellidos[1] : "");

                control.getEstudiante().setCedula(txtCedula.getText());
                control.getEstudiante().setCelular(txtCelular.getText());

                String fechaNacimiento = txtNacimiento.getText();
                if (!fechaNacimiento.isEmpty()) {
                    control.getEstudiante().setNacimiento(fechaNacimiento);

                    control.getEstudiante().setGenero((String) cbxGenero.getSelectedItem());
                    Integer edad = Utilidades.calcularEdad(txtNacimiento.getText());
                    control.getEstudiante().setEdad(edad);
                    control.getEstudiante().setCorreoIns(txtInstitucional.getText());
                    control.getEstudiante().setCorreoPer(txtPersonal.getText());
                    control.getEstudiante().setCiclo(usuarioAModificar.getCiclo());
                    control.getEstudiante().setParalelo(usuarioAModificar.getParalelo());
                    control.getEstudiante().setModalidad(cbxModalidad.getSelectedItem().toString());

                    control.getEstudianteDao().modificar(pos);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese una fecha de nacimiento válida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Boolean validar() {
        return (!txtNombre.getText().trim().isEmpty()
                && !txtApellido.getText().trim().isEmpty()
                && !txtCedula.getText().trim().isEmpty()
                && !txtCelular.getText().trim().isEmpty()
                && !txtNacimiento.getText().trim().isEmpty()
                && !txtPersonal.getText().trim().isEmpty()
                && !txtInstitucional.getText().trim().isEmpty()
                && cbxEstado.getSelectedItem() != null
                && !cbxEstado.getSelectedItem().toString().isEmpty())
                && Utilidades.validarCedula(txtCedula.getText())
                && Utilidades.verificarCelular(txtCelular.getText())
                && Utilidades.validarCorreoInstitucional(txtInstitucional.getText())
                && Utilidades.validarCorreoPersonal(txtPersonal.getText())
                && Utilidades.validarEdad(txtEdad.getText())
                && Utilidades.validarNombresApellidos(txtNombre.getText(), txtApellido.getText());

    }

    private void initDatePickerForNacimiento() {
        JDatePickerImpl datePicker = Utilidades.createDatePicker();

        datePicker.addActionListener(e -> {
            Date selectedDate = (Date) datePicker.getModel().getValue();
            txtNacimiento.setText(Utilidades.formatDateString(selectedDate, "yyyy-MM-dd"));
            actualizarEdad();
        });

        txtNacimiento.setEditable(false);
        txtEdad.setEditable(false);

        txtNacimiento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Utilidades.showDatePickerPopup(txtNacimiento, datePicker);
                }
            }
        });
    }

    private void actualizarEdad() {
        Integer edad = Utilidades.calcularEdad(txtNacimiento.getText());
        txtEdad.setText(edad.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtInstitucional = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPersonal = new javax.swing.JTextField();
        txtNacimiento = new javax.swing.JTextField();
        cbxCiclo = new javax.swing.JComboBox<>();
        lblNombre2 = new javax.swing.JLabel();
        cbxGenero = new javax.swing.JComboBox<>();
        cbxEstado = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbxParalelo = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbxModalidad = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbxRol = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Personales"));

        lblNombre.setFont(new java.awt.Font("Roboto Black", 0, 12)); // NOI18N
        lblNombre.setText("NOMBRES");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        lblPrecio.setFont(new java.awt.Font("Roboto Black", 0, 12)); // NOI18N
        lblPrecio.setText("APELLIDOS");

        jLabel2.setText("CEDULA");

        jLabel3.setText("CELULAR");

        jLabel4.setText("ESTADO");

        jLabel5.setText("F.NACIMIENTO");

        jLabel6.setText("EDAD");

        jLabel7.setText("Correo Institucional");

        jLabel8.setText("Correo Personal");

        cbxCiclo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblNombre2.setFont(new java.awt.Font("Roboto Black", 0, 12)); // NOI18N
        lblNombre2.setText("GENERO");

        cbxGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoActionPerformed(evt);
            }
        });

        jLabel9.setText("PARALELO");

        cbxParalelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxParalelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxParaleloActionPerformed(evt);
            }
        });

        jLabel10.setText("CICLO");

        jLabel1.setText("MODALIDAD");

        cbxModalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("ROL");

        cbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lblNombre)
                                .addGap(18, 18, 18)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(lblPrecio))
                        .addGap(198, 198, 198)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtInstitucional, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(106, 106, 106))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(cbxModalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lblNombre2)
                                .addGap(18, 18, 18)
                                .addComponent(cbxGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtEdad)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNombre)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblPrecio))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNombre2)
                        .addComponent(cbxGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(cbxParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInstitucional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel1)
                    .addComponent(cbxModalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(cbxRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Estudiantes"));

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblUsuarios);

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setText("Registrar Estudiante");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModificar)
                        .addGap(29, 29, 29)
                        .addComponent(btnGuardar)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 853, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            try {
                guardar();
            } catch (EmptyException ex) {
                Logger.getLogger(pnlEstudiante.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PositionException ex) {
                Logger.getLogger(pnlEstudiante.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(pnlEstudiante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarUsuario();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void cbxParaleloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxParaleloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxParaleloActionPerformed

    private void cbxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxEstadoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cbxCiclo;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxGenero;
    private javax.swing.JComboBox<String> cbxModalidad;
    private javax.swing.JComboBox<String> cbxParalelo;
    private javax.swing.JComboBox<String> cbxRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombre2;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtInstitucional;
    private javax.swing.JTextField txtNacimiento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPersonal;
    // End of variables declaration//GEN-END:variables
}
