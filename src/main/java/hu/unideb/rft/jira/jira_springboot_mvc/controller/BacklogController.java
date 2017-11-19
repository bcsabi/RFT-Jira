package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;

import hu.unideb.rft.jira.jira_springboot_mvc.repository.TaskRepository;
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

    @RequestMapping(value = {"/", "/backlog"}, method = RequestMethod.GET)
    public String getTasks(Model model , Principal user) {
        model.addAttribute("taskForm", new Task());
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
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
    public String createTask(@ModelAttribute("taskForm") Task taskForm, BindingResult bindingResult, Model model, Principal user){
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        taskForm.setCreator(currentUser.getUsername());
        taskValidator.validate(taskForm, bindingResult);

        taskService.save(taskForm);

        if(bindingResult.hasErrors()){
            return "backlog";
        }

        List<Task> tasks = taskRepository.findAll();
        //átmeneti
        List<String> tasknames = new ArrayList<>();
        for(Task task : tasks){
            tasknames.add(task.getTaskName());
        }
        model.addAttribute("tasks", tasknames);

        return "redirect:/backlog";
    }


}
