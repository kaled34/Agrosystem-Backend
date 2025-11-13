package Repository;

import Model.ReporteMedico;
import Model.Animales;
import Model.Usuario;
import Config.ConfigDB;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReporteMedicoRepository {
    private Connection connection;
    private AnimalesRepository animalRepository;
    private UsuarioRepository usuarioRepository;

    // ✅ CORREGIDO: Constructor sin parámetros
    public ReporteMedicoRepository() {
        try {
            this.connection = ConfigDB.getDataSource().getConnection();
            this.animalRepository = new AnimalesRepository();
            this.usuarioRepository = new UsuarioRepository();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Crear un nuevo reporte médico
    public ReporteMedico crear(ReporteMedico reporte) {
        String sql = "INSERT INTO ReporteMedico (id_animal, id_usuario, temperatura, condicion_corporal, frecuencia_respiratoria, fecha, diagnostico_presuntivo, diagnostico_definitivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reporte.getIdAnimales().getIdAnimal());
            stmt.setInt(2, reporte.getIdUsuario().getIdUsuario());
            stmt.setDouble(3, reporte.getTemperatura());
            stmt.setString(4, reporte.getCondicionCorporal());
            stmt.setInt(5, reporte.getFrecuenciaRespiratoria());
            stmt.setDate(6, Date.valueOf(reporte.getFecha()));
            stmt.setString(7, reporte.getDiagnosticoPresuntivo());
            stmt.setString(8, reporte.getDiagnosticoDefinitivo());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        reporte.idReporte = generatedKeys.getInt(1);
                    }
                }
                return reporte;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Obtener reporte médico por ID
    public ReporteMedico obtenerPorId(int idReporte) {
        String sql = "SELECT * FROM ReporteMedico WHERE id_reporte = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReporte);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearReporte(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todos los reportes médicos
    public List<ReporteMedico> obtenerTodos() {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT * FROM ReporteMedico ORDER BY fecha DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                reportes.add(mapearReporte(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    // Obtener reportes por animal
    public List<ReporteMedico> obtenerPorAnimal(int idAnimal) {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT * FROM ReporteMedico WHERE id_animal = ? ORDER BY fecha DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reportes.add(mapearReporte(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    // Obtener reportes por usuario (veterinario)
    public List<ReporteMedico> obtenerPorUsuario(int idUsuario) {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT * FROM ReporteMedico WHERE id_usuario = ? ORDER BY fecha DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reportes.add(mapearReporte(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    // Obtener reportes por rango de fechas
    public List<ReporteMedico> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT * FROM ReporteMedico WHERE fecha BETWEEN ? AND ? ORDER BY fecha DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fechaInicio));
            stmt.setDate(2, Date.valueOf(fechaFin));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reportes.add(mapearReporte(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    // Obtener reportes por fecha específica
    public List<ReporteMedico> obtenerPorFecha(LocalDate fecha) {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT * FROM ReporteMedico WHERE fecha = ? ORDER BY id_reporte DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reportes.add(mapearReporte(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    // Actualizar reporte médico
    public ReporteMedico actualizar(ReporteMedico reporte) {
        String sql = "UPDATE ReporteMedico SET id_animal = ?, id_usuario = ?, temperatura = ?, condicion_corporal = ?, frecuencia_respiratoria = ?, fecha = ?, diagnostico_presuntivo = ?, diagnostico_definitivo = ? WHERE id_reporte = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reporte.getIdAnimales().getIdAnimal());
            stmt.setInt(2, reporte.getIdUsuario().getIdUsuario());
            stmt.setDouble(3, reporte.getTemperatura());
            stmt.setString(4, reporte.getCondicionCorporal());
            stmt.setInt(5, reporte.getFrecuenciaRespiratoria());
            stmt.setDate(6, Date.valueOf(reporte.getFecha()));
            stmt.setString(7, reporte.getDiagnosticoPresuntivo());
            stmt.setString(8, reporte.getDiagnosticoDefinitivo());
            stmt.setInt(9, reporte.getIdReporte());

            if (stmt.executeUpdate() > 0) {
                return reporte;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Eliminar reporte médico
    public boolean eliminar(int idReporte) {
        String sql = "DELETE FROM ReporteMedico WHERE id_reporte = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReporte);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para mapear ResultSet a objeto ReporteMedico
    private ReporteMedico mapearReporte(ResultSet rs) throws SQLException {
        Animales animal = animalRepository.obtenerPorId(rs.getInt("id_animal"));
        Usuario usuario = usuarioRepository.obtenerPorId(rs.getInt("id_usuario"));

        return new ReporteMedico(
                rs.getInt("id_reporte"),
                animal,
                usuario,
                rs.getDouble("temperatura"),
                rs.getString("condicion_corporal"),
                rs.getInt("frecuencia_respiratoria"),
                rs.getDate("fecha").toLocalDate(),
                rs.getString("diagnostico_presuntivo"),
                rs.getString("diagnostico_definitivo")
        );
    }
}