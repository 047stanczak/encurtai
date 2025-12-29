package com.encurtai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.encurtai.dto.UserDTO;
import com.encurtai.models.User;
import com.encurtai.repository.UserRepository;

@Service
public class RegisterService {

    @Autowired
    private UserRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void register(UserDTO user){

            User newLogin = new User();
            newLogin.setEmail(user.email);
            newLogin.setPassword(passwordEncoder.encode(user.password));
            loginRepository.save(newLogin);

    }
}
