//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

    ArrayList<Publicacion> publicaciones = new ArrayList<>();

    publicaciones.add(new Publicacion(3, "Ana",
            LocalDateTime.of(2026, 10, 15, 18, 30), 20, 5));

    publicaciones.add(new Publicacion(1, "Juan",
            LocalDateTime.of(2026, 10, 14, 12, 0), 30, 5));

    publicaciones.add(new Publicacion(2, "Sofia",
            LocalDateTime.of(2026, 10, 15, 18, 30), 15, 10));

    publicaciones.add(new Publicacion(4, "Pedro",
            LocalDateTime.of(2026, 10, 13, 10, 0), 10, 2));

    System.out.println("ORDENADAS POR FECHA:");

    ArrayList<Publicacion> porFecha = Publicacion.ordenarPorFecha(publicaciones);

    for (Publicacion p : porFecha) {
        System.out.println(p);
    }

    System.out.println("\nORDENADAS POR RELEVANCIA:");

    ArrayList<Publicacion> porRelevancia = Publicacion.ordenarPorRelevancia(publicaciones);

    for (Publicacion p : porRelevancia) {
        System.out.println(p);
    }



}
