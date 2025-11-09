package com.matchmaker.controller;

import com.matchmaker.model.UserProfile;
import com.matchmaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // ------------------------
    // Register API
    // ------------------------
    @PostMapping("/register")
    public ResponseEntity<UserProfile> register(@RequestBody UserProfile user) {
        try {
            UserProfile createdUser = userService.registerUser(user); // hashes password inside service
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ------------------------
    // Login API
    // ------------------------
    @PostMapping("/login")
    public ResponseEntity<UserProfile> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserProfile user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(user);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(401).body(null);
        }
    }

    // ------------------------
    // DTO for login
    // ------------------------
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // ------------------------
    // Existing CRUD APIs
    // ------------------------
    @PostMapping
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfile user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping
    public List<UserProfile> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserById(@PathVariable Long id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUser(@PathVariable Long id, @RequestBody UserProfile user) {
        UserProfile updated = userService.update(id, user);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.delete(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // ------------------------
    // Search APIs
    // ------------------------
    @GetMapping("/search/gender")
    public List<UserProfile> searchByGender(@RequestParam String gender) {
        return userService.searchByGender(gender);
    }

    @GetMapping("/search/age")
    public List<UserProfile> searchByAge(@RequestParam int min, @RequestParam int max) {
        return userService.searchByAgeRange(min, max);
    }

    @GetMapping("/search/city")
    public List<UserProfile> searchByCity(@RequestParam String city) {
        return userService.searchByCity(city);
    }

    @GetMapping("/search/religion")
    public List<UserProfile> searchByReligion(@RequestParam String religion) {
        return userService.searchByReligion(religion);
    }

    // Simple Ping API
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }


    @GetMapping("/search/caste")
    public List<UserProfile> searchByCaste(@RequestParam String caste) {
        return userService.searchByCaste(caste);
    }
}
