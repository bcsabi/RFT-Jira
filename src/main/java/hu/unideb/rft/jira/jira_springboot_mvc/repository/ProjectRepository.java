package hu.unideb.rft.jira.jira_springboot_mvc.repository;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByProjectName(String projectName);
    List<Project> findByUsername(String userName);
}
