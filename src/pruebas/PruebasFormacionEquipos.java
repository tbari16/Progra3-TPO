package pruebas;

import algoritmos.FormadorEquiposBacktracking;
import modelo.Estudiante;

import java.util.ArrayList;

public class PruebasFormacionEquipos {

    private static int total = 0;
    private static int correctas = 0;

    private static Estudiante e(int id, String... habilidades) {
        return new Estudiante(id, "modelo.Estudiante " + id, habilidades);
    }

    // Para comparar equipos, ordenamos sus IDs solamente en la prueba.
    private static String clave(ArrayList<Estudiante> equipo) {

        int[] ids = new int[equipo.size()];
        for (int i = 0; i < equipo.size(); i++) {
            ids[i] = equipo.get(i).getId();
        }

        for (int i = 0; i < ids.length; i++) {
            for (int j = i + 1; j < ids.length; j++) {
                if (ids[j] < ids[i]) {
                    int auxiliar = ids[i];
                    ids[i] = ids[j];
                    ids[j] = auxiliar;
                }
            }
        }

        String texto = "";
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                texto += "-";
            }

            texto += ids[i];

        }
        return texto;
    }

    private static void probar(String nombre, Estudiante[] estudiantes, int tamano, String[] habilidades, int[] obligatorios, int[][] incompatibles, String... esperados) {
        total++;
        ArrayList<ArrayList<Estudiante>> resultados = FormadorEquiposBacktracking.formarEquipos(estudiantes, tamano, habilidades, obligatorios, incompatibles);

        boolean correcto = resultados.size() == esperados.length;
        ArrayList<String> claves = new ArrayList<>();
        for (ArrayList<Estudiante> equipo : resultados) {
            String codigo = clave(equipo);

            if (claves.contains(codigo)) {
                correcto = false;
            }

            claves.add(codigo);
        }
        for (String esperado : esperados) {
            if (!claves.contains(esperado)) {
                correcto = false;

            }

        }

        if (correcto) {
            correctas++;
            System.out.println("OK: " + nombre);
        }

        else {
            System.out.println("ERROR: " + nombre);
            System.out.println("  Esperados: " + String.join(", ", esperados));
            System.out.println("  Obtenidos: " + claves);
        }


    }

    private static void pruebaGrande() {
        total++;
        Estudiante[] estudiantes = new Estudiante[12];

        for (int i = 0; i < estudiantes.length; i++) {
            estudiantes[i] = e(i + 1);
        }

        ArrayList<ArrayList<Estudiante>> resultados = FormadorEquiposBacktracking.formarEquipos(estudiantes, 3, new String[0], new int[0], new int[0][0]);
        ArrayList<String> claves = new ArrayList<>();
        boolean correcto = resultados.size() == 220; // C(12,3) = 220

        for (ArrayList<Estudiante> equipo : resultados) {
            String codigo = clave(equipo);

            if (equipo.size() != 3 || claves.contains(codigo)) {
                correcto = false;
            }
            claves.add(codigo);
        }
        if (correcto) {
            correctas++;
            System.out.println("OK: 16. Doce estudiantes, equipos de tres (220 sin repetidos)");
        }
        else {
            System.out.println("ERROR: 16. Doce estudiantes, equipos de tres");
        }


    }

    public static void main(String[] args) {
        Estudiante[] ejemplo = {
                e(1, "Java", "BD"),
                e(2, "Python"),
                e(3, "Java", "Python"),
                e(4, "BD", "Diseno"),
                e(5, "Diseno", "Python"),
                e(6, "Testing")
        };

        String[] requeridas = {"Java", "Python", "BD", "Diseno"};
        int[] obligatorioAna = {1};
        int[][] incompatibles = {{2, 4}};

        probar("1. Ejemplo completo con habilidades, obligatorio e incompatibles",
                ejemplo, 3, requeridas, obligatorioAna, incompatibles,
                "1-2-5", "1-3-4", "1-3-5", "1-4-5", "1-5-6");

        probar("2. Combinaciones sin restricciones, sin permutaciones",
                new Estudiante[] {e(1), e(2), e(3)}, 2,
                new String[0], new int[0], new int[0][0],
                "1-2", "1-3", "2-3");

        probar("3. Habilidades cubiertas entre los integrantes",
                new Estudiante[] {e(1, "Java"), e(2, "BD"), e(3, "Python")}, 2,
                new String[] {"Java", "BD"}, new int[0], new int[0][0], "1-2");

        probar("4. Habilidad requerida que nadie posee",
                ejemplo, 3, new String[] {"Robotica"},
                new int[0], new int[0][0]);

        probar("5. Un integrante obligatorio",
                new Estudiante[] {e(1), e(2), e(3)}, 2,
                new String[0], new int[] {3}, new int[0][0], "1-3", "2-3");

        probar("6. Dos integrantes obligatorios",
                new Estudiante[] {e(1), e(2), e(3)}, 2,
                new String[0], new int[] {1, 3}, new int[0][0], "1-3");

        probar("7. Obligatorios incompatibles (sin solucion)",
                new Estudiante[] {e(1), e(2), e(3)}, 3,
                new String[0], new int[] {1, 2}, new int[][] {{1, 2}});

        probar("8. Pareja incompatible",
                new Estudiante[] {e(1), e(2), e(3)}, 2,
                new String[0], new int[0], new int[][] {{1, 2}}, "1-3", "2-3");

        probar("9. Equipo de un integrante con una habilidad",
                new Estudiante[] {e(1, "Java"), e(2, "Python"), e(3, "Java")}, 1,
                new String[] {"Java"}, new int[0], new int[0][0], "1", "3");

        probar("10. Tamano de equipo mayor a los estudiantes",
                new Estudiante[] {e(1), e(2)}, 3,
                new String[0], new int[0], new int[0][0]);

        probar("11. Sin estudiantes disponibles",
                new Estudiante[0], 2, new String[0], new int[0], new int[0][0]);

        probar("12. Tamano de equipo igual a cero",
                new Estudiante[] {e(1)}, 0, new String[0], new int[0], new int[0][0]);

        probar("13. Obligatorio que no esta entre los estudiantes",
                new Estudiante[] {e(1), e(2)}, 2,
                new String[0], new int[] {3}, new int[0][0]);

        probar("14. Mas obligatorios que lugares",
                new Estudiante[] {e(1), e(2), e(3)}, 2,
                new String[0], new int[] {1, 2, 3}, new int[0][0]);

        probar("15. Orden de entrada diferente: no repite combinaciones",
                new Estudiante[] {e(3), e(1), e(2)}, 2,
                new String[0], new int[0], new int[0][0], "1-2", "1-3", "2-3");

        pruebaGrande();

        System.out.println("\nPruebas correctas: " + correctas + " de " + total);
        if (correctas != total) {
            throw new AssertionError("Hay pruebas de Backtracking que fallaron");
        }
    }


}
