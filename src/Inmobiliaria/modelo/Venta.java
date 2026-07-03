
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class Venta {
    private String fechaVenta;
    private double precioFinal;
    private String modalidadPago;
    private double saldoPendiente;
    private Pago[] cronogramaPagos;
    private int cantCuotas;
    private Reserva reservaOrigen;
    private AsesorVenta asesor;
    private static final int MAX_CUOTAS = 120;

    public Venta(Reserva reservaOrigen, AsesorVenta asesor,
                 double precioFinal, String modalidad,
                 String fechaVenta) {
        this.reservaOrigen   = reservaOrigen;
        this.asesor          = asesor;
        this.precioFinal     = precioFinal;
        this.modalidadPago   = modalidad;
        this.fechaVenta      = fechaVenta;
        this.cronogramaPagos = new Pago[MAX_CUOTAS];
        this.cantCuotas      = 0;

        if (modalidad.equals(Constantes.PAGO_CUOTAS)) {
            this.saldoPendiente = precioFinal - reservaOrigen.getMontoSeparacion();
        } else {
            this.saldoPendiente = 0;
        }

        reservaOrigen.getDepartamento().vender();
    }

    public void generarCronograma(double cuotaInicial, int numCuotas,
                                   String primerVencimiento) {
        if (!modalidadPago.equals(Constantes.PAGO_CUOTAS)) return;

        cronogramaPagos[0] = new Pago(0, cuotaInicial, fechaVenta);
        cantCuotas = 1;
        double montoPorCuota = (saldoPendiente - cuotaInicial) / numCuotas;
        for (int i = 1; i <= numCuotas; i++) {
            cronogramaPagos[cantCuotas] = new Pago(i, montoPorCuota,
                                                    primerVencimiento);
            cantCuotas++;
        }
    }

    public boolean registrarPago(int numeroCuota, String fechaPago) {
        for (int i = 0; i < cantCuotas; i++) {
            if (cronogramaPagos[i].getNumeroCuota() == numeroCuota) {
                boolean ok = cronogramaPagos[i].registrarPago(fechaPago);
                if (ok) {
                    saldoPendiente = saldoPendiente - cronogramaPagos[i].getMonto();
                }
                return ok;
            }
        }
        return false;
    }

    public double calcularSaldo() {
        double saldo = 0;
        for (int i = 0; i < cantCuotas; i++) {
            if (!cronogramaPagos[i].isPagado()) {
                saldo = saldo + cronogramaPagos[i].getMonto();
            }
        }
        return saldo;
    }

    public String generarContrato() {
        Cliente      c = reservaOrigen.getCliente();
        Departamento d = reservaOrigen.getDepartamento();
        return "============================================" +
               "\n       CONTRATO DE COMPRA-VENTA"          +
               "\n============================================" +
               "\nCLIENTE     : " + c.getDatosCompletos()   +
               "\nDNI         : " + c.getDni()              +
               "\nDEPARTAMENTO: " + d.getCodigo() +
                              " | Piso " + d.getNumeroPiso() +
               "\nTIPO        : " + d.getTipo() +
                              " | " + d.getArea() + " m2"   +
               "\nPRECIO TOTAL: S/ " + precioFinal          +
               "\nMODALIDAD   : " + modalidadPago           +
               "\nFECHA VENTA : " + fechaVenta              +
               "\nASESOR      : " + asesor.getDatosCompletos() +
               "\n============================================";
    }

    public String generarReporte() {
        return "=== Reporte Venta ===" +
               "\nFecha       : " + fechaVenta +
               "\nPrecio final: S/ " + precioFinal +
               "\nModalidad   : " + modalidadPago +
               "\nSaldo pend. : S/ " + calcularSaldo();
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public String getModalidadPago() {
        return modalidadPago;
    }

    public void setModalidadPago(String modalidadPago) {
        this.modalidadPago = modalidadPago;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public int getCantCuotas() {
        return cantCuotas;
    }

    public void setCantCuotas(int cantCuotas) {
        this.cantCuotas = cantCuotas;
    }

    public Reserva getReservaOrigen() {
        return reservaOrigen;
    }

    public void setReservaOrigen(Reserva reservaOrigen) {
        this.reservaOrigen = reservaOrigen;
    }

    public AsesorVenta getAsesor() {
        return asesor;
    }

    public void setAsesor(AsesorVenta asesor) {
        this.asesor = asesor;
    }

    

    public Pago[] getCronogramaPagos() {
        Pago[] copia = new Pago[cantCuotas];
        for (int i = 0; i < cantCuotas; i++) {
            copia[i] = cronogramaPagos[i];
        }
        return copia;
    }
}
