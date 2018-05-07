package alexander.zabenkov.business.builder;

import alexander.zabenkov.business.BusinessContext;
import alexander.zabenkov.business.utils.SqlPartTableColumns;
import alexander.zabenkov.business.utils.SqlSortingOrder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Builder creates SQL-clauses for filtering Part data fetching.
 */
public class SqlClausesBuilder {
    private StringBuilder sqlClauses;
    private List<Object> values;

    public SqlClausesBuilder() {
        sqlClauses = new StringBuilder();
        values = new ArrayList<>();
    }

    /**
     * Appends to SQL-query criteria with String value.
     * @param column column-criteria
     * @param value criteria value
     * @return SQL-clauses builder for further usage
     */
    public SqlClausesBuilder with(SqlPartTableColumns column, String value) {
        if (value == null)
            return this;

        whereOrAnd();
        sqlClauses.append(column);
        sqlClauses.append(" LIKE ?");
        values.add(value);
        return this;
    }

    /**
     * Appends to SQL-query criteria with Integer value.
     * @param column column-criteria
     * @param value criteria value
     * @return SQL-clauses builder for further usage
     */
    public SqlClausesBuilder with(SqlPartTableColumns column, Integer value) {
        if (value == null)
            return this;

        whereOrAnd();
        sqlClauses.append(String.format("%s >= ?", column));
        values.add(value);
        return this;
    }

    /**
     * Appends to SQL-query criteria with Date-range values.
     * @param column column-criteria
     * @param after criteria Date after value
     * @param before criteria Date before value
     * @return SQL-clauses builder for further usage
     */
    public SqlClausesBuilder with(SqlPartTableColumns column, Date after, Date before) {
        if (after != null) {
            whereOrAnd();
            sqlClauses.append(String.format("%s >= ?", column));
            values.add(after);
        }
        if (before != null) {
            whereOrAnd();
            sqlClauses.append(String.format("%s < ?", column));
            values.add(before);
        }
        return this;
    }

    /**
     * Appends to SQL-query sorting criteria.
     * @param column column-criteria
     * @param order sorting order
     * @return SQL-clauses builder for further usage
     */
    public SqlClausesBuilder with(SqlPartTableColumns column, SqlSortingOrder order) {
        if (order == null)
            return this;

        sqlClauses.append(String.format(" order by %s %s;", column, order));
        return this;
    }

    /**
     * Builds SQL-query string.
     * @return SQL-query string.
     * @throws SQLException PrepareStatement
     */
    public String build() throws SQLException {
        PreparedStatement preparedStatement =
                BusinessContext.getConnection().prepareStatement(sqlClauses.toString());
        for (Object value : values) {
            Integer index = values.indexOf(value)+1;
            if (value instanceof String)
                preparedStatement.setString(index, "%" + value + "%");
            if (value instanceof Integer)
                preparedStatement.setInt(index, (Integer) value);
            if (value instanceof Date)
                preparedStatement.setDate(index, new java.sql.Date(((Date) value).getTime()));
        }
        return preparedStatement.toString();
    }

    /**
     * Appends to SQL-query "AND" operator.
     * If there are no other criteria, method appends "WHERE" operator.
     */
    private void whereOrAnd() {
        if (sqlClauses.length() > 0)
            sqlClauses.append(" AND ");
        else
            sqlClauses.append(" WHERE ");
    }
}
