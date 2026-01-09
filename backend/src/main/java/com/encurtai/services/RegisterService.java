package com.encurtai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.encurtai.dto.UserDTO;
import com.encurtai.exception.EmailAlreadyExistsException;
import com.encurtai.models.User;
import com.encurtai.repository.UserRepository;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void register(UserDTO user){

        if (userRepository.existsByEmail(user.email())) {
            throw new EmailAlreadyExistsException("Email já cadastrado");
        }

        User newUser = new User();
        newUser.setEmail(user.email());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        userRepository.save(newUser);
    }
}
