public class Dijkstra {

    public static ResultadoDijkstra calcular(Grafo G, int v) {
        int n = G.cantidadUsuarios();

        if (v < 0 || v >= n) {
            throw new IllegalArgumentException("El usuario de origen no existe");
        }

        StringBuilder decisiones = new StringBuilder();

        // PASO 1: Visitados = {v}
        boolean[] visitados = new boolean[n];
        visitados[v] = true;

        // PASO 2: Grafo auxiliar A, con los mismos usuarios
        Grafo A = new Grafo(G.nombres());

        // Guarda el usuario anterior para reconstruir la cadena
        int[] anterior = new int[n];
        for (int i = 0; i < n; i++) {
            anterior[i] = -1;
        }

        // Copiamos los vinculos directos desde el origen
        for (int p = 0; p < n; p++) {
            if (p != v && G.existeArista(v, p)) {
                A.agregarArista(v, p, G.pesoArista(v, p));
                anterior[p] = v;
                decisiones.append("Camino inicial a ").append(G.nombre(p))
                        .append(": ").append(G.pesoArista(v, p))
                        .append(" minutos\n");
            }
        }

        // Pendientes = Vertices(G) - Visitados
        boolean[] pendientes = new boolean[n];
        for (int p = 0; p < n; p++) {
            pendientes[p] = !visitados[p];
        }

        // PASO 3: Elegir el pendiente de menor tiempo y actualizar caminos
        while (hayPendientes(pendientes)) {
            int w = -1;
            long menor = Long.MAX_VALUE;

            // Funcion de seleccion Greedy
            for (int p = 0; p < n; p++) {
                if (pendientes[p] && A.existeArista(v, p) &&
                        A.pesoArista(v, p) < menor) {
                    menor = A.pesoArista(v, p);
                    w = p;
                }
            }

            // Los pendientes restantes no se pueden alcanzar
            if (w == -1) {
                decisiones.append("No quedan usuarios alcanzables desde el origen.\n");
                break;
            }

            visitados[w] = true;
            pendientes[w] = false;
            decisiones.append("Se selecciona ").append(G.nombre(w))
                    .append(" con tiempo acumulado de ").append(menor)
                    .append(" minutos\n");

            // auxPendientes = Pendientes (igual al pseudocodigo)
            boolean[] auxPendientes = pendientes.clone();

            for (int p = 0; p < n; p++) {
                if (auxPendientes[p]) {
                    auxPendientes[p] = false;

                    // ¿Existe un camino hasta w y un vinculo entre w y p?
                    if (A.existeArista(v, w) && G.existeArista(w, p)) {
                        long nuevoTiempo = A.pesoArista(v, w) + G.pesoArista(w, p);

                        if (A.existeArista(v, p)) {
                            if (nuevoTiempo < A.pesoArista(v, p)) {
                                decisiones.append("  Mejora hacia ").append(G.nombre(p))
                                        .append(": ").append(A.pesoArista(v, p))
                                        .append(" -> ").append(nuevoTiempo)
                                        .append(" minutos\n");
                                A.agregarArista(v, p, nuevoTiempo);
                                anterior[p] = w;
                            }
                        } else {
                            A.agregarArista(v, p, nuevoTiempo);
                            anterior[p] = w;
                            decisiones.append("  Nuevo camino hacia ").append(G.nombre(p))
                                    .append(": ").append(nuevoTiempo)
                                    .append(" minutos\n");
                        }
                    }
                }
            }
        }

        return new ResultadoDijkstra(G, A, v, anterior, decisiones.toString());
    }

    private static boolean hayPendientes(boolean[] pendientes) {
        for (int i = 0; i < pendientes.length; i++) {
            if (pendientes[i]) {
                return true;
            }
        }
        return false;
    }
}