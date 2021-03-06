package hu.unideb.rft.jira.jira_springboot_mvc.validator;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.service.ProjectService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskValidator implements Validator{

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

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
        if (task.getTaskName().length() >= 2900) {
            errors.rejectValue("description", "Size.taskForm.description");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "NotEmpty");
        if(!task.getType().equals("Bug") && !task.getType().equals("Improvement") && !task.getType().equals("New feature")
                && !task.getType().equals("Content")){
            errors.rejectValue("type", "Value.taskForm.type");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priority", "NotEmpty");
        if(!task.getPriority().equals("Highest") && !task.getPriority().equals("High") && !task.getPriority().equals("Medium")
                && !task.getPriority().equals("Low") && !task.getPriority().equals("Lowest")){
            errors.rejectValue("priority", "Value.taskForm.priority");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "votesPoint", "NotEmpty");


        List<Task> tasks = taskService.findByProjectNamee(task.getProjectNamee());
        List<String> curr = new ArrayList<>();
        for(Task tsk : tasks)
            curr.add(tsk.getTaskName());
        for(String tskName : curr)
        {
            if (tskName.equals(task.getTaskName()))
            {
                errors.rejectValue("taskName", "Duplicate.projectForm.taskname");
                break;
            }
        }
    }
}
