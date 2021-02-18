package pl.wolniarskim.task_management.logic;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wolniarskim.task_management.model.Task;
import pl.wolniarskim.task_management.repositories.TaskRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Test
    void shouldReturnProperListOfClasses() {

        // given
        TaskService service = new TaskService(null);

        List<Task> tasks = new ArrayList<>();
        List<String> statuses = new ArrayList<>();
        statuses.add("New");
        statuses.add("Pending");
        statuses.add("To Verify");
        statuses.add("Closed");
        statuses.add("Done");
        for(int i=0;i<5;i++){
            Task tmp = new Task();
            tmp.setStatus(statuses.get(i));
            tasks.add(tmp);
        }
        List<String> properClasses = Arrays.asList("badge bg-primary","badge bg-info text-dark","badge bg-warning text-dark","badge bg-dark","badge bg-success");
        // when
        List<String> returnedClasses = service.createTaskClassesList(tasks);
        // then
        assertEquals(properClasses, returnedClasses);
    }
}