package smas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Spring MVC configuration
 */
@Configuration
@EnableWebMvc
@ComponentScan("smas.web")
public class WebConfig extends WebMvcConfigurerAdapter{

    /**
     * ThymeleafViewResolver that resolves Thymeleaf template views from logical view names
     * @param templateEngine *
     * @return *
     */
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    /**
     * SpringTemplateEngine to process the templates and render the results
     * @param templateResolver *
     * @return *
     */
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    /**
     * TemplateResolver that loads Thymeleaf templates
     * @return *
     */
    @Bean
    public ITemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("WEB-INF/thymeleaf/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }

    /**
     * Handler for static resources
     * @param configurer *
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }
}
