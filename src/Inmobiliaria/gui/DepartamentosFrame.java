
package Inmobiliaria.gui;

import Inmobiliaria.modelo.Constantes;
import Inmobiliaria.modelo.Departamento;
import Inmobiliaria.modelo.Proyecto;
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
public class DepartamentosFrame extends JFrame {

    private final Proyecto proyecto;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtCodigo, txtPiso, txtNumDpto, txtArea;
    private JTextField txtDorm, txtBanos, txtPrecio;
    private JComboBox<String> cboTipo;

    public DepartamentosFrame(Proyecto proyecto) {
        this.proyecto = proyecto;
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Departamentos - " + proyecto.getNombre());
        setSize(860, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setLayout(new BorderLayout(10, 10));

        String[] cols = {"Codigo", "Piso", "N Dpto", "Area m2",
                         "Dorm", "Banos", "Tipo", "Precio Base", "Estado"};
        modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(
            e -> cargarSeleccion());
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(860, 200));

        JPanel form = new JPanel(new GridLayout(4, 4, 8, 6));
        form.setBorder(BorderFactory.createTitledBorder(
            "Datos del departamento"));

        txtCodigo  = new JTextField();
        txtPiso    = new JTextField();
        txtNumDpto = new JTextField();
        txtArea    = new JTextField();
        txtDorm    = new JTextField();
        txtBanos   = new JTextField();
        cboTipo = new JComboBox<>(new String[]{
            Constantes.TIPO_FLAT,
            Constantes.TIPO_DUPLEX,
            Constantes.TIPO_PENTHOUSE
        });
        txtPrecio = new JTextField();

        form.add(new JLabel("Codigo:"));       form.add(txtCodigo);
        form.add(new JLabel("Piso:"));         form.add(txtPiso);
        form.add(new JLabel("N Dpto:"));       form.add(txtNumDpto);
        form.add(new JLabel("Area m2:"));      form.add(txtArea);
        form.add(new JLabel("Dormitorios:"));  form.add(txtDorm);
        form.add(new JLabel("Banos:"));        form.add(txtBanos);
        form.add(new JLabel("Tipo:"));         form.add(cboTipo);
        form.add(new JLabel("Precio base S/:")); form.add(txtPrecio);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        JButton btnAgregar   = boton("Agregar",   new Color(30, 130, 80));
        JButton btnModificar = boton("Modificar precio", new Color(30, 80, 160));
        JButton btnEliminar  = boton("Eliminar",  new Color(180, 50, 50));
        JButton btnLimpiar   = boton("Limpiar",   new Color(100, 100, 100));

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
        Departamento[] dptos = proyecto.getDepartamentos();
        for (int i = 0; i < dptos.length; i++) {
            Departamento d = dptos[i];
            modelo.addRow(new Object[]{
                d.getCodigo(), d.getNumeroPiso(), d.getNumeroDpto(),
                d.getArea(), d.getNumDormitorios(), d.getNumBanos(),
                d.getTipo(), d.getPrecioBase(), d.getEstado()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        Departamento d = proyecto.buscarDepartamento(
            (String) modelo.getValueAt(fila, 0));
        if (d == null) return;
        txtCodigo.setText(d.getCodigo());
        txtPiso.setText(String.valueOf(d.getNumeroPiso()));
        txtNumDpto.setText(d.getNumeroDpto());
        txtArea.setText(String.valueOf(d.getArea()));
        txtDorm.setText(String.valueOf(d.getNumDormitorios()));
        txtBanos.setText(String.valueOf(d.getNumBanos()));
        cboTipo.setSelectedItem(d.getTipo());
        txtPrecio.setText(String.valueOf(d.getPrecioBase()));
    }

    private void agregar() {
        try {
            String cod   = txtCodigo.getText().trim();
            int    piso  = Integer.parseInt(txtPiso.getText().trim());
            String ndpto = txtNumDpto.getText().trim();
            double area  = Double.parseDouble(txtArea.getText().trim());
            int    dorm  = Integer.parseInt(txtDorm.getText().trim());
            int    ban   = Integer.parseInt(txtBanos.getText().trim());
            String tipo  = (String) cboTipo.getSelectedItem();
            double prec  = Double.parseDouble(txtPrecio.getText().trim());

            if (cod.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El codigo es obligatorio.");
                return;
            }
            Departamento d = new Departamento(
                cod, piso, ndpto, area, dorm, ban, tipo, prec);
            if (proyecto.agregarDepartamento(d)) {
                JOptionPane.showMessageDialog(this, "Departamento agregado.");
                cargarTabla();
                limpiar();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Verifique que los campos numericos sean validos.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un departamento.");
            return;
        }
        Departamento d = proyecto.buscarDepartamento(
            (String) modelo.getValueAt(fila, 0));
        if (d == null) return;
        try {
            double nuevoPrecio = Double.parseDouble(
                txtPrecio.getText().trim());
            d.setPrecioBase(nuevoPrecio);
            JOptionPane.showMessageDialog(this, "Departamento actualizado.");
            cargarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio invalido.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un departamento.");
            return;
        }
        String cod = (String) modelo.getValueAt(fila, 0);
        int ok = JOptionPane.showConfirmDialog(this,
            "Eliminar el departamento " + cod + "?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            proyecto.eliminarDepartamento(cod);
            cargarTabla();
            limpiar();
        }
    }

    private void limpiar() {
        txtCodigo.setText(""); txtPiso.setText("");
        txtNumDpto.setText(""); txtArea.setText("");
        txtDorm.setText(""); txtBanos.setText("");
        txtPrecio.setText(""); tabla.clearSelection();
    }

    private JButton boton(String t, Color c) {
        JButton b = new JButton(t);
        b.setBackground(c); b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        return b;
    }
}
