import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<Publicacion> publicaciones = new ArrayList<>();

        publicaciones.add(new Publicacion(3, "Ana", LocalDateTime.of(2026, 10, 15, 18, 30), 20, 5));
        publicaciones.add(new Publicacion(1, "Juan", LocalDateTime.of(2026, 10, 14, 12, 0), 30, 5));
        publicaciones.add(new Publicacion(2, "Sofia", LocalDateTime.of(2026, 10, 15, 18, 30), 15, 10));
        publicaciones.add(new Publicacion(4, "Pedro", LocalDateTime.of(2026, 10, 13, 10, 0), 10, 2));

        Scanner teclado = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n--- MURO DE PUBLICACIONES ---");
            System.out.println("1. Mostrar publicaciones originales");
            System.out.println("2. Ordenar por fecha (mas recientes primero)");
            System.out.println("3. Ordenar por relevancia (mayor primero)");
            System.out.println("4. Salir");
            System.out.print("Elegi una opcion: ");

            if (!teclado.hasNextInt()) {
                System.out.println("Ingresa un numero del 1 al 4.");
                teclado.next();
                continue;
            }

            opcion = teclado.nextInt();

            if (opcion == 1) {
                System.out.println("\nPUBLICACIONES ORIGINALES:");
                mostrarPublicaciones(publicaciones);
            } else if (opcion == 2) {
                System.out.println("\nORDENADAS POR FECHA:");
                ArrayList<Publicacion> porFecha = Publicacion.ordenarPorFecha(publicaciones);
                mostrarPublicaciones(porFecha);
            } else if (opcion == 3) {
                System.out.println("\nORDENADAS POR RELEVANCIA:");
                ArrayList<Publicacion> porRelevancia = Publicacion.ordenarPorRelevancia(publicaciones);
                mostrarPublicaciones(porRelevancia);
            } else if (opcion == 4) {
                System.out.println("Saliendo del muro...");
            } else {
                System.out.println("Opcion incorrecta.");
            }

        } while (opcion != 4);

        teclado.close();
    }

    private static void mostrarPublicaciones(ArrayList<Publicacion> publicaciones) {
        if (publicaciones.isEmpty()) {
            System.out.println("No hay publicaciones para mostrar.");
        } else {
            for (Publicacion p : publicaciones) {
                System.out.println(p);
            }
        }
    }
}
