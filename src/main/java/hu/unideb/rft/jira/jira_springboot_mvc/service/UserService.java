package hu.unideb.rft.jira.jira_springboot_mvc.service;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
