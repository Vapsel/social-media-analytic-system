package smas.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smas.core.database.domain.IntelligentNodeData;
import smas.core.database.service.interfaces.GraphService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AnalysisProcessing {

    @Autowired
    private GraphService graphService;

    /**
     * Finds preferences by parsing <code>sentences</code>.
     * @param sentences Collection of available sentences. Will be split to words.
     * @return Map of preferences sorted by value in descending order.
     */
    public Map<String, Long> processSentences(Collection<String> sentences){

        return findKeywordsInSentences(sentences).entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getName(),
                        Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    /**
     * Finds keywords by parsing <code>sentences</code>.
     * @param sentences Collection of available sentences. Will be split to words.
     * @return Map of keywords sorted by value in descending order.
     * @see #findKeywordsInSentences(Collection)
     */
    public Map<IntelligentNodeData, Long> findKeywordsInSentences(Collection<String> sentences){

        Map<IntelligentNodeData, Long> preferences = sentences.stream()
                .map(sentence -> sentence.split(" "))// todo split by comma, dots etc.
                .flatMap(Arrays::stream)
                .filter(word -> (word.length() > 1))
                .map(graphService::findNodeByNotion)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return preferences.entrySet().stream()
                .sorted(Map.Entry.<IntelligentNodeData, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));
    }
}
