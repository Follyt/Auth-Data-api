package com.auth.aService.DTO.ApiError;

public record ApiError (
        String timestamp,
        int status,
        String error,
        String message,
        String path
){
}
