package com.auth.aService.Controller.Log;

import com.auth.aService.DTO.Request.LogRequest;
import com.auth.aService.DTO.Request.LogResponse;
import com.auth.aService.Service.ProcessingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LogController {


    private final ProcessingService processingService;

    public LogController(ProcessingService ProcessingService) {
        this.processingService = ProcessingService;
    }

    @PostMapping("/process")
    public ResponseEntity<LogResponse> process(@Valid @RequestBody LogRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String subject = auth.getName();
        UUID userId = UUID.fromString(subject);

        String result = processingService.process(userId, request.text());
        return ResponseEntity.ok(new LogResponse(result));
    }
}
