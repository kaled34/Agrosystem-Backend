package Model;

public class Rol {
    public int idRol;
    public String nombre;
    public Rol(){}

    public Rol(int idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    // Getters
    public int getIdRol() {
        return idRol;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}