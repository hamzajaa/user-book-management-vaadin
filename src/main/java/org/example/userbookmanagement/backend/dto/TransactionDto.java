package org.example.userbookmanagement.backend.dto;

import java.time.LocalDateTime;

public record TransactionDto(
        Long id,
        BookDto bookDto,
        ClientDto clientDto,
        LocalDateTime checkoutDate,
        LocalDateTime returnDate
) {
}
