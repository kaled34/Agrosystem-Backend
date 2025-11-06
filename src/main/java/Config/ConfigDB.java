package Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.util.security.Password;

import javax.sql.DataSource;


public class ConfigDB {
    private static HikariDataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            String host = "127.0.0.1";
            String port = "3306";
            String user = "root";
            String dbName = "agrova";
            String username = "root";
            String password = "root";
            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

            HikariConfig conf = new HikariConfig();
            conf.setJdbcUrl(url);
            conf.setUsername(user);
            conf.setPassword(password);
            conf.setDriverClassName("com.mysql.cj.jdbc.Driver");

            dataSource = new HikariDataSource(conf);
        }
        return dataSource;
    }

}