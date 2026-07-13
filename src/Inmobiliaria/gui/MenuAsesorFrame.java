
package Inmobiliaria.gui;

import Inmobiliaria.servicio.GestorSistema;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class MenuAsesorFrame extends JFrame {

    private final GestorSistema sistema;

    public MenuAsesorFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Asesor de Ventas - "
                + sistema.getEmpleadoLogueado().getDatosCompletos());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setSize(480, 440);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 1, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        panel.setBackground(new Color(245, 250, 245));

        JLabel lblTitulo = new JLabel("Panel Asesor de Ventas",
                                       SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitulo.setForeground(new Color(30, 130, 100));

        JButton btnClientes  = boton("Gestionar Clientes",
                                      new Color(30, 130, 100));
        JButton btnReservas  = boton("Registrar Reserva",
                                      new Color(30, 80, 160));
        JButton btnVentas    = boton("Registrar Venta",
                                      new Color(130, 60, 20));
        JButton btnPagos     = boton("Registrar Pago de Cuota",
                                      new Color(100, 100, 30));
        JButton btnBuscador  = boton("Buscar y Ordenar Departamentos",
                                      new Color(80, 50, 140));
        JButton btnSalir     = boton("Cerrar Sesion",
                                      new Color(180, 50, 50));

        btnClientes.addActionListener(e ->
            new ClientesFrame(sistema).setVisible(true));
        btnReservas.addActionListener(e ->
            new ReservasFrame(sistema).setVisible(true));
        btnVentas.addActionListener(e ->
            new VentasFrame(sistema).setVisible(true));
        btnPagos.addActionListener(e ->
            new PagosFrame(sistema).setVisible(true));
        btnBuscador.addActionListener(e ->
            new BuscadorFrame(sistema).setVisible(true));
        btnSalir.addActionListener(e -> cerrarSesion());

        panel.add(lblTitulo);
        panel.add(btnClientes);
        panel.add(btnReservas);
        panel.add(btnVentas);
        panel.add(btnPagos);
        panel.add(btnBuscador);
        panel.add(btnSalir);

        add(panel);
    }

    private void cerrarSesion() {
        sistema.logout();
        new LoginFrame(sistema).setVisible(true);
        this.dispose();
    }

    private JButton boton(String texto, Color color) {
        JButton b = new JButton(texto);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        b.setFocusPainted(false);
        return b;
    }
}
