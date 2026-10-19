public class Iniciativa {
    private int id;
    private int costo;
    private int impacto;

    public Iniciativa(int id, int costo, int impacto) {
        this.id = id;
        this.costo = costo;
        this.impacto = impacto;
    }

    public int getId() {
        return id;
    }

    public int getCosto() {
        return costo;
    }

    public int getImpacto() {
        return impacto;
    }
}
