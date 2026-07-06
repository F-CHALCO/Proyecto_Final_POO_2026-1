
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class Cliente extends Persona{
    private String fechaNacimiento;
    private String estadoCivil;
    private String ocupacion;
    private double ingresosMensuales;
    private Reserva[] reservas;
    private int cantReservas;
    private static final int MAX_RESERVAS = 20;

    public Cliente(String fechaNacimiento, String estadoCivil, String ocupacion, double ingresosMensuales, int cantReservas, String dni, String nombres, String apellidos, String telefono, String correo) {
        super(dni, nombres, apellidos, telefono, correo);
        this.fechaNacimiento = fechaNacimiento;
        this.estadoCivil = estadoCivil;
        this.ocupacion = ocupacion;
        this.ingresosMensuales = ingresosMensuales;
        this.reservas = new Reserva[MAX_RESERVAS];
        this.cantReservas = 0;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public double getIngresosMensuales() {
        return ingresosMensuales;
    }

    public void setIngresosMensuales(double ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }

    public int getCantReservas() {
        return cantReservas;
    }

    public void setCantReservas(int cantReservas) {
        this.cantReservas = cantReservas;
    }
    
    public boolean agregarReserva(Reserva r) {
        if (cantReservas >= MAX_RESERVAS) return false;
        reservas[cantReservas] = r;
        cantReservas++;
        return true;
    }
        
    public Reserva[] getHistorialReservas() {
        Reserva[] resultado = new Reserva[cantReservas];
        for (int i = 0; i < cantReservas; i++) {
            resultado[i] = reservas[i];
        }
        return resultado;
    }

    @Override
    public String generarReporte() {
        return "=== Reporte Cliente: " + getDatosCompletos() + " ===" +
               "\nFecha de nac.    : " + fechaNacimiento +
               "\nOcupacion        : " + ocupacion +
               "\nIngresos mensual : S/ " + ingresosMensuales +
               "\nReservas activas : " + cantReservas;  
    }
    
    
}
