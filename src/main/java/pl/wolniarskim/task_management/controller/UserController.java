package pl.wolniarskim.task_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wolniarskim.task_management.logic.UserService;
import pl.wolniarskim.task_management.model.User;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user){
        return ResponseEntity.created(URI.create("/api/users/getUsers/" + service.addUser(user).getId())).build();
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(service.getUsers());
    }

    @GetMapping("/getUsers/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User tmp = service.getUserById(id);
        if(tmp == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tmp);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        if(service.deleteUser(id))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @Valid @RequestBody User user){
        if(service.updateUser(id, user))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
