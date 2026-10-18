import java.time.LocalDateTime;
import java.util.ArrayList;

public class PruebasOrdenamientoPublicaciones {
    static int total = 0;
    static int correctas = 0;

    static Publicacion p(int id, int dia, int hora, int reacciones, int comentarios) {
        return new Publicacion(id, "Estudiante",
                LocalDateTime.of(2026, 10, dia, hora, 0),
                reacciones, comentarios);
    }

    static ArrayList<Publicacion> lista(Publicacion... datos) {ArrayList<Publicacion> publicaciones = new ArrayList<>();
        for (Publicacion p : datos) {
            publicaciones.add(p);
        }
        return publicaciones;
    }

    static void probar(String nombre, ArrayList<Publicacion> datos, boolean porRelevancia, int[] esperados) {
        total++;
        ArrayList<Publicacion> resultado;
        if (porRelevancia) {
            resultado = Publicacion.ordenarPorRelevancia(datos);
        } else {
            resultado = Publicacion.ordenarPorFecha(datos);
        }
        boolean correcto = resultado.size() == esperados.length;
        if (correcto) {
            for (int i = 0; i < esperados.length; i++) {
                if (resultado.get(i).getId() != esperados[i]) {
                    correcto = false;
                    break;
                }
            }
        }
        if (correcto) {
            correctas++;
            System.out.println("OK: " + nombre);
        } else {
            System.out.println("ERROR: " + nombre);
            System.out.print("Esperado: ");
            for (int id : esperados) {
                System.out.print(id + " ");
            }
            System.out.print("\nObtenido: ");
            for (Publicacion p : resultado) {
                System.out.print(p.getId() + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // PRUEBA 1: Fechas diferentes
        probar("1. Fechas diferentes",
                lista(p(1, 10, 9, 2, 0),
                        p(2, 12, 9, 0, 0),
                        p(3, 11, 9, 1, 0)),
                false, new int[] {2, 3, 1});

        // PRUEBA 2: Misma fecha, ID menor primero
        probar("2. Igual fecha y hora: desempate por ID",
                lista(p(3, 15, 18, 20, 5),
                        p(2, 15, 18, 15, 10)),
                false, new int[] {2, 3});

        // PRUEBA 3: Mismo dia, distinta hora
        probar("3. Mismo dia, distinta hora",
                lista(p(1, 15, 18, 0, 0),
                        p(2, 15, 9, 0, 0),
                        p(3, 15, 23, 0, 0)),
                false, new int[] {3, 1, 2});

        // PRUEBA 4: Relevancias diferentes
        probar("4. Relevancias diferentes",
                lista(p(1, 15, 9, 1, 0),
                        p(2, 10, 9, 6, 0),
                        p(3, 12, 9, 2, 1)),
                true, new int[] {2, 3, 1});

        // PRUEBA 5: Igual relevancia, desempate por fecha
        probar("5. Igual relevancia: desempate por fecha",
                lista(p(1, 10, 9, 5, 0),
                        p(2, 12, 9, 2, 3)),
                true, new int[] {2, 1});

        // PRUEBA 6: Igual relevancia y fecha, desempate por ID
        probar("6. Igual relevancia y fecha: desempate por ID",
                lista(p(8, 12, 9, 4, 1),
                        p(3, 12, 9, 2, 3),
                        p(5, 12, 9, 1, 4)),
                true, new int[] {3, 5, 8});

        // PRUEBAS 7 Y 8: Publicaciones originales del Main
        ArrayList<Publicacion> ejemploMain = lista(
                new Publicacion(3, "Ana", LocalDateTime.of(2026, 10, 15, 18, 30), 20, 5),
                new Publicacion(1, "Juan", LocalDateTime.of(2026, 10, 14, 12, 0), 30, 5),
                new Publicacion(2, "Sofia", LocalDateTime.of(2026, 10, 15, 18, 30), 15, 10),
                new Publicacion(4, "Pedro", LocalDateTime.of(2026, 10, 13, 10, 0), 10, 2)
        );
        probar("7. Datos del Main ordenados por fecha",
                ejemploMain, false, new int[] {2, 3, 1, 4});
        probar("8. Datos del Main ordenados por relevancia",
                ejemploMain, true, new int[] {1, 2, 3, 4});

        // PRUEBA 9: Lista vacia
        probar("9. Lista vacia",
                lista(), false, new int[] {});

        // PRUEBA 10: Una sola publicacion
        probar("10. Una sola publicacion",
                lista(p(7, 12, 9, 0, 0)),
                true, new int[] {7});

        // PRUEBA 11: Verificar que la lista original no cambie
        total++;
        ArrayList<Publicacion> original = lista(
                p(3, 15, 18, 20, 5),
                p(1, 14, 12, 30, 5),
                p(2, 15, 18, 15, 10));

        Publicacion.ordenarPorFecha(original);
        Publicacion.ordenarPorRelevancia(original);
        if (original.get(0).getId() == 3 &&
                original.get(1).getId() == 1 &&
                original.get(2).getId() == 2) {

            correctas++;
            System.out.println("OK: 11. La lista original no cambia");
        } else {
            System.out.println("ERROR: 11. La lista original no cambia");
        }

        // PRUEBA 12: Mil publicaciones con igual relevancia y fecha
        ArrayList<Publicacion> milPublicaciones = new ArrayList<>();
        int[] esperados = new int[1000];
        for (int id = 1000; id >= 1; id--) {
            milPublicaciones.add(p(id, 15, 18, 5, 0));
        }
        for (int i = 0; i < 1000; i++) {
            esperados[i] = i + 1;
        }
        probar("12. Mil publicaciones con empate", milPublicaciones, true, esperados);
        System.out.println("\nPruebas correctas: " + correctas + " de " + total);
        if (correctas != total) {
            throw new AssertionError("Hay pruebas que fallaron");
        }
    }
}
