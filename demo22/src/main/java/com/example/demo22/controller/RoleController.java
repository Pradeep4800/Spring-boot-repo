package com.example.demo22.controller;

import com.example.demo22.model.Role;
import com.example.demo22.model.User;
import com.example.demo22.repository.RoleRepository;
import com.example.demo22.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    public RoleService roleService;
    @Autowired
    public RoleRepository roleRepository;

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createUser(role);
    }
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        Role savedRole=roleService.createRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }
    @PutMapping("/{roleId}")
    public Role updateRole(@PathVariable Long roleId, @RequestBody Role updatedRole) {
        return roleService.updateRole(roleId, updatedRole);
    }
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}
