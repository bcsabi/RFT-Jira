package hu.unideb.rft.jira.jira_springboot_mvc.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {
    private Long id;
    @Column(length = 100)
    private String projectName;
    private String projectDescription;
    private String username;
    private User user;
    private Set<Task> tasks;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @OneToMany
    @JoinColumn(name = "project_id")
    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void setUser(User user) {
        this.user = user;
    }

    void update(String projectName, String projectDescription) {

        this.projectName = projectName;
        this.projectDescription = projectDescription;
    }
}