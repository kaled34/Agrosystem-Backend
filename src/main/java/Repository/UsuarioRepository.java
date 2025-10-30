package Repository;

import Model.Rol;
import Model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private List<Usuario> usuarios;

    public UsuarioRepository() {
        this.usuarios = new ArrayList<>();
    }

    public Usuario crear(Usuario usuario) {
        usuarios.add(usuario);
        return usuario;
    }

    public Usuario buscarPorId(int idUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdUsuario() == idUsuario) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }

    public Usuario actualizar(Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdUsuario() == usuarioActualizado.getIdUsuario()) {
                usuarios.set(i, usuarioActualizado);
                return usuarioActualizado;
            }
        }
        return null;
    }

    public boolean eliminar(int idUsuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdUsuario() == idUsuario) {
                usuarios.remove(i);
                return true;
            }
        }
        return false;
    }

    public Usuario buscarPorNombre(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> buscarPorRol(Rol rol) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().getIdRol() == rol.getIdRol()) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    public Usuario validarCredenciales(String nombre, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombre) && usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
        }
        return null;
    }

    public int obtenerTotal() {
        return usuarios.size();
    }
}
