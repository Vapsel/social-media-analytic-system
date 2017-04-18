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

    @RequestMapping(value = "/getCategories", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
     public String categories(){
        return "[\n" +
                "      {name: 'Hydrogen', symbol: 'H', number: 1},\n" +
                "      {name: 'Helium', symbol: 'He', number: 2},\n" +
                "      {name: 'Lithium', symbol: 'Li', number: 3},\n" +
                "      {name: 'Beryllium', symbol: 'Be', number: 4},\n" +
                "      {name: 'Boron', symbol: 'B', number: 5},\n" +
                "      {name: 'Carbon', symbol: 'C', number: 6},\n" +
                "      {name: 'Nitrogen', symbol: 'N', number: 7},\n" +
                "      {name: 'Oxygen', symbol: 'O', number: 8},\n" +
                "      {name: 'Fluorine', symbol: 'F', number: 9},\n" +
                "      {name: 'Neon', symbol: 'Ne', number: 10},\n" +
                "      {name: 'Sodium', symbol: 'Na', number: 11}\n" +
                "]";
    }

/*    @RequestMapping(value = "/getRelations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
     public String relations(){
        return "[\n" +
                "      {name: 'Hydrogen', symbol: 'H', number: 1},\n" +
                "      {name: 'Helium', symbol: 'He', number: 2},\n" +
                "      {name: 'Lithium', symbol: 'Li', number: 3},\n" +
                "      {name: 'Beryllium', symbol: 'Be', number: 4},\n" +
                "      {name: 'Boron', symbol: 'B', number: 5},\n" +
                "      {name: 'Carbon', symbol: 'C', number: 6},\n" +
                "      {name: 'Nitrogen', symbol: 'N', number: 7},\n" +
                "      {name: 'Oxygen', symbol: 'O', number: 8},\n" +
                "      {name: 'Fluorine', symbol: 'F', number: 9},\n" +
                "      {name: 'Neon', symbol: 'Ne', number: 10},\n" +
                "      {name: 'Sodium', symbol: 'Na', number: 11}\n" +
                "]";
    }*/

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
        return new ResponseEntity<>(new A("Hydrogen", "H", (long) 1), HttpStatus.OK);

        /*return "[\n" +
                "      {name: 'Hydrogen', symbol: 'H', number: 1},\n" +
                "      {name: 'Helium', symbol: 'He', number: 2},\n" +
                "      {name: 'Lithium', symbol: 'Li', number: 3},\n" +
                "      {name: 'Beryllium', symbol: 'Be', number: 4},\n" +
                "      {name: 'Boron', symbol: 'B', number: 5},\n" +
                "      {name: 'Carbon', symbol: 'C', number: 6},\n" +
                "      {name: 'Nitrogen', symbol: 'N', number: 7},\n" +
                "      {name: 'Oxygen', symbol: 'O', number: 8},\n" +
                "      {name: 'Fluorine', symbol: 'F', number: 9},\n" +
                "      {name: 'Neon', symbol: 'Ne', number: 10},\n" +
                "      {name: 'Sodium', symbol: 'Na', number: 11}\n" +
                "]";*/
    }

}