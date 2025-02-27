package com.example.manyToManyDemo.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
@AllArgsConstructor
public class RoleDTO implements Serializable {
    private Long id;
    private String name;

    public RoleDTO() {}

    public RoleDTO(Long id) {
        this.id = id;
    }

    public RoleDTO(String name) {
        this.name = name;
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
}

