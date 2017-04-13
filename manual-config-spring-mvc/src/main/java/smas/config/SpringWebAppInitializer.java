package smas.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Configuration of {@link org.springframework.web.servlet.DispatcherServlet}
 * This class replace web.xml
 */
public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Maps DispatcherServlet to root i.e. it will handle all requests
     * @return Root path
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * Configures the application context created by {@link org.springframework.web.context.ContextLoaderListener}
     * @return Config class
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    /**
     * Configures DispatcherServlet i.e. beans
     * @return Config class
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }


}
