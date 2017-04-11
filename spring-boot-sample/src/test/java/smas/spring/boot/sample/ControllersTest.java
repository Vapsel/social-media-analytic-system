package smas.spring.boot.sample;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ControllersTest {
    @Test
    public void testLoginPage() throws Exception{
        LoginController controller = new LoginController();
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/login")).andExpect(view().name("html/login"));
    }

    @Test
    public void testOffersPage() throws Exception {
        OffersController controller = new OffersController();
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/offers")).andExpect(view().name("html/offers"));
    }

    @Test
    public void testLoginModels() throws Exception {
        LoginController controller = new LoginController();
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/login"))
                .andExpect(model().attributeExists("scope"))
                .andExpect(model().attribute("scope", "user_hometown, user_location, user_about_me, email, public_profile"));


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
