
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class Gerente extends Empleado {
    
    private String areaResponsable;

    public Gerente(String areaResponsable, String usuario, String contrasena,
                   String rol, String dni, String nombres,
                   String apellidos, String telefono, String correo) {
        super(usuario, contrasena, rol, dni, nombres, apellidos, telefono, correo);
        this.areaResponsable = areaResponsable;
    }

    public String getAreaResponsable() {
        return areaResponsable;
    }

    public void setAreaResponsable(String areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

    @Override
    public String generarReporte() {
        return "=== Reporte Gerente: " + getDatosCompletos() + " ===" +
               "\nArea responsable: " + areaResponsable;
    }
}
