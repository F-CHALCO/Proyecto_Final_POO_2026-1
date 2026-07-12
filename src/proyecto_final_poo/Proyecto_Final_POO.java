
package proyecto_final_poo;

import Inmobiliaria.servicio.GestorSistema;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */ 
public class Proyecto_Final_POO {
    public static void main(String[] args) {
        GestorSistema sistema = new GestorSistema();
        imprimir("El sistema ha iniciado correctamente en modo consola.");
    }
    public static void imprimir(String cadena) {
        System.out.println(cadena);
    }
}
