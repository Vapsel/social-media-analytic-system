package smas.core.database.service.interfaces;

import smas.core.database.domain.CategoryData;
import smas.core.database.domain.IntelligentNodeData;

import java.util.List;

public interface GraphService {

    /**
     * Persists node in database.
     * <p>
     *     If node already has id, it will be updated.
     *     If node doesn't have id, new entity will be created.
     * </p>
     * @param node Node to persists
     */
    void save(IntelligentNodeData node);

    /**
     * Find all available categories (used and unused).
     * @return List of categories
     */
    List<CategoryData> findAllCategories();
}
