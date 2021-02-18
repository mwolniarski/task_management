package pl.wolniarskim.task_management.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wolniarskim.task_management.model.Task;
import pl.wolniarskim.task_management.model.User;
import pl.wolniarskim.task_management.repositories.UserRepository;

import java.util.*;

@Service
public class UserService {

    Logger logger= LoggerFactory.getLogger("UserService");

    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
        initUsers();
    }

    public User addUser(User user){
        User tmp = repository.save(user);
        return tmp;
    }

    public List<User> getUsers(){
        List<User> tmp = repository.findAll();
        return tmp;
    }

    public User getUserById(Long id){
        Optional<User> tmp = repository.findById(id);
        if(tmp.isPresent())
            return tmp.get();
        return null;
    }

    public boolean deleteUser(Long id){
        boolean exist = repository.existsById(id);
        if(exist){
            repository.deleteById(id);
        }
        return exist;
    }

    public boolean updateUser(Long id, User user){
        boolean exist = repository.existsById(id);
        if(exist){
            repository.findById(id).map(userTmp ->{
                userTmp.setFirstName(user.getFirstName());
                userTmp.setLastName(user.getLastName());
                userTmp.setEmail(user.getEmail());
                return repository.save(userTmp);
            });
        }
        return exist;
    }

    public List<User> getUserByPartOfName(String partOfName){
        List<User> users = repository.getUserByPartOfName(partOfName.toUpperCase());
        return users;
    }

    private void initUsers(){
        repository.save(new User("Michal","Kapusta","michal@wp.pl"));
        repository.save(new User("Roman","Pomidor","pomidor@wp.pl"));
        repository.save(new User("Arkadiusz","Gruszka","gruszka@wp.pl"));
    }
}
