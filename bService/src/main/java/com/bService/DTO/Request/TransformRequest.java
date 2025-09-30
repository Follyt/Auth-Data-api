package com.bService.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TransformRequest (@NotBlank @Size(min = 8, max = 2000)String text) {
}
