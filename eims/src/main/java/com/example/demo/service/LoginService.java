package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.model.dto.LoginResponse;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoginService {

    private UserRepository userRepository;

    public LoginResponse login(Users user){
        String jwtToken = "";

        LoginResponse response = new LoginResponse();
        if(user.getUsername() == null || user.getPass() == null) {
            response.setMessage("Please enter your credentials");
            response.setStatus(false);
            //Error Handling to be placed here
            //return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String username = user.getUsername();
        String pass = user.getPass();

        List<Users> reg = userRepository.findByUsernameAndPassword(username, pass);

        jwtToken = Jwts.builder().setSubject(username).claim("user", user)
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretKey")
                .compact();

        if(Objects.isNull(reg)|| reg.isEmpty()) {
            response.setStatus(false);
            response.setMessage("User doesn't exist");
        }
        else {
            response.setStatus(true);
            response.setMessage("Welcome");
            response.setToken(jwtToken);
        }

        return response;
    }
}
