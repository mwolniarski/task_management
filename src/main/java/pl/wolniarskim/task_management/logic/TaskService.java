package pl.wolniarskim.task_management.logic;

import org.springframework.stereotype.Service;
import pl.wolniarskim.task_management.model.Task;
import pl.wolniarskim.task_management.model.User;
import pl.wolniarskim.task_management.repositories.TaskRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
        initTasks();
    }

    public Task addTask(Task task){
        Task tmp = repository.save(task);
        return tmp;
    }

    public List<Task> getTasks(){
        List<Task> tmp = repository.findAll();
        return tmp;
    }

    public Task getTaskById(Long id){
        Optional<Task> tmp = repository.findById(id);
        if(tmp.isPresent())
            return tmp.get();
        return null;
    }

    public boolean deleteTask(Long id){
        boolean exist = repository.existsById(id);
        if(exist){
            repository.deleteById(id);
        }
        return exist;
    }

    public boolean updateTask(Long id, Task task){
        boolean exist = repository.existsById(id);
        if(exist){
            repository.findById(id).map(taskTmp ->{
                taskTmp.setTitle(task.getTitle());
                taskTmp.setDescription(task.getDescription());
                taskTmp.setActive(task.isActive());
                taskTmp.setDeadline(task.getDeadline());
                taskTmp.setUsers(task.getUsers());
                taskTmp.setStatus(task.getStatus());
                return repository.save(taskTmp);
            });
        }
        return exist;
    }

    public List<String> createTaskClassesList(List<Task> taskClasses){
        List<String> classes = new ArrayList<>();
        for(Task t:taskClasses){
            switch (t.getStatus()){
                case "New":
                    classes.add("badge bg-primary");
                    break;
                case "Pending":
                    classes.add("badge bg-info text-dark");
                    break;
                case "To Verify":
                    classes.add("badge bg-warning text-dark");
                    break;
                case "Closed":
                    classes.add("badge bg-dark");
                    break;
                case "Done":
                    classes.add("badge bg-success");
                    break;
            }
        }
        return classes;
    }

    public List<Task> getTaskByPartOfTitle(String partOfTitle){
        List<Task> tasks = repository.getTaskByPartOfTitle(partOfTitle.toUpperCase());
        return tasks;
    }

    private void initTasks(){
        if(repository != null){
            repository.save(new Task("Zadanie 1", "Zadanko nr 1", LocalDate.parse("2020-06-03")));
            repository.save(new Task("Zadanie 2", "Zadanko nr 2", LocalDate.parse("2020-01-02")));
            repository.save(new Task("Zadanie 3", "Zadanko nr 3", LocalDate.parse("2010-10-15")));
        }
    }
}
