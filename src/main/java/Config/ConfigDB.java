package Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * Clase de configuración de base de datos para el backend Agrosystem.
 * Implementa el patrón Singleton para gestionar un pool de conexiones a MySQL
 * utilizando HikariCP para optimizar el rendimiento y gestión de conexiones.
 * 
 * <p>
 * Características del pool de conexiones:
 * </p>
 * <ul>
 * <li>Tamaño máximo del pool: 5 conexiones</li>
 * <li>Conexiones mínimas sin uso: 1</li>
 * <li>Timeout de conexión: 10 segundos</li>
 * <li>Tiempo máximo de vida: 10 minutos</li>
 * <li>Detección de fugas después de 60 segundos</li>
 * </ul>
 * 
 * <p>
 * Optimizaciones de rendimiento activadas:
 * </p>
 * <ul>
 * <li>Cache de Prepared Statements habilitado</li>
 * <li>Tamaño de cache: 250 statements</li>
 * <li>Límite SQL del cache: 2048 caracteres</li>
 * </ul>
 * 
 * @author Agrosystem Team
 * @version 1.0
 */
public class ConfigDB {
    private static HikariDataSource dataSource;

    /**
     * Obtiene una instancia del DataSource con pool de conexiones Hikari.
     * Implementa lazy initialization para crear el pool solo cuando se necesite.
     * 
     * <p>
     * Configuración de conexión:
     * </p>
     * <ul>
     * <li>Base de datos: MySQL</li>
     * <li>Nombre BD: agrova</li>
     * <li>Puerto: 3306</li>
     * <li>SSL deshabilitado para desarrollo</li>
     * <li>Timezone: UTC</li>
     * </ul>
     * 
     * @return DataSource configurado con HikariCP
     * @throws RuntimeException si no se puede establecer la conexión con MySQL
     */
    public static DataSource getDataSource() {
        if (dataSource == null) {
            String host = "127.0.0.1"; // Para producción: usar IP de instancia 13.216.106.104
            String port = "3306";
            String dbName = "agrova";
            String username = "root"; // Para producción: "AgroSystem"
            String password = "root";
            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                    + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // Configuración del pool de conexiones
            config.setMaximumPoolSize(5);
            config.setMinimumIdle(1);
            config.setConnectionTimeout(10000);
            config.setIdleTimeout(300000);
            config.setMaxLifetime(600000);
            config.setLeakDetectionThreshold(60000);

            // Optimizaciones de rendimiento
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

    /**
     * Cierra el pool de conexiones de HikariCP.
     * Debe llamarse cuando la aplicación se esté cerrando para liberar recursos.
     * Este método es called automáticamente por el shutdown hook en Main.java.
     */
    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Conexión a la base de datos cerrada");
        }
    }
}