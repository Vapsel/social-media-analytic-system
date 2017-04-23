package smas.analysis;

import facebook4j.*;
import facebook4j.auth.AccessToken;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import smas.core.database.service.interfaces.GraphService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//@NoArgsConstructor
public class AnalysisProcessing {

    private GraphService graphService;

//    private static final Facebook facebook = new FacebookFactory().getInstance();

//    // todo
//    private static String APP_ID = "744869465672302";
//    private static String APP_SECRET = "fa2d57c30c40876ae99fc78f463e7084";
//    private static String PERMISSIONS = "user_likes, user_photos, user_about_me, user_posts, public_profile";
//
//    static {
//        facebook.setOAuthAppId(APP_ID, APP_SECRET);
//        facebook.setOAuthPermissions(PERMISSIONS);
//    }


    public AnalysisProcessing(GraphService graphService){
//        try {
//            if (!userAccessToken.equals(facebook.getOAuthAccessToken().getToken())) {
//                facebook.setOAuthAccessToken(new AccessToken(userAccessToken, null));
//            }
//        } catch (IllegalStateException e){
//            facebook.setOAuthAccessToken(new AccessToken(userAccessToken, null));
//        }

        this.graphService = graphService;
    }

//    public Map<String, Long> processLikedPagesAbout(){
//
//        ResponseList<Like> likedPages = null;
//        try {
//            likedPages = facebook.getUserLikes();
//
//            Reading reading = new Reading();
//            reading.fields("about, name");
//
//            Map<String, Long> preferences = likedPages.stream()
//                    .map(page -> {
//                        try {
//                            return facebook.getPage(page.getId(), reading).getAbout();
//                        } catch (FacebookException e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    })
//                    .filter(Objects::nonNull)
//                    .map(about -> about.split("[\\s,./]"))
//                    .flatMap(Arrays::stream)
//                    .filter(word -> (word.length() > 1) && (graphService.findNodeByNotion(word) != null))
//                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
////            .collect(Vollectors.toList())
//
//            Map<String, Long> sortedPreferences = new LinkedHashMap<>();
//            preferences.entrySet().stream()
//                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
//                    .forEachOrdered(x -> sortedPreferences.put(x.getKey(), x.getValue()));
//
//            return sortedPreferences;
//        } catch (FacebookException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public List<String> processJson(String json){

        Map<String, Long> preferences = Arrays.stream(json.split("[\\s,./\":\\n]"))
            .filter(word -> (word.length() > 1) && (graphService.findNodeByNotion(word) != null))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Long> sortedPreferences = new LinkedHashMap<>();
            preferences.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEachOrdered(x -> sortedPreferences.put(x.getKey(), x.getValue()));

        return preferences.entrySet().stream()
                .map(Map.Entry::getKey)
                .sorted((e1, e2) -> e2.compareTo(e1))
                .collect(Collectors.toList());

    }
}
