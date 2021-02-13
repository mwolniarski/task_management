package pl.wolniarskim.task_management.logic;

import org.springframework.stereotype.Service;
import pl.wolniarskim.task_management.model.Task;
import pl.wolniarskim.task_management.repositories.TaskRepository;

import java.util.Optional;

@Service
public class TaskService {

    TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task getTask(long id){
        Optional<Task> task = repository.findById(id);
        if(task.isPresent())
            return task.get();
        return null;
    }

    public void addTask(Task task){
        repository.save(task);
    }

    public void updateTask(Task task){
        Optional<Task> taskFromRepo = repository.findById(task.getId());
        if(taskFromRepo.isPresent()){
            Task taskToSave = taskFromRepo.get();
            taskToSave.setTitle(task.getTitle());
            taskToSave.setStatus(task.isStatus());
            taskToSave.setDeadline(task.getDeadline());
            taskToSave.setDescription(task.getDescription());
            // TODO add userTask
            repository.save(taskToSave);
        }
    }

    public void deleteTask(long id){
        repository.deleteById(id);
    }
}
