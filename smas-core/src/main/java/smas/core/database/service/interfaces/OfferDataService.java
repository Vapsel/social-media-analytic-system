package smas.core.database.service.interfaces;

import smas.core.database.domain.OfferData;

public interface OfferDataService {

    /**
     * Persists offer in database.
     * <p>
     *     If offer already has id, it will be updated.
     *     If offer doesn't have id, new entity will be created.
     * </p>
     * @param offerData Offer to persist
     */
    OfferData save(OfferData offerData);
}
