
package Inmobiliaria.gui;

import Inmobiliaria.servicio.GestorSistema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class ReportesFrame extends JFrame {

    private final GestorSistema sistema;
    private JTextArea txtReporte;

    public ReportesFrame(GestorSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Reportes y Estadisticas");
        setSize(700, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(IconoApp.getImagen());
        setLayout(new BorderLayout(10, 10));

        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 10, 10));
        panelBotones.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JButton btnDeptos     = boton("Dptos por Proyecto",
                                       new Color(30, 80, 160));
        JButton btnVentas     = boton("Ventas por Asesor",
                                       new Color(30, 130, 80));
        JButton btnIngresos   = boton("Ingresos y Saldos",
                                       new Color(130, 60, 20));
        JButton btnPorcentaje = boton("% Ventas por Proyecto",
                                       new Color(100, 60, 160));
        JButton btnGeneral    = boton("Reporte General",
                                       new Color(60, 60, 60));
        JButton btnLimpiar    = boton("Limpiar",
                                       new Color(160, 160, 160));

        btnDeptos.addActionListener(e ->
            txtReporte.setText(sistema.reporteDptosPorProyecto()));
        btnVentas.addActionListener(e ->
            txtReporte.setText(sistema.reporteVentasPorAsesor()));
        btnIngresos.addActionListener(e ->
            txtReporte.setText(sistema.reporteIngresos()));
        btnPorcentaje.addActionListener(e ->
            txtReporte.setText(sistema.reportePorcentajeVentas()));
        btnGeneral.addActionListener(e ->
            txtReporte.setText(sistema.reporteGeneral()));
        btnLimpiar.addActionListener(e ->
            txtReporte.setText(""));

        panelBotones.add(btnDeptos);
        panelBotones.add(btnVentas);
        panelBotones.add(btnIngresos);
        panelBotones.add(btnPorcentaje);
        panelBotones.add(btnGeneral);
        panelBotones.add(btnLimpiar);

        txtReporte = new JTextArea();
        txtReporte.setEditable(false);
        txtReporte.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txtReporte);
        scroll.setBorder(BorderFactory.createTitledBorder(
            "Resultado del reporte"));

        add(panelBotones, BorderLayout.NORTH);
        add(scroll,       BorderLayout.CENTER);
    }

    private JButton boton(String t, Color c) {
        JButton b = new JButton(t);
        b.setBackground(c); b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 11));
        return b;
    }
}
