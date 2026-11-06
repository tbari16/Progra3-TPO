package algoritmos;

public class AlgoritmoFloyd {

    public static ResultadoFloyd calcular(Grafo G) {

        int n = G.cantidadUsuarios();

        long[][] distancias = new long[n][n];
        int[][] siguiente = new int[n][n];


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                siguiente[i][j] = -1;

                if (i == j) {

                    distancias[i][j] = 0;
                    siguiente[i][j] = j;

                }

                else if (G.existeArista(i, j)) {
                    distancias[i][j] = G.pesoArista(i, j);
                    siguiente[i][j] = j;

                }

                else {

                    distancias[i][j] = Long.MAX_VALUE;

                }


            }

        }


        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {


                    if (distancias[i][k] != Long.MAX_VALUE && distancias[k][j] != Long.MAX_VALUE) {

                        long nuevoTiempo = distancias[i][k] + distancias[k][j];

                        if (nuevoTiempo < distancias[i][j]) {

                            distancias[i][j] = nuevoTiempo;
                            siguiente[i][j] = siguiente[i][k];

                        }
                    }


                }
            }
        }

        return new ResultadoFloyd(G, distancias, siguiente);


    }




}
