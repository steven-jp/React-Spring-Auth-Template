package com.example.Authentication.User;

import com.example.Authentication.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil util;

    public ResponseEntity<?> loginUser(User user) throws Exception {
        try {
            Authentication auth = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            String token = util.generateToken(auth);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization: Bearer ", token);

            return new ResponseEntity<Object>(headers,HttpStatus.OK);
        }catch (AuthenticationException e){
            throw new Exception("Invalid credentials");
        }
    }

    public String registerUser(RegistrationDto user) {
        //check if email exists in repo
        if (repo.existsByEmail(user.getEmail())){
            return "User with that email already exists";
        }
        //check empty fields
        if (user.getPassword().length() < 8){
            return "Password must be at least 8 characters";
        }
        if (user.getEmail().length() < 3){
            return "Incorrect email";
        }

        //check if passwords match
        if (!user.getPassword().equals(user.getConfirmedPassword())){
            return "Passwords do not match";
        }

        //create user object
        User newUser = new User();
        newUser.setRole(List.of("User"));
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());

        repo.save(newUser);
        return "User " + newUser.getEmail() + " successfully registered";
    }

    public String deleteUser(String email, String password) {
        Optional<User> optionalUser = repo.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!encoder.matches(password,user.getPassword())){
                return "Incorrect password";
            }

            repo.deleteById(user.getId());
            return "Deleted user : " + user.getId();
        }
        return "User was not found";
    }
    public User getUser(Long id) {
        return repo.findById(id).orElse(new User());
    }

    public String updateUserPassword(String email, String password, String newPassword) {
        Optional<User> optionalUser = repo.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!encoder.matches(password,user.getPassword())){
                return "Incorrect password";
            }
            //update to new password
            user.setPassword(encoder.encode(newPassword));
            repo.save(user);
            return "Updated User : " + user.getId();
        }
        return "User was not found";
    }
}
