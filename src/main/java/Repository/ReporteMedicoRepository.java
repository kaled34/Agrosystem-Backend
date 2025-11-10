package Repository;

import Config.ConfigDB;
import Model.ReporteMedico;
import Model.Animales;
import Model.Enfermedad;
import Model.Tratamiento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReporteMedicoRepository {

    public ReporteMedico crear(ReporteMedico reporte) {
        String sql = "INSERT INTO ReporteMedico (idAnimal, idEnfermedad, idTratamiento, " +
                "fechaReporte, descripcionReporte) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setObject(1, reporte.getIdAnimales() != null ? reporte.getIdAnimales().getIdAnimal() : null);
            stmt.setObject(2, reporte.getIdEnfermedad() != null ? reporte.getIdEnfermedad().getIdEnfermedad() : null);
            stmt.setObject(3, reporte.getIdTratamiento() != null ? reporte.getIdTratamiento().getIdTratamiento() : null);
            stmt.setDate(4, reporte.getFechaReporte() != null ? Date.valueOf(reporte.getFechaReporte()) : null);
            stmt.setString(5, reporte.getDescripcionReporte());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    reporte.idReporte = rs.getInt(1);
                }
            }

            return reporte;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear reporte médico: " + e.getMessage(), e);
        }
    }

    public ReporteMedico buscarPorId(int idReporte) {
        String sql = "SELECT * FROM ReporteMedico WHERE idReporte = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReporte);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearReporte(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar reporte: " + e.getMessage(), e);
        }

        return null;
    }

    public List<ReporteMedico> obtenerTodos() {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT * FROM ReporteMedico";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                reportes.add(mapearReporte(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener reportes: " + e.getMessage(), e);
        }

        return reportes;
    }

    public ReporteMedico actualizar(ReporteMedico reporte) {
        String sql = "UPDATE ReporteMedico SET idAnimal = ?, idEnfermedad = ?, idTratamiento = ?, " +
                "fechaReporte = ?, descripcionReporte = ? WHERE idReporte = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, reporte.getIdAnimales() != null ? reporte.getIdAnimales().getIdAnimal() : null);
            stmt.setObject(2, reporte.getIdEnfermedad() != null ? reporte.getIdEnfermedad().getIdEnfermedad() : null);
            stmt.setObject(3, reporte.getIdTratamiento() != null ? reporte.getIdTratamiento().getIdTratamiento() : null);
            stmt.setDate(4, reporte.getFechaReporte() != null ? Date.valueOf(reporte.getFechaReporte()) : null);
            stmt.setString(5, reporte.getDescripcionReporte());
            stmt.setInt(6, reporte.getIdReporte());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return reporte;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar reporte: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean eliminar(int idReporte) {
        String sql = "DELETE FROM ReporteMedico WHERE idReporte = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReporte);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar reporte: " + e.getMessage(), e);
        }
    }

    public List<ReporteMedico> buscarPorAnimal(int idAnimal) {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT * FROM ReporteMedico WHERE idAnimal = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAnimal);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reportes.add(mapearReporte(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por animal: " + e.getMessage(), e);
        }

        return reportes;
    }

    public List<ReporteMedico> buscarPorFecha(LocalDate fecha) {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT * FROM ReporteMedico WHERE fechaReporte = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(fecha));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reportes.add(mapearReporte(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por fecha: " + e.getMessage(), e);
        }

        return reportes;
    }

    public int obtenerTotal() {
        String sql = "SELECT COUNT(*) FROM ReporteMedico";

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

    private ReporteMedico mapearReporte(ResultSet rs) throws SQLException {
        ReporteMedico reporte = new ReporteMedico();
        reporte.idReporte = rs.getInt("idReporte");

        int idAnimal = rs.getInt("idAnimal");
        if (!rs.wasNull()) {
            Animales animal = new Animales();
            animal.idAnimal = idAnimal;
            reporte.idAnimales = animal;
        }

        int idEnfermedad = rs.getInt("idEnfermedad");
        if (!rs.wasNull()) {
            Enfermedad enfermedad = new Enfermedad();
            enfermedad.idEnfermedad = idEnfermedad;
            reporte.idEnfermedad = enfermedad;
        }

        int idTratamiento = rs.getInt("idTratamiento");
        if (!rs.wasNull()) {
            Tratamiento tratamiento = new Tratamiento();
            tratamiento.idTratamiento = idTratamiento;
            reporte.idTratamiento = tratamiento;
        }

        Date fecha = rs.getDate("fechaReporte");
        reporte.fechaReporte = fecha != null ? fecha.toLocalDate() : null;

        reporte.descripcionReporte = rs.getString("descripcionReporte");

        return reporte;
    }
}