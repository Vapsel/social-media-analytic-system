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
     * @param node Node to persist
     */
    void save(IntelligentNodeData node);

    /**
     * Persists category in database.
     * <p>
     *     If category already has id, it will be updated.
     *     If category doesn't have id, new entity will be created.
     * </p>
     * @param category Category to persist
     */
    void save(CategoryData category);

    /**
     * Persists node with new categories.
     * New categories will be persist in database and added to to <code>node</code>.
     * @param node Node to persist
     * @param newCategories New categories
     * @see GraphService#save(IntelligentNodeData)
     * @see GraphService#save(CategoryData)
     */
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

    /**
     * Search for node where <code>searchText</code> is like persisted node's notion.
     * @param searchText Query text
     * @return Node that has similar <code>notion</code> as <code>searchText</code>
     */
    IntelligentNodeData findNodeByNotion(String searchText);
}
