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

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    private final AnalysisProcessing analysisProcessing;

    @Autowired
    public TwitterController(Twitter twitter, ConnectionRepository connectionRepository,
                             AnalysisProcessing analysisProcessing) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
        this.analysisProcessing = analysisProcessing;
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
        Map<String, Long> preferenceToProbability = analysisProcessing.processSentences(favoriteTexts);
        model.addAttribute("preferences", preferenceToProbability);

        model.addAttribute(twitter.userOperations().getUserProfile());
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
        model.addAttribute("friends", friends);
        return "html/hello";
    }

    public Set<String> getFavoriteTexts(){
        return twitter.timelineOperations().getFavorites().stream()
            .map(Tweet::getText)
            .collect(Collectors.toSet());
    }

}
