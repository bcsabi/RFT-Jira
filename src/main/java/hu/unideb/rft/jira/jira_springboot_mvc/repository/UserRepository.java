package hu.unideb.rft.jira.jira_springboot_mvc.repository;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
