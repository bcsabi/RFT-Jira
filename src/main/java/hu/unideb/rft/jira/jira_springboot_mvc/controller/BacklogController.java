package hu.unideb.rft.jira.jira_springboot_mvc.controller;

import hu.unideb.rft.jira.jira_springboot_mvc.entity.Comment;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Project;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.Task;
import hu.unideb.rft.jira.jira_springboot_mvc.entity.User;

import hu.unideb.rft.jira.jira_springboot_mvc.service.CommentService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.ProjectService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.TaskService;
import hu.unideb.rft.jira.jira_springboot_mvc.service.UserService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BacklogController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TaskValidator taskValidator;

    @Autowired
    private ProjectService projectService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = {"/backlog"}, method = RequestMethod.GET)
    public String getTasks(Model model , Principal user,
                           @RequestParam(value="pid") String pid) {
        model.addAttribute("taskForm", new Task());
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects",currentProjects);
        Project project = projectService.findById(Long.parseLong(pid));
        model.addAttribute("pid", pid);
        model.addAttribute("projectName", project.getProjectName());

        logger.info(project.getProjectName());
        List<Task> tasksByCurrentProject = new ArrayList<>();


        for(Task task : project.getTasks()){
            tasksByCurrentProject.add(task);
        }


        Comparator<Task> comparator = Comparator.comparing(task -> task.getId());
        comparator = comparator.thenComparing(Comparator.comparing(task -> task.getTaskName()));
        List<Task> tasks = tasksByCurrentProject.stream().sorted(comparator).collect(Collectors.toList());
        model.addAttribute("tasks", tasks);

        double donec = 0;
        double donepoints = 0;
        double allpoints = 0;
        for(Task tsk : tasksByCurrentProject) {
            if (tsk.getStatus().equals("Done")) {
                donec++;
                donepoints += tsk.getVotesPoint();
            }
            allpoints += tsk.getVotesPoint();
        }
        model.addAttribute("all_task",tasksByCurrentProject.size());
        model.addAttribute("donec",(int) donec);
        model.addAttribute("donepoints",(int) donepoints);
        model.addAttribute("allpoints",(int) allpoints);
        double percentage = ((donepoints / tasksByCurrentProject.size()) * 100);
        model.addAttribute("percentage",Math.round(percentage));

        return "backlog";
    }

    @RequestMapping(value = {"/backlog"}, method = RequestMethod.POST, params = "create")
    public String createTask(@ModelAttribute("taskForm") Task taskForm, BindingResult bindingResult, Model model, Principal user,
                             @RequestParam(value = "pid") String pid){
        User currentUser = userService.findByUsername(user.getName());
        model.addAttribute("firstName",currentUser.getFirstName());
        model.addAttribute("lastName",currentUser.getLastName());
        taskForm.setCreator(currentUser.getUsername());
        taskForm.setProject(projectService.findById(Long.parseLong(pid)));
        taskForm.setProjectNamee(projectService.findById(Long.parseLong(pid)).getProjectName());
        taskForm.setStatus("ToDo");
        taskValidator.validate(taskForm, bindingResult);
        List<Project> currentProjects = projectService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects",currentProjects);
        System.out.println(taskForm.getId());

        logger.info("CREATE TASK");
        logger.info("SAVED TASK NAME = " + taskForm.getTaskName());
        logger.info("SAVED TASK TO = " + taskForm.getProjectNamee());

        if(bindingResult.hasErrors()){
            return "redirect:/backlog?pid=" + pid;
        }

        taskService.save(taskForm);

        Project project = projectService.findById(Long.parseLong(pid));

        logger.info(project.getProjectName());
        List<Task> tasksByCurrentProject = new ArrayList<>();

        for(Task task : project.getTasks()){
            tasksByCurrentProject.add(task);
        }
        model.addAttribute("tasks", tasksByCurrentProject);

        return "redirect:/backlog?pid=" + pid;
    }

    @Modifying
    @Transactional
    @RequestMapping(value = "/backlog", method = RequestMethod.POST, params = "modify")
    public String modifyTask(@ModelAttribute("taskForm") Task taskForm, @RequestParam("pid") String pid,
                             @RequestParam("taskName") String taskName,
                             @RequestParam("taskDescription") String taskDescription,
                             @RequestParam("taskType") String taskType,
                             @RequestParam("taskPriority") String taskPriority,
                             @RequestParam("taskStatus") String taskStatus,
                             @RequestParam("taskVotes") String taskVotes){
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
        currentTask.setTaskName(taskName);
        currentTask.setDescription(taskDescription);
        currentTask.setType(taskType);
        currentTask.setPriority(taskPriority);
        currentTask.setStatus(taskStatus);
        currentTask.setVotesPoint(Integer.parseInt(taskVotes));
        taskService.save(currentTask);

        return "redirect:/backlog?pid=" + pid;
    }

    @Transactional
    @RequestMapping(value = "/backlog", method = RequestMethod.POST, params = "delete")
    public String deleteTask(@ModelAttribute("taskForm") Task taskForm, @RequestParam("pid") String pid,
                             @RequestParam("taskName") String taskName){
        Project project = projectService.findById(Long.parseLong(pid));

        logger.info(project.getProjectName());
        List<Task> tasksByCurrentProject = new ArrayList<>();

        for(Task task : project.getTasks()){
            tasksByCurrentProject.add(task);
        }

        //int index = Integer.parseInt(taskIndex);
        //taskService.deleteByTaskName(tasksByCurrentProject.f)
        int index = 0;
        List<String> tasks = new ArrayList<>();
        for(Task tsk : tasksByCurrentProject)
            tasks.add(tsk.getTaskName());

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
