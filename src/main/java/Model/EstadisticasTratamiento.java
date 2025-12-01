package Model;

public class EstadisticasTratamiento {
    private int enTratamiento;
    private int sanos;

    public EstadisticasTratamiento(int enTratamiento, int sanos) {
        this.enTratamiento = enTratamiento;
        this.sanos = sanos;
    }

    public int getEnTratamiento() {
        return enTratamiento;
    }

    public void setEnTratamiento(int enTratamiento) {
        this.enTratamiento = enTratamiento;
    }

    public int getSanos() {
        return sanos;
    }

    public void setSanos(int sanos) {
        this.sanos = sanos;
    }
}