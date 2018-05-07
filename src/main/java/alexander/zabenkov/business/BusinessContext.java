package alexander.zabenkov.business;

import alexander.zabenkov.business.service.DbSchemaMigrationService;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Business context for managing business "beans":
 * - Database connection
 * - Database schema migration service
 */
public class BusinessContext {
    private static Logger logger = Logger.getLogger(BusinessContext.class.getName());

    /** Database connection */
    private static Connection connection;

    /** Database schema migration service */
    private static DbSchemaMigrationService migrationService;

    /**
     * Creates singleton of database connection
     * @return singleton of database connection
     */
    public static Connection getConnection() {
        if (connection != null)
            return connection;

        try {
            InputStream inputStream = BusinessContext.class.getClassLoader()
                    .getResourceAsStream("config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            String driver = properties.getProperty("jdbc.driver");
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");

            inputStream.close();
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            logger.severe("[JDBC Error] Unable to create a database connection: " + e.getMessage());
        }

        return connection;
    }

    /**
     * Creates singleton of database schema migration service
     * @return singleton of database schema migration service
     */
    public static DbSchemaMigrationService getSchemaMigrator() {
        if (migrationService != null)
            return migrationService;

        migrationService = new DbSchemaMigrationService(getConnection());

        return migrationService;
    }

}
