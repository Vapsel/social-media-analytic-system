package smas.web;

import org.junit.Test;
import org.springframework.http.MediaType;import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ControllersTest {

    @Test
    public void testRootPage() throws Exception {
        HomeController controller = new HomeController();
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/")).andExpect(view().name("html/login"));
    }

    @Test
    public void testLoginPage() throws Exception {
        HomeController controller = new HomeController();
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/login")).andExpect(view().name("html/login"));
    }

    @Test
    public void testContactPage() throws Exception {
        HomeController controller = new HomeController();
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/contact")).andExpect(view().name("html/contact"));
    }

    @Test
    public void testAbout() throws Exception {
        HomeController controller = new HomeController();
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/about")).andExpect(view().name("html/about"));
    }

    @Test
    public void testAjaxController() throws Exception {
        AjaxController controller = new AjaxController();
        MockMvc mockMvc = standaloneSetup(controller).build();

        String testJson = "{\"name\":\"First Last\",\"id\":\"7777777777777777\"}";

        mockMvc.perform(post("/userJson")
                .accept(MediaType.APPLICATION_JSON)
                .content(testJson))
                .andExpect(status().isAccepted());
    }
}