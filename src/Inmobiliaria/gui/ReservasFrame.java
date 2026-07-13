
package Inmobiliaria.gui;

import Inmobiliaria.modelo.Cliente;
import Inmobiliaria.modelo.Departamento;
import Inmobiliaria.modelo.Proyecto;
import Inmobiliaria.modelo.Reserva;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class ReservasFrame extends JFrame {

    private final GestorSistema sistema;
    private JComboBox<String> cboDni;
    private JComboBox<String> cboCodigo;
    private JTextField txtMonto, txtVigencia, txtFechaHoy;
    private JTextArea txtResultado;

    public ReservasFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Reserva / Separacion");
        setSize(520, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder(
            "Datos de la reserva"));
        form.setBackground(Color.WHITE);

        cboDni    = new JComboBox<>();
        cboCodigo = new JComboBox<>();
        txtMonto    = new JTextField("5000.00");
        txtVigencia = new JTextField("2026-09-01");
        txtFechaHoy = new JTextField("2026-07-01");

        Cliente[] clientes = sistema.getClientes();
        for (int i = 0; i < clientes.length; i++) {
            cboDni.addItem(clientes[i].getDni()
                + " - " + clientes[i].getNombres());
        }

        Proyecto[] proyectos = sistema.getProyectos();
        for (int i = 0; i < proyectos.length; i++) {
            Departamento[] dptos =
                proyectos[i].getDepartamentosDisponibles();
            for (int j = 0; j < dptos.length; j++) {
                cboCodigo.addItem(dptos[j].getCodigo()
                    + " | " + proyectos[i].getNombre()
                    + " | S/" + dptos[j].getPrecioBase());
            }
        }

        form.add(new JLabel("Cliente (DNI):")); form.add(cboDni);
        form.add(new JLabel("Departamento disponible:")); form.add(cboCodigo);
        form.add(new JLabel("Monto separacion S/:")); form.add(txtMonto);
        form.add(new JLabel("Vigencia hasta:")); form.add(txtVigencia);
        form.add(new JLabel("Fecha de hoy:")); form.add(txtFechaHoy);

        JButton btnReservar = boton("Registrar Reserva",
                                     new Color(30, 80, 160));
        btnReservar.addActionListener(e -> registrar());
        form.add(new JLabel()); form.add(btnReservar);

        txtResultado = new JTextArea(6, 40);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));

        add(form, BorderLayout.CENTER);
        add(new JScrollPane(txtResultado), BorderLayout.SOUTH);
    }

    private void registrar() {
        if (cboDni.getItemCount() == 0 || cboCodigo.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay clientes o departamentos disponibles.");
            return;
        }
        try {
            String dniStr = ((String) cboDni.getSelectedItem())
                            .split(" - ")[0];
            String codStr = ((String) cboCodigo.getSelectedItem())
                            .split(" \\| ")[0];
            double monto    = Double.parseDouble(txtMonto.getText().trim());
            String vigencia = txtVigencia.getText().trim();
            String hoy      = txtFechaHoy.getText().trim();

            Cliente cliente = sistema.buscarClientePorDni(dniStr);
            Departamento dpto = null;
            Proyecto[] proyectos = sistema.getProyectos();
            for (int i = 0; i < proyectos.length; i++) {
                dpto = proyectos[i].buscarDepartamento(codStr);
                if (dpto != null) break;
            }

            if (cliente == null || dpto == null) {
                JOptionPane.showMessageDialog(this,
                    "No se encontro el cliente o departamento.");
                return;
            }

            Reserva r = sistema.registrarReserva(
                cliente, dpto, monto, vigencia, hoy);
            if (r != null) {
                txtResultado.setText(r.generarReporte());
                JOptionPane.showMessageDialog(this,
                    "Reserva registrada. Departamento marcado como RESERVADO.");
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se pudo registrar. El departamento no esta disponible.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "El monto debe ser un numero valido.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton boton(String t, Color c) {
        JButton b = new JButton(t);
        b.setBackground(c); b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        return b;
    }
}
