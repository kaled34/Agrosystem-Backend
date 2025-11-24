package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Config.ConfigDB;
import Model.Animales;
import Model.Enfermedad;
import Model.TarjetaSalud;
import Model.Tratamiento;

public class TarjetaSaludRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public TarjetaSalud crear(TarjetaSalud tarjeta) {
        String sql = "INSERT INTO TarjetaSalud (id_animal, id_enfermedad, id_tratamiento) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, tarjeta.getIdAnimal().getIdAnimal());
            stmt.setInt(2, tarjeta.getIdEnfermedad().getIdEnfermedad());
            stmt.setInt(3, tarjeta.getIdTratamiento().getIdTratamiento());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        tarjeta.idTarjeta = generatedKeys.getInt(1);
                    }
                }
                return tarjeta;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TarjetaSalud obtenerPorId(int idTarjeta) {
        String sql = "SELECT t.id_tarjeta, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "e.id_enfermedad AS e_id, e.nombre_enfermedad AS e_nombre, e.tipo_enfermedad AS e_tipo, e.sintomas AS e_sintomas, e.duracion_estimada AS e_duracion, e.tratamientos_recomendados AS e_tratamientos, e.id_medicamento AS e_id_medicamento, e.nivel_riesgo AS e_nivel_riesgo, e.modo_transmision AS e_modo_transmision, e.id_analisis AS e_id_analisis, "
                + "tr.id_tratamiento AS tr_id, tr.id_animal AS tr_id_animal, tr.id_reporte AS tr_id_reporte, tr.id_usuario AS tr_id_usuario, tr.nombre_tratamiento AS tr_nombre, tr.fecha_inicio AS tr_fecha_inicio, tr.fecha_fin AS tr_fecha_fin, tr.id_medicamento AS tr_id_medicamento, tr.observaciones AS tr_observaciones "
                + "FROM TarjetaSalud t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN enfermedad e ON t.id_enfermedad = e.id_enfermedad "
                + "LEFT JOIN Tratamientos tr ON t.id_tratamiento = tr.id_tratamiento "
                + "WHERE t.id_tarjeta = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTarjeta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTarjetaSaludConJoins(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TarjetaSalud> obtenerTodas() {
        List<TarjetaSalud> tarjetas = new ArrayList<>();
        String sql = "SELECT t.id_tarjeta, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "e.id_enfermedad AS e_id, e.nombre_enfermedad AS e_nombre, e.tipo_enfermedad AS e_tipo, e.sintomas AS e_sintomas, e.duracion_estimada AS e_duracion, e.tratamientos_recomendados AS e_tratamientos, e.id_medicamento AS e_id_medicamento, e.nivel_riesgo AS e_nivel_riesgo, e.modo_transmision AS e_modo_transmision, e.id_analisis AS e_id_analisis, "
                + "tr.id_tratamiento AS tr_id, tr.id_animal AS tr_id_animal, tr.id_reporte AS tr_id_reporte, tr.id_usuario AS tr_id_usuario, tr.nombre_tratamiento AS tr_nombre, tr.fecha_inicio AS tr_fecha_inicio, tr.fecha_fin AS tr_fecha_fin, tr.id_medicamento AS tr_id_medicamento, tr.observaciones AS tr_observaciones "
                + "FROM TarjetaSalud t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN enfermedad e ON t.id_enfermedad = e.id_enfermedad "
                + "LEFT JOIN Tratamientos tr ON t.id_tratamiento = tr.id_tratamiento";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tarjetas.add(mapearTarjetaSaludConJoins(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarjetas;
    }

    public List<TarjetaSalud> obtenerPorAnimal(int idAnimal) {
        List<TarjetaSalud> tarjetas = new ArrayList<>();
        String sql = "SELECT t.id_tarjeta, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "e.id_enfermedad AS e_id, e.nombre_enfermedad AS e_nombre, e.tipo_enfermedad AS e_tipo, e.sintomas AS e_sintomas, e.duracion_estimada AS e_duracion, e.tratamientos_recomendados AS e_tratamientos, e.id_medicamento AS e_id_medicamento, e.nivel_riesgo AS e_nivel_riesgo, e.modo_transmision AS e_modo_transmision, e.id_analisis AS e_id_analisis, "
                + "tr.id_tratamiento AS tr_id, tr.id_animal AS tr_id_animal, tr.id_reporte AS tr_id_reporte, tr.id_usuario AS tr_id_usuario, tr.nombre_tratamiento AS tr_nombre, tr.fecha_inicio AS tr_fecha_inicio, tr.fecha_fin AS tr_fecha_fin, tr.id_medicamento AS tr_id_medicamento, tr.observaciones AS tr_observaciones "
                + "FROM TarjetaSalud t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN enfermedad e ON t.id_enfermedad = e.id_enfermedad "
                + "LEFT JOIN Tratamientos tr ON t.id_tratamiento = tr.id_tratamiento "
                + "WHERE t.id_animal = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tarjetas.add(mapearTarjetaSaludConJoins(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarjetas;
    }

    public List<TarjetaSalud> obtenerPorEnfermedad(int idEnfermedad) {
        List<TarjetaSalud> tarjetas = new ArrayList<>();
        String sql = "SELECT t.id_tarjeta, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "e.id_enfermedad AS e_id, e.nombre_enfermedad AS e_nombre, e.tipo_enfermedad AS e_tipo, e.sintomas AS e_sintomas, e.duracion_estimada AS e_duracion, e.tratamientos_recomendados AS e_tratamientos, e.id_medicamento AS e_id_medicamento, e.nivel_riesgo AS e_nivel_riesgo, e.modo_transmision AS e_modo_transmision, e.id_analisis AS e_id_analisis, "
                + "tr.id_tratamiento AS tr_id, tr.id_animal AS tr_id_animal, tr.id_reporte AS tr_id_reporte, tr.id_usuario AS tr_id_usuario, tr.nombre_tratamiento AS tr_nombre, tr.fecha_inicio AS tr_fecha_inicio, tr.fecha_fin AS tr_fecha_fin, tr.id_medicamento AS tr_id_medicamento, tr.observaciones AS tr_observaciones "
                + "FROM TarjetaSalud t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN enfermedad e ON t.id_enfermedad = e.id_enfermedad "
                + "LEFT JOIN Tratamientos tr ON t.id_tratamiento = tr.id_tratamiento "
                + "WHERE t.id_enfermedad = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEnfermedad);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tarjetas.add(mapearTarjetaSaludConJoins(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarjetas;
    }

    public TarjetaSalud obtenerPorTratamiento(int idTratamiento) {
        String sql = "SELECT t.id_tarjeta, "
                + "a.id_animal AS a_id, a.nombre_animal AS a_nombre, a.num_arete AS a_num_arete, a.`rebaño` AS a_rebano, a.fecha_nacimiento AS a_fecha_nacimiento, a.peso_inicial AS a_peso_inicial, a.caracteristica AS a_caracteristica, a.edad AS a_edad, a.procedencia AS a_procedencia, a.sexo AS a_sexo, a.id_padre AS a_id_padre, a.id_madre AS a_id_madre, a.id_propietario AS a_id_propietario, "
                + "e.id_enfermedad AS e_id, e.nombre_enfermedad AS e_nombre, e.tipo_enfermedad AS e_tipo, e.sintomas AS e_sintomas, e.duracion_estimada AS e_duracion, e.tratamientos_recomendados AS e_tratamientos, e.id_medicamento AS e_id_medicamento, e.nivel_riesgo AS e_nivel_riesgo, e.modo_transmision AS e_modo_transmision, e.id_analisis AS e_id_analisis, "
                + "tr.id_tratamiento AS tr_id, tr.id_animal AS tr_id_animal, tr.id_reporte AS tr_id_reporte, tr.id_usuario AS tr_id_usuario, tr.nombre_tratamiento AS tr_nombre, tr.fecha_inicio AS tr_fecha_inicio, tr.fecha_fin AS tr_fecha_fin, tr.id_medicamento AS tr_id_medicamento, tr.observaciones AS tr_observaciones "
                + "FROM TarjetaSalud t "
                + "LEFT JOIN animal a ON t.id_animal = a.id_animal "
                + "LEFT JOIN enfermedad e ON t.id_enfermedad = e.id_enfermedad "
                + "LEFT JOIN Tratamientos tr ON t.id_tratamiento = tr.id_tratamiento "
                + "WHERE t.id_tratamiento = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTratamiento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTarjetaSaludConJoins(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TarjetaSalud actualizar(TarjetaSalud tarjeta) {
        String sql = "UPDATE TarjetaSalud SET id_animal = ?, id_enfermedad = ?, id_tratamiento = ? WHERE id_tarjeta = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, tarjeta.getIdAnimal().getIdAnimal());
            stmt.setInt(2, tarjeta.getIdEnfermedad().getIdEnfermedad());
            stmt.setInt(3, tarjeta.getIdTratamiento().getIdTratamiento());
            stmt.setInt(4, tarjeta.getIdTarjeta());

            if (stmt.executeUpdate() > 0) {
                return tarjeta;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean eliminar(int idTarjeta) {
        String sql = "DELETE FROM TarjetaSalud WHERE id_tarjeta = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTarjeta);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private TarjetaSalud mapearTarjetaSalud(ResultSet rs) throws SQLException {
        // legacy lightweight mapper (kept for compatibility)
        Animales animal = new Animales();
        animal.idAnimal = rs.getInt("id_animal");

        Enfermedad enfermedad = new Enfermedad();
        enfermedad.idEnfermedad = rs.getInt("id_enfermedad");

        Tratamiento tratamiento = new Tratamiento();
        tratamiento.idTratamiento = rs.getInt("id_tratamiento");

        return new TarjetaSalud(
            rs.getInt("id_tarjeta"),
            animal,
            enfermedad,
            tratamiento
        );
    }

    private TarjetaSalud mapearTarjetaSaludConJoins(ResultSet rs) throws SQLException {
        // Map Animal
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

        // Map Enfermedad
        Enfermedad enfermedad = null;
        int eId = rs.getInt("e_id");
        if (!rs.wasNull()) {
            enfermedad = new Enfermedad();
            enfermedad.idEnfermedad = eId;
            enfermedad.nombreEnfermedad = rs.getString("e_nombre");
            enfermedad.tipoEnfermedad = rs.getString("e_tipo");
            enfermedad.sintomas = rs.getString("e_sintomas");
            enfermedad.duracionEstimada = rs.getInt("e_duracion");
            enfermedad.tratamientosRecomendados = rs.getString("e_tratamientos");
            int eMedId = rs.getInt("e_id_medicamento");
            if (!rs.wasNull()) {
                Model.Medicamento med = new Model.Medicamento();
                med.idMedicamento = eMedId;
                enfermedad.idMedicamento = med;
            }
            enfermedad.nivelRiesgo = rs.getString("e_nivel_riesgo");
            enfermedad.modoTransmision = rs.getString("e_modo_transmision");
            int eAnalId = rs.getInt("e_id_analisis");
            if (!rs.wasNull()) {
                Model.Analisis anal = new Model.Analisis();
                anal.idAnalisis = eAnalId;
                enfermedad.idAnalisis = anal;
            }
        }

        // Map Tratamiento
        Tratamiento tratamiento = null;
        int trId = rs.getInt("tr_id");
        if (!rs.wasNull()) {
            tratamiento = new Tratamiento();
            tratamiento.idTratamiento = trId;
            int trAnimalId = rs.getInt("tr_id_animal");
            if (!rs.wasNull()) {
                Animales tAnimal = new Animales();
                tAnimal.idAnimal = trAnimalId;
                tratamiento.idAnimal = tAnimal;
            }
            int trReporte = rs.getInt("tr_id_reporte");
            if (!rs.wasNull()) {
                Model.ReporteMedico rep = new Model.ReporteMedico();
                rep.idReporte = trReporte;
                tratamiento.idReporte = rep;
            }
            int trUsuario = rs.getInt("tr_id_usuario");
            if (!rs.wasNull()) {
                Model.Usuario usr = new Model.Usuario();
                usr.idUsuario = trUsuario;
                tratamiento.idUsuario = usr;
            }
            tratamiento.nombreTratamiento = rs.getString("tr_nombre");
            java.sql.Date trInicio = rs.getDate("tr_fecha_inicio");
            if (trInicio != null) tratamiento.fechaInicio = trInicio.toLocalDate();
            java.sql.Date trFin = rs.getDate("tr_fecha_fin");
            if (trFin != null) tratamiento.fechaFinal = trFin.toLocalDate();
            int trMed = rs.getInt("tr_id_medicamento");
            if (!rs.wasNull()) {
                Model.Medicamento m = new Model.Medicamento();
                m.idMedicamento = trMed;
                tratamiento.idMedicamento = m;
            }
            tratamiento.observaciones = rs.getString("tr_observaciones");
        }

        return new TarjetaSalud(
                rs.getInt("id_tarjeta"),
                animal,
                enfermedad,
                tratamiento
        );
    }
}