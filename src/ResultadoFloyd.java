public class ResultadoFloyd {

    private Grafo G;
    private long[][] distancias;
    private int[][] siguiente;

    public ResultadoFloyd(Grafo G, long[][] distancias, int[][] siguiente) {
        this.G = G;
        this.distancias = distancias;
        this.siguiente = siguiente;
    }

    public long getTiempo(int origen, int destino) {
        if (origen < 0 || origen >= G.cantidadUsuarios() ||
                destino < 0 || destino >= G.cantidadUsuarios()) {
            throw new IllegalArgumentException("Usuario inexistente");
        }

        if (distancias[origen][destino] == Long.MAX_VALUE) {
            return -1;
        }

        return distancias[origen][destino];
    }

    public String getCadena(int origen, int destino) {
        if (getTiempo(origen, destino) == -1) {
            return "No existe una cadena de difusion";
        }

        StringBuilder cadena = new StringBuilder();
        int actual = origen;

        cadena.append(G.nombre(actual));

        if (origen == destino) {
            return cadena.toString();
        }

        int cantidad = 0;

        while (actual != destino && cantidad < G.cantidadUsuarios()) {
            actual = siguiente[actual][destino];

            if (actual == -1) {
                return "No existe una cadena de difusion";
            }

            cadena.append(" -> ").append(G.nombre(actual));
            cantidad++;
        }

        if (actual != destino) {
            return "No se pudo reconstruir la cadena";
        }

        return cadena.toString();
    }


}
