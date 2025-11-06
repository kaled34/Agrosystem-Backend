package Model;

public enum Raza {
    Holstein("Holstein");
    private final String nombre;

    

    Raza(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}