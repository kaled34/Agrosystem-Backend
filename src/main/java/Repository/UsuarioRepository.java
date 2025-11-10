package Repository;

import Config.ConfigDB;
import Model.Rol;
import Model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    public Usuario crear(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nombre, contrasena, idRol) VALUES (?, ?, ?)";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getContrasena());
            stmt.setInt(3, usuario.getRol().getIdRol());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.idUsuario = rs.getInt(1);
                }
            }

            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear usuario: " + e.getMessage(), e);
        }
    }

    public Usuario buscarPorId(int idUsuario) {
        String sql = "SELECT u.*, r.nombre as nombreRol FROM Usuario u " +
                "INNER JOIN Rol r ON u.idRol = r.idRol " +
                "WHERE u.idUsuario = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre as nombreRol FROM Usuario u " +
                "INNER JOIN Rol r ON u.idRol = r.idRol";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuarios: " + e.getMessage(), e);
        }

        return usuarios;
    }

    public Usuario actualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET nombre = ?, contrasena = ?, idRol = ? WHERE idUsuario = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getContrasena());
            stmt.setInt(3, usuario.getRol().getIdRol());
            stmt.setInt(4, usuario.getIdUsuario());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return usuario;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM Usuario WHERE idUsuario = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    public Usuario buscarPorNombre(String nombre) {
        String sql = "SELECT u.*, r.nombre as nombreRol FROM Usuario u " +
                "INNER JOIN Rol r ON u.idRol = r.idRol " +
                "WHERE u.nombre = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por nombre: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Usuario> buscarPorRol(Rol rol) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre as nombreRol FROM Usuario u " +
                "INNER JOIN Rol r ON u.idRol = r.idRol " +
                "WHERE u.idRol = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rol.getIdRol());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuarios por rol: " + e.getMessage(), e);
        }

        return usuarios;
    }

    public Usuario validarCredenciales(String nombre, String contrasena) {
        String sql = "SELECT u.*, r.nombre as nombreRol FROM Usuario u " +
                "INNER JOIN Rol r ON u.idRol = r.idRol " +
                "WHERE u.nombre = ? AND u.contrasena = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, contrasena);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al validar credenciales: " + e.getMessage(), e);
        }

        return null;
    }

    public int obtenerTotal() {
        String sql = "SELECT COUNT(*) FROM Usuario";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener total: " + e.getMessage(), e);
        }

        return 0;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.idUsuario = rs.getInt("idUsuario");
        usuario.nombre = rs.getString("nombre");
        usuario.contrasena = rs.getString("contrasena");

        Rol rol = new Rol();
        rol.idRol = rs.getInt("idRol");
        rol.nombre = rs.getString("nombreRol");
        usuario.rol = rol;

        return usuario;
    }
}