public class Grafo {
    private String[] usuarios;
    private long[][] tiempos;

    public Grafo(String[] nombres) {
        usuarios = nombres.clone();
        tiempos = new long[nombres.length][nombres.length];

        // -1 significa que no hay vinculo directo
        for (int i = 0; i < nombres.length; i++) {
            for (int j = 0; j < nombres.length; j++) {
                tiempos[i][j] = -1;
            }
        }
    }

    public int cantidadUsuarios() {
        return usuarios.length;
    }

    public String nombre(int indice) {
        return usuarios[indice];
    }

    public String[] nombres() {
        return usuarios.clone();
    }

    public void agregarArista(int origen, int destino, long minutos) {
        if (origen < 0 || origen >= usuarios.length ||
                destino < 0 || destino >= usuarios.length || minutos <= 0) {
            throw new IllegalArgumentException("Vinculo o tiempo incorrecto");
        }

        tiempos[origen][destino] = minutos;
    }

    public boolean existeArista(int origen, int destino) {
        return tiempos[origen][destino] != -1;
    }

    public long pesoArista(int origen, int destino) {
        return tiempos[origen][destino];
    }
}
