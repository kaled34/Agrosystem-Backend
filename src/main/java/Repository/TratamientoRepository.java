package Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Config.ConfigDB;
import Model.Animales;
import Model.Medicamento;
import Model.ReporteMedico;
import Model.Tratamiento;
import Model.Usuario;

public class TratamientoRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public Tratamiento crear(Tratamiento tratamiento) {
        String sql = "INSERT INTO Tratamientos (id_animal, id_reporte, id_usuario, nombre_tratamiento, fecha_inicio, fecha_fin, id_medicamento, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

    public Tratamiento obtenerPorId(int idTratamiento) {
        String sql = "SELECT t.id_tratamiento, t.nombre_tratamiento, t.fecha_inicio, t.fecha_fin, t.observaciones, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "r.id_reporte AS r_id, r.temperatura AS r_temperatura, r.condicion_corporal AS r_condicion_corporal, r.frecuencia_respiratoria AS r_fr, r.fecha AS r_fecha, r.diagnostico_presuntivo AS r_dp, r.diagnostico_definitivo AS r_dd, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo, "
                + "m.id_medicamento AS m_id, m.nombre_medicamento AS m_nombre, m.solucion AS m_solucion, m.dosis AS m_dosis, m.caducidad AS m_caducidad, m.via_administracion AS m_via, m.composicion AS m_composicion, m.indicaciones AS m_indicaciones, m.frecuencia_aplicacion AS m_freq "
                + "FROM Tratamientos t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN ReporteMedico r ON t.id_reporte = r.id_reporte "
                + "LEFT JOIN usuario u ON t.id_usuario = u.id_usuario "
                + "LEFT JOIN medicamentos m ON t.id_medicamento = m.id_medicamento "
                + "WHERE t.id_tratamiento = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTratamiento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTratamientoConJoins(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Tratamiento> obtenerTodos() {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT t.id_tratamiento, t.nombre_tratamiento, t.fecha_inicio, t.fecha_fin, t.observaciones, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "r.id_reporte AS r_id, r.temperatura AS r_temperatura, r.condicion_corporal AS r_condicion_corporal, r.frecuencia_respiratoria AS r_fr, r.fecha AS r_fecha, r.diagnostico_presuntivo AS r_dp, r.diagnostico_definitivo AS r_dd, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo, "
                + "m.id_medicamento AS m_id, m.nombre_medicamento AS m_nombre, m.solucion AS m_solucion, m.dosis AS m_dosis, m.caducidad AS m_caducidad, m.via_administracion AS m_via, m.composicion AS m_composicion, m.indicaciones AS m_indicaciones, m.frecuencia_aplicacion AS m_freq "
                + "FROM Tratamientos t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN ReporteMedico r ON t.id_reporte = r.id_reporte "
                + "LEFT JOIN usuario u ON t.id_usuario = u.id_usuario "
                + "LEFT JOIN medicamentos m ON t.id_medicamento = m.id_medicamento "
                + "ORDER BY t.fecha_inicio DESC";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tratamientos.add(mapearTratamientoConJoins(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    public List<Tratamiento> obtenerPorAnimal(int idAnimal) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT t.id_tratamiento, t.nombre_tratamiento, t.fecha_inicio, t.fecha_fin, t.observaciones, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "r.id_reporte AS r_id, r.temperatura AS r_temperatura, r.condicion_corporal AS r_condicion_corporal, r.frecuencia_respiratoria AS r_fr, r.fecha AS r_fecha, r.diagnostico_presuntivo AS r_dp, r.diagnostico_definitivo AS r_dd, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo, "
                + "m.id_medicamento AS m_id, m.nombre_medicamento AS m_nombre, m.solucion AS m_solucion, m.dosis AS m_dosis, m.caducidad AS m_caducidad, m.via_administracion AS m_via, m.composicion AS m_composicion, m.indicaciones AS m_indicaciones, m.frecuencia_aplicacion AS m_freq "
                + "FROM Tratamientos t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN ReporteMedico r ON t.id_reporte = r.id_reporte "
                + "LEFT JOIN usuario u ON t.id_usuario = u.id_usuario "
                + "LEFT JOIN medicamentos m ON t.id_medicamento = m.id_medicamento "
                + "WHERE t.id_animal = ? "
                + "ORDER BY t.fecha_inicio DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tratamientos.add(mapearTratamientoConJoins(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    public List<Tratamiento> obtenerActivos() {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT t.id_tratamiento, t.nombre_tratamiento, t.fecha_inicio, t.fecha_fin, t.observaciones, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "r.id_reporte AS r_id, r.temperatura AS r_temperatura, r.condicion_corporal AS r_condicion_corporal, r.frecuencia_respiratoria AS r_fr, r.fecha AS r_fecha, r.diagnostico_presuntivo AS r_dp, r.diagnostico_definitivo AS r_dd, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo, "
                + "m.id_medicamento AS m_id, m.nombre_medicamento AS m_nombre, m.solucion AS m_solucion, m.dosis AS m_dosis, m.caducidad AS m_caducidad, m.via_administracion AS m_via, m.composicion AS m_composicion, m.indicaciones AS m_indicaciones, m.frecuencia_aplicacion AS m_freq "
                + "FROM Tratamientos t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN ReporteMedico r ON t.id_reporte = r.id_reporte "
                + "LEFT JOIN usuario u ON t.id_usuario = u.id_usuario "
                + "LEFT JOIN medicamentos m ON t.id_medicamento = m.id_medicamento "
                + "WHERE t.fecha_fin >= CURDATE() "
                + "ORDER BY t.fecha_inicio DESC";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tratamientos.add(mapearTratamientoConJoins(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    public List<Tratamiento> obtenerActivosPorAnimal(int idAnimal) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT t.id_tratamiento, t.nombre_tratamiento, t.fecha_inicio, t.fecha_fin, t.observaciones, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "r.id_reporte AS r_id, r.temperatura AS r_temperatura, r.condicion_corporal AS r_condicion_corporal, r.frecuencia_respiratoria AS r_fr, r.fecha AS r_fecha, r.diagnostico_presuntivo AS r_dp, r.diagnostico_definitivo AS r_dd, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo, "
                + "m.id_medicamento AS m_id, m.nombre_medicamento AS m_nombre, m.solucion AS m_solucion, m.dosis AS m_dosis, m.caducidad AS m_caducidad, m.via_administracion AS m_via, m.composicion AS m_composicion, m.indicaciones AS m_indicaciones, m.frecuencia_aplicacion AS m_freq "
                + "FROM Tratamientos t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN ReporteMedico r ON t.id_reporte = r.id_reporte "
                + "LEFT JOIN usuario u ON t.id_usuario = u.id_usuario "
                + "LEFT JOIN medicamentos m ON t.id_medicamento = m.id_medicamento "
                + "WHERE t.id_animal = ? AND t.fecha_fin >= CURDATE() "
                + "ORDER BY t.fecha_inicio DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tratamientos.add(mapearTratamientoConJoins(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    public List<Tratamiento> obtenerPorReporte(int idReporte) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT t.id_tratamiento, t.nombre_tratamiento, t.fecha_inicio, t.fecha_fin, t.observaciones, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "r.id_reporte AS r_id, r.temperatura AS r_temperatura, r.condicion_corporal AS r_condicion_corporal, r.frecuencia_respiratoria AS r_fr, r.fecha AS r_fecha, r.diagnostico_presuntivo AS r_dp, r.diagnostico_definitivo AS r_dd, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo, "
                + "m.id_medicamento AS m_id, m.nombre_medicamento AS m_nombre, m.solucion AS m_solucion, m.dosis AS m_dosis, m.caducidad AS m_caducidad, m.via_administracion AS m_via, m.composicion AS m_composicion, m.indicaciones AS m_indicaciones, m.frecuencia_aplicacion AS m_freq "
                + "FROM Tratamientos t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN ReporteMedico r ON t.id_reporte = r.id_reporte "
                + "LEFT JOIN usuario u ON t.id_usuario = u.id_usuario "
                + "LEFT JOIN medicamentos m ON t.id_medicamento = m.id_medicamento "
                + "WHERE t.id_reporte = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReporte);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tratamientos.add(mapearTratamientoConJoins(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    public List<Tratamiento> obtenerPorUsuario(int idUsuario) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT t.id_tratamiento, t.nombre_tratamiento, t.fecha_inicio, t.fecha_fin, t.observaciones, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "r.id_reporte AS r_id, r.temperatura AS r_temperatura, r.condicion_corporal AS r_condicion_corporal, r.frecuencia_respiratoria AS r_fr, r.fecha AS r_fecha, r.diagnostico_presuntivo AS r_dp, r.diagnostico_definitivo AS r_dd, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo, "
                + "m.id_medicamento AS m_id, m.nombre_medicamento AS m_nombre, m.solucion AS m_solucion, m.dosis AS m_dosis, m.caducidad AS m_caducidad, m.via_administracion AS m_via, m.composicion AS m_composicion, m.indicaciones AS m_indicaciones, m.frecuencia_aplicacion AS m_freq "
                + "FROM Tratamientos t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN ReporteMedico r ON t.id_reporte = r.id_reporte "
                + "LEFT JOIN usuario u ON t.id_usuario = u.id_usuario "
                + "LEFT JOIN medicamento m ON t.id_medicamento = m.id_medicamento "
                + "WHERE t.id_usuario = ? "
                + "ORDER BY t.fecha_inicio DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tratamientos.add(mapearTratamientoConJoins(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    public List<Tratamiento> obtenerPorMedicamento(int idMedicamento) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT t.id_tratamiento, t.nombre_tratamiento, t.fecha_inicio, t.fecha_fin, t.observaciones, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "r.id_reporte AS r_id, r.temperatura AS r_temperatura, r.condicion_corporal AS r_condicion_corporal, r.frecuencia_respiratoria AS r_fr, r.fecha AS r_fecha, r.diagnostico_presuntivo AS r_dp, r.diagnostico_definitivo AS r_dd, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo, "
                + "m.id_medicamento AS m_id, m.nombre_medicamento AS m_nombre, m.solucion AS m_solucion, m.dosis AS m_dosis, m.caducidad AS m_caducidad, m.via_administracion AS m_via, m.composicion AS m_composicion, m.indicaciones AS m_indicaciones, m.frecuencia_aplicacion AS m_freq "
                + "FROM Tratamientos t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN ReporteMedico r ON t.id_reporte = r.id_reporte "
                + "LEFT JOIN usuario u ON t.id_usuario = u.id_usuario "
                + "LEFT JOIN medicamento m ON t.id_medicamento = m.id_medicamento "
                + "WHERE t.id_medicamento = ? "
                + "ORDER BY t.fecha_inicio DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamento);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tratamientos.add(mapearTratamientoConJoins(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    public Tratamiento actualizar(Tratamiento tratamiento) {
        String sql = "UPDATE Tratamientos SET id_animal = ?, id_reporte = ?, id_usuario = ?, nombre_tratamiento = ?, fecha_fin = ?, id_medicamento = ?, observaciones = ? WHERE id_tratamiento = ?";
        String selectSql = "SELECT t.id_tratamiento, t.nombre_tratamiento, t.fecha_inicio, t.fecha_fin, t.observaciones, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "r.id_reporte AS r_id, r.temperatura AS r_temperatura, r.condicion_corporal AS r_condicion_corporal, r.frecuencia_respiratoria AS r_fr, r.fecha AS r_fecha, r.diagnostico_presuntivo AS r_dp, r.diagnostico_definitivo AS r_dd, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo, "
                + "m.id_medicamento AS m_id, m.nombre_medicamento AS m_nombre, m.solucion AS m_solucion, m.dosis AS m_dosis, m.caducidad AS m_caducidad, m.via_administracion AS m_via, m.composicion AS m_composicion, m.indicaciones AS m_indicaciones, m.frecuencia_aplicacion AS m_freq "
                + "FROM Tratamientos t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN ReporteMedico r ON t.id_reporte = r.id_reporte "
                + "LEFT JOIN usuario u ON t.id_usuario = u.id_usuario "
                + "LEFT JOIN medicamentos m ON t.id_medicamento = m.id_medicamento "
                + "WHERE t.id_tratamiento = ?";

        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, tratamiento.getIdAnimales().getIdAnimal());
                stmt.setInt(2, tratamiento.getIdReporte().getIdReporte());
                stmt.setInt(3, tratamiento.getIdUsuario().getIdUsuario());
                stmt.setString(4, tratamiento.getNombreTratamiento());
                stmt.setDate(5, Date.valueOf(tratamiento.getFechaFinal()));
                stmt.setInt(6, tratamiento.getIdMedicamento().getIdMedicamento());
                stmt.setString(7, tratamiento.getObservaciones());
                stmt.setInt(8, tratamiento.getIdTratamiento());

                int updated = stmt.executeUpdate();
                if (updated <= 0) {
                    connection.rollback();
                    return null;
                }
            }

            // Re-select updated row using same connection to get joined fields
            try (PreparedStatement sel = connection.prepareStatement(selectSql)) {
                sel.setInt(1, tratamiento.getIdTratamiento());
                try (ResultSet rs = sel.executeQuery()) {
                    if (rs.next()) {
                        Tratamiento refreshed = mapearTratamientoConJoins(rs);
                        connection.commit();
                        return refreshed;
                    }
                }
            }

            connection.commit();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try { connection.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            return null;
        } finally {
            if (connection != null) {
                try { connection.setAutoCommit(true); connection.close(); } catch (SQLException ex) { /* ignore */ }
            }
        }
    }

    public boolean eliminar(int idTratamiento) {
        String sql = "DELETE FROM Tratamientos WHERE id_tratamiento = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTratamiento);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Tratamiento mapearTratamiento(ResultSet rs) throws SQLException {
        // Avoid opening new DB connections here. Create lightweight objects with only IDs.
        Animales animal = new Animales();
        animal.idAnimal = rs.getInt("id_animal");

        ReporteMedico reporte = new ReporteMedico();
        reporte.idReporte = rs.getInt("id_reporte");

        Usuario usuario = new Usuario();
        usuario.idUsuario = rs.getInt("id_usuario");

        Medicamento medicamento = new Medicamento();
        medicamento.idMedicamento = rs.getInt("id_medicamento");

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

    private Tratamiento mapearTratamientoConJoins(ResultSet rs) throws SQLException {
        Animales animal = null;
        int aId = rs.getInt("a_id");
        if (!rs.wasNull()) {
            animal = new Animales();
            animal.idAnimal = aId;
            animal.nombreAnimal = rs.getString("a_nombre");
            animal.numArete = rs.getInt("a_num_arete");
            animal.rebaño = rs.getString("a_rebano");
            java.sql.Date aFecha = rs.getDate("a_fecha_nacimiento");
            if (aFecha != null) animal.fechaNacimiento = aFecha.toLocalDate();
            animal.pesoInicial = rs.getDouble("a_peso_inicial");
            animal.caracteristica = rs.getString("a_caracteristica");
            animal.edad = rs.getInt("a_edad");
            animal.procedencia = rs.getString("a_procedencia");
            String aSexo = rs.getString("a_sexo");
            animal.sexo = "M".equals(aSexo);
            animal.idPadre = rs.getInt("a_id_padre");
            animal.idMadre = rs.getInt("a_id_madre");
            animal.idPropiertario = rs.getInt("a_id_propietario");
        }

        ReporteMedico reporte = null;
        int rId = rs.getInt("r_id");
        if (!rs.wasNull()) {
            reporte = new ReporteMedico();
            reporte.idReporte = rId;
            reporte.temperatura = rs.getDouble("r_temperatura");
            reporte.condicionCorporal = rs.getString("r_condicion_corporal");
            reporte.frecuenciaRespiratoria = rs.getInt("r_fr");
            java.sql.Date rFecha = rs.getDate("r_fecha");
            if (rFecha != null) reporte.fecha = rFecha.toLocalDate();
            reporte.diagnosticoPresuntivo = rs.getString("r_dp");
            reporte.diagnosticoDefinitivo = rs.getString("r_dd");
        }

        Usuario usuario = null;
        int uId = rs.getInt("u_id");
        if (!rs.wasNull()) {
            usuario = new Usuario();
            usuario.idUsuario = uId;
            usuario.nombreUsuario = rs.getString("u_nombre");
            usuario.contrasena = rs.getString("u_pass");
            usuario.correo = rs.getString("u_correo");
            usuario.telefono = rs.getString("u_telefono");
            usuario.activo = rs.getBoolean("u_activo");
            int uRolId = rs.getInt("u_id_rol");
            if (!rs.wasNull()) {
                Model.Rol rol = new Model.Rol();
                rol.idRol = uRolId;
                usuario.rol = rol;
            }
        }

        Medicamento medicamento = null;
        int mId = rs.getInt("m_id");
        if (!rs.wasNull()) {
            medicamento = new Medicamento();
            medicamento.idMedicamento = mId;
            medicamento.nombreMedicamento = rs.getString("m_nombre");
            medicamento.solucion = rs.getString("m_solucion");
            medicamento.dosis = rs.getFloat("m_dosis");
            java.sql.Date mCad = rs.getDate("m_caducidad");
            if (mCad != null) medicamento.caducidad = mCad; // java.sql.Date is a java.util.Date subclass
            medicamento.viaAdministracion = rs.getString("m_via");
            medicamento.composicion = rs.getString("m_composicion");
            medicamento.indicaciones = rs.getString("m_indicaciones");
            medicamento.frecuenciaAplicacion = rs.getString("m_freq");
        }

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