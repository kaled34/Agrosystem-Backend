package Repository;

import Config.ConfigDB;
import Model.Enfermedad;
import Model.Analisis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnfermedadRepository {

    public Enfermedad crear(Enfermedad enfermedad) {
        String sql = "INSERT INTO Enfermedad (nombreEnfermedad, descripcionEnfermedad, sintomas, " +
                "cuidadosPreventivos, duracion, diasDuracion, idAnalisis) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, enfermedad.getNombreEnfermedad());
            stmt.setString(2, enfermedad.getDescripcionEnfermedad());
            stmt.setString(3, enfermedad.getSintomas());
            stmt.setString(4, enfermedad.getCuidadosPreventivos());
            stmt.setString(5, enfermedad.getDuracion());
            stmt.setInt(6, enfermedad.getDiasDuracion());
            stmt.setObject(7, enfermedad.getIdAnalisis() != null ? enfermedad.getIdAnalisis().getIdAnalisis() : null);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    enfermedad.idEnfermedad = rs.getInt(1);
                }
            }

            return enfermedad;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear enfermedad: " + e.getMessage(), e);
        }
    }

    public Enfermedad buscarPorId(int idEnfermedad) {
        String sql = "SELECT * FROM Enfermedad WHERE idEnfermedad = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEnfermedad);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEnfermedad(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar enfermedad: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Enfermedad> obtenerTodos() {
        List<Enfermedad> enfermedades = new ArrayList<>();
        String sql = "SELECT * FROM Enfermedad";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                enfermedades.add(mapearEnfermedad(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener enfermedades: " + e.getMessage(), e);
        }

        return enfermedades;
    }

    public Enfermedad actualizar(Enfermedad enfermedad) {
        String sql = "UPDATE Enfermedad SET nombreEnfermedad = ?, descripcionEnfermedad = ?, " +
                "sintomas = ?, cuidadosPreventivos = ?, duracion = ?, diasDuracion = ?, " +
                "idAnalisis = ? WHERE idEnfermedad = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, enfermedad.getNombreEnfermedad());
            stmt.setString(2, enfermedad.getDescripcionEnfermedad());
            stmt.setString(3, enfermedad.getSintomas());
            stmt.setString(4, enfermedad.getCuidadosPreventivos());
            stmt.setString(5, enfermedad.getDuracion());
            stmt.setInt(6, enfermedad.getDiasDuracion());
            stmt.setObject(7, enfermedad.getIdAnalisis() != null ? enfermedad.getIdAnalisis().getIdAnalisis() : null);
            stmt.setInt(8, enfermedad.getIdEnfermedad());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return enfermedad;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar enfermedad: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean eliminar(int idEnfermedad) {
        String sql = "DELETE FROM Enfermedad WHERE idEnfermedad = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEnfermedad);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar enfermedad: " + e.getMessage(), e);
        }
    }

    public List<Enfermedad> buscarPorNombre(String nombre) {
        List<Enfermedad> enfermedades = new ArrayList<>();
        String sql = "SELECT * FROM Enfermedad WHERE nombreEnfermedad LIKE ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    enfermedades.add(mapearEnfermedad(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por nombre: " + e.getMessage(), e);
        }

        return enfermedades;
    }

    public Enfermedad buscarPorNombreExacto(String nombre) {
        String sql = "SELECT * FROM Enfermedad WHERE nombreEnfermedad = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEnfermedad(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por nombre exacto: " + e.getMessage(), e);
        }

        return null;
    }

    public int obtenerTotal() {
        String sql = "SELECT COUNT(*) FROM Enfermedad";

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

    private Enfermedad mapearEnfermedad(ResultSet rs) throws SQLException {
        Enfermedad enfermedad = new Enfermedad();
        enfermedad.idEnfermedad = rs.getInt("idEnfermedad");
        enfermedad.nombreEnfermedad = rs.getString("nombreEnfermedad");
        enfermedad.descripcionEnfermedad = rs.getString("descripcionEnfermedad");
        enfermedad.sintomas = rs.getString("sintomas");
        enfermedad.cuidadosPreventivos = rs.getString("cuidadosPreventivos");
        enfermedad.duracion = rs.getString("duracion");
        enfermedad.diasDuracion = rs.getInt("diasDuracion");

        int idAnalisis = rs.getInt("idAnalisis");
        if (!rs.wasNull()) {
            Analisis analisis = new Analisis();
            analisis.idAnalisis = idAnalisis;
            enfermedad.idAnalisis = analisis;
        }

        return enfermedad;
    }
}