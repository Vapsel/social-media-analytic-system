package smas.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import smas.analysis.AnalysisProcessing;
import smas.core.database.domain.NotionNodeData;
import smas.core.database.service.interfaces.GraphService;
import smas.core.database.service.interfaces.OfferDataService;
import smas.solver.MaxSatSolver;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    private final AnalysisProcessing analysisProcessing;
    private final GraphService graphService;
    private final OfferDataService offerDataService;
    private final MaxSatSolver maxSatSolver;

    @Autowired
    public TwitterController(Twitter twitter, ConnectionRepository connectionRepository,
                             AnalysisProcessing analysisProcessing, GraphService graphService, OfferDataService offerDataService, MaxSatSolver maxSatSolver) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
        this.analysisProcessing = analysisProcessing;
        this.graphService = graphService;
        this.offerDataService = offerDataService;
        this.maxSatSolver = maxSatSolver;
    }

    /**
     * /connect/twitter path is handled by ConnectController.
     * It will kick off the OAuth authorization code flow. -->
     * @param model
     * @return
     */
    @RequestMapping(method= RequestMethod.GET)
    public String helloTwitter(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        Set<String> favoriteTexts = getFavoriteTexts();
        Map<NotionNodeData, Long> preferenceToProbability = analysisProcessing.findKeywordsInSentences(favoriteTexts);
        List<Integer> userPreferenceIds = preferenceToProbability.entrySet().stream()
            .map(e -> e.getKey().getId().intValue())
            .collect(Collectors.toList());
        int[] userPrefIds = convertListToArray(userPreferenceIds);


        Map<Long, List<Integer>> offerIdToRelatedNodeIds = offerDataService.findAll().stream()
            .collect(Collectors.toMap(o -> o.getId(),
                o -> o.getRelatedNodes().stream()
                    .map(n -> n.getId().intValue())
                    .collect(Collectors.toList())));

        Map<Long, Integer> offerIdToMaxResult = offerIdToRelatedNodeIds.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey(),
                e -> maxSatSolver.iloscDopasowan(userPrefIds, convertListToArray(e.getValue()))))
            .entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .collect(Collectors.toMap(e -> e.getKey(),
                e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

        model.addAttribute(twitter.userOperations().getUserProfile());
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
        model.addAttribute("friends", friends);
        model.addAttribute("preferences", preferenceToProbability);
        model.addAttribute("offers", offerIdToMaxResult);
        return "html/hello";
    }

    public Set<String> getFavoriteTexts(){
        return twitter.timelineOperations().getFavorites().stream()
            .map(Tweet::getText)
            .collect(Collectors.toSet());
    }

    public int[] convertListToArray(Collection<Integer> listResult) {
        int[] result = new int[listResult.size()];
        int i= 0;
        for (int num : listResult) {
            result[i++] = num;
        }
        return result;
    }

}
