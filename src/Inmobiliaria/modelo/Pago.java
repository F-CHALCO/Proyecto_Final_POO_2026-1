
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class Pago {
    private int numeroCuota;
    private double montoCuota;
    private String fechaVencimiento;
    private String fechaPago;
    private boolean pagado;

    public Pago(int numeroCuota, double montoCuota, String fechaVencimiento) {
        this.numeroCuota      = numeroCuota;
        this.montoCuota       = montoCuota;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaPago        = "";
        this.pagado           = false;
    }

    public boolean registrarPago(String fecha) {
        if (pagado) return false;
        this.fechaPago = fecha;
        this.pagado    = true;
        return true;
    }

    public int getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(int numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public double getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(double montoCuota) {
        this.montoCuota = montoCuota;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    @Override
    public String toString() {
        String estado = pagado ? "PAGADO" : "PENDIENTE";
        return "Cuota " + numeroCuota + " | S/ " + montoCuota +
               " | Vence: " + fechaVencimiento + " | " + estado;
    }
}
