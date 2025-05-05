package the305labs.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.thymeleaf.ThymeleafViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ThymeleafConfig implements WebMvcConfigurer {

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setSuffix(".html");
        resolver.setPrefix("classpath:/templates/");
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
}
