package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import hu.unideb.rft.jira.jira_springboot_mvc.passChange;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.ChangePassValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChangePassValidator changePassValidator;


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

        if (bindingResult.hasErrors()) {
            return "profile";
        }

        currentUser.setPassword(pass.getNew_password());
        userService.save(currentUser);

        return "redirect:/profile";
    }
}
