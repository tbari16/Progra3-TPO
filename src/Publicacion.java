
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Publicacion {
    private int id;
    private String autor;
    private LocalDateTime fechaHora;
    private int reacciones;
    private int comentarios;

    public Publicacion(int id, String autor, LocalDateTime fechaHora, int reacciones, int comentarios) {
        this.id = id;
        this.autor = autor;
        this.fechaHora = fechaHora;
        this.reacciones = reacciones;
        this.comentarios = comentarios;
    }

    public int getId() {
        return id;
    }
    public String getAutor() {
        return autor;
    }
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    public int getReacciones() {
        return reacciones;
    }
    public int getComentarios() {
        return comentarios;
    }
    public int getRelevancia() {
        return reacciones + comentarios;
    }


    public static ArrayList<Publicacion> ordenarPorFecha(ArrayList<Publicacion> publicaciones) {
        ArrayList<Publicacion> copia = new ArrayList<>(publicaciones);
        mergeSort(copia, 0, copia.size() - 1, false);
        return copia;
    }

    public static ArrayList<Publicacion> ordenarPorRelevancia(ArrayList<Publicacion> publicaciones) {
        ArrayList<Publicacion> copia = new ArrayList<>(publicaciones);
        mergeSort(copia, 0, copia.size() - 1, true);
        return copia;
    }

    private static void mergeSort(ArrayList<Publicacion> lista, int inicio, int fin, boolean porRelevancia) {
        if (inicio < fin) {
            int medio = (inicio + fin) / 2;
            mergeSort(lista, inicio, medio, porRelevancia);
            mergeSort(lista, medio + 1, fin, porRelevancia);
            merge(lista, inicio, medio, fin, porRelevancia);
        }
    }

    private static void merge(ArrayList<Publicacion> lista, int inicio, int medio, int fin, boolean porRelevancia) {
        ArrayList<Publicacion> auxiliar = new ArrayList<>();
        for (int k = inicio; k <= fin; k++) {
            auxiliar.add(null);
        }
        int i = inicio;
        int j = medio + 1;
        for (int k = 0; k < fin - inicio + 1; k++) {
            if (i <= medio && (j > fin || comparar(lista.get(i), lista.get(j), porRelevancia) <= 0)) {
                auxiliar.set(k, lista.get(i));
                i++;
            }
            else {
                auxiliar.set(k, lista.get(j));
                j++;
            }
        }
        for (int k = 0; k < fin - inicio + 1; k++) {
            lista.set(inicio + k, auxiliar.get(k));
        }
    }

    private static int comparar(Publicacion a, Publicacion b, boolean porRelevancia) {
        if (porRelevancia) {
            if (a.getRelevancia() < b.getRelevancia()) {
                return 1;
            }
            if (a.getRelevancia() > b.getRelevancia()) {
                return -1;
            }

            int resultado = b.getFechaHora().compareTo(a.getFechaHora());
            if (resultado != 0) {
                return resultado;
            }
            if (a.getId() < b.getId()) {
                return -1;
            }
            if (a.getId() > b.getId()) {
                return 1;
            }
            return 0;
        }
        else {
            int resultado = b.getFechaHora().compareTo(a.getFechaHora());
            if(resultado != 0){
                return resultado;
            }
            if (a.getId() < b.getId()) {
                return -1;
            }
            if (a.getId() > b.getId()) {
                return 1;
            }
            return 0;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Autor: " + autor +
                " | Fecha: " + fechaHora +
                " | Reacciones: " + reacciones +
                " | Comentarios: " + comentarios +
                " | Relevancia: " + getRelevancia();
    }

}
