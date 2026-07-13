
package Inmobiliaria.gui;

import Inmobiliaria.modelo.Cliente;
import Inmobiliaria.modelo.Constantes;
import Inmobiliaria.modelo.Reserva;
import Inmobiliaria.modelo.Venta;
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
public class VentasFrame extends JFrame {

    private final GestorSistema sistema;
    private JComboBox<String> cboDni;
    private JComboBox<String> cboModalidad;
    private JTextField txtPrecio, txtCuotaInicial, txtNumCuotas;
    private JTextField txtFechaVenta, txtFechaCronograma;
    private JTextArea  txtContrato;

    public VentasFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Venta");
        setSize(560, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Datos de la venta"));
        form.setBackground(Color.WHITE);

        cboDni       = new JComboBox<>();
        cboModalidad = new JComboBox<>(new String[]{
            Constantes.PAGO_CONTADO,
            Constantes.PAGO_BANCARIO,
            Constantes.PAGO_CUOTAS
        });
        txtPrecio          = new JTextField();
        txtCuotaInicial    = new JTextField("50000.00");
        txtNumCuotas       = new JTextField("24");
        txtFechaVenta      = new JTextField("2026-07-01");
        txtFechaCronograma = new JTextField("2026-08-01");

        Cliente[] clientes = sistema.getClientes();
        for (int i = 0; i < clientes.length; i++) {
            Reserva[] reservas = clientes[i].getHistorialReservas();
            for (int j = 0; j < reservas.length; j++) {
                if (reservas[j].estaVigente()) {
                    cboDni.addItem(clientes[i].getDni()
                        + " - " + clientes[i].getNombres()
                        + " ["
                        + reservas[j].getDepartamento().getCodigo()
                        + "]");
                }
            }
        }

        cboModalidad.addActionListener(e -> toggleCuotas());

        form.add(new JLabel("Cliente con reserva vigente:")); form.add(cboDni);
        form.add(new JLabel("Precio final S/:"));  form.add(txtPrecio);
        form.add(new JLabel("Modalidad de pago:")); form.add(cboModalidad);
        form.add(new JLabel("Cuota inicial S/:")); form.add(txtCuotaInicial);
        form.add(new JLabel("N cuotas:"));         form.add(txtNumCuotas);
        form.add(new JLabel("Fecha venta:"));      form.add(txtFechaVenta);
        form.add(new JLabel("Inicio cronograma:")); form.add(txtFechaCronograma);

        JButton btnVender = boton("Registrar Venta y Generar Contrato",
                                   new Color(130, 60, 20));
        btnVender.addActionListener(e -> registrar());

        txtContrato = new JTextArea(8, 50);
        txtContrato.setEditable(false);
        txtContrato.setFont(new Font("Monospaced", Font.PLAIN, 11));
        txtContrato.setBorder(BorderFactory.createTitledBorder(
            "Contrato generado"));

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnVender);

        add(form,    BorderLayout.NORTH);
        add(panelBoton, BorderLayout.CENTER);
        add(new JScrollPane(txtContrato), BorderLayout.SOUTH);

        toggleCuotas();
    }

    private void toggleCuotas() {
        boolean esCuotas = Constantes.PAGO_CUOTAS.equals(
            cboModalidad.getSelectedItem());
        txtCuotaInicial.setEnabled(esCuotas);
        txtNumCuotas.setEnabled(esCuotas);
        txtFechaCronograma.setEnabled(esCuotas);
    }

    private void registrar() {
        if (cboDni.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay clientes con reservas vigentes.");
            return;
        }
        try {
            String sel      = (String) cboDni.getSelectedItem();
            String dniStr   = sel.split(" - ")[0];
            String codDpto  = sel.replaceAll(".*\\[(.+)\\]", "$1");
            double precio   = Double.parseDouble(txtPrecio.getText().trim());
            String modalidad = (String) cboModalidad.getSelectedItem();
            String fechaVta  = txtFechaVenta.getText().trim();

            Cliente cliente = sistema.buscarClientePorDni(dniStr);
            Reserva reserva = null;
            Reserva[] reservas = cliente.getHistorialReservas();
            for (int i = 0; i < reservas.length; i++) {
                if (reservas[i].estaVigente()
                        && reservas[i].getDepartamento()
                                      .getCodigo().equals(codDpto)) {
                    reserva = reservas[i];
                    break;
                }
            }

            if (reserva == null) {
                JOptionPane.showMessageDialog(this,
                    "No se encontro reserva vigente.");
                return;
            }

            Venta venta = sistema.registrarVenta(
                reserva, precio, modalidad, fechaVta);
            if (venta != null) {
                if (modalidad.equals(Constantes.PAGO_CUOTAS)) {
                    double cuotaIni  = Double.parseDouble(
                        txtCuotaInicial.getText().trim());
                    int    numCuotas = Integer.parseInt(
                        txtNumCuotas.getText().trim());
                    String fechaCron = txtFechaCronograma.getText().trim();
                    venta.generarCronograma(cuotaIni, numCuotas, fechaCron);
                }
                txtContrato.setText(venta.generarContrato());
                JOptionPane.showMessageDialog(this,
                    "Venta registrada. Departamento marcado como VENDIDO.");
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se pudo registrar la venta.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Verifique los campos numericos.",
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

