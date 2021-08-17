package pl.wolniarskim.task_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wolniarskim.task_management.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(
            value = "SELECT * FROM Task t WHERE UPPER(t.title) LIKE %?1%",
            nativeQuery = true)
    List<Task> getTaskByPartOfTitle(String partOfTitleToUpper);

    
}
