
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class AsesorVenta extends Empleado {
    private Venta[] ventasRealizadas;
    private int     cantVentas;
    private double  metaVentas;
    private static final int MAX_VENTAS = 100;

    public AsesorVenta(Venta[] ventasRealizadas, int cantVentas, double metaVentas, String usuario, String contrasena, String rol, String dni, String nombres, String apellidos, String telefono, String correo) {
        super(usuario, contrasena, rol, dni, nombres, apellidos, telefono, correo);
        this.ventasRealizadas = new Venta[MAX_VENTAS];
        this.cantVentas = cantVentas;
        this.metaVentas = metaVentas;
    }
    
    public boolean agregarVenta(Venta v) {
        if (cantVentas >= MAX_VENTAS) return false;
        ventasRealizadas[cantVentas] = v;
        cantVentas++;
        return true;
    }

    public double getComision() {
        double total = 0;
        for (int i = 0; i < cantVentas; i++) {
            total = total + ventasRealizadas[i].getPrecioFinal();
        }
        return total * 0.02;
    }

    public int    getCantVentas() { return cantVentas; }
    public double getMetaVentas() { return metaVentas; }

    // Polimorfismo: cada subclase implementa generarReporte() distinto (semana 11)
    @Override
    public String generarReporte() {
        return "=== Reporte Asesor: " + getDatosCompletos() + " ===" +
               "\nVentas realizadas : " + cantVentas +
               "\nMeta de ventas    : S/ " + metaVentas +
               "\nComision estimada : S/ " + getComision();
    }
    
}
