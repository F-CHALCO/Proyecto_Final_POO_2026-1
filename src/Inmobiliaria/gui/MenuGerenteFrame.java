
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
public class MenuGerenteFrame extends JFrame {

    private final GestorSistema sistema;

    public MenuGerenteFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Gerente - "
                + sistema.getEmpleadoLogueado().getDatosCompletos());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setSize(480, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        panel.setBackground(new Color(250, 248, 245));

        JLabel lblTitulo = new JLabel("Panel Gerencia",
                                       SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitulo.setForeground(new Color(120, 60, 20));

        JButton btnReportes = boton("Ver Reportes y Estadisticas",
                                     new Color(120, 60, 20));
        JButton btnSalir    = boton("Cerrar Sesion",
                                     new Color(180, 50, 50));

        btnReportes.addActionListener(e ->
            new ReportesFrame(sistema).setVisible(true));
        btnSalir.addActionListener(e -> cerrarSesion());

        panel.add(lblTitulo);
        panel.add(btnReportes);
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
