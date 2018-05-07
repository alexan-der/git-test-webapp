package alexander.zabenkov.business.specification;

import alexander.zabenkov.business.builder.SqlClausesBuilder;
import alexander.zabenkov.business.utils.SqlPartTableColumns;
import alexander.zabenkov.business.utils.SqlSortingOrder;

import java.sql.SQLException;
import java.util.Objects;

/**
 * The sorting criteria for managing Part data fetching.
 */
public class PartSpecificationWithSorting implements PartSpecification {

    /** Sorting */
    private SqlPartTableColumns column;
    private SqlSortingOrder order;

    public PartSpecificationWithSorting() {}

    public PartSpecificationWithSorting(SqlPartTableColumns column, SqlSortingOrder order) {
        this.column = column;
        this.order = order;
    }

    /**
     * Manages sorted Part data fetching
     * @return sorting sql-criteria
     */
    @Override
    public String toSqlClauses() throws SQLException {
        SqlClausesBuilder sqlClausesBuilder = new SqlClausesBuilder();
        return sqlClausesBuilder.
                with(column, order).
                build();
    }

    public SqlPartTableColumns getSortingColumn() {
        return column;
    }

    /**
     * Sets column for sorting
     * @param column column for sorting
     */
    public void setSortingColumn(SqlPartTableColumns column) {
        this.column = column;
    }

    public SqlSortingOrder getSortingOrder() {
        return order;
    }

    /**
     * Sets sorting order
     * @param order sorting order
     */
    public void setSortingOrder(SqlSortingOrder order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof PartSpecificationWithSorting)) return false;
        PartSpecificationWithSorting that = (PartSpecificationWithSorting) o;
        return column == that.column &&
                order == that.order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, order);
    }
}
