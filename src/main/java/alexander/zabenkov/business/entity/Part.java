package alexander.zabenkov.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * POJO class for Part entity.
 */
public class Part implements Serializable {

    /** Part primary key */
    private Long id;

    /** Part Name (String) */
    private String name;

    /** Part Number (String) */
    private String number;

    /** Vendor (String) */
    private String vendor;

    /** Qty (Integer) */
    private Integer qty;

    /** Shipped (Date) */
    private Date shipped;

    /** Receive (Date) */
    private Date receive;

    /**
     * Constructors
     */

    public Part() {}

    public Part(Long id, String name, String number, String vendor, Integer qty, Date shipped, Date receive) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.vendor = vendor;
        this.qty = qty;
        this.shipped = shipped;
        this.receive = receive;
    }

    /**
     * Getters and setters
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Date getShipped() {
        return shipped;
    }

    public void setShipped(Date shipped) {
        this.shipped = shipped;
    }

    public Date getReceive() {
        return receive;
    }

    public void setReceive(Date receive) {
        this.receive = receive;
    }
}
