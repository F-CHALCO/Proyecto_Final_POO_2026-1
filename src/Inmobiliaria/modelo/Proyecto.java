
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class Proyecto {
    private String nombre;
    private String direccion;
    private String distrito;
    private int    numPisos;
    private String fechaInicio;
    private String fechaEntrega;
    private String estado; 

   
    private Departamento[] departamentos;
    private int            cantDepartamentos;
    private static final int MAX_DPTOS = 200;

    public Proyecto(String nombre, String direccion, String distrito,
                    int numPisos, String fechaInicio, String fechaEntrega) {
        this.nombre            = nombre;
        this.direccion         = direccion;
        this.distrito          = distrito;
        this.numPisos          = numPisos;
        this.fechaInicio       = fechaInicio;
        this.fechaEntrega      = fechaEntrega;
        this.estado            = Constantes.PROY_EN_PLANOS;
        this.departamentos     = new Departamento[MAX_DPTOS];
        this.cantDepartamentos = 0;
    }

    public boolean agregarDepartamento(Departamento d) {
        if (cantDepartamentos >= MAX_DPTOS) return false;
        departamentos[cantDepartamentos] = d;
        cantDepartamentos++;
        return true;
    }

   
    public boolean eliminarDepartamento(String codigo) {
        for (int i = 0; i < cantDepartamentos; i++) {
            if (departamentos[i].getCodigo().equals(codigo)) {
                for (int j = i; j < cantDepartamentos - 1; j++) {
                    departamentos[j] = departamentos[j + 1];
                }
                cantDepartamentos--;
                departamentos[cantDepartamentos] = null;
                return true;
            }
        }
        return false;
    }

    
    public Departamento buscarDepartamento(String codigo) {
        for (int i = 0; i < cantDepartamentos; i++) {
            if (departamentos[i].getCodigo().equals(codigo)) {
                return departamentos[i];
            }
        }
        return null;
    }

    public Departamento[] getDepartamentosDisponibles() {
        int count = 0;
        for (int i = 0; i < cantDepartamentos; i++) {
            if (departamentos[i].estaDisponible()) count++;
        }
        Departamento[] resultado = new Departamento[count];
        int idx = 0;
        for (int i = 0; i < cantDepartamentos; i++) {
            if (departamentos[i].estaDisponible()) {
                resultado[idx] = departamentos[i];
                idx++;
            }
        }
        return resultado;
    }

    public double getPorcentajeVentas() {
        if (cantDepartamentos == 0) return 0;
        int vendidos = 0;
        for (int i = 0; i < cantDepartamentos; i++) {
            if (departamentos[i].getEstado().equals(Constantes.DPTO_VENDIDO)) {
                vendidos++;
            }
        }
        return (vendidos * 100.0) / cantDepartamentos;
    }

    public Departamento[] getDepartamentos() {
        Departamento[] copia = new Departamento[cantDepartamentos];
        for (int i = 0; i < cantDepartamentos; i++) {
            copia[i] = departamentos[i];
        }
        return copia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public int getNumPisos() {
        return numPisos;
    }

    public void setNumPisos(int numPisos) {
        this.numPisos = numPisos;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantDepartamentos() {
        return cantDepartamentos;
    }

    public void setCantDepartamentos(int cantDepartamentos) {
        this.cantDepartamentos = cantDepartamentos;
    }   

    @Override
    public String toString() {
        return nombre + " - " + distrito + " | " + estado +
               " | Dptos: " + cantDepartamentos;
    }
}
