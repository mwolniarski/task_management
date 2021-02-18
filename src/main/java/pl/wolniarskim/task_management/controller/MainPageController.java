package pl.wolniarskim.task_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping
    public String openPage(){
        return "index.html";
    }
}
