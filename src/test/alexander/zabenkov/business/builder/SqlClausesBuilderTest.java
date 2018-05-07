package alexander.zabenkov.business.builder;

import alexander.zabenkov.business.utils.SqlPartTableColumns;
import alexander.zabenkov.business.utils.SqlSortingOrder;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test for builder of sql-clauses strings.
 */
public class SqlClausesBuilderTest {

    /**
     * Test of building sql-clauses string with sorting criteria.
     */
    @Test
    public void buildOnlyWithSorting() throws Exception {
        SqlClausesBuilder builder = new SqlClausesBuilder();
        String expectedSqlStr = " order by id ASC";
        String actualSqlStr = builder.
                with(SqlPartTableColumns.ID, SqlSortingOrder.ASC).
                build();

        assertEquals(expectedSqlStr, actualSqlStr);
    }

    /**
     * Test of building sql-clauses string with column-params and sorting criteria.
     */
    @Test
    public void buildWithManyParams() throws Exception {
        SqlClausesBuilder builder = new SqlClausesBuilder();
        String expectedSqlStr = " WHERE part_name LIKE '%name 1%' AND shipped >= '2018-05-06 +03' AND shipped < '2018-05-06 +03' order by id ASC";
        String actualSqlStr = builder.
                with(SqlPartTableColumns.NAME, "name 1").
                with(SqlPartTableColumns.SHIPPED, new Date(1525620740264L), new Date(1525620740284L)).
                with(SqlPartTableColumns.ID, SqlSortingOrder.ASC).
                build();

        assertEquals(expectedSqlStr, actualSqlStr);
    }
}