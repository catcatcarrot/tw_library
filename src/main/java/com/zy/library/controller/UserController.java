package com.zy.library.controller;

import com.zy.library.entity.User;
import com.zy.library.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户管理相关接口")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("根据userNumber查询用户的接口")
    @ApiImplicitParam(name = "userNumber", value = "用户编号", required = true)
    @GetMapping(value = "/user")
    public User getUserByUserNumber(@RequestParam String userNumber) {
        return userService.getUserByUserNumber(userNumber);
    }

    @ApiOperation("添加用户的接口")
    @PostMapping("/user")
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @ApiOperation("修改用户信息的接口")
    @PutMapping("/user")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @ApiOperation("根据userId删除用户的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    @DeleteMapping("/user")
    public String deleteUserByUserId(@RequestParam Long userId) {
        userService.deleteUserByUserId(userId);
        return "success";
    }

}
