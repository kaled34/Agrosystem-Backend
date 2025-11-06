package Service;

import Model.Rol;
import Model.Usuario;
import Repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio.");
        }

        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña del usuario es obligatoria.");
        }

        if (usuario.getRol() == null) {
            throw new IllegalArgumentException("El rol del usuario es obligatorio.");
        }

        if (usuarioRepository.buscarPorNombre(usuario.getNombre()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con el nombre: " + usuario.getNombre());
        }

        return usuarioRepository.crear(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.obtenerTodos();
    }

    public Optional<Usuario> buscarPorId(int id) {
        Usuario usuario = usuarioRepository.buscarPorId(id);
        return Optional.ofNullable(usuario);
    }

    public Optional<Usuario> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser vacío.");
        }
        Usuario usuario = usuarioRepository.buscarPorNombre(nombre);
        return Optional.ofNullable(usuario);
    }

    public List<Usuario> buscarPorRol(Rol rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo.");
        }
        return usuarioRepository.buscarPorRol(rol);
    }

    public Usuario actualizarUsuario(Usuario usuarioActualizado) {
        if (usuarioRepository.buscarPorId(usuarioActualizado.getIdUsuario()) == null) {
            return null;
        }

        if (usuarioActualizado.getNombre() == null || usuarioActualizado.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio.");
        }

        if (usuarioActualizado.getContrasena() == null || usuarioActualizado.getContrasena().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña del usuario es obligatoria.");
        }

        if (usuarioActualizado.getRol() == null) {
            throw new IllegalArgumentException("El rol del usuario es obligatorio.");
        }

        return usuarioRepository.actualizar(usuarioActualizado);
    }

    public boolean eliminarUsuario(int id) {
        return usuarioRepository.eliminar(id);
    }

    public Optional<Usuario> validarCredenciales(String nombre, String contrasena) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser vacío.");
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser vacía.");
        }

        Usuario usuario = usuarioRepository.validarCredenciales(nombre, contrasena);
        return Optional.ofNullable(usuario);
    }
}