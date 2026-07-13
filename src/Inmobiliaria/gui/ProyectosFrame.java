
package Inmobiliaria.gui;

import Inmobiliaria.modelo.Constantes;
import Inmobiliaria.modelo.Proyecto;
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
public class ProyectosFrame extends JFrame {

    private final GestorSistema sistema;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtDireccion, txtDistrito;
    private JTextField txtPisos, txtFechaInicio, txtFechaEntrega;
    private JComboBox<String> cboEstado;

    public ProyectosFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Gestion de Proyectos");
        setSize(900, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setLayout(new BorderLayout(10, 10));

        String[] cols = {"Nombre", "Direccion", "Distrito",
                         "Pisos", "Estado", "% Vendido"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(
            e -> cargarSeleccion());
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(900, 180));

        JPanel form = new JPanel(new GridLayout(4, 4, 8, 6));
        form.setBorder(BorderFactory.createTitledBorder("Datos del proyecto"));

        txtNombre       = new JTextField();
        txtDireccion    = new JTextField();
        txtDistrito     = new JTextField();
        txtPisos        = new JTextField();
        txtFechaInicio  = new JTextField("2025-01-01");
        txtFechaEntrega = new JTextField("2026-12-31");
        cboEstado = new JComboBox<>(new String[]{
            Constantes.PROY_EN_PLANOS,
            Constantes.PROY_EN_CONSTRUCCION,
            Constantes.PROY_TERMINADO,
            Constantes.PROY_ENTREGADO
        });

        form.add(new JLabel("Nombre:"));         form.add(txtNombre);
        form.add(new JLabel("Direccion:"));      form.add(txtDireccion);
        form.add(new JLabel("Distrito:"));       form.add(txtDistrito);
        form.add(new JLabel("N Pisos:"));        form.add(txtPisos);
        form.add(new JLabel("Inicio obra:"));    form.add(txtFechaInicio);
        form.add(new JLabel("Entrega est.:"));   form.add(txtFechaEntrega);
        form.add(new JLabel("Estado:"));         form.add(cboEstado);
        form.add(new JLabel());                  form.add(new JLabel());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        JButton btnAgregar   = boton("Agregar",    new Color(30, 130, 80));
        JButton btnModificar = boton("Modificar",   new Color(30, 80, 160));
        JButton btnEliminar  = boton("Eliminar",    new Color(180, 50, 50));
        JButton btnDeptos    = boton("Ver Departamentos", new Color(100, 60, 160));
        JButton btnLimpiar   = boton("Limpiar",     new Color(100, 100, 100));

        btnAgregar.addActionListener(e   -> agregarProyecto());
        btnModificar.addActionListener(e -> modificarProyecto());
        btnEliminar.addActionListener(e  -> eliminarProyecto());
        btnDeptos.addActionListener(e    -> abrirDepartamentos());
        btnLimpiar.addActionListener(e   -> limpiar());

        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnDeptos);
        botones.add(btnLimpiar);

        add(scroll,  BorderLayout.NORTH);
        add(form,    BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        Proyecto[] proyectos = sistema.getProyectos();
        for (int i = 0; i < proyectos.length; i++) {
            Proyecto p = proyectos[i];
            modeloTabla.addRow(new Object[]{
                p.getNombre(), p.getDireccion(), p.getDistrito(),
                p.getNumPisos(), p.getEstado(),
                String.format("%.1f%%", p.getPorcentajeVentas())
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        Proyecto p = sistema.buscarProyecto(
            (String) modeloTabla.getValueAt(fila, 0));
        if (p == null) return;
        txtNombre.setText(p.getNombre());
        txtDireccion.setText(p.getDireccion());
        txtDistrito.setText(p.getDistrito());
        txtPisos.setText(String.valueOf(p.getNumPisos()));
        txtFechaInicio.setText(p.getFechaInicio());
        txtFechaEntrega.setText(p.getFechaEntrega());
        cboEstado.setSelectedItem(p.getEstado());
    }

    private void agregarProyecto() {
        try {
            String nombre = txtNombre.getText().trim();
            String dir    = txtDireccion.getText().trim();
            String dist   = txtDistrito.getText().trim();
            int    pisos  = Integer.parseInt(txtPisos.getText().trim());
            String ini    = txtFechaInicio.getText().trim();
            String ent    = txtFechaEntrega.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El nombre es obligatorio.");
                return;
            }
            Proyecto p = new Proyecto(nombre, dir, dist, pisos, ini, ent);
            p.setEstado((String) cboEstado.getSelectedItem());

            if (sistema.agregarProyecto(p)) {
                JOptionPane.showMessageDialog(this, "Proyecto registrado.");
                cargarTabla();
                limpiar();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "El numero de pisos debe ser un valor entero.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarProyecto() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto.");
            return;
        }
        String nombre = (String) modeloTabla.getValueAt(fila, 0);
        boolean ok = sistema.modificarProyecto(
            nombre,
            txtDireccion.getText().trim(),
            txtDistrito.getText().trim(),
            (String) cboEstado.getSelectedItem()
        );
        if (ok) {
            JOptionPane.showMessageDialog(this, "Proyecto actualizado.");
            cargarTabla();
        }
    }

    private void eliminarProyecto() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto.");
            return;
        }
        String nombre = (String) modeloTabla.getValueAt(fila, 0);
        int ok = JOptionPane.showConfirmDialog(this,
            "Eliminar el proyecto '" + nombre + "'?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            sistema.eliminarProyecto(nombre);
            cargarTabla();
            limpiar();
        }
    }

    private void abrirDepartamentos() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un proyecto primero.");
            return;
        }
        Proyecto p = sistema.buscarProyecto(
            (String) modeloTabla.getValueAt(fila, 0));
        if (p != null) new DepartamentosFrame(p).setVisible(true);
    }

    private void limpiar() {
        txtNombre.setText(""); txtDireccion.setText("");
        txtDistrito.setText(""); txtPisos.setText("");
        txtFechaInicio.setText("2025-01-01");
        txtFechaEntrega.setText("2026-12-31");
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
