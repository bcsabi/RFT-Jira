package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.email.RegistrationEmailThread;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import hu.unideb.rft.jira.jira_springboot_mvc.service.SecurityService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.TaskService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.TaskValidator;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private TaskValidator taskValidator;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String registration(Model model , Principal user) {
        model.addAttribute("userForm", new User());

        if (user != null)
            return "redirect:/welcome";

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

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout,Principal user) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        if (user != null)
            return "redirect:/welcome";

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model , Principal user) {
        return "welcome";
    }

    @RequestMapping(value = {"/tasktest"}, method = RequestMethod.GET)
    public String tasktest(Model model,Principal user) {
        //Task table test
        Task a= new Task();
        a.setTaskname("valami");
        a.setBesorolas("bug");
        a.setSkalazas(1);
        a.setUsername(user.getName());
        a.setUser(userService.findByUsername(user.getName()));
        taskService.save(a);
        return "tasktest";
    }

}
