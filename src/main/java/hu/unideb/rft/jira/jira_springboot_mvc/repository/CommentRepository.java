package hu.unideb.rft.jira.jira_springboot_mvc.repository;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
