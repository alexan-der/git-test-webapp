package alexander.zabenkov.business.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * The simple service for a database schema migration. Compares the current version of the schema
 * with the existing scripts in the project folder. If the version is different, service executes the script.
 */
public class DbSchemaMigrationService {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    /** Project folder with SQL-scripts */
    private static final String MIGRATIONS_DIR = "db_schema";
    /** Version separator in file name */
    private static final String VERSION_SEPARATOR = "__";

    /** Database connection */
    private Connection connection;

    public DbSchemaMigrationService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Initiates a database schema migration.
     * Scans the script files and compares it with the current version of the database schema.
     */
    public void migrateSchema() {
        logger.info("Database schema migration begins >>>>>>>>>>>");
        checkDbVersionTableExisting();
        ClassLoader classLoader = getClass().getClassLoader();
        File db_schema_folder = new File(classLoader.getResource(MIGRATIONS_DIR).getFile());
        for (File migrationFile : db_schema_folder.listFiles()) {
            if (migrationFile != null)
                processMigrationFile(migrationFile);
        }
        logger.info("<<<<<<<<<< Database schema migration ends");
    }

    /**
     * Handles a migration script file.
     * @param file script file
     */
    private void processMigrationFile(File file) {
        String fileVersion = getFileVersion(file);

        try {
            if (!isDbVersionExisted(fileVersion))
                makeSchemaMigration(file);
        } catch (SQLException e) {
            logger.severe("Error occurs in DB schema migration: ["+file.getName()+"]. " + e.getMessage());
        }
    }

    /**
     * @param file script file
     * @return file version, based on filename
     */
    private String getFileVersion(File file) {
        return file.getName().substring(0, file.getName().indexOf(VERSION_SEPARATOR));
    }

    /**
     * Reads a script file content and executes it like a sql-query.
     * @param file script file
     */
    private void makeSchemaMigration(File file) {
        logger.info("---------- Trying to migrate schema: ["+file.getName()+"].");
        try {
            connection.prepareStatement(
                    new String(Files.readAllBytes(Paths.get(file.toURI()))))
                    .execute();
        } catch (IOException e) {
            logger.severe("Unable to read migration file: " + file.getPath() + "." + e.getMessage());
        } catch (SQLException e) {
            logger.severe("Unable to execute migration script: " + file.getPath() + "." + e.getMessage());
        }
        logger.info("---------- Migration successful");
    }

    /**
     * Checking existing a database schema version.
     * @param version database schema version
     * @return true if schema version is existing, else false
     * @throws SQLException PreparedStatement
     */
    private boolean isDbVersionExisted(String version) throws SQLException {
        PreparedStatement versionStatement = connection.prepareStatement(
                "SELECT id FROM db_version WHERE version = ?");
        versionStatement.setString(1, version);
        ResultSet result = versionStatement.executeQuery();
        if (result != null && result.next())
            return true;

        return false;
    }

    /**
     * Checks existing a db_version table.
     */
    private void checkDbVersionTableExisting() {
        try {
            ResultSet result = connection.prepareStatement(
            "SELECT relname FROM pg_catalog.pg_class c " +
                "JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace " +
                "WHERE n.nspname = 'public' " +
                "AND c.relname = 'db_version' " +
                "AND c.relkind = 'r'").executeQuery();

            if (result != null && !result.next())
                initializeDbVersion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a db_version table.
     */
    private void initializeDbVersion() throws SQLException {
        connection.prepareStatement(
            "CREATE TABLE public.db_version" +
            "(" +
            "    id bigserial PRIMARY KEY NOT NULL," +
            "    version varchar(20) NOT NULL," +
            "    comment varchar(255)" +
            ");" +
            "CREATE UNIQUE INDEX db_version_id_uindex ON public.db_version (id);" +
            "COMMENT ON TABLE public.db_version IS 'Table for managing schema migration.';" +
            "INSERT INTO public.db_version" +
            "  (version, comment)" +
            "VALUES" +
            "  ('0', 'Database schema version initialization.');").execute();
    }
}
