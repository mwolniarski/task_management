package pl.wolniarskim.task_management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.wolniarskim.task_management.logic.UserService;
import pl.wolniarskim.task_management.model.Task;
import pl.wolniarskim.task_management.model.User;
import pl.wolniarskim.task_management.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class UserPageController {

    UserService service;

    public UserPageController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public String userView(Model model) {
        List<User> users = service.getUsers();
        model.addAttribute("users",users);
        return "usersView.html";
    }

    @PostMapping("/users")
    public String taskView(Model model, @Param("partOfName") String partOfName) {
        List<User> users = service.getUserByPartOfName(partOfName);
        model.addAttribute("users",users);
        return "usersView.html";
    }

    @GetMapping("/userEdit/{id}")
    public String userEdit(Model model, @PathVariable Long id) {
        User user = service.getUserById(id);
        if(user != null){
            model.addAttribute("user",user);
            return "userEdit.html";
        }
        return "";
    }

    @PostMapping("/userEdit/{id}")
    public RedirectView userEdit(@ModelAttribute("user") User user, @PathVariable Long id){
        service.updateUser(id, user);
        return new RedirectView("/users");
    }

    @GetMapping("/newUser")
    public String addUser(Model model) {
        model.addAttribute("user",new User());
        return "addNewUser.html";
    }

    @PostMapping("/newUser")
    public RedirectView addUser(@ModelAttribute("user") User user){
        service.addUser(user);
        return new RedirectView("/users");
    }
}
