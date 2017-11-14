package smas.core.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smas.core.database.domain.OfferData;

public interface OfferRepository extends JpaRepository<OfferData, Long> {

}
