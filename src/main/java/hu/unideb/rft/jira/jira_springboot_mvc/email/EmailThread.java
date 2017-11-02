package hu.unideb.rft.jira.jira_springboot_mvc.email;

public interface EmailThread extends Runnable{

    void send();
}
