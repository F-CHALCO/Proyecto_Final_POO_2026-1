
package Inmobiliaria.gui;

import Inmobiliaria.modelo.Administrador;
import Inmobiliaria.modelo.AsesorVenta;
import Inmobiliaria.modelo.Constantes;
import Inmobiliaria.modelo.Empleado;
import Inmobiliaria.modelo.Gerente;
import Inmobiliaria.servicio.GestorSistema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class EmpleadosFrame extends JFrame {

    private final GestorSistema sistema;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtDni, txtNombres, txtApellidos;
    private JTextField txtTelefono, txtCorreo, txtUsuario;
    private JPasswordField txtContrasena;
    private JComboBox<String> cboRol;

    public EmpleadosFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Gestion de Empleados");
        setSize(820, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(IconoApp.getImagen());

        String[] cols = {"DNI", "Nombres", "Apellidos", "Usuario", "Rol"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(
            e -> cargarSeleccion());
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(820, 200));

        JPanel form = new JPanel(new GridLayout(4, 4, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Datos del empleado"));

        txtDni        = new JTextField();
        txtNombres    = new JTextField();
        txtApellidos  = new JTextField();
        txtTelefono   = new JTextField();
        txtCorreo     = new JTextField();
        txtUsuario    = new JTextField();
        txtContrasena = new JPasswordField();
        cboRol = new JComboBox<>(new String[]{
            Constantes.ROL_ASESOR,
            Constantes.ROL_ADMIN,
            Constantes.ROL_GERENTE
        });

        form.add(new JLabel("DNI:"));        form.add(txtDni);
        form.add(new JLabel("Nombres:"));    form.add(txtNombres);
        form.add(new JLabel("Apellidos:"));  form.add(txtApellidos);
        form.add(new JLabel("Telefono:"));   form.add(txtTelefono);
        form.add(new JLabel("Correo:"));     form.add(txtCorreo);
        form.add(new JLabel("Usuario:"));    form.add(txtUsuario);
        form.add(new JLabel("Contrasena:")); form.add(txtContrasena);
        form.add(new JLabel("Rol:"));        form.add(cboRol);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        JButton btnAgregar   = boton("Agregar",   new Color(30, 130, 80));
        JButton btnModificar = boton("Modificar",  new Color(30, 80, 160));
        JButton btnEliminar  = boton("Eliminar",   new Color(180, 50, 50));
        JButton btnLimpiar   = boton("Limpiar",    new Color(100, 100, 100));

        btnAgregar.addActionListener(e   -> agregarEmpleado());
        btnModificar.addActionListener(e -> modificarEmpleado());
        btnEliminar.addActionListener(e  -> eliminarEmpleado());
        btnLimpiar.addActionListener(e   -> limpiar());

        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        setLayout(new BorderLayout(10, 10));
        add(scroll,  BorderLayout.NORTH);
        add(form,    BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (int i = 0; i < sistema.getEmpleados().length; i++) {
            Empleado e = sistema.getEmpleados()[i];
            modeloTabla.addRow(new Object[]{
                e.getDni(), e.getNombres(), e.getApellidos(),
                e.getUsuario(), e.getRol()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        String dni = (String) modeloTabla.getValueAt(fila, 0);
        Empleado e = sistema.buscarEmpleadoPorDni(dni);
        if (e == null) return;
        txtDni.setText(e.getDni());
        txtNombres.setText(e.getNombres());
        txtApellidos.setText(e.getApellidos());
        txtTelefono.setText(e.getTelefono());
        txtCorreo.setText(e.getCorreo());
        txtUsuario.setText(e.getUsuario());
        cboRol.setSelectedItem(e.getRol());
    }

    private void agregarEmpleado() {
        String dni   = txtDni.getText().trim();
        String nom   = txtNombres.getText().trim();
        String ape   = txtApellidos.getText().trim();
        String tel   = txtTelefono.getText().trim();
        String cor   = txtCorreo.getText().trim();
        String usu   = txtUsuario.getText().trim();
        String pass  = new String(txtContrasena.getPassword()).trim();
        String rol   = (String) cboRol.getSelectedItem();

        if (dni.isEmpty() || nom.isEmpty() || usu.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "DNI, nombres, usuario y contrasena son obligatorios.");
            return;
        }

        Empleado nuevo;
        if (rol.equals(Constantes.ROL_ADMIN)) {
            nuevo = new Administrador(1, usu, pass, rol,
                                      dni, nom, ape, tel, cor);
        } else if (rol.equals(Constantes.ROL_GERENTE)) {
            nuevo = new Gerente("General", usu, pass, rol,
                                dni, nom, ape, tel, cor);
        } else {
            nuevo = new AsesorVenta(0, usu, pass, rol,
                                    dni, nom, ape, tel, cor);
        }

        if (sistema.agregarEmpleado(nuevo)) {
            JOptionPane.showMessageDialog(this, "Empleado registrado.");
            cargarTabla();
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this,
                "Ya existe un empleado con ese DNI.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarEmpleado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado.");
            return;
        }
        String dni  = (String) modeloTabla.getValueAt(fila, 0);
        String tel  = txtTelefono.getText().trim();
        String cor  = txtCorreo.getText().trim();
        String pass = new String(txtContrasena.getPassword()).trim();

        boolean ok = sistema.modificarEmpleado(dni, tel, cor, pass);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Empleado actualizado.");
            cargarTabla();
            limpiar();
        }
    }

    private void eliminarEmpleado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado.");
            return;
        }
        String dni = (String) modeloTabla.getValueAt(fila, 0);

        // Validacion: no puede eliminarse a si mismo
        if (dni.equals(sistema.getEmpleadoLogueado().getDni())) {
            JOptionPane.showMessageDialog(this,
                "No puede eliminarse a usted mismo.",
                "Operacion no permitida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Eliminar al empleado con DNI " + dni + "?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            sistema.eliminarEmpleado(dni);
            JOptionPane.showMessageDialog(this, "Empleado eliminado.");
            cargarTabla();
            limpiar();
        }
    }

    private void limpiar() {
        txtDni.setText(""); txtNombres.setText(""); txtApellidos.setText("");
        txtTelefono.setText(""); txtCorreo.setText("");
        txtUsuario.setText(""); txtContrasena.setText("");
        tabla.clearSelection();
    }

    private JButton boton(String t, Color c) {
        JButton b = new JButton(t);
        b.setBackground(c); b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        return b;
    }
}
