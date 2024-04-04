package chess.database;

public class TestDatabaseConnectionManager extends DatabaseConnectionManager {
    private static final String PROPERTIES_PATH = "src/main/java/chess/resource/test.yml";

    @Override
    protected String getPropertiesPath() {
        return PROPERTIES_PATH;
    }
}
