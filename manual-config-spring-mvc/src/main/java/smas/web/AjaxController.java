package smas.web;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.types.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AjaxController {

    @RequestMapping(value = "/userJson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> receiveResponse(@RequestBody String json) {
        User user = parseUserData(json);

        printUserInfo(user);

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

    @RequestMapping(value = "/getCategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> categories(){
        @Getter
        @Setter
        @AllArgsConstructor
        class A{
            private String name;
            private String symbol;
            private Long number;
        }

        ArrayList<A> list = new ArrayList<>();
        list.add(new A("Category1", "Category1", (long) 1));
        list.add(new A("Category2", "Category2", (long) 2));
        list.add(new A("Category3", "Category3", (long) 3));
        list.add(new A("Category4", "Category4", (long) 4));
        list.add(new A("Category5", "Category5", (long) 5));
        list.add(new A("Category6", "Category6", (long) 6));

        return new ResponseEntity<>(list.toArray(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getRelations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> relations(){
        @Getter
        @Setter
        @AllArgsConstructor
        class A{
            private String name;
            private String symbol;
            private Long number;
        }

        ArrayList<A> list = new ArrayList<>();
        list.add(new A("Relation1", "Relation1", (long) 1));
        list.add(new A("Relation2", "Relation2", (long) 2));
        list.add(new A("Relation3", "Relation3", (long) 3));
        list.add(new A("Relation4", "Relation4", (long) 4));
        list.add(new A("Relation5", "Relation5", (long) 5));
        list.add(new A("Relation6", "Relation6", (long) 6));

        return new ResponseEntity<Object>(list.toArray(), HttpStatus.OK);
    }

}