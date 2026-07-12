
package Inmobiliaria.modelo;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class BuscadorDepartamentos {
    private Departamento[] departamentos;
    private int cantidad;

    public BuscadorDepartamentos(Departamento[] dptos, int cantidad) {
        this.cantidad = cantidad;
        this.departamentos = new Departamento[cantidad];
        for (int i = 0; i < cantidad; i++) {
            this.departamentos[i] = dptos[i];
        }
    }

    public Departamento[] ordenarPorPrecioAsc() {
        Departamento[] copia = copiar();
        for (int i = 0; i < cantidad - 1; i++) {
            for (int j = 0; j < cantidad - 1 - i; j++) {
                if (copia[j].getPrecioTotal() > copia[j + 1].getPrecioTotal()) {
                    Departamento temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                }
            }
        }
        return copia;
    }

    public Departamento[] ordenarPorPrecioDesc() {
        Departamento[] copia = copiar();
        for (int i = 0; i < cantidad - 1; i++) {
            for (int j = 0; j < cantidad - 1 - i; j++) {
                if (copia[j].getPrecioTotal() < copia[j + 1].getPrecioTotal()) {
                    Departamento temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                }
            }
        }
        return copia;
    }

    public Departamento[] ordenarPorAreaAsc() {
        Departamento[] copia = copiar();
        for (int i = 0; i < cantidad - 1; i++) {
            for (int j = 0; j < cantidad - 1 - i; j++) {
                if (copia[j].getArea() > copia[j + 1].getArea()) {
                    Departamento temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                }
            }
        }
        return copia;
    }

    public Departamento[] ordenarPorPisoAsc() {
        Departamento[] copia = copiar();
        for (int i = 0; i < cantidad - 1; i++) {
            for (int j = 0; j < cantidad - 1 - i; j++) {
                if (copia[j].getNumeroPiso() > copia[j + 1].getNumeroPiso()) {
                    Departamento temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                }
            }
        }
        return copia;
    }

    public Departamento[] buscarPorTipo(String tipo) {
        int count = 0;
        for (int i = 0; i < cantidad; i++) {
            if (departamentos[i].getTipo().equalsIgnoreCase(tipo)) {
                count++;
            }
        }
        Departamento[] resultado = new Departamento[count];
        int idx = 0;
        for (int i = 0; i < cantidad; i++) {
            if (departamentos[i].getTipo().equalsIgnoreCase(tipo)) {
                resultado[idx] = departamentos[i];
                idx++;
            }
        }
        return resultado;
    }

    public Departamento[] buscarPorRangoPrecio(double minimo, double maximo) {
        int count = 0;
        for (int i = 0; i < cantidad; i++) {
            double precio = departamentos[i].getPrecioTotal();
            if (precio >= minimo && precio <= maximo) {
                count++;
            }
        }
        Departamento[] resultado = new Departamento[count];
        int idx = 0;
        for (int i = 0; i < cantidad; i++) {
            double precio = departamentos[i].getPrecioTotal();
            if (precio >= minimo && precio <= maximo) {
                resultado[idx] = departamentos[i];
                idx++;
            }
        }
        return resultado;
    }

    public Departamento[] buscarPorDormitorios(int numDorm) {
        int count = 0;
        for (int i = 0; i < cantidad; i++) {
            if (departamentos[i].getNumDormitorios() == numDorm) {
                count++;
            }
        }
        Departamento[] resultado = new Departamento[count];
        int idx = 0;
        for (int i = 0; i < cantidad; i++) {
            if (departamentos[i].getNumDormitorios() == numDorm) {
                resultado[idx] = departamentos[i];
                idx++;
            }
        }
        return resultado;
    }

    public Departamento getMasBarato() {
        if (cantidad == 0) return null;
        Departamento min = departamentos[0];
        for (int i = 1; i < cantidad; i++) {
            if (departamentos[i].getPrecioTotal() < min.getPrecioTotal()) {
                min = departamentos[i];
            }
        }
        return min;
    }

    public Departamento getMasCaro() {
        if (cantidad == 0) return null;
        Departamento max = departamentos[0];
        for (int i = 1; i < cantidad; i++) {
            if (departamentos[i].getPrecioTotal() > max.getPrecioTotal()) {
                max = departamentos[i];
            }
        }
        return max;
    }

    public double getPrecioPromedio() {
        if (cantidad == 0) return 0;
        double suma = 0;
        for (int i = 0; i < cantidad; i++) {
            suma = suma + departamentos[i].getPrecioTotal();
        }
        return suma / cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    private Departamento[] copiar() {
        Departamento[] copia = new Departamento[cantidad];
        for (int i = 0; i < cantidad; i++) {
            copia[i] = departamentos[i];
        }
        return copia;
    }
}
