package pl.wolniarskim.task_management.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserTask {

    @Id
    long id;

    @ManyToOne
    User user;

    @ManyToOne
    Task task;
}
