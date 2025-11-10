package Repository;

import Config.ConfigDB;
import Model.Tratamiento;
import Model.Animales;
import Model.Medicamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientoRepository {

    public Tratamiento crear(Tratamiento tratamiento) {
        String sql = "INSERT INTO tratamiento (idAnimal, idMedicamento, fechaInicio, fechaFinal, " +
                "nombreTratamiento, descripcionReporte, evolucion, nombreVeterinario) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setObject(1, tratamiento.getIdAnimales() != null ? tratamiento.getIdAnimales().getIdAnimal() : null);
            stmt.setObject(2, tratamiento.idMedicamento != null ? tratamiento.idMedicamento.getIdMedicamento() : null);
            stmt.setDate(3, tratamiento.getFechaInicio() != null ? Date.valueOf(tratamiento.getFechaInicio()) : null);
            stmt.setDate(4, tratamiento.getFechaFinal() != null ? Date.valueOf(tratamiento.getFechaFinal()) : null);
            stmt.setString(5, tratamiento.getNombreTratamiento());
            stmt.setString(6, tratamiento.getDescripcionReporte());
            stmt.setString(7, tratamiento.getEvolucion());
            stmt.setString(8, tratamiento.getNombreVeterinario());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tratamiento.idTratamiento = rs.getInt(1);
                }
            }

            return tratamiento;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear tratamiento: " + e.getMessage(), e);
        }
    }

    public Tratamiento buscarPorId(int idTratamiento) {
        String sql = "SELECT * FROM tratamiento WHERE idTratamiento = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTratamiento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTratamiento(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar tratamiento: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Tratamiento> obtenerTodos() {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM tratamiento";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tratamientos.add(mapearTratamiento(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener tratamientos: " + e.getMessage(), e);
        }

        return tratamientos;
    }

    public Tratamiento actualizar(Tratamiento tratamiento) {
        String sql = "UPDATE tratamiento SET idAnimal = ?, idMedicamento = ?, fechaInicio = ?, " +
                "fechaFinal = ?, nombreTratamiento = ?, descripcionReporte = ?, evolucion = ?, " +
                "nombreVeterinario = ? WHERE idTratamiento = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, tratamiento.getIdAnimales() != null ? tratamiento.getIdAnimales().getIdAnimal() : null);
            stmt.setObject(2, tratamiento.idMedicamento != null ? tratamiento.idMedicamento.getIdMedicamento() : null);
            stmt.setDate(3, tratamiento.getFechaInicio() != null ? Date.valueOf(tratamiento.getFechaInicio()) : null);
            stmt.setDate(4, tratamiento.getFechaFinal() != null ? Date.valueOf(tratamiento.getFechaFinal()) : null);
            stmt.setString(5, tratamiento.getNombreTratamiento());
            stmt.setString(6, tratamiento.getDescripcionReporte());
            stmt.setString(7, tratamiento.getEvolucion());
            stmt.setString(8, tratamiento.getNombreVeterinario());
            stmt.setInt(9, tratamiento.getIdTratamiento());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return tratamiento;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tratamiento: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean eliminar(int idTratamiento) {
        String sql = "DELETE FROM tratamiento WHERE idTratamiento = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTratamiento);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar tratamiento: " + e.getMessage(), e);
        }
    }

    public List<Tratamiento> buscarPorAnimal(Animales animal) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM tratamiento WHERE idAnimal = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animal.getIdAnimal());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tratamientos.add(mapearTratamiento(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por animal: " + e.getMessage(), e);
        }

        return tratamientos;
    }

    public List<Tratamiento> buscarPorVeterinario(String nombreVeterinario) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM tratamiento WHERE nombreVeterinario = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreVeterinario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tratamientos.add(mapearTratamiento(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por veterinario: " + e.getMessage(), e);
        }

        return tratamientos;
    }

    public int obtenerTotal() {
        String sql = "SELECT COUNT(*) FROM tratamiento";

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

    private Tratamiento mapearTratamiento(ResultSet rs) throws SQLException {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.idTratamiento = rs.getInt("idTratamiento");

        int idAnimal = rs.getInt("idAnimal");
        if (!rs.wasNull()) {
            Animales animal = new Animales();
            animal.idAnimal = idAnimal;
            tratamiento.idAnimal = animal;
        }

        int idMedicamento = rs.getInt("idMedicamento");
        if (!rs.wasNull()) {
            Medicamento medicamento = new Medicamento();
            medicamento.idMedicamento = idMedicamento;
            tratamiento.idMedicamento = medicamento;
        }

        Date fechaIni = rs.getDate("fechaInicio");
        tratamiento.fechaInicio = fechaIni != null ? fechaIni.toLocalDate() : null;

        Date fechaFin = rs.getDate("fechaFinal");
        tratamiento.fechaFinal = fechaFin != null ? fechaFin.toLocalDate() : null;

        tratamiento.nombreTratamiento = rs.getString("nombreTratamiento");
        tratamiento.descripcionReporte = rs.getString("descripcionReporte");
        tratamiento.evolucion = rs.getString("evolucion");
        tratamiento.nombreVeterinario = rs.getString("nombreVeterinario");

        return tratamiento;
    }
}