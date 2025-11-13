package Repository;

import Model.Analisis;
import Config.ConfigDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnalisisRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public boolean crear(Analisis analisis) {
        String sql = "INSERT INTO Analisis (tipo_analisis, resultado, interpretacion) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, analisis.getTipoAnalisis());
            stmt.setString(2, analisis.getResultado());
            stmt.setString(3, analisis.getInterpretacion());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        analisis.idAnalisis = generatedKeys.getInt(1);
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

    public Analisis obtenerPorId(int idAnalisis) {
        String sql = "SELECT * FROM Analisis WHERE id_analisis = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnalisis);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearAnalisis(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Analisis> obtenerTodos() {
        List<Analisis> analisisList = new ArrayList<>();
        String sql = "SELECT * FROM Analisis";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                analisisList.add(mapearAnalisis(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return analisisList;
    }

    public List<Analisis> obtenerPorTipo(String tipoAnalisis) {
        List<Analisis> analisisList = new ArrayList<>();
        String sql = "SELECT * FROM Analisis WHERE tipo_analisis = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipoAnalisis);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                analisisList.add(mapearAnalisis(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return analisisList;
    }

    public boolean actualizar(Analisis analisis) {
        String sql = "UPDATE Analisis SET tipo_analisis = ?, resultado = ?, interpretacion = ? WHERE id_analisis = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, analisis.getTipoAnalisis());
            stmt.setString(2, analisis.getResultado());
            stmt.setString(3, analisis.getInterpretacion());
            stmt.setInt(4, analisis.getIdAnalisis());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idAnalisis) {
        String sql = "DELETE FROM Analisis WHERE id_analisis = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnalisis);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Analisis mapearAnalisis(ResultSet rs) throws SQLException {
        return new Analisis(
                rs.getInt("id_analisis"),
                rs.getString("tipo_analisis"),
                rs.getString("resultado"),
                rs.getString("interpretacion")
        );
    }
}