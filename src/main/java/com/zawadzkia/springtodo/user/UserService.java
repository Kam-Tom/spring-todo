package com.zawadzkia.springtodo.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zawadzkia.springtodo.task.status.TaskStatusDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    
    public void create(UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getMatchingPassword())) {
            throw new RuntimeException("Passwords do not match.");
        }

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken.");
        }

        UserModel user = new UserModel();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(true);

        userRepository.save(user);
    }
}
