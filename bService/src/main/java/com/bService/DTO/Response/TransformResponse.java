package com.bService.DTO.Response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TransformResponse (@NotBlank String result) {
}
