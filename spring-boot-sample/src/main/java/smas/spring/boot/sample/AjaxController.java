package smas.spring.boot.sample;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.types.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AjaxController {
    @RequestMapping(value="/login")
    public void receiveResponse(@RequestBody String json, HttpServletRequest request){
        User user = parseUserData(json);

        printUserInfo(user);
    }

    private User parseUserData(String jsonString) {
        FacebookClient fbclient = new DefaultFacebookClient();
        JsonMapper mapper = fbclient.getJsonMapper();
        User user = mapper.toJavaObject(jsonString, User.class);

        return user;
    }

    private void printUserInfo(User user){
        String info =
                "Name: " + user.getName() + "\n" +
                "Hometown: " + user.getHometown().getName() + "\n" +
                "Location: " + user.getLocation().getName() + "\n" +
                "Work: " + user.getWork() + "\n" +
                "Likes: " + user.getLikes() + "\n";

        System.out.print(info);
    }

}
