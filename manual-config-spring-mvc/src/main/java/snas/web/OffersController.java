package snas.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OffersController {
    private final String HTML_TEMPLATE = "html/offers";

    @RequestMapping(value="/offers", method= RequestMethod.GET)
    public String display(Model model) {

        return HTML_TEMPLATE;
    }
}
