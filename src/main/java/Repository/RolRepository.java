package Repository;

import Config.ConfigDB;
import Model.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolRepository {

    public Rol crear(Rol rol) {
        String sql = "INSERT INTO rol (nombre) VALUES (?)";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, rol.getNombre());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    rol.idRol = rs.getInt(1);
                }
            }

            return rol;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear rol: " + e.getMessage(), e);
        }
    }

    public Rol buscarPorId(int idRol) {
        String sql = "SELECT * FROM rol WHERE idRol = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRol);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRol(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar rol: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Rol> obtenerTodos() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM rol";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                roles.add(mapearRol(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener roles: " + e.getMessage(), e);
        }

        return roles;
    }

    public Rol actualizar(Rol rol) {
        String sql = "UPDATE rol SET nombre = ? WHERE idRol = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rol.getNombre());
            stmt.setInt(2, rol.getIdRol());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return rol;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar rol: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean eliminar(int idRol) {
        String sql = "DELETE FROM rol WHERE idRol = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRol);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar rol: " + e.getMessage(), e);
        }
    }

    public Rol buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM rol WHERE nombre = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRol(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar rol por nombre: " + e.getMessage(), e);
        }

        return null;
    }

    public int obtenerTotal() {
        String sql = "SELECT COUNT(*) FROM rol";

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

    private Rol mapearRol(ResultSet rs) throws SQLException {
        Rol rol = new Rol();
        rol.idRol = rs.getInt("idRol");
        rol.nombre = rs.getString("nombre");
        return rol;
    }
}