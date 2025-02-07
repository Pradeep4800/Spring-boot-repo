package com.example.demo22.service;

import com.example.demo22.exception.ResourceNotFoundException;
import com.example.demo22.model.Role;
import com.example.demo22.model.User;
import com.example.demo22.repository.RoleRepository;
import com.example.demo22.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    public Role createUser(Role role) {
        return roleRepository.save(role);
    }
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
    public Role updateRole(Long roleId, Role updatedRole) {
        Role existingRole=roleRepository.findById(roleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: "+roleId));
        existingRole.setName(updatedRole.getName());
        return roleRepository.save(existingRole);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
