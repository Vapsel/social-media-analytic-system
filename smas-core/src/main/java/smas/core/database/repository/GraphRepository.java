package smas.core.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smas.core.database.domain.IntelligentNodeData;

@Repository
public interface GraphRepository extends JpaRepository<IntelligentNodeData, Long>{

}
