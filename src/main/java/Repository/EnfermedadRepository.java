package Repository;

import Config.ConfigDB;
import Model.Enfermedad;
import Model.Medicamento;
import Model.Analisis;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnfermedadRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public Enfermedad crear(Enfermedad enfermedad) {
        String sql = "INSERT INTO Enfermedad (nombre_enfermedad, tipo_enfermedad, sintomas, duracion_estimada, tratamientos_recomendados, id_medicamento, nivel_riesgo, modo_transmision, id_analisis) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, enfermedad.getNombreEnfermedad());
            stmt.setString(2, enfermedad.getTipoEnfermedad());
            stmt.setString(3, enfermedad.getSintomas());
            stmt.setInt(4, enfermedad.getDuracionEstimada());
            stmt.setString(5, enfermedad.getTratamientosRecomendados());

            if (enfermedad.getIdMedicamento() != null) {
                stmt.setInt(6, enfermedad.getIdMedicamento().getIdMedicamento());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setString(7, enfermedad.getNivelRiesgo());
            stmt.setString(8, enfermedad.getModoTransmision());

            if (enfermedad.getIdAnalisis() != null) {
                stmt.setInt(9, enfermedad.getIdAnalisis().getIdAnalisis());
            } else {
                stmt.setNull(9, Types.INTEGER);
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        enfermedad.idEnfermedad = generatedKeys.getInt(1);
                    }
                }
                return enfermedad;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Enfermedad obtenerPorId(int idEnfermedad) {
        String sql = "SELECT * FROM Enfermedad WHERE id_enfermedad = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEnfermedad);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearEnfermedad(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Enfermedad obtenerPorNombre(String nombreEnfermedad) {
        String sql = "SELECT * FROM Enfermedad WHERE nombre_enfermedad = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreEnfermedad);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearEnfermedad(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Enfermedad> obtenerTodas() {
        List<Enfermedad> enfermedades = new ArrayList<>();
        String sql = "SELECT * FROM Enfermedad";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                enfermedades.add(mapearEnfermedad(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enfermedades;
    }

    public List<Enfermedad> obtenerPorTipo(String tipoEnfermedad) {
        List<Enfermedad> enfermedades = new ArrayList<>();
        String sql = "SELECT * FROM Enfermedad WHERE tipo_enfermedad = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipoEnfermedad);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                enfermedades.add(mapearEnfermedad(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enfermedades;
    }

    public List<Enfermedad> obtenerPorNivelRiesgo(String nivelRiesgo) {
        List<Enfermedad> enfermedades = new ArrayList<>();
        String sql = "SELECT * FROM Enfermedad WHERE nivel_riesgo = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nivelRiesgo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                enfermedades.add(mapearEnfermedad(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enfermedades;
    }

    public Enfermedad actualizar(Enfermedad enfermedad) {
        String sql = "UPDATE Enfermedad SET nombre_enfermedad = ?, tipo_enfermedad = ?, sintomas = ?, duracion_estimada = ?, tratamientos_recomendados = ?, id_medicamento = ?, nivel_riesgo = ?, modo_transmision = ?, id_analisis = ? WHERE id_enfermedad = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, enfermedad.getNombreEnfermedad());
            stmt.setString(2, enfermedad.getTipoEnfermedad());
            stmt.setString(3, enfermedad.getSintomas());
            stmt.setInt(4, enfermedad.getDuracionEstimada());
            stmt.setString(5, enfermedad.getTratamientosRecomendados());

            if (enfermedad.getIdMedicamento() != null) {
                stmt.setInt(6, enfermedad.getIdMedicamento().getIdMedicamento());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setString(7, enfermedad.getNivelRiesgo());
            stmt.setString(8, enfermedad.getModoTransmision());

            if (enfermedad.getIdAnalisis() != null) {
                stmt.setInt(9, enfermedad.getIdAnalisis().getIdAnalisis());
            } else {
                stmt.setNull(9, Types.INTEGER);
            }

            stmt.setInt(10, enfermedad.getIdEnfermedad());

            if (stmt.executeUpdate() > 0) {
                return enfermedad;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean eliminar(int idEnfermedad) {
        String sql = "DELETE FROM Enfermedad WHERE id_enfermedad = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEnfermedad);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Enfermedad mapearEnfermedad(ResultSet rs) throws SQLException {
        MedicamentoRepository medicamentoRepository = new MedicamentoRepository();
        AnalisisRepository analisisRepository = new AnalisisRepository();

        Medicamento medicamento = null;
        if (rs.getInt("id_medicamento") > 0) {
            medicamento = medicamentoRepository.obtenerPorId(rs.getInt("id_medicamento"));
        }

        Analisis analisis = null;
        if (rs.getInt("id_analisis") > 0) {
            analisis = analisisRepository.obtenerPorId(rs.getInt("id_analisis"));
        }

        return new Enfermedad(
                rs.getInt("id_enfermedad"),
                rs.getString("nombre_enfermedad"),
                rs.getString("tipo_enfermedad"),
                rs.getString("sintomas"),
                rs.getInt("duracion_estimada"),
                rs.getString("tratamientos_recomendados"),
                medicamento,
                "",
                rs.getString("nivel_riesgo"),
                rs.getString("modo_transmision"),
                analisis
        );
    }
}