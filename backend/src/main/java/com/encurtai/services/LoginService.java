package com.encurtai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.encurtai.dto.UserDTO;
import com.encurtai.exception.InvalidCredentialsException;
import com.encurtai.exception.UserNotFoundException;
import com.encurtai.models.User;
import com.encurtai.repository.UserRepository;
import com.encurtai.security.TokenSecurity;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenSecurity tokenSecurity;

    public String login(UserDTO user){
        User userEntity = userRepository.findByEmail(user.email());

        if (userEntity == null) {
            throw new UserNotFoundException("Usuário não encontrado");
        }

        boolean passwordMatches = passwordEncoder.matches(
                user.password(),
                userEntity.getPassword()
        );
        
        if (!passwordMatches) {
            throw new InvalidCredentialsException("Senha inválida");
        }

        return tokenSecurity.generateToken(userEntity);
    }
}
