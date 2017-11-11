package hu.unideb.rft.jira.jira_springboot_mvc.validator;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import hu.unideb.rft.jira.jira_springboot_mvc.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProjectValidator implements Validator{

    @Autowired
    private ProjectService projectService;

    private User user;

    @Override
    public boolean supports(Class<?> aClass) {
        return Project.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Project project = (Project) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectName", "NotEmpty");
        if (project.getProjectName().length() >= 30 || project.getProjectName().length() <= 5) {
            errors.rejectValue("projectName", "Size.projectForm.projectname");
        }
        if (projectService.findByProjectName(project.getProjectName()) != null && project.getProjectName() !=
                user.getUsername()) {
            errors.rejectValue("projectName", "Duplicate.projectForm.projectname");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectDescription", "NotEmpty");
        if (project.getProjectDescription().length() > 200)
        {
            errors.rejectValue("projectDescription","Size.projectForm.projectdescription");
        }

    }

    void setUser(User user) { this.user = user; }
}
