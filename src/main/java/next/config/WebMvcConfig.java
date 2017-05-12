package next.config;

import next.utils.LoginUserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "next.controller" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private static final int CACHE_PERIOD = 31556926; // one year

    @Bean
    public LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver() {
        return new LoginUserHandlerMethodArgumentResolver();
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/jsp/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/resources/**")
                .addResourceLocations("/WEB-INF/static_resources/")
                .setCachePeriod(CACHE_PERIOD);
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // Serving static files using the Servlet container's default Servlet.
        configurer.enable();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // loginUserHandlerMethodArgumentResolver()은 이 클래스에 정의된 메소드가 호출되는 것이 아니라
        // 이 클래스를 감싸고 있는 Proxy 클래스에 존재하는 loginUserHandlerMethodArgumentResolver()가 먼저 호출된다
        // Proxy에서는 실행되는 메소드의 return type에 해당하는 Bean이 존재하는지를 확인하고
        // 존재한다면 그 Bean을 return하고 존재하지 않으면 기존 클래스의 메소드를 그대로 호출한다.
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver());
    }
}
