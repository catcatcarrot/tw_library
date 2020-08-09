package com.zy.library.controller;

import com.zy.library.entity.Role;
import com.zy.library.entity.User;
import com.zy.library.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void getUserByUserNumber() throws Exception {
        User expectedOutput = new User(1L,"Smith","123","123");
        Role role = new Role(1);
        expectedOutput.setUserRole(role);

        Mockito.when(userService.getUserByUserNumber(Mockito.any())).thenReturn(expectedOutput);

        mvc.perform(MockMvcRequestBuilders.get("/user?userNumber=123")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(expectedOutput.getUserId()))
                .andExpect(jsonPath("$.userName").value(expectedOutput.getUserName()));
    }

    @Test
    void saveUser() throws Exception {
        User expectedOutput = new User(1L,"Smith","123","123");
        Role role = new Role(1);
        expectedOutput.setUserRole(role);

        Mockito.when(userService.save(Mockito.any())).thenReturn(expectedOutput);

        String requestJson = "{\n" +
                "    \"userId\": 1345,\n" +
                "    \"userNumber\": \"317041024\",\n" +
                "    \"userName\": \"祁正\",\n" +
                "    \"userPassword\": \"123\",\n" +
                "    \"userRole\": {\n" +
                "        \"roleId\": 1\n" +
                "    }\n" +
                "}";

        mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(expectedOutput.getUserId()))
                .andExpect(jsonPath("$.userNumber").value(expectedOutput.getUserNumber()));

    }

    @Test
    void updateUser() throws Exception {
        User expectedOutput = new User(1L,"Smith","123","123");

        Mockito.when(userService.updateUser(Mockito.any())).thenReturn(expectedOutput);

        String requestJson = "{\n" +
                "    \"userId\": 1345,\n" +
                "    \"userNumber\": \"317041024\",\n" +
                "    \"userName\": \"祁正\",\n" +
                "    \"userPassword\": \"123\",\n" +
                "    \"userRole\": {\n" +
                "        \"roleId\": 1\n" +
                "    }\n" +
                "}";

        mvc.perform(MockMvcRequestBuilders.put("/user?userId=1&userName=Smith")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(expectedOutput.getUserId()))
                .andExpect(jsonPath("$.userNumber").value(expectedOutput.getUserNumber()));

    }

    @Test
    void deleteUserByUserId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/user?userId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("success"));
    }
}