package pl.wolniarskim.task_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wolniarskim.task_management.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
