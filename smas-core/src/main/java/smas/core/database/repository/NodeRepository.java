package smas.core.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smas.core.database.domain.CategoryData;
import smas.core.database.domain.IntelligentNodeData;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<IntelligentNodeData, Long>{

    @Query("SELECT c FROM CategoryData c")
    List<CategoryData> findAllCategories();

    /**
     * Find all nodes that have <code>notion</code> started with <code>searchText</code>.
     * @param searchText Started text that will be search
     * @return List of nodes that match restriction
     */
    @Query("SELECT n FROM IntelligentNodeData n WHERE lower(n.notion) like lower(concat(?1, '%'))")
    List<IntelligentNodeData> findNodesStartNotion(String searchText);

    /**
     * Find all nodes that have <code>notion</code> contained <code>searchText</code>.
     * @param searchText Text that will be search
     * @return List of nodes that match restriction
     */
    @Query("SELECT n FROM IntelligentNodeData n WHERE lower(n.notion) like lower(concat('%', ?1, '%'))")
    List<IntelligentNodeData> findNodesContainNotion(String searchText);

}