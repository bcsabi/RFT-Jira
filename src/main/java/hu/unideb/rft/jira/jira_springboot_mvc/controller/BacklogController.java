package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;

import hu.unideb.rft.jira.jira_springboot_mvc.repository.TaskRepository;
import hu.unideb.rft.jira.jira_springboot_mvc.service.ProjectService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.TaskService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BacklogController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskValidator taskValidator;

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = {"/", "/backlog"}, method = RequestMethod.GET)
    public String getTasks(Model model , Principal user) {
        model.addAttribute("taskForm", new Task());
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects",currentProjects);
        List<Task> tasks = taskRepository.findAll();
        //átmeneti
        List<String> tasknames = new ArrayList<>();
        for(Task task : tasks){
            tasknames.add(task.getTaskName());
        }
        model.addAttribute("tasks", tasknames);

        return "backlog";
    }

    @RequestMapping(value = {"/", "/backlog"}, method = RequestMethod.POST)
    public String createTask(@ModelAttribute("taskForm") Task taskForm, BindingResult bindingResult, Model model, Principal user,
                             @RequestParam(name = "project_name") String project_name){
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        taskForm.setCreator(currentUser.getUsername());
        taskForm.setProject(projectService.findByProjectName(project_name));
        taskValidator.validate(taskForm, bindingResult);
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects",currentProjects);

        if(bindingResult.hasErrors()){
            return "backlog";
        }

        taskService.save(taskForm);

        List<Task> tasks = taskRepository.findAll();
        //átmeneti
        List<String> tasknames = new ArrayList<>();
        for(Task task : tasks){
            tasknames.add(task.getTaskName());
        }
        model.addAttribute("tasks", tasknames);


        return "backlog";
    }


}
