package pruebas;

import algoritmos.CadenasAlternativas;
import algoritmos.Grafo;

import java.util.ArrayList;

public class PruebasCadenasAlternativas {

    public static void main(String[] args) {
        Grafo grafo = crearGrafoPruebas();

        probar("Caso 1 - Varias cadenas validas", grafo,
                0, 4, 8, 2, new int[]{}, 4, 7, new int[]{0, 2, 3, 4});

        probar("Caso 2 - Usuario excluido", grafo,
                0, 4, 8, 2, new int[]{2}, 2, 8, new int[]{0, 1, 3, 4});

        probar("Caso 3 - No hay cadenas por restricciones", grafo,
                0, 4, 6, 2, new int[]{}, 0, -1, new int[]{});

        probar("Caso 4 - Tres intermediarios permiten la mejor cadena", grafo,
                0, 4, 6, 3, new int[]{}, 1, 6, new int[]{0, 1, 2, 3, 4});

        probar("Caso 5 - Sin intermediarios", grafo,
                0, 4, 10, 0, new int[]{}, 1, 8, new int[]{0, 4});

        probar("Caso 6 - Destino excluido", grafo,
                0, 4, 10, 3, new int[]{4}, 0, -1, new int[]{});

        probar("Caso 7 - Los vinculos tienen direccion", grafo,
                4, 0, 20, 3, new int[]{}, 0, -1, new int[]{});

        probar("Caso 8 - Origen igual al destino", grafo,
                0, 0, 0, 0, new int[]{}, 1, 0, new int[]{0});

        probar("Caso 9 - Origen excluido", grafo,
                0, 4, 10, 3, new int[]{0}, 0, -1, new int[]{});

        probar("Caso 10 - Tiempo limite exacto", grafo,
                0, 4, 7, 2, new int[]{}, 1, 7, new int[]{0, 2, 3, 4});

        Grafo tiemposGrandes = new Grafo(new String[]{"A", "B", "C"});
        tiemposGrandes.agregarArista(0, 1, 3000000000L);
        tiemposGrandes.agregarArista(1, 2, 3000000000L);
        tiemposGrandes.agregarArista(0, 2, 7000000000L);

        probar("Caso 11 - Tiempos long", tiemposGrandes, 0, 2, 6500000000L, 1, new int[]{}, 1, 6000000000L, new int[]{0, 1, 2});
    }

    private static Grafo crearGrafoPruebas() {

        String[] usuarios = {"Ana", "Bruno", "Carla", "Diego", "Elena"};
        Grafo grafo = new Grafo(usuarios);

        grafo.agregarArista(0, 1, 2);
        grafo.agregarArista(0, 2, 4);
        grafo.agregarArista(0, 3, 7);
        grafo.agregarArista(0, 4, 8);
        grafo.agregarArista(1, 2, 1);
        grafo.agregarArista(1, 3, 4);
        grafo.agregarArista(1, 4, 8);
        grafo.agregarArista(2, 3, 1);
        grafo.agregarArista(2, 4, 5);
        grafo.agregarArista(3, 4, 2);
        grafo.agregarArista(3, 1, 1);

        return grafo;

    }

    private static void probar(String nombre, Grafo grafo, int origen, int destino, long tiempoMaximo, int limiteIntermediarios, int[] excluidos, int cantidadEsperada, long tiempoEsperado, int[] caminoEsperado) {

        System.out.println("\n=============================================");
        System.out.println(nombre);
        System.out.println("Esperado: " + cantidadEsperada
                + " cadenas, tiempo minimo " + tiempoEsperado);

        CadenasAlternativas buscador = new CadenasAlternativas(grafo);
        buscador.buscarCadenas(origen, destino, tiempoMaximo, limiteIntermediarios, excluidos);

        ArrayList<Integer> caminoObtenido = buscador.getMejorCamino();
        boolean caminoCorrecto = caminoObtenido.size() == caminoEsperado.length;

        if (caminoCorrecto) {
            for (int i = 0; i < caminoEsperado.length; i++) {
                if (caminoObtenido.get(i) != caminoEsperado[i]) {

                    caminoCorrecto = false;
                    break;
                }

            }
        }

        boolean correcto = buscador.getCantidadCadenas() == cantidadEsperada
                && buscador.getMenorTiempo() == tiempoEsperado
                && caminoCorrecto;

        System.out.println(correcto ? "PRUEBA CORRECTA" : "PRUEBA INCORRECTA");

        if (!correcto) {
            System.out.println("Cantidad obtenida: " + buscador.getCantidadCadenas());
            System.out.println("Tiempo obtenido: " + buscador.getMenorTiempo());
            System.out.println("Mejor camino obtenido: " + caminoObtenido);
            throw new AssertionError("Fallo: " + nombre);
        }
    }


}
