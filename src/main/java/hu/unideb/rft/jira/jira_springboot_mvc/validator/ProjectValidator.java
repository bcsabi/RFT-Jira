package hu.unideb.rft.jira.jira_springboot_mvc.validator;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import hu.unideb.rft.jira.jira_springboot_mvc.service.ProjectService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectValidator implements Validator{

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Project.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Project project = (Project) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectName", "NotEmptyProject");
        if (project.getProjectName().length() >= 30 || project.getProjectName().length() <= 5) {
            errors.rejectValue("projectName", "Size.projectForm.projectname");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectDescription", "NotEmptyProject");
        if (project.getProjectDescription().length() > 200)
        {
            errors.rejectValue("projectDescription","Size.projectForm.projectdescription");
        }

        List<Project> list = projectService.findByUsername(project.getUsername());
        List<String> lista2 = new ArrayList<>();
        for(Project proj : list)
            lista2.add(proj.getProjectName());
        for(String projectname : lista2) {
            if (projectname.equals(project.getProjectName())) {
                errors.rejectValue("projectName", "Duplicate.projectForm.projectname");
                break;
            }
        }

    }
}
