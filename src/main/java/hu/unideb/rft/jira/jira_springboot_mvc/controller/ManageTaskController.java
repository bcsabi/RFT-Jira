package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Comment;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;
import hu.unideb.rft.jira.jira_springboot_mvc.repository.TaskRepository;
import hu.unideb.rft.jira.jira_springboot_mvc.service.CommentService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.ProjectService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.TaskService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.CommentValidator;
import hu.unideb.rft.jira.jira_springboot_mvc.validator.TaskValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ManageTaskController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentValidator commentValidator;

    @Autowired
    private ProjectService projectService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = {"/manage_task"}, method = RequestMethod.GET)
    public String manage_task(Model model, Principal user,
                              @RequestParam(value = "pid") String pid,
                              @RequestParam(value = "taskID") String taskID) {
        model.addAttribute("commentForm", new Comment());
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects",currentProjects);
        Project project = projectService.findById(Long.parseLong(pid));
        model.addAttribute("pid", pid);
        model.addAttribute("projectName", project.getProjectName());
        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        model.addAttribute("taskForm", new Task());

        Task currentTask = taskService.findById(Long.parseLong(taskID));

        model.addAttribute("currentTask", currentTask);

        List<Comment> commentsByCurrentTask = new ArrayList<>();

        for(Comment comment : currentTask.getComments()){
            commentsByCurrentTask.add(comment);
            logger.info(comment.getCommentText());
        }

        Collections.sort(commentsByCurrentTask, Comparator.comparing(Comment::getDateTime));
        model.addAttribute("comments", commentsByCurrentTask);


        return "manage_task";
    }

    @Modifying
    @Transactional
    @RequestMapping(value = "/manage_task", method = RequestMethod.POST, params = "modify")
    public String modifyTask(@ModelAttribute("taskForm") Task taskForm, Principal user,
                             @RequestParam("pid") String pid,
                             @RequestParam("taskID") String taskID,
                             @RequestParam("taskName") String taskName,
                             @RequestParam("taskDescription") String taskDescription,
                             @RequestParam("taskType") String taskType,
                             @RequestParam("taskPriority") String taskPriority,
                             @RequestParam("taskStatus") String taskStatus,
                             @RequestParam("taskVotes") String taskVotes,
                             @RequestParam("assignedTo") String assignedTo){

        Project project = projectService.findById(Long.parseLong(pid));

        logger.info(project.getProjectName());
        List<Task> tasksByCurrentProject = new ArrayList<>();

        for(Task task : project.getTasks()){
            tasksByCurrentProject.add(task);
        }

        int index = 0;
        List<String> tasks = new ArrayList<>();
        for(Task tsk : tasksByCurrentProject)
            tasks.add(tsk.getTaskName());

        if (tasks.contains(taskName)) {
            for (int i = 0; i < tasks.size(); i++) {
                if (taskName.equals(tasks.get(i))) {
                    index = i;
                }
            }
        }

        Task currentTask = tasksByCurrentProject.get(index);
        if(!user.getName().equals(assignedTo) && !currentTask.getStatus().equals(taskStatus)){
            return "redirect:/backlog?pid=" + pid;
        }

        currentTask.setTaskName(taskName);
        currentTask.setDescription(taskDescription);
        currentTask.setType(taskType);
        currentTask.setPriority(taskPriority);
        currentTask.setStatus(taskStatus);
        currentTask.setVotesPoint(Integer.parseInt(taskVotes));
        currentTask.setAssignedTo(assignedTo);
        taskService.save(currentTask);

        taskID = taskID.replace(",", "");
        logger.info("TASKID = " + taskID);

        return "redirect:/manage_task?pid=" + pid + "&taskID=" + taskID;
    }

    @RequestMapping(value = "/manage_task", method = RequestMethod.POST, params = "create_comment")
    public String createComment(@ModelAttribute("commentForm") Comment commentForm, BindingResult bindingResult, Model model, Principal user,
                                @RequestParam("pid") String pid,
                                @RequestParam("taskID") String taskID){
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        commentForm.setUsername(currentUser.getUsername());
        commentForm.setDateTime(new Date());
        commentForm.setTask(taskService.findById(Long.parseLong(taskID)));
        commentValidator.validate(commentForm, bindingResult);

        if(bindingResult.hasErrors()){
            return "redirect:/manage_task?pid=" + pid + "&taskID=" + taskID;
        }

        commentService.save(commentForm);

        Task currentTask = taskService.findById(Long.parseLong(taskID));

        List<Comment> commentsByCurrentTask = new ArrayList<>();

        for(Comment comment : currentTask.getComments()){
            commentsByCurrentTask.add(comment);
        }

        Collections.sort(commentsByCurrentTask, Comparator.comparing(Comment::getDateTime));
        model.addAttribute("comments", commentsByCurrentTask);
        return "redirect:/manage_task?pid=" + pid + "&taskID=" + taskID;
    }

    @Transactional
    @RequestMapping(value = "/manage_task", method = RequestMethod.POST, params = "delete")
    public String deleteTask(@ModelAttribute("taskForm") Task taskForm,  @RequestParam("pid") String pid,
                             @RequestParam("taskID") String taskID) {
        Project project = projectService.findById(Long.parseLong(pid));

        logger.info(project.getProjectName());
        List<Task> tasksByCurrentProject = new ArrayList<>();
        for(Task task : project.getTasks()){
            tasksByCurrentProject.add(task);
        }

        int index = 0;
        List<String> tasks = new ArrayList<>();
        for(Task tsk : tasksByCurrentProject)
            tasks.add(tsk.getTaskName());

        taskID = taskID.replace(",", "");
        String taskName = taskService.findById(Long.valueOf(taskID)).getTaskName();
        if (tasks.contains(taskName)) {
            for (int i = 0; i < tasks.size(); i++) {
                if(taskName.equals(tasks.get(i))) {
                    index = i;
                }
            }

            for(Comment comment : tasksByCurrentProject.get(index).getComments()){
                commentService.deleteById(comment.getId());
            }
            taskService.deleteByTaskName(tasksByCurrentProject.get(index).getTaskName());
        }

        return "redirect:/backlog?pid=" + pid;
    }

}
