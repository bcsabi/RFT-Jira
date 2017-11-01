package hu.unideb.rft.jira.jira_springboot_mvc.repository;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
