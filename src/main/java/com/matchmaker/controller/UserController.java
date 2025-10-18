package com.matchmaker.controller;

import com.matchmaker.model.UserProfile;
import com.matchmaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create user
    @PostMapping
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfile user) {
        return ResponseEntity.ok(userService.save(user));
    }

    // Get all users
    @GetMapping
    public List<UserProfile> getAllUsers() {
        return userService.getAll();
    }

    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserById(@PathVariable Long id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUser(@PathVariable Long id, @RequestBody UserProfile user) {
        UserProfile updated = userService.update(id, user);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.delete(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Search APIs

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

    @GetMapping("/search/caste")
    public List<UserProfile> searchByCaste(@RequestParam String caste) {
        return userService.searchByCaste(caste);
    }
}
