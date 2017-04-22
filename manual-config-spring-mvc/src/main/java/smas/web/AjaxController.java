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
import smas.core.database.domain.CategoryData;
import smas.core.database.domain.IntelligentNodeData;
import smas.core.database.service.interfaces.GraphService;

import java.util.List;

@RestController
public class AjaxController {

    @Autowired
    GraphService service;

    @RequestMapping(value = "/userJson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> receiveResponse(@RequestBody String json) {
        User user = parseUserData(json);

        printUserInfo(user);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/getCategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> categories(){
        List<CategoryData> list = service.findAllCategories();
        return new ResponseEntity<>(list.toArray(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getRelations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> relations(@RequestBody String searchText){
        List<IntelligentNodeData> list = service.findNodesWithNotion(searchText);
        return new ResponseEntity<>(list.toArray(), HttpStatus.OK);
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