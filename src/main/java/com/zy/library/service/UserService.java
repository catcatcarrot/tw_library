package com.zy.library.service;

import com.zy.library.entity.User;

public interface UserService {

    User getUserByUserNumber(String userNumber);

    User save(User user);

    User updateUser(User user);

    void deleteUserByUserId(Long userId);

}
