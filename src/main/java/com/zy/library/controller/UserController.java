package com.zy.library.controller;

import com.zy.library.entity.Role;
import com.zy.library.entity.User;
import com.zy.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public User getUserByUserNumber(@RequestParam String userNumber) {
        return userService.getUserByUserNumber(userNumber);
    }

    @PostMapping("/user")
    public User saveUser(User user, Role role) {
        return userService.saveUser(user, role);
    }

    @PutMapping("/user")
    public User updateUser(User user){
        return userService.updateUser(user);
    }

    @DeleteMapping("/user")
    public void deleteUserByUserId(Long userId){
        userService.deleteUserByUserId(userId);
    }

}
