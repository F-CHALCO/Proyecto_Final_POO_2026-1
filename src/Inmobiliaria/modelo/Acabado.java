
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

    public String getNombre()                  { return nombre; }
    public void   setNombre(String n)          { this.nombre = n; }
    public String getDescripcion()             { return descripcion; }
    public void   setDescripcion(String d)     { this.descripcion = d; }
    public double getPrecio()                  { return precioAdicional; }
    public void   setPrecioAdicional(double p) { this.precioAdicional = p; }
    public String getCategoria()               { return categoria; }
    public void   setCategoria(String c)       { this.categoria = c; }

    @Override
    public String toString() {
        return nombre + " [" + categoria + "] - S/ " + precioAdicional;
    }
}
