package web;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;
import org.junit.Before;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class MainPageTest {
    @Before
    public void prepare() {
        setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);
        setBaseUrl("http://localhost:8080/");
    }
    @Test
    public void testBeforeLogin() {
        beginAt("/");
        assertTitleEquals("Sax-L - Quiz Website | Home");

        assertLinkPresent("home-ref");

        //these links are present after login
        assertLinkNotPresent("profile-ref");
        assertLinkNotPresent("users-list-ref");
        assertLinkNotPresent("quiz-ref");
    }
}
