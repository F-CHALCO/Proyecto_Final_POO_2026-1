
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class Reserva {
    private double       montoSeparacion;
    private String       fechaVigencia;
    private String       fechaReserva;
    private boolean      activa;
    private Cliente      cliente;
    private Departamento departamento;
    private AsesorVenta  asesor;

    public Reserva(Cliente cliente, Departamento departamento,
                   AsesorVenta asesor,
                   double montoSeparacion, String fechaVigencia,
                   String fechaReserva) {
        this.cliente         = cliente;
        this.departamento    = departamento;
        this.asesor          = asesor;
        this.montoSeparacion = montoSeparacion;
        this.fechaVigencia   = fechaVigencia;
        this.fechaReserva    = fechaReserva;
        this.activa          = true;
    }

    public boolean estaVigente() {
        return activa;
    }

    public void cancelar() {
        this.activa = false;
        departamento.setEstado(Constantes.DPTO_DISPONIBLE);
    }

    public String generarReporte() {
        return "=== Reserva ===" +
               "\nCliente      : " + cliente.getDatosCompletos() +
               "\nDepartamento : " + departamento.getCodigo() +
               "\nMonto sep.   : S/ " + montoSeparacion +
               "\nVigente hasta: " + fechaVigencia +
               "\nEstado       : " + (activa ? "VIGENTE" : "CANCELADA");
    }

    public double getMontoSeparacion() {
        return montoSeparacion;
    }

    public void setMontoSeparacion(double montoSeparacion) {
        this.montoSeparacion = montoSeparacion;
    }

    public String getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(String fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public AsesorVenta getAsesor() {
        return asesor;
    }

    public void setAsesor(AsesorVenta asesor) {
        this.asesor = asesor;
    }

    
}
