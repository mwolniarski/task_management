package pl.wolniarskim.task_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.wolniarskim.task_management.model.Task;
import pl.wolniarskim.task_management.model.User;
import pl.wolniarskim.task_management.repositories.TaskRepository;
import pl.wolniarskim.task_management.repositories.UserRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository repository;

    @Test
    void httpGetShouldReturnProperTaskWhenIsPresent() throws Exception {
        //given
        long id = repository.save(new Task("test", "description", LocalDate.now())).getId();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/getTasks/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void httpGetShouldReturnNotFoundWhenTaskIsNotPresent() throws Exception {
        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/getTasks/" + 700))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void httpPostShouldReturnCreated() throws Exception {
        //given
        Task task = new Task("test", null, null);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(task);
        //when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks/addTask").contentType("application/json").content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void httpDeleteShouldDeleteGivenTaskById() throws Exception {
        //given
        long id = repository.save(new Task("test", "description", LocalDate.now())).getId();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/deleteTask/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void httpDeleteShouldReturnNotFoundWhereIdIsInvalid() throws Exception {
        //when + then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/deleteTask/" + 700))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void httpPutShouldUpdateAllPropertiesWithoutId() throws Exception {
        //given
        Task task = new Task("test", "description", LocalDate.now());
        long id = repository.save(task).getId();
        task.setId(700);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(task);
        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/updateTask/"+id).contentType("application/json").content(json));
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/getTasks/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}