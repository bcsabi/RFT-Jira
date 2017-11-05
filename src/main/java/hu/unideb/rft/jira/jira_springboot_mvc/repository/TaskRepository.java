package hu.unideb.rft.jira.jira_springboot_mvc.repository;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByTaskname(String taskname);
}
