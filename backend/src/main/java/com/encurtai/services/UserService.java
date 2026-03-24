package com.encurtai.services;

import com.encurtai.dto.UserChangePasswordDTO;
import com.encurtai.dto.UserDTO;
import com.encurtai.exception.EmailAlreadyExistsException;
import com.encurtai.exception.InvalidCredentialsException;
import com.encurtai.exception.UserNotFoundException;
import com.encurtai.models.Roles;
import com.encurtai.models.User;
import com.encurtai.repository.UserRepository;
import com.encurtai.security.TokenSecurity;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenSecurity tokenSecurity;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenSecurity tokenSecurity) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenSecurity = tokenSecurity;
    }

    public void register(UserDTO user){

        if (userRepository.existsByEmail(user.email())) {
            throw new EmailAlreadyExistsException("Email já cadastrado");
        }

        User newUser = new User();
        newUser.setEmail(user.email());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        newUser.setRoles(Roles.USER);
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
    public void updateUser(User user, UserChangePasswordDTO userChangePasswordDTO){

            if(passwordEncoder.matches(userChangePasswordDTO.password(), user.getPassword())){
                user.setPassword(passwordEncoder.encode(userChangePasswordDTO.newPassword()));
                userRepository.save(user);
            } else {
                throw new InvalidCredentialsException("Senha atual inválida");
            }
    } 
}
