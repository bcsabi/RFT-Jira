package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
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
public class TeamController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = {"/team"}, method = RequestMethod.GET)
    public String taskboard(Model model, Principal user, @RequestParam(value = "pid") String pid) {

        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());

        Project project = projectService.findById(Long.parseLong(pid));
        model.addAttribute("pid", pid);
        model.addAttribute("projectName", project.getProjectName());

        List<User> projectUsers = new ArrayList<>();
        for(User u : project.getUser()){
            projectUsers.add(u);
        }

        model.addAttribute("projectUsers", projectUsers);

        List<User> users = new ArrayList<>();
        for(User ur : userService.findAll()){
            if(!projectUsers.contains(ur)){
                users.add(ur);
            }
        }
        model.addAttribute("users", users);



        return "team";
    }
}