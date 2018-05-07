package alexander.zabenkov.business.service;

import alexander.zabenkov.business.entity.Part;
import alexander.zabenkov.business.repository.PartRepository;
import alexander.zabenkov.business.repository.PartRepositoryImpl;
import alexander.zabenkov.business.specification.PartSpecificationWithAllFieldsAndSorting;
import alexander.zabenkov.business.utils.SqlPartTableColumns;
import alexander.zabenkov.business.utils.SqlSortingOrder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The service layer for Part data fetching and managing.
 */
public class PartService {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    /** Part repository */
    private PartRepository partRepository;

    public PartService() {
        partRepository = new PartRepositoryImpl();
    }

    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    /**
     * All Part data fetching.
     * @return collection of all Part data.
     */
    public List<Part> getAllParts() {
        List<Part> parts = new ArrayList<>();

        Map<String, String> filters = new HashMap<>();
        filters.put("sortingColumn", SqlPartTableColumns.ID.getName());
        filters.put("sortingOrder", SqlSortingOrder.ASC.toString());

        parts.addAll(getParts(filters));

        return parts;
    }

    /**
     * Fetches Part data that matches specified filter.
     * @param filters specified filter.
     * @return collection of fetched data.
     */
    public List<Part> getParts(Map<String, String> filters) {
        List<Part> parts = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        PartSpecificationWithAllFieldsAndSorting specification = new PartSpecificationWithAllFieldsAndSorting();
        if (filters.containsKey("part_name") && !filters.get("part_name").isEmpty())
            specification.setName(filters.get("part_name"));
        if (filters.containsKey("part_number") && !filters.get("part_number").isEmpty())
            specification.setNumber(filters.get("part_number"));
        if (filters.containsKey("vendor") && !filters.get("vendor").isEmpty())
            specification.setVendor(filters.get("vendor"));
        if (filters.containsKey("qty") && !filters.get("qty").isEmpty())
            specification.setQty(Integer.parseInt(filters.get("qty")));
        try {
            if (filters.containsKey("shippedAfter") && !filters.get("shippedAfter").isEmpty())
                specification.setShippedAfter(df.parse(filters.get("shippedAfter")));
            if (filters.containsKey("shippedBefore") && !filters.get("shippedBefore").isEmpty())
                specification.setShippedBefore(df.parse(filters.get("shippedBefore")));
            if (filters.containsKey("receiveAfter") && !filters.get("receiveAfter").isEmpty())
                specification.setReceiveAfter(df.parse(filters.get("receiveAfter")));
            if (filters.containsKey("receiveBefore") && !filters.get("receiveBefore").isEmpty())
                specification.setReceiveBefore(df.parse(filters.get("receiveBefore")));
        } catch (ParseException e) {
            logger.severe("[Part Controller Error] Unable to parse date: " + e.getMessage());
        }
        if (filters.containsKey("sortingColumn") && !filters.get("sortingColumn").isEmpty()) {
            specification.setSortingColumn(
                    SqlPartTableColumns.getColumnByName(filters.get("sortingColumn")));
            specification.setSortingOrder(
                    SqlSortingOrder.getOrderByName(filters.get("sortingOrder")));
        }

        parts.addAll(partRepository.query(specification));

        return parts;
    }
}
