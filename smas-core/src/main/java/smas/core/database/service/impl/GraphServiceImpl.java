package smas.core.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smas.core.database.domain.CategoryData;
import smas.core.database.domain.NotionNodeData;
import smas.core.database.repository.CategoryRepository;
import smas.core.database.repository.NodeRepository;
import smas.core.database.service.interfaces.GraphService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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
    public void save(NotionNodeData node) {
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
    public void save(NotionNodeData node, Collection<String> newCategories) {
        Set<Long> categoryIds = newCategories.stream()
                .map(s -> categoryRepository.save(new CategoryData(s)).getId())
                .collect(Collectors.toSet());
        // TODO test
        categoryRepository.flush();
//        node.setCategoryIds(categoryIds);
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
    public List<NotionNodeData> findNodesWithNotion(String searchText) {
        List<NotionNodeData> result = nodeRepository.findNodesStartNotion(searchText);
        if (result.isEmpty()){
            return nodeRepository.findNodesContainNotion(searchText);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotionNodeData findNodeByNotion(String searchText) {
        Page<NotionNodeData> result = nodeRepository.findNodeByNotion(searchText, new PageRequest(0, 1));
        List<NotionNodeData> content = result.getContent();
        if (content.size() > 0){
            return content.get(0);
        }
        return null;
    }

    @Override
    public Map<CategoryData, List<NotionNodeData>> retrieveCategoryToNodesMap() {
        return nodeRepository.findAll().stream()
            .collect(groupingBy(NotionNodeData::getCategory));
    }

    public List<NotionNodeData> findNodesByIds(Collection<Long> notionsIds){
        return nodeRepository.findNotionNodeDataByIdIn(notionsIds);
    }
}
