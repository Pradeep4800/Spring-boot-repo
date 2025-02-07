package com.example.demo11.service;

import com.example.demo11.model.User;
import com.example.demo11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());

            if (user.getProfile() != null && userDetails.getProfile() != null) {
                user.getProfile().setFirstName(userDetails.getProfile().getFirstName());
                user.getProfile().setLastName(userDetails.getProfile().getLastName());
                user.getProfile().setPhoneNumber(userDetails.getProfile().getPhoneNumber());
            }
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
