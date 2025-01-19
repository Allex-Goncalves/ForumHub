package com.github.allex_goncalves.Forum.Hub.Api.service;


import com.github.allex_goncalves.Forum.Hub.Api.Infra.security.TokenService;
import com.github.allex_goncalves.Forum.Hub.Api.repository.CourseRepository;
import com.github.allex_goncalves.Forum.Hub.Api.repository.TopicRepository;
import com.github.allex_goncalves.Forum.Hub.Api.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HttpServletRequest request;


    public UserDetails validateUser(HttpServletRequest request) throws UsernameNotFoundException {
        var tokenJWT = recoverTokenFromRequest(request);
        if (tokenJWT == null) {
            throw new UsernameNotFoundException("Token JWT não encontrado no cabeçalho.");
        }

        String userEmail = tokenService.getSubject(tokenJWT);
        var user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + userEmail);
        }

        return user;
    }

    private String recoverTokenFromRequest(HttpServletRequest request) {

        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }

}
