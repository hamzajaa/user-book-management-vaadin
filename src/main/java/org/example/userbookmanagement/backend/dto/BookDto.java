package org.example.userbookmanagement.backend.dto;

public record BookDto(
        Long id,
        String title,
        String isbn,
        String description,
        int numberOfPages,
        boolean onLoan,
        AuthorDto authorDto,
        CategoryDto categoryDto
) {
}
