package hu.unideb.rft.jira.jira_springboot_mvc.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "task")
public class Task {
    private Long id;
    @Column(length = 100)
    private String taskname;
    private String besorolas;
    private int skalazas;
    private String username;
    private User user;
    private Project project;
    private Set<Comment> comments;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getBesorolas() { return besorolas; }

    public void setBesorolas(String besorolas) {
        this.besorolas = besorolas;
    }

    public int getSkalazas() {
        return skalazas;
    }

    public void setSkalazas(int skalazas) {
        this.skalazas = skalazas;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "project_id")
    public Project getProject() {return project; }

    public void setProject(Project project) { this.project = project; }

    @OneToMany
    @JoinColumn(name = "task_id")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
