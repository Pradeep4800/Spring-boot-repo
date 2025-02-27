package com.example.manyToManyDemo.dto;

import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
public class UserDTO implements Serializable {
    private Long id;
    private String name;
    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO() {}
    public UserDTO(Long id, String name, Set<RoleDTO> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = (roles != null) ? roles : new HashSet<>();
    }
}
