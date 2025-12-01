package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Config.ConfigDB;
import Model.EstadisticasTratamiento;

public class EstadisticasTratamientoRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public EstadisticasTratamiento obtenerEstadisticaTratamientoVsSanos() {
        String sqlEnTratamiento = "SELECT COUNT(DISTINCT id_animal) FROM Tratamientos WHERE fecha_fin IS NULL OR fecha_fin >= CURDATE()";
        String sqlTotal = "SELECT COUNT(*) FROM Animal";

        try (Connection connection = getConnection()) {
            int enTratamiento = 0;
            int total = 0;

            try (PreparedStatement stmt = connection.prepareStatement(sqlEnTratamiento);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    enTratamiento = rs.getInt(1);
                }
            }

            try (PreparedStatement stmt = connection.prepareStatement(sqlTotal);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }

            int sanos = total - enTratamiento;
            return new EstadisticasTratamiento(enTratamiento, sanos);

        } catch (SQLException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}