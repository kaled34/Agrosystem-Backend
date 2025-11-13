package Repository;

import Model.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolRepository {
    private Connection connection;

    public RolRepository(Connection connection) {
        this.connection = connection;
    }

    // Crear un nuevo rol
    public Rol crear(Rol rol) {
        String sql = "INSERT INTO Rol (nombre_rol) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            e.printStackTrace();
            return null;
        }
    }

    // Obtener rol por ID
    public Rol obtenerPorId(int idRol) {
        String sql = "SELECT * FROM Rol WHERE id_rol = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("nombre_rol")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todos los roles
    public List<Rol> obtenerTodos() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM Rol";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                roles.add(new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("nombre_rol")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    // Actualizar rol
    public Rol actualizar(Rol rol) {
        String sql = "UPDATE Rol SET nombre_rol = ? WHERE id_rol = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, rol.getNombre());
            stmt.setInt(2, rol.getIdRol());

            if (stmt.executeUpdate() > 0) {
                return rol;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Eliminar rol
    public boolean eliminar(int idRol) {
        String sql = "DELETE FROM Rol WHERE id_rol = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String obtenerPorNombre(String nombre) {
        String sql = "SELECT * FROM rol WHERE nombreRol";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            }

        }
    }