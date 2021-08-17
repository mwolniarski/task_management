package pl.wolniarskim.task_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

//    @NotBlank(message = "First Name is mandatory!")
    String firstName;
//    @NotBlank(message = "Last Name is mandatory!")
    String lastName;
    @NotBlank(message = "Email is mandatory!")
    @Email(message = "Email should be valid")
    String email;

    String password;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    List<Task> tasks;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User tmp = (User) obj;
            if(
                tmp.getFirstName().equals(this.firstName) &&
                tmp.getLastName().equals(this.lastName) &&
                tmp.getEmail().equals(this.email) &&
                tmp.getId() == this.id){
                return true;
            }
        }
        return false;
    }
}
