public class PruebasSeleccionIniciativas {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("CASO 1 - Mejor combinacion");
        System.out.println("======================================");

        Iniciativa[] caso1 = {
                new Iniciativa(1, 6, 30),
                new Iniciativa(2, 5, 24),
                new Iniciativa(3, 5, 24)
        };

        System.out.println("Presupuesto: 10");
        System.out.println("Resultado esperado:");
        System.out.println("Iniciativas 2 y 3");
        System.out.println("Costo total: 10");
        System.out.println("Impacto total: 48");
        System.out.println("Presupuesto restante: 0");
        System.out.println();
        System.out.println("Resultado obtenido:");

        SeleccionIniciativas.seleccionarIniciativas(caso1, 10);


        System.out.println("\n\n======================================");
        System.out.println("CASO 2 - Presupuesto insuficiente");
        System.out.println("======================================");

        Iniciativa[] caso2 = {
                new Iniciativa(1, 6, 30),
                new Iniciativa(2, 8, 50),
                new Iniciativa(3, 5, 20)
        };

        System.out.println("Presupuesto: 4");
        System.out.println("Resultado esperado:");
        System.out.println("Ninguna iniciativa");
        System.out.println("Costo total: 0");
        System.out.println("Impacto total: 0");
        System.out.println("Presupuesto restante: 4");
        System.out.println();
        System.out.println("Resultado obtenido:");

        SeleccionIniciativas.seleccionarIniciativas(caso2, 4);


        System.out.println("\n\n======================================");
        System.out.println("CASO 3 - Una iniciativa entra exacta");
        System.out.println("======================================");

        Iniciativa[] caso3 = {
                new Iniciativa(1, 6, 30),
                new Iniciativa(2, 8, 50),
                new Iniciativa(3, 4, 15)
        };

        System.out.println("Presupuesto: 6");
        System.out.println("Resultado esperado:");
        System.out.println("Iniciativa 1");
        System.out.println("Costo total: 6");
        System.out.println("Impacto total: 30");
        System.out.println("Presupuesto restante: 0");
        System.out.println();
        System.out.println("Resultado obtenido:");

        SeleccionIniciativas.seleccionarIniciativas(caso3, 6);


        System.out.println("\n\n======================================");
        System.out.println("CASO 4 - Se pueden financiar todas");
        System.out.println("======================================");

        Iniciativa[] caso4 = {
                new Iniciativa(1, 2, 10),
                new Iniciativa(2, 3, 20),
                new Iniciativa(3, 1, 5)
        };

        System.out.println("Presupuesto: 10");
        System.out.println("Resultado esperado:");
        System.out.println("Iniciativas 1, 2 y 3");
        System.out.println("Costo total: 6");
        System.out.println("Impacto total: 35");
        System.out.println("Presupuesto restante: 4");
        System.out.println();
        System.out.println("Resultado obtenido:");

        SeleccionIniciativas.seleccionarIniciativas(caso4, 10);


        System.out.println("\n\n======================================");
        System.out.println("CASO 5 - Conviene combinar iniciativas");
        System.out.println("======================================");

        Iniciativa[] caso5 = {
                new Iniciativa(1, 9, 40),
                new Iniciativa(2, 5, 28),
                new Iniciativa(3, 5, 27),
                new Iniciativa(4, 2, 8)
        };

        System.out.println("Presupuesto: 10");
        System.out.println("Resultado esperado:");
        System.out.println("Iniciativas 2 y 3");
        System.out.println("Costo total: 10");
        System.out.println("Impacto total: 55");
        System.out.println("Presupuesto restante: 0");
        System.out.println();
        System.out.println("Resultado obtenido:");

        SeleccionIniciativas.seleccionarIniciativas(caso5, 10);
    }
}
