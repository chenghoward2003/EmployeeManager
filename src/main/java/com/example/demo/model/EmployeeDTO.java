package com.example.demo.model;

public record EmployeeDTO(Long id, String firstName, String lastName, String email) {
    public Long getId() {
        return id;
    }
}