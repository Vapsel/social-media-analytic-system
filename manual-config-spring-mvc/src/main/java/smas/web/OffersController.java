package smas.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import smas.core.database.domain.CategoryData;
import smas.core.database.domain.NotionNodeData;
import smas.core.database.domain.OfferData;
import smas.core.database.service.interfaces.GraphService;
import smas.core.database.service.interfaces.OfferDataService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/offers")
public class OffersController {

    private final String ADD_TEMPLATE = "html/offers/add";
    private final String SHOW_TEMPLATE = "html/offers/show";

    private final GraphService graphService;
    private final OfferDataService offerDataService;

    @Autowired
    public OffersController(GraphService graphService, OfferDataService offerDataService) {
        this.graphService = graphService;
        this.offerDataService = offerDataService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String loadAdd(Model model) {
        Map<CategoryData, List<NotionNodeData>> categoryToNotions = graphService.retrieveCategoryToNodesMap();
        model.addAttribute("categoryToNotions", categoryToNotions);
        return ADD_TEMPLATE;
    }

    @RequestMapping(value = "/add/{offerId}", method = RequestMethod.POST)
    public void actionAdd(String offerName, Long[] notions, HttpServletResponse response, @PathVariable Long offerId)
        throws IOException {
        List<NotionNodeData> relatedNodes = graphService.findNodesByIds(Arrays.asList(notions));
        OfferData offer;
        if (offerId == null) {
            offer = new OfferData(null, offerName, new HashSet<>(relatedNodes));
        } else  {
            offer = offerDataService.findOfferById(offerId);
            offer.setRelatedNodes(new HashSet<>(relatedNodes));
        }
        offerDataService.save(offer);
        response.sendRedirect("/offers/add");
    }

    @RequestMapping(value = "/show/{offerId}", method = RequestMethod.GET)
    public String loadShow(Model model, @PathVariable Long offerId){
        OfferData offer = offerDataService.findOfferById(offerId);
        List<Long> checkedNotionIds = offer.getRelatedNodes().stream()
            .map(NotionNodeData::getId)
            .collect(Collectors.toList());
        Map<CategoryData, List<NotionNodeData>> categoryToNotions = graphService.retrieveCategoryToNodesMap();

        model.addAttribute("categoryToNotions", categoryToNotions);
        model.addAttribute("checkedNotionIds", checkedNotionIds);
        model.addAttribute("offer", offer);
        model.addAttribute("id", offerId);
        return SHOW_TEMPLATE;
    }
}
