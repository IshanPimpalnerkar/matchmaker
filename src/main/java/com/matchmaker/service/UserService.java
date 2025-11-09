package com.matchmaker.service;

import com.matchmaker.model.UserProfile;
import com.matchmaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserProfile save(UserProfile user) {
        return userRepository.save(user);
    }

    public List<UserProfile> getAll() {
        return userRepository.findAll();
    }

    public Optional<UserProfile> getById(Long id) {
        return userRepository.findById(id);
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserProfile registerUser(UserProfile user) {
        // Check if user already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        // Hash password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserProfile loginUser(String email, String password) {
        Optional<UserProfile> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        UserProfile user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user; // You could return a token or session later
    }

    public UserProfile update(Long id, UserProfile updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setAge(updatedUser.getAge());
                    user.setGender(updatedUser.getGender());
                    user.setCity(updatedUser.getCity());
                    user.setReligion(updatedUser.getReligion());
                    user.setCaste(updatedUser.getCaste());
                    user.setEmail(updatedUser.getEmail());
                    user.setPhone(updatedUser.getPhone());
                    return userRepository.save(user);
                })
                .orElse(null);
    }

    public boolean delete(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

    public List<UserProfile> searchByGender(String gender) {
        return userRepository.findByGender(gender);
    }

    public List<UserProfile> searchByAgeRange(int min, int max) {
        return userRepository.findByAgeBetween(min, max);
    }

    public List<UserProfile> searchByCity(String city) {
        return userRepository.findByCity(city);
    }

    public List<UserProfile> searchByReligion(String religion) {
        return userRepository.findByReligion(religion);
    }

    public List<UserProfile> searchByCaste(String caste) {
        return userRepository.findByCaste(caste);
    }
}
