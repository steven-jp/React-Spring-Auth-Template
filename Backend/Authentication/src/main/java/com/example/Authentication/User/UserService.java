package com.example.Authentication.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    @Autowired
    PasswordEncoder encoder;

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
        return "User" + newUser.getEmail() + "successfully registered";
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
