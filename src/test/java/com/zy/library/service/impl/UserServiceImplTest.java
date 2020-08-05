package com.zy.library.service.impl;

import com.zy.library.entity.Role;
import com.zy.library.entity.User;
import com.zy.library.repository.UserRepository;
import com.zy.library.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getUserByUserNumber() {
        String input = "张三三";
        User expectedOutput = new User(1L, "张三三", "123", "124");

        Mockito.when(userRepository.findByUserNumber(input)).thenReturn(expectedOutput);

        assertEquals(expectedOutput, userService.getUserByUserNumber(input));
    }

    @Test
    void saveUser() {
        User inputUser = new User(1L, "张三三", "123", "124");
        Role inputRole = new Role(1);
        inputUser.setUserRole(inputRole);

        User expectedOutput = inputUser;

        Mockito.when(userRepository.save(inputUser)).thenReturn(expectedOutput);

        assertEquals(expectedOutput, userService.saveUser(inputUser, inputRole));

    }

    @Test
    void updateUser() {
        User input = new User(1L, "张三三", "123", "124");
        User expectedOutput = new User(1L, "张三", "123", "124");

        Mockito.when(userRepository.getOne(input.getUserId())).thenReturn(input);
        Mockito.when(userRepository.save(input)).thenReturn(expectedOutput);

        assertEquals(expectedOutput, userRepository.save(input));
    }

    @Test
    void deleteUserByUserId() {
        Long input = 5L;

        userService.deleteUserByUserId(input);

        Mockito.verify(userRepository).deleteById(input);
    }
}