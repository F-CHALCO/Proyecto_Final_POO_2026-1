package Inmobiliaria.servicio;

import Inmobiliaria.modelo.Acabado;
import Inmobiliaria.modelo.Administrador;
import Inmobiliaria.modelo.AsesorVenta;
import Inmobiliaria.modelo.Cliente;
import Inmobiliaria.modelo.Constantes;
import Inmobiliaria.modelo.Departamento;
import Inmobiliaria.modelo.Empleado;
import Inmobiliaria.modelo.Proyecto;
import Inmobiliaria.modelo.Reserva;
import Inmobiliaria.modelo.Venta;

public class GestorSistema {

    private Empleado[] empleados;
    private int cantEmpleados;

    private Proyecto[] proyectos;
    private int cantProyectos;

    private Cliente[] clientes;
    private int cantClientes;

    private Reserva[] reservas;
    private int cantReservas;

    private Venta[] ventas;
    private int cantVentas;

    private Acabado[] acabados;
    private int cantAcabados;

    private static final int MAX = 500;

    private Empleado empleadoLogueado;

    public GestorSistema() {
        empleados     = new Empleado[MAX]; cantEmpleados = 0;
        proyectos     = new Proyecto[MAX]; cantProyectos = 0;
        clientes      = new Cliente[MAX];  cantClientes  = 0;
        reservas      = new Reserva[MAX];  cantReservas  = 0;
        ventas        = new Venta[MAX];    cantVentas    = 0;
        acabados      = new Acabado[MAX];  cantAcabados  = 0;
        empleadoLogueado = null;

        
        agregarEmpleado(new Administrador(
            3,
            "admin",
            "admin123",
            Constantes.ROL_ADMIN,
            "00000001",
            "Admin",
            "Sistema",
            "999000001",
            "admin@inmob.pe"
        ));
    }

    public boolean login(String usuario, String contrasena) {
        for (int i = 0; i < cantEmpleados; i++) {
            if (empleados[i].login(usuario, contrasena)) {
                empleadoLogueado = empleados[i];
                return true;
            }
        }
        return false;
    }

    public void logout() {
        empleadoLogueado = null;
    }

    public Empleado getEmpleadoLogueado() {
        return empleadoLogueado;
    }

    public boolean esAdmin() {
        return empleadoLogueado != null
            && empleadoLogueado.getRol().equals(Constantes.ROL_ADMIN);
    }

    public boolean esAsesor() {
        return empleadoLogueado != null
            && empleadoLogueado.getRol().equals(Constantes.ROL_ASESOR);
    }

    public boolean esGerente() {
        return empleadoLogueado != null
            && empleadoLogueado.getRol().equals(Constantes.ROL_GERENTE);
    }

    public boolean agregarEmpleado(Empleado e) {
        if (cantEmpleados >= MAX) return false;
        if (buscarEmpleadoPorDni(e.getDni()) != null) return false;
        empleados[cantEmpleados] = e;
        cantEmpleados++;
        return true;
    }
   
    public boolean eliminarEmpleado(String dni) {
        if (empleadoLogueado != null
                && empleadoLogueado.getDni().equals(dni)) {
            return false;
        }
        for (int i = 0; i < cantEmpleados; i++) {
            if (empleados[i].getDni().equals(dni)) {
                for (int j = i; j < cantEmpleados - 1; j++) {
                    empleados[j] = empleados[j + 1];
                }
                cantEmpleados--;
                empleados[cantEmpleados] = null;
                return true;
            }
        }
        return false;
    }
   
    public boolean modificarEmpleado(String dni, String nuevoTelefono,
                                      String nuevoCorreo, String nuevaContrasena) {
        Empleado e = buscarEmpleadoPorDni(dni);
        if (e == null) return false;
        if (nuevoTelefono != null && !nuevoTelefono.isEmpty())
            e.setTelefono(nuevoTelefono);
        if (nuevoCorreo != null && !nuevoCorreo.isEmpty())
            e.setCorreo(nuevoCorreo);
        if (nuevaContrasena != null && !nuevaContrasena.isEmpty())
            e.setContrasena(nuevaContrasena);
        return true;
    }

    public Empleado buscarEmpleadoPorDni(String dni) {
        for (int i = 0; i < cantEmpleados; i++) {
            if (empleados[i].getDni().equals(dni)) return empleados[i];
        }
        return null;
    }

    public Empleado[] getEmpleados() {
        Empleado[] copia = new Empleado[cantEmpleados];
        for (int i = 0; i < cantEmpleados; i++) copia[i] = empleados[i];
        return copia;
    }

    public boolean agregarProyecto(Proyecto p) {
        if (cantProyectos >= MAX) return false;
        proyectos[cantProyectos] = p;
        cantProyectos++;
        return true;
    }

    public boolean eliminarProyecto(String nombre) {
        for (int i = 0; i < cantProyectos; i++) {
            if (proyectos[i].getNombre().equals(nombre)) {
                for (int j = i; j < cantProyectos - 1; j++) {
                    proyectos[j] = proyectos[j + 1];
                }
                cantProyectos--;
                proyectos[cantProyectos] = null;
                return true;
            }
        }
        return false;
    }
   
    public boolean modificarProyecto(String nombre, String nuevaDireccion,
                                      String nuevoDistrito, String nuevoEstado) {
        Proyecto p = buscarProyecto(nombre);
        if (p == null) return false;
        if (nuevaDireccion != null && !nuevaDireccion.isEmpty())
            p.setDireccion(nuevaDireccion);
        if (nuevoDistrito != null && !nuevoDistrito.isEmpty())
            p.setDistrito(nuevoDistrito);
        if (nuevoEstado != null && !nuevoEstado.isEmpty())
            p.setEstado(nuevoEstado);
        return true;
    }

    public Proyecto buscarProyecto(String nombre) {
        for (int i = 0; i < cantProyectos; i++) {
            if (proyectos[i].getNombre().equalsIgnoreCase(nombre))
                return proyectos[i];
        }
        return null;
    }

    public Proyecto[] getProyectos() {
        Proyecto[] copia = new Proyecto[cantProyectos];
        for (int i = 0; i < cantProyectos; i++) copia[i] = proyectos[i];
        return copia;
    }

    public boolean agregarCliente(Cliente c) {
        if (cantClientes >= MAX) return false;
        if (buscarClientePorDni(c.getDni()) != null) return false;
        clientes[cantClientes] = c;
        cantClientes++;
        return true;
    }

    public boolean eliminarCliente(String dni) {
        for (int i = 0; i < cantClientes; i++) {
            if (clientes[i].getDni().equals(dni)) {
                for (int j = i; j < cantClientes - 1; j++) {
                    clientes[j] = clientes[j + 1];
                }
                cantClientes--;
                clientes[cantClientes] = null;
                return true;
            }
        }
        return false;
    }
  
    public boolean modificarCliente(String dni, String nuevoTelefono,
                                     String nuevoCorreo, String nuevaOcupacion,
                                     double nuevosIngresos) {
        Cliente c = buscarClientePorDni(dni);
        if (c == null) return false;
        if (nuevoTelefono != null && !nuevoTelefono.isEmpty())
            c.setTelefono(nuevoTelefono);
        if (nuevoCorreo != null && !nuevoCorreo.isEmpty())
            c.setCorreo(nuevoCorreo);
        if (nuevaOcupacion != null && !nuevaOcupacion.isEmpty())
            c.setOcupacion(nuevaOcupacion);
        if (nuevosIngresos > 0)
            c.setIngresosMensuales(nuevosIngresos);
        return true;
    }

    public Cliente buscarClientePorDni(String dni) {
        for (int i = 0; i < cantClientes; i++) {
            if (clientes[i].getDni().equals(dni)) return clientes[i];
        }
        return null;
    }

    public Cliente[] getClientes() {
        Cliente[] copia = new Cliente[cantClientes];
        for (int i = 0; i < cantClientes; i++) copia[i] = clientes[i];
        return copia;
    }

    public Reserva registrarReserva(Cliente cliente, Departamento dpto,
                                     double montoSep, String vigencia,
                                     String fechaHoy) {
        if (!esAsesor()) return null;
        if (cantReservas >= MAX) return null;
        if (!dpto.estaDisponible()) return null;
        if (!dpto.reservar()) return null;

        AsesorVenta asesor = (AsesorVenta) empleadoLogueado;
        Reserva r = new Reserva(cliente, dpto, asesor,
                                montoSep, vigencia, fechaHoy);
        reservas[cantReservas] = r;
        cantReservas++;
        cliente.agregarReserva(r);
        return r;
    }

    public Reserva[] getReservas() {
        Reserva[] copia = new Reserva[cantReservas];
        for (int i = 0; i < cantReservas; i++) copia[i] = reservas[i];
        return copia;
    }

    public Venta registrarVenta(Reserva reserva, double precioFinal,
                                 String modalidad, String fechaHoy) {
        if (!esAsesor()) return null;
        if (cantVentas >= MAX) return null;
        if (!reserva.estaVigente()) return null;

        AsesorVenta asesor = (AsesorVenta) empleadoLogueado;
        Venta v = new Venta(reserva, asesor, precioFinal, modalidad, fechaHoy);
        ventas[cantVentas] = v;
        cantVentas++;
        asesor.agregarVenta(v);
        return v;
    }

    public Venta[] getVentas() {
        Venta[] copia = new Venta[cantVentas];
        for (int i = 0; i < cantVentas; i++) copia[i] = ventas[i];
        return copia;
    }

    public boolean agregarAcabado(Acabado a) {
        if (cantAcabados >= MAX) return false;
        acabados[cantAcabados] = a;
        cantAcabados++;
        return true;
    }

    public boolean eliminarAcabado(String nombre) {
        for (int i = 0; i < cantAcabados; i++) {
            if (acabados[i].getNombre().equalsIgnoreCase(nombre)) {
                for (int j = i; j < cantAcabados - 1; j++) {
                    acabados[j] = acabados[j + 1];
                }
                cantAcabados--;
                acabados[cantAcabados] = null;
                return true;
            }
        }
        return false;
    }

    public boolean modificarAcabado(String nombre, String nuevaDescripcion,
                                     double nuevoPrecio, String nuevaCategoria) {
        for (int i = 0; i < cantAcabados; i++) {
            if (acabados[i].getNombre().equalsIgnoreCase(nombre)) {
                if (nuevaDescripcion != null && !nuevaDescripcion.isEmpty())
                    acabados[i].setDescripcion(nuevaDescripcion);
                if (nuevoPrecio > 0)
                    acabados[i].setPrecioAdicional(nuevoPrecio);
                if (nuevaCategoria != null && !nuevaCategoria.isEmpty())
                    acabados[i].setCategoria(nuevaCategoria);
                return true;
            }
        }
        return false;
    }

    public Acabado[] getAcabados() {
        Acabado[] copia = new Acabado[cantAcabados];
        for (int i = 0; i < cantAcabados; i++) copia[i] = acabados[i];
        return copia;
    }
    
    public String reporteIngresos() {
        double totalVendido   = 0;
        double totalPendiente = 0;
        for (int i = 0; i < cantVentas; i++) {
            totalVendido   += ventas[i].getPrecioFinal();
            totalPendiente += ventas[i].calcularSaldo();
        }
        return "=== Reporte de Ingresos ==="
            + "\nTotal ventas    : " + cantVentas
            + "\nIngresos totales: S/ " + totalVendido
            + "\nSaldo pendiente : S/ " + totalPendiente;
    }

    public String reporteVentasPorAsesor() {
        String resultado = "=== Ventas por Asesor ===\n\n";
        for (int i = 0; i < cantEmpleados; i++) {
            if (empleados[i].getRol().equals(Constantes.ROL_ASESOR)) {
                AsesorVenta a = (AsesorVenta) empleados[i];
                double totalVendido = 0;
                resultado += "Asesor: " + a.getDatosCompletos() + "\n";
                for (int j = 0; j < cantVentas; j++) {
                    if (ventas[j].getAsesor().getDni().equals(a.getDni())) {
                        resultado += "  - Dpto "
                            + ventas[j].getReservaOrigen()
                                       .getDepartamento().getCodigo()
                            + " | S/ " + ventas[j].getPrecioFinal()
                            + " | " + ventas[j].getModalidadPago()
                            + " | " + ventas[j].getFechaVenta() + "\n";
                        totalVendido += ventas[j].getPrecioFinal();
                    }
                }
                resultado += "Total vendido: S/ " + totalVendido
                          + " | Comision: S/ " + a.getComision() + "\n\n";
            }
        }
        return resultado;
    }

    public String reporteDptosPorProyecto() {
        String resultado = "=== Departamentos por Proyecto ===\n\n";
        for (int i = 0; i < cantProyectos; i++) {
            Proyecto p = proyectos[i];
            int disp = 0, res = 0, vend = 0;
            resultado += "Proyecto: " + p.getNombre()
                      + " | " + p.getDistrito()
                      + " | " + p.getEstado() + "\n";
            for (Departamento d : p.getDepartamentos()) {
                if (d.getEstado().equals(Constantes.DPTO_DISPONIBLE)) disp++;
                else if (d.getEstado().equals(Constantes.DPTO_RESERVADO)) res++;
                else vend++;
                resultado += "  " + d.toString() + "\n";
            }
            resultado += "  Resumen: " + disp + " disponibles | "
                      + res + " reservados | " + vend + " vendidos\n\n";
        }
        return resultado;
    }

    public String reportePorcentajeVentas() {
        Proyecto[] copia = new Proyecto[cantProyectos];
        for (int i = 0; i < cantProyectos; i++) copia[i] = proyectos[i];

        // Burbuja descendente por porcentaje
        for (int i = 0; i < cantProyectos - 1; i++) {
            for (int j = 0; j < cantProyectos - 1 - i; j++) {
                if (copia[j].getPorcentajeVentas()
                        < copia[j + 1].getPorcentajeVentas()) {
                    Proyecto temp = copia[j];
                    copia[j]      = copia[j + 1];
                    copia[j + 1]  = temp;
                }
            }
        }

        String resultado = "=== Proyectos por % de Ventas ===\n\n";
        for (int i = 0; i < cantProyectos; i++) {
            resultado += (i + 1) + ". " + copia[i].getNombre()
                      + " - " + copia[i].getPorcentajeVentas() + "% vendido"
                      + " | " + copia[i].getCantDepartamentos()
                      + " dptos total\n";
        }
        return resultado;
    }

    public String reporteGeneral() {
        String resultado = "===== REPORTE GENERAL =====\n\n";
        for (int i = 0; i < cantEmpleados; i++) {
            resultado += empleados[i].generarReporte() + "\n\n";
        }
        for (int i = 0; i < cantClientes; i++) {
            resultado += clientes[i].generarReporte() + "\n\n";
        }
        return resultado;
    }
}
