package Repository;

import Model.TarjetaSalud;
import Model.Animales;
import Model.Enfermedad;
import Model.Tratamiento;
import Config.ConfigDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "SELECT * FROM TarjetaSalud WHERE id_tarjeta = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTarjeta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearTarjetaSalud(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TarjetaSalud> obtenerTodas() {
        List<TarjetaSalud> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM TarjetaSalud";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tarjetas.add(mapearTarjetaSalud(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarjetas;
    }

    public List<TarjetaSalud> obtenerPorAnimal(int idAnimal) {
        List<TarjetaSalud> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM TarjetaSalud WHERE id_animal = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tarjetas.add(mapearTarjetaSalud(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarjetas;
    }

    public List<TarjetaSalud> obtenerPorEnfermedad(int idEnfermedad) {
        List<TarjetaSalud> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM TarjetaSalud WHERE id_enfermedad = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEnfermedad);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tarjetas.add(mapearTarjetaSalud(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarjetas;
    }

    public TarjetaSalud obtenerPorTratamiento(int idTratamiento) {
        String sql = "SELECT * FROM TarjetaSalud WHERE id_tratamiento = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTratamiento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearTarjetaSalud(rs);
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
        AnimalesRepository animalRepository = new AnimalesRepository();
        EnfermedadRepository enfermedadRepository = new EnfermedadRepository();
        TratamientoRepository tratamientoRepository = new TratamientoRepository();

        Animales animal = animalRepository.obtenerPorId(rs.getInt("id_animal"));
        Enfermedad enfermedad = enfermedadRepository.obtenerPorId(rs.getInt("id_enfermedad"));
        Tratamiento tratamiento = tratamientoRepository.obtenerPorId(rs.getInt("id_tratamiento"));

        return new TarjetaSalud(
                rs.getInt("id_tarjeta"),
                animal,
                enfermedad,
                tratamiento
        );
    }
}