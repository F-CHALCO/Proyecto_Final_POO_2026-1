package Inmobiliaria.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Utilidad centralizada para cargar la imagen/icono del sistema y
 * reutilizarla en todas las ventanas (JFrame) de la aplicacion.
 *
 * Uso en cualquier JFrame:
 *     setIconImage(IconoApp.getImagen());
 */
public class IconoApp {

    private static final String RUTA_RECURSO = "/Inmobiliaria/gui/recursos/icono_inmobiliaria.png";
    private static BufferedImage imagenCache;

    private IconoApp() {
        // clase utilitaria, no se instancia
    }

    public static Image getImagen() {
        if (imagenCache == null) {
            try (InputStream in = IconoApp.class.getResourceAsStream(RUTA_RECURSO)) {
                if (in != null) {
                    imagenCache = ImageIO.read(in);
                }
            } catch (IOException ex) {
                imagenCache = null;
            }
            if (imagenCache == null) {
                // Si el recurso no se encuentra, se evita un NullPointerException
                // y simplemente no se asigna un icono visible.
                imagenCache = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            }
        }
        return imagenCache;
    }

    public static ImageIcon getIcono() {
        return new ImageIcon(getImagen());
    }

    /** Version reducida del icono, util para mostrarlo como logo dentro de un panel. */
    public static ImageIcon getIconoEscalado(int ancho, int alto) {
        Image escalada = getImagen().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }
}
