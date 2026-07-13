
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
public class MenuAdminFrame extends JFrame {

    private final GestorSistema sistema;

    public MenuAdminFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Administrador - "
                + sistema.getEmpleadoLogueado().getDatosCompletos());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setSize(480, 380);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        panel.setBackground(new Color(245, 245, 250));

        JLabel lblTitulo = new JLabel("Panel Administrador",
                                       SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitulo.setForeground(new Color(30, 80, 160));

        JButton btnEmpleados = boton("Gestionar Empleados",
                                      new Color(30, 80, 160));
        JButton btnProyectos = boton("Gestionar Proyectos",
                                      new Color(30, 130, 100));
        JButton btnAcabados  = boton("Gestionar Acabados",
                                      new Color(130, 80, 160));
        JButton btnSalir     = boton("Cerrar Sesion",
                                      new Color(180, 50, 50));

        btnEmpleados.addActionListener(e ->
            new EmpleadosFrame(sistema).setVisible(true));
        btnProyectos.addActionListener(e ->
            new ProyectosFrame(sistema).setVisible(true));
        btnAcabados.addActionListener(e ->
            new AcabadosFrame(sistema).setVisible(true));
        btnSalir.addActionListener(e -> cerrarSesion());

        panel.add(lblTitulo);
        panel.add(btnEmpleados);
        panel.add(btnProyectos);
        panel.add(btnAcabados);
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
