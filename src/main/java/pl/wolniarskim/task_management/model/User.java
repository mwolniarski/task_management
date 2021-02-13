package pl.wolniarskim.task_management.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    long id;

    String firstName;
    String lastName;
    String email;
}
