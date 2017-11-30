package hu.unideb.rft.jira.jira_springboot_mvc.validator;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Comment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CommentValidator implements Validator{

    @Override
    public boolean supports(Class<?> aClass) {
        return Comment.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Comment comment = (Comment) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "commentText", "NotEmpty");
        if(comment.getCommentText().length() > 3000){
            errors.rejectValue("commentText", "Size.commentForm.comment");
        }
    }
}
