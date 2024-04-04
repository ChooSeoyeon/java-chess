package chess.database;

public class ProductionDatabaseConnectionManager extends DatabaseConnectionManager {
    private static final String PROPERTIES_PATH = "src/main/java/chess/resource/production.yml";

    @Override
    protected String getPropertiesPath() {
        return PROPERTIES_PATH;
    }
}
