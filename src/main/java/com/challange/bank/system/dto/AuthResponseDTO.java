package com.challange.bank.system.dto;

public record AuthResponseDTO(
        String status,
        AuthorizationDataDTO authorizationDataDTO
) {
}
