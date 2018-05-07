package alexander.zabenkov.business.repository;

import alexander.zabenkov.business.entity.Part;
import alexander.zabenkov.business.specification.PartSpecification;

import java.util.List;

/**
 * The interface of the data access object for the database entity.
 */
public interface PartRepository {
    /** Creates a new record in database for the corresponding object */
    void add(Part part);

    /** Deletes the passed object in database */
    void remove(Part part);

    /** Updates the state of the passed object in database */
    void update(Part part);

    /** Returns a collection of objects that responds the specification */
    List query(PartSpecification specification);
}
