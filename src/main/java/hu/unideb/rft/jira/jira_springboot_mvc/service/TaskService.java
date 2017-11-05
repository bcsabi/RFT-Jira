package hu.unideb.rft.jira.jira_springboot_mvc.service;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;

public interface TaskService {
    void save(Task task);

    Task findByTaskname(String taskname);
}
