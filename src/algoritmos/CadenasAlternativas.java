package algoritmos;

import java.util.ArrayList;

public class CadenasAlternativas {

    private Grafo grafo;
    private boolean[] visitados;
    private boolean[] excluidos;
    private ArrayList<Integer> caminoActual;
    private ArrayList<Integer> mejorCamino;

    private int destino;
    private long tiempoMaximo;
    private int limiteIntermediarios;
    private int cantidadCadenas;
    private long menorTiempo;

    public CadenasAlternativas(Grafo grafo) {

        if (grafo == null) {

            throw new IllegalArgumentException("El grafo no puede ser null.");

        }

        this.grafo = grafo;
    }

    public void buscarCadenas(int origen, int destino, long tiempoMaximo, int limiteIntermediarios, int[] usuariosExcluidos) {

        int n = grafo.cantidadUsuarios();

        if (origen < 0 || origen >= n || destino < 0 || destino >= n) {
            throw new IllegalArgumentException("El origen o destino no existe.");
        }
        if (tiempoMaximo < 0 || limiteIntermediarios < 0) {
            throw new IllegalArgumentException("Los limites no pueden ser negativos.");
        }

        this.destino = destino;
        this.tiempoMaximo = tiempoMaximo;
        this.limiteIntermediarios = limiteIntermediarios;
        this.visitados = new boolean[n];
        this.excluidos = new boolean[n];
        this.caminoActual = new ArrayList<>();
        this.mejorCamino = new ArrayList<>();
        this.cantidadCadenas = 0;
        this.menorTiempo = Long.MAX_VALUE;

        if (usuariosExcluidos != null) {

            for (int usuario : usuariosExcluidos) {

                if (usuario < 0 || usuario >= n) {
                    throw new IllegalArgumentException("Usuario excluido inexistente: " + usuario);
                }

                excluidos[usuario] = true;
            }

        }

        System.out.println("Origen: " + grafo.nombre(origen) + " | Destino: " + grafo.nombre(destino));
        System.out.println("Tiempo maximo: " + tiempoMaximo + " minutos");
        System.out.println("Maximo de intermediarios: " + limiteIntermediarios);
        System.out.println("Cadenas validas encontradas:");

        if (excluidos[origen] || excluidos[destino]) {
            System.out.println("No hay cadenas validas: el origen o el destino esta excluido.");
            return;
        }

        visitados[origen] = true;
        caminoActual.add(origen);

        backtracking(origen, 0, 0);

        if (cantidadCadenas == 0) {
            System.out.println("No se encontraron cadenas validas.");
        }

        else {

            System.out.println("Cantidad de cadenas: " + cantidadCadenas);
            System.out.println("Cadena mas rapida: " + mostrarCamino(mejorCamino));
            System.out.println("Tiempo minimo: " + menorTiempo + " minutos");

        }


    }

    private void backtracking(int actual, long tiempoActual, int intermediarios) {

        if (actual == destino) {
            cantidadCadenas++;
            System.out.println("  " + cantidadCadenas + ") " + mostrarCamino(caminoActual) + " - " + tiempoActual + " minutos");

            if (tiempoActual < menorTiempo) {
                menorTiempo = tiempoActual;
                mejorCamino = new ArrayList<>(caminoActual);
            }


            return;

        }

        for (int siguiente = 0; siguiente < grafo.cantidadUsuarios(); siguiente++) {

            if (visitados[siguiente] || excluidos[siguiente]) {
                continue;
            }
            if (!grafo.existeArista(actual, siguiente)) {
                continue;
            }

            long tiempoVinculo = grafo.pesoArista(actual, siguiente);

            if (tiempoVinculo > tiempoMaximo - tiempoActual) {
                continue;
            }

            if (siguiente != destino && intermediarios >= limiteIntermediarios) {
                continue;
            }

            visitados[siguiente] = true;
            caminoActual.add(siguiente);

            int nuevosIntermediarios = intermediarios;

            if (siguiente != destino) {
                nuevosIntermediarios++;
            }

            backtracking(siguiente, tiempoActual + tiempoVinculo, nuevosIntermediarios);

            caminoActual.remove(caminoActual.size() - 1);
            visitados[siguiente] = false;

        }


    }

    private String mostrarCamino(ArrayList<Integer> camino) {

        String texto = "";

        for (int i = 0; i < camino.size(); i++) {
            texto += grafo.nombre(camino.get(i));

            if (i < camino.size() - 1) {
                texto += " -> ";
            }

        }
        return texto;
    }

    public int getCantidadCadenas() {
        return cantidadCadenas;
    }


    public long getMenorTiempo() {

        if (cantidadCadenas == 0) {
            return -1;

        }
        else {

            return menorTiempo;

        }

    }

    public ArrayList<Integer> getMejorCamino() {
        return new ArrayList<>(mejorCamino);
    }


}
