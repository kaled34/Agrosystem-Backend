package Model;


public class Usuario {
    public int idUsuario;
    public String nombreUsuario;
    public String contrasena;
    public String correo;
    public String telefono;
    public Rol rol;
    public boolean activo;

    public Usuario(){}

    public Usuario(int idUsuario, String nombreUsuario, String contrasena, String correo, String telefono, Rol rol, boolean activo) {
        this.idUsuario = idUsuario;
        this.nombreUsuario= nombreUsuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.activo = activo;
    }

    // Getters
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    // Setters
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }
}