package pl.wolniarskim.task_management.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank(message = "Title is mandatory!")
    String title;
    String description;
    String status;
    boolean active;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate deadline;

    @ManyToMany
    @JoinTable(
            name = "users_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<User> users;

    public Task() {
        active = true;
        status = "New";
    }

    public Task(String title, String description, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.active = true;
        this.status = "New";
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Task){
            Task tmp = (Task) obj;
            if(
                    tmp.getTitle() == this.title &&
                    tmp.getStatus() == this.status &&
                    tmp.getId() == this.id &&
                    tmp.getDescription() == this.description &&
                    tmp.getDeadline() == this.deadline
            ){
                return true;
            }
        }
        return false;
    }
}
