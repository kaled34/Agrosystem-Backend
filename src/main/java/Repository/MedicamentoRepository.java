package Repository;

import Config.ConfigDB;
import Model.Medicamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoRepository {

    public Medicamento crear(Medicamento medicamento) {
        String sql = "INSERT INTO medicamento (nombreMedicamento, principioActivo, descripcionMedicamento, " +
                "fechaCaducidad, cantidadMedicamento, viaAdministracion) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, medicamento.getNombreMedicamento());
            stmt.setString(2, medicamento.getPrincipioActivo());
            stmt.setString(3, medicamento.getDescripcionMedicamento());
            stmt.setString(4, medicamento.getFechaCaducidad());
            stmt.setFloat(5, medicamento.getCantidadMedicamento());
            stmt.setString(6, medicamento.getViaAdministracion());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    medicamento.idMedicamento = rs.getInt(1);
                }
            }

            return medicamento;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear medicamento: " + e.getMessage(), e);
        }
    }

    public Medicamento buscarPorId(int idMedicamento) {
        String sql = "SELECT * FROM medicamento WHERE idMedicamento = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedicamento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMedicamento(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar medicamento: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Medicamento> obtenerTodos() {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM medicamento";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medicamentos.add(mapearMedicamento(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener medicamentos: " + e.getMessage(), e);
        }

        return medicamentos;
    }

    public Medicamento actualizar(Medicamento medicamento) {
        String sql = "UPDATE medicamento SET nombreMedicamento = ?, principioActivo = ?, " +
                "descripcionMedicamento = ?, fechaCaducidad = ?, cantidadMedicamento = ?, " +
                "viaAdministracion = ? WHERE idMedicamento = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medicamento.getNombreMedicamento());
            stmt.setString(2, medicamento.getPrincipioActivo());
            stmt.setString(3, medicamento.getDescripcionMedicamento());
            stmt.setString(4, medicamento.getFechaCaducidad());
            stmt.setFloat(5, medicamento.getCantidadMedicamento());
            stmt.setString(6, medicamento.getViaAdministracion());
            stmt.setInt(7, medicamento.getIdMedicamento());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return medicamento;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar medicamento: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean eliminar(int idMedicamento) {
        String sql = "DELETE FROM medicamento WHERE idMedicamento = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedicamento);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar medicamento: " + e.getMessage(), e);
        }
    }

    public List<Medicamento> buscarPorNombre(String nombre) {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM medicamento WHERE nombreMedicamento LIKE ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    medicamentos.add(mapearMedicamento(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por nombre: " + e.getMessage(), e);
        }

        return medicamentos;
    }

    public int obtenerTotal() {
        String sql = "SELECT COUNT(*) FROM medicamento";

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

    private Medicamento mapearMedicamento(ResultSet rs) throws SQLException {
        Medicamento medicamento = new Medicamento();
        medicamento.idMedicamento = rs.getInt("idMedicamento");
        medicamento.nombreMedicamento = rs.getString("nombreMedicamento");
        medicamento.principioActivo = rs.getString("principioActivo");
        medicamento.descripcionMedicamento = rs.getString("descripcionMedicamento");
        medicamento.fechaCaducidad = rs.getString("fechaCaducidad");
        medicamento.cantidadMedicamento = rs.getFloat("cantidadMedicamento");
        medicamento.viaAdministracion = rs.getString("viaAdministracion");
        return medicamento;
    }
}