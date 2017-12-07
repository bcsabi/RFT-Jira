package hu.unideb.rft.jira.jira_springboot_mvc.service.impl;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import hu.unideb.rft.jira.jira_springboot_mvc.repository.ProjectRepository;
import hu.unideb.rft.jira.jira_springboot_mvc.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project findByProjectName(String projectName) {
        return projectRepository.findByProjectName(projectName);
    }

    @Override
    public List<Project> findByUsername(String userName) { return projectRepository.findByUsername(userName); }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Long deleteById(Long id) {
        return projectRepository.deleteById(id);
    }
}
