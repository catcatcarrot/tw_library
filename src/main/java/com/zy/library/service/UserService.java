package com.zy.library.service;

import com.zy.library.entity.Role;
import com.zy.library.entity.User;

public interface UserService {

    User getUserByUserNumber(String userNumber);

    User saveUser(User user, Role role);

    User updateUser(User user);

    void deleteUserByUserId(Long userId);

}
