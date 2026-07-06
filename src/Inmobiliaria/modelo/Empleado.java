
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public abstract class Empleado extends Persona {

    private String usuario;
    private String contrasena;
    private String rol;

    public Empleado(String usuario, String contrasena, String rol,
                    String dni, String nombres, String apellidos,
                    String telefono, String correo) {

        super(dni, nombres, apellidos, telefono, correo);

        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean login(String user, String pass) {
        return usuario.equals(user) && contrasena.equals(pass);
    }

    // Método abstracto que implementarán las clases hijas
    public abstract String generarReporte();

}
