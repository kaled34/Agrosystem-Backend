package Model;

/**
 * Representa un rol de usuario en el sistema Agrosystem.
 * Los roles definen los permisos y niveles de acceso de los usuarios en el
 * sistema.
 * 
 * @author Agrosystem Team
 * @version 1.0
 */
public class Rol {
    /** Identificador único del rol */
    public int idRol;

    /**
     * Nombre descriptivo del rol (ej: "Administrador", "Veterinario", "Usuario")
     */
    public String nombre;

    /**
     * Constructor por defecto.
     */
    public Rol() {
    }

    /**
     * Constructor con parámetros para crear un Rol.
     * 
     * @ idRol Identificador único del rol
     * 
     * @param nombre Nombre descriptivo del rol
     */
    public Rol(int idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    // Getters

    /**
     * Obtiene el ID del rol.
     * 
     * @return ID del rol
     */
    public int getIdRol() {
        return idRol;
    }

    /**
     * Obtiene el nombre del rol.
     * 
     * @return Nombre del rol
     */
    public String getNombre() {
        return nombre;
    }

    // Setters

    /**
     * Establece el nombre del rol.
     * 
     * @param nombre Nuevo nombre para el rol
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}