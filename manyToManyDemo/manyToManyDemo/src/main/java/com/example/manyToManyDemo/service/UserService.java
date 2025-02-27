package com.example.manyToManyDemo.service;

import com.example.manyToManyDemo.dto.RoleDTO;
import com.example.manyToManyDemo.dto.UserDTO;
import com.example.manyToManyDemo.entity.Role;
import com.example.manyToManyDemo.entity.User;
import com.example.manyToManyDemo.exception.ResourceNotFoundException;
import com.example.manyToManyDemo.repository.RoleRepository;
import com.example.manyToManyDemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
    }

    public List<UserDTO>getAllUsers() {
        List<User>users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("user not found with id: "+userId));
        return convertToDTO(user);
    }
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.getRoles().clear();
        userRepository.save(user);

        userRepository.deleteById(userId);
    }


    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return convertToDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        existingUser.setName(userDTO.getName());
        Set<Role> existingRoles = existingUser.getRoles();

        Set<Role> updatedRoles = new HashSet<>();

        for (RoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepository.findById(roleDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleDTO.getId()));

            role.setName(roleDTO.getName());
            roleRepository.save(role);

            updatedRoles.add(role);
        }
        existingRoles.addAll(updatedRoles);
        existingUser.setRoles(existingRoles);
        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }





    private UserDTO convertToDTO(User user) {
        Set<RoleDTO> roleDTOS = user.getRoles().stream()
                .map(role -> new RoleDTO(role.getId(),role.getName()))
                .collect(Collectors.toSet());
        return new UserDTO(user.getId(), user.getName(), roleDTOS);
    }

    private User convertToEntity(UserDTO userDTO) {
        Set<Role> roles = (userDTO.getRoles() != null) ? userDTO.getRoles().stream()
                .map(roleDTO -> roleRepository.findByName(roleDTO.getName())
                        .orElseGet(()-> {
                            Role newRole = new Role();
                            newRole.setName(roleDTO.getName());
                            return roleRepository.save(newRole);
                        }))
                .collect(Collectors.toSet())
                : new HashSet<>();
        return new User(null, userDTO.getName(), roles);
    }
}
