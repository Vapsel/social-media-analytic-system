package smas.core.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smas.core.database.domain.IntelligentNodeData;
import smas.core.database.repository.GraphRepository;
import smas.core.database.service.interfaces.GraphService;

@Service
@Transactional
public class GraphServiceImpl implements GraphService{

    @Autowired
    private GraphRepository graphRepository;

    @Override
    public void save(IntelligentNodeData node) {
        graphRepository.save(node);
    }
}
