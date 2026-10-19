import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // FUNCIONALIDAD 1: datos originales del proyecto
        ArrayList<Publicacion> publicaciones = new ArrayList<>();
        publicaciones.add(new Publicacion(3, "Ana", LocalDateTime.of(2026, 10, 15, 18, 30), 20, 5));
        publicaciones.add(new Publicacion(1, "Juan", LocalDateTime.of(2026, 10, 14, 12, 0), 30, 5));
        publicaciones.add(new Publicacion(2, "Sofia", LocalDateTime.of(2026, 10, 15, 18, 30), 15, 10));
        publicaciones.add(new Publicacion(4, "Pedro", LocalDateTime.of(2026, 10, 13, 10, 0), 10, 2));

        // FUNCIONALIDAD 2: red de contactos entre los mismos usuarios
        Grafo red = DatosEjemplo.crearRedUsuarios();

        // FUNCIONALIDAD 3: iniciativas para financiar
        Iniciativa[] iniciativas = {
                new Iniciativa(1, 6, 30),
                new Iniciativa(2, 5, 24),
                new Iniciativa(3, 5, 24)
        };
        int presupuesto = 15;

        Scanner teclado = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- RED SOCIAL UNIVERSITARIA ---");
            System.out.println("1. Organizar muro de publicaciones");
            System.out.println("2. Buscar cadena de difusion mas rapida");
            System.out.println("3. Seleccionar iniciativas para financiar");
            System.out.println("4. Salir");
            System.out.print("Elegi una opcion: ");
            opcion = leerNumero(teclado);

            if (opcion == 1) {
                menuPublicaciones(teclado, publicaciones);
            } else if (opcion == 2) {
                menuDifusion(teclado, red);
            } else if (opcion == 3) {
                menuIniciativas(teclado, iniciativas, presupuesto);
            } else if (opcion == 4) {
                System.out.println("Saliendo del programa...");
            } else {
                System.out.println("Opcion incorrecta.");
            }
        } while (opcion != 4);

        teclado.close();
    }

    private static void menuPublicaciones(Scanner teclado, ArrayList<Publicacion> publicaciones) {
        int opcion;
        do {
            System.out.println("\n--- MURO DE PUBLICACIONES ---");
            System.out.println("1. Mostrar publicaciones originales");
            System.out.println("2. Ordenar por fecha (mas recientes primero)");
            System.out.println("3. Ordenar por relevancia (mayor primero)");
            System.out.println("4. Volver al menu principal");
            System.out.print("Elegi una opcion: ");
            opcion = leerNumero(teclado);

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
            } else if (opcion != 4) {
                System.out.println("Opcion incorrecta.");
            }
        } while (opcion != 4);
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

    private static void menuDifusion(Scanner teclado, Grafo red) {
        int opcion;
        do {
            System.out.println("\n--- CADENA DE DIFUSION MAS RAPIDA ---");
            System.out.println("1. Buscar cadena entre dos usuarios");
            System.out.println("2. Mostrar los vinculos y tiempos");
            System.out.println("3. Volver al menu principal");
            System.out.print("Elegi una opcion: ");
            opcion = leerNumero(teclado);

            if (opcion == 1) {
                System.out.println("\nUsuarios disponibles:");
                mostrarUsuarios(red);

                System.out.print("Numero del usuario de origen: ");
                int origen = leerNumero(teclado);
                System.out.print("Numero del usuario de destino: ");
                int destino = leerNumero(teclado);

                if (origen < 0 || origen >= red.cantidadUsuarios() ||
                        destino < 0 || destino >= red.cantidadUsuarios()) {
                    System.out.println("Usuario de origen o destino incorrecto.");
                } else {
                    ResultadoDijkstra resultado = Dijkstra.calcular(red, origen);
                    if (resultado.existeCamino(destino)) {
                        System.out.println("\nCadena mas rapida: " + resultado.obtenerCadena(destino));
                        System.out.println("Tiempo total: " + resultado.obtenerTiempo(destino) + " minutos");
                    } else {
                        System.out.println("No existe una cadena de difusion desde "
                                + red.nombre(origen) + " hasta " + red.nombre(destino) + ".");
                    }

                    System.out.print("¿Mostrar las decisiones de Dijkstra? (1=Si, 2=No): ");
                    int mostrar = leerNumero(teclado);
                    if (mostrar == 1) {
                        System.out.println("\n--- DECISIONES DEL ALGORITMO ---");
                        System.out.print(resultado.obtenerDecisiones());
                    }
                }
            } else if (opcion == 2) {
                System.out.println("\nVINCULOS HABILITADOS (ORIGEN -> DESTINO):");
                for (int i = 0; i < red.cantidadUsuarios(); i++) {
                    for (int j = 0; j < red.cantidadUsuarios(); j++) {
                        if (red.existeArista(i, j)) {
                            System.out.println(red.nombre(i) + " -> " + red.nombre(j)
                                    + ": " + red.pesoArista(i, j) + " minutos");
                        }
                    }
                }
            } else if (opcion != 3) {
                System.out.println("Opcion incorrecta.");
            }
        } while (opcion != 3);
    }

    private static void menuIniciativas(Scanner teclado, Iniciativa[] iniciativas, int presupuesto) {
        int opcion;

        do {
            System.out.println("\n--- SELECCION DE INICIATIVAS ---");
            System.out.println("1. Mostrar iniciativas y presupuesto");
            System.out.println("2. Calcular iniciativas a financiar");
            System.out.println("3. Volver al menu principal");
            System.out.print("Elegi una opcion: ");
            opcion = leerNumero(teclado);

            if (opcion == 1) {
                System.out.println("\nPresupuesto disponible: " + presupuesto);
                System.out.println("Iniciativas:");

                for (int i = 0; i < iniciativas.length; i++) {
                    Iniciativa actual = iniciativas[i];
                    System.out.println("- Iniciativa " + actual.getId()
                            + " | Costo: " + actual.getCosto()
                            + " | Impacto: " + actual.getImpacto());
                }

            } else if (opcion == 2) {
                System.out.println("\n--- RESULTADO DE LA SELECCION ---");
                SeleccionIniciativas.seleccionarIniciativas(iniciativas, presupuesto);

            } else if (opcion != 3) {
                System.out.println("Opcion incorrecta.");
            }

        } while (opcion != 3);
    }

    private static void mostrarUsuarios(Grafo red) {
        for (int i = 0; i < red.cantidadUsuarios(); i++) {
            System.out.println(i + ". " + red.nombre(i));
        }
    }

    private static int leerNumero(Scanner teclado) {
        if (!teclado.hasNextInt()) {
            System.out.println("Debes ingresar un numero.");
            teclado.next();
            return -1;
        }
        return teclado.nextInt();
    }
}
