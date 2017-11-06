package hu.unideb.rft.jira.jira_springboot_mvc.validator;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator{

    @Autowired
    private UserService userService;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        if (user.getFirstName().length() < 3 || user.getFirstName().length() > 15) {
            errors.rejectValue("firstName", "Size.userForm.firstname");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        if (user.getLastName().length() < 3 || user.getLastName().length() > 15) {
            errors.rejectValue("lastName", "Size.userForm.lastname");
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(user.getEmail());
        if (!matcher.find())
        {
            errors.rejectValue("email","Wrong.email");
        }
    }
}
