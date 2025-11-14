package Repository;

import Model.Rol;
import Config.ConfigDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public Rol crear(Rol rol) {
        String sql = "INSERT INTO rol (nombre) VALUES (?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, rol.getNombre());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rol.idRol = generatedKeys.getInt(1);
                    }
                }
                return rol;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error al crear rol: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Rol obtenerPorId(int idRol) {
        String sql = "SELECT * FROM rol WHERE idRol = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Rol(
                        rs.getInt("idRol"),
                        rs.getString("nombre")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener rol por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Rol> obtenerTodos() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM rol";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                roles.add(new Rol(
                        rs.getInt("idRol"),
                        rs.getString("nombre")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los roles: " + e.getMessage());
            e.printStackTrace();
        }
        return roles;
    }

    public Rol actualizar(Rol rol) {
        String sql = "UPDATE rol SET nombre = ? WHERE idRol = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, rol.getNombre());
            stmt.setInt(2, rol.getIdRol());

            if (stmt.executeUpdate() > 0) {
                return rol;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error al actualizar rol: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean eliminar(int idRol) {
        String sql = "DELETE FROM rol WHERE idRol = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar rol: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Rol obtenerPorNombre(String nombre) {
        String sql = "SELECT * FROM rol WHERE nombre = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Rol(
                        rs.getInt("idRol"),
                        rs.getString("nombre")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener rol por nombre: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}