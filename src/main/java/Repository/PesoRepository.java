package Repository;

import Model.Peso;
import Model.Animales;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PesoRepository {
    private Connection connection;
    private AnimalesRepository animalRepository;

    public PesoRepository(Connection connection) {
        this.connection = connection;
        this.animalRepository = new AnimalesRepository(connection);
    }

    // Crear un nuevo registro de peso
    public boolean crear(Peso peso) {
        String sql = "INSERT INTO PesoAnimal (id_animal, id_usuarioRegistro, fecha_medicion, peso_kg, condicion_corporal, observaciones) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, peso.getIdAnimal().getIdAnimal());
            stmt.setInt(2, 1); // id_usuarioRegistro - deberías pasarlo como parámetro
            stmt.setDate(3, Date.valueOf(peso.getFechaMedicion()));
            stmt.setDouble(4, peso.getPesoActual());
            stmt.setString(5, peso.getCondicionCorporal());
            stmt.setString(6, peso.getObservaciones());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        peso.idPeso = generatedKeys.getInt(1);
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener peso por ID
    public Peso obtenerPorId(int idPeso) {
        String sql = "SELECT * FROM PesoAnimal WHERE id_peso = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPeso);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearPeso(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todos los registros de peso
    public List<Peso> obtenerTodos() {
        List<Peso> pesos = new ArrayList<>();
        String sql = "SELECT * FROM PesoAnimal ORDER BY fecha_medicion DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pesos.add(mapearPeso(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesos;
    }

    // Obtener pesos por animal
    public List<Peso> obtenerPorAnimal(int idAnimal) {
        List<Peso> pesos = new ArrayList<>();
        String sql = "SELECT * FROM PesoAnimal WHERE id_animal = ? ORDER BY fecha_medicion DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pesos.add(mapearPeso(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesos;
    }

    // Obtener el peso más reciente de un animal
    public Peso obtenerUltimoPesoPorAnimal(int idAnimal) {
        String sql = "SELECT * FROM PesoAnimal WHERE id_animal = ? ORDER BY fecha_medicion DESC LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearPeso(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener pesos por rango de fechas
    public List<Peso> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Peso> pesos = new ArrayList<>();
        String sql = "SELECT * FROM PesoAnimal WHERE fecha_medicion BETWEEN ? AND ? ORDER BY fecha_medicion DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fechaInicio));
            stmt.setDate(2, Date.valueOf(fechaFin));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pesos.add(mapearPeso(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesos;
    }

    // Obtener pesos de un animal en un rango de fechas
    public List<Peso> obtenerPorAnimalYRangoFechas(int idAnimal, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Peso> pesos = new ArrayList<>();
        String sql = "SELECT * FROM PesoAnimal WHERE id_animal = ? AND fecha_medicion BETWEEN ? AND ? ORDER BY fecha_medicion DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            stmt.setDate(2, Date.valueOf(fechaInicio));
            stmt.setDate(3, Date.valueOf(fechaFin));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pesos.add(mapearPeso(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesos;
    }

    // Calcular ganancia de peso promedio
    public double calcularGananciaPromedio(int idAnimal) {
        String sql = "SELECT AVG(peso_kg) as promedio FROM PesoAnimal WHERE id_animal = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("promedio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Actualizar registro de peso
    public boolean actualizar(Peso peso) {
        String sql = "UPDATE PesoAnimal SET id_animal = ?, fecha_medicion = ?, peso_kg = ?, condicion_corporal = ?, observaciones = ? WHERE id_peso = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, peso.getIdAnimal().getIdAnimal());
            stmt.setDate(2, Date.valueOf(peso.getFechaMedicion()));
            stmt.setDouble(3, peso.getPesoActual());
            stmt.setString(4, peso.getCondicionCorporal());
            stmt.setString(5, peso.getObservaciones());
            stmt.setInt(6, peso.getIdPeso());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar registro de peso
    public boolean eliminar(int idPeso) {
        String sql = "DELETE FROM PesoAnimal WHERE id_peso = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPeso);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para mapear ResultSet a objeto Peso
    private Peso mapearPeso(ResultSet rs) throws SQLException {
        Animales animal = animalRepository.obtenerPorId(rs.getInt("id_animal"));

        // Nota: El modelo Peso tiene pesoNacimiento pero la BD no lo tiene en PesoAnimal
        // Se asume que pesoNacimiento viene de Animal.peso_inicial
        double pesoNacimiento = animal != null ? animal.getPesoInicial() : 0.0;

        return new Peso(
                rs.getInt("id_peso"),
                animal,
                pesoNacimiento,
                rs.getDouble("peso_kg"),
                rs.getDate("fecha_medicion").toLocalDate(),
                rs.getString("condicion_corporal"),
                rs.getString("observaciones")
        );
    }
}