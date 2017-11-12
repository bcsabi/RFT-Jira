package hu.unideb.rft.jira.jira_springboot_mvc.service.impl;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Comment;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.repository.CommentRepository;
import hu.unideb.rft.jira.jira_springboot_mvc.repository.TaskRepository;
import hu.unideb.rft.jira.jira_springboot_mvc.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByTaskId(Long taskId) {
        List<Comment> goodComments = new ArrayList<>();

        for(Comment comment : commentRepository.findAll()){
            if(comment.getTask().getId().equals(taskId)){
                goodComments.add(comment);
            }
        }
        return goodComments;
    }
}
