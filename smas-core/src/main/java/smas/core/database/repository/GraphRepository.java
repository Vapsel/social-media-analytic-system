package smas.core.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smas.core.database.domain.CategoryData;
import smas.core.database.domain.IntelligentNodeData;

import java.util.List;

@Repository
public interface GraphRepository extends JpaRepository<IntelligentNodeData, Long>{

    @Query("Select c FROM CategoryData c")
    List<CategoryData> findAllCategories();

}
