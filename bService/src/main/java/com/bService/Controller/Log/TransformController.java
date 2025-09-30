package com.bService.Controller.Log;

import com.bService.DTO.Request.TransformRequest;
import com.bService.DTO.Response.TransformResponse;
import com.bService.Service.TransformService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")

public class TransformController {

    private final TransformService service;

    @Value("${internal.token}")
    private String internalToken;

    public TransformController(TransformService service) {
        this.service = service;
    }

    @PostMapping("/transform")
    public TransformResponse transform(
            @RequestHeader("X-Internal-Token") String token,
            @Valid @RequestBody TransformRequest req
    ) {
        if (!internalToken.equals(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid internal token");
        }
        String result = service.toUpper(req.text());
        return new TransformResponse(result);
    }

}
