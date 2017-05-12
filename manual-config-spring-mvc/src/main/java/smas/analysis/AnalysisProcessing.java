package smas.analysis;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smas.core.database.service.interfaces.GraphService;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class AnalysisProcessing {

    @Autowired
    private GraphService graphService;

    /**
     * Finds preferences bt parsing <code>sentences</code>.
     * @param sentences Collection of available sentences. Will be split to words.
     * @return Map of preferences sorted by value in descending order.
     */
    public Map<String, Long> processSentences(Collection<String> sentences){

        Map<String, Long> preferences = sentences.stream()
                .map(sentence -> sentence.split(" "))
                .flatMap(Arrays::stream)
                .filter(word -> (word.length() > 1) && (graphService.findNodeByNotion(word) != null))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return preferences.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

}
