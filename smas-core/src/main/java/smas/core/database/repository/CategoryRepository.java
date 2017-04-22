package smas.core.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smas.core.database.domain.CategoryData;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryData, Long> {



}
