package com.encurtai.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.encurtai.models.User;
import com.encurtai.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenSecurity tokenSecurity;
    private final UserRepository userRepository;

    public SecurityFilter(TokenSecurity tokenSecurity, UserRepository userRepository) {
        this.tokenSecurity = tokenSecurity;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = recoverToken(request);

        if (token != null) {
            String email = tokenSecurity.validateToken(token);

            if (email != null && !email.isBlank()) {
                User user = userRepository.findByEmail(email);

                if (user != null) {
                    var authentication =
                        new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                        );

                    SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
                }
            }
        }

    filterChain.doFilter(request, response);
}


    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }
        
        return null;
    }
}
