package alexander.zabenkov.business.specification;

import java.sql.SQLException;

/**
 * The criteria interface for managing data fetching.
 */
public interface PartSpecification {
    /**
     * Manages data fetching
     * @return sql-clauses after WHERE operator
     */
    String toSqlClauses() throws SQLException;
}
