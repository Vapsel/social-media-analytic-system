package smas.web;

import com.google.gson.*;
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
import smas.analysis.AnalysisProcessing;
import smas.analysis.SATProcessing;
import smas.core.database.domain.CategoryData;
import smas.core.database.domain.IntelligentNodeData;
import smas.core.database.service.interfaces.GraphService;
import smas.dto.PreferencesResponseDTO;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AjaxController {

    @Autowired
    GraphService service;

    @Autowired
    AnalysisProcessing analysisProcessing;

    @Autowired
    SATProcessing satProcessing;

    @RequestMapping(value = "/userJson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> receiveResponse(@RequestBody String json) {

        Set<String> descriptions = retrieveDescriptionsFromJson(json);

        List<String> sortedPreferences = analysisProcessing.processSentences(descriptions).entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        String answer = null;
        try {
            answer = satProcessing.getSATAnswer(descriptions);
        } catch (IOException e) {
            // todo log and return error
            e.printStackTrace();
        }

        PreferencesResponseDTO responseDTO = new PreferencesResponseDTO(answer, sortedPreferences);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
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

    @RequestMapping(value = "/editor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editor(@RequestBody String jsonSummary) {
        performSummary(jsonSummary);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void performSummary(String json) {
        String key = getKeyFromJson(json);
        Set<Long> relationsIds = getRelationsIds(json);
        Set<Long> existingCategoriesIds = getExistingCategoriesIds(json);
        Set<String> newCategoriesNames = getNewCategoriesNames(json);


        if (!"".equals(key)) {
            IntelligentNodeData node = new IntelligentNodeData();
            node.setName(key);
            node.setCategoryIds(existingCategoriesIds);
            node.setRelatedNodesIds(relationsIds);

            if (newCategoriesNames.size() == 0) {
                service.save(node);
            } else {
                service.save(node, newCategoriesNames);
            }
        }else{
            for(String categoryName : newCategoriesNames){
                CategoryData category = new CategoryData();
                category.setName(categoryName);
                service.save(category);
                //maybe better to create save method which get a Set of categories as a parametr
            }
        }
    }

    private String getKeyFromJson(String json) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);
        JsonObject rootObject = jsonElement.getAsJsonObject();

        String key = rootObject.get("key").getAsString();

        return key;
    }

    private Set<Long> getRelationsIds(String json) {
        Set<Long> set = new HashSet<>();

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);
        JsonObject rootObject = jsonElement.getAsJsonObject();

        JsonArray relations = rootObject.get("relations").getAsJsonArray();

        if (relations != null) {
            for (int i = 0; i < relations.size(); ++i) {
                JsonObject relation = relations.get(i).getAsJsonObject();
                Long id = relation.get("value").getAsLong();
                set.add(id);
            }
        }

        return set;
    }

    private Set<Long> getExistingCategoriesIds(String json) {
        Set<Long> set = new HashSet<>();

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);
        JsonObject rootObject = jsonElement.getAsJsonObject();

        JsonArray categories = rootObject.get("existingCategories").getAsJsonArray();

        if (categories != null) {
            for (int i = 0; i < categories.size(); ++i) {
                JsonObject relation = categories.get(i).getAsJsonObject();
                Long id = relation.get("value").getAsLong();
                set.add(id);
            }
        }

        return set;
    }

    private Set<String> getNewCategoriesNames(String json) {
        Set<String> set = new HashSet<>();

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);
        JsonObject rootObject = jsonElement.getAsJsonObject();

        JsonArray categories = rootObject.get("newCategories").getAsJsonArray();

        if (categories != null) {
            for (int i = 0; i < categories.size(); ++i) {
                JsonObject relation = categories.get(i).getAsJsonObject();
                String id = relation.get("label").getAsString();
                set.add(id);
            }
        }

        return set;
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

    /**
     * Parse string as JSON object and retrieve <code>about</code> section from each <code>like</code>
     * @param json JSON object as string
     * @return All <code>abouts</code> that were found
     */
    private Set<String> retrieveDescriptionsFromJson(String json){

        Set<String> descriptions = new HashSet<>();
        Gson gson = new Gson();
        gson.fromJson(json, JsonElement.class).getAsJsonObject().get("likes")
                .getAsJsonObject().get("data").getAsJsonArray()
                .forEach(likeObj -> {
                    JsonElement about = likeObj.getAsJsonObject().get("about");
                    if (about != null){
                        descriptions.add(about.getAsString());
                    }
                });
        return descriptions;
    }

}
