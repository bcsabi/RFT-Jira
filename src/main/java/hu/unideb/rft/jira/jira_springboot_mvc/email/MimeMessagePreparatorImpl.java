package hu.unideb.rft.jira.jira_springboot_mvc.email;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

public class MimeMessagePreparatorImpl implements MimeMessagePreparator {

    private static final String ENCODING = "UTF-8";

    private VelocityEngine velocityEngine;
    private String recipient;
    private InternetAddress from;
    private String subject;
    private Map<String, String> parameters;
    private String template;

    public MimeMessagePreparatorImpl() {
    }

    public MimeMessagePreparatorImpl(VelocityEngine velocityEngine, String recipient, InternetAddress from, String subject, Map<String, String> parameters, String template) {
        this.velocityEngine = velocityEngine;
        this.recipient = recipient;
        this.from = from;
        this.subject = subject;
        this.parameters = parameters;
        this.template = template;
    }

    public static String getENCODING() {
        return ENCODING;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public InternetAddress getFrom() {
        return from;
    }

    public void setFrom(InternetAddress from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {

        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

        message.setTo(recipient);
        message.setFrom(from);
        message.setSubject(subject);
        message.setSentDate(new Date());

        VelocityContext velocityContext = new VelocityContext();
        for(String key : parameters.keySet()){
            velocityContext.put(key, parameters.get(key));
        }

        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate(template, ENCODING, velocityContext, stringWriter);

        message.setText(stringWriter.toString(), true);
    }
}
