
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class Administrador extends Empleado {
    private int nivelAcceso;

    public Administrador(int nivelAcceso, String usuario, String contrasena, String rol, String dni, String nombres, String apellidos, String telefono, String correo) {
        super(usuario, contrasena, rol, dni, nombres, apellidos, telefono, correo);
        this.nivelAcceso = nivelAcceso;
    }

    public int getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(int nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    @Override
    public String generarReporte() {
        return "=== Reporte Administrador: " + getDatosCompletos() + " ===" +
               "\nNivel de acceso: " + nivelAcceso;
    }
    
    
}
