package tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class Main{

	@Value("${frontend.application.path}")
	private String frontEndApplicationPath;

	public static void main(String[] args) {
			SpringApplication.run(Main.class, args);
	}

	/*
		Here we configure the origins that are allowed to access ms endpoints:
		- "addMapping" defines the endpoints that are going to be managed
		- "allowedOrigins" defines the origins allowed to this/these endpoints
	*/
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins(frontEndApplicationPath);
			}
		};
	}

}
