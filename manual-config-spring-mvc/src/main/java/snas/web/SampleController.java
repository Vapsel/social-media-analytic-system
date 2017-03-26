package snas.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class SampleController {

    private final String HTML_TEMPLATE = "pages/hi";

    private final String MODEL_ATTRIBUTE_HELLO = "hello";

    @RequestMapping(value="/test", method= RequestMethod.GET)
    public String display(Model model) {

        model.addAttribute(MODEL_ATTRIBUTE_HELLO, "This is spring boot sample");

        return HTML_TEMPLATE;
    }
}
