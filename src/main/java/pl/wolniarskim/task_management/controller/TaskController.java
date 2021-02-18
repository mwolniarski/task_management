package pl.wolniarskim.task_management.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wolniarskim.task_management.logic.TaskService;
import pl.wolniarskim.task_management.model.Task;

import javax.persistence.GeneratedValue;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping("/addTask")
    public ResponseEntity<Task> addTask(@Valid @RequestBody Task task){
        return ResponseEntity.created(URI.create("/api/tasks/getTask/" + service.addTask(task).getId())).build();
    }

    @GetMapping("/getTasks")
    public ResponseEntity<List<Task>> getTasks(){
        return ResponseEntity.ok(service.getTasks());
    }

    @GetMapping("/getTasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Task tmp = service.getTaskById(id);
        if(tmp == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tmp);
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id){
        if(service.deleteTask(id))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/updateTask/{id}")
    public ResponseEntity updateTask(@PathVariable Long id, @Valid @RequestBody Task task){
        if(service.updateTask(id, task))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getTaskByPartOfTitle")
    public ResponseEntity<List<Task>> getTaskByPartOfTitle(@Param("partOfTitle") String partOfTitle){
        return ResponseEntity.ok(service.getTaskByPartOfTitle(partOfTitle));
    }
}
