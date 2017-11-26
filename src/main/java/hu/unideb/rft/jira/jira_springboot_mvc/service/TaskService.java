package hu.unideb.rft.jira.jira_springboot_mvc.service;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;

import java.util.List;

public interface TaskService {
    void save(Task task);

    Task findByTaskname(String taskname);

    Long deleteByTaskName(String taskName);
    List<Task> findByProjectNamee(String projectName);
    Task findById(Long id);
}

