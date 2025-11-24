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
import Model.Peso;

public class PesoRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public Peso crear(Peso peso) {
        String sql = "INSERT INTO PesoAnimal (id_animal, id_usuarioRegistro, fecha_medicion, peso_kg, condicion_corporal, observaciones) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, peso.getIdAnimal().getIdAnimal());
            stmt.setInt(2, 1);
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
                return peso;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Peso obtenerPorId(int idPeso) {
        String sql = "SELECT * FROM PesoAnimal WHERE id_peso = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPeso);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPeso(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Peso> obtenerTodos() {
        List<Peso> pesos = new ArrayList<>();
        String sql = "SELECT * FROM PesoAnimal ORDER BY fecha_medicion DESC";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pesos.add(mapearPeso(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesos;
    }

    public List<Peso> obtenerPorAnimal(int idAnimal) {
        List<Peso> pesos = new ArrayList<>();
        String sql = "SELECT * FROM PesoAnimal WHERE id_animal = ? ORDER BY fecha_medicion DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pesos.add(mapearPeso(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesos;
    }

    public Peso obtenerUltimoPesoPorAnimal(int idAnimal) {
        String sql = "SELECT * FROM PesoAnimal WHERE id_animal = ? ORDER BY fecha_medicion DESC LIMIT 1";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPeso(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Peso> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Peso> pesos = new ArrayList<>();
        String sql = "SELECT * FROM PesoAnimal WHERE fecha_medicion BETWEEN ? AND ? ORDER BY fecha_medicion DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fechaInicio));
            stmt.setDate(2, Date.valueOf(fechaFin));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pesos.add(mapearPeso(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesos;
    }

    public List<Peso> obtenerPorAnimalYRangoFechas(int idAnimal, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Peso> pesos = new ArrayList<>();
        String sql = "SELECT * FROM PesoAnimal WHERE id_animal = ? AND fecha_medicion BETWEEN ? AND ? ORDER BY fecha_medicion DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            stmt.setDate(2, Date.valueOf(fechaInicio));
            stmt.setDate(3, Date.valueOf(fechaFin));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pesos.add(mapearPeso(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesos;
    }

    public double calcularGananciaPromedio(int idAnimal) {
        String sql = "SELECT AVG(peso_kg) as promedio FROM PesoAnimal WHERE id_animal = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("promedio");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public Peso actualizar(Peso peso) {
        String sql = "UPDATE PesoAnimal SET id_animal = ?, fecha_medicion = ?, peso_kg = ?, condicion_corporal = ?, observaciones = ? WHERE id_peso = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, peso.getIdAnimal().getIdAnimal());
            stmt.setDate(2, Date.valueOf(peso.getFechaMedicion()));
            stmt.setDouble(3, peso.getPesoActual());
            stmt.setString(4, peso.getCondicionCorporal());
            stmt.setString(5, peso.getObservaciones());
            stmt.setInt(6, peso.getIdPeso());

            if (stmt.executeUpdate() > 0) {
                return peso;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean eliminar(int idPeso) {
        String sql = "DELETE FROM PesoAnimal WHERE id_peso = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPeso);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Peso mapearPeso(ResultSet rs) throws SQLException {
        // Avoid opening a new connection here. Create a lightweight animal with only ID.
        Animales animal = new Animales();
        animal.idAnimal = rs.getInt("id_animal");

        double pesoNacimiento = 0.0; // peso inicial not loaded to avoid extra query

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