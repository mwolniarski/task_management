package pl.wolniarskim.task_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.wolniarskim.task_management.model.User;
import pl.wolniarskim.task_management.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @Test
    void httpGetShouldReturnProperUserWhenIsPresent() throws Exception {
        //given
        long id = repository.save(new User("Roman","Polana","test@wp.pl",passwordEncoder().encode("test123"))).getId();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/getUsers/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void httpGetShouldReturnNotFoundWhenUserIsNotPresent() throws Exception {
        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/getUsers/" + 700))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void httpPostShouldReturnCreated() throws Exception {
        //given
        User user = new User("Roman","Polana","test@wp.pl",passwordEncoder().encode("test123"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(user);
        //when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/addUser").contentType("application/json").content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void httpDeleteShouldDeleteGivenUserById() throws Exception {
        //given
        long id = repository.save(new User("Roman","Polana","test@wp.pl",passwordEncoder().encode("test123"))).getId();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/deleteUser/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void httpDeleteShouldReturnNotFoundWhereIdIsInvalid() throws Exception {
        //when + then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/deleteUser/" + 700))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void httpPutShouldUpdateAllPropertiesWithoutId() throws Exception {
        //given
        User user = new User("Roman","Polana","test@wp.pl",passwordEncoder().encode("test123"));
        long id = repository.save(user).getId();
        user.setId(700);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(user);
        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/updateUser/"+id).contentType("application/json").content(json));
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/getUsers/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}