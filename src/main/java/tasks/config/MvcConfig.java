package tasks.config;

import tasks.interceptor.RestInterceptorAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private RestInterceptorAll restInterceptorAll;

    @Autowired
    public MvcConfig(RestInterceptorAll restInterceptorAll) {
        this.restInterceptorAll = restInterceptorAll;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Registers our "RestInterceptorAll" into the list of global interceptors
        registry.addInterceptor(restInterceptorAll);
    }

}
