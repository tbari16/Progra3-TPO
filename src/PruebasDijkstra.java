public class PruebasDijkstra {
    static int total = 0;
    static int correctas = 0;

    // Esto compara el tiempo minimo y los usuarios de la cadena
    static void probarCamino(String nombre, Grafo G, int origen, int destino, long tiempoEsperado, String cadenaEsperada) {
        total++;
        ResultadoDijkstra resultado = Dijkstra.calcular(G, origen);

        boolean correcto = resultado.obtenerTiempo(destino) == tiempoEsperado && resultado.obtenerCadena(destino).equals(cadenaEsperada);
        if (correcto) {
            correctas++;
            System.out.println("OK: " + nombre);
        } else {
            System.out.println("ERROR: " + nombre);
            System.out.println("  Esperado: " + cadenaEsperada + " (" + tiempoEsperado + " min)");
            System.out.println("  Obtenido: " + resultado.obtenerCadena(destino)
                    + " (" + resultado.obtenerTiempo(destino) + " min)");
        }
    }

    // Esto lo hicimos para casos donde puede haber mas de una cadena con el mismo tiempo minimo
    static void probarTiempo(String nombre, Grafo G, int origen, int destino, long tiempoEsperado) {
        total++;
        ResultadoDijkstra resultado = Dijkstra.calcular(G, origen);
        if (resultado.obtenerTiempo(destino) == tiempoEsperado) {
            correctas++;
            System.out.println("OK: " + nombre);
        } else {
            System.out.println("ERROR: " + nombre);
            System.out.println("  Esperado: " + tiempoEsperado + " minutos");
            System.out.println("  Obtenido: " + resultado.obtenerTiempo(destino) + " minutos");
        }
    }

    static void probarCondicion(String nombre, boolean condicion) {
        total++;
        if (condicion) {
            correctas++;
            System.out.println("OK: " + nombre);
        } else {
            System.out.println("ERROR: " + nombre);
        }
    }

    //Acá empiezan las pruebas que pensamos con los chicos
    public static void main(String[] args) {
        Grafo red = DatosEjemplo.crearRedUsuarios();

        // PRUEBA 1: gana el camino con varios intermediarios
        probarCamino("1. Ana a Pedro: camino mas corto",
                red, 0, 3, 9, "Ana -> Juan -> Sofia -> Pedro");

        // PRUEBA 2: pasar por Juan mejora el vinculo directo
        probarCamino("2. Ana a Sofia: mejora del tiempo",
                red, 0, 2, 7, "Ana -> Juan -> Sofia");

        // PRUEBA 3: el vinculo directo es el mejor
        probarCamino("3. Ana a Juan: vinculo directo",
                red, 0, 1, 4, "Ana -> Juan");

        // PRUEBA 4: otro origen con camino indirecto
        probarCamino("4. Juan a Pedro: usando a Sofia",
                red, 1, 3, 5, "Juan -> Sofia -> Pedro");

        // PRUEBA 5: camino directo desde otro origen
        probarCamino("5. Sofia a Pedro: camino directo",
                red, 2, 3, 2, "Sofia -> Pedro");

        // PRUEBA 6: destino inalcanzable
        probarCamino("6. Pedro a Ana: no hay cadena",
                red, 3, 0, -1, "No existe cadena");

        // PRUEBA 7: no se puede recorrer un vinculo al reves
        probarCamino("7. Sofia a Juan: sentido de las aristas",
                red, 2, 1, -1, "No existe cadena");

        // PRUEBA 8: el origen es el destino
        probarCamino("8. Ana a Ana: tiempo cero",
                red, 0, 0, 0, "Ana");

        // PRUEBA 9: origen = destino incluso si no tiene salidas
        probarCamino("9. Pedro a Pedro: sin vinculos de salida",
                red, 3, 3, 0, "Pedro");

        // PRUEBA 10: grafo pero sin aristas
        Grafo vacio = new Grafo(new String[] {"A", "B"});
        probarCamino("10. Grafo sin vinculos",
                vacio, 0, 1, -1, "No existe cadena");

        // PRUEBA 11: un solo usuario
        Grafo unico = new Grafo(new String[] {"A"});
        probarCamino("11. Un solo usuario",
                unico, 0, 0, 0, "A");

        // PRUEBA 12: puede haber mas de una cadena optima
        Grafo empate = new Grafo(new String[] {"A", "B", "C", "D"});
        empate.agregarArista(0, 1, 2);
        empate.agregarArista(0, 2, 2);
        empate.agregarArista(1, 3, 3);
        empate.agregarArista(2, 3, 3);
        probarTiempo("12. Dos caminos con el mismo tiempo", empate, 0, 3, 5);

        // PRUEBA 13: un ciclo no genera un recorrido infinito
        Grafo ciclo = new Grafo(new String[] {"A", "B", "C"});
        ciclo.agregarArista(0, 1, 2);
        ciclo.agregarArista(1, 2, 2);
        ciclo.agregarArista(2, 0, 1);
        ciclo.agregarArista(0, 2, 12);
        probarCamino("13. Grafo con un ciclo",
                ciclo, 0, 2, 4, "A -> B -> C");

        // PRUEBA 14: red de mayor tamanio
        String[] nombres = new String[120];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = "U" + i;
        }
        Grafo grande = new Grafo(nombres);
        for (int i = 0; i < nombres.length - 1; i++) {
            grande.agregarArista(i, i + 1, 1);
        }
        probarTiempo("14. Red de 120 usuarios", grande, 0, 119, 119);

        // PRUEBA 15: no permitir tiempos de cero minutos
        boolean rechazoCero = false;
        try {
            red.agregarArista(0, 1, 0);
        } catch (IllegalArgumentException e) {
            rechazoCero = true;
        }
        probarCondicion("15. Rechazar tiempo cero", rechazoCero);

        // PRUEBA 16: no permitir tiempos negativos
        boolean rechazoNegativo = false;
        try {
            red.agregarArista(0, 1, -5);
        } catch (IllegalArgumentException e) {
            rechazoNegativo = true;
        }
        probarCondicion("16. Rechazar tiempo negativo", rechazoNegativo);

        // PRUEBA 17: Dijkstra no debe modificar el grafo original
        Dijkstra.calcular(red, 0);
        boolean sinCambios = red.pesoArista(0, 1) == 4 &&
                            red.pesoArista(0, 2) == 12 &&
                            red.pesoArista(0, 3) == 20 &&
                            !red.existeArista(3, 0);
        probarCondicion("17. El grafo original no cambia", sinCambios);

    }
}
