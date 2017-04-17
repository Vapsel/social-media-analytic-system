package smas.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import smas.core.database.domain.IntelligentNodeData;
import smas.core.database.service.interfaces.GraphService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private GraphService graphService;

    private final String HOME_TEMPLATE = "html/login";
    private final String ABOUT_TEMPLATE = "html/about";
    private final String CONTACT_TEMPLATE = "html/contact";

    private final String MODEL_ATTRIBUTE_SCOPE = "scope";

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login(Model model) {

        model.addAttribute(MODEL_ATTRIBUTE_SCOPE, "user_hometown, user_location, user_about_me, email, public_profile");

        return HOME_TEMPLATE;
    }

    @RequestMapping(value = "contact", method = RequestMethod.GET)
    public String contact(Model model) {
        return CONTACT_TEMPLATE;
    }

    @RequestMapping(value = "about", method = RequestMethod.GET)
    public String about(Model model) {
        return ABOUT_TEMPLATE;
    }

    @RequestMapping(value = "testdb", method = RequestMethod.GET)
    public void testDatabase(HttpServletResponse response,
                             @RequestParam("text") String text) throws IOException {
        IntelligentNodeData node = new IntelligentNodeData();
        node.setNotion(text);
        graphService.save(node);
        response.sendRedirect("/");
    }
}