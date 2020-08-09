package com.zy.library.controller;

import com.zy.library.entity.User;
import com.zy.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public User getUserByUserNumber(@RequestParam String userNumber) {
        return userService.getUserByUserNumber(userNumber);
    }

    @PostMapping("/user")
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/user")
    public String deleteUserByUserId(@RequestParam Long userId) {
        userService.deleteUserByUserId(userId);
        return "success";
    }

}
