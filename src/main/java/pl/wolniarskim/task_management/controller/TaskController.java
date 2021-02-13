package pl.wolniarskim.task_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wolniarskim.task_management.logic.TaskService;
import pl.wolniarskim.task_management.model.Task;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }
    
}
