package smas.core.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smas.core.database.domain.CategoryData;
import smas.core.database.domain.IntelligentNodeData;
import smas.core.database.repository.GraphRepository;
import smas.core.database.service.interfaces.GraphService;

import java.util.List;

@Service
@Transactional
public class GraphServiceImpl implements GraphService{

    @Autowired
    private GraphRepository graphRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(IntelligentNodeData node) {
        graphRepository.save(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryData> findAllCategories() {
        return graphRepository.findAllCategories();
    }
}
