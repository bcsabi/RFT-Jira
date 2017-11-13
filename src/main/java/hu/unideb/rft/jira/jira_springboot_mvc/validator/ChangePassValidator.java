package hu.unideb.rft.jira.jira_springboot_mvc.validator;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import passchange.passChange;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ChangePassValidator implements Validator{


    @Override
    public boolean supports(Class<?> aClass) {
        return passChange.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        passChange pass = (passChange) o;
        BCryptPasswordEncoder a = new BCryptPasswordEncoder();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "new_password", "NotEmpty");
        if (pass.getNew_password().length() <= 7) {
            errors.rejectValue("new_password", "Size.changePassForm.newPassword");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirm_password", "NotEmpty");
        if (pass.getConfirm_password().length() <= 7) {
            errors.rejectValue("confirm_password", "Size.changePassForm.confirmPassword");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "current_password", "NotEmpty");
        if (!a.matches(pass.getCurrent_password(),pass.getUser().getPassword())) {
            errors.rejectValue("current_password", "Size.changePassForm.currentPasswordNoMatch");
        }

        if (!pass.getNew_password().equals(pass.getConfirm_password()))
        {
            errors.rejectValue("new_password","changePassForm.NewNoMatchConfirm");
        }

        if (pass.getNew_password().equals(pass.getCurrent_password()))
        {
            errors.rejectValue("new_password","changePassForm.NewMatchCurrent");
        }
    }
}
