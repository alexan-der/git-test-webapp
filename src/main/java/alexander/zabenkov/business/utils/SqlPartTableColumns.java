package alexander.zabenkov.business.utils;

/**
 * Enumeration of part table column names
 */
public enum SqlPartTableColumns {

    ID("id"),
    NAME("part_name"),
    NUMBER("part_number"),
    VENDOR("vendor"),
    QTY("qty"),
    SHIPPED("shipped"),
    RECEIVE("receive");

    private String name;

    SqlPartTableColumns(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return column string-name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns enum object by string-name
     * @param name string-name
     * @return enum object by string-name
     */
    public static SqlPartTableColumns getColumnByName(String name) {
        for (SqlPartTableColumns column : SqlPartTableColumns.values()) {
            if (column.getName().equals(name))
                return column;
        }

        return ID;
    }
}
