package Repository;

import Model.Usuario;
import Model.Rol;
import Config.ConfigDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private Connection connection;
    private RolRepository rolRepository;

    // ✅ CORREGIDO: Constructor sin parámetros
    public UsuarioRepository() {
        try {
            this.connection = ConfigDB.getDataSource().getConnection();
            this.rolRepository = new RolRepository();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario crear(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nombre_usuario, contrasena, correo, telefono, id_rol, activo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getContrasena());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getTelefono());
            stmt.setInt(5, usuario.getRol().getIdRol());
            stmt.setBoolean(6, usuario.isActivo());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.idUsuario = generatedKeys.getInt(1);
                    }
                }
                return usuario;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario obtenerPorId(int idUsuario) {
        String sql = "SELECT * FROM Usuario WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Rol rol = rolRepository.obtenerPorId(rs.getInt("id_rol"));

                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rol,
                        rs.getBoolean("activo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario obtenerPorNombreUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM Usuario WHERE nombre_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Rol rol = rolRepository.obtenerPorId(rs.getInt("id_rol"));

                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rol,
                        rs.getBoolean("activo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Rol rol = rolRepository.obtenerPorId(rs.getInt("id_rol"));

                usuarios.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rol,
                        rs.getBoolean("activo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public List<Usuario> obtenerActivos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario WHERE activo = true";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Rol rol = rolRepository.obtenerPorId(rs.getInt("id_rol"));

                usuarios.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rol,
                        rs.getBoolean("activo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public Usuario actualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET nombre_usuario = ?, contrasena = ?, correo = ?, telefono = ?, id_rol = ?, activo = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getContrasena());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getTelefono());
            stmt.setInt(5, usuario.getRol().getIdRol());
            stmt.setBoolean(6, usuario.isActivo());
            stmt.setInt(7, usuario.getIdUsuario());

            if (stmt.executeUpdate() > 0) {
                return usuario;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean desactivar(int idUsuario) {
        String sql = "UPDATE Usuario SET activo = false WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ AGREGADO: Método validarCredenciales (nombre correcto para el Controller)
    public Usuario validarCredenciales(String nombreUsuario, String contrasena) {
        Usuario usuario = obtenerPorNombreUsuario(nombreUsuario);
        if (usuario != null && usuario.getContrasena().equals(contrasena) && usuario.isActivo()) {
            return usuario;
        }
        return null;
    }

    // Mantener también validarLogin por compatibilidad
    public Usuario validarLogin(String nombreUsuario, String contrasena) {
        return validarCredenciales(nombreUsuario, contrasena);
    }

    // ✅ AGREGADO: Método buscarPorNombre (alias de obtenerPorNombreUsuario)
    public Usuario buscarPorNombre(String nombre) {
        return obtenerPorNombreUsuario(nombre);
    }

    // ✅ AGREGADO: Método buscarPorRol
    public List<Usuario> buscarPorRol(Rol rol) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario WHERE id_rol = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rol.getIdRol());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Rol rolCompleto = rolRepository.obtenerPorId(rs.getInt("id_rol"));

                usuarios.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rolCompleto,
                        rs.getBoolean("activo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}