package hu.unideb.rft.jira.jira_springboot_mvc.email;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public class RegistrationEmailThread implements EmailThread {

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    private User user;

    public RegistrationEmailThread() {
    }

    public RegistrationEmailThread(User user) {
        this.user = user;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void send() {

        MimeMessagePreparatorImpl mimeMessagePreparator = new MimeMessagePreparatorImpl();
        mimeMessagePreparator.setRecipient(user.getEmail());
        try {
            mimeMessagePreparator.setFrom(new InternetAddress(environment.getRequiredProperty("mail.address"), "RFT Jira Site"));
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        mimeMessagePreparator.setSubject("RFT Jira registration");
        mimeMessagePreparator.setTemplate("email_welcome.vm");
        mimeMessagePreparator.setVelocityEngine(velocityEngine);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", user.getUsername());
        mimeMessagePreparator.setParameters(parameters);

        javaMailSender.send(mimeMessagePreparator);
    }

    @Override
    public void run() {

        send();
    }
}
