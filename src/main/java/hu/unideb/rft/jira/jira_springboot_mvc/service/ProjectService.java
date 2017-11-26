package hu.unideb.rft.jira.jira_springboot_mvc.service;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;

import java.util.List;

public interface ProjectService {
    void save(Project project);

    Project findById(Long id);
    Project findByProjectName(String projectName);
    List<Project> findByUsername(String userName);
    Long deleteById(Long id);
}
