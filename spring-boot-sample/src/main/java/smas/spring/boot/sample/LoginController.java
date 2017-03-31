package smas.spring.boot.sample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private final String HTML_TEMPLATE = "html/login";

    private final String MODEL_ATTRIBUTE_HELLO = "hello";
    private final String MODEL_ATTRIBUTE_SCOPE = "scope";

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String display(Model model) {

        model.addAttribute(MODEL_ATTRIBUTE_HELLO, "This is spring boot sample");
        model.addAttribute(MODEL_ATTRIBUTE_SCOPE, "user_hometown, user_location, user_about_me, email, public_profile");

        return HTML_TEMPLATE;
    }

}
