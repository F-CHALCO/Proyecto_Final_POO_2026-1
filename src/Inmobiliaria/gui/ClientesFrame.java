
package Inmobiliaria.gui;

import Inmobiliaria.modelo.Cliente;
import Inmobiliaria.servicio.GestorSistema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
public class ClientesFrame extends JFrame {

    private final GestorSistema sistema;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtDni, txtNombres, txtApellidos, txtTelefono;
    private JTextField txtCorreo, txtFechaNac, txtEstadoCivil;
    private JTextField txtOcupacion, txtIngresos;

    public ClientesFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Gestion de Clientes Potenciales");
        setSize(860, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setLayout(new BorderLayout(10, 10));

        String[] cols = {"DNI", "Nombres", "Apellidos",
                         "Telefono", "Correo", "Ocupacion", "Ingresos S/"};
        modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(
            e -> cargarSeleccion());
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(860, 180));

        JPanel form = new JPanel(new GridLayout(5, 4, 8, 6));
        form.setBorder(BorderFactory.createTitledBorder("Datos del cliente"));

        txtDni         = new JTextField();
        txtNombres     = new JTextField();
        txtApellidos   = new JTextField();
        txtTelefono    = new JTextField();
        txtCorreo      = new JTextField();
        txtFechaNac    = new JTextField("1990-01-01");
        txtEstadoCivil = new JTextField();
        txtOcupacion   = new JTextField();
        txtIngresos    = new JTextField();

        form.add(new JLabel("DNI:"));          form.add(txtDni);
        form.add(new JLabel("Nombres:"));      form.add(txtNombres);
        form.add(new JLabel("Apellidos:"));    form.add(txtApellidos);
        form.add(new JLabel("Telefono:"));     form.add(txtTelefono);
        form.add(new JLabel("Correo:"));       form.add(txtCorreo);
        form.add(new JLabel("Fecha nac.:"));   form.add(txtFechaNac);
        form.add(new JLabel("Estado civil:")); form.add(txtEstadoCivil);
        form.add(new JLabel("Ocupacion:"));    form.add(txtOcupacion);
        form.add(new JLabel("Ingresos S/:"));  form.add(txtIngresos);
        form.add(new JLabel()); form.add(new JLabel());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        JButton btnAgregar   = boton("Agregar",   new Color(30, 130, 80));
        JButton btnModificar = boton("Modificar",  new Color(30, 80, 160));
        JButton btnEliminar  = boton("Eliminar",   new Color(180, 50, 50));
        JButton btnLimpiar   = boton("Limpiar",    new Color(100, 100, 100));

        btnAgregar.addActionListener(e   -> agregar());
        btnModificar.addActionListener(e -> modificar());
        btnEliminar.addActionListener(e  -> eliminar());
        btnLimpiar.addActionListener(e   -> limpiar());

        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        add(scroll,  BorderLayout.NORTH);
        add(form,    BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        Cliente[] clientes = sistema.getClientes();
        for (int i = 0; i < clientes.length; i++) {
            Cliente c = clientes[i];
            modelo.addRow(new Object[]{
                c.getDni(), c.getNombres(), c.getApellidos(),
                c.getTelefono(), c.getCorreo(),
                c.getOcupacion(), c.getIngresosMensuales()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        Cliente c = sistema.buscarClientePorDni(
            (String) modelo.getValueAt(fila, 0));
        if (c == null) return;
        txtDni.setText(c.getDni());
        txtNombres.setText(c.getNombres());
        txtApellidos.setText(c.getApellidos());
        txtTelefono.setText(c.getTelefono());
        txtCorreo.setText(c.getCorreo());
        txtFechaNac.setText(c.getFechaNacimiento());
        txtEstadoCivil.setText(c.getEstadoCivil());
        txtOcupacion.setText(c.getOcupacion());
        txtIngresos.setText(String.valueOf(c.getIngresosMensuales()));
    }

    private void agregar() {
        try {
            String dni    = txtDni.getText().trim();
            String nom    = txtNombres.getText().trim();
            String ape    = txtApellidos.getText().trim();
            String tel    = txtTelefono.getText().trim();
            String cor    = txtCorreo.getText().trim();
            String fn     = txtFechaNac.getText().trim();
            String ecivil = txtEstadoCivil.getText().trim();
            String ocup   = txtOcupacion.getText().trim();
            double ingr   = Double.parseDouble(txtIngresos.getText().trim());

            if (dni.isEmpty() || nom.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "DNI y nombres son obligatorios.");
                return;
            }
            Cliente c = new Cliente(fn, ecivil, ocup, ingr, 0,
                                    dni, nom, ape, tel, cor);
            if (sistema.agregarCliente(c)) {
                JOptionPane.showMessageDialog(this,
                    "Cliente registrado.");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Ya existe un cliente con ese DNI.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Los ingresos deben ser un numero valido.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
            return;
        }
        String dni = (String) modelo.getValueAt(fila, 0);
        try {
            double ingr = Double.parseDouble(txtIngresos.getText().trim());
            boolean ok = sistema.modificarCliente(
                dni,
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim(),
                txtOcupacion.getText().trim(),
                ingr
            );
            if (ok) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado.");
                cargarTabla();
                limpiar();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Los ingresos deben ser un numero valido.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
            return;
        }
        String dni = (String) modelo.getValueAt(fila, 0);
        int ok = JOptionPane.showConfirmDialog(this,
            "Eliminar al cliente con DNI " + dni + "?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            sistema.eliminarCliente(dni);
            cargarTabla();
            limpiar();
        }
    }

    private void limpiar() {
        txtDni.setText(""); txtNombres.setText(""); txtApellidos.setText("");
        txtTelefono.setText(""); txtCorreo.setText("");
        txtFechaNac.setText("1990-01-01"); txtEstadoCivil.setText("");
        txtOcupacion.setText(""); txtIngresos.setText("");
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
