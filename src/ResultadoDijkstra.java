import java.util.ArrayList;

public class ResultadoDijkstra {
    private Grafo G;
    private Grafo A;
    private int origen;
    private int[] anterior;
    private String decisiones;

    public ResultadoDijkstra(Grafo G, Grafo A, int origen,
                             int[] anterior, String decisiones) {
        this.G = G;
        this.A = A;
        this.origen = origen;
        this.anterior = anterior;
        this.decisiones = decisiones;
    }

    public boolean existeCamino(int destino) {
        if (destino < 0 || destino >= G.cantidadUsuarios()) {
            throw new IllegalArgumentException("El usuario de destino no existe");
        }
        return destino == origen || A.existeArista(origen, destino);
    }

    // Devuelve -1 cuando no hay cadena
    public long obtenerTiempo(int destino) {
        if (!existeCamino(destino)) {
            return -1;
        }
        if (destino == origen) {
            return 0;
        }
        return A.pesoArista(origen, destino);
    }

    public String obtenerCadena(int destino) {
        if (!existeCamino(destino)) {
            return "No existe cadena";
        }

        ArrayList<String> invertida = new ArrayList<>();
        int actual = destino;

        // Vamos desde el destino hasta el origen
        while (actual != -1) {
            invertida.add(G.nombre(actual));
            if (actual == origen) {
                break;
            }
            actual = anterior[actual];
        }

        // La mostramos en el orden correcto: origen -> destino
        StringBuilder cadena = new StringBuilder();
        for (int i = invertida.size() - 1; i >= 0; i--) {
            cadena.append(invertida.get(i));
            if (i > 0) {
                cadena.append(" -> ");
            }
        }
        return cadena.toString();
    }

    public String obtenerDecisiones() {
        return decisiones;
    }
}
