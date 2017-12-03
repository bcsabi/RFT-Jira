package hu.unideb.rft.jira.jira_springboot_mvc.service;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
    List<User> findAll();
}
