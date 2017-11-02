package hu.unideb.rft.jira.jira_springboot_mvc;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.Resource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan
@PropertySource("classpath:mail.properties")
public class MvcConfiguration extends WebMvcConfigurerAdapter{

    private static final String PROPERTY_MAIL_HOST = "mail.host";
    private static final String PROPERTY_MAIL_PORT = "mail.port";
    private static final String PROPERTY_MAIL_ADDRESS = "mail.address";
    private static final String PROPERTY_MAIL_PASSWORD = "mail.password";
    private static final String PROPERTY_MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String PROPERTY_MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String PROPERTY_MAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String PROPERTY_MAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
    private static final String PROPERTY_MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String PROPERTY_MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String PROPERTY_MAIL_DEBUG = "mail.debug";

    @Resource
    private Environment environment;

    @Autowired
    private ApplicationContext applicationContext;



    // Bean for Velocity - e-mail templates
    @Bean
    public VelocityEngine velocityEngine() {

        Properties properties = new Properties();
        properties.put("resource.loader", "class");
        properties.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        return new VelocityEngine(properties);

    }

    @Bean
    public JavaMailSender javaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getRequiredProperty(PROPERTY_MAIL_HOST));
        mailSender.setPort(Integer.valueOf(environment.getRequiredProperty(PROPERTY_MAIL_PORT)));

        mailSender.setUsername(environment.getRequiredProperty(PROPERTY_MAIL_ADDRESS));
        mailSender.setPassword(environment.getRequiredProperty(PROPERTY_MAIL_PASSWORD));

        Properties props = mailSender.getJavaMailProperties();
        props.put(PROPERTY_MAIL_SMTP_STARTTLS_ENABLE, environment.getRequiredProperty(PROPERTY_MAIL_SMTP_STARTTLS_ENABLE));
        props.put(PROPERTY_MAIL_SMTP_HOST, environment.getRequiredProperty(PROPERTY_MAIL_SMTP_HOST));
        props.put(PROPERTY_MAIL_SMTP_SOCKETFACTORY_PORT, environment.getRequiredProperty(PROPERTY_MAIL_SMTP_SOCKETFACTORY_PORT));
        props.put(PROPERTY_MAIL_SMTP_SOCKETFACTORY_CLASS, environment.getRequiredProperty(PROPERTY_MAIL_SMTP_SOCKETFACTORY_CLASS));
        props.put(PROPERTY_MAIL_SMTP_AUTH, environment.getRequiredProperty(PROPERTY_MAIL_SMTP_AUTH));
        props.put(PROPERTY_MAIL_SMTP_PORT, environment.getRequiredProperty(PROPERTY_MAIL_SMTP_PORT));
        props.put(PROPERTY_MAIL_DEBUG, environment.getRequiredProperty(PROPERTY_MAIL_DEBUG));

        return mailSender;

    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/Views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        registry.viewResolver(resolver);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
