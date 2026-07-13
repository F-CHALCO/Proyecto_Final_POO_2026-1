
package Inmobiliaria.gui;

import Inmobiliaria.servicio.GestorSistema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class LoginFrame extends JFrame{
    private final GestorSistema sistema;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JLabel lblError;

    public LoginFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema Inmobiliaria - Iniciar Sesion");
        setIconImage(IconoApp.getImagen());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 340);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));
        panel.setBackground(new Color(245, 245, 250));

        JLabel lblLogo = new JLabel(IconoApp.getIconoEscalado(90, 90), SwingConstants.CENTER);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblTitulo = new JLabel("SISTEMA INMOBILIARIA",
                                       SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(30, 80, 160));

        JPanel panelSuperior = new JPanel(new BorderLayout(4, 4));
        panelSuperior.setBackground(new Color(245, 245, 250));
        panelSuperior.add(lblLogo, BorderLayout.NORTH);
        panelSuperior.add(lblTitulo, BorderLayout.SOUTH);

        JPanel panelForm = new JPanel(new GridLayout(4, 1, 8, 8));
        panelForm.setBackground(new Color(245, 245, 250));

        txtUsuario    = new JTextField();
        txtContrasena = new JPasswordField();

        txtUsuario.setBorder(BorderFactory.createTitledBorder("Usuario"));
        txtContrasena.setBorder(BorderFactory.createTitledBorder("Contrasena"));

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBackground(new Color(30, 80, 160));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 13));
        btnIngresar.setFocusPainted(false);

        lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Arial", Font.PLAIN, 12));

        panelForm.add(txtUsuario);
        panelForm.add(txtContrasena);
        panelForm.add(btnIngresar);
        panelForm.add(lblError);

        panel.add(panelSuperior,  BorderLayout.NORTH);
        panel.add(panelForm,  BorderLayout.CENTER);

        btnIngresar.addActionListener(e -> intentarLogin());
        txtContrasena.addActionListener(e -> intentarLogin());

        add(panel);
    }

    private void intentarLogin() {
        String usuario = txtUsuario.getText().trim();
        String pass    = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || pass.isEmpty()) {
            lblError.setText("Ingrese usuario y contrasena.");
            return;
        }

        if (sistema.login(usuario, pass)) {
            lblError.setText("");
            abrirMenuSegunRol();
        } else {
            lblError.setText("Usuario o contrasena incorrectos.");
            txtContrasena.setText("");
        }
    }

    private void abrirMenuSegunRol() {
        JFrame siguiente;
        if (sistema.esAdmin()) {
            siguiente = new MenuAdminFrame(sistema);
        } else if (sistema.esAsesor()) {
            siguiente = new MenuAsesorFrame(sistema);
        } else {
            siguiente = new MenuGerenteFrame(sistema);
        }
        siguiente.setVisible(true);
        this.dispose();
    }
}
