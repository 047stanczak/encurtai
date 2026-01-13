package com.encurtai.services;

import com.encurtai.dto.UserDTO;
import com.encurtai.exception.EmailAlreadyExistsException;
import com.encurtai.exception.InvalidCredentialsException;
import com.encurtai.exception.UserNotFoundException;
import com.encurtai.models.User;
import com.encurtai.repository.UserRepository;
import com.encurtai.security.TokenSecurity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenSecurity tokenSecurity;

    public void register(UserDTO user){

        if (userRepository.existsByEmail(user.email())) {
            throw new EmailAlreadyExistsException("Email já cadastrado");
        }

        User newUser = new User();
        newUser.setEmail(user.email());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        userRepository.save(newUser);
    }

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

    @Transactional
    public void updateUser(User user, UserDTO userDTO){
        if (userDTO.email() != null && !userDTO.email().isBlank()) {
            user.setEmail(userDTO.email());
        }

        if (userDTO.password() != null && !userDTO.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDTO.password()));
        }

        userRepository.save(user);
    }
}
