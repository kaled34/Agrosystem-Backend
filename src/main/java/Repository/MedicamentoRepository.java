package Repository;

import Model.Medicamento;
import Config.ConfigDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoRepository {
    private Connection connection;

    // Constructor sin parámetros que obtiene la conexión del pool
    public MedicamentoRepository() {
        try {
            this.connection = ConfigDB.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ CORREGIDO: Crear un nuevo medicamento - ahora retorna el medicamento
    public Medicamento crear(Medicamento medicamento) {
        String sql = "INSERT INTO Medicamentos (nombre_medicamento, solucion, dosis, caducidad, via_administracion, composicion, indicaciones, frecuencia_aplicacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
                return medicamento; // ✅ CORREGIDO: Retorna medicamento en lugar de null
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Obtener medicamento por ID
    public Medicamento obtenerPorId(int idMedicamento) {
        String sql = "SELECT * FROM Medicamentos WHERE id_medicamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearMedicamento(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener medicamento por nombre
    public Medicamento obtenerPorNombre(String nombre) {
        String sql = "SELECT * FROM Medicamentos WHERE nombre_medicamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearMedicamento(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todos los medicamentos
    public List<Medicamento> obtenerTodos() {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM Medicamentos";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medicamentos.add(mapearMedicamento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentos;
    }

    // Obtener medicamentos próximos a caducar
    public List<Medicamento> obtenerProximosACaducar(int diasAnticipacion) {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM Medicamentos WHERE caducidad <= DATE_ADD(NOW(), INTERVAL ? DAY) AND caducidad >= NOW()";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, diasAnticipacion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                medicamentos.add(mapearMedicamento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentos;
    }

    // Obtener medicamentos caducados
    public List<Medicamento> obtenerCaducados() {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM Medicamentos WHERE caducidad < NOW()";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medicamentos.add(mapearMedicamento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentos;
    }

    // Actualizar medicamento
    public Medicamento actualizar(Medicamento medicamento) {
        String sql = "UPDATE Medicamentos SET nombre_medicamento = ?, dosis = ?, caducidad = ?, composicion = ?, indicaciones = ?, frecuencia_aplicacion = ?, via_administracion = ? WHERE id_medicamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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

    // Eliminar medicamento
    public boolean eliminar(int idMedicamento) {
        String sql = "DELETE FROM Medicamentos WHERE id_medicamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamento);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para mapear ResultSet a objeto Medicamento
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