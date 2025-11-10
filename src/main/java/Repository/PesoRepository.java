package Repository;

import Config.ConfigDB;
import Model.Peso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PesoRepository {

    public Peso crear(Peso peso) {
        String sql = "INSERT INTO peso (pesoNacimiento, pesoActual) VALUES (?, ?)";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, peso.getPesoNacimiento());
            stmt.setDouble(2, peso.getPesoActual());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    peso.idPeso = rs.getInt(1);
                }
            }

            return peso;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear peso: " + e.getMessage(), e);
        }
    }

    public Peso buscarPorId(int idPeso) {
        String sql = "SELECT * FROM peso WHERE idPeso = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPeso);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPeso(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar peso: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Peso> obtenerTodos() {
        List<Peso> pesos = new ArrayList<>();
        String sql = "SELECT * FROM peso";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pesos.add(mapearPeso(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener pesos: " + e.getMessage(), e);
        }

        return pesos;
    }

    public Peso actualizar(Peso peso) {
        String sql = "UPDATE Peso SET pesoNacimiento = ?, pesoActual = ? WHERE idPeso = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, peso.getPesoNacimiento());
            stmt.setDouble(2, peso.getPesoActual());
            stmt.setInt(3, peso.getIdPeso());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return peso;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar peso: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean eliminar(int idPeso) {
        String sql = "DELETE FROM peso WHERE idPeso = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPeso);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar peso: " + e.getMessage(), e);
        }
    }

    public int obtenerTotal() {
        String sql = "SELECT COUNT(*) FROM peso";

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

    private Peso mapearPeso(ResultSet rs) throws SQLException {
        Peso peso = new Peso();
        peso.idPeso = rs.getInt("idPeso");
        peso.pesoNacimiento = rs.getLong("pesoNacimiento");
        peso.pesoActual = rs.getDouble("pesoActual");
        return peso;
    }
}