package com.zy.library.service.impl;

import com.zy.library.entity.Role;
import com.zy.library.entity.User;
import com.zy.library.repository.UserRepository;
import com.zy.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUserNumber(String userNumber) {
        return userRepository.findByUserNumber(userNumber);
    }

    @Override
    public User saveUser(User user, Role role) {
        user.setUserRole(role);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User userBeforeUpdated = userRepository.getOne(user.getUserId());

        if (user.getUserName() != null) {
            userBeforeUpdated.setUserName(user.getUserName());
        }
        if (user.getUserPassword() != null) {
            userBeforeUpdated.setUserPassword(user.getUserPassword());
        }

        return userRepository.save(userBeforeUpdated);
    }

    @Override
    public void deleteUserByUserId(Long userId) {
        userRepository.deleteById(userId);
    }
}
