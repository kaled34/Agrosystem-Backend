package Repository;

import Model.TarjetaSalud;
import Model.Animales;
import Model.Enfermedad;
import Model.Tratamiento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarjetaSaludRepository {
    private Connection connection;
    private AnimalesRepository animalRepository;
    private EnfermedadRepository enfermedadRepository;
    private TratamientoRepository tratamientoRepository;

    public TarjetaSaludRepository(Connection connection) {
        this.connection = connection;
        this.animalRepository = new AnimalesRepository(connection);
        this.enfermedadRepository = new EnfermedadRepository(connection);
        this.tratamientoRepository = new TratamientoRepository(connection);
    }

    // Crear una nueva tarjeta de salud
    public TarjetaSalud crear(TarjetaSalud tarjeta) {
        String sql = "INSERT INTO TarjetaSalud (id_animal, id_enfermedad, id_tratamiento) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

    // Obtener tarjeta de salud por ID
    public TarjetaSalud obtenerPorId(int idTarjeta) {
        String sql = "SELECT * FROM TarjetaSalud WHERE id_tarjeta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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

    // Obtener todas las tarjetas de salud
    public List<TarjetaSalud> obtenerTodas() {
        List<TarjetaSalud> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM TarjetaSalud";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tarjetas.add(mapearTarjetaSalud(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarjetas;
    }

    // Obtener tarjetas de salud por animal
    public List<TarjetaSalud> obtenerPorAnimal(int idAnimal) {
        List<TarjetaSalud> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM TarjetaSalud WHERE id_animal = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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

    // Obtener tarjetas de salud por enfermedad
    public List<TarjetaSalud> obtenerPorEnfermedad(int idEnfermedad) {
        List<TarjetaSalud> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM TarjetaSalud WHERE id_enfermedad = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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

    // Obtener tarjeta de salud por tratamiento
    public TarjetaSalud obtenerPorTratamiento(int idTratamiento) {
        String sql = "SELECT * FROM TarjetaSalud WHERE id_tratamiento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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

    // Actualizar tarjeta de salud
    public TarjetaSalud actualizar(TarjetaSalud tarjeta) {
        String sql = "UPDATE TarjetaSalud SET id_animal = ?, id_enfermedad = ?, id_tratamiento = ? WHERE id_tarjeta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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

    // Eliminar tarjeta de salud
    public boolean eliminar(int idTarjeta) {
        String sql = "DELETE FROM TarjetaSalud WHERE id_tarjeta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTarjeta);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para mapear ResultSet a objeto TarjetaSalud
    private TarjetaSalud mapearTarjetaSalud(ResultSet rs) throws SQLException {
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