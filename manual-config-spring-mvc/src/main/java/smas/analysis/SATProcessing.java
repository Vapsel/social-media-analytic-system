package smas.analysis;

import smas.solver.SATSolverManager;
import smas.solver.aggregators.VariableAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smas.core.database.domain.CategoryData;
import smas.core.database.service.interfaces.GraphService;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SATProcessing {

    @Autowired
    GraphService graphService;

    @Autowired
    AnalysisProcessing analysisProcessing;

    @Autowired
    SATSolverManager satSolverManager;

    /**
     * Produces answer for satisfiable problem as inline string of positive and negative preferences
     * @param sentences Texts that user likes
     * @return Answer
     * @throws IOException *
     */
    public String getSATAnswer(Collection<String> sentences) throws IOException {

        return satSolverManager.resolveSatisfiableProblem(getCategoriesAggregator(sentences));
    }

    // TODO optimize algorithm of category mapping
    /**
     * Creates instance of interface to pass to SAT solver module
     * @param sentences Texts that user likes
     * @return New instance
     */
    private VariableAggregator getCategoriesAggregator(Collection<String> sentences){

        Map<Long, Set<String>> longSetMap = graphService.findAllCategories().stream()
                .collect(Collectors.toMap(CategoryData::getId, c -> new HashSet<String>()));

        analysisProcessing.findKeywordsInSentences(sentences).entrySet()
                .forEach(entry -> entry.getKey().getCategoryIds()
                        .forEach(catId -> longSetMap.get(catId)
                                .add(entry.getKey().getName())));

        final Collection<Collection<String>> values = longSetMap.values().stream()
                .filter(set -> !set.isEmpty())
                .map(HashSet::new)
                .collect(Collectors.toSet());

        return () -> values;
    }
}
