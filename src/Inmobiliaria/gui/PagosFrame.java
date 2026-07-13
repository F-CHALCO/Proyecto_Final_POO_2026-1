
package Inmobiliaria.gui;

import Inmobiliaria.modelo.Cliente;
import Inmobiliaria.modelo.Pago;
import Inmobiliaria.modelo.Venta;
import Inmobiliaria.servicio.GestorSistema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
public class PagosFrame extends JFrame {

    private final GestorSistema sistema;
    private JComboBox<String> cboDni;
    private JTable tablaCuotas;
    private DefaultTableModel modeloCuotas;
    private JTextField txtFechaPago;

    public PagosFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
        if (cboDni.getItemCount() > 0) cargarCuotas();
    }

    private void initComponents() {
        setTitle("Registrar Pago de Cuota");
        setSize(620, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setLayout(new BorderLayout(10, 10));

        JPanel panelSel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cboDni = new JComboBox<>();
        Cliente[] clientes = sistema.getClientes();
        for (int i = 0; i < clientes.length; i++) {
            cboDni.addItem(clientes[i].getDni()
                + " - " + clientes[i].getNombres());
        }
        cboDni.addActionListener(e -> cargarCuotas());
        panelSel.add(new JLabel("Cliente: "));
        panelSel.add(cboDni);

        String[] cols = {"N Cuota", "Monto S/", "Vencimiento", "Pagado"};
        modeloCuotas = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaCuotas = new JTable(modeloCuotas);
        tablaCuotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel panelPago = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        txtFechaPago = new JTextField("2026-07-01", 12);
        JButton btnPagar = boton("Registrar Pago", new Color(30, 130, 80));
        btnPagar.addActionListener(e -> registrarPago());
        panelPago.add(new JLabel("Fecha de pago:"));
        panelPago.add(txtFechaPago);
        panelPago.add(btnPagar);
        panelPago.setBorder(BorderFactory.createTitledBorder(
            "Registrar pago de la cuota seleccionada"));

        add(panelSel,  BorderLayout.NORTH);
        add(new JScrollPane(tablaCuotas), BorderLayout.CENTER);
        add(panelPago, BorderLayout.SOUTH);
    }

    private void cargarCuotas() {
        modeloCuotas.setRowCount(0);
        if (cboDni.getItemCount() == 0) return;
        String dniStr = ((String) cboDni.getSelectedItem()).split(" - ")[0];
        Venta[] ventas = sistema.getVentas();
        for (int i = 0; i < ventas.length; i++) {
            if (ventas[i].getReservaOrigen().getCliente()
                         .getDni().equals(dniStr)) {
                Pago[] pagos = ventas[i].getCronogramaPagos();
                for (int j = 0; j < pagos.length; j++) {
                    modeloCuotas.addRow(new Object[]{
                        pagos[j].getNumeroCuota(),
                        pagos[j].getMontoCuota(),
                        pagos[j].getFechaVencimiento(),
                        pagos[j].isPagado() ? "SI" : "NO"
                    });
                }
            }
        }
    }

    private void registrarPago() {
        int fila = tablaCuotas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una cuota.");
            return;
        }
        int    numCuota  = (int) modeloCuotas.getValueAt(fila, 0);
        String fechaPago = txtFechaPago.getText().trim();
        String dniStr = ((String) cboDni.getSelectedItem()).split(" - ")[0];

        Venta[] ventas = sistema.getVentas();
        for (int i = 0; i < ventas.length; i++) {
            if (ventas[i].getReservaOrigen().getCliente()
                         .getDni().equals(dniStr)) {
                boolean ok = ventas[i].registrarPago(numCuota, fechaPago);
                if (ok) {
                    JOptionPane.showMessageDialog(this,
                        "Pago registrado.\nSaldo pendiente: S/ "
                        + ventas[i].calcularSaldo());
                    cargarCuotas();
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(this,
            "No se pudo registrar (cuota ya pagada o no encontrada).",
            "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private JButton boton(String t, Color c) {
        JButton b = new JButton(t);
        b.setBackground(c); b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        return b;
    }
}
