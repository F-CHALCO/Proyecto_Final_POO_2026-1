
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class Departamento {
    private String codigo;
    private int numeroPiso;
    private String numeroDpto;
    private double area;
    private int numDormitorios;
    private int numBanos;
    private String tipo;   
    private double precioBase;
    private String estado;
    private Acabado[] acabados;
    private int cantAcabados;
    private static final int MAX_ACABADOS = 10;

    public Departamento(String codigo, int numeroPiso, String numeroDpto,
                        double area, int numDormitorios, int numBanos,
                        String tipo, double precioBase) {
        this.codigo         = codigo;
        this.numeroPiso     = numeroPiso;
        this.numeroDpto     = numeroDpto;
        this.area           = area;
        this.numDormitorios = numDormitorios;
        this.numBanos       = numBanos;
        this.tipo           = tipo;
        this.precioBase     = precioBase;
        this.estado         = Constantes.DPTO_DISPONIBLE;
        this.acabados       = new Acabado[MAX_ACABADOS];
        this.cantAcabados   = 0;
    }

    public boolean reservar() {
        if (!estado.equals(Constantes.DPTO_DISPONIBLE)) return false;
        estado = Constantes.DPTO_RESERVADO;
        return true;
    }

    public boolean vender() {
        if (!estado.equals(Constantes.DPTO_RESERVADO)) return false;
        estado = Constantes.DPTO_VENDIDO;
        return true;
    }

    public boolean agregarAcabado(Acabado a) {
        if (cantAcabados >= MAX_ACABADOS) return false;
        acabados[cantAcabados] = a;
        cantAcabados++;
        return true;
    }

    public double getPrecioTotal() {
        double total = precioBase;
        for (int i = 0; i < cantAcabados; i++) {
            total = total + acabados[i].getPrecio();
        }
        return total;
    }

    public boolean estaDisponible() {
        return estado.equals(Constantes.DPTO_DISPONIBLE);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getNumeroPiso() {
        return numeroPiso;
    }

    public void setNumeroPiso(int numeroPiso) {
        this.numeroPiso = numeroPiso;
    }

    public String getNumeroDpto() {
        return numeroDpto;
    }

    public void setNumeroDpto(String numeroDpto) {
        this.numeroDpto = numeroDpto;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getNumDormitorios() {
        return numDormitorios;
    }

    public void setNumDormitorios(int numDormitorios) {
        this.numDormitorios = numDormitorios;
    }

    public int getNumBanos() {
        return numBanos;
    }

    public void setNumBanos(int numBanos) {
        this.numBanos = numBanos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Acabado[] getAcabados() {
        return acabados;
    }

    public void setAcabados(Acabado[] acabados) {
        this.acabados = acabados;
    }

    public int getCantAcabados() {
        return cantAcabados;
    }

    public void setCantAcabados(int cantAcabados) {
        this.cantAcabados = cantAcabados;
    }


    @Override
    public String toString() {
        return "Dpto " + codigo + " | Piso " + numeroPiso +
               " | " + tipo + " | " + area + "m2 | S/ " +
               getPrecioTotal() + " | " + estado;
    }
}
