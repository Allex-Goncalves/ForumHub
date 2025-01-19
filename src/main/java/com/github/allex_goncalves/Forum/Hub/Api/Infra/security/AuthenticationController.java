package com.github.allex_goncalves.Forum.Hub.Api.Infra.security;


import com.github.allex_goncalves.Forum.Hub.Api.domain.user.AuthenticationDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity startLogin(@RequestBody @Valid AuthenticationDTO auth){
        var authenticationToken = new UsernamePasswordAuthenticationToken(auth.email(), auth.password());
        var authentication = manager.authenticate(authenticationToken);
        var token = tokenService.generateToken((User)authentication.getPrincipal());

        return ResponseEntity.ok(new DataTokenDTO(token));
    }

}
