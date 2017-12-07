package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import hu.unideb.rft.jira.jira_springboot_mvc.service.ProjectService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.ProjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ManageProjectsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectValidator projectValidator;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = {"/", "/manage_projects"}, method = RequestMethod.GET)
    public String manage_projects(Model model, Principal user) {
        model.addAttribute("projectForm", new Project());
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        //List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        List<Project> currentProjects = new ArrayList<>();
        for(Project p : projectService.findAll()){
            if(p.getUser().contains(currentUser)){
                currentProjects.add(p);
            }
        }
        model.addAttribute("projects",currentProjects);

        return "manage_projects";
    }

    @RequestMapping(value = {"/", "/manage_projects"}, method = RequestMethod.POST,params = "create")
    public String createProject(@ModelAttribute("projectForm") Project projectForm, BindingResult bindingResult, Model model, Principal user) {
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects",currentProjects);
        projectForm.setUsername(currentUser.getUsername());
        Set<User> users = new HashSet<>();
        users.add(currentUser);
        projectForm.setUser(users);
        projectValidator.validate(projectForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "manage_projects";
        }

        logger.info("CREATE PROJ");
        logger.info("SAVED TASK NAME = " + projectForm.getProjectName());
        //logger.info("SAVED TASK TO = " + taskForm.getProjectNamee());

        projectService.save(projectForm);

        return "redirect:/manage_projects";
    }

    @Transactional
    @RequestMapping(value = {"/", "/manage_projects"}, method = RequestMethod.POST,params = "delete")
    public String deleteProject(@ModelAttribute("projectForm") Project projectForm, BindingResult bindingResult, Model model,Principal user,
                                @RequestParam(value = "projectNamee") String projectNamee) {
        User currentUser = userService.findByUsername(user.getName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        List<String> projects = new ArrayList<>();

        for(Project proj : currentProjects) {
            projects.add(proj.getProjectName());
        }
        int index = 0;

        if (projects.contains(projectNamee)) {
            for (int i = 0; i < projects.size(); i++) {
                if(projectNamee.equals(projects.get(i))) {
                    index = i;
                }
            }
            projectService.deleteById(currentProjects.get(index).getId());
        }

        return "redirect:/manage_projects";
    }

    @Modifying
    @Transactional
    @RequestMapping(value = {"/", "/manage_projects"}, method = RequestMethod.POST, params = "modify")
    public String modifyProject(@ModelAttribute("projectForm") Project projectForm, BindingResult bindingResult, Model model,Principal user,
                                @RequestParam(value = "projectNamee")String projectNamee,
                                @RequestParam(value = "projectDescription") String projectDescription,
                                @RequestParam(value = "projectIndex") String projectIndex) {
        User currentUser = userService.findByUsername(user.getName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        List<String> projects = new ArrayList<>();

        logger.info("Project modify!");
        for(Project proj : currentProjects) {
            projects.add(proj.getProjectName());
        }

        int index = Integer.parseInt(projectIndex);
        logger.info("Project index: " + projectIndex);
        Project project = currentProjects.get(index);
        logger.info("Old project name: " + project.getProjectName());
        project.setProjectName(projectNamee);
        logger.info("New project name: " + project.getProjectName());
        logger.info("Old project description: " + project.getProjectDescription());
        project.setProjectDescription(projectDescription);
        logger.info("New project description: " + project.getProjectDescription());
        projectService.save(project);

        return "redirect:/manage_projects";
    }

    @Modifying
    @Transactional
    @RequestMapping(value = {"/", "/manage_projects"}, method = RequestMethod.POST, params = "add")
    public String addUserToProject(@ModelAttribute("projectForm") Project projectForm, BindingResult bindingResult, Model model,Principal user,
                                   @RequestParam(value = "projectNamee")String projectNamee,
                                   @RequestParam(value = "projectDescription") String projectDescription,
                                   @RequestParam(value = "projectIndex") String projectIndex,
                                   @RequestParam(value = "userToAdd") String userToAdd) {
        User currentUser = userService.findByUsername(user.getName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());

        int index = Integer.parseInt(projectIndex);
        Project project = currentProjects.get(index);
        Set<User> users = project.getUser();
        if (!users.contains(userService.findByUsername(userToAdd)) && userService.findByUsername(userToAdd) != null)
            users.add(userService.findByUsername(userToAdd));
        project.setUser(users);
        projectService.save(project);


        return "redirect:/manage_projects";
    }
}
