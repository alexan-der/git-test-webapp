package alexander.zabenkov.business.service;

import alexander.zabenkov.business.entity.Part;
import alexander.zabenkov.business.repository.PartRepository;
import alexander.zabenkov.business.specification.PartSpecification;
import alexander.zabenkov.business.specification.PartSpecificationWithAllFieldsAndSorting;
import alexander.zabenkov.business.specification.PartSpecificationWithSorting;
import alexander.zabenkov.business.utils.SqlPartTableColumns;
import alexander.zabenkov.business.utils.SqlSortingOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test Part Service for fetching Part data from {@link PartRepository}
 */
@RunWith(MockitoJUnitRunner.class)
public class PartServiceTest {

    /**
     * Injects mock of PartRepository in PartService
     */
    @Mock
    private PartRepository partRepository;
    @InjectMocks
    private PartService partService;

    /**
     * Control group of Part objects.
     */
    private Part part1;
    private Part part2;
    private Part part3;

    /**
     * Sets up reactions of PartRepository for Query method.
     */
    @Before
    public void setUp() {
        List<Part> partsOrderedByIdAsc = new ArrayList<>();
        List<Part> partsWithVendorOrderedByNumberAsc = new ArrayList<>();

        part1 = createPart(1L, "Part 1", "1", "Vendor", 1, new Date(), new Date());
        part2 = createPart(2L, "Part 2", "2", "Vendor", 1, new Date(), new Date());
        part3 = createPart(3L, "Part 3", "3", "Render", 1, new Date(), new Date());

        partsOrderedByIdAsc.add(part1);
        partsOrderedByIdAsc.add(part2);
        partsOrderedByIdAsc.add(part3);

        partsWithVendorOrderedByNumberAsc.add(part2);
        partsWithVendorOrderedByNumberAsc.add(part1);

        PartSpecificationWithSorting specOrderedByIdAsc = new PartSpecificationWithSorting(
                SqlPartTableColumns.ID,
                SqlSortingOrder.ASC
        );
        PartSpecificationWithAllFieldsAndSorting specWithVendorOrderedByNumberDesc = new PartSpecificationWithAllFieldsAndSorting();
        specWithVendorOrderedByNumberDesc.setVendor("Vendor");
        specWithVendorOrderedByNumberDesc.setSortingColumn(SqlPartTableColumns.NUMBER);
        specWithVendorOrderedByNumberDesc.setSortingOrder(SqlSortingOrder.DESC);

        when(partRepository.query(specOrderedByIdAsc)).thenReturn(partsOrderedByIdAsc);
        when(partRepository.query(specWithVendorOrderedByNumberDesc)).thenReturn(partsWithVendorOrderedByNumberAsc);
    }

    /**
     * Test of getting all parts
     */
    @Test
    public void getAllParts() {
        List<Part> parts = partService.getAllParts();
        assertEquals(3, parts.size());

        Part actualPart1 = parts.get(0);
        Part actualPart2 = parts.get(1);
        Part actualPart3 = parts.get(2);

        assertEquals(part1, actualPart1);
        assertEquals(part2, actualPart2);
        assertEquals(part3, actualPart3);
    }

    /**
     * Test of getting filtered parts
     */
    @Test
    public void getParts() {
        Map<String, String> filter = new HashMap<>();
        filter.put("vendor", "Vendor");
        filter.put("sortingColumn", SqlPartTableColumns.NUMBER.getName());
        filter.put("sortingOrder", SqlSortingOrder.DESC.toString());

        List<Part> parts = partService.getParts(filter);
        assertEquals(2, parts.size());

        Part actualPart1 = parts.get(0);
        Part actualPart2 = parts.get(1);

        assertEquals(part2, actualPart1);
        assertEquals(part1, actualPart2);
    }

    /**
     * Instantiates Part object by specified values
     * @return Part object
     */
    private Part createPart(Long id, String name, String number, String vendor, Integer qty, Date shipped, Date receive) {
        Part part = new Part();
        part.setId(id);
        part.setName(name);
        part.setNumber(number);
        part.setVendor(vendor);
        part.setQty(qty);
        part.setShipped(shipped);
        part.setReceive(receive);
        return part;
    }
}