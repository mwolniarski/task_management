package pl.wolniarskim.task_management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.wolniarskim.task_management.logic.TaskService;
import pl.wolniarskim.task_management.logic.UserService;
import pl.wolniarskim.task_management.model.Task;
import pl.wolniarskim.task_management.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TasksPageController {

    TaskService service;
    UserService userService;

    public TasksPageController(TaskService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/tasks")
    public String taskView(Model model) {
        List<Task> tasks = service.getTasks();
        model.addAttribute("tasks",tasks);
        model.addAttribute("taskClasses",service.createTaskClassesList(tasks));
        return "tasksView.html";
    }

    @PostMapping("/tasks")
    public String taskView(Model model, @Param(value = "partOfTitle") String partOfTitle) {
        List<Task> tasks = service.getTaskByPartOfTitle(partOfTitle);
        model.addAttribute("tasks",tasks);
        model.addAttribute("taskClasses",service.createTaskClassesList(tasks));
        return "tasksView.html";
    }

    @GetMapping("/taskEdit/{id}")
    public String taskEdit(Model model, @PathVariable Long id) {
        Task task = service.getTaskById(id);
        List<User> usersList = userService.getUsers();
        if(task != null){
            model.addAttribute("task",task);
            model.addAttribute("usersList",usersList);
            return "taskEdit.html";
        }
        return "";
    }

    @GetMapping("/newTask")
    public String addTask(Model model) {
        List<User> userList = userService.getUsers();
        model.addAttribute("task",new Task());
        model.addAttribute("usersList",userList);
        return "addNewTask.html";
    }

    @PostMapping("/taskEdit/{id}")
    public RedirectView taskEdit(@ModelAttribute("task") Task task, @PathVariable Long id){
        service.updateTask(id, task);
        return new RedirectView("/tasks");
    }

    @PostMapping("/newTask")
    public RedirectView addTask(@ModelAttribute("task") Task task){
        service.addTask(task);
        return new RedirectView("/tasks");
    }

}
