package smas.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    @Autowired
    public TwitterController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
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
        twitter.timelineOperations().getFavorites().get(0).getText();
        model.addAttribute(twitter.userOperations().getUserProfile());
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
        model.addAttribute("friends", friends);
        return "html/hello";
    }

}
