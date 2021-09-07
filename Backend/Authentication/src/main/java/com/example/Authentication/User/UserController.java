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
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getUser(id);
    }

    @GetMapping("/user")
    public String getAllUsers() {
        return "GET USER TEST! ";
    }

//    @PostMapping("/user")
//    public User saveUser(@RequestBody User user) {
//        return service.saveUser(user);
//    }
    @PostMapping("/user/register")
    public String registerUser(@RequestBody User user) {
        System.out.println("HERE");
        if (userRepository.existsByEmail(user.getEmail())){
            return "User with that email already exists";
        }
        if (user.getRoles() == null){
            List<String> roles = new ArrayList<>();
            roles.add("USER");
            user.setRole(roles);
        }
        service.registerUser(user);
        return "User" + user.getEmail() + "successfully registered";

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
