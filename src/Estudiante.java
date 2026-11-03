public class Estudiante {
    private int id;
    private String nombre;
    private String[] habilidades;

    public Estudiante(int id, String nombre, String[] habilidades) {
        this.id = id;
        this.nombre = nombre;
        this.habilidades = habilidades;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String[] getHabilidades() {
        return habilidades;
    }

    public boolean tieneHabilidad(String habilidad) {
        for (String h : habilidades) {
            if (h.equalsIgnoreCase(habilidad)) {
                return true;
            }
        }
        return false;
    }
}
