package Repository;

import Model.EstadisticaAnimales;
import Config.ConfigDB;
import java.sql.*;

public class EstadisticasRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public EstadisticaAnimales obtenerEstadisticasAnimales() {
        String sql = "SELECT " +
                "COUNT(*) as total, " +
                "SUM(CASE WHEN sexo = 'M' THEN 1 ELSE 0 END) as machos, " +
                "SUM(CASE WHEN sexo = 'F' THEN 1 ELSE 0 END) as hembras " +
                "FROM animal";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return new EstadisticaAnimales(
                        rs.getInt("total"),
                        rs.getInt("machos"),
                        rs.getInt("hembras")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener estadísticas de animales: " + e.getMessage());
            e.printStackTrace();
        }
        return new EstadisticaAnimales(0, 0, 0);
    }
}