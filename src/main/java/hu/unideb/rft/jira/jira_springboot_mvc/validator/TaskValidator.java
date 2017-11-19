package hu.unideb.rft.jira.jira_springboot_mvc.validator;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TaskValidator implements Validator{

    @Autowired
    private TaskService taskService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Task.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Task task = (Task) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taskName", "NotEmpty");
        if (task.getTaskName().length() >= 100 || task.getTaskName().length() <= 5) {
            errors.rejectValue("taskName", "Size.taskForm.taskname");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");
        if (task.getTaskName().length() >= 1000) {
            errors.rejectValue("description", "Size.taskForm.description");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "votesPoint", "NotEmpty");

    }
}
