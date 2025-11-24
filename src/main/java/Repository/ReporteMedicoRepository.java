package Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Config.ConfigDB;
import Model.Animales;
import Model.ReporteMedico;
import Model.Rol;
import Model.Usuario;

public class ReporteMedicoRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public ReporteMedico crear(ReporteMedico reporte) {
        String sql = "INSERT INTO ReporteMedico (id_animal, id_usuario, temperatura, condicion_corporal, frecuencia_respiratoria, fecha, diagnostico_presuntivo, diagnostico_definitivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

    public ReporteMedico obtenerPorId(int idReporte) {
        String sql = "SELECT r.id_reporte, r.temperatura, r.condicion_corporal, r.frecuencia_respiratoria, r.fecha AS r_fecha, r.diagnostico_presuntivo, r.diagnostico_definitivo, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo "
                + "FROM ReporteMedico r "
                + "LEFT JOIN animal a ON r.id_animal = a.id_animal "
                + "LEFT JOIN usuario u ON r.id_usuario = u.id_usuario "
                + "WHERE r.id_reporte = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReporte);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearReporteConJoins(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ReporteMedico> obtenerTodos() {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT r.id_reporte, r.temperatura, r.condicion_corporal, r.frecuencia_respiratoria, r.fecha AS r_fecha, r.diagnostico_presuntivo, r.diagnostico_definitivo, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo "
                + "FROM ReporteMedico r "
                + "LEFT JOIN animal a ON r.id_animal = a.id_animal "
                + "LEFT JOIN usuario u ON r.id_usuario = u.id_usuario "
                + "ORDER BY r.fecha DESC";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                reportes.add(mapearReporteConJoins(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    public List<ReporteMedico> obtenerPorAnimal(int idAnimal) {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT r.id_reporte, r.temperatura, r.condicion_corporal, r.frecuencia_respiratoria, r.fecha AS r_fecha, r.diagnostico_presuntivo, r.diagnostico_definitivo, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo "
                + "FROM ReporteMedico r "
                + "LEFT JOIN animal a ON r.id_animal = a.id_animal "
                + "LEFT JOIN usuario u ON r.id_usuario = u.id_usuario "
                + "WHERE r.id_animal = ? "
                + "ORDER BY r.fecha DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reportes.add(mapearReporteConJoins(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    public List<ReporteMedico> obtenerPorUsuario(int idUsuario) {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT r.id_reporte, r.temperatura, r.condicion_corporal, r.frecuencia_respiratoria, r.fecha AS r_fecha, r.diagnostico_presuntivo, r.diagnostico_definitivo, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo "
                + "FROM ReporteMedico r "
                + "LEFT JOIN animal a ON r.id_animal = a.id_animal "
                + "LEFT JOIN usuario u ON r.id_usuario = u.id_usuario "
                + "WHERE r.id_usuario = ? "
                + "ORDER BY r.fecha DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reportes.add(mapearReporteConJoins(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    public List<ReporteMedico> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT r.id_reporte, r.temperatura, r.condicion_corporal, r.frecuencia_respiratoria, r.fecha AS r_fecha, r.diagnostico_presuntivo, r.diagnostico_definitivo, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo "
                + "FROM ReporteMedico r "
                + "LEFT JOIN animal a ON r.id_animal = a.id_animal "
                + "LEFT JOIN usuario u ON r.id_usuario = u.id_usuario "
                + "WHERE r.fecha BETWEEN ? AND ? "
                + "ORDER BY r.fecha DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fechaInicio));
            stmt.setDate(2, Date.valueOf(fechaFin));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reportes.add(mapearReporteConJoins(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    public List<ReporteMedico> obtenerPorFecha(LocalDate fecha) {
        List<ReporteMedico> reportes = new ArrayList<>();
        String sql = "SELECT r.id_reporte, r.temperatura, r.condicion_corporal, r.frecuencia_respiratoria, r.fecha AS r_fecha, r.diagnostico_presuntivo, r.diagnostico_definitivo, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "u.id_usuario AS u_id, u.nombre_usuario AS u_nombre, u.contrasena AS u_pass, u.correo AS u_correo, u.telefono AS u_telefono, u.id_rol AS u_id_rol, u.activo AS u_activo "
                + "FROM ReporteMedico r "
                + "LEFT JOIN animal a ON r.id_animal = a.id_animal "
                + "LEFT JOIN usuario u ON r.id_usuario = u.id_usuario "
                + "WHERE r.fecha = ? "
                + "ORDER BY r.id_reporte DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fecha));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reportes.add(mapearReporteConJoins(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }

    public ReporteMedico actualizar(ReporteMedico reporte) {
        String sql = "UPDATE ReporteMedico SET id_animal = ?, id_usuario = ?, temperatura = ?, condicion_corporal = ?, frecuencia_respiratoria = ?, fecha = ?, diagnostico_presuntivo = ?, diagnostico_definitivo = ? WHERE id_reporte = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
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

    public boolean eliminar(int idReporte) {
        String sql = "DELETE FROM ReporteMedico WHERE id_reporte = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReporte);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ReporteMedico mapearReporte(ResultSet rs) throws SQLException {
        // Avoid opening new DB connections here. Create lightweight objects with only IDs.
        Animales animal = new Animales();
        animal.idAnimal = rs.getInt("id_animal");

        Usuario usuario = new Usuario();
        usuario.idUsuario = rs.getInt("id_usuario");

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

    private ReporteMedico mapearReporteConJoins(ResultSet rs) throws SQLException {
        // Map animal
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

        // Map usuario
        Usuario usuario = null;
        int uId = rs.getInt("u_id");
        if (!rs.wasNull()) {
            usuario = new Usuario();
            usuario.idUsuario = uId;
            usuario.nombreUsuario = rs.getString("u_nombre");
            usuario.contrasena = rs.getString("u_pass");
            usuario.correo = rs.getString("u_correo");
            usuario.telefono = rs.getString("u_telefono");
            int uRolId = rs.getInt("u_id_rol");
            if (!rs.wasNull()) {
                Rol rol = new Rol();
                rol.idRol = uRolId;
                usuario.rol = rol;
            }
            usuario.activo = rs.getBoolean("u_activo");
        }

        return new ReporteMedico(
                rs.getInt("id_reporte"),
                animal,
                usuario,
                rs.getDouble("temperatura"),
                rs.getString("condicion_corporal"),
                rs.getInt("frecuencia_respiratoria"),
                rs.getDate("r_fecha").toLocalDate(),
                rs.getString("diagnostico_presuntivo"),
                rs.getString("diagnostico_definitivo")
        );
    }
}