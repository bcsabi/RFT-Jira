package hu.unideb.rft.jira.jira_springboot_mvc.service.impl;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.repository.TaskRepository;
import hu.unideb.rft.jira.jira_springboot_mvc.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository Taskrepository;

    @Override
    public void save(Task task) {
        Taskrepository.save(task);
    }

    @Override
    public Task findByTaskname(String taskname) {
        return Taskrepository.findByTaskname(taskname);
    }

}
