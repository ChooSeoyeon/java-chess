package chess.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String JDBC_URL = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;

    private DatabaseConnectionManager() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 연결을 실패했습니다.");
        }
    }
}
