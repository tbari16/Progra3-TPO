import java.util.ArrayList;

public class SeleccionIniciativas {

    public static void seleccionarIniciativas(Iniciativa[] iniciativas, int presupuesto) {
        int cantidad = iniciativas.length;

        // matriz[i][p] guarda el mayor impacto posible usando las primeras i iniciativas
        // con un presupuesto maximo p.
        int[][] matriz = new int[cantidad + 1][presupuesto + 1];

        // Se completa la matriz de abajo hacia arriba (bottom-up).
        for (int i = 1; i <= cantidad; i++) {
            Iniciativa actual = iniciativas[i - 1];

            for (int p = 0; p <= presupuesto; p++) {
                if (actual.getCosto() > p) {
                    // No alcanza el presupuesto para financiar esta iniciativa.
                    matriz[i][p] = matriz[i - 1][p];
                } else {
                    // Opcion 1: no financiar la iniciativa actual.
                    int sinActual = matriz[i - 1][p];

                    // Opcion 2: financiarla y sumar su impacto.
                    int conActual = matriz[i - 1][p - actual.getCosto()]
                            + actual.getImpacto();

                    matriz[i][p] = Math.max(sinActual, conActual);
                }
            }
        }

        // Reconstruccion de la solucion desde la ultima celda de la matriz.
        ArrayList<Iniciativa> seleccionadas = new ArrayList<>();
        int presupuestoActual = presupuesto;

        for (int i = cantidad; i > 0; i--) {
            if (matriz[i][presupuestoActual] != matriz[i - 1][presupuestoActual]) {
                Iniciativa seleccionada = iniciativas[i - 1];
                seleccionadas.add(seleccionada);
                presupuestoActual -= seleccionada.getCosto();
            }
        }

        int costoTotal = 0;
        int impactoTotal = matriz[cantidad][presupuesto];

        System.out.println("Iniciativas seleccionadas:");

        // La reconstruccion se hizo de atras hacia adelante, por eso se imprime al reves.
        for (int i = seleccionadas.size() - 1; i >= 0; i--) {
            Iniciativa iniciativa = seleccionadas.get(i);
            System.out.println("- Iniciativa " + iniciativa.getId()
                    + " | Costo: " + iniciativa.getCosto()
                    + " | Impacto: " + iniciativa.getImpacto());
            costoTotal += iniciativa.getCosto();
        }

        System.out.println("Costo total: " + costoTotal);
        System.out.println("Impacto total: " + impactoTotal);
        System.out.println("Presupuesto restante: " + (presupuesto - costoTotal));
    }
}
