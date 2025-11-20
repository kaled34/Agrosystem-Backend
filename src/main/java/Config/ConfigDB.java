package Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class ConfigDB {
    private static HikariDataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            String host = "13.216.106.104"; // aquí tiene la ip de la instancia si lo quieren local le ponen la ip del workbench 127.0.0.1 13.216.106.104
            String port = "3306";
            String dbName = "agrova";
            String username = "AgroSystem";  // para instancia es AgroSystem
            String password = "root";
            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            config.setMaximumPoolSize(5);
            config.setMinimumIdle(1);
            config.setConnectionTimeout(10000);
            config.setIdleTimeout(300000);
            config.setMaxLifetime(600000);
            config.setLeakDetectionThreshold(60000);

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            try {
                dataSource = new HikariDataSource(config);
                System.out.println("✅ Conexión a la base de datos establecida correctamente");
            } catch (Exception e) {
                System.err.println("❌ Error al conectar con la base de datos: " + e.getMessage());
                throw new RuntimeException("No se pudo establecer conexión con MySQL", e);
            }
        }
        return dataSource;
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Conexión a la base de datos cerrada");
        }
    }
}