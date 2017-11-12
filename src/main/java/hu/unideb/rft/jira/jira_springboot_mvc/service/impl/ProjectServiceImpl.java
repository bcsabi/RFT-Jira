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
    private ProjectRepository Projectrepository;

    @Override
    public void save(Project project) {
        Projectrepository.save(project);
    }

    @Override
    public Project findByProjectName(String projectName) {
        return Projectrepository.findByProjectName(projectName);
    }

    @Override
    public List<Project> findByUsername(String userName) { return Projectrepository.findByUsername(userName); }

    @Override
    public Long deleteById(Long id) {
        return Projectrepository.deleteById(id);
    }
}
