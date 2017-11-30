package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.service.SecurityService;
import hu.unideb.rft.jira.jira_springboot_mvc.email.RegistrationEmailThread;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
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
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String registration(Model model , Principal user) {
        model.addAttribute("userForm", new User());

        if (user != null) {
            return "redirect:/backlog";
        }

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        userService.save(userForm);

        RegistrationEmailThread registrationEmailThread = applicationContext.getBean(RegistrationEmailThread.class);
        registrationEmailThread.setUser(userForm);
        threadPoolTaskExecutor.execute(registrationEmailThread);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/manage_projects";
    }

}
