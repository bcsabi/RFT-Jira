package hu.unideb.rft.jira.jira_springboot_mvc.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
