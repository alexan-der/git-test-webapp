package alexander.zabenkov.business.specification;

import alexander.zabenkov.business.builder.SqlClausesBuilder;

import static alexander.zabenkov.business.utils.SqlPartTableColumns.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

/**
 * The criteria for fetching Part data with column-parameters criteria.
 */
public class PartSpecificationWithAllFieldsAndSorting extends PartSpecificationWithSorting
        implements PartSpecification {

    /** Part Name */
    private String name;

    /** Part Number */
    private String number;

    /** Vendor */
    private String vendor;

    /** Qty */
    private Integer qty;

    /** Shipped */
    private Date shippedAfter;
    private Date shippedBefore;

    /** Receive */
    private Date receiveAfter;
    private Date receiveBefore;

    /**
     * Manages sorted Part data fetching with column-parameters criteria
     * @return sorting sql-criteria and column-parameters criteria
     */
    @Override
    public String toSqlClauses() throws SQLException {
        SqlClausesBuilder sqlClausesBuilder = new SqlClausesBuilder();
        return sqlClausesBuilder.
                with(NAME, name).
                with(NUMBER, number).
                with(VENDOR, vendor).
                with(QTY, qty).
                with(SHIPPED, shippedAfter, shippedBefore).
                with(RECEIVE, receiveAfter, receiveBefore).
                build() + super.toSqlClauses();
    }

    /**
     * Sets criteria for column "name"
     * @param name value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets criteria for column "number"
     * @param number value
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Sets criteria for column "vendor"
     * @param vendor value
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * Sets criteria for column "qty"
     * @param qty value
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * Sets "after" criteria for column "shipped"
     * @param shippedAfter "after" criteria value
     */
    public void setShippedAfter(Date shippedAfter) {
        this.shippedAfter = shippedAfter;
    }

    /**
     * Sets "before" criteria for column "shipped"
     * @param shippedBefore "before" criteria value
     */
    public void setShippedBefore(Date shippedBefore) {
        this.shippedBefore = shippedBefore;
    }

    /**
     * Sets "after" criteria for column "receive"
     * @param receiveAfter "after" criteria value
     */
    public void setReceiveAfter(Date receiveAfter) {
        this.receiveAfter = receiveAfter;
    }

    /**
     * Sets "before" criteria for column "receive"
     * @param receiveBefore "before" criteria value
     */
    public void setReceiveBefore(Date receiveBefore) {
        this.receiveBefore = receiveBefore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof PartSpecificationWithAllFieldsAndSorting)) return false;
        if (!super.equals(o)) return false;
        PartSpecificationWithAllFieldsAndSorting that = (PartSpecificationWithAllFieldsAndSorting) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(number, that.number) &&
                Objects.equals(vendor, that.vendor) &&
                Objects.equals(qty, that.qty) &&
                Objects.equals(shippedAfter, that.shippedAfter) &&
                Objects.equals(shippedBefore, that.shippedBefore) &&
                Objects.equals(receiveAfter, that.receiveAfter) &&
                Objects.equals(receiveBefore, that.receiveBefore);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, number, vendor, qty, shippedAfter, shippedBefore, receiveAfter, receiveBefore);
    }
}
