package chess.database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DatabaseConnectionManager {
    protected final Properties properties;

    public DatabaseConnectionManager() {
        this.properties = loadProperties(getPropertiesPath());
    }

    protected abstract String getPropertiesPath();

    private static Properties loadProperties(String configurationFileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(configurationFileName);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (Exception e) {
            throw new IllegalArgumentException("properties 파일을 읽는데 실패했습니다.");
        }
    }

    public Connection getConnection() {
        try {
            String server = properties.get("server").toString();
            String database = properties.get("database").toString();
            String option = properties.get("option").toString();
            String username = properties.get("username").toString();
            String password = properties.get("password").toString();
            String jdbcUrl = "jdbc:mysql://" + server + "/" + database + option;
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결을 실패했습니다.");
        }
    }
}
