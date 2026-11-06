package algoritmos;

import modelo.Estudiante;

import java.util.ArrayList;

public class FormadorEquiposBacktracking {



    public static ArrayList<ArrayList<Estudiante>> formarEquipos(Estudiante[] estudiantes, int tamano, String[] habilidadesRequeridas, int[] obligatorios, int[][] incompatibles) {

        ArrayList<ArrayList<Estudiante>> soluciones = new ArrayList<>();
        ArrayList<Estudiante> equipo = new ArrayList<>();


        if (tamano <= 0 || tamano > estudiantes.length || obligatorios.length > tamano) {
            return soluciones;
        }


        backtracking(estudiantes, 0, tamano, habilidadesRequeridas, obligatorios, incompatibles, equipo, soluciones);

        return soluciones;

    }

    private static void backtracking(Estudiante[] estudiantes, int inicio, int tamano, String[] habilidadesRequeridas, int[] obligatorios, int[][] incompatibles, ArrayList<Estudiante> equipo, ArrayList<ArrayList<Estudiante>> soluciones) {

        for (int i = inicio; i < estudiantes.length; i++) {
            equipo.add(estudiantes[i]);


            if (esFactible(equipo, tamano, obligatorios, incompatibles)) {

                if (esSolucion(equipo, tamano, habilidadesRequeridas, obligatorios)) {
                    soluciones.add(new ArrayList<>(equipo));
                }
                else {
                    backtracking(estudiantes, i + 1, tamano, habilidadesRequeridas, obligatorios, incompatibles, equipo, soluciones);
                }

            }

            equipo.remove(equipo.size() - 1);

        }
    }

    private static boolean esFactible(ArrayList<Estudiante> equipo, int tamano, int[] obligatorios, int[][] incompatibles) {

        if (equipo.size() > tamano) {
            return false;
        }

        for (int[] par : incompatibles) {

            if (contiene(equipo, par[0]) && contiene(equipo, par[1])) {
                return false;
            }

        }

        int faltantes = 0;

        for (int id : obligatorios) {
            if (!contiene(equipo, id)) {
                faltantes++;
            }
        }

        return faltantes <= tamano - equipo.size();
    }

    private static boolean esSolucion(ArrayList<Estudiante> equipo, int tamano, String[] habilidadesRequeridas, int[] obligatorios) {

        if (equipo.size() != tamano) {
            return false;
        }

        for (int id : obligatorios) {

            if (!contiene(equipo, id)) {
                return false;
            }

        }

        for (String requerida : habilidadesRequeridas) {
            boolean cubierta = false;

            for (Estudiante estudiante : equipo) {
                if (estudiante.tieneHabilidad(requerida)) {
                    cubierta = true;
                    break;
                }
            }

            if (!cubierta) {
                return false;
            }

        }

        return true;
    }

    private static boolean contiene(ArrayList<Estudiante> equipo, int id) {

        for (Estudiante estudiante : equipo) {

            if (estudiante.getId() == id) {
                return true;
            }

        }
        return false;
    }


}
