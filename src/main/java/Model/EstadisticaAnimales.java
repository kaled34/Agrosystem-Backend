package Model;

public class EstadisticaAnimales {   private int total;
    private int machos;
    private int hembras;

    public EstadisticaAnimales() {}

    public EstadisticaAnimales(int total, int machos, int hembras) {
        this.total = total;
        this.machos = machos;
        this.hembras = hembras;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMachos() {
        return machos;
    }

    public void setMachos(int machos) {
        this.machos = machos;
    }

    public int getHembras() {
        return hembras;
    }

    public void setHembras(int hembras) {
        this.hembras = hembras;
    }
}
