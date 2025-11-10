package Repository;

import Config.ConfigDB;
import Model.TarjetaSalud;
import Model.Animales;
import Model.Enfermedad;
import Model.Tratamiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarjetaSaludRepository {

    public TarjetaSalud crear(TarjetaSalud tarjeta) {
        String sql = "INSERT INTO tarjetasalud (idAnimal, idEnfermedad, idTratamiento, " +
                "historialEnfermedades, historialTratamientos, descripcionReporte) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setObject(1, tarjeta.getIdAnimal() != null ? tarjeta.getIdAnimal().getIdAnimal() : null);
            stmt.setObject(2, tarjeta.getIdEnfermedad() != null ? tarjeta.getIdEnfermedad().getIdEnfermedad() : null);
            stmt.setObject(3, tarjeta.getIdTratamiento() != null ? tarjeta.getIdTratamiento().getIdTratamiento() : null);
            stmt.setString(4, tarjeta.getHistorialEnfermedades());
            stmt.setString(5, tarjeta.getHistorialTratamientos());
            stmt.setString(6, tarjeta.getDescripcionReporte());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tarjeta.idTarjeta = rs.getInt(1);
                }
            }

            return tarjeta;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear tarjeta de salud: " + e.getMessage(), e);
        }
    }

    public TarjetaSalud buscarPorId(int idTarjeta) {
        String sql = "SELECT * FROM tarjetasalud WHERE idTarjeta = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTarjeta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTarjeta(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar tarjeta: " + e.getMessage(), e);
        }

        return null;
    }

    public List<TarjetaSalud> obtenerTodos() {
        List<TarjetaSalud> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM tarjetasalud";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tarjetas.add(mapearTarjeta(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener tarjetas: " + e.getMessage(), e);
        }

        return tarjetas;
    }

    public TarjetaSalud actualizar(TarjetaSalud tarjeta) {
        String sql = "UPDATE tarjetasalud SET idAnimal = ?, idEnfermedad = ?, idTratamiento = ?, " +
                "historialEnfermedades = ?, historialTratamientos = ?, descripcionReporte = ? " +
                "WHERE idTarjeta = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, tarjeta.getIdAnimal() != null ? tarjeta.getIdAnimal().getIdAnimal() : null);
            stmt.setObject(2, tarjeta.getIdEnfermedad() != null ? tarjeta.getIdEnfermedad().getIdEnfermedad() : null);
            stmt.setObject(3, tarjeta.getIdTratamiento() != null ? tarjeta.getIdTratamiento().getIdTratamiento() : null);
            stmt.setString(4, tarjeta.getHistorialEnfermedades());
            stmt.setString(5, tarjeta.getHistorialTratamientos());
            stmt.setString(6, tarjeta.getDescripcionReporte());
            stmt.setInt(7, tarjeta.getIdTarjeta());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return tarjeta;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tarjeta: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean eliminar(int idTarjeta) {
        String sql = "DELETE FROM tarjetasalud WHERE idTarjeta = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTarjeta);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar tarjeta: " + e.getMessage(), e);
        }
    }

    public TarjetaSalud buscarPorAnimal(int idAnimal) {
        String sql = "SELECT * FROM tarjetasalud WHERE idAnimal = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAnimal);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTarjeta(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por animal: " + e.getMessage(), e);
        }

        return null;
    }

    public int obtenerTotal() {
        String sql = "SELECT COUNT(*) FROM tarjetasalud";

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

    private TarjetaSalud mapearTarjeta(ResultSet rs) throws SQLException {
        TarjetaSalud tarjeta = new TarjetaSalud();
        tarjeta.idTarjeta = rs.getInt("idTarjeta");

        int idAnimal = rs.getInt("idAnimal");
        if (!rs.wasNull()) {
            Animales animal = new Animales();
            animal.idAnimal = idAnimal;
            tarjeta.idAnimal = animal;
        }

        int idEnfermedad = rs.getInt("idEnfermedad");
        if (!rs.wasNull()) {
            Enfermedad enfermedad = new Enfermedad();
            enfermedad.idEnfermedad = idEnfermedad;
            tarjeta.idEnfermedad = enfermedad;
        }

        int idTratamiento = rs.getInt("idTratamiento");
        if (!rs.wasNull()) {
            Tratamiento tratamiento = new Tratamiento();
            tratamiento.idTratamiento = idTratamiento;
            tarjeta.idTratamiento = tratamiento;
        }

        tarjeta.historialEnfermedades = rs.getString("historialEnfermedades");
        tarjeta.historialTratamientos = rs.getString("historialTratamientos");
        tarjeta.descripcionReporte = rs.getString("descripcionReporte");

        return tarjeta;
    }
}