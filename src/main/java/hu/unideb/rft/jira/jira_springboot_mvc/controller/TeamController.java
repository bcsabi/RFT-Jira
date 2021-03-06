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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

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

        model.addAttribute("memberForm", new User());
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

    @RequestMapping(value = "/team", method = RequestMethod.POST, params = "add")
    public String addMember(@ModelAttribute("memberForm") User userForm, @RequestParam("pid") String pid,
                            @RequestParam("member") String newMember){

        Project project = projectService.findById(Long.parseLong(pid));
        List<User> users = new ArrayList<>();
        users.addAll(project.getUser());

        users.add(userService.findByUsername(newMember));
        Set<User> projectUsers = new HashSet<>(users);
        project.setUser(projectUsers);
        projectService.save(project);

        return "redirect:/team?pid=" + pid;
    }

    @RequestMapping(value = "/team", method = RequestMethod.POST, params = "delete")
    public String deleteMember(@ModelAttribute("memberForm") User userForm, @RequestParam("pid") String pid,
                               @RequestParam("clickedMember") String member){

        Project project = projectService.findById(Long.parseLong(pid));
        List<User> users = new ArrayList<>();
        users.addAll(project.getUser());

        User userToDelete = userService.findByUsername(member);
        users.remove(userToDelete);
        Set<User> projectUsers = new HashSet<>(users);
        project.setUser(projectUsers);
        projectService.save(project);

        return "redirect:/team?pid=" + pid;
    }

}