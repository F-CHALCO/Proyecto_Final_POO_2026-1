
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class Acabado {
    private String nombre;
    private String descripcion;
    private double precioAdicional;
    private String categoria;

    public Acabado(String nombre, String descripcion,
                   double precioAdicional, String categoria) {
        this.nombre          = nombre;
        this.descripcion     = descripcion;
        this.precioAdicional = precioAdicional;
        this.categoria       = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precioAdicional;
    }

    public void setPrecioAdicional(double precioAdicional) {
        if(precioAdicional >= 0){
        this.precioAdicional = precioAdicional;
    }}

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }



    @Override
    public String toString() {
        return nombre + " [" + categoria + "] - S/ " + precioAdicional;
    }
}
