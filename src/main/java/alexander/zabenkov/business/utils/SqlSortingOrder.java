package alexander.zabenkov.business.utils;

/**
 * Enumeration of sorting orders
 */
public enum SqlSortingOrder {

    ASC("ASC"),
    DESC("DESC");

    String order;

    SqlSortingOrder(String order){
        this.order = order;
    }

    @Override
    public String toString() {
        return order;
    }

    /**
     * Returns enum object by string-name
     * @param name string-name
     * @return enum object by string-name
     */
    public static SqlSortingOrder getOrderByName(String name) {
        for (SqlSortingOrder column : SqlSortingOrder.values()) {
            if (column.toString().equals(name))
                return column;
        }

        return ASC;
    }
}
