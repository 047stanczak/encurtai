package com.encurtai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.encurtai.dto.RegisterDTO;
import com.encurtai.models.Login;
import com.encurtai.repository.LoginRepository;

@Service
public class RegisterService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void register(RegisterDTO register){

            Login newLogin = new Login();
            newLogin.setEmail(register.email);
            newLogin.setPassword(passwordEncoder.encode(register.password));
            loginRepository.save(newLogin);

    }
}
