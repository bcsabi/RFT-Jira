package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.email.RegistrationEmailThread;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import hu.unideb.rft.jira.jira_springboot_mvc.repository.ProjectRepository;
import hu.unideb.rft.jira.jira_springboot_mvc.service.ProjectService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.SecurityService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.TaskService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.ChangePassValidator;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.ProjectValidator;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.TaskValidator;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import hu.unideb.rft.jira.jira_springboot_mvc.passChange;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private TaskValidator taskValidator;

    @Autowired
    private ProjectValidator projectValidator;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ChangePassValidator changePassValidator;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String registration(Model model , Principal user) {
        model.addAttribute("userForm", new User());

        if (user != null)
            return "redirect:/backlog";

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        userService.save(userForm);

        //send email
        RegistrationEmailThread registrationEmailThread = applicationContext.getBean(RegistrationEmailThread.class);
        registrationEmailThread.setUser(userForm);
        threadPoolTaskExecutor.execute(registrationEmailThread);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/backlog";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout,Principal user) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        if (user != null)
            return "redirect:/backlog";

        return "login";
    }

    @RequestMapping(value = {"/", "/backlog"}, method = RequestMethod.GET)
    public String welcome(Model model , Principal user) {
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        return "backlog";
    }

    @RequestMapping(value = {"/manage_projects"}, method = RequestMethod.GET)
    public String manage_projects(Model model, Principal user) {
        model.addAttribute("projectForm", new Project());
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects",currentProjects);
        return "manage_projects";
    }

    @RequestMapping(value = "/manage_projects", method = RequestMethod.POST,params = "create")
    public String createProject(@ModelAttribute("projectForm") Project projectForm, BindingResult bindingResult, Model model,Principal user) {
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects",currentProjects);
        projectForm.setUsername(currentUser.getUsername());
        projectForm.setUser(currentUser);
        projectValidator.validate(projectForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "manage_projects";
        }

        projectService.save(projectForm);

        return "redirect:/manage_projects";
    }

    @Transactional
    @RequestMapping(value = "/manage_projects", method = RequestMethod.POST,params = "delete")
    public String deleteProject(@ModelAttribute("projectForm") Project projectForm, BindingResult bindingResult, Model model,Principal user,
                                @RequestParam(value = "projectNamee") String projectNamee) {
        User currentUser = userService.findByUsername(user.getName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        List<String> projects = new ArrayList<>();
        for(Project proj : currentProjects)
            projects.add(proj.getProjectName());
        int index = 0;

        if (projects.contains(projectNamee))
        {
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
    @RequestMapping(value = "/manage_projects", method = RequestMethod.POST,params = "modify")
    public String modifyProject(@ModelAttribute("projectForm") Project projectForm, BindingResult bindingResult, Model model,Principal user,
                                @RequestParam(value = "projectNamee") String projectNamee) {
        User currentUser = userService.findByUsername(user.getName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        List<String> projects = new ArrayList<>();
        for(Project proj : currentProjects)
            projects.add(proj.getProjectName());
        int index = 0;

        if (projects.contains(projectNamee))
        {
            for (int i = 0; i < projects.size(); i++) {
                if(projectNamee.equals(projects.get(i))) {
                    index = i;
                }
            }
            /*projectService.findByProjectName((currentProjects.get(index).getProjectName())).setProjectName(projectNamee);
            projectService.save((currentProjects.get(index)));*/

        }

        return "redirect:/manage_projects";
    }

    @RequestMapping(value = {"/taskboard"}, method = RequestMethod.GET)
    public String taskboard(Model model,Principal user) {

        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());

        //Task table test
        Task a= new Task();
        a.setTaskname("valami");
        a.setBesorolas("bug");
        a.setSkalazas(1);
        a.setUsername(user.getName());
        a.setUser(userService.findByUsername(user.getName()));
        taskService.save(a);
        return "taskboard";
    }

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public String profile(Model model, Principal user) {
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        model.addAttribute("email", currentUser.getEmail());
        model.addAttribute("changePassForm",new passChange());

        return "profile";
    }

    @RequestMapping(value = {"/profile"}, method = RequestMethod.POST)
    public String profilepost(@ModelAttribute("changePassForm") passChange pass, BindingResult bindingResult, Model model, Principal user) {
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        model.addAttribute("email", currentUser.getEmail());
        pass.setUser(currentUser);
        changePassValidator.validate(pass,bindingResult);

        if (bindingResult.hasErrors())
        {
            return "profile";
        }

        currentUser.setPassword(pass.getNew_password());
        userService.save(currentUser);
        return "redirect:/profile";
    }

}
