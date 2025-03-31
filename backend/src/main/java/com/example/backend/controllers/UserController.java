package com.example.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.models.User;
import com.example.backend.services.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.getUserByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        // Save the new user
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(201).body(registeredUser);
    }

  

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody User user) {
    System.out.println("🔍 Login attempt for username: " + user.getUsername());

    Optional<User> existingUser = userService.getUserByUsername(user.getUsername());

    if (existingUser.isEmpty()) {
        System.out.println("User not found: " + user.getUsername());
        return ResponseEntity.status(401).body("Unauthorized access. User not found.");
    }

    User foundUser = existingUser.get();
    System.out.println("✅ User found: " + foundUser.getUsername());
    System.out.println("🔍 Stored password: " + foundUser.getPassword());
    System.out.println("🔍 Entered password: " + user.getPassword());

    if (!foundUser.getPassword().equals(user.getPassword())) {
        System.out.println("Incorrect password for: " + user.getUsername());
        return ResponseEntity.status(401).body("Unauthorized access. Incorrect password.");
    }

    System.out.println("Login successful for: " + user.getUsername());
    return ResponseEntity.ok(foundUser);
}



    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
