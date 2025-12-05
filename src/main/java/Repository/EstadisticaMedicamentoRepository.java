package Repository;
import  java.sql.Connection;
import java.sql.PreparedStatement;
import  java.sql.ResultSet;
import java.sql.SQLException;
import Config.ConfigDB;
import Model.EstadisticaMedicamento;
import Model.Medicamento;

public class EstadisticaMedicamentoRepository {

    private Connection getConecction() throws SQLException {
        return  ConfigDB.getDataSource().getConnection();
    }

    public EstadisticaMedicamento obtenerMedicamentoMasUsado(){
        String  sql = "SELECT m.id_medicamento, m.nombre_medicamento, m.solucion, m.dosis, m.caducidad, " +
                "m.via_administracion, m.composicion, m.indicaciones, m.frecuencia_aplicacion, " +
                "COUNT(t.id_medicamento) as cantidad_usos " +
                "FROM medicamentos m " +
                "LEFT JOIN Tratamientos t ON m.id_medicamento = t.id_medicamento " +
                "GROUP BY m.id_medicamento " +
                "ORDER BY cantidad_usos DESC " +
                "LIMIT 1";

        try (Connection connection = getConecction();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Medicamento medicamento = new Medicamento(
                        rs.getInt("id_medicamento"),
                        rs.getString("nombre_medicamento"),
                        rs.getString("solucion"),
                        rs.getFloat("dosis"),
                        new java.util.Date(rs.getTimestamp("caducidad").getTime()),
                        rs.getString("via_administracion"),
                        rs.getString("composicion"),
                        rs.getString("indicaciones"),
                        rs.getString("frecuencia_aplicacion")
                );

                return new EstadisticaMedicamento(
                        rs.getInt("cantidad_usos"),
                        medicamento
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener medicamento más usado: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
