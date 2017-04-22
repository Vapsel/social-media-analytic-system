package smas.core.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smas.core.database.domain.CategoryData;
import smas.core.database.domain.IntelligentNodeData;
import smas.core.database.repository.CategoryRepository;
import smas.core.database.repository.NodeRepository;
import smas.core.database.service.interfaces.GraphService;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class GraphServiceImpl implements GraphService{

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(IntelligentNodeData node) {
        nodeRepository.save(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(CategoryData category) {
        categoryRepository.save(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(IntelligentNodeData node, Collection<String> newCategories) {
        Set<Long> categoryIds = newCategories.stream()
                .map(s -> categoryRepository.saveAndFlush(new CategoryData(s)).getId())
                .collect(Collectors.toSet());
        node.setCategoryIds(categoryIds);
        save(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryData> findAllCategories() {
        return nodeRepository.findAllCategories();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IntelligentNodeData> findNodesWithNotion(String searchText) {
        List<IntelligentNodeData> result = nodeRepository.findNodesStartNotion(searchText);
        if (result.isEmpty()){
            return nodeRepository.findNodesContainNotion(searchText);
        }
        return result;
    }
}
