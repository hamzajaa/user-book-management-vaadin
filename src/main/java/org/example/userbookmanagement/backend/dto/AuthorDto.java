package org.example.userbookmanagement.backend.dto;

public record AuthorDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
