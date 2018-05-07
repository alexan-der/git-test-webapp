package alexander.zabenkov.business.repository;

import alexander.zabenkov.business.BusinessContext;
import alexander.zabenkov.business.entity.Part;
import alexander.zabenkov.business.specification.PartSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static alexander.zabenkov.business.utils.SqlPartTableColumns.*;

/**
 * The implementation of interface of the data access object for the Part entity.
 */
public class PartRepositoryImpl implements PartRepository {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * The database connection.
     */
    private Connection connection;

    public PartRepositoryImpl() {
        connection = BusinessContext.getConnection();
    }

    public PartRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a new record in database for the passed object
     * @param part passed Part object
     */
    @Override
    public void add(Part part) {
        // Not implemented
    }

    /**
     * Deletes the passed object in database
     * @param part passed object
     */
    @Override
    public void remove(Part part) {
        // Not implemented
    }

    /**
     * Updates the state of the passed object in database
     * @param part passed object
     */
    @Override
    public void update(Part part) {
        // Not implemented
    }

    /**
     * Returns a collection of objects that responds the specification
     * @param specification the parts data fetching specification
     * @return collection of Part entity objects
     */
    @Override
    public List query(PartSpecification specification) {
        List<Part> parts = new ArrayList<>();
        try {
            PreparedStatement prepStatement = connection.prepareStatement(
                    "SELECT * FROM part " + specification.toSqlClauses());

            ResultSet result = prepStatement.executeQuery();
            if (result != null) {
                while (result.next()) {
                    Part part = new Part(
                        result.getLong(ID.getName()),
                        result.getString(NAME.getName()),
                        result.getString(NUMBER.getName()),
                        result.getString(VENDOR.getName()),
                        result.getInt(QTY.getName()),
                        result.getDate(SHIPPED.getName()),
                        result.getDate(RECEIVE.getName())
                    );
                    parts.add(part);
                }
            }
        } catch (Exception e) {
            logger.severe("[JDBC Error] Unable to fetch the Part data. " + e.getMessage());
        }
        return parts;
    }
}
