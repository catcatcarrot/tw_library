package com.zy.library.repository;

import com.zy.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {

    User findByUserNumber(String userNumber);

}
