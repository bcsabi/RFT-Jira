package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import hu.unideb.rft.jira.jira_springboot_mvc.repository.TaskRepository;
import hu.unideb.rft.jira.jira_springboot_mvc.service.ProjectService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.TaskService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class TaskBoardController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = {"/taskboard"}, method = RequestMethod.GET)
    public String taskboard(Model model, Principal user, @RequestParam(value = "id") String id) {

        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        Project project = projectService.findById(Long.parseLong(id));
        model.addAttribute("id", id);
        model.addAttribute("projectName", project.getProjectName());

        List<Task> tasks = new ArrayList<>();
        List<Task> taskByToDo = new ArrayList<>();
        List<Task> taskByReady = new ArrayList<>();
        List<Task> taskByInProgress = new ArrayList<>();
        List<Task> taskByReadyForTest = new ArrayList<>();
        List<Task> taskByDone = new ArrayList<>();

        for(Task task : project.getTasks()){
            tasks.add(task);
        }

        for(Task task : tasks) {
            switch (task.getStatus()) {
                case "ToDo":
                    taskByToDo.add(task);
                    break;
                case "Ready":
                    taskByReady.add(task);
                    break;
                case "In Progress":
                    taskByInProgress.add(task);
                    break;
                case "Ready for test":
                    taskByReadyForTest.add(task);
                    break;
                case "Done":
                    taskByDone.add(task);
                    break;
            }
        }

        Collections.sort(taskByToDo,(a, b) -> b.getTaskName().compareTo(a.getTaskName()));
        Collections.sort(taskByReady,(a, b) -> b.getTaskName().compareTo(a.getTaskName()));
        Collections.sort(taskByInProgress,(a, b) -> b.getTaskName().compareTo(a.getTaskName()));
        Collections.sort(taskByReadyForTest,(a, b) -> b.getTaskName().compareTo(a.getTaskName()));
        Collections.sort(taskByDone,(a, b) -> b.getTaskName().compareTo(a.getTaskName()));

        Collections.reverse(taskByToDo);
        Collections.reverse(taskByReady);
        Collections.reverse(taskByInProgress);
        Collections.reverse(taskByReadyForTest);
        Collections.reverse(taskByDone);

        model.addAttribute("taskByToDo", taskByToDo);
        model.addAttribute("taskByReady", taskByReady);
        model.addAttribute("taskByInProgress", taskByInProgress);
        model.addAttribute("taskByReadyForTest", taskByReadyForTest);
        model.addAttribute("taskByDone", taskByDone);

        return "taskboard";
    }
}
