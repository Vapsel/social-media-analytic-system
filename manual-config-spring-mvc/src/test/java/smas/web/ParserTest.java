package smas.web;

import com.restfb.types.User;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    @Test
    public void testJsonParser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AjaxController controller = new AjaxController();

        Method parseUserDataMethod = controller.getClass().getDeclaredMethod("parseUserData", String.class);
        parseUserDataMethod.setAccessible(true);

        String testJson = "{\"name\":\"Zlatan Popovic\",\"id\":\"7777777777777777\"}";

        User user = (User) parseUserDataMethod.invoke(controller, testJson);

        assertEquals("Zlatan Popovic", user.getName());
        assertEquals("7777777777777777", user.getId());
    }
}