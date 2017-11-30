package hu.unideb.rft.jira.jira_springboot_mvc.service;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Comment;

import java.util.List;

public interface CommentService {

    void save(Comment comment);

    void deleteById(Long id);
}
