package smas.core.database.service.interfaces;

import smas.core.database.domain.CategoryData;
import smas.core.database.domain.IntelligentNodeData;

import java.util.Collection;
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

    // save only category
    void save(CategoryData category);

    // save node with new categories
    @Deprecated
    // TODO
    void save(IntelligentNodeData node, Collection<String> newCategories);

    /**
     * Find all available categories (used and unused).
     * @return List of categories
     */
    List<CategoryData> findAllCategories();

    /**
     * Search nodes that have notion like <code>searchText</code>.
     * First, try to find node's notion that start with <code>searchText</code>.
     * If there isn't any nodes from first step then find node's notion that contains <code>searchText</code>.
     * @param searchText Query text
     * @return Nodes that satisfy query from first or second step.
     */
    List<IntelligentNodeData> findNodesWithNotion(String searchText);


}
