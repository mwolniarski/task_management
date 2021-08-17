package pl.wolniarskim.task_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wolniarskim.task_management.model.Task;
import pl.wolniarskim.task_management.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
            value = "SELECT * FROM User u WHERE UPPER(CONCAT(u.first_name,' ',u.last_name)) LIKE %?1%",
            nativeQuery = true)
    List<User> getUserByPartOfName(String partOfName);

    User findByEmail(String email);
}
