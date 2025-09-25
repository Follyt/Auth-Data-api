package com.auth.aService.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LogRequest (@NotBlank @Size(max = 2000) String text) {
}
