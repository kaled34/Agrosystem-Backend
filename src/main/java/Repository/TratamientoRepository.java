package Repository;

import Model.Tratamiento;
import Model.Animales;
import Model.ReporteMedico;
import Model.Usuario;
import Model.Medicamento;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TratamientoRepository {
    private Connection connection;
    private AnimalesRepository animalRepository;
    private ReporteMedicoRepository reporteMedicoRepository;
    private UsuarioRepository usuarioRepository;
    private MedicamentoRepository medicamentoRepository;

    public TratamientoRepository(Connection connection) {
        this.connection = connection;
        this.animalRepository = new AnimalesRepository(connection);
        this.reporteMedicoRepository = new ReporteMedicoRepository(connection);
        this.usuarioRepository = new UsuarioRepository(connection);
        this.medicamentoRepository = new MedicamentoRepository(connection);
    }

    // Crear un nuevo tratamiento
    public Tratamiento crear(Tratamiento tratamiento) {
        String sql = "INSERT INTO Tratamientos (id_animal, id_reporte, id_usuario, nombre_tratamiento, fecha_inicio, fecha_fin, id_medicamento, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, tratamiento.getIdAnimales().getIdAnimal());
            stmt.setInt(2, tratamiento.getIdReporte().getIdReporte());
            stmt.setInt(3, tratamiento.getIdUsuario().getIdUsuario());
            stmt.setString(4, tratamiento.getNombreTratamiento());
            stmt.setDate(5, Date.valueOf(tratamiento.getFechaInicio()));
            stmt.setDate(6, Date.valueOf(tratamiento.getFechaFinal()));
            stmt.setInt(7, tratamiento.getIdMedicamento().getIdMedicamento());
            stmt.setString(8, tratamiento.getObservaciones());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        tratamiento.idTratamiento = generatedKeys.getInt(1);
                    }
                }
                return tratamiento;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Obtener tratamiento por ID
    public Tratamiento obtenerPorId(int idTratamiento) {
        String sql = "SELECT * FROM Tratamientos WHERE id_tratamiento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTratamiento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearTratamiento(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todos los tratamientos
    public List<Tratamiento> obtenerTodos() {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM Tratamientos ORDER BY fecha_inicio DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tratamientos.add(mapearTratamiento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    // Obtener tratamientos por animal
    public List<Tratamiento> obtenerPorAnimal(int idAnimal) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM Tratamientos WHERE id_animal = ? ORDER BY fecha_inicio DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tratamientos.add(mapearTratamiento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    // Obtener tratamientos activos (no finalizados)
    public List<Tratamiento> obtenerActivos() {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM Tratamientos WHERE fecha_fin >= CURDATE() ORDER BY fecha_inicio DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tratamientos.add(mapearTratamiento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    // Obtener tratamientos activos por animal
    public List<Tratamiento> obtenerActivosPorAnimal(int idAnimal) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM Tratamientos WHERE id_animal = ? AND fecha_fin >= CURDATE() ORDER BY fecha_inicio DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tratamientos.add(mapearTratamiento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    // Obtener tratamientos por reporte médico
    public List<Tratamiento> obtenerPorReporte(int idReporte) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM Tratamientos WHERE id_reporte = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReporte);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tratamientos.add(mapearTratamiento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    // Obtener tratamientos por usuario
    public List<Tratamiento> obtenerPorUsuario(int idUsuario) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM Tratamientos WHERE id_usuario = ? ORDER BY fecha_inicio DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tratamientos.add(mapearTratamiento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    // Obtener tratamientos por medicamento
    public List<Tratamiento> obtenerPorMedicamento(int idMedicamento) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM Tratamientos WHERE id_medicamento = ? ORDER BY fecha_inicio DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamento);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tratamientos.add(mapearTratamiento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    // Actualizar tratamiento
    public Tratamiento actualizar(Tratamiento tratamiento) {
        String sql = "UPDATE Tratamientos SET id_animal = ?, id_reporte = ?, id_usuario = ?, nombre_tratamiento = ?, fecha_fin = ?, id_medicamento = ?, observaciones = ? WHERE id_tratamiento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tratamiento.getIdAnimales().getIdAnimal());
            stmt.setInt(2, tratamiento.getIdReporte().getIdReporte());
            stmt.setInt(3, tratamiento.getIdUsuario().getIdUsuario());
            stmt.setString(4, tratamiento.getNombreTratamiento());
            stmt.setDate(5, Date.valueOf(tratamiento.getFechaFinal()));
            stmt.setInt(6, tratamiento.getIdMedicamento().getIdMedicamento());
            stmt.setString(7, tratamiento.getObservaciones());
            stmt.setInt(8, tratamiento.getIdTratamiento());

            if (stmt.executeUpdate() > 0) {
                return tratamiento;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Eliminar tratamiento
    public boolean eliminar(int idTratamiento) {
        String sql = "DELETE FROM Tratamientos WHERE id_tratamiento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTratamiento);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para mapear ResultSet a objeto Tratamiento
    private Tratamiento mapearTratamiento(ResultSet rs) throws SQLException {
        Animales animal = animalRepository.obtenerPorId(rs.getInt("id_animal"));
        ReporteMedico reporte = reporteMedicoRepository.obtenerPorId(rs.getInt("id_reporte"));
        Usuario usuario = usuarioRepository.obtenerPorId(rs.getInt("id_usuario"));
        Medicamento medicamento = medicamentoRepository.obtenerPorId(rs.getInt("id_medicamento"));

        return new Tratamiento(
                rs.getInt("id_tratamiento"),
                animal,
                reporte,
                usuario,
                rs.getString("nombre_tratamiento"),
                rs.getDate("fecha_inicio").toLocalDate(),
                rs.getDate("fecha_fin").toLocalDate(),
                medicamento,
                rs.getString("observaciones")
        );
    }
}