package pl.wolniarskim.task_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank(message = "First Name is mandatory!")
    String firstName;
    @NotBlank(message = "Last Name is mandatory!")
    String lastName;
    @NotBlank(message = "Email is mandatory!")
    @Email(message = "Email should be valid")
    String email;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    List<Task> tasks;

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public List<Task> getTasks() {
//        return tasks;
//    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User tmp = (User) obj;
            if(
                tmp.getFirstName() == this.firstName &&
                tmp.getLastName() == this.lastName &&
                tmp.getEmail() == this.email &&
                tmp.getId() == this.id){
                return true;
            }
        }
        return false;
    }
}
