package com.example.Authentication.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getUser(id);
    }

    @PostMapping("/user")
    public User saveUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    //update password based on userid
    @PutMapping("/user/{id}/{password}")
    public String updateUser(@PathVariable Long id,@PathVariable String password) {
        return service.updateUser(id, password);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }
}
