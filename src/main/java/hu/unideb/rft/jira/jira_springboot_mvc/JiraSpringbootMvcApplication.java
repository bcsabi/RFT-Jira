package hu.unideb.rft.jira.jira_springboot_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class JiraSpringbootMvcApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(JiraSpringbootMvcApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder.sources(JiraSpringbootMvcApplication.class);
	}
}
