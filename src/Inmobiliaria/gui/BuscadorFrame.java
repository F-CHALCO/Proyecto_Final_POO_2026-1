
package Inmobiliaria.gui;

import Inmobiliaria.modelo.BuscadorDepartamentos;
import Inmobiliaria.modelo.Constantes;
import Inmobiliaria.modelo.Departamento;
import Inmobiliaria.modelo.Proyecto;
import Inmobiliaria.servicio.GestorSistema;
import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class BuscadorFrame extends JFrame {

    private final GestorSistema sistema;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cboOrden;
    private JComboBox<String> cboTipo;
    private JTextField txtPrecioMin, txtPrecioMax, txtDormitorios;
    private JLabel lblResultados;

    public BuscadorFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
        cargarTodos();
    }

    private void initComponents() {
        setTitle("Buscador y Ordenador de Departamentos (R13)");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setLayout(new BorderLayout(10, 10));

        JPanel panelFiltros = new JPanel(new GridLayout(3, 4, 8, 6));
        panelFiltros.setBorder(BorderFactory.createTitledBorder(
            "Filtros y ordenamiento"));

        cboOrden = new JComboBox<>(new String[]{
            "Sin orden",
            "Precio: menor a mayor",
            "Precio: mayor a menor",
            "Area: menor a mayor",
            "Piso: menor a mayor"
        });
        cboTipo = new JComboBox<>(new String[]{
            "Todos",
            Constantes.TIPO_FLAT,
            Constantes.TIPO_DUPLEX,
            Constantes.TIPO_PENTHOUSE
        });
        txtPrecioMin   = new JTextField("0");
        txtPrecioMax   = new JTextField("9999999");
        txtDormitorios = new JTextField("");

        panelFiltros.add(new JLabel("Ordenar por:")); panelFiltros.add(cboOrden);
        panelFiltros.add(new JLabel("Tipo:"));        panelFiltros.add(cboTipo);
        panelFiltros.add(new JLabel("Precio min S/:"));panelFiltros.add(txtPrecioMin);
        panelFiltros.add(new JLabel("Precio max S/:"));panelFiltros.add(txtPrecioMax);
        panelFiltros.add(new JLabel("Dormitorios (vacio=todos):"));
        panelFiltros.add(txtDormitorios);

        JButton btnBuscar  = boton("Buscar / Ordenar", new Color(30, 80, 160));
        JButton btnVerTodos = boton("Ver todos",        new Color(100, 100, 100));
        btnBuscar.addActionListener(e  -> buscarYOrdenar());
        btnVerTodos.addActionListener(e -> cargarTodos());
        panelFiltros.add(btnBuscar);
        panelFiltros.add(btnVerTodos);

        String[] cols = {"Codigo", "Piso", "Tipo", "Area m2",
                         "Dorm", "Banos", "Estado", "Precio S/"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(22);

        lblResultados = new JLabel("  Mostrando todos los departamentos");
        lblResultados.setFont(new Font("Arial", Font.ITALIC, 12));

        add(panelFiltros,              BorderLayout.NORTH);
        add(new JScrollPane(tabla),    BorderLayout.CENTER);
        add(lblResultados,             BorderLayout.SOUTH);
    }

    private void cargarTodos() {
        Departamento[] todos = obtenerTodos();
        mostrarEnTabla(todos,
            "Mostrando todos: " + todos.length + " departamentos");
    }

    private void buscarYOrdenar() {
        try {
            Departamento[] todos = obtenerTodos();
            BuscadorDepartamentos buscador =
                new BuscadorDepartamentos(todos, todos.length);

            
            String tipoSel = (String) cboTipo.getSelectedItem();
            Departamento[] filtrados;
            if (tipoSel.equals("Todos")) {
                filtrados = todos;
            } else {
                filtrados = buscador.buscarPorTipo(tipoSel);
                buscador  = new BuscadorDepartamentos(
                    filtrados, filtrados.length);
            }

            
            double min = Double.parseDouble(txtPrecioMin.getText().trim());
            double max = Double.parseDouble(txtPrecioMax.getText().trim());
            filtrados = buscador.buscarPorRangoPrecio(min, max);
            buscador  = new BuscadorDepartamentos(
                filtrados, filtrados.length);

            
            String dormStr = txtDormitorios.getText().trim();
            if (!dormStr.isEmpty()) {
                int dorm  = Integer.parseInt(dormStr);
                filtrados = buscador.buscarPorDormitorios(dorm);
                buscador  = new BuscadorDepartamentos(
                    filtrados, filtrados.length);
            }

           
            Departamento[] resultado;
            int orden = cboOrden.getSelectedIndex();
            if (orden == 1) {
                resultado = buscador.ordenarPorPrecioAsc();
            } else if (orden == 2) {
                resultado = buscador.ordenarPorPrecioDesc();
            } else if (orden == 3) {
                resultado = buscador.ordenarPorAreaAsc();
            } else if (orden == 4) {
                resultado = buscador.ordenarPorPisoAsc();
            } else {
                resultado = filtrados;
            }

            String info = "Resultados: " + resultado.length
                        + " departamentos";
            if (resultado.length > 0) {
                BuscadorDepartamentos stats =
                    new BuscadorDepartamentos(resultado, resultado.length);
                info += "  |  Mas barato: S/ "
                      + stats.getMasBarato().getPrecioTotal()
                      + "  |  Promedio: S/ "
                      + String.format("%.0f", stats.getPrecioPromedio());
            }
            mostrarEnTabla(resultado, info);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Verifique que precio y dormitorios sean numeros validos.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarEnTabla(Departamento[] dptos, String info) {
        modeloTabla.setRowCount(0);
        for (int i = 0; i < dptos.length; i++) {
            Departamento d = dptos[i];
            modeloTabla.addRow(new Object[]{
                d.getCodigo(), d.getNumeroPiso(), d.getTipo(),
                d.getArea(), d.getNumDormitorios(), d.getNumBanos(),
                d.getEstado(), d.getPrecioTotal()
            });
        }
        lblResultados.setText("  " + info);
    }

    private Departamento[] obtenerTodos() {
        int total = 0;
        Proyecto[] proyectos = sistema.getProyectos();
        for (int i = 0; i < proyectos.length; i++) {
            total = total + proyectos[i].getCantDepartamentos();
        }
        Departamento[] todos = new Departamento[total];
        int idx = 0;
        for (int i = 0; i < proyectos.length; i++) {
            Departamento[] dptos = proyectos[i].getDepartamentos();
            for (int j = 0; j < dptos.length; j++) {
                todos[idx] = dptos[j];
                idx++;
            }
        }
        return todos;
    }

    private JButton boton(String t, Color c) {
        JButton b = new JButton(t);
        b.setBackground(c); b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        return b;
    }
}
