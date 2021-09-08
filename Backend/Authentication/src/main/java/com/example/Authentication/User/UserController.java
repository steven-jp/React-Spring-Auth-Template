package com.example.Authentication.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getUser(id);
    }

    @GetMapping("/user")
    public String test() {
        return "GET TEST! ";
    }

    @PostMapping("/user/register")
    public String registerUser(@RequestBody RegistrationDto user) {
        return service.registerUser(user);
    }

    //update password based on userid
    @PutMapping("/user/{email}/{password}/{newPassword}")
    public String updateUser(@PathVariable String email, @PathVariable String password, @PathVariable String newPassword) {
        return service.updateUserPassword(email, password, newPassword);
    }

    @DeleteMapping("/user/{email}/{password}")
    public String deleteUser(@PathVariable String email, @PathVariable String password) {
        return service.deleteUser(email, password);
    }
}
