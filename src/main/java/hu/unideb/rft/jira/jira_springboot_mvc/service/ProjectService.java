package hu.unideb.rft.jira.jira_springboot_mvc.service;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;

public interface ProjectService {
    void save(Project project);

    Project findByProjectName(String projectName);
}
