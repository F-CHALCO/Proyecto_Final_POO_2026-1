
package Inmobiliaria.gui;

import Inmobiliaria.modelo.Acabado;
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
public class AcabadosFrame extends JFrame {

    private final GestorSistema sistema;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtNombre, txtDescripcion, txtPrecio, txtCategoria;

    public AcabadosFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Gestion de Acabados y Opcionales");
        setSize(700, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setLayout(new BorderLayout(10, 10));

        String[] cols = {"Nombre", "Descripcion", "Categoria", "Precio S/"};
        modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(
            e -> cargarSeleccion());
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(700, 180));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Datos del acabado"));

        txtNombre      = new JTextField();
        txtDescripcion = new JTextField();
        txtPrecio      = new JTextField();
        txtCategoria   = new JTextField();

        form.add(new JLabel("Nombre:"));      form.add(txtNombre);
        form.add(new JLabel("Descripcion:")); form.add(txtDescripcion);
        form.add(new JLabel("Categoria:"));   form.add(txtCategoria);
        form.add(new JLabel("Precio S/:"));   form.add(txtPrecio);

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
        Acabado[] acabados = sistema.getAcabados();
        for (int i = 0; i < acabados.length; i++) {
            Acabado a = acabados[i];
            modelo.addRow(new Object[]{
                a.getNombre(), a.getDescripcion(),
                a.getCategoria(), a.getPrecio()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        txtNombre.setText((String) modelo.getValueAt(fila, 0));
        txtDescripcion.setText((String) modelo.getValueAt(fila, 1));
        txtCategoria.setText((String) modelo.getValueAt(fila, 2));
        txtPrecio.setText(String.valueOf(modelo.getValueAt(fila, 3)));
    }

    private void agregar() {
        try {
            String nombre = txtNombre.getText().trim();
            String desc   = txtDescripcion.getText().trim();
            String cat    = txtCategoria.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El nombre es obligatorio.");
                return;
            }
            sistema.agregarAcabado(new Acabado(nombre, desc, precio, cat));
            JOptionPane.showMessageDialog(this, "Acabado registrado.");
            cargarTabla();
            limpiar();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "El precio debe ser un numero valido.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un acabado.");
            return;
        }
        String nombre = (String) modelo.getValueAt(fila, 0);
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            boolean ok = sistema.modificarAcabado(
                nombre,
                txtDescripcion.getText().trim(),
                precio,
                txtCategoria.getText().trim()
            );
            if (ok) {
                JOptionPane.showMessageDialog(this, "Acabado actualizado.");
                cargarTabla();
                limpiar();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio invalido.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un acabado.");
            return;
        }
        String nombre = (String) modelo.getValueAt(fila, 0);
        int ok = JOptionPane.showConfirmDialog(this,
            "Eliminar el acabado '" + nombre + "'?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            sistema.eliminarAcabado(nombre);
            cargarTabla();
            limpiar();
        }
    }

    private void limpiar() {
        txtNombre.setText(""); txtDescripcion.setText("");
        txtCategoria.setText(""); txtPrecio.setText("");
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
