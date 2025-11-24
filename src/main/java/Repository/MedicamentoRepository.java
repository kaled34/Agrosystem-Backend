package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Config.ConfigDB;
import Model.Medicamento;

public class MedicamentoRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public Medicamento crear(Medicamento medicamento) {
        String sql = "INSERT INTO medicamentos (nombre_medicamento, solucion, dosis, caducidad, via_administracion, composicion, indicaciones, frecuencia_aplicacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, medicamento.getNombreMedicamento());
            stmt.setString(2, medicamento.getSolucion());
            stmt.setFloat(3, medicamento.getDosis());
            stmt.setTimestamp(4, new Timestamp(medicamento.getCaducidad().getTime()));
            stmt.setString(5, medicamento.viaAdministracion);
            stmt.setString(6, medicamento.getComposicion());
            stmt.setString(7, medicamento.getIndicaciones());
            stmt.setString(8, medicamento.getFrecuenciaAplicacion());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        medicamento.idMedicamento = generatedKeys.getInt(1);
                    }
                }
                return medicamento;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Medicamento obtenerPorId(int idMedicamento) {
        String sql = "SELECT * FROM medicamentos WHERE id_medicamento = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMedicamento(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Medicamento obtenerPorNombre(String nombre) {
        String sql = "SELECT * FROM medicamentos WHERE nombre_medicamento = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMedicamento(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Medicamento> obtenerTodos() {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medicamentos.add(mapearMedicamento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentos;
    }

    public List<Medicamento> obtenerProximosACaducar(int diasAnticipacion) {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos WHERE caducidad <= DATE_ADD(NOW(), INTERVAL ? DAY) AND caducidad >= NOW()";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, diasAnticipacion);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    medicamentos.add(mapearMedicamento(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentos;
    }

    public List<Medicamento> obtenerCaducados() {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos WHERE caducidad < NOW()";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medicamentos.add(mapearMedicamento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentos;
    }

    public Medicamento actualizar(Medicamento medicamento) {
        String sql = "UPDATE medicamentos SET nombre_medicamento = ?, dosis = ?, caducidad = ?, composicion = ?, indicaciones = ?, frecuencia_aplicacion = ?, via_administracion = ? WHERE id_medicamento = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, medicamento.getNombreMedicamento());
            stmt.setFloat(2, medicamento.getDosis());
            stmt.setTimestamp(3, new Timestamp(medicamento.getCaducidad().getTime()));
            stmt.setString(4, medicamento.getComposicion());
            stmt.setString(5, medicamento.getIndicaciones());
            stmt.setString(6, medicamento.getFrecuenciaAplicacion());
            stmt.setString(7, medicamento.viaAdministracion);
            stmt.setInt(8, medicamento.getIdMedicamento());

            if (stmt.executeUpdate() > 0) {
                return medicamento;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean eliminar(int idMedicamento) {
        String sql = "DELETE FROM medicamentos WHERE id_medicamento = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamento);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Medicamento mapearMedicamento(ResultSet rs) throws SQLException {
        return new Medicamento(
                rs.getInt("id_medicamento"),
                rs.getString("nombre_medicamento"),
                rs.getString("solucion"),
                rs.getFloat("dosis"),
                new java.util.Date(rs.getTimestamp("caducidad").getTime()),
                rs.getString("via_administracion"),
                rs.getString("composicion"),
                rs.getString("indicaciones"),
                rs.getString("frecuencia_aplicacion")
        );
    }
}