package Model;

/**
 * Representa un usuario del sistema Agrosystem.
 * Esta clase maneja la información de autenticación y datos personales de los
 * usuarios
 * que tienen acceso al sistema de gestión ganadera.
 * 
 * @author Agrosystem Team
 * @version 1.0
 */
public class Usuario {
    /** Identificador único del usuario */
    public int idUsuario;

    /** Nombre de usuario para autenticación */
    public String nombreUsuario;

    /** Contraseña del usuario (debe almacenarse encriptada) */
    public String contrasena;

    /** Correo electrónico del usuario */
    public String correo;

    /** Número de teléfono del usuario */
    public String telefono;

    /** Rol asignado al usuario (define permisos y acceso) */
    public Rol rol;

    /** Estado de activación del usuario */
    public boolean activo;

    /**
     * Constructor por defecto.
     */
    public Usuario() {
    }

    /**
     * Constructor completo para crear un usuario con todos sus atributos.
     * 
     * @param idUsuario     Identificador único del usuario
     * @param nombreUsuario Nombre de usuario para login
     * @param contrasena    Contraseña del usuario
     * @param correo        Correo electrónico
     * @param telefono      Número de teléfono
     * @param rol           Rol asignado al usuario
     * @param activo        Estado de activación (true = activo, false = inactivo)
     */
    public Usuario(int idUsuario, String nombreUsuario, String contrasena, String correo, String telefono, Rol rol,
            boolean activo) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.activo = activo;
    }

    // Getters

    /**
     * Obtiene el ID del usuario.
     * 
     * @return ID del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Obtiene el nombre de usuario.
     * 
     * @return Nombre de usuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return Contraseña
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Obtiene el rol del usuario.
     * 
     * @return Objeto Rol con la información del rol asignado
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * 
     * @return Correo electrónico
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Obtiene el teléfono del usuario.
     * 
     * @return Número de teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    // Setters

    /**
     * Establece el nombre de usuario.
     * 
     * @param nombreUsuario Nuevo nombre de usuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Establece la contraseña del usuario.
     * 
     * @param contrasena Nueva contraseña
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Establece el correo electrónico.
     * 
     * @param correo Nuevo correo electrónico
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Establece el teléfono del usuario.
     * 
     * @param telefono Nuevo número de teléfono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Establece el rol del usuario.
     * 
     * @param rol Nuevo rol a asignar
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Verifica si el usuario está activo.
     * 
     * @return true si está activo, false si está inactivo
     */
    public boolean isActivo() {
        return activo;
    }
}