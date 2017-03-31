package smas.spring.boot.sample;


import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}
