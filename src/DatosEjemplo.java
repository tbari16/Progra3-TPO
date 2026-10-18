public class DatosEjemplo {

    public static Grafo crearRedUsuarios() {
        // Utilizamos los mismos autores que en Publicacion.java / Main.java
        String[] nombres = {"Ana", "Juan", "Sofia", "Pedro"};
        Grafo G = new Grafo(nombres);

        // Origen, destino, minutos (las conexiones tienen sentido)
        G.agregarArista(0, 1, 4);  // Ana -> Juan
        G.agregarArista(0, 2, 12); // Ana -> Sofia
        G.agregarArista(0, 3, 20); // Ana -> Pedro
        G.agregarArista(1, 2, 3);  // Juan -> Sofia
        G.agregarArista(1, 3, 10); // Juan -> Pedro
        G.agregarArista(2, 3, 2);  // Sofia -> Pedro

        return G;
    }
}
