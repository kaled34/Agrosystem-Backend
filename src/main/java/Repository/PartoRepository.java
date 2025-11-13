package Repository;

import Model.Parto;
import Model.Animales;
import Config.ConfigDB;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PartoRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public boolean crear(Parto parto) {
        String sql = "INSERT INTO Parto (id_madre, fecha, cantidadCrias) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, parto.getIdMadre().getIdAnimal());
            stmt.setDate(2, Date.valueOf(parto.getFecha()));
            stmt.setInt(3, parto.getCantidadCrias());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        parto.idParto = generatedKeys.getInt(1);
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

    public Parto obtenerPorId(int idParto) {
        String sql = "SELECT * FROM Parto WHERE id_Parto = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idParto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearParto(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Parto> obtenerTodos() {
        List<Parto> partos = new ArrayList<>();
        String sql = "SELECT * FROM Parto ORDER BY fecha DESC";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                partos.add(mapearParto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partos;
    }

    public List<Parto> obtenerPorMadre(int idMadre) {
        List<Parto> partos = new ArrayList<>();
        String sql = "SELECT * FROM Parto WHERE id_madre = ? ORDER BY fecha DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMadre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                partos.add(mapearParto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partos;
    }

    public Parto obtenerUltimoPartoPorMadre(int idMadre) {
        String sql = "SELECT * FROM Parto WHERE id_madre = ? ORDER BY fecha DESC LIMIT 1";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMadre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearParto(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Parto> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Parto> partos = new ArrayList<>();
        String sql = "SELECT * FROM Parto WHERE fecha BETWEEN ? AND ? ORDER BY fecha DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fechaInicio));
            stmt.setDate(2, Date.valueOf(fechaFin));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                partos.add(mapearParto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partos;
    }

    public List<Parto> obtenerPartosDelAño(int año) {
        List<Parto> partos = new ArrayList<>();
        String sql = "SELECT * FROM Parto WHERE YEAR(fecha) = ? ORDER BY fecha DESC";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, año);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                partos.add(mapearParto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partos;
    }

    public int contarTotalCriasPorMadre(int idMadre) {
        String sql = "SELECT SUM(cantidadCrias) as total FROM Parto WHERE id_madre = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMadre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double obtenerPromedioMadre(int idMadre) {
        String sql = "SELECT AVG(cantidadCrias) as promedio FROM Parto WHERE id_madre = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMadre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("promedio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public boolean actualizar(Parto parto) {
        String sql = "UPDATE Parto SET fecha = ?, cantidadCrias = ? WHERE id_Parto = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(parto.getFecha()));
            stmt.setInt(2, parto.getCantidadCrias());
            stmt.setInt(3, parto.getIdParto());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idParto) {
        String sql = "DELETE FROM Parto WHERE id_Parto = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idParto);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Parto mapearParto(ResultSet rs) throws SQLException {
        AnimalesRepository animalRepository = new AnimalesRepository();
        Animales madre = animalRepository.obtenerPorId(rs.getInt("id_madre"));

        return new Parto(
                rs.getInt("id_Parto"),
                madre,
                rs.getDate("fecha").toLocalDate(),
                rs.getInt("cantidadCrias")
        );
    }
}