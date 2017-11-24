package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;

import hu.unideb.rft.jira.jira_springboot_mvc.service.ProjectService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.TaskService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.TaskValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private TaskService taskService;

    @Autowired
    private TaskValidator taskValidator;

    @Autowired
    private ProjectService projectService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = {"/backlog"}, method = RequestMethod.GET)
    public String getTasks(Model model , Principal user,
                           @RequestParam(value="projectName") String projectName) {
        model.addAttribute("taskForm", new Task());
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects",currentProjects);
        model.addAttribute("projectName", projectName);
        Project project = projectService.findByProjectName(projectName);

        logger.info(projectName);
        List<Task> tasksByCurrentProject = new ArrayList<>();

        for(Task task : project.getTasks()){
                tasksByCurrentProject.add(task);
        }
        model.addAttribute("tasks", tasksByCurrentProject);

        return "backlog";
    }

    @RequestMapping(value = {"/backlog"}, method = RequestMethod.POST, params = "create")
    public String createTask(@ModelAttribute("taskForm") Task taskForm, BindingResult bindingResult, Model model, Principal user,
                             @RequestParam(name = "projectName") String projectName){
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        taskForm.setCreator(currentUser.getUsername());
        taskForm.setProject(projectService.findByProjectName(projectName));
        taskForm.setProject_name(projectName);
        taskForm.setStatus("ToDo");
        taskValidator.validate(taskForm, bindingResult);
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects",currentProjects);

        logger.info("CREATE TASK");
        logger.info("SAVED TASK NAME = " + taskForm.getTaskName());
        logger.info("SAVED TASK TO = " + taskForm.getProject_name());

        if(bindingResult.hasErrors()){
            return "redirect:/backlog?projectName=" + projectName;
        }

        taskService.save(taskForm);

        Project project = projectService.findByProjectName(projectName);

        logger.info(projectName);
        List<Task> tasksByCurrentProject = new ArrayList<>();

        for(Task task : project.getTasks()){
            tasksByCurrentProject.add(task);
        }
        model.addAttribute("tasks", tasksByCurrentProject);

        return "redirect:/backlog?projectName=" + projectName;
    }

}
