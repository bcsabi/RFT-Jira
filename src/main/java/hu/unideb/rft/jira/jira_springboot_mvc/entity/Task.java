package hu.unideb.rft.jira.jira_springboot_mvc.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "task")
public class Task {
    private Long id;
    @Column(length = 100)
    private String taskName;
    @Size(max = 3000)
    private String description;
    private String type;
    private String priority;
    private Integer votesPoint;
    private String creator;
    private String status;
    private String assignedTo;
    private String projectNamee;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getVotesPoint() {
        return votesPoint;
    }

    public void setVotesPoint(Integer votesPoint) {
        this.votesPoint = votesPoint;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @ManyToOne
    //@JoinColumn(name = "project_id")
    public Project getProject() {return project; }

    public void setProject(Project project) { this.project = project; }

    @OneToMany
    @JoinColumn(name = "comment_id")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getProjectNamee() {
        return projectNamee;
    }

    public void setProjectNamee(String projectNamee) {
        this.projectNamee = projectNamee;
    }
}
