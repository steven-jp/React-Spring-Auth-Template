package com.example.Authentication.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public User saveUser(User user) {
        return repo.save(user);
    }

    public String deleteUser(Long id) {
        repo.deleteById(id);
        return "Deleted user : " + id;
    }

    public User getUser(Long id) {
        return repo.findById(id).orElse(new User());
    }

    public String updateUser(Long id, String password) {
        Optional<User> optionalUser = repo.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(password);
            repo.save(user);
            return "Updated User : " + id;
        }
        return "User was not found";
    }
}
