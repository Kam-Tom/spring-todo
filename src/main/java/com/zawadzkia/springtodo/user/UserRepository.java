package com.zawadzkia.springtodo.user;

import com.zawadzkia.springtodo.user.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
}
