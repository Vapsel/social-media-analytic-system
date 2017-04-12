package snas.web;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.types.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
public class AjaxController {

    @RequestMapping(value = "/userJson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> receiveResponse(@RequestBody String json, HttpServletRequest request) {
        User user = parseUserData(json);

        //printUserInfo(user);
        getUserLocations(user);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private User parseUserData(String jsonString) {
        FacebookClient fbclient = new DefaultFacebookClient();
        JsonMapper mapper = fbclient.getJsonMapper();
        User user = mapper.toJavaObject(jsonString, User.class);
        return user;
    }

    private ArrayList<String> getUserLocations(User user){
        ArrayList<String> locations = new ArrayList<>();

        locations.add(user.getLocation().getName());
        locations.add(user.getHometown().getName());
        user.getWork().forEach(w -> locations.add(w.getLocation().getName()));

        return locations;
    }

    private void printUserInfo(User user){
        try {
            String info =
                    "Name: " + user.getName() + "\n" +
                            "Hometown: " + user.getHometown().getName() + "\n" +
                            "Location: " + user.getLocation().getName() + "\n" +
                            "Work: " + user.getWork() + "\n" +
                            "Likes: " + user.getLikes() + "\n";

            System.out.print(info);
        }catch (NullPointerException e){
            //of course, it is a very bad way, but user produces too much null's
        }
    }

}
