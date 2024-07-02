package com.dodonov.bootmvc.users;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldCreateNewUser() throws Exception {
        UserDto newUser = new UserDto( null, "Kirill", "test@example.com", 19, List.of());
        String newUserJson = objectMapper.writeValueAsString(newUser);

        var jsonResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var newUserResponse = objectMapper.readValue(jsonResponse, UserDto.class);
        Assertions.assertEquals(newUser.getName(), newUserResponse.getName());
        Assertions.assertEquals(newUser.getEmail(), newUserResponse.getEmail());
        Assertions.assertEquals(newUser.getAge(), newUserResponse.getAge());
        Assertions.assertEquals(newUser.getPets(), newUserResponse.getPets());

        Assertions.assertDoesNotThrow(() -> userService.getUser(newUserResponse.getId()));
    }

}
