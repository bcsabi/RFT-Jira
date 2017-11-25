package hu.unideb.rft.jira.jira_springboot_mvc.service.impl;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.repository.TaskRepository;
import hu.unideb.rft.jira.jira_springboot_mvc.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Task findByTaskname(String taskname) {
        return taskRepository.findByTaskName(taskname);
    }

    @Override
    public Long deleteByTaskName(String taskName) {
        return taskRepository.deleteByTaskName(taskName);
    }

    @Override
    public List<Task> findByProjectNamee(String projectName) {
        return taskRepository.findByProjectNamee(projectName);
    }
}
