package com.auth.aService.DTO.Response;

import java.util.UUID;

public record UserResponse(UUID id, String email) {
}
