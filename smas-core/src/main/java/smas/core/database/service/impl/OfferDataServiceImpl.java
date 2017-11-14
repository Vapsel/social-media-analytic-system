package smas.core.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smas.core.database.domain.OfferData;
import smas.core.database.repository.OfferRepository;
import smas.core.database.service.interfaces.OfferDataService;

@Service
@Transactional
public class OfferDataServiceImpl implements OfferDataService {

    private final OfferRepository offerRepository;

    @Autowired
    public OfferDataServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public OfferData save(OfferData offerData) {
        return offerRepository.save(offerData);
    }

    @Override
    public OfferData findOfferById(Long offerId) {
        return offerRepository.findOfferDataById(offerId);
    }
}
