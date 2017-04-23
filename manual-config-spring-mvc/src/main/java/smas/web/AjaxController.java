package smas.web;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.types.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import smas.analysis.AnalysisProcessing;
import smas.core.database.service.interfaces.GraphService;

import java.util.List;

@RestController
public class AjaxController {

    @Autowired
    private GraphService graphService;

    @RequestMapping(value = "/userJson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> receiveResponse(@RequestBody String json) {
        AnalysisProcessing analysis = new AnalysisProcessing(graphService);
        List<String> sortedPreferences = analysis.processJson(json);

//        User user = parseUserData(json);
//
//        printUserInfo(user);


        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private User parseUserData(String jsonString) {
        FacebookClient fbclient = new DefaultFacebookClient();
        JsonMapper mapper = fbclient.getJsonMapper();
        User user = mapper.toJavaObject(jsonString, User.class);
        return user;
    }

    private void printUserInfo(User user){
        if (user == null) {
            System.err.println("AjaxController: No user.");
            return;
        }

        StringBuilder info = new StringBuilder();
        String splitter = ":\t";

        info.append("Name").append(splitter).append(user.getName()).append("\n");
        if (user.getHometown() != null) {
            info.append("HomeTown").append(splitter).append(user.getHometown().getName()).append("\n");
        }
        if (user.getLocation() != null) {
            info.append("Location").append(splitter).append(user.getLocation().getName()).append("\n");
        }
        info.append("Work").append(splitter).append(user.getWork()).append("\n");
        info.append("Likes").append(splitter).append(user.getLikes()).append("\n");

        System.out.print(info.toString());
    }

}